package controller.sc.scCommittee;

import controller.sc.ScBaseController;
import domain.cadre.CadreView;
import domain.sc.scCommittee.*;
import domain.unit.UnitPostView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.tags.CmTag;
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
public class ScCommitteeVoteController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scCommitteeVote:list")
    @RequestMapping("/scCommitteeVote")
    public String scCommitteeVote(@RequestParam(defaultValue = "1") Integer cls,
                                  Integer cadreId,
                                  ModelMap modelMap) {
        modelMap.put("cls", cls);

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
        }

        {
            ScCommitteeTopicExample example = new ScCommitteeTopicExample();
            example.createCriteria().andIsDeletedEqualTo(false);
            example.setOrderByClause("id desc");
            List<ScCommitteeTopic> scCommitteeTopics = scCommitteeTopicMapper.selectByExample(example);
            modelMap.put("scCommitteeTopics", scCommitteeTopics);
        }

        return "sc/scCommittee/scCommitteeVote/scCommitteeVote_page";
    }

    @RequiresPermissions("scCommitteeVote:list")
    @RequestMapping("/scCommitteeVote_data")
    public void scCommitteeVote_data(HttpServletResponse response,
                                     String name,
                                    Integer topicId,
                                    Integer cadreId,
                                     Byte type,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScCommitteeVoteViewExample example = new ScCommitteeVoteViewExample();
        ScCommitteeVoteViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("hold_date desc, seq asc, id asc");

        if(StringUtils.isNotBlank(name)){
            criteria.andNameLike("%"+name.trim()+"%");
        }
        if (topicId!=null) {
            criteria.andTopicIdEqualTo(topicId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scCommitteeVote_export(example, response);
            return;
        }

        long count = scCommitteeVoteViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScCommitteeVoteView> records= scCommitteeVoteViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scCommitteeVote.class, scCommitteeVoteMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scCommitteeVote:list")
    @RequestMapping("/scCommitteeVote_au_form")
    public String scCommitteeVote_au_form(Integer topicId, Integer id, ModelMap modelMap) {

        if(topicId!=null) {

            ScCommitteeTopic scCommitteeTopic = scCommitteeTopicMapper.selectByPrimaryKey(topicId);
            modelMap.put("scCommitteeTopic", scCommitteeTopic);

            modelMap.put("scCommittee", scCommitteeService.getView(scCommitteeTopic.getCommitteeId()));

            if(id!=null){
                ScCommitteeVoteView scCommitteeVote = iScMapper.getScCommitteeVoteView(id);
                modelMap.put("scCommitteeVote", scCommitteeVote);
                if(scCommitteeVote.getUnitPostId()!=null) {
                    UnitPostView unitPost = iUnitMapper.getUnitPost(scCommitteeVote.getUnitPostId());
                    modelMap.put("unitPost", unitPost);
                }
            }

            ScCommitteeVoteExample example = new ScCommitteeVoteExample();
            example.createCriteria().andTopicIdEqualTo(topicId);
            List<ScCommitteeVote> scCommitteeVotes = scCommitteeVoteMapper.selectByExample(example);
            modelMap.put("scCommitteeVotes", scCommitteeVotes);
        }

        return "sc/scCommittee/scCommitteeVote/scCommitteeVote_au_form";
    }

    @RequiresPermissions("scCommitteeVote:edit")
    @RequestMapping(value = "/scCommitteeVote_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommitteeVote_au(ScCommitteeVote record,
                                     String originalPost,
                                     @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date originalPostTime,
                                     HttpServletRequest request) {

        Integer id = record.getId();
        if (id == null) {
            scCommitteeVoteService.insertSelective(record, originalPost, originalPostTime);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "添加干部选拔任用表决：%s", record.getCadreId()));
        } else {

            scCommitteeVoteService.updateByPrimaryKeySelective(record, originalPost, originalPostTime);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "更新干部选拔任用表决：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommitteeVote:edit")
    @RequestMapping("/scCommitteeVote_au")
    public String scCommitteeVote_au(Integer topicId, ModelMap modelMap) {

        if (topicId != null) {

            ScCommitteeTopic scCommitteeTopic = scCommitteeTopicMapper.selectByPrimaryKey(topicId);
            modelMap.put("scCommitteeTopic", scCommitteeTopic);

            modelMap.put("scCommittee", scCommitteeService.getView(scCommitteeTopic.getCommitteeId()));
        }
        return "sc/scCommittee/scCommitteeVote/scCommitteeVote_au";
    }

    @RequiresPermissions("scCommitteeVote:del")
    @RequestMapping(value = "/scCommitteeVote_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scCommitteeVote_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scCommitteeVoteService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "批量删除干部选拔任用表决：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommitteeVote:changeOrder")
    @RequestMapping(value = "/scCommitteeVote_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommitteeVote_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        scCommitteeVoteService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "干部选拔任用表决调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommitteeVote:edit")
    @RequestMapping("/scCommitteeVote_selectScRecord")
    public String scCommitteeVote_selectScRecord(int userId,
                                      ModelMap modelMap) {

        modelMap.put("sysUser", CmTag.getUserById(userId));

        return "sc/scCommittee/scCommitteeVote/scCommitteeVote_selectScRecord";
    }

    public void scCommitteeVote_export(ScCommitteeVoteViewExample example, HttpServletResponse response) {

        List<ScCommitteeVoteView> records = scCommitteeVoteViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"议题|100","所属干部|100","类别|100","原任职务|100","原任职务任职时间|100","干部类型|100","任免方式|100","任免程序|100","职务|100","职务属性|100","行政级别|100","所属单位|100","参会同意人数|100","备注|100","排序|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScCommitteeVoteView record = records.get(i);
            String[] values = {
                record.getTopicId()+"",
                            record.getCadreId()+"",
                            record.getType() + "",
                            record.getOriginalPost(),
                            DateUtils.formatDate(record.getOriginalPostTime(), DateUtils.YYYY_MM_DD),
                            record.getCadreTypeId()+"",
                            record.getWayId()+"",
                            record.getProcedureId()+"",
                            record.getPost(),
                            record.getPostType()+"",
                            record.getAdminLevel()+"",
                            record.getUnitId()+"",
                            record.getAgreeCount()+"",
                            record.getRemark(),
                            record.getSortOrder()+""
            };
            valuesList.add(values);
        }
        String fileName = "干部选拔任用表决_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
