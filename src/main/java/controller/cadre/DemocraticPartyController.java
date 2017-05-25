package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/9/27.
 *
 * 民主党派干部库，和干部库的部分字段对应
 */
@Controller
public class DemocraticPartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("democraticParty:list")
    @RequestMapping("/democraticParty")
    public String cadre() {

        return "index";
    }
    @RequiresPermissions("democraticParty:list")
    @RequestMapping("/democraticParty_page")
    public String cadre_page(Integer cadreId,ModelMap modelMap) {

        if (cadreId!=null) {
            CadreView cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            if(cadre!=null) {
                SysUserView sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }

        return "cadre/democraticParty/democraticParty_page";
    }
    @RequiresPermissions("democraticParty:list")
    @RequestMapping("/democraticParty_data")
    public void democraticParty_data(HttpServletResponse response,
                           @SortParam(required = false, defaultValue = "sort_order",tableName = "cadre") String sort,
                           @OrderParam(required = false, defaultValue = "desc") String order,
                           Byte status,
                           Integer cadreId,
                           Integer typeId,
                           Integer postId,
                           Integer dpTypeId,
                           String title,
                           Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreViewExample example = new CadreViewExample();
        CadreViewExample.Criteria criteria = example.createCriteria().andIsDpEqualTo(true);
        example.setOrderByClause(String.format("%s %s", sort, order));
        if (status!=null) {
            criteria.andStatusEqualTo(status);
        }
        if (cadreId!=null) {
            criteria.andIdEqualTo(cadreId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (dpTypeId!=null) {
            criteria.andDpTypeIdEqualTo(dpTypeId);
        }
        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike("%" + title + "%");
        }

        long count = cadreViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreView> Cadres = cadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(CadreView.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("democraticParty:edit")
    @RequestMapping(value = "/democraticParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_democraticParty_au(Integer cadreId, Integer dpTypeId, String _dpAddTime,
                                     String dpPost, String dpRemark, HttpServletRequest request) {

        cadreService.addDemocraticParty(cadreId, dpTypeId, _dpAddTime, dpPost, dpRemark);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新民主党派干部：%s", cadreId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("democraticParty:edit")
    @RequestMapping("/democraticParty_au")
    public String democraticParty_au(Integer cadreId,  ModelMap modelMap) {

        if(cadreId!=null) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
            modelMap.put("sysUser", sysUserService.findById(cadre.getUserId()));
        }

        return "cadre/democraticParty/democraticParty_au";
    }

    @RequiresPermissions("democraticParty:del")
    @RequestMapping(value = "/democraticParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            cadreService.democraticParty_batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除民主党派干部：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }


}
