package controller.base;

import controller.BaseController;
import domain.base.ContentTpl;
import domain.base.ContentTplExample;
import domain.base.ContentTplExample.Criteria;
import domain.base.ShortMsgReceiver;
import domain.base.ShortMsgReceiverExample;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import sys.constants.ContentTplConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ContentTplController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("contentTpl:list")
    @RequestMapping("/contentTpl")
    public String contentTpl(HttpServletResponse response, Byte type, String content,
                             @RequestParam(required = false, defaultValue = "0") boolean isDeleted,
                             ModelMap modelMap) {

        modelMap.put("isDeleted",isDeleted);
        return "base/contentTpl/contentTpl_page";
    }
    @RequiresPermissions("contentTpl:list")
    @RequestMapping("/contentTpl_data")
    public void contentTpl_data(HttpServletResponse response,
                                    Byte type,
                                    String name,
                                    String content,
                                    String code,
                                @RequestParam(required = false, defaultValue = "0") Boolean isDeleted,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ContentTplExample example = new ContentTplExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (StringUtils.isNotBlank(content)) {
            criteria.andContentLike(SqlUtils.like(content));
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (isDeleted!=null) {
            criteria.andIsDeletedEqualTo(isDeleted);
        }
        if (export == 1) {
            contentTpl_export(example, response);
            return;
        }

        long count = contentTplMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ContentTpl> contentTpls = contentTplMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", contentTpls);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(ContentTpl.class, ContentTplMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("contentTpl:edit")
    @RequestMapping(value = "/contentTpl_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_contentTpl_au(@CurrentUser SysUserView loginUser,
                                ContentTpl record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setContent(record.getContent());
        if (StringUtils.isNotBlank(record.getCode()) && contentTplService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }
        if (id == null) {
            record.setCreateTime(new Date());
            record.setUserId(loginUser.getId());
            contentTplService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加内容模板：%s", record.getId()));
        } else {

            contentTplService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新内容模板：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("contentTpl:edit")
    @RequestMapping("/contentTpl_au")
    public String contentTpl_au(Integer id,  @RequestParam(required = false,
            defaultValue = ContentTplConstants.CONTENT_TPL_CONTENT_TYPE_STRING+"") Byte contentType,
                                ModelMap modelMap) {

        if (id != null) {
            ContentTpl contentTpl = contentTplMapper.selectByPrimaryKey(id);
            contentType = contentTpl.getContentType();
            modelMap.put("contentTpl", contentTpl);
        }

        modelMap.put("contentType", contentType);

        return "base/contentTpl/contentTpl_au";
    }

    @RequiresPermissions("contentTpl:del")
    @RequestMapping(value = "/contentTpl_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_contentTpl_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            contentTplService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除内容模板：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    //返回列表
    @RequiresPermissions("contentTpl:del")
    @RequestMapping(value = "/contentTpl_batchUnDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchUnDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            contentTplService.batchUnDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量返回内容模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //删除消息模板
    @RequiresPermissions("contentTpl:del")
    @RequestMapping(value = "/contentTpl_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            contentTplService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除内容模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //彻底删除消息模板
    @RequiresPermissions("contentTpl:del")
    @RequestMapping(value = "/contentTpl_doBatchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map doBatchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            contentTplService.doBatchUnDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "彻底删除内容模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping(value = "/contentTplRole", method = RequestMethod.POST)
    @ResponseBody
    public Map do_contentTplRole(int id,
                                Integer roleId,
                                HttpServletRequest request) {

        if (roleId == null) {
            roleId = -1;
        }
        contentTplService.updateRoles(id, roleId);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新模板所属角色 %s, %s", id, roleId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping("/contentTplRole")
    public String contentTplRole(Integer id, ModelMap modelMap) throws IOException {

        Set<Integer> selectIdSet = new HashSet<Integer>();
        if (id != null) {

            ContentTpl contentTpl = contentTplMapper.selectByPrimaryKey(id);
            selectIdSet.add(contentTpl.getRoleId());
            modelMap.put("contentTpl", contentTpl);
        }

        TreeNode tree = sysRoleService.getTree(selectIdSet, false);
        modelMap.put("tree", JSONUtils.toString(tree));

        return "base/contentTpl/contentTplRole";
    }

    @RequiresPermissions("contentTpl:changeOrder")
    @RequestMapping(value = "/contentTpl_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_contentTpl_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        contentTplService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "模板调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void contentTpl_export(ContentTplExample example, HttpServletResponse response) {

        List<ContentTpl> records = contentTplMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"ID","所属角色","类型","代码","模板内容","模板引擎","模板参数数量","参数名称","添加人","添加时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ContentTpl record = records.get(i);
            String[] values = {
                record.getId()+"",
                            record.getRoleId()+"",
                            record.getType()+"",
                            record.getCode()+"",
                            record.getContent(),
                            record.getEngine() + "",
                            record.getParamCount()+"",
                            record.getParamNames(),
                            record.getUserId()+"",
                            DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "内容模板_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }


    @RequiresPermissions("contentTpl:list")
    @RequestMapping("/contentTpl_receivers")
    public String contentTpl_receivers(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            ShortMsgReceiverExample example = new ShortMsgReceiverExample();
            ShortMsgReceiverExample.Criteria criteria = example.createCriteria().andTplIdEqualTo(id);

            int count = shortMsgReceiverMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<ShortMsgReceiver> records = shortMsgReceiverMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("records", records);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id!=null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            ContentTpl contentTpl = contentTplMapper.selectByPrimaryKey(id);
            modelMap.put("contentTpl", contentTpl);
        }

        return "base/contentTpl/contentTpl_receivers";
    }
}
