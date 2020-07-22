package service.dr;

import domain.dr.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.constants.DrConstants;

import java.util.*;

@Service
public class DrOnlineInspectorLogService extends DrBaseMapper {

    @Autowired
    protected DrOnlineInspectorService drOnlineInspectorService;

    @Transactional
    public void insertSelective(DrOnlineInspectorLog record) {

        drOnlineInspectorLogMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        drOnlineInspectorLogMapper.deleteByPrimaryKey(id);
    }

    /*ok
        删除参评人
        删除结果
        删除参评人日志
     */
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        DrOnlineInspectorExample inspectorExample = new DrOnlineInspectorExample();
        inspectorExample.createCriteria().andLogIdIn(Arrays.asList(ids));
        List<DrOnlineInspector> inspectors = drOnlineInspectorMapper.selectByExample(inspectorExample);
        List<Integer> inspectorIds = new ArrayList<>();
        for (DrOnlineInspector inspector : inspectors){
            inspectorIds.add(inspector.getId());
        }

        DrOnlineResultExample resultExample = new DrOnlineResultExample();
        resultExample.createCriteria().andInspectorIdIn(inspectorIds);
        drOnlineResultMapper.deleteByExample(resultExample);
        drOnlineInspectorMapper.deleteByExample(inspectorExample);

        DrOnlineInspectorLogExample example = new DrOnlineInspectorLogExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlineInspectorLogMapper.deleteByExample(example);
    }

    public Map<Integer, DrOnlineInspectorLog> findAll() {

        DrOnlineInspectorLogExample example = new DrOnlineInspectorLogExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DrOnlineInspectorLog> records = drOnlineInspectorLogMapper.selectByExample(example);
        Map<Integer, DrOnlineInspectorLog> map = new LinkedHashMap<>();
        for (DrOnlineInspectorLog record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    //生成参评人日志和参评人账号（单个生成）
    @Transactional
    public Integer generateInspector(Integer inspectorTypeId, Integer unitId, int type, boolean isAppended, Integer count, Integer onlineId) {

        int logId = 0;
        Date now = new Date();
        DrOnlineInspectorLogExample example = new DrOnlineInspectorLogExample();
        DrOnlineInspectorLogExample.Criteria criteria = example.createCriteria().andTypeIdEqualTo(inspectorTypeId).andOnlineIdEqualTo(onlineId);
        if (unitId != null){
            criteria.andUnitIdEqualTo(unitId);
        }else {
            criteria.andUnitIdIsNull();
        }
        List<DrOnlineInspectorLog> inspectorLogs = drOnlineInspectorLogMapper.selectByExample(example);
        if (count > 0){
            if (inspectorLogs != null && !inspectorLogs.isEmpty()){
                DrOnlineInspectorLog drOnlineInspectorLog = inspectorLogs.get(0);
                logId = drOnlineInspectorLog.getId();
                if (type == 1){//列表生成
                    count = count - drOnlineInspectorLog.getTotalCount() > 0 ? count - drOnlineInspectorLog.getTotalCount() : 0;
                }

            }else {
                DrOnlineInspectorLog inspectorLog = new DrOnlineInspectorLog();
                inspectorLog.setTypeId(inspectorTypeId);
                inspectorLog.setUnitId(unitId);
                inspectorLog.setCreateTime(now);
                inspectorLog.setTotalCount(count);
                inspectorLog.setExportCount(0);
                inspectorLog.setOnlineId(onlineId);
                inspectorLog.setFinishCount(0);
                inspectorLog.setPubCount(0);

                drOnlineInspectorLogMapper.insert(inspectorLog);

                logId = inspectorLog.getId();
            }
        }

        Assert.isTrue(logId > 0);

        // 个别生成，非补发时，标记为列表生成
        if(!isAppended && type==2){
            type = 1;
        }

        if (count > 0){
            for (int i = 0; i < count; i++){

                DrOnlineInspector inspector = new DrOnlineInspector();
                inspector.setLogId(logId);
                inspector.setOnlineId(onlineId);
                inspector.setUsername(drOnlineInspectorService.buildUsername());
                inspector.setPasswd(RandomStringUtils.randomNumeric(6));
                inspector.setTypeId(inspectorTypeId);
                inspector.setUnitId(unitId);
                inspector.setType(type);
                inspector.setIsMobile(false);
                inspector.setStatus(DrConstants.INSPECTOR_STATUS_INIT);
                inspector.setPubStatus(DrConstants.INSPECTOR_PUB_STATUS_NOT_RELEASE);
                inspector.setCreateTime(now);
                inspector.setRemark(isAppended ? "补发" : null);

                drOnlineInspectorMapper.insert(inspector);
            }

            //生成账号后更新参评人日志总数
            updateTotalCount(logId);
        }
        return logId;
    }

    @Transactional
    public void changeStatus(Integer[] ids) {

        for (Integer id : ids){
            DrOnlineInspectorExample example = new DrOnlineInspectorExample();
            example.createCriteria().andLogIdEqualTo(id).andPubStatusEqualTo(DrConstants.INSPECTOR_PUB_STATUS_NOT_RELEASE)
                    .andStatusNotEqualTo(DrConstants.INSPECTOR_STATUS_ABOLISH);
            List<DrOnlineInspector> inspectors = drOnlineInspectorMapper.selectByExample(example);

            DrOnlineInspector inspector = new DrOnlineInspector();
            inspector.setPubStatus(DrConstants.INSPECTOR_PUB_STATUS_RELEASE);
            drOnlineInspectorMapper.updateByExampleSelective(inspector, example);

            //更新pubCount
            updateCount(id, inspectors.size(), 0, 0);
        }

    }

    //生成账号时更新总数
    @Transactional
    public void updateTotalCount(Integer logId){

        DrOnlineInspectorExample inspectorExample = new DrOnlineInspectorExample();
        inspectorExample.createCriteria().andLogIdEqualTo(logId).andStatusNotEqualTo(DrConstants.INSPECTOR_STATUS_ABOLISH);
        List<DrOnlineInspector> inspectors = drOnlineInspectorMapper.selectByExample(inspectorExample);

        DrOnlineInspectorLog record = drOnlineInspectorLogMapper.selectByPrimaryKey(logId);
        record.setTotalCount(inspectors.size());
        drOnlineInspectorLogMapper.updateByPrimaryKeySelective(record);

    }

    @Transactional
    public void updateCount(Integer logId, Integer pubCount, Integer finishCount, Integer totalCount) {

        DrOnlineInspectorLog record = drOnlineInspectorLogMapper.selectByPrimaryKey(logId);

        record.setPubCount(record.getPubCount() + pubCount);
        record.setFinishCount(record.getFinishCount() + finishCount);
        record.setTotalCount(record.getTotalCount() + totalCount);

        drOnlineInspectorLogMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void updatePostIds(Integer id, Integer[] postIds) {

        DrOnlineInspectorLog record = new DrOnlineInspectorLog();
        record.setId(id);
        if (postIds != null && postIds.length > 0) {
            record.setPostIds(StringUtils.join(postIds, ","));
            drOnlineInspectorLogMapper.updateByPrimaryKeySelective(record);
        }else {
            commonMapper.excuteSql("update dr_online_inspector_log set post_ids=null where id="+id);
        }
    }
}
