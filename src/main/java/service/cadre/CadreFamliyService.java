package service.cadre;

import controller.global.OpException;
import domain.cadre.CadreFamliy;
import domain.cadre.CadreFamliyAbroadExample;
import domain.cadre.CadreFamliyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.CadreConstants;

import java.util.Arrays;

@Service
public class CadreFamliyService extends BaseMapper {

    public CadreFamliy get(int id){

        return cadreFamliyMapper.selectByPrimaryKey(id);
    }

    public void addCheck(int cadreId, byte title){

        if(title== CadreConstants.CADRE_FAMLIY_TITLE_FATHER
                || title == CadreConstants.CADRE_FAMLIY_TITLE_MOTHER) {
            CadreFamliyExample example = new CadreFamliyExample();
            CadreFamliyExample.Criteria criteria = example.createCriteria().andTitleEqualTo(title);
            criteria.andCadreIdEqualTo(cadreId);

            if(cadreFamliyMapper.countByExample(example)>0){
                throw new OpException(CadreConstants.CADRE_FAMLIY_TITLE_MAP.get(title)+"已经添加了，请不要重复添加");
            }
        }

        CadreFamliyExample example = new CadreFamliyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        if(cadreFamliyMapper.countByExample(example)>=6){
            throw new OpException("最多只允许添加6个家庭成员");
        }
    }

    public void updateCheck(int id, int cadreId, byte title){

        if(title== CadreConstants.CADRE_FAMLIY_TITLE_FATHER
                || title == CadreConstants.CADRE_FAMLIY_TITLE_MOTHER) {
            CadreFamliyExample example = new CadreFamliyExample();
            CadreFamliyExample.Criteria criteria = example.createCriteria().andTitleEqualTo(title);
            criteria.andCadreIdEqualTo(cadreId);
            criteria.andIdNotEqualTo(id);

            if(cadreFamliyMapper.countByExample(example)>0){
                throw new OpException(CadreConstants.CADRE_FAMLIY_TITLE_MAP.get(title)+"已经添加了，请不要重复添加");
            }
        }
    }

    @Transactional
    public int insertSelective(CadreFamliy record){

        addCheck(record.getCadreId(), record.getTitle());

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

        if(record.getTitle()!=null)
            updateCheck(record.getId(), record.getCadreId(), record.getTitle());

        record.setCadreId(null);
        return cadreFamliyMapper.updateByPrimaryKeySelective(record);
    }

}
