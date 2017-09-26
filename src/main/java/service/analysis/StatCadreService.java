package service.analysis;

import bean.statCadre.StatCadreBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.NumberUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2017/1/18.
 */
@Service
public class StatCadreService extends BaseMapper {

    // 导出
    public XSSFWorkbook toXlsx() throws IOException {

        InputStream is = getClass().getResourceAsStream("/xlsx/cadre_template.xlsx");
        XSSFWorkbook wb=new XSSFWorkbook(is);

        renderSheetData(wb, null);
        renderSheetData(wb, SystemConstants.UNIT_TYPE_ATTR_JG);
        renderSheetData(wb, SystemConstants.UNIT_TYPE_ATTR_XY);
        renderSheetData(wb, SystemConstants.UNIT_TYPE_ATTR_FS);

        return wb;
    }

    private void renderSheetData(XSSFWorkbook wb, String type)  {

        Sheet sheet = wb.getSheetAt(0);
        if(StringUtils.equals(type, SystemConstants.UNIT_TYPE_ATTR_JG)){
            sheet = wb.getSheetAt(1);
        }else if(StringUtils.equals(type, SystemConstants.UNIT_TYPE_ATTR_XY)){
            sheet = wb.getSheetAt(2);
        }else if(StringUtils.equals(type, SystemConstants.UNIT_TYPE_ATTR_FS)){
            sheet = wb.getSheetAt(3);
        }

        Header header = sheet.getHeader();
        header.setRight("截至" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        int rowNum = 3;
        Map<String, List> dataMap = stat(type);
        for (Map.Entry<String, List> entry : dataMap.entrySet()) {

            List rowData = entry.getValue();
            readerCellData(2, sheet.getRow(rowNum), rowData);
            rowNum++;
        }
    }

    private static void readerCellData(int startColumnNum, Row row, List data){

        NumberFormat nf = NumberFormat.getPercentInstance();
        int size = data.size();
        for (int i = 0; i < size; i++) {

            Cell cell=row.getCell(startColumnNum);
            if(i%2==0) {
                cell.setCellValue((Integer) data.get(i));
            }else {
                try {
                    cell.setCellValue(nf.parse((String) data.get(i)).doubleValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            startColumnNum++;
        }
    }

    // 中层干部统计
    public Map<String, List> stat(String unitTypeAttr){

        unitTypeAttr = StringUtils.trimToNull(unitTypeAttr);

        int cadreCount = statCadreMapper.countCadre(null);
        int count = cadreCount;
        if(unitTypeAttr!=null) {
            // 总人数
            count = statCadreMapper.countCadre(unitTypeAttr);
        }

        Map<String, List> result = new LinkedHashMap<>();
        // 第一行：总数
        row1(result, cadreCount, count, unitTypeAttr);
        // 第二、三、四行：正处、副处、聘任制（无级别）
        row234(result, unitTypeAttr);
        // 汉族、少数名族
        row56(result, count, unitTypeAttr);
        // 中共党员、民主党派
        row78(result, count, unitTypeAttr);
        // 30岁及以下、...、55岁以上
        row9_15(result, count, unitTypeAttr);
        // 正高(总)、...、中级及以下
        row16_21(result, count, unitTypeAttr);
        // 博士、...、大专
        row22_25(result, count, unitTypeAttr);
        // 专职干部
        row26(result, count, unitTypeAttr);

        return result;
    }

    public void row1(Map<String, List> result, int cadreCount, int count, String unitTypeAttr){

        List row = new ArrayList<>();
        row.add(count);
        row.add(percent(count, cadreCount));

        // 行政级别
        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_adminLevel(unitTypeAttr);

        int mainCount = 0, viceCount = 0, noneCount = 0;
        for (StatCadreBean bean : adminLevelList) {
            if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")){
                mainCount = bean.getNum();
            }else if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")){
                viceCount = bean.getNum();
            }else if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")){
                noneCount = bean.getNum();
            }
        }
        row.add(mainCount);
        row.add(percent(mainCount, count));
        row.add(viceCount);
        row.add(percent(viceCount, count));
        row.add(noneCount);
        row.add(percent(noneCount, count));

        // 男女
        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_gender(unitTypeAttr);
        int male = 0, female = 0;
        for (StatCadreBean bean : genderList) {
            if(bean.getGender()== SystemConstants.GENDER_MALE){
                male = bean.getNum();
            }else if(bean.getGender()== SystemConstants.GENDER_FEMALE){
                female = bean.getNum();
            }
        }
        row.add(male);
        row.add(percent(male, count));
        row.add(female);
        row.add(percent(female, count));

        result.put("row1", row);
    }

    public void row234(Map<String, List> result, String unitTypeAttr){

        List row1 = result.get("row1");

        List row2 = new ArrayList<>();
        List row3 = new ArrayList<>();
        List row4 = new ArrayList<>();
        row2.add(row1.get(2));
        row2.add(row1.get(3));
        row2.add(row1.get(2));
        row2.add(percent((int)row1.get(2), row2));
        row2.add(0);
        row2.add(percent(0, 0));
        row2.add(0);
        row2.add(percent(0, 0));

        row3.add(row1.get(4));
        row3.add(row1.get(5));
        row3.add(0);
        row3.add(percent(0, 0));
        row3.add(row1.get(4));
        row3.add(percent((int)row1.get(4), row3));
        row3.add(0);
        row3.add(percent(0, 0));

        row4.add(row1.get(6));
        row4.add(row1.get(7));
        row4.add(0);
        row4.add(percent(0, 0));
        row4.add(0);
        row4.add(percent(0, 0));
        row4.add(row1.get(6));
        row4.add(percent((int)row1.get(6), row4));

        // 行政级别
        List<StatCadreBean> adminLevelGenderList = statCadreMapper.cadre_stat_adminLevel_gender(unitTypeAttr);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0; // 男
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0; // 女
        for (StatCadreBean bean : adminLevelGenderList) {
            if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")){
                if(bean.getGender()== SystemConstants.GENDER_MALE){
                    mainCount1 += bean.getNum();
                }else if(bean.getGender()== SystemConstants.GENDER_FEMALE){
                    mainCount2 += bean.getNum();
                }
            }else if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")){
                if(bean.getGender()== SystemConstants.GENDER_MALE){
                    viceCount1 += bean.getNum();
                }else if(bean.getGender()== SystemConstants.GENDER_FEMALE){
                    viceCount2 += bean.getNum();
                }
            }else if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")){
                if(bean.getGender()== SystemConstants.GENDER_MALE){
                    noneCount1 += bean.getNum();
                }else if(bean.getGender()== SystemConstants.GENDER_FEMALE){
                    noneCount2 += bean.getNum();
                }
            }
        }
        row2.add(mainCount1);
        row2.add(percent(mainCount1, row2));
        row2.add(mainCount2);
        row2.add(percent(mainCount2, row2));

        row3.add(viceCount1);
        row3.add(percent(viceCount1, row3));
        row3.add(viceCount2);
        row3.add(percent(viceCount2, row3));

        row4.add(noneCount1);
        row4.add(percent(noneCount1, row4));
        row4.add(noneCount2);
        row4.add(percent(noneCount2, row4));

        result.put("row2", row2);
        result.put("row3", row3);
        result.put("row4", row4);
    }

    public void row56(Map<String, List> result, int count, String unitTypeAttr){


        List row5 = new ArrayList<>();
        List row6 = new ArrayList<>();

        List<StatCadreBean> nationList = statCadreMapper.cadre_stat_nation(unitTypeAttr);

        int nation1 = 0, nation2 = 0;
        for (StatCadreBean bean : nationList) {
            if(StringUtils.contains(bean.getNation(), "汉")){
                nation1 += bean.getNum();
            }else {
                nation2 += bean.getNum();
            }
        }
        row5.add(nation1);
        row5.add(percent(nation1, count));

        row6.add(nation2);
        row6.add(percent(nation2, count));


        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_nation_adminLevel(unitTypeAttr);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        for (StatCadreBean bean : adminLevelList) {
            if(StringUtils.contains(bean.getNation(), "汉")){
                if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")){
                    mainCount1 += bean.getNum();
                }else if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")){
                    viceCount1 += bean.getNum();
                }else if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")){
                    noneCount1 += bean.getNum();
                }
            }else {
                if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")){
                    mainCount2 += bean.getNum();
                }else if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")){
                    viceCount2 += bean.getNum();
                }else if(StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")){
                    noneCount2 += bean.getNum();
                }
            }
        }
        row5.add(mainCount1);
        row5.add(percent(mainCount1, row5));
        row5.add(viceCount1);
        row5.add(percent(viceCount1, row5));
        row5.add(noneCount1);
        row5.add(percent(noneCount1, row5));

        row6.add(mainCount2);
        row6.add(percent(mainCount2, row6));
        row6.add(viceCount2);
        row6.add(percent(viceCount2, row6));
        row6.add(noneCount2);
        row6.add(percent(noneCount2, row6));

        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_nation_gender(unitTypeAttr);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        for (StatCadreBean bean : genderList) {
            if(StringUtils.contains(bean.getNation(), "汉")){
                if(bean.getGender()== SystemConstants.GENDER_MALE){
                    male1 += bean.getNum();
                }else if(bean.getGender()== SystemConstants.GENDER_FEMALE){
                    female1 += bean.getNum();
                }
            }else {
                if(bean.getGender()== SystemConstants.GENDER_MALE){
                    male2 += bean.getNum();
                }else if(bean.getGender()== SystemConstants.GENDER_FEMALE){
                    female2 += bean.getNum();
                }
            }

        }
        row5.add(male1);
        row5.add(percent(male1, row5));
        row5.add(female1);
        row5.add(percent(female1, row5));

        row6.add(male2);
        row6.add(percent(male2, row6));
        row6.add(female2);
        row6.add(percent(female2, row6));

        result.put("row5", row5);
        result.put("row6", row6);
    }

    private void row78(Map<String, List> result, int count, String unitTypeAttr) {

        List row7 = new ArrayList<>();
        List row8 = new ArrayList<>();

        StatCadreBean totalBean = statCadreMapper.cadre_stat_dp(unitTypeAttr);
        row8.add(totalBean==null?"":totalBean.getNum1());
        row8.add(totalBean == null ? "" : percent(totalBean.getNum1(), count));
        row7.add(totalBean==null?"":totalBean.getNum2());
        row7.add(totalBean==null?"":percent(totalBean.getNum2(), count));

        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_dp_adminLevel(unitTypeAttr);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        if(adminLevelList!=null) {
            for (StatCadreBean bean : adminLevelList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")) {
                    mainCount1 += bean.getNum1();
                    mainCount2 += bean.getNum2();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")) {
                    viceCount1 += bean.getNum1();
                    viceCount2 += bean.getNum2();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                    noneCount1 += bean.getNum1();
                    noneCount2 += bean.getNum2();
                }
            }
        }
        row8.add(mainCount1);
        row8.add(percent(mainCount1, row8));
        row8.add(viceCount1);
        row8.add(percent(viceCount1, row8));
        row8.add(noneCount1);
        row8.add(percent(noneCount1, row8));

        row7.add(mainCount2);
        row7.add(percent(mainCount2, row7));
        row7.add(viceCount2);
        row7.add(percent(viceCount2, row7));
        row7.add(noneCount2);
        row7.add(percent(noneCount2, row7));

        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_dp_gender(unitTypeAttr);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        if(genderList!=null) {
            for (StatCadreBean bean : genderList) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    male1 += bean.getNum1();
                    male2 += bean.getNum2();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    female1 += bean.getNum1();
                    female2 += bean.getNum2();
                }
            }
        }
        row8.add(male1);
        row8.add(percent(male1, row8));
        row8.add(female1);
        row8.add(percent(female1, row8));

        row7.add(male2);
        row7.add(percent(male2, row7));
        row7.add(female2);
        row7.add(percent(female2, row7));

        result.put("row7", row7);
        result.put("row8", row8);
    }

    private void row9_15(Map<String, List> result, int count, String unitTypeAttr) {

        List row9 = new ArrayList<>();
        List row10 = new ArrayList<>();
        List row11 = new ArrayList<>();
        List row12 = new ArrayList<>();
        List row13 = new ArrayList<>();
        List row14 = new ArrayList<>();
        List row15 = new ArrayList<>();

        StatCadreBean totalBean = statCadreMapper.cadre_stat_age(unitTypeAttr);
        row9.add(totalBean==null?"":totalBean.getNum1());
        row9.add(totalBean == null ? "" : percent(totalBean.getNum1(), count));

        row10.add(totalBean==null?"":totalBean.getNum2());
        row10.add(totalBean==null?"":percent(totalBean.getNum2(), count));

        row11.add(totalBean==null?"":totalBean.getNum3());
        row11.add(totalBean==null?"":percent(totalBean.getNum3(), count));

        row12.add(totalBean==null?"":totalBean.getNum4());
        row12.add(totalBean==null?"":percent(totalBean.getNum4(), count));

        row13.add(totalBean==null?"":totalBean.getNum5());
        row13.add(totalBean==null?"":percent(totalBean.getNum5(), count));

        row14.add(totalBean==null?"":totalBean.getNum6());
        row14.add(totalBean==null?"":percent(totalBean.getNum6(), count));

        row15.add(totalBean==null?"":totalBean.getNum7());
        row15.add(totalBean==null?"":percent(totalBean.getNum7(), count));



        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_age_adminLevel(unitTypeAttr);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        int mainCount3 = 0, viceCount3 = 0, noneCount3 = 0;
        int mainCount4 = 0, viceCount4 = 0, noneCount4 = 0;
        int mainCount5 = 0, viceCount5 = 0, noneCount5 = 0;
        int mainCount6 = 0, viceCount6 = 0, noneCount6 = 0;
        int mainCount7 = 0, viceCount7 = 0, noneCount7 = 0;
        if(adminLevelList!=null) {
            for (StatCadreBean bean : adminLevelList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")) {
                    mainCount1 += bean.getNum1();
                    mainCount2 += bean.getNum2();
                    mainCount3 += bean.getNum3();
                    mainCount4 += bean.getNum4();
                    mainCount5 += bean.getNum5();
                    mainCount6 += bean.getNum6();
                    mainCount7 += bean.getNum7();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")) {
                    viceCount1 += bean.getNum1();
                    viceCount2 += bean.getNum2();
                    viceCount3 += bean.getNum3();
                    viceCount4 += bean.getNum4();
                    viceCount5 += bean.getNum5();
                    viceCount6 += bean.getNum6();
                    viceCount7 += bean.getNum7();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                    noneCount1 += bean.getNum1();
                    noneCount2 += bean.getNum2();
                    noneCount3 += bean.getNum3();
                    noneCount4 += bean.getNum4();
                    noneCount5 += bean.getNum5();
                    noneCount6 += bean.getNum6();
                    noneCount7 += bean.getNum7();
                }
            }
        }
        row9.add(mainCount1);
        row9.add(percent(mainCount1, row9));
        row9.add(viceCount1);
        row9.add(percent(viceCount1, row9));
        row9.add(noneCount1);
        row9.add(percent(noneCount1, row9));

        row10.add(mainCount2);
        row10.add(percent(mainCount2, row10));
        row10.add(viceCount2);
        row10.add(percent(viceCount2, row10));
        row10.add(noneCount2);
        row10.add(percent(noneCount2, row10));

        row11.add(mainCount3);
        row11.add(percent(mainCount3, row11));
        row11.add(viceCount3);
        row11.add(percent(viceCount3, row11));
        row11.add(noneCount3);
        row11.add(percent(noneCount3, row11));

        row12.add(mainCount4);
        row12.add(percent(mainCount4, row12));
        row12.add(viceCount4);
        row12.add(percent(viceCount4, row12));
        row12.add(noneCount4);
        row12.add(percent(noneCount4, row12));

        row13.add(mainCount5);
        row13.add(percent(mainCount5, row13));
        row13.add(viceCount5);
        row13.add(percent(viceCount5, row13));
        row13.add(noneCount5);
        row13.add(percent(noneCount5, row13));

        row14.add(mainCount6);
        row14.add(percent(mainCount6, row14));
        row14.add(viceCount6);
        row14.add(percent(viceCount6, row14));
        row14.add(noneCount6);
        row14.add(percent(noneCount6, row14));

        row15.add(mainCount7);
        row15.add(percent(mainCount7, row15));
        row15.add(viceCount7);
        row15.add(percent(viceCount7, row15));
        row15.add(noneCount7);
        row15.add(percent(noneCount7, row15));

        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_age_gender(unitTypeAttr);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        int male3 = 0, female3 = 0;
        int male4 = 0, female4 = 0;
        int male5 = 0, female5 = 0;
        int male6 = 0, female6 = 0;
        int male7 = 0, female7 = 0;
        if(genderList!=null) {
            for (StatCadreBean bean : genderList) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    male1 += bean.getNum1();
                    male2 += bean.getNum2();
                    male3 += bean.getNum3();
                    male4 += bean.getNum4();
                    male5 += bean.getNum5();
                    male6 += bean.getNum6();
                    male7 += bean.getNum7();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    female1 += bean.getNum1();
                    female2 += bean.getNum2();
                    female3 += bean.getNum3();
                    female4 += bean.getNum4();
                    female5 += bean.getNum5();
                    female6 += bean.getNum6();
                    female7 += bean.getNum7();
                }
            }
        }
        row9.add(male1);
        row9.add(percent(male1, row9));
        row9.add(female1);
        row9.add(percent(female1, row9));

        row10.add(male2);
        row10.add(percent(male2, row10));
        row10.add(female2);
        row10.add(percent(female2, row10));

        row11.add(male3);
        row11.add(percent(male3, row11));
        row11.add(female3);
        row11.add(percent(female3, row11));

        row12.add(male4);
        row12.add(percent(male4, row12));
        row12.add(female4);
        row12.add(percent(female4, row12));

        row13.add(male5);
        row13.add(percent(male5, row13));
        row13.add(female5);
        row13.add(percent(female5, row13));

        row14.add(male6);
        row14.add(percent(male6, row14));
        row14.add(female6);
        row14.add(percent(female6, row14));

        row15.add(male7);
        row15.add(percent(male7, row15));
        row15.add(female7);
        row15.add(percent(female7, row15));

        result.put("row9", row9);
        result.put("row10", row10);
        result.put("row11", row11);
        result.put("row12", row12);
        result.put("row13", row13);
        result.put("row14", row14);
        result.put("row15", row15);
    }

    private void row16_21(Map<String, List> result, int count, String unitTypeAttr) {

        List row16 = new ArrayList<>();
        List row17 = new ArrayList<>();
        List row18 = new ArrayList<>();
        List row19 = new ArrayList<>();
        List row20 = new ArrayList<>();
        List row21 = new ArrayList<>();

        StatCadreBean totalBean = statCadreMapper.cadre_stat_post(unitTypeAttr);
        row16.add(totalBean==null?"":totalBean.getNum1());
        row16.add(totalBean == null ? "" : percent(totalBean.getNum1(), count));

        row17.add(totalBean==null?"":totalBean.getNum2());
        row17.add(totalBean==null?"":percent(totalBean.getNum2(), count));

        row18.add(totalBean==null?"":totalBean.getNum3());
        row18.add(totalBean==null?"":percent(totalBean.getNum3(), count));

        row19.add(totalBean==null?"":totalBean.getNum4());
        row19.add(totalBean==null?"":percent(totalBean.getNum4(), count));

        row20.add(totalBean==null?"":totalBean.getNum5());
        row20.add(totalBean==null?"":percent(totalBean.getNum5(), count));

        row21.add(totalBean==null?"":totalBean.getNum6());
        row21.add(totalBean==null?"":percent(totalBean.getNum6(), count));


        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_post_adminLevel(unitTypeAttr);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        int mainCount3 = 0, viceCount3 = 0, noneCount3 = 0;
        int mainCount4 = 0, viceCount4 = 0, noneCount4 = 0;
        int mainCount5 = 0, viceCount5 = 0, noneCount5 = 0;
        int mainCount6 = 0, viceCount6 = 0, noneCount6 = 0;
        if(adminLevelList!=null) {
            for (StatCadreBean bean : adminLevelList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")) {
                    mainCount1 += bean.getNum1();
                    mainCount2 += bean.getNum2();
                    mainCount3 += bean.getNum3();
                    mainCount4 += bean.getNum4();
                    mainCount5 += bean.getNum5();
                    mainCount6 += bean.getNum6();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")) {
                    viceCount1 += bean.getNum1();
                    viceCount2 += bean.getNum2();
                    viceCount3 += bean.getNum3();
                    viceCount4 += bean.getNum4();
                    viceCount5 += bean.getNum5();
                    viceCount6 += bean.getNum6();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                    noneCount1 += bean.getNum1();
                    noneCount2 += bean.getNum2();
                    noneCount3 += bean.getNum3();
                    noneCount4 += bean.getNum4();
                    noneCount5 += bean.getNum5();
                    noneCount6 += bean.getNum6();
                }
            }
        }
        row16.add(mainCount1);
        row16.add(percent(mainCount1, row16));
        row16.add(viceCount1);
        row16.add(percent(viceCount1, row16));
        row16.add(noneCount1);
        row16.add(percent(noneCount1, row16));

        row17.add(mainCount2);
        row17.add(percent(mainCount2, row17));
        row17.add(viceCount2);
        row17.add(percent(viceCount2, row17));
        row17.add(noneCount2);
        row17.add(percent(noneCount2, row17));

        row18.add(mainCount3);
        row18.add(percent(mainCount3, row18));
        row18.add(viceCount3);
        row18.add(percent(viceCount3, row18));
        row18.add(noneCount3);
        row18.add(percent(noneCount3, row18));

        row19.add(mainCount4);
        row19.add(percent(mainCount4, row19));
        row19.add(viceCount4);
        row19.add(percent(viceCount4, row19));
        row19.add(noneCount4);
        row19.add(percent(noneCount4, row19));

        row20.add(mainCount5);
        row20.add(percent(mainCount5, row20));
        row20.add(viceCount5);
        row20.add(percent(viceCount5, row20));
        row20.add(noneCount5);
        row20.add(percent(noneCount5, row20));

        row21.add(mainCount6);
        row21.add(percent(mainCount6, row21));
        row21.add(viceCount6);
        row21.add(percent(viceCount6, row21));
        row21.add(noneCount6);
        row21.add(percent(noneCount6, row21));


        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_post_gender(unitTypeAttr);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        int male3 = 0, female3 = 0;
        int male4 = 0, female4 = 0;
        int male5 = 0, female5 = 0;
        int male6 = 0, female6 = 0;
        if(genderList!=null) {
            for (StatCadreBean bean : genderList) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    male1 += bean.getNum1();
                    male2 += bean.getNum2();
                    male3 += bean.getNum3();
                    male4 += bean.getNum4();
                    male5 += bean.getNum5();
                    male6 += bean.getNum6();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    female1 += bean.getNum1();
                    female2 += bean.getNum2();
                    female3 += bean.getNum3();
                    female4 += bean.getNum4();
                    female5 += bean.getNum5();
                    female6 += bean.getNum6();
                }
            }
        }
        row16.add(male1);
        row16.add(percent(male1, row16));
        row16.add(female1);
        row16.add(percent(female1, row16));

        row17.add(male2);
        row17.add(percent(male2, row17));
        row17.add(female2);
        row17.add(percent(female2, row17));

        row18.add(male3);
        row18.add(percent(male3, row18));
        row18.add(female3);
        row18.add(percent(female3, row18));

        row19.add(male4);
        row19.add(percent(male4, row19));
        row19.add(female4);
        row19.add(percent(female4, row19));

        row20.add(male5);
        row20.add(percent(male5, row20));
        row20.add(female5);
        row20.add(percent(female5, row20));

        row21.add(male6);
        row21.add(percent(male6, row21));
        row21.add(female6);
        row21.add(percent(female6, row21));

        result.put("row16", row16);
        result.put("row17", row17);
        result.put("row18", row18);
        result.put("row19", row19);
        result.put("row20", row20);
        result.put("row21", row21);
    }

    private void row22_25(Map<String, List> result, int count, String unitTypeAttr) {

        List row22 = new ArrayList<>();
        List row23 = new ArrayList<>();
        List row24 = new ArrayList<>();
        List row25 = new ArrayList<>();

        int bs = 0, yjs = 0, bk = 0, zk = 0;
        List<StatCadreBean> eduList = statCadreMapper.cadre_stat_edu(unitTypeAttr);
        if(eduList!=null) {
            for (StatCadreBean bean : eduList) {
                if (StringUtils.equals(bean.getMaxCeEduAttr(), "bs")) {
                    bs += bean.getNum();
                } else if (StringUtils.equals(bean.getMaxCeEduAttr(), "yjs")) {
                    yjs += bean.getNum();
                } else if (StringUtils.equals(bean.getMaxCeEduAttr(), "bk")) {
                    bk += bean.getNum();
                } else if (StringUtils.equals(bean.getMaxCeEduAttr(), "zk")) {
                    zk += bean.getNum();
                }
            }
        }
        row22.add(bs);
        row22.add(percent(bs, count));

        row23.add(yjs);
        row23.add(percent(yjs, count));

        row24.add(bk);
        row24.add(percent(bk, count));

        row25.add(zk);
        row25.add(percent(zk, count));


        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_edu_adminLevel(unitTypeAttr);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        int mainCount3 = 0, viceCount3 = 0, noneCount3 = 0;
        int mainCount4 = 0, viceCount4 = 0, noneCount4 = 0;
        if(adminLevelList!=null) {
            for (StatCadreBean bean : adminLevelList) {

                if (StringUtils.equals(bean.getMaxCeEduAttr(), "bs")) {
                    if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")) {
                        mainCount1 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")) {
                        viceCount1 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                        noneCount1 += bean.getNum();
                    }
                } else if (StringUtils.equals(bean.getMaxCeEduAttr(), "yjs")) {
                    if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")) {
                        mainCount2 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")) {
                        viceCount2 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                        noneCount2 += bean.getNum();
                    }
                } else if (StringUtils.equals(bean.getMaxCeEduAttr(), "bk")) {
                    if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")) {
                        mainCount3 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")) {
                        viceCount3 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                        noneCount3 += bean.getNum();
                    }
                } else if (StringUtils.equals(bean.getMaxCeEduAttr(), "zk")) {
                    if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")) {
                        mainCount4 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")) {
                        viceCount4 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                        noneCount4 += bean.getNum();
                    }
                }

            }
        }
        row22.add(mainCount1);
        row22.add(percent(mainCount1, row22));
        row22.add(viceCount1);
        row22.add(percent(viceCount1, row22));
        row22.add(noneCount1);
        row22.add(percent(noneCount1, row22));

        row23.add(mainCount2);
        row23.add(percent(mainCount2, row23));
        row23.add(viceCount2);
        row23.add(percent(viceCount2, row23));
        row23.add(noneCount2);
        row23.add(percent(noneCount2, row23));

        row24.add(mainCount3);
        row24.add(percent(mainCount3, row24));
        row24.add(viceCount3);
        row24.add(percent(viceCount3, row24));
        row24.add(noneCount3);
        row24.add(percent(noneCount3, row24));

        row25.add(mainCount4);
        row25.add(percent(mainCount4, row25));
        row25.add(viceCount4);
        row25.add(percent(viceCount4, row25));
        row25.add(noneCount4);
        row25.add(percent(noneCount4, row25));


        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_edu_gender(unitTypeAttr);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        int male3 = 0, female3 = 0;
        int male4 = 0, female4 = 0;
        if(genderList!=null) {
            for (StatCadreBean bean : genderList) {

                if (StringUtils.equals(bean.getMaxCeEduAttr(), "bs")) {
                    if (bean.getGender() == SystemConstants.GENDER_MALE) {
                        male1 += bean.getNum();
                    } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                        female1 += bean.getNum();
                    }
                } else if (StringUtils.equals(bean.getMaxCeEduAttr(), "yjs")) {
                    if (bean.getGender() == SystemConstants.GENDER_MALE) {
                        male2 += bean.getNum();
                    } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                        female2 += bean.getNum();
                    }
                } else if (StringUtils.equals(bean.getMaxCeEduAttr(), "bk")) {
                    if (bean.getGender() == SystemConstants.GENDER_MALE) {
                        male3 += bean.getNum();
                    } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                        female3 += bean.getNum();
                    }
                } else if (StringUtils.equals(bean.getMaxCeEduAttr(), "zk")) {
                    if (bean.getGender() == SystemConstants.GENDER_MALE) {
                        male4 += bean.getNum();
                    } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                        female4 += bean.getNum();
                    }
                }

            }
        }
        row22.add(male1);
        row22.add(percent(male1, row22));
        row22.add(female1);
        row22.add(percent(female1, row22));

        row23.add(male2);
        row23.add(percent(male2, row23));
        row23.add(female2);
        row23.add(percent(female2, row23));

        row24.add(male3);
        row24.add(percent(male3, row24));
        row24.add(female3);
        row24.add(percent(female3, row24));

        row25.add(male4);
        row25.add(percent(male4, row25));
        row25.add(female4);
        row25.add(percent(female4, row25));


        result.put("row22", row22);
        result.put("row23", row23);
        result.put("row24", row24);
        result.put("row25", row25);
    }

    private void row26(Map<String, List> result, int count, String unitTypeAttr) {

        StatCadreBean doubleBean = statCadreMapper.cadre_stat_double(unitTypeAttr);
        List row = new ArrayList<>();
        row.add(doubleBean==null?"":doubleBean.getNum());
        row.add(doubleBean == null ? "" : percent(doubleBean.getNum(), count));

        // 行政级别
        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_double_adminLevel(unitTypeAttr);

        int mainCount = 0, viceCount = 0, noneCount = 0;
        if(adminLevelList!=null) {
            for (StatCadreBean bean : adminLevelList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_main")) {
                    mainCount = bean.getNum();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice")) {
                    viceCount = bean.getNum();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                    noneCount = bean.getNum();
                }
            }
        }
        row.add(mainCount);
        row.add(percent(mainCount, row));
        row.add(viceCount);
        row.add(percent(viceCount, row));
        row.add(noneCount);
        row.add(percent(noneCount, row));

        // 男女
        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_double_gender(unitTypeAttr);
        int male = 0, female = 0;
        if(genderList!=null) {
            for (StatCadreBean bean : genderList) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    male = bean.getNum();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    female = bean.getNum();
                }
            }
        }
        row.add(male);
        row.add(percent(male, row));
        row.add(female);
        row.add(percent(female, row));

        result.put("row26", row);
    }

    public static String percent(Integer count , List row){
        
        if(row==null || row.size()==0 || !(row.get(0) instanceof Integer)) return "0.0%";
        return percent(count, (int)row.get(0));
    }
    
    public static String percent(Integer count , Integer total){

        if(count==null || total==null || count<=0 || total<=0) return "0.0%";

        return NumberUtils.formatDoubleFixed((count*100.0)/total, 1) + "%";
    }

}
