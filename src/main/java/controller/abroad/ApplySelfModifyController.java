package controller.abroad;

import controller.BaseController;
import domain.ApplySelfModify;
import domain.ApplySelfModifyExample;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.ApplySelfMixin;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApplySelfModifyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySelf:list")
    @RequestMapping("/applySelfModify_page")
    public String applySelfModify_page() {

        return "abroad/applySelf/applySelfModify_page";
    }

    @RequiresPermissions("applySelf:list")
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
