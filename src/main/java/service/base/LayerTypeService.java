package service.base;

import controller.global.OpException;
import domain.base.LayerType;
import domain.base.LayerTypeExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LayerTypeService extends BaseMapper {

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "LayerTypes", key = "#record.code"),
        @CacheEvict(value = "LayerTypes")
    })
    public void insertSelective(LayerType record){

        Integer fid = record.getFid();
        String whereSql = null;
        if(fid!=null){
            whereSql = "fid="+ fid;
        }else{
            String code = record.getCode();
            if(StringUtils.isBlank(code)){
                throw new OpException("类别代码不能为空");
            }
            LayerType topLayerType = getTopLayerType(code);
            if(topLayerType!=null){
                throw new OpException("类别代码重复");
            }
        }

        record.setSortOrder(getNextSortOrder("base_layer_type", whereSql));
        layerTypeMapper.insertSelective(record);

        updateChildrenNum(fid);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "LayerTypes", key = "#result.code"),
        @CacheEvict(value = "LayerTypes")
    })
    public LayerType del(Integer id){

        LayerType layerType = layerTypeMapper.selectByPrimaryKey(id);
        layerTypeMapper.deleteByPrimaryKey(id);
        updateChildrenNum(layerType.getFid());

        return layerType;
    }

    @Transactional
    @CacheEvict(value="LayerTypes", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            del(id);
        }
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "LayerTypes", key = "#record.code"),
        @CacheEvict(value = "LayerTypes")
    })
    public void updateByPrimaryKeySelective(LayerType record){

        layerTypeMapper.updateByPrimaryKeySelective(record);
    }

    // 读取两层分类
    @Cacheable(value="LayerTypes", key = "#code")
    public List<LayerType> findAll(String code) {

        LayerType topLayerType = getTopLayerType(code);
        if(topLayerType==null) return null;

        List<LayerType> layerTypes = getChildren(topLayerType.getId());
        for (LayerType type : layerTypes) {
            type.setChildren(getChildren(type.getId()));
        }
        
        return layerTypes;
    }

    @Cacheable(value="LayerTypes")
    public Map<Integer, LayerType> findAll() {

        List<LayerType> layerTypes = layerTypeMapper.selectByExample(new LayerTypeExample());
        Map<Integer, LayerType> layerTypeMap = new HashMap<>();
        for (LayerType type : layerTypes) {
            layerTypeMap.put(type.getId(), type);
        }

        return layerTypeMap;
    }

    // 获取顶级类别
    public LayerType getTopLayerType(String code){

        LayerType layerType = null;
        {
            if (StringUtils.isNotBlank(code)) {
                LayerTypeExample example = new LayerTypeExample();
                example.createCriteria().andCodeEqualTo(code).andFidIsNull();
                List<LayerType> layerTypes = layerTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
                if (layerTypes.size() == 1) {
                    layerType = layerTypes.get(0);
                }
            }
        }
        return layerType;
    }

    public List<LayerType> getChildren(int fid){
        
        LayerTypeExample example = new LayerTypeExample();
        example.createCriteria().andFidEqualTo(fid);
        example.setOrderByClause("sort_order asc");
        return layerTypeMapper.selectByExample(example);
    }

    public void updateChildrenNum(Integer fid){

        if(fid==null) return;
        LayerTypeExample example = new LayerTypeExample();
        example.createCriteria().andFidEqualTo(fid);
        int num = (int) layerTypeMapper.countByExample(example);

        LayerType record = new LayerType();
        record.setId(fid);
        record.setNum(num);
        layerTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @CacheEvict(value = "LayerTypes", allEntries = true)
    public void changeOrder(int id, int addNum) {

        String whereSql = null;
        LayerType layerType = layerTypeMapper.selectByPrimaryKey(id);
        if(layerType!=null && layerType.getFid()!=null){
            whereSql = "fid="+layerType.getFid();
        }
        changeOrder("base_layer_type", whereSql, ORDER_BY_ASC, id, addNum);
    }
}
