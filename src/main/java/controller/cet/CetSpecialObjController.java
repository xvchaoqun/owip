package controller.cet;

import domain.cet.CetSpecial;
import domain.cet.CetSpecialObjCadreView;
import domain.cet.CetSpecialObjCadreViewExample;
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
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/cet")
public class CetSpecialObjController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetSpecialObj:list")
    @RequestMapping("/cetSpecialObj")
    public String cetSpecialObj(
            Integer userId,
            @RequestParam(defaultValue = "1") Integer cls,
            @RequestParam(required = false,
            defaultValue = CetConstants.CET_SPECIAL_OBJ_TYPE_CADRE+"") byte type, ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);

        if(userId!=null)
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "cet/cetSpecialObj/cetSpecialObj_page";
    }

    @RequiresPermissions("cetSpecialObj:list")
    @RequestMapping("/cetSpecialObj_data")
    public void cetSpecialObj_data(HttpServletResponse response,
                                   @RequestParam(defaultValue = "1") Integer cls,
                                    Integer specialId,
                                   Integer userId,
                                   @RequestParam(required = false,
                                           defaultValue = CetConstants.CET_SPECIAL_OBJ_TYPE_CADRE+"") byte type,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetSpecialObjCadreViewExample example = new CetSpecialObjCadreViewExample();
        CetSpecialObjCadreViewExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        //example.setOrderByClause("id desc");

        criteria.andIsQuitEqualTo(cls==2);

        if (specialId!=null) {
            criteria.andSpecialIdEqualTo(specialId);
        }
        if(userId!=null){
            criteria.andUserIdEqualTo(userId);
        }

        long count = cetSpecialObjCadreViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetSpecialObjCadreView> records= cetSpecialObjCadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetSpecialObj.class, cetSpecialObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }


    @RequiresPermissions("cetSpecialObj:edit")
    @RequestMapping(value = "/cetSpecialObj_quit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetSpecialObj_add(boolean isQuit,
                                 @RequestParam(value = "ids[]", required = false) Integer[] ids ,
                                 HttpServletRequest request) {

        cetSpecialObjService.quit(isQuit, ids);
        logger.info(addLog(SystemConstants.LOG_CET, "培训对象： %s, %s", isQuit?"退出":"重新学习",
                StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetSpecialObj:edit")
    @RequestMapping(value = "/cetSpecialObj_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetSpecialObj_add(int specialId, byte type,
                                 @RequestParam(value = "userIds[]", required = false) Integer[] userIds ,
                                 HttpServletRequest request) {

        cetSpecialObjService.addOrUpdate(specialId, type, userIds);
        logger.info(addLog(SystemConstants.LOG_CET, "编辑培训对象： %s, %s, %s", specialId, type,
                StringUtils.join(userIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetSpecialObj:edit")
    @RequestMapping("/cetSpecialObj_add")
    public String cetSpecialObj_add(int specialId, byte type, ModelMap modelMap) {

        CetSpecial cetSpecial = cetSpecialMapper.selectByPrimaryKey(specialId);
        modelMap.put("cetSpecial", cetSpecial);

        switch (type) {
            // 中层干部
            case CetConstants.CET_SPECIAL_OBJ_TYPE_CADRE:
                return "cet/cetSpecialObj/cetSpecialObj_selectCadres";
        }

        return null;
    }

    @RequiresPermissions("cetSpecialObj:edit")
    @RequestMapping("/cetSpecialObj_selectCadres_tree")
    @ResponseBody
    public Map cetSpecialObj_selectCadres_tree(int specialId) throws IOException {

        Set<Integer> selectIdSet = cetSpecialObjService.getSelectedSpecialObjUserIdSet(specialId);

        Set<Byte> cadreStatusList = new HashSet<>();
        cadreStatusList.add(SystemConstants.CADRE_STATUS_MIDDLE);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }
    @RequiresPermissions("cetSpecialObj:del")
    @RequestMapping(value = "/cetSpecialObj_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetSpecialObj_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetSpecialObjService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除培训对象：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetSpecialObj:del")
    @RequestMapping(value = "/cetSpecialObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetSpecialObj_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetSpecialObjService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除培训对象：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
