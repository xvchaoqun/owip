package service.sys;

import domain.Country;
import domain.CountryExample;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountryService extends BaseMapper{

    @Cacheable(value = "Country:ALL")
    public Map<Integer, Country> findAll(){

        Map<Integer, Country> map = new HashMap<>();
        List<Country> countries = countryMapper.selectByExample(new CountryExample());
        for (Country country : countries) {

            map.put(country.getId(), country);
        }

        return map;
    }
}
