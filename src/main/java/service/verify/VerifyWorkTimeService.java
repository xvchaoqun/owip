package service.verify;

import domain.verify.VerifyWorkTime;
import domain.verify.VerifyWorkTimeExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;

import java.util.Arrays;
import java.util.Date;

@Service
public class VerifyWorkTimeService extends BaseMapper {

    public boolean idDuplicate(int cadreId) {

        VerifyWorkTimeExample example = new VerifyWorkTimeExample();
        VerifyWorkTimeExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.VERIFY_STATUS_NORMAL);
        criteria.andCadreIdEqualTo(cadreId);
        return verifyWorkTimeMapper.countByExample(example) > 0;
    }

    // 只能添加正常记录
    @Transactional
    public void insertSelective(VerifyWorkTime record) {

        record.setStatus(SystemConstants.VERIFY_STATUS_NORMAL);
        Assert.isTrue(!idDuplicate(record.getCadreId()), "duplicate cadreId");
        record.setSubmitTime(new Date());
        record.setSubmitIp(ContextHelper.getRealIp());
        record.setSubmitUserId(ShiroHelper.getCurrentUserId());
        verifyWorkTimeMapper.insertSelective(record);
    }

    // 只能更新正常记录
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        VerifyWorkTimeExample example = new VerifyWorkTimeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(SystemConstants.VERIFY_STATUS_NORMAL);

        VerifyWorkTime record = new VerifyWorkTime();
        record.setStatus(SystemConstants.VERIFY_STATUS_DEL);
        record.setUpdateTime(new Date());
        record.setUpdateIp(ContextHelper.getRealIp());
        record.setUpdateUserId(ShiroHelper.getCurrentUserId());

        verifyWorkTimeMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(VerifyWorkTime record) {

        // 更新原记录
        VerifyWorkTime _update = new VerifyWorkTime();
        _update.setId(record.getId());
        _update.setStatus(SystemConstants.VERIFY_STATUS_MODIFY);
        _update.setUpdateTime(new Date());
        _update.setUpdateIp(ContextHelper.getRealIp());
        _update.setUpdateUserId(ShiroHelper.getCurrentUserId());
        verifyWorkTimeMapper.updateByPrimaryKeySelective(_update);

        // 插入新记录
        VerifyWorkTime verifyWorkTime = verifyWorkTimeMapper.selectByPrimaryKey(record.getId());
        record.setId(null);
        record.setCadreId(verifyWorkTime.getCadreId());
        record.setUpdateTime(null);
        record.setUpdateIp(null);
        record.setUpdateUserId(null);
        insertSelective(record);
    }
}
