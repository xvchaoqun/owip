package controller.sc.scAd;

import controller.sc.ScBaseController;
import domain.sc.scAd.ScAdArchiveVote;
import domain.sc.scAd.ScAdArchiveVoteExample;
import domain.sc.scAd.ScAdArchiveVoteExample.Criteria;
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
public class ScAdArchiveVoteController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scAdArchiveVote:list")
    @RequestMapping("/scAdArchiveVote")
    public String scAdArchiveVote() {

        return "sc/scAd/scAdArchiveVote/scAdArchiveVote_page";
    }

    @RequiresPermissions("scAdArchiveVote:list")
    @RequestMapping("/scAdArchiveVote_data")
    public void scAdArchiveVote_data(HttpServletResponse response,
                                    Integer archiveId,
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

        ScAdArchiveVoteExample example = new ScAdArchiveVoteExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (archiveId!=null) {
            criteria.andArchiveIdEqualTo(archiveId);
        }

        long count = scAdArchiveVoteMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScAdArchiveVote> records= scAdArchiveVoteMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scAdArchiveVote.class, scAdArchiveVoteMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scAdArchiveVote:edit")
    @RequestMapping(value = "/scAdArchiveVote_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scAdArchiveVote_au(ScAdArchiveVote record, HttpServletRequest request) {

        Integer id = record.getId();

        if (scAdArchiveVoteService.idDuplicate(id, record.getVoteId())) {
            return failed("添加重复");
        }
        if (id == null) {
            scAdArchiveVoteService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_AD, "添加归档表决：%s", record.getId()));
        } else {

            scAdArchiveVoteService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_AD, "更新归档表决：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scAdArchiveVote:edit")
    @RequestMapping("/scAdArchiveVote_au")
    public String scAdArchiveVote_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScAdArchiveVote scAdArchiveVote = scAdArchiveVoteMapper.selectByPrimaryKey(id);
            modelMap.put("scAdArchiveVote", scAdArchiveVote);
        }
        return "sc/scAd/scAdArchiveVote/scAdArchiveVote_au";
    }

    @RequiresPermissions("scAdArchiveVote:del")
    @RequestMapping(value = "/scAdArchiveVote_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scAdArchiveVote_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scAdArchiveVoteService.del(id);
            logger.info(addLog(LogConstants.LOG_SC_AD, "删除归档表决：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scAdArchiveVote:del")
    @RequestMapping(value = "/scAdArchiveVote_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scAdArchiveVote_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scAdArchiveVoteService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_AD, "批量删除归档表决：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
