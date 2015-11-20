package service;

import domain.MemberApply;
import domain.MemberApplyExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberApplyService extends BaseMapper {


    @Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public int insertSelective(MemberApply record) {
        return memberApplyMapper.insertSelective(record);
    }

    @Cacheable(value = "MemberApply", key = "#userId")
    public MemberApply get(int userId) {

        return memberApplyMapper.selectByPrimaryKey(userId);
    }

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public int updateByPrimaryKeySelective(MemberApply record) {

        return memberApplyMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public int updateByExampleSelective(int userId, MemberApply record, MemberApplyExample example){

        return memberApplyMapper.updateByExampleSelective(record, example);
    }
}
