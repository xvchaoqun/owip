package service.analysis;

import bean.StatByteBean;
import domain.cet.CetRecord;
import domain.party.Party;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.HtmlUtils;
import service.BaseMapper;
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.NumberUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatOwInfoService extends BaseMapper {

    public XSSFWorkbook statOnInfoExport(ModelMap modelMap, BigDecimal masterPercent, BigDecimal doctorPercent) {
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


    public XSSFWorkbook statOnPartyInfoExport(ModelMap modelMap, List<Party> partyNames) {
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
//            cell.setCellStyle(style2);

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

}
