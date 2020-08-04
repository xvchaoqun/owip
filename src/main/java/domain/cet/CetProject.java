package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import persistence.cet.CetPartyMapper;
import service.cet.CetProjectObjService;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetProject implements Serializable {

    public CetProjectObj getObj(){

        HttpServletRequest request = ContextHelper.getRequest();
        if (request == null) return null;

        Integer userId = (Integer) request.getAttribute("userId");
        if(userId==null) return null;

        CetProjectObjService cetProjectObjService = CmTag.getBean(CetProjectObjService.class);
        return cetProjectObjService.get(userId, id);
    }

    public CetParty getCetParty(){

        if(cetPartyId==null) return null;
        return CmTag.getBean(CetPartyMapper.class).selectByPrimaryKey(cetPartyId);
    }

    // 判断选课是否正在进行
    public static boolean getIsApplyOpen(Date startTime, Date endTime) {

        Date now = new Date();
        if (startTime != null && endTime != null) {

            return now.after(startTime) && now.before(endTime);

        } else if (startTime != null) {

            return startTime.before(now);

        } else if (endTime != null) {

            return now.before(endTime);

        }

        return false;
    }

    private Integer id;

    private Byte type;

    private Integer cetPartyId;

    private Integer unitId;

    private Integer year;

    private String traineeTypeIds;

    private String otherTraineeType;

    private Integer objCount;

    private Integer quitCount;

    private Date startTime;

    private Date endTime;

    private Integer fileCount;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date startDate;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date endDate;

    private Date openTime;

    private String openAddress;

    private String name;

    private Boolean isValid;

    private Integer projectTypeId;

    private String category;

    private String fileName;

    private String pdfFilePath;

    private String wordFilePath;

    private BigDecimal period;

    private BigDecimal requirePeriod;

    private String remark;

    private Date createTime;

    private Boolean hasArchive;

    private Boolean isDeleted;

    private Byte status;

    private String backReason;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCetPartyId() {
        return cetPartyId;
    }

    public void setCetPartyId(Integer cetPartyId) {
        this.cetPartyId = cetPartyId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTraineeTypeIds() {
        return traineeTypeIds;
    }

    public void setTraineeTypeIds(String traineeTypeIds) {
        this.traineeTypeIds = traineeTypeIds == null ? null : traineeTypeIds.trim();
    }

    public String getOtherTraineeType() {
        return otherTraineeType;
    }

    public void setOtherTraineeType(String otherTraineeType) {
        this.otherTraineeType = otherTraineeType == null ? null : otherTraineeType.trim();
    }

    public Integer getObjCount() {
        return objCount;
    }

    public void setObjCount(Integer objCount) {
        this.objCount = objCount;
    }

    public Integer getQuitCount() {
        return quitCount;
    }

    public void setQuitCount(Integer quitCount) {
        this.quitCount = quitCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public String getOpenAddress() {
        return openAddress;
    }

    public void setOpenAddress(String openAddress) {
        this.openAddress = openAddress == null ? null : openAddress.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Integer getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(Integer projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath == null ? null : pdfFilePath.trim();
    }

    public String getWordFilePath() {
        return wordFilePath;
    }

    public void setWordFilePath(String wordFilePath) {
        this.wordFilePath = wordFilePath == null ? null : wordFilePath.trim();
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public BigDecimal getRequirePeriod() {
        return requirePeriod;
    }

    public void setRequirePeriod(BigDecimal requirePeriod) {
        this.requirePeriod = requirePeriod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getHasArchive() {
        return hasArchive;
    }

    public void setHasArchive(Boolean hasArchive) {
        this.hasArchive = hasArchive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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