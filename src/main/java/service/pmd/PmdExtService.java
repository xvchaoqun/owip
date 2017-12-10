package service.pmd;

import domain.member.MemberTeacher;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import persistence.member.MemberTeacherMapper;
import service.BaseMapper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/11/15.
 */
@Service
public class PmdExtService extends BaseMapper{

    @Autowired
    private MemberTeacherMapper memberTeacherMapper;

    private static Map<String, Integer> rcchNormMap;
    private static Map<String, Integer> proPostLevelNormMap;
    private static Map<String, Integer> managerLevelNormMap;
    private static Map<String, Integer> officeLevelNormMap;
    private static Map<String, Integer> eduNormMap;

    // 高级人才收费标准
    public Map<String, Integer> getRcchNormMap(){

        if(rcchNormMap!=null) return rcchNormMap;

        rcchNormMap = getPayMap("classpath:xml/pmd/rcch.xml");
        return rcchNormMap;
    }

    // 专技岗位等级收费标准
    public Map<String, Integer> getProPostLevelNormMap(){

        if(proPostLevelNormMap!=null) return proPostLevelNormMap;

        proPostLevelNormMap = getPayMap("classpath:xml/pmd/pro_post_level.xml");
        return proPostLevelNormMap;
    }

    // 管理岗位等级收费标准
    public Map<String, Integer> getManagerLevelNormMap(){

        if(managerLevelNormMap!=null) return managerLevelNormMap;

        managerLevelNormMap = getPayMap("classpath:xml/pmd/manage_level.xml");
        return managerLevelNormMap;
    }

    // 工勤岗位等级收费标准
    public Map<String, Integer> getOfficeLevelNormMap(){

        if(officeLevelNormMap!=null) return officeLevelNormMap;

        officeLevelNormMap = getPayMap("classpath:xml/pmd/office_level.xml");
        return officeLevelNormMap;
    }

    // （非事业编-校聘-最高学历）党费标准
    public Map<String, Integer> getEduNormMap(){

        if(eduNormMap!=null) return eduNormMap;

        eduNormMap = getPayMap("classpath:xml/pmd/xp_xl.xml");
        return eduNormMap;
    }

    private Map<String, Integer> getPayMap(String classpath){

        Map<String, Integer> map = new LinkedHashMap<>();
        try {
            SAXReader reader = new SAXReader();
            InputStream is = new FileInputStream(ResourceUtils.getFile(classpath));
            Document document = reader.read(is);
            List<Node> nodeList = document.selectNodes("//list/item");
            for (Node node : nodeList) {
                map.put(node.valueOf("@name").trim(), Integer.valueOf(node.getText()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return map;
    }

    // 具有指定高级人才称号的党员
    // <userId, MemberTeacher>
    /*public Map<Integer, MemberTeacher> getRCCH(){

        // 人才称号
        Set<String> rcchList = getRcchNormMap().keySet();
        MemberTeacherExample example = new MemberTeacherExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL)
                .andStaffStatusEqualTo("在职").andAuthorizedTypeEqualTo("事业编")
                .andTalentTitleRegexp(StringUtils.join(rcchList, "|"));

        List<MemberTeacher> memberTeachers = memberTeacherMapper.selectByExample(example);

        Map<Integer, MemberTeacher>  resultMap = new HashMap<>();
        for (MemberTeacher memberTeacher : memberTeachers) {
            resultMap.put(memberTeacher.getUserId(), memberTeacher);
        }

        return resultMap;
    }
*/
    // 如果是在职事业编的高级人才，返回对应的最大缴纳金额，否则返回-1
    public int getMaxRCCHDuePay(MemberTeacher memberTeacher){

        int duePay = -1;

        String staffStatus = StringUtils.trim(memberTeacher.getStaffStatus());
        String authorizedType = StringUtils.trim(memberTeacher.getAuthorizedType());
        String talentTitle = StringUtils.trim(memberTeacher.getTalentTitle());

        if(StringUtils.equals(staffStatus, "在职")
                && StringUtils.equals(authorizedType, "事业编")
                && StringUtils.isNotBlank(talentTitle)) {

            Map<String, Integer> rcchNormMap = getRcchNormMap();
            String[] talentTitles = talentTitle.split(",");
            for (String title : talentTitles) {
                if (StringUtils.isBlank(title)) continue;
                Integer _duePay = rcchNormMap.get(title.trim());
                if (_duePay != null && _duePay > duePay) {
                    // 取最大值
                    duePay = _duePay;
                }
            }
        }

        return duePay;
    }

    class PostDuePayBean{

        private int duePay;
        private String post;

        public PostDuePayBean(int duePay, String post) {
            this.duePay = duePay;
            this.post = post;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public int getDuePay() {
            return duePay;
        }

        public void setDuePay(int duePay) {
            this.duePay = duePay;
        }
    }

    // 根据专技、管理、工勤3种等级得到最高的缴纳金额，都不匹配否则返回-1
    public PostDuePayBean getPostDuePay(MemberTeacher memberTeacher){

        int duePay = -1;
        String post = null;

        String staffStatus = StringUtils.trim(memberTeacher.getStaffStatus());
        String authorizedType = StringUtils.trim(memberTeacher.getAuthorizedType());

        if(StringUtils.equals(staffStatus, "在职")
                && StringUtils.equals(authorizedType, "事业编")) {

            String proPostLevel = StringUtils.trim(memberTeacher.getProPostLevel());
            String manageLevel = StringUtils.trim(memberTeacher.getManageLevel());
            String officeLevel = StringUtils.trim(memberTeacher.getOfficeLevel());

            if(StringUtils.isNotBlank(proPostLevel)) {
                Integer _duePay = getProPostLevelNormMap().get(proPostLevel);
                if(_duePay!=null && _duePay > duePay){
                    duePay = _duePay;
                    post = proPostLevel;
                }
            }
            if(StringUtils.isNotBlank(manageLevel)) {
                Integer _duePay = getManagerLevelNormMap().get(manageLevel);
                if(_duePay!=null && _duePay > duePay){
                    duePay = _duePay;
                    post = manageLevel;
                }
            }
            if(StringUtils.isNotBlank(officeLevel)) {
                Integer _duePay = getOfficeLevelNormMap().get(officeLevel);
                if(_duePay!=null && _duePay > duePay){
                    duePay = _duePay;
                    post = officeLevel;
                }
            }
        }

        return new PostDuePayBean(duePay, post);
    }

    // 在职事业编党员
    // <userId, MemberTeacher>
   /* public Map<Integer, MemberTeacher> getSYB(){

        MemberTeacherExample example = new MemberTeacherExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL)
                .andStaffStatusEqualTo("在职").andAuthorizedTypeEqualTo("事业编");
        List<MemberTeacher> memberTeachers = memberTeacherMapper.selectByExample(example);

        Map<Integer, MemberTeacher>  resultMap = new HashMap<>();
        for (MemberTeacher memberTeacher : memberTeachers) {
            resultMap.put(memberTeacher.getUserId(), memberTeacher);
        }

        return resultMap;
    }*/

    public boolean isSYB(MemberTeacher memberTeacher){

        String staffStatus = StringUtils.trim(memberTeacher.getStaffStatus());
        String authorizedType = StringUtils.trim(memberTeacher.getAuthorizedType());

        return StringUtils.equals(staffStatus, "在职")
                && StringUtils.equals(authorizedType, "事业编");
    }

    // 在职非事业编党员（校聘、学生助理）
    // <userId, MemberTeacher>
    /*public Map<Integer, MemberTeacher> getFSYB(){

        MemberTeacherExample example = new MemberTeacherExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL)
                .andStaffStatusEqualTo("在职").andAuthorizedTypeNotEqualTo("事业编")
                .andStaffTypeIn(Arrays.asList("校聘", "学生助理"));
        List<MemberTeacher> memberTeachers = memberTeacherMapper.selectByExample(example);

        Map<Integer, MemberTeacher>  resultMap = new HashMap<>();
        for (MemberTeacher memberTeacher : memberTeachers) {
            resultMap.put(memberTeacher.getUserId(), memberTeacher);
        }

        return resultMap;
    }*/

    public boolean isXP(MemberTeacher memberTeacher){

        String staffStatus = StringUtils.trim(memberTeacher.getStaffStatus());
        String authorizedType = StringUtils.trim(memberTeacher.getAuthorizedType());
        String staffType = StringUtils.trim(memberTeacher.getStaffType());

        return StringUtils.equals(staffStatus, "在职")
                && !StringUtils.equals(authorizedType, "事业编")
                && StringUtils.equals(staffType, "校聘") ;
    }

    // 校聘应交额度
    public int getXPDuePay(MemberTeacher memberTeacher){

        int duePay = -1;

        String staffStatus = StringUtils.trim(memberTeacher.getStaffStatus());
        String authorizedType = StringUtils.trim(memberTeacher.getAuthorizedType());
        String staffType = StringUtils.trim(memberTeacher.getStaffType());
        String education = StringUtils.trim(memberTeacher.getEducation());

        if(StringUtils.equals(staffStatus, "在职")
                && !StringUtils.equals(authorizedType, "事业编")
                && StringUtils.equals(staffType, "校聘")
                && StringUtils.isNotBlank(education)){

            Map<String, Integer> eduNormMap = getEduNormMap();
            Integer _duePay = eduNormMap.get(education);
            if(_duePay!=null && _duePay>0){
                duePay = _duePay;
            }
        }

        return duePay;
    }

    public boolean isXSZL(MemberTeacher memberTeacher){

        String staffStatus = StringUtils.trim(memberTeacher.getStaffStatus());
        String authorizedType = StringUtils.trim(memberTeacher.getAuthorizedType());
        String staffType = StringUtils.trim(memberTeacher.getStaffType());

        return StringUtils.equals(staffStatus, "在职")
                && !StringUtils.equals(authorizedType, "事业编")
                && StringUtils.equals(staffType, "学生助理") ;
    }

    // 学生助理应交额度
    public int getXSZLDuePay(MemberTeacher memberTeacher){

        int duePay = -1;

        String staffStatus = StringUtils.trim(memberTeacher.getStaffStatus());
        String authorizedType = StringUtils.trim(memberTeacher.getAuthorizedType());
        String staffType = StringUtils.trim(memberTeacher.getStaffType());

        if(StringUtils.equals(staffStatus, "在职")
                && !StringUtils.equals(authorizedType, "事业编")
                && StringUtils.equals(staffType, "学生助理")){

            duePay = 10;
        }

        return duePay;
    }

    // 离退休费
    // <userId, MemberTeacher>
    public BigDecimal getLtxf(String code){

        return iExtMapper.getLtxf(code);
    }

    // 根据离退休费计算党费
    public BigDecimal getDuePayFromLtxf(BigDecimal ltxf){

        BigDecimal duePay = null;

        if (ltxf == null || ltxf.compareTo(BigDecimal.ZERO) <= 0) {
            // 没有读取到离退休工资的情况？
        } else if (ltxf.compareTo(BigDecimal.valueOf(5000)) > 0) {

            duePay = ltxf.multiply(BigDecimal.valueOf(0.01));
        } else {

            duePay = ltxf.multiply(BigDecimal.valueOf(0.005));
        }

        return duePay;
    }
}
