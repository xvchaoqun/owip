package controller.member;

import bean.UserBean;
import domain.member.MemberOut;
import domain.member.MemberStay;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.StudentInfo;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.jasper.ReportUtils;
import sys.spring.UserRes;
import sys.spring.UserResUtils;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/report")
public class MemberStayReportController extends MemberBaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    // 出国暂留
    @RequestMapping(value = "/member_stay")
    public String member_stay(HttpServletRequest request,
                              HttpServletResponse response,
                              byte type,
                              String ids,
                              @RequestParam(required = false, defaultValue = "0") Byte print,
                              @RequestParam(defaultValue = "pdf") String format,
                              Model model) throws IOException, JRException {

        UserRes verify = UserResUtils.verify(ids);
        String res = verify.getRes();
        Set<Integer> idSet = NumberUtils.toIntSet(res, ",");

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : idSet) {
            Map<String, Object> map = getMemberStayMap(id);

            if (type == MemberConstants.MEMBER_STAY_TYPE_ABROAD) {
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

        String jasperPath = "";
        if (type == MemberConstants.MEMBER_STAY_TYPE_ABROAD) {
            jasperPath = "jasper/member_stay_abroad.jasper";
        } else {
            jasperPath = "jasper/member_stay_internal.jasper";
        }

        if (print==0 || print==1) {

            // 报表数据源
            JRDataSource jrDataSource = new JRMapCollectionDataSource(data);
            model.addAttribute("url", "/WEB-INF/" + jasperPath);
            model.addAttribute("format", format); // 报表格式
            model.addAttribute("jrMainDataSource", jrDataSource);

            if(print==1) {
                iMemberMapper.increasePrintCount("ow_member_stay", new ArrayList<>(idSet), new Date(), ShiroHelper.getCurrentUserId());

                logger.info("申请组织关系暂留打印 {}, {}, {}, {}, {}, {}",
                        new Object[]{ShiroHelper.getCurrentUsername(), request.getRequestURI(),
                                request.getMethod(),
                                JSONUtils.toString(request.getParameterMap(), false),
                                RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
            }

            return "iReportView";
        }else if (print == 2) {

            DownloadUtils.addFileDownloadCookieHeader(response);
            ReportUtils.exportPdf(jasperPath, data,
                    null, "申请组织关系暂留", request, response);
            return null;
        }/*else if (print == 3) {
            ReportUtils.exportDoc(jasperPath, data,
                    null, "申请组织关系暂留", request, response);
            return null;
        }*/

        return null;
    }

    // 获取出国暂留相关信息
    public Map<String, Object> getMemberStayMap(int id) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        int userId = memberStay.getUserId();
        UserBean u = userBeanService.get(userId);
        StudentInfo student = studentInfoService.get(userId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", memberStay.getCode());

        Date createTime = memberStay.getCreateTime();
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

        map.put("birth", DateUtils.formatDate(u.getBirth(), DateUtils.YYYYMM));
        map.put("growTime", DateUtils.formatDate(u.getGrowTime(), "yyyy.MM.dd"));
        map.put("nativePlace", u.getNativePlace());
        map.put("idcard", u.getIdcard());
        map.put("grade", student==null?"":student.getEnrolYear());
        // 学历？
        map.put("education", student==null?"":student.getEduLevel());
        map.put("mobile", memberStay.getMobile());
        map.put("phone", memberStay.getPhone());
        map.put("qq", memberStay.getQq());
        map.put("email", memberStay.getEmail());
        map.put("weixin", memberStay.getWeixin());

        // 接收函？
        map.put("letter", "");

        String name1 = "";
        if (memberStay.getName1() != null && memberStay.getRelate1() != null) {
            name1 = String.format("%s(%s)", memberStay.getName1(), memberStay.getRelate1());
        } else if (memberStay.getName1() != null) {
            name1 = memberStay.getName1();
        }
        map.put("name1", name1);
        map.put("mobile1", memberStay.getMobile1());
        map.put("stayReason", memberStay.getStayReason());


        if (memberStay.getType() == MemberConstants.MEMBER_STAY_TYPE_ABROAD) {
            map.put("inAddress", memberStay.getInAddress());
            map.put("outAddress", memberStay.getOutAddress());

            map.put("unit1", memberStay.getUnit1());
            map.put("post1", memberStay.getPost1());
            map.put("phone1", memberStay.getPhone1());
            map.put("email1", memberStay.getEmail1());

            String name2 = "";
            if (memberStay.getName2() != null && memberStay.getRelate2() != null) {
                name2 = String.format("%s(%s)", memberStay.getName2(), memberStay.getRelate2());
            } else if (memberStay.getName2() != null) {
                name2 = memberStay.getName2();
            }
            map.put("name2", name2);

            map.put("unit2", memberStay.getUnit2());
            map.put("post2", memberStay.getPost2());
            map.put("phone2", memberStay.getPhone2());
            map.put("mobile2", memberStay.getMobile2());
            map.put("email2", memberStay.getEmail2());

            map.put("country", memberStay.getCountry());
            map.put("school", memberStay.getSchool());

            String startTime = "";
            if (memberStay.getStartTime() != null && memberStay.getEndTime() != null) {
                startTime = DateUtils.formatDate(memberStay.getStartTime(), DateUtils.YYYYMM)
                        + " 至 " + DateUtils.formatDate(memberStay.getEndTime(), DateUtils.YYYYMM);
            } else if (memberStay.getStartTime() != null) {
                startTime = DateUtils.formatDate(memberStay.getStartTime(), DateUtils.YYYYMM);
            }
            map.put("startTime", startTime);

            map.put("payTime", DateUtils.formatDate(memberStay.getPayTime(), DateUtils.YYYYMM));

            Byte abroadType = memberStay.getAbroadType();
            map.put("typeCheck1", abroadType == MemberConstants.MEMBER_STAY_ABROAD_TYPE_MAP_PUB ? "√" : "");
            map.put("typeCheck2", abroadType == MemberConstants.MEMBER_STAY_ABROAD_TYPE_MAP_SELF ? "√" : "");
        }

        map.put("overDate", DateUtils.formatDate(memberStay.getOverDate(), DateUtils.YYYYMM));

        String branchAdmin = "";
        if (memberStay.getOrgBranchAdminPhone() != null && memberStay.getOrgBranchAdmin() != null) {
            branchAdmin = String.format("%s(%s)", memberStay.getOrgBranchAdmin().getRealname(), memberStay.getOrgBranchAdminPhone());
        } else if (memberStay.getOrgBranchAdmin() != null) {
            branchAdmin = memberStay.getOrgBranchAdmin().getRealname();
        }
        map.put("branchAdmin", branchAdmin);

        map.put("saveStartTime", DateUtils.formatDate(memberStay.getSaveStartTime(), DateUtils.YYYYMM)
                + " 至 " + DateUtils.formatDate(memberStay.getSaveEndTime(), DateUtils.YYYYMM));

        String party = "";
        Map<Integer, Party> partyMap = partyService.findAll();
        if (memberStay.getPartyId() != null) {
            Party _party = partyMap.get(memberStay.getPartyId());
            if (_party != null) party = _party.getName();
        }
        map.put("party", party); // 所在党组织

        String branch = "";
        Map<Integer, Branch> branchMap = branchService.findAll();
        if (memberStay.getBranchId() != null) {
            Branch _branch = branchMap.get(memberStay.getBranchId());
            if (_branch != null) branch = _branch.getName();
        }
        map.put("branch", branch); // 暂留所在党支部

        String toBranch = "";
        if (memberStay.getToBranchId() != null) {
            Branch _branch = branchMap.get(memberStay.getToBranchId());
            if (_branch != null) toBranch = _branch.getName();
        }
        map.put("toBranch", toBranch); // 暂留所在党支部

        map.put("check2", (u.getPoliticalStatus() != null && u.getPoliticalStatus()
                == MemberConstants.MEMBER_POLITICAL_STATUS_GROW) ? "√" : ""); // 预备党员
        map.put("check1", (u.getPoliticalStatus() != null && u.getPoliticalStatus()
                == MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE) ? "√" : ""); // 正式党员

        String transferTime = "";
        if (u.getMemberStatus() != null && u.getMemberStatus() == MemberConstants.MEMBER_STATUS_OUT) {
            MemberOut memberOut = memberOutService.getLatest(userId);
            if (memberOut != null && memberOut.getStatus()==MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY)
                transferTime = DateUtils.formatDate(memberOut.getHandleTime(), DateUtils.YYYYMM);
        }
        map.put("transferTime", transferTime);

        map.put("selfOpinion", "申请保留组织关系");
        map.put("selfYear", DateUtils.getYear(createTime));
        map.put("selfMonth", DateUtils.getMonth(createTime));
        map.put("selfDay", DateUtils.getDay(createTime));

        Date branchCheckTime = memberStay.getBranchCheckTime();
        map.put("branchOpinion", "审批通过");
        if(branchCheckTime!=null) {
            map.put("branchYear", DateUtils.getYear(branchCheckTime));
            map.put("branchMonth", DateUtils.getMonth(branchCheckTime));
            map.put("branchDay", DateUtils.getDay(branchCheckTime));
        }

        Date partyCheckTime = memberStay.getPartyCheckTime();
        map.put("partyOpinion", "审批通过");
        if(partyCheckTime!=null) {
            map.put("partyYear", DateUtils.getYear(partyCheckTime));
            map.put("partyMonth", DateUtils.getMonth(partyCheckTime));
            map.put("partyDay", DateUtils.getDay(partyCheckTime));
        }

        Date checkTime = memberStay.getCheckTime();
        map.put("owOpinion", "审批通过");
        if(checkTime!=null) {
            map.put("owYear", DateUtils.getYear(checkTime));
            map.put("owMonth", DateUtils.getMonth(checkTime));
            map.put("owDay", DateUtils.getDay(checkTime));
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null) {
                map.put(entry.getKey(), "");
            }
        }

        return map;
    }
}
