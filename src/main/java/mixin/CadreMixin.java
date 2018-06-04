package mixin;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"code", "realname", "unit.unitType.name",
        "unit.name", "title", "typeId", "postId", "dpTypeId", "dpGrowTime", "isOw", "owGrowTime", "mobile", "email"})
public class CadreMixin {

}
