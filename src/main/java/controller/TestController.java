package controller;

import com.lowagie.text.DocumentException;
import domain.sys.SysResource;
import domain.sys.SysResourceExample;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.TestServcie;
import sys.spring.Base64File;
import sys.spring.RequestBase64;
import sys.utils.ConfigUtil;
import sys.utils.FormUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by fafa on 2016/1/18.
 */
//@Controller
//@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    TestServcie testServcie;

    @RequestMapping(value = "/updateSysResource")
    @ResponseBody
    public Map updateSysResource(){

        SysResourceExample example = new SysResourceExample();
        List<SysResource> sysResources = sysResourceMapper.selectByExample(example);

        for (SysResource sysResource : sysResources) {

            Integer parentId = sysResource.getParentId();
            if(parentId!=null) {
                SysResource parent = sysResourceMapper.selectByPrimaryKey(parentId);
                if(parent!=null) {
                    SysResource record = new SysResource();
                    record.setId(sysResource.getId());
                    record.setParentIds(parent.getParentIds() + parentId + "/");
                    sysResourceMapper.updateByPrimaryKeySelective(record);
                }
            }
        }
        return success();
    }

    // 保存base64图片
    //@RequestMapping(value = "/base64", method = RequestMethod.POST)
    @RequestMapping(value = "/base64")
    @ResponseBody
    public String base64(@RequestBase64 Base64File file, @RequestBase64 List<Base64File> file2) {
        try {
            System.out.println(Arrays.toString(file.getBytes()));

            System.out.println("=============================");
            file2.stream().forEach(new Consumer<Base64File>() {

                @Override
                public void accept(Base64File t) {
                    try {
                        System.out.println(Arrays.toString(t.getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            file.transferTo(new File("d:/test.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FormUtils.SUCCESS;
    }

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

    //@RequestMapping(value = "/report2", method = RequestMethod.GET)
    public String report2(Integer type, Model model) throws IOException, DocumentException {

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "123123");
        map.put("name", "张三");
        map.put("partyName", "历史学院党总支");
        map.put("age", "25");
        map.put("nation", "汉");
        map.put("from", "历史学院党总支");
        map.put("to", "历史学院党总支");
        map.put("idcard", "360502198547544784");
        map.put("year", "2016");
        map.put("month", "5");
        map.put("day", "15");
        map.put("tel", "010-84572014");
        map.put("fax", "010-84572014");
        map.put("postCode", "100875");
        map.put("imagePath", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR);
        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        ///BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/report2.jasper");
        //model.addAttribute("url", "/WEB-INF/jasper/2.jasper");
        if (type != null)
            model.addAttribute("url", "/WEB-INF/jasper/report2_noimg.jasper");
        model.addAttribute("format", "pdf"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }
}
