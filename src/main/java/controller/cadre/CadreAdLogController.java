package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreAdLog;
import domain.cadre.CadreAdLogExample;
import domain.cadreInspect.CadreInspect;
import domain.cadreReserve.CadreReserve;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.RoleConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreAdLogController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping("/cadreAdLog")
    public String cadreAdLog(Integer inspectId, Integer reserveId, Integer cadreId, ModelMap modelMap) {

        if (cadreId == null) {
            if (inspectId != null) {
                CadreInspect cadreInspect = cadreInspectMapper.selectByPrimaryKey(inspectId);
                cadreId = cadreInspect.getCadreId();
            } else if (reserveId != null) {
                CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(reserveId);
                cadreId = cadreReserve.getCadreId();
            }
        }

        modelMap.put("cadreId", cadreId);
        return "cadre/cadreAdLog/cadreAdLog_page";
    }

    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping("/cadreAdLog_data")
    @ResponseBody
    public void cadreAdLog_data(int cadreId,
                                Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreAdLogExample example = new CadreAdLogExample();
        CadreAdLogExample.Criteria criteria = example.createCriteria();
        criteria.andCadreIdEqualTo(cadreId);
        example.setOrderByClause("create_time desc");

        long count = cadreAdLogMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreAdLog> cadreAdLogs = cadreAdLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadreAdLogs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }
}
