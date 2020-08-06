package sys.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.apache.commons.lang3.StringUtils;
import sys.tags.UserTag;

import java.io.IOException;
import java.util.Objects;

public class SignResSerialize extends JsonSerializer<String> implements
        ContextualSerializer {

    private String permissions; // 资源使用权限
    private String method; // 资源使用权限方法
    private String params;

    public SignResSerialize() {

    }

    public SignResSerialize(String permissions, String method, String params) {
        this.permissions = permissions;
        this.method = method;
        this.params = params;
    }

    @Override
    public void serialize(final String res, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        String signRes = null;
        if (StringUtils.isNotBlank(res)) {
            signRes = UserTag.sign(res, null, permissions, method, params);
        }

        jsonGenerator.writeString(signRes);
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
                                              final BeanProperty beanProperty) throws JsonMappingException {

        if (beanProperty != null) { // 为空直接跳过
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) { // 非 String 类直接跳过

                SignRes signRes = beanProperty.getAnnotation(SignRes.class);
                if (signRes == null) {
                    signRes = beanProperty.getContextAnnotation(SignRes.class);
                }
                if (signRes != null) { // 如果能得到注解，就将注解的 value 传入 SensitiveInfoSerialize
                    return new SignResSerialize(signRes.permissions(), signRes.method(), signRes.params());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(beanProperty);
    }
}