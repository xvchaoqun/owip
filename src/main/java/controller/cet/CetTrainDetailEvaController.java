package controller.cet;

import domain.cet.CetTrain;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cet/cetTrain_detail_eva")
public class CetTrainDetailEvaController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("")
    public String menu(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        return "cet/cetTrain/cetTrain_detail_eva/menu";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/inspectors")
    public String inspectors(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        return "cet/cetTrain/cetTrain_detail_eva/inspectors";
    }
}
