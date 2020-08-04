package controller.cet;

import controller.global.OpException;
import domain.base.ContentTpl;
import domain.base.MetaType;
import domain.cet.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.constants.ContentTplConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.utils.ExcelUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetProjectDetailController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail")
    public String cetProject_detail(Integer projectId, ModelMap modelMap) {

        if (projectId != null) {
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            modelMap.put("cetProject", cetProject);
        }
        return "cet/cetProject/cetProject_detail/cetProject_detail";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail_obj")
    public String cetProject_detail_obj(int projectId,
                                        /** cls=1 培训对象
                                         *  cls=2 培训班选择学员
                                         *  cls=3 培训方案选择学员
                                         *  cls=4 撰写心得体会
                                         *  cls=5 设置小组成员（分组讨论）
                                         *  cls=6 自主学习
                                         */
                            @RequestParam(defaultValue = "1") Integer cls, // 同 /cetProjectObj 的cls
                            Integer trainCourseId, // 培训班选课
                            Integer planCourseId,  // 培训方案选课
                            Integer discussGroupId,  // 讨论小组
                            ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        if(trainCourseId!=null){
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
            modelMap.put("cetTrainCourse", cetTrainCourse);
            Integer trainId = cetTrainCourse.getTrainId();
            if(trainId!=null) {
                CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
                modelMap.put("cetTrain", cetTrain);
                Integer planId = cetTrain.getPlanId();
                if (planId != null) {
                    CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
                    modelMap.put("cetProjectPlan", cetProjectPlan);
                }
            }

        }else if(planCourseId!=null){

            CetPlanCourse cetPlanCourse = cetPlanCourseMapper.selectByPrimaryKey(planCourseId);
            modelMap.put("cetPlanCourse", cetPlanCourse);
            Integer planId = cetPlanCourse.getPlanId();
            if(planId!=null){
                CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
                modelMap.put("cetProjectPlan", cetProjectPlan);
            }
        }else if(discussGroupId!=null){

            CetDiscussGroup cetDiscussGroup = cetDiscussGroupMapper.selectByPrimaryKey(discussGroupId);
            modelMap.put("cetDiscussGroup", cetDiscussGroup);

            Integer discussId = cetDiscussGroup.getDiscussId();
            CetDiscuss cetDiscuss = cetDiscussMapper.selectByPrimaryKey(discussId);
            modelMap.put("cetDiscuss", cetDiscuss);

            /*CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(cetDiscuss.getPlanId());
            modelMap.put("cetProjectPlan", cetProjectPlan);*/
        }

        modelMap.put("cls", cls);

        return "cet/cetProject/cetProject_detail/cetProject_detail_obj";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail_begin")
    public String cetProject_detail_begin(int projectId, ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);
        
        List<ContentTpl> tplList = new ArrayList<>();
        tplList.add(CmTag.getContentTpl(ContentTplConstants.CONTENT_TPL_CET_MSG_1));
        modelMap.put("tplList", tplList);

        return "cet/cetProject/cetProject_detail/cetProject_detail_begin";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_detail_begin", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_detail_begin(int projectId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date openTime,
                                          String openAddress) {

        CetProject record = new CetProject();
        record.setId(projectId);
        record.setOpenTime(openTime);
        record.setOpenAddress(openAddress);

        cetProjectMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(LogConstants.LOG_CET, "更新开班仪式设置：%s~%s",
                openTime, openAddress));

        return success(FormUtils.SUCCESS);
    }

    // 批量导入培训对象
    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProjectObj_import")
    public String cetProjectObj_import(Integer projectId, ModelMap modelMap){

        modelMap.put("projectId", projectId);

        List<CetTraineeType> cetTraineeTypes = cetProjectService.getCetTraineeTypes(projectId);

        modelMap.put("cetTraineeTypes", cetTraineeTypes);

        return "cet/cetProject/cetProject_detail/cetProjectObj_import";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProjectObj_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_import(int projectId, int traineeTypeId, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CetProjectObj> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            CetProjectObj record = new CetProjectObj();
            row++;
            record.setProjectId(projectId);
            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)){
                throw new OpException("Excel中第{0}行学工号不能为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            record.setUserId(uv.getUserId());
            record.setTraineeTypeId(traineeTypeId);

            record.setTitle(StringUtils.trimToNull(xlsRow.get(2)));

            MetaType metaType = metaTypeService.findByName("mc_post", xlsRow.get(3));
            if (metaType != null){
                record.setPostType(metaType.getId());
            }
            String _identity = StringUtils.trim(xlsRow.get(4));
            if (StringUtils.isNotBlank(_identity)) {
                String[] identities = _identity.split(",|，|、");
                List<Integer> identityList = new ArrayList<>();
                for (String s : identities) {
                    MetaType metaType1 = metaTypeService.findByName("mc_cet_identity", s);
                    if (metaType1 != null) {
                        identityList.add(metaType1.getId());
                    }
                }
                if(identityList.size()>0) {
                    record.setIdentity("," + StringUtils.join(identityList, ",") + ",");
                }
            }else {
                record.setIdentity(""); // 为了更新时覆盖
            }

            records.add(record);
        }
        Collections.reverse(records);
        int successCount = cetProjectObjService.importCetProjectObj(projectId, records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_CET,
                "导入培训对象及学习情况成功，总共{0}条记录，其中成功导入{1}条记录",
                records.size(), successCount));

        return resultMap;
    }
}
