package mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fafa on 2016/5/12.
 */
public class StatusOptionMixin extends OptionMixin {
    @JsonProperty
    public Byte status;
}
