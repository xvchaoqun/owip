package service.sys;

import domain.sys.SysMsg;
import domain.sys.SysMsgExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.sys.SysMsgMapper;
import service.BaseMapper;
import service.global.CacheHelper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class SysMsgService extends BaseMapper {

    @Autowired
    private SysMsgMapper sysMsgMapper;
    @Autowired
    private CacheHelper cacheHelper;

    @Transactional
    @CacheEvict(value = "SysMsgCount", key = "#record.userId")
    public void insertSelective(SysMsg record){

        sysMsgMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SysMsgExample example = new SysMsgExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        List<SysMsg> sysMsgs = sysMsgMapper.selectByExample(example);

        sysMsgMapper.deleteByExample(example);

        for (SysMsg sysMsg : sysMsgs) {
            cacheHelper.clearSysMsgCount(sysMsg.getUserId());
        }
    }

    @Transactional
    @CacheEvict(value = "SysMsgCount", key = "#record.userId")
    public void updateByPrimaryKeySelective(SysMsg record){
       /* if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");*/
        sysMsgMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "SysMsgCount", key = "#userId")
    public int getSysMsgCount(int userId){

        SysMsgExample example = new SysMsgExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(SystemConstants.SYS_MSG_STATUS_UNREAD);
        return (int) sysMsgMapper.countByExample(example);
    }
}
