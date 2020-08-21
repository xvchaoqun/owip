package controller.pcs;

import domain.party.Branch;
import domain.party.Party;
import domain.pcs.*;
import domain.pcs.PcsPollInspectorExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
@RequestMapping("/pcs")
public class PcsPollInspectorController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPollInspector:list")
    @RequestMapping("/pcsPollInspector")
    public String pcsPollInspector(Integer pollId, ModelMap modelMap) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        modelMap.put("pcsPoll", pcsPoll);

        return "pcs/pcsPoll/pcsPollInspector/pcsPollInspector_page";
    }

    @RequiresPermissions("pcsPollInspector:list")
    @RequestMapping("/pcsPollInspector_data")
    @ResponseBody
    public void pcsPollInspector_data(HttpServletResponse response,
                                    int pollId,
                                    String username,
                                    Integer isPositive,
                                    Integer partyId,
                                    Integer branchId,
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

        PcsPollInspectorExample example = new PcsPollInspectorExample();
        Criteria criteria = example.createCriteria().andPollIdEqualTo(pollId);
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(username)) {
            criteria.andUsernameLike(SqlUtils.trimLike(username));
        }
        if (isPositive != null){
            if (isPositive == -1){
                criteria.andIsPositiveIsNull();
            }else {
                criteria.andIsPositiveEqualTo(isPositive == 1);
            }
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsPollInspector_export(pollId, example, response);
            return;
        }

        long count = pcsPollInspectorMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsPollInspector> records= pcsPollInspectorMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsPollInspector.class, pcsPollInspectorMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsPollInspector:edit")
    @RequestMapping(value = "/pcsPollInspector_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPollInspector_au(Integer pollId, Integer count, HttpServletRequest request) {

        if (pollId != null && count > 0){
            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
            int configId = pcsPoll.getConfigId();
            int partyId = pcsPoll.getPartyId();
            Integer branchId = pcsPoll.getBranchId();
            PcsBranch pcsBranch =  pcsBranchService.get(configId, partyId, branchId);

            int requiredCount = pcsBranch.getMemberCount();
            int existCount = pcsPoll.getInspectorNum();
            if (count + existCount > requiredCount){
                return failed("生成账号失败，账号总数超出本支部党员总数{0}人",count + existCount - requiredCount);
            }
            pcsPollInspectorService.genInspector(pollId, count);
            logger.info(log( LogConstants.LOG_PCS, "党代会投票{0}生成投票人数{1}", pollId, count));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPollInspector:edit")
    @RequestMapping("/pcsPollInspector_au")
    public String pcsPollInspector_au(Integer pollId, ModelMap modelMap) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        int configId = pcsPoll.getConfigId();
        int partyId = pcsPoll.getPartyId();
        Integer branchId = pcsPoll.getBranchId();
        PcsBranch pcsBranch =  pcsBranchService.get(configId, partyId, branchId);
        modelMap.put("pcsBranch", pcsBranch);
        modelMap.put("pcsPoll", pcsPoll);

        return "pcs/pcsPoll/pcsPollInspector/pcsPollInspector_au";
    }

    @RequiresPermissions("pcsPollInspector:del")
    @RequestMapping(value = "/pcsPollInspector_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPollInspector_batchDel(HttpServletRequest request, Integer[] ids, Integer pollId) {


        if (null != ids && ids.length>0){
            pcsPollInspectorService.batchDel(ids, pollId);
            logger.info(log( LogConstants.LOG_PCS, "批量删除党代会投票投票人：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspector:edit")
    @RequestMapping("/pcsPollInspector_print")
    public String pcsPollInspector_print(Integer pollId,
                                         ModelMap modelMap,
                                         HttpServletResponse response,
                                         HttpServletRequest request){

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        modelMap.put("pcsPoll", pcsPoll);

        PcsPollInspectorExample example = new PcsPollInspectorExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andPollIdEqualTo(pollId);
        List<PcsPollInspector> inspectors = pcsPollInspectorMapper.selectByExample(example);
        modelMap.put("inspectors", inspectors);

        return "pcs/pcsPoll/pcsPollInspector/pcsPollInspector_print";
    }


    @RequiresPermissions("drOnlineInspector:edit")
    @RequestMapping("/pcspollInspector_Result")
    public String pcspollInspector_Result(Integer id,
                                         ModelMap modelMap,
                                         HttpServletResponse response,
                                         HttpServletRequest request){

        PcsPollInspectorExample example = new PcsPollInspectorExample();
        example.createCriteria().andIdEqualTo(id);
        List<PcsPollInspector> inspectors = pcsPollInspectorMapper.selectByExample(example);

        List<PcsPollResult> pcsPollResults = new ArrayList<>();
        if (inspectors != null &&inspectors.size() > 0){

            PcsPollInspector inspector = inspectors.get(0);
            int pollId = inspector.getPollId();
            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
            modelMap.put("stage", pcsPoll.getStage());
            PcsPollResultExample resultExample = new PcsPollResultExample();
            resultExample.createCriteria().andInspectorIdEqualTo(inspector.getId());
            resultExample.setOrderByClause("type");
            pcsPollResults = pcsPollResultMapper.selectByExample(resultExample);
        }
        modelMap.put("pcsPollResults", pcsPollResults);

        return "pcs/pcsPoll/pcsPollInspector/pcspollInspector_Result";
    }

    public void pcsPollInspector_export(Integer pollId, PcsPollInspectorExample example, HttpServletResponse response) {

        List<PcsPollInspector> records = pcsPollInspectorMapper.selectByExample(example);
        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        int rownum = records.size();
        String[] titles = {"登录账号|100","登录密码|100","所属二级党组织|300","所属党支部|252","投票人身份|100","是否完成投票|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsPollInspector record = records.get(i);
            Party party = null;
            Branch branch = null;
            if (record.getPartyId() != null) {
                party = partyMapper.selectByPrimaryKey(record.getPartyId());
            }
            if(record.getBranchId() != null){
                branch = branchMapper.selectByPrimaryKey(record.getBranchId());
            }
            String[] values = {
                    record.getUsername(),
                    record.getPasswd(),
                    party == null ? "" : party.getName(),
                    branch == null ? "" : branch.getName(),
                    record.getIsPositive() == null ? "" : BooleanUtils.isTrue(record.getIsPositive()) ? "正式党员" : "预备党员",
                    BooleanUtils.isTrue(record.getIsFinished()) ? "是" : "否",
            };
            valuesList.add(values);
        }
        String fileName = String.format(pcsPoll.getName() + "投票投票人账号(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
