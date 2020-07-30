package mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public interface CadreDispatchMixin {

    @JsonProperty
    Integer getId();
    @JsonProperty
    String getDispatchCode();
    @JsonProperty
    String getFile();
    @JsonProperty
    String getFileName();
}
