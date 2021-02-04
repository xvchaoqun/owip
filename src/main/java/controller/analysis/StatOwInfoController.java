package controller.analysis;

import bean.StatByteBean;
import controller.BaseController;
import domain.cet.CetTraineeType;
import domain.party.Party;
import domain.party.PartyExample;
import domain.sys.StudentInfoExample;
import domain.sys.SysUser;
import domain.sys.SysUserExample;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.analysis.StatOwInfoMapper;
import service.analysis.StatOwInfoService;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Controller
public class StatOwInfoController extends BaseController {

    @Autowired
    private StatOwInfoService statOwInfoService;
    @Autowired
    private StatOwInfoMapper statOwInfoMapper;

    @RequiresPermissions("statOwInfo:list")
    @RequestMapping("/statOwInfo")
    public String owYjsInfo(@RequestParam(required = false, defaultValue = "0") Byte cls, @RequestParam(required = false, defaultValue = "1") Byte export,ModelMap modelMap, HttpServletResponse response) {
        modelMap.put("cls", cls);
        Date now = new Date();
        int year = DateUtils.getYear(now);
        int month = DateUtils.getMonth(now);
        modelMap.put("year", year);
        modelMap.put("month", month);

        Map<String, String> masters = new HashMap<>();
        Map<String, String> doctors = new HashMap<>();
        String schoolName = CmTag.getSysConfig().getSchoolName();
        modelMap.put("schoolName", schoolName);
        DecimalFormat df = new DecimalFormat("0.00");

        if (cls == 0) {
            // 全校研究生总数
            SysUserExample example = new SysUserExample();
            example.createCriteria().andTypeEqualTo(SystemConstants.SYNC_TYPE_YJS).andLockedEqualTo(false);
            int countYjs = (int) sysUserMapper.countByExample(example);

            // 预备党员
            List<StatByteBean> preparedMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, null, null);
            // 正式党员
            List<StatByteBean> formalMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, null, null);
            //申请入党人员
            List<StatByteBean> applyJoin = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT, null, null, null);
            List<StatByteBean> passJoin = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS, null, null, null);
            // 入党积极分子
            List<StatByteBean> countActivists = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE, null, null, null);
            // 发展对象
            List<StatByteBean> countDevelopment = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE, null, null, null);
            // 封装数据
            List<Map<String, String>> data = statOwInfoService.encapsulationData(preparedMembers, formalMembers, applyJoin, passJoin, countActivists, countDevelopment);
            masters = data.get(0);
            doctors = data.get(1);

            //硕士和博士研究生总数
            StudentInfoExample studentInfoExample = new StudentInfoExample();
            studentInfoExample.createCriteria().andStudentLevelEqualTo(SystemConstants.STUDENT_TYPE_SS);
            int countMasters = (int) studentInfoMapper.countByExample(studentInfoExample);
            StudentInfoExample studentInfoExample2 = new StudentInfoExample();
            studentInfoExample2.createCriteria().andStudentLevelEqualTo(SystemConstants.STUDENT_TYPE_BS);
            int countDoctors = (int) studentInfoMapper.countByExample(studentInfoExample2);

            //硕士研究生总数
            masters.put("total", String.valueOf(countMasters));
            //博士研究生总数
            doctors.put("total", String.valueOf(countDoctors));

            //硕士生党员占比
            BigDecimal num1 = new BigDecimal(masters.get("partyMembersCount"));
            BigDecimal num2 = new BigDecimal(countMasters);
            BigDecimal masterPercent = num1.divide(num2,4,BigDecimal.ROUND_HALF_UP);

            //博士生党员占比
            num1 = new BigDecimal(doctors.get("partyMembersCount"));
            num2 = new BigDecimal(countDoctors);
            BigDecimal doctorPercent = num1.divide(num2,4,BigDecimal.ROUND_HALF_UP);

            modelMap.put("total", countYjs);
            //研究生占比
            Integer mpmc = masters.get("partyMembersCount") == null ? 0 : Integer.valueOf(masters.get("partyMembersCount"));
            Integer dpmc = doctors.get("partyMembersCount") == null ? 0 : Integer.valueOf(doctors.get("partyMembersCount"));
            Integer total = modelMap.get("total") == null ? 0 : (Integer) modelMap.get("total");
            num1 = new BigDecimal(mpmc + dpmc);
            num2 = new BigDecimal(total);
            BigDecimal percent = num1.divide(num2,4,BigDecimal.ROUND_HALF_UP);

            masters.put("masterPercent", df.format(masterPercent.doubleValue() * 100) + "%");
            doctors.put("doctorPercent", df.format(doctorPercent.doubleValue() * 100) + "%");

            modelMap.put("percent", df.format(percent.doubleValue() * 100) + "%");
            modelMap.put("masters", masters);
            modelMap.put("doctors", doctors);

            if (export == 2) {
                XSSFWorkbook wb = statOwInfoService.statOnInfoExport(modelMap, masterPercent, doctorPercent);
                String filename = String.format("%s研究生队伍党员信息分析.xlsx", schoolName);
                ExportHelper.output(wb, filename, response);
                return null;
            }
        } else if (cls == 1) {
            List<String> columns = new ArrayList<>();
            columns.add("入党申请人");
            columns.add("入党积极分子");
            columns.add("发展对象");
            columns.add("正式党员");
            columns.add("预备党员");
            columns.add("普通学生");
            columns.add("合计");
            columns.add("培养情况占比");
            columns.add("党员占比");
            modelMap.put("columns", columns);

            List<Map<String, String>> data = new ArrayList<>();
            // 二级党组织名称

            List<Party> partyNames = statOwInfoMapper.getSecondPartyName();
            for (Party p: partyNames) {
                //申请入党人员
                List<StatByteBean> applyJoin = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT, null, p.getId(), null);
                List<StatByteBean> passJoin = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS, null, p.getId(), null);
                // 入党积极分子
                List<StatByteBean> countActivists = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE, null, p.getId(), null);
                // 发展对象
                List<StatByteBean> countDevelopment = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE, null, p.getId(), null);
                // 预备党员
                List<StatByteBean> preparedMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, p.getId(), null, null, null);
                // 正式党员
                List<StatByteBean> formalMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, p.getId(), null, null, null);
                // 封装数据
                List<Map<String, String>> result = statOwInfoService.encapsulationData(preparedMembers, formalMembers, applyJoin, passJoin, countActivists, countDevelopment);
                masters = result.get(0);
                doctors = result.get(1);

                masters.put("identity", "masters");
                doctors.put("identity", "doctors");
                // 普通学生
                int general = 0;
                masters.put("generalCount", String.valueOf(general));
                doctors.put("generalCount", String.valueOf(general));
                //硕士研究生合计
                Integer applyTotal = masters.get("applyTotal") == null ? 0 : Integer.valueOf(masters.get("applyTotal"));
                Integer activityTotal = masters.get("activityTotal") == null ? 0 : Integer.valueOf(masters.get("activityTotal"));
                Integer developTotal = masters.get("developTotal") == null ? 0 : Integer.valueOf(masters.get("developTotal"));
                Integer formalMember = masters.get("formalMembers") == null ? 0 : Integer.valueOf(masters.get("formalMembers"));
                Integer preparedMember = masters.get("preparedMembers") == null ? 0 : Integer.valueOf(masters.get("preparedMembers"));
                Integer masterTotal = applyTotal + activityTotal + developTotal + formalMember + preparedMember +
                                      (masters.get("generalCount") == null ? 0 : Integer.valueOf(masters.get("generalCount")));
                masters.put("total", masterTotal.toString());
                //培养情况占比 (入党申请人+入党积极分子+发展对象)/合计
                BigDecimal num1 = new BigDecimal(applyTotal + activityTotal + developTotal);
                BigDecimal num2 = new BigDecimal(masterTotal);
                BigDecimal masterScale = (num1.compareTo(BigDecimal.ZERO) == 0 || num2.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : num1.divide(num2,4,BigDecimal.ROUND_HALF_UP));
                //党员占比 (正式党员+预备党员)/合计
                num1 = new BigDecimal(formalMember + preparedMember);
                num2 = new BigDecimal(masterTotal);
                BigDecimal masterPercent = (num1.compareTo(BigDecimal.ZERO) == 0 || num2.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : num1.divide(num2,4,BigDecimal.ROUND_HALF_UP));

                //博士研究生合计
                applyTotal = doctors.get("applyTotal") == null ? 0 : Integer.valueOf(doctors.get("applyTotal"));
                activityTotal = doctors.get("activityTotal") == null ? 0 : Integer.valueOf(doctors.get("activityTotal"));
                developTotal = doctors.get("developTotal") == null ? 0 : Integer.valueOf(doctors.get("developTotal"));
                formalMember = doctors.get("formalMembers") == null ? 0 : Integer.valueOf(doctors.get("formalMembers"));
                preparedMember = doctors.get("preparedMembers") == null ? 0 : Integer.valueOf(doctors.get("preparedMembers"));
                Integer doctorTotal = applyTotal + activityTotal + developTotal + formalMember + preparedMember +
                        (doctors.get("generalCount") == null ? 0 : Integer.valueOf(doctors.get("generalCount")));
                doctors.put("total", doctorTotal.toString());
                //培养情况占比 (入党申请人+入党积极分子+发展对象)/合计
                num1 = new BigDecimal(applyTotal + activityTotal + developTotal);
                num2 = new BigDecimal(doctorTotal);
                BigDecimal doctorScale = (num1.compareTo(BigDecimal.ZERO) == 0 || num2.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : num1.divide(num2,4,BigDecimal.ROUND_HALF_UP));
                //党员占比 (正式党员+预备党员)/合计
                num1 = new BigDecimal(formalMember + preparedMember);
                num2 = new BigDecimal(doctorTotal);
                BigDecimal doctorPercent = (num1.compareTo(BigDecimal.ZERO) == 0 || num2.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : num1.divide(num2,4,BigDecimal.ROUND_HALF_UP));

                masters.put("masterScale", df.format(masterScale.doubleValue() * 100) + "%");
                doctors.put("doctorScale", df.format(doctorScale.doubleValue() * 100) + "%");
                masters.put("masterPercent", df.format(masterPercent.doubleValue() * 100) + "%");
                doctors.put("doctorPercent", df.format(doctorPercent.doubleValue() * 100) + "%");
                masters.put("partyName", p.getShortName());
                data.add(masters);
                data.add(doctors);
            }
            modelMap.put("data", data);
            if (export == 2) {
                XSSFWorkbook wb = statOwInfoService.statOnPartyInfoExport(modelMap, partyNames);
                String filename = String.format("各二级党组织研究生队伍党员信息分析.xlsx");
                ExportHelper.output(wb, filename, response);
                return null;
            }
            return "analysis/statOwInfo/ow_yjs_secord_party_info";
        }
        return "analysis/statOwInfo/ow_yjs_info_page";
    }
}
