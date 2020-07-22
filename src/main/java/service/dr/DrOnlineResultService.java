package service.dr;

import controller.global.OpException;
import domain.dr.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    private DrOnlineInspectorLogService drOnlineInspectorLogService;
    @Autowired
    private DrOnlinePostService drOnlinePostService;
    @Autowired
    private DrOnlineInspectorService drOnlineInspectorService;

    @Transactional
    public void del(Integer id){

        drOnlineResultMapper.deleteByPrimaryKey(id);
    }

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

    //处理参评人的另选候选人
    public Map<Integer, String> consoleOthers(String[] others, String[] datas){

        Map<Integer, String> otherMap = new HashMap<>();
        for (String other : others){
            int count = 0;
            if (StringUtils.isNotBlank(other)) {
                String[] _names = other.split("-");
                Integer postId = Integer.valueOf(_names[0]);
                DrOnlinePostView post = drOnlinePostService.getPost(postId);
                String[] arr = new String[_names.length - 1];
                for (int i = 1; i< _names.length; i++){
                    if (post.getCans().contains(_names[i]))
                        throw new OpException("候选人" + _names[i] + "重复，请加以区别！");
                    arr[i-1] = _names[i];
                }

                //推荐人数是否超额
                if (datas != null) {
                    for (String data : datas) {
                        String[] results = StringUtils.split(data, "_");
                        if (postId != Integer.valueOf(results[0])) {
                            continue;
                        }
                        if (Integer.valueOf(results[2]) == 1) {
                            count++;
                        }
                    }
                }
                count += arr.length;
                if (count > post.getCompetitiveNum()){
                    throw new OpException(post.getName() + "的推荐人数超过最大推荐人数！");
                }
                String names = StringUtils.join(arr, ",");
                otherMap.put(postId, names);
            }
        }

        return otherMap;
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

        //批量插入推荐结果
        iDrMapper.batchInsert_result(resultList);

        inspector.setSubmitTime(new Date());
        inspector.setStatus(DrConstants.INSPECTOR_STATUS_FINISH);

        drOnlineInspectorMapper.updateByPrimaryKey(inspector);

        drOnlineInspectorLogService.updateCount(inspector.getLogId(), 0, 1, 0);
    }

    //结果中包含几个岗位
    public List<Integer> getPostId(List<Integer> typeIds, Integer onlineId){

        List<DrFinalResult> drFinalResults = iDrMapper.selectResultList(typeIds, null, onlineId, null, null, new RowBounds((1 - 1) * 20, 20));
        List<Integer> postIds = new ArrayList<>();
        for (DrFinalResult record : drFinalResults){
            if (!postIds.contains(record.getPostId()))
                postIds.add(record.getPostId());
        }
        return postIds;
    }

    public DrFinalResult findCount(Integer onlineId, Integer postId, String realname, List<Integer> typeIds){

        List<DrFinalResult> drFinalResults = iDrMapper.selectResultList(typeIds, null, onlineId, null,null, new RowBounds((1 - 1) * 20, 20));
        for (DrFinalResult drFinalResult : drFinalResults){
            if (postId == drFinalResult.getPostId()){
                if (null != realname) {
                    if (drFinalResult.getRealname().equals(realname))
                        return drFinalResult;
                    else
                        continue;
                }
                return drFinalResult;
            }
        }

        return null;
    }

    //<postId, candidateStr>
    public Map<Integer ,List<String>> findCandidate(List<Integer> typeIds, Integer onlineId){

        List<DrFinalResult> drFinalResults = iDrMapper.selectResultList(typeIds, null, onlineId, null,null, new RowBounds((1 - 1) * 20, 20));
        DrOnlinePostViewExample postExample = new DrOnlinePostViewExample();
        postExample.createCriteria().andOnlineIdEqualTo(onlineId);
        List<DrOnlinePostView> posts = drOnlinePostViewMapper.selectByExample(postExample);

        Map<Integer ,List<String>> record = new HashMap<>();

        for (DrOnlinePostView post : posts){
            List<String> candidates = new ArrayList<>();
            for (DrFinalResult result : drFinalResults){
                if (result.getPostId().equals(post.getId())){
                    candidates.add(result.getRealname());
                }
            }
            record.put(post.getId(), candidates);
        }
        return record;
    }
}
