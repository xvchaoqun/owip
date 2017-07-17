package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"createTime", "applyTime",
        "source", "activeTime", "politicalStatus" })
public class DispatchMixin {

}
