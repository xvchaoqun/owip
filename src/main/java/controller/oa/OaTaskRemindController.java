package controller.oa;

import controller.OaBaseController;
import domain.oa.OaTaskRemind;
import domain.oa.OaTaskRemindExample;
import domain.oa.OaTaskRemindExample.Criteria;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OaTaskRemindController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("oaTaskRemind:list")
    @RequestMapping("/oaTaskRemind")
    public String oaTaskRemind() {

        return "oa/oaTaskRemind/oaTaskRemind_page";
    }

    @RequiresPermissions("oaTaskRemind:list")
    @RequestMapping("/oaTaskRemind_data")
    public void oaTaskRemind_data(HttpServletResponse response,
                                  Integer taskId,
                                  Integer userId,
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaTaskRemindExample example = new OaTaskRemindExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (taskId != null) {
            criteria.andTaskIdEqualTo(taskId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        long count = oaTaskRemindMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OaTaskRemind> records = oaTaskRemindMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(oaTaskRemind.class, oaTaskRemindMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("oaTaskRemind:edit")
    @RequestMapping(value = "/oaTaskRemind_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskRemind_au(OaTaskRemind record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            if (oaTaskRemindService.idDuplicate(id, record.getTaskId(), record.getUserId())) {
                return failed("添加重复");
            }

            oaTaskRemindService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_OA, "添加任务对象本人设置的短信提醒：%s", record.getId()));
        } else {

            oaTaskRemindService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OA, "更新任务对象本人设置的短信提醒：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaTaskRemind:edit")
    @RequestMapping("/oaTaskRemind_au")
    public String oaTaskRemind_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            OaTaskRemind oaTaskRemind = oaTaskRemindMapper.selectByPrimaryKey(id);
            modelMap.put("oaTaskRemind", oaTaskRemind);
        }
        return "oa/oaTaskRemind/oaTaskRemind_au";
    }

    @RequiresPermissions("oaTaskRemind:del")
    @RequestMapping(value = "/oaTaskRemind_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskRemind_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            oaTaskRemindService.del(id);
            logger.info(addLog(SystemConstants.LOG_OA, "删除任务对象本人设置的短信提醒：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaTaskRemind:del")
    @RequestMapping(value = "/oaTaskRemind_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            oaTaskRemindService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OA, "批量删除任务对象本人设置的短信提醒：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
