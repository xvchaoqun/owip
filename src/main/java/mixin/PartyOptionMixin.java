package mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fafa on 2016/5/12.
 */
public class PartyOptionMixin extends OptionMixin {
    @JsonProperty
    public Boolean isDeleted;
    @JsonProperty
    public String email;
    @JsonProperty
    public String mailbox;
    @JsonProperty
    public String phone;
}
