package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BranchSerializer;
import mixin.serializer.PartySerializer;

import java.util.Date;

@JsonIgnoreProperties(value = { })
public class GraduateAbroadMixin {

    @JsonProperty("party")
    @JsonSerialize(using = PartySerializer.class,nullsUsing=PartySerializer.class)
    public Integer partyId;

    @JsonProperty("branch")
    @JsonSerialize(using = BranchSerializer.class,nullsUsing=BranchSerializer.class)
    public Integer branchId;

    @JsonProperty("toBranch")
    @JsonSerialize(using = BranchSerializer.class,nullsUsing=BranchSerializer.class)
    public Integer toBranchId;

    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    public Date startTime;
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    public Date endTime;
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    public Date saveStartTime;
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    public Date saveEndTime;
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    public Date payTime;
}
