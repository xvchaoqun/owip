package mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"userId", "typeId", "unitId", "remark", "sortOrder"})
public class CadreMixin {

}
