package controller.sys;

import controller.BaseController;
import domain.sys.HtmlFragment;
import domain.sys.HtmlFragmentExample;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.helper.ShiroHelper;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by fafa on 2016/6/17.
 */
@Controller
public class HtmlFragmentController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("htmlFragment:list")
    @RequestMapping("/htmlFragment")
    public String htmlFragment() {

        return "index";
    }

    @RequiresPermissions("htmlFragment:list")
    @RequestMapping("/htmlFragment_page")
    public String htmlFragment_page() {

        return "sys/htmlFragment/htmlFragment_page";
    }
    @RequiresPermissions("htmlFragment:list")
    @RequestMapping("/htmlFragment_data")
    @ResponseBody
    public void htmlFragment_data(@CurrentUser SysUserView loginUser,
                               HttpServletResponse response,
                               String title, String code,
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        HtmlFragmentExample example = new HtmlFragmentExample();
        HtmlFragmentExample.Criteria criteria = example.createCriteria();

        if (!ShiroHelper.hasRole(SystemConstants.ROLE_ADMIN)) {

            Set<Integer> roleIdSet = sysUserService.getUserRoleIdSet(loginUser.getRoleIds());
            criteria.andRoleIdIn(new ArrayList<>(roleIdSet));
        }

        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }

        int count = htmlFragmentMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<HtmlFragment> HtmlFragments = htmlFragmentMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", HtmlFragments);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(HtmlFragment.class, HtmlFragmentMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
    @RequiresPermissions("HtmlFragment:edit")
    @RequestMapping("/htmlFragment_au")
    public String htmlFragment_note_au(String code, Integer id, ModelMap modelMap) {

        HtmlFragment htmlFragment = null;
        if(id !=null) // 以id为主
            htmlFragment = htmlFragmentMapper.selectByPrimaryKey(id);
        else
            htmlFragment = htmlFragmentService.codeKeyMap().get(code);
        modelMap.put("htmlFragment", htmlFragment);

        return "sys/htmlFragment/htmlFragment_au";
    }

    @RequiresPermissions("HtmlFragment:edit")
    @RequestMapping(value = "/htmlFragment_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_htmlFragment_note_au(HtmlFragment record, ModelMap modelMap) {

        if (record.getId() == null) {
            if (StringUtils.isBlank(record.getCode()))
                record.setCode(htmlFragmentService.genCode());
            htmlFragmentService.insertSelective(record);
        } else {
            htmlFragmentService.updateByPrimaryKeySelective(record);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("htmlFragment:del")
    @RequestMapping(value = "/htmlFragment_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            htmlFragmentService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除配置：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value = "/htmlFragmentRole", method = RequestMethod.POST)
    @ResponseBody
    public Map do_htmlFragmentRole(int id,
                                Integer roleId,
                                HttpServletRequest request) {

        if (roleId == null) {
            roleId = -1;
        }
        htmlFragmentService.updateRoles(id, roleId);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新系统配置所属角色 %s, %s", id, roleId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/htmlFragmentRole")
    public String htmlFragmentRole(Integer id, ModelMap modelMap) throws IOException {

        Set<Integer> selectIdSet = new HashSet<Integer>();
        if (id != null) {

            HtmlFragment htmlFragment = htmlFragmentMapper.selectByPrimaryKey(id);
            selectIdSet.add(htmlFragment.getRoleId());
            modelMap.put("htmlFragment", htmlFragment);
        }

        TreeNode tree = sysRoleService.getTree(selectIdSet, false);
        modelMap.put("tree", JSONUtils.toString(tree));

        return "sys/htmlFragment/htmlFragmentRole";
    }

    @RequestMapping("/hf_content")
    public String hf_content(String code, Integer id, ModelMap modelMap) {

        HtmlFragment htmlFragment = null;
        if(id !=null) // 以id为主
            htmlFragment = htmlFragmentMapper.selectByPrimaryKey(id);
        else
            htmlFragment = htmlFragmentService.codeKeyMap().get(code);

        modelMap.put("htmlFragment", htmlFragment);

        return "sys/htmlFragment/hf_content";
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/htmlFragment_selects")
    @ResponseBody
    public Map htmlFragment_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        HtmlFragmentExample example = new HtmlFragmentExample();
        HtmlFragmentExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" sort_order desc");

        if(org.apache.commons.lang.StringUtils.isNotBlank(searchStr)){
            criteria.andTitleLike("%" + searchStr + "%");
            example.or(example.createCriteria().andRemarkLike("%" + searchStr + "%"));
        }

        int count = htmlFragmentMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<HtmlFragment> htmlFragments = htmlFragmentMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        for(HtmlFragment htmlFragment:htmlFragments){

            Select2Option option = new Select2Option();
            option.setText(htmlFragment.getTitle());
            option.setId(htmlFragment.getId() + "");

            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
