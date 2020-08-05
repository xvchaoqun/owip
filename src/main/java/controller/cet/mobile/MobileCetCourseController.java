package controller.cet.mobile;

import controller.cet.CetBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Created by lm on 2018/6/20.
 */
@Controller
@RequestMapping("/m/cet")
public class MobileCetCourseController extends CetBaseController {

    @RequiresPermissions("m:cetTrainList:*")
    @RequestMapping("/courseList_page")
    public String courseList_page(Integer trainId, Integer projectId, ModelMap modelMap) throws IOException {

        cetTrainObjService.trainDetail(trainId, projectId, modelMap);

        return "cet/mobile/courseList_page";
    }
}
