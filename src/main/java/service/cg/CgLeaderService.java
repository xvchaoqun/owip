package service.cg;

import domain.cg.CgLeader;
import domain.cg.CgLeaderExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CgLeaderService extends CgBaseMapper {

    public boolean idDuplicate(Integer id, Integer teamId,Boolean isCurrent){

        if (!isCurrent) return false;
        CgLeaderExample example = new CgLeaderExample();
        CgLeaderExample.Criteria criteria = example.createCriteria()
                .andTeamIdEqualTo(teamId)
                .andIsCurrentEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cgLeaderMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CgLeader record){

        cgLeaderMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cgLeaderMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CgLeaderExample example = new CgLeaderExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cgLeaderMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKey(CgLeader record){

        cgLeaderMapper.updateByPrimaryKey(record);
    }

    public Map<Integer, CgLeader> findAll() {

        CgLeaderExample example = new CgLeaderExample();
        example.createCriteria().andIsCurrentEqualTo(true);
        List<CgLeader> records = cgLeaderMapper.selectByExample(example);
        Map<Integer, CgLeader> map = new LinkedHashMap<>();
        for (CgLeader record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void updatecgLeaderStatus(Integer[] ids, boolean isCurrent){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids){

            CgLeader cgLeader = new CgLeader();
            cgLeader.setId(id);
            cgLeader.setIsCurrent(isCurrent);
            cgLeaderMapper.updateByPrimaryKeySelective(cgLeader);
        }
    }
}
