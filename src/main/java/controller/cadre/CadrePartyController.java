package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreParty;
import domain.cadre.CadrePartyExample;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.sys.SysUserView;
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
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadrePartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreParty:list")
    @RequestMapping("/cadreParty")
    public String cadreParty(Integer userId,ModelMap modelMap) {

        if (userId!=null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }

        return "cadre/cadreParty/cadreParty_page";
    }
    @RequiresPermissions("cadreParty:list")
    @RequestMapping("/cadreParty_data")
    public void cadreParty_data(HttpServletResponse response,
                           byte type,
                           Byte status,
                           Integer userId,
                           Integer typeId,
                           Integer postId,
                           Integer dpTypeId,
                           String title,
                           Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreViewExample example = new CadreViewExample();
        CadreViewExample.Criteria criteria = example.createCriteria();

        if(type==1)
            criteria.andDpIdIsNotNull();
        else if(type==2)
            criteria.andOwIdIsNotNull();

        example.setOrderByClause("sort_order desc");
        if (status!=null) {
            criteria.andStatusEqualTo(status);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (dpTypeId!=null) {
            criteria.andDpTypeIdEqualTo(dpTypeId);
        }
        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike("%" + title + "%");
        }

        long count = cadreViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreView> Cadres = cadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(CadreView.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    public boolean idDuplicate(Integer id, int userId, byte type){

        CadrePartyExample example = new CadrePartyExample();
        CadrePartyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(type);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadrePartyMapper.countByExample(example) > 0;
    }

    @RequiresPermissions("cadreParty:edit")
    @RequestMapping(value = "/cadreParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreParty_au(CadreParty record,
                                HttpServletRequest request) {

        if(idDuplicate(record.getId(), record.getUserId(), record.getType())){

            return failed("添加重复");
        }

        cadreService.addOrUPdateCadreParty(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部党派：%s", JSONUtils.toString(record, false)));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreParty:edit")
    @RequestMapping("/cadreParty_au")
    public String cadreParty_au(Integer id, Byte type, ModelMap modelMap) {

        if(id!=null) {
            CadreParty cadreParty = cadrePartyMapper.selectByPrimaryKey(id);
            modelMap.put("cadreParty", cadreParty);
            if(cadreParty!=null){
                type = cadreParty.getType();
            }
            modelMap.put("sysUser", sysUserService.findById(cadreParty.getUserId()));
        }
        modelMap.put("type", type);

        return "cadre/cadreParty/cadreParty_au";
    }

    @RequiresPermissions("cadreParty:del")
    @RequestMapping(value = "/cadreParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            cadreService.cadreParty_batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部党派：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }


}
