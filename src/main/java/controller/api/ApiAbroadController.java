package controller.api;

import controller.BaseController;
import domain.sys.SysUserView;
import interceptor.NeedSign;
import interceptor.SignParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.abroad.ApplySelfService;
import service.abroad.ApproverService;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/abroad")
public class ApiAbroadController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private void log(Map<String, Object> result, HttpServletRequest request){

        logger.info(MessageFormat.format("{0}, {1}, {2}, {3}, {4}, result：{5}",
                        request.getRequestURI(),
                        request.getMethod(),
                        JSONUtils.toString(request.getParameterMap(), false),
                        RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)),
                JSONUtils.toString(result, false));
    }

    @NeedSign
    @RequestMapping("/approve_count")
    public void approve_count(@SignParam(value = "code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<>();
        result.put("Success", false);

        try {
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null) {

                result.put("Message", "没有这个学工号");
                log(result, request);
                JSONUtils.write(response, result, false);
                return;
            }

            int userId = uv.getId();
            if(!CmTag.hasRole(uv.getUsername(), RoleConstants.ROLE_CADRE)
                    || cadreService.dbFindByUserId(userId) == null
                    || !CmTag.getBean(ApproverService.class).hasApproveAuth(userId)){

                result.put("Message", "没有审批权限");
                log(result, request);
                JSONUtils.write(response, result, false);
                return;
            }

            ApplySelfService applySelfService = CmTag.getBean(ApplySelfService.class);
            Map map = applySelfService.findApplySelfList(userId, null, null,
                    null, 0, -1, null);
            CommonList commonList = (CommonList) map.get("commonList");
            int recNum = commonList.getRecNum();

            result.put("Success", true);
            result.put("Count", recNum);
            log(result, request);
            JSONUtils.write(response, result, false);
            return;

        }catch (Exception ex){

            logger.error("查询因私审批数量接口", ex);

            result.put("Message", "系统访问出错");
            JSONUtils.write(response, result, false);
            return;
        }
    }
}
