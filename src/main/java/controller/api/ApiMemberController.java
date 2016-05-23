package controller.api;

import controller.BaseController;
import domain.Member;
import domain.SysUser;
import interceptor.NeedSign;
import interceptor.SignParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;

import java.util.Map;

/**
 * Created by fafa on 2016/5/23.
 */
@Controller
@RequestMapping("/api/member")
public class ApiMemberController extends BaseController {

    @NeedSign
    @RequestMapping("/status")
    @ResponseBody
    public Map member_status(@SignParam(value = "code") String code) {

        Map resultMap;
        SysUser sysUser = sysUserService.findByCode(code);
        if(sysUser==null){
            resultMap = ret(-1, "用户不存在");
            return resultMap;
        }
        Member member = memberService.get(sysUser.getId());
        if(member==null){
            resultMap = ret(-2, "该用户不是党员");
            return resultMap;
        }
        if(member.getStatus()!= SystemConstants.MEMBER_STATUS_TRANSFER){
            resultMap = ret(0, "未转出");
        }else{
            resultMap = ret(1, "已转出");
        }
        return resultMap;
    }
}
