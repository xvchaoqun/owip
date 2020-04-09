package service.dr;

import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlineInspectorExample;
import domain.dr.DrOnlineInspectorLog;
import domain.dr.DrOnlineInspectorLogExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.unit.UnitService;
import sys.constants.DrConstants;

import java.util.*;

@Service
public class DrOnlineInspectorLogService extends DrBaseMapper {

    @Autowired
    protected DrOnlineInspectorService drOnlineInspectorService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private DrOnlineInspectorTypeService drOnlineInspectorTypeService;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DrOnlineInspectorLogExample example = new DrOnlineInspectorLogExample();
        DrOnlineInspectorLogExample.Criteria criteria = example.createCriteria();
        if (id != null) criteria.andIdNotEqualTo(id);

        return drOnlineInspectorLogMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DrOnlineInspectorLog record) {

        drOnlineInspectorLogMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        drOnlineInspectorLogMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        DrOnlineInspectorExample inspectorExample = new DrOnlineInspectorExample();
        inspectorExample.createCriteria().andLogIdIn(Arrays.asList(ids));
        drOnlineInspectorMapper.deleteByExample(inspectorExample);

        DrOnlineInspectorLogExample example = new DrOnlineInspectorLogExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlineInspectorLogMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DrOnlineInspectorLog record) {
        drOnlineInspectorLogMapper.updateByPrimaryKeySelective(record);
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
        example.createCriteria().andTypeIdEqualTo(inspectorTypeId).andUnitIdEqualTo(unitId).andOnlineIdEqualTo(onlineId);
        List<DrOnlineInspectorLog> inspectorLogs = drOnlineInspectorLogMapper.selectByExample(example);
        if (count > 0){
            if (inspectorLogs != null && !inspectorLogs.isEmpty()){
                DrOnlineInspectorLog drOnlineInspectorLog = inspectorLogs.get(0);
                logId = drOnlineInspectorLog.getId();
                if (type == 1){
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
            example.createCriteria().andLogIdEqualTo(id).andPubStatusEqualTo(DrConstants.INSPECTOR_PUB_STATUS_NOT_RELEASE);

            DrOnlineInspector inspector = new DrOnlineInspector();
            inspector.setPubStatus(DrConstants.INSPECTOR_PUB_STATUS_RELEASE);
            int pubCount = drOnlineInspectorMapper.updateByExampleSelective(inspector, example);

            updateTotalCount(id);
            updatePubCount(id);
            updateFinishCount(id);
        }

    }

    //更新总数
    @Transactional
    public void updateTotalCount(Integer logId){

        DrOnlineInspectorExample inspectorExample = new DrOnlineInspectorExample();
        inspectorExample.createCriteria().andLogIdEqualTo(logId);
        List<DrOnlineInspector> inspectors = drOnlineInspectorMapper.selectByExample(inspectorExample);

        DrOnlineInspectorLog record = drOnlineInspectorLogMapper.selectByPrimaryKey(logId);
        record.setTotalCount(inspectors.size());
        updateByPrimaryKeySelective(record);

    }

    //更新已分发数目
    @Transactional
    public void updatePubCount(Integer logId){
        DrOnlineInspectorExample inspectorExample = new DrOnlineInspectorExample();
        inspectorExample.createCriteria().andLogIdEqualTo(logId).andPubStatusEqualTo(DrConstants.INSPECTOR_PUB_STATUS_RELEASE);
        List<DrOnlineInspector> inspectors = drOnlineInspectorMapper.selectByExample(inspectorExample);

        DrOnlineInspectorLog record = drOnlineInspectorLogMapper.selectByPrimaryKey(logId);
        record.setPubCount(inspectors.size());
        drOnlineInspectorLogMapper.updateByPrimaryKeySelective(record);
    }

    //更新已完成数目
    @Transactional
    public void updateFinishCount(Integer logId){
        DrOnlineInspectorExample inspectorExample = new DrOnlineInspectorExample();
        inspectorExample.createCriteria().andLogIdEqualTo(logId).andStatusEqualTo(DrConstants.INSPECTOR_STATUS_FINISH);
        List<DrOnlineInspector> inspectors = drOnlineInspectorMapper.selectByExample(inspectorExample);

        DrOnlineInspectorLog record = drOnlineInspectorLogMapper.selectByPrimaryKey(logId);
        record.setFinishCount(inspectors.size());
        updateByPrimaryKeySelective(record);
    }

}
