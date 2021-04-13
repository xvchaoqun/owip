package ext.service;

import com.google.gson.JsonObject;
import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberView;
import domain.party.Branch;
import domain.party.Party;
import domain.pmd.*;
import domain.sys.SysUserView;
import ext.domain.ExtJzgSalary;
import ext.domain.ExtRetireSalary;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import persistence.common.CommonMapper;
import persistence.member.common.IMemberMapper;
import persistence.pmd.PmdConfigMemberMapper;
import persistence.pmd.PmdMemberMapper;
import persistence.pmd.PmdMemberPayMapper;
import persistence.pmd.common.IPmdMapper;
import service.party.BranchService;
import service.party.MemberService;
import service.party.PartyService;
import service.pmd.PmdConfigMemberService;
import service.pmd.PmdConfigMemberTypeService;
import service.pmd.PmdConfigResetService;
import service.pmd.PmdMonthService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;
import sys.gson.GsonUtils;
import sys.helper.PmdHelper;
import sys.tags.CmTag;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lm on 2017/11/15.
 */
@Service
public class PmdExtService {

    @Autowired
    protected IPmdMapper iPmdMapper;
    @Autowired
    protected PmdConfigMemberMapper pmdConfigMemberMapper;
    @Autowired
    protected PmdMemberMapper pmdMemberMapper;
    @Autowired
    protected PmdMemberPayMapper pmdMemberPayMapper;
    @Autowired
    protected PmdMonthService pmdMonthService;
    @Autowired
    protected IMemberMapper iMemberMapper;
    @Autowired
    protected CommonMapper commonMapper;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    private PmdExtService pmdExtService;
    @Autowired
    private PmdConfigMemberService pmdConfigMemberService;
    @Autowired
    private PmdConfigMemberTypeService pmdConfigMemberTypeService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PmdConfigResetService pmdConfigResetService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String, Integer> rcchNormMap;
    private static Map<String, Integer> proPostLevelNormMap;
    private static Map<String, Integer> managerLevelNormMap;
    private static Map<String, Integer> officeLevelNormMap;
    private static Map<String, Integer> eduNormMap;

    // 高级人才收费标准
    @Deprecated
    public Map<String, Integer> getRcchNormMap(){

        if(rcchNormMap!=null) return rcchNormMap;

        rcchNormMap = getPayMap("classpath:xml/pmd/rcch.xml");
        return rcchNormMap;
    }

    // 职称级别收费标准
    @Deprecated
    public Map<String, Integer> getProPostLevelNormMap(){

        if(proPostLevelNormMap!=null) return proPostLevelNormMap;

        proPostLevelNormMap = getPayMap("classpath:xml/pmd/pro_post_level.xml");
        return proPostLevelNormMap;
    }

    // 管理岗位等级收费标准
    @Deprecated
    public Map<String, Integer> getManagerLevelNormMap(){

        if(managerLevelNormMap!=null) return managerLevelNormMap;

        managerLevelNormMap = getPayMap("classpath:xml/pmd/manage_level.xml");
        return managerLevelNormMap;
    }

    // 工勤岗位等级收费标准
    @Deprecated
    public Map<String, Integer> getOfficeLevelNormMap(){

        if(officeLevelNormMap!=null) return officeLevelNormMap;

        officeLevelNormMap = getPayMap("classpath:xml/pmd/office_level.xml");
        return officeLevelNormMap;
    }

    // （非事业编-校聘-最高学历）党费标准
    @Deprecated
    public Map<String, Integer> getEduNormMap(){

        if(eduNormMap!=null) return eduNormMap;

        eduNormMap = getPayMap("classpath:xml/pmd/xp_xl.xml");
        return eduNormMap;
    }
    @Deprecated
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
    @Deprecated
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

    // 保存工资项到 党员缴费分类 表
    public String getSalaryJSON(ExtJzgSalary ejs) {

        BigDecimal gwgz = ejs.getGwgz();
        BigDecimal xpgz = ejs.getXpgz();
        if (gwgz == null || (xpgz != null && gwgz.compareTo(xpgz) < 0))
            gwgz = xpgz;
        Map<String, BigDecimal> salaryMap = new HashMap<>();

        salaryMap.put("gwgz", gwgz);
        salaryMap.put("xjgz", ejs.getXjgz());
        salaryMap.put("gwjt", ejs.getGwjt());
        salaryMap.put("zwbt", ejs.getZwbt());
        salaryMap.put("zwbt1", ejs.getZwbt1());
        salaryMap.put("shbt", ejs.getShbt());
        salaryMap.put("sbf", ejs.getSbf());
        salaryMap.put("xlf", ejs.getXlf());

        BigDecimal gzcx = ejs.getGzcx();
        if (gzcx != null) {
            gzcx = gzcx.multiply(BigDecimal.valueOf(-1));
        }
        salaryMap.put("gzcx", gzcx);
        salaryMap.put("shiyebx", ejs.getSygr());
        salaryMap.put("yanglaobx", ejs.getYanglaogr());
        salaryMap.put("yiliaobx", ejs.getYiliaogr());
        salaryMap.put("zynj", ejs.getNjgr());
        salaryMap.put("gjj", ejs.getZfgjj());

        return JSONUtils.toString(salaryMap, false);
    }

    @Deprecated
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
    @Deprecated
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

    // 校聘应交额度
    @Deprecated
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

    @Deprecated
    public boolean isXSZL(MemberView member){

        String staffStatus = StringUtils.trim(member.getStaffStatus());
        String authorizedType = StringUtils.trim(member.getAuthorizedType());
        String staffType = StringUtils.trim(member.getStaffType());

        return StringUtils.equals(staffStatus, "在职")
                && !StringUtils.equals(authorizedType, "事业编")
                && StringUtils.equals(staffType, "学生助理") ;
    }

    // 学生助理应交额度
    @Deprecated
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

    public boolean isSYB(MemberView memberView){

        String staffStatus = StringUtils.trim(memberView.getStaffStatus());
        String authorizedType = StringUtils.trim(memberView.getAuthorizedType());

        return StringUtils.equals(staffStatus, "在职")
                && StringUtils.equals(authorizedType, "事业编");
    }

    public boolean isXP(MemberView memberView){

        String staffStatus = StringUtils.trim(memberView.getStaffStatus());
        String authorizedType = StringUtils.trim(memberView.getAuthorizedType());
        String staffType = StringUtils.trim(memberView.getStaffType());

        return StringUtils.equals(staffStatus, "在职")
                && !StringUtils.equals(authorizedType, "事业编")
                && StringUtils.equals(staffType, "校聘") ;
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

    private BigDecimal getParamValue(HttpServletRequest req, String paramName){

        return RequestUtils.getAsBigDecimal(req, paramName);
    }
    
    // 表单参数转换成工资JSON
    public String formSalaryToJSON(HttpServletRequest req){

        Map<String, BigDecimal> salaryMap = new HashMap<>();
        salaryMap.put("gwgz", getParamValue(req, "gwgz"));
        salaryMap.put("xjgz", getParamValue(req, "xjgz"));
        salaryMap.put("gwjt", getParamValue(req, "gwjt"));
        salaryMap.put("zwbt", getParamValue(req, "zwbt"));
        salaryMap.put("zwbt1", getParamValue(req, "zwbt1"));
        salaryMap.put("shbt", getParamValue(req, "shbt"));
        salaryMap.put("sbf", getParamValue(req, "sbf"));
        salaryMap.put("xlf", getParamValue(req, "xlf"));
        salaryMap.put("gzcx", getParamValue(req, "gzcx"));
        salaryMap.put("shiyebx", getParamValue(req, "shiyebx"));
        salaryMap.put("yanglaobx", getParamValue(req, "yanglaobx"));
        salaryMap.put("yiliaobx", getParamValue(req, "yiliaobx"));
        salaryMap.put("zynj", getParamValue(req, "zynj"));
        salaryMap.put("gjj", getParamValue(req, "gjj"));

        return JSONUtils.toString(salaryMap, false);
    }

    private BigDecimal getJsonObjValue(JsonObject jo, String name){

        return NumberUtils.trimToZero(GsonUtils.getAsBigDecimal(jo, name));
    }
    // 根据工资计算党费（ 针对在职、校聘教职工）
    public BigDecimal calDuePay(int userId, String salaryJSON)  {

        if(StringUtils.isBlank(salaryJSON)) return null;
        JsonObject jo = GsonUtils.toJsonObject(salaryJSON);
        if(jo==null) return null;

        BigDecimal gwgz = getJsonObjValue(jo, "gwgz");
        BigDecimal xjgz = getJsonObjValue(jo, "xjgz");
        BigDecimal gwjt = getJsonObjValue(jo, "gwjt");
        BigDecimal zwbt = getJsonObjValue(jo, "zwbt");
        BigDecimal zwbt1 = getJsonObjValue(jo, "zwbt1");
        BigDecimal shbt = getJsonObjValue(jo, "shbt");
        BigDecimal sbf = getJsonObjValue(jo, "sbf");
        BigDecimal xlf = getJsonObjValue(jo, "xlf");
        BigDecimal gzcx = getJsonObjValue(jo, "gzcx");
        BigDecimal shiyebx = getJsonObjValue(jo, "shiyebx");
        BigDecimal yanglaobx = getJsonObjValue(jo, "yanglaobx");
        BigDecimal yiliaobx = getJsonObjValue(jo, "yiliaobx");
        BigDecimal gsbx = getJsonObjValue(jo, "gsbx");
        BigDecimal shengyubx = getJsonObjValue(jo, "shengyubx");
        BigDecimal qynj = getJsonObjValue(jo, "qynj");
        BigDecimal zynj = getJsonObjValue(jo, "zynj");
        BigDecimal gjj = getJsonObjValue(jo, "gjj");

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
            logger.info("党费计算有误，工号{}", CmTag.getUserById(userId).getCode());
            return null;
        }
        return partyBase.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    public void extJzgSalary_export(String salaryMonth, List<ExtJzgSalary> records, HttpServletResponse response) {

        int rownum = records.size();
        String[] titles = {"日期|100","工号|100","姓名|50","所在党组织|350|left","所在党支部|350|left","校聘工资|80", "薪级工资|80",
                "岗位工资|80","岗位津贴|80","职务补贴|80","职务补贴1|80","生活补贴|80",
                "书报费|80","洗理费|80","工资冲销|80","失业个人|80","养老个人|80",
                "医疗个人|80","年金个人|80","住房公积金|80","在职人员工资合计|80","校聘人员工资合计|80"};
        List<String[]> valuesList = new ArrayList<>();
         Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        for (int i = 0; i < rownum; i++) {
            ExtJzgSalary record = records.get(i);
            String partyName = null;
            String branchName = null;
            SysUserView uv = sysUserService.findByCode(record.getZgh());
            if(uv!=null){
                Member member = memberService.get(uv.getUserId());
                if(member!=null){
                    Party party = partyMap.get(member.getPartyId());
                    if(party!=null){
                        partyName = party.getName();
                    }
                    if(member.getBranchId()!=null) {
                        Branch branch = branchMap.get(member.getBranchId());
                        if (branch != null) {
                            branchName = branch.getName();
                        }
                    }
                }
            }
            String[] values = {
                    record.getRq(),
                    record.getZgh(),
                    record.getXm(),
                    partyName,
                    branchName,
                    NumberUtils.stripTrailingZeros(record.getXpgz()),
                    NumberUtils.stripTrailingZeros(record.getXjgz()),

                    NumberUtils.stripTrailingZeros(record.getGwgz()),
                    NumberUtils.stripTrailingZeros(record.getGwjt()),
                    NumberUtils.stripTrailingZeros(record.getZwbt()),
                    NumberUtils.stripTrailingZeros(record.getZwbt1()),
                    NumberUtils.stripTrailingZeros(record.getShbt()),

                    NumberUtils.stripTrailingZeros(record.getSbf()),
                    NumberUtils.stripTrailingZeros(record.getXlf()),
                    NumberUtils.stripTrailingZeros(record.getGzcx()),
                    NumberUtils.stripTrailingZeros(record.getSygr()),
                    NumberUtils.stripTrailingZeros(record.getYanglaogr()),

                    NumberUtils.stripTrailingZeros(record.getYiliaogr()),
                    NumberUtils.stripTrailingZeros(record.getNjgr()),
                    NumberUtils.stripTrailingZeros(record.getZfgjj()),
                    NumberUtils.stripTrailingZeros(record.getZzryhj()),
                    NumberUtils.stripTrailingZeros(record.getXpryhj())
            };
            valuesList.add(values);
        }
        String fileName = "在职教职工党费工资基数(" + salaryMonth + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    public void extRetireSalary_export(String salaryMonth, List<ExtRetireSalary> records, HttpServletResponse response) {

        int rownum = records.size();
        String[] titles = {"日期|100","工号|100","姓名|50","所在党组织|350|left","所在党支部|350|left","党费计算基数|80"};
        List<String[]> valuesList = new ArrayList<>();

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        for (int i = 0; i < rownum; i++) {
            ExtRetireSalary record = records.get(i);
            String realname = null;
            String partyName = null;
            String branchName = null;
            SysUserView uv = sysUserService.findByCode(record.getZgh());
            if(uv!=null){
                realname = uv.getRealname();
                Member member = memberService.get(uv.getUserId());
                if(member!=null){
                    Party party = partyMap.get(member.getPartyId());
                    if(party!=null){
                        partyName = party.getName();
                    }
                    if(member.getBranchId()!=null) {
                        Branch branch = branchMap.get(member.getBranchId());
                        if (branch != null) {
                            branchName = branch.getName();
                        }
                    }
                }
            }
            String[] values = {
                    record.getRq(),
                    record.getZgh(),
                    realname,
                    partyName,
                    branchName,
                    NumberUtils.stripTrailingZeros(record.getBase())
            };
            valuesList.add(values);
        }
        String fileName = "离退休党费计算基数(" + salaryMonth + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 添加或重置党员缴费记录
    @Transactional
    public PmdMember addOrResetMember(Integer pmdMemberId,
                                      PmdMonth pmdMonth,
                                      Member member, boolean forceResetPmdConfigMember) {

        int monthId = pmdMonth.getId();
        int userId = member.getUserId();
        SysUserView uv = sysUserService.findById(userId);

        /**
         * 读取党员缴费分类
          */
        // 党员分类
        Byte configMemberType = null;
        // 标准对应的额度，系统自动计算得到
        BigDecimal duePay = null;
        // 党员分类别
        Integer configMemberTypeId = null;
        // 离退休人员党费计算基数
        BigDecimal retireBase = null;

        Boolean hasSalary = null;
        // 是否需要提交工资明细，如果是A1、A2类别的党员还没提交工资明细则需要，否则不需要（辅助字段）
        Boolean needSetSalary = false;
        // 党费缴纳标准（辅助字段）
        String duePayReason = null;

        Boolean isOnlinePay = true;

        if(forceResetPmdConfigMember){ // 强制重置

            pmdConfigMemberService.del(userId);
        }

        PmdConfigMember pmdConfigMember = pmdConfigMemberMapper.selectByPrimaryKey(userId);
        if (pmdConfigMember != null && pmdConfigMember.getConfigMemberType() != null) {
            configMemberType = pmdConfigMember.getConfigMemberType();
            duePay = pmdConfigMember.getDuePay();
            configMemberTypeId = pmdConfigMember.getConfigMemberTypeId();
            retireBase = pmdConfigMember.getRetireSalary();
            if (uv.isStudent()) {
                hasSalary = BooleanUtils.isTrue(pmdConfigMember.getHasSalary());
            }
            needSetSalary = BooleanUtils.isNotTrue(pmdConfigMember.getHasSetSalary());
            isOnlinePay = pmdConfigMember.getIsOnlinePay();

            if(pmdMemberId!=null){

                isOnlinePay = true;
                // 缴费方式更新为线上缴费（现金缴费修改为线上缴费时调用）
                PmdConfigMember record = new PmdConfigMember();
                record.setUserId(userId);
                record.setIsOnlinePay(true);
                pmdConfigMemberMapper.updateByPrimaryKeySelective(record);
            }

        } else {
            if (uv.isStudent()) {
                configMemberType = PmdConstants.PMD_MEMBER_TYPE_STUDENT;
            } else {
                MemberView memberView = iMemberMapper.getMemberView(userId);
                configMemberType = uv.isRetire() ? PmdConstants.PMD_MEMBER_TYPE_RETIRE
                        : PmdConstants.PMD_MEMBER_TYPE_ONJOB;
                // 附属学校
                Set<String> partyCodeSet = new HashSet<>();
                partyCodeSet.add("030300");
                partyCodeSet.add("030500");
                int partyId = member.getPartyId();
                Party party = partyService.findAll().get(partyId);
                String partyCode = party.getCode();
                if (partyCodeSet.contains(partyCode)) {
                    configMemberType = PmdConstants.PMD_MEMBER_TYPE_OTHER;
                }

                Map<Byte, PmdConfigMemberType> formulaMap = pmdConfigMemberTypeService.formulaMap();
                if (configMemberType == PmdConstants.PMD_MEMBER_TYPE_RETIRE) {

                    // 设定分类别：离退休
                    PmdConfigMemberType pmdConfigMemberType = formulaMap.get(PmdConstants.PMD_FORMULA_TYPE_RETIRE);
                    if (pmdConfigMemberType != null) {
                        configMemberTypeId = pmdConfigMemberType.getId();
                    }

                    retireBase = pmdExtService.getRetireBase(memberView.getCode());
                    duePay = pmdExtService.getDuePayFromRetireBase(retireBase);
                } else {
                    boolean syb = pmdExtService.isSYB(memberView);
                    if (syb) {

                        needSetSalary = true;

                        // 设定分类别：在职在编教职工
                        PmdConfigMemberType pmdConfigMemberType = formulaMap.get(PmdConstants.PMD_FORMULA_TYPE_ONJOB);
                        if (pmdConfigMemberType != null) {
                            configMemberTypeId = pmdConfigMemberType.getId();
                        }

                    } else if (pmdExtService.isXP(memberView)) {

                        needSetSalary = true;

                        // 设定分类别：校聘教职工
                        PmdConfigMemberType pmdConfigMemberType = formulaMap.get(PmdConstants.PMD_FORMULA_TYPE_EXTERNAL);
                        if (pmdConfigMemberType != null) {
                            configMemberTypeId = pmdConfigMemberType.getId();
                        }
                    }
                }
            }

            PmdConfigMember record = new PmdConfigMember();
            record.setUserId(userId);
            record.setConfigMemberType(configMemberType);
            record.setConfigMemberTypeId(configMemberTypeId);
            record.setDuePay(duePay);
            record.setRetireSalary(retireBase);
            // 默认线上缴费
            record.setIsOnlinePay(true);
            // ???
            record.setHasReset(true);

            pmdConfigMemberService.insertSelective(record);

            // 更新新加入缴费党员的计算工资
            String salaryMonth = DateUtils.formatDate(pmdConfigResetService.getSalaryMonth(), "yyyyMM");
            if(salaryMonth!=null) {

                ExtJzgSalary ejs = iPmdMapper.getExtJzgSalary(salaryMonth, uv.getCode());
                pmdConfigResetService.updateDuePayByJzgSalary(ejs);
                // 也可能是离退休老师
                ExtRetireSalary ers = iPmdMapper.getExtRetireSalary(salaryMonth, uv.getCode());
                pmdConfigResetService.updateDuePayByRetireSalary(ers);
            }
        }

        Integer _pmdMemberId = null;
        // 新建党员快照
        PmdMember _pmdMember = new PmdMember();
        {
            _pmdMember.setMonthId(monthId);
            _pmdMember.setPayMonth(pmdMonth.getPayMonth());
            _pmdMember.setUserId(userId);
            _pmdMember.setPartyId(member.getPartyId());
            _pmdMember.setBranchId(member.getBranchId());

            if(configMemberType != PmdConstants.PMD_MEMBER_TYPE_STUDENT){

                MemberView memberView = iMemberMapper.getMemberView(userId);
                _pmdMember.setTalentTitle(memberView.getTalentTitle());
                _pmdMember.setPostClass(memberView.getPostClass());
                _pmdMember.setMainPostLevel(memberView.getMainPostLevel());
                _pmdMember.setProPostLevel(memberView.getProPostLevel());
                _pmdMember.setManageLevel(memberView.getManageLevel());
                _pmdMember.setOfficeLevel(memberView.getOfficeLevel());
                _pmdMember.setAuthorizedType(memberView.getAuthorizedType());
                _pmdMember.setStaffType(memberView.getStaffType());
            }

            _pmdMember.setType(configMemberType);
            if(configMemberTypeId!=null){
                PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(configMemberTypeId);
                if(pmdConfigMemberType!=null) {
                    PmdNorm pmdNorm = pmdConfigMemberType.getPmdNorm();

                    _pmdMember.setConfigMemberTypeId(configMemberTypeId);
                    _pmdMember.setConfigMemberTypeName(pmdConfigMemberType.getName());
                    _pmdMember.setConfigMemberTypeNormId(pmdConfigMemberType.getNormId());
                    _pmdMember.setConfigMemberTypeNormName(pmdNorm.getName());

                    duePayReason = pmdConfigMemberType.getName();
                    /*if(pmdNorm.getType() == PmdConstants.PMD_NORM_SET_TYPE_FORMULA){
                        switch (pmdNorm.getFormulaType()){
                            case PmdConstants.PMD_FORMULA_TYPE_ONJOB:
                            case PmdConstants.PMD_FORMULA_TYPE_EXTERNAL:
                                needSetSalary = true;
                                break;
                        }
                    }*/
                }
            }
            _pmdMember.setConfigMemberDuePay(duePay);
            _pmdMember.setSalary(retireBase);
            _pmdMember.setDuePay(duePay);

            // 只针对学生党员
            _pmdMember.setHasSalary(hasSalary);
            _pmdMember.setNeedSetSalary(needSetSalary);
            _pmdMember.setDuePayReason(duePayReason);

            //record.setRealPay(new BigDecimal(0));
            _pmdMember.setIsDelay(false);
            _pmdMember.setHasPay(false);

            _pmdMember.setIsOnlinePay(true);

            if(pmdMemberId==null) {
                pmdMemberMapper.insertSelective(_pmdMember);
                _pmdMemberId = _pmdMember.getId();
            }else{
                _pmdMember.setId(pmdMemberId);
                _pmdMember.setRealPay(new BigDecimal(0));
                pmdMemberMapper.updateByPrimaryKeySelective(_pmdMember);

                if(duePay==null){ //  防止 现金 改为 线上 缴费时，pmd_config_member的due_pay变为null
                    commonMapper.excuteSql(String.format("update pmd_member set due_pay=null " +
                    " where id=%s", pmdMemberId));
                }
            }
        }

        if(pmdMemberId==null) {
            // 同步至党员账本
            PmdMemberPay record = new PmdMemberPay();
            record.setMemberId(_pmdMemberId);
            record.setHasPay(false);

            pmdMemberPayMapper.insertSelective(record);
        }else{

            commonMapper.excuteSql("update pmd_member_pay set real_pay=null, " +
                    "has_pay=0, is_online_pay=1, pay_month_id=null where member_id=" + pmdMemberId);
        }

        // 当党员默认为现金缴费时，同步添加当月的缴费记录时，需要更新为已缴费
        if(!isOnlinePay){

            if(/*pmdMemberId != null || */duePay == null){

                // 重置缴费信息时，不可能为现金缴费
                throw new OpException("参数有误（重置缴费信息时，不可能为现金缴费），{0}, {1}", uv.getRealname(), uv.getCode());
            }

            commonMapper.excuteSql(String.format("update pmd_member set real_pay=%s, has_pay=1, " +
                    "is_online_pay=0 where id=%s", duePay, _pmdMemberId));

            int partyId = _pmdMember.getPartyId();
            Integer branchId = _pmdMember.getBranchId();

            commonMapper.excuteSql(String.format("update pmd_member_pay set real_pay=%s, " +
                    "has_pay=1, is_online_pay=0, pay_month_id=%s, charge_party_id=%s, charge_branch_id=%s"
                    +" where member_id=%s" , duePay, monthId, partyId, branchId,  _pmdMemberId));
        }

        // 启动收缴党费时记录进度
        if(PmdHelper.processMemberCount>=0) PmdHelper.processMemberCount++;

        return _pmdMember;
    }

    // 添加或重置党员缴费记录
    @Transactional
    @CacheEvict(value = "PmdConfigMember", key = "#userId")
    public void addOrResetPmdMember(int userId, Integer pmdMemberId){

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        Member member = memberService.get(userId);
        if(currentPmdMonth==null || member==null){

            throw new OpException("{1}缴费记录失败，未到缴费月份或不是党员：{0}",
                    sysUserService.findById(member.getUserId()).getRealname(),
                    pmdMemberId==null?"添加":"更新");
        }

        PmdMember pmdMember = addOrResetMember(pmdMemberId, currentPmdMonth, member, true);

        Date salaryMonth = pmdConfigResetService.getSalaryMonth();
        if(salaryMonth!=null) {

            String _salaryMonth = DateUtils.formatDate(salaryMonth, "yyyyMM");
            SysUserView uv = sysUserService.findById(userId);

            ExtJzgSalary ejs = iPmdMapper.getExtJzgSalary(_salaryMonth, uv.getCode());
            // 更新在职教职工工资
            pmdConfigResetService.updateDuePayByJzgSalary(ejs);

            ExtRetireSalary ers = iPmdMapper.getExtRetireSalary(_salaryMonth, uv.getCode());
            // 更新离退休人员党费计算基数
            pmdConfigResetService.updateDuePayByRetireSalary(ers);
        }

        sysApprovalLogService.add(pmdMember.getId(), userId, SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                (pmdMemberId==null?"添加":"更新")+ "缴费记录", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }
}
