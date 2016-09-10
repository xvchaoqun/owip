package controller;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import interceptor.SignParamsException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import shiro.ShiroUser;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getMsg(HttpServletRequest request, Exception ex){

        ex.printStackTrace();

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        String username = (shiroUser!=null)?shiroUser.getUsername():null;
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

        logger.warn(getMsg(request, ex));
        return resultMap;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Map resolveDataIntegrityViolationException(HttpServletRequest request, Exception ex) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        if(ex.getCause() instanceof MySQLIntegrityConstraintViolationException){

            resultMap.put("success", false);
            if(StringUtils.contains(ex.getCause().getMessage(), "Duplicate"))
                resultMap.put("msg", "添加重复");
            else
                resultMap.put("msg", "请先删除关联表的所有数据");
        }else if(ex.getCause() instanceof SQLException){

            resultMap.put("success", false);
            resultMap.put("msg", "系统异常：" + ex.getMessage());
        }

        logger.error(getMsg(request, ex));

        return resultMap;
    }

    @ExceptionHandler({RuntimeException.class, FileNotFoundException.class})
    @ResponseBody
    public ModelAndView resolveException(HttpServletRequest request, Exception ex) {

        //ex.printStackTrace();
        // request.getMethod().equals("GET")  防止sslvpn.xxx.edu.cn 访问地址报错
        if (!HttpUtils.isAjaxRequest(request) && request.getMethod().equalsIgnoreCase("GET")) {

            //ex.printStackTrace();
            logger.error(getMsg(request, ex));
            ModelAndView mv = new ModelAndView();
            mv.addObject("exception", ex);
            mv.setViewName("500");
            return mv;
        }

        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map attributes = new HashMap();
        attributes.put("success", false);
        attributes.put("msg", ex.getMessage());
        view.setAttributesMap(attributes);
        mav.setView(view);

        logger.warn(getMsg(request, ex));

        return mav;
    }


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ModelAndView resolveUnauthorizedException(HttpServletRequest request, Exception ex) {

        if (!HttpUtils.isAjaxRequest(request) && request.getMethod().equalsIgnoreCase("GET")) {

            //ex.printStackTrace();
            logger.error(getMsg(request, ex));
            ModelAndView mv = new ModelAndView();
            mv.addObject("exception", ex);
            mv.setViewName("unauthorized");
            return mv;
        }

        ModelAndView mav = new ModelAndView();
        //ex.printStackTrace();

        logger.warn(getMsg(request, ex));

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map attributes = new HashMap();
        attributes.put("success", false);
        attributes.put("msg", "您没有权限");
        view.setAttributesMap(attributes);
        mav.setView(view);

        return mav;
    }

    @ExceptionHandler(SignParamsException.class)
    @ResponseBody
    public Map resolveSignParamsException(HttpServletRequest request, Exception ex) {

        String msg = "签名错误";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("ret", -10);
        resultMap.put("msg", msg);

        logger.warn(getMsg(request, ex));
        return resultMap;
    }

}
