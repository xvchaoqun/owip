package mixin.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import domain.party.RetireApply;
import sys.tags.CmTag;

import java.io.IOException;

public class RetireApplySerializer extends JsonSerializer<Integer> {

    public void serialize(Integer value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {

        RetireApply retireApply = null;
        if (value != null) {
            retireApply = CmTag.getRetireApply(value);
        }
        if (retireApply == null) {
            retireApply = new RetireApply();
            retireApply.setUserId(value);
        }
        generator.writeObject(retireApply);
    }
}