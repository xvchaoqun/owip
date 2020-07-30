package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import sys.jackson.SensitiveInfo;
import sys.jackson.SensitiveType;

@JsonIgnoreProperties(value = {"passwd", "raw", "salt"})
public class SysUserListMixin {

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
