package controller.user;

import controller.BaseController;
import domain.*;
import domain.ApplySelfExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserApplySelfController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

/*    @RequiresRoles("cadre")
    @RequestMapping("/applySelf_download")
    public void applySelf_download(@CurrentUser SysUser loginUser,
                                   Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ApplySelfFile applySelfFile = applySelfFileMapper.selectByPrimaryKey(id);
        
        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        Integer applyId = applySelfFile.getApplyId();
        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        if(applySelf.getCadreId().intValue() != cadre.getId().intValue()) {
            throw new UnauthorizedException();
        }

        DownloadUtils.download(request, response,
                springProps.uploadPath + applySelfFile.getFilePath(), applySelfFile.getFileName());
    }*/

    @RequiresRoles("cadre")
    @RequestMapping("/applySelf_view")
    public String applySelf_view(@CurrentUser SysUser loginUser, Integer id, ModelMap modelMap) {

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
        if(applySelf.getCadreId().intValue() != cadre.getId().intValue()) {
            throw new UnauthorizedException();
        }
        modelMap.put("sysUser", loginUser);
        modelMap.put("cadre", cadre);
        modelMap.put("applySelf", applySelf);

        List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
        modelMap.put("files", files);

        Map<Integer, Integer> approvalResultMap = applySelfService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);
        modelMap.put("approverTypeMap", approverTypeService.findAll());

        // 本年度的申请记录
        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadre.getId());
        Integer currentYear = DateUtils.getYear(applySelf.getApplyDate());
        criteria.andApplyDateBetween(DateUtils.parseDate(currentYear +"-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(currentYear +"-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");
        List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
        modelMap.put("currentYear", currentYear);
        modelMap.put("applySelfs", applySelfs);

        modelMap.put("justView", true);

        return "user/applySelf/applySelf_view";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/applySelf_note")
    public String applySelf_note(ModelMap modelMap) {

        modelMap.put("notice", "说明");
        return "user/applySelf/applySelf_note";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/applySelf")
    public String applySelf() {

        return "index";
    }
    @RequiresRoles("cadre")
    @RequestMapping("/applySelf_page")
    public String applySelf_page(@CurrentUser SysUser loginUser,
                                 @RequestParam(required = false, defaultValue = "create_time") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());

        int count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("applySelfs", applySelfs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        modelMap.put("approverTypeMap", approverTypeMap);

        return "user/applySelf/applySelf_page";
    }

    @RequiresRoles("cadre")
    @RequestMapping(value = "/applySelf_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_del(@CurrentUser SysUser loginUser, HttpServletRequest request, Integer id) {

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        if (id != null) {
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            if(CmTag.getAdminFirstTrialStatus(id)==null && applySelf.getCadreId().intValue() == cadre.getId().intValue()) { // 没有初审时才允许删除

                ApplySelfFileExample example = new ApplySelfFileExample();
                example.createCriteria().andApplyIdEqualTo(id);
                applySelfFileMapper.deleteByExample(example); // 先删除相关材料

                applySelfService.del(id);
                logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除因私出国申请：%s", id));
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("cadre")
    @RequestMapping(value = "/applySelf_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_au(@CurrentUser SysUser loginUser,
                               ApplySelf record,
                               String _startDate,
                               String _endDate,
                               @RequestParam(value = "_files[]") MultipartFile[] _files,
                               HttpServletRequest request) {
        int userId= loginUser.getId();
        List<ApplySelfFile> applySelfFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {
            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  File.separator
                    + "apply_self" + File.separator + userId + File.separator
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            ApplySelfFile applySelfFile = new ApplySelfFile();
            applySelfFile.setFileName(originalFilename);
            applySelfFile.setFilePath(savePath);
            applySelfFile.setCreateTime(new Date());

            applySelfFiles.add(applySelfFile);
        }

        record.setApplyDate(new Date());

        if(StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }
        if(record.getId()==null) {
            Cadre cadre = cadreService.findByUserId(userId);
            record.setCadreId(cadre.getId());
            record.setCreateTime(new Date());
            record.setIp(IpUtils.getRealIp(request));
            record.setStatus(true);// 提交
            applySelfService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加因私出国申请：%s", record.getId()));
        }else{

            record.setStatus(true);// 重新提交
            applySelfService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新因私出国申请：%s", record.getId()));
        }
        Integer applyId = record.getId();
        for (ApplySelfFile applySelfFile : applySelfFiles) {
            applySelfFile.setApplyId(applyId);
            applySelfFileMapper.insert(applySelfFile);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("cadre")
    @RequestMapping(value = "/applySelfFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelfFile_del(@CurrentUser SysUser loginUser, Integer id) {

        ApplySelfFile applySelfFile = applySelfFileMapper.selectByPrimaryKey(id);
        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfFile.getApplyId());

        Integer firstTrialStatus = CmTag.getAdminFirstTrialStatus(applySelf.getId());
        if((firstTrialStatus==null||firstTrialStatus==0)
                && applySelf.getCadreId().intValue() == cadre.getId().intValue()) { // 没有初审或初审未通过时才允许删除
            applySelfFileMapper.deleteByPrimaryKey(id);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("cadre")
    @RequestMapping("/applySelf_au")
    public String applySelf_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            modelMap.put("applySelf", applySelf);

            List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
            modelMap.put("files", files);
        }

        List<String> countryList = new ArrayList<>();
        Map<Integer, Country> countryMap = countryService.findAll();
        for (Country country : countryMap.values()) {
            countryList.add(country.getCninfo());
        }
        modelMap.put("countryList", JSONUtils.toString(countryList));

        return "user/applySelf/applySelf_au";
    }
}
