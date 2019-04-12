package controller.sc.scCommittee;

import controller.sc.ScBaseController;
import domain.sc.scCommittee.ScCommittee;
import domain.sc.scCommittee.ScCommitteeMember;
import domain.sc.scCommittee.ScCommitteeMemberExample;
import domain.sc.scCommittee.ScCommitteeMemberView;
import org.apache.commons.lang3.StringUtils;
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
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScCommitteeMemberController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scCommitteeMember:list")
    @RequestMapping("/scCommitteeMember")
    public String scCommitteeMember(Integer committeeId, Boolean isAbsent, ModelMap modelMap) {

        ScCommittee scCommittee = scCommitteeMapper.selectByPrimaryKey(committeeId);
        modelMap.put("scCommittee", scCommittee);

        List<ScCommitteeMemberView> userList = scCommitteeService.getMemberList(committeeId, isAbsent);
        modelMap.put("userList", userList);

        return "sc/scCommittee/scCommitteeMember/scCommitteeMember_page";
    }

    /*@RequiresPermissions("scCommitteeMember:list")
    @RequestMapping("/scCommitteeMember_data")
    public void scCommitteeMember_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "sc_committee_member") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
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

        ScCommitteeMemberExample example = new ScCommitteeMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scCommitteeMember_export(example, response);
            return;
        }

        long count = scCommitteeMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScCommitteeMember> records= scCommitteeMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scCommitteeMember.class, scCommitteeMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }*/

    @RequiresPermissions("scCommitteeMember:edit")
    @RequestMapping(value = "/scCommitteeMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommitteeMember_au(ScCommitteeMember record, HttpServletRequest request) {

        Integer id = record.getId();
        if (id == null) {
            scCommitteeMemberService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "添加党委常委会成员：%s", record.getId()));
        } else {

            scCommitteeMemberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "更新党委常委会成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommitteeMember:edit")
    @RequestMapping("/scCommitteeMember_au")
    public String scCommitteeMember_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScCommitteeMember scCommitteeMember = scCommitteeMemberMapper.selectByPrimaryKey(id);
            modelMap.put("scCommitteeMember", scCommitteeMember);
        }
        return "sc/scCommittee/scCommitteeMember/scCommitteeMember_au";
    }

    @RequiresPermissions("scCommitteeMember:del")
    @RequestMapping(value = "/scCommitteeMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scCommitteeMemberService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "批量删除党委常委会成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommitteeMember:changeOrder")
    @RequestMapping(value = "/scCommitteeMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommitteeMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        scCommitteeMemberService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "党委常委会成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void scCommitteeMember_export(ScCommitteeMemberExample example, HttpServletResponse response) {

        List<ScCommitteeMember> records = scCommitteeMemberMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"成员|100","是否请假|100","排序|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScCommitteeMember record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getIsAbsent() + "",
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = "党委常委会成员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
