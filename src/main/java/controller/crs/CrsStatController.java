package controller.crs;

import controller.CrsBaseController;
import domain.crs.CrsCandidateView;
import domain.crs.CrsCandidateViewExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CrsStatController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsStat:*")
    @RequestMapping("/crsStat")
    public String crsStat(@RequestParam(required = false, defaultValue = "1") Byte cls,
                          Integer expertUserId,
                          ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "crs/crsStat/crsStat_page";
    }

    @RequiresPermissions("crsStat:list")
    @RequestMapping("/crsCandidate_data")
    public void crsCandidate_data(HttpServletResponse response,
                                    Integer year,
                                    Boolean isFirst,
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        // 年度、专家推荐排名、年龄、党派、最高学历、是否现任中层干部。
        CrsCandidateViewExample example = new CrsCandidateViewExample();
        CrsCandidateViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("crs_post_year desc, crs_post_id desc, is_first desc");

        if(year!=null){
            criteria.andCrsPostYearEqualTo(year);
        }
        if(isFirst!=null){
            criteria.andIsFirstEqualTo(isFirst);
        }

        long count = crsCandidateViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsCandidateView> records = crsCandidateViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsCandidateView.class, crsCandidateViewMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
