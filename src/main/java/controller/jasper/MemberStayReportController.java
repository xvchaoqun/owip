package controller.jasper;

import bean.UserBean;
import com.lowagie.text.DocumentException;
import controller.BaseController;
import domain.member.MemberOut;
import domain.member.MemberStay;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.StudentInfo;
import domain.sys.SysUserView;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.ConfigUtil;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
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

@Controller
@RequestMapping("/report")
public class MemberStayReportController extends BaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    // 出国暂留
    @RequestMapping(value = "/member_stay", method = RequestMethod.GET)
    public String member_stay(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                              byte type,
                              @RequestParam(value = "ids[]") Integer[] ids,
                              @RequestParam(required = false, defaultValue = "0") Boolean print,
                              @RequestParam(defaultValue = "pdf") String format,
                              Model model) throws IOException, DocumentException {

        // 分党委、组织部管理员或管理员才可以操作
        if (!ShiroHelper.hasAnyRoles(SystemConstants.ROLE_ODADMIN,
                SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_PARTYADMIN)) {
            throw new UnauthorizedException();
        }

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : ids) {
            Map<String, Object> map = getMemberStayMap(id);

            if (type == SystemConstants.MEMBER_STAY_TYPE_ABROAD) {
                map.put("bg1", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR +
                        "jasper" + FILE_SEPARATOR + "member_stay_abroad_page1.jpg");
                map.put("bg2", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR +
                        "jasper" + FILE_SEPARATOR + "member_stay_abroad_page2.jpg");
            } else {
                map.put("bg1", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR +
                        "jasper" + FILE_SEPARATOR + "member_stay_internal.jpg");
            }

            data.add(map);
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        if (type == SystemConstants.MEMBER_STAY_TYPE_ABROAD) {
            model.addAttribute("url", "/WEB-INF/jasper/member_stay_abroad.jasper");
        } else {
            model.addAttribute("url", "/WEB-INF/jasper/member_stay_internal.jasper");
        }
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        if (print) {
            iMemberMapper.increasePrintCount("ow_member_stay", Arrays.asList(ids), new Date(), ShiroHelper.getCurrentUserId());

            logger.info("出国暂留打印 {}, {}, {}, {}, {}, {}",
                    new Object[]{loginUser.getUsername(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }

        return "iReportView";
    }

    // 获取出国暂留相关信息
    public Map<String, Object> getMemberStayMap(int id) {

        MemberStay ga = memberStayMapper.selectByPrimaryKey(id);
        int userId = ga.getUserId();
        UserBean u = userBeanService.get(userId);
        StudentInfo student = studentService.get(userId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ga.getCode());

        Date createTime = ga.getCreateTime();
        map.put("year", DateUtils.getYear(createTime));
        map.put("month", DateUtils.getMonth(createTime));
        map.put("day", DateUtils.getDay(createTime));

        map.put("realname", u.getRealname());
        map.put("userCode", u.getCode());
        map.put("gender", u.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(u.getGender()));
        map.put("nation", u.getNation());

        String avatar = springProps.avatarFolder + u.getAvatar();
        if (!FileUtils.exists(avatar)) {
            avatar = ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "px.png";
        }
        map.put("avatar", avatar); // 头像 360*480

        map.put("birth", DateUtils.formatDate(u.getBirth(), "yyyy.MM"));
        map.put("growTime", DateUtils.formatDate(u.getGrowTime(), "yyyy.MM.dd"));
        map.put("nativePlace", u.getNativePlace());
        map.put("idcard", u.getIdcard());
        map.put("grade", student==null?"":student.getGrade());
        // 学历？
        map.put("education", student==null?"":student.getEduLevel());
        map.put("mobile", ga.getMobile());
        map.put("phone", ga.getPhone());
        map.put("qq", ga.getQq());
        map.put("email", ga.getEmail());
        map.put("weixin", ga.getWeixin());

        // 接收函？
        map.put("letter", "");

        String name1 = "";
        if (ga.getName1() != null && ga.getRelate1() != null) {
            name1 = String.format("%s(%s)", ga.getName1(), ga.getRelate1());
        } else if (ga.getName1() != null) {
            name1 = ga.getName1();
        }
        map.put("name1", name1);
        map.put("mobile1", ga.getMobile1());
        map.put("stayReason", ga.getStayReason());


        if (ga.getType() == SystemConstants.MEMBER_STAY_TYPE_ABROAD) {
            map.put("inAddress", ga.getInAddress());
            map.put("outAddress", ga.getOutAddress());

            map.put("unit1", ga.getUnit1());
            map.put("post1", ga.getPost1());
            map.put("phone1", ga.getPhone1());
            map.put("email1", ga.getEmail1());

            String name2 = "";
            if (ga.getName2() != null && ga.getRelate2() != null) {
                name2 = String.format("%s(%s)", ga.getName2(), ga.getRelate2());
            } else if (ga.getName2() != null) {
                name2 = ga.getName2();
            }
            map.put("name2", name2);

            map.put("unit2", ga.getUnit2());
            map.put("post2", ga.getPost2());
            map.put("phone2", ga.getPhone2());
            map.put("mobile2", ga.getMobile2());
            map.put("email2", ga.getEmail2());

            map.put("country", ga.getCountry());
            map.put("school", ga.getSchool());

            String startTime = "";
            if (ga.getStartTime() != null && ga.getEndTime() != null) {
                startTime = DateUtils.formatDate(ga.getStartTime(), "yyyy.MM")
                        + " 至 " + DateUtils.formatDate(ga.getEndTime(), "yyyy.MM");
            } else if (ga.getStartTime() != null) {
                startTime = DateUtils.formatDate(ga.getStartTime(), "yyyy.MM");
            }
            map.put("startTime", startTime);

            map.put("payTime", DateUtils.formatDate(ga.getPayTime(), "yyyy.MM"));

            Byte abroadType = ga.getAbroadType();
            map.put("typeCheck1", abroadType == SystemConstants.MEMBER_STAY_ABROAD_TYPE_MAP_PUB ? "√" : "");
            map.put("typeCheck2", abroadType == SystemConstants.MEMBER_STAY_ABROAD_TYPE_MAP_SELF ? "√" : "");
        }

        map.put("overDate", DateUtils.formatDate(ga.getOverDate(), "yyyy.MM"));

        String branchAdmin = "";
        if (ga.getOrgBranchAdminPhone() != null && ga.getOrgBranchAdmin() != null) {
            branchAdmin = String.format("%s(%s)", ga.getOrgBranchAdmin().getRealname(), ga.getOrgBranchAdminPhone());
        } else if (ga.getOrgBranchAdmin() != null) {
            branchAdmin = ga.getOrgBranchAdmin().getRealname();
        }
        map.put("branchAdmin", branchAdmin);

        map.put("saveStartTime", DateUtils.formatDate(ga.getSaveStartTime(), "yyyy.MM")
                + " 至 " + DateUtils.formatDate(ga.getSaveEndTime(), "yyyy.MM"));

        String party = "";
        Map<Integer, Party> partyMap = partyService.findAll();
        if (ga.getPartyId() != null) {
            Party _party = partyMap.get(ga.getPartyId());
            if (_party != null) party = _party.getName();
        }
        map.put("party", party); // 所属分党委

        String branch = "";
        Map<Integer, Branch> branchMap = branchService.findAll();
        if (ga.getBranchId() != null) {
            Branch _branch = branchMap.get(ga.getBranchId());
            if (_branch != null) branch = _branch.getName();
        }
        map.put("branch", branch); // 暂留所在党支部

        String toBranch = "";
        if (ga.getToBranchId() != null) {
            Branch _branch = branchMap.get(ga.getToBranchId());
            if (_branch != null) toBranch = _branch.getName();
        }
        map.put("toBranch", toBranch); // 暂留所在党支部

        map.put("check1", (u.getPoliticalStatus() != null && u.getPoliticalStatus()
                == SystemConstants.MEMBER_POLITICAL_STATUS_GROW) ? "√" : ""); // 预备党员
        map.put("check2", (u.getPoliticalStatus() != null && u.getPoliticalStatus()
                == SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE) ? "√" : ""); // 正式党员

        String transferTime = "";
        if (u.getMemberStatus() != null && u.getMemberStatus() == SystemConstants.MEMBER_STATUS_TRANSFER) {
            MemberOut memberOut = memberOutService.getLatest(userId);
            if (memberOut != null && memberOut.getStatus()==SystemConstants.MEMBER_OUT_STATUS_OW_VERIFY)
                transferTime = DateUtils.formatDate(memberOut.getHandleTime(), "yyyy.MM");
        }
        map.put("transferTime", transferTime);


        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null) {
                map.put(entry.getKey(), "");
            }
        }

        return map;
    }
}
