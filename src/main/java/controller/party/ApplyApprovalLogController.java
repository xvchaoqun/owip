package controller.party;

import controller.BaseController;
import domain.ApplyApprovalLog;
import domain.ApplyApprovalLogExample;
import mixin.ApplyApprovalLogMixin;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

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

    @RequestMapping("/applyApprovalLog_data")
    public void applyApprovalLog_data(HttpServletResponse response,
                                   Integer id,
                                   Byte type,
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

        example.setOrderByClause("create_time asc");

        criteria.andRecordIdEqualTo(id);
        criteria.andTypeEqualTo(type );

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
        sourceMixins.put(ApplyApprovalLog.class, ApplyApprovalLogMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
}
