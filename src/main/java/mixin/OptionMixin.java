package mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fafa on 2016/5/12.
 */
public class OptionMixin {
    @JsonProperty
    public Integer id;
    @JsonProperty
    public String name;
    @JsonProperty
    public String description;
}
