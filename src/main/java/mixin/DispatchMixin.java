package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BranchSerializer;

import java.util.Date;

@JsonIgnoreProperties(value = {"createTime", "applyTime",
        "source", "activeTime", "politicalStatus" })
public class DispatchMixin {

}
