package controller.party;

import controller.BaseController;
import domain.party.BranchTransferLog;
import domain.party.BranchTransferLogExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BranchTransferLogController extends BaseController {

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN,
            SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping("/branchTransferLog_page")
    public String branchTransferLog_page(Integer branchId, ModelMap modelMap) {

        modelMap.put("branch", branchService.findAll().get(branchId));
        return "party/branchTransferLog/branchTransferLog_page";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,
            SystemConstants.ROLE_ODADMIN,
            SystemConstants.ROLE_PARTYADMIN,
            SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping("/branchTransferLog_data")
    @ResponseBody
    public void branchTransferLog_data(Integer branchId,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchTransferLogExample example = new BranchTransferLogExample();
        example.createCriteria().andBranchIdEqualTo(branchId);
        example.setOrderByClause("create_time desc");

        int count = branchTransferLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<BranchTransferLog> records = branchTransferLogMapper
                .selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }
}
