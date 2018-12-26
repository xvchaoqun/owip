package controller.sc.scCommittee;

import controller.sc.ScBaseController;
import domain.sc.scCommittee.ScCommitteeTopic;
import domain.sc.scCommittee.ScCommitteeTopicCadre;
import domain.sc.scCommittee.ScCommitteeTopicView;
import domain.sc.scCommittee.ScCommitteeTopicViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScCommitteeTopicController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scCommitteeTopic:list")
    @RequestMapping("/scCommitteeTopic")
    public String scCommitteeTopic(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("scCommittees", scCommitteeService.findAll());

        return "sc/scCommittee/scCommitteeTopic/scCommitteeTopic_page";
    }

    @RequiresPermissions("scCommitteeTopic:list")
    @RequestMapping("/scCommitteeTopic_data")
    public void scCommitteeTopic_data(HttpServletResponse response,
                                      Integer year,
                                    Integer committeeId,
                                      @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date holdDate,
                                    String name,
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

        ScCommitteeTopicViewExample example = new ScCommitteeTopicViewExample();
        ScCommitteeTopicViewExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("hold_date desc, committee_id desc, seq asc, id asc");

        if (committeeId!=null) {
            criteria.andCommitteeIdEqualTo(committeeId);
        }
        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (name!=null) {
            criteria.andNameLike("%" + name + "%");
        }

        long count = scCommitteeTopicViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScCommitteeTopicView> records= scCommitteeTopicViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scCommitteeTopic.class, scCommitteeTopicMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scCommitteeTopic:edit")
    @RequestMapping(value = "/scCommitteeTopic_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommitteeTopic_au(ScCommitteeTopic record,
                                      MultipartFile _voteFilePath,
                                      HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if(record.getSeq() != null && scCommitteeTopicService.idDuplicate
                (id, record.getCommitteeId(), record.getSeq())){
            return failed("议题序号重复");
        }

        record.setHasVote(BooleanUtils.isTrue(record.getHasVote()));
        record.setHasOtherVote(BooleanUtils.isTrue(record.getHasOtherVote()));

        record.setContent(HtmlUtils.htmlUnescape(record.getContent()));
        record.setVoteFilePath(uploadPdf(_voteFilePath, "scCommitteeTopic-vote"));
        if (id == null) {
            if (record.getSeq() == null)
                record.setSeq(scCommitteeTopicService.genSeq(record.getCommitteeId()));

            scCommitteeTopicService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "添加党委常委会议题：%s", record.getId()));
        } else {

            scCommitteeTopicService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "更新党委常委会议题：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommitteeTopic:edit")
    @RequestMapping("/scCommitteeTopic_au")
    public String scCommitteeTopic_au(Integer id, Integer committeeId, ModelMap modelMap) {

        if (id != null) {
            ScCommitteeTopic scCommitteeTopic = scCommitteeTopicMapper.selectByPrimaryKey(id);
            modelMap.put("scCommitteeTopic", scCommitteeTopic);
            if(scCommitteeTopic!=null){
                committeeId = scCommitteeTopic.getCommitteeId();
            }
        }

        modelMap.put("committeeId", committeeId);

        modelMap.put("scCommittees", scCommitteeService.findAll());

        return "sc/scCommittee/scCommitteeTopic/scCommitteeTopic_au";
    }


    @RequiresPermissions("scCommitteeTopic:list")
    @RequestMapping("/scCommitteeTopic_cadre")
    @ResponseBody
    public ScCommitteeTopicCadre scCommitteeTopic_cadre(int topicId, int cadreId) {

        return scCommitteeTopicService.getTopicCadre(topicId, cadreId);
    }

    @RequiresPermissions("scCommitteeTopic:edit")
    @RequestMapping("/scCommitteeTopic_content")
    public String scCommitteeTopic_content(Integer topicId, ModelMap modelMap) {

        if (topicId != null) {
            ScCommitteeTopic scCommitteeTopic = scCommitteeTopicMapper.selectByPrimaryKey(topicId);
            modelMap.put("scCommitteeTopic", scCommitteeTopic);
        }

        return "sc/scCommittee/scCommitteeTopic/scCommitteeTopic_content";
    }

    @RequiresPermissions("scCommitteeTopic:del")
    @RequestMapping(value = "/scCommitteeTopic_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scCommitteeTopicService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "批量删除党委常委会议题：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
