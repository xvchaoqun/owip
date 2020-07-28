package service.dr;

import domain.dr.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import persistence.dr.common.DrFinalResult;
import service.base.MetaTypeService;
import sys.tags.CmTag;
import sys.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class DrExportService extends DrBaseMapper {

    @Autowired
    private DrVoterTypeService drVoterTypeService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private DrOnlineResultService drOnlineResultService;
    @Autowired
    private DrOnlinePostService drOnlinePostService;

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

                    Integer count = candidateVoterMap.get(drVoterType.getId());
                    Integer totalCount = voterMap.get(drVoterType.getId());

                    cell = row.getCell(column++);
                    if(count!=null) {
                        cell.setCellValue(count);
                    }
                    cell = row.getCell(column++);
                    if(totalCount==null || totalCount==0)
                        cell.setCellValue("0.0%");
                    else if(count!=null)
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

     // 导出线上民主推荐结果，按岗位导出，模板中预留一行
     public void exportOnlineResult(Integer onlineId, List<DrFinalResult> drFinalResults, HttpServletResponse response) throws IOException {

        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(onlineId);
        Set<Integer> postIds = new HashSet<>();
         for (DrFinalResult drFinalResult : drFinalResults) {
             postIds.add(drFinalResult.getPostId());
         }
        Map<Integer, List<DrFinalResult>> resultMap = drOnlineResultService.getResult(drFinalResults, postIds);

        InputStream is = new FileInputStream(ResourceUtils
                .getFile("classpath:xlsx/dr/dr_online_template.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(3);
        str = cell.getStringCellValue()
                .replace("date", DateUtils.formatDate(drOnline.getRecommendDate(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);

        if (postIds.size() == 0){

            row = sheet.getRow(2);
            cell = row.getCell(0);
            str = cell.getStringCellValue()
                    .replace("post", "无")
                    .replace("headcount", "0");
            cell.setCellValue(str);

            String fileName = String.format("线上民主推荐结果（%s）", drOnline.getCode());
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return;
        }

        //进行row扩充
        int startRow = 3;
        int rowInsert = drFinalResults.size() + (postIds.size() - 1) * 3;
        ExcelUtils.insertRow(wb, sheet, startRow, rowInsert - 1);
        String[] tableHead = {"序号", "推荐人选", "票数","备注"};
        //获得单元格样式
        XSSFCellStyle cellStyle = null;
        XSSFRow _row = sheet.getRow(3);
        XSSFCellStyle cellStyle1 = _row.getCell(0).getCellStyle();

        int rowCount = 2;//记录行数
        for (Integer postId : postIds){
            //设置模板中每一个职务的第一行
            DrOnlinePost postView = drOnlinePostService.getPost(postId);
            List<DrFinalResult> _drFinalResults = resultMap.get(postId);
            row = sheet.getRow(rowCount++);//1
            cell = row.getCell(0);
            if (rowCount <= 3) {
                cellStyle = cell.getCellStyle();
                str = cell.getStringCellValue()
                        .replace("post", postView.getName())
                        .replace("headcount", postView.getHeadCount() + "");
            }else {
                //合并单元格
                CellRangeAddress cra = new CellRangeAddress(rowCount - 1,rowCount - 1,0,2);
                sheet.addMergedRegion(cra);
                for (int i = 3; i >= 0; i--){
                    cell = row.getCell(i);
                    cell.setCellStyle(cellStyle);
                }
                str = "推荐职务：" + postView.getName() + "（" + postView.getHeadCount() + "名）";
            }
            cell.setCellValue(str);
            //设置每一个岗位的表头
            if (_drFinalResults.size() == 0) {//参评人为空
                //插入表头
                row = sheet.getRow(rowCount++);
                for(int i = 0; i < tableHead.length; i++){
                    cell = row.getCell(i);
                    cell.setCellStyle(cellStyle1);
                    cell.setCellValue(tableHead[i]);
                }
            }else {

                row = sheet.getRow(rowCount++);
                for(int i = 0; i < tableHead.length; i++){
                    cell = row.getCell(i);
                    cell.setCellStyle(cellStyle1);
                    cell.setCellValue(tableHead[i]);
                }

                //处理得票
                int i = 0;
                for (DrFinalResult record : _drFinalResults) {

                    row = sheet.getRow(rowCount++);
                    int column = 0;
                    // 序号
                    cell = row.getCell(column++);
                    cell.setCellValue(++i);

                    // 推荐人选
                    cell = row.getCell(column++);
                    cell.setCellValue(record.getRealname());

                    // 票数
                    cell = row.getCell(column++);
                    cell.setCellValue(record.getBallot());
                }
            }
            rowCount++;
        }
        String fileName = String.format("线上民主推荐结果（%s）", drOnline.getCode());
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
