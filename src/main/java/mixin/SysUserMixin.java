package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mixin.serializer.BirthToAgeSerializer;

import java.util.Date;

/**
 * Created by fafa on 2016/3/27.
 */
@JsonIgnoreProperties(value = {"passwd", "salt", "roleIds", "idcard","sign","mobile", "email", "createTime", "source", "locked"})
public class SysUserMixin {

    @JsonProperty("age")
    @JsonSerialize(using = BirthToAgeSerializer.class,nullsUsing=BirthToAgeSerializer.class)
    public Date birth;
}
