package mixin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(value = {})
public class ApplySelfMixin {

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date applyDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date returnDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date realReturnDate;

}
