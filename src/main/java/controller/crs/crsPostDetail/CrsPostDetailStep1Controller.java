package controller.crs.crsPostDetail;

import controller.crs.CrsBaseController;
import domain.crs.CrsPost;
import domain.crs.CrsPostRequire;
import domain.crs.CrsPostWithBLOBs;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CrsPostDetailStep1Controller extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step1_base")
    public String step1_base(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }
        return "crs/crsPost/crsPost_detail/step1_base";
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step1_notice")
    public String step1_notice(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }
        return "crs/crsPost/crsPost_detail/step1_notice";
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step1_postDuty")
    public String step1_postDuty(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }
        return "crs/crsPost/crsPost_detail/step1_postDuty";
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step1_require")
    public String step1_require(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);

            if (crsPost.getPostRequireId() != null) {
                Map<Integer, CrsPostRequire> postRequireMap = crsPostRequireService.findAll();
                modelMap.put("crsPostRequire", postRequireMap.get(crsPost.getPostRequireId()));
            }
        }

        return "crs/crsPost/crsPost_detail/step1_require";
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step1_require_au")
    public String step1_require_au(int id, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
        modelMap.put("crsPost", crsPost);

        if (crsPost.getPostRequireId() != null) {
            Map<Integer, CrsPostRequire> postRequireMap = crsPostRequireService.findAll();
            modelMap.put("crsPostRequire", postRequireMap.get(crsPost.getPostRequireId()));
        }

        Map<Integer, CrsPostRequire> postRequireMap = crsPostRequireService.findAll();
        modelMap.put("crsPostRequires", postRequireMap.values());

        return "crs/crsPost/crsPost_detail/step1_require_au";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step1_require_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step1_require_au(int id, int postRequireId, HttpServletRequest request) {

        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setId(id);
        record.setPostRequireId(postRequireId);
        crsPostService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CRS, "更新岗位要求：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_detail/step1_meetingApplyCount")
    public String step1_meetingApplyCount(int id, ModelMap modelMap) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
        modelMap.put("crsPost", crsPost);

        return "crs/crsPost/crsPost_detail/step1_meetingApplyCount";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_detail/step1_meetingApplyCount", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step1_meetingApplyCount(int id, int meetingApplyCount, HttpServletRequest request) {

        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setId(id);
        record.setMeetingApplyCount(meetingApplyCount);
        crsPostService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CRS, "更新岗位招聘会人数要求：%s", id));

        return success(FormUtils.SUCCESS);
    }
}
