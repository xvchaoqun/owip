package service.analysis;

import bean.StatByteBean;
import bean.StatIntBean;
import controller.global.OpException;
import domain.party.Party;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import persistence.member.common.MemberStatByBranchBean;
import persistence.member.common.MemberStatByPartyBean;
import service.BaseMapper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import javax.ws.rs.GET;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fafa on 2016/8/1.
 */
@Service
public class StatService extends BaseMapper {

    // 以分党委分类的党员总数（默认前二十）
    public List<MemberStatByPartyBean> partyMap(Integer top) {
        if (top == null) top = 20;
        return statMemberMapper.memberApply_groupByPartyId(top);
    }

    //分党委下党支部分类的党员总数
    public List<MemberStatByBranchBean> branchMap(Integer partyId) {

        return statMemberMapper.memberApply_groupByBranchId(partyId);
    }

    // 按阶段统计党员发展
    public Map politicalStatusMap(Integer partyId, Integer branchId) {

        Map<Byte, Integer> _map = new HashMap<>();
        List<StatByteBean> statByteBeans = statMemberMapper.member_groupByPoliticalStatus(partyId, branchId);
        for (StatByteBean statByteBean : statByteBeans) {
            _map.put(statByteBean.getGroupBy(), statByteBean.getNum());
        }

        Map<Byte, Integer> map = new LinkedHashMap<>();
        for (Byte key : MemberConstants.MEMBER_POLITICAL_STATUS_MAP.keySet()) {
            if (_map.get(key) == null) {
                map.put(key, 0);
            }else {
                map.put(key, _map.get(key));
            }
        }

        return map;
    }

    // 按类型统计党员发展
    public Map typeMap(Byte politicalStatus, Integer partyId, Integer branchId, Byte isRetire) {

        Map<Byte, Integer> _map = new HashMap<>();
        List<StatByteBean> statByteBeans = new ArrayList<>();
        statByteBeans = statMemberMapper.member_groupByType(politicalStatus, partyId, branchId, isRetire);
        if (isRetire == null) {
            int bksCount = 0;
            for (StatByteBean statByteBean : statByteBeans) {
                Byte studentLevel = statByteBean.getGroupBy();
                if (statByteBean.getGroupBy() == null || statByteBean.getGroupBy() == SystemConstants.STUDENT_TYPE_BKS) {
                    studentLevel = SystemConstants.STUDENT_TYPE_BKS;
                    bksCount = statByteBean.getNum() + bksCount;
                    _map.put(studentLevel, bksCount);
                    continue;
                }

                _map.put(studentLevel, statByteBean.getNum());
            }

            Map<Byte, Integer> map = new LinkedHashMap<>();
            for (Byte key : SystemConstants.STUDENT_TYPE_MAP.keySet()) {
                if (_map.get(key) == null) {
                    map.put(key, 0);
                } else {
                    map.put(key, _map.get(key));
                }
            }
            return map;
        }else {
            Map<Byte, Integer> map = new LinkedHashMap<>();
            for (StatByteBean statByteBean : statByteBeans) {
                map.put(statByteBean.getGroupBy(), statByteBean.getNum());
            }
            if (map.size() == 0){
                map.put(SystemConstants.TEACHER_TYPE_JZG, 0);
            }

            return map;
        }

    }

    // 按阶段统计党员发展
    public Map applyMap(Byte type, Integer partyId, Integer branchId) {

        Map<Byte, Integer> _applyMap = new HashMap<>();
        List<StatByteBean> statByteBeans = statMemberMapper.memberApply_groupByStage(type, partyId, branchId);
        for (StatByteBean statByteBean : statByteBeans) {
            if (CmTag.getBoolProperty("ignore_plan_and_draw")){
                if (statByteBean.getGroupBy().equals(OwConstants.OW_APPLY_STAGE_PLAN)
                        || statByteBean.getGroupBy().equals(OwConstants.OW_APPLY_STAGE_DRAW)){
                    continue;
                }
            }
            _applyMap.put(statByteBean.getGroupBy(), statByteBean.getNum());
        }

        Map<Byte, Integer> applyMap = new LinkedHashMap<>();
        for (Byte key : OwConstants.OW_APPLY_STAGE_MAP.keySet()) {
            Integer count = _applyMap.get(key);
            if (key == OwConstants.OW_APPLY_STAGE_INIT) {
                Integer initCount = _applyMap.get(OwConstants.OW_APPLY_STAGE_INIT);
                Integer passCount = _applyMap.get(OwConstants.OW_APPLY_STAGE_PASS);
                count = (initCount != null ? initCount : 0) + (passCount != null ? passCount : 0);
            }
            applyMap.put(key, count);
        }

        return applyMap;
    }

    // 按年龄结构统计
    public Map ageMap(Byte type, Integer partyId, Integer branchId) {

        Map<Byte, Integer> _ageMap = new HashMap<>();
        List<StatIntBean> statIntBeans = new ArrayList<>();

        if (type == null || type == MemberConstants.MEMBER_TYPE_TEACHER)
            statIntBeans.addAll(statMemberMapper.member_teatcherGroupByBirth(partyId, branchId));
        if (type == null || type == MemberConstants.MEMBER_TYPE_STUDENT)
            statIntBeans.addAll(statMemberMapper.member_studentGroupByBirth(partyId, branchId));

        //int year = DateUtils.getCurrentYear();
        for (StatIntBean statIntBean : statIntBeans) {
            Integer age = statIntBean.getGroupBy();
            byte key = MemberConstants.getMemberAgeRange(age);
            Integer total = _ageMap.get(key);
            total = (total == null) ? statIntBean.getNum() : (total + statIntBean.getNum());
            _ageMap.put(key, total);
        }

        Map<Byte, Integer> ageMap = new LinkedHashMap<>();
        for (Byte key : MemberConstants.MEMBER_AGE_MAP.keySet()) {
            ageMap.put(key, _ageMap.get(key));
        }

        return ageMap;
    }

    //统计支部类型
    public Map branchTypeMap(Integer partyId) {

        Map<Integer, Integer> branchTypeMap = new HashMap();
        List<String> branchTypes = statMemberMapper.getBranchTypes(partyId);
        for (String branchType : branchTypes) {

            for (String type : branchType.split(",")) {

                Integer key = Integer.parseInt(type);
                Integer value = branchTypeMap.get(key);

                if (value == null) {

                    branchTypeMap.put(key, 1);
                } else {

                    branchTypeMap.put(key, value + 1);
                }
            }
        }
        return branchTypeMap;
    }

    //党员其他类型统计 1.性别 2.民族
    public Map otherMap(Integer type, Integer partyId, Integer branchId) {

        Map otherMap = new LinkedHashMap();
        if (type == 1) {
            List<StatIntBean> others = statMemberMapper.member_countGroupByGender(partyId, branchId);

            for (StatIntBean other : others) {

                if (other.getGroupBy() != null && (other.getGroupBy().byteValue() == SystemConstants.GENDER_MALE)) {
                    otherMap.put("男", other.getNum());
                } else if (other.getGroupBy() != null && (other.getGroupBy().byteValue() == SystemConstants.GENDER_FEMALE)) {
                    otherMap.put("女", other.getNum());
                } else if(other.getNum()>0){
                    otherMap.put("无数据", other.getNum());
                }
            }
        } else if (type == 2) {

            otherMap.put("汉族", statMemberMapper.countHan(partyId, branchId));
            otherMap.put("少数民族", statMemberMapper.countMinority(partyId, branchId));
            otherMap.put("无数据", statMemberMapper.countNull(partyId, branchId));
        }

        return otherMap;
    }

    // 导出组织机构年统数据
    public XSSFWorkbook owToXlsx(ModelMap modelMap, List<Integer> partyCounts) throws IOException {

        InputStream is = getClass().getResourceAsStream("/xlsx/party/stat_ow_sum.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);

        renderOwData(wb, modelMap, partyCounts); // 汇总

        wb.removeSheetAt(0);

        return wb;
    }

    //导出组织机构年统数据统计
    private void renderOwData(XSSFWorkbook wb, ModelMap modelMap, List<Integer> partyCounts) {

        XSSFSheet sheet = wb.cloneSheet(0, null);
        XSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(true); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE); //纸张

        Header header = sheet.getHeader();

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("date", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);

        //党工委、二级党组织总数
        row = sheet.getRow(3);
        cell = row.getCell(0);
        cell.setCellValue(modelMap.get("partySumCount").toString());

        int j = 5;
        for (int i = 1; i <= partyCounts.size(); i++) {
            cell = row.getCell(j);
            cell.setCellValue(partyCounts.get(i-1)+"");
            j=j+2;
        }

        //党支部总数
        row = sheet.getRow(8);
        cell = row.getCell(0);
        cell.setCellValue(modelMap.get("pgbCount").toString());

        cell = row.getCell(1);
        cell.setCellValue(modelMap.get("branchTotalCount").toString());
        
        cell = row.getCell(2);
        cell.setCellValue(Integer.parseInt(modelMap.get("professionalCount").toString())+Integer.parseInt(modelMap.get("supportCount").toString()));
        
        cell = row.getCell(3);
        cell.setCellValue(modelMap.get("professionalCount").toString());
        
        cell = row.getCell(4);
        cell.setCellValue(modelMap.get("supportCount").toString());
        
        cell = row.getCell(5);
        cell.setCellValue(modelMap.get("retireCount").toString());
        
        cell = row.getCell(6);
        cell.setCellValue(Integer.parseInt(modelMap.get("undergraduateCount").toString())+Integer.parseInt(modelMap.get("graduateCount").toString()));
        
        cell = row.getCell(7);
        cell.setCellValue(modelMap.get("undergraduateCount").toString());
        
        cell = row.getCell(8);
        cell.setCellValue(modelMap.get("graduateCount").toString());
        
        cell = row.getCell(9);
        cell.setCellValue(Integer.parseInt(modelMap.get("ssCount").toString())+Integer.parseInt(modelMap.get("sbCount").toString())+Integer.parseInt(modelMap.get("bsCount").toString()));
        
        cell = row.getCell(10);
        cell.setCellValue(modelMap.get("ssCount").toString());
        
        cell = row.getCell(11);
        cell.setCellValue(modelMap.get("sbCount").toString());
        
        cell = row.getCell(12);
        cell.setCellValue(modelMap.get("bsCount").toString());

        //党员数量
        row = sheet.getRow(13);
        cell = row.getCell(2);
        cell.setCellValue(modelMap.get("totalCount").toString());

        cell = row.getCell(3);
        cell.setCellValue(modelMap.get("teacherCount").toString());

        cell = row.getCell(4);
        cell.setCellValue(Integer.parseInt(modelMap.get("chiefCount").toString())+Integer.parseInt(modelMap.get("deputyCount").toString())+Integer.parseInt(modelMap.get("middleCount").toString()));

        //Integer.parseInt(modelMap.get("").toString())
        cell = row.getCell(5);
        cell.setCellValue(modelMap.get("chiefCount").toString());

        cell = row.getCell(6);
        cell.setCellValue(modelMap.get("deputyCount").toString());

        cell = row.getCell(7);
        cell.setCellValue(modelMap.get("middleCount").toString());

        cell = row.getCell(8);
        cell.setCellValue(modelMap.get("retireTeacherCount").toString());

        cell = row.getCell(9);
        cell.setCellValue(Integer.parseInt(modelMap.get("bksStuCount").toString())+Integer.parseInt(modelMap.get("ssStuCount").toString())+Integer.parseInt(modelMap.get("bsStuCount").toString()));

        cell = row.getCell(10);
        cell.setCellValue(modelMap.get("bksStuCount").toString());

        cell = row.getCell(11);
        cell.setCellValue(modelMap.get("ssStuCount").toString());

        cell = row.getCell(12);
        cell.setCellValue(modelMap.get("bsStuCount").toString());

    }

    // 导出二级党委年统数据
    public XSSFWorkbook partyToXlsx(ModelMap modelMap, Integer partyId) throws IOException {

        InputStream is = getClass().getResourceAsStream("/xlsx/party/stat_party_sum.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);

        renderPartyData(wb, modelMap, partyId); // 汇总

        wb.removeSheetAt(0);

        return wb;
    }

    //导出单个二级党委年统数据统计
    private void renderPartyData(XSSFWorkbook wb, ModelMap modelMap, Integer partyId) {

        XSSFSheet sheet = wb.cloneSheet(0, null);
        XSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(true); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE); //纸张

        Header header = sheet.getHeader();

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("date", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);

        row = sheet.getRow(0);
        cell = row.getCell(0);
        if (partyId != null){
            Party party = partyMapper.selectByPrimaryKey(partyId);
            str = cell.getStringCellValue().replace("party", party.getName());
            cell.setCellValue(str);
        }else {
            str = cell.getStringCellValue().replace("party", "支部及党员");
            cell.setCellValue(str);
            return;
        }

        //党支部总数
        row = sheet.getRow(4);
        cell = row.getCell(0);
        cell.setCellValue(modelMap.get("pgbCount").toString());

        cell = row.getCell(1);
        cell.setCellValue(modelMap.get("branchTotalCount").toString());

        cell = row.getCell(2);
        cell.setCellValue(Integer.parseInt(modelMap.get("professionalCount").toString())+Integer.parseInt(modelMap.get("supportCount").toString()));

        cell = row.getCell(3);
        cell.setCellValue(modelMap.get("professionalCount").toString());

        cell = row.getCell(4);
        cell.setCellValue(modelMap.get("supportCount").toString());

        cell = row.getCell(5);
        cell.setCellValue(modelMap.get("retireCount").toString());

        cell = row.getCell(6);
        cell.setCellValue(Integer.parseInt(modelMap.get("undergraduateCount").toString())+Integer.parseInt(modelMap.get("graduateCount").toString()));

        cell = row.getCell(7);
        cell.setCellValue(modelMap.get("undergraduateCount").toString());

        cell = row.getCell(8);
        cell.setCellValue(modelMap.get("graduateCount").toString());

        cell = row.getCell(9);
        cell.setCellValue(Integer.parseInt(modelMap.get("ssCount").toString())+Integer.parseInt(modelMap.get("sbCount").toString())+Integer.parseInt(modelMap.get("bsCount").toString()));

        cell = row.getCell(10);
        cell.setCellValue(modelMap.get("ssCount").toString());

        cell = row.getCell(11);
        cell.setCellValue(modelMap.get("sbCount").toString());

        cell = row.getCell(12);
        cell.setCellValue(modelMap.get("bsCount").toString());

        //党员数量
        row = sheet.getRow(9);
        cell = row.getCell(2);
        cell.setCellValue(modelMap.get("totalCount").toString());

        cell = row.getCell(3);
        cell.setCellValue(modelMap.get("teacherCount").toString());

        cell = row.getCell(4);
        cell.setCellValue(Integer.parseInt(modelMap.get("chiefCount").toString())+Integer.parseInt(modelMap.get("deputyCount").toString())+Integer.parseInt(modelMap.get("middleCount").toString()));

        //Integer.parseInt(modelMap.get("").toString())
        cell = row.getCell(5);
        cell.setCellValue(modelMap.get("chiefCount").toString());

        cell = row.getCell(6);
        cell.setCellValue(modelMap.get("deputyCount").toString());

        cell = row.getCell(7);
        cell.setCellValue(modelMap.get("middleCount").toString());

        cell = row.getCell(8);
        cell.setCellValue(modelMap.get("retireTeacherCount").toString());

        cell = row.getCell(9);
        cell.setCellValue(Integer.parseInt(modelMap.get("bksStuCount").toString())+Integer.parseInt(modelMap.get("ssStuCount").toString())+Integer.parseInt(modelMap.get("bsStuCount").toString()));

        cell = row.getCell(10);
        cell.setCellValue(modelMap.get("bksStuCount").toString());

        cell = row.getCell(11);
        cell.setCellValue(modelMap.get("ssStuCount").toString());

        cell = row.getCell(12);
        cell.setCellValue(modelMap.get("bsStuCount").toString());

    }

    //得到两个时间点之间的月份（yyyy-MM格式）
    public Set<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        Set<String> result = new TreeSet<>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 1);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        }catch (OpException e){
            e.printStackTrace();
        }

        return result;
    }

    //得到距date相隔amount年的日期
    public String getOtherYear(Date date, int amount){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.YEAR, amount);
        Date y = c.getTime();

        return format.format(y);
    }
}
