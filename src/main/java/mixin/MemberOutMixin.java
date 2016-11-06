package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BranchSerializer;
import mixin.serializer.PartySerializer;

import java.util.Date;

@JsonIgnoreProperties(value = { })
public class MemberOutMixin {

   /* @JsonProperty("party")
    @JsonSerialize(using = PartySerializer.class,nullsUsing=PartySerializer.class)
    public Integer partyId;

    @JsonProperty("branch")
    @JsonSerialize(using = BranchSerializer.class,nullsUsing=BranchSerializer.class)
    public Integer branchId;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date handleTime;
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    public Date payTime;*/
}
