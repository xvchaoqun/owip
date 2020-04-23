package service.dr;

import domain.dr.DrOnline;
import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlineInspectorExample;
import domain.dr.DrOnlineResultExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.DrConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrOnlineInspectorService extends DrBaseMapper {

    @Autowired
    private DrOnlineInspectorLogService drOnlineInspectorLogService;

    @Transactional
    public void insertSelective(DrOnlineInspector record){

        drOnlineInspectorMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        drOnlineInspectorMapper.deleteByPrimaryKey(id);
    }

    /*
        删除参评人
        删除结果
        更新参评人log
    * */
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        Integer logId = 0;
        Integer pubCount = 0;
        Integer finishCount = 0;
        Integer totalCount = 0;
        for (int i = 0; i < ids.length; i++){
            DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(ids[i]);
            if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH){
                DrOnlineResultExample example = new DrOnlineResultExample();
                example.createCriteria().andInspectorIdEqualTo(inspector.getId());
                drOnlineResultMapper.deleteByExample(example);
                pubCount--;
                finishCount--;
                totalCount--;
            }else if (inspector.getStatus() != DrConstants.INSPECTOR_STATUS_ABOLISH){
                if (inspector.getPubStatus() == DrConstants.INSPECTOR_PUB_STATUS_RELEASE){
                    pubCount--;
                    totalCount--;
                }else {
                    totalCount--;
                }
            }
            logId = inspector.getLogId();
            drOnlineInspectorMapper.deleteByPrimaryKey(ids[i]);
        }

        drOnlineInspectorLogService.updateCount(logId, pubCount, finishCount, totalCount);

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

    /*
        作废参评人
        删除结果
        更新pubCount、finishCount、totalCount数量
    * */
    @Transactional
    public void cancel(Integer[] ids){

        int pubCount  = 0;
        int finishCount = 0;
        int totalCount = 0;
        Integer logId = null;
        for (Integer id : ids){
            DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(id);
            if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_ABOLISH){
                continue;
            }else if (inspector.getStatus() == DrConstants.DR_ONLINE_FINISH){
                DrOnlineResultExample example = new DrOnlineResultExample();
                example.createCriteria().andInspectorIdEqualTo(id);
                drOnlineResultMapper.deleteByExample(example);
                pubCount--;
                finishCount--;
                totalCount--;
            }else if (inspector.getPubStatus() == DrConstants.INSPECTOR_PUB_STATUS_RELEASE){
                pubCount--;
                totalCount--;
            }else if (inspector.getPubStatus() == DrConstants.INSPECTOR_PUB_STATUS_NOT_RELEASE){
                totalCount--;
            }
            inspector.setStatus(DrConstants.INSPECTOR_STATUS_ABOLISH);
            logId = inspector.getLogId();
            drOnlineInspectorMapper.updateByPrimaryKey(inspector);
        }
        drOnlineInspectorLogService.updateCount(logId, pubCount, finishCount, totalCount);
    }

    //发布账号
    @Transactional
    public void release(Integer[] ids){

        int pubCount = 0;
        Integer logId = null;
        for (Integer id : ids){
            DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(id);
            logId = inspector.getLogId();
            if (inspector.getStatus() != DrConstants.INSPECTOR_STATUS_ABOLISH && inspector.getPubStatus() == DrConstants.INSPECTOR_PUB_STATUS_NOT_RELEASE)
                pubCount++;
            inspector.setPubStatus(DrConstants.INSPECTOR_PUB_STATUS_RELEASE);
            drOnlineInspectorMapper.updateByPrimaryKeySelective(inspector);
            //throw new OpException("事务回滚！");
        }
        drOnlineInspectorLogService.updateCount(logId, pubCount, 0, 0);
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

    //检查状态
    public Boolean checkStatus(DrOnlineInspector record){

        DrOnline drOnline = record.getDrOnline();
        if (record.getStatus() == DrConstants.INSPECTOR_STATUS_ABOLISH || null == drOnline
                || drOnline.getStatus() == DrConstants.DR_ONLINE_WITHDRAW || drOnline.getIsDeleteed())
            return false;

        return true;
    }

}
