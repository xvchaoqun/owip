package domain.dp;

import domain.cadre.Cadre;
import domain.sys.SysUserView;
import sys.helper.DpPartyHelper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class DpMemberView implements Serializable {

    public Cadre getCadre(){
        return CmTag.getCadre(userId);
    }

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public DpParty getDpParty(){
        return DpPartyHelper.getDpPartyByPartyId(partyId);
    }
    public String getTypes(){return DpPartyHelper.getTypes(userId);}
    private Integer userId;

    private Integer partyId;

    private Byte type;

    private Byte status;

    private String dpPost;

    private Byte source;

    private Integer addType;

    private Date dpGrowTime;

    private Boolean isPartyMember;

    private Date growTime;

    private Date createTime;

    private Date outTime;

    private String address;

    private String mobile;

    private String email;

    private String remark;

    private Date updateTime;

    private Byte gender;

    private Date birth;

    private String nation;

    private String nativePlace;

    private String unit;

    private Date workTime;

    private String authorizedType;

    private String highEdu;

    private String highDegree;

    private Boolean isRetire;

    private Date retireTime;

    private Boolean isHonorRetire;

    private Integer adminLevel;

    private String post;

    private String proPost;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getDpPost() {
        return dpPost;
    }

    public void setDpPost(String dpPost) {
        this.dpPost = dpPost == null ? null : dpPost.trim();
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }

    public Date getDpGrowTime() {
        return dpGrowTime;
    }

    public void setDpGrowTime(Date dpGrowTime) {
        this.dpGrowTime = dpGrowTime;
    }

    public Boolean getIsPartyMember() {
        return isPartyMember;
    }

    public void setIsPartyMember(Boolean isPartyMember) {
        this.isPartyMember = isPartyMember;
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getAuthorizedType() {
        return authorizedType;
    }

    public void setAuthorizedType(String authorizedType) {
        this.authorizedType = authorizedType == null ? null : authorizedType.trim();
    }

    public String getHighEdu() {
        return highEdu;
    }

    public void setHighEdu(String highEdu) {
        this.highEdu = highEdu == null ? null : highEdu.trim();
    }

    public String getHighDegree() {
        return highDegree;
    }

    public void setHighDegree(String highDegree) {
        this.highDegree = highDegree == null ? null : highDegree.trim();
    }

    public Boolean getIsRetire() {
        return isRetire;
    }

    public void setIsRetire(Boolean isRetire) {
        this.isRetire = isRetire;
    }

    public Date getRetireTime() {
        return retireTime;
    }

    public void setRetireTime(Date retireTime) {
        this.retireTime = retireTime;
    }

    public Boolean getIsHonorRetire() {
        return isHonorRetire;
    }

    public void setIsHonorRetire(Boolean isHonorRetire) {
        this.isHonorRetire = isHonorRetire;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost == null ? null : proPost.trim();
    }
}