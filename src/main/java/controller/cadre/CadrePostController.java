package controller.cadre;

import controller.BaseController;
import domain.cadre.CadrePost;
import domain.cadre.CadrePostExample;
import domain.cadre.CadreView;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreRelate;
import domain.unit.Unit;
import domain.unit.UnitPostView;
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
import org.springframework.web.util.HtmlUtils;
import service.dispatch.DispatchCadreRelateService;
import service.dispatch.DispatchCadreService;
import sys.constants.DispatchConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
            modelMap.put("cadre", cadreViewMapper.selectByPrimaryKey(cadreId));
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
                               @RequestParam(value = "unitIds[]", required = false) Integer[] unitIds,
                               HttpServletRequest request) {

        Integer id = record.getId();
        record.setPost(HtmlUtils.htmlUnescape(record.getPost()));
        // 只用于主职
        if(BooleanUtils.isTrue(record.getIsMainPost())) {
            record.setIsDouble(BooleanUtils.isTrue(record.getIsDouble()));
            if(record.getIsDouble()){
                if(unitIds==null || unitIds.length==0) {
                    return failed("请选择双肩挑单位");
                }
                record.setDoubleUnitIds(StringUtils.join(unitIds, ","));
            }
        }
        if(record.getUnitPostId()!=null) {
            CadrePost byUnitPostId = cadrePostService.getByUnitPostId(record.getUnitPostId());
            if(byUnitPostId!=null && (id==null || id!=byUnitPostId.getId().intValue())){
                return failed(String.format("岗位已被%s(%s)使用。",
                        byUnitPostId.getCadre().getRealname(),
                        byUnitPostId.getIsMainPost()?"主职":"兼职"));
            }
        }

        if(BooleanUtils.isNotTrue(record.getIsMainPost()))
            record.setIsCpc(BooleanUtils.isTrue(isCpc));

        record.setIsMainPost(BooleanUtils.isTrue(record.getIsMainPost()));
        if (id == null) {
            cadrePostService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加现任职务：%s", JSONUtils.toString(record, MixinUtils.baseMixins(), false)));
        } else {
            cadrePostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新现任职务：%s", JSONUtils.toString(record, MixinUtils.baseMixins(), false)));
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

            if(cadrePost!=null && cadrePost.getUnitPostId()!=null) {
                UnitPostView unitPost = iUnitMapper.getUnitPost(cadrePost.getUnitPostId());
                modelMap.put("unitPost", unitPost);
            }

            modelMap.put("unit", unitService.findAll().get(cadrePost.getUnitId()));
            /*if (cadrePost.getDoubleUnitId() != null)
                modelMap.put("doubleUnit", unitService.findAll().get(cadrePost.getDoubleUnitId()));*/
        }
        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        modelMap.put("cadre", cadre);

        // MAP<unitTypeId, List<unitId>>
        Map<Integer, List<Integer>> unitListMap = new LinkedHashMap<>();
        Map<Integer, List<Integer>> historyUnitListMap = new LinkedHashMap<>();
        Map<Integer, Unit> unitMap = unitService.findAll();
        for (Unit unit : unitMap.values()) {

            Integer unitTypeId = unit.getTypeId();
            if (unit.getStatus() == SystemConstants.UNIT_STATUS_HISTORY){
                List<Integer> units = historyUnitListMap.get(unitTypeId);
                if (units == null) {
                    units = new ArrayList<>();
                    historyUnitListMap.put(unitTypeId, units);
                }
                units.add(unit.getId());
            }else {
                List<Integer> units = unitListMap.get(unitTypeId);
                if (units == null) {
                    units = new ArrayList<>();
                    unitListMap.put(unitTypeId, units);
                }
                units.add(unit.getId());
            }
        }
        modelMap.put("unitListMap", unitListMap);
        modelMap.put("historyUnitListMap", historyUnitListMap);

        return isMainPost?"cadre/cadrePost/mainCadrePost_au":"cadre/cadrePost/subCadrePost_au";
    }

/*    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadrePost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadrePostMapper.deleteByPrimaryKey(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除现任职务：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadrePost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadrePostService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除现任职务：%s", StringUtils.join(ids, ",")));
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

        DispatchCadreRelateService dispatchCadreRelateService = CmTag.getBean(DispatchCadreRelateService.class);
        List<DispatchCadreRelate> dispatchCadreRelates =
                dispatchCadreRelateService.findDispatchCadreRelates(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
        for (DispatchCadreRelate dispatchCadreRelate : dispatchCadreRelates) {
            Integer dispatchCadreId = dispatchCadreRelate.getDispatchCadreId();
            DispatchCadre dispatchCadre = CmTag.getDispatchCadre(dispatchCadreId);
            dispatchCadreIdSet.add(dispatchCadreId);
            relateDispatchCadres.add(dispatchCadre);
        }

        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);

        if (relateDispatchCadres.size() == 0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");

            List<DispatchCadre> dispatchCadres = iDispatchMapper.selectDispatchCadreList(cadreId, DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT);
            modelMap.put("dispatchCadres", dispatchCadres);

            // 去掉：主职和兼职的任免文件改为可以重复，因为有时主职和兼职是放在一个文件中的
            //Set<Integer> otherDispatchCadreRelateSet = dispatchCadreRelateService.findOtherDispatchCadreRelateSet(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
            //modelMap.put("otherDispatchCadreRelateSet", otherDispatchCadreRelateSet);
        } else {
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        return "cadre/cadrePost/cadrePost_addDispatchs";
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadrePost_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_addDispatchs(HttpServletRequest request,
                                         int id,
                                         @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        DispatchCadreRelateService dispatchCadreRelateService = CmTag.getBean(DispatchCadreRelateService.class);
        dispatchCadreRelateService.updateDispatchCadreRelates(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST, ids);
        logger.info(addLog(LogConstants.LOG_ADMIN, "修改现任职务%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:changeOrder")
    @RequestMapping(value = "/cadrePost_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadrePostService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "干部职务调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
