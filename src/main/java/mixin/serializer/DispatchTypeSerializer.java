package mixin.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import domain.DispatchType;
import org.springframework.context.ApplicationContext;
import service.dispatch.DispatchTypeService;
import service.party.ApplicationContextSupport;

import java.io.IOException;
import java.util.Map;

/**
 * Created by fafa on 2016/3/17.
 */
public class DispatchTypeSerializer extends JsonSerializer<Integer> {

    static ApplicationContext context = ApplicationContextSupport.getContext();
    static DispatchTypeService dispatchTypeService = (DispatchTypeService) context.getBean("dispatchTypeService");

    public void serialize(Integer value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        generator.writeString(dispatchTypeMap.get(value).getName());
    }
}
