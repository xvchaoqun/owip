package domain.sys;

import org.springframework.format.annotation.DateTimeFormat;
import sys.jackson.SignRes;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class SysUserInfo implements Serializable {
    private Integer userId;

    private String realname;

    private String userStatus;

    private String country;

    private String idcardType;

    private String idcard;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date birth;

    @SignRes
    private String avatar;

    private Date avatarUploadTime;

    private Byte gender;

    private String nation;

    private String nativePlace;

    private String homeplace;

    private String household;

    private String specialty;

    private Integer health;

    private String sign;

    private String mobile;

    private String post;

    private String msgMobile;

    private Boolean notSendMsg;

    private String fileNumber;

    private String unit;

    private String phone;

    private String homePhone;

    private String email;

    private String mailingAddress;

    private String msgTitle;

    private String resume;

    private Integer sync;

    private String resIdsAdd;

    private String mResIdsAdd;

    private String resIdsMinus;

    private String mResIdsMinus;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getIdcardType() {
        return idcardType;
    }

    public void setIdcardType(String idcardType) {
        this.idcardType = idcardType == null ? null : idcardType.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Date getAvatarUploadTime() {
        return avatarUploadTime;
    }

    public void setAvatarUploadTime(Date avatarUploadTime) {
        this.avatarUploadTime = avatarUploadTime;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
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

    public String getHomeplace() {
        return homeplace;
    }

    public void setHomeplace(String homeplace) {
        this.homeplace = homeplace == null ? null : homeplace.trim();
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household == null ? null : household.trim();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getMsgMobile() {
        return msgMobile;
    }

    public void setMsgMobile(String msgMobile) {
        this.msgMobile = msgMobile == null ? null : msgMobile.trim();
    }

    public Boolean getNotSendMsg() {
        return notSendMsg;
    }

    public void setNotSendMsg(Boolean notSendMsg) {
        this.notSendMsg = notSendMsg;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber == null ? null : fileNumber.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone == null ? null : homePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress == null ? null : mailingAddress.trim();
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume == null ? null : resume.trim();
    }

    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }

    public String getResIdsAdd() {
        return resIdsAdd;
    }

    public void setResIdsAdd(String resIdsAdd) {
        this.resIdsAdd = resIdsAdd == null ? null : resIdsAdd.trim();
    }

    public String getmResIdsAdd() {
        return mResIdsAdd;
    }

    public void setmResIdsAdd(String mResIdsAdd) {
        this.mResIdsAdd = mResIdsAdd == null ? null : mResIdsAdd.trim();
    }

    public String getResIdsMinus() {
        return resIdsMinus;
    }

    public void setResIdsMinus(String resIdsMinus) {
        this.resIdsMinus = resIdsMinus == null ? null : resIdsMinus.trim();
    }

    public String getmResIdsMinus() {
        return mResIdsMinus;
    }

    public void setmResIdsMinus(String mResIdsMinus) {
        this.mResIdsMinus = mResIdsMinus == null ? null : mResIdsMinus.trim();
    }
}