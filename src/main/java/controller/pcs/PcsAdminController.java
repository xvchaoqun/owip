package controller.pcs;

import controller.BaseController;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsAdminExample;
import domain.pcs.PcsAdminExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsAdmin:list")
    @RequestMapping("/pcsAdmin")
    public String pcsAdmin(Integer partyId, Integer userId, ModelMap modelMap) {

        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if(partyId!=null){
            modelMap.put("party", partyService.findAll().get(partyId));
        }

        return "pcs/pcsAdmin/pcsAdmin_page";
    }

    @RequiresPermissions("pcsAdmin:list")
    @RequestMapping("/pcsAdmin_data")
    public void pcsAdmin_data(HttpServletResponse response,
                              Integer partyId,
                              Integer userId,
                              Byte type,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsAdminExample example = new PcsAdminExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("party_id asc, type asc");

        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(type != null){
            criteria.andTypeEqualTo(type);
        }

        /*if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsAdmin_export(example, response);
            return;
        }*/

        long count = pcsAdminMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsAdmin> records = pcsAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsAdmin.class, pcsAdminMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping(value = "/pcsAdmin_sync", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsAdmin_sync() {

        pcsAdminService.syncCurrentPcsAdmin();
        logger.info(addLog(SystemConstants.LOG_ADMIN, "同步党代会分党委管理员"));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping("/pcsAdmin_au")
    public String pcsAdmin_au(Integer id, ModelMap modelMap) {

        if(id!=null) {
            PcsAdmin pcsAdmin = pcsAdminMapper.selectByPrimaryKey(id);
            modelMap.put("pcsAdmin", pcsAdmin);
            SysUserView sysUser = sysUserService.findById(pcsAdmin.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "pcs/pcsAdmin/pcsAdmin_au";
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping(value = "/pcsAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsAdmin_add(PcsAdmin record,String mobile, HttpServletRequest request) {

        if(StringUtils.isBlank(mobile) || !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            return failed("手机号码有误："+ mobile);
        }

        if (pcsAdminService.idDuplicate(record.getId(), record.getUserId())) {
            return failed("该用户已经是党代会管理员");
        }

        pcsAdminService.addOrUpdate(record, mobile);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加/修改党代会分党委管理员：%s-%s",
                JSONUtils.toString(record, false), mobile));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping("/pcsAdmin_add")
    public String pcsAdmin_add(ModelMap modelMap) {

        modelMap.put("pcsConfig", pcsConfigService.getCurrentPcsConfig());

        return "pcs/pcsAdmin/pcsAdmin_add";
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping(value = "/pcsAdmin_msg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsAdmin_msg(byte type, // type=1 两委委员 type=2 党代表
                               byte stage,
                               byte adminType, String mobile, String msg, HttpServletRequest request) {

        if(StringUtils.isNotBlank(mobile) && !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            return failed("手机号码有误："+ mobile);
        }

        Map<String, Integer> result = pcsAdminService.sendMsg(type, stage, adminType, mobile, msg);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "发送短信给分党委管理员：%s-%s", msg, mobile));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("totalCount", result.get("total"));
        resultMap.put("successCount", result.get("success"));
        return resultMap;
    }

    @RequiresPermissions("pcsAdmin:edit")
    @RequestMapping("/pcsAdmin_msg")
    public String pcsAdmin_msg() {

        return "pcs/pcsAdmin/pcsAdmin_msg";
    }

    @RequiresPermissions("pcsAdmin:del")
    @RequestMapping(value = "/pcsAdmin_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pcsAdminService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除党代会分党委管理员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
