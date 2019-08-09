package service.ps;

import domain.ps.PsMember;
import domain.ps.PsMemberExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;

@Service
public class PsMemberService extends PsBaseMapper {

    public boolean idDuplicate(Integer id, Integer psId, int userId, Integer type){

        PsMemberExample example = new PsMemberExample();
        PsMemberExample.Criteria criteria = example.createCriteria()
                                            .andUserIdEqualTo(userId)
                                            .andPsIdEqualTo(psId)
                                            .andIsHistoryEqualTo(false)
                                            .andTypeEqualTo(type);
        if(id!=null) criteria.andIdNotEqualTo(id);

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

        PsMember psMember = psMemberMapper.selectByPrimaryKey(id);
        changeOrder("ps_member", String.format("ps_id=%s and is_history=%s",
                psMember.getPsId(), psMember.getIsHistory()), ORDER_BY_ASC, id, addNum);
    }

    @Transactional
    public void updateMemberState(Integer[] ids, String _endDate, Boolean isHistory){
        if(ids==null || ids.length==0) return;

        Date endDate = null;
        if (StringUtils.isNotBlank(_endDate)){
            endDate = DateUtils.parseDate(_endDate,DateUtils.YYYYMM);
        }
        for (Integer id : ids){
            PsMember psMember = new PsMember();
            psMember.setId(id);
            psMember.setEndDate(endDate);
            psMember.setIsHistory(isHistory);
            psMember.setSortOrder(getNextSortOrder("ps_member","is_history="+isHistory));
            psMemberMapper.updateByPrimaryKeySelective(psMember);
        }
    }
}
