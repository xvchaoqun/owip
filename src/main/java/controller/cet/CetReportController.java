package controller.cet;

import bean.UserBean;
import domain.cet.CetProject;
import domain.cet.CetProjectObj;
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
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class CetReportController extends CetBaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    // 培训证书打印
    @RequestMapping(value = "/cet_graduate", method = RequestMethod.GET)
    public String cet_graduate(@CurrentUser SysUserView loginUser,
                               HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "ids[]") Integer[] ids,
                               @RequestParam(required = false, defaultValue = "0") Boolean print,
                               @RequestParam(defaultValue = "pdf") String format,
                               Model model) throws IOException {

        // 分党委、组织部管理员或管理员才可以操作
        if (!ShiroHelper.hasAnyRoles(RoleConstants.ROLE_CET_ADMIN, RoleConstants.ROLE_ADMIN)) {
            throw new UnauthorizedException();
        }

        String fileName = "cet_graduate";
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : ids) {

            CetProjectObj obj = cetProjectObjMapper.selectByPrimaryKey(id);

            Map<String, Object> map = getCetGraduateInfoMap(obj);
            map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + fileName + ".jpg");
            data.add(map);
        }

        /*if(format.equals("image")){
            model.addAttribute("image.zoom", 0.25f);
        }*/

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/"+ fileName +".jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        if (print) {

            logger.info("培训结业证书打印 {}, {}, {}, {}, {}, {}",
                    new Object[]{loginUser.getUsername(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }


    // 获取组织关系转出相关信息
    public Map<String, Object> getCetGraduateInfoMap(CetProjectObj obj) {

        UserBean userBean = userBeanService.get(obj.getUserId());
        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(obj.getProjectId());

        String gender = userBean.getGender()==null?"":SystemConstants.GENDER_MAP.get(userBean.getGender());

        String birthYear = "";
        String birthMonth = "";
        if(userBean.getBirth()!=null) {
            birthYear = DateUtils.getYear(userBean.getBirth())+"";
            birthMonth = DateUtils.getMonth(userBean.getBirth())+"";
        }
        String startDate = DateUtils.formatDate(cetProject.getStartDate(), DateUtils.YYYY_MM_DD_CHINA);
        String endDate = DateUtils.formatDate(cetProject.getEndDate(), DateUtils.YYYY_MM_DD_CHINA);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("realname", userBean.getRealname());
        map.put("gender", gender);
        map.put("birth_year", birthYear);
        map.put("birth_month", birthMonth);
        map.put("start_date", startDate);
        map.put("end_date", endDate);
        map.put("organizer", "党委组织部");
        map.put("train_name", cetProject.getName());

        map.put("sn", "S0009999999001TEST");
        map.put("idcard", userBean.getIdcard());

        if(cetProject.getEndDate()!=null) {
            map.put("year", DateUtils.getYear(cetProject.getEndDate()));
            map.put("month", DateUtils.getMonth(cetProject.getEndDate()));
            map.put("day", DateUtils.getDay(cetProject.getEndDate()));
        }

        return map;
    }
}
