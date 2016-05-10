package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.PartySerializer;

import java.util.Date;

@JsonIgnoreProperties(value = { })
public class PartyMemberGroupMixin {

    @JsonProperty("party")
    @JsonSerialize(using = PartySerializer.class,nullsUsing=PartySerializer.class)
    public Integer partyId;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date tranTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date actualTranTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date appointTime;
}
