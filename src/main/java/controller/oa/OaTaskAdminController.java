package controller.oa;

import domain.base.MetaType;
import domain.oa.OaTaskAdmin;
import domain.oa.OaTaskAdminExample;
import domain.oa.OaTaskAdminExample.Criteria;
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
import sys.tags.CmTag;
import sys.tool.fancytree.TreeNode;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/oa")
public class OaTaskAdminController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("oaTaskAdmin:list")
    @RequestMapping("/oaTaskAdmin")
    public String oaTaskAdmin(Integer userId, ModelMap modelMap) {

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        return "oa/oaTaskAdmin/oaTaskAdmin_page";
    }

    @RequiresPermissions("oaTaskAdmin:list")
    @RequestMapping("/oaTaskAdmin_data")
    @ResponseBody
    public void oaTaskAdmin_data(HttpServletResponse response,
                                 Integer userId,
                                 Integer type,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaTaskAdminExample example = new OaTaskAdminExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type != null) {
            criteria.andTypesContain(type);
        }

        long count = oaTaskAdminMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OaTaskAdmin> records = oaTaskAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(oaTaskAdmin.class, oaTaskAdminMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("oaTaskAdmin:edit")
    @RequestMapping(value = "/oaTaskAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskAdmin_au(OaTaskAdmin record, HttpServletRequest request) {

        Integer userId = record.getUserId();
        OaTaskAdmin oaTaskAdmin = oaTaskAdminMapper.selectByPrimaryKey(userId);
        if (oaTaskAdmin == null) {

            oaTaskAdminService.insertSelective(record);
            logger.info(log(LogConstants.LOG_OA, "添加协同办公任务管理员：{0}", userId));
        } else {

            oaTaskAdminService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_OA, "更新协同办公任务管理员：{0}", userId));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaTaskAdmin:edit")
    @RequestMapping("/oaTaskAdmin_au")
    public String oaTaskAdmin_au(Integer id, ModelMap modelMap) {

        Set<Integer> selectTaskTypeSet = new HashSet<>();
        if (id != null) {
            OaTaskAdmin oaTaskAdmin = oaTaskAdminMapper.selectByPrimaryKey(id);
            modelMap.put("oaTaskAdmin", oaTaskAdmin);
            String types = oaTaskAdmin.getTypes();
            if (StringUtils.isNotBlank(types)) {
                for (String _type : types.split(",")) {
                    selectTaskTypeSet.add(Integer.valueOf(_type));
                }
            }
        }

        TreeNode root = new TreeNode();
        root.title = "选择工作类型";
        root.expanded = true;
        root.folder = true;
        root.checkbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> metaTypeMap = CmTag.getMetaTypes("mc_oa_task_type");
        for (MetaType taskType : metaTypeMap.values()) {

            TreeNode treeNode = new TreeNode();
            treeNode.key = taskType.getId() + "";
            treeNode.title = taskType.getName();
            treeNode.selected = selectTaskTypeSet.contains(taskType.getId());
            treeNode.checkbox = true;
            rootChildren.add(treeNode);
        }
        modelMap.put("treeData", root);

        return "oa/oaTaskAdmin/oaTaskAdmin_au";
    }

    @RequiresPermissions("oaTaskAdmin:del")
    @RequestMapping(value = "/oaTaskAdmin_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map oaTaskAdmin_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            oaTaskAdminService.batchDel(ids);
            logger.info(log(LogConstants.LOG_OA, "批量删除协同办公任务管理员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
