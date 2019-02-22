package service.cm;

import domain.cm.CmMember;
import domain.cm.CmMemberExample;
import domain.cm.CmMemberView;
import domain.cm.CmMemberViewExample;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.constants.CmConstants;

import java.util.*;

@Service
public class CmMemberService extends CmBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean idDuplicate(Integer id, Byte type, Integer userId, Boolean isQuit){

        if(type==null || userId==null || isQuit==null) return false;
        if(isQuit) return false;

        CmMemberExample example = new CmMemberExample();
        CmMemberExample.Criteria criteria = example.createCriteria()
                .andTypeEqualTo(type).andIsQuitEqualTo(isQuit)
                .andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cmMemberMapper.countByExample(example) > 0;
    }

    public CmMember get(byte type, int userId, boolean isQuit){

        CmMemberExample example = new CmMemberExample();
        CmMemberExample.Criteria criteria = example.createCriteria()
                .andTypeEqualTo(type).andIsQuitEqualTo(isQuit)
                .andUserIdEqualTo(userId);
        List<CmMember> cmMembers = cmMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return  cmMembers.size()>0?cmMembers.get(0):null;
    }

    @Transactional
    @CacheEvict(value="CmMembers", allEntries = true)
    public void insertSelective(CmMember record){

        Assert.isTrue(!idDuplicate(null, record.getType(), record.getUserId(), record.getIsQuit()), "duplicate");
        record.setSortOrder(getNextSortOrder("cm_member", "type="+record.getType()
                + " and is_quit="+ record.getIsQuit()));
        cmMemberMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CmMembers", allEntries = true)
    public void del(Integer id){

        cmMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="CmMembers", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CmMemberExample example = new CmMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cmMemberMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="CmMembers", allEntries = true)
    public void draw(Integer[] memberIds) {

        if(memberIds ==null || memberIds.length==0) return;

        List<Integer> memberIdList = Arrays.asList(memberIds); // 保证和党委委员的顺序一致
        Collections.reverse(memberIdList);

        for (Integer memberId : memberIdList) {

            CmMember cmMember = cmMemberMapper.selectByPrimaryKey(memberId);
            if(cmMember==null || cmMember.getType()!=CmConstants.CM_MEMBER_TYPE_DW
                    || cmMember.getIsQuit()) continue;

            CmMember record = new CmMember();
            try {
                PropertyUtils.copyProperties(record, cmMember);
            } catch (Exception e) {
                logger.error("异常", e);
            }
            record.setId(null);
            record.setType(CmConstants.CM_MEMBER_TYPE_CW);

            CmMember _check = get(record.getType(), record.getUserId(), record.getIsQuit());
            if(_check==null) {
                insertSelective(record);
            }
        }
    }

    @Transactional
    @CacheEvict(value="CmMembers", allEntries = true)
    public int updateByPrimaryKeySelective(CmMember record){

        if(record.getType()!=null && record.getUserId()!=null && record.getIsQuit()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getType(),
                    record.getUserId(), record.getIsQuit()), "duplicate");

        return cmMemberMapper.updateByPrimaryKeySelective(record);
    }

    // 读取现任常委
    @Cacheable(value="CmMembers")
    public Map<Integer, CmMemberView> committeeMemberMap() {

        CmMemberViewExample example = new CmMemberViewExample();
        example.createCriteria()
                .andTypeEqualTo(CmConstants.CM_MEMBER_TYPE_CW)
                .andIsQuitEqualTo(false);
        example.setOrderByClause("sort_order desc");
        List<CmMemberView> cmMemberes = cmMemberViewMapper.selectByExample(example);
        Map<Integer, CmMemberView> map = new LinkedHashMap<>();
        for (CmMemberView cmMemberView : cmMemberes) {
            map.put(cmMemberView.getId(), cmMemberView);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CmMembers", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        CmMember entity = cmMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Byte type = entity.getType();
        Boolean isQuit = entity.getIsQuit();

        CmMemberExample example = new CmMemberExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andTypeEqualTo(type)
                    .andIsQuitEqualTo(isQuit).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andTypeEqualTo(type)
                    .andIsQuitEqualTo(isQuit).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CmMember> overEntities = cmMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CmMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cm_member", "type="+type + " and is_quit="+ isQuit,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cm_member", "type="+type + " and is_quit="+ isQuit,
                        baseSortOrder, targetEntity.getSortOrder());

            CmMember record = new CmMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cmMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
