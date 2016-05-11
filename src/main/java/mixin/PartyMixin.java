package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(value = { })
public class PartyMixin {

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date foundTime;
}
