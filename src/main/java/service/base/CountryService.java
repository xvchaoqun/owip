package service.base;

import domain.base.Country;
import domain.base.CountryExample;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService extends BaseMapper {

    @Cacheable(value = "Country:ALL")
    public List<String> getCountryList() {

        List<Country> countries = countryMapper.selectByExample(new CountryExample());
        List<String> countryList = new ArrayList<>();
        for (Country country : countries) {
            countryList.add(country.getCninfo());
        }

        return countryList;
    }
}
