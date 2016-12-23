package controller.user.modify;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreEdu;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
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
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserModifyCadreEduController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 提交/更新 [添加或修改申请]
    @RequiresRoles(value = {SystemConstants.ROLE_CADRE, SystemConstants.ROLE_CADRERESERVE}, logical = Logical.OR)
    @RequestMapping(value = "/cadreEdu_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEdu_au(@CurrentUser SysUserView loginUser,
                              // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
                              @RequestParam(required = true, defaultValue = "0")boolean _isUpdate,
                              Integer applyId, // _isUpdate=true时，传入
                              CadreEdu record, String _enrolTime,
                              String _finishTime,
                              String _degreeTime,
                              @RequestParam(value = "_files[]") MultipartFile[] _files,
                              HttpServletRequest request) {

        List<String> filePaths = new ArrayList<>();
        for (MultipartFile _file : _files) {
            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "cadre_edu" + FILE_SEPARATOR + record.getCadreId() + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            filePaths.add(savePath);
        }

        if(filePaths.size()>0){
            record.setCertificate(StringUtils.join(filePaths, ","));
        }

        if(StringUtils.isNotBlank(_enrolTime)){
            record.setEnrolTime(DateUtils.parseDate(_enrolTime, "yyyy.MM"));
        }
        if(StringUtils.isNotBlank(_finishTime)){
            record.setFinishTime(DateUtils.parseDate(_finishTime, "yyyy.MM"));
        }
        if(StringUtils.isNotBlank(_degreeTime)){
            record.setDegreeTime(DateUtils.parseDate(_degreeTime, "yyyy.MM"));
        }
        record.setIsGraduated(BooleanUtils.isTrue(record.getIsGraduated()));
        record.setHasDegree(BooleanUtils.isTrue(record.getHasDegree()));
        /*if(!record.getHasDegree()){
            record.setDegree(""); // 没有获得学位，清除学位名称
            record.setDegreeCountry("");
            record.setDegreeUnit("");
        }*/
        if(record.getSchoolType()== SystemConstants.CADRE_SCHOOL_TYPE_THIS_SCHOOL ||
                record.getSchoolType()==SystemConstants.CADRE_SCHOOL_TYPE_DOMESTIC){
            record.setDegreeCountry("中国");
        }

        record.setIsHighEdu(BooleanUtils.isTrue(record.getIsHighEdu()));
        record.setIsHighDegree(BooleanUtils.isTrue(record.getIsHighDegree()));

        Integer id = record.getId();
        if (id == null) {

            cadreEduService.modifyApply(record, null, false);
            logger.info(addLog(SystemConstants.LOG_USER, "添加申请-干部学习经历：%s", record.getId()));
        } else {

            if(_isUpdate==false) {
                cadreEduService.modifyApply(record, id, false);
                logger.info(addLog(SystemConstants.LOG_USER, "修改申请-干部学习经历：%s", record.getId()));
            }else{

                // 更新修改申请的内容
                cadreEduService.updateModify(record, applyId);
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {SystemConstants.ROLE_CADRE, SystemConstants.ROLE_CADRERESERVE}, logical = Logical.OR)
    @RequestMapping("/cadreEdu_au")
    public String cadreEdu_au(@CurrentUser SysUserView loginUser, Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreEdu cadreEdu = cadreEduMapper.selectByPrimaryKey(id);
            modelMap.put("cadreEdu", cadreEdu);
        }

        Cadre cadre = cadreService.findByUserId(loginUser.getId());
        modelMap.put("cadre", cadre);
        modelMap.put("sysUser", loginUser);

        return "user/modify/cadreEdu/cadreEdu_au";
    }

    // 删除申请
    @RequiresRoles(value = {SystemConstants.ROLE_CADRE, SystemConstants.ROLE_CADRERESERVE}, logical = Logical.OR)
    @RequestMapping(value = "/cadreEdu_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEdu_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreEduService.modifyApply(null, id, true);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除申请-干部学习经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }
}
