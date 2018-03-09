package controller.cet.cetTrainDetail;

import controller.cet.CetBaseController;
import domain.cet.CetTrain;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cet")
public class CetTrainDetailController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail/step")
    public String cetTrain_detail_step(Integer id, @RequestParam(required = false, defaultValue = "1") int step, ModelMap modelMap) {

        if (id != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrain", cetTrain);
        }
        
        return "cet/cetTrain/cetTrain_detail/step" + step;
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail")
    public String cetTrain_detail(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrain", cetTrain);
        }
        return "cet/cetTrain/cetTrain_detail/index";
    }

}
