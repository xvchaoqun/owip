package controller.cadre;

import controller.BaseController;
import domain.Cadre;
import domain.CadreAdditionalPost;
import domain.CadreAdditionalPostExample;
import domain.CadreAdditionalPostExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class CadreAdditionalPostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("cadreAdditionalPost:list")
    @RequestMapping("/cadre_additional_post")
    public String cadre_additional_post(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id!=null) {
            Cadre cadre = cadreService.findAll().get(id);
            modelMap.put("cadre", cadre);

            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            CadreAdditionalPostExample example = new CadreAdditionalPostExample();
            CadreAdditionalPostExample.Criteria criteria = example.createCriteria();
            criteria.andCadreIdEqualTo(id);

            int count = cadreAdditionalPostMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<CadreAdditionalPost> cadreAdditionalPosts = cadreAdditionalPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("cadreAdditionalPosts", cadreAdditionalPosts);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id!=null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }

        return "cadre/cadreAdditionalPost/cadre_additional_post";
    }

    @RequiresPermissions("cadreAdditionalPost:list")
    @RequestMapping("/cadreAdditionalPost")
    public String cadreAdditionalPost() {

        return "index";
    }
    @RequiresPermissions("cadreAdditionalPost:list")
    @RequestMapping("/cadreAdditionalPost_page")
    public String cadreAdditionalPost_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "base_cadre_additional_post") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                    Integer unitId,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreAdditionalPostExample example = new CadreAdditionalPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }

        int count = cadreAdditionalPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreAdditionalPost> cadreAdditionalPosts = cadreAdditionalPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadreAdditionalPosts", cadreAdditionalPosts);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (unitId!=null) {
            searchStr += "&unitId=" + unitId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "cadre/cadreAdditionalPost/cadreAdditionalPost_page";
    }

    @RequiresPermissions("cadreAdditionalPost:edit")
    @RequestMapping(value = "/cadreAdditionalPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdditionalPost_au(CadreAdditionalPost record, HttpServletRequest request) {

        Integer id = record.getId();

        if (cadreAdditionalPostService.idDuplicate(id, record.getCadreId(), record.getUnitId())) {
            return failed("添加重复");
        }
        if (id == null) {
            cadreAdditionalPostService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "添加干部附属职务属性：%s", record.getId()));
        } else {

            //cadreAdditionalPostService.updateByPrimaryKeySelective(record);
            //logger.info(addLog( SystemConstants.LOG_ADMIN, "更新干部附属职务属性：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreAdditionalPost:edit")
    @RequestMapping("/cadreAdditionalPost_au")
    public String cadreAdditionalPost_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreAdditionalPost cadreAdditionalPost = cadreAdditionalPostMapper.selectByPrimaryKey(id);
            modelMap.put("cadreAdditionalPost", cadreAdditionalPost);
        }
        return "cadre/cadreAdditionalPost/cadreAdditionalPost_au";
    }

    @RequiresPermissions("cadreAdditionalPost:del")
    @RequestMapping(value = "/cadreAdditionalPost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreAdditionalPost_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            CadreAdditionalPost cPost = cadreAdditionalPostMapper.selectByPrimaryKey(id);
            cadreAdditionalPostService.del(cPost.getId(), cPost.getCadreId());
            logger.info(addLog( SystemConstants.LOG_ADMIN, "删除干部附属职务属性：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

   /* @RequiresPermissions("cadreAdditionalPost:del")
    @RequestMapping(value = "/cadreAdditionalPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreAdditionalPostService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_ADMIN, "批量删除干部附属职务属性：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
