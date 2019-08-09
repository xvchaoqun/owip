package service.cg;

import domain.cg.CgTeam;
import domain.cg.CgTeamExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CgTeamService extends CgBaseMapper {

    @Transactional
    public void insertSelective(CgTeam record){

        record.setSortOrder(getNextSortOrder("cg_team", "is_current="+record.getIsCurrent()));
        cgTeamMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cgTeamMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CgTeamExample example = new CgTeamExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cgTeamMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CgTeam record){
        cgTeamMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("cg_team", null, ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateTeamState(Integer[] ids){

        for (Integer id : ids){

            CgTeam record = new CgTeam();
            record.setId(id);
            record.setIsCurrent(false);
            record.setSortOrder(getNextSortOrder("cg_team", "is_current="+record.getIsCurrent()));
            cgTeamMapper.updateByPrimaryKeySelective(record);
        }
    }
}
