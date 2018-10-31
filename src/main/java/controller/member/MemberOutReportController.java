package controller.member;

import bean.UserBean;
import controller.BaseController;
import domain.member.MemberOut;
import domain.sys.SysUserView;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.ConfigUtil;
import sys.utils.DateUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
@RequestMapping("/report")
public class MemberOutReportController extends BaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    // 京外套打
    @RequestMapping(value = "/member_out_bj", method = RequestMethod.GET)
    public String member_out_bj(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                @RequestParam(value = "ids[]") Integer[] ids,
                                @RequestParam(required = false, defaultValue = "0") Boolean print,
                                Integer type,
                                @RequestParam(defaultValue = "pdf") String format,
                                Model model) throws IOException {

        // 分党委、组织部管理员或管理员才可以操作
        if (!ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ODADMIN,
                RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_PARTYADMIN)) {
            throw new UnauthorizedException();
        }

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : ids) {
            Map<String, Object> map = getMemberOutInfoMap(id);
            map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "member_out_bj.jpg");
            if (type != null && type == 1) {
                //套打，需要透明背景图片
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
            iMemberMapper.increasePrintCount("ow_member_out", Arrays.asList(ids), new Date(), ShiroHelper.getCurrentUserId());

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
                               @RequestParam(defaultValue = "pdf") String format,
                               Model model) throws IOException {

        // 分党委、组织部管理员或管理员才可以操作
        if (!ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ODADMIN,
                RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_PARTYADMIN)) {
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
            iMemberMapper.increasePrintCount("ow_member_out", Arrays.asList(ids), new Date(), ShiroHelper.getCurrentUserId());

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
        map.put("check1", (userBean.getPoliticalStatus() != null && userBean.getPoliticalStatus() == MemberConstants.MEMBER_POLITICAL_STATUS_GROW) ? "√" : ""); // 预备党员
        map.put("check2", (userBean.getPoliticalStatus() != null && userBean.getPoliticalStatus() == MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE) ? "√" : ""); // 正式党员
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
}
