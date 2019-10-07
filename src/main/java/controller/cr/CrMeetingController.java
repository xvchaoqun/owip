package controller.cr;

import domain.cr.CrMeeting;
import domain.cr.CrMeetingExample;
import domain.cr.CrMeetingExample.Criteria;
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

public class CrMeetingController extends CrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crMeeting:list")
    @RequestMapping("/crMeeting")
    public String crMeeting(Integer infoId, ModelMap modelMap) {

        modelMap.put("crInfo", crInfoMapper.selectByPrimaryKey(infoId));

        return "cr/crMeeting/crMeeting_page";
    }

    @RequiresPermissions("crMeeting:list")
    @RequestMapping("/crMeeting_data")
    @ResponseBody
    public void crMeeting_data(HttpServletResponse response,
                               Date meetingDate,
                               Integer infoId,
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

        CrMeetingExample example = new CrMeetingExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("meeting_date asc");

        if (infoId != null) {
            criteria.andInfoIdEqualTo(infoId);
        }

        if (meetingDate != null) {
            criteria.andMeetingDateGreaterThan(meetingDate);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crMeeting_export(example, response);
            return;
        }

        long count = crMeetingMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrMeeting> records = crMeetingMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crMeeting.class, crMeetingMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crMeeting:edit")
    @RequestMapping(value = "/crMeeting_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crMeeting_au(CrMeeting record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            crMeetingService.insertSelective(record);
            logger.info(log(LogConstants.LOG_CR, "添加招聘会信息：{0}", record.getId()));
        } else {

            crMeetingService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_CR, "更新招聘会信息：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crMeeting:edit")
    @RequestMapping("/crMeeting_au")
    public String crMeeting_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrMeeting crMeeting = crMeetingMapper.selectByPrimaryKey(id);
            modelMap.put("crMeeting", crMeeting);
        }
        return "cr/crMeeting/crMeeting_au";
    }

    @RequiresPermissions("crMeeting:del")
    @RequestMapping(value = "/crMeeting_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crMeeting_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            crMeetingService.del(id);
            logger.info(log(LogConstants.LOG_CR, "删除招聘会信息：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crMeeting:del")
    @RequestMapping(value = "/crMeeting_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map crMeeting_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crMeetingService.batchDel(ids);
            logger.info(log(LogConstants.LOG_CR, "批量删除招聘会信息：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void crMeeting_export(CrMeetingExample example, HttpServletResponse response) {

        List<CrMeeting> records = crMeetingMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"招聘会日期|100", "招聘岗位|100", "招聘会人数要求|100", "备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrMeeting record = records.get(i);
            String[] values = {
                    DateUtils.formatDate(record.getMeetingDate(), DateUtils.YYYY_MM_DD),
                    record.getPostIds(),
                    record.getApplyCount() + "",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("招聘会信息(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
