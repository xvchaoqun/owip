package controller.modify;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.modify.ModifyCadreAuth;
import domain.modify.ModifyCadreAuthExample;
import domain.modify.ModifyCadreAuthExample.Criteria;
import domain.sys.SysUserView;
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
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ModifyCadreAuthController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("modifyCadreAuth:list")
    @RequestMapping("/modifyCadreAuth")
    public String modifyCadreAuth() {

        return "index";
    }
    @RequiresPermissions("modifyCadreAuth:list")
    @RequestMapping("/modifyCadreAuth_page")
    public String modifyCadreAuth_page(Integer cadreId, ModelMap modelMap) {

        if(cadreId!=null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            modelMap.put("sysUser", cadre.getUser());
        }

        return "modify/modifyCadreAuth/modifyCadreAuth_page";
    }

    @RequiresPermissions("modifyCadreAuth:list")
    @RequestMapping("/modifyCadreAuth_data")
    public void modifyCadreAuth_data(HttpServletResponse response,
                                    Integer cadreId, Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ModifyCadreAuthExample example = new ModifyCadreAuthExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("add_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        int count = modifyCadreAuthMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ModifyCadreAuth> modifyCadreAuths = modifyCadreAuthMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", modifyCadreAuths);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    @RequiresPermissions("modifyCadreAuth:edit")
    @RequestMapping(value = "/modifyCadreAuth_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_modifyCadreAuth_au(@CurrentUser SysUserView loginUser, String _startTime, String _endTime,
                                     ModifyCadreAuth record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD));
        record.setIsUnlimited(BooleanUtils.isTrue(record.getIsUnlimited()));
        record.setAddTime(new Date());
        record.setAddUserId(loginUser.getUserId());
        record.setAddIp(IpUtils.getRealIp(request));

        if (id == null) {
            modifyCadreAuthService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "添加干部信息修改权限设置：%s", record.getId()));
        } else {
            modifyCadreAuthService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "更新干部信息修改权限设置：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("modifyCadreAuth:edit")
    @RequestMapping("/modifyCadreAuth_au")
    public String modifyCadreAuth_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ModifyCadreAuth modifyCadreAuth = modifyCadreAuthMapper.selectByPrimaryKey(id);
            modelMap.put("modifyCadreAuth", modifyCadreAuth);
            Cadre cadre = cadreService.findAll().get(modifyCadreAuth.getCadreId());
            modelMap.put("cadre", cadre);
            modelMap.put("sysUser", cadre.getUser());
        }
        return "modify/modifyCadreAuth/modifyCadreAuth_au";
    }

    // 批量添加
    @RequiresPermissions("modifyCadreAuth:edit")
    @RequestMapping("/modifyCadreAuth_batchAdd")
    public String modifyCadreAuth_batchAdd() {

        return "modify/modifyCadreAuth/modifyCadreAuth_batchAdd";
    }

    // 批量添加-选择干部
    @RequiresPermissions("modifyCadreAuth:edit")
    @RequestMapping("/modifyCadreAuth/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree() throws IOException {

        TreeNode tree = modifyCadreAuthService.getTree();
        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("modifyCadreAuth:edit")
    @RequestMapping(value = "/modifyCadreAuth_batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Map do_modifyCadreAuth_batchAdd(@RequestParam(value = "cadreIds[]", required = false) Integer[] cadreIds,
                                           String _startTime, String _endTime, Boolean isUnlimited) {

        Date start = DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD);
        Date end = DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD);
        isUnlimited = BooleanUtils.isTrue(isUnlimited);

        modifyCadreAuthService.batchAdd(cadreIds, start, end, isUnlimited);
        logger.info(addLog( SystemConstants.LOG_ADMIN, "批量添加干部信息修改权限设置：%s", StringUtils.join(cadreIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("modifyCadreAuth:del")
    @RequestMapping(value = "/modifyCadreAuth_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_modifyCadreAuth_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            modifyCadreAuthService.del(id);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "删除干部信息修改权限设置：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("modifyCadreAuth:del")
    @RequestMapping(value = "/modifyCadreAuth_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            modifyCadreAuthService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "批量删除干部信息修改权限设置：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
