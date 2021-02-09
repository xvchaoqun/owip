package service.analysis;

import bean.StatByteBean;
import domain.party.Party;
import domain.sys.StudentInfoExample;
import domain.sys.SysUserExample;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import persistence.analysis.StatOwInfoMapper;
import service.BaseMapper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class StatOwInfoService extends BaseMapper {
    @Autowired
    private StatOwInfoMapper statOwInfoMapper;

    public static BigDecimal calculateDivide(Integer number1, Integer number2) {
        BigDecimal num1 = new BigDecimal(number1);
        BigDecimal num2 = new BigDecimal(number2);
        BigDecimal result = (num1.compareTo(BigDecimal.ZERO) == 0 || num2.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : num1.divide(num2,4,BigDecimal.ROUND_HALF_UP));
        return result;
    }

    public Map encapsulationData(List<StatByteBean> man, List<StatByteBean> woman, List<StatByteBean> isLevelMan, List<StatByteBean> isLevelWoman,
                                 List<StatByteBean> DeputyLevelMan, List<StatByteBean> DeputyLevelWoman, List<StatByteBean> formation,
                                 List<StatByteBean> prepared, List<StatByteBean> isLevelFormation, List<StatByteBean> DeputyLevelFormation,
                                 List<StatByteBean> isLevelPrepared, List<StatByteBean> deputyLevelPrepared, List<StatByteBean> applyToJoin,
                                 List<StatByteBean> applyToJoinPass, List<StatByteBean> activity, List<StatByteBean> develop,
                                 List<StatByteBean> isLevelJoin, List<StatByteBean> isLevelJoinPass, List<StatByteBean> isLevelActivity,
                                 List<StatByteBean> isLevelDevelop, List<StatByteBean> deputyLevelJoin, List<StatByteBean> deputyLevelJoinPass,
                                 List<StatByteBean> deputyLevelActivity, List<StatByteBean> deputyLevelDevelop, DecimalFormat df) {
        Map dataMap = new HashedMap();
        for (StatByteBean obj: man) {
            if (obj.getGroupBy() != null) {
                // 专任教师男
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("speciallyMan", num != null ? obj.getNum() : 0);
                }
                // 校编教工男
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_FORMATION) {
                    Integer num = obj.getNum();
                    dataMap.put("formationMan", num != null ? obj.getNum() : 0);
                }

            }
        }
        if (dataMap.get("speciallyMan") == null) {
            dataMap.put("speciallyMan", 0);
        }
        if (dataMap.get("formationMan") == null) {
            dataMap.put("formationMan", 0);
        }

        for (StatByteBean obj: woman) {
            if (obj.getGroupBy() != null) {
                // 专任教师女
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("speciallyWoman", num != null ? obj.getNum() : 0);
                }
                // 校编教工女
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_FORMATION) {
                    Integer num = obj.getNum();
                    dataMap.put("formationWoman", num != null ? obj.getNum() : 0);
                }

            }
        }
        if (dataMap.get("speciallyWoman") == null) {
            dataMap.put("speciallyWoman", 0);
        }
        if (dataMap.get("formationWoman") == null) {
            dataMap.put("formationWoman", 0);
        }
        dataMap.put("speciallyManAndWoman", Integer.valueOf(dataMap.get("speciallyMan").toString()) + Integer.valueOf(dataMap.get("speciallyWoman").toString()));
        dataMap.put("formationManAndWoman", Integer.valueOf(dataMap.get("formationMan").toString()) + Integer.valueOf(dataMap.get("formationWoman").toString()));

        for (StatByteBean obj: isLevelMan) {
            if (obj.getGroupBy() != null) {
                // 正高男
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("isLevelMan", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("isLevelMan") == null) {
            dataMap.put("isLevelMan", 0);
        }
        for (StatByteBean obj: isLevelWoman) {
            if (obj.getGroupBy() != null) {
                // 正高女
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("isLevelWoman", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("isLevelWoman") == null) {
            dataMap.put("isLevelWoman", 0);
        }
        for (StatByteBean obj: DeputyLevelMan) {
            if (obj.getGroupBy() != null) {
                // 副高男
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("deputyLevelMan", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("deputyLevelMan") == null) {
            dataMap.put("deputyLevelMan", 0);
        }
        for (StatByteBean obj: DeputyLevelWoman) {
            if (obj.getGroupBy() != null) {
                // 副高女
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("deputyLevelWoman", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("deputyLevelWoman") == null) {
            dataMap.put("deputyLevelWoman", 0);
        }
        dataMap.put("isLevel", Integer.valueOf(dataMap.get("isLevelMan").toString()) + Integer.valueOf(dataMap.get("isLevelWoman").toString()));
        dataMap.put("deputyLevel", Integer.valueOf(dataMap.get("deputyLevelMan").toString()) + Integer.valueOf(dataMap.get("deputyLevelWoman").toString()));
        //中级及以下合计
        Integer isLevelTotal = Integer.valueOf(dataMap.get("speciallyManAndWoman").toString()) - Integer.valueOf(dataMap.get("isLevel").toString()) - Integer.valueOf(dataMap.get("deputyLevel").toString());

        //中级及以下男
        Integer followingMan = Integer.valueOf(dataMap.get("speciallyMan").toString()) - Integer.valueOf(dataMap.get("isLevelMan").toString()) - Integer.valueOf(dataMap.get("deputyLevelMan").toString());
        //中级及以下女
        Integer followingWoman = Integer.valueOf(dataMap.get("speciallyWoman").toString()) - Integer.valueOf(dataMap.get("isLevelWoman").toString()) - Integer.valueOf(dataMap.get("deputyLevelWoman").toString());
        dataMap.put("isLevelTotal", isLevelTotal);
        dataMap.put("followingMan", followingMan);
        dataMap.put("followingWoman", followingWoman);

        for (StatByteBean obj: formation) {
            if (obj.getGroupBy() != null) {
                // 校编教工正式党员
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_FORMATION) {
                    Integer num = obj.getNum();
                    dataMap.put("schoolFormation", num != null ? obj.getNum() : 0);
                }
                // 专任教师正式党员
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("speciallyFormation", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("schoolFormation") == null) {
            dataMap.put("schoolFormation", 0);
        }
        if (dataMap.get("speciallyFormation") == null) {
            dataMap.put("speciallyFormation", 0);
        }

        for (StatByteBean obj: prepared) {
            if (obj.getGroupBy() != null) {
                // 校编教工预备党员
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_FORMATION) {
                    Integer num = obj.getNum();
                    dataMap.put("schoolPrepared", num != null ? obj.getNum() : 0);
                }
                // 专任教师预备党员
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("speciallyPrepared", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("schoolPrepared") == null) {
            dataMap.put("schoolPrepared", 0);
        }
        if (dataMap.get("speciallyPrepared") == null) {
            dataMap.put("speciallyPrepared", 0);
        }
        Integer schoolPartyTotal = Integer.valueOf(dataMap.get("schoolFormation").toString()) + Integer.valueOf(dataMap.get("schoolPrepared").toString());
        Integer speciallyPartyTotal = Integer.valueOf(dataMap.get("speciallyFormation").toString()) + Integer.valueOf(dataMap.get("speciallyPrepared").toString());
        dataMap.put("schoolPartyTotal", schoolPartyTotal);
        dataMap.put("speciallyPartyTotal", speciallyPartyTotal);

        for (StatByteBean obj: isLevelFormation) {
            if (obj.getGroupBy() != null) {
                // 专任教师正式党员（正高级）
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("speciallyIsLevelFormation", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("speciallyIsLevelFormation") == null) {
            dataMap.put("speciallyIsLevelFormation", 0);
        }
        for (StatByteBean obj: DeputyLevelFormation) {
            if (obj.getGroupBy() != null) {
                // 专任教师正式党员（副高级）
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("speciallyDeputyLevel", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("speciallyDeputyLevel") == null) {
            dataMap.put("speciallyDeputyLevel", 0);
        }
        for (StatByteBean obj: isLevelPrepared) {
            if (obj.getGroupBy() != null) {
                // 专任教师预备党员（正高级）
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("preparedSpeciallyIsLevelFormation", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("preparedSpeciallyIsLevelFormation") == null) {
            dataMap.put("preparedSpeciallyIsLevelFormation", 0);
        }
        for (StatByteBean obj: deputyLevelPrepared) {
            if (obj.getGroupBy() != null) {
                // 专任教师预备党员（副高级）
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("preparedSpeciallyDeputyLevel", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("preparedSpeciallyDeputyLevel") == null) {
            dataMap.put("preparedSpeciallyDeputyLevel", 0);
        }
        //正高合计
        Integer isLevelPartyTotal = Integer.valueOf(dataMap.get("speciallyIsLevelFormation").toString()) + Integer.valueOf(dataMap.get("preparedSpeciallyIsLevelFormation").toString());
        //副高合计
        Integer deputyLevelPartyTotal = Integer.valueOf(dataMap.get("speciallyDeputyLevel").toString()) + Integer.valueOf(dataMap.get("preparedSpeciallyDeputyLevel").toString());
        dataMap.put("isLevelPartyTotal", isLevelPartyTotal);
        dataMap.put("deputyLevelPartyTotal", deputyLevelPartyTotal);
        //中级及以下正式党员
        Integer intermediateFormation = Integer.valueOf(dataMap.get("speciallyFormation").toString()) - Integer.valueOf(dataMap.get("speciallyIsLevelFormation").toString()) - Integer.valueOf(dataMap.get("speciallyDeputyLevel").toString());
        //中级及以下预备党员
        Integer intermediatePrepared = Integer.valueOf(dataMap.get("speciallyPrepared").toString()) - Integer.valueOf(dataMap.get("preparedSpeciallyIsLevelFormation").toString()) - Integer.valueOf(dataMap.get("preparedSpeciallyDeputyLevel").toString());
        //中级及以下合计
        Integer intermediateTotal = intermediateFormation + intermediatePrepared;
        dataMap.put("intermediateFormation", intermediateFormation);
        dataMap.put("intermediatePrepared", intermediatePrepared);
        dataMap.put("intermediateTotal", intermediateTotal);
        //校编教工占比
        BigDecimal percent = calculateDivide(Integer.valueOf(dataMap.get("schoolPartyTotal").toString()), Integer.valueOf(dataMap.get("formationManAndWoman").toString()));
        dataMap.put("schoolPercent", df.format(percent.doubleValue() * 100) + "%");
        //专任教师占比
        percent = calculateDivide(Integer.valueOf(dataMap.get("speciallyPartyTotal").toString()), Integer.valueOf(dataMap.get("speciallyManAndWoman").toString()));
        dataMap.put("speciallyPercent", df.format(percent.doubleValue() * 100) + "%");
        //正高级占比
        percent = calculateDivide(Integer.valueOf(dataMap.get("isLevelPartyTotal").toString()), Integer.valueOf(dataMap.get("isLevel").toString()));
        dataMap.put("isLevelPercent", df.format(percent.doubleValue() * 100) + "%");
        //副高级占比
        percent = calculateDivide(Integer.valueOf(dataMap.get("deputyLevelPartyTotal").toString()), Integer.valueOf(dataMap.get("deputyLevel").toString()));
        dataMap.put("deputyLevelPercent", df.format(percent.doubleValue() * 100) + "%");
        //中级及以下占比
        percent = calculateDivide(Integer.valueOf(dataMap.get("intermediateTotal").toString()), Integer.valueOf(dataMap.get("isLevelTotal").toString()));
        dataMap.put("intermediatePercent", df.format(percent.doubleValue() * 100) + "%");

        Integer applySchoolCount = 0, applySpeciallyCount = 0;
        for (StatByteBean obj: applyToJoin) {
            if (obj.getGroupBy() != null) {
                // 校编教工入党申请人
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_FORMATION) {
                    Integer num = obj.getNum();
                    applySchoolCount += num;
                }
                // 专任教师入党申请人
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    applySpeciallyCount += num;
                }
            }
        }
        for (StatByteBean obj: applyToJoinPass) {
            if (obj.getGroupBy() != null) {
                // 校编教工入党通过
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_FORMATION) {
                    Integer num = obj.getNum();
                    applySchoolCount += num;
                }
                // 专任教师入党通过
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    applySpeciallyCount += num;
                }
            }
        }
        dataMap.put("applySchool", applySchoolCount);
        dataMap.put("applySpecially", applySpeciallyCount);

        for (StatByteBean obj: activity) {
            if (obj.getGroupBy() != null) {
                // 校编教工入党积极分子
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_FORMATION) {
                    Integer num = obj.getNum();
                    dataMap.put("activitySchool", num != null ? obj.getNum() : 0);
                }
                // 专任教师入党积极分子
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("activitySpecially", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("activitySchool") == null) {
            dataMap.put("activitySchool", 0);
        }
        if (dataMap.get("activitySpecially") == null) {
            dataMap.put("activitySpecially", 0);
        }

        for (StatByteBean obj: develop) {
            if (obj.getGroupBy() != null) {
                // 校编教工发展对象
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_FORMATION) {
                    Integer num = obj.getNum();
                    dataMap.put("developSchool", num != null ? obj.getNum() : 0);
                }
                // 专任教师发展对象
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("developSpecially", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("developSchool") == null) {
            dataMap.put("developSchool", 0);
        }
        if (dataMap.get("developSpecially") == null) {
            dataMap.put("developSpecially", 0);
        }

        applySpeciallyCount = 0;
        for (StatByteBean obj: isLevelJoin) {
            if (obj.getGroupBy() != null) {
                // 专任教师正高级入党申请人
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    applySpeciallyCount += num;
                }
            }
        }
        for (StatByteBean obj: isLevelJoinPass) {
            if (obj.getGroupBy() != null) {
                // 专任教师入党通过
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    applySpeciallyCount += num;
                }
            }
        }
        dataMap.put("applySpeciallyIsLevel", applySpeciallyCount);

        for (StatByteBean obj: isLevelActivity) {
            if (obj.getGroupBy() != null) {
                // 专任教师正高级入党积极分子
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("activitySpeciallyIsLevel", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("activitySpeciallyIsLevel") == null) {
            dataMap.put("activitySpeciallyIsLevel", 0);
        }

        for (StatByteBean obj: isLevelDevelop) {
            if (obj.getGroupBy() != null) {
                // 专任教师正高级发展对象
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("developSpeciallyIsLevel", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("developSpeciallyIsLevel") == null) {
            dataMap.put("developSpeciallyIsLevel", 0);
        }

        applySpeciallyCount = 0;
        for (StatByteBean obj: deputyLevelJoin) {
            if (obj.getGroupBy() != null) {
                // 专任教师副高级入党申请人
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    applySpeciallyCount += num;
                }
            }
        }
        for (StatByteBean obj: deputyLevelJoinPass) {
            if (obj.getGroupBy() != null) {
                // 专任教师入党通过
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    applySpeciallyCount += num;
                }
            }
        }
        dataMap.put("applySpeciallyDeputyLevel", applySpeciallyCount);

        for (StatByteBean obj: deputyLevelActivity) {
            if (obj.getGroupBy() != null) {
                // 专任教师副高级入党积极分子
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("activitySpeciallyDeputyLevel", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("activitySpeciallyDeputyLevel") == null) {
            dataMap.put("activitySpeciallyDeputyLevel", 0);
        }

        for (StatByteBean obj: deputyLevelDevelop) {
            if (obj.getGroupBy() != null) {
                // 专任教师副高级发展对象
                if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                    Integer num = obj.getNum();
                    dataMap.put("developSpeciallyDeputyLevel", num != null ? obj.getNum() : 0);
                }
            }
        }
        if (dataMap.get("developSpeciallyDeputyLevel") == null) {
            dataMap.put("developSpeciallyDeputyLevel", 0);
        }

        // 专任教师中级及以下入党申请人
        Integer mediumApply = Integer.valueOf(dataMap.get("applySpecially").toString()) - Integer.valueOf(dataMap.get("applySpeciallyIsLevel").toString()) - Integer.valueOf(dataMap.get("applySpeciallyDeputyLevel").toString());
        // 专任教师中级及以下入党积极分子
        Integer mediumActivity = Integer.valueOf(dataMap.get("activitySpecially").toString()) - Integer.valueOf(dataMap.get("activitySpeciallyIsLevel").toString()) - Integer.valueOf(dataMap.get("activitySpeciallyDeputyLevel").toString());
        // 专任教师中级及以下发展对象
        Integer mediumDevelop = Integer.valueOf(dataMap.get("developSpecially").toString()) - Integer.valueOf(dataMap.get("developSpeciallyIsLevel").toString()) - Integer.valueOf(dataMap.get("developSpeciallyDeputyLevel").toString());
        dataMap.put("mediumApply", mediumApply);
        dataMap.put("mediumActivity", mediumActivity);
        dataMap.put("mediumDevelop", mediumDevelop);

        return dataMap;
    }

    public XSSFWorkbook statOwYjsInfoExport(ModelMap modelMap) {
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

    public XSSFWorkbook statPartyYjsInfoExport(ModelMap modelMap) {
        InputStream is = null;
        try {
            int startRow = 4;
            int startCol = 0;
            is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/analysis/stat_party_yjs_info.xlsx"));
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

    public XSSFWorkbook statOwJzgInfoExport(ModelMap modelMap) {
        InputStream is = null;
        try {
            int startRow = 4;
            int startCol = 1;
            is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/analysis/stat_ow_jzg_info.xlsx"));
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

            //校编教工总数合计
            row = sheet.getRow(startRow);
            cell = row.getCell(startCol);
            cell.setCellValue(modelMap.get("formationManAndWoman") == null ? 0 : Integer.valueOf(modelMap.get("formationManAndWoman").toString()));
            //校编教工男
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("formationMan") == null ? 0 : Integer.valueOf(modelMap.get("formationMan").toString()));
            //校编教工女
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("formationWoman") == null ? 0 : Integer.valueOf(modelMap.get("formationWoman").toString()));
            //校编教工正式党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("schoolFormation") == null ? 0 : Integer.valueOf(modelMap.get("schoolFormation").toString()));
            //校编教工预备党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("schoolPrepared") == null ? 0 : Integer.valueOf(modelMap.get("schoolPrepared").toString()));
            //校编教工党员合计
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("schoolPartyTotal") == null ? 0 : Integer.valueOf(modelMap.get("schoolPartyTotal").toString()));
            //校编教工党员占比
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("schoolPercent").toString());
            //校编教工入党申请人
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("applySchool") == null ? 0 : Integer.valueOf(modelMap.get("applySchool").toString()));
            //校编教工入党积极分子
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("activitySchool") == null ? 0 : Integer.valueOf(modelMap.get("activitySchool").toString()));
            //校编教工发展对象
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("developSchool") == null ? 0 : Integer.valueOf(modelMap.get("developSchool").toString()));

            startRow += 1;
            startCol = 1;
            row = sheet.getRow(startRow);
            cell = row.getCell(startCol);

            //专任教师总数合计
            cell.setCellValue(modelMap.get("speciallyManAndWoman") == null ? 0 : Integer.valueOf(modelMap.get("speciallyManAndWoman").toString()));
            //专任教师男
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("speciallyMan") == null ? 0 : Integer.valueOf(modelMap.get("speciallyMan").toString()));
            //专任教师女
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("speciallyWoman") == null ? 0 : Integer.valueOf(modelMap.get("speciallyWoman").toString()));
            //专任教师正式党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("speciallyFormation") == null ? 0 : Integer.valueOf(modelMap.get("speciallyFormation").toString()));
            //专任教师预备党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("speciallyPrepared") == null ? 0 : Integer.valueOf(modelMap.get("speciallyPrepared").toString()));
            //专任教师党员合计
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("speciallyPartyTotal") == null ? 0 : Integer.valueOf(modelMap.get("speciallyPartyTotal").toString()));
            //专任教师党员占比
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("speciallyPercent").toString());
            //专任教师入党申请人
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("applySpecially") == null ? 0 : Integer.valueOf(modelMap.get("applySpecially").toString()));
            //专任教师入党积极分子
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("activitySpecially") == null ? 0 : Integer.valueOf(modelMap.get("activitySpecially").toString()));
            //专任教师发展对象
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("developSpecially") == null ? 0 : Integer.valueOf(modelMap.get("developSpecially").toString()));

            startRow += 2;
            startCol = 1;
            row = sheet.getRow(startRow);
            cell = row.getCell(startCol);

            //正高级总数合计
            cell.setCellValue(modelMap.get("isLevel") == null ? 0 : Integer.valueOf(modelMap.get("isLevel").toString()));
            //正高级男
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("isLevelMan") == null ? 0 : Integer.valueOf(modelMap.get("isLevelMan").toString()));
            //正高级女
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("isLevelWoman") == null ? 0 : Integer.valueOf(modelMap.get("isLevelWoman").toString()));
            //正高级正式党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("speciallyIsLevelFormation") == null ? 0 : Integer.valueOf(modelMap.get("speciallyIsLevelFormation").toString()));
            //正高级预备党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("preparedSpeciallyIsLevelFormation") == null ? 0 : Integer.valueOf(modelMap.get("preparedSpeciallyIsLevelFormation").toString()));
            //正高级党员合计
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("isLevelPartyTotal") == null ? 0 : Integer.valueOf(modelMap.get("isLevelPartyTotal").toString()));
            //正高级党员占比
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("isLevelPercent").toString());
            //正高级入党申请人
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("applySpeciallyIsLevel") == null ? 0 : Integer.valueOf(modelMap.get("applySpeciallyIsLevel").toString()));
            //正高级入党积极分子
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("activitySpeciallyIsLevel") == null ? 0 : Integer.valueOf(modelMap.get("activitySpeciallyIsLevel").toString()));
            //正高级发展对象
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("developSpeciallyIsLevel") == null ? 0 : Integer.valueOf(modelMap.get("developSpeciallyIsLevel").toString()));

            startRow += 1;
            startCol = 1;
            row = sheet.getRow(startRow);
            cell = row.getCell(startCol);

            //副高级总数合计
            cell.setCellValue(modelMap.get("deputyLevel") == null ? 0 : Integer.valueOf(modelMap.get("deputyLevel").toString()));
            //副高级男
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("deputyLevelMan") == null ? 0 : Integer.valueOf(modelMap.get("deputyLevelMan").toString()));
            //副高级女
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("deputyLevelWoman") == null ? 0 : Integer.valueOf(modelMap.get("deputyLevelWoman").toString()));
            //副高级正式党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("speciallyDeputyLevel") == null ? 0 : Integer.valueOf(modelMap.get("speciallyDeputyLevel").toString()));
            //副高级预备党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("preparedSpeciallyDeputyLevel") == null ? 0 : Integer.valueOf(modelMap.get("preparedSpeciallyDeputyLevel").toString()));
            //副高级党员合计
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("deputyLevelPartyTotal") == null ? 0 : Integer.valueOf(modelMap.get("deputyLevelPartyTotal").toString()));
            //副高级党员占比
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("deputyLevelPercent").toString());
            //副高级入党申请人
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("applySpeciallyDeputyLevel") == null ? 0 : Integer.valueOf(modelMap.get("applySpeciallyDeputyLevel").toString()));
            //副高级入党积极分子
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("activitySpeciallyDeputyLevel") == null ? 0 : Integer.valueOf(modelMap.get("activitySpeciallyDeputyLevel").toString()));
            //副高级发展对象
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("developSpeciallyDeputyLevel") == null ? 0 : Integer.valueOf(modelMap.get("developSpeciallyDeputyLevel").toString()));

            startRow += 1;
            startCol = 1;
            row = sheet.getRow(startRow);
            cell = row.getCell(startCol);

            //中级及以下总数合计
            cell.setCellValue(modelMap.get("isLevelTotal") == null ? 0 : Integer.valueOf(modelMap.get("isLevelTotal").toString()));
            //中级及以下男
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("followingMan") == null ? 0 : Integer.valueOf(modelMap.get("followingMan").toString()));
            //中级及以下女
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("followingWoman") == null ? 0 : Integer.valueOf(modelMap.get("followingWoman").toString()));
            //中级及以下正式党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("intermediateFormation") == null ? 0 : Integer.valueOf(modelMap.get("intermediateFormation").toString()));
            //中级及以下预备党员
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("intermediatePrepared") == null ? 0 : Integer.valueOf(modelMap.get("intermediatePrepared").toString()));
            //中级及以下党员合计
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("intermediateTotal") == null ? 0 : Integer.valueOf(modelMap.get("intermediateTotal").toString()));
            //中级及以下党员占比
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("intermediatePercent").toString());
            //中级及以下入党申请人
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("mediumApply") == null ? 0 : Integer.valueOf(modelMap.get("mediumApply").toString()));
            //中级及以下入党积极分子
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("mediumActivity") == null ? 0 : Integer.valueOf(modelMap.get("mediumActivity").toString()));
            //中级及以下发展对象
            cell = row.getCell(++startCol);
            cell.setCellValue(modelMap.get("mediumDevelop") == null ? 0 : Integer.valueOf(modelMap.get("mediumDevelop").toString()));
            return wb;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public XSSFWorkbook statPartyJzgInfoExport(ModelMap modelMap) {
        InputStream is = null;
        try {
            int startRow = 3;
            int startCol = 0;
            is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/analysis/stat_party_jzg_info.xlsx"));
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

            XSSFCellStyle style2 = wb.createCellStyle();// 设置这些样式
            style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style2.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            XSSFFont font2 = wb.createFont();// 生成一个字体
            font2.setFontHeightInPoints((short) 11);
            font2.setFontName("宋体");
            style2.setFont(font2);// 把字体应用到当前的样式

            for (int i = 0; i < data.size(); i++) {
                Map<String, String> map = data.get(i);
                if (i > 0) {
                    startRow++;
                    startCol = 0;
                }
                //序号
                row = sheet.createRow(startRow);
                cell = row.createCell(startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(i + 1);

                //二级党组织
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("partyName"));

                //入党申请人
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("jzgApplyTotal") == null ? 0 : Integer.valueOf(map.get("jzgApplyTotal")));

                //入党积极分子
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("jzgActivity") == null ? 0 : Integer.valueOf(map.get("jzgActivity")));

                //发展对象
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("jzgDevelop") == null ? 0 : Integer.valueOf(map.get("jzgDevelop")));

                //正式党员
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("jzgFormation") == null ? 0 : Integer.valueOf(map.get("jzgFormation")));

                //预备党员
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("jzgPrepared") == null ? 0 : Integer.valueOf(map.get("jzgPrepared")));

                //普通教师
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("jzgGeneral") == null ? 0 : Integer.valueOf(map.get("jzgGeneral")));

                //总计
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("jzgTotal") == null ? 0 : Integer.valueOf(map.get("jzgTotal")));

                //培养情况占比
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("trainPercent"));

                //党员占比
                cell = row.getCell(++startCol)==null ? row.createCell(startCol) : row.getCell(++startCol);
                cell.setCellStyle(style2);
                cell.setCellValue(map.get("partyPercent"));
            }

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
                                                        List<StatByteBean> countActivists, List<StatByteBean> countDevelopment, Byte cls) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> masters = new HashMap<>();
        Map<String, String> doctors = new HashMap<>();
        Integer masterCount = 0, doctorCount = 0;
        Integer mastersApplyTotal = 0, doctorsApplyTotal = 0;
        //预备党员
        for (StatByteBean obj: preparedMembers) {
            if (obj.getGroupBy() != null) {
                if (cls == 6) { //各二级党组织专任教师队伍党员信息分析
                    if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                        masters.put("jzgPrepared", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                } else {
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                        masters.put("preparedMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                        doctors.put("preparedMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                }

            }
        }

        //正式党员
        for (StatByteBean obj: formalMembers) {
            if (obj.getGroupBy() != null) {
                if (cls == 6) { //各二级党组织专任教师队伍党员信息分析
                    if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                        masters.put("jzgFormation", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                } else {
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                        masters.put("formalMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                        doctors.put("formalMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                }


            }
        }

        //申请入党人数
        for (StatByteBean obj: applyJoin) {
            if (obj.getGroupBy() != null) {
                if (cls == 6) { //各二级党组织专任教师队伍党员信息分析
                    if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                        if (obj.getNum() > 0) {
                            mastersApplyTotal += obj.getNum();
                        }
                    }
                } else {
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
        }
        for (StatByteBean obj: passJoin) {
            if (obj.getGroupBy() != null) {
                if (cls == 6) { //各二级党组织专任教师队伍党员信息分析
                    if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                        if (obj.getNum() > 0) {
                            mastersApplyTotal += obj.getNum();
                        }
                    }
                } else {
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                        mastersApplyTotal += obj.getNum();
                    }
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                        doctorsApplyTotal += obj.getNum();
                    }
                }
            }
        }

        //入党积极分子
        for (StatByteBean obj: countActivists) {
            if (obj.getGroupBy() != null) {
                if (cls == 6) { //各二级党组织专任教师队伍党员信息分析
                    if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                        masters.put("jzgActivity", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                } else {
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                        masters.put("activityTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                        doctors.put("activityTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                }
            }
        }

        //发展对象
        for (StatByteBean obj: countDevelopment) {
            if (obj.getGroupBy() != null) {
                if (cls == 6) { //各二级党组织专任教师队伍党员信息分析
                    if (obj.getGroupBy() == OwConstants.OW_STAT_JZG_SPECIALLY) {
                        masters.put("jzgDevelop", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                } else {
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_SS) {  //硕士研究生
                        masters.put("developTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                    if (obj.getGroupBy() == SystemConstants.STUDENT_TYPE_BS) {   //博士研究生
                        doctors.put("developTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                }
            }
        }
        if (cls == 6) { //各二级党组织专任教师队伍党员信息分析
            //预备党员
            if (masters.get("jzgPrepared") == null) {
                masters.put("jzgPrepared", "0");
            }
            //正式党员
            if (masters.get("jzgFormation") == null) {
                masters.put("jzgFormation", "0");
            }
            //申请入党人数
            masters.put("jzgApplyTotal", mastersApplyTotal.toString());
            //入党积极分子
            if (masters.get("jzgActivity") == null) {
                masters.put("jzgActivity", "0");
            }
            //发展对象
            if (masters.get("jzgDevelop") == null) {
                masters.put("jzgDevelop", "0");
            }
            //普通教师
            Integer generalTotal = 0;
            masters.put("jzgGeneral", generalTotal.toString());
            //申请入党人数+入党积极分子+发展对象
            Integer trainTotal = mastersApplyTotal + Integer.valueOf(masters.get("jzgActivity")) + Integer.valueOf(masters.get("jzgDevelop"));
            masters.put("trainTotal", trainTotal.toString());
            //正式党员+预备党员
            Integer partyTotal = Integer.valueOf(masters.get("jzgFormation")) + Integer.valueOf(masters.get("jzgPrepared"));
            masters.put("partyTotal", partyTotal.toString());
            //总计
            Integer total = trainTotal + partyTotal + generalTotal;
            masters.put("jzgTotal", total.toString());
        } else {
            //预备党员
            if (masters.get("preparedMembers") == null) {
                masters.put("preparedMembers", "0");
            }
            if (doctors.get("preparedMembers") == null) {
                doctors.put("preparedMembers", "0");
            }
            //正式党员
            if (masters.get("formalMembers") == null) {
                masters.put("formalMembers", "0");
            }
            if (doctors.get("formalMembers") == null) {
                doctors.put("formalMembers", "0");
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
            masters.put("applyTotal", mastersApplyTotal.toString());
            doctors.put("applyTotal", doctorsApplyTotal.toString());
            //入党积极分子
            if (masters.get("activityTotal") == null) {
                masters.put("activityTotal", "0");
            }
            if (doctors.get("activityTotal") == null) {
                doctors.put("activityTotal", "0");
            }
            //发展对象
            if (masters.get("developTotal") == null) {
                masters.put("developTotal", "0");
            }
            if (doctors.get("developTotal") == null) {
                doctors.put("developTotal", "0");
            }
        }
        list.add(masters);
        if (cls != 6) {
            list.add(doctors);
        }
        return list;
    }

    @Cacheable(value="statOwInfo", key = "#cls")
    public Map getOwYjsInfo(Byte cls, DecimalFormat df) {

        Map dataMap = new HashedMap();

        Map<String, String> masters = new HashMap<>();
        Map<String, String> doctors = new HashMap<>();

        Date now = new Date();
        int year = DateUtils.getYear(now);
        int month = DateUtils.getMonth(now);
        dataMap.put("year", year);
        dataMap.put("month", month);

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
        List<Map<String, String>> data = encapsulationData(preparedMembers, formalMembers, applyJoin, passJoin, countActivists, countDevelopment, cls);
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

        dataMap.put("total", countYjs);
        //研究生占比
        Integer mpmc = masters.get("partyMembersCount") == null ? 0 : Integer.valueOf(masters.get("partyMembersCount"));
        Integer dpmc = doctors.get("partyMembersCount") == null ? 0 : Integer.valueOf(doctors.get("partyMembersCount"));
        Integer total = dataMap.get("total") == null ? 0 : (Integer) dataMap.get("total");

        BigDecimal percent = calculateDivide(mpmc + dpmc, total);

        masters.put("masterPercent", df.format(masterPercent.doubleValue() * 100) + "%");
        doctors.put("doctorPercent", df.format(doctorPercent.doubleValue() * 100) + "%");

        dataMap.put("percent", df.format(percent.doubleValue() * 100) + "%");
        dataMap.put("masters", masters);
        dataMap.put("doctors", doctors);
        dataMap.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));

        return dataMap;
    }

    @Cacheable(value="statOwInfo", key = "#cls")
    public Map getPartyYjsInfo(Byte cls, DecimalFormat df) {

        Map<String, String> masters = new HashMap<>();
        Map<String, String> doctors = new HashMap<>();
        Map dataMap = new HashMap();

        Date now = new Date();
        int year = DateUtils.getYear(now);
        int month = DateUtils.getMonth(now);
        dataMap.put("year", year);
        dataMap.put("month", month);

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
            List<Map<String, String>> result = encapsulationData(preparedMembers, formalMembers, applyJoin, passJoin, countActivists, countDevelopment, cls);
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
            data.add( masters);
            data.add( doctors);
            dataMap.put("data", data);
            dataMap.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));
        }
        return dataMap;
    }

    @Cacheable(value="statOwInfo", key = "#cls")
    public Map getOwJzgInfo(Byte cls, DecimalFormat df) {
        Map dataMap = new HashedMap();
        Date now = new Date();
        int year = DateUtils.getYear(now);
        int month = DateUtils.getMonth(now);
        dataMap.put("year", year);
        dataMap.put("month", month);

        //校编教工和专任教师(男 女)
        List<StatByteBean> man = statOwInfoMapper.member_teacherCount(null, SystemConstants.GENDER_MALE);
        List<StatByteBean> woman = statOwInfoMapper.member_teacherCount(null, SystemConstants.GENDER_FEMALE);

        //正高级、副高级(专任教师)
        List<StatByteBean> isLevelMan = statOwInfoMapper.member_teacherCount("正高", SystemConstants.GENDER_MALE);//正高男
        List<StatByteBean> isLevelWoman = statOwInfoMapper.member_teacherCount("正高", SystemConstants.GENDER_FEMALE);//正高女
        List<StatByteBean> DeputyLevelMan = statOwInfoMapper.member_teacherCount( "副高", SystemConstants.GENDER_MALE);//副高男
        List<StatByteBean> DeputyLevelWoman = statOwInfoMapper.member_teacherCount( "副高", SystemConstants.GENDER_FEMALE);//副高女

        //校编教工、专任教师正式党员
        List<StatByteBean> formation = statOwInfoMapper.member_teacherSort(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, null);
        //校编教工、专任教师预备党员
        List<StatByteBean> prepared = statOwInfoMapper.member_teacherSort(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, null);

        //专任教师正式党员（正高级）
        List<StatByteBean> isLevelFormation = statOwInfoMapper.member_teacherSort(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, "正高", null, null);
        //专任教师正式党员（副高级）
        List<StatByteBean> DeputyLevelFormation = statOwInfoMapper.member_teacherSort(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, "副高", null, null);
        //专任教师预备党员（正高级）
        List<StatByteBean> isLevelPrepared = statOwInfoMapper.member_teacherSort(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, "正高", null, null);
        //专任教师预备党员（副高级）
        List<StatByteBean> deputyLevelPrepared = statOwInfoMapper.member_teacherSort(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, "副高", null, null);

        //校编教工、专任教师入党申请人
        List<StatByteBean> applyToJoin = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_INIT, null, null, null, null);
        List<StatByteBean> applyToJoinPass = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_PASS, null, null, null, null);
        //校编教工、专任教师入党积极分子
        List<StatByteBean> activity = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_ACTIVE, null, null, null, null);
        //校编教工、专任教师发展对象
        List<StatByteBean> develop = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_CANDIDATE, null, null, null, null);

        //专任教师正高级入党申请人
        List<StatByteBean> isLevelJoin = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_INIT, "正高级", null, null, null);
        List<StatByteBean> isLevelJoinPass = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_PASS, "正高级", null, null, null);
        //专任教师正高级入党积极分子
        List<StatByteBean> isLevelActivity = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_ACTIVE, "正高级", null, null, null);
        //专任教师正高级发展对象
        List<StatByteBean> isLevelDevelop = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_CANDIDATE, "正高级", null, null, null);

        //专任教师副高级入党申请人
        List<StatByteBean> deputyLevelJoin = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_INIT, "副高级", null, null, null);
        List<StatByteBean> deputyLevelJoinPass = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_PASS, "副高级", null, null, null);
        //专任教师副高级入党积极分子
        List<StatByteBean> deputyLevelActivity = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_ACTIVE, "副高级", null, null, null);
        //专任教师副高级发展对象
        List<StatByteBean> deputyLevelDevelop = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_CANDIDATE, "副高级", null, null, null);

        Map data = encapsulationData(man, woman, isLevelMan, isLevelWoman, DeputyLevelMan,  DeputyLevelWoman,  formation, prepared, isLevelFormation, DeputyLevelFormation,
                 isLevelPrepared, deputyLevelPrepared, applyToJoin, applyToJoinPass, activity, develop, isLevelJoin, isLevelJoinPass, isLevelActivity,
                 isLevelDevelop, deputyLevelJoin, deputyLevelJoinPass, deputyLevelActivity, deputyLevelDevelop, df);
        dataMap.putAll(data);
        dataMap.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));

        return dataMap;
    }


    @Cacheable(value="statOwInfo", key = "#cls")
    public Map getPartyJzgInfo(Byte cls, DecimalFormat df) {
        Map dataMap = new HashMap();

        Date now = new Date();
        int year = DateUtils.getYear(now);
        int month = DateUtils.getMonth(now);
        dataMap.put("year", year);
        dataMap.put("month", month);

        List<Map<String, String>> data = new ArrayList<>();
        // 二级党组织名称
        List<Party> partyNames = statOwInfoMapper.getSecondPartyName();
        for (Party p: partyNames) {
            //申请入党人员
            List<StatByteBean> applyJoin = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_INIT, null, p.getId(), null, null);
            List<StatByteBean> passJoin = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_PASS, null, p.getId(), null, null);
            // 入党积极分子
            List<StatByteBean> countActivists = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_ACTIVE, null, p.getId(), null, null);
            // 发展对象
            List<StatByteBean> countDevelopment = statOwInfoMapper.memberApply_teacherSort(OwConstants.OW_APPLY_STAGE_CANDIDATE, null, p.getId(), null, null);

            // 预备党员
            List<StatByteBean> preparedMembers = statOwInfoMapper.member_teacherSort(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, p.getId(), null);
            // 正式党员
            List<StatByteBean> formalMembers = statOwInfoMapper.member_teacherSort(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, p.getId(), null);
            // 封装数据
            List<Map<String, String>> result = encapsulationData(preparedMembers, formalMembers, applyJoin, passJoin, countActivists, countDevelopment, cls);
            Map<String, String> jzgData = result.get(0);
            //培养情况占比
            BigDecimal trainPercent = calculateDivide(Integer.valueOf(jzgData.get("trainTotal")), Integer.valueOf(jzgData.get("jzgTotal")));
            //党员占比
            BigDecimal partyPercent = calculateDivide(Integer.valueOf(jzgData.get("partyTotal")), Integer.valueOf(jzgData.get("jzgTotal")));
            jzgData.put("trainPercent", df.format(trainPercent.doubleValue() * 100) + "%");
            jzgData.put("partyPercent", df.format(partyPercent.doubleValue() * 100) + "%");
            jzgData.put("partyName", p.getShortName());
            data.add(jzgData);
            dataMap.put("data", data);
        }
        dataMap.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));
        return dataMap;
    }

    @Cacheable(value="statOwInfo",key = "#cls")
    public ModelMap getOwBksInfo(Byte cls,ModelMap modelMap) {

        //2019级本科生
        List<StatByteBean> statByteBeans_19 = statOwInfoMapper.selectUser_groupByLevel("2019");
        int studentNum_19 = getCount(statByteBeans_19);
        modelMap.put("studentNum_19",studentNum_19);

        //2019正式党员
        List<StatByteBean> positivePartyList_19 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, null, "2019");
        int positivePartyNum_19 = getCount(positivePartyList_19);
        modelMap.put("positivePartyNum_19",positivePartyNum_19);
        //2019预备党员
        List<StatByteBean> growPartyList_19 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, null, "2019");
        int growPartyNum_19 = getCount(growPartyList_19);
        modelMap.put("growPartyNum_19",growPartyNum_19);

        //2019党员总数
        int countNum_19 = positivePartyNum_19+growPartyNum_19;
        modelMap.put("count_19",countNum_19);

        //占比
        double partyProportion_19 = new BigDecimal((float)countNum_19/studentNum_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr_19 = partyProportion_19+"%";
        modelMap.put("partyStr_19",partyStr_19);

        //2019申请人数
        List<StatByteBean> applyBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT,"2019",null,null);
        int applyNum_19 = getCount(applyBeans_19);

        List<StatByteBean> applyPassBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS,"2019",null,null);
        int applyPassNum_19 = getCount(applyPassBeans_19);

        int totalNum_19 = applyNum_19+applyPassNum_19;
        modelMap.put("totalNum_19",totalNum_19);
        //占比
        double applyProportion_19 = new BigDecimal((float)totalNum_19/studentNum_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr_19 = applyProportion_19+"%";
        modelMap.put("applyStr_19",applyStr_19);

        //2019积极分子
        List<StatByteBean> activeBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE,"2019",null,null);
        int activeNum_19 = getCount(activeBeans_19);
        modelMap.put("activeNum_19",activeNum_19);
        //占比
        double activeProportion_19 = new BigDecimal((float)activeNum_19/studentNum_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr_19 = activeProportion_19+"%";
        modelMap.put("activeStr_19",activeStr_19);

        //2019发展对象
        List<StatByteBean> devBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE,"2019",null,null);
        int devNum_19 = getCount(devBeans_19);
        modelMap.put("devNum_19",devNum_19);
        //占比
        double devProportion_19 = new BigDecimal((float)devNum_19/studentNum_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr_19 = devProportion_19+"%";
        modelMap.put("devStr_19",devStr_19);
        //2018级本科生
        List<StatByteBean> statByteBeans_18 = statOwInfoMapper.selectUser_groupByLevel("2018");
        int studentNum_18 = getCount(statByteBeans_18);
        modelMap.put("studentNum_18",studentNum_18);
        //2018正式党员
        List<StatByteBean> positivePartyList_18 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, null, "2018");
        int positivePartyNum_18 = getCount(positivePartyList_18);
        modelMap.put("positivePartyNum_18",positivePartyNum_18);
        //2018预备党员
        List<StatByteBean> growPartyList_18 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, null, "2018");
        int growPartyNum_18 = getCount(growPartyList_18);
        modelMap.put("growPartyNum_18",growPartyNum_18);
        //2018党员总数
        int countNum_18 = positivePartyNum_18+growPartyNum_18;
        modelMap.put("count_18",countNum_18);
        //占比
        double partyProportion_18 = new BigDecimal((float)countNum_18/studentNum_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr_18 = partyProportion_18+"%";
        modelMap.put("partyStr_18",partyStr_18);
        //2018申请人数
        List<StatByteBean> applyBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT,"2018",null,null);
        int applyNum_18 = getCount(applyBeans_18);

        List<StatByteBean> applyPassBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS,"2018",null,null);
        int applyPassNum_18 = getCount(applyPassBeans_18);

        int totalNum_18 = applyNum_18+applyPassNum_18;
        modelMap.put("totalNum_18",totalNum_18);
        //占比
        double applyProportion_18 = new BigDecimal((float)totalNum_18/studentNum_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr_18 = applyProportion_18+"%";
        modelMap.put("applyStr_18",applyStr_18);

        //2018积极分子
        List<StatByteBean> activeBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE,"2018",null,null);
        int activeNum_18 = getCount(activeBeans_18);
        modelMap.put("activeNum_18",activeNum_18);
        //占比
        double activeProportion_18 = new BigDecimal((float)activeNum_18/studentNum_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr_18 = activeProportion_18+"%";
        modelMap.put("activeStr_18",activeStr_18);

        //2018发展对象
        List<StatByteBean> devBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE,"2018",null,null);
        int devNum_18 = getCount(devBeans_18);
        modelMap.put("devNum_18",devNum_18);
        //占比
        double devProportion_18 = new BigDecimal((float)devNum_18/studentNum_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr_18 = devProportion_18+"%";
        modelMap.put("devStr_18",devStr_18);


        //2017级本科生
        List<StatByteBean> statByteBeans_17 = statOwInfoMapper.selectUser_groupByLevel("2017");
        int studentNum_17 = getCount(statByteBeans_17);
        modelMap.put("studentNum_17",studentNum_17);

        //2017正式党员
        List<StatByteBean> positivePartyList_17 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, null, "2017");
        int positivePartyNum_17 = getCount(positivePartyList_17);
        modelMap.put("positivePartyNum_17",positivePartyNum_17);
        //2017预备党员
        List<StatByteBean> growPartyList_17 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, null, "2017");
        int growPartyNum_17 = getCount(growPartyList_17);
        modelMap.put("growPartyNum_17",growPartyNum_17);
        //2017党员总数
        int countNum_17 = positivePartyNum_17+growPartyNum_17;
        modelMap.put("count_17",countNum_17);

        //占比
        double partyProportion_17 = new BigDecimal((float)countNum_17/studentNum_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr_17 = partyProportion_17+"%";
        modelMap.put("partyStr_17",partyStr_17);

        //2017申请人数
        List<StatByteBean> applyBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT,"2017",null,null);
        int applyNum_17 = getCount(applyBeans_17);

        List<StatByteBean> applyPassBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS,"2017",null,null);
        int applyPassNum_17 = getCount(applyPassBeans_17);

        int totalNum_17 = applyNum_17+applyPassNum_17;
        modelMap.put("totalNum_17",totalNum_17);
        //占比
        double applyProportion_17 = new BigDecimal((float)totalNum_17/studentNum_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr_17 = applyProportion_17+"%";
        modelMap.put("applyStr_17",applyStr_17);

        //2017积极分子
        List<StatByteBean> activeBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE,"2017",null,null);
        int activeNum_17 = getCount(activeBeans_17);
        modelMap.put("activeNum_17",activeNum_17);
        //占比
        double activeProportion_17 = new BigDecimal((float)activeNum_17/studentNum_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr_17 = activeProportion_17+"%";
        modelMap.put("activeStr_17",activeStr_17);

        //2017发展对象
        List<StatByteBean> devBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE,"2017",null,null);
        int devNum_17 = getCount(devBeans_17);
        modelMap.put("devNum_17",devNum_17);
        //占比
        double devProportion_17 = new BigDecimal((float)devNum_17/studentNum_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr_17 = devProportion_17+"%";
        modelMap.put("devStr_17",devStr_17);


        //2016级本科生
        List<StatByteBean> statByteBeans_16 = statOwInfoMapper.selectUser_groupByLevel("2016");
        int studentNum_16 = getCount(statByteBeans_16);
        modelMap.put("studentNum_16",studentNum_16);

        //2016正式党员
        List<StatByteBean> positivePartyList_16 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, null, "2016");
        int positivePartyNum_16 = getCount(positivePartyList_16);
        modelMap.put("positivePartyNum_16",positivePartyNum_16);
        //2016预备党员
        List<StatByteBean> growPartyList_16 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, null, "2016");
        int growPartyNum_16 = getCount(growPartyList_16);
        modelMap.put("growPartyNum_16",growPartyNum_16);
        //2016党员总数
        int countNum_16 = positivePartyNum_16+growPartyNum_16;
        modelMap.put("count_16",countNum_16);
        //占比
        double partyProportion_16 = new BigDecimal((float)countNum_16/studentNum_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr_16 = partyProportion_16+"%";
        modelMap.put("partyStr_16",partyStr_16);

        //2016申请人数
        List<StatByteBean> applyBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT,"2016",null,null);
        int applyNum_16 = getCount(applyBeans_16);

        List<StatByteBean> applyPassBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS,"2016",null,null);
        int applyPassNum_16 = getCount(applyPassBeans_16);

        int totalNum_16 = applyNum_16+applyPassNum_16;
        modelMap.put("totalNum_16",totalNum_16);
        //占比
        double applyProportion_16 = new BigDecimal((float)totalNum_16/studentNum_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr_16 = applyProportion_16+"%";
        modelMap.put("applyStr_16",applyStr_16);

        //2016积极分子
        List<StatByteBean> activeBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE,"2016",null,null);
        int activeNum_16 = getCount(activeBeans_16);
        modelMap.put("activeNum_16",activeNum_16);
        //占比
        double activeProportion_16 = new BigDecimal((float)activeNum_16/studentNum_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr_16 = activeProportion_16+"%";
        modelMap.put("activeStr_16",activeStr_16);

        //2016发展对象
        List<StatByteBean> devBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE,"2016",null,null);
        int devNum_16 = getCount(devBeans_16);
        modelMap.put("devNum_16",devNum_16);
        //占比
        double devProportion_16 = new BigDecimal((float)devNum_16/studentNum_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr_16 = devProportion_16+"%";
        modelMap.put("devStr_16",devStr_16);

        //所有本科学生
        int studentNumBks = studentNum_19+studentNum_18+studentNum_17+studentNum_16;
        modelMap.put("studentNum",studentNumBks);
        //正式党员
        int positivePartyNum = positivePartyNum_19+positivePartyNum_18+positivePartyNum_17+positivePartyNum_16;
        modelMap.put("positivePartyNum",positivePartyNum);
        //预备党员
        int growPartyNum = growPartyNum_19+growPartyNum_18+growPartyNum_17+growPartyNum_16;
        modelMap.put("growPartyNum",growPartyNum);
        //党员总数
        int countNum = positivePartyNum+growPartyNum;
        modelMap.put("count",countNum);
        //占比
        double partyProportion = new BigDecimal((float)countNum/studentNumBks*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr = partyProportion+"%";
        modelMap.put("Proportion",partyStr);
        //入党申请人数
        int totalNum = totalNum_19+totalNum_18+totalNum_17+totalNum_16;
        modelMap.put("totalNum",totalNum);
        //占比
        double applyProportion = new BigDecimal((float)totalNum/studentNumBks*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr = applyProportion+"%";
        modelMap.put("applyStr",applyStr);
        //入党积极分子
        int activeNum = activeNum_19+activeNum_18+activeNum_17+activeNum_16;
        modelMap.put("activeNum",activeNum);
        //占比
        double activeProportion = new BigDecimal((float)activeNum/studentNumBks*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr = activeProportion+"%";
        modelMap.put("activeStr",activeStr);
        //发展对象
        int devNum = devNum_19+devNum_18+devNum_17+devNum_16;
        modelMap.put("devNum",devNum);
        //占比
        double devProportion = new BigDecimal((float)devNum/studentNumBks*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr = devProportion+"%";
        modelMap.put("devStr",devStr);

        modelMap.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));
        return modelMap;
    }

    @Cacheable(value="statOwInfo",key = "#cls")
    public ModelMap getPartyBksInfo(Byte cls,ModelMap modelMap) {

        List<Party> partyNameList = statOwInfoMapper.getSecondPartyName();
        List<Map<String, String>> data = new ArrayList<>();
        for (Party party : partyNameList) {

            Map dataMap = new HashedMap();

            //2019入党申请人
            List<StatByteBean> applyBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT, "2019", party.getId(), null);
            int applyCount_19 = getCount(applyBeans_19);
            List<StatByteBean> applyPassBeans = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS, "2019", party.getId(), null);
            int applyPassCount_19 = getCount(applyPassBeans);
            int count_19 = applyPassCount_19 + applyCount_19;
            dataMap.put("count_19", count_19);
            //2018入党申请人
            List<StatByteBean> applyBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT, "2018", party.getId(), null);
            int applyCount_18 = getCount(applyBeans_18);
            List<StatByteBean> applyPassBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS, "2018", party.getId(), null);
            int applyPassCount_18 = getCount(applyPassBeans_18);
            int count_18 =  applyPassCount_18 + applyCount_18;
            dataMap.put("count_18",count_18);
            //2017入党申请人
            List<StatByteBean> applyBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT, "2017", party.getId(), null);
            int applyCount_17 = getCount(applyBeans_17);
            List<StatByteBean> applyPassBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS, "2017", party.getId(), null);
            int applyPassCount_17 = getCount(applyPassBeans_17);
            int count_17 =  applyPassCount_17 + applyCount_17;
            dataMap.put("count_17",count_17);
            //2016入党申请人
            List<StatByteBean> applyBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT, "2016", party.getId(), null);
            int applyCount_16 = getCount(applyBeans_16);
            List<StatByteBean> applyPassBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS, "2016", party.getId(), null);
            int applyPassCount_16 = getCount(applyPassBeans_16);
            int count_16 =  applyPassCount_16 + applyCount_16;
            dataMap.put("count_16",count_16);
            //年级入党申请总数
            int totalCount = count_16 + count_17 + count_18 + count_19;
            dataMap.put("totalCount",totalCount);

            //入党积极分子
            List<StatByteBean> activeBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE, "2019", party.getId(), null);
            int activeNum_19 = getCount(activeBeans_19);
            dataMap.put("activeNum_19",activeNum_19);

            List<StatByteBean> activeBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE, "2018", party.getId(), null);
            int activeNum_18 = getCount(activeBeans_18);
            dataMap.put("activeNum_18",activeNum_18);

            List<StatByteBean> activeBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE, "2017", party.getId(), null);
            int activeNum_17 = getCount(activeBeans_17);
            dataMap.put("activeNum_17",activeNum_17);

            List<StatByteBean> activeBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE, "2016", party.getId(), null);
            int activeNum_16 = getCount(activeBeans_16);
            dataMap.put("activeNum_16",activeNum_16);
            //积极分子总数
            int activeTotalCount = activeNum_19+activeNum_18+activeNum_17+activeNum_16;
            dataMap.put("activeTotalCount",activeTotalCount);

            //发展对象
            List<StatByteBean> devBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE, "2019", party.getId(), null);
            int devNum_19 = getCount(devBeans_19);
            dataMap.put("devNum_19",devNum_19);
            List<StatByteBean> devBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE, "2018", party.getId(), null);
            int devNum_18 = getCount(devBeans_18);
            dataMap.put("devNum_18",devNum_18);
            List<StatByteBean> devBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE, "2017", party.getId(), null);
            int devNum_17 = getCount(devBeans_17);
            dataMap.put("devNum_17",devNum_17);
            List<StatByteBean> devBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE, "2016", party.getId(), null);
            int devNum_16 = getCount(devBeans_16);
            dataMap.put("devNum_16",devNum_16);
            int devTotalCount = devNum_19+devNum_18+devNum_17+devNum_16;
            //发展对象总数
            dataMap.put("devTotalCount",devTotalCount);

            //2019级正式党员
            List<StatByteBean> positivePartyBeans_19 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, party.getId(), null, null, "2019");
            int positivePartyCount_19 = getCount(positivePartyBeans_19);
            dataMap.put("positivePartyCount_19", positivePartyCount_19);
            //2018级正式党员
            List<StatByteBean> positivePartyBeans_18 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, party.getId(), null, null, "2018");
            int positivePartyCount_18 = getCount(positivePartyBeans_18);
            dataMap.put("positivePartyCount_18", positivePartyCount_18);
            //2017级正式党员
            List<StatByteBean> positivePartyBeans_17 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, party.getId(), null, null, "2017");
            int positivePartyCount_17 = getCount(positivePartyBeans_17);
            dataMap.put("positivePartyCount_17", positivePartyCount_17);
            //2016级正式党员
            List<StatByteBean> positivePartyBeans_16 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, party.getId(), null, null, "2016");
            int positivePartyCount_16 = getCount(positivePartyBeans_16);
            dataMap.put("positivePartyCount_16", positivePartyCount_16);
            //正式党员总和
            int positivePartyTotalCount = positivePartyCount_16 + positivePartyCount_17 + positivePartyCount_18 + positivePartyCount_19;
            dataMap.put("positivePartyTotalCount", positivePartyTotalCount);


            //2019级预备党员
            List<StatByteBean> growPartyBeans_19 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, party.getId(), null, null, "2019");
            int growPartyCount_19 = getCount(growPartyBeans_19);
            dataMap.put("growPartyCount_19", growPartyCount_19);
            //2018级预备党员
            List<StatByteBean> growPartyBeans_18 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, party.getId(), null, null, "2018");
            int growPartyCount_18 = getCount(growPartyBeans_18);
            dataMap.put("growPartyCount_18", growPartyCount_18);
            //2017级预备党员
            List<StatByteBean> growPartyBeans_17 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, party.getId(), null, null, "2017");
            int growPartyCount_17 = getCount(growPartyBeans_17);
            dataMap.put("growPartyCount_17", growPartyCount_17);
            //2016级预备党员
            List<StatByteBean> growPartyBeans_16 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, party.getId(), null, null, "2016");
            int growPartyCount_16 = getCount(growPartyBeans_16);
            dataMap.put("growPartyCount_16", growPartyCount_16);
            //预备党员总和
            int growPartyTotalCount = growPartyCount_16 + growPartyCount_17 + growPartyCount_18 + growPartyCount_19;
            dataMap.put("growPartyTotalCount", growPartyTotalCount);


            //2016级党员数
            int totalCount_16 = count_16 + activeNum_16 + devNum_16 + positivePartyCount_16 + growPartyCount_16;
            dataMap.put("totalCount_16",totalCount_16);
            //2017级党员数
            int totalCount_17 = count_17 + applyCount_17 + activeNum_17 + devNum_17 + positivePartyCount_17 + growPartyCount_17;
            dataMap.put("totalCount_17",totalCount_17);
            //2018级党员数
            int totalCount_18 = count_18 + applyCount_18 + activeNum_18 + devNum_18 + positivePartyCount_18 + growPartyCount_18;
            dataMap.put("totalCount_18",totalCount_18);
            //2019级党员数
            int totalCount_19 = count_19 + applyCount_19 + activeNum_19 + devNum_19 + positivePartyCount_19 + growPartyCount_19;
            dataMap.put("totalCount_19",totalCount_19);
            //全党委党员数
            int allParty = totalCount_16+totalCount_17+totalCount_18+totalCount_19;
            dataMap.put("allParty",allParty);

            //2016培养情况占比
            int trainNum_16 = count_16 + activeNum_16 + devNum_16;
            String train_16;
            if(totalCount_16 == 0){
                train_16 = "0.0%";
            }else{
                double trainProportion_16 = new BigDecimal((float)trainNum_16/totalCount_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                train_16 = trainProportion_16+"%";
            }
            dataMap.put("train_16",train_16);
            //2017培养情况占比
            int trainNum_17 = count_17 + activeNum_17 + devNum_17;
            String train_17;
            if(totalCount_17 == 0){
                train_17 = "0.0%";
            }else{
                double trainProportion_17 = new BigDecimal((float)trainNum_17/totalCount_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                train_17 = trainProportion_17+"%";
            }
            dataMap.put("train_17",train_17);
            //2018培养情况占比
            int trainNum_18 = count_18 + activeNum_18 + devNum_18;
            String train_18;
            if(totalCount_18 == 0){
                train_18 = "0.0%";
            }else{
                double trainProportion_18 = new BigDecimal((float)trainNum_18/totalCount_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                train_18 = trainProportion_18+"%";
            }
            dataMap.put("train_18",train_18);
            //2019培养情况占比
            int trainNum_19 = count_19 + activeNum_19 + devNum_19;
            String train_19;
            if(totalCount_19 == 0){
                train_19 = "0.0%";
            }else{
                double trainProportion_19 = new BigDecimal((float)trainNum_19/totalCount_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                train_19 = trainProportion_19+"%";
            }
            dataMap.put("train_19",train_19);
            //总占比
            int trainNum = trainNum_19+trainNum_18+trainNum_17+trainNum_16;
            String train;
            if(allParty == 0){
                train = "0.0%";
            }else{
                double trainProportion = new BigDecimal((float)trainNum/allParty*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                train = trainProportion+"%";
            }
            dataMap.put("train",train);

            //2016党员占比
            int partyNum_16 = positivePartyCount_16+growPartyCount_16;
            String partyProportion_16;
            if(totalCount_16 == 0){
                partyProportion_16 = "0.0%";
            }else{
                double Proportion = new BigDecimal((float)partyNum_16/totalCount_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                partyProportion_16 = Proportion+"%";
            }
            dataMap.put("partyProportion_16",partyProportion_16);

            //2017党员占比
            int partyNum_17 = positivePartyCount_17+growPartyCount_17;
            String partyProportion_17;
            if(totalCount_17 == 0){
                partyProportion_17 = "0.0%";
            }else{
                double Proportion = new BigDecimal((float)partyNum_17/totalCount_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                partyProportion_17 = Proportion+"%";
            }
            dataMap.put("partyProportion_17",partyProportion_17);

            //2018党员占比
            int partyNum_18 = positivePartyCount_18+growPartyCount_18;
            String partyProportion_18;
            if(totalCount_18 == 0){
                partyProportion_18 = "0.0%";
            }else{
                double Proportion = new BigDecimal((float)partyNum_18/totalCount_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                partyProportion_18 = Proportion+"%";
            }
            dataMap.put("partyProportion_18",partyProportion_18);

            //2016党员占比
            int partyNum_19 = positivePartyCount_19+growPartyCount_19;
            String partyProportion_19;
            if(totalCount_19 == 0){
                partyProportion_19 = "0.0%";
            }else{
                double Proportion = new BigDecimal((float)partyNum_19/totalCount_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                partyProportion_19 = Proportion+"%";
            }
            dataMap.put("partyProportion_19",partyProportion_19);

            //总占比
            int partyNum = partyNum_16+partyNum_17+partyNum_18+partyNum_19;
            String partyProportion;
            if(allParty == 0){
                partyProportion = "0.0%";
            }else{
                double Proportion = new BigDecimal((float)partyNum/allParty*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                partyProportion = Proportion+"%";
            }
            dataMap.put("partyProportion",partyProportion);



            dataMap.put("partyName",party.getShortName());
            data.add(dataMap);
            modelMap.put("data",data);
        }
        modelMap.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));
        return modelMap;

    }

    public XSSFWorkbook statOwBksInfoExport(ModelMap modelMap)throws IOException {
        InputStream is = getClass().getResourceAsStream("/xlsx/analysis/stat_ow_bks_info.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);
        renderSheetData(wb,modelMap); // 汇总
        wb.removeSheetAt(0);
        return wb;
    }
    public XSSFWorkbook statPartyBksInfoExport(ModelMap modelMap)throws IOException {
        InputStream is = getClass().getResourceAsStream("/xlsx/analysis/stat_party_bks_info.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);
        renderPartySheetData(wb,modelMap); // 汇总
        wb.removeSheetAt(0);
        return wb;

    }


    private void renderSheetData(XSSFWorkbook wb, ModelMap modelMap) {
        XSSFSheet sheet = wb.cloneSheet(0, null);
        XSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(true); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE); //纸张

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName())
                .replace("date",DateUtils.formatDate(new Date(),DateUtils.YYYY_MM));
        cell.setCellValue(str);

        cell.setCellValue(str);
        //总数
        row = sheet.getRow(2);
        cell = row.getCell(1);
        cell.setCellValue((int)modelMap.get("studentNum"));
        cell = row.getCell(2);
        cell.setCellValue((int)modelMap.get("positivePartyNum"));
        cell = row.getCell(3);
        cell.setCellValue((int)modelMap.get("growPartyNum"));
        cell = row.getCell(4);
        cell.setCellValue((int)modelMap.get("count"));
        cell = row.getCell(5);
        cell.setCellValue(modelMap.get("Proportion").toString());
        cell = row.getCell(6);
        cell.setCellValue((int)modelMap.get("totalNum"));
        cell = row.getCell(7);
        cell.setCellValue(modelMap.get("applyStr").toString());
        cell = row.getCell(8);
        cell.setCellValue((int)modelMap.get("activeNum"));
        cell = row.getCell(9);
        cell.setCellValue(modelMap.get("activeStr").toString());
        cell = row.getCell(10);
        cell.setCellValue((int)modelMap.get("devNum"));
        cell = row.getCell(11);
        cell.setCellValue(modelMap.get("devStr").toString());

        //2020级
        int fake = 0;
        row = sheet.getRow(4);
        cell = row.getCell(1);
        cell.setCellValue(fake);
        cell = row.getCell(2);
        cell.setCellValue(fake);
        cell = row.getCell(3);
        cell.setCellValue(fake);
        cell = row.getCell(4);
        cell.setCellValue(fake);
        cell = row.getCell(5);
        cell.setCellValue(fake);
        cell = row.getCell(6);
        cell.setCellValue(fake);
        cell = row.getCell(7);
        cell.setCellValue(fake);
        cell = row.getCell(8);
        cell.setCellValue(fake);
        cell = row.getCell(9);
        cell.setCellValue(fake);
        cell = row.getCell(10);
        cell.setCellValue(fake);
        cell = row.getCell(11);
        cell.setCellValue(fake);

        //2019级
        row = sheet.getRow(5);
        cell = row.getCell(1);
        cell.setCellValue((int)modelMap.get("studentNum_19"));
        cell = row.getCell(2);
        cell.setCellValue((int)modelMap.get("positivePartyNum_19"));
        cell = row.getCell(3);
        cell.setCellValue((int)modelMap.get("growPartyNum_19"));
        cell = row.getCell(4);
        cell.setCellValue((int)modelMap.get("count_19"));
        cell = row.getCell(5);
        cell.setCellValue(modelMap.get("partyStr_19").toString());
        cell = row.getCell(6);
        cell.setCellValue((int)modelMap.get("totalNum_19"));
        cell = row.getCell(7);
        cell.setCellValue(modelMap.get("applyStr_19").toString());
        cell = row.getCell(8);
        cell.setCellValue((int)modelMap.get("activeNum_19"));
        cell = row.getCell(9);
        cell.setCellValue(modelMap.get("activeStr_19").toString());
        cell = row.getCell(10);
        cell.setCellValue((int)modelMap.get("devNum_19"));
        cell = row.getCell(11);
        cell.setCellValue(modelMap.get("devStr_19").toString());

        //2018级
        row = sheet.getRow(6);
        cell = row.getCell(1);
        cell.setCellValue((int)modelMap.get("studentNum_18"));
        cell = row.getCell(2);
        cell.setCellValue((int)modelMap.get("positivePartyNum_18"));
        cell = row.getCell(3);
        cell.setCellValue((int)modelMap.get("growPartyNum_18"));
        cell = row.getCell(4);
        cell.setCellValue((int)modelMap.get("count_18"));
        cell = row.getCell(5);
        cell.setCellValue(modelMap.get("partyStr_18").toString());
        cell = row.getCell(6);
        cell.setCellValue((int)modelMap.get("totalNum_18"));
        cell = row.getCell(7);
        cell.setCellValue(modelMap.get("applyStr_18").toString());
        cell = row.getCell(8);
        cell.setCellValue((int)modelMap.get("activeNum_18"));
        cell = row.getCell(9);
        cell.setCellValue(modelMap.get("activeStr_18").toString());
        cell = row.getCell(10);
        cell.setCellValue((int)modelMap.get("devNum_18"));
        cell = row.getCell(11);
        cell.setCellValue(modelMap.get("devStr_18").toString());

        //2017级
        row = sheet.getRow(7);
        cell = row.getCell(1);
        cell.setCellValue((int)modelMap.get("studentNum_17"));
        cell = row.getCell(2);
        cell.setCellValue((int)modelMap.get("positivePartyNum_17"));
        cell = row.getCell(3);
        cell.setCellValue((int)modelMap.get("growPartyNum_17"));
        cell = row.getCell(4);
        cell.setCellValue((int)modelMap.get("count_17"));
        cell = row.getCell(5);
        cell.setCellValue(modelMap.get("partyStr_17").toString());
        cell = row.getCell(6);
        cell.setCellValue((int)modelMap.get("totalNum_17"));
        cell = row.getCell(7);
        cell.setCellValue(modelMap.get("applyStr_17").toString());
        cell = row.getCell(8);
        cell.setCellValue((int)modelMap.get("activeNum_17"));
        cell = row.getCell(9);
        cell.setCellValue(modelMap.get("activeStr_17").toString());
        cell = row.getCell(10);
        cell.setCellValue((int)modelMap.get("devNum_17"));
        cell = row.getCell(11);
        cell.setCellValue(modelMap.get("devStr_17").toString());

        //2016级
        row = sheet.getRow(8);
        cell = row.getCell(1);
        cell.setCellValue((int)modelMap.get("studentNum_16"));
        cell = row.getCell(2);
        cell.setCellValue((int)modelMap.get("positivePartyNum_16"));
        cell = row.getCell(3);
        cell.setCellValue((int)modelMap.get("growPartyNum_16"));
        cell = row.getCell(4);
        cell.setCellValue((int)modelMap.get("count_16"));
        cell = row.getCell(5);
        cell.setCellValue(modelMap.get("partyStr_16").toString());
        cell = row.getCell(6);
        cell.setCellValue((int)modelMap.get("totalNum_16"));
        cell = row.getCell(7);
        cell.setCellValue(modelMap.get("applyStr_16").toString());
        cell = row.getCell(8);
        cell.setCellValue((int)modelMap.get("activeNum_16"));
        cell = row.getCell(9);
        cell.setCellValue(modelMap.get("activeStr_16").toString());
        cell = row.getCell(10);
        cell.setCellValue((int)modelMap.get("devNum_16"));
        cell = row.getCell(11);
        cell.setCellValue(modelMap.get("devStr_16").toString());

    }

    private void renderPartySheetData(XSSFWorkbook wb, ModelMap modelMap) {
        XSSFSheet sheet = wb.cloneSheet(0, null);

        XSSFCellStyle style = wb.createCellStyle();

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);


        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName())
                .replace("date",DateUtils.formatDate(new Date(),DateUtils.YYYY_MM));
        cell.setCellValue(str);

        List<Map<String, Object>> data = (List<Map<String, Object>>) modelMap.get("data");


        cell.setCellValue(str);
        int startRow = 1;
        for (int i = 0; i <data.size(); i++) {
            Map<String, Object> map = data.get(i);

            int fake = 0;
            startRow++;
            row = sheet.createRow(startRow);
            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("往届");
            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(fake);
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(fake);
            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue(fake);
            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue(fake);
            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue(fake);
            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue(fake);
            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue(fake);
            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(fake);
            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue(fake);

            startRow++;
            row = sheet.createRow(startRow);
            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("2016");
            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("count_16"));
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("activeNum_16"));
            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("devNum_16"));
            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("positivePartyCount_16"));
            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("growPartyCount_16"));
            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue("-");
            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("totalCount_16"));
            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("train_16").toString());
            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("partyProportion_16").toString());

            startRow++;
            row = sheet.createRow(startRow);
            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("2017");
            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("count_17"));
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("activeNum_17"));
            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("devNum_17"));
            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("positivePartyCount_17"));
            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("growPartyCount_17"));
            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue("-");
            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("totalCount_17"));
            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("train_17").toString());
            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("partyProportion_17").toString());

            startRow++;
            row = sheet.createRow(startRow);
            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("2018");
            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("count_18"));
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("activeNum_18"));
            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("devNum_18"));
            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("positivePartyCount_18"));
            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("growPartyCount_18"));
            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue("-");
            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("totalCount_18"));
            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("train_18").toString());
            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("partyProportion_18").toString());

            startRow++;
            row = sheet.createRow(startRow);
            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("2019");
            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("count_19"));
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("activeNum_19"));
            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("devNum_19"));
            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("positivePartyCount_19"));
            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("growPartyCount_19"));
            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue("-");
            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("totalCount_19"));
            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("train_19").toString());
            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("partyProportion_19").toString());

            startRow++;
            row = sheet.createRow(startRow);
            cell = row.createCell(0);
            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("合计");
            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("totalCount"));
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("activeTotalCount"));
            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("devTotalCount"));
            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("positivePartyTotalCount"));
            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("growPartyTotalCount"));
            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue("-");
            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("allParty"));
            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("train").toString());
            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue(map.get("partyProportion").toString());

            for (int j=startRow-4;j<startRow;j++){
                sheet.getRow(j).createCell(0);
            }

            row = sheet.getRow(startRow-5);
            cell = row.createCell(0);
            cell.setCellValue(map.get("partyName").toString());
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(startRow-5, 0, startRow, 0));


        }

    }

    public static int getCount(List<StatByteBean> beans){
        int count = 0;
        for (StatByteBean statByteBean : beans) {
            //为空或为2的是本科生
            if (statByteBean.getGroupBy() == null || statByteBean.getGroupBy() == SystemConstants.STUDENT_TYPE_BKS) {
                count = statByteBean.getNum() + count;
                continue;
            }
        }
        return count;
    }


}
