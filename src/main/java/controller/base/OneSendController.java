package controller.base;

import controller.BaseController;
import domain.base.OneSend;
import domain.base.OneSendExample;
import domain.base.OneSendExample.Criteria;
import ext.service.OneSendService;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.base.OneSendMapper;
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
public class OneSendController extends BaseController {

    @Autowired
    private OneSendMapper oneSendMapper;
    @Autowired
    private OneSendService oneSendService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /*@RequiresPermissions("oneSend:repeat")
    @RequestMapping(value = "/oneSend_repeat", method = RequestMethod.POST)
    @ResponseBody
    public Map oneSend_repeat(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            oneSendService.repeat(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "重复发送：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("oneSend:list")
    @RequestMapping("/oneSend")
    public String oneSend(Integer senderId, ModelMap modelMap) {

        if(senderId!=null) {
            modelMap.put("sender", sysUserService.findById(senderId));
        }
        return "base/oneSend/oneSend_page";
    }
    @RequiresPermissions("oneSend:list")
    @RequestMapping("/oneSend_data")
    @ResponseBody
    public void oneSend_data(HttpServletResponse response,
                              String receiver,
                              Integer senderId,
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

        OneSendExample example = new OneSendExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("send_time desc");

        if (StringUtils.isNotBlank(receiver)) {
            criteria.andReciversIn(receiver.trim());
        }
        if (senderId!=null) {
            criteria.andSendUserIdEqualTo(senderId);
        }
        if (StringUtils.isNotBlank(content)) {
            criteria.andContentLike(SqlUtils.like(content.trim()));
        }

        if (_sendTime.getStart()!=null) {
            criteria.andSendTimeGreaterThanOrEqualTo(_sendTime.getStart());
        }

        if (_sendTime.getEnd()!=null) {
            criteria.andSendTimeLessThanOrEqualTo(_sendTime.getEnd());
        }

        long count = oneSendMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OneSend> oneSends = oneSendMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", oneSends);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
