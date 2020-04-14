package service.dr;

import bean.DrTempResult;
import controller.global.OpException;
import domain.dr.*;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sys.constants.DrConstants;
import sys.tags.CmTag;
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

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DrOnlineResultExample example = new DrOnlineResultExample();
        DrOnlineResultExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return drOnlineResultMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DrOnlineResult record){

        drOnlineResultMapper.insertSelective(record);
    }

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

    @Transactional
    public void updateByPrimaryKeySelective(DrOnlineResult record){

        drOnlineResultMapper.updateByPrimaryKeySelective(record);
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

    //处理参评人自己添加的候选人
    public Map<Integer, String> consoleOthers(String[] others, String[] datas){

        Map<Integer, String> otherMap = new HashMap<>();
        for (String other : others){
            int count = 0;
            if (StringUtils.isNotBlank(other)) {
                String[] _nameCode = other.split("-");
                Integer postId = Integer.valueOf(_nameCode[0]);

                String[] nameCode = new String[_nameCode.length - 1];
                //是否存在用户
                for (Integer i = 1; i < _nameCode.length; i++) {
                    try {
                        String _code = (_nameCode[i].split("\\("))[1];
                        String code = _code.substring(0, _code.length() - 1);
                        SysUserView userView = CmTag.getUserByCode(code);
                    }catch (Exception e) {
                        throw new OpException("用户" + _nameCode[i] + "不存在！");
                    }
                    nameCode[i-1] = _nameCode[i];
                }

                //推荐人数是否超额
                for (String data : datas) {
                    String[] results = org.apache.commons.lang3.StringUtils.split(data, "_");
                    if (postId != Integer.valueOf(results[0])){
                        continue;
                    }
                    if (Integer.valueOf(results[2]) == 1){
                        count++;
                    }
                }
                count += nameCode.length;
                DrOnlinePostView post = drOnlinePostService.getPost(postId);
                if (count > post.getCompetitiveNum()){
                    throw new OpException(post.getName() + "的推荐人数超过最大推荐人数");
                }
                String nameCodes = StringUtils.join(nameCode, "-");
                otherMap.put(postId, nameCodes);
            }
        }

        return otherMap;
    }

    //提交推荐结果
    public Boolean submitResult(Boolean isMoblie, Integer inspectorId, HttpServletRequest request){

        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(inspectorId);
        DrTempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());
        Integer onlineId = inspector.getOnlineId();

        List<DrOnlineResult> resultList = new ArrayList<>();
        //TempInspectorResult inspectorResult = new TempInspectorResult();
        for (Map.Entry<String, Integer> entry : tempResult.getRawOptionMap().entrySet()){
                String[] postUserId = entry.getKey().split("_");
                DrOnlineResult result = new DrOnlineResult();
                result.setOnlineId(onlineId);
                result.setPostId(Integer.valueOf(postUserId[0]));
                result.setCandidateId(drOnlineCandidateService.getId(Integer.valueOf(postUserId[1]), Integer.valueOf(postUserId[0])));
                result.setInspectorId(inspector.getId());
                result.setInspectorTypeId(inspector.getTypeId());
                result.setInsOption(entry.getValue() == 1 ? true : false);

                resultList.add(result);
        }

        //另选的候选人
        Map<Integer, String> otherResultMap = tempResult.getOtherResultMap();
        if (otherResultMap == null || otherResultMap.size() > 0){
            for (Map.Entry<Integer, String> entry2 : otherResultMap.entrySet()) {
                //添加其他的候选人
                List<Integer> candidateIds = drOnlineCandidateService.insertOther(entry2.getKey(), entry2.getValue());
                if (candidateIds != null){
                    if (candidateIds.size() > 0) {
                        for (Integer id : candidateIds) {
                            DrOnlineResult result = new DrOnlineResult();
                            result.setOnlineId(onlineId);
                            result.setPostId(entry2.getKey());
                            result.setCandidateId(id);
                            result.setInspectorId(inspector.getId());
                            result.setInspectorTypeId(inspector.getTypeId());
                            result.setInsOption(true);

                            resultList.add(result);
                        }
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

        if(resultList == null || resultList.size() == 0)
        			throw new OpException("测评结果异常，请联系管理员");
        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(inspectorId);
        //批量插入推荐结果
        iDrMapper.batchInsert_result(resultList);

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

        DrOnlineInspector record = new DrOnlineInspector();
        record.setSubmitTime(new Date());
        record.setSubmitIp(IpUtils.getRealIp(request));
        record.setStatus(DrConstants.INSPECTOR_STATUS_FINISH);
        record.setIsMobile(isMobile);

        //是否设置一个账号只能提交一次
        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria().andIdEqualTo(inspectorId).andStatusEqualTo(DrConstants.INSPECTOR_STATUS_SAVE);
        drOnlineInspectorMapper.updateByExampleSelective(record, example);

        /*if (drOnlineInspectorMapper.updateByExampleSelective(record, example) == 1){
            throw new OpException("该账号已经投过票, inspector's name:" + inspector.getUsername());
        }*/
        drOnlineInspectorLogService.updateFinishCount(inspector.getLogId());
        /*if(drOnlineInspectorLogMapper.incrFinishCount(inspector.getLogId()) == 0){
            throw new OpException("update finish_count error, inspector's name:" + inspector.getUsername());
        }*/
    }

    public Map<Integer ,List<DrOnlineCandidate>> findCandidate(Integer onlineId){

        DrOnlineResultViewExample example = new DrOnlineResultViewExample();
        DrOnlineResultViewExample.Criteria criteria = example.createCriteria().andOnlineIdEqualTo(onlineId);
        example.setOrderByClause(" option_sum desc");
        List<DrOnlineResultView> results = drOnlineResultViewMapper.selectByExample(example);
        DrOnlinePostViewExample postExample = new DrOnlinePostViewExample();
        postExample.createCriteria().andOnlineIdEqualTo(onlineId);
        List<DrOnlinePostView> posts = drOnlinePostViewMapper.selectByExample(postExample);

        Map<Integer ,List<DrOnlineCandidate>> record = new HashMap<>();

        for (DrOnlinePostView post : posts){
            List<DrOnlineCandidate> candidates = new ArrayList<>();
            for (DrOnlineResultView result : results){
                if (result.getPostId().equals(post.getId())){
                    candidates.add(result.getCandidate());
                }
            }
            record.put(post.getId(), candidates);
        }
        return record;
    }

    //结果中包含几个岗位
    public List<Integer> getPostId(Integer onlineId){

        DrOnlineResultViewExample example = new DrOnlineResultViewExample();
        example.createCriteria().andOnlineIdEqualTo(onlineId);
        List<DrOnlineResultView> records = drOnlineResultViewMapper.selectByExample(example);
        List<Integer> postIds = new ArrayList<>();
        for (DrOnlineResultView record : records){
            if (!postIds.contains(record.getPostId()))
                postIds.add(record.getPostId());
        }
        return postIds;
    }

    public DrOnlineResultView findCount(Integer onlineId, Integer postId, Integer candidateId){

        DrOnlineResultViewExample example = new DrOnlineResultViewExample();
        DrOnlineResultViewExample.Criteria criteria = example.createCriteria().andOnlineIdEqualTo(onlineId).andPostIdEqualTo(postId);
        if (candidateId != null){
            criteria.andCandidateIdEqualTo(candidateId);
        }
        List<DrOnlineResultView> resultViews = drOnlineResultViewMapper.selectByExample(example);

        return resultViews.size() > 0 ? resultViews.get(0) : null;
    }
}
