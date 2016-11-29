package controller.modify;

import controller.BaseController;
import domain.modify.ModifyBaseApply;
import domain.modify.ModifyBaseItem;
import domain.modify.ModifyBaseItemExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/11/27.
 */
@Controller
public class ModifyBaseItemController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("modifyBaseItem:list")
    @RequestMapping("/modifyBaseItem")
    public String modifyBaseItem() {

        return "index";
    }

    @RequiresPermissions("modifyBaseItem:list")
    @RequestMapping("/modifyBaseItem_page")
    public String modifyBaseItem_page(ModelMap modelMap) {
        
        return "modifyBaseItem/modifyBaseItem_page";
    }

    @RequiresPermissions("modifyBaseItem:list")
    @RequestMapping("/modifyBaseItem_data")
    public void modifyBaseItem_data(HttpServletResponse response,
                           Integer applyId,
                           Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ModifyBaseItemExample example = new ModifyBaseItemExample();
        ModifyBaseItemExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("check_time desc");

        criteria.andApplyIdEqualTo(applyId);

        int count = modifyBaseItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ModifyBaseItem> records = modifyBaseItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    // 审核
    @RequiresPermissions("modifyBaseItem:approval")
    @RequestMapping("/modifyBaseItem_approval")
    public String modifyBaseItem_approval(int id, ModelMap modelMap) {

        ModifyBaseItem mbi = modifyBaseItemMapper.selectByPrimaryKey(id);
        modelMap.put("record", mbi);
        ModifyBaseApply mba = modifyBaseApplyMapper.selectByPrimaryKey(mbi.getApplyId());
        Integer userId = mba.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));
        modelMap.put("cadre", cadreService.findByUserId(userId));
        return "modify/modifyBaseItem/modifyBaseItem_approval";
    }

    @RequiresPermissions("modifyBaseItem:approval")
    @RequestMapping(value = "/modifyBaseItem_approval", method = RequestMethod.POST)
    public Map do_modifyBaseItem_approval(Integer id, Boolean status, String checkRemark, String checkReason){

        if (null != id){
            modifyBaseItemService.approval(id, status, checkRemark, checkReason);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "审核基本信息修改申请：%s, %s", id, status));
        }

        return success(FormUtils.SUCCESS);
    }
}
