package controller.crs.crsPostDetail;

import controller.CrsBaseController;
import domain.crs.CrsPost;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.SystemConstants;

import java.util.List;
import java.util.Map;

@Controller
public class CrsPostDetailController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail/step")
    public String crsPost_detail_step(Integer id, @RequestParam(required = false, defaultValue = "1") int step, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }

        if(step==2){
            int[] count = new int[]{-1, 0,0,0,0};
            List<Map> sta = iCrsMapper.applicantStatic(id, SystemConstants.CRS_APPLICANT_STATUS_SUBMIT);
            for (Map entity : sta) {

                byte require_check_status = -1;
                if(entity.get("require_check_status")!=null){
                    require_check_status = ((Integer) entity.get("require_check_status")).byteValue();
                }
                boolean is_require_check_pass = ((Integer) entity.get("is_require_check_pass")==1);
                boolean is_quit = BooleanUtils.isTrue((Boolean)entity.get("is_quit"));

                int num = ((Long) entity.get("num")).intValue();

                // cls==1
                if(is_quit==false) {
                    if (require_check_status == SystemConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT) {
                        count[1] += num;
                    }
                    if (is_require_check_pass) {
                        count[2] += num;
                    } else if (require_check_status == SystemConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS) {
                        count[3] += num;
                    }
                }else{
                    count[4] += num;
                }
            }
            modelMap.put("count", count);
        }

        return "crs/crsPost/crsPost_detail/step" + step;
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_detail")
    public String crsPost_detail(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }
        return "crs/crsPost/crsPost_detail/index";
    }

}
