package controller.party;

import controller.BaseController;
import domain.member.MemberOutModify;
import domain.member.MemberOutModifyExample;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberOutModifyMixin;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberOutModifyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOutModify_page")
    public String memberOutModify_page() {

        return "party/memberOut/memberOutModify_page";
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOutModify_data")
    @ResponseBody
    public void memberOutModify_data(
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "ow_member_out_modify") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 int outId,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberOutModifyExample example = new MemberOutModifyExample();
        MemberOutModifyExample.Criteria criteria = example.createCriteria();
        criteria.andOutIdEqualTo(outId);
        example.setOrderByClause(String.format("%s %s", sort, order));

        int count = memberOutModifyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberOutModify> memberOutModifys = memberOutModifyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberOutModifys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberOutModify.class, MemberOutModifyMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
}
