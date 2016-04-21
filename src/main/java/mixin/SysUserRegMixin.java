package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BranchSerializer;
import mixin.serializer.PartySerializer;

import java.util.Date;

@JsonIgnoreProperties(value = { })
public class SysUserRegMixin {

    @JsonProperty("party")
    @JsonSerialize(using = PartySerializer.class,nullsUsing=PartySerializer.class)
    public Integer partyId;

    @JsonFormat(pattern = "yyyy-MM-dd mm:HH:ss",timezone="GMT+8")
    public Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd mm:HH:ss",timezone="GMT+8")
    public Date checkTime;
}
