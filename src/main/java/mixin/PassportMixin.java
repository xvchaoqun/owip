package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.CancelTypeSerializer;
import mixin.serializer.PartySerializer;

import java.util.Date;

@JsonIgnoreProperties(value = {})
public class PassportMixin {

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date issueDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date expiryDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date lostTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date keepDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date cancelTime;

    @JsonSerialize(using = CancelTypeSerializer.class,nullsUsing=CancelTypeSerializer.class)
    public Byte cancelType;
}
