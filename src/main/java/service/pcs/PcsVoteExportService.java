package service.pcs;

import domain.pcs.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import service.analysis.StatService;
import service.party.PartyService;
import service.sys.SysConfigService;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.NumberUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/8/27.
 */
@Service
public class PcsVoteExportService extends PcsBaseMapper {

    @Autowired
    private PartyService partyService;
    @Autowired
    protected StatService statService;
    @Autowired
    protected SysConfigService sysConfigService;
    @Autowired
    protected PcsConfigService pcsConfigService;
    @Autowired
    protected PcsExportService pcsExportService;

    // 小组计票
    public XSSFWorkbook vote_jp(PcsVoteGroup pcsVoteGroup) throws IOException {

        //PcsVoteGroup pcsVoteGroup = pcsVoteGroupMapper.selectByPrimaryKey(groupId);
        int groupId = pcsVoteGroup.getId();
        byte type = pcsVoteGroup.getType();

        boolean isDw = (type == PcsConstants.PCS_USER_TYPE_DW);
        String filePath = "";
        String title = "";

        String pcsNum=pcsConfigService.getPcsNum(pcsVoteGroup.getConfigId());

        if(isDw){
            filePath = "classpath:xlsx/pcs/vote_dw_jp.xlsx";
            title = "中共" + CmTag.getSysConfig().getSchoolName() + "第"+ pcsNum +"届委员会委员计票结果报告单";
        }else{
            filePath = "classpath:xlsx/pcs/vote_jw_jp.xlsx";
            title = "中共" + CmTag.getSysConfig().getSchoolName() + "第"+ pcsNum +"届纪律检查委员会委员计票结果报告单";
        }

        InputStream is = new FileInputStream(ResourceUtils.getFile(filePath));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("title", title)
                .replace("name", StringUtils.trimToEmpty(pcsVoteGroup.getName()));
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("vote", NumberUtils.trimToEmpty(pcsVoteGroup.getVote()))
                .replace("yx", NumberUtils.trimToEmpty(pcsVoteGroup.getValid()))
                .replace("wx", NumberUtils.trimToEmpty(pcsVoteGroup.getInvalid()));
        cell.setCellValue(str);

        List<PcsVoteCandidate> candidates;
        {
            PcsVoteCandidateExample example = new PcsVoteCandidateExample();
            example.createCriteria().andGroupIdEqualTo(groupId).andIsFromStageEqualTo(true);
            example.setOrderByClause("id asc");
            candidates = pcsVoteCandidateMapper.selectByExample(example);
        }
        int rowCount = Math.min(candidates.size(), isDw ? 30 : 16);
        int startRow = 3;
        for (int i = 0; i < rowCount; i++) {

            PcsVoteCandidate bean = candidates.get(i);

            int column = 1;
            row = sheet.getRow(startRow++);
            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getAgree()));
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getDegree()));
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getAbstain()));
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getInvalid()));
        }

        List<PcsVoteCandidate> otherCandidates;
        {
            PcsVoteCandidateExample example = new PcsVoteCandidateExample();
            example.createCriteria().andGroupIdEqualTo(groupId).andIsFromStageEqualTo(false);
            example.setOrderByClause("id asc");
            otherCandidates = pcsVoteCandidateMapper.selectByExample(example);
        }

        rowCount = otherCandidates.size();
        startRow = (isDw ? 35 : 21);
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsVoteCandidate bean = otherCandidates.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            cell = row.getCell(column++);
            cell.setCellValue(i+1);
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getAgree()));
            cell = row.getCell(column++);
            cell.setCellValue("—");
            cell = row.getCell(column++);
            cell.setCellValue("—");
            cell = row.getCell(column++);
            cell.setCellValue("—");
        }

        /*row = sheet.getRow((isDw ? 35 : 21) + (rowCount==0?1:rowCount));
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("recorder", pcsVoteGroup.getRecordUser().getRealname());
        cell.setCellValue(str);*/

        return wb;
    }

    public XSSFWorkbook vote(byte type) throws IOException {

        boolean isDw = (type == PcsConstants.PCS_USER_TYPE_DW);

        String filePath = "";
        String title = "";

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        String pcsNum=pcsConfigService.getPcsNum(currentPcsConfig.getId());

        if(isDw){
            filePath = "classpath:xlsx/pcs/vote_dw.xlsx";
            title = "中共" + CmTag.getSysConfig().getSchoolName() + "第"+ pcsNum +"届委员会委员";
        }else{
            filePath = "classpath:xlsx/pcs/vote_jw.xlsx";
            title = "中共" + CmTag.getSysConfig().getSchoolName() + "第"+ pcsNum +"届纪律检查委员会委员";
        }

        InputStream is = new FileInputStream(ResourceUtils.getFile(filePath));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                    .replace("title", title);
        cell.setCellValue(str);

        PcsVoteGroup pcsVoteGroup = iPcsMapper.statPcsVoteGroup(type);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("jc", NumberUtils.trimToEmpty(currentPcsConfig.getCommitteeJoinCount()) + "")
                .replace("sv", (isDw ? NumberUtils.trimToEmpty(currentPcsConfig.getDwSendVote()) :
                        NumberUtils.trimToEmpty(currentPcsConfig.getJwSendVote()) + ""))
                .replace("bv", (isDw ? NumberUtils.trimToEmpty(currentPcsConfig.getDwBackVote()) :
                        NumberUtils.trimToEmpty(currentPcsConfig.getJwBackVote()) + ""))
                .replace("vc", NumberUtils.trimToEmpty(pcsVoteGroup.getValid()) + "")
                .replace("ic", NumberUtils.trimToEmpty(pcsVoteGroup.getInvalid()) + "");
        cell.setCellValue(str);

        List<PcsVoteCandidate> candidates = iPcsMapper.selectVoteCandidateStatList(type, null, true);
        int rowCount = Math.min(candidates.size(), isDw ? 30 : 16);
        int startRow = 3;
        for (int i = 0; i < rowCount; i++) {

            PcsVoteCandidate bean = candidates.get(i);

            int column = 1;
            row = sheet.getRow(startRow++);
            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getAgree()));
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getDegree()));
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getAbstain()));
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getInvalid()));
        }

        List<PcsVoteCandidate> otherCandidates = iPcsMapper.selectVoteCandidateStatList(type, null, false);
        //rowCount = Math.min(otherCandidates.size(), isDw ? 20 : 12);
        rowCount = otherCandidates.size();
        startRow = (isDw ? 35 : 21);
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsVoteCandidate bean = otherCandidates.get(i);

            //int column = (i < (isDw ? 10 : 6)) ? 1 : 3;
            //int rowNum = startRow + (i % (isDw ? 10 : 6));

            int column = 0;
            row = sheet.getRow(startRow++);

            cell = row.getCell(column++);
            cell.setCellValue(i+1);
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getAgree()));
            cell = row.getCell(column++);
            cell.setCellValue("—");
            cell = row.getCell(column++);
            cell.setCellValue("—");
            cell = row.getCell(column++);
            cell.setCellValue("—");
        }

        return wb;
    }

    public XSSFWorkbook vote_zj(byte type) throws IOException {

        boolean isDw = (type == PcsConstants.PCS_USER_TYPE_DW);

        String filePath = "";
        String title = "";

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        String pcsNum=pcsConfigService.getPcsNum(currentPcsConfig.getId());

        if(isDw){
            filePath = "classpath:xlsx/pcs/vote_dw_zj.xlsx";
            title = "中共" + CmTag.getSysConfig().getSchoolName() + "第"+ pcsNum +"届委员会委员计票结果报告单";
        }else{
            filePath = "classpath:xlsx/pcs/vote_jw_zj.xlsx";
            title = "中共" + CmTag.getSysConfig().getSchoolName() + "第"+ pcsNum +"届纪律检查委员会委员计票结果报告单";
        }

        InputStream is = new FileInputStream(ResourceUtils.getFile(filePath));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                    .replace("title", title);
        cell.setCellValue(str);

        PcsVoteGroup pcsVoteGroup = iPcsMapper.statPcsVoteGroup(type);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("jc", NumberUtils.trimToEmpty(currentPcsConfig.getCommitteeJoinCount()) + "")
                .replace("sv", (isDw ? NumberUtils.trimToEmpty(currentPcsConfig.getDwSendVote())
                        : NumberUtils.trimToEmpty(currentPcsConfig.getJwSendVote())) + "")
                .replace("bv", (isDw ? NumberUtils.trimToEmpty(currentPcsConfig.getDwBackVote()) :
                        NumberUtils.trimToEmpty(currentPcsConfig.getJwBackVote())) + "")
                .replace("vc", NumberUtils.trimToEmpty(pcsVoteGroup.getValid()) + "")
                .replace("ic", NumberUtils.trimToEmpty(pcsVoteGroup.getInvalid()) + "");
        cell.setCellValue(str);

        List<PcsVoteCandidate> candidates = iPcsMapper.selectVoteCandidateStatList(type, null, true);
        int rowCount = Math.min(candidates.size(), isDw ? 30 : 16);
        int startRow = 4;
        for (int i = 0; i < rowCount; i++) {

            PcsVoteCandidate bean = candidates.get(i);

            int column = (i < (isDw ? 15 : 8)) ? 1 : 4;
            int rowNum = startRow + (i % (isDw ? 15 : 8));

            row = sheet.getRow(rowNum);
            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getAgree()));
        }

        List<PcsVoteCandidate> otherCandidates = iPcsMapper.selectVoteCandidateStatList(type, null, false);
        rowCount = otherCandidates.size();
        startRow = (isDw ? 21 : 14);
        // 双列显示，只有一半的行数
        int realRowCount = (rowCount/2 + (rowCount%2>0?1:0));
        ExcelUtils.insertRow(wb, sheet, startRow,  realRowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            PcsVoteCandidate bean = otherCandidates.get(i);

            int column = (i < realRowCount) ? 0 : 3;
            int rowNum = startRow + (i % realRowCount);
            row = sheet.getRow(rowNum);
            cell = row.getCell(column++);
            cell.setCellValue(i+1);
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());
            cell = row.getCell(column++);
            cell.setCellValue(NumberUtils.trimToEmpty(bean.getAgree()));
        }

        startRow = startRow + realRowCount + 1;
        try {
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(startRow, 0, startRow, row.getLastCellNum() - 1));
        } catch (Exception e) {

        }

        return wb;
    }

    // 当选名单 TODO 根据具体学校情况修改Excel里的表头名称
    public XSSFWorkbook member() throws IOException {

        PcsConfig pcsConfig=pcsConfigService.getCurrentPcsConfig();
        String pcsNum=pcsConfigService.getPcsNum(pcsConfig.getId());
        String school = pcsExportService.getSchoolName();


        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/vote_member.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", school).replace("pcsNum", pcsNum);
        cell.setCellValue(str);
        row = sheet.getRow(1);
        cell = row.getCell(0);
        String date = cell.getStringCellValue()
                .replace("date", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(date);
        row = sheet.getRow(2);
        cell = row.getCell(0);
        String str1 = cell.getStringCellValue()
                .replace("school", school).replace("pcsNum", pcsNum);
        cell.setCellValue(str1);

        {
            PcsVoteMemberExample example = new PcsVoteMemberExample();
            example.createCriteria().andTypeEqualTo(PcsConstants.PCS_USER_TYPE_DW);
            example.setOrderByClause("sort_order asc");
            List<PcsVoteMember> pcsVoteMembers = pcsVoteMemberMapper.selectByExample(example);
            int rowCount = Math.min(pcsVoteMembers.size(), 25);
            int startRow = 3;
            for (int i = 0; i < rowCount; i++) {
                PcsVoteMember bean = pcsVoteMembers.get(i);
                row = sheet.getRow(startRow + (i / 5));
                cell = row.getCell(i % 5);

                String realname = bean.getRealname();
                boolean isFemale = (bean.getGender()==SystemConstants.GENDER_FEMALE);
                boolean isSsmz = (bean.getNation().indexOf("汉")==-1); // 是否少数民族

                if(isFemale && isSsmz){
                    realname += "\r\n（女，"+bean.getNation()+"）";
                }else if(isFemale){
                    realname += "\r\n（女）";
                }else if(isSsmz){
                    realname += "\r\n（"+bean.getNation()+"）";
                }

                cell.setCellValue(realname);
            }
        }

        row = sheet.getRow(9);
        cell = row.getCell(0);
        String str2 = cell.getStringCellValue()
                .replace("school", school).replace("pcsNum", pcsNum);
        cell.setCellValue(str2);
        row = sheet.getRow(10);
        cell = row.getCell(0);
        cell.setCellValue(date);
        row = sheet.getRow(11);
        cell = row.getCell(0);
        String str3 = cell.getStringCellValue()
                .replace("school", school).replace("pcsNum", pcsNum);
        cell.setCellValue(str3);

        {
            PcsVoteMemberExample example = new PcsVoteMemberExample();
            example.createCriteria().andTypeEqualTo(PcsConstants.PCS_USER_TYPE_JW);
            example.setOrderByClause("sort_order asc");
            List<PcsVoteMember> pcsVoteMembers = pcsVoteMemberMapper.selectByExample(example);
            int rowCount = Math.min(pcsVoteMembers.size(), 13);
            int startRow = 12;
            for (int i = 0; i < rowCount; i++) {
                PcsVoteMember bean = pcsVoteMembers.get(i);
                row = sheet.getRow(startRow + (i / 5));
                cell = row.getCell(i % 5);

                String realname = bean.getRealname();
                boolean isFemale = (bean.getGender()==SystemConstants.GENDER_FEMALE);
                boolean isSsmz = (bean.getNation().indexOf("汉")==-1); // 是否少数民族

                if(isFemale && isSsmz){
                    realname += "\r\n（女，"+bean.getNation()+"）";
                }else if(isFemale){
                    realname += "\r\n（女）";
                }else if(isSsmz){
                    realname += "\r\n（"+bean.getNation()+"）";
                }

                cell.setCellValue(realname);
            }
        }

        return wb;
    }
}
