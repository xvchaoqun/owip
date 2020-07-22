package service.dr;

import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlineInspectorExample;
import domain.dr.DrOnlineResultExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.DrConstants;

import java.util.*;

@Service
public class DrOnlineInspectorService extends DrBaseMapper {

    @Transactional
    public void insertSelective(DrOnlineInspector record){

        drOnlineInspectorMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        drOnlineInspectorMapper.deleteByPrimaryKey(id);
    }

    // 删除参评人
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        Set<Integer> logIdSet = new HashSet<>();

        for (int id:ids){

            DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(id);

            drOnlineInspectorMapper.deleteByPrimaryKey(id);
            logIdSet.add(inspector.getLogId());
        }

        for (int logId : logIdSet) {
            iDrMapper.refreshInspectorLogCount(logId);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(DrOnlineInspector record){

        drOnlineInspectorMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DrOnlineInspector> findAll() {

        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria();
        List<DrOnlineInspector> records = drOnlineInspectorMapper.selectByExample(example);
        Map<Integer, DrOnlineInspector> map = new LinkedHashMap<>();
        for (DrOnlineInspector record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    public String buildUsername(){

        String username = RandomStringUtils.random(5, "abcdefghigklmnopqrstuvwxyz")
                + RandomStringUtils.random(3, "12345678");
        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria().andUsernameEqualTo(username);
        while (drOnlineInspectorMapper.countByExample(example) > 0){
            username = RandomStringUtils.random(5, "abcdefghigklmnopqrstuvwxyz")
                    + RandomStringUtils.random(3, "12345678");
        }

        return username;

    }

    // 作废参评人
    @Transactional
    public void cancel(Integer[] ids){

        Set<Integer> logIdSet = new HashSet<>();
        for (Integer id : ids){
            DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(id);
            if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_ABOLISH){
                continue;
            }else if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH){
                DrOnlineResultExample example = new DrOnlineResultExample();
                example.createCriteria().andInspectorIdEqualTo(id);
                drOnlineResultMapper.deleteByExample(example);
            }

            inspector.setStatus(DrConstants.INSPECTOR_STATUS_ABOLISH);
            drOnlineInspectorMapper.updateByPrimaryKey(inspector);

            logIdSet.add(inspector.getLogId());
        }

        for (int logId : logIdSet) {
            iDrMapper.refreshInspectorLogCount(logId);
        }
    }

    public DrOnlineInspector tryLogin(String username, String passwd){

        username = StringUtils.trimToNull(username);
        passwd = StringUtils.trimToNull(passwd);

        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswdEqualTo(passwd);
        List<DrOnlineInspector> inspectors = drOnlineInspectorMapper.selectByExample(example);

        return inspectors.size() > 0 ? inspectors.get(0) : null;
    }

    @Transactional
    public void changePasswd(Integer id, String passwd, Byte passwdChangeType){

        DrOnlineInspector inspector = new DrOnlineInspector();
        inspector.setId(id);
        inspector.setPasswd(passwd);
        inspector.setPasswdChangeType(passwdChangeType);
        drOnlineInspectorMapper.updateByPrimaryKeySelective(inspector);
    }

    //提交暂存数据    inspector
    @Transactional
    public void updateByExampleSelectiveBeforeSubmit(DrOnlineInspector record){

        List<Byte> statusList = new ArrayList<>();

        statusList.add(DrConstants.INSPECTOR_STATUS_SAVE);
        statusList.add(DrConstants.INSPECTOR_STATUS_INIT);
        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria().andIdEqualTo(record.getId()).andStatusIn(statusList);

        drOnlineInspectorMapper.updateByExampleSelective(record, example);
    }

    public List<DrOnlineInspector> findByLogId(Integer logId){

        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria().andLogIdEqualTo(logId);

        return drOnlineInspectorMapper.selectByExample(example);

    }
}
