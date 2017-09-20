package controller.user.crs;

import controller.CrsBaseController;
import domain.crs.CrsShortMsg;
import domain.crs.CrsShortMsgExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class UserCrsShortMsgController extends CrsBaseController {

    @RequiresPermissions("userCrsShortMsg:*")
    @RequestMapping("/crsShortMsg")
    public String crsShortMsg(ModelMap modelMap) {
        
        return "user/crs/crsShortMsg_page";
    }

    @RequiresPermissions("userCrsShortMsg:list")
    @RequestMapping("/crsShortMsg_data")
    public void crsShortMsg_data(HttpServletResponse response, Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsShortMsgExample example = new CrsShortMsgExample();
        example.createCriteria()
                .andUserIdEqualTo(ShiroHelper.getCurrentUserId())
                .andSuccessEqualTo(true); // 发送成功的短信
        example.setOrderByClause("send_time desc");

        long count = crsShortMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsShortMsg> records = crsShortMsgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
