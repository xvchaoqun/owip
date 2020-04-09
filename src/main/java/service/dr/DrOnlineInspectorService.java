package service.dr;

import controller.global.OpException;
import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlineInspectorExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.constants.DrConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrOnlineInspectorService extends DrBaseMapper {

    @Autowired
    private DrOnlineInspectorLogService drOnlineInspectorLogService;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        DrOnlineInspectorExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return drOnlineInspectorMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DrOnlineInspector record){

        drOnlineInspectorMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        drOnlineInspectorMapper.deleteByPrimaryKey(id);
    }

    //todo
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        Integer logId = 0;
        Integer pubCount = 0;
        for (int i = 0; i < ids.length; i++){
            DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(ids[i]);
            if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH){
                throw new OpException("不能删除已完成测评账号！");
            }
            if (inspector.getPubStatus() == DrConstants.INSPECTOR_PUB_STATUS_RELEASE){
                pubCount = 1;
            }
            logId = inspector.getLogId();
            drOnlineInspectorMapper.deleteByPrimaryKey(ids[i]);

            drOnlineInspectorLogService.updateTotalCount(logId);
            drOnlineInspectorLogService.updatePubCount(logId);
            drOnlineInspectorLogService.updateFinishCount(logId);

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

    //作废测评账号；已完成和暂存的也可作废，同时，作废结果一条
    //todo
    @Transactional
    public void cancel(Integer[] ids){

        for (Integer id : ids){
            DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(id);
            if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH) {
                throw new OpException("不能作废已完成测评账号!");
            }
            inspector.setStatus(DrConstants.INSPECTOR_STATUS_ABOLISH);

            drOnlineInspectorMapper.updateByPrimaryKey(inspector);
        }
    }

    //单个发布账号
    @Transactional
    public void release(Integer[] ids){

        for (Integer id : ids){
            DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(id);
            inspector.setPubStatus(DrConstants.INSPECTOR_PUB_STATUS_RELEASE);
            drOnlineInspectorMapper.updateByPrimaryKeySelective(inspector);
            drOnlineInspectorLogService.updatePubCount(inspector.getLogId());
        }



    }


    public DrOnlineInspector findByName(String username){

        username = StringUtils.trimToNull(username);

        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria().andUsernameEqualTo(username).andStatusNotEqualTo(DrConstants.INSPECTOR_STATUS_ABOLISH);
        List<DrOnlineInspector> inspectors = drOnlineInspectorMapper.selectByExample(example);

        return inspectors.size() > 0 ? inspectors.get(0) : null;
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
    public void updateByExampleSelectiveBeforeSubmit(Integer inspectorId, DrOnlineInspector record){

        List<Byte> canUpdateStatusList = new ArrayList<>();

        canUpdateStatusList.add(DrConstants.INSPECTOR_STATUS_SAVE);
        canUpdateStatusList.add(DrConstants.INSPECTOR_STATUS_INIT);
        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        example.createCriteria().andIdEqualTo(inspectorId).andStatusIn(canUpdateStatusList);

        drOnlineInspectorMapper.updateByExampleSelective(record, example);
    }

}
