package controller.abroad;

import domain.abroad.ApplySelf;
import domain.abroad.Passport;
import domain.abroad.PassportApply;
import domain.abroad.PassportDraw;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import domain.unit.Unit;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.jasper.JasperReportsImageView;
import sys.shiro.CurrentUser;
import sys.spring.UserRes;
import sys.spring.UserResUtils;
import sys.tags.CmTag;
import sys.utils.ConfigUtil;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.NumberUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
@RequestMapping("/report")
public class AbroadReportController extends AbroadBaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    // 确认取消集中管理
    @RequestMapping(value = "/cancel")
    public String cancel(String id,
                         @RequestParam(defaultValue = "image") String format,
                         Model model) throws IOException {

        UserRes verify = UserResUtils.verify(id);
        Integer idInt = Integer.parseInt(verify.getRes());

        Passport passport = passportMapper.selectByPrimaryKey(idInt);
        MetaType passportType = CmTag.getMetaType(passport.getClassId());
        CadreView cadre = iCadreMapper.getCadre(passport.getCadreId());
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
        if (passport.getCancelType() != null && passport.getCancelType() == AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_EXPIRE)
            check1 = "√";
        else if (passport.getCancelType() != null && passport.getCancelType() == AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_DISMISS)
            check2 = "√";
        else if (passport.getCancelType() != null && passport.getCancelType() == AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_ABOLISH)
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
        if (FileUtils.exists(springProps.uploadPath + _user.getSign()))
            sign = springProps.uploadPath + _user.getSign();
        Date cancelTime = new Date();
        map.put("year", DateUtils.getYear(cancelTime));
        map.put("month", DateUtils.getMonth(cancelTime));
        map.put("day", DateUtils.getDay(cancelTime));

        map.put("sign", sign);
        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/cancel.jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("image.zoom", 0.25f);
        model.addAttribute("image.bg", JasperReportsImageView.BG_IMAGE);
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    // 领取证件？
    @RequestMapping(value = "/passportSign")
    public String passportSign(String id,
                               @RequestParam(defaultValue = "image") String format,
                               Model model) throws IOException {

        UserRes verifyId= UserResUtils.verify(id);
        String[] idInt=verifyId.getRes().split("_");
        int recordId=0;
        int classId=0;
        int userId=0;
        if(idInt.length>=1){
            recordId= Integer.valueOf(idInt[0]);
        }
        if (idInt.length>=2){
            classId= Integer.valueOf(idInt[1]);
        }
        if (idInt.length==3){
            userId= Integer.valueOf(idInt[2]);
        }

        PassportDraw passportDraw = null;
        if (recordId>0) { // 以id为准

            passportDraw = passportDrawMapper.selectByPrimaryKey(recordId);
            Integer passportId = passportDraw.getPassportId();
            Passport passport = passportMapper.selectByPrimaryKey(passportId);
            classId = passport.getClassId();
            CadreView cadre = iCadreMapper.getCadre(passportDraw.getCadreId());
            userId = cadre.getUserId();
        }

        SysUserView user = sysUserService.findById(userId);
        CadreView cadre = cadreService.dbFindByUserId(userId);
        String unit = CmTag.getUnit(cadre.getUnitId()).getName();
        String post = cadre.getPost();

        String to = "self";
        if (CmTag.typeEqualsCode(classId, "mt_passport_hk"))
            to = "hk";
        if (CmTag.typeEqualsCode(classId, "mt_passport_tw"))
            to = "tw";

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getRealname());
        map.put("locate", CmTag.getSysConfig().getCity());
        map.put("idcard", user.getIdcard());
        String schoolName = CmTag.getSysConfig().getSchoolName();
        map.put("unit", unit.startsWith(schoolName) ? unit : (schoolName + unit));
        map.put("title", post);  // 职务
        map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR
                + "abroadApply" + FILE_SEPARATOR + to + ".jpg");

        String sign = ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "px.png";
        String mobile = "";
        if (passportDraw != null) {
            if (passportDraw.getStatus() != null && passportDraw.getStatus() == AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_PASS) {
                SysUserView _user = sysUserService.findById(passportDraw.getUserId()); // 审核人

                if (CmTag.getBoolProperty("abroadContactUseSign")) {
                    if (FileUtils.exists(springProps.uploadPath + _user.getSign()))
                        sign = springProps.uploadPath + _user.getSign();
                } else {
                    map.put("contactName", _user.getRealname());
                }

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
        model.addAttribute("url", "/WEB-INF/jasper/abroadApply/abroad.jasper");

        model.addAttribute("format", format); // 报表格式
        model.addAttribute("image.zoom", 0.25f);
        model.addAttribute("image.bg", JasperReportsImageView.BG_IMAGE);
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    @RequestMapping(value = "/passportApply")
    public String passportApply(String id,
                                @RequestParam(defaultValue = "image") String format,
                                Model model) throws IOException, DocumentException {

        UserRes verifyId= UserResUtils.verify(id);
        String[] idInt=verifyId.getRes().split("_");
        int recordId=0;
        int classId=0;
        int userId=0;
        if(idInt.length>=1){
            recordId= Integer.valueOf(idInt[0]);
        }
        if (idInt.length>=2){
           classId= Integer.valueOf(idInt[1]);
        }
        if (idInt.length==3){
           userId= Integer.valueOf(idInt[2]);
        }

        PassportApply passportApply = null;
        if (recordId>0) { // 以id为准
            passportApply = passportApplyMapper.selectByPrimaryKey(recordId);
            classId = passportApply.getClassId();
            CadreView cadre = iCadreMapper.getCadre(passportApply.getCadreId());
            userId = cadre.getUserId();
        }

        SysUserView user = sysUserService.findById(userId);
        CadreView cadre = cadreService.dbFindByUserId(userId);
        Unit unit = unitMapper.selectByPrimaryKey(cadre.getUnitId());
        String unitName = (unit==null)?"":unit.getName();
        String post = cadre.getPost();

        String to = "self";
        if (CmTag.typeEqualsCode(classId, "mt_passport_hk"))
            to = "hk";
        if (CmTag.typeEqualsCode(classId, "mt_passport_tw"))
            to = "tw";

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getRealname());
        map.put("locate", CmTag.getSysConfig().getCity());
        map.put("idcard", user.getIdcard());
        String schoolName = CmTag.getSysConfig().getSchoolName();
        map.put("unit", unitName.startsWith(schoolName) ? unitName : (schoolName + unitName));
        map.put("title", post);  // 职务
        map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR
                + "abroadApply" + FILE_SEPARATOR + to + ".jpg");

        String sign = ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "px.png";
        String mobile = "";
        if (passportApply != null) {
            if (passportApply.getStatus() != null && passportApply.getStatus() == AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS) {
                SysUserView _user = sysUserService.findById(passportApply.getUserId()); // 审核人

                if (CmTag.getBoolProperty("abroadContactUseSign")) {
                    if (FileUtils.exists(springProps.uploadPath + _user.getSign()))
                        sign = springProps.uploadPath + _user.getSign();
                } else {
                    map.put("contactName", _user.getRealname());
                }

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
        model.addAttribute("url", "/WEB-INF/jasper/abroadApply/abroad.jasper");

        model.addAttribute("format", format); // 报表格式
        model.addAttribute("image.zoom", 0.25f);
        model.addAttribute("image.bg", JasperReportsImageView.BG_IMAGE);
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    @RequestMapping(value = "/abroad_draw_proof")
    public String abroad_draw_proof(@CurrentUser SysUserView loginUser,
                                    String ids,
                                    Integer type,
                                    @RequestParam(defaultValue = "pdf") String format,
                                    Model model) throws IOException, DocumentException {

        ShiroHelper.checkPermission("passportDraw:list");

        UserRes verify = UserResUtils.verify(ids);
        String res = verify.getRes();
        Set<Integer> idSet = NumberUtils.toIntSet(res, ",");

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : idSet) {
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
        model.addAttribute("image.zoom", 0.25f);
        model.addAttribute("image.bg", JasperReportsImageView.BG_IMAGE);
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    // 获取因私证件领取相关信息
    public Map getDrawProofMap(int id) {
        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);

        Integer cadreId = passportDraw.getCadreId();
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        String realname = cadre.getRealname();
        String code = cadre.getCode();
        String unit = cadre.getUnit().getName();

        Map<String, Object> map = new HashMap<String, Object>();

        String passportName = passportDraw.getPassportClass().getName();
        ApplySelf applySelf = passportDraw.getApplySelf();
        if (applySelf != null) {
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

            map.put("travelTime", travelTime);
            map.put("toCountry", toCountry);

            List<String> reasonList = new ArrayList<>();
            String[] reasons = reason.split("\\+\\+\\+");
            for (String r : reasons) {
                if (r.startsWith("其他:")) {
                    reasonList.add(r.substring(3));
                } else {
                    reasonList.add(r);
                }
            }
            map.put("reason", StringUtils.join(reasonList, "，"));
        }

        /*if(passportDraw.getDrawTime()==null){
            new OpException("证件还未领取");
        }*/

        String drawTime = DateUtils.formatDate(passportDraw.getDrawTime(), DateUtils.YYYY_MM_DD_CHINA);
        String printTime = DateUtils.getCurrentDateTime(DateUtils.YYYY_MM_DD_CHINA);


        map.put("title", CmTag.getSysConfig().getSchoolName() + "处级干部因私出国（境）备案证明");
        map.put("realname", realname);
        map.put("code", code);
        map.put("unit", unit);
        map.put("drawTime", drawTime);
        map.put("passportName", passportName);

        map.put("printTime", printTime);

        return map;
    }
}
