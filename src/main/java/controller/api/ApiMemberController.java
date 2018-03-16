package controller.api;

import controller.BaseController;
import domain.member.Member;
import domain.sys.SysUserView;
import interceptor.NeedSign;
import interceptor.SignParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.MemberConstants;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by fafa on 2016/5/23.
 */
@Controller
@RequestMapping("/api/member")
public class ApiMemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @NeedSign
    @RequestMapping("/status")
    @ResponseBody
    public Map member_status(@SignParam(value = "code") String code, HttpServletRequest request) {

        logger.info(MessageFormat.format("查询党员状态接口, {0}, {1}, {2}, {3}, {4}",
                request.getRequestURI(),
                request.getMethod(),
                JSONUtils.toString(request.getParameterMap(), false),
                RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)));

        Map resultMap;
        SysUserView sysUser = sysUserService.findByCode(code);
        if(sysUser==null){
            resultMap = ret(-1, "用户不存在");
            return resultMap;
        }
        Member member = memberService.get(sysUser.getId());
        if(member==null){
            resultMap = ret(-2, "该用户不是党员");
            return resultMap;
        }
        if(member.getStatus()!= MemberConstants.MEMBER_STATUS_TRANSFER){
            resultMap = ret(0, "未转出");
        }else{
            resultMap = ret(1, "已转出");
        }
        return resultMap;
    }
}
