package service.dr;

import domain.dr.DrMember;
import domain.dr.DrMemberExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.DrConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class DrMemberService extends DrBaseMapper {

    public DrMember getMember(int id){

        return drMemberMapper.selectByPrimaryKey(id);
    }

    public List<DrMember> getMembers(byte status){

        DrMemberExample example = new DrMemberExample();
        example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");
        return drMemberMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(DrMember record) {

        record.setSortOrder(getNextSortOrder("dr_member", "status=" + record.getStatus()));
        drMemberMapper.insertSelective(record);
    }

    public void abolish(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            DrMember record = new DrMember();
            record.setId(id);
            record.setStatus(DrConstants.DR_MEMBER_STATUS_HISTORY);
            record.setSortOrder(getNextSortOrder("dr_member", "status=" + DrConstants.DR_MEMBER_STATUS_HISTORY));

            drMemberMapper.updateByPrimaryKeySelective(record);
        }
    }

    public void reuse(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            DrMember record = new DrMember();
            record.setId(id);
            record.setStatus(DrConstants.DR_MEMBER_STATUS_NOW);
            record.setSortOrder(getNextSortOrder("dr_member", "status=" + DrConstants.DR_MEMBER_STATUS_NOW));

            drMemberMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        DrMember record = new DrMember();
        record.setStatus(DrConstants.DR_MEMBER_STATUS_DELETE);

        DrMemberExample example = new DrMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        drMemberMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(DrMember record) {
        return drMemberMapper.updateByPrimaryKeySelective(record);
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

        DrMember entity = drMemberMapper.selectByPrimaryKey(id);
        Byte status = entity.getStatus();
        Integer baseSortOrder = entity.getSortOrder();

        DrMemberExample example = new DrMemberExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(status).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andStatusEqualTo(status).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DrMember> overEntities = drMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            DrMember targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("dr_member", "status=" + status, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dr_member", "status=" + status, baseSortOrder, targetEntity.getSortOrder());

            DrMember record = new DrMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            drMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
