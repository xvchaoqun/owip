package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.CadreEdu;
import domain.cadre.CadreEduExample;
import domain.cadre.CadreEduExample.Criteria;
import domain.cadre.CadreInfo;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreEduController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreEdu:list")
    @RequestMapping("/cadreEdu_page")
    public String cadreEdu_page(@RequestParam(defaultValue = "1") Byte type, // 1 列表 2 预览
                                Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 2) {

            modelMap.put("cadreEdus", cadreEduService.list(cadreId, null));

            CadreInfo cadreInfo = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_EDU);
            modelMap.put("cadreInfo", cadreInfo);
        }

        if (cadreId != null) {

            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        List<Integer> needTutorEduTypes = cadreEduService.needTutorEduTypes();
        modelMap.put("needTutorEduTypes", needTutorEduTypes);

        //modelMap.put("cadreTutors", JSONUtils.toString(cadreTutorService.findAll(cadreId).values()));
        return "cadre/cadreEdu/cadreEdu_page";
    }

    @RequiresPermissions("cadreEdu:list")
    @RequestMapping("/cadreEdu_data")
    @ResponseBody
    public void cadreEdu_data(HttpServletResponse response,
                              Integer cadreId, Integer pageSize, Integer pageNo,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录（exportType=0时为现任干部id)
                              @RequestParam(required = false, defaultValue = "0") int exportType,// 0: 现任干部 1：年轻干部
                              Integer reserveType // 年轻干部类别
                              ) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreEduExample example = new CadreEduExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            SecurityUtils.getSubject().checkPermission("cadre:export");
            cadreEduService.cadreEdu_export(ids, exportType, reserveType, response);
            return;
        }

        long count = cadreEduMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreEdu> records = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreEdu:edit")
    @RequestMapping(value = "/cadreEdu_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEdu_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入
            CadreEdu record,
            @RequestParam(value = "_files[]") MultipartFile[] _files,
            HttpServletRequest request) {

        Integer id = record.getId();

        List<String> filePaths = new ArrayList<>();
        for (MultipartFile _file : _files) {
            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "cadre_edu" + FILE_SEPARATOR + record.getCadreId() + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            filePaths.add(savePath);
        }
        if (filePaths.size() > 0) {
            record.setCertificate(StringUtils.join(filePaths, ","));
        }

        record.setIsGraduated(BooleanUtils.isTrue(record.getIsGraduated()));
        record.setHasDegree(BooleanUtils.isTrue(record.getHasDegree()));
        /*if(!record.getHasDegree()){
            record.setDegree(""); // 没有获得学位，清除学位名称
            record.setDegreeCountry("");
            record.setDegreeUnit("");
        }*/
        if (record.getSchoolType()!=null && (record.getSchoolType() == CadreConstants.CADRE_SCHOOL_TYPE_THIS_SCHOOL ||
                record.getSchoolType() == CadreConstants.CADRE_SCHOOL_TYPE_DOMESTIC)) {
            record.setDegreeCountry("中国");
        }

        record.setIsHighEdu(BooleanUtils.isTrue(record.getIsHighEdu()));
        record.setIsHighDegree(BooleanUtils.isTrue(record.getIsHighDegree()));

        if (id == null) {

            if (!toApply) {
                cadreEduService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部学习经历：%s", record.getId()));
            } else {
                cadreEduService.modifyApply(record, null, false);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-干部学习经历：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreEdu _record = cadreEduMapper.selectByPrimaryKey(id);
            if (_record.getCadreId().intValue() != record.getCadreId()) {
                throw new OpException("数据请求错误，没有操作权限");
            }

            if (!toApply) {
                record.setSortOrder(_record.getSortOrder());
                record.setStatus(_record.getStatus());
                if (record.getCertificate() == null) {
                    record.setCertificate(_record.getCertificate());
                }
                cadreEduService.updateByPrimaryKey(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部学习经历：%s", record.getId()));
            } else {
                if (_isUpdate == false) {
                    cadreEduService.modifyApply(record, id, false);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-干部学习经历：%s", record.getId()));
                } else {
                    // 更新修改申请的内容
                    cadreEduService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-干部学习经历：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEdu:edit")
    @RequestMapping("/cadreEdu_au")
    public String cadreEdu_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreEdu cadreEdu = cadreEduMapper.selectByPrimaryKey(id);
            modelMap.put("cadreEdu", cadreEdu);
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreEdu/cadreEdu_au";
    }

    // 认定规则
    @RequiresPermissions("cadreEdu:edit")
    @RequestMapping("/cadreEdu_rule")
    public String cadreEdu_rule() {

        return "cadre/cadreEdu/cadreEdu_rule";
    }

 /*   @RequiresPermissions("cadreEdu:del")
    @RequestMapping(value = "/cadreEdu_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEdu_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreEduService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除干部学习经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreEdu:del")
    @RequestMapping(value = "/cadreEdu_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadreEduService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部学习经历：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
