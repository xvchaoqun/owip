package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
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
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.CadreConstants;
import sys.utils.FormUtils;

import java.util.*;

/**
 * Created by lm on 2018/5/28.
 */
@Controller
public class CadreSearchController  extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 提取简介
    @RequiresPermissions("cadre:archive")
    @RequestMapping("/cadre_search_brief")
    public String cadre_search_brief(Integer[] cadreIds, ModelMap modelMap) {

        if(cadreIds!=null && cadreIds.length>0) {
            CadreViewExample example = new CadreViewExample();
            example.createCriteria().andIdIn(Arrays.asList(cadreIds));
            example.setOrderByClause("sort_order desc");
            List<CadreView> cadres = cadreViewMapper.selectByExample(example);

            List<Map<String, Object>> selectedItems = new ArrayList<>();
            for (CadreView cadre : cadres) {

                Map<String, Object> u = new HashMap<>();
                u.put("userId", cadre.getUserId());
                u.put("realname", cadre.getRealname());
                u.put("code", cadre.getCode());

                selectedItems.add(u);
            }

            modelMap.put("selectedItems", selectedItems);
        }

        return "cadre/cadreSearch/cadre_search_brief";
    }

    @RequiresPermissions("cadre:archive")
    @RequestMapping(value = "/cadre_search_brief", method = RequestMethod.POST)
    public String do_cadre_search_brief(Integer[] userIds, ModelMap modelMap) {

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        example.setOrderByClause("sort_order desc");
        List<CadreView> cadres = cadreViewMapper.selectByExample(example);
        modelMap.put("cadres", cadres);

        return "cadre/cadreSearch/cadre_search_brief_result";
    }

    // 查询干部所在库
    @RequiresPermissions("cadre:archive")
    @RequestMapping("/cadre_search")
    public String cadre_search() {

        return "cadre/cadreSearch/cadre_search";
    }

    @RequiresPermissions("cadre:archive")
    @RequestMapping(value = "/cadre_search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_search(int cadreId) {

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        String msg = "";
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        if (cadre == null) {
            msg = "该账号不存在";
        } else {
            resultMap.put("realname", cadre.getRealname());

            if (cadre == null) {
                msg = "该账号不是干部";
            } else {
                resultMap.put("cadreId", cadre.getId());
                resultMap.put("status", cadre.getStatus());
            }
        }
        resultMap.put("msg", msg);

        return resultMap;
    }

    @RequiresPermissions("cadre:archive")
    @RequestMapping(value = "/cadre_archive_search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_archive_search(int userId) {

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        String msg = "";

        SysUserView uv = sysUserService.findById(userId);
        if(uv==null){
            msg = "该账号不存在。";
        }else{
            resultMap.put("realname", uv.getRealname());
            resultMap.put("userId", uv.getId());
            CadreView cadre = cadreService.dbFindByUserId(userId);
             if (cadre == null) {
                msg = "该账号还没有干部档案。";
            } else {
                resultMap.put("cadreId", cadre.getId());
                msg = CadreConstants.CADRE_STATUS_MAP.get(cadre.getStatus());
            }
        }

        resultMap.put("msg", msg);
        return resultMap;
    }

    // 转到干部档案。如果是普通教师，则建立“非干部”档案
    @RequiresPermissions("cadre:archive")
    @RequestMapping("/cadre_archive")
    public String cadre_archive(int userId) {

        Integer cadreId = null;
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if(cadre==null) {
            Cadre tempCadre = cadreService.addTempCadre(userId);
            cadreId = tempCadre.getId();
        }else{
            cadreId = cadre.getId();
        }

        return "redirect:/cadre_view?hideBack=1&cadreId=" + cadreId;
    }
}
