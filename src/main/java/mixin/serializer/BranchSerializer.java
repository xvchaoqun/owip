package mixin.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import domain.Branch;
import org.springframework.context.ApplicationContext;
import service.party.ApplicationContextSupport;
import service.party.BranchService;

import java.io.IOException;
import java.util.Map;

public class BranchSerializer extends JsonSerializer<Integer> {

    static ApplicationContext context = ApplicationContextSupport.getContext();
    static BranchService branchService = (BranchService) context.getBean("branchService");

    public void serialize(Integer value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {

        Map<Integer, Branch> branchMap = branchService.findAll();
        Branch branch = branchMap.get(value);
        if(branch!=null)
            generator.writeString(branch.getName());
        else
            generator.writeNull();
    }
}