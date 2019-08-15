package service.pm;

import domain.member.Member;
import domain.pm.PmQuarter;
import domain.pm.PmQuarterExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.party.MemberService;
import service.party.PartyMemberService;
import shiro.ShiroHelper;
import sys.constants.PmConstants;

import java.util.*;


@Service
public class PmQuarterService extends PmBaseMapper {
    @Autowired
    MemberService memberService;
    @Autowired
    PmMeetingService pmMeetingService;
    @Autowired
    PartyMemberService partyMemberService;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PmQuarterExample example = new PmQuarterExample();
        PmQuarterExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pmQuarterMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(Integer partyId,Byte type){
        Date date=new Date();
        PmQuarter record=new PmQuarter();
        record.setYear(pmMeetingService.getYear(date));
        record.setQuarter(pmMeetingService.getQuarter(date));
        record.setPartyId(partyId);
        if(type==1){
            record.setType(PmConstants.PARTY_QUARTER_PARTY);
        }else{
            record.setType(PmConstants.PARTY_QUARTER_BRANCH);
        }

        //record.setNum();
        //record.setDueNum();
        //record.setFinishNum();
        pmQuarterMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PmQuarter:ALL", allEntries = true)
    public void del(Integer id){

        pmQuarterMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PmQuarter:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PmQuarterExample example = new PmQuarterExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmQuarterMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PmQuarter:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(PmQuarter record){

        pmQuarterMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PmQuarter:ALL")
    public Map<Integer, PmQuarter> findAll() {

        PmQuarterExample example = new PmQuarterExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PmQuarter> records = pmQuarterMapper.selectByExample(example);
        Map<Integer, PmQuarter> map = new LinkedHashMap<>();
        for (PmQuarter record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "PmQuarter:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("pm_quarter", null, ORDER_BY_DESC, id, addNum);
    }
}
