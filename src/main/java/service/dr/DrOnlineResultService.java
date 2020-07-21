package service.dr;

import controller.global.OpException;
import domain.dr.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import persistence.dr.common.DrFinalResult;
import persistence.dr.common.DrTempResult;
import sys.constants.DrConstants;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
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
    public Boolean submitResult(Boolean isMoblie, Integer inspectorId, HttpServletRequest request){

        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(inspectorId);
        DrTempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());
        Integer onlineId = inspector.getOnlineId();

        List<DrOnlineResult> resultList = new ArrayList<>();
        if (tempResult.getRawOptionMap() != null) {
            for (Map.Entry<String, Integer> entry : tempResult.getRawOptionMap().entrySet()) {
                String[] postUserId = entry.getKey().split("_");
                DrOnlineResult result = new DrOnlineResult();
                result.setOnlineId(onlineId);
                result.setPostId(Integer.valueOf(postUserId[0]));
                result.setUserId(Integer.valueOf(postUserId[1]));
                result.setCandidate(drOnlineCandidateService.getId(Integer.valueOf(postUserId[1]), Integer.valueOf(postUserId[0])).getCandidate());
                result.setInspectorId(inspector.getId());
                result.setInspectorTypeId(inspector.getTypeId());
                result.setIsAgree(entry.getValue() == 1 ? true : false);

                resultList.add(result);
            }
        }

        //另选的候选人
        Map<Integer, String> otherResultMap = new HashMap<>();
        otherResultMap = tempResult.getOtherResultMap();
        if (otherResultMap != null && otherResultMap.size() > 0){

            for (Map.Entry<Integer, String> entry2 : otherResultMap.entrySet()) {
                String[] candidates = entry2.getValue().split(",");
                List<String> cans = drOnlinePostService.getPost(entry2.getKey()).getCans();
                if (null != entry2 && candidates.length > 0) {
                    for (String candidate: candidates) {
                        if (cans.contains(candidate))
                            throw new OpException("候选人" + candidate + "重名，请加以区别！");
                        DrOnlineResult result = new DrOnlineResult();
                        result.setOnlineId(onlineId);
                        result.setPostId(entry2.getKey());
                        result.setCandidate(candidate);
                        result.setInspectorId(inspector.getId());
                        result.setInspectorTypeId(inspector.getTypeId());
                        result.setIsAgree(true);

                        resultList.add(result);
                    }
                }

            }
        }

        if (resultList == null || resultList.size() == 0){
            throw new OpException("请先完成投票，再提交！");
        }

        submit(isMoblie, inspectorId, resultList);

        return true;
    }

    @Transactional
    public void submit(Boolean isMobile, Integer inspectorId, List<DrOnlineResult> resultList){

        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(inspectorId);
        //批量插入推荐结果
        iDrMapper.batchInsert_result(resultList);

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

        DrOnlineInspector record = new DrOnlineInspector();
        record.setSubmitTime(new Date());
        record.setSubmitIp(IpUtils.getRealIp(request));
        record.setStatus(DrConstants.INSPECTOR_STATUS_FINISH);
        record.setIsMobile(isMobile);

        List<Byte> statusList = new ArrayList<>();

        statusList.add(DrConstants.INSPECTOR_STATUS_SAVE);
        statusList.add(DrConstants.INSPECTOR_STATUS_INIT);
        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria().andIdEqualTo(inspectorId).andStatusIn(statusList);
        drOnlineInspectorMapper.updateByExampleSelective(record, example);

        /*if (drOnlineInspectorMapper.updateByExampleSelective(record, example) == 1){
            throw new OpException("该账号已经投过票, inspector's name:" + inspector.getUsername());
        }*/
        drOnlineInspectorLogService.updateCount(inspector.getLogId(), 0, 1, 0);
        //throw new OpException("事务回滚！");
        /*if(drOnlineInspectorLogMapper.incrFinishCount(inspector.getLogId()) == 0){
            throw new OpException("update finish_count error, inspector's name:" + inspector.getUsername());
        }*/
    }

    @Transactional
    public Boolean saveOrSubmit(Boolean isMoblie, Integer isSubmit, Integer inspectorId, DrOnlineInspector record, HttpServletRequest request) {

        drOnlineInspectorService.updateByExampleSelectiveBeforeSubmit(record);

        if (isSubmit == 1){
            return submitResult(isMoblie, inspectorId, request);
        }
        return true;
    }

    //结果中包含几个岗位
    public List<Integer> getPostId(List<Integer> typeIds, Integer onlineId){

        List<DrFinalResult> drFinalResults = iDrMapper.resultOne(typeIds, null, onlineId, null, null, new RowBounds((1 - 1) * 20, 20));
        List<Integer> postIds = new ArrayList<>();
        for (DrFinalResult record : drFinalResults){
            if (!postIds.contains(record.getPostId()))
                postIds.add(record.getPostId());
        }
        return postIds;
    }

    public DrFinalResult findCount(Integer onlineId, Integer postId, String candidate, List<Integer> typeIds){

        List<DrFinalResult> drFinalResults = iDrMapper.resultOne(typeIds, null, onlineId, null,null, new RowBounds((1 - 1) * 20, 20));
        for (DrFinalResult drFinalResult : drFinalResults){
            if (postId == drFinalResult.getPostId()){
                if (null != candidate) {
                    if (drFinalResult.getCandidate().equals(candidate))
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

        List<DrFinalResult> drFinalResults = iDrMapper.resultOne(typeIds, null, onlineId, null,null, new RowBounds((1 - 1) * 20, 20));
        DrOnlinePostViewExample postExample = new DrOnlinePostViewExample();
        postExample.createCriteria().andOnlineIdEqualTo(onlineId);
        List<DrOnlinePostView> posts = drOnlinePostViewMapper.selectByExample(postExample);

        Map<Integer ,List<String>> record = new HashMap<>();

        for (DrOnlinePostView post : posts){
            List<String> candidates = new ArrayList<>();
            for (DrFinalResult result : drFinalResults){
                if (result.getPostId().equals(post.getId())){
                    candidates.add(result.getCandidate());
                }
            }
            record.put(post.getId(), candidates);
        }
        return record;
    }
}
