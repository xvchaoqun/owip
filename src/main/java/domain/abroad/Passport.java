package domain.abroad;

import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import persistence.cadre.CadreMapper;
import persistence.cadre.common.ICadreMapper;
import sys.constants.AbroadConstants;
import sys.helper.AbroadHelper;
import sys.jackson.SignRes;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Passport implements Serializable {

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

    public String getPassportType(){

        return AbroadConstants.ABROAD_PASSPORT_TYPE_MAP.get(type);
    }
    public MetaType getPassportClass(){

        Map<Integer, MetaType> passportClassMap = CmTag.getMetaTypes("mc_passport_type");
        return passportClassMap.get(classId);
    }
    public SafeBox getSafeBox(){

        return AbroadHelper.getSafeBoxMap().get(safeBoxId);
    }

    public String getRefuseReturnReason(){

        if(BooleanUtils.isTrue(isLent)){
            PassportDraw passportDraw = AbroadHelper.getRefuseReturnPassportDraw(id);
            if(passportDraw!=null)
                return StringUtils.defaultIfBlank(passportDraw.getReturnRemark(), "拒不交回");
        }

        return null;
    }

    private Integer id;

    private Integer applyId;

    private Integer taiwanRecordId;

    private Integer cadreId;

    private Integer classId;

    private String code;

    private String authority;

    private Date issueDate;

    private Date expiryDate;

    private Date keepDate;

    private Integer safeBoxId;

    @SignRes
    private String pic;

    private Boolean isLent;

    private Byte type;

    private Byte cancelType;

    private String cancelTypeOther;

    private Boolean cancelConfirm;

    private String cancelPic;

    private Date cancelTime;

    private Integer cancelUserId;

    private String cancelRemark;

    private Byte lostType;

    private Date lostTime;

    private String lostProof;

    private Integer lostUserId;

    private Boolean hasFind;

    private Date findTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getTaiwanRecordId() {
        return taiwanRecordId;
    }

    public void setTaiwanRecordId(Integer taiwanRecordId) {
        this.taiwanRecordId = taiwanRecordId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority == null ? null : authority.trim();
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getKeepDate() {
        return keepDate;
    }

    public void setKeepDate(Date keepDate) {
        this.keepDate = keepDate;
    }

    public Integer getSafeBoxId() {
        return safeBoxId;
    }

    public void setSafeBoxId(Integer safeBoxId) {
        this.safeBoxId = safeBoxId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Boolean getIsLent() {
        return isLent;
    }

    public void setIsLent(Boolean isLent) {
        this.isLent = isLent;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getCancelType() {
        return cancelType;
    }

    public void setCancelType(Byte cancelType) {
        this.cancelType = cancelType;
    }

    public String getCancelTypeOther() {
        return cancelTypeOther;
    }

    public void setCancelTypeOther(String cancelTypeOther) {
        this.cancelTypeOther = cancelTypeOther == null ? null : cancelTypeOther.trim();
    }

    public Boolean getCancelConfirm() {
        return cancelConfirm;
    }

    public void setCancelConfirm(Boolean cancelConfirm) {
        this.cancelConfirm = cancelConfirm;
    }

    public String getCancelPic() {
        return cancelPic;
    }

    public void setCancelPic(String cancelPic) {
        this.cancelPic = cancelPic == null ? null : cancelPic.trim();
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Integer getCancelUserId() {
        return cancelUserId;
    }

    public void setCancelUserId(Integer cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark == null ? null : cancelRemark.trim();
    }

    public Byte getLostType() {
        return lostType;
    }

    public void setLostType(Byte lostType) {
        this.lostType = lostType;
    }

    public Date getLostTime() {
        return lostTime;
    }

    public void setLostTime(Date lostTime) {
        this.lostTime = lostTime;
    }

    public String getLostProof() {
        return lostProof;
    }

    public void setLostProof(String lostProof) {
        this.lostProof = lostProof == null ? null : lostProof.trim();
    }

    public Integer getLostUserId() {
        return lostUserId;
    }

    public void setLostUserId(Integer lostUserId) {
        this.lostUserId = lostUserId;
    }

    public Boolean getHasFind() {
        return hasFind;
    }

    public void setHasFind(Boolean hasFind) {
        this.hasFind = hasFind;
    }

    public Date getFindTime() {
        return findTime;
    }

    public void setFindTime(Date findTime) {
        this.findTime = findTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}