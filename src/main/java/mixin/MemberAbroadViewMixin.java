package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BranchSerializer;
import mixin.serializer.PartySerializer;

import java.util.Date;

@JsonIgnoreProperties(value = { })
public class MemberAbroadViewMixin {

    /*@JsonProperty("party")
    @JsonSerialize(using = PartySerializer.class,nullsUsing=PartySerializer.class)
    public Integer partyId;

    @JsonProperty("branch")
    @JsonSerialize(using = BranchSerializer.class,nullsUsing=BranchSerializer.class)
    public Integer branchId;*/

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date yjcfsj;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date ygsj;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date sjcfsj;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date sgsj;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date yq1s;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date yq1z;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date yq2s;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date yq2z;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date pzwh;
}
