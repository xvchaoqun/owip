package mixin;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.*;

import java.util.Date;

@JsonIgnoreProperties(value = {"createTime", "applyTime",
        "source", "activeTime", "politicalStatus" })
public class MemberTeacherMixin {

    @JsonProperty("gender")
    @JsonSerialize(using = GenderSerializer.class,nullsUsing=GenderSerializer.class)
    public String gender;

    @JsonProperty("age")
    @JsonSerialize(using = BirthToAgeSerializer.class,nullsUsing=BirthToAgeSerializer.class)
    public Date birth;

    @JsonProperty("party")
    @JsonSerialize(using = PartySerializer.class,nullsUsing=PartySerializer.class)
    public Integer partyId;

    @JsonProperty("branch")
    @JsonSerialize(using = BranchSerializer.class,nullsUsing=BranchSerializer.class)
    public Integer branchId;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date growTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date positiveTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date retireTime;

    @JsonProperty("retireApply")
    @JsonSerialize(using = RetireApplySerializer.class,nullsUsing=RetireApplySerializer.class)
    public Integer userId;
}
