package location;

import com.google.gson.Gson;
import domain.Location;
import domain.LocationExample;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.LocationMapper;
import persistence.common.SelectMapper;
import service.sys.LocationService;
import sys.utils.JSONUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2015/12/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class GsonTest {

    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private LocationService locationService;

    @Test
    public void insertToDB() throws FileNotFoundException {
        //JSON
        Gson gson = new Gson();
        Map<String, Map<String,String>> map = gson.fromJson(new FileReader("D:\\IdeaProjects\\exp\\sys.tools\\src\\test\\java\\gson\\data"), Map.class);
        //System.out.println(map);

        for (Map.Entry<String, Map<String,String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "======" + entry.getValue());
            String[] split = entry.getKey().split(",");
            int fid = Integer.parseInt(split[split.length-1]);
            for (Map.Entry<String, String> stringEntry : entry.getValue().entrySet()) {
                System.out.println(stringEntry.getKey() + ":" + stringEntry.getValue());
                Location record = new Location();
                record.setCode(Integer.parseInt(stringEntry.getKey()));
                record.setParentCode(fid);
                record.setName(StringUtils.trimToNull(stringEntry.getValue()));
                locationMapper.insert(record);
            }
        }
    }

    @Test
    public void toJSON(){
        System.out.println(locationService.toJSON());
    }

}
