package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import sys.jackson.SensitiveInfo;
import sys.jackson.SensitiveType;

/**
 * Created by fafa on 2016/3/27.
 */
@JsonIgnoreProperties(value = {"passwd", "salt", "roleIds", "idcard","sign", "createTime", "source", "locked"})
public class SysUserMixin {

    @SensitiveInfo(SensitiveType.MOBILE_PHONE)
    private String mobile;

    @SensitiveInfo(SensitiveType.ID_CARD)
    private String idcard;

    @SensitiveInfo(SensitiveType.EMAIL)
    private String email;

    @SensitiveInfo(SensitiveType.FIXED_PHONE)
    private String homePhone;

    @SensitiveInfo(SensitiveType.FIXED_PHONE)
    private String phone;
}
