package controller.cadre;

import controller.BaseController;
import domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.DispatchConstants;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class CadrePostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePost:list")
    @RequestMapping("/cadrePost")
    public String cadrePost() {

        return "index";
    }
    // 主职、兼职、任职级经历 放在同一个页面
    @RequiresPermissions("cadrePost:list")
    @RequestMapping("/cadrePost_page")
    public String cadrePost_page(HttpServletResponse response,
                                  Integer cadreId, ModelMap modelMap) {

        List<CadrePost> cadrePosts = new ArrayList<>();
        {
            CadrePostExample example = new CadrePostExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadrePosts = cadrePostMapper.selectByExample(example);
        }
        List<CadreMainWork> cadreMainWorks = new ArrayList<>();
        {
            CadreMainWorkExample example = new CadreMainWorkExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadreMainWorks = cadreMainWorkMapper.selectByExample(example);
        }
        List<CadreSubWork> cadreSubWorks = new ArrayList<>();
        {
            CadreSubWorkExample example = new CadreSubWorkExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadreSubWorks = cadreSubWorkMapper.selectByExample(example);
        }
        modelMap.put("cadrePosts", cadrePosts);
        modelMap.put("cadreMainWorks", cadreMainWorks);
        modelMap.put("cadreSubWorks", cadreSubWorks);

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        modelMap.put("unitMap", unitService.findAll());
        modelMap.put("dispatchCadreMap", dispatchCadreService.findAll());
        modelMap.put("dispatchMap", dispatchService.findAll());
        modelMap.put("postMap", metaTypeService.metaTypes("mc_post"));
        modelMap.put("adminLevelMap", metaTypeService.metaTypes("mc_admin_level"));
        modelMap.put("postClassMap", metaTypeService.metaTypes("mc_post_class"));

        return "cadrePost/cadrePost_page";
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadrePost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_au(CadrePost record, String _startTime, String _endTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endTime)){
            record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD));
        }

        record.setIsPresent((record.getIsPresent()==null)?false:record.getIsPresent());

        if (id == null) {
            cadrePostMapper.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加任职级经历：%s", record.getId()));
        }else {
            cadrePostMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新任职级经历：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadrePost_au")
    public String cadrePost_au(Integer id,  int cadreId,ModelMap modelMap) {

        if (id != null) {
            CadrePost cadrePost = cadrePostMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePost", cadrePost);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadrePost/cadrePost_au";
    }

    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadrePost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadrePostMapper.deleteByPrimaryKey(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除任职级经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadrePost_addDispatchs")
    public String cadrePost_addDispatchs(HttpServletResponse response, String type, int id, int cadreId, ModelMap modelMap) {

        Set<Integer> cadreDispatchIdSet = new HashSet<>();
        CadrePost cadrePost = cadrePostMapper.selectByPrimaryKey(id);
        if(StringUtils.equalsIgnoreCase(type, "start")){
            if(cadrePost.getStartDispatchCadreId()!=null)
                cadreDispatchIdSet.add(cadrePost.getStartDispatchCadreId());
        }else if(StringUtils.equalsIgnoreCase(type, "end")){
            if(cadrePost.getEndDispatchCadreId()!=null)
                cadreDispatchIdSet.add(cadrePost.getEndDispatchCadreId());
        }
        modelMap.put("cadreDispatchIdSet", cadreDispatchIdSet);

        List<DispatchCadre> dispatchCadres = commonMapper.selectDispatchCadreList(cadreId);
        modelMap.put("dispatchCadres", dispatchCadres);

        modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_dispatch"));
        modelMap.put("wayMap", metaTypeService.metaTypes("mc_dispatch_cadre_way"));
        modelMap.put("procedureMap", metaTypeService.metaTypes("mc_dispatch_cadre_procedure"));
        modelMap.put("postMap", metaTypeService.metaTypes("mc_post"));
        modelMap.put("adminLevelMap", metaTypeService.metaTypes("mc_admin_level"));
        modelMap.put("unitMap", unitService.findAll());
        modelMap.put("dispatchMap", dispatchService.findAll());

        modelMap.put("DISPATCH_CADRE_TYPE_MAP", DispatchConstants.DISPATCH_CADRE_TYPE_MAP);

        return "cadrePost/cadrePost_addDispatchs";
    }

    @RequestMapping(value = "/cadrePost_addDispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_addDispatch(HttpServletRequest request, String type, int id, Integer dispatchCadreId, ModelMap modelMap) {

        CadrePost record = cadrePostMapper.selectByPrimaryKey(id);
        if(StringUtils.equalsIgnoreCase(type, "start")){
            record.setStartDispatchCadreId(dispatchCadreId);
        }else if(StringUtils.equalsIgnoreCase(type, "end")){
            record.setEndDispatchCadreId(dispatchCadreId);
        }

        cadrePostMapper.updateByPrimaryKey(record);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "修改任职级经历%s %s-文号：%s", id, type, dispatchCadreId));
        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadreMainWork_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreMainWork_au(CadreMainWork record, String _startTime, String _postTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_postTime)){
            record.setPostTime(DateUtils.parseDate(_postTime, DateUtils.YYYY_MM_DD));
        }

        record.setIsPositive((record.getIsPositive() == null) ? false : record.getIsPositive());
        record.setIsDouble((record.getIsDouble() == null) ? false : record.getIsDouble());

        if (id == null) {
            cadreMainWorkMapper.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加主职：%s", record.getId()));
        }else {
            cadreMainWorkMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新主职：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadreMainWork_au")
    public String cadreMainWork_au(Integer id,  int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreMainWork cadreMainWork = cadreMainWorkMapper.selectByPrimaryKey(id);
            modelMap.put("cadreMainWork", cadreMainWork);

            modelMap.put("unit", unitService.findAll().get(cadreMainWork.getUnitId()));
            if(cadreMainWork.getDoubleUnitId()!=null)
                modelMap.put("doubleUnit", unitService.findAll().get(cadreMainWork.getDoubleUnitId()));
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);



        return "cadrePost/cadreMainWork_au";
    }

    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadreMainWork_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreMainWork_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadreMainWorkMapper.deleteByPrimaryKey(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除主职：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadreMainWork_addDispatchs")
    public String cadreMainWork_addDispatchs(HttpServletResponse response,
                                             String type,
                                             int id, int cadreId, ModelMap modelMap) {

        Set<Integer> cadreDispatchIdSet = new HashSet<>();
        CadreMainWork cadreMainWork = cadreMainWorkMapper.selectByPrimaryKey(id);
        if(StringUtils.equalsIgnoreCase(type, "radio")){
            if(cadreMainWork.getDispatchCadreId()!=null)
                cadreDispatchIdSet.add(cadreMainWork.getDispatchCadreId());
        }else {
            String dispatchs = cadreMainWork.getDispatchs();
            if (StringUtils.isNotBlank(dispatchs)) {
                for (String str : dispatchs.split(",")) {
                    try {
                        cadreDispatchIdSet.add(Integer.valueOf(str));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        modelMap.put("cadreDispatchIdSet", cadreDispatchIdSet);

        List<DispatchCadre> dispatchCadres = commonMapper.selectDispatchCadreList(cadreId);
        modelMap.put("dispatchCadres", dispatchCadres);

        modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_dispatch"));
        modelMap.put("wayMap", metaTypeService.metaTypes("mc_dispatch_cadre_way"));
        modelMap.put("procedureMap", metaTypeService.metaTypes("mc_dispatch_cadre_procedure"));
        modelMap.put("postMap", metaTypeService.metaTypes("mc_post"));
        modelMap.put("adminLevelMap", metaTypeService.metaTypes("mc_admin_level"));
        modelMap.put("unitMap", unitService.findAll());
        modelMap.put("dispatchMap", dispatchService.findAll());

        modelMap.put("DISPATCH_CADRE_TYPE_MAP", DispatchConstants.DISPATCH_CADRE_TYPE_MAP);

        return "cadrePost/cadreMainWork_addDispatchs";
    }

    @RequestMapping(value = "/cadreMainWork_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreMainWork_addDispatchs(HttpServletRequest request,
                                             int id,
                                             @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        CadreMainWork record = new CadreMainWork();
        record.setId(id);
        record.setDispatchs("-1");
        if (null != ids && ids.length>0){
            record.setDispatchs(StringUtils.join(ids, ","));
        }
        cadreMainWorkMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "修改主职%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/cadreMainWork_addDispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreMainWork_addDispatch(HttpServletRequest request, int id, Integer dispatchCadreId, ModelMap modelMap) {

        CadreMainWork record = cadreMainWorkMapper.selectByPrimaryKey(id);
        record.setDispatchCadreId(dispatchCadreId);

        cadreMainWorkMapper.updateByPrimaryKey(record);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "修改主职%s-现职务始任文号：%s", id, dispatchCadreId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadreSubWork_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreSubWork_au(CadreSubWork record, String _startTime, String _postTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_postTime)){
            record.setPostTime(DateUtils.parseDate(_postTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            cadreSubWorkMapper.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加兼职：%s", record.getId()));
        }else {
            cadreSubWorkMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新兼职：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadreSubWork_au")
    public String cadreSubWork_au(Integer id,  int cadreId,  ModelMap modelMap) {

        if (id != null) {
            CadreSubWork cadreSubWork = cadreSubWorkMapper.selectByPrimaryKey(id);
            modelMap.put("cadreSubWork", cadreSubWork);

            modelMap.put("unit", unitService.findAll().get(cadreSubWork.getUnitId()));
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadrePost/cadreSubWork_au";
    }

    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadreSubWork_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreSubWork_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadreSubWorkMapper.deleteByPrimaryKey(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除兼职：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadreSubWork_addDispatchs")
    public String cadreSubWork_addDispatchs(HttpServletResponse response, String type, int id, int cadreId, ModelMap modelMap) {

        Set<Integer> cadreDispatchIdSet = new HashSet<>();
        CadreSubWork cadreSubWork = cadreSubWorkMapper.selectByPrimaryKey(id);
        if(StringUtils.equalsIgnoreCase(type, "radio")){
            if(cadreSubWork.getDispatchCadreId()!=null)
                cadreDispatchIdSet.add(cadreSubWork.getDispatchCadreId());
        }else {
            String dispatchs = cadreSubWork.getDispatchs();
            if (StringUtils.isNotBlank(dispatchs)) {
                for (String str : dispatchs.split(",")) {
                    try {
                        cadreDispatchIdSet.add(Integer.valueOf(str));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }
        modelMap.put("cadreDispatchIdSet", cadreDispatchIdSet);

        List<DispatchCadre> dispatchCadres = commonMapper.selectDispatchCadreList(cadreId);
        modelMap.put("dispatchCadres", dispatchCadres);

        modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_dispatch"));
        modelMap.put("wayMap", metaTypeService.metaTypes("mc_dispatch_cadre_way"));
        modelMap.put("procedureMap", metaTypeService.metaTypes("mc_dispatch_cadre_procedure"));
        modelMap.put("postMap", metaTypeService.metaTypes("mc_post"));
        modelMap.put("adminLevelMap", metaTypeService.metaTypes("mc_admin_level"));
        modelMap.put("unitMap", unitService.findAll());
        modelMap.put("dispatchMap", dispatchService.findAll());

        modelMap.put("DISPATCH_CADRE_TYPE_MAP", DispatchConstants.DISPATCH_CADRE_TYPE_MAP);

        return "cadrePost/cadreSubWork_addDispatchs";
    }

    @RequestMapping(value = "/cadreSubWork_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreSubWork_addDispatchs(HttpServletRequest request, int id, @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        CadreSubWork record = new CadreSubWork();
        record.setId(id);
        record.setDispatchs("-1");
        if (null != ids && ids.length>0){
            record.setDispatchs(StringUtils.join(ids, ","));
        }
        cadreSubWorkMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "修改兼职%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/cadreSubWork_addDispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreSubWork_addDispatch(HttpServletRequest request, int id, Integer dispatchCadreId, ModelMap modelMap) {

        CadreSubWork record = cadreSubWorkMapper.selectByPrimaryKey(id);
        record.setDispatchCadreId(dispatchCadreId);

        cadreSubWorkMapper.updateByPrimaryKey(record);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "修改兼职%s-现职务始任文号：%s", id, dispatchCadreId));
        return success(FormUtils.SUCCESS);
    }
}
