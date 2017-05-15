package sys.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sys.utils.DateUtils;

/**
 * Created by fafa on 2017/5/11.
 */
public class DateRangeConverter implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestDateRange.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        RequestDateRange annotation = parameter.getParameterAnnotation(RequestDateRange.class);
        String format = annotation.format();
        String separtor = annotation.separtor();

        DateRange dateRange = new DateRange();

        String _dateRange = webRequest.getParameter(parameter.getParameterName());
        if(StringUtils.isNotBlank(_dateRange)){

            String start = _dateRange.split(separtor)[0];
            String end = _dateRange.split(separtor)[1];

            dateRange.setStart(DateUtils.parseDate(start, format));
            dateRange.setEnd(DateUtils.parseDate(end, format));
        }

        return dateRange; // 返回对象保证不为空
    }
}
