package service.cadre;

import domain.cadre.CadreBook;
import domain.cadre.CadreBookExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreBookService extends BaseMapper {


    @Transactional
    public int insertSelective(CadreBook record) {

        return cadreBookMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cadreBookMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreBookExample example = new CadreBookExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreBookMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }
        CadreBookExample example = new CadreBookExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreBookMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreBook record) {
        return cadreBookMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreBook> findAll() {

        CadreBookExample example = new CadreBookExample();
        example.setOrderByClause("id desc");
        List<CadreBook> cadreBookes = cadreBookMapper.selectByExample(example);
        Map<Integer, CadreBook> map = new LinkedHashMap<>();
        for (CadreBook cadreBook : cadreBookes) {
            map.put(cadreBook.getId(), cadreBook);
        }

        return map;
    }


}
