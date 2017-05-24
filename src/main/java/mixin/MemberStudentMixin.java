package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"createTime", "applyTime",
        "source", "activeTime" })
public class MemberStudentMixin {

}
