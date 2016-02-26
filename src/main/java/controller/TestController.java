package controller;

import com.lowagie.text.DocumentException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.TestServcie;
import sys.utils.ConfigUtil;
import sys.utils.FormUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/1/18.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestServcie testServcie;

    //@RequestMapping("/toMember")
    @ResponseBody
    public String toMember(int userId) {

        testServcie.toMember(userId);
        return FormUtils.SUCCESS;
    }

    //@RequestMapping("/toGuest")
    @ResponseBody
    public String toGuest(int userId) {

        testServcie.toGuest(userId);
        return FormUtils.SUCCESS;
    }
    @RequestMapping(value = "/report2", method = RequestMethod.GET)
    public String report2(Integer type, Model model) throws IOException, DocumentException {

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "123123");
        map.put("name", "张三");
        map.put("partyName", "历史学院党总支");
        map.put("age", "25");
        map.put("nation", "汉");
        map.put("from", "北师大历史学院党总支");
        map.put("to", "北师大历史学院党总支");
        map.put("idcard", "360502198547544784");
        map.put("year", "2016");
        map.put("month", "5");
        map.put("day", "15");
        map.put("tel", "010-84572014");
        map.put("fax", "010-84572014");
        map.put("postCode", "100875");
        map.put("imagePath", ConfigUtil.defaultConfigPath() + File.separator + "jasper" + File.separator );
        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        ///BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/report2.jasper");
        //model.addAttribute("url", "/WEB-INF/jasper/2.jasper");
        if(type!=null)
            model.addAttribute("url", "/WEB-INF/jasper/report2_noimg.jasper");
        model.addAttribute("format", "pdf"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }
}
