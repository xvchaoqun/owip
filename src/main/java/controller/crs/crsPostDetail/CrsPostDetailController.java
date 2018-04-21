package controller.crs.crsPostDetail;

import controller.crs.CrsBaseController;
import domain.crs.CrsPost;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CrsPostDetailController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step")
    public String crsPost_detail_step(Integer id, @RequestParam(required = false, defaultValue = "1") int step, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }

        if(step==2){
            modelMap.put("count", crsPostService.goupCount(id));
        }

        return "crs/crsPost/crsPost_detail/step" + step;
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail")
    public String crsPost_detail(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }
        return "crs/crsPost/crsPost_detail/index";
    }

}
