package service.sys;

import domain.Location;
import domain.LocationExample;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2015/12/2.
 */
@Service
public class LocationService extends BaseMapper{

    @Cacheable(value = "Location:ALL")
    public Map<Integer, Location> findAll(){

        Map<Integer, Location> map = new HashMap<>();
        List<Location> locations = locationMapper.selectByExample(new LocationExample());
        for (Location location : locations) {

            map.put(location.getId(), location);
        }

        return map;
    }
}
