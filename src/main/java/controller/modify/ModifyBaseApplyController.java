package controller.modify;

import controller.BaseController;
import domain.modify.ModifyBaseApply;
import domain.modify.ModifyBaseApplyExample;
import domain.sys.SysUserView;
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
import shiro.ShiroHelper;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/11/27.
 */
@Controller
public class ModifyBaseApplyController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("modifyBaseApply:list")
    @RequestMapping("/modifyBaseApply")
    public String modifyBaseApply(
            Integer userId,
            @RequestParam(required = false, defaultValue = "1")Byte status, // 1 干部信息修改申请 2 审核完成 3 删除
            ModelMap modelMap) {

        if(userId!=null)
            modelMap.put("sysUser", sysUserService.findById(userId));
        modelMap.put("status", status);
        return "modify/modifyBaseApply/modifyBaseApply_page";
    }

    @RequiresPermissions("modifyBaseApply:list")
    @RequestMapping("/modifyBaseApply_data")
    public void modifyBaseApply_data(@CurrentUser SysUserView loginUser, HttpServletResponse response,
                           @RequestParam(required = false, defaultValue = "1")Byte status,
                           Integer userId,
                           Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ModifyBaseApplyExample example = new ModifyBaseApplyExample();
        ModifyBaseApplyExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("check_time desc");

        if(ShiroHelper.hasRole(SystemConstants.ROLE_ADMIN)){
            if (userId != null) {
                criteria.andUserIdEqualTo(userId);
            }
        }else{ // 干部只能看到自己的
            criteria.andUserIdEqualTo(loginUser.getId());
        }

        if(status==1){ // 待审核、部分审核
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY);
            statusList.add(SystemConstants.MODIFY_BASE_APPLY_STATUS_PART_CHECK);
            criteria.andStatusIn(statusList);
        }else if(status==2){ // 审核完成
            criteria.andStatusEqualTo(SystemConstants.MODIFY_BASE_APPLY_STATUS_ALL_CHECK);
        }else if(status==3){ // 已删除
            criteria.andStatusEqualTo(SystemConstants.MODIFY_BASE_APPLY_STATUS_DELETE);
        }else{
            criteria.andStatusIsNull();
        }

        int count = modifyBaseApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ModifyBaseApply> records = modifyBaseApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    // 假删除
    @RequiresPermissions("modifyBaseApply:fakeDel")
    @RequestMapping(value = "/modifyBaseApply_fakeDel", method = RequestMethod.POST)
    @ResponseBody
    public Map modifyBaseApply_fakeDel(@CurrentUser SysUserView loginUser,
                      @RequestParam(value = "ids[]") Integer[] ids,
                      HttpServletRequest request){

        if (null != ids && ids.length>0){
            modifyBaseApplyService.fakeDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量【假】删除基本信息修改申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    // 真删除
    @RequiresPermissions("modifyBaseApply:del")
    @RequestMapping(value = "/modifyBaseApply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map modifyBaseApply_batchDel(@RequestParam(value = "ids[]") Integer[] ids,
                                         HttpServletRequest request) {

        if (null != ids && ids.length > 0) {
            modifyBaseApplyService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除基本信息修改申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
