package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BirthToAgeSerializer;
import mixin.serializer.BranchSerializer;
import mixin.serializer.DispatchTypeSerializer;
import mixin.serializer.GenderSerializer;
import sys.tags.CmTag;

import java.util.Date;

@JsonIgnoreProperties(value = {"createTime", "applyTime",
        "source", "activeTime", "politicalStatus" })
public class DispatchMixin {

    @JsonProperty("dispatchType")
    @JsonSerialize(using = DispatchTypeSerializer.class,nullsUsing=DispatchTypeSerializer.class)
    public Integer dispatchTypeId;

    public Integer code;

    public Integer year;

    @JsonProperty("branch")
    @JsonSerialize(using = BranchSerializer.class,nullsUsing=BranchSerializer.class)
    public Integer branchId;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date meetingTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date pubTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date workTime;
}
