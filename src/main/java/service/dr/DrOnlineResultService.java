package service.dr;

import bean.TempInspectorResult;
import bean.TempResult;
import controller.global.OpException;
import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlineInspectorExample;
import domain.dr.DrOnlineResult;
import domain.dr.DrOnlineResultExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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

    public Map<Integer, String> consoleOthers(String[] others){

        Map<Integer, String> otherMap = new HashMap<>();
        for (String other : others){
            if (StringUtils.isNotBlank(other)) {
                String[] data = other.split("-");
                Integer postId = Integer.valueOf(data[0]);
                String nameCode = data[1];
                otherMap.put(postId, nameCode);
            }
        }

        return otherMap;
    }

    //提交推荐结果
    public Boolean submitResult(Boolean isMoblie, Integer inspectorId, HttpServletRequest request){

        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(inspectorId);
        TempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());
        Integer onlineId = inspector.getOnlineId();

        List<DrOnlineResult> resultList = new ArrayList<>();
        //TempInspectorResult inspectorResult = new TempInspectorResult();
        for (Map.Entry<String, TempInspectorResult> entry : tempResult.getTempInspectorResultMap().entrySet()){
            for (Map.Entry<String, Integer> entry1 : entry.getValue().getOptionIdMap().entrySet()){
                String[] postUserId = entry1.getKey().split("_");
                DrOnlineResult result = new DrOnlineResult();
                result.setOnlineId(onlineId);
                result.setPostId(Integer.valueOf(postUserId[0]));
                result.setCandidateId(drOnlineCandidateService.getId(Integer.valueOf(postUserId[1]), Integer.valueOf(postUserId[0])));
                result.setInspectorId(inspector.getId());
                result.setInspectorTypeId(inspector.getTypeId());
                result.setInsOption(entry1.getValue() == 1 ? true : false);

                resultList.add(result);
            }
        }
        Map<Integer, String> otherResultMap = tempResult.getOtherResultMap();
        if (otherResultMap == null ||otherResultMap.size() > 0){
            for (Map.Entry<Integer, String> entry2 : otherResultMap.entrySet()) {
                //添加其他的候选人
                List<Integer> candidateIds = drOnlineCandidateService.insertOther(entry2.getKey(), entry2.getValue());
                if (candidateIds.size() > 0 || candidateIds != null){
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

        if (resultList == null || resultList.size() == 0){
            return false;
        }

        submit(isMoblie, inspectorId, resultList);

        return true;
    }

    @Transactional
    public int submit(Boolean isMobile, Integer inspectorId, List<DrOnlineResult> resultList){

        if(resultList==null || resultList.size()==0)
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

        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria().andIdEqualTo(inspectorId).andStatusEqualTo(DrConstants.INSPECTOR_STATUS_SAVE);

        if (drOnlineInspectorMapper.updateByExampleSelective(record, example)==0){
            throw new OpException("update inspector's status error, inspector's name:" + inspector.getUsername());
        }
        if(drOnlineInspectorLogMapper.incrFinishCount(inspector.getLogId())==0){
            throw new OpException("update finish_count error, inspector's name:" + inspector.getUsername());
        }

        return 1;
    }
}
