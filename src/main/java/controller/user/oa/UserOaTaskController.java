package controller.user.oa;

import controller.OaBaseController;
import domain.oa.OaTaskUserView;
import domain.oa.OaTaskUserViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;
import sys.constants.OaConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/oa")
public class UserOaTaskController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userOaTask:list")
    @RequestMapping("/oaTask")
    public String oaTask(@RequestParam(required = false, defaultValue = "1") Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "user/oa/oaTask_page";
    }

    @RequiresPermissions("userOaTask:list")
    @RequestMapping("/oaTask_data")
    public void oaTask_data(HttpServletResponse response,
                            @RequestParam(required = false, defaultValue = "1") Byte cls,
                            Byte taskType,
                            String taskName,
                            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaTaskUserViewExample example = new OaTaskUserViewExample();
        OaTaskUserViewExample.Criteria criteria = example.createCriteria()
                .andTaskIsDeleteEqualTo(false)
                .andTaskIsPublishEqualTo(true)
                .andTaskStatusIn(Arrays.asList(OaConstants.OA_TASK_STATUS_PUBLISH,
                        OaConstants.OA_TASK_STATUS_FINISH))
                .andIsDeleteEqualTo(false)
                .own(ShiroHelper.getCurrentUserId());
        example.setOrderByClause("id desc");

        switch (cls) {
            case 1:
                criteria.andHasReportEqualTo(false);
                break;
            case 2:
                criteria.andHasReportEqualTo(true);
                break;
        }

        if (taskType != null) {
            criteria.andTaskTypeEqualTo(taskType);
        }

        if (StringUtils.isNotBlank(taskName)) {
            criteria.andTaskNameLike("%" + taskName + "%");
        }

        long count = oaTaskUserViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OaTaskUserView> records = oaTaskUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(oaTask.class, oaTaskMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
