package controller.member;

import domain.member.MemberInModify;
import domain.member.MemberInModifyExample;
import mixin.MixinUtils;
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
public class MemberInModifyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberInModify")
    public String memberInModify() {

        return "member/memberIn/memberInModify_page";
    }

    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberInModify_data")
    @ResponseBody
    public void memberInModify_data(

                                 int inId,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberInModifyExample example = new MemberInModifyExample();
        MemberInModifyExample.Criteria criteria = example.createCriteria();
        criteria.andInIdEqualTo(inId);
        example.setOrderByClause("id desc");

        int count = memberInModifyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberInModify> memberInModifys = memberInModifyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberInModifys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
