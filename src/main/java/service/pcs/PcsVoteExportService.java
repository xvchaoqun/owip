package service.pcs;

import domain.pcs.PcsConfig;
import domain.pcs.PcsVoteCandidate;
import domain.pcs.PcsVoteGroup;
import domain.pcs.PcsVoteMember;
import domain.pcs.PcsVoteMemberExample;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.analysis.StatService;
import service.party.PartyService;
import service.sys.SysConfigService;
import sys.constants.SystemConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by lm on 2017/8/27.
 */
@Service
public class PcsVoteExportService extends BaseMapper {

    @Autowired
    private PartyService partyService;
    @Autowired
    protected StatService statService;
    @Autowired
    protected SysConfigService sysConfigService;
    @Autowired
    protected PcsConfigService pcsConfigService;

    public XSSFWorkbook vote(byte type) throws IOException {

        boolean isDw = (type == SystemConstants.PCS_USER_TYPE_DW);
        String filePath = isDw ? "classpath:xlsx/pcs/vote_dw.xlsx"
                : "classpath:xlsx/pcs/vote_jw.xlsx";

        InputStream is = new FileInputStream(ResourceUtils.getFile(filePath));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        PcsVoteGroup pcsVoteGroup = iPcsMapper.statPcsVoteGroup(type);

        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("jc", currentPcsConfig.getCommitteeJoinCount() + "")
                .replace("sv", (isDw ? currentPcsConfig.getDwSendVote() : currentPcsConfig.getJwSendVote()) + "")
                .replace("bv", (isDw ? currentPcsConfig.getDwBackVote() : currentPcsConfig.getJwBackVote()) + "")
                .replace("vc", pcsVoteGroup.getValid() + "")
                .replace("ic", pcsVoteGroup.getInvalid() + "");
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
            cell.setCellValue(bean.getAgree());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getDegree());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getAbstain());
        }

        List<PcsVoteCandidate> otherCandidates = iPcsMapper.selectVoteCandidateStatList(type, null, false);
        rowCount = Math.min(otherCandidates.size(), isDw ? 20 : 12);
        startRow = (isDw ? 35 : 21);
        for (int i = 0; i < rowCount; i++) {

            PcsVoteCandidate bean = otherCandidates.get(i);

            int column = (i < (isDw ? 10 : 6)) ? 1 : 3;
            int rowNum = startRow + (i % (isDw ? 10 : 6));
            row = sheet.getRow(rowNum);
            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getAgree());
        }

        return wb;
    }

    public XSSFWorkbook vote_zj(byte type) throws IOException {

        boolean isDw = (type == SystemConstants.PCS_USER_TYPE_DW);
        String filePath = isDw ? "classpath:xlsx/pcs/vote_dw_zj.xlsx"
                : "classpath:xlsx/pcs/vote_jw_zj.xlsx";

        InputStream is = new FileInputStream(ResourceUtils.getFile(filePath));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        PcsVoteGroup pcsVoteGroup = iPcsMapper.statPcsVoteGroup(type);

        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("jc", currentPcsConfig.getCommitteeJoinCount() + "")
                .replace("sv", (isDw ? currentPcsConfig.getDwSendVote() : currentPcsConfig.getJwSendVote()) + "")
                .replace("bv", (isDw ? currentPcsConfig.getDwBackVote() : currentPcsConfig.getJwBackVote()) + "")
                .replace("vc", pcsVoteGroup.getValid() + "")
                .replace("ic", pcsVoteGroup.getInvalid() + "");
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
            cell.setCellValue(bean.getAgree());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getDegree());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getAbstain());
        }

        List<PcsVoteCandidate> otherCandidates = iPcsMapper.selectVoteCandidateStatList(type, null, false);
        rowCount = Math.min(otherCandidates.size(), 10);
        startRow = (isDw ? 21 : 14);
        for (int i = 0; i < rowCount; i++) {

            PcsVoteCandidate bean = otherCandidates.get(i);

            int column = (i < 5) ? 1 : 4;
            int rowNum = startRow + (i % 5);
            row = sheet.getRow(rowNum);
            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(bean.getRealname());
            cell = row.getCell(column++);
            cell.setCellValue(bean.getAgree());
        }

        return wb;
    }

    // 当选名单
    public XSSFWorkbook member() throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/vote_member.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;

        {
            PcsVoteMemberExample example = new PcsVoteMemberExample();
            example.createCriteria().andTypeEqualTo(SystemConstants.PCS_USER_TYPE_DW);
            example.setOrderByClause("sort_order desc");
            List<PcsVoteMember> pcsVoteMembers = pcsVoteMemberMapper.selectByExample(example);
            int rowCount = Math.min(pcsVoteMembers.size(), 25);
            int startRow = 3;
            for (int i = 0; i < rowCount; i++) {
                PcsVoteMember bean = pcsVoteMembers.get(i);
                row = sheet.getRow(startRow + (i / 5));
                cell = row.getCell(i % 5);
                cell.setCellValue(bean.getRealname());
            }
        }

        {
            PcsVoteMemberExample example = new PcsVoteMemberExample();
            example.createCriteria().andTypeEqualTo(SystemConstants.PCS_USER_TYPE_JW);
            example.setOrderByClause("sort_order desc");
            List<PcsVoteMember> pcsVoteMembers = pcsVoteMemberMapper.selectByExample(example);
            int rowCount = Math.min(pcsVoteMembers.size(), 13);
            int startRow = 12;
            for (int i = 0; i < rowCount; i++) {
                PcsVoteMember bean = pcsVoteMembers.get(i);
                row = sheet.getRow(startRow + (i / 5));
                cell = row.getCell(i % 5);
                cell.setCellValue(bean.getRealname());
            }
        }

        return wb;
    }
}
