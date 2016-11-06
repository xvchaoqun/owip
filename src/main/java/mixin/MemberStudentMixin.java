package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"createTime", "applyTime",
        "source", "activeTime" })
public class MemberStudentMixin {

    /*@JsonProperty("gender")
    @JsonSerialize(using = GenderSerializer.class,nullsUsing=GenderSerializer.class)
    public String gender;

    @JsonProperty("age")
    @JsonSerialize(using = BirthToAgeSerializer.class,nullsUsing=BirthToAgeSerializer.class)
    public Date birth;

    *//*@JsonProperty("party")
    @JsonSerialize(using = PartySerializer.class,nullsUsing=PartySerializer.class)
    public Integer partyId;*//*

    @JsonProperty("branch")
    @JsonSerialize(using = BranchSerializer.class,nullsUsing=BranchSerializer.class)
    public Integer branchId;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date growTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date positiveTime;*/
}
