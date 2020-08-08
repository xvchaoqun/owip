package domain.abroad;

import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import persistence.cadre.common.ICadreMapper;
import sys.constants.AbroadConstants;
import sys.helper.AbroadHelper;
import sys.jackson.SignRes;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PassportDraw implements Serializable {

    private CadreView cadre;
    private SysUserView sysUserView;
    public SysUserView getUser(){

        if(sysUserView==null) {
            CadreView cadre = getCadre();
            sysUserView = CmTag.getUserById(cadre.getUserId());
        }
        return sysUserView;
    }
    public CadreView getCadre(){

        if(cadre==null){
            ICadreMapper iCadreMapper = CmTag.getBean(ICadreMapper.class);
            cadre = iCadreMapper.getCadre(cadreId);
        }
        return cadre;
    }

    public Passport getPassport(){
        return AbroadHelper.getPassport(passportId);
    }

    public List<PassportDrawFile> getFiles(){

        return AbroadHelper.getPassportDrawFiles(id);
    }

    public MetaType getPassportClass(){

        Passport passport = AbroadHelper.getPassport(passportId);
        Map<Integer, MetaType> passportClassMap = CmTag.getMetaTypes("mc_passport_type");
        return passportClassMap.get(passport.getClassId());
    }

    public ApplySelf getApplySelf(){

        if(type== AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF && applyId!=null)
            return AbroadHelper.getApplySelf(applyId);
        return null;
    }
    public String getStatusName(){
        return AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_MAP.get(status);
    }
    /*public String getDrawStatusName(){
        return AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP.get(drawStatus);
    }*/
    public Boolean getReturnDateNotNow(){
        Date now = new Date();
        return DateUtils.compareDate(returnDate, now);
    }
    private Integer id;

    private Integer cadreId;

    private Byte type;

    private Integer applyId;

    private Integer passportId;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date applyDate;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date startDate;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date endDate;

    private String reason;

    private String costSource;

    private Boolean needSign;

    private String remark;

    private Byte useType;

    private Date createTime;

    private String ip;

    private Byte status;

    private Integer userId;

    private String approveRemark;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date approveTime;

    private String approveIp;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date realStartDate;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date realEndDate;

    private String realToCountry;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date returnDate;

    private Integer drawUserId;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date drawTime;

    private String drawRecord;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date realReturnDate;

    private Byte drawStatus;

    private String returnRemark;

    @SignRes
    private String useRecord;

    private Boolean jobCertify;

    @SignRes
    private String attachment;

    private String attachmentFilename;

    private Byte usePassport;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getPassportId() {
        return passportId;
    }

    public void setPassportId(Integer passportId) {
        this.passportId = passportId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getCostSource() {
        return costSource;
    }

    public void setCostSource(String costSource) {
        this.costSource = costSource == null ? null : costSource.trim();
    }

    public Boolean getNeedSign() {
        return needSign;
    }

    public void setNeedSign(Boolean needSign) {
        this.needSign = needSign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getUseType() {
        return useType;
    }

    public void setUseType(Byte useType) {
        this.useType = useType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark == null ? null : approveRemark.trim();
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveIp() {
        return approveIp;
    }

    public void setApproveIp(String approveIp) {
        this.approveIp = approveIp == null ? null : approveIp.trim();
    }

    public Date getRealStartDate() {
        return realStartDate;
    }

    public void setRealStartDate(Date realStartDate) {
        this.realStartDate = realStartDate;
    }

    public Date getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(Date realEndDate) {
        this.realEndDate = realEndDate;
    }

    public String getRealToCountry() {
        return realToCountry;
    }

    public void setRealToCountry(String realToCountry) {
        this.realToCountry = realToCountry == null ? null : realToCountry.trim();
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getDrawUserId() {
        return drawUserId;
    }

    public void setDrawUserId(Integer drawUserId) {
        this.drawUserId = drawUserId;
    }

    public Date getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }

    public String getDrawRecord() {
        return drawRecord;
    }

    public void setDrawRecord(String drawRecord) {
        this.drawRecord = drawRecord == null ? null : drawRecord.trim();
    }

    public Date getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(Date realReturnDate) {
        this.realReturnDate = realReturnDate;
    }

    public Byte getDrawStatus() {
        return drawStatus;
    }

    public void setDrawStatus(Byte drawStatus) {
        this.drawStatus = drawStatus;
    }

    public String getReturnRemark() {
        return returnRemark;
    }

    public void setReturnRemark(String returnRemark) {
        this.returnRemark = returnRemark == null ? null : returnRemark.trim();
    }

    public String getUseRecord() {
        return useRecord;
    }

    public void setUseRecord(String useRecord) {
        this.useRecord = useRecord == null ? null : useRecord.trim();
    }

    public Boolean getJobCertify() {
        return jobCertify;
    }

    public void setJobCertify(Boolean jobCertify) {
        this.jobCertify = jobCertify;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public String getAttachmentFilename() {
        return attachmentFilename;
    }

    public void setAttachmentFilename(String attachmentFilename) {
        this.attachmentFilename = attachmentFilename == null ? null : attachmentFilename.trim();
    }

    public Byte getUsePassport() {
        return usePassport;
    }

    public void setUsePassport(Byte usePassport) {
        this.usePassport = usePassport;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}