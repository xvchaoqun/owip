package mixin.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import sys.constants.SystemConstants;

import java.io.IOException;

public class GenderSerializer extends JsonSerializer<Byte>{

        public void serialize(Byte value, JsonGenerator generator, SerializerProvider provider)
                throws IOException {

            generator.writeString(SystemConstants.GENDER_MAP.get(value));
        }
    }