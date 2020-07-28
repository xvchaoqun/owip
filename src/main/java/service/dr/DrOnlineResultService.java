package service.dr;

import controller.global.OpException;
import domain.dr.DrOnlineCandidate;
import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlineResult;
import domain.dr.DrOnlineResultExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.dr.common.DrFinalResult;
import persistence.dr.common.DrTempResult;
import sys.constants.DrConstants;

import java.util.*;

@Service
public class DrOnlineResultService extends DrBaseMapper {

    @Autowired
    private DrCommonService drCommonService;
    @Autowired
    private DrOnlineCandidateService drOnlineCandidateService;

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DrOnlineResultExample example = new DrOnlineResultExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlineResultMapper.deleteByExample(example);
    }

    public Map<Integer, DrOnlineResult> findAll() {

        DrOnlineResultExample example = new DrOnlineResultExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DrOnlineResult> records = drOnlineResultMapper.selectByExample(example);
        Map<Integer, DrOnlineResult> map = new LinkedHashMap<>();
        for (DrOnlineResult record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    //提交推荐结果
    @Transactional
    public void submitResult(DrOnlineInspector inspector){

        DrTempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());
        int onlineId = inspector.getOnlineId();
        int inspectorId = inspector.getId();
        Integer inspectorTypeId = inspector.getTypeId();

        List<DrOnlineResult> resultList = new ArrayList<>();

        Map<String, Byte> candidateMap = tempResult.getCandidateMap();
        Map<String, String> otherMap = tempResult.getOtherMap();
        Map<Integer, Set<String>> realnameSetMap = tempResult.getRealnameSetMap();

        // 有候选人的推荐人
        for (Map.Entry<String, Byte> entry : candidateMap.entrySet()) {

            String key = entry.getKey();
            byte status = entry.getValue();

            int postId = Integer.valueOf(key.split("_")[0]);
            int userId = Integer.valueOf(key.split("_")[1]);

            DrOnlineResult result = new DrOnlineResult();
            result.setOnlineId(onlineId);
            result.setPostId(postId);
            result.setUserId(userId);
            if(status==DrConstants.RESULT_STATUS_AGREE) { // 同意
                DrOnlineCandidate candidate = drOnlineCandidateService.getId(userId, postId);
                result.setRealname(candidate.getRealname());
            }else{
                String realname = otherMap.get(key);
                result.setRealname(realname); // 另选推荐人（可以为空）
            }

            result.setInspectorId(inspectorId);
            result.setInspectorTypeId(inspectorTypeId);
            result.setStatus(status);

            resultList.add(result);
        }

        //无候选人的推荐人
        for (Map.Entry<Integer, Set<String>> entry : realnameSetMap.entrySet()) {
            int postId = entry.getKey();
            Set<String> realnameSet = entry.getValue();

            for (String realname : realnameSet) {

                DrOnlineResult result = new DrOnlineResult();
                result.setOnlineId(onlineId);
                result.setPostId(postId);
                result.setRealname(realname);
                result.setInspectorId(inspectorId);
                result.setInspectorTypeId(inspectorTypeId);
                result.setStatus(DrConstants.RESULT_STATUS_OTHER);

                resultList.add(result);
            }
        }

        if(resultList.size()==0) {

            throw new OpException("无效数据");
        }

        //批量插入推荐结果
        iDrMapper.batchInsert_result(resultList);

        inspector.setSubmitTime(new Date());
        inspector.setStatus(DrConstants.INSPECTOR_STATUS_FINISH);

        drOnlineInspectorMapper.updateByPrimaryKey(inspector);

        iDrMapper.refreshInspectorLogCount(inspector.getLogId());
    }

    //<postId, DrFinalResult>
    public Map<Integer ,List<DrFinalResult>> getResult(List<DrFinalResult> drFinalResults, Set<Integer> postIds){

        Map<Integer ,List<DrFinalResult>> record = new HashMap<>();
        for (Integer postId : postIds) {
            List<DrFinalResult> _drFinalResults = new ArrayList<>();
            for (DrFinalResult drFinalResult : drFinalResults) {
                if (drFinalResult.getPostId() == postId){
                    _drFinalResults.add(drFinalResult);
                }
            }
            record.put(postId, _drFinalResults);
        }

        return record;
    }
}
