package controller.cet.user;

import controller.cet.CetBaseController;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.common.bean.ICetTrain;
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
public class UserCetTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain")
    public String cetTrain(@RequestParam(defaultValue = "1") Integer module, ModelMap modelMap) {

        modelMap.put("module", module);

        return "cet/user/cetTrain/cetTrain_page";
    }

    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain_data")
    public void cetTrain_data(@RequestParam(defaultValue = "1") Integer module,
                              Boolean isFinished,
                              HttpServletResponse response,
                              Integer pageSize, Integer pageNo)  throws IOException{

        int userId = ShiroHelper.getCurrentUserId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        Boolean hasSelected = null;
        if(module==2){ // 参训情况
            hasSelected = true;
        }

        long count = iCetMapper.countUserCetTrains(userId, hasSelected, isFinished);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ICetTrain> records= iCetMapper.findUserCetTrains(userId, hasSelected, isFinished,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrain.class, cetTrainMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
