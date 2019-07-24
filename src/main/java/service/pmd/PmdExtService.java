package service.pmd;

import domain.member.MemberView;
import domain.pmd.PmdConfigMember;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import sys.utils.NumberUtils;

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
public class PmdExtService extends PmdBaseMapper{

    private Logger logger = LoggerFactory.getLogger(getClass());

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

    // 职称级别收费标准
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
            logger.error("异常", ex);
        }

        return map;
    }

    // 具有指定高级人才称号的党员
    // <userId, MemberTeacher>
    /*public Map<Integer, MemberTeacher> getRCCH(){

        // 人才称号
        Set<String> rcchList = getRcchNormMap().keySet();
        MemberTeacherExample example = new MemberTeacherExample();
        example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
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
    public int getMaxRCCHDuePay(MemberView member){

        int duePay = -1;

        String staffStatus = StringUtils.trim(member.getStaffStatus());
        String authorizedType = StringUtils.trim(member.getAuthorizedType());
        String talentTitle = StringUtils.trim(member.getTalentTitle());

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
    public PostDuePayBean getPostDuePay(MemberView member){

        int duePay = -1;
        String post = null;

        String staffStatus = StringUtils.trim(member.getStaffStatus());
        String authorizedType = StringUtils.trim(member.getAuthorizedType());

        if(StringUtils.equals(staffStatus, "在职")
                && StringUtils.equals(authorizedType, "事业编")) {

            String proPostLevel = StringUtils.trim(member.getProPostLevel());
            String manageLevel = StringUtils.trim(member.getManageLevel());
            String officeLevel = StringUtils.trim(member.getOfficeLevel());

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
        example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                .andStaffStatusEqualTo("在职").andAuthorizedTypeEqualTo("事业编");
        List<MemberTeacher> memberTeachers = memberTeacherMapper.selectByExample(example);

        Map<Integer, MemberTeacher>  resultMap = new HashMap<>();
        for (MemberTeacher memberTeacher : memberTeachers) {
            resultMap.put(memberTeacher.getUserId(), memberTeacher);
        }

        return resultMap;
    }*/

    public boolean isSYB(MemberView memberView){

        String staffStatus = StringUtils.trim(memberView.getStaffStatus());
        String authorizedType = StringUtils.trim(memberView.getAuthorizedType());

        return StringUtils.equals(staffStatus, "在职")
                && StringUtils.equals(authorizedType, "事业编");
    }

    // 在职非事业编党员（校聘、学生助理）
    // <userId, MemberTeacher>
    /*public Map<Integer, MemberTeacher> getFSYB(){

        MemberTeacherExample example = new MemberTeacherExample();
        example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                .andStaffStatusEqualTo("在职").andAuthorizedTypeNotEqualTo("事业编")
                .andStaffTypeIn(Arrays.asList("校聘", "学生助理"));
        List<MemberTeacher> memberTeachers = memberTeacherMapper.selectByExample(example);

        Map<Integer, MemberTeacher>  resultMap = new HashMap<>();
        for (MemberTeacher memberTeacher : memberTeachers) {
            resultMap.put(memberTeacher.getUserId(), memberTeacher);
        }

        return resultMap;
    }*/

    public boolean isXP(MemberView memberView){

        String staffStatus = StringUtils.trim(memberView.getStaffStatus());
        String authorizedType = StringUtils.trim(memberView.getAuthorizedType());
        String staffType = StringUtils.trim(memberView.getStaffType());

        return StringUtils.equals(staffStatus, "在职")
                && !StringUtils.equals(authorizedType, "事业编")
                && StringUtils.equals(staffType, "校聘") ;
    }

    // 校聘应交额度
    public int getXPDuePay(MemberView member){

        int duePay = -1;

        String staffStatus = StringUtils.trim(member.getStaffStatus());
        String authorizedType = StringUtils.trim(member.getAuthorizedType());
        String staffType = StringUtils.trim(member.getStaffType());
        String education = StringUtils.trim(member.getEducation());

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

    public boolean isXSZL(MemberView member){

        String staffStatus = StringUtils.trim(member.getStaffStatus());
        String authorizedType = StringUtils.trim(member.getAuthorizedType());
        String staffType = StringUtils.trim(member.getStaffType());

        return StringUtils.equals(staffStatus, "在职")
                && !StringUtils.equals(authorizedType, "事业编")
                && StringUtils.equals(staffType, "学生助理") ;
    }

    // 学生助理应交额度
    public int getXSZLDuePay(MemberView member){

        int duePay = -1;

        String staffStatus = StringUtils.trim(member.getStaffStatus());
        String authorizedType = StringUtils.trim(member.getAuthorizedType());
        String staffType = StringUtils.trim(member.getStaffType());

        if(StringUtils.equals(staffStatus, "在职")
                && !StringUtils.equals(authorizedType, "事业编")
                && StringUtils.equals(staffType, "学生助理")){

            duePay = 10;
        }

        return duePay;
    }

    // 离退休人员党费计算基数
    // <userId, MemberTeacher>
    public BigDecimal getRetireBase(String code){

        return iPmdMapper.getLatestRetireBase(code);
    }

    // 根据离退休人员党费计算基数计算党费
    public BigDecimal getDuePayFromRetireBase(BigDecimal base){

        BigDecimal duePay = null;
        
        if (base == null || base.compareTo(BigDecimal.ZERO) <= 0) {
            // 没有读取到离退休党费计算基数的情况？
            return null;
        }
        // 201901调整为党费计算基数的30%
        base = base.multiply(BigDecimal.valueOf(0.3));
        
        if (base.compareTo(BigDecimal.valueOf(5000)) > 0) {

            duePay = base.multiply(BigDecimal.valueOf(0.01));
        } else {

            duePay = base.multiply(BigDecimal.valueOf(0.005));
        }

        return duePay;
    }

    // 根据工资计算党费（ 针对在职、校聘教职工）
    public BigDecimal calDuePay(PmdConfigMember record) {

        BigDecimal gwgz = NumberUtils.trimToZero(record.getGwgz());
        BigDecimal xjgz = NumberUtils.trimToZero(record.getXjgz());
        BigDecimal gwjt = NumberUtils.trimToZero(record.getGwjt());
        BigDecimal zwbt = NumberUtils.trimToZero(record.getZwbt());
        BigDecimal zwbt1 = NumberUtils.trimToZero(record.getZwbt1());
        BigDecimal shbt = NumberUtils.trimToZero(record.getShbt());
        BigDecimal sbf = NumberUtils.trimToZero(record.getSbf());
        BigDecimal xlf = NumberUtils.trimToZero(record.getXlf());
        BigDecimal gzcx = NumberUtils.trimToZero(record.getGzcx());
        BigDecimal shiyebx = NumberUtils.trimToZero(record.getShiyebx());
        BigDecimal yanglaobx = NumberUtils.trimToZero(record.getYanglaobx());
        BigDecimal yiliaobx = NumberUtils.trimToZero(record.getYiliaobx());
        BigDecimal gsbx = NumberUtils.trimToZero(record.getGsbx());
        BigDecimal shengyubx = NumberUtils.trimToZero(record.getShengyubx());
        BigDecimal qynj = NumberUtils.trimToZero(record.getQynj());
        BigDecimal zynj = NumberUtils.trimToZero(record.getZynj());
        BigDecimal gjj = NumberUtils.trimToZero(record.getGjj());

        // 前几项合计
        BigDecimal total = gwgz.add(xjgz).add(gwjt).add(zwbt).add(zwbt1).add(shbt).add(sbf).add(xlf)
                .subtract(gzcx).subtract(shiyebx).subtract(yanglaobx)
                .subtract(yiliaobx).subtract(gsbx).subtract(shengyubx).subtract(qynj)
                .subtract(zynj).subtract(gjj);
        // 扣除5000的计税基数
        BigDecimal base = total.subtract(BigDecimal.valueOf(5000));
        if (base.compareTo(BigDecimal.ZERO) < 0) {
            base = BigDecimal.ZERO;
        }
        /*=IF(AND(U3>0,U3<=1500),U3*0.03,
                IF(AND(U3>1500,U3<=4500), (U3-1500)*0.1+45,
                        IF(AND(U3>4500,U3<=9000),(U3-4500)*0.2+345,
                                IF(AND(U3>9000,U3<=35000),(U3-9000)*0.25+1245,
                                        IF(AND(U3>35000,U3<=55000),(U3-35000)*0.3+7745,
                                                IF(AND(U3>55000,U3<=80000),(U3-55000)*0.35+13745,
                                                        IF(AND(U3>80000),(U3-80000)*0.45+22495)))))))*/
        // 税金
        /*BigDecimal tax = BigDecimal.ZERO;
        if (base.compareTo(BigDecimal.ZERO) > 0 && base.compareTo(BigDecimal.valueOf(1500)) <= 0) {
            tax = base.multiply(BigDecimal.valueOf(0.03));
        } else if (base.compareTo(BigDecimal.valueOf(1500)) > 0 && base.compareTo(BigDecimal.valueOf(4500)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(1500))).multiply(BigDecimal.valueOf(0.1));
            tax = tax.add(BigDecimal.valueOf(45));
        } else if (base.compareTo(BigDecimal.valueOf(4500)) > 0 && base.compareTo(BigDecimal.valueOf(9000)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(4500))).multiply(BigDecimal.valueOf(0.2));
            tax = tax.add(BigDecimal.valueOf(345));
        } else if (base.compareTo(BigDecimal.valueOf(9000)) > 0 && base.compareTo(BigDecimal.valueOf(35000)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(9000))).multiply(BigDecimal.valueOf(0.25));
            tax = tax.add(BigDecimal.valueOf(1245));
        } else if (base.compareTo(BigDecimal.valueOf(35000)) > 0 && base.compareTo(BigDecimal.valueOf(55000)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(35000))).multiply(BigDecimal.valueOf(0.3));
            tax = tax.add(BigDecimal.valueOf(7745));
        } else if (base.compareTo(BigDecimal.valueOf(55000)) > 0 && base.compareTo(BigDecimal.valueOf(80000)) <= 0) {
            tax = (base.subtract(BigDecimal.valueOf(55000))).multiply(BigDecimal.valueOf(0.35));
            tax = tax.add(BigDecimal.valueOf(13745));
        } else if (base.compareTo(BigDecimal.valueOf(80000)) > 0) {
            tax = (base.subtract(BigDecimal.valueOf(80000))).multiply(BigDecimal.valueOf(0.45));
            tax = tax.add(BigDecimal.valueOf(22495));
        }*/
        BigDecimal tax = BigDecimal.ZERO;
        if (base.compareTo(BigDecimal.ZERO) > 0 && base.compareTo(BigDecimal.valueOf(3000)) <= 0) {
            tax = base.multiply(BigDecimal.valueOf(0.03));
        } else if (base.compareTo(BigDecimal.valueOf(3000)) > 0 && base.compareTo(BigDecimal.valueOf(12000)) <= 0) {
            tax = base.multiply(BigDecimal.valueOf(0.1));
            tax = tax.subtract(BigDecimal.valueOf(210));
        } else if (base.compareTo(BigDecimal.valueOf(12000)) > 0 && base.compareTo(BigDecimal.valueOf(25000)) <= 0) {
            tax = base.multiply(BigDecimal.valueOf(0.2));
            tax = tax.subtract(BigDecimal.valueOf(1410));
        } else if (base.compareTo(BigDecimal.valueOf(25000)) > 0 && base.compareTo(BigDecimal.valueOf(35000)) <= 0) {
            tax = base.multiply(BigDecimal.valueOf(0.25));
            tax = tax.subtract(BigDecimal.valueOf(2660));
        } else if (base.compareTo(BigDecimal.valueOf(35000)) > 0 && base.compareTo(BigDecimal.valueOf(55000)) <= 0) {
            tax = base.multiply(BigDecimal.valueOf(0.3));
            tax = tax.subtract(BigDecimal.valueOf(4410));
        } else if (base.compareTo(BigDecimal.valueOf(55000)) > 0 && base.compareTo(BigDecimal.valueOf(80000)) <= 0) {
            tax = base.multiply(BigDecimal.valueOf(0.35));
            tax = tax.subtract(BigDecimal.valueOf(7160));
        } else if (base.compareTo(BigDecimal.valueOf(80000)) > 0) {
            tax = base.multiply(BigDecimal.valueOf(0.45));
            tax = tax.subtract(BigDecimal.valueOf(15160));
        }

        // 党费基数
        BigDecimal partyBase = total.subtract(tax);

        // 计算应交党费 =W3*IF(W3<=3000,0.5%,IF(W3<=5000,1%,IF(W3<=10000,1.5%,2%)))
        BigDecimal rate = BigDecimal.ZERO;
        if (partyBase.compareTo(BigDecimal.valueOf(3000)) <= 0) {
            rate = BigDecimal.valueOf(0.005);
        } else if (partyBase.compareTo(BigDecimal.valueOf(5000)) <= 0) {
            rate = BigDecimal.valueOf(0.01);
        } else if (partyBase.compareTo(BigDecimal.valueOf(10000)) <= 0) {
            rate = BigDecimal.valueOf(0.015);
        } else {
            rate = BigDecimal.valueOf(0.02);
        }
        
        if(partyBase.compareTo(BigDecimal.ZERO)<=0){
            logger.info("党费计算异常，工号{}", record.getUser().getCode());
            return null;
        }
        return partyBase.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
