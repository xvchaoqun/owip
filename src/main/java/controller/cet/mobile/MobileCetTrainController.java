package controller.cet.mobile;

import controller.cet.CetBaseController;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.cet.common.ICetTrain;
import shiro.ShiroHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lm on 2018/6/20.
 */
@Controller
@RequestMapping("/m/cet")
public class MobileCetTrainController extends CetBaseController {

    @RequiresPermissions("m:cetTrainList:*")
    @RequestMapping("/trainList")
    public String trainList(ModelMap modelMap) {

        return "mobile/index";
    }

    @RequiresPermissions("m:cetTrainList:*")
    @RequestMapping("/trainList_page")
    public String trainList_page(HttpServletResponse response,
                                 Integer pageNo, HttpServletRequest request, ModelMap modelMap) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();
        List<ICetTrain> trains = iCetMapper.selectUserCetTrainList(userId, null, false, new RowBounds());
        modelMap.put("trains", trains);

        return "cet/mobile/trainList_page";
    }
}
