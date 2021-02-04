package service.analysis;

import bean.StatByteBean;
import domain.party.Party;
import domain.sys.StudentInfoExample;
import domain.sys.SysUserExample;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import persistence.analysis.StatOwInfoMapper;
import service.BaseMapper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatOwInfoService extends BaseMapper {
    @Autowired
    private StatOwInfoMapper statOwInfoMapper;

    public XSSFWorkbook statOnInfoExport(ModelMap modelMap) {
        InputStream is = null;
        try {
            int startRow = 5;
            int startCol = 1;
            is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/analysis/stat_ow_yjs_info.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);

            XSSFRow row = sheet.getRow(0);
            XSSFCell cell = row.getCell(0);

            String str = cell.getStringCellValue()
                    .replace("school", CmTag.getSysConfig().getSchoolName());
            cell.setCellValue(str);

            row = sheet.getRow(1);
            cell = row.getCell(0);

            str = cell.getStringCellValue()
                    .replace("year", modelMap.get("year").toString());
            cell.setCellValue(str);

            str = cell.getStringCellValue()
                    .replace("month", modelMap.get("month").toString());
            cell.setCellValue(str);

            row = sheet.getRow(startRow);
            cell = row.getCell(startCol);
            Map<String, String> doctors = (Map<String, String>) modelMap.get("doctors");
            cell.setCellValue(Integer.valueOf(doctors.get("total")));
            //博士生总数
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(doctors.get("partyMembersCount")));
            //正式党员
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(doctors.get("formalMembers")));
            //预备党员
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(doctors.get("preparedMembers")));
            //博士生占比
            cell = row.getCell(++startCol);
            cell.setCellValue(doctors.get("doctorPercent"));
            //入党申请人
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(doctors.get("applyTotal")));
            //入党积极分子
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(doctors.get("activityTotal")));
            //发展对象
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(doctors.get("developTotal")));

            startCol = 1;
            row = sheet.getRow(++startRow);
            cell = row.getCell(startCol);

            Map<String, String> masters = (Map<String, String>) modelMap.get("masters");
            //硕士生总数
            cell.setCellValue(Integer.valueOf(masters.get("total")));
            //党员数
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("partyMembersCount")));
            //正式党员
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("formalMembers")));
            //预备党员
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("preparedMembers")));
            //硕士生占比
            cell = row.getCell(++startCol);
            cell.setCellValue(masters.get("masterPercent"));
            //入党申请人
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("applyTotal")));
            //入党积极分子
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("activityTotal")));
            //发展对象
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("developTotal")));

            startCol = 1;
            row = sheet.getRow(3);
            cell = row.getCell(startCol);

            //研究生总数
            cell.setCellValue((Integer) modelMap.get("total"));
            //党员数
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("partyMembersCount")) + Integer.valueOf(doctors.get("partyMembersCount")));
            //正式党员
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("formalMembers")) + Integer.valueOf(doctors.get("formalMembers")));
            //预备党员
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("preparedMembers")) + Integer.valueOf(doctors.get("preparedMembers")));
            //研究生占比
            cell = row.getCell(++startCol);

            cell.setCellValue((String) modelMap.get("percent"));
            //入党申请人
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("applyTotal")) + Integer.valueOf(doctors.get("applyTotal")));
            //入党积极分子
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("activityTotal")) + Integer.valueOf(doctors.get("activityTotal")));
            //发展对象
            cell = row.getCell(++startCol);
            cell.setCellValue(Integer.valueOf(masters.get("developTotal")) + Integer.valueOf(doctors.get("developTotal")));

            return wb;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, String>> encapsulationData(List<StatByteBean> preparedMembers, List<StatByteBean> formalMembers,
                                                        List<StatByteBean> applyJoin, List<StatByteBean> passJoin,
                                                        List<StatByteBean> countActivists, List<StatByteBean> countDevelopment) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> masters = new HashMap<>();
        Map<String, String> doctors = new HashMap<>();
        Integer masterCount = 0, doctorCount = 0;
        Integer mastersApplyTotal = 0, doctorsApplyTotal = 0;
        //预备党员
        for (StatByteBean obj: preparedMembers) {
            if (obj.getGroupBy() != null) {
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                    masters.put("preparedMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                }
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                    doctors.put("preparedMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                }
            }
        }
        //正式党员
        for (StatByteBean obj: formalMembers) {
            if (obj.getGroupBy() != null) {
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                    masters.put("formalMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                }
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                    doctors.put("formalMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                }
            }
        }
        //硕士研究生党员数
        masterCount = masters.get("preparedMembers") == null ? 0 : Integer.valueOf(masters.get("preparedMembers"));
        doctorCount = masters.get("formalMembers") == null ? 0 : Integer.valueOf(masters.get("formalMembers"));
        masters.put("partyMembersCount", String.valueOf(masterCount + doctorCount));
        //博士研究生党员数
        masterCount = doctors.get("preparedMembers") == null ? 0 : Integer.valueOf(doctors.get("preparedMembers"));
        doctorCount = doctors.get("formalMembers") == null ? 0 : Integer.valueOf(doctors.get("formalMembers"));
        doctors.put("partyMembersCount", String.valueOf(masterCount + doctorCount));
        //申请入党人数
        for (StatByteBean obj: applyJoin) {
            if (obj.getGroupBy() != null) {
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                    if (obj.getNum() > 0) {
                        mastersApplyTotal += obj.getNum();
                    }
                }
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                    if (obj.getNum() > 0) {
                        doctorsApplyTotal += obj.getNum();
                    }
                }
            }
        }
        for (StatByteBean obj: passJoin) {
            if (obj.getGroupBy() != null) {
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                    mastersApplyTotal += obj.getNum();
                }
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                    doctorsApplyTotal += obj.getNum();
                }
            }
        }
        masters.put("applyTotal", mastersApplyTotal.toString());
        doctors.put("applyTotal", doctorsApplyTotal.toString());
        //入党积极分子
        for (StatByteBean obj: countActivists) {
            if (obj.getGroupBy() != null) {
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                    masters.put("activityTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                }
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                    doctors.put("activityTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                }
            }
        }
        //发展对象
        for (StatByteBean obj: countDevelopment) {
            if (obj.getGroupBy() != null) {
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                    masters.put("developTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                }
                if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                    doctors.put("developTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                }
            }
        }
        list.add(masters);
        list.add(doctors);
        return list;
    }


    public XSSFWorkbook statOnPartyInfoExport(ModelMap modelMap) {
        InputStream is = null;
        try {
            int startRow = 4;
            int startCol = 0;
            is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/analysis/stat_ow_party_info.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);

            XSSFRow row = sheet.getRow(1);
            XSSFCell cell = row.getCell(0);

            String str = cell.getStringCellValue()
                    .replace("year", modelMap.get("year").toString());
            cell.setCellValue(str);

            str = cell.getStringCellValue()
                    .replace("month", modelMap.get("month").toString());
            cell.setCellValue(str);

            row = sheet.getRow(startRow);
            List<Map<String, String>> data = (List<Map<String, String>>) modelMap.get("data");

            XSSFCellStyle style = wb.createCellStyle();
            XSSFCellStyle style2 = wb.createCellStyle();// 设置这些样式
            style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style2.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            XSSFFont font2 = wb.createFont();// 生成一个字体
            font2.setFontHeightInPoints((short) 11);
            font2.setFontName("宋体");
            style2.setFont(font2);// 把字体应用到当前的样式
            style.setFont(font2);

            for (int i = 0; i < data.size(); i++) {
                Map<String, String> map = data.get(i);

                // map.get("identity")为 masters 表示硕士研究生,为 doctors表示博士研究生
                boolean identity = map.get("identity").equals("masters");
                if (identity) {
                    if (i > 0) {
                        startRow++;
                        startCol = 0;
                    }
                    //二级党组织名称
                    row = sheet.createRow(startRow);
                    cell = row.createCell(startCol);
                    cell.setCellStyle(style);
                    cell.setCellValue(map.get("partyName"));
                }

                //入党申请人
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("applyTotal") == null ? 0 : Integer.valueOf(map.get("applyTotal")));

                //入党积极分子
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("activityTotal") == null ? 0 : Integer.valueOf(map.get("activityTotal")));

                //发展对象
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("developTotal") == null ? 0 : Integer.valueOf(map.get("developTotal")));

                //正式党员
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("formalMembers") == null ? 0 : Integer.valueOf(map.get("formalMembers")));

                //预备党员
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("preparedMembers") == null ? 0 : Integer.valueOf(map.get("preparedMembers")));

                //普通学生
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("generalCount") == null ? 0 : Integer.valueOf(map.get("generalCount")));

                //合计
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(Integer.valueOf(map.get("total")));

                //培养情况占比
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(identity ? map.get("masterScale") : map.get("doctorScale"));

                //党员占比
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(identity ? map.get("masterPercent") : map.get("doctorPercent"));
            }

            return wb;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigDecimal calculateDivide(Integer number1, Integer number2) {
        BigDecimal num1 = new BigDecimal(number1);
        BigDecimal num2 = new BigDecimal(number2);
        BigDecimal result = (num1.compareTo(BigDecimal.ZERO) == 0 || num2.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : num1.divide(num2,4,BigDecimal.ROUND_HALF_UP));
        return result;
    }

    public ModelMap getYjsInfo(ModelMap modelMap, DecimalFormat df) {
        Map<String, String> masters = new HashMap<>();
        Map<String, String> doctors = new HashMap<>();

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
        List<Map<String, String>> data = encapsulationData(preparedMembers, formalMembers, applyJoin, passJoin, countActivists, countDevelopment);
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
        BigDecimal masterPercent = calculateDivide(Integer.valueOf(masters.get("partyMembersCount")), countMasters);

        //博士生党员占比
        BigDecimal doctorPercent = calculateDivide(Integer.valueOf(doctors.get("partyMembersCount")), countDoctors);

        modelMap.put("total", countYjs);
        //研究生占比
        Integer mpmc = masters.get("partyMembersCount") == null ? 0 : Integer.valueOf(masters.get("partyMembersCount"));
        Integer dpmc = doctors.get("partyMembersCount") == null ? 0 : Integer.valueOf(doctors.get("partyMembersCount"));
        Integer total = modelMap.get("total") == null ? 0 : (Integer) modelMap.get("total");

        BigDecimal percent = calculateDivide(mpmc + dpmc, total);

        masters.put("masterPercent", df.format(masterPercent.doubleValue() * 100) + "%");
        doctors.put("doctorPercent", df.format(doctorPercent.doubleValue() * 100) + "%");

        modelMap.put("percent", df.format(percent.doubleValue() * 100) + "%");
        modelMap.put("masters", masters);
        modelMap.put("doctors", doctors);
        return modelMap;
    }

    public List<Map<String, String>> getYjsPartyInfo(DecimalFormat df) {
        Map<String, String> masters = new HashMap<>();
        Map<String, String> doctors = new HashMap<>();
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
            List<Map<String, String>> result = encapsulationData(preparedMembers, formalMembers, applyJoin, passJoin, countActivists, countDevelopment);
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
            BigDecimal masterScale = calculateDivide(applyTotal + activityTotal + developTotal, masterTotal);

            //党员占比 (正式党员+预备党员)/合计
            BigDecimal masterPercent = calculateDivide(formalMember + preparedMember, masterTotal);

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
            BigDecimal doctorScale = calculateDivide(applyTotal + activityTotal + developTotal, doctorTotal);

            //党员占比 (正式党员+预备党员)/合计
            BigDecimal doctorPercent = calculateDivide(formalMember + preparedMember, doctorTotal);

            masters.put("masterScale", df.format(masterScale.doubleValue() * 100) + "%");
            doctors.put("doctorScale", df.format(doctorScale.doubleValue() * 100) + "%");
            masters.put("masterPercent", df.format(masterPercent.doubleValue() * 100) + "%");
            doctors.put("doctorPercent", df.format(doctorPercent.doubleValue() * 100) + "%");
            masters.put("partyName", p.getShortName());
            data.add(masters);
            data.add(doctors);
        }
        return data;
    }
}
