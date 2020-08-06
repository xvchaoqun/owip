package sys.jackson;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SignResSerialize.class)
public @interface SignRes {

    String permissions() default ""; // 资源使用权限
    String method() default ""; // 资源使用权限方法
    String params() default ""; // 资源使用权限方法的参数
}