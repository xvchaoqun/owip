package controller.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdPayPartyView;
import domain.pmd.PmdPayPartyViewExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdPayPartyController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdPayParty:list")
    @RequestMapping("/pmdPayParty")
    public String pmdPayParty() {

        return "pmd/pmdPayParty/pmdPayParty_page";
    }

    @RequiresPermissions("pmdPayParty:list")
    @RequestMapping("/pmdPayParty_data")
    public void pmdPayParty_data(HttpServletResponse response,
                                 Integer monthId,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdPayPartyViewExample example = new PmdPayPartyViewExample();
        PmdPayPartyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (monthId != null) {
            criteria.andMonthIdEqualTo(monthId);
        }

        long count = pmdPayPartyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdPayPartyView> records = pmdPayPartyViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
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
}
