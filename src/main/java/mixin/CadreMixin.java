package mixin;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"code", "realname", "unit.unitType.name",
        "unit.name", "title", "typeId", "postId", "cadreDpType", "cadreGrowTime", "mobile", "email"})
public class CadreMixin {

}
