package service.cet;

import domain.cet.CetSpecial;
import domain.cet.CetSpecialExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.Date;

@Service
public class CetSpecialService extends BaseMapper {


    @Transactional
    public void insertSelective(CetSpecial record){

        record.setCreateTime(new Date());
        cetSpecialMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetSpecialExample example = new CetSpecialExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetSpecialMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetSpecial record){

        return cetSpecialMapper.updateByPrimaryKeySelective(record);
    }
}
