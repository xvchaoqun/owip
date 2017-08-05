package controller.cadre;

import controller.BaseController;
import domain.cadre.CadrePost;
import domain.cadre.CadrePostExample;
import domain.cadre.CadreView;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreRelate;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class CadrePostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 主职、兼职、任职级经历 放在同一个页面
    @RequiresPermissions("cadrePost:list")
    @RequestMapping("/cadrePost_page")
    public String cadrePost_page(HttpServletResponse response,
                                 @RequestParam(defaultValue = "1") Byte type, // “1 任现职情况”和“2 任职经历”
                                 Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 1) {
            // 主职
            modelMap.put("mainCadrePost", cadrePostService.getCadreMainCadrePost(cadreId));
            // 兼职
            modelMap.put("subCadrePosts", cadrePostService.getSubCadrePosts(cadreId));
        }
        if(type==3){
            modelMap.put("cadre", cadreService.findAll().get(cadreId));
            // 任职级经历
            modelMap.put("cadreAdminLevels", cadreAdminLevelService.getCadreAdminLevels(cadreId));
        }
        return "cadre/cadrePost/cadrePost_page";
    }

    @RequiresPermissions("cadrePost:list")
    @RequestMapping("/cadrePost_data")
    public void cadrePost_data(HttpServletResponse response,
                                    int cadreId,
                                    @RequestParam(required = false, defaultValue = "0") Boolean isMainPost,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadrePostExample example = new CadrePostExample();
        CadrePostExample.Criteria criteria = example.createCriteria()
                .andCadreIdEqualTo(cadreId).andIsMainPostEqualTo(isMainPost);
        example.setOrderByClause("sort_order desc");

        long count = cadrePostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePost> CadrePosts = cadrePostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", CadrePosts);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;

    }
    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadrePost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_au(CadrePost record,
                               Boolean isCpc,
                               HttpServletRequest request) {

        Integer id = record.getId();

        // 只用于主职
        if(BooleanUtils.isTrue(record.getIsMainPost()))
            record.setIsDouble(BooleanUtils.isTrue(record.getIsDouble()));

        if(BooleanUtils.isNotTrue(record.getIsMainPost()))
            record.setIsCpc(BooleanUtils.isTrue(isCpc));

        if (id == null) {
            cadrePostService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加现任职务：%s", JSONUtils.toString(record, MixinUtils.baseMixins(), false)));
        } else {
            cadrePostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新现任职务：%s", JSONUtils.toString(record, MixinUtils.baseMixins(), false)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadrePost_au")
    public String cadrePost_au(Integer id,
                               @RequestParam(defaultValue = "0") boolean isMainPost,
                               int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadrePost cadrePost = cadrePostMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePost", cadrePost);

            modelMap.put("unit", unitService.findAll().get(cadrePost.getUnitId()));
            if (cadrePost.getDoubleUnitId() != null)
                modelMap.put("doubleUnit", unitService.findAll().get(cadrePost.getDoubleUnitId()));
        }
        CadreView cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return isMainPost?"cadre/cadrePost/mainCadrePost_au":"cadre/cadrePost/subCadrePost_au";
    }

/*    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadrePost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadrePostMapper.deleteByPrimaryKey(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除现任职务：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadrePost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadrePostService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除现任职务：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadrePost_addDispatchs")
    public String cadrePost_addDispatchs(HttpServletResponse response,
                                         String type,
                                         int id, int cadreId, ModelMap modelMap) {


        Set<Integer> dispatchCadreIdSet = new HashSet<>(); // 已关联的干部任免文件ID
        List<DispatchCadre> relateDispatchCadres = new ArrayList<>(); // 已关联的干部任免文件

        Map<Integer, DispatchCadre> dispatchCadreMap = dispatchCadreService.findAll();
        List<DispatchCadreRelate> dispatchCadreRelates =
                dispatchCadreRelateService.findDispatchCadreRelates(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
        for (DispatchCadreRelate dispatchCadreRelate : dispatchCadreRelates) {
            Integer dispatchCadreId = dispatchCadreRelate.getDispatchCadreId();
            DispatchCadre dispatchCadre = dispatchCadreMap.get(dispatchCadreId);
            dispatchCadreIdSet.add(dispatchCadreId);
            relateDispatchCadres.add(dispatchCadre);
        }

        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);

        if (relateDispatchCadres.size() == 0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");

            List<DispatchCadre> dispatchCadres = iDispatchMapper.selectDispatchCadreList(cadreId, SystemConstants.DISPATCH_CADRE_TYPE_APPOINT);
            modelMap.put("dispatchCadres", dispatchCadres);

            Set<Integer> otherDispatchCadreRelateSet = dispatchCadreRelateService.findOtherDispatchCadreRelateSet(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
            modelMap.put("otherDispatchCadreRelateSet", otherDispatchCadreRelateSet);
        } else {
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        return "cadre/cadrePost/cadrePost_addDispatchs";
    }

    @RequestMapping(value = "/cadrePost_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_addDispatchs(HttpServletRequest request,
                                         int id,
                                         @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        dispatchCadreRelateService.updateDispatchCadreRelates(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_POST, ids);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "修改现任职务%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:changeOrder")
    @RequestMapping(value = "/cadrePost_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        CadrePost cadrePost = cadrePostMapper.selectByPrimaryKey(id);
        cadrePostService.changeOrder(id, cadrePost.getCadreId(), addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部职务调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
