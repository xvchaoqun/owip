package service.cadre;

import domain.cadre.CadreFamliy;
import domain.cadre.CadreFamliyAbroadExample;
import domain.cadre.CadreFamliyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadreFamliyService extends BaseMapper {

    public CadreFamliy get(int id){

        return cadreFamliyMapper.selectByPrimaryKey(id);
    }
    @Transactional
    public int insertSelective(CadreFamliy record){

        return cadreFamliyMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreFamliyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreFamliyExample example = new CadreFamliyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreFamliyMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }

        {
            // 删除关联的家庭成员移居国（境）外的情况
            CadreFamliyAbroadExample example = new CadreFamliyAbroadExample();
            example.createCriteria().andFamliyIdIn(Arrays.asList(ids));
            cadreFamliyAbroadMapper.deleteByExample(example);
        }

        CadreFamliyExample example = new CadreFamliyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreFamliyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreFamliy record){
        return cadreFamliyMapper.updateByPrimaryKeySelective(record);
    }

}
