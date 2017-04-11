package controller.cadre;

import controller.BaseController;
import domain.cadre.*;
import domain.base.MetaType;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadrePostProController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostPro_metaTypes")
    @ResponseBody
    public Map cadrePostPro_metaTypes(Integer cadreId, String postLevel) {

        // 专业技术职务
        List<Map<String, Object>> options1 = new ArrayList<Map<String, Object>>();
        for (MetaType metaType : metaTypeService.metaTypes("mc_post_pro_post").values()) {
            if(StringUtils.equals(postLevel, metaType.getExtraAttr())){
                Map<String, Object> option = new HashMap<>();
                option.put("id", metaType.getId() + "");
                option.put("text", metaType.getName());
                options1.add(option);
            }
        }
        // 专技岗位等级
        List<Map<String, Object>> options2 = new ArrayList<Map<String, Object>>();
        for (MetaType metaType : metaTypeService.metaTypes("mc_post_pro_level").values()) {
            if(StringUtils.equals(postLevel, metaType.getExtraAttr())){
                Map<String, Object> option = new HashMap<>();
                option.put("id", metaType.getId() + "");
                option.put("text", metaType.getName());
                options2.add(option);
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("options1", options1);
        resultMap.put("options2", options2);

        return resultMap;
    }

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostPro_data")
    @ResponseBody
    public void cadrePostPro_data(HttpServletResponse response,
                              Integer cadreId,
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadrePostProExample example = new CadrePostProExample();
        CadrePostProExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("grade_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }


        int count = cadrePostProMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePostPro> CadrePostPros = cadrePostProMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", CadrePostPros);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadrePostInfo:edit")
    @RequestMapping(value = "/cadrePostPro_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePostPro_au(CadrePostPro record, String _holdTime, String _gradeTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_holdTime)){
            record.setHoldTime(DateUtils.parseDate(_holdTime, "yyyy.MM"));
        }
        if(StringUtils.isNotBlank(_gradeTime)){
            record.setGradeTime(DateUtils.parseDate(_gradeTime, "yyyy.MM"));
        }
        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));

        if (id == null) {
            cadrePostProService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加专技岗位过程信息：%s", record.getId()));
        } else {
            // 干部信息本人直接修改数据校验
            CadrePostPro _record = cadrePostProMapper.selectByPrimaryKey(id);
            if(_record.getCadreId().intValue() != record.getCadreId()){
                throw new IllegalArgumentException("数据异常");
            }
            cadrePostProService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新专技岗位过程信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePostInfo:edit")
    @RequestMapping("/cadrePostPro_au")
    public String cadrePostPro_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadrePostPro cadrePostPro = cadrePostProMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePostPro", cadrePostPro);
        }

        CadreView cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadrePostPro/cadrePostPro_au";
    }

    @RequiresPermissions("cadrePostInfo:del")
    @RequestMapping(value = "/cadrePostPro_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadrePostProService.batchDel(ids, cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除专技岗位过程信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
