package controller.member;

import bean.UserBean;
import domain.base.MetaType;
import domain.member.MemberOut;
import domain.sys.SysUserView;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.spring.UserRes;
import sys.spring.UserResUtils;
import sys.tags.CmTag;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
@RequestMapping("/report")
public class MemberOutReportController extends MemberBaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    // 介绍信套打
    @RequestMapping(value = "/letter_fill_print")
    public String letter_fill_print(@CurrentUser SysUserView loginUser,
                                    HttpServletRequest request, HttpServletResponse response,
                                String ids,
                                @RequestParam(required = false, defaultValue = "0") Boolean print,
                                Integer type,
                                @RequestParam(defaultValue = "pdf") String format,
                                Model model) throws IOException {

        UserRes verify = UserResUtils.verify(ids);
        String res = verify.getRes();
        Set<Integer> idSet = NumberUtils.toIntSet(res, ",");

        Set<Integer> fillPrintTypeSet = new HashSet<>();
        String fileName = "";
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : idSet) {
            MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);

            MetaType fillPrintType = metaTypeMapper.selectByPrimaryKey(memberOut.getType());
            if(BooleanUtils.isNotTrue(fillPrintType.getBoolAttr())){
                JSONUtils.write(response, MessageFormat.format("[{0}]的介绍信请直接打印，无需套打。",
                        memberOut.getUser().getRealname()), false);
                return null;
            }

            fillPrintTypeSet.add(fillPrintType.getId());
            if(fillPrintTypeSet.size()>1){
                 JSONUtils.write(response, MessageFormat.format("[{0}]的介绍信套打格式不相同，请确认批量套打时所选记录的套打模板是否一致。",
                        memberOut.getUser().getRealname()), false);
                return null;
            }

            fileName = fillPrintType.getExtraAttr();

            Map<String, Object> map = getMemberOutInfoMap(memberOut);
            map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + fileName + ".jpg");
            if (type != null && type == 1) {
                //套打，需要透明背景图片
                map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + "px.png");
            }
            data.add(map);
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/"+ fileName +".jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        if (print) {
            iMemberMapper.increasePrintCount("ow_member_out", new ArrayList<>(idSet), new Date(), ShiroHelper.getCurrentUserId());

            logger.info("介绍信套打 {}, {}, {}, {}, {}, {}",
                    new Object[]{loginUser.getUsername(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    // 介绍信打印
    @RequestMapping(value = "/letter_print")
    public String letter_print(@CurrentUser SysUserView loginUser,
                               HttpServletRequest request, HttpServletResponse response,
                               String ids,
                               @RequestParam(required = false, defaultValue = "0") Boolean print,
                               @RequestParam(defaultValue = "pdf") String format,
                               Model model) throws IOException {

        UserRes verify = UserResUtils.verify(ids);
        String res = verify.getRes();
        Set<Integer> idSet = NumberUtils.toIntSet(res, ",");

        Set<Integer> fillPrintTypeSet = new HashSet<>();
        String fileName = "";
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : idSet) {
            MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);

            MetaType fillPrintType = metaTypeMapper.selectByPrimaryKey(memberOut.getType());
            if(BooleanUtils.isTrue(fillPrintType.getBoolAttr())){
                JSONUtils.write(response, MessageFormat.format("[{0}]的介绍信不可直接打印，需套打。",
                        memberOut.getUser().getRealname()), false);
                return null;
            }

            fillPrintTypeSet.add(fillPrintType.getId());
            if(fillPrintTypeSet.size()>1){
                 JSONUtils.write(response, MessageFormat.format("[{0}]的介绍信打印格式不相同，请确认批量打印时所选记录的打印模板是否一致。",
                        memberOut.getUser().getRealname()), false);
                return null;
            }

            fileName = fillPrintType.getExtraAttr();

            Map<String, Object> map = getMemberOutInfoMap(memberOut);
            map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + fileName + ".jpg");
            data.add(map);
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/"+ fileName +".jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        if (print) {
            iMemberMapper.increasePrintCount("ow_member_out", new ArrayList<>(idSet), new Date(), ShiroHelper.getCurrentUserId());

            logger.info("介绍信打印 {}, {}, {}, {}, {}, {}",
                    new Object[]{loginUser.getUsername(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }


    // 获取组织关系转出相关信息
    public Map<String, Object> getMemberOutInfoMap(MemberOut memberOut) {

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
        if (CmTag.getBoolProperty("use_code_as_identify")){
            map.put("code", userBean.getCode());
        }else {
            map.put("code", StringUtils.trimToEmpty(memberOut.getCode()));
        }
        Date handleTime = memberOut.getHandleTime();

        map.put("handleYear", handleTime != null ? DateUtils.getYear(handleTime) : "");
        map.put("handleMonth", handleTime != null ? DateUtils.getMonth(handleTime) : "");
        map.put("handleDay", handleTime != null ? DateUtils.getDay(handleTime) : "");

        return map;
    }
}
