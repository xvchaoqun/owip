package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.utils.FormUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/5/28.
 */
@Controller
public class CadreSearchController  extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 提取简介
    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre_search_brief")
    public String cadre_search_brief() {

        return "cadre/cadreSearch/cadre_search_brief";
    }

    @RequiresPermissions("cadre:list")
    @RequestMapping(value = "/cadre_search_brief", method = RequestMethod.POST)
    public String do_cadre_search_brief(@RequestParam(name = "userIds[]")Integer[] userIds, ModelMap modelMap) {

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        example.setOrderByClause("sort_order desc");
        List<CadreView> cadres = cadreViewMapper.selectByExample(example);
        modelMap.put("cadres", cadres);

        return "cadre/cadreSearch/cadre_search_brief_result";
    }

    // 查询干部所在库
    @RequiresPermissions("cadre:list")
    @RequestMapping("/cadre_search")
    public String cadre_search() {

        return "cadre/cadreSearch/cadre_search";
    }

    @RequiresPermissions("cadre:list")
    @RequestMapping(value = "/cadre_search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_search(int cadreId) {

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        String msg = "";
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        SysUserView sysUser = cadre.getUser();
        if (sysUser == null) {
            msg = "该用户不存在";
        } else {
            resultMap.put("realname", sysUser.getRealname());

            if (cadre == null) {
                msg = "该用户不是干部";
            } else {
                resultMap.put("cadreId", cadre.getId());
                resultMap.put("status", cadre.getStatus());
            }
        }
        resultMap.put("msg", msg);

        return resultMap;
    }
}
