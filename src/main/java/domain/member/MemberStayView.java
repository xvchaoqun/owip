package domain.member;

import domain.sys.SysUserView;
import sys.jackson.SignRes;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class MemberStayView implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public SysUserView getOrgBranchAdmin(){
        return CmTag.getUserById(orgBranchAdminId);
    }
    public SysUserView getLastPrintUser(){

        return CmTag.getUserById(lastPrintUserId);
    }
    private Integer id;

    private String code;

    private Integer userId;

    private Integer partyId;

    private Integer branchId;

    private Integer toBranchId;

    private Integer orgBranchAdminId;

    private String orgBranchAdminPhone;

    private Integer userType;

    private String stayReason;

    private String mobile;

    private String phone;

    private String weixin;

    private String email;

    private String qq;

    private String inAddress;

    private String outAddress;

    private String name1;

    private String relate1;

    private String unit1;

    private String post1;

    private String phone1;

    private String mobile1;

    private String email1;

    private String name2;

    private String relate2;

    private String unit2;

    private String post2;

    private String phone2;

    private String mobile2;

    private String email2;

    @SignRes
    private String letter;

    private String country;

    private String school;

    private Date startTime;

    private Date endTime;

    private Date overDate;

    private Byte abroadType;

    private Date saveStartTime;

    private Date saveEndTime;

    private Date payTime;

    private Byte type;

    private Byte status;

    private Boolean isBack;

    private String reason;

    private Date createTime;

    private Date checkTime;

    private Integer printCount;

    private Date lastPrintTime;

    private Integer lastPrintUserId;

    private Byte memberStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

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

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getToBranchId() {
        return toBranchId;
    }

    public void setToBranchId(Integer toBranchId) {
        this.toBranchId = toBranchId;
    }

    public Integer getOrgBranchAdminId() {
        return orgBranchAdminId;
    }

    public void setOrgBranchAdminId(Integer orgBranchAdminId) {
        this.orgBranchAdminId = orgBranchAdminId;
    }

    public String getOrgBranchAdminPhone() {
        return orgBranchAdminPhone;
    }

    public void setOrgBranchAdminPhone(String orgBranchAdminPhone) {
        this.orgBranchAdminPhone = orgBranchAdminPhone == null ? null : orgBranchAdminPhone.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getStayReason() {
        return stayReason;
    }

    public void setStayReason(String stayReason) {
        this.stayReason = stayReason == null ? null : stayReason.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getInAddress() {
        return inAddress;
    }

    public void setInAddress(String inAddress) {
        this.inAddress = inAddress == null ? null : inAddress.trim();
    }

    public String getOutAddress() {
        return outAddress;
    }

    public void setOutAddress(String outAddress) {
        this.outAddress = outAddress == null ? null : outAddress.trim();
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1 == null ? null : name1.trim();
    }

    public String getRelate1() {
        return relate1;
    }

    public void setRelate1(String relate1) {
        this.relate1 = relate1 == null ? null : relate1.trim();
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1 == null ? null : unit1.trim();
    }

    public String getPost1() {
        return post1;
    }

    public void setPost1(String post1) {
        this.post1 = post1 == null ? null : post1.trim();
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1 == null ? null : phone1.trim();
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1 == null ? null : mobile1.trim();
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1 == null ? null : email1.trim();
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2 == null ? null : name2.trim();
    }

    public String getRelate2() {
        return relate2;
    }

    public void setRelate2(String relate2) {
        this.relate2 = relate2 == null ? null : relate2.trim();
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2 == null ? null : unit2.trim();
    }

    public String getPost2() {
        return post2;
    }

    public void setPost2(String post2) {
        this.post2 = post2 == null ? null : post2.trim();
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2 == null ? null : phone2.trim();
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2 == null ? null : mobile2.trim();
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2 == null ? null : email2.trim();
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter == null ? null : letter.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
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

    public Date getOverDate() {
        return overDate;
    }

    public void setOverDate(Date overDate) {
        this.overDate = overDate;
    }

    public Byte getAbroadType() {
        return abroadType;
    }

    public void setAbroadType(Byte abroadType) {
        this.abroadType = abroadType;
    }

    public Date getSaveStartTime() {
        return saveStartTime;
    }

    public void setSaveStartTime(Date saveStartTime) {
        this.saveStartTime = saveStartTime;
    }

    public Date getSaveEndTime() {
        return saveEndTime;
    }

    public void setSaveEndTime(Date saveEndTime) {
        this.saveEndTime = saveEndTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public Boolean getIsBack() {
        return isBack;
    }

    public void setIsBack(Boolean isBack) {
        this.isBack = isBack;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public Date getLastPrintTime() {
        return lastPrintTime;
    }

    public void setLastPrintTime(Date lastPrintTime) {
        this.lastPrintTime = lastPrintTime;
    }

    public Integer getLastPrintUserId() {
        return lastPrintUserId;
    }

    public void setLastPrintUserId(Integer lastPrintUserId) {
        this.lastPrintUserId = lastPrintUserId;
    }

    public Byte getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Byte memberStatus) {
        this.memberStatus = memberStatus;
    }
}