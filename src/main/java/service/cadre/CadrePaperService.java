package service.cadre;

import domain.cadre.CadrePaper;
import domain.cadre.CadrePaperExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadrePaperService extends BaseMapper {


    @Transactional
    public int insertSelective(CadrePaper record) {

        return cadrePaperMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cadrePaperMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CadrePaperExample example = new CadrePaperExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePaperMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadrePaper record) {
        return cadrePaperMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadrePaper> findAll() {

        CadrePaperExample example = new CadrePaperExample();
        example.setOrderByClause("id desc");
        List<CadrePaper> cadrePaperes = cadrePaperMapper.selectByExample(example);
        Map<Integer, CadrePaper> map = new LinkedHashMap<>();
        for (CadrePaper cadrePaper : cadrePaperes) {
            map.put(cadrePaper.getId(), cadrePaper);
        }

        return map;
    }


}
