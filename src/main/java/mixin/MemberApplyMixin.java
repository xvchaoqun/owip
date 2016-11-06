package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BranchSerializer;
import mixin.serializer.PartySerializer;

import java.util.Date;

@JsonIgnoreProperties(value = { })
public class MemberApplyMixin {

  /*  @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date applyTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date activeTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date candidateTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date planTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date drawTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date growTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date positiveTime;*/
}
