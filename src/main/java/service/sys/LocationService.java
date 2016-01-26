package service.sys;

import domain.Location;
import domain.LocationExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.utils.JSONUtils;

import java.util.*;

/**
 * Created by fafa on 2015/12/2.
 */
@Service
public class LocationService extends BaseMapper{

    private String getParentCodeStr(Integer parentCode){
        String parentStr = parentCode + "";
        LocationExample example = new LocationExample();
        example.createCriteria().andCodeEqualTo(parentCode);
        List<Location> locations = locationMapper.selectByExample(example);
        if(locations.size()!=1) return parentStr;

        return getParentCodeStr(locations.get(0).getParentCode()) + "," + parentStr;
    }
    @Cacheable(value = "Location:JSON")
    public String toJSON(){
        Map<String, Map> items = new LinkedHashMap<>();
        List<Integer> parentCodeList = selectMapper.selectDistinctLocationParentCode();
        for (Integer parentCode : parentCodeList) {

            String key = getParentCodeStr(parentCode);

            LocationExample example = new LocationExample();
            example.createCriteria().andParentCodeEqualTo(parentCode);
            example.setOrderByClause("code asc");
            List<Location> locations = locationMapper.selectByExample(example);
            Map<Integer, String> values = new LinkedHashMap<>();
            for (Location location : locations) {
                values.put(location.getCode(), location.getName());
            }
            items.put(key, values);
        }
        return JSONUtils.toString(items);
    }
    public boolean idDuplicate(Integer id, Integer code){

        LocationExample example = new LocationExample();
        LocationExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return locationMapper.countByExample(example) > 0;
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="Location:JSON", allEntries=true),
            @CacheEvict(value="Location:ALL", allEntries=true)
    })
    public int insertSelective(Location record){

        Assert.isTrue(!idDuplicate(null, record.getCode()));
        return  locationMapper.insertSelective(record);
    }
    @Transactional
    @Caching(evict={
            @CacheEvict(value="Location:JSON", allEntries=true),
            @CacheEvict(value="Location:ALL", allEntries=true)
    })
    public void del(Integer id){
        locationMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="Location:JSON", allEntries=true),
            @CacheEvict(value="Location:ALL", allEntries=true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        LocationExample example = new LocationExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        locationMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="Location:JSON", allEntries=true),
            @CacheEvict(value="Location:ALL", allEntries=true)
    })
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
