package service.dr;

import domain.dr.DrOfflineCandidate;
import domain.dr.DrOfflineCandidateExample;
import domain.dr.DrOfflineView;
import domain.dr.DrVoterType;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import service.base.MetaTypeService;
import sys.tags.CmTag;
import sys.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrExportService extends DrBaseMapper {

    @Autowired
    private DrVoterTypeService drVoterTypeService;
    @Autowired
    private MetaTypeService metaTypeService;

    // 线下民主推荐-导出含推荐人类型的统计表 （最多支持5个推荐人类型）
    public void exportOffline(int offlineId, Boolean needVoterType, HttpServletResponse response) throws IOException {

        DrOfflineView drOffline = iDrMapper.getDrOfflineView(offlineId);
        if(needVoterType==null)
            needVoterType = BooleanUtils.isTrue(drOffline.getNeedVoterType());

        DrOfflineCandidateExample example = new DrOfflineCandidateExample();
        example.createCriteria().andOfflineIdEqualTo(offlineId);
        example.setOrderByClause("weight desc, id asc");
        List<DrOfflineCandidate> candidates = drOfflineCandidateMapper.selectByExample(example);

        Map<Integer, DrVoterType> typeMap = new HashMap<>();
        Integer tplId = drOffline.getVoterTypeTplId();
        if (tplId != null) {
            typeMap = drVoterTypeService.findAll(tplId);
        }
        Map<Integer, Integer> voterMap = new HashMap<>();
        {
            String voters = drOffline.getVoters();
            if (StringUtils.isNotBlank(voters)) {
                voterMap = XmlSerializeUtils.unserialize(voters, Map.class);
            }
        }

        InputStream is = new FileInputStream(ResourceUtils
                .getFile("classpath:xlsx/dr/dr_offline_template"+(needVoterType?1:2)+".xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("title", drOffline.getTitle());
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("type", metaTypeService.getName(drOffline.getType()));
        cell.setCellValue(str);

        row = sheet.getRow(2);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("post", drOffline.getPostName())
                .replace("headcount", drOffline.getHeadcount()+"");
        cell.setCellValue(str);

        row = sheet.getRow(3);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("scope", drOffline.getScope())
                .replace("expect_voter_num", drOffline.getExpectVoterNum()+"");
        cell.setCellValue(str);

        row = sheet.getRow(4);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("actual_voter_num", drOffline.getActualVoterNum()+"")
                .replace("ballot", drOffline.getBallot()+"")
                .replace("abstain", drOffline.getAbstain()+"")
                .replace("invalid", drOffline.getInvalid()+"");
        cell.setCellValue(str);

        List<DrVoterType> drVoterTypeList = null;
        if(needVoterType) {
            drVoterTypeList = new ArrayList<>(typeMap.values());
            if (drVoterTypeList.size() > 5) { // 最多支持5个类别
                drVoterTypeList = drVoterTypeList.subList(0, 5);
            }
            row = sheet.getRow(6);
            for (int j = 0; j < drVoterTypeList.size(); j++) {
                cell = row.getCell(j * 2 + 4);
                DrVoterType drVoterType = drVoterTypeList.get(j);
                cell.setCellValue(drVoterType.getName());
            }
            row = sheet.getRow(7);
            for (int j = 0; j < drVoterTypeList.size(); j++) {
                cell = row.getCell(j * 2 + 4);
                DrVoterType drVoterType = drVoterTypeList.get(j);
                cell.setCellValue(voterMap.get(drVoterType.getId()));
            }
        }

        int startRow = needVoterType?9:7;
        int rowCount = candidates.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        int i=0;

        int validVoterNum = drOffline.getActualVoterNum() - drOffline.getInvalid();
        for (DrOfflineCandidate candidate : candidates) {

            int totalVote = candidate.getVote();
            Map<Integer, Integer> candidateVoterMap = new HashMap<>();
                    String voters = candidate.getVoters();
            if (StringUtils.isNotBlank(voters)) {
                candidateVoterMap = XmlSerializeUtils.unserialize(voters, Map.class);
                /*for (Integer vote : candidateVoterMap.values()) {
                    totalVote += vote;
                }*/
            }

            row = sheet.getRow(startRow++);
            int column = 0;
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(++i);

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(CmTag.realnameWithEmpty(candidate.getUser().getRealname()));

            // 推荐总体情况
            cell = row.getCell(column++);
            cell.setCellValue(totalVote);
            cell = row.getCell(column++);
            if(validVoterNum==0)
                cell.setCellValue("0.00%");
            else
                cell.setCellValue(NumberUtils.formatDoubleFixed(totalVote*100.0/validVoterNum, 2) + "%");

            if(needVoterType) {
                for (int j = 0; j < drVoterTypeList.size(); j++) {

                    DrVoterType drVoterType = drVoterTypeList.get(j);

                    int count = candidateVoterMap.get(drVoterType.getId());
                    Integer totalCount = voterMap.get(drVoterType.getId());

                    cell = row.getCell(column++);
                    cell.setCellValue(count);
                    cell = row.getCell(column++);
                    if(totalCount==null || totalCount==0)
                        cell.setCellValue("0.0%");
                    else
                        cell.setCellValue(NumberUtils.formatDoubleFixed(count * 100.0 / totalCount, 1) + "%");
                }
            }
        }

        row = sheet.getRow(startRow+(rowCount>0?0:1));
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("recommend_date", DateUtils.formatDate(drOffline.getRecommendDate(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);

        String fileName = String.format("%s（%s）", drOffline.getTitle(), needVoterType?"分类统计":"推荐总结果");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
