package domain.oa;

import controller.global.OpException;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import persistence.oa.OaGridMapper;
import service.oa.OaGridPartyService;
import service.pcs.PcsPollService;
import sys.constants.OaConstants;
import sys.jackson.SignRes;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class OaGridParty implements Serializable {

    public String getReportMsg(){

        if(status == OaConstants.OA_GRID_PARTY_REPORT) return "reported";
        if(id==null) return "null";

        OaGridPartyService oaGridPartyService = CmTag.getBean(OaGridPartyService.class);
        try {
            oaGridPartyService.checkReportData(this);
        }catch (OpException opException){
            return opException.getMessage();
        }

        return "";
    }

    public OaGrid getGrid(){
        OaGridMapper oaGridMapper = CmTag.getBean(OaGridMapper.class);
        return oaGridMapper.selectByPrimaryKey(gridId);
    }
    public SysUserView getUser(){ return CmTag.getUserById(reportUserId); }
    private Integer id;

    private Integer gridId;

    private Integer year;

    private Integer partyId;

    private String gridName;

    private String partyName;

    @SignRes
    private String excelFilePath;

    private String fileName;

    private String filePath;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM)
    private Date reportTime;

    private Integer reportUserId;

    private Byte status;

    private String backReason;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGridId() {
        return gridId;
    }

    public void setGridId(Integer gridId) {
        this.gridId = gridId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName == null ? null : gridName.trim();
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    public String getExcelFilePath() {
        return excelFilePath;
    }

    public void setExcelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath == null ? null : excelFilePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(Integer reportUserId) {
        this.reportUserId = reportUserId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason == null ? null : backReason.trim();
    }
}