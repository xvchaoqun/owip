package service.verify;

import domain.member.Member;
import domain.sys.SysUserView;
import domain.verify.VerifyGrowTime;
import domain.verify.VerifyGrowTimeExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.VerifyConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import java.util.*;

@Service
public class VerifyGrowTimeService extends VerifyBaseMapper {

    public boolean idDuplicate(Integer cadreId){

        VerifyGrowTimeExample example = new VerifyGrowTimeExample();
        VerifyGrowTimeExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(VerifyConstants.VERIFY_STATUS_NORMAL);

        return verifyGrowTimeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(VerifyGrowTime record){

        SysUserView uv = CmTag.getUserByCode(CmTag.getCadreById(record.getCadreId()).getCode());
        Member member = memberMapper.selectByPrimaryKey(uv.getUserId());
        if (null != member && null == record.getOldGrowTime())
            record.setOldGrowTime(member.getGrowTime());

        record.setStatus(VerifyConstants.VERIFY_STATUS_NORMAL);
        record.setSubmitIp(ContextHelper.getRealIp());
        record.setSubmitTime(new Date());
        record.setSubmitUserId(ShiroHelper.getCurrentUserId());
        verifyGrowTimeMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        verifyGrowTimeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        VerifyGrowTimeExample example = new VerifyGrowTimeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(VerifyConstants.VERIFY_STATUS_NORMAL);

        VerifyGrowTime record = new VerifyGrowTime();
        record.setStatus(VerifyConstants.VERIFY_STATUS_DEL);
        record.setUpdateIp(ContextHelper.getRealIp());
        record.setUpdateTime(new Date());
        record.setUpdateUserId(ShiroHelper.getCurrentUserId());
        verifyGrowTimeMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(VerifyGrowTime record){

        verifyGrowTimeMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, VerifyGrowTime> findAll() {

        VerifyGrowTimeExample example = new VerifyGrowTimeExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<VerifyGrowTime> records = verifyGrowTimeMapper.selectByExample(example);
        Map<Integer, VerifyGrowTime> map = new LinkedHashMap<>();
        for (VerifyGrowTime record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void update(VerifyGrowTime record) {

        // 更新原记录
        VerifyGrowTime _update = new VerifyGrowTime();
        _update.setId(record.getId());
        _update.setStatus(VerifyConstants.VERIFY_STATUS_MODIFY);
        _update.setUpdateTime(new Date());
        _update.setUpdateIp(ContextHelper.getRealIp());
        _update.setUpdateUserId(ShiroHelper.getCurrentUserId());
        verifyGrowTimeMapper.updateByPrimaryKeySelective(_update);

        // 插入新记录
        VerifyGrowTime verifyGrowTime = verifyGrowTimeMapper.selectByPrimaryKey(record.getId());
        record.setId(null);
        record.setCadreId(verifyGrowTime.getCadreId());
        record.setUpdateTime(null);
        record.setUpdateIp(null);
        record.setUpdateUserId(null);
        insertSelective(record);
    }
}
