package service.cadre;

import domain.cadre.CadreCompany;
import domain.cadre.CadreCompanyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadreCompanyService extends BaseMapper {

    @Transactional
    public int insertSelective(CadreCompany record){

        return cadreCompanyMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreCompanyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreCompanyExample example = new CadreCompanyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreCompanyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreCompany record){
        return cadreCompanyMapper.updateByPrimaryKeySelective(record);
    }
}
