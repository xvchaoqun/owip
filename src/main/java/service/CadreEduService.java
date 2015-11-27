package service;

import domain.CadreEdu;
import domain.CadreEduExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class CadreEduService extends BaseMapper {


    public CadreEdu getHighEdu(int cadreId){

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIsHighEduEqualTo(true);
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(cadreEdus.size()>0) return  cadreEdus.get(0);
        return null;
    }
    public CadreEdu getHighDegree(int cadreId){

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIsHighDegreeEqualTo(true);
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(cadreEdus.size()>0) return  cadreEdus.get(0);
        return null;
    }

    @Transactional
    public int insertSelective(CadreEdu record){

        return cadreEduMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreEduMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreEduMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreEdu record){
        return cadreEduMapper.updateByPrimaryKeySelective(record);
    }
}
