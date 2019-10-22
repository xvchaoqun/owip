package service.sp;

import domain.cadre.CadreView;
import domain.sp.SpDp;
import domain.sp.SpDpExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpDpService extends SpBaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){

        SpDpExample example = new SpDpExample();
        SpDpExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return spDpMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(SpDp record){

        record.setSortOrder(getNextSortOrder("sp_dp",null));
        spDpMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        spDpMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SpDpExample example = new SpDpExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        spDpMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SpDp record){

        spDpMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, SpDp> findAll() {

        SpDpExample example = new SpDpExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<SpDp> records = spDpMapper.selectByExample(example);
        Map<Integer, SpDp> map = new LinkedHashMap<>();
        for (SpDp record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("sp_dp", null, ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateRecord(SpDp spDp){

        spDp.setIsCadre(false);
        CadreView cadre = CmTag.getCadreByUserId(spDp.getUserId());

        if (cadre != null) {

            spDp.setIsCadre(isCurrentCadre(cadre.getStatus()));
            spDp.setAdminPost(cadre.getTitle());
        }
    }

    /*public List getLayerType(String code){

        List<LayerType> layerTypes = CmTag.getLayerTypes("lt_spDp");

        return layerTypes;
    }*/

}
