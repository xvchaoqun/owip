package controller.pmd;

import domain.pmd.PmdPayBranchView;
import domain.pmd.PmdPayBranchViewExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdPayBranchController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdPayBranch:list")
    @RequestMapping("/pmdPayBranch")
    public String pmdPayBranch() {

        return "pmd/pmdPayBranch/pmdPayBranch_page";
    }

    @RequiresPermissions("pmdPayBranch:list")
    @RequestMapping("/pmdPayBranch_data")
    public void pmdPayBranch_data(HttpServletResponse response,
                                 Integer monthId,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdPayBranchViewExample example = new PmdPayBranchViewExample();
        PmdPayBranchViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("party_sort_order desc, branch_id desc");

        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            int userId = ShiroHelper.getCurrentUserId();
            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
            if (adminPartyIds.size() > 0) {
                criteria.andPartyIdIn(adminPartyIds);
            } else {
                criteria.andPartyIdIsNull();
            }
        }

        if (monthId != null) {
            criteria.andMonthIdEqualTo(monthId);
        }

        long count = pmdPayBranchViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdPayBranchView> records = pmdPayBranchViewMapper.selectByExampleWithRowbounds(example,
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
