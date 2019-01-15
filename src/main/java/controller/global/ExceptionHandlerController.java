package controller.global;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import interceptor.SignParamsException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.HttpUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getMsg(HttpServletRequest request, Exception ex) {

        //ex.printStackTrace();
        String username = ShiroHelper.getCurrentUsername();
        return MessageFormat.format("{0}, {1}, {2}, {3}, {4}, {5}, {6}",
                username, ex.getMessage(), request.getRequestURI(),
                request.getMethod(),
                JSONUtils.toString(request.getParameterMap(), false),
                RequestUtils.getUserAgent(request), IpUtils.getRealIp(request));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public Map resolveMaxUploadSizeExceededException(HttpServletRequest request, Exception ex) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", false);
        resultMap.put("msg", FormUtils.FILEMAX);

        logger.warn(getMsg(request, ex), ex);
        return resultMap;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Map resolveDataIntegrityViolationException(HttpServletRequest request, Exception ex) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        if (ex.getCause() instanceof MySQLIntegrityConstraintViolationException) {

            resultMap.put("success", false);
            String message = ex.getCause().getMessage();
            if (StringUtils.contains(message, "Duplicate")) {

                resultMap.put("msg", "添加重复");
                logger.warn(getMsg(request, ex), ex);

            } else if (StringUtils.contains(message, "foreign key constraint")) {

                String msg = "数据已被关联使用，不可以删除";
                Map<String, String> delMsgMap = SystemConstants.FOREIN_KEY_DEL_MSG_MAP;
                for (Map.Entry<String, String> entry : delMsgMap.entrySet()) {
                    if(StringUtils.contains(message, MessageFormat.format("REFERENCES `{0}` (`id`)", entry.getKey()))){
                        msg = entry.getValue();
                    }
                }

                resultMap.put("msg", msg);
                logger.warn(getMsg(request, ex));
            } else {
                resultMap.put("msg", "数据异常，请稍后重试");
                logger.error(getMsg(request, ex), ex);
            }
        } else if (ex.getCause() instanceof SQLException) {

            resultMap.put("success", false);
            resultMap.put("msg", "系统异常，请稍后重试");
            logger.error(getMsg(request, ex), ex);
        }

        return resultMap;
    }

    @ExceptionHandler({Exception.class, FileNotFoundException.class, OpException.class})
    @ResponseBody
    public ModelAndView resolveException(HttpServletRequest request, Exception ex) {

        String msg = "系统异常，请稍后重试";
        if (ex instanceof FileNotFoundException) {
            msg = "文件不存在";
        } else if (ex instanceof OpException) {
            msg = ex.getMessage();
        }else if(ex instanceof IOException){
            // org.apache.catalina.connector.ClientAbortException ??
            logger.warn(getMsg(request, ex));
        }else{
            logger.error(getMsg(request, ex), ex);
        }

        //ex.printStackTrace();
        // request.getMethod().equals("GET")  防止sslvpn.xxx.edu.cn 访问地址报错
        if (!HttpUtils.isAjaxRequest(request) && request.getMethod().equalsIgnoreCase("GET")) {

            //ex.printStackTrace();
            ModelAndView mv = new ModelAndView();
            mv.addObject("exception", msg);
            mv.setViewName("500");
            return mv;
        }

        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map attributes = new HashMap();
        attributes.put("success", false);
        attributes.put("msg", msg);
        view.setAttributesMap(attributes);
        mav.setView(view);

        return mav;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ModelAndView resolveUnauthorizedException(HttpServletRequest request, Exception ex) {

        logger.warn(getMsg(request, ex), ex);

        if (!HttpUtils.isAjaxRequest(request) && request.getMethod().equalsIgnoreCase("GET")) {

            //ex.printStackTrace();
            ModelAndView mv = new ModelAndView();
            mv.addObject("exception", "没有权限访问");
            mv.setViewName("unauthorized");
            return mv;
        }

        ModelAndView mav = new ModelAndView();
        //ex.printStackTrace();

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map attributes = new HashMap();
        attributes.put("success", false);
        attributes.put("msg", "没有权限访问");
        view.setAttributesMap(attributes);
        mav.setView(view);

        return mav;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ModelAndView resolveNullPointerException(HttpServletRequest request, Exception ex) {

        logger.error(getMsg(request, ex), ex);

        // request.getMethod().equals("GET")  防止sslvpn.xxx.edu.cn 访问地址报错
        if (!HttpUtils.isAjaxRequest(request) && request.getMethod().equalsIgnoreCase("GET")) {

            //ex.printStackTrace();
            ModelAndView mv = new ModelAndView();
            mv.addObject("exception", "系统异常[" + ex.getMessage() + "]，请稍后重试");
            mv.setViewName("500");
            return mv;
        }

        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map attributes = new HashMap();
        attributes.put("success", false);
        attributes.put("msg", "系统异常[NULL]，请稍后重试");
        view.setAttributesMap(attributes);
        mav.setView(view);

        return mav;
    }

    @ExceptionHandler(SignParamsException.class)
    public void resolveSignParamsException(HttpServletRequest request, HttpServletResponse response, Exception ex) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        String msg = StringUtils.defaultIfBlank(ex.getMessage(), "签名错误");
        String app = request.getParameter("app");
        String sign = request.getParameter("sign");
        if(StringUtils.isBlank(app)){
            try {
                JSONUtils.write(response, "参数app为空", false);
                return;
            } catch (IOException e) {}
        }

        if(StringUtils.isBlank(sign)){
            try {
                JSONUtils.write(response, "参数sign为空", false);
                return;
            } catch (IOException e) {}
        }

        switch (app) {
            case "oa":
                resultMap.put("Message", msg);
                resultMap.put("Success", false);
                break;
            default:
                resultMap.put("ret", -10);
                resultMap.put("msg", msg);
                break;
        }

        //logger.warn(getMsg(request, ex), ex);
        logger.warn(getMsg(request, ex));

        try {
            JSONUtils.write(response, resultMap, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
