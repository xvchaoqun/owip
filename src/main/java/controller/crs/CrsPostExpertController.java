package controller.crs;

import domain.crs.CrsPostExpert;
import domain.crs.CrsPostExpertExample;
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
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
public class CrsPostExpertController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsPostExpert:list")
    @RequestMapping("/crsPostExperts_tree")
    @ResponseBody
    public Map crsPostExperts_tree(int postId, byte role) throws IOException {

        List<Integer> expertUserIds = crsPostExpertService.getExpertUserIds(postId, role);
        TreeNode tree = crsExpertService.getTree(new HashSet<>(expertUserIds));

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("crsPostExpert:list")
    @RequestMapping("/crsPostExperts")
    public String crsPostExperts() throws IOException {

        return "crs/crsPostExpert/crsPostExperts";
    }

    @RequiresPermissions("crsPostExpert:edit")
    @RequestMapping(value = "/crsPostExperts", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPostExperts(Integer postId,
                                 @RequestParam(value = "headUserIds[]", required = false) Integer[] headUserIds,
                                 @RequestParam(value = "leaderUserIds[]", required = false) Integer[] leaderUserIds,
                                 @RequestParam(value = "memberUserIds[]", required = false) Integer[] memberUserIds) {

        crsPostExpertService.updateExpertUserIds(postId, headUserIds, leaderUserIds, memberUserIds);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPostExpert:list")
    @RequestMapping("/crsPostExpert")
    public String crsPostExpert() {

        return "crs/crsPostExpert/crsPostExpert_page";
    }

    @RequiresPermissions("crsPostExpert:list")
    @RequestMapping("/crsPostExpert_data")
    public void crsPostExpert_data(HttpServletResponse response,
                                   Integer postId,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsPostExpertExample example = new CrsPostExpertExample();
        CrsPostExpertExample.Criteria criteria = example.createCriteria().andPostIdEqualTo(postId);
        example.setOrderByClause("sort_order desc");


        long count = crsPostExpertMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsPostExpert> records = crsPostExpertMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsPostExpert:edit")
    @RequestMapping("/crsPostExpert_au")
    public String crsPostExpert_au(Integer id, Integer postId, ModelMap modelMap) {

        if (id != null) {
            CrsPostExpert crsPostExpert = crsPostExpertMapper.selectByPrimaryKey(id);
            modelMap.put("crsPostExpert", crsPostExpert);

            if (crsPostExpert != null) {

                postId = crsPostExpert.getPostId();

                modelMap.put("sysUser", sysUserService.findById(crsPostExpert.getUserId()));
            }
        }
        modelMap.put("postId", postId);

        return "crs/crsPostExpert/crsPostExpert_au";
    }

    @RequiresPermissions("crsPostExpert:edit")
    @RequestMapping(value = "/crsPostExpert_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPostExpert_au(CrsPostExpert record, HttpServletRequest request) {


        Integer id = record.getId();

        if (crsPostExpertService.idDuplicate(id, record.getPostId(), record.getUserId())) {
            return failed("添加重复");
        }

        if (id == null) {

            crsPostExpertService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加岗位专家：%s", id));
        } else {

            crsPostExpertService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新岗位专家：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("crsPostExpert:edit")
    @RequestMapping(value = "/crsPostExpert_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPostExpert_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crsPostExpertService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CRS, "岗位专家调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("crsPostExpert:del")
    @RequestMapping(value = "/crsPostExpert_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPostExpert_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {

            crsPostExpertService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除岗位专家：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }
}
