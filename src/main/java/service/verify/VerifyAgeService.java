package service.verify;

import domain.verify.VerifyAge;
import domain.verify.VerifyAgeExample;
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
public class VerifyAgeService extends BaseMapper {

    public boolean idDuplicate(int cadreId) {

        // 正式记录只有一条
        VerifyAgeExample example = new VerifyAgeExample();
        VerifyAgeExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.VERIFY_STATUS_NORMAL);
        criteria.andCadreIdEqualTo(cadreId);
        return verifyAgeMapper.countByExample(example) > 0;
    }

    // 只能添加正常记录
    @Transactional
    public void insertSelective(VerifyAge record) {


        record.setStatus(SystemConstants.VERIFY_STATUS_NORMAL);
        Assert.isTrue(!idDuplicate(record.getCadreId()));
        record.setSubmitTime(new Date());
        record.setSubmitIp(ContextHelper.getRealIp());
        record.setSubmitUserId(ShiroHelper.getCurrentUserId());
        verifyAgeMapper.insertSelective(record);
    }

    // 只能更新正常记录
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        VerifyAgeExample example = new VerifyAgeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(SystemConstants.VERIFY_STATUS_NORMAL);

        VerifyAge record = new VerifyAge();
        record.setStatus(SystemConstants.VERIFY_STATUS_DEL);
        record.setUpdateTime(new Date());
        record.setUpdateIp(ContextHelper.getRealIp());
        record.setUpdateUserId(ShiroHelper.getCurrentUserId());

        verifyAgeMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(VerifyAge record) {

        // 更新原记录
        VerifyAge _update = new VerifyAge();
        _update.setId(record.getId());
        _update.setStatus(SystemConstants.VERIFY_STATUS_MODIFY);
        _update.setUpdateTime(new Date());
        _update.setUpdateIp(ContextHelper.getRealIp());
        _update.setUpdateUserId(ShiroHelper.getCurrentUserId());
        verifyAgeMapper.updateByPrimaryKeySelective(_update);

        // 插入新记录
        VerifyAge verifyAge = verifyAgeMapper.selectByPrimaryKey(record.getId());
        record.setId(null);
        record.setCadreId(verifyAge.getCadreId());
        record.setUpdateTime(null);
        record.setUpdateIp(null);
        record.setUpdateUserId(null);
        insertSelective(record);
    }
}
