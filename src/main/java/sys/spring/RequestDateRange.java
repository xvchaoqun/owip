package sys.spring;

import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestDateRange {

    String separtor() default  SystemConstants.DATERANGE_SEPARTOR;

    String format() default DateUtils.YYYY_MM_DD;
}