package mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DpPartyMemberGroupOptionMiXin extends OptionMixin{
    @JsonProperty
    public Integer partyId;
}
