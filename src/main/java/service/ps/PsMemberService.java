package service.ps;

import domain.ps.PsMember;
import domain.ps.PsMemberExample;
import domain.ps.PsParty;
import domain.ps.PsPartyExample;
import domain.unit.UnitPost;
import domain.unit.UnitPostExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class PsMemberService extends PsBaseMapper {

    public boolean idDuplicate(Integer psId, int userId, Integer type){

        PsMemberExample example = new PsMemberExample();
        /*PsMemberExample.Criteria criteria = */
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andPsIdEqualTo(psId)
                .andIsHistoryEqualTo(false)
                .andTypeEqualTo(type);
        //if(id!=null) criteria.andIdNotEqualTo(id);

        return psMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PsMember record){
        record.setSortOrder(getNextSortOrder("ps_member", "ps_id=" + record.getPsId()));
        psMemberMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        psMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsMemberExample example = new PsMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psMemberMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsMember record){

        psMemberMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        byte orderBy = ORDER_BY_DESC;

        PsMember psMember = psMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = psMember.getSortOrder();

        PsMemberExample example = new PsMemberExample();
        if (addNum * orderBy > 0) {

            example.createCriteria().andPsIdEqualTo(psMember.getPsId()).andIsHistoryEqualTo(psMember.getIsHistory())
                    .andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andPsIdEqualTo(psMember.getPsId()).andIsHistoryEqualTo(psMember.getIsHistory())
                    .andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PsMember> overEntities = psMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PsMember targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum * orderBy > 0)
                commonMapper.downOrder("ps_member", String.format("ps_id=%s and is_history=%s", psMember.getPsId(), psMember.getIsHistory()),
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ps_member", String.format("ps_id=%s and is_history=%s", psMember.getPsId(), psMember.getIsHistory()),
                        baseSortOrder, targetEntity.getSortOrder());

            PsMember record = new PsMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            psMemberMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void history(Integer[] ids, String _endDate){

        if(ids==null || ids.length==0) return;

        PsMemberExample example = new PsMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        PsMember record = new PsMember();
        if (StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate,DateUtils.YYYYMM));
        }
        record.setIsHistory(true);
        psMemberMapper.updateByExampleSelective(record, example);
    }

}
