package controller.member;

import domain.member.MemberCertify;
import domain.sys.SysUserView;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.MemberConstants;
import sys.shiro.CurrentUser;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/report")
public class MemberCertifyReportController extends MemberBaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    // 打印组织关系介绍信
    @RequestMapping(value = "/member_certify")
    public String member_certify(@CurrentUser SysUserView loginUser,
                               HttpServletRequest request, HttpServletResponse response,
                               Integer[] ids,
                               @RequestParam(required = false, defaultValue = "0") Boolean print,
                               @RequestParam(defaultValue = "pdf") String format,
                               Model model) throws IOException {

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : ids) {
            MemberCertify memberCertify = memberCertifyMapper.selectByPrimaryKey(id);
            Map<String, Object> map = getMemberCertifyMap(memberCertify);
            map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "member_certify.jpg");
            data.add(map);
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/member_certify.jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        if (print) {

            logger.info("组织关系证明打印 {}, {}, {}, {}, {}, {}",
                    new Object[]{loginUser.getUsername(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    private Map<String, Object> getMemberCertifyMap(MemberCertify record) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", record.getUser().getRealname());
        map.put("sn", record.getSn());
        map.put("ps", MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()).substring(0,2));
        map.put("title", record.getToTitle());
        map.put("from", record.getFromUnit());
        map.put("to", record.getToUnit());
        map.put("year", DateUtils.getYear(record.getCertifyDate()));
        map.put("month", DateUtils.getMonth(record.getCertifyDate()));
        map.put("day", DateUtils.getDay(record.getCertifyDate()));

        return map;

    }
}
