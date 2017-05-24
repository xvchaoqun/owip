package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by fafa on 2016/3/27.
 */
@JsonIgnoreProperties(value = {"passwd", "salt", "roleIds", "idcard","sign", "email", "createTime", "source", "locked"})
public class SysUserMixin {

/*    @JsonProperty("age")
    @JsonSerialize(using = BirthToAgeSerializer.class,nullsUsing=BirthToAgeSerializer.class)
    public Date birth;*/
}
