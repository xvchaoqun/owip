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
public class MobileCetCourseController extends CetBaseController {

    @RequiresPermissions("m:cetCourseList:*")
    //@RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping("/courseList")
    public String courseList(ModelMap modelMap) {

        return "mobile/index";
    }

    @RequiresPermissions("m:cetCourseList:*")
    @RequestMapping("/courseList_page")
    public String courseList_page(HttpServletResponse response,
                                 Integer pageNo, HttpServletRequest request, ModelMap modelMap) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();
        List<ICetTrain> trains = iCetMapper.selectUserCetTrainList(userId, null, null, new RowBounds());
        modelMap.put("trains", trains);

        return "cet/mobile/courseList_page";
    }
}
