package controller.member;

import controller.MemberBaseController;
import domain.member.MemberModify;
import domain.member.MemberModifyExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
public class MemberModifyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN, RoleConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping("/memberModify")
    public String memberModify() {

        return "member/member/memberModify_page";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN,RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN, RoleConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping("/memberModify_data")
    @ResponseBody
    public void memberModify_data(
                                 int userId,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberModifyExample example = new MemberModifyExample();
        MemberModifyExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        example.setOrderByClause("id desc");

        int count = memberModifyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberModify> memberModifys = memberModifyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberModifys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
