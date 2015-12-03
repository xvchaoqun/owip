package service.sys;

import domain.Location;
import domain.LocationExample;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.*;

/**
 * Created by fafa on 2015/12/2.
 */
@Service
public class LocationService extends BaseMapper{


    public boolean idDuplicate(Integer id, Integer code){

        LocationExample example = new LocationExample();
        LocationExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return locationMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(Location record){

        Assert.isTrue(!idDuplicate(null, record.getCode()));
        return  locationMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){
        locationMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        LocationExample example = new LocationExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        locationMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(Location record){
        return locationMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "Location:ALL")
    public Map<Integer, Location> codeMap(){

        Map<Integer, Location> map = new HashMap<>();
        List<Location> locations = locationMapper.selectByExample(new LocationExample());
        for (Location location : locations) {

            map.put(location.getCode(), location);
        }

        return map;
    }

    public boolean hasChildren(int parentCode){

        LocationExample example = new LocationExample();
        example.createCriteria().andParentCodeEqualTo(parentCode);
        return locationMapper.countByExample(example)>0;
    }

    public List<Map<String, Object>> findChildNodes(int code){

        LocationExample example = new LocationExample();
        example.createCriteria().andParentCodeEqualTo(code);
        example.setOrderByClause("id asc");
        List<Location> locations = locationMapper.selectByExample(example);

        List<Map<String, Object>> nodes = new ArrayList<>();
        for (Location location : locations) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", location.getId());
            map.put("code", location.getCode());
            map.put("title", location.getName());
            if(hasChildren(location.getCode())) {
                map.put("lazy", true);
                map.put("folder", true);
            }
            nodes.add(map);
        }
        return nodes;
    }
}
