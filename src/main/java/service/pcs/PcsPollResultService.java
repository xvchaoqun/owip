package service.pcs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import controller.global.OpException;
import domain.pcs.PcsPoll;
import domain.pcs.PcsPollInspector;
import domain.pcs.PcsPollResult;
import domain.pcs.PcsPollResultExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.PcsFinalResult;
import persistence.pcs.common.PcsTempResult;
import sys.constants.PcsConstants;
import sys.tags.CmTag;

import java.io.StringWriter;
import java.util.*;

@Service
public class PcsPollResultService extends PcsBaseMapper {

    @Autowired
    private PcsPollInspectorService pcsPollInspectorService;
    @Autowired
    private PcsPollCandidateService pcsPollCandidateService;

    @Transactional
    public void insertSelective(PcsPollResult record){

        pcsPollResultMapper.insertSelective(record);

    }

    @Transactional
    public void batchInsert(List<PcsPollResult> records){

        for (PcsPollResult record : records) {
            pcsPollResultMapper.insertSelective(record);
        }

    }

    @Transactional
    public void del(Integer id){

        pcsPollResultMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsPollResultExample example = new PcsPollResultExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollResultMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsPollResult record){
        pcsPollResultMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, PcsPollResult> findAll() {

        PcsPollResultExample example = new PcsPollResultExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PcsPollResult> records = pcsPollResultMapper.selectByExample(example);
        Map<Integer, PcsPollResult> map = new LinkedHashMap<>();
        for (PcsPollResult record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    //转换暂存票数
    public PcsTempResult getTempResult(String tempData){

        PcsTempResult tempResult = null;

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", PcsTempResult.class);//将序列化中的类全量名称，用别名替换。

        if (StringUtils.isNotBlank(tempData)){
            tempResult = (PcsTempResult) xStream.fromXML(tempData);//XML反序列化
        }

        tempResult = (tempResult == null) ? new PcsTempResult() : tempResult;

        return tempResult;
    }

    public String getStringTemp(PcsTempResult record){

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", PcsTempResult.class);

        StringWriter sw = new StringWriter();
        xStream.marshal(record, new CompactWriter(sw));
        return sw.toString();
    }

    @Transactional
    public void submitResult(PcsPollInspector inspector) {

        PcsPoll pcsPoll = inspector.getPcsPoll();
        int pollId = pcsPoll.getId();
        Byte stage = pcsPoll.getStage();
        PcsTempResult tempResult = getTempResult(inspector.getTempdata());

        List<PcsPollResult> resultList = new ArrayList<>();

        if (stage != PcsConstants.PCS_POLL_FIRST_STAGE) {//提交二下/三下阶段推荐结果

            Map<String, Byte> secondResultMap = tempResult.getSecondResultMap();
            Map<String, Integer> otherResultMap = tempResult.getOtherResultMap();

            for (String key : secondResultMap.keySet()) {

                PcsPollResult result = new PcsPollResult();
                Byte type = Byte.valueOf(key.split("_")[0]);
                Integer userId = Integer.valueOf(key.split("_")[1]);
                Byte status = secondResultMap.get(key);
                result.setPollId(pollId);
                result.setCandidateUserId(userId);
                result.setInspectorId(inspector.getId());
                result.setStage(stage);
                result.setPartyId(pcsPoll.getPartyId());
                result.setBranchId(pcsPoll.getBranchId());
                result.setIsPositive(inspector.getIsPositive());
                result.setStatus(status);
                result.setType(type);

                if (status == PcsConstants.RESULT_STATUS_DISAGREE){
                    String otherKey = key + "_4";
                    userId = otherResultMap.get(otherKey); // 允许不填
                }
                result.setUserId(userId);
                resultList.add(result);
            }

        }else {//提交一下阶段推荐结果

            Map<Byte, Set<Integer>> firstResultMap = tempResult.getFirstResultMap();

            for (Byte type : firstResultMap.keySet()) {
                Set<Integer> userIdList = firstResultMap.get(type);
                for (Integer userId : userIdList) {
                    PcsPollResult result = new PcsPollResult();
                    result.setPollId(pollId);
                    result.setInspectorId(inspector.getId());
                    result.setStage(stage);
                    result.setPartyId(inspector.getPartyId());
                    result.setBranchId(inspector.getBranchId());
                    result.setIsPositive(inspector.getIsPositive());
                    result.setStatus(PcsConstants.RESULT_STATUS_AGREE);
                    result.setType(type);
                    result.setUserId(userId);

                    resultList.add(result);
                }
            }
        }

        // 允许全部不填
        if (resultList.size() > 0 ){
            batchInsert(resultList);
        }

        //更新投票人状态
        inspector.setIsFinished(true);
        inspector.setSubmitTime(new Date());
        pcsPollInspectorService.updateByPrimaryKeySelective(inspector);

        iPcsMapper.updatePollInspectorCount(pollId);
    }

    @Transactional
    public void batchCancel(Integer[] ids, Boolean isCandidate, Integer pollId, Byte type) {

        if (pollId == null) return;

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        Integer partyId = pcsPoll.getPartyId();
        Byte stage = pcsPoll.getStage();

        List<Integer> pollIdList = new ArrayList<>();
        pollIdList.add(pollId);
        if (isCandidate) {
            List<PcsFinalResult> pcsFinalResultList = iPcsMapper.selectResultList(type, pollIdList, stage, isCandidate, null, null, null, null, null, new RowBounds());
            Set<Integer> userIdSet = new HashSet<>();
            for (PcsFinalResult pcsFinalResult : pcsFinalResultList) {
                userIdSet.add(pcsFinalResult.getUserId());
            }
            userIdSet.addAll(Arrays.asList(ids));

            int requiredCount = 0;
            if (type == PcsConstants.PCS_POLL_CANDIDATE_PR){
                requiredCount = pcsPollCandidateService.getPrRequiredCount(partyId);
            }else if (type == PcsConstants.PCS_POLL_CANDIDATE_DW){
                requiredCount = CmTag.getIntProperty("pcs_poll_dw_num");
            }else if (type == PcsConstants.PCS_POLL_CANDIDATE_JW){
                requiredCount = CmTag.getIntProperty("pcs_poll_jw_num");
            }
            if (userIdSet.size() > requiredCount){
                throw new OpException("设置候选人失败，超过设置{0}候选人的最大数量{1}", PcsConstants.PCS_POLL_CANDIDATE_TYPE.get(type), requiredCount);
                //throw new OpException("{0}已有{1}名候选人，再添加{2}名，则会超过候选人的最大数量。", PcsConstants.PCS_POLL_CANDIDATE_TYPE.get(type), count);
            }
        }
        PcsPollResult record = new PcsPollResult();
        record.setIsCandidate(isCandidate);
        PcsPollResultExample example = new PcsPollResultExample();
        example.createCriteria().andPollIdEqualTo(pollId).andUserIdIn(Arrays.asList(ids)).andTypeEqualTo(type);
        pcsPollResultMapper.updateByExampleSelective(record, example);
    }

}
