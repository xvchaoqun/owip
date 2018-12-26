package controller.sc.scDispatch;

import controller.sc.ScBaseController;
import domain.sc.scCommittee.ScCommitteeVoteView;
import domain.sc.scCommittee.ScCommitteeVoteViewExample;
import domain.sc.scDispatch.ScDispatchUser;
import domain.sc.scDispatch.ScDispatchUserExample;
import domain.sc.scDispatch.ScDispatchUserExample.Criteria;
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
import sys.constants.DispatchConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScDispatchUserController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scDispatch:list")
    @RequestMapping("/scDispatchUser")
    public String scDispatchUser() {

        return "sc/scDispatch/scDispatchUser/scDispatchUser_page";
    }

    @RequiresPermissions("scDispatch:list")
    @RequestMapping("/scDispatchUser_data")
    public void scDispatchUser_data(HttpServletResponse response,
                                    Integer dispatchId,
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScDispatchUserExample example = new ScDispatchUserExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (dispatchId != null) {
            criteria.andDispatchIdEqualTo(dispatchId);
        }

        long count = scDispatchUserMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScDispatchUser> records = scDispatchUserMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scDispatchUser.class, scDispatchUserMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scDispatch:edit")
    @RequestMapping(value = "/scDispatchUser_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scDispatchUser_au(ScDispatchUser record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scDispatchUserService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_DISPATCH, "添加任免对象：%s", record.getId()));
        } else {

            scDispatchUserService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_DISPATCH, "更新任免对象：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scDispatch:edit")
    @RequestMapping("/scDispatchUser_au")
    public String scDispatchUser_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScDispatchUser scDispatchUser = scDispatchUserMapper.selectByPrimaryKey(id);
            modelMap.put("scDispatchUser", scDispatchUser);
        }
        return "sc/scDispatch/scDispatchUser/scDispatchUser_au";
    }

    @RequiresPermissions("scDispatch:del")
    @RequestMapping(value = "/scDispatchUser_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scDispatchUser_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scDispatchUserService.del(id);
            logger.info(addLog(LogConstants.LOG_SC_DISPATCH, "删除任免对象：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scDispatch:del")
    @RequestMapping(value = "/scDispatchUser_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scDispatchUser_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            scDispatchUserService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_DISPATCH, "批量删除任免对象：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scDispatch:changeOrder")
    @RequestMapping(value = "/scDispatchUser_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scDispatchUser_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        scDispatchUserService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_SC_DISPATCH, "任免对象调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scDispatch:edit")
    @RequestMapping("/scDispatchUser_export")
    public void scDispatchUser_export(Integer dispatchId, @RequestParam(required = false, value = "voteIds[]") Integer[] voteIds,
                                      HttpServletResponse response) {

        if (dispatchId==null && (voteIds == null || voteIds.length == 0)) {
            return;
        }
        List<ScCommitteeVoteView> votes = null;
        if(dispatchId!=null) {
            votes = iScMapper.getScDispatchVotes(dispatchId);
        }else {
            ScCommitteeVoteViewExample example = new ScCommitteeVoteViewExample();
            example.createCriteria().andIdIn(Arrays.asList(voteIds));
            example.setOrderByClause("field(id," + StringUtils.join(voteIds, ",") + ") asc");
            votes = scCommitteeVoteViewMapper.selectByExample(example);
        }

        int rownum = votes.size();
        String[] titles = {"工作证号|100", "姓名|80", "任免类别|80", "原任职务|300", "职务|300"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScCommitteeVoteView record = votes.get(i);
            SysUserView user = record.getUser();
            String[] values = {
                    user.getCode(),
                    user.getRealname(),
                    DispatchConstants.DISPATCH_CADRE_TYPE_MAP.get(record.getType()),
                    record.getOriginalPost(),
                    record.getPost()
            };
            valuesList.add(values);
        }

        String fileName = "任免对象_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
