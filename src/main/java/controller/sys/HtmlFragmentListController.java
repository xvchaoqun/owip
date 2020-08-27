package controller.sys;

import controller.BaseController;
import domain.sys.HtmlFragment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.utils.FormUtils;

import java.util.Map;

@Controller
public class HtmlFragmentListController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private void checkPermission(String cls){
        ShiroHelper.checkPermission(cls+":*");
    }

    @RequestMapping("/htmlFragment_list")
    public String htmlFragment_list(String[] cls, ModelMap modelMap) {

        return "sys/htmlFragment/htmlFragment_list";
    }

    @RequestMapping("/htmlFragment_list_item")
    public String htmlFragment_list_item(String code, ModelMap modelMap) {

        checkPermission(code);

        Map<String, HtmlFragment> htmlFragmentMap = htmlFragmentService.codeKeyMap();
        modelMap.put("htmlFragment", htmlFragmentMap.get(code));

        return "sys/htmlFragment/htmlFragment_list_item";
    }

    @RequestMapping(value = "/htmlFragment_list_item", method = RequestMethod.POST)
    @ResponseBody
    public Map do_htmlFragment_list_item(String cls, String content) {

        checkPermission(cls);

        HtmlFragment htmlFragment = htmlFragmentService.codeKeyMap().get(cls);
        HtmlFragment record = new HtmlFragment();
        record.setId(htmlFragment.getId());
        record.setContent(content);

        htmlFragmentService.updateByPrimaryKeySelective(record);

        return success(FormUtils.SUCCESS);
    }
}
