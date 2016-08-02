package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BranchSerializer;
import mixin.serializer.PartySerializer;

@JsonIgnoreProperties(value = {"createTime", "updateTime" })
public class MemberMixin {

    @JsonProperty("party")
    @JsonSerialize(using = PartySerializer.class,nullsUsing=PartySerializer.class)
    public Integer partyId;

    @JsonProperty("branch")
    @JsonSerialize(using = BranchSerializer.class,nullsUsing=BranchSerializer.class)
    public Integer branchId;

    /*@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date growTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date positiveTime;*/
}
