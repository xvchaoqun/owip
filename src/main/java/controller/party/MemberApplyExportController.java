package controller.party;

import controller.BaseController;
import domain.ext.ExtBks;
import domain.ext.ExtJzg;
import domain.ext.ExtYjs;
import domain.member.*;
import domain.member.MemberApplyExample.Criteria;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.StudentInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.helper.ExportHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MemberApplyExportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApplyExport_page")
    public String memberApplyExport_page(@RequestParam(defaultValue = "1")int cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "party/memberApply/memberApplyExport_page";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApplyExport")
    public void memberApplyExport(HttpServletResponse response,
                                   Integer userId,
                                   Integer partyId,
                                   Integer branchId,
                                   @RequestParam(defaultValue = SystemConstants.APPLY_TYPE_STU+"")Byte type,
                                   @RequestParam(defaultValue = "1")Byte exportType //导出类型：1：申请入党人员信息（包含申请至领取志愿书 5个阶段） 2：发展党员人员信息（即预备党员阶段）
                    ) throws IOException {

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
            stageList.add(SystemConstants.APPLY_STAGE_PASS);
            stageList.add(SystemConstants.APPLY_STAGE_ACTIVE);
            stageList.add(SystemConstants.APPLY_STAGE_CANDIDATE);
            stageList.add(SystemConstants.APPLY_STAGE_PLAN);
            stageList.add(SystemConstants.APPLY_STAGE_DRAW);
            criteria.andStageIn(stageList);

            if(type==SystemConstants.APPLY_TYPE_STU) {
                memberStudent_apply_export(example, response);
                logger.info(addLog(SystemConstants.LOG_OW, "导出学生申请入党人员信息"));
            }else {
                memberTeacher_apply_export(example, response);
                logger.info(addLog(SystemConstants.LOG_OW, "导出教职工申请入党人员信息"));
            }

        } else if(exportType==2){

            criteria.andStageEqualTo(SystemConstants.APPLY_STAGE_GROW);

            if(type==SystemConstants.APPLY_TYPE_STU) {
                memberStudent_export(example, response);
                logger.info(addLog(SystemConstants.LOG_OW, "导出学生预备党员信息"));
            }else {
                memberTeacher_export(example, response);
                logger.info(addLog(SystemConstants.LOG_OW, "导出教职工预备党员信息"));
            }
        }
    }

    // 学生申请入党人员信息导出
    public void memberStudent_apply_export(MemberApplyExample example, HttpServletResponse response) {

        List<MemberApply> records = memberApplyMapper.selectByExample(example);
        String[] titles = {"学号","学生类别","姓名","性别", "出生日期", "身份证号",
                "民族", "年级", "所在分党委、党总支、直属党支部", "所属党支部", "发展程度",
                "提交书面申请书时间" , "确定为入党积极分子时间", "确定为发展对象时间",
                "培养层次（研究生填写）","培养类型（研究生填写）", "教育类别（研究生填写）",
                "培养方式（研究生填写）","预计毕业年月", "籍贯"};
        List<String[]> valuesList = new ArrayList<>();
        for (MemberApply memberApply:records) {

            String stage = "";
            if(memberApply.getStage()==SystemConstants.APPLY_STAGE_PASS){
                stage="申请入党人员";
            }else if(memberApply.getStage()==SystemConstants.APPLY_STAGE_ACTIVE){
                stage="入党积极分子";
            }else if(memberApply.getStage()==SystemConstants.APPLY_STAGE_CANDIDATE
                    ||memberApply.getStage()==SystemConstants.APPLY_STAGE_PLAN
                    || memberApply.getStage()==SystemConstants.APPLY_STAGE_DRAW){
                stage="发展对象";
            }
            SysUserView uv = sysUserService.findById(memberApply.getUserId());
            Byte gender = uv.getGender();
            StudentInfo record = studentService.get(memberApply.getUserId());
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
                    uv.getNativePlace()
            };
            valuesList.add(values);
        }
        String fileName = "学生申请入党人员导出信息";
        ExportHelper.export(titles, valuesList, fileName, response);
    }


    // 学生发展党员信息导出
    public void memberStudent_export(MemberApplyExample example, HttpServletResponse response) {

        List<MemberApply> records = memberApplyMapper.selectByExample(example);
        String[] titles = {"学号","学生类别","姓名","性别", "出生日期", "身份证号",
                "民族", "年级", "所在分党委、党总支、直属党支部", "所属党支部", "政治面貌", "发展时间",
                "培养层次（研究生填写）","培养类型（研究生填写）", "教育类别（研究生填写）",
                "培养方式（研究生填写）","预计毕业年月", "籍贯","转正时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (MemberApply memberApply:records) {
            MemberStudent record = memberStudentService.get(memberApply.getUserId());
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();

            String[] values = {
                    record==null?"":record.getCode(),
                    record==null?"":record.getType(),
                    record==null?"":record.getRealname(),
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    record==null?"":DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),
                    record==null?"":record.getIdcard(),
                    record==null?"":record.getNation(),
                    record==null?"":record.getGrade(), // 年级
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    record==null?"":SystemConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()), // 政治面貌
                    record==null?"":DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record==null?"":record.getEduLevel(),
                    record==null?"":record.getEduType(),
                    record==null?"":record.getEduCategory(),
                    record==null?"":record.getEduWay(),
                    record==null?"":DateUtils.formatDate(record.getExpectGraduateTime(), DateUtils.YYYY_MM_DD),
                    record==null?"":record.getNativePlace(),
                    record==null?"":DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "学生发展党员导出信息";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 教职工申请入党人员导出信息
    public void memberTeacher_apply_export( MemberApplyExample example, HttpServletResponse response) {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<MemberApply> records = memberApplyMapper.selectByExample(example);
        String[] titles = {"工作证号","姓名","编制类别","人员类别","人员状态","在岗情况","岗位类别", "主岗等级",
                "性别","出生日期", "年龄","年龄范围","民族", "国家/地区", "证件号码",
                "发展程度","所在分党委、党总支、直属党支部","所在党支部",
                "提交书面申请书时间" , "确定为入党积极分子时间", "确定为发展对象时间","到校日期",
                "专业技术职务","专技岗位等级","管理岗位等级","任职级别","行政职务","单位全称", "学历","学历毕业学校","学位授予学校",
                "学位","学员结构", "人才类型", "人才称号", "籍贯","手机号码"};
        List<String[]> valuesList = new ArrayList<>();
        for (MemberApply memberApply:records) {

            String stage = "";
            if(memberApply.getStage()==SystemConstants.APPLY_STAGE_PASS){
                stage="申请入党人员";
            }else if(memberApply.getStage()==SystemConstants.APPLY_STAGE_ACTIVE){
                stage="入党积极分子";
            }else if(memberApply.getStage()==SystemConstants.APPLY_STAGE_CANDIDATE
                    ||memberApply.getStage()==SystemConstants.APPLY_STAGE_PLAN
                    || memberApply.getStage()==SystemConstants.APPLY_STAGE_DRAW){
                stage="发展对象";
            }

            SysUserView uv = sysUserService.findById(memberApply.getUserId());
            Byte gender = uv.getGender();
            TeacherInfo record = teacherService.get(memberApply.getUserId());
            Integer partyId = memberApply.getPartyId();
            Integer branchId = memberApply.getBranchId();
            ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
            Date birth = uv.getBirth();
            String ageRange = "";
            if(birth!=null){
                byte memberAgeRange = SystemConstants.getMemberAgeRange(DateUtils.getYear(birth));
                if(memberAgeRange>0)
                    ageRange = SystemConstants.MEMBER_AGE_MAP.get(memberAgeRange);
            }
            String[] values = {
                    uv.getCode(),
                    uv.getRealname(),
                    record==null?"":record.getAuthorizedType(),
                    record==null?"":record.getStaffType(),
                    record==null?"":record.getStaffStatus(), // 人员状态
                    record==null?"":record.getOnJob(), // 在岗情况
                    record==null?"":record.getPostClass(), // 岗位类别
                    record==null?"":record.getPostType(), // 主岗等级--岗位级别
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth!=null?DateUtils.intervalYearsUntilNow(birth) + "":"",
                    ageRange, // 年龄范围
                    uv.getNation(),
                    extJzg==null?"":extJzg.getGj(), // 国家/地区
                    extJzg==null?"":extJzg.getSfzh(), // 证件号码
                    stage, // 发展程度
                    partyId==null?"":partyMap.get(partyId).getName(),
                    branchId==null?"":branchMap.get(branchId).getName(),
                    DateUtils.formatDate(memberApply.getApplyTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(memberApply.getActiveTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(memberApply.getCandidateTime(), DateUtils.YYYY_MM_DD),
                    record==null?"":record.getArriveTime(), // 到校日期
                    record==null?"":record.getProPost(),
                    record==null?"":record.getProPostLevel(), //专技岗位等级
                    record==null?"":record.getManageLevel(), // 管理岗位等级
                    record==null?"":record.getPostLevel(), // 任职级别 -- 行政级别
                    record==null?"":record.getPost(), // 行政职务 -- 职务
                    extJzg==null?"":extJzg.getDwmc(),
                    record==null?"":record.getEducation(), // 学历
                    record==null?"":record.getSchool(), // 学历毕业学校
                    extJzg==null?"":extJzg.getXwsyxx(), // 学位授予学校
                    record==null?"":record.getDegree(), // 学位
                    extJzg==null?"":extJzg.getXyjg(), // 学员结构 (学位授予国家)
                    extJzg==null?"":extJzg.getRclx(),
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
    public void memberTeacher_export( MemberApplyExample example, HttpServletResponse response) {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<MemberApply> records = memberApplyMapper.selectByExample(example);
        String[] titles = {"工作证号","姓名","编制类别","人员类别","人员状态","在岗情况","岗位类别", "主岗等级",
                "性别","出生日期", "年龄","年龄范围","民族", "国家/地区", "证件号码",
                "政治面貌","所在分党委、党总支、直属党支部","所在党支部", "所在单位", "入党时间","到校日期",
                "专业技术职务","专技岗位等级","管理岗位等级","任职级别","行政职务","学历","学历毕业学校","学位授予学校",
                "学位","学员结构", "人才类型", "人才称号", "籍贯","转正时间","手机号码"};
        List<String[]> valuesList = new ArrayList<>();
        for (MemberApply memberApply:records) {

            MemberTeacher record = memberTeacherService.get(memberApply.getUserId());
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            ExtJzg extJzg = extJzgService.getByCode(record.getCode());
            Date birth = record.getBirth();
            String ageRange = "";
            if(birth!=null){
                byte memberAgeRange = SystemConstants.getMemberAgeRange(DateUtils.getYear(birth));
                if(memberAgeRange>0)
                    ageRange = SystemConstants.MEMBER_AGE_MAP.get(memberAgeRange);
            }
            String[] values = {
                    record.getCode(),
                    record.getRealname(),
                    record.getAuthorizedType(),
                    record.getStaffType(),
                    record.getStaffStatus(), // 人员状态
                    record.getOnJob(), // 在岗情况
                    record.getPostClass(), // 岗位类别
                    record.getPostType(), // 主岗等级--岗位级别
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth!=null?DateUtils.intervalYearsUntilNow(birth) + "":"",
                    ageRange, // 年龄范围
                    record.getNation(),
                    extJzg==null?"":extJzg.getGj(), // 国家/地区
                    extJzg==null?"":extJzg.getSfzh(), // 证件号码
                    //extJzg.getZzmm(), // 政治面貌
                    SystemConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()),
                    partyId==null?"":partyMap.get(partyId).getName(),
                    branchId==null?"":branchMap.get(branchId).getName(),
                    //unitMap.get(record.getUnitId()).getName(),
                    extJzg==null?"":extJzg.getDwmc(),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record.getArriveTime(), // 到校日期
                    record.getProPost(),
                    record.getProPostLevel(), //专技岗位等级
                    record.getManageLevel(), // 管理岗位等级
                    record.getPostLevel(), // 任职级别 -- 行政级别
                    record.getPost(), // 行政职务 -- 职务
                    record.getEducation(), // 学历
                    record.getSchool(), // 学历毕业学校
                    extJzg==null?"":extJzg.getXwsyxx(), // 学位授予学校
                    record.getDegree(), // 学位
                    extJzg==null?"":extJzg.getXyjg(), // 学员结构 (学位授予国家)
                    extJzg==null?"":extJzg.getRclx(),
                    record.getTalentTitle(),
                    record.getNativePlace(),
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD),
                    record.getMobile()
            };
            valuesList.add(values);
        }

        String fileName = "教职工发展党员导出信息";

        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
