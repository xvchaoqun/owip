package controller.member;

import bean.UserBean;
import domain.cadre.CadreView;
import domain.member.MemberApply;
import domain.member.MemberApplyExample;
import domain.member.MemberApplyExample.Criteria;
import domain.member.MemberStudent;
import domain.member.MemberTeacher;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.StudentInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.*;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberApplyExportController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApplyExport")
    public String memberApplyExport(HttpServletResponse response,
                                    @RequestParam(defaultValue = "1")int cls,
                                   Integer userId,
                                   Integer partyId,
                                   Integer branchId,
                                   @RequestParam(defaultValue = OwConstants.OW_APPLY_TYPE_STU+"")Byte type,
                                    //导出类型：1：申请入党人员信息（包含申请至领取志愿书 5个阶段）
                                    // 2：发展党员人员信息（即预备党员阶段） 3: 领取志愿书人员信息
                                    @RequestParam(defaultValue = "0")Byte exportType,
                                    @RequestDateRange DateRange _applyTime,
                                    @RequestDateRange DateRange _growTime,
                                    @RequestDateRange DateRange _drawTime,
                                    ModelMap modelMap
                    ) throws IOException {

        if(exportType==0){

            modelMap.put("cls", cls);
            return "member/memberApply/memberApplyExport_page";
        }

        MemberApplyExample example = new MemberApplyExample();
        Criteria criteria = example.createCriteria();
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        example.setOrderByClause("create_time desc");

        if(type !=null) {
            criteria.andTypeEqualTo(type);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if(exportType==1){

            List<Byte> stageList = new ArrayList<>();
            stageList.add(OwConstants.OW_APPLY_STAGE_PASS);
            stageList.add(OwConstants.OW_APPLY_STAGE_ACTIVE);
            stageList.add(OwConstants.OW_APPLY_STAGE_CANDIDATE);
            stageList.add(OwConstants.OW_APPLY_STAGE_PLAN);
            stageList.add(OwConstants.OW_APPLY_STAGE_DRAW);
            criteria.andStageIn(stageList);

            if (_applyTime.getStart()!=null) {
                criteria.andApplyTimeGreaterThanOrEqualTo(_applyTime.getStart());
            }
            if (_applyTime.getEnd()!=null) {
                criteria.andApplyTimeLessThanOrEqualTo(_applyTime.getEnd());
            }


            if(type==OwConstants.OW_APPLY_TYPE_STU) {
                memberStudent_apply_export(example, response);
                logger.info(addLog(LogConstants.LOG_MEMBER, "导出学生申请入党人员信息"));
            }else {
                memberTeacher_apply_export(example, response);
                logger.info(addLog(LogConstants.LOG_MEMBER, "导出教职工申请入党人员信息"));
            }

        } else if(exportType==2){

            criteria.andStageEqualTo(OwConstants.OW_APPLY_STAGE_GROW);

            if (_growTime.getStart()!=null) {
                criteria.andGrowTimeGreaterThanOrEqualTo(_growTime.getStart());
            }
            if (_growTime.getEnd()!=null) {
                criteria.andGrowTimeLessThanOrEqualTo(_growTime.getEnd());
            }
            if(type==OwConstants.OW_APPLY_TYPE_STU) {
                memberStudent_export(example, exportType, response);
                logger.info(addLog(LogConstants.LOG_MEMBER, "导出学生预备党员信息"));
            }else {
                memberTeacher_export(example, exportType, response);
                logger.info(addLog(LogConstants.LOG_MEMBER, "导出教职工预备党员信息"));
            }
        }else if(exportType==3){

            List<Byte> stageList = new ArrayList<>();
            stageList.add(OwConstants.OW_APPLY_STAGE_DRAW);
            stageList.add(OwConstants.OW_APPLY_STAGE_GROW);
            stageList.add(OwConstants.OW_APPLY_STAGE_POSITIVE);
            criteria.andStageIn(stageList);

            if (_drawTime.getStart()!=null) {
                criteria.andDrawTimeGreaterThanOrEqualTo(_drawTime.getStart());
            }
            if (_drawTime.getEnd()!=null) {
                criteria.andDrawTimeLessThanOrEqualTo(_drawTime.getEnd());
            }
            if(type==OwConstants.OW_APPLY_TYPE_STU) {
                memberStudent_export(example, exportType, response);
                logger.info(addLog(LogConstants.LOG_MEMBER, "导出学生领取志愿书信息"));
            }else {
                memberTeacher_export(example, exportType, response);
                logger.info(addLog(LogConstants.LOG_MEMBER, "导出教职工领取志愿书信息"));
            }
        }

        return null;
    }

    // 学生申请入党人员信息导出
    public void memberStudent_apply_export(MemberApplyExample example, HttpServletResponse response) {

        List<MemberApply> records = memberApplyMapper.selectByExample(example);
        String[] titles = {"学号|100","学生类别|150","姓名|100","性别|50", "出生日期|100", "身份证号|180",
                "民族|100", "年级|100", "所在分党委、党总支、直属党支部|300|left", "所属党支部|300|left", "发展程度|100",
                "提交书面申请书时间|120" , "确定为入党积极分子时间|120", "确定为发展对象时间|120",
                "培养层次（研究生填写）|150","培养类型（研究生填写）|150", "教育类别（研究生填写）|150",
                "培养方式（研究生填写）|150","预计毕业年月|120", "学籍状态|100","籍贯|180"};
        List<String[]> valuesList = new ArrayList<>();
        for (MemberApply memberApply:records) {

            String stage = "";
            if(memberApply.getStage()==OwConstants.OW_APPLY_STAGE_PASS){
                stage="申请入党人员";
            }else if(memberApply.getStage()==OwConstants.OW_APPLY_STAGE_ACTIVE){
                stage="入党积极分子";
            }else if(memberApply.getStage()==OwConstants.OW_APPLY_STAGE_CANDIDATE
                    ||memberApply.getStage()==OwConstants.OW_APPLY_STAGE_PLAN
                    || memberApply.getStage()==OwConstants.OW_APPLY_STAGE_DRAW){
                stage="发展对象";
            }
            SysUserView uv = sysUserService.findById(memberApply.getUserId());
            Byte gender = uv.getGender();
            StudentInfo record = studentInfoService.get(memberApply.getUserId());
            Integer partyId = memberApply.getPartyId();
            Integer branchId = memberApply.getBranchId();

            String[] values = {
                    uv.getCode(),
                    record==null?"":record.getType(),
                    uv.getRealname(),
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(uv.getBirth(), DateUtils.YYYY_MM_DD),
                    uv.getIdcard(),
                    uv.getNation(),
                    record==null?"":record.getGrade(), // 年级
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    stage,
                    DateUtils.formatDate(memberApply.getApplyTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(memberApply.getActiveTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(memberApply.getCandidateTime(), DateUtils.YYYY_MM_DD),
                    record==null?"":record.getEduLevel(),
                    record==null?"":record.getEduType(),
                    record==null?"":record.getEduCategory(),
                    record==null?"":record.getEduWay(),
                    record==null?"":DateUtils.formatDate(record.getExpectGraduateTime(), DateUtils.YYYY_MM_DD),
                    record==null?"":record.getXjStatus(),
                    uv.getNativePlace()
            };
            valuesList.add(values);
        }
        String fileName = "学生申请入党人员导出信息";
        ExportHelper.export(titles, valuesList, fileName, response);
    }


    // 学生发展党员信息导出
    public void memberStudent_export(MemberApplyExample example, Byte exportType, HttpServletResponse response) {

        List<MemberApply> records = memberApplyMapper.selectByExample(example);
        List<String> titles = new ArrayList<>(Arrays.asList(new String[]{"学号|100","学生类别|150","姓名|50","性别|50", "出生日期|100", "身份证号|180",
                "民族|100", "年级|100", "所在分党委、党总支、直属党支部|300|left", "所属党支部|300|left", "政治面貌|100", "发展时间|100",
                "培养层次（研究生填写）|180","培养类型（研究生填写）|180", "教育类别（研究生填写）|180",
                "培养方式（研究生填写）|180","预计毕业年月|120", "学籍状态|100","籍贯|180","转正时间|100"}));
        if(exportType==3){
            titles.set(11, "领取志愿书时间|130");
            titles.add(12, "发展程度|100");
            titles.add(13, "审批状态|250");
        }

        List<List<String>> valuesList = new ArrayList<>();
        for (MemberApply memberApply:records) {
            UserBean record = userBeanService.get(memberApply.getUserId());
            StudentInfo studentInfo = studentInfoService.get(memberApply.getUserId());
            MemberStudent memberStudent = memberStudentService.get(memberApply.getUserId());
            Byte gender = record==null?null:record.getGender();
            Integer partyId = memberApply.getPartyId();
            Integer branchId = memberApply.getBranchId();

            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    record==null?"":record.getCode(),
                    studentInfo==null?"":studentInfo.getType(),
                    record==null?"":record.getRealname(),
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    record==null?"":DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),
                    record==null?"":record.getIdcard(),
                    record==null?"":record.getNation(),
                    studentInfo==null?"":studentInfo.getGrade(), // 年级
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    record==null?"":MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()), // 政治面貌
                    memberStudent==null?"":DateUtils.formatDate(memberStudent.getGrowTime(), DateUtils.YYYY_MM_DD),
                    studentInfo==null?"":studentInfo.getEduLevel(),
                    studentInfo==null?"":studentInfo.getEduType(),
                    studentInfo==null?"":studentInfo.getEduCategory(),
                    studentInfo==null?"":studentInfo.getEduWay(),
                    studentInfo==null?"":DateUtils.formatDate(studentInfo.getExpectGraduateTime(), DateUtils.YYYY_MM_DD),
                    studentInfo==null?"":studentInfo.getXjStatus(),
                    record==null?"":record.getNativePlace(),
                    memberStudent==null?"":DateUtils.formatDate(memberStudent.getPositiveTime(), DateUtils.YYYY_MM_DD)
            }));

            if(exportType==3){
                values.set(11, DateUtils.formatDate(memberApply.getDrawTime(), DateUtils.YYYY_MM_DD));
                values.add(12, OwConstants.OW_APPLY_STAGE_MAP.get(memberApply.getStage()));
                values.add(13, memberApply.getApplyStatus());
            }

            valuesList.add(values);
        }
        String fileName = "学生发展党员导出信息";
        if(exportType==3)
            fileName = "学生领取志愿书导出信息";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 教职工申请入党人员导出信息
    public void memberTeacher_apply_export( MemberApplyExample example, HttpServletResponse response) {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<MemberApply> records = memberApplyMapper.selectByExample(example);
        String[] titles = {"工作证号|100","姓名|100","编制类别|100","人员类别|100",
                "人员状态|100","在岗情况|100","岗位类别|100", "主岗等级|100",
                "性别|100","出生日期|100", "年龄|100","年龄范围|100","民族|100", "国家/地区|100", "证件号码|180",
                "发展程度|100","所在分党委、党总支、直属党支部|300|left","所在党支部|300|left","所在单位|200",
                "提交书面申请书时间|150" , "确定为入党积极分子时间|150", "确定为发展对象时间|150","到校日期|100",
                "专业技术职务|120","专技岗位等级|120","管理岗位等级|120","任职级别|120","行政职务|180","单位全称|200",
                "学历|120","学历毕业学校|200","学位授予学校|200",
                "学位|120","学员结构|100", "人才类型|100", "人才称号|200", "籍贯|100","手机号码|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (MemberApply memberApply:records) {

            String stage = "";
            if(memberApply.getStage()==OwConstants.OW_APPLY_STAGE_PASS){
                stage="申请入党人员";
            }else if(memberApply.getStage()==OwConstants.OW_APPLY_STAGE_ACTIVE){
                stage="入党积极分子";
            }else if(memberApply.getStage()==OwConstants.OW_APPLY_STAGE_CANDIDATE
                    ||memberApply.getStage()==OwConstants.OW_APPLY_STAGE_PLAN
                    || memberApply.getStage()==OwConstants.OW_APPLY_STAGE_DRAW){
                stage="发展对象";
            }

            SysUserView uv = sysUserService.findById(memberApply.getUserId());
            Byte gender = uv.getGender();
            TeacherInfo record = teacherInfoService.get(memberApply.getUserId());
            Integer partyId = memberApply.getPartyId();
            Integer branchId = memberApply.getBranchId();
            //ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
            Date birth = uv.getBirth();
            String ageRange = "";
            if(birth!=null){
                byte memberAgeRange = MemberConstants.getMemberAgeRange(birth);
                if(memberAgeRange>0)
                    ageRange = MemberConstants.MEMBER_AGE_MAP.get(memberAgeRange);
            }

            CadreView cadre = CmTag.getCadreByUserId(memberApply.getUserId());
            String post = record==null?"":record.getPost();  // 行政职务 -- 所在单位及职务
            String adminLevel = record==null?"":record.getPostLevel(); // 任职级别 -- 行政级别
            if(cadre!=null && CadreConstants.CADRE_STATUS_NOW_SET.contains(cadre.getStatus())){
                post = cadre.getTitle();
                adminLevel = CmTag.getMetaType(cadre.getAdminLevel()).getName();
            }

            String[] values = {
                    uv.getCode(),
                    uv.getRealname(),
                    record==null?"":record.getAuthorizedType(),
                    record==null?"":record.getStaffType(),
                    record==null?"":record.getStaffStatus(), // 人员状态
                    record==null?"":record.getOnJob(), // 在岗情况
                    record==null?"":record.getPostClass(), // 岗位类别
                    record==null?"":record.getMainPostLevel(), // 主岗等级
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth!=null?DateUtils.intervalYearsUntilNow(birth) + "":"",
                    ageRange, // 年龄范围
                    uv.getNation(),
                    record==null?"":uv.getCountry(), // 国家/地区
                    uv.getIdcard(), // 证件号码
                    stage, // 发展程度
                    partyId==null?"":partyMap.get(partyId).getName(),
                    branchId==null?"":branchMap.get(branchId).getName(),
                    record==null?"":uv.getUnit(), // 所在单位
                    DateUtils.formatDate(memberApply.getApplyTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(memberApply.getActiveTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(memberApply.getCandidateTime(), DateUtils.YYYY_MM_DD),
                    record==null?"":DateUtils.formatDate(record.getArriveTime(),DateUtils.YYYY_MM_DD), // 到校日期
                    record==null?"":record.getProPost(),
                    record==null?"":record.getProPostLevel(), //专技岗位等级
                    record==null?"":record.getManageLevel(), // 管理岗位等级
                    adminLevel, // 任职级别 -- 行政级别
                    post, // 行政职务 -- 职务
                    record==null?"":uv.getUnit(),
                    record==null?"":record.getEducation(), // 学历
                    record==null?"":record.getSchool(), // 学历毕业学校
                    record==null?"":record.getDegreeSchool(), // 学位授予学校
                    record==null?"":record.getDegree(), // 学位
                    record==null?"":record.getFromType(), // 学员结构 (学位授予国家)
                    record==null?"":record.getTalentType(),
                    record==null?"":record.getTalentTitle(),
                    uv.getNativePlace(),
                    uv.getMobile()
            };
            valuesList.add(values);
        }

        String fileName = "教职工申请入党人员导出信息";

        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 教职工发展党员信息导出
    public void memberTeacher_export( MemberApplyExample example, Byte exportType, HttpServletResponse response) {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<MemberApply> records = memberApplyMapper.selectByExample(example);
        List<String> titles = new ArrayList<>(Arrays.asList(new String[]{"工作证号|100","姓名|100","编制类别|100","人员类别|100",
                "人员状态|80","在岗情况|80","岗位类别|80", "主岗等级|120",
                "性别|100","出生日期|80", "年龄|100","年龄范围|100","民族|100", "国家/地区|100", "证件号码|180",
                "政治面貌|100","所在分党委、党总支、直属党支部|300|left","所在党支部|300|left", "所在单位|200", "入党时间|80","到校日期|80",
                "专业技术职务|120","专技岗位等级|120","管理岗位等级|120","任职级别|100","行政职务|180",
                "学历|100","学历毕业学校|200","学位授予学校|200",
                "学位|100","学员结构|100", "人才类型|100", "人才称号|200", "籍贯|100","转正时间|80","手机号码|100"}));
        if(exportType==3){
            titles.set(19, "领取志愿书时间|130");
            titles.add(20, "发展程度|100");
            titles.add(21, "审批状态|250");
        }

        List<List<String>> valuesList = new ArrayList<>();
        for (MemberApply memberApply:records) {

            UserBean userBean = userBeanService.get(memberApply.getUserId());
            TeacherInfo record = teacherInfoService.get(memberApply.getUserId());
            MemberTeacher memberTeacher = memberTeacherService.get(memberApply.getUserId());
            Byte gender = userBean==null?null:userBean.getGender();
            Integer partyId = memberApply.getPartyId();
            Integer branchId = memberApply.getBranchId();
            //ExtJzg extJzg = extJzgService.getByCode(record.getCode());
            Date birth = userBean==null?null:userBean.getBirth();
            String ageRange = "";
            if(birth!=null){
                byte memberAgeRange = MemberConstants.getMemberAgeRange(birth);
                if(memberAgeRange>0)
                    ageRange = MemberConstants.MEMBER_AGE_MAP.get(memberAgeRange);
            }

            CadreView cadre = CmTag.getCadreByUserId(memberApply.getUserId());
            String post = record==null?"":record.getPost();  // 行政职务 -- 所在单位及职务
            String adminLevel = record==null?"":record.getPostLevel(); // 任职级别 -- 行政级别
            if(cadre!=null && CadreConstants.CADRE_STATUS_NOW_SET.contains(cadre.getStatus())){
                post = cadre.getTitle();
                adminLevel = CmTag.getMetaType(cadre.getAdminLevel()).getName();
            }

            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    userBean==null?"":userBean.getCode(),
                    userBean==null?"":userBean.getRealname(),
                    record==null?"":record.getAuthorizedType(),
                    record==null?"":record.getStaffType(),
                    record==null?"":record.getStaffStatus(), // 人员状态
                    record==null?"":record.getOnJob(), // 在岗情况
                    record==null?"":record.getPostClass(), // 岗位类别
                    record==null?"":record.getMainPostLevel(), // 主岗等级
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth!=null?DateUtils.intervalYearsUntilNow(birth) + "":"",
                    ageRange, // 年龄范围
                    userBean==null?"":userBean.getNation(),
                    record==null?"":userBean.getCountry(), // 国家/地区
                    userBean==null?"":userBean.getIdcard(), // 证件号码
                    memberTeacher==null?"":MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(memberTeacher.getPoliticalStatus()),
                    partyId==null?"":partyMap.get(partyId).getName(),
                    branchId==null?"":branchMap.get(branchId).getName(),
                    record==null?"":userBean.getUnit(), // 所在单位
                    memberTeacher==null?"":DateUtils.formatDate(memberTeacher.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record==null?"":DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYY_MM_DD), // 到校日期
                    record==null?"":record.getProPost(),
                    record==null?"":record.getProPostLevel(), //专技岗位等级
                    record==null?"":record.getManageLevel(), // 管理岗位等级
                    adminLevel, // 任职级别 -- 行政级别
                    post, // 行政职务 -- 职务
                    record==null?"":record.getEducation(), // 学历
                    record==null?"":record.getSchool(), // 学历毕业学校
                    record==null?"":record.getDegreeSchool(), // 学位授予学校
                    record==null?"":record.getDegree(), // 学位
                    record==null?"":record.getFromType(), // 学员结构
                    record==null?"":record.getTalentType(), // 人才类型
                    record==null?"":record.getTalentTitle(),
                    userBean==null?"":userBean.getNativePlace(),
                    memberTeacher==null?"":DateUtils.formatDate(memberTeacher.getPositiveTime(), DateUtils.YYYY_MM_DD),
                    userBean==null?"":userBean.getMobile()
            }));
            if(exportType==3){
                values.set(19, DateUtils.formatDate(memberApply.getDrawTime(), DateUtils.YYYY_MM_DD));
                values.add(20, OwConstants.OW_APPLY_STAGE_MAP.get(memberApply.getStage()));
                values.add(21, memberApply.getApplyStatus());
            }
            valuesList.add(values);
        }

        String fileName = "教职工发展党员导出信息";
        if(exportType==3){
            fileName = "教职工领取志愿书导出信息";
        }

        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
