package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"createTime", "applyTime", "activeTime" })
public class MemberStudentMixin {

}
