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
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

//@Controller
public class CadrePostController1 extends BaseController {

   /* private Logger logger = LoggerFactory.getLogger(getClass());

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
            if(cadreMainWorks.size()>0) {
                CadreMainWork cadreMainWork = cadreMainWorks.get(0);
                List<DispatchCadreRelate> cadreMainWorkDispatchCadres =
                        dispatchCadreRelateService.findDispatchCadreRelates(cadreMainWork.getId(),
                                SystemConstants.DISPATCH_CADRE_RELATE_TYPE_MAINWORK);
                modelMap.put("cadreMainWork", cadreMainWork);
                modelMap.put("cadreMainWorkDispatchCadres", cadreMainWorkDispatchCadres); // 已关联任命文件

                // 查找最近的任命文件
                Map<Integer, DispatchCadre> dispatchCadreMap = dispatchCadreService.findAll();
                Dispatch latestDispatch = null;
                for (DispatchCadreRelate cadreMainWorkDispatchCadre : cadreMainWorkDispatchCadres) {
                    Integer dispatchCadreId = cadreMainWorkDispatchCadre.getDispatchCadreId();
                    DispatchCadre dispatchCadre = dispatchCadreMap.get(dispatchCadreId);
                    Dispatch dispatch = dispatchCadre.getDispatch();
                    if(latestDispatch==null) latestDispatch = dispatch;
                    else{
                        if(latestDispatch.getWorkTime().before(dispatch.getWorkTime())){
                            latestDispatch = dispatch;
                        }
                    }
                }
                modelMap.put("latestDispatch", latestDispatch);
            }
        }
        List<CadreSubWork> cadreSubWorks = new ArrayList<>();
        {
            CadreSubWorkExample example = new CadreSubWorkExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadreSubWorks = cadreSubWorkMapper.selectByExample(example);
        }
        modelMap.put("cadrePosts", cadrePosts);

        modelMap.put("cadreSubWorks", cadreSubWorks);

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadrePost/cadrePost_page";
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadrePost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_au(CadrePost record, String _startTime, String _endTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cadrePostMapper.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加任职级经历：%s", record.getId()));
        } else {
            cadrePostMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新任职级经历：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadrePost_au")
    public String cadrePost_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadrePost cadrePost = cadrePostMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePost", cadrePost);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadrePost/cadrePost_au";
    }

    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadrePost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadrePostMapper.deleteByPrimaryKey(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除任职级经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadrePost_addDispatchs")
    public String cadrePost_addDispatchs(HttpServletResponse response, String type, int id, int cadreId, ModelMap modelMap) {

        Set<Integer> cadreDispatchIdSet = new HashSet<>();
        CadrePost cadrePost = cadrePostMapper.selectByPrimaryKey(id);
        if (StringUtils.equalsIgnoreCase(type, "start")) {
            if (cadrePost.getStartDispatchCadreId() != null)
                cadreDispatchIdSet.add(cadrePost.getStartDispatchCadreId());
        } else if (StringUtils.equalsIgnoreCase(type, "end")) {
            if (cadrePost.getEndDispatchCadreId() != null)
                cadreDispatchIdSet.add(cadrePost.getEndDispatchCadreId());
        }
        modelMap.put("cadreDispatchIdSet", cadreDispatchIdSet);

        List<DispatchCadre> dispatchCadres = commonMapper.selectDispatchCadreList(cadreId, null);
        modelMap.put("dispatchCadres", dispatchCadres);

        return "cadre/cadrePost/cadrePost_addDispatchs";
    }

    @RequestMapping(value = "/cadrePost_addDispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_addDispatch(HttpServletRequest request, String type, int id, Integer dispatchCadreId, ModelMap modelMap) {

        CadrePost record = cadrePostMapper.selectByPrimaryKey(id);
        if (StringUtils.equalsIgnoreCase(type, "start")) {
            record.setStartDispatchCadreId(dispatchCadreId);
        } else if (StringUtils.equalsIgnoreCase(type, "end")) {
            record.setEndDispatchCadreId(dispatchCadreId);
        }

        cadrePostMapper.updateByPrimaryKey(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "修改任职级经历%s %s-文号：%s", id, type, dispatchCadreId));
        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadreMainWork_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreMainWork_au(CadreMainWork record, String _startTime, String _postTime, HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsDouble((record.getIsDouble() == null) ? false : record.getIsDouble());

        if (id == null) {
            cadreMainWorkMapper.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加主职：%s", record.getId()));
        } else {
            cadreMainWorkMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新主职：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadreMainWork_au")
    public String cadreMainWork_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreMainWork cadreMainWork = cadreMainWorkMapper.selectByPrimaryKey(id);
            modelMap.put("cadreMainWork", cadreMainWork);

            modelMap.put("unit", unitService.findAll().get(cadreMainWork.getUnitId()));
            if (cadreMainWork.getDoubleUnitId() != null)
                modelMap.put("doubleUnit", unitService.findAll().get(cadreMainWork.getDoubleUnitId()));
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadrePost/cadreMainWork_au";
    }

    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadreMainWork_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreMainWork_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadreMainWorkMapper.deleteByPrimaryKey(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除主职：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadreMainWork_addDispatchs")
    public String cadreMainWork_addDispatchs(HttpServletResponse response,
                                             @RequestParam(defaultValue = "0") boolean single,
                                             String type,
                                             int id, int cadreId, ModelMap modelMap) {


        Set<Integer> dispatchCadreIdSet = new HashSet<>(); // 已关联的干部任免文件ID
        List<DispatchCadre> relateDispatchCadres = new ArrayList<>(); // 已关联的干部任免文件

        List<DispatchCadre> selectDispatchCadres = new ArrayList<>();
        Map<Integer, DispatchCadre> dispatchCadreMap = dispatchCadreService.findAll();
        List<DispatchCadreRelate> dispatchCadreRelates =
                dispatchCadreRelateService.findDispatchCadreRelates(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_MAINWORK);
        for (DispatchCadreRelate dispatchCadreRelate : dispatchCadreRelates) {
            Integer dispatchCadreId = dispatchCadreRelate.getDispatchCadreId();
            DispatchCadre dispatchCadre = dispatchCadreMap.get(dispatchCadreId);
            selectDispatchCadres.add(dispatchCadre);

            dispatchCadreIdSet.add(dispatchCadreId);
            relateDispatchCadres.add(dispatchCadre);
        }

        if(single){ // 现任职务始任文件
            dispatchCadreIdSet.clear();
            relateDispatchCadres.clear();
            CadreMainWork cadreMainWork = cadreMainWorkMapper.selectByPrimaryKey(id);
            if(cadreMainWork.getDispatchCadreId()!=null) {
                dispatchCadreIdSet.add(cadreMainWork.getDispatchCadreId());
                relateDispatchCadres.add(dispatchCadreMap.get(cadreMainWork.getDispatchCadreId()));
            }
        }
        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);

        if (relateDispatchCadres.size() == 0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");
            if(single){
                modelMap.put("dispatchCadres", selectDispatchCadres);
            }else {
                List<DispatchCadre> dispatchCadres = commonMapper.selectDispatchCadreList(cadreId, SystemConstants.DISPATCH_CADRE_TYPE_APPOINT);
                modelMap.put("dispatchCadres", dispatchCadres);
            }
        } else {
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        return "cadre/cadrePost/cadreMainWork_addDispatchs";
    }

    @RequestMapping(value = "/cadreMainWork_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreMainWork_addDispatchs(HttpServletRequest request,
                                             int id,
                                             @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        dispatchCadreRelateService.updateDispatchCadreRelates(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_MAINWORK, ids);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "修改主职%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/cadreMainWork_addDispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreMainWork_addDispatch(HttpServletRequest request, int id, Integer dispatchCadreId, ModelMap modelMap) {

        CadreMainWork record = cadreMainWorkMapper.selectByPrimaryKey(id);
        record.setDispatchCadreId(dispatchCadreId);

        cadreMainWorkMapper.updateByPrimaryKey(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "修改主职%s-现职务始任文号：%s", id, dispatchCadreId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadreSubWork_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreSubWork_au(CadreSubWork record, String _startTime, String _postTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_startTime)) {
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_postTime)) {
            record.setPostTime(DateUtils.parseDate(_postTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            cadreSubWorkMapper.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加兼职：%s", record.getId()));
        } else {
            cadreSubWorkMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新兼职：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadreSubWork_au")
    public String cadreSubWork_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreSubWork cadreSubWork = cadreSubWorkMapper.selectByPrimaryKey(id);
            modelMap.put("cadreSubWork", cadreSubWork);

            modelMap.put("unit", unitService.findAll().get(cadreSubWork.getUnitId()));
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadrePost/cadreSubWork_au";
    }

    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadreSubWork_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreSubWork_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadreSubWorkMapper.deleteByPrimaryKey(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除兼职：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadreSubWork_addDispatchs")
    public String cadreSubWork_addDispatchs(HttpServletResponse response, String type, int id, int cadreId, ModelMap modelMap) {


        Set<Integer> dispatchCadreIdSet = new HashSet<>(); // 已关联的干部任免文件ID
        List<DispatchCadre> relateDispatchCadres = new ArrayList<>(); // 已关联的干部任免文件

        List<DispatchCadre> selectDispatchCadres = new ArrayList<>();
        Map<Integer, DispatchCadre> dispatchCadreMap = dispatchCadreService.findAll();
        List<DispatchCadreRelate> dispatchCadreRelates =
                dispatchCadreRelateService.findDispatchCadreRelates(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_MAINWORK);
        for (DispatchCadreRelate dispatchCadreRelate : dispatchCadreRelates) {
            Integer dispatchCadreId = dispatchCadreRelate.getDispatchCadreId();
            DispatchCadre dispatchCadre = dispatchCadreMap.get(dispatchCadreId);
            selectDispatchCadres.add(dispatchCadre);

            dispatchCadreIdSet.add(dispatchCadreId);
            relateDispatchCadres.add(dispatchCadre);
        }

        if(single){ // 现任职务始任文件
            dispatchCadreIdSet.clear();
            relateDispatchCadres.clear();
            CadreMainWork cadreMainWork = cadreMainWorkMapper.selectByPrimaryKey(id);
            if(cadreMainWork.getDispatchCadreId()!=null) {
                dispatchCadreIdSet.add(cadreMainWork.getDispatchCadreId());
                relateDispatchCadres.add(dispatchCadreMap.get(cadreMainWork.getDispatchCadreId()));
            }
        }
        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);

        if (relateDispatchCadres.size() == 0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");
            if(single){
                modelMap.put("dispatchCadres", selectDispatchCadres);
            }else {
                List<DispatchCadre> dispatchCadres = commonMapper.selectDispatchCadreList(cadreId, SystemConstants.DISPATCH_CADRE_TYPE_APPOINT);
                modelMap.put("dispatchCadres", dispatchCadres);
            }
        } else {
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        *//*Set<Integer> cadreDispatchIdSet = new HashSet<>();
        CadreSubWork cadreSubWork = cadreSubWorkMapper.selectByPrimaryKey(id);
        if (StringUtils.equalsIgnoreCase(type, "radio")) {
            if (cadreSubWork.getDispatchCadreId() != null)
                cadreDispatchIdSet.add(cadreSubWork.getDispatchCadreId());
        } else {
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

        List<DispatchCadre> dispatchCadres = commonMapper.selectDispatchCadreList(cadreId, null);
        modelMap.put("dispatchCadres", dispatchCadres);

        modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_dispatch"));*//*

        return "cadre/cadrePost/cadreSubWork_addDispatchs";
    }

    @RequestMapping(value = "/cadreSubWork_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreSubWork_addDispatchs(HttpServletRequest request, int id, @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        CadreSubWork record = new CadreSubWork();
        record.setId(id);
        record.setDispatchs("-1");
        if (null != ids && ids.length > 0) {
            record.setDispatchs(StringUtils.join(ids, ","));
        }
        cadreSubWorkMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "修改兼职%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/cadreSubWork_addDispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreSubWork_addDispatch(HttpServletRequest request, int id, Integer dispatchCadreId, ModelMap modelMap) {

        CadreSubWork record = cadreSubWorkMapper.selectByPrimaryKey(id);
        record.setDispatchCadreId(dispatchCadreId);

        cadreSubWorkMapper.updateByPrimaryKey(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "修改兼职%s-现职务始任文号：%s", id, dispatchCadreId));
        return success(FormUtils.SUCCESS);
    }*/
}
