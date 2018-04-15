package controller.cet.user;

import controller.cet.CetBaseController;
import domain.cet.CetShortMsg;
import domain.cet.CetShortMsgExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/8/18.
 */
@Controller
@RequestMapping("/user")
public class UserCetShortMsgController extends CetBaseController {

    @RequiresPermissions("userCetShortMsg:*")
    @RequestMapping("/cetShortMsg")
    public String cetShortMsg(ModelMap modelMap) {
        
        return "cet/user/cetShortMsg_page";
    }

    @RequiresPermissions("userCetShortMsg:list")
    @RequestMapping("/cetShortMsg_data")
    @ResponseBody
    public void cetShortMsg_data(HttpServletResponse response, Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetShortMsgExample example = new CetShortMsgExample();
        example.createCriteria()
                .andUserIdEqualTo(ShiroHelper.getCurrentUserId())
                .andSuccessEqualTo(true); // 发送成功的短信
        example.setOrderByClause("send_time desc");

        long count = cetShortMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetShortMsg> records = cetShortMsgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
