package controller.party;

import controller.BaseController;
import domain.member.*;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/4/9.
 */
@Controller
public class ApplyApprovalLogController extends BaseController {

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping("/applyApprovalLog_page")
    public String applyApprovalLog_page(Integer id, Byte type, ModelMap modelMap) {

        if(id!=null) {
            Integer userId = null;
            switch (type) {
                case SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY: {
                    MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(id);
                    userId = memberApply.getUserId();
                    break;
                }
                case SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD:
                    MemberAbroad memberAbroad = memberAbroadMapper.selectByPrimaryKey(id);
                    userId = memberAbroad.getUserId();
                    break;
                case SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN:
                    MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
                    userId = memberReturn.getUserId();
                    break;
                case SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_IN:
                    MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
                    userId = memberIn.getUserId();
                    break;
                case SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT:
                    MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
                    userId = memberOut.getUserId();
                    break;
                case SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW:
                    MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
                    userId = memberInflow.getUserId();
                    break;
                case SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW:
                    MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
                    userId = memberOutflow.getUserId();
                    break;
                case SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER:
                    MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
                    userId = memberTransfer.getUserId();
                    break;
                case SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY:
                    MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
                    userId = memberStay.getUserId();
                    break;
            }

            if (userId != null) {
                SysUserView sysUser = sysUserService.findById(userId);
                modelMap.put("sysUser", sysUser);
            }
        }
        modelMap.put("type", type);

        return "party/applyApprovalLog/applyApprovalLog_page";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,
            SystemConstants.ROLE_ODADMIN,
            SystemConstants.ROLE_PARTYADMIN,
            SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping("/applyApprovalLog_data")
    public void applyApprovalLog_data(HttpServletResponse response,
                                   Integer id,
                                   Byte type,Integer partyId,
                                   Integer branchId,
                                   String stage,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplyApprovalLogExample example = new ApplyApprovalLogExample();
        ApplyApprovalLogExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause("create_time desc");

        if(id!=null)
            criteria.andRecordIdEqualTo(id);
        criteria.andTypeEqualTo(type );
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if(StringUtils.isNotBlank(stage)){
            criteria.andStageEqualTo(stage);
        }

        int count = applyApprovalLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplyApprovalLog> applyApprovalLogs = applyApprovalLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", applyApprovalLogs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping("/applyApprovalLogs")
    public String applyApprovalLogs(HttpServletRequest request, String idName, Byte type, ModelMap modelMap) {

        idName = StringUtils.defaultIfBlank(idName, "id");
        String idStr = request.getParameter(idName);

        ApplyApprovalLogExample example = new ApplyApprovalLogExample();
        ApplyApprovalLogExample.Criteria criteria = example.createCriteria();
        criteria.andRecordIdEqualTo(Integer.parseInt(idStr));
        criteria.andTypeEqualTo(type );
        example.setOrderByClause("create_time desc");
        List<ApplyApprovalLog> applyApprovalLogs = applyApprovalLogMapper.selectByExample(example);
        modelMap.put("applyApprovalLogs", applyApprovalLogs);
        return "party/applyApprovalLog/applyApprovalLogs";
    }

}
