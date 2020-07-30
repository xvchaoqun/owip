package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import sys.jackson.SensitiveInfo;
import sys.jackson.SensitiveType;

@JsonIgnoreProperties(value = {"idcard", "email", "createTime", "applyTime", "activeTime" })
public class MemberMixin {

    @SensitiveInfo(SensitiveType.MOBILE_PHONE)
    private String mobile;
}
