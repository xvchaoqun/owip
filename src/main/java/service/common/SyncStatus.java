package service.common;

import org.apache.commons.lang3.RegExUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 同步字段是否只同步一次，0或空代表每次都同步覆盖，1代表仅同步一次，后面不覆盖，
 * 字段顺序：姓名、性别、出生年月、身份证号码、民族、籍贯、出生地、
 * 户籍地、职称、手机号、邮箱、办公电话、家庭电话、头像、专业技术职务级别、所在单位
 */
public class SyncStatus {

    public boolean realname;
    public boolean gender;
    public boolean birth;
    public boolean idcard;
    public boolean nation;
    public boolean nativePlace;
    public boolean homeplace; // 出生地
    public boolean household;
    public boolean proPost; // 专业技术职务（职称）
    public boolean mobile;
    public boolean email;
    public boolean phone;
    public boolean homePhone;
    public boolean avatar;
    public boolean proPostLevel; // 专业技术职务级别（职称）
    public boolean unit; // 所在单位（院系）

    public SyncStatus(Integer sync) {

        sync = (sync==null || sync<0)?0:sync;
        String status = Integer.toBinaryString(sync);

        fromSync(status);
    }

    public SyncStatus(String sync) {

        fromSync(sync);
    }

    private void fromSync(String status) {

        status = RegExUtils.replaceAll(status, "[^01]", "0");

        int i = 0;

        // 倒序解析
        unit = getBit(status, i++);
        proPostLevel = getBit(status, i++);
        avatar = getBit(status, i++);
        homePhone = getBit(status, i++);
        phone = getBit(status, i++);
        email = getBit(status, i++);
        mobile = getBit(status, i++);
        proPost = getBit(status, i++); // 专业技术职务（职称）
        household = getBit(status, i++);
        homeplace = getBit(status, i++); // 出生地
        nativePlace = getBit(status, i++);
        nation = getBit(status, i++);
        idcard = getBit(status, i++);
        birth = getBit(status, i++);
        gender = getBit(status, i++);
        realname = getBit(status, i++);
    }

    public int toSync(){

        // 正序生成
        return Integer.parseUnsignedInt(toBit(realname)
                + toBit(gender)
                + toBit(birth)
                + toBit(idcard)
                + toBit(nation)
                + toBit(nativePlace)
                + toBit(homeplace)
                + toBit(household)
                + toBit(proPost)
                + toBit(mobile)
                + toBit(email)
                + toBit(phone)
                + toBit(homePhone)
                + toBit(avatar)
                + toBit(proPostLevel)
                + toBit(unit), 2);
    }

    public void combine(SyncStatus other){

        this.realname = this.realname || other.realname;
        this.gender = this.gender || other.gender;
        this.birth = this.birth || other.birth;
        this.idcard = this.idcard || other.idcard;
        this.nation = this.nation || other.nation;
        this.nativePlace = this.nativePlace || other.nativePlace;
        this.homeplace = this.homeplace || other.homeplace;
        this.household = this.household || other.household;
        this.proPost = this.proPost || other.proPost;
        this.mobile = this.mobile || other.mobile;
        this.email = this.email || other.email;
        this.phone = this.phone || other.phone;
        this.homePhone = this.homePhone || other.homePhone;
        this.avatar = this.avatar || other.avatar;
        this.proPostLevel = this.proPostLevel || other.proPostLevel;
        this.unit = this.unit || other.unit;
    }

    public void setByNames(String names){

        if(names==null) return;
        String[] nameArray = names.split(",");
        Set<String> nameSet = new HashSet<>();
        nameSet.addAll(Arrays.asList(nameArray));

        this.realname = this.realname || nameSet.contains("realname");
        this.gender = this.gender || nameSet.contains("gender");
        this.birth = this.birth || nameSet.contains("birth");
        this.idcard = this.idcard || nameSet.contains("idcard");
        this.nation = this.nation || nameSet.contains("nation");
        this.nativePlace = this.nativePlace || nameSet.contains("nativePlace");
        this.homeplace = this.homeplace || nameSet.contains("homeplace");
        this.household = this.household || nameSet.contains("household");
        this.proPost = this.proPost || nameSet.contains("proPost");
        this.mobile = this.mobile || nameSet.contains("mobile");
        this.email = this.email || nameSet.contains("email");
        this.phone = this.phone || nameSet.contains("phone");
        this.homePhone = this.homePhone || nameSet.contains("homePhone");
        this.avatar = this.avatar || nameSet.contains("avatar");
        this.proPostLevel = this.proPostLevel || nameSet.contains("proPostLevel");
        this.unit = this.unit || nameSet.contains("unit");
    }

    // pos是status的倒序位数
    private boolean getBit(String status, int pos){

        if(status==null) return false;

        int len = status.length();
        if(pos >= len) return false;

        return status.charAt(len - pos - 1) == '1';
    }

    private String toBit(boolean col){

        return col?"1":"0";
    }

    public static void main(String[] args) {
        String sync = "12w122001";
        sync = sync.replaceAll("[^01]", "0");
        System.out.println("sync = " + sync);

        int status = Integer.parseUnsignedInt(sync,2);
        System.out.println("status = " + status);

        String _status = Integer.toBinaryString(status);
        System.out.println("_status = " + _status);
    }

    public boolean isRealname() {
        return realname;
    }

    public boolean isGender() {
        return gender;
    }

    public boolean isBirth() {
        return birth;
    }

    public boolean isIdcard() {
        return idcard;
    }

    public boolean isNation() {
        return nation;
    }

    public boolean isNativePlace() {
        return nativePlace;
    }

    public boolean isHomeplace() {
        return homeplace;
    }

    public boolean isHousehold() {
        return household;
    }

    public boolean isProPost() {
        return proPost;
    }

    public boolean isMobile() {
        return mobile;
    }

    public boolean isEmail() {
        return email;
    }

    public boolean isPhone() {
        return phone;
    }

    public boolean isHomePhone() {
        return homePhone;
    }

    public boolean isAvatar() {
        return avatar;
    }

    public boolean isProPostLevel() { return proPostLevel; }

    public boolean isUnit() {
        return unit;
    }
}
