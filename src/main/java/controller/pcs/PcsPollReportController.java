package controller.pcs;

import domain.pcs.PcsConfig;
import domain.pcs.PcsPollReport;
import domain.pcs.PcsPollReportExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.pcs.common.PcsFinalResult;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
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
@RequestMapping("/pcs")
public class PcsPollReportController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPollReport", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPollReport(HttpServletRequest request,
                                Integer[] ids,//对应查出来结果的userId
                                Boolean isCandidate,
                                int pollId,
                                byte type) {

        if (null != ids && ids.length > 0) {
            pcsPollReportService.batchInsertOrUpdate(ids, isCandidate, pollId, type);
            logger.info(log(LogConstants.LOG_PCS, "批量{1}：{0}", StringUtils.join(ids, ","),
                    isCandidate ? "设置候选人" : "取消候选人资格"));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/pcsPollReport_data")
    @ResponseBody
    public void pcsPollReport_data(HttpServletResponse response,
                                   Integer userId,
                                   Integer partyId,
                                   Integer branchId,
                                   Byte stage,
                                   @RequestParam(required = false, defaultValue = PcsConstants.PCS_USER_TYPE_DW + "") byte type,
                                   Integer pollId,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer[] ids, // 导出的记录
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);


        PcsPollReportExample example = new PcsPollReportExample();
        PcsPollReportExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("ballot desc,positive_ballot desc");

        if (pollId != null) {
            criteria.andPollIdEqualTo(pollId);
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        long count = pcsPollReportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsPollReport> records = pcsPollReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsPollReport.class, pcsPollReportMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);

        return;
    }

    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/pcsPollReport_list_data")
    @ResponseBody
    public void pcsPollReport_list_data(HttpServletResponse response,
                                        Integer userId,
                                        Integer partyId,
                                        Integer branchId,
                                        Byte stage,
                                        Byte type,
                                        Integer pollId,
                                        @RequestParam(required = false, defaultValue = "0") int export,
                                        Integer[] ids, // 导出的记录
                                        Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);


        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        Integer configId = pcsConfig.getId();

        int count = 0;
        count = iPcsMapper.countReport(type, configId, stage, userId, partyId, branchId);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsFinalResult> records = iPcsMapper.selectReport(type, configId, stage, userId, partyId, branchId, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsPollReport.class, pcsPollReportMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);

        return;
    }

    public void pcsPollReport_export(PcsPollReportExample example, HttpServletResponse response) {

        List<PcsPollReport> records = pcsPollReportMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"候选人|100", "党代会|100", "所属二级分党委|100", "所属支部|100", "投票阶段 1一下阶段 2二下阶段 3三下阶段|100", "推荐人类型 1 党代表 2 党委委员 3 纪委委员|100", "得票总数|100", "正式党员票数|100", "预备党员票数|100", "不支持人数|100", "弃权票|100", "备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsPollReport record = records.get(i);
            String[] values = {
                    record.getUserId() + "",
                    record.getConfigId() + "",
                    record.getPartyId() + "",
                    record.getBranchId() + "",
                    record.getStage() + "",
                    record.getType() + "",
                    record.getBallot() + "",
                    record.getPositiveBallot() + "",
                    record.getGrowBallot() + "",
                    record.getDisagreeBallot() + "",
                    record.getAbstainBallot() + "",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("党代会投票报送结果(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }


}
