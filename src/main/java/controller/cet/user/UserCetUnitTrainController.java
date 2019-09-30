package controller.cet.user;

import controller.cet.CetBaseController;
import domain.cet.CetUnitTrain;
import domain.cet.CetUnitTrainExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping("/user/cet")
public class UserCetUnitTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userCetUnitTrain:list")
    @RequestMapping("/cetUnitTrain")
    public String cetUnitTrain(ModelMap modelMap) {
        
        return "cet/user/cetUnitTrain_page";
    }

    @RequiresPermissions("userCetUnitTrain:list")
    @RequestMapping("/cetUnitTrain_data")
    @ResponseBody
    public void cetUnitTrain_data(HttpServletResponse response,
                                   Integer pageSize, Integer pageNo) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetUnitTrainExample example = new CetUnitTrainExample();
        example.createCriteria().andUserIdEqualTo(userId);
        
        example.setOrderByClause("id desc");

        long count = cetUnitTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUnitTrain> records = cetUnitTrainMapper.selectByExampleWithRowbounds(example,
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
