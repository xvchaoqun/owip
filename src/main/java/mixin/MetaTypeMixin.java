package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"id", "classId", "code", "extraAttr", "boolAttr",
        "remark", "sortOrder", "available"})
public class MetaTypeMixin {

}
