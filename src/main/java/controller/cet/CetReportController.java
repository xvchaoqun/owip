package controller.cet;

import bean.UserBean;
import domain.cet.CetRecord;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
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

    // 结业证书
    @RequestMapping(value = "/cet_cert")
    public String cet_cert(HttpServletRequest request, HttpServletResponse response,
                               Byte sourceType,
                               @RequestParam(value = "ids") Integer[] ids,
                               @RequestParam(required = false, defaultValue = "0") Boolean print,
                               @RequestParam(defaultValue = "pdf") String format,
                               @RequestParam(required = false, defaultValue = "0") Boolean download,
                               String filename,
                               Model model) throws IOException {

        int currentUserId = ShiroHelper.getCurrentUserId();
        boolean isAdmin = ShiroHelper.hasAnyRoles(RoleConstants.ROLE_CET_ADMIN, RoleConstants.ROLE_ADMIN);

        String fileName = "cet_cert";
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (Integer id : ids) {

            CetRecord cetRecord = null;
            if(sourceType==null){
                cetRecord = cetRecordMapper.selectByPrimaryKey(id);
            }else{
                cetRecord = cetRecordService.get(sourceType, id);
            }

            // 本人、培训管理员、系统管理员、二级党委管理员
            if(currentUserId!=cetRecord.getUserId() && !isAdmin
                && cetPartyAdminService.get(cetRecord.getCetPartyId(), currentUserId)==null){

                throw new UnauthorizedException();
            }

            Map<String, Object> map = getCetGraduateInfoMap(cetRecord);
            map.put("bg", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR + fileName + ".jpg");
            data.add(map);
        }

        if(format.equals("pdf") && download){

            model.addAttribute("download", true);
            model.addAttribute("filename", filename);
            model.addAttribute("request", request);
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        model.addAttribute("url", "/WEB-INF/jasper/"+ fileName +".jasper");
        model.addAttribute("format", format); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        if (print) {

            logger.info("党校培训结业证书打印 {}, {}, {}, {}, {}, {}",
                    new Object[]{ShiroHelper.getCurrentUsername(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }


    // 获取组织关系转出相关信息
    public Map<String, Object> getCetGraduateInfoMap(CetRecord record) {

        UserBean userBean = userBeanService.get(record.getUserId());

        String gender = userBean.getGender()==null?"":SystemConstants.GENDER_MAP.get(userBean.getGender());

        String birthYear = "";
        Integer birthMonth = null;
        if(userBean.getBirth()!=null) {
            birthYear = DateUtils.getYear(userBean.getBirth())+"";
            birthMonth = DateUtils.getMonth(userBean.getBirth());
        }
        String startDate = DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD_CHINA);
        String endDate = DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD_CHINA);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("realname", userBean.getRealname());
        map.put("gender", gender);
        map.put("birth_year", birthYear);
        if(birthMonth!=null) {
            map.put("birth_month", String.format("%02d", birthMonth));
        }
        map.put("start_date", startDate);
        map.put("end_date", endDate);
        map.put("organizer", record.getOrganizer());
        map.put("train_name", record.getName());

        map.put("sn", cetRecordService.selectOrUpdateCertNo(record));
        map.put("idcard", userBean.getIdcard());

        if(record.getEndDate()!=null) {
            map.put("year", DateUtils.getYear(record.getEndDate()));
            map.put("month", DateUtils.getMonth(record.getEndDate()));
            map.put("day", DateUtils.getDay(record.getEndDate()));
        }

        return map;
    }
}
