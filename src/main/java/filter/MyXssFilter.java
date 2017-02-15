package filter;

import org.apache.commons.lang3.StringUtils;
import sys.filter.XssHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by fafa on 2015/8/3.
 */
public class MyXssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if(StringUtils.equals(requestURI, "/htmlFragment_au")
                || StringUtils.equals(requestURI, "/contentTpl_au")
                || StringUtils.equals(requestURI, "/cadreInfo_updateContent")
                || StringUtils.equals(requestURI, "/cisInspectObj_summary")
                || StringUtils.equals(requestURI, "/train_note")
                ){  // 不需要XSS参数编码的资源
            filterChain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
                (HttpServletRequest) request);
        filterChain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {

    }
}
