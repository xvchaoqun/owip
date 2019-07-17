package controller.ext;

import controller.BaseController;
import domain.ext.*;
import domain.sys.SysSync;
import domain.sys.SysSyncExample;
import domain.sys.SysSyncExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ext.ExtBksImport;
import service.ext.ExtJzgImport;
import service.ext.ExtRetireSalaryImport;
import service.ext.ExtYjsImport;
import sys.constants.LogConstants;
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
public class SyncController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ExtJzgImport extJzgImport;
    @Autowired
    private ExtBksImport extBksImport;
    @Autowired
    private ExtYjsImport extYjsImport;
    @Autowired
    private ExtRetireSalaryImport extRetireSalaryImport;

    // 离退休人员党费计算基数
    @RequiresPermissions("sysSync:salary")
    @RequestMapping("/sync_retire_salary")
    @ResponseBody
    public String sync_retire_salary(String rq) {

        extRetireSalaryImport.excute(rq);
        return "success";
    }

    // 同步学校信息（系统不存在账号插入，已存在的更新）
    @RequiresPermissions("sysSync:user")
    @RequestMapping("/sync_user_byCode")
    public String sync_user_byCode() {

        return "sys/sysSync/sync_user_byCode";
    }

    @RequiresPermissions("sysSync:user")
    @RequestMapping(value = "/sync_user_byCode", method = RequestMethod.POST)
    @ResponseBody
    public Map sync_user_byCode(String code) {

        logger.info(addLog(LogConstants.LOG_ADMIN, "同步账号信息：%s", code));

        {
            extJzgImport.byCode(code);

            ExtJzgExample example = new ExtJzgExample();
            example.createCriteria().andZghEqualTo(code);
            List<ExtJzg> extJzges = extJzgMapper.selectByExample(example);
            if(extJzges.size()==1){
                syncService.syncExtJzg(extJzges.get(0));
                return success(FormUtils.SUCCESS);
            }
        }
        {
                extYjsImport.byCode(code);

                ExtYjsExample example = new ExtYjsExample();
                example.createCriteria().andXhEqualTo(code);
                List<ExtYjs> extYjses = extYjsMapper.selectByExample(example);
                if(extYjses.size()==1){
                    syncService.sysExtYjs(extYjses.get(0));
                    return success(FormUtils.SUCCESS);
                }
        }
        {
                extBksImport.byCode(code);
                ExtBksExample example = new ExtBksExample();
                example.createCriteria().andXhEqualTo(code);
                List<ExtBks> extBkses = extBksMapper.selectByExample(example);
                if(extBkses.size()==1){
                    syncService.syncExtBks(extBkses.get(0));
                    return success(FormUtils.SUCCESS);
                }
        }

        return failed("学工号不存在");
    }

    // 同步学校用户信息（系统已存在该账号）
    @RequiresPermissions("sysSync:user")
    @RequestMapping(value = "/sync_user", method = RequestMethod.POST)
    @ResponseBody
    public Map sync_user(Integer userId) {

        SysUserView sysUser = sysUserService.findById(userId);
        String code = sysUser.getCode();
        if (sysUser.getType() == SystemConstants.USER_TYPE_JZG) {
            extJzgImport.byCode(code);

            ExtJzgExample example = new ExtJzgExample();
            example.createCriteria().andZghEqualTo(code);
            List<ExtJzg> extJzges = extJzgMapper.selectByExample(example);
            if(extJzges.size()==1) syncService.syncExtJzg(extJzges.get(0));
        }else {
            if (sysUser.getType() == SystemConstants.USER_TYPE_YJS) {
                extYjsImport.byCode(code);

                ExtYjsExample example = new ExtYjsExample();
                example.createCriteria().andXhEqualTo(code);
                List<ExtYjs> extYjses = extYjsMapper.selectByExample(example);
                if(extYjses.size()==1) syncService.sysExtYjs(extYjses.get(0));
            }else {

                extBksImport.byCode(code);
                ExtBksExample example = new ExtBksExample();
                example.createCriteria().andXhEqualTo(code);
                List<ExtBks> extBkses = extBksMapper.selectByExample(example);
                if(extBkses.size()==1) syncService.syncExtBks(extBkses.get(0));
            }
        }
        return success(FormUtils.SUCCESS);
    }

    // 同步学校信息（批量）
    @RequiresPermissions("sysSync:batch")
    @RequestMapping("/sync_user_batch")
    @ResponseBody
    public Map sync_user_batch(int type) {
        switch (type){
            case SystemConstants.SYNC_TYPE_JZG:
                syncService.syncAllJZG(false);
                break;
            case SystemConstants.SYNC_TYPE_YJS:
                syncService.syncAllYJS(false);
                break;
            case SystemConstants.SYNC_TYPE_BKS:
                syncService.syncAllBks(false);
                break;
            case SystemConstants.SYNC_TYPE_ABROAD:
                syncService.syncAllAbroad(false);
                break;
            case SystemConstants.SYNC_TYPE_RETIRE_SALARY:
                syncService.syncRetireSalary(false);
                break;
            case SystemConstants.SYNC_TYPE_JZG_SALARY:
                syncService.syncJzgSalary(false);
                break;
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysSync:list")
    @RequestMapping("/sync_status")
    @ResponseBody
    public Map sync_status() {

        Map<String, Object> map = success(FormUtils.SUCCESS);
        map.put("lastSyncIsNotStop-" + SystemConstants.SYNC_TYPE_JZG, syncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_JZG));
        map.put("lastSyncIsNotStop-" + SystemConstants.SYNC_TYPE_YJS, syncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_YJS));
        map.put("lastSyncIsNotStop-" + SystemConstants.SYNC_TYPE_BKS, syncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_BKS));
        map.put("lastSyncIsNotStop-" + SystemConstants.SYNC_TYPE_ABROAD, syncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_ABROAD));
        return map;
    }

    @RequiresPermissions("sysSync:list")
    @RequestMapping("/sysSync")
    public String sysSync() {

        return "sys/sysSync/sysSync_page";
    }
    @RequiresPermissions("sysSync:list")
    @RequestMapping("/sysSync_data")
    @ResponseBody
    public void sysSync_data(HttpServletResponse response,
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

        SysSyncExample example = new SysSyncExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("start_time desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        long count = sysSyncMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysSync> sysSyncs = sysSyncMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", sysSyncs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;

    }

    @RequiresPermissions("sysSync:edit")
    @RequestMapping(value = "/sync_stop", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysSync_au(int id,  HttpServletRequest request) {

        SysSync record = new SysSync();
        record.setId(id);
        record.setIsStop(true);
        record.setEndTime(new Date());
        record.setAutoStop(false);
        syncService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "结束账号同步：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysSync:edit")
    @RequestMapping("/sysSync_au")
    public String sysSync_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SysSync sysSync = sysSyncMapper.selectByPrimaryKey(id);
            modelMap.put("sysSync", sysSync);
        }
        return "sys/sysSync/sysSync_au";
    }

    @RequiresPermissions("sysSync:del")
    @RequestMapping(value = "/sysSync_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysSync_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            syncService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除账号同步日志：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysSync:del")
    @RequestMapping(value = "/sysSync_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            syncService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除账号同步日志：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
