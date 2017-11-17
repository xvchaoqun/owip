package controller.user.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMemberPayViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/pmd")
public class UserPmdMemberController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userPmdMember:list")
    @RequestMapping("/pmdMember")
    public String pmdMember() {

        return "user/pmd/pmdMember_page";
    }

    @RequiresPermissions("userPmdMember:list")
    @RequestMapping("/pmdMember_data")
    public void pmdMember_data(HttpServletResponse response,
                               @DateTimeFormat(pattern = "yyyy-MM") Date payMonth,
                               Boolean hasPay,
                               Boolean isDelay,
                               Boolean isOnlinePay,
                            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int userId = ShiroHelper.getCurrentUserId();
        PmdMemberPayViewExample example = new PmdMemberPayViewExample();
        PmdMemberPayViewExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId);
        example.setOrderByClause("month_id desc");

        if(payMonth!=null){
            criteria.andPayMonthEqualTo(DateUtils.getFirstDateOfMonth(payMonth));
        }
        if (hasPay != null) {
            criteria.andHasPayEqualTo(hasPay);
        }
        if (isDelay != null) {
            if(BooleanUtils.isFalse(isDelay)){
                // 按时缴费，肯定已缴费
                criteria.andHasPayEqualTo(true);
            }
            criteria.andIsDelayEqualTo(isDelay);
        }
        if (isOnlinePay != null) {
            criteria.andIsOnlinePayEqualTo(isOnlinePay);
        }

        long count = pmdMemberPayViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdMemberPayView> records = pmdMemberPayViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdMember.class, pmdMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
