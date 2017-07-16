package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"passwd", "raw", "salt"})
public class SysUserListMixin {

}
