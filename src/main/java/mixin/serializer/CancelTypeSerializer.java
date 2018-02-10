package mixin.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import sys.constants.AbroadConstants;

import java.io.IOException;

public class CancelTypeSerializer extends JsonSerializer<Byte>{

        public void serialize(Byte value, JsonGenerator generator, SerializerProvider provider)
                throws IOException {

            generator.writeString(AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_MAP.get(value));
        }
    }