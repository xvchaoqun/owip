package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"userId", "typeId", "unitId",
        "post", "remark", "sortOrder"})
public class CadreMixin {

}
