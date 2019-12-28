package service.sys;

import domain.sys.SysSync;
import domain.sys.SysSyncExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SysSyncService extends BaseMapper {

    public boolean lastSyncIsNotStop(byte type) {

        SysSyncExample example = new SysSyncExample();
        example.createCriteria()
                .andTypeEqualTo(type).andIsStopEqualTo(false);

        return sysSyncMapper.countByExample(example) > 0;
    }

    // 停止一类任务
    public void stopAll(byte type){

        SysSyncExample example = new SysSyncExample();
        example.createCriteria()
                .andTypeEqualTo(type).andIsStopEqualTo(false);
        List<SysSync> sysSyncs = sysSyncMapper.selectByExample(example);
        for (SysSync sysSync : sysSyncs) {

            stop(sysSync.getId());
        }
    }

    // 停止一个任务
    public void stop(int id){

        SysSync record = new SysSync();
        record.setId(id);
        record.setIsStop(true);
        record.setEndTime(new Date());
        record.setAutoStop(false);
        updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int insertSelective(SysSync record) {

        Assert.isTrue(!lastSyncIsNotStop(record.getType()), "last sync is not stop.");
        return sysSyncMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        sysSyncMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        SysSyncExample example = new SysSyncExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        sysSyncMapper.deleteByExample(example);
    }

    public boolean isStop(int syncId) {
        SysSync sysSync = sysSyncMapper.selectByPrimaryKey(syncId);
        return sysSync.getIsStop();
    }

    @Transactional
    public boolean updateByPrimaryKeySelective(SysSync record) {
        sysSyncMapper.updateByPrimaryKeySelective(record);

        SysSync sysSync = sysSyncMapper.selectByPrimaryKey(record.getId());
        return sysSync.getIsStop();
    }
}
