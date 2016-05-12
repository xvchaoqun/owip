package location;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.CommonController;
import domain.MetaType;
import mixin.OptionMixin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.LocationMapper;
import service.sys.MetaTypeService;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class JacksonTest {

    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private MetaTypeService metaTypeService;

    @Test
    public void toJson() throws FileNotFoundException, JsonProcessingException {
        ObjectMapper mapper = JSONUtils.buildObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);

        Map<Class<?>, Class<?>> sourceMixins = new HashMap<>();
        sourceMixins.put(MetaType.class, OptionMixin.class);
        mapper.setMixInAnnotations(sourceMixins);

        Map cMap = new HashMap();

        cMap.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));
        Map constantMap = new HashMap();

        Field[] fields = SystemConstants.class.getFields();
        for (Field field : fields) {
            if(org.apache.commons.lang3.StringUtils.equals(field.getType().getName(), "java.util.Map")){
                try {
                    constantMap.put(field.getName(), field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        cMap.putAll(constantMap);

        System.out.println(mapper.writeValueAsString(cMap));
    }


}
