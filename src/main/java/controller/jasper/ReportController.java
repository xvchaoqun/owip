package controller.jasper;

import controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.spring.UserResUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 加密预览
     *
     * @param url  必传
     * @param _key 自定义加密字段名称，默认为ids
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/preview")
    public String preview(String url, String _key, HttpServletRequest request, ModelMap modelMap) {

        Map<String, String[]> parameterMap = request.getParameterMap();

        _key = StringUtils.defaultString(_key, "ids[]");
        String[] ids = parameterMap.get(_key);
        if(ids!=null && ids.length>0) {
            parameterMap.put(_key, new String[]{UserResUtils.sign(StringUtils.join(ids, ","))});
        }
        parameterMap.remove("url");
        String queryString = RequestUtils.toQueryString(parameterMap);

        modelMap.put("url", url + (StringUtils.isBlank(queryString)?"":"?"+queryString));

        return "jasper/preview";
    }


    @RequestMapping("/printPreview")
    public String printPreview() {

        return "jasper/print_preview";
    }

}
