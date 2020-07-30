package mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public interface CadreEduMixin {

    @JsonProperty
    Integer getEduId();
    @JsonProperty
    String getSchool();
    @JsonProperty
    String getDep();
    @JsonProperty
    String getMajor();
}
