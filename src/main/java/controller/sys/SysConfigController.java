package controller.sys;

import controller.BaseController;
import domain.SysConfig;
import domain.SysConfigExample;
import domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by fafa on 2016/6/17.
 */
@Controller
public class SysConfigController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sysConfig:list")
    @RequestMapping("/sysConfig")
    public String sysConfig() {

        return "index";
    }

    @RequiresPermissions("sysConfig:list")
    @RequestMapping("/sysConfig_page")
    public String sysConfig_page() {

        return "sys/sysConfig/sysConfig_page";
    }
    @RequiresPermissions("sysConfig:list")
    @RequestMapping("/sysConfig_data")
    @ResponseBody
    public void sysConfig_data(@CurrentUser SysUser loginUser,
                               HttpServletResponse response,
                               String name, String code,
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysConfigExample example = new SysConfigExample();
        SysConfigExample.Criteria criteria = example.createCriteria();

        if (!SecurityUtils.getSubject().hasRole(SystemConstants.ROLE_ADMIN)) {

            Set<Integer> roleIdSet = sysUserService.getUserRoleIdSet(loginUser.getRoleIds());
            criteria.andRoleIdIn(new ArrayList<>(roleIdSet));
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }

        int count = sysConfigMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysConfig> SysConfigs = sysConfigMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", SysConfigs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(SysConfig.class, SysConfigMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
    @RequiresPermissions("SysConfig:edit")
    @RequestMapping("/sysConfig_au")
    public String sysConfig_note_au(String code, Integer id, ModelMap modelMap) {

        SysConfig SysConfig = null;
        if(id !=null) // 以id为主
            SysConfig = sysConfigMapper.selectByPrimaryKey(id);
        else
            SysConfig = sysConfigService.codeKeyMap().get(code);
        modelMap.put("sysConfig", SysConfig);

        return "sys/sysConfig/sysConfig_au";
    }

    @RequiresPermissions("SysConfig:edit")
    @RequestMapping(value = "/sysConfig_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysConfig_note_au(SysConfig record, ModelMap modelMap) {

        if (record.getId() == null) {
            if (StringUtils.isBlank(record.getCode()))
                record.setCode(sysConfigService.genCode());
            sysConfigService.insertSelective(record);
        } else {
            sysConfigService.updateByPrimaryKeySelective(record);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysConfig:del")
    @RequestMapping(value = "/sysConfig_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            sysConfigService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除配置：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "/sysConfigRole", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysConfigRole(int id,
                                Integer roleId,
                                HttpServletRequest request) {

        if (roleId == null) {
            roleId = -1;
        }
        sysConfigService.updateRoles(id, roleId);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新元数据所属角色 %s, %s", id, roleId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("admin")
    @RequestMapping("/sysConfigRole")
    public String sysConfigRole(Integer id, ModelMap modelMap) throws IOException {

        Set<Integer> selectIdSet = new HashSet<Integer>();
        if (id != null) {

            SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(id);
            selectIdSet.add(sysConfig.getRoleId());
            modelMap.put("sysConfig", sysConfig);
        }

        TreeNode tree = sysRoleService.getTree(selectIdSet, false);
        modelMap.put("tree", JSONUtils.toString(tree));

        return "sys/sysConfig/sysConfigRole";
    }

    @RequestMapping("/sc_content")
    public String sc_content(String code, Integer id, ModelMap modelMap) {

        SysConfig SysConfig = null;
        if(id !=null) // 以id为主
            SysConfig = sysConfigMapper.selectByPrimaryKey(id);
        else
            SysConfig = sysConfigService.codeKeyMap().get(code);

        modelMap.put("sysConfig", SysConfig);

        return "sys/sysConfig/sc_content";
    }
}
