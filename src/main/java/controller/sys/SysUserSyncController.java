package controller.sys;

import controller.BaseController;
import domain.sys.SysUserSync;
import domain.sys.SysUserSyncExample;
import domain.sys.SysUserSyncExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.SysUserSyncMixin;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SysUserSyncController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 同步学校信息
    @RequestMapping("/sync_user")
    @ResponseBody
    public Map sync_user(int type) {
        switch (type){
            case SystemConstants.SYNC_TYPE_JZG:
                sysUserSyncService.syncJZG(false);
                break;
            case SystemConstants.SYNC_TYPE_YJS:
                sysUserSyncService.syncYJS(false);
                break;
            case SystemConstants.SYNC_TYPE_BKS:
                sysUserSyncService.syncBks(false);
                break;
            case SystemConstants.SYNC_TYPE_ABROAD:
                sysUserSyncService.syncAbroad(false);
                break;
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/sync_status")
    @ResponseBody
    public Map sync_status() {

        Map<String, Object> map = success(FormUtils.SUCCESS);
        map.put("lastSyncIsNotStop-" + SystemConstants.SYNC_TYPE_JZG, sysUserSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_JZG));
        map.put("lastSyncIsNotStop-" + SystemConstants.SYNC_TYPE_YJS, sysUserSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_YJS));
        map.put("lastSyncIsNotStop-" + SystemConstants.SYNC_TYPE_BKS, sysUserSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_BKS));
        map.put("lastSyncIsNotStop-" + SystemConstants.SYNC_TYPE_ABROAD, sysUserSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_ABROAD));
        return map;
    }

    @RequiresPermissions("sysUserSync:list")
    @RequestMapping("/sysUserSync")
    public String sysUserSync() {

        return "index";
    }
    @RequiresPermissions("sysUserSync:list")
    @RequestMapping("/sysUserSync_page")
    public String sysUserSync_page(HttpServletResponse response,
                                   @SortParam(required = false, defaultValue = "start_time", tableName = "sys_user_sync") String sort,
                                   @OrderParam(required = false, defaultValue = "desc") String order,
                                   Integer userId,
                                   Byte type,
                                   Integer pageSize, Integer pageNo, ModelMap modelMap) {

        return "sys/sysUserSync/sysUserSync_page";
    }
    @RequiresPermissions("sysUserSync:list")
    @RequestMapping("/sysUserSync_data")
    @ResponseBody
    public void sysUserSync_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "start_time", tableName = "sys_user_sync") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                     Byte type,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysUserSyncExample example = new SysUserSyncExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        int count = sysUserSyncMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserSync> sysUserSyncs = sysUserSyncMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", sysUserSyncs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(SysUserSync.class, SysUserSyncMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;

    }

    @RequestMapping(value = "/sync_stop", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserSync_au(int id,  HttpServletRequest request) {

        SysUserSync record = new SysUserSync();
        record.setId(id);
        record.setIsStop(true);
        record.setEndTime(new Date());
        record.setAutoStop(false);
        sysUserSyncService.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "结束账号同步：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUserSync:edit")
    @RequestMapping("/sysUserSync_au")
    public String sysUserSync_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SysUserSync sysUserSync = sysUserSyncMapper.selectByPrimaryKey(id);
            modelMap.put("sysUserSync", sysUserSync);
        }
        return "sys/sysUserSync/sysUserSync_au";
    }

    @RequiresPermissions("sysUserSync:del")
    @RequestMapping(value = "/sysUserSync_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysUserSync_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            sysUserSyncService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除账号同步日志：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysUserSync:del")
    @RequestMapping(value = "/sysUserSync_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            sysUserSyncService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除账号同步日志：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
