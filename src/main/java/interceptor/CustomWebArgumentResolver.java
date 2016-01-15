package interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import service.DBServcie;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by fafa on 2016/1/15.
 */
public class CustomWebArgumentResolver implements WebArgumentResolver {
    @Autowired
    protected DBServcie dbServcie;
    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {

        SortParam sortParam = methodParameter.getParameterAnnotation(SortParam.class);
        if(sortParam!=null) {
            Set<String> tableColumns = dbServcie.getTableColumns(sortParam.tableName());
            String parameterName = methodParameter.getParameterName();
            String parameterValue = webRequest.getParameter(parameterName);

            //System.out.println("sort+++++++" + parameterName + "=" + parameterValue);
            if (parameterValue==null || !tableColumns.contains(parameterValue)) {
                return sortParam.defaultValue();
            }
            return parameterValue;
        }

        OrderParam orderParam = methodParameter.getParameterAnnotation(OrderParam.class);
        if(orderParam!=null){
            String parameterName = methodParameter.getParameterName();
            String parameterValue = webRequest.getParameter(parameterName);
            //System.out.println("order+++++++" + parameterName + "=" + parameterValue);
            if(parameterValue==null || (!StringUtils.equals(parameterValue, "desc") && !StringUtils.equals(parameterValue, "asc"))){
                return orderParam.defaultValue();
            }
            return parameterValue;
        }

        return UNRESOLVED;
    }
}
