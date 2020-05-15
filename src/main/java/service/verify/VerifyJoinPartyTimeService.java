package service.verify;

import domain.member.Member;
import domain.sys.SysUserView;
import domain.verify.VerifyJoinPartyTime;
import domain.verify.VerifyJoinPartyTimeExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.VerifyConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import java.util.*;

@Service
public class VerifyJoinPartyTimeService extends VerifyBaseMapper {

    public boolean idDuplicate(Integer cadreId){

        VerifyJoinPartyTimeExample example = new VerifyJoinPartyTimeExample();
        VerifyJoinPartyTimeExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(VerifyConstants.VERIFY_STATUS_NORMAL);

        return verifyJoinPartyTimeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(VerifyJoinPartyTime record){

        SysUserView uv = CmTag.getUserByCode(CmTag.getCadreById(record.getCadreId()).getCode());
        Member member = memberMapper.selectByPrimaryKey(uv.getUserId());
        if (null != member && null == record.getOldJoinTime())
            record.setOldJoinTime(member.getGrowTime());

        record.setStatus(VerifyConstants.VERIFY_STATUS_NORMAL);
        record.setSubmitIp(ContextHelper.getRealIp());
        record.setSubmitTime(new Date());
        record.setSubmitUserId(ShiroHelper.getCurrentUserId());
        verifyJoinPartyTimeMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        verifyJoinPartyTimeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        VerifyJoinPartyTimeExample example = new VerifyJoinPartyTimeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(VerifyConstants.VERIFY_STATUS_NORMAL);

        VerifyJoinPartyTime record = new VerifyJoinPartyTime();
        record.setStatus(VerifyConstants.VERIFY_STATUS_DEL);
        record.setUpdateIp(ContextHelper.getRealIp());
        record.setUpdateTime(new Date());
        record.setUpdateUserId(ShiroHelper.getCurrentUserId());
        verifyJoinPartyTimeMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(VerifyJoinPartyTime record){

        verifyJoinPartyTimeMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, VerifyJoinPartyTime> findAll() {

        VerifyJoinPartyTimeExample example = new VerifyJoinPartyTimeExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<VerifyJoinPartyTime> records = verifyJoinPartyTimeMapper.selectByExample(example);
        Map<Integer, VerifyJoinPartyTime> map = new LinkedHashMap<>();
        for (VerifyJoinPartyTime record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void update(VerifyJoinPartyTime record) {

        // 更新原记录
        VerifyJoinPartyTime _update = new VerifyJoinPartyTime();
        _update.setId(record.getId());
        _update.setStatus(VerifyConstants.VERIFY_STATUS_MODIFY);
        _update.setUpdateTime(new Date());
        _update.setUpdateIp(ContextHelper.getRealIp());
        _update.setUpdateUserId(ShiroHelper.getCurrentUserId());
        verifyJoinPartyTimeMapper.updateByPrimaryKeySelective(_update);

        // 插入新记录
        VerifyJoinPartyTime verifyJoinPartyTime = verifyJoinPartyTimeMapper.selectByPrimaryKey(record.getId());
        record.setId(null);
        record.setCadreId(verifyJoinPartyTime.getCadreId());
        record.setUpdateTime(null);
        record.setUpdateIp(null);
        record.setUpdateUserId(null);
        insertSelective(record);
    }
}
