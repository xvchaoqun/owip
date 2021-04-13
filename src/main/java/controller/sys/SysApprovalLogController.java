package controller.sys;

import controller.BaseController;
import domain.sys.SysApprovalLog;
import domain.sys.SysApprovalLogExample;
import mixin.MixinUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class SysApprovalLogController extends BaseController {

    @RequestMapping("/sysApprovalLog")
    public String sysApprovalLog(Byte displayType) {

        if(displayType!=null){
            if(displayType==1){
                return "sys/sysApprovalLog/sysApprovalLog_page_popup";
            }else if(displayType==2){
                return "sys/sysApprovalLog/sysApprovalLog_main_page";
            }
        }

        return "sys/sysApprovalLog/sysApprovalLog_page";
    }

    @RequestMapping("/sysApprovalLog_data")
    public void sysApprovalLog_data(HttpServletResponse response,
                                      Integer id,
                                      Byte type,
                                      Integer userId,
                                      String stage,
                                      Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysApprovalLogExample example = new SysApprovalLogExample();
        SysApprovalLogExample.Criteria criteria = example.createCriteria();

        //criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause("id desc");

        if (id != null)
            criteria.andRecordIdEqualTo(id);
        criteria.andTypeEqualTo(type);
        if (userId != null) {
            criteria.andApplyUserIdEqualTo(userId);
        }

        if (StringUtils.isNotBlank(stage)) {
            criteria.andStageEqualTo(stage);
        }

        long count = sysApprovalLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysApprovalLog> records = sysApprovalLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequestMapping("/sysApprovalLogs")
    public String sysApprovalLogs(HttpServletRequest request, String idName, Byte type, ModelMap modelMap) {

        idName = StringUtils.defaultIfBlank(idName, "id");
        String idStr = request.getParameter(idName);

        SysApprovalLogExample example = new SysApprovalLogExample();
        SysApprovalLogExample.Criteria criteria = example.createCriteria();
        criteria.andRecordIdEqualTo(Integer.parseInt(idStr));
        criteria.andTypeEqualTo(type);
        example.setOrderByClause("create_time desc");
        List<SysApprovalLog> applyApprovalLogs = sysApprovalLogMapper.selectByExample(example);
        modelMap.put("applyApprovalLogs", applyApprovalLogs);

        return "rs/sysApprovalLog/sysApprovalLogs";
    }

}
