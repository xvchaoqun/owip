package service.party;

import domain.party.PartyEva;
import domain.party.PartyEvaExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class PartyEvaService extends BaseMapper {

    public boolean idDuplicate(Integer id, int userId, int year){

        PartyEvaExample example = new PartyEvaExample();
        PartyEvaExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId).andYearEqualTo(year);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyEvaMapper.countByExample(example) > 0;
    }
    public PartyEva get(int userId, int year){

        PartyEvaExample example = new PartyEvaExample();
        PartyEvaExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId).andYearEqualTo(year);

        List<PartyEva> partyEvas = partyEvaMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return partyEvas.size()>0?partyEvas.get(0):null;
    }

    @Transactional
    public void insertSelective(PartyEva record){

        Assert.isTrue(!idDuplicate(null, record.getUserId(), record.getYear()), "duplicate");
        partyEvaMapper.insertSelective(record);
    }

    @Transactional
    public int batchImport(List<PartyEva> records) {

        int addCount = 0;
        for (PartyEva record : records) {

            int userId = record.getUserId();
            int year = record.getYear();
            PartyEva partyEva = get(userId, year);
            if(partyEva==null) {
                insertSelective(record);
                addCount++;
            }else{
                int id = partyEva.getId();
                record.setId(id);
                updateByPrimaryKeySelective(record);

                /*if(StringUtils.isBlank(record.getTitle())){
                    commonMapper.excuteSql("update cadre_eva set title = null where id="+id);
                }*/
            }
        }

        return addCount;
    }

    @Transactional
    public void del(Integer id){

        partyEvaMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyEvaExample example = new PartyEvaExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyEvaMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PartyEva record){
        if(record.getUserId()!=null && record.getType()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getUserId(), record.getYear()), "duplicate");

        return partyEvaMapper.updateByPrimaryKeySelective(record);
    }
}
