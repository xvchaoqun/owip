package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BranchSerializer;
import mixin.serializer.PartySerializer;

import java.util.Date;

@JsonIgnoreProperties(value = { })
public class MemberOutModifyMixin {


    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date handleTime;
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    public Date payTime;
}
