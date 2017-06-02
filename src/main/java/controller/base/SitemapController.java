package controller.base;

import controller.BaseController;
import domain.base.Sitemap;
import domain.base.SitemapExample;
import domain.sys.SysResource;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SitemapController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/sitemap_view")
    public String sitemap_view(ModelMap modelMap){

        List<Sitemap> userTopSitemap = sitemapService.getUserTopSitemap(ShiroHelper.getCurrentUserId());
        modelMap.put("sitemaps", userTopSitemap);

        return "base/sitemap/sitemap_view";
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/sitemap")
    public String sitemap(ModelMap modelMap) {

        modelMap.put("sitemaps", sitemapService.getSortedSitemaps().values());
        return "base/sitemap/sitemap_page";
    }
    
    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value="/sitemap_au", method= RequestMethod.POST)
    @ResponseBody
    public Map do_sitemap_au(@CurrentUser SysUserView loginUser, Sitemap sitemap, HttpServletRequest request) {

        if(sitemap.getId() == null){

            sitemapService.insert(sitemap);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加网站导航：%s", JSONUtils.toString(sitemap, false)));

        }else{

            sitemapService.updateByPrimaryKeySelective(sitemap);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新网站导航：%s", JSONUtils.toString(sitemap, false)));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/sitemap_au")
    public String sitemap_au(Integer id, Integer fid, ModelMap modelMap) {

        Sitemap sitemap = new Sitemap();
        if(id != null){

            sitemap = sitemapMapper.selectByPrimaryKey(id);
            modelMap.put("sitemap", sitemap);
            modelMap.addAttribute("op", "修改");

            Integer resourceId = sitemap.getResourceId();
            if(resourceId!=null) {
                SysResource sysResource = sysResourceMapper.selectByPrimaryKey(resourceId);
                modelMap.addAttribute("sysResource", sysResource);
            }

            Integer _fid = sitemap.getFid();
            if(_fid!=null&&_fid>0){
                Sitemap parent = sitemapMapper.selectByPrimaryKey(_fid);
                modelMap.addAttribute("parent", parent);
            }
        }
        if(fid!=null){

            Sitemap parent = sitemapMapper.selectByPrimaryKey(fid);
            modelMap.addAttribute("parent", parent);
            sitemap.setFid(fid);

            modelMap.addAttribute("op", "新增子节点");
        }

        modelMap.addAttribute("sitemap", sitemap);

        return "base/sitemap/sitemap_au";
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value="/sitemap_del", method=RequestMethod.POST)
    @ResponseBody
    public Map do_sitemap_del(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request) {

        if(id!=null){

            sitemapService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除网站导航：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/sitemap_selects")
    @ResponseBody
    public Map sitemap_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SitemapExample example = new SitemapExample();
        SitemapExample.Criteria criteria = example.createCriteria();
        criteria.andFidIsNull();
        example.setOrderByClause(" sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andTitleLike("%" + searchStr + "%");
        }

        int count = sitemapMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Sitemap> sitemaps = sitemapMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        for(Sitemap sitemap:sitemaps){

            Select2Option option = new Select2Option();
            option.setText(sitemap.getTitle());
            option.setId(sitemap.getId() + "");

            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    /*@RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value = "/sitemapRole", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sitemapRole(int id,
                              @RequestParam(required = false, value = "roleIds[]") Integer[] roleIds,
                                HttpServletRequest request) {

        sitemapService.updateRoles(id, roleIds);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "分配网站导航所属角色 %s, %s", id, StringUtils.join(roleIds, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/sitemapRole")
    public String sitemapRole(Integer id, ModelMap modelMap) throws IOException {

        if (id != null) {

            Sitemap sitemap = sitemapMapper.selectByPrimaryKey(id);
            modelMap.put("sitemap", sitemap);
        }

        TreeNode tree = sysRoleService.getTree(sitemapService.getRoleIdSet(id), false);
        modelMap.put("tree", JSONUtils.toString(tree));

        return "base/sitemap/sitemapRole";
    }*/
}
