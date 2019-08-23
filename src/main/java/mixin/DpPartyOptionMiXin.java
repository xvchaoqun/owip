package mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DpPartyOptionMiXin extends OptionMixin{
    @JsonProperty
    public Boolean isDeleted;
}
