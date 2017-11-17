package service.pmd;

import domain.member.MemberTeacher;
import domain.member.MemberTeacherExample;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import persistence.member.MemberTeacherMapper;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lm on 2017/11/15.
 */
@Service
public class PmdExtService extends BaseMapper{

    @Autowired
    private MemberTeacherMapper memberTeacherMapper;

    private static Map<String, Integer> rcchNormMap;
    private static Map<String, Integer> mainPostNormMap;
    private static Map<String, Integer> eduNormMap;

    // 高级人才收费标准
    public Map<String, Integer> getRcchNormMap(){

        if(rcchNormMap!=null) return rcchNormMap;

        rcchNormMap = new LinkedHashMap<>();
        try {
            SAXReader reader = new SAXReader();
            InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xml/pmd/rcch.xml"));
            Document document = reader.read(is);
            List<Node> nodeList = document.selectNodes("//rcch-list/rcch");
            for (Node node : nodeList) {
                rcchNormMap.put(node.valueOf("@name").trim(), Integer.valueOf(node.getText()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return rcchNormMap;
    }

    // 主岗等级收费标准
    public Map<String, Integer> getMainPostNormMap(){

        if(mainPostNormMap!=null) return mainPostNormMap;

        mainPostNormMap = new LinkedHashMap<>();
        try {
            SAXReader reader = new SAXReader();
            InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xml/pmd/main_post.xml"));
            Document document = reader.read(is);
            List<Node> nodeList = document.selectNodes("//main-post-list/main-post");
            for (Node node : nodeList) {
                mainPostNormMap.put(node.valueOf("@name").trim(), Integer.valueOf(node.getText()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return mainPostNormMap;
    }

    // （非事业编-校聘-最高学历）党费标准
    public Map<String, Integer> getEduNormMap(){

        if(eduNormMap!=null) return eduNormMap;

        eduNormMap = new LinkedHashMap<>();
        try {
            SAXReader reader = new SAXReader();
            InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xml/pmd/xp_xl.xml"));
            Document document = reader.read(is);
            List<Node> nodeList = document.selectNodes("//xl-list/xl");
            for (Node node : nodeList) {
                eduNormMap.put(node.valueOf("@name").trim(), Integer.valueOf(node.getText()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return eduNormMap;
    }

    // 具有指定高级人才称号的党员
    // <userId, MemberTeacher>
    public Map<Integer, MemberTeacher> getRCCH(){

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

    // 根据主岗等级得到缴纳金额，主岗等级不匹配否则返回-1
    public int getMainPostDuePay(MemberTeacher memberTeacher){

        int duePay = -1;

        String staffStatus = StringUtils.trim(memberTeacher.getStaffStatus());
        String authorizedType = StringUtils.trim(memberTeacher.getAuthorizedType());
        String mainPostLevel = StringUtils.trim(memberTeacher.getMainPostLevel());

        if(StringUtils.equals(staffStatus, "在职")
                && StringUtils.equals(authorizedType, "事业编")
                && StringUtils.isNotBlank(mainPostLevel)) {

            Map<String, Integer> mainPostNormMap = getMainPostNormMap();
            Integer _duePay = mainPostNormMap.get(mainPostLevel.trim());
            if (_duePay != null && _duePay>0) {
                duePay = _duePay;
            }
        }

        return duePay;
    }

    // 在职事业编党员
    // <userId, MemberTeacher>
    public Map<Integer, MemberTeacher> getSYB(){

        MemberTeacherExample example = new MemberTeacherExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL)
                .andStaffStatusEqualTo("在职").andAuthorizedTypeEqualTo("事业编");
        List<MemberTeacher> memberTeachers = memberTeacherMapper.selectByExample(example);

        Map<Integer, MemberTeacher>  resultMap = new HashMap<>();
        for (MemberTeacher memberTeacher : memberTeachers) {
            resultMap.put(memberTeacher.getUserId(), memberTeacher);
        }

        return resultMap;
    }

    public boolean isSYB(MemberTeacher memberTeacher){

        String staffStatus = StringUtils.trim(memberTeacher.getStaffStatus());
        String authorizedType = StringUtils.trim(memberTeacher.getAuthorizedType());

        return StringUtils.equals(staffStatus, "在职")
                && StringUtils.equals(authorizedType, "事业编");
    }

    // 在职非事业编党员（校聘、学生助理）
    // <userId, MemberTeacher>
    public Map<Integer, MemberTeacher> getFSYB(){

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
    }

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
}
