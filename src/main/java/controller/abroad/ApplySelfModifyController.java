package controller.abroad;

import bean.ApplySelfModifyBean;
import controller.BaseController;
import domain.ApplySelfModify;
import domain.ApplySelfModifyExample;
import mixin.ApplySelfMixin;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApplySelfModifyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySelf:modifyLog")
    @RequestMapping("/applySelfModifyList")
    public String applySelfModifyList(int applyId, ModelMap modelMap) {

        /*List<ApplySelfModifyBean> applySelfModifyList = selectMapper.getApplySelfModifyList(applyId);
        modelMap.put("modifyList", applySelfModifyList);*/
        ApplySelfModifyExample example = new ApplySelfModifyExample();
        example.createCriteria().andApplyIdEqualTo(applyId).andModifyTypeEqualTo(SystemConstants.APPLYSELF_MODIFY_TYPE_MODIFY);
        List<ApplySelfModify> applySelfModifies = applySelfModifyMapper.selectByExample(example);
        modelMap.put("modifyList", applySelfModifies);
        return "abroad/applySelf/applySelfModifyList";
    }


    @RequiresPermissions("applySelf:modifyLog")
    @RequestMapping("/applySelfModify_page")
    public String applySelfModify_page(int applyId, ModelMap modelMap) {

        // 获取第一条原始记录
        ApplySelfModifyExample example2 = new ApplySelfModifyExample();
        example2.createCriteria().andApplyIdEqualTo(applyId).andModifyTypeEqualTo(SystemConstants.APPLYSELF_MODIFY_TYPE_ORIGINAL);
        List<ApplySelfModify> applySelfModifies = applySelfModifyMapper.selectByExampleWithRowbounds(example2, new RowBounds(0, 1));
        if(applySelfModifies.size()>0){
            Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
            sourceMixins.put(ApplySelfModify.class, ApplySelfMixin.class);
            modelMap.put("record", JSONUtils.toString(applySelfModifies.get(0), sourceMixins));
        }

        return "abroad/applySelf/applySelfModify_page";
    }

    @RequiresPermissions("applySelf:modifyLog")
    @RequestMapping("/applySelfModify_data")
    @ResponseBody
    public void applySelfModify_data(
            int applyId,
            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySelfModifyExample example = new ApplySelfModifyExample();
        ApplySelfModifyExample.Criteria criteria = example.createCriteria();
        criteria.andApplyIdEqualTo(applyId);
        //example.setOrderByClause("id asc");

        int count = applySelfModifyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelfModify> applySelfModifys = applySelfModifyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", applySelfModifys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(ApplySelfModify.class, ApplySelfMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
}
