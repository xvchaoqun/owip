package service.analysis;

import bean.StatByteBean;
import bean.StatIntBean;
import bean.StatOwInfoBean;
import domain.base.MetaType;
import domain.party.Party;
import domain.sys.SysUserExample;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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

import static sys.utils.DateUtils.YYYYMMDD_DOT;
import static sys.utils.DateUtils.YYYY_MM;

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
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_SS) {  //硕士研究生
                        masters.put("preparedMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_BS) {   //博士研究生
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
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_SS) {  //硕士研究生
                        masters.put("formalMembers", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_BS) {   //博士研究生
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
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_SS) {  //硕士研究生
                        if (obj.getNum() > 0) {
                            mastersApplyTotal += obj.getNum();
                        }
                    }
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_BS) {   //博士研究生
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
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_SS) {  //硕士研究生
                        mastersApplyTotal += obj.getNum();
                    }
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_BS) {   //博士研究生
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
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_SS) {  //硕士研究生
                        masters.put("activityTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_BS) {   //博士研究生
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
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_SS) {  //硕士研究生
                        masters.put("developTotal", obj.getNum() > 0 ? String.valueOf(obj.getNum()) : "0");
                    }
                    if (obj.getGroupBy() == SystemConstants.USER_TYPE_BS) {   //博士研究生
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
        List<StatByteBean> preparedMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, null);
        // 正式党员
        List<StatByteBean> formalMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, null);
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
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andTypeEqualTo(SystemConstants.USER_TYPE_SS);
        int countMasters = (int) sysUserMapper.countByExample(sysUserExample);
        sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andTypeEqualTo(SystemConstants.USER_TYPE_BS);
        int countDoctors = (int) sysUserMapper.countByExample(sysUserExample);

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
            List<StatByteBean> preparedMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, p.getId(), null, null);
            // 正式党员
            List<StatByteBean> formalMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, p.getId(), null, null);
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
            data.add(masters);
            data.add(doctors);
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
    public Map getOwBksInfo(Byte cls) {
        Map map = new HashMap();

        //2019级本科生
        List<StatByteBean> statByteBeans_19 = statOwInfoMapper.selectUser_groupByLevel("2019");
        int studentNum_19 = getCount(statByteBeans_19);
        map.put("studentNum_19",studentNum_19);

        //2019正式党员
        List<StatByteBean> positivePartyList_19 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null,  "2019");
        int positivePartyNum_19 = getCount(positivePartyList_19);
        map.put("positivePartyNum_19",positivePartyNum_19);
        //2019预备党员
        List<StatByteBean> growPartyList_19 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, "2019");
        int growPartyNum_19 = getCount(growPartyList_19);
        map.put("growPartyNum_19",growPartyNum_19);

        //2019党员总数
        int countNum_19 = positivePartyNum_19+growPartyNum_19;
        map.put("count_19",countNum_19);

        //占比
        double partyProportion_19 = new BigDecimal((float)countNum_19/studentNum_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr_19 = partyProportion_19+"%";
        map.put("partyStr_19",partyStr_19);

        //2019申请人数
        List<StatByteBean> applyBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT,"2019",null,null);
        int applyNum_19 = getCount(applyBeans_19);

        List<StatByteBean> applyPassBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS,"2019",null,null);
        int applyPassNum_19 = getCount(applyPassBeans_19);

        int totalNum_19 = applyNum_19+applyPassNum_19;
        map.put("totalNum_19",totalNum_19);
        //占比
        double applyProportion_19 = new BigDecimal((float)totalNum_19/studentNum_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr_19 = applyProportion_19+"%";
        map.put("applyStr_19",applyStr_19);

        //2019积极分子
        List<StatByteBean> activeBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE,"2019",null,null);
        int activeNum_19 = getCount(activeBeans_19);
        map.put("activeNum_19",activeNum_19);
        //占比
        double activeProportion_19 = new BigDecimal((float)activeNum_19/studentNum_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr_19 = activeProportion_19+"%";
        map.put("activeStr_19",activeStr_19);

        //2019发展对象
        List<StatByteBean> devBeans_19 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE,"2019",null,null);
        int devNum_19 = getCount(devBeans_19);
        map.put("devNum_19",devNum_19);
        //占比
        double devProportion_19 = new BigDecimal((float)devNum_19/studentNum_19*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr_19 = devProportion_19+"%";
        map.put("devStr_19",devStr_19);
        //2018级本科生
        List<StatByteBean> statByteBeans_18 = statOwInfoMapper.selectUser_groupByLevel("2018");
        int studentNum_18 = getCount(statByteBeans_18);
        map.put("studentNum_18",studentNum_18);
        //2018正式党员
        List<StatByteBean> positivePartyList_18 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, "2018");
        int positivePartyNum_18 = getCount(positivePartyList_18);
        map.put("positivePartyNum_18",positivePartyNum_18);
        //2018预备党员
        List<StatByteBean> growPartyList_18 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, "2018");
        int growPartyNum_18 = getCount(growPartyList_18);
        map.put("growPartyNum_18",growPartyNum_18);
        //2018党员总数
        int countNum_18 = positivePartyNum_18+growPartyNum_18;
        map.put("count_18",countNum_18);
        //占比
        double partyProportion_18 = new BigDecimal((float)countNum_18/studentNum_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr_18 = partyProportion_18+"%";
        map.put("partyStr_18",partyStr_18);
        //2018申请人数
        List<StatByteBean> applyBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT,"2018",null,null);
        int applyNum_18 = getCount(applyBeans_18);

        List<StatByteBean> applyPassBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS,"2018",null,null);
        int applyPassNum_18 = getCount(applyPassBeans_18);

        int totalNum_18 = applyNum_18+applyPassNum_18;
        map.put("totalNum_18",totalNum_18);
        //占比
        double applyProportion_18 = new BigDecimal((float)totalNum_18/studentNum_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr_18 = applyProportion_18+"%";
        map.put("applyStr_18",applyStr_18);

        //2018积极分子
        List<StatByteBean> activeBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE,"2018",null,null);
        int activeNum_18 = getCount(activeBeans_18);
        map.put("activeNum_18",activeNum_18);
        //占比
        double activeProportion_18 = new BigDecimal((float)activeNum_18/studentNum_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr_18 = activeProportion_18+"%";
        map.put("activeStr_18",activeStr_18);

        //2018发展对象
        List<StatByteBean> devBeans_18 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE,"2018",null,null);
        int devNum_18 = getCount(devBeans_18);
        map.put("devNum_18",devNum_18);
        //占比
        double devProportion_18 = new BigDecimal((float)devNum_18/studentNum_18*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr_18 = devProportion_18+"%";
        map.put("devStr_18",devStr_18);


        //2017级本科生
        List<StatByteBean> statByteBeans_17 = statOwInfoMapper.selectUser_groupByLevel("2017");
        int studentNum_17 = getCount(statByteBeans_17);
        map.put("studentNum_17",studentNum_17);

        //2017正式党员
        List<StatByteBean> positivePartyList_17 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, "2017");
        int positivePartyNum_17 = getCount(positivePartyList_17);
        map.put("positivePartyNum_17",positivePartyNum_17);
        //2017预备党员
        List<StatByteBean> growPartyList_17 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, "2017");
        int growPartyNum_17 = getCount(growPartyList_17);
        map.put("growPartyNum_17",growPartyNum_17);
        //2017党员总数
        int countNum_17 = positivePartyNum_17+growPartyNum_17;
        map.put("count_17",countNum_17);

        //占比
        double partyProportion_17 = new BigDecimal((float)countNum_17/studentNum_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr_17 = partyProportion_17+"%";
        map.put("partyStr_17",partyStr_17);

        //2017申请人数
        List<StatByteBean> applyBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT,"2017",null,null);
        int applyNum_17 = getCount(applyBeans_17);

        List<StatByteBean> applyPassBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS,"2017",null,null);
        int applyPassNum_17 = getCount(applyPassBeans_17);

        int totalNum_17 = applyNum_17+applyPassNum_17;
        map.put("totalNum_17",totalNum_17);
        //占比
        double applyProportion_17 = new BigDecimal((float)totalNum_17/studentNum_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr_17 = applyProportion_17+"%";
        map.put("applyStr_17",applyStr_17);

        //2017积极分子
        List<StatByteBean> activeBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE,"2017",null,null);
        int activeNum_17 = getCount(activeBeans_17);
        map.put("activeNum_17",activeNum_17);
        //占比
        double activeProportion_17 = new BigDecimal((float)activeNum_17/studentNum_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr_17 = activeProportion_17+"%";
        map.put("activeStr_17",activeStr_17);

        //2017发展对象
        List<StatByteBean> devBeans_17 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE,"2017",null,null);
        int devNum_17 = getCount(devBeans_17);
        map.put("devNum_17",devNum_17);
        //占比
        double devProportion_17 = new BigDecimal((float)devNum_17/studentNum_17*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr_17 = devProportion_17+"%";
        map.put("devStr_17",devStr_17);


        //2016级本科生
        List<StatByteBean> statByteBeans_16 = statOwInfoMapper.selectUser_groupByLevel("2016");
        int studentNum_16 = getCount(statByteBeans_16);
        map.put("studentNum_16",studentNum_16);

        //2016正式党员
        List<StatByteBean> positivePartyList_16 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null, "2016");
        int positivePartyNum_16 = getCount(positivePartyList_16);
        map.put("positivePartyNum_16",positivePartyNum_16);
        //2016预备党员
        List<StatByteBean> growPartyList_16 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null, "2016");
        int growPartyNum_16 = getCount(growPartyList_16);
        map.put("growPartyNum_16",growPartyNum_16);
        //2016党员总数
        int countNum_16 = positivePartyNum_16+growPartyNum_16;
        map.put("count_16",countNum_16);
        //占比
        double partyProportion_16 = new BigDecimal((float)countNum_16/studentNum_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr_16 = partyProportion_16+"%";
        map.put("partyStr_16",partyStr_16);

        //2016申请人数
        List<StatByteBean> applyBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT,"2016",null,null);
        int applyNum_16 = getCount(applyBeans_16);

        List<StatByteBean> applyPassBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS,"2016",null,null);
        int applyPassNum_16 = getCount(applyPassBeans_16);

        int totalNum_16 = applyNum_16+applyPassNum_16;
        map.put("totalNum_16",totalNum_16);
        //占比
        double applyProportion_16 = new BigDecimal((float)totalNum_16/studentNum_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr_16 = applyProportion_16+"%";
        map.put("applyStr_16",applyStr_16);

        //2016积极分子
        List<StatByteBean> activeBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE,"2016",null,null);
        int activeNum_16 = getCount(activeBeans_16);
        map.put("activeNum_16",activeNum_16);
        //占比
        double activeProportion_16 = new BigDecimal((float)activeNum_16/studentNum_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr_16 = activeProportion_16+"%";
        map.put("activeStr_16",activeStr_16);

        //2016发展对象
        List<StatByteBean> devBeans_16 = statMemberMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE,"2016",null,null);
        int devNum_16 = getCount(devBeans_16);
        map.put("devNum_16",devNum_16);
        //占比
        double devProportion_16 = new BigDecimal((float)devNum_16/studentNum_16*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr_16 = devProportion_16+"%";
        map.put("devStr_16",devStr_16);

        //所有本科学生
        int studentNumBks = studentNum_19+studentNum_18+studentNum_17+studentNum_16;
        map.put("studentNum",studentNumBks);
        //正式党员
        int positivePartyNum = positivePartyNum_19+positivePartyNum_18+positivePartyNum_17+positivePartyNum_16;
        map.put("positivePartyNum",positivePartyNum);
        //预备党员
        int growPartyNum = growPartyNum_19+growPartyNum_18+growPartyNum_17+growPartyNum_16;
        map.put("growPartyNum",growPartyNum);
        //党员总数
        int countNum = positivePartyNum+growPartyNum;
        map.put("count",countNum);
        //占比
        double partyProportion = new BigDecimal((float)countNum/studentNumBks*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String partyStr = partyProportion+"%";
        map.put("Proportion",partyStr);
        //入党申请人数
        int totalNum = totalNum_19+totalNum_18+totalNum_17+totalNum_16;
        map.put("totalNum",totalNum);
        //占比
        double applyProportion = new BigDecimal((float)totalNum/studentNumBks*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String applyStr = applyProportion+"%";
        map.put("applyStr",applyStr);
        //入党积极分子
        int activeNum = activeNum_19+activeNum_18+activeNum_17+activeNum_16;
        map.put("activeNum",activeNum);
        //占比
        double activeProportion = new BigDecimal((float)activeNum/studentNumBks*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String activeStr = activeProportion+"%";
        map.put("activeStr",activeStr);
        //发展对象
        int devNum = devNum_19+devNum_18+devNum_17+devNum_16;
        map.put("devNum",devNum);
        //占比
        double devProportion = new BigDecimal((float)devNum/studentNumBks*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String devStr = devProportion+"%";
        map.put("devStr",devStr);

        map.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));
        return map;
    }

    @Cacheable(value="statOwInfo",key = "#cls")
    public Map getPartyBksInfo(Byte cls) {

        List<Party> partyNameList = statOwInfoMapper.getSecondPartyName();
        List<Map<String, String>> data = new ArrayList<>();
        Map map = new HashMap();
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
            List<StatByteBean> positivePartyBeans_19 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, party.getId(), null, "2019");
            int positivePartyCount_19 = getCount(positivePartyBeans_19);
            dataMap.put("positivePartyCount_19", positivePartyCount_19);
            //2018级正式党员
            List<StatByteBean> positivePartyBeans_18 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, party.getId(), null, "2018");
            int positivePartyCount_18 = getCount(positivePartyBeans_18);
            dataMap.put("positivePartyCount_18", positivePartyCount_18);
            //2017级正式党员
            List<StatByteBean> positivePartyBeans_17 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, party.getId(), null, "2017");
            int positivePartyCount_17 = getCount(positivePartyBeans_17);
            dataMap.put("positivePartyCount_17", positivePartyCount_17);
            //2016级正式党员
            List<StatByteBean> positivePartyBeans_16 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, party.getId(), null, "2016");
            int positivePartyCount_16 = getCount(positivePartyBeans_16);
            dataMap.put("positivePartyCount_16", positivePartyCount_16);
            //正式党员总和
            int positivePartyTotalCount = positivePartyCount_16 + positivePartyCount_17 + positivePartyCount_18 + positivePartyCount_19;
            dataMap.put("positivePartyTotalCount", positivePartyTotalCount);


            //2019级预备党员
            List<StatByteBean> growPartyBeans_19 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, party.getId(), null, "2019");
            int growPartyCount_19 = getCount(growPartyBeans_19);
            dataMap.put("growPartyCount_19", growPartyCount_19);
            //2018级预备党员
            List<StatByteBean> growPartyBeans_18 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, party.getId(), null, "2018");
            int growPartyCount_18 = getCount(growPartyBeans_18);
            dataMap.put("growPartyCount_18", growPartyCount_18);
            //2017级预备党员
            List<StatByteBean> growPartyBeans_17 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, party.getId(), null, "2017");
            int growPartyCount_17 = getCount(growPartyBeans_17);
            dataMap.put("growPartyCount_17", growPartyCount_17);
            //2016级预备党员
            List<StatByteBean> growPartyBeans_16 = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, party.getId(), null, "2016");
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
            map.put("data",data);
        }
        map.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));
        return map;

    }

    @Cacheable(value="statOwInfo",key = "#cls")
    public Map getPartyBranchInfo(Byte cls) {

        List<Party> partyNameList = statOwInfoMapper.getSecondPartyName();
        Map map = new HashedMap();
        List<Map<String,Object>> dataList = new ArrayList<>();

        MetaType mtUndergraduate = CmTag.getMetaTypeByCode("mt_undergraduate_assistant");//本科生辅导员纵向党支部
        MetaType mtSsGraduate = CmTag.getMetaTypeByCode("mt_ss_graduate");//硕士研究生党支部
        MetaType mtBsGraduate = CmTag.getMetaTypeByCode("mt_bs_graduate");//博士研究生党支部
        MetaType mtSbGraduate = CmTag.getMetaTypeByCode("mt_sb_graduate");//硕博研究生党支部
        MetaType mtGraduateTeacher = CmTag.getMetaTypeByCode("mt_graduate_teacher");//研究生导师纵向党支部
        MetaType mtProfessional = CmTag.getMetaTypeByCode("mt_professional_teacher");//专任教师党支部
        MetaType mtRetire = CmTag.getMetaTypeByCode("mt_retire");//离退休党支部
        MetaType mtSupportTeacher = CmTag.getMetaTypeByCode("mt_support_teacher");//机关行政产业后勤党支部
        MetaType mtBranchSecretary = CmTag.getMetaTypeByCode("mt_branch_secretary");//支部书记

        Integer mtUnderGraduateId = mtUndergraduate == null ? 0 : mtUndergraduate.getId();
        Integer mtSsGraduateId = mtSsGraduate == null ? 0 : mtSsGraduate.getId();
        Integer mtBsGraduateId = mtBsGraduate == null ? 0 : mtBsGraduate.getId();
        Integer mtSbGraduateId = mtSbGraduate == null ? 0 : mtSbGraduate.getId();
        Integer mtGraduateTeacherId = mtGraduateTeacher == null ? 0 : mtGraduateTeacher.getId();
        Integer mtProfessionalId = mtProfessional == null ? 0 : mtProfessional.getId();
        Integer mtRetireId = mtRetire == null ? 0 : mtRetire.getId();
        Integer mtSupportTeacherId = mtSupportTeacher == null ? 0 : mtSupportTeacher.getId();
        Integer mtBranchSecretaryId = mtBranchSecretary == null ? 0 : mtBranchSecretary.getId();

        int totalUndergraduateNum = 0 ;//本科生辅导员总数
        int totalSsGraduateNum = 0 ; //硕士研究生总数
        int totalBsGraduateNum = 0 ; //博士研究生总数
        int totalSbGraduateNum = 0 ; //硕博研究生总数
        int totalYjsNum = 0;//研究生总数之和

        //研究生导师纵向党支部正高，副高，中级及以下总数
        int totalDirectorYjsNum = 0;
        int totalDeputyNum = 0;
        int totalIntermediate = 0;
        int totalYjsTecher = 0;
        //专任教师正高，副高,中级及以下总数
        int totalDirectorTeacher = 0;
        int totalDeputyTeacher = 0;
        int totalIntermediateTeacher = 0;
        int totalFulltimeTecher = 0;
        //离退休总数
        int totalRetireNum = 0;
        //机关行政产业后勤总数
        int totalSupportNum = 0;
        //所有支部书记总数
        int allNum = 0;
        for (Party party : partyNameList) {
            Map dataMap = new HashedMap();

            List<StatIntBean> statByteBeans = statOwInfoMapper.branchCount_groupByType(mtBranchSecretaryId.byteValue(), null, party.getId());
            List<StatIntBean> directorBeans = statOwInfoMapper.branchCount_groupByType(mtBranchSecretaryId.byteValue(), "正高", party.getId());
            List<StatIntBean> deputyBeans = statOwInfoMapper.branchCount_groupByType(mtBranchSecretaryId.byteValue(),"副高", party.getId());

            Integer undergraduateNum = 0;
            int yjsTecherNum = 0;
            int professionalTeacherNum = 0;
            Integer ssGraduateNum = 0;
            Integer bsGraduateNum = 0;
            Integer sbGraduateNum = 0;
            Integer retireNum = 0;
            Integer supportTeacherNum = 0;
            for (StatIntBean statIntBean : statByteBeans) {
                Integer groupBy = statIntBean.getGroupBy();
                if (groupBy != null) {
                    if (groupBy.equals(mtUnderGraduateId)) {
                        undergraduateNum = statIntBean.getNum();//本科生辅导员纵向党支部
                        totalUndergraduateNum += undergraduateNum;
                    } else if (groupBy.equals(mtSsGraduateId)) {
                        ssGraduateNum = statIntBean.getNum();//硕士研究生党支部
                        totalSsGraduateNum += ssGraduateNum;
                    } else if (groupBy.equals(mtBsGraduateId)) {
                        bsGraduateNum = statIntBean.getNum();//博士研究生党支部
                        totalBsGraduateNum += bsGraduateNum;
                    } else if (groupBy.equals(mtSbGraduateId)) {
                        sbGraduateNum = statIntBean.getNum();//硕博研究生党支部
                        totalSbGraduateNum += sbGraduateNum;
                    } else if (groupBy.equals(mtRetireId)) {
                        retireNum = statIntBean.getNum();//离退休党支部
                        totalRetireNum += retireNum;
                    } else if (groupBy.equals(mtSupportTeacherId)) {
                        supportTeacherNum = statIntBean.getNum();//机关行政产业后勤党支部
                        totalSupportNum += supportTeacherNum;
                    } else if (groupBy.equals(mtGraduateTeacherId)) {
                        yjsTecherNum = statIntBean.getNum();//研究生导师纵向党支部
                    } else if (groupBy.equals(mtProfessionalId)) {
                        professionalTeacherNum = statIntBean.getNum();
                    }
                }
            }
            dataMap.put("undergraduateNum",undergraduateNum);
            dataMap.put("ssGraduateNum",ssGraduateNum);
            dataMap.put("bsGraduateNum",bsGraduateNum);
            dataMap.put("sbGraduateNum",sbGraduateNum);
            dataMap.put("retireNum",retireNum);
            dataMap.put("supportTeacherNum",supportTeacherNum);

            //研究生党支部合计
            int yjsTotal = ssGraduateNum+bsGraduateNum+sbGraduateNum;
            totalYjsNum+=yjsTotal;
            dataMap.put("yjsTotal",yjsTotal);
            //党支部书记总数
            int rowTotalCount = undergraduateNum+yjsTotal+yjsTecherNum+professionalTeacherNum+retireNum+supportTeacherNum;
            allNum+=rowTotalCount;
            dataMap.put("rowTotalCount",rowTotalCount);
            //研究生导师纵向党支部正高，副高级
            Integer directorYjsNum = 0;
            Integer deputyNum = 0;
            //专任教师正高，副高
            Integer directorTeacherNum = 0;
            Integer deputyTeacherNum = 0;

            for (StatIntBean directorBean : directorBeans) {
                if(mtGraduateTeacherId.equals(directorBean.getGroupBy())){
                    directorYjsNum = directorBean.getNum();//研究生导师党支部正高级
                    totalDirectorYjsNum+=directorYjsNum;
                }else if(mtProfessionalId.equals(directorBean.getGroupBy())){
                    directorTeacherNum = directorBean.getNum();//专任教师党支部正高级
                    totalDirectorTeacher+=directorTeacherNum;
                }
            }

            for (StatIntBean deputyBean : deputyBeans) {
                if(mtGraduateTeacherId.equals(deputyBean.getGroupBy())){
                    deputyNum = deputyBean.getNum();//研究生导师党支部副高级
                    totalDeputyNum+=deputyNum;
                }else if(mtProfessionalId.equals(deputyBean.getGroupBy())){
                    deputyTeacherNum = deputyBean.getNum();//专任教师党支部副高级
                    totalDeputyTeacher+=deputyTeacherNum;
                }
            }

            //研究生导师纵向党支部中级及以下
            Integer intermediateNum = yjsTecherNum-directorYjsNum-deputyNum;
            totalIntermediate+=intermediateNum;
            //专任教师中级及以下
            Integer intermediateTeacher = professionalTeacherNum-directorTeacherNum-deputyTeacherNum;
            totalIntermediateTeacher+=intermediateTeacher;
            //研究生导师总数之和
            totalYjsTecher+=yjsTecherNum;
            //专任教师总数之和
            totalFulltimeTecher+=professionalTeacherNum;

            dataMap.put("directorYjsNum",directorYjsNum == null ? "" :directorYjsNum);
            dataMap.put("deputyNum",deputyNum == null ? "" :deputyNum);
            dataMap.put("intermediateNum",intermediateNum == null ? "" :intermediateNum);
            dataMap.put("yjsTecherNum",yjsTecherNum);
            dataMap.put("directorTeacherNum",directorTeacherNum == null ? "" :directorTeacherNum);
            dataMap.put("deputyTeacherNum",deputyTeacherNum == null ? "" :deputyTeacherNum);
            dataMap.put("intermediateTeacher",intermediateTeacher == null ? "" :intermediateTeacher);
            dataMap.put("professionalTeacherNum",professionalTeacherNum);

            dataMap.put("partyName",party.getShortName());
            dataList.add(dataMap);
        }
        map.put("dataList",dataList);
        map.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));
        map.put("totalUndergraduateNum",totalUndergraduateNum);
        map.put("totalSsGraduateNum",totalSsGraduateNum);
        map.put("totalBsGraduateNum",totalBsGraduateNum);
        map.put("totalSbGraduateNum",totalSbGraduateNum);
        map.put("totalYjsNum",totalYjsNum);
        map.put("totalDirectorYjsNum",totalDirectorYjsNum);
        map.put("totalDeputyNum",totalDeputyNum);
        map.put("totalIntermediate",totalIntermediate);
        map.put("totalYjsTecher",totalYjsTecher);
        map.put("totalDirectorTeacher",totalDirectorTeacher);
        map.put("totalDeputyTeacher",totalDeputyTeacher);
        map.put("totalIntermediateTeacher",totalIntermediateTeacher);
        map.put("totalFulltimeTecher",totalFulltimeTecher);
        map.put("totalRetireNum",totalRetireNum);
        map.put("totalSupportNum",totalSupportNum);
        map.put("allNum",allNum);
        return map;
    }

    @Cacheable(value="statOwInfo",key = "#cls")
    public Map getGrassrootsPartyInfo(Byte cls) {
        List<Party> partyNameList = statOwInfoMapper.getSecondPartyName();
        Map map = new HashedMap();
        List<Map<String,Object>> dataList = new ArrayList<>();

        MetaType mtUndergraduate = CmTag.getMetaTypeByCode("mt_undergraduate_assistant");//本科生辅导员纵向党支部
        MetaType mtSsGraduate = CmTag.getMetaTypeByCode("mt_ss_graduate");//硕士研究生党支部
        MetaType mtBsGraduate = CmTag.getMetaTypeByCode("mt_bs_graduate");//博士研究生党支部
        MetaType mtSbGraduate = CmTag.getMetaTypeByCode("mt_sb_graduate");//硕博研究生党支部
        MetaType mtGraduateTeacher = CmTag.getMetaTypeByCode("mt_graduate_teacher");//研究生导师纵向党支部
        MetaType mtProfessional = CmTag.getMetaTypeByCode("mt_professional_teacher");//专任教师党支部
        MetaType mtRetire = CmTag.getMetaTypeByCode("mt_retire");//离退休党支部
        MetaType mtSupportTeacher = CmTag.getMetaTypeByCode("mt_support_teacher");//机关行政产业后勤党支部

        Integer mtUndergraduateId = mtUndergraduate == null ? 0 : mtUndergraduate.getId();
        Integer mtSsGraduateId = mtSsGraduate == null ? 0 : mtSsGraduate.getId();
        Integer mtBsGraduateId = mtBsGraduate == null ? 0 : mtBsGraduate.getId();
        Integer mtSbGraduateId = mtSbGraduate == null ? 0 : mtSbGraduate.getId();
        Integer mtGraduateTeacherId = mtGraduateTeacher == null ? 0 : mtGraduateTeacher.getId();
        Integer mtProfessionalId = mtProfessional == null ? 0 : mtProfessional.getId();
        Integer mtRetireId = mtRetire == null ? 0 : mtRetire.getId();
        Integer mtSupportTeacherId = mtSupportTeacher == null ? 0 : mtSupportTeacher.getId();

        // 预备党员总数
        int totalPreparedNum = 0;
        //正式党员总数
        int totalFormal = 0;
        //申请入党总数
        int totalApply = 0;
        //积极分子总数
        int totalActivists = 0;
        //发展对象总数
        int totalDevelopment = 0;
        int allNum = 0;
        Date nowDate = new Date();
        int rowNum=0;
        for (Party party : partyNameList) {
            List<StatOwInfoBean> statOwInfoBeans = statOwInfoMapper.getParty_Branch(party.getId());
            for (StatOwInfoBean statOwInfoBean : statOwInfoBeans) {
                Map dataMap = new HashedMap();
                //序号
                rowNum++;
                dataMap.put("rowNum",rowNum);
                //二级党组织名称
                dataMap.put("partyName",party.getShortName() == null ? "" :party.getShortName());
                //党支部名称
                String branchName = statOwInfoBean.getName() == null ? "" :statOwInfoBean.getName();
                dataMap.put("branchName",branchName);
                //支部类型
                Integer branchType = statOwInfoBean.getTypes();
                String branchTypeStr = "";
                if (branchType != null) {
                    if (branchType.equals(mtUndergraduateId)) {
                        branchTypeStr = mtUndergraduate.getName();
                    } else if (branchType.equals(mtSsGraduateId)) {
                        branchTypeStr = mtSsGraduate.getName();
                    } else if (branchType.equals(mtBsGraduateId)) {
                        branchTypeStr = mtBsGraduate.getName();
                    } else if (branchType.equals(mtSbGraduateId)) {
                        branchTypeStr = mtSbGraduate.getName();
                    } else if (branchType.equals(mtGraduateTeacherId)) {
                        branchTypeStr = mtGraduateTeacher.getName();
                    } else if (branchType.equals(mtProfessionalId)) {
                        branchTypeStr = mtProfessional.getName();
                    } else if (branchType.equals(mtRetireId)) {
                        branchTypeStr = mtRetire.getName();
                    } else if (branchType.equals(mtSupportTeacherId)) {
                        branchTypeStr = mtSupportTeacher.getName();
                    }
                }
                dataMap.put("branchTypeStr", branchTypeStr);

                //支部书记
                String cadreName = statOwInfoBean.getRealName();
                dataMap.put("cadreName",cadreName == null ? "" :cadreName);
                //性别
                String gender = "";
                byte cadreGender = statOwInfoBean.getGender();
                if(cadreGender == 1){
                    gender="男";
                }else if(cadreGender == 2){
                    gender ="女";
                }else{
                    gender = "";
                }
                dataMap.put("gender",gender);
                //民族
                String cadreNation = statOwInfoBean.getNation();
                dataMap.put("cadreNation",cadreNation == null ? "" :cadreNation);

                //党龄
                int partyAge = 0;
                //入党时间
                Date growTime = statOwInfoBean.getGrowTime();
                if (growTime!=null){
                    dataMap.put("growTime",DateUtils.formatDate(growTime,YYYYMMDD_DOT));
                    partyAge = DateUtils.getYear(nowDate) - DateUtils.getYear(growTime);
                }else{
                    dataMap.put("growTime","");
                }
                dataMap.put("partyAge",partyAge);

                //年龄
                int age = 0;
                //出生日期
                Date cadreBirth = statOwInfoBean.getBirth();

                if (cadreBirth!=null){
                    dataMap.put("cadreBirth",DateUtils.formatDate(cadreBirth,YYYYMMDD_DOT));
                    age = DateUtils.getYear(nowDate) - DateUtils.getYear(cadreBirth);
                }else{
                    dataMap.put("cadreBirth","");
                }
                dataMap.put("age",age);

                //职称 身份
                String cadreStatus = statOwInfoBean.getProPost();
                dataMap.put("cadreStatus",cadreStatus == null ? "" :cadreStatus);
                //职务
                String cadreDuty = statOwInfoBean.getTitle();
                dataMap.put("cadreDuty",cadreDuty == null ? "" :cadreDuty);

                // 预备党员
                List<StatByteBean> preparedMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, statOwInfoBean.getBranchId(), null);
                int preparedNum=getBranchCounting(preparedMembers);
                dataMap.put("preparedNum",preparedNum);
                totalPreparedNum+=preparedNum;
                // 正式党员
                List<StatByteBean> formalMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, statOwInfoBean.getBranchId(), null);
                int formalNum = getBranchCounting(formalMembers);
                dataMap.put("formalNum",formalNum);
                totalFormal+=formalNum;
                //申请入党人员
                List<StatByteBean> applyJoin = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT, null, null, statOwInfoBean.getBranchId());
                List<StatByteBean> passJoin = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS, null, null, statOwInfoBean.getBranchId());
                int applyNum=getBranchCounting(applyJoin)+getBranchCounting(passJoin);
                dataMap.put("applyNum",applyNum);
                totalApply+=applyNum;
                // 入党积极分子
                List<StatByteBean> countActivists = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE, null, null, statOwInfoBean.getBranchId());
                int activistsNum = getBranchCounting(countActivists);
                dataMap.put("activistsNum",activistsNum);
                totalActivists+=activistsNum;
                // 发展对象
                List<StatByteBean> countDevelopment = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE, null, null, statOwInfoBean.getBranchId());
                int developmentNum = getBranchCounting(countDevelopment);
                dataMap.put("developmentNum",developmentNum);
                totalDevelopment+=developmentNum;
                //总数
                int rowSum = preparedNum+formalNum+applyNum+activistsNum+developmentNum;
                dataMap.put("rowSum",rowSum);
                allNum+=rowSum;

                dataList.add(dataMap);
            }
        }
        //直属党支部
        MetaType mtPartySecretary = CmTag.getMetaTypeByCode("mt_party_secretary");
        MetaType mtDirectBranch = CmTag.getMetaTypeByCode("mt_direct_branch");
        List<StatOwInfoBean> statOwInfoBeans = statOwInfoMapper.getDirectlyBranch(mtDirectBranch == null ? 0 : mtDirectBranch.getId(),mtPartySecretary == null ? 0 : mtPartySecretary.getId());
        for (StatOwInfoBean statOwInfoBean : statOwInfoBeans) {
            Map dataMap = new HashedMap();
            //序号
            rowNum++;
            dataMap.put("rowNum",rowNum);

            String name = statOwInfoBean.getName();
            dataMap.put("partyName",name);

            //支部类型
            Integer branchType = statOwInfoBean.getTypes();
            String branchTypeStr = "";
            if (branchType != null) {
                if (branchType.equals(mtUndergraduateId)) {
                    branchTypeStr = mtUndergraduate.getName();
                } else if (branchType.equals(mtSsGraduateId)) {
                    branchTypeStr = mtSsGraduate.getName();
                } else if (branchType.equals(mtBsGraduateId)) {
                    branchTypeStr = mtBsGraduate.getName();
                } else if (branchType.equals(mtSbGraduateId)) {
                    branchTypeStr = mtSbGraduate.getName();
                } else if (branchType.equals(mtGraduateTeacherId)) {
                    branchTypeStr = mtGraduateTeacher.getName();
                } else if (branchType.equals(mtProfessionalId)) {
                    branchTypeStr = mtProfessional.getName();
                } else if (branchType.equals(mtRetireId)) {
                    branchTypeStr = mtRetire.getName();
                } else if (branchType.equals(mtSupportTeacherId)) {
                    branchTypeStr = mtSupportTeacher.getName();
                }
            }
            dataMap.put("branchTypeStr",branchTypeStr);

            String cadreName = statOwInfoBean.getRealName();
            dataMap.put("cadreName",cadreName == null ? "" :cadreName);

            String gender = "";
            byte cadreGender = statOwInfoBean.getGender();
            if(cadreGender == 1){
                gender="男";
            }else if(cadreGender == 2){
                gender ="女";
            }else{
                gender = "";
            }
            dataMap.put("gender",gender);

            String cadreNation = statOwInfoBean.getNation();
            dataMap.put("cadreNation",cadreNation == null ? "" :cadreNation);

            //党龄
            int partyAge = 0;
            //入党时间
            Date growTime = statOwInfoBean.getGrowTime();
            if (growTime!=null){
                dataMap.put("growTime",DateUtils.formatDate(growTime,YYYYMMDD_DOT));
                partyAge = DateUtils.getYear(nowDate) - DateUtils.getYear(growTime);
            }else{
                dataMap.put("growTime","");
            }
            dataMap.put("partyAge",partyAge);

            //年龄
            int age = 0;
            //出生日期
            Date cadreBirth = statOwInfoBean.getBirth();
            if (cadreBirth!=null){
                dataMap.put("cadreBirth",DateUtils.formatDate(cadreBirth,YYYYMMDD_DOT));
                age = DateUtils.getYear(nowDate) - DateUtils.getYear(cadreBirth);
            }else{
                dataMap.put("cadreBirth","");
            }
            dataMap.put("age",age);

            //职称 身份
            String cadreStatus = statOwInfoBean.getProPost();
            dataMap.put("cadreStatus",cadreStatus == null ? "" :cadreStatus);
            //职务
            String cadreDuty = statOwInfoBean.getTitle();
            dataMap.put("cadreDuty",cadreDuty == null ? "" :cadreDuty);

            // 预备党员
            List<StatByteBean> preparedMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, statOwInfoBean.getPartyId(), null, null);
            int preparedNum=getBranchCounting(preparedMembers);
            dataMap.put("preparedNum",preparedNum);
            totalPreparedNum+=preparedNum;
            // 正式党员
            List<StatByteBean> formalMembers = statOwInfoMapper.member_groupByType(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, statOwInfoBean.getPartyId(), null, null);
            int formalNum = getBranchCounting(formalMembers);
            dataMap.put("formalNum",formalNum);
            totalFormal+=formalNum;
            //申请入党人员
            List<StatByteBean> applyJoin = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_INIT, null, statOwInfoBean.getPartyId(), null);
            List<StatByteBean> passJoin = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_PASS, null, statOwInfoBean.getPartyId(), null);
            int applyNum=getBranchCounting(applyJoin)+getBranchCounting(passJoin);
            dataMap.put("applyNum",applyNum);
            totalApply+=applyNum;
            // 入党积极分子
            List<StatByteBean> countActivists = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_ACTIVE, null, statOwInfoBean.getPartyId(), null);
            int activistsNum = getBranchCounting(countActivists);
            dataMap.put("activistsNum",activistsNum);
            totalActivists+=activistsNum;
            // 发展对象
            List<StatByteBean> countDevelopment = statOwInfoMapper.memberApply_groupByLevel(OwConstants.OW_APPLY_STAGE_CANDIDATE, null, statOwInfoBean.getPartyId(), null);
            int developmentNum = getBranchCounting(countDevelopment);
            dataMap.put("developmentNum",developmentNum);
            totalDevelopment+=developmentNum;

            //总数
            int rowSum = preparedNum+formalNum+applyNum+activistsNum+developmentNum;
            dataMap.put("rowSum",rowSum);
            allNum+=rowSum;

            dataList.add(dataMap);
        }

        map.put("totalFormal",totalFormal);
        map.put("totalPreparedNum",totalPreparedNum);
        map.put("totalApply",totalApply);
        map.put("totalActivists",totalActivists);
        map.put("totalDevelopment",totalDevelopment);
        map.put("allNum",allNum);

        map.put("dataList",dataList);
        map.put("cacheTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_CHINA));

        return map;
    }

    public XSSFWorkbook statOwBksInfoExport(ModelMap modelMap)throws IOException {
        InputStream is = getClass().getResourceAsStream("/xlsx/analysis/stat_ow_bks_info.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);
        renderOwBksSheetData(wb,modelMap); // 汇总
        wb.removeSheetAt(0);
        return wb;
    }
    public XSSFWorkbook statPartyBksInfoExport(ModelMap modelMap)throws IOException {
        InputStream is = getClass().getResourceAsStream("/xlsx/analysis/stat_party_bks_info.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);
        renderPartyBksSheetData(wb,modelMap); // 汇总
        wb.removeSheetAt(0);
        return wb;

    }
    public XSSFWorkbook statPartyBranchInfoExport(ModelMap modelMap)throws IOException {
        InputStream is = getClass().getResourceAsStream("/xlsx/analysis/stat_party_branch_info.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);
        renderPartyBranchSheetData(wb,modelMap); // 汇总
        wb.removeSheetAt(0);
        return wb;
    }
    public HSSFWorkbook statBranchInfoExport(ModelMap modelMap)throws IOException {
        InputStream is = getClass().getResourceAsStream("/xlsx/analysis/stat_party_branch_detail.xls");
        HSSFWorkbook hb = new HSSFWorkbook(is);
        renderBranchSheetData(hb,modelMap); // 汇总
        hb.removeSheetAt(0);
        return hb;
    }

    private void renderOwBksSheetData(XSSFWorkbook wb, ModelMap modelMap) {
        XSSFSheet sheet = wb.cloneSheet(0, null);
        XSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(true); // 打印方向，true：横向，false：纵向

        Date now = new Date();
        String year = String.valueOf(DateUtils.getYear(now));
        String month = String.valueOf(DateUtils.getMonth(now));

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);
        XSSFRow dateRow = sheet.getRow(1);
        XSSFCell dateCell = dateRow.getCell(0);
        String date = dateCell.getStringCellValue()
                .replace("year",year)
                .replace("month",month);
        dateCell.setCellValue(date);

        //总数
        row = sheet.getRow(3);
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
        row = sheet.getRow(5);
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
        row = sheet.getRow(6);
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
        row = sheet.getRow(7);
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
        row = sheet.getRow(8);
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
        row = sheet.getRow(9);
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

    private void renderPartyBksSheetData(XSSFWorkbook wb, ModelMap modelMap) {
        XSSFSheet sheet = wb.cloneSheet(0, null);

        XSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        Date now = new Date();
        String year = String.valueOf(DateUtils.getYear(now));
        String month = String.valueOf(DateUtils.getMonth(now));

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);
        XSSFRow dateRow = sheet.getRow(1);
        XSSFCell dateCell = dateRow.getCell(0);
        String date = dateCell.getStringCellValue()
                .replace("year",year)
                .replace("month",month);
        dateCell.setCellValue(date);

        List<Map<String, Object>> data = (List<Map<String, Object>>) modelMap.get("data");

        cell.setCellValue(str);
        int startRow = 2;
        for (int i = 0; i <data.size(); i++) {
            Map<String, Object> map = data.get(i);

            String fake = "-";
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
            cell.setCellStyle(style);//边框
            cell.getCellStyle().setWrapText(true);//自动换行
            cell.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
            cell.getCellStyle().setVerticalAlignment(VerticalAlignment.CENTER);//居中
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(startRow-5, 0, startRow, 0));

        }

    }

    private void renderPartyBranchSheetData(XSSFWorkbook wb, ModelMap modelMap) {

        XSSFSheet sheet = wb.cloneSheet(0, null);

        XSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        style.setWrapText(true);//自动换行
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);//居中

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);

        XSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示

        List<Map<String, Object>> data = (List<Map<String, Object>>) modelMap.get("dataList");
        int startRow = 1;
        for (int i = 0; i <data.size(); i++) {
            Map<String, Object> map = data.get(i);
            startRow++;
            row = sheet.createRow(startRow);
            row.setHeightInPoints(80);//设置行高
            cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue((String) map.get("partyName"));
            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("undergraduateNum"));
            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("ssGraduateNum"));
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("bsGraduateNum"));
            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("sbGraduateNum"));
            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellFormula("C"+(startRow+1)+"+D"+(startRow+1)+"+E"+(startRow+1));
            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("directorYjsNum"));
            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("deputyNum"));
            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("intermediateNum"));
            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellFormula("G"+(startRow+1)+"+H"+(startRow+1)+"+I"+(startRow+1));
            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("directorTeacherNum"));
            cell = row.createCell(11);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("deputyTeacherNum"));
            cell = row.createCell(12);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("intermediateTeacher"));
            cell = row.createCell(13);
            cell.setCellStyle(style);
            cell.setCellFormula("K"+(startRow+1)+"+L"+(startRow+1)+"+M"+(startRow+1));
            cell = row.createCell(14);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("retireNum"));
            cell = row.createCell(15);
            cell.setCellStyle(style);
            cell.setCellValue((Integer)map.get("supportTeacherNum"));
            cell = row.createCell(16);
            cell.setCellStyle(style);
            cell.setCellFormula("B"+(startRow+1)+"+F"+(startRow+1)+"+J"+(startRow+1)+"+N"+(startRow+1)+"+O"+(startRow+1)+"+P"+(startRow+1));

            if((i+1)==data.size()){
                startRow++;
                row = sheet.createRow(startRow);
                row.setHeightInPoints(40);//设置行高
                cell = row.createCell(0);
                cell.setCellStyle(style);
                cell.setCellValue("合计");
                cell = row.createCell(1);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(B3:B"+(i+3)+")");
                cell = row.createCell(2);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(C3:C"+(i+3)+")");
                cell = row.createCell(3);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(D3:D"+(i+3)+")");
                cell = row.createCell(4);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(E3:E"+(i+3)+")");
                cell = row.createCell(5);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(F3:F"+(i+3)+")");
                cell = row.createCell(6);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(G3:G"+(i+3)+")");
                cell = row.createCell(7);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(H3:H"+(i+3)+")");
                cell = row.createCell(8);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(I3:I"+(i+3)+")");
                cell = row.createCell(9);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(J3:J"+(i+3)+")");
                cell = row.createCell(10);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(K3:K"+(i+3)+")");
                cell = row.createCell(11);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(L3:L"+(i+3)+")");
                cell = row.createCell(12);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(M3:M"+(i+3)+")");
                cell = row.createCell(13);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(N3:N"+(i+3)+")");
                cell = row.createCell(14);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(O3:O"+(i+3)+")");
                cell = row.createCell(15);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(P3:P"+(i+3)+")");
                cell = row.createCell(16);
                cell.setCellStyle(style);
                cell.setCellFormula("SUM(Q3:Q"+(i+3)+")");

                startRow++;
                XSSFFont fontDZHT = wb.createFont();
                fontDZHT.setFontHeightInPoints((short) 14); // 字体高度
                fontDZHT.setFontName("黑体"); // 字体
                row = sheet.getRow(startRow);
                cell = row.createCell(0);

                String directorProportion = "0.00%";
                String deputyProportion = "0.00%";
                String proportion = "0.00%";
                if (modelMap.get("totalFulltimeTecher").equals("0")) {
                    directorProportion = new BigDecimal((float) (modelMap.get("totalDirectorTeacher")) / (float) (modelMap.get("totalFulltimeTecher")) * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%";
                    deputyProportion = new BigDecimal((float) (modelMap.get("totalDeputyTeacher")) / (float) (modelMap.get("totalFulltimeTecher")) * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%";
                    proportion = new BigDecimal(((float) (modelMap.get("totalDeputyTeacher")) + (float) (modelMap.get("totalDirectorTeacher"))) / (float) (modelMap.get("totalFulltimeTecher")) * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%";
                }

                String rowStr1 = String.format("汇总：\n截至%s，全校共有基层党支部%s个。\n" , DateUtils.formatDate(new Date(),YYYY_MM),modelMap.get("allNum"));
                String rowStr2 = String.format("1.所有%s个本科生辅导员纵向党支部，均有辅导员担任党支部书记；\n" , modelMap.get("totalUndergraduateNum"));
                String rowStr3 = String.format("2.共有研究生党支部%s个，由研究生党员担任党支部书记；\n" , modelMap.get("totalYjsNum"));
                String rowStr4 = String.format("3.全校共有研究生导师纵向党支部%s个，其中%s位党支部书记为正高级专任教师，%s位副高职专任教师；%s位中级专任教师；\n"
                        ,modelMap.get("totalYjsTecher"),modelMap.get("totalDirectorYjsNum"),modelMap.get("totalDeputyNum"),modelMap.get("totalIntermediate"));
                String rowStr5 = String.format("4.全校共有专任教师党支部%s个，其中%s位党支部书记为正高级专任教师，占比%s；%s位副高职专任教师；占比%s；合计%s；另有%s位专任教师党支部书记具有中级专业技术职称,相关情况党委组织部正在专项督导；\n"
                        ,modelMap.get("totalFulltimeTecher"),modelMap.get("totalDirectorTeacher"),directorProportion,modelMap.get("totalDeputyTeacher"),deputyProportion,proportion,modelMap.get("totalIntermediateTeacher"));
                String rowStr6 = String.format("5.离退休党支部共%s个，机关行政后勤产业教工党支部共计%s个。",modelMap.get("totalRetireNum"),modelMap.get("totalSupportNum"));

                cell.setCellValue(rowStr1+rowStr2+rowStr3+rowStr4+rowStr5+rowStr6);
                cell.setCellStyle(style);//边框
                cell.getCellStyle().setWrapText(true);//自动换行
                cell.getCellStyle().setFont(fontDZHT);//设置字体
                //设置水平对齐的样式;
                cell.getCellStyle().setAlignment(HorizontalAlignment.LEFT);
                //设置垂直对齐的样式;
                cell.getCellStyle().setVerticalAlignment(VerticalAlignment.CENTER);


                sheet.addMergedRegion(ExcelTool.getCellRangeAddress(startRow, 0, startRow+16, 16));
            }
        }

    }

    private void renderBranchSheetData(HSSFWorkbook wb, ModelMap modelMap) {

        HSSFSheet sheet = wb.cloneSheet(0);

        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        style.setWrapText(true);//自动换行
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);//居中

        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell(0);

        List<Map<String, Object>> data = (List<Map<String, Object>>) modelMap.get("dataList");
        int startRow = 1;
        for (int i = 0; i <data.size(); i++) {
            Map<String, Object> map = data.get(i);
            startRow++;
            row = sheet.createRow(startRow);
            row.setHeightInPoints(60);//设置行高
            cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("rowNum"));
            cell = row.createCell(1);
            cell.setCellStyle(style);
            String partyName = map.get("partyName") == null ? "" : map.get("partyName").toString();
            cell.setCellValue(partyName);
            cell = row.createCell(2);
            cell.setCellStyle(style);
            String branchName = map.get("branchName") == null ? "" : map.get("branchName").toString();
            cell.setCellValue(branchName);
            cell = row.createCell(3);
            cell.setCellStyle(style);
            String branchTypeStr = map.get("branchTypeStr") == null ? "" : map.get("branchTypeStr").toString();
            cell.setCellValue(branchTypeStr);
            cell = row.createCell(4);
            cell.setCellStyle(style);
            String cadreName = map.get("cadreName") == null ? "" : map.get("cadreName").toString();
            cell.setCellValue(cadreName);
            cell = row.createCell(5);
            cell.setCellStyle(style);
            String gender = map.get("gender") == null ? "" : map.get("gender").toString();
            cell.setCellValue(gender);
            cell = row.createCell(6);
            cell.setCellStyle(style);
            String cadreNation = map.get("cadreNation") == null ? "" : map.get("cadreNation").toString();
            cell.setCellValue(cadreNation);
            cell = row.createCell(7);
            cell.setCellStyle(style);
            String growTime = map.get("growTime") == null ? "" : map.get("growTime").toString();
            cell.setCellValue(growTime);
            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("partyAge"));
            cell = row.createCell(9);
            cell.setCellStyle(style);
            String cadreBirth = map.get("cadreBirth") == null ? "" :map.get("cadreBirth").toString();
            cell.setCellValue(cadreBirth);
            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("age"));
            cell = row.createCell(11);
            cell.setCellStyle(style);
            String cadreStatus = map.get("cadreStatus") == null ? "" :map.get("cadreStatus").toString();
            cell.setCellValue(cadreStatus);
            cell = row.createCell(12);
            cell.setCellStyle(style);
            String cadreDuty = map.get("cadreDuty") == null ? "" :map.get("cadreDuty").toString();
            cell.setCellValue(cadreDuty);
            cell = row.createCell(13);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("formalNum"));
            cell = row.createCell(14);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("preparedNum"));
            cell = row.createCell(15);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("applyNum"));
            cell = row.createCell(16);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("activistsNum"));
            cell = row.createCell(17);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("developmentNum"));
            cell = row.createCell(18);
            cell.setCellStyle(style);
            cell.setCellValue((int)map.get("rowSum"));
        }
        startRow++;
        row = sheet.createRow(startRow);
        row.setHeightInPoints(40);//设置行高
        cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("合计");
        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(7);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(8);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(9);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(10);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(11);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(12);
        cell.setCellStyle(style);
        cell.setCellValue("--");
        cell = row.createCell(13);
        cell.setCellStyle(style);
        cell.setCellValue((int)modelMap.get("totalFormal"));
        cell = row.createCell(14);
        cell.setCellStyle(style);
        cell.setCellValue((int)modelMap.get("totalPreparedNum"));
        cell = row.createCell(15);
        cell.setCellStyle(style);
        cell.setCellValue((int)modelMap.get("totalApply"));
        cell = row.createCell(16);
        cell.setCellStyle(style);
        cell.setCellValue((int)modelMap.get("totalActivists"));
        cell = row.createCell(17);
        cell.setCellStyle(style);
        cell.setCellValue((int)modelMap.get("totalDevelopment"));
        cell = row.createCell(18);
        cell.setCellStyle(style);
        cell.setCellValue((int)modelMap.get("allNum"));
    }

    //获取本科生数量
    public static int getCount(List<StatByteBean> beans){
        int count = 0;
        for (StatByteBean statByteBean : beans) {
            //为空或为2的是本科生
            if (statByteBean.getGroupBy() == null || statByteBean.getGroupBy() == SystemConstants.USER_TYPE_BKS) {
                count = statByteBean.getNum() + count;
                continue;
            }
        }
        return count;
    }

    //遍历集合获取党支部不同阶段人数
    public static int getBranchCounting(List<StatByteBean> beans){
        int count = 0;
        for (StatByteBean statByteBean : beans) {
            count+=statByteBean.getNum();
        }
        return count;
    }


}
