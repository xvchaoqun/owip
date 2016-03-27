package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by fafa on 2016/3/27.
 */
@JsonIgnoreProperties(value = {"passwd", "salt", "roleIds", "type"
,"idcard","sign","mobile", "email", "createTime", "source", "locked"})
public class SysUserMixin {
}
