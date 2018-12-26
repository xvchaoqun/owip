package controller.sc.scCommittee;

import controller.sc.ScBaseController;
import domain.sc.scCommittee.*;
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
import org.springframework.web.util.HtmlUtils;
import sys.constants.LogConstants;
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
@RequestMapping("/sc")
public class ScCommitteeOtherVoteController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scCommitteeOtherVote:list")
    @RequestMapping("/scCommitteeOtherVote")
    public String scCommitteeOtherVote(@RequestParam(defaultValue = "1") Integer cls,
                                       ModelMap modelMap) {

        modelMap.put("cls", cls);

        {
            ScCommitteeTopicExample example = new ScCommitteeTopicExample();
            example.createCriteria().andIsDeletedEqualTo(false);
            example.setOrderByClause("id desc");
            List<ScCommitteeTopic> scCommitteeTopics = scCommitteeTopicMapper.selectByExample(example);
            modelMap.put("scCommitteeTopics", scCommitteeTopics);
        }

        return "sc/scCommittee/scCommitteeOtherVote/scCommitteeOtherVote_page";
    }

    @RequiresPermissions("scCommitteeOtherVote:list")
    @RequestMapping("/scCommitteeOtherVote_data")
    public void scCommitteeOtherVote_data(HttpServletResponse response,
                                    Integer topicId,
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

        ScCommitteeOtherVoteViewExample example = new ScCommitteeOtherVoteViewExample();
        ScCommitteeOtherVoteViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (topicId!=null) {
            criteria.andTopicIdEqualTo(topicId);
        }

        long count = scCommitteeOtherVoteViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScCommitteeOtherVoteView> records= scCommitteeOtherVoteViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scCommitteeOtherVote.class, scCommitteeOtherVoteMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scCommitteeOtherVote:edit")
    @RequestMapping(value = "/scCommitteeOtherVote_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommitteeOtherVote_au(ScCommitteeOtherVote record, HttpServletRequest request) {

        Integer id = record.getId();
        record.setMemo(HtmlUtils.htmlUnescape(record.getMemo()));
        if (id == null) {
            scCommitteeOtherVoteService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "添加其他事项表决：%s", record.getId()));
        } else {

            scCommitteeOtherVoteService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "更新其他事项表决：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommitteeOtherVote:edit")
    @RequestMapping("/scCommitteeOtherVote_au")
    public String scCommitteeOtherVote_au(Integer id, Integer topicId, ModelMap modelMap) {

        if (id != null) {
            ScCommitteeOtherVote scCommitteeOtherVote = scCommitteeOtherVoteMapper.selectByPrimaryKey(id);
            topicId = scCommitteeOtherVote.getTopicId();
            modelMap.put("scCommitteeOtherVote", scCommitteeOtherVote);
        }else if(topicId!=null){

            ScCommitteeOtherVoteExample example = new ScCommitteeOtherVoteExample();
            example.createCriteria().andTopicIdEqualTo(topicId);
            List<ScCommitteeOtherVote> scCommitteeOtherVotes = scCommitteeOtherVoteMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
            if(scCommitteeOtherVotes.size()==1)
                modelMap.put("scCommitteeOtherVote", scCommitteeOtherVotes.get(0));
        }
        modelMap.put("topicId", topicId);

        return "sc/scCommittee/scCommitteeOtherVote/scCommitteeOtherVote_au";
    }

    @RequiresPermissions("scCommitteeOtherVote:edit")
    @RequestMapping("/scCommitteeOtherVote_memo")
    public String scCommitteeOtherVote_memo(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScCommitteeOtherVote scCommitteeOtherVote = scCommitteeOtherVoteMapper.selectByPrimaryKey(id);
            modelMap.put("scCommitteeOtherVote", scCommitteeOtherVote);
        }

        return "sc/scCommittee/scCommitteeOtherVote/scCommitteeOtherVote_memo";
    }

    @RequiresPermissions("scCommitteeOtherVote:del")
    @RequestMapping(value = "/scCommitteeOtherVote_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommitteeOtherVote_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scCommitteeOtherVoteService.del(id);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "删除其他事项表决：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommitteeOtherVote:del")
    @RequestMapping(value = "/scCommitteeOtherVote_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scCommitteeOtherVoteService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "批量删除其他事项表决：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
