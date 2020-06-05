package service.analysis;

import bean.MetaClassOption;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.CadreView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import persistence.cadre.common.CadreSearchBean;
import persistence.cadre.common.StatCadreBean;
import service.BaseMapper;
import sys.constants.CadreConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.NumberUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by fafa on 2017/1/18.
 */
@Service
public class StatCadreService extends BaseMapper {

    private static Logger logger = LoggerFactory.getLogger(StatCadreService.class);

    // 导出
    public XSSFWorkbook toXlsx(CadreSearchBean searchBean) throws IOException {

        InputStream is = getClass().getResourceAsStream("/xlsx/cadre/stat_cadre.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);

        renderSheetData(wb, searchBean, null); // 汇总

        MetaClass mcUnitType = CmTag.getMetaClassByCode("mc_unit_type");
        Map<String, MetaClassOption> unitTypeGroupMap = mcUnitType.getOptions();
        for (Map.Entry<String, MetaClassOption> entry : unitTypeGroupMap.entrySet()) {

            MetaClassOption option = entry.getValue();
            searchBean.setUnitTypeGroup(entry.getKey());
            renderSheetData(wb, searchBean, option.getName());
        }

        wb.removeSheetAt(0);

        return wb;
    }

    private void renderSheetData(XSSFWorkbook wb, CadreSearchBean searchBean, String typeName) {

        XSSFSheet sheet = wb.cloneSheet(0, StringUtils.isBlank(typeName) ? "全部" : typeName);
        XSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(true); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE); //纸张
        /*sheet.setMargin(XSSFSheet.BottomMargin,( double ) 0.5 );// 页边距（下）
        sheet.setMargin(XSSFSheet.LeftMargin,( double ) 0.1 );// 页边距（左）
        sheet.setMargin(XSSFSheet.RightMargin,( double ) 0.1 );// 页边距（右）
        sheet.setMargin(XSSFSheet.TopMargin,( double ) 0.5 );// 页边距（上）
        sheet.setHorizontallyCenter(true);//设置打印页面为水平居中
        sheet.setVerticallyCenter(true);//设置打印页面为垂直居中使用POI输出Excel时打印页面的设置*/

        Header header = sheet.getHeader();
        header.setRight("截至" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName()
                        + CadreConstants.CADRE_TYPE_MAP.get(searchBean.cadreType))
                .replace("type", StringUtils.isBlank(typeName) ? "" : "（" + typeName + "）");
        cell.setCellValue(str);

        row = sheet.getRow(3);
        cell = row.getCell(4);
        cell.setCellValue(searchBean.cadreType == CadreConstants.CADRE_TYPE_CJ ? "正处" : "正科");
        cell = row.getCell(6);
        cell.setCellValue(searchBean.cadreType == CadreConstants.CADRE_TYPE_CJ ? "副处" : "副科");

        int rowNum = 4;
        Map<String, List> dataMap = stat(searchBean);
        for (Map.Entry<String, List> entry : dataMap.entrySet()) {

            List rowData = entry.getValue();
            String rowKey = entry.getKey();
            if(StringUtils.equals(rowKey, "row_avgAge")){
                readerCellData2(2, sheet.getRow(rowNum), rowData);
            }else {
                readerCellData(2, sheet.getRow(rowNum), rowData);
            }
            rowNum++;
        }
    }

    // 带百分比列
    private static void readerCellData(int startColumnNum, Row row, List data) {

        NumberFormat nf = NumberFormat.getPercentInstance();
        int size = data.size();
        for (int i = 0; i < size; i++) {

            Cell cell = row.getCell(startColumnNum);
            if (i % 2 == 0) {
                if (data.get(i) != null && StringUtils.isNotBlank(data.get(i).toString()))
                    cell.setCellValue((Integer) data.get(i));
            } else {
                try {
                    if (data.get(i) != null && StringUtils.isNotBlank(data.get(i).toString()))
                        cell.setCellValue(nf.parse((String) data.get(i)).doubleValue());
                } catch (ParseException e) {
                    logger.error("异常", e);
                }

            }
            startColumnNum++;
        }
    }

    // 无百分比列（合并了两列）
    private static void readerCellData2(int startColumnNum, Row row, List data) {

        NumberFormat nf = NumberFormat.getNumberInstance();
        int size = data.size();
        for (int i = 0; i < size; i++) {

            Cell cell = row.getCell(startColumnNum);
            if (data.get(i) != null && StringUtils.isNotBlank(data.get(i).toString())) {
                try {
                    cell.setCellValue(nf.parse((String) data.get(i)).doubleValue());
                } catch (ParseException e) {
                    logger.error("异常", e);
                }
            }
            startColumnNum += 2;
        }
    }

    // 干部统计
    public Map<String, List> stat(CadreSearchBean searchBean) {

        boolean birthToDay = CmTag.getBoolProperty("birthToDay");
        searchBean.setBirthToDay(birthToDay);

        String unitTypeGroup = searchBean.getUnitTypeGroup();
        searchBean.setUnitTypeGroup(null); // 全部干部数量
        int cadreCount = statCadreMapper.countCadre(searchBean);
        int count = cadreCount;

        if (unitTypeGroup != null) {

            searchBean.setUnitTypeGroup(unitTypeGroup);
            // 单位大类的干部总数
            count = statCadreMapper.countCadre(searchBean);
        }

        Map<String, List> result = new LinkedHashMap<>();
        // 第一行：总数
        row1(result, cadreCount, count, searchBean);
        // 第二、三、四行：正处、副处、聘任制（无级别）
        row2_4(result, searchBean);
        // 汉族、少数名族
        row5_6(result, count, searchBean);
        // 中共党员、民主党派
        row7_8(result, count, searchBean);
        // 30岁及以下、...、55岁以上
        row9_15(result, count, searchBean);
        // 平均年龄
        row_avgAge(result, searchBean);
        // 正高(总)、...、中级及以下
        row16_18(result, count, searchBean);
        // 博士、硕士、学士
        row19_21(result, count, searchBean);
        // 专职、双肩挑干部
        row22_23(result, count, searchBean);

        return result;
    }

    public void row1(Map<String, List> result, int cadreCount, int count, CadreSearchBean searchBean) {

        List row = new ArrayList<>();
        row.add(count);
        row.add(percent(count, cadreCount));

        // 行政级别
        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_adminLevel(searchBean);

        int mainCount = 0, viceCount = 0, noneCount = 0;
        for (StatCadreBean bean : adminLevelList) {
            if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                mainCount = bean.getNum();
            } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                viceCount = bean.getNum();
            } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
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
        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_gender(searchBean);
        int male = 0, female = 0;
        for (StatCadreBean bean : genderList) {
            if (bean.getGender() == SystemConstants.GENDER_MALE) {
                male = bean.getNum();
            } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                female = bean.getNum();
            }
        }
        row.add(male);
        row.add(percent(male, count));
        row.add(female);
        row.add(percent(female, count));

        result.put("row1", row);
    }

    public void row2_4(Map<String, List> result, CadreSearchBean searchBean) {

        List row1 = result.get("row1");

        List row2 = new ArrayList<>();
        List row3 = new ArrayList<>();
        List row4 = new ArrayList<>();
        row2.add(row1.get(2));
        row2.add(row1.get(3));
        row2.add(row1.get(2));
        row2.add(percent((int) row1.get(2), row2));
        row2.add(0);
        row2.add(percent(0, 0));
        row2.add(0);
        row2.add(percent(0, 0));

        row3.add(row1.get(4));
        row3.add(row1.get(5));
        row3.add(0);
        row3.add(percent(0, 0));
        row3.add(row1.get(4));
        row3.add(percent((int) row1.get(4), row3));
        row3.add(0);
        row3.add(percent(0, 0));

        row4.add(row1.get(6));
        row4.add(row1.get(7));
        row4.add(0);
        row4.add(percent(0, 0));
        row4.add(0);
        row4.add(percent(0, 0));
        row4.add(row1.get(6));
        row4.add(percent((int) row1.get(6), row4));

        // 行政级别
        List<StatCadreBean> adminLevelGenderList = statCadreMapper.cadre_stat_adminLevel_gender(searchBean);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0; // 男
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0; // 女
        for (StatCadreBean bean : adminLevelGenderList) {
            if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    mainCount1 += bean.getNum();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    mainCount2 += bean.getNum();
                }
            } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    viceCount1 += bean.getNum();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    viceCount2 += bean.getNum();
                }
            } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    noneCount1 += bean.getNum();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
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

    public void row5_6(Map<String, List> result, int count, CadreSearchBean searchBean) {


        List row5 = new ArrayList<>();
        List row6 = new ArrayList<>();

        List<StatCadreBean> nationList = statCadreMapper.cadre_stat_nation(searchBean);

        int nation1 = 0, nation2 = 0;
        for (StatCadreBean bean : nationList) {
            if (StringUtils.contains(bean.getNation(), "汉")) {
                nation1 += bean.getNum();
            } else {
                nation2 += bean.getNum();
            }
        }
        row5.add(nation1);
        row5.add(percent(nation1, count));

        row6.add(nation2);
        row6.add(percent(nation2, count));


        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_nation_adminLevel(searchBean);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        for (StatCadreBean bean : adminLevelList) {
            if (StringUtils.contains(bean.getNation(), "汉")) {
                if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                    mainCount1 += bean.getNum();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                    viceCount1 += bean.getNum();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                    noneCount1 += bean.getNum();
                }
            } else {
                if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                    mainCount2 += bean.getNum();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                    viceCount2 += bean.getNum();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
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

        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_nation_gender(searchBean);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        for (StatCadreBean bean : genderList) {
            if (StringUtils.contains(bean.getNation(), "汉")) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    male1 += bean.getNum();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    female1 += bean.getNum();
                }
            } else {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    male2 += bean.getNum();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
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

    private void row7_8(Map<String, List> result, int count, CadreSearchBean searchBean) {

        List row7 = new ArrayList<>();
        List row8 = new ArrayList<>();

        MetaType metaType = CmTag.getMetaTypeByCode("mt_dp_qz");  //群众
        int crowdId = metaType.getId();

        StatCadreBean totalBean = statCadreMapper.cadre_stat_dp(crowdId, searchBean);
        row8.add(totalBean == null ? "" : totalBean.getNum1());
        row8.add(totalBean == null ? "" : percent(totalBean.getNum1(), count));
        row7.add(totalBean == null ? "" : totalBean.getNum2());
        row7.add(totalBean == null ? "" : percent(totalBean.getNum2(), count));

        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_dp_adminLevel(crowdId, searchBean);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        if (adminLevelList != null) {
            for (StatCadreBean bean : adminLevelList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                    mainCount1 += bean.getNum1();
                    mainCount2 += bean.getNum2();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
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

        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_dp_gender(crowdId, searchBean);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        if (genderList != null) {
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

    private void row9_15(Map<String, List> result, int count, CadreSearchBean searchBean) {

        List row9 = new ArrayList<>();
        List row10 = new ArrayList<>();
        List row11 = new ArrayList<>();
        List row12 = new ArrayList<>();
        List row13 = new ArrayList<>();
        List row14 = new ArrayList<>();
        List row15 = new ArrayList<>();

        StatCadreBean totalBean = statCadreMapper.cadre_stat_age(searchBean);
        row9.add(totalBean == null ? "" : totalBean.getNum1());
        row9.add(totalBean == null ? "" : percent(totalBean.getNum1(), count));

        row10.add(totalBean == null ? "" : totalBean.getNum2());
        row10.add(totalBean == null ? "" : percent(totalBean.getNum2(), count));

        row11.add(totalBean == null ? "" : totalBean.getNum3());
        row11.add(totalBean == null ? "" : percent(totalBean.getNum3(), count));

        row12.add(totalBean == null ? "" : totalBean.getNum4());
        row12.add(totalBean == null ? "" : percent(totalBean.getNum4(), count));

        row13.add(totalBean == null ? "" : totalBean.getNum5());
        row13.add(totalBean == null ? "" : percent(totalBean.getNum5(), count));

        row14.add(totalBean == null ? "" : totalBean.getNum6());
        row14.add(totalBean == null ? "" : percent(totalBean.getNum6(), count));

        row15.add(totalBean == null ? "" : totalBean.getNum7());
        row15.add(totalBean == null ? "" : percent(totalBean.getNum7(), count));

        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_age_adminLevel(searchBean);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        int mainCount3 = 0, viceCount3 = 0, noneCount3 = 0;
        int mainCount4 = 0, viceCount4 = 0, noneCount4 = 0;
        int mainCount5 = 0, viceCount5 = 0, noneCount5 = 0;
        int mainCount6 = 0, viceCount6 = 0, noneCount6 = 0;
        int mainCount7 = 0, viceCount7 = 0, noneCount7 = 0;
        if (adminLevelList != null) {
            for (StatCadreBean bean : adminLevelList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                    mainCount1 += bean.getNum1();
                    mainCount2 += bean.getNum2();
                    mainCount3 += bean.getNum3();
                    mainCount4 += bean.getNum4();
                    mainCount5 += bean.getNum5();
                    mainCount6 += bean.getNum6();
                    mainCount7 += bean.getNum7();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
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

        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_age_gender(searchBean);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        int male3 = 0, female3 = 0;
        int male4 = 0, female4 = 0;
        int male5 = 0, female5 = 0;
        int male6 = 0, female6 = 0;
        int male7 = 0, female7 = 0;
        if (genderList != null) {
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

    // 平均年龄
    private void row_avgAge(Map<String, List> result, CadreSearchBean searchBean) {

        List row = new ArrayList<>();

        StatCadreBean totalBean = statCadreMapper.cadre_avg_age(searchBean);
        row.add((totalBean == null || totalBean.getVal()==null) ? ""
                : NumberUtils.formatDoubleFixed(totalBean.getVal().doubleValue(), 1));

        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_avg_age_adminLevel(searchBean);

        BigDecimal mainAge=null, viceAge=null, noneAge=null;
        if (adminLevelList != null) {
            for (StatCadreBean bean : adminLevelList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                    mainAge = bean.getVal();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                    viceAge = bean.getVal();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                    noneAge = bean.getVal();
                }
            }
        }
        row.add(mainAge==null?"":NumberUtils.formatDoubleFixed(mainAge.doubleValue(), 1));
        row.add(viceAge==null?"":NumberUtils.formatDoubleFixed(viceAge.doubleValue(), 1));
        row.add(noneAge==null?"":NumberUtils.formatDoubleFixed(noneAge.doubleValue(), 1));

        List<StatCadreBean> genderList = statCadreMapper.cadre_avg_age_gender(searchBean);
        BigDecimal maleAge=null, femaleAge=null;
        if (genderList != null) {
            for (StatCadreBean bean : genderList) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    maleAge = bean.getVal();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    femaleAge = bean.getVal();
                }
            }
        }
        row.add(maleAge==null?"":NumberUtils.formatDoubleFixed(maleAge.doubleValue(), 1));
        row.add(femaleAge==null?"":NumberUtils.formatDoubleFixed(femaleAge.doubleValue(), 1));

        result.put("row_avgAge", row);
    }

    private void row16_18(Map<String, List> result, int count, CadreSearchBean searchBean) {

        List row16 = new ArrayList<>();
        List row17 = new ArrayList<>();
        List row18 = new ArrayList<>();

        StatCadreBean totalBean = statCadreMapper.cadre_stat_post(searchBean);
        row16.add(totalBean == null ? "" : totalBean.getNum1());
        row16.add(totalBean == null ? "" : percent(totalBean.getNum1(), count));

        row17.add(totalBean == null ? "" : totalBean.getNum2());
        row17.add(totalBean == null ? "" : percent(totalBean.getNum2(), count));

        row18.add(totalBean == null ? "" : totalBean.getNum3());
        row18.add(totalBean == null ? "" : percent(totalBean.getNum3(), count));

        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_post_adminLevel(searchBean);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        int mainCount3 = 0, viceCount3 = 0, noneCount3 = 0;
        if (adminLevelList != null) {
            for (StatCadreBean bean : adminLevelList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                    mainCount1 += bean.getNum1();
                    mainCount2 += bean.getNum2();
                    mainCount3 += bean.getNum3();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                    viceCount1 += bean.getNum1();
                    viceCount2 += bean.getNum2();
                    viceCount3 += bean.getNum3();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                    noneCount1 += bean.getNum1();
                    noneCount2 += bean.getNum2();
                    noneCount3 += bean.getNum3();
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

        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_post_gender(searchBean);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        int male3 = 0, female3 = 0;
        if (genderList != null) {
            for (StatCadreBean bean : genderList) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    male1 += bean.getNum1();
                    male2 += bean.getNum2();
                    male3 += bean.getNum3();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    female1 += bean.getNum1();
                    female2 += bean.getNum2();
                    female3 += bean.getNum3();
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

        result.put("row16", row16);
        result.put("row17", row17);
        result.put("row18", row18);
    }

    private void row19_21(Map<String, List> result, int count, CadreSearchBean searchBean) {

        List row19 = new ArrayList<>();
        List row20 = new ArrayList<>();
        List row21 = new ArrayList<>();

        int bs = 0, ss = 0, xs = 0;
        List<StatCadreBean> eduList = statCadreMapper.cadre_stat_degree(searchBean);
        if (eduList != null) {
            for (StatCadreBean bean : eduList) {
                if (bean.getDegreeType() == null) continue;
                if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_BS) {
                    bs += bean.getNum();
                } else if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_SS) {
                    ss += bean.getNum();
                } else if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_XS) {
                    xs += bean.getNum();
                }
            }
        }
        row19.add(bs);
        row19.add(percent(bs, count));

        row20.add(ss);
        row20.add(percent(ss, count));

        row21.add(xs);
        row21.add(percent(xs, count));

        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_degree_adminLevel(searchBean);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        int mainCount3 = 0, viceCount3 = 0, noneCount3 = 0;
        if (adminLevelList != null) {
            for (StatCadreBean bean : adminLevelList) {

                if (bean.getDegreeType() == null) continue;
                if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_BS) {
                    if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                        mainCount1 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                        viceCount1 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                        noneCount1 += bean.getNum();
                    }
                } else if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_SS) {
                    if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                        mainCount2 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                        viceCount2 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                        noneCount2 += bean.getNum();
                    }
                } else if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_XS) {
                    if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                        mainCount3 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                        viceCount3 += bean.getNum();
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                        noneCount3 += bean.getNum();
                    }
                }
            }
        }
        row19.add(mainCount1);
        row19.add(percent(mainCount1, row19));
        row19.add(viceCount1);
        row19.add(percent(viceCount1, row19));
        row19.add(noneCount1);
        row19.add(percent(noneCount1, row19));

        row20.add(mainCount2);
        row20.add(percent(mainCount2, row20));
        row20.add(viceCount2);
        row20.add(percent(viceCount2, row20));
        row20.add(noneCount2);
        row20.add(percent(noneCount2, row20));

        row21.add(mainCount3);
        row21.add(percent(mainCount3, row21));
        row21.add(viceCount3);
        row21.add(percent(viceCount3, row21));
        row21.add(noneCount3);
        row21.add(percent(noneCount3, row21));

        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_degree_gender(searchBean);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        int male3 = 0, female3 = 0;
        if (genderList != null) {
            for (StatCadreBean bean : genderList) {

                if (bean.getDegreeType() == null) continue;
                if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_BS) {
                    if (bean.getGender() == SystemConstants.GENDER_MALE) {
                        male1 += bean.getNum();
                    } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                        female1 += bean.getNum();
                    }
                } else if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_SS) {
                    if (bean.getGender() == SystemConstants.GENDER_MALE) {
                        male2 += bean.getNum();
                    } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                        female2 += bean.getNum();
                    }
                } else if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_XS) {
                    if (bean.getGender() == SystemConstants.GENDER_MALE) {
                        male3 += bean.getNum();
                    } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                        female3 += bean.getNum();
                    }
                }
            }
        }
        row19.add(male1);
        row19.add(percent(male1, row19));
        row19.add(female1);
        row19.add(percent(female1, row19));

        row20.add(male2);
        row20.add(percent(male2, row20));
        row20.add(female2);
        row20.add(percent(female2, row20));

        row21.add(male3);
        row21.add(percent(male3, row21));
        row21.add(female3);
        row21.add(percent(female3, row21));

        result.put("row19", row19);
        result.put("row20", row20);
        result.put("row21", row21);
    }

    private void row22_23(Map<String, List> result, int count, CadreSearchBean searchBean) {

        StatCadreBean doubleBean = statCadreMapper.cadre_stat_double(searchBean);
        List row22 = new ArrayList<>();
        List row23 = new ArrayList<>();
        row22.add(doubleBean == null ? "" : doubleBean.getNum1());
        row22.add(doubleBean == null ? "" : percent(doubleBean.getNum1(), count));
        row23.add(doubleBean == null ? "" : doubleBean.getNum2());
        row23.add(doubleBean == null ? "" : percent(doubleBean.getNum2(), count));

        // 行政级别
        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_double_adminLevel(searchBean);

        int mainCount1 = 0, viceCount1 = 0, noneCount1 = 0;
        int mainCount2 = 0, viceCount2 = 0, noneCount2 = 0;
        if (adminLevelList != null) {
            for (StatCadreBean bean : adminLevelList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                    mainCount1 = bean.getNum1();
                    mainCount2 = bean.getNum2();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                    viceCount1 = bean.getNum1();
                    viceCount2 = bean.getNum2();
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                    noneCount1 = bean.getNum1();
                    noneCount2 = bean.getNum2();
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

        // 男女
        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_double_gender(searchBean);
        int male1 = 0, female1 = 0;
        int male2 = 0, female2 = 0;
        if (genderList != null) {
            for (StatCadreBean bean : genderList) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    male1 = bean.getNum1();
                    male2 = bean.getNum2();
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    female1 = bean.getNum1();
                    female2 = bean.getNum2();
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

        result.put("row22", row22);
        result.put("row23", row23);
    }

    public Map<Integer, List> eduRowMap(CadreSearchBean searchBean) {

        int count = statCadreMapper.countCadre(searchBean);

        List<StatCadreBean> statCadreBeans = statCadreMapper.cadre_stat_edu(searchBean);

        Map<Integer, MetaType> eduTypes = CmTag.getMetaTypes("mc_edu");
        Map<Integer, List> rowMap = new TreeMap<Integer, List>(
                new Comparator<Integer>() {
                    public int compare(Integer obj1, Integer obj2) {
                        // 降序排序
                        return eduTypes.get(obj2).getSortOrder().compareTo(eduTypes.get(obj1).getSortOrder());
                    }
                });
        for (StatCadreBean statCadreBean : statCadreBeans) {

            int num = statCadreBean.getNum();
            List row = new ArrayList();
            row.add(num);
            row.add(percent(num, count));
            row.add(0);
            row.add(0);
            row.add(0);
            row.add(0);
            row.add(0);
            row.add(0);
            row.add(0);
            row.add(0);
            row.add(0);
            row.add(0);

            rowMap.put(statCadreBean.getEduId(), row);
        }

        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_edu_adminLevel(searchBean);
        for (StatCadreBean bean : adminLevelList) {

            int eduId = bean.getEduId();
            List row = rowMap.get(eduId);

            if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBean.getCadreType()))) {
                int mainCount = bean.getNum();
                row.set(2, mainCount);
                row.set(3, percent(mainCount, count));
            } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBean.getCadreType()))) {
                int viceCount = bean.getNum();
                row.set(4, viceCount);
                row.set(5, percent(viceCount, count));
            } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                int noneCount = bean.getNum();
                row.set(6, noneCount);
                row.set(7, percent(noneCount, count));
            }
        }

        List<StatCadreBean> genderList = statCadreMapper.cadre_stat_edu_gender(searchBean);
        for (StatCadreBean bean : genderList) {

            int eduId = bean.getEduId();
            List row = rowMap.get(eduId);

            if (bean.getGender() == SystemConstants.GENDER_MALE) {
                int male = bean.getNum();
                row.set(8, male);
                row.set(9, percent(male, count));

            } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                int female = bean.getNum();
                row.set(10, female);
                row.set(11, percent(female, count));
            }
        }

        return rowMap;
    }

    public static String percent(Integer count, List row) {

        if (row == null || row.size() == 0 || !(row.get(0) instanceof Integer)) return "0.0%";
        return percent(count, (int) row.get(0));
    }

    public static String percent(Integer count, Integer total) {

        if (count == null || total == null || count <= 0 || total <= 0) return "0.0%";

        return NumberUtils.formatDoubleFixed((count * 100.0) / total, 1) + "%";
    }

    //全部类型
    public List<CadreView> allCadreList(CadreSearchBean searchBean, Integer secondNum) {

        List<CadreView> cadreViewList = statCadreMapper.allCadreList(searchBean);
        return groupByCadre(cadreViewList, secondNum);
    }

    ;

    //行政级别
    public List<CadreView> adminLevelList(CadreSearchBean searchBean, Integer firstTypeNum, Integer secondNum) {
        String adminLevelCode = null;
        if (firstTypeNum == 1) //正处
            adminLevelCode = searchBean.cadreType == 1 ? "mt_admin_level_main" : "mt_admin_level_main_kj";
        if (firstTypeNum == 2)//副处
            adminLevelCode = searchBean.cadreType == 1 ? "mt_admin_level_vice" : "mt_admin_level_vice_kj";
        if (firstTypeNum == 3)//无行政级别
            adminLevelCode = "mt_admin_level_none";

        List<CadreView> cadreViewList = statCadreMapper.adminLevelList(searchBean, adminLevelCode);
        return groupByCadre(cadreViewList, secondNum);
    }

    //民族
    public List<CadreView> nationList(CadreSearchBean searchBean, Integer firstTypeNum, Integer secondNum) {

        boolean isHan = true;// 汉族

        if (firstTypeNum == 2)//少数民族
            isHan = false;
        List<CadreView> cadreViewList = statCadreMapper.nationList(searchBean, isHan);
        return groupByCadre(cadreViewList, secondNum);
    }

    //政治面貌
    public List<CadreView> psList(CadreSearchBean searchBean, Integer firstTypeNum, Integer secondNum) {
        Boolean isOw = null;
        Integer dpTypeId = null;
        if (firstTypeNum == 1)
            isOw = true;
        if (firstTypeNum == 2)
            dpTypeId = 1;

        MetaType metaType = CmTag.getMetaTypeByCode("mt_dp_qz");  //群众
        int crowdId = metaType.getId();

        List<CadreView> cadreViewList = statCadreMapper.psList(searchBean, isOw, dpTypeId, crowdId);
        return groupByCadre(cadreViewList, secondNum);
    }

    //年龄
    public List<CadreView> ageList(CadreSearchBean searchBean, Integer firstTypeNum, Integer secondNum) {

        Integer startAge = null;
        Integer endAge = null;
        switch (firstTypeNum) {
            case 1://30岁及以下
                endAge = 30;
                break;
            case 2://31-35岁
                startAge = 31;
                endAge = 35;
                break;
            case 3://36-40岁
                startAge = 36;
                endAge = 40;
                break;
            case 4://41-45岁
                startAge = 41;
                endAge = 45;
                break;
            case 5://46-50岁
                startAge = 46;
                endAge = 50;
                break;
            case 6://51-55岁
                startAge = 51;
                endAge = 55;
                break;
            case 7://56及以上
                startAge = 56;
        }

        boolean birthToDay = CmTag.getBoolProperty("birthToDay");
        searchBean.setBirthToDay(birthToDay);

        List<CadreView> cadreViewList = statCadreMapper.ageList(searchBean, startAge, endAge);
        return groupByCadre(cadreViewList, secondNum);
    }

    //职称
    public List<CadreView> postLevelList(CadreSearchBean searchBean, Integer firstTypeNum, Integer secondNum) {

        String postLevel = null;
        Boolean isRegexp = null;
        switch (firstTypeNum) {
            case 1:
                postLevel = "%正高%";
                isRegexp = false;
                break;
            case 2:
                postLevel = "%副高%";
                isRegexp = false;
                break;
            case 3:
                postLevel = "(中|初)级";
                isRegexp = true;
        }

        List<CadreView> cadreViewList = statCadreMapper.postLevelList(searchBean, postLevel, isRegexp);
        return groupByCadre(cadreViewList, secondNum);
    }

    //学位
    public List<CadreView> degreeTypeList(CadreSearchBean searchBean, Integer firstTypeNum, Integer secondNum) {

        List<CadreView> cadreViewList = statCadreMapper.degreeList(searchBean, firstTypeNum);
        return groupByCadre(cadreViewList, secondNum);
    }

    //专职干部
    public List<CadreView> isDoubleList(CadreSearchBean searchBean, Integer secondNum, boolean isDouble) {

        List<CadreView> cadreViewList = statCadreMapper.isDoubleList(searchBean, isDouble);
        return groupByCadre(cadreViewList, secondNum);
    }

    //学历
    public List<CadreView> educationList(CadreSearchBean searchBean, Integer firstTypeNum, Integer secondNum) {

        List<CadreView> cadreViewList = statCadreMapper.educationList(searchBean, firstTypeNum);
        return groupByCadre(cadreViewList, secondNum);
    }

    //判断第二个参数的类型
    public List<CadreView> groupByCadre(List<CadreView> cadreViewList, Integer secondNum) {
        List<CadreView> cadreViews = new ArrayList<>();
        for (CadreView cadreView : cadreViewList) {

            if (secondNum == 1) {//总体
                cadreViews.add(cadreView);
            } else if (secondNum == 2) {//行政级别 正处

                if (StringUtils.equals(cadreView.getAdminLevelCode(), "mt_admin_level_main"))
                    cadreViews.add(cadreView);
                else if (StringUtils.equals(cadreView.getAdminLevelCode(), "mt_admin_level_main_kj"))
                    cadreViews.add(cadreView);
                continue;
            } else if (secondNum == 3) {//行政级别 副处

                if (StringUtils.equals(cadreView.getAdminLevelCode(), "mt_admin_level_vice"))
                    cadreViews.add(cadreView);
                else if (StringUtils.equals(cadreView.getAdminLevelCode(), "mt_admin_level_vice_kj"))
                    cadreViews.add(cadreView);
                continue;
            } else if (secondNum == 4) {//行政级别 无级别

                if (StringUtils.equals(cadreView.getAdminLevelCode(), "mt_admin_level_none"))
                    cadreViews.add(cadreView);
                continue;
            } else if (secondNum == 5) {//性别 男

                if (cadreView.getGender() != null && cadreView.getGender() == SystemConstants.GENDER_MALE)
                    cadreViews.add(cadreView);
                continue;
            } else if (secondNum == 6) {//性别 女

                if (cadreView.getGender() != null && cadreView.getGender() == SystemConstants.GENDER_FEMALE)
                    cadreViews.add(cadreView);
                continue;
            }
        }
        return cadreViews;
    }
}
