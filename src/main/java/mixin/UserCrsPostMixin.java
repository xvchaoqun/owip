package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(value = {"remark",
        "statExpertCount", "statGiveCount", "statBackCount", "statFile",
        "statFileName", "statDate"})
public class UserCrsPostMixin {

}
