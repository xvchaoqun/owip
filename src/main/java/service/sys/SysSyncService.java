package service.sys;

import domain.sys.SysSync;
import domain.sys.SysSyncExample;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class SysSyncService extends BaseMapper {

    public boolean lastSyncIsNotStop(byte type) {

        SysSyncExample example = new SysSyncExample();
        example.createCriteria()
                .andTypeEqualTo(type).andIsStopEqualTo(false);

        return sysSyncMapper.countByExample(example) > 0;
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

    @Cacheable(value = "syncIsStop", key = "#syncId")
    public boolean isStop(int syncId) {
        SysSync sysSync = sysSyncMapper.selectByPrimaryKey(syncId);
        return sysSync.getIsStop();
    }

    @CachePut(value = "syncIsStop", key = "#record.id")
    @Transactional
    public boolean updateByPrimaryKeySelective(SysSync record) {
        sysSyncMapper.updateByPrimaryKeySelective(record);

        SysSync sysSync = sysSyncMapper.selectByPrimaryKey(record.getId());
        return sysSync.getIsStop();
    }
}
