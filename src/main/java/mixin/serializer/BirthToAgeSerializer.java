package mixin.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang.StringUtils;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.IOException;
import java.util.Date;

public class BirthToAgeSerializer extends JsonSerializer<Date>{

        public void serialize(Date value, JsonGenerator generator, SerializerProvider provider)
                throws IOException {

            if(value!=null)
                generator.writeNumber(DateUtils.intervalYearsUntilNow(value));
            else
                generator.writeNull();
        }
    }