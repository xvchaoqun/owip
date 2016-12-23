package controller.jasper;

import bean.UserBean;
import com.lowagie.text.DocumentException;
import controller.BaseController;
import domain.abroad.ApplySelf;
import domain.abroad.Passport;
import domain.abroad.PassportApply;
import domain.abroad.PassportDraw;
import domain.cadre.Cadre;
import domain.member.MemberOut;
import domain.sys.MetaType;
import domain.sys.SysUserView;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.helper.ShiroHelper;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    // 京外套打
    @RequestMapping(value = "/member_out_bj", method = RequestMethod.GET)
    public String member_out_bj(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                @RequestParam(value = "ids[]") Integer[] ids,
                                @RequestParam(required = false, defaultValue = "0") Boolean print,
                                Integer type,
                                @RequestParam(defaultValue = "pdf")String format,
                                Model model) throws IOException, DocumentException {

        // 分党委、组织部管理员或管理员才可以操作
        if (!ShiroHelper.hasAnyRoles(SystemConstants.ROLE_ODADMIN,
                SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_PARTYADMIN)) {
            throw new UnauthorizedException();
        }

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : ids) {
            Map<String, Object> map = getMemberOutInfoMap(id);
            map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "member_out_bj.jpg");
            if (type != null && type == 1) {
                map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "px.png");
            }
            data.add(map);
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/member_out_bj.jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        if (print) {
            memberOutService.incrPrintCount(ids);
            logger.info("京外套打 {}, {}, {}, {}, {}, {}",
                    new Object[]{loginUser.getUsername(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    // 京内打印
    @RequestMapping(value = "/member_in_bj", method = RequestMethod.GET)
    public String member_in_bj(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                               @RequestParam(value = "ids[]") Integer[] ids,
                               @RequestParam(required = false, defaultValue = "0") Boolean print,
                               @RequestParam(defaultValue = "pdf")String format,
                               Model model) throws IOException, DocumentException {

        // 分党委、组织部管理员或管理员才可以操作
        if (!ShiroHelper.hasAnyRoles(SystemConstants.ROLE_ODADMIN,
                SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_PARTYADMIN)) {
            throw new UnauthorizedException();
        }

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : ids) {
            Map<String, Object> map = getMemberOutInfoMap(id);
            map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "member_in_bj.jpg");
            data.add(map);
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/member_in_bj.jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        if (print) {
            memberOutService.incrPrintCount(ids);
            logger.info("京内打印 {}, {}, {}, {}, {}, {}",
                    new Object[]{loginUser.getUsername(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    // 获取组织关系转出相关信息
    public Map<String, Object> getMemberOutInfoMap(int id) {

        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        UserBean userBean = userBeanService.get(memberOut.getUserId());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", userBean.getRealname());
        map.put("from", memberOut.getFromUnit());
        map.put("to", memberOut.getToUnit());
        map.put("toTitle", memberOut.getToTitle());
        map.put("check1", (userBean.getPoliticalStatus() != null && userBean.getPoliticalStatus() == SystemConstants.MEMBER_POLITICAL_STATUS_GROW) ? "√" : ""); // 预备党员
        map.put("check2", (userBean.getPoliticalStatus() != null && userBean.getPoliticalStatus() == SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE) ? "√" : ""); // 正式党员
        map.put("male", (userBean.getGender() != null && userBean.getGender() == SystemConstants.GENDER_MALE) ? "√" : "");
        map.put("female", (userBean.getGender() != null && userBean.getGender() == SystemConstants.GENDER_FEMALE) ? "√" : "");
        map.put("age", (userBean.getBirth() != null) ? DateUtils.intervalYearsUntilNow(userBean.getBirth()) : "");
        map.put("nation", StringUtils.trimToEmpty(userBean.getNation()));
        map.put("payYear", DateUtils.getYear(memberOut.getPayTime()));
        map.put("payMonth", DateUtils.getMonth(memberOut.getPayTime()));
        map.put("validDays", memberOut.getValidDays());
        map.put("mobile", memberOut.getPhone()); // 联系方式
        map.put("phone", memberOut.getFromPhone()); // 原组织关系联系方式
        map.put("fax", memberOut.getFromFax()); //
        map.put("postCode", memberOut.getFromPostCode());
        map.put("idcard", userBean.getIdcard());
        map.put("code", userBean.getCode());
        Date handleTime = memberOut.getHandleTime();

        map.put("handleYear", handleTime != null ? DateUtils.getYear(handleTime) : "");
        map.put("handleMonth", handleTime != null ? DateUtils.getMonth(handleTime) : "");
        map.put("handleDay", handleTime != null ? DateUtils.getDay(handleTime) : "");

        return map;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String cancel(Integer id,
                         @RequestParam(defaultValue = "image")String format,
                         Model model) throws IOException, DocumentException {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        MetaType passportType = CmTag.getMetaType(passport.getClassId());
        Cadre cadre = cadreService.findAll().get(passport.getCadreId());
        SysUserView user = sysUserService.findById(cadre.getUserId());
        //String unit = unitService.findAll().get(cadre.getUnitId()).getName();
        String title = cadre.getTitle();

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getRealname());
        map.put("code", user.getCode());
        map.put("unit", title); // 所属单位及职务
        String check1 = "";
        String check2 = "";
        String check3 = "";
        if (passport.getCancelType() != null && passport.getCancelType() == SystemConstants.PASSPORT_CANCEL_TYPE_EXPIRE)
            check1 = "√";
        else if (passport.getCancelType() != null && passport.getCancelType() == SystemConstants.PASSPORT_CANCEL_TYPE_DISMISS)
            check2 = "√";
        else if (passport.getCancelType() != null && passport.getCancelType() == SystemConstants.PASSPORT_CANCEL_TYPE_ABOLISH)
            check3 = "√";
        map.put("check1", check1);
        map.put("check2", check2);
        map.put("check3", check3);
        map.put("passport1", passportType.getName());
        map.put("passport1Code", passport.getCode());
        map.put("passport1Expiry", DateUtils.formatDate(passport.getExpiryDate(), DateUtils.YYYY_MM_DD_CHINA));
        map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "cancel.jpg");

        // 在预览时显示签名和日期
        String sign = ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "px.png";
        /*if (passport.getCancelConfirm()) {
            SysUser _user = sysUserService.findById(passport.getCancelUserId()); // 审核人
            sign = springProps.uploadPath + _user.getSign();
        }
        Date cancelTime = passport.getCancelTime();
        if (cancelTime != null) {
            map.put("year", DateUtils.getYear(cancelTime));
            map.put("month", DateUtils.getMonth(cancelTime));
            map.put("day", DateUtils.getDay(cancelTime));
        }*/
        SysUserView _user = ShiroHelper.getCurrentUser();
        if(FileUtils.exists(springProps.uploadPath + _user.getSign()))
            sign = springProps.uploadPath + _user.getSign();
        Date cancelTime =new Date();
        map.put("year", DateUtils.getYear(cancelTime));
        map.put("month", DateUtils.getMonth(cancelTime));
        map.put("day", DateUtils.getDay(cancelTime));

        map.put("sign", sign);
        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/cancel.jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    // 领取证件？
    @RequestMapping(value = "/passportSign", method = RequestMethod.GET)
    public String passportSign(Integer classId, Integer userId, Integer id,
                               @RequestParam(defaultValue = "image")String format,
                               Model model) throws IOException, DocumentException {

        PassportDraw passportDraw = null;
        if (id != null) { // 以id为准
            passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            Integer passportId = passportDraw.getPassportId();
            Passport passport = passportMapper.selectByPrimaryKey(passportId);
            classId = passport.getClassId();
            Cadre cadre = cadreService.findAll().get(passportDraw.getCadreId());
            userId = cadre.getUserId();
        }

        SysUserView user = sysUserService.findById(userId);
        Cadre cadre = cadreService.findByUserId(userId);
        String unit = unitService.findAll().get(cadre.getUnitId()).getName();
        String post = cadre.getPost();

        String to = "self";
        if (CmTag.typeEqualsCode(classId, "mt_passport_hk"))
            to = "hk";
        if (CmTag.typeEqualsCode(classId, "mt_passport_tw"))
            to = "tw";

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getRealname());
        map.put("locate", "北京市");
        map.put("idcard", user.getIdcard());
        map.put("unit", PropertiesUtils.getString("report.unit.prefix") + unit);
        map.put("title", post);  // 职务
        map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + to + ".jpg");

        String sign = ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "px.png";
        String mobile = "";
        if (passportDraw != null) {
            if (passportDraw.getStatus() != null && passportDraw.getStatus() == SystemConstants.PASSPORT_DRAW_STATUS_PASS) {
                SysUserView _user = sysUserService.findById(passportDraw.getUserId()); // 审核人
                if(FileUtils.exists(springProps.uploadPath + _user.getSign()))
                    sign = springProps.uploadPath + _user.getSign();
                mobile = _user.getPhone(); // 办公电话
            }

            Date approveTime = passportDraw.getApproveTime();
            if (approveTime != null) {
                map.put("year", DateUtils.getYear(approveTime));
                map.put("month", DateUtils.getMonth(approveTime));
                map.put("day", DateUtils.getDay(approveTime));
            }
        }
        map.put("sign", sign);
        map.put("mobile", mobile);

        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/abroad.jasper");

        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    @RequestMapping(value = "/passportApply", method = RequestMethod.GET)
    public String passportApply(Integer classId, Integer userId,
                                Integer id,
                                @RequestParam(defaultValue = "image")String format,
                                Model model) throws IOException, DocumentException {

        PassportApply passportApply = null;
        if (id != null) { // 以id为准
            passportApply = passportApplyMapper.selectByPrimaryKey(id);
            classId = passportApply.getClassId();
            Cadre cadre = cadreService.findAll().get(passportApply.getCadreId());
            userId = cadre.getUserId();
        }

        SysUserView user = sysUserService.findById(userId);
        Cadre cadre = cadreService.findByUserId(userId);
        String unit = unitService.findAll().get(cadre.getUnitId()).getName();
        String post = cadre.getPost();

        String to = "self";
        if (CmTag.typeEqualsCode(classId, "mt_passport_hk"))
            to = "hk";
        if (CmTag.typeEqualsCode(classId, "mt_passport_tw"))
            to = "tw";

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getRealname());
        map.put("locate", "北京市");
        map.put("idcard", user.getIdcard());
        map.put("unit", PropertiesUtils.getString("report.unit.prefix") + unit);
        map.put("title", post);  // 职务
        map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + to + ".jpg");

        String sign = ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "px.png";
        String mobile = "";
        if (passportApply != null) {
            if (passportApply.getStatus() != null && passportApply.getStatus() == SystemConstants.PASSPORT_APPLY_STATUS_PASS) {
                SysUserView _user = sysUserService.findById(passportApply.getUserId()); // 审核人
                if(FileUtils.exists(springProps.uploadPath + _user.getSign()))
                    sign = springProps.uploadPath + _user.getSign();
                mobile = _user.getPhone(); // 办公电话
            }

            Date approveTime = passportApply.getApproveTime();
            if (approveTime != null) {
                map.put("year", DateUtils.getYear(approveTime));
                map.put("month", DateUtils.getMonth(approveTime));
                map.put("day", DateUtils.getDay(approveTime));
            }
        }
        map.put("sign", sign);
        map.put("mobile", mobile);

        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/abroad.jasper");

        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    @RequestMapping(value = "/abroad_draw_proof", method = RequestMethod.GET)
    public String abroad_draw_proof(@CurrentUser SysUserView loginUser,
                                    @RequestParam(value = "ids[]") Integer[] ids,
                                    Integer type,
                                    @RequestParam(defaultValue = "pdf")String format,
                                    Model model) throws IOException, DocumentException {

        // 分党委、组织部管理员或管理员才可以操作
        if (!ShiroHelper.hasAnyRoles(SystemConstants.ROLE_ODADMIN,
                SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_PARTYADMIN)) {
            throw new UnauthorizedException();
        }

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : ids) {
            Map<String, Object> map = getDrawProofMap(id);
            map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "abroad_draw_proof.jpg");
            if (type != null && type == 1) {
                map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "px.png");
            }
            data.add(map);
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/abroad_draw_proof.jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    // 获取因私证件领取相关信息
    public Map getDrawProofMap(int id) {
        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);

        Integer cadreId = passportDraw.getCadreId();
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
        SysUserView sysUser = cadre.getUser();
        String realname = sysUser.getRealname();
        String code = sysUser.getCode();
        String unit = cadre.getUnit().getName();

        String passportName = passportDraw.getPassportClass().getName();
        ApplySelf applySelf = passportDraw.getApplySelf();
        String toCountry = applySelf.getToCountry();
        Date startDate = applySelf.getStartDate();
        Date endDate = applySelf.getEndDate();
        String travelTime = DateUtils.formatDate(startDate, DateUtils.YYYY_MM_DD_CHINA);
        if (DateUtils.getYear(startDate).intValue() == DateUtils.getYear(endDate)) {
            travelTime += "-" + DateUtils.formatDate(endDate, "MM月dd日");
        } else {
            travelTime += "-" + DateUtils.formatDate(endDate, DateUtils.YYYY_MM_DD_CHINA);
        }
        String reason = applySelf.getReason();

        /*if(passportDraw.getDrawTime()==null){
            new RuntimeException("证件还未领取");
        }*/

        String drawTime = DateUtils.formatDate(passportDraw.getDrawTime(), DateUtils.YYYY_MM_DD_CHINA);
        String printTime = DateUtils.getCurrentDateTime(DateUtils.YYYY_MM_DD_CHINA);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("realname", realname);
        map.put("code", code);
        map.put("unit", unit);
        map.put("drawTime", drawTime);
        map.put("passportName", passportName);
        map.put("travelTime", travelTime);
        map.put("toCountry", toCountry);
        map.put("reason", StringUtils.replace(reason, "+++", "，"));
        map.put("printTime", printTime);

        return map;
    }

    /**
     * 返回iReport报表视图
     * @param model
     * @return
     */
    /*@RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report(Model model) throws IOException, DocumentException {
        // 报表数据源
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(JavaBeanPerson.getList());

        ///BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/report1.jasper");
        model.addAttribute("format", "html"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }*/
}
