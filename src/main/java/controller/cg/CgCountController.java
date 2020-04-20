package controller.cg;

import domain.cg.CgMember;
import domain.cg.CgMemberExample;
import domain.cg.CgMemberExample.Criteria;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cg")
public class CgCountController extends CgBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cgCount:list")
    @RequestMapping("/cgCount")
    public String cgMember(@RequestParam(required = false, defaultValue = "1")boolean isCurrent,
                           Integer userId,
                           Integer teamId,
                           ModelMap modelMap) {

        modelMap.put("cgTeam",cgTeamMapper.selectByPrimaryKey(teamId));
        modelMap.put("sysUser", CmTag.getUserById(userId));
        modelMap.put("isCurrent",isCurrent);
        return "cg/cgCount/cgCount_page";
    }

    @RequiresPermissions("cgCount:list")
    @RequestMapping("/cgCount_data")
    @ResponseBody
    public void cgMember_data(HttpServletResponse response,
                              Integer teamId,
                              Integer post,
                              Byte type,
                              Integer unitPostId,
                              Integer userId,
                              @RequestParam(required = false, defaultValue = "1")Boolean isCurrent,
                              Integer pageSize, Integer pageNo) throws IOException, IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CgMemberExample example = new CgMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (teamId!=null) {
            criteria.andTeamIdEqualTo(teamId);
        }
        if (post!=null) {
            criteria.andPostEqualTo(post);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (unitPostId!=null) {
            criteria.andUnitPostIdEqualTo(unitPostId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (isCurrent!=null) {
            criteria.andIsCurrentEqualTo(isCurrent);
        }

        long count = cgMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CgMember> records= cgMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
