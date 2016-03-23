package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(value = {})
public class PassportApplyMixin {

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date applyDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date approveTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date expectDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date handleDate;
}
