package controller.jasper;

import com.lowagie.text.DocumentException;
import controller.BaseController;
import domain.*;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.ConfigUtil;
import sys.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String big(Integer id, Model model) throws IOException, DocumentException {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        MetaType passportType = CmTag.getMetaType("mc_passport_type", passport.getClassId());
        Cadre cadre = cadreService.findAll().get(passport.getCadreId());
        SysUser user = sysUserService.findById(cadre.getUserId());
        String unit = unitService.findAll().get(cadre.getUnitId()).getName();
        String title = cadre.getTitle();

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getRealname());
        map.put("code", user.getCode());
        map.put("unit", title); // 所属单位及职务
        String check1 = "";
        String check2 = "";
        if(passport.getCancelType() == SystemConstants.PASSPORT_CANCEL_TYPE_EXPIRE)
            check1 = "√";
        else
            check2 = "√";
        map.put("check1", check1);
        map.put("check2", check2);
        map.put("passport1", passportType.getName());
        map.put("passport1Code", passport.getCode());
        map.put("passport1Expiry", DateUtils.formatDate(passport.getExpiryDate(), DateUtils.YYYY_MM_DD_CHINA));
        map.put("bg", ConfigUtil.defaultConfigPath() + File.separator + "jasper" + File.separator +"cancel.jpg" );
        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/cancel.jasper");
        model.addAttribute("format", "pdf"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    @RequestMapping(value = "/passportSign", method = RequestMethod.GET)
    public String passportSign(Integer classId, Integer userId, Integer id, Model model) throws IOException, DocumentException {

        PassportDraw passportDraw = null;
        if(id!=null){ // 以id为准
            passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            Integer passportId = passportDraw.getPassportId();
            Passport passport = passportMapper.selectByPrimaryKey(passportId);
            classId = passport.getClassId();
            Cadre cadre = cadreService.findAll().get(passportDraw.getCadreId());
            userId = cadre.getUserId();
        }

        SysUser user = sysUserService.findById(userId);
        Cadre cadre = cadreService.findByUserId(userId);
        String unit = unitService.findAll().get(cadre.getUnitId()).getName();
        String post = cadre.getPost();

        String to = "self";
        if(CmTag.typeEqualsCode(classId, "mt_passport_hk"))
            to = "hk";
        if(CmTag.typeEqualsCode(classId, "mt_passport_tw"))
            to = "tw";

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getRealname());
        map.put("locate", "北京市");
        map.put("idcard", user.getIdcard());
        map.put("unit", unit);
        map.put("title", post);  // 职务
        map.put("bg", ConfigUtil.defaultConfigPath() + File.separator + "jasper" + File.separator + to + ".jpg" );

        String sign = ConfigUtil.defaultConfigPath() + File.separator + "jasper" + File.separator + "px.png";
        String mobile = "";
        if(passportDraw != null) {
            if (passportDraw.getStatus() != null && passportDraw.getStatus() == SystemConstants.PASSPORT_DRAW_STATUS_PASS) {
                SysUser _user = sysUserService.findById(passportDraw.getUserId()); // 审核人
                sign = springProps.uploadPath + _user.getSign();
                mobile = _user.getMobile();
            }
        }
        map.put("sign", sign);
        map.put("mobile", mobile);

        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/abroad.jasper");

        model.addAttribute("format", "pdf"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    @RequestMapping(value = "/passportApply", method = RequestMethod.GET)
    public String passportApply(Integer classId, Integer userId, Integer id, Model model) throws IOException, DocumentException {

        PassportApply passportApply = null;
        if(id!=null){ // 以id为准
            passportApply = passportApplyMapper.selectByPrimaryKey(id);
            classId = passportApply.getClassId();
            Cadre cadre = cadreService.findAll().get(passportApply.getCadreId());
            userId = cadre.getUserId();
        }

        SysUser user = sysUserService.findById(userId);
        Cadre cadre = cadreService.findByUserId(userId);
        String unit = unitService.findAll().get(cadre.getUnitId()).getName();
        String title = cadre.getTitle();

        String to = "self";
        if(CmTag.typeEqualsCode(classId, "mt_passport_hk"))
            to = "hk";
        if(CmTag.typeEqualsCode(classId, "mt_passport_tw"))
            to = "tw";

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getRealname());
        map.put("locate", "北京市");
        map.put("idcard", user.getIdcard());
        map.put("unit", unit);
        map.put("title", title);
        map.put("bg", ConfigUtil.defaultConfigPath() + File.separator + "jasper" + File.separator + to + ".jpg" );

        String sign = ConfigUtil.defaultConfigPath() + File.separator + "jasper" + File.separator + "px.png";
        String mobile = "";
        if(passportApply != null) {
            if (passportApply.getStatus() != null && passportApply.getStatus() == SystemConstants.PASSPORT_APPLY_STATUS_PASS) {
                SysUser _user = sysUserService.findById(passportApply.getUserId()); // 审核人
                sign = springProps.uploadPath + _user.getSign();
                mobile = _user.getMobile();
            }
        }
        map.put("sign", sign);
        map.put("mobile", mobile);

        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/abroad.jasper");

        model.addAttribute("format", "pdf"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    /**
     * 返回iReport报表视图
     * @param model
     * @return
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report(Model model) throws IOException, DocumentException {
        // 报表数据源
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(JavaBeanPerson.getList());

        ///BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/report1.jasper");
        model.addAttribute("format", "html"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }
}
