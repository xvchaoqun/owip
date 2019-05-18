package controller.cet;

import domain.cet.*;
import domain.cet.CetDiscussGroupExample.Criteria;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetDiscussGroupController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetDiscussGroup:list")
    @RequestMapping("/cetDiscussGroup")
    public String cetDiscussGroup(int discussId, ModelMap modelMap) {

        CetDiscuss cetDiscuss = cetDiscussMapper.selectByPrimaryKey(discussId);
        modelMap.put("cetDiscuss", cetDiscuss);

        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(cetDiscuss.getPlanId());
        modelMap.put("cetProjectPlan", cetProjectPlan);

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(cetProjectPlan.getProjectId());
        modelMap.put("cetProject", cetProject);

        return "cet/cetDiscussGroup/cetDiscussGroup_page";
    }

    @RequiresPermissions("cetDiscussGroup:list")
    @RequestMapping("/cetDiscussGroup_data")
    public void cetDiscussGroup_data(HttpServletResponse response,
                                    int discussId,
                                    String subject,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetDiscussGroupExample example = new CetDiscussGroupExample();
        Criteria criteria = example.createCriteria().andDiscussIdEqualTo(discussId);
        example.setOrderByClause("sort_order asc");

        if (StringUtils.isNotBlank(subject)) {
            criteria.andSubjectLike(SqlUtils.like(subject));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetDiscussGroup_export(example, response);
            return;
        }

        long count = cetDiscussGroupMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetDiscussGroup> records= cetDiscussGroupMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetDiscussGroup.class, cetDiscussGroupMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetDiscussGroup:edit")
    @RequestMapping(value = "/cetDiscussGroup_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscussGroup_au(CetDiscussGroup record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetDiscussGroupService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加研讨小组：%s", record.getId()));
        } else {

            cetDiscussGroupService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新研讨小组：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetDiscussGroup:edit")
    @RequestMapping("/cetDiscussGroup_au")
    public String cetDiscussGroup_au(Integer id,
                                     Integer discussId,
                                     ModelMap modelMap) {

        if (id != null) {
            CetDiscussGroup cetDiscussGroup = cetDiscussGroupMapper.selectByPrimaryKey(id);
            modelMap.put("cetDiscussGroup", cetDiscussGroup);
            discussId = cetDiscussGroup.getDiscussId();
        }

        CetDiscuss cetDiscuss = cetDiscussMapper.selectByPrimaryKey(discussId);
        modelMap.put("cetDiscuss", cetDiscuss);

        return "cet/cetDiscussGroup/cetDiscussGroup_au";
    }

    // 上传开会信息
    @RequiresPermissions("cetDiscussGroup:edit")
    @RequestMapping("/cetDiscussGroup_result")
    public String cetDiscussGroup_result(Integer id,
                                     ModelMap modelMap) {
        if (id != null) {
            CetDiscussGroup cetDiscussGroup = cetDiscussGroupMapper.selectByPrimaryKey(id);
            modelMap.put("cetDiscussGroup", cetDiscussGroup);
        }
        return "cet/cetDiscussGroup/cetDiscussGroup_result";
    }

    // 选择学员/取消选择
    @RequiresPermissions("cetDiscussGroup:edit")
    @RequestMapping(value = "/cetDiscussGroup_selectObjs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscussGroup_selectObjs(boolean select, int discussGroupId,
                                           @RequestParam(value = "ids[]", required = false) Integer[] ids ,
                                           HttpServletRequest request) {

        cetDiscussGroupService.selectObjs(ids, select, discussGroupId);
        logger.info(addLog(LogConstants.LOG_CET, "选择学员/取消选择： %s, %s, %s",
                StringUtils.join(ids, ","), select, discussGroupId));

        return success(FormUtils.SUCCESS);
    }

    // 参会/取消参会
    @RequiresPermissions("cetDiscussGroup:edit")
    @RequestMapping(value = "/cetDiscussGroup_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscussGroup_finish(boolean finish, int discussGroupId,
                                           @RequestParam(value = "ids[]", required = false) Integer[] ids ,
                                           HttpServletRequest request) {

        cetDiscussGroupService.finish(ids, finish, discussGroupId);
        logger.info(addLog(LogConstants.LOG_CET, "参会/取消参会： %s, %s, %s",
                StringUtils.join(ids, ","), finish, discussGroupId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetDiscussGroup:del")
    @RequestMapping(value = "/cetDiscussGroup_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscussGroup_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetDiscussGroupService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除研讨小组：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetDiscussGroup:del")
    @RequestMapping(value = "/cetDiscussGroup_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetDiscussGroup_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetDiscussGroupService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除研讨小组：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetDiscussGroup:changeOrder")
    @RequestMapping(value = "/cetDiscussGroup_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscussGroup_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetDiscussGroupService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "研讨小组调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetDiscussGroup_export(CetDiscussGroupExample example, HttpServletResponse response) {

        List<CetDiscussGroup> records = cetDiscussGroupMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"分组讨论|100", "研讨主题|100","是否允许修改研讨主题|100","召开时间|100","召开地点|100","负责单位|100","负责单位管理员|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetDiscussGroup record = records.get(i);
            String[] values = {
                record.getDiscussId()+"",

                            record.getSubject(),
                            record.getSubjectCanModify()+"",
                            DateUtils.formatDate(record.getDiscussTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            record.getDiscussAddress(),
                            record.getUnitId()+"",
                            record.getAdminUserId()+"",
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "研讨小组_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
