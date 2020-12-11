package controller.pm;

import domain.base.ContentTpl;
import domain.pm.Pm3Guide;
import domain.pm.Pm3GuideExample;
import domain.pm.Pm3GuideExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.ContentTplConstants;
import sys.constants.LogConstants;
import sys.constants.PmConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/pm")
public class Pm3GuideController extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pm3Guide:list")
    @RequestMapping("/pm3Guide")
    public String pm3Guide() {

        return "pm/pm3Guide/pm3Guide_page";
    }

    @RequiresPermissions("pm3Guide:list")
    @RequestMapping("/pm3Guide_data")
    @ResponseBody
    public void pm3Guide_data(HttpServletResponse response,
                              String _meetingMonth,
                              Integer pageSize,
                              Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        Pm3GuideExample example = new Pm3GuideExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(_meetingMonth)){
            criteria.andMeetingMonthEqualTo(DateUtils.parseStringToDate(_meetingMonth));
        }

        long count = pm3GuideMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Pm3Guide> records= pm3GuideMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pm3Guide.class, pm3GuideMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pm3Guide:edit")
    @RequestMapping(value = "/pm3Guide_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pm3Guide_au(Pm3Guide record,
                              String _meetingMonth,
                              String _reportTime,
                              MultipartFile[] _guideFile,
                              HttpServletRequest request) {

        Integer id = record.getId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL))
            throw new UnauthorizedException();
        if (StringUtils.isNotBlank(_meetingMonth)){
            pm3GuideService.isDuplicate(_meetingMonth);
            record.setMeetingMonth(DateUtils.parseDate(_meetingMonth, DateUtils.YYYY_MM));
        }
        if (StringUtils.isNotBlank(_reportTime)){
            record.setReportTime(DateUtils.parseDate(_reportTime, DateUtils.YYYY_MM_DD_HH_MM));
        }
        if (_guideFile != null){
            List<String> nameList = new ArrayList<>();
            List<String> pathList = new ArrayList<>();
            for (MultipartFile file : _guideFile) {
                String originaFileName = file.getOriginalFilename();
                String path = upload(file, "pm3MeetingFile");
                nameList.add(originaFileName);
                pathList.add(path);
            }
            String fileName = StringUtils.trimToNull(record.getGuideFilenames());
            String filePath = StringUtils.trimToNull(record.getGuideFiles());
            if (StringUtils.isBlank(fileName)){
                record.setGuideFilenames(StringUtils.join(nameList, ";"));
                record.setGuideFiles(StringUtils.join(pathList, ";"));
            }else{
                record.setGuideFilenames(fileName + ";" + StringUtils.join(nameList, ";"));
                record.setGuideFiles(filePath + ";" + StringUtils.join(pathList, ";"));
            }
        }
        if (id == null) {
            
            pm3GuideService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PM, "上传组织生活指南：{0}", record.getId()));
        } else {

            pm3GuideService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PM, "更新组织生活指南：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pm3Guide:edit")
    @RequestMapping("/pm3Guide_au")
    public String pm3Guide_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Pm3Guide pm3Guide = pm3GuideMapper.selectByPrimaryKey(id);
            modelMap.put("pm3Guide", pm3Guide);
        }
        return "pm/pm3Guide/pm3Guide_au";
    }

    @RequiresPermissions("pm3Guide:edit")
    @RequestMapping(value = "/pm3Guide_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pm3Guide_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL))
                throw new UnauthorizedException();

            pm3GuideService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PM, "批量删除组织生活指南：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pm3Guide:list")
    @RequestMapping("/pm3Guide_files")
    public String pm3Guide_files(int id, ModelMap modelMap) {

        Pm3Guide pm3Guide = pm3GuideMapper.selectByPrimaryKey(id);

        String fileName = StringUtils.trimToNull(pm3Guide.getGuideFilenames());
        if (StringUtils.isNotBlank(fileName)){
            String[] fileNames = pm3Guide.getGuideFilenames().split(";");
            String[] filePaths = pm3Guide.getGuideFiles().split(";");
            modelMap.put("fileNames", Arrays.asList(fileNames));
            modelMap.put("filePaths", Arrays.asList(filePaths));
        }
        modelMap.put("pm3Guide", pm3Guide);

        return "pm/pm3Guide/pm3Guide_files";
    }

    @RequiresPermissions("pm3Guide:edit")
    @RequestMapping(value = "/pm3Guide_delFile", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pm3Guide_delFile(HttpServletRequest request, Integer id, String filePath, String fileName, ModelMap modelMap) {

        if (null != id){
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL))
                throw new UnauthorizedException();

            pm3GuideService.delFile(id, filePath);
            logger.info(log(LogConstants.LOG_PM, "删除组织生活指南：{0},{1},{2}", id,fileName,filePath));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pm3Guide:list")
    @RequestMapping("/pm3Guide_notice")
    public String pm3Guide_notice(int id, boolean isOdAdmin, ModelMap modelMap) {

        Pm3Guide pm3Guide = pm3GuideMapper.selectByPrimaryKey(id);

        int year = DateUtils.getYear(pm3Guide.getMeetingMonth());
        int month = DateUtils.getMonth(pm3Guide.getMeetingMonth());
        modelMap.put("partyList", iPmMapper.selectPartyList(year, month, PmConstants.PM_3_STATUS_OW, new RowBounds()));
        modelMap.put("branchList", iPmMapper.selectBranchList(year, month, loginUserService.adminPartyIdList(), PmConstants.PM_3_STATUS_SAVE, new RowBounds()));

        ContentTpl tpl = null;
        if (isOdAdmin){
            tpl = CmTag.getContentTpl(ContentTplConstants.PM_3_NOTICE_PARTY);
        }else {
            tpl = CmTag.getContentTpl(ContentTplConstants.PM_3_NOTICE_BRANCH);
        }
        tpl.setContent(String.format(tpl.getContent(), DateUtils.formatDate(pm3Guide.getMeetingMonth(), "yyyy年MM月")));

        modelMap.put("tpl", tpl);

        return "pm/pm3Guide/pm3Guide_notice";
    }

    @RequiresPermissions("pm3Guide:list")
    @RequestMapping(value = "/pm3Guide_notice", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pm3Guide_notice(HttpServletRequest request, Integer[] ids,
                                  boolean isOdAdmin,//是否是组织部管理员
                                  String notice) {

        if (ids != null && ids.length > 0){

            pm3GuideService.notice(Arrays.asList(ids), isOdAdmin, notice);
            logger.info(log(LogConstants.LOG_PM, "给{0}发送组织生活提醒：{1}", (isOdAdmin?"分党委管理员":"党支部管理员"), StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
