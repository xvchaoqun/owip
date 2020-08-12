package controller.abroad;

import domain.abroad.AbroadAdditionalPost;
import domain.abroad.AbroadAdditionalPostView;
import domain.abroad.AbroadAdditionalPostViewExample;
import domain.cadre.CadreView;
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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/abroad")
public class AbroadAdditionalPostController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("abroadAdditionalPost:list")
    @RequestMapping("/abroadAdditionalPost")
    public String abroadAdditionalPost(Integer cadreId, ModelMap modelMap) {

        modelMap.put("cadre", cadreService.findAll().get(cadreId));
        return "abroad/abroadAdditionalPost/abroadAdditionalPost_page";
    }

    @RequiresPermissions("abroadAdditionalPost:list")
    @RequestMapping("/abroadAdditionalPost_data")
    @ResponseBody
    public void abroadAdditionalPost_data(HttpServletResponse response,
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

        AbroadAdditionalPostViewExample example = new AbroadAdditionalPostViewExample();
        AbroadAdditionalPostViewExample.Criteria criteria = example.createCriteria();
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

        long count = abroadAdditionalPostViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<AbroadAdditionalPostView> abroadAdditionalPosts =
                abroadAdditionalPostViewMapper.selectByExampleWithRowbounds(example,
                        new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", abroadAdditionalPosts);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("abroadAdditionalPost:edit")
    @RequestMapping(value = "/abroadAdditionalPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_abroadAdditionalPost_au(AbroadAdditionalPost record, HttpServletRequest request) {

        Integer id = record.getId();

        if (abroadAdditionalPostService.idDuplicate(id, record.getCadreId(), record.getUnitId())) {
            return failed("添加重复");
        }

        if (id == null) {
            abroadAdditionalPostService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "添加干部因私兼审单位"));
        } else {

            abroadAdditionalPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "更新干部因私兼审单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("abroadAdditionalPost:edit")
    @RequestMapping("/abroadAdditionalPost_au")
    public String abroadAdditionalPost_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            AbroadAdditionalPost abroadAdditionalPost = abroadAdditionalPostMapper.selectByPrimaryKey(id);
            modelMap.put("abroadAdditionalPost", abroadAdditionalPost);

            CadreView cadre = cadreService.findAll().get(abroadAdditionalPost.getCadreId());
            modelMap.put("cadre", cadre);
        }

        return "abroad/abroadAdditionalPost/abroadAdditionalPost_au";
    }

    @RequiresPermissions("abroadAdditionalPost:del")
    @RequestMapping(value = "/abroadAdditionalPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map abroadAdditionalPost_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            abroadAdditionalPostService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量删除因私兼审单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
