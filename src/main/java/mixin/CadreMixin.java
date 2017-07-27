package mixin;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"user.code", "user.realname", "unit.unitType.name",
        "unit.name", "title", "typeId", "postId", "cadreDpType", "cadreGrowTime", "mobile", "email"})
public class CadreMixin {

}
