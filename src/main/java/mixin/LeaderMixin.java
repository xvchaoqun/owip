package mixin;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"code", "realname", "unit.unitType.name",
        "unit.name", "title", "adminLevel", "postType", "dpTypeId", "dpGrowTime", "isOw", "owGrowTime", "mobile", "email"})
public class LeaderMixin {

}
