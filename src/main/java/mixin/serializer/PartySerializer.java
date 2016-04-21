package mixin.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import domain.Party;
import org.springframework.context.ApplicationContext;
import service.party.ApplicationContextSupport;
import service.party.PartyService;

import java.io.IOException;
import java.util.Map;

public class PartySerializer extends JsonSerializer<Integer> {

    static ApplicationContext context = ApplicationContextSupport.getContext();
    static PartyService partyService = (PartyService) context.getBean("partyService");

    public void serialize(Integer value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {

        if(value==null || value<=0){
            generator.writeNull();
            return;
        }

        Map<Integer, Party> partyMap = partyService.findAll();
        Party party = partyMap.get(value);
        if(party!=null)
            generator.writeString(party.getName());
        else
            generator.writeNull();
    }
}