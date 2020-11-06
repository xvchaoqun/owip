package controller.cla;

import domain.cadre.CadreView;
import domain.cla.ClaAdditionalPost;
import domain.cla.ClaAdditionalPostView;
import domain.cla.ClaAdditionalPostViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cla")
public class ClaAdditionalPostController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("claAdditionalPost:list")
    @RequestMapping("/claAdditionalPost")
    public String claAdditionalPost(Integer cadreId, ModelMap modelMap) {

        if(cadreId!=null) {
            modelMap.put("cadre", cadreService.get(cadreId));
        }
        return "cla/claAdditionalPost/claAdditionalPost_page";
    }

    @RequiresPermissions("claAdditionalPost:list")
    @RequestMapping("/claAdditionalPost_data")
    @ResponseBody
    public void claAdditionalPost_data(HttpServletResponse response,
                                          Integer cadreId,
                                          Integer postId,
                                          Integer unitId,
                                          Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ClaAdditionalPostViewExample example = new ClaAdditionalPostViewExample();
        ClaAdditionalPostViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("cadre_status desc, cadre_sort_order desc, unit_sort_order desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }

        long count = claAdditionalPostViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ClaAdditionalPostView> claAdditionalPosts =
                claAdditionalPostViewMapper.selectByExampleWithRowbounds(example,
                        new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", claAdditionalPosts);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("claAdditionalPost:edit")
    @RequestMapping(value = "/claAdditionalPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claAdditionalPost_au(ClaAdditionalPost record, HttpServletRequest request) {

        Integer id = record.getId();

        if (claAdditionalPostService.idDuplicate(id, record.getCadreId(), record.getUnitId())) {
            return failed("添加重复");
        }

        if (id == null) {
            claAdditionalPostService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "添加干部请假兼审单位：%s", record.getId()));
        } else {

            claAdditionalPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "更新干部请假兼审单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("claAdditionalPost:edit")
    @RequestMapping("/claAdditionalPost_au")
    public String claAdditionalPost_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ClaAdditionalPost claAdditionalPost = claAdditionalPostMapper.selectByPrimaryKey(id);
            modelMap.put("claAdditionalPost", claAdditionalPost);

            CadreView cadre = cadreService.get(claAdditionalPost.getCadreId());
            modelMap.put("cadre", cadre);
        }

        return "cla/claAdditionalPost/claAdditionalPost_au";
    }

    @RequiresPermissions("claAdditionalPost:del")
    @RequestMapping(value = "/claAdditionalPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map claAdditionalPost_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            claAdditionalPostService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CLA, "批量删除干部请假兼审单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
