package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreAdminLevel;
import domain.cadre.CadreView;
import domain.dispatch.DispatchCadre;
import domain.sys.SysUserView;
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
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fafa on 2016/6/30.
 */
@Controller
public class CadreAdminLevelController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreAdminLevel:edit")
    @RequestMapping(value = "/cadreAdminLevel_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdminLevel_au(CadreAdminLevel record, String _startTime, String _endTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cadreAdminLevelMapper.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加任职级经历：%s", record.getId()));
        } else {
            cadreAdminLevelMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新任职级经历：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreAdminLevel:edit")
    @RequestMapping("/cadreAdminLevel_au")
    public String cadreAdminLevel_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreAdminLevel cadreAdminLevel = cadreAdminLevelMapper.selectByPrimaryKey(id);
            modelMap.put("cadreAdminLevel", cadreAdminLevel);
        }
        CadreView cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreAdminLevel/cadreAdminLevel_au";
    }

/*    @RequiresPermissions("cadreAdminLevel:del")
    @RequestMapping(value = "/cadreAdminLevel_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdminLevel_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadreAdminLevelMapper.deleteByPrimaryKey(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除任职级经历：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreAdminLevel:del")
    @RequestMapping(value = "/cadreAdminLevel_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadreAdminLevelService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除任职级经历：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("cadreAdminLevel:edit")
    @RequestMapping("/cadreAdminLevel_addDispatchs")
    public String cadreAdminLevel_addDispatchs(HttpServletResponse response, String cls,  String type, int id, int cadreId, ModelMap modelMap) {

        Byte dispatchCadreType = null;

        Set<Integer> dispatchCadreIdSet = new HashSet<>(); // 已选择的干部发文ID
        List<DispatchCadre> relateDispatchCadres = new ArrayList<>();
        Map<Integer, DispatchCadre> dispatchCadreMap = dispatchCadreService.findAll();
        CadreAdminLevel cadreAdminLevel = cadreAdminLevelMapper.selectByPrimaryKey(id);
        if (StringUtils.equalsIgnoreCase(cls, "start")) {

            dispatchCadreType = SystemConstants.DISPATCH_CADRE_TYPE_APPOINT;

            if (cadreAdminLevel.getStartDispatchCadreId() != null) {
                Integer startDispatchCadreId = cadreAdminLevel.getStartDispatchCadreId();
                dispatchCadreIdSet.add(startDispatchCadreId);
                relateDispatchCadres.add(dispatchCadreMap.get(startDispatchCadreId));
            }
        } else if (StringUtils.equalsIgnoreCase(cls, "end")) {

            //dispatchCadreType = SystemConstants.DISPATCH_CADRE_TYPE_DISMISS;
            if (cadreAdminLevel.getEndDispatchCadreId() != null) {
                Integer endDispatchCadreId = cadreAdminLevel.getEndDispatchCadreId();
                dispatchCadreIdSet.add(endDispatchCadreId);
                relateDispatchCadres.add(dispatchCadreMap.get(endDispatchCadreId));
            }
            dispatchCadreType = null; // 结束文件不限制，可以在全部干部发文中选择
        }else{
            throw new IllegalArgumentException("cls 参数有误");
        }
        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);


        if(relateDispatchCadres.size()==0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");
            List<DispatchCadre> dispatchCadres = iDispatchMapper.selectDispatchCadreList(cadreId, dispatchCadreType);
            modelMap.put("dispatchCadres", dispatchCadres);
            if (StringUtils.equalsIgnoreCase(cls, "start")) { // 只有始任文件有限制
                // 已被选
                Set<Integer> otherDispatchCadreRelateSet = cadreAdminLevelService.findOtherDispatchCadreRelateSet(cadreId, id);
                modelMap.put("otherDispatchCadreRelateSet", otherDispatchCadreRelateSet);
            }
        }else{
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        return "cadre/cadreAdminLevel/cadreAdminLevel_addDispatchs";
    }

    @RequiresPermissions("cadreAdminLevel:edit")
    @RequestMapping(value = "/cadreAdminLevel_addDispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdminLevel_addDispatch(HttpServletRequest request, String cls, int id, Integer dispatchCadreId, ModelMap modelMap) {

        CadreAdminLevel record = cadreAdminLevelMapper.selectByPrimaryKey(id);
            if (StringUtils.equalsIgnoreCase(cls, "start")) {
                record.setStartDispatchCadreId(dispatchCadreId);
            } else if (StringUtils.equalsIgnoreCase(cls, "end")) {
                record.setEndDispatchCadreId(dispatchCadreId);
            } else {
                throw new IllegalArgumentException("cls 参数错误");
            }

            cadreAdminLevelMapper.updateByPrimaryKey(record); // 可以删除
            logger.info(addLog(SystemConstants.LOG_ADMIN, "修改任职级经历%s %s-文号：%s", id, cls, dispatchCadreId));
        return success(FormUtils.SUCCESS);
    }
}
