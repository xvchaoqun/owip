package controller.base;

import controller.BaseController;
import domain.base.ShortMsg;
import domain.base.ShortMsgExample;
import domain.base.ShortMsgExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShortMsgController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("shortMsg:list")
    @RequestMapping("/shortMsg")
    public String shortMsg(Integer receiverId, Integer senderId, ModelMap modelMap) {
        if (receiverId!=null) {
            if(receiverId!=null)
                modelMap.put("receiver", sysUserService.findById(receiverId));
            if(senderId!=null)
                modelMap.put("sender", sysUserService.findById(senderId));
        }
        return "base/shortMsg/shortMsg_page";
    }
    @RequiresPermissions("shortMsg:list")
    @RequestMapping("/shortMsg_data")
    @ResponseBody
    public void shortMsg_data(HttpServletResponse response,
                              Integer receiverId,
                              Integer senderId,
                              String mobile,
                              String content,
                              @RequestDateRange DateRange _sendTime,
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ShortMsgExample example = new ShortMsgExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (receiverId!=null) {
            criteria.andReceiverIdEqualTo(receiverId);
        }
        if (senderId!=null) {
            criteria.andSenderIdEqualTo(senderId);
        }
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andMobileLike(SqlUtils.like(mobile));
        }
        if (StringUtils.isNotBlank(content)) {
            criteria.andContentLike(SqlUtils.like(content));
        }
        if (_sendTime.getStart()!=null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(_sendTime.getStart());
        }

        if (_sendTime.getEnd()!=null) {
            criteria.andCreateTimeLessThanOrEqualTo(_sendTime.getEnd());
        }

        long count = shortMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ShortMsg> shortMsgs = shortMsgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", shortMsgs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
