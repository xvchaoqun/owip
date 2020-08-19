package controller.pcs;

import domain.pcs.PcsConfig;
import domain.pcs.PcsPoll;
import domain.pcs.PcsPollReport;
import domain.pcs.PcsPollReportExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.pcs.common.PcsFinalResult;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/pcs")
public class PcsPollReportController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*del
    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/pcsPollReport")
    public String pcsPollReport(@RequestParam(required = false, defaultValue = "1") Byte type,
                                @RequestParam(required = false, defaultValue = "4") Integer cls,
                                Integer pollId,
                                ModelMap modelMap) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        modelMap.put("stage", pcsPoll.getStage());
        modelMap.put("type", type);
        modelMap.put("cls", cls);

        List<PcsPollReport> reportList = pcsPollReportService.getReport(pcsPoll, type);
        modelMap.put("num", reportList.size());

        return "pcs/pcsPoll/pcsPollReport/pcsPollReport_page";
    }*/

    @RequiresPermissions("pcsPollReport:list")
    @RequestMapping("/pcsPollReport_data")
    @ResponseBody
    public void pcsPollReport_data(HttpServletResponse response,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                    Byte stage,
                                    Byte type,
                                   @RequestParam(required = false, defaultValue = "1")Byte _type,

                                 Integer pollId,
                                
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


        if (pollId == null) {
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
        }else {
            PcsPollReportExample example = new PcsPollReportExample();
            PcsPollReportExample.Criteria criteria = example.createCriteria();
            example.setOrderByClause("ballot desc,positive_ballot desc");

            if (pollId != null) {
                PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
                partyId = pcsPoll.getPartyId();
                branchId = pcsPoll.getBranchId();
                stage = pcsPoll.getStage();
            }

            if (userId!=null) {
                criteria.andUserIdEqualTo(userId);
            }
            if (partyId!=null) {
                criteria.andPartyIdEqualTo(partyId);
            }
            if (branchId!=null) {
                criteria.andBranchIdEqualTo(branchId);
            }
            if (stage!=null) {
                criteria.andStageEqualTo(stage);
            }
            if (_type!=null) {
                criteria.andTypeEqualTo(_type);
            }

            /*if (export == 1) {
                if(ids!=null && ids.length>0)
                    criteria.andIdIn(Arrays.asList(ids));
                pcsPollReport_export(example, response);
                return;
            }*/

            long count = pcsPollReportMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<PcsPollReport> records= pcsPollReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            CommonList commonList = new CommonList(count, pageNo, pageSize);

            Map resultMap = new HashMap();
            resultMap.put("rows", records);
            resultMap.put("records", count);
            resultMap.put("page", pageNo);
            resultMap.put("total", commonList.pageNum);
            Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
            //baseMixins.put(pcsPollReport.class, pcsPollReportMixin.class);
            JSONUtils.jsonp(resultMap, baseMixins);
        }

        return;
    }

    public void pcsPollReport_export(PcsPollReportExample example, HttpServletResponse response) {

        List<PcsPollReport> records = pcsPollReportMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"候选人|100","党代会|100","所属二级分党委|100","所属支部|100","投票阶段 1一下阶段 2二下阶段 3三下阶段|100","推荐人类型 1 党代表 2 党委委员 3 纪委委员|100","得票总数|100","正式党员票数|100","预备党员票数|100","不支持人数|100","弃权票|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsPollReport record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getConfigId()+"",
                            record.getPartyId()+"",
                            record.getBranchId()+"",
                            record.getStage()+"",
                            record.getType()+"",
                            record.getBallot()+"",
                            record.getPositiveBallot()+"",
                            record.getGrowBallot()+"",
                            record.getDisagreeBallot()+"",
                            record.getAbstainBallot()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("党代会投票报送结果(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }


}
