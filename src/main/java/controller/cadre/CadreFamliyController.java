package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreFamliy;
import domain.cadre.CadreFamliyAbroadExample;
import domain.cadre.CadreFamliyExample;
import domain.cadre.CadreFamliyExample.Criteria;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
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
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreFamliyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreFamliy:list")
    @RequestMapping("/cadreFamliy_page")
    public String cadreFamliy_page(HttpServletResponse response,
                                Integer cadreId, ModelMap modelMap) {

        if (cadreId!=null) {

            CadreView cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        {
            CadreFamliyAbroadExample example = new CadreFamliyAbroadExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            modelMap.put("cadreFamliyAbroadCount", cadreFamliyAbroadMapper.countByExample(example));
        }

        modelMap.put("cadreTutors", JSONUtils.toString(cadreTutorService.findAll(cadreId).values()));
        return "cadre/cadreFamliy/cadreFamliy_page";
    }

    @RequiresPermissions("cadreFamliy:list")
    @RequestMapping("/cadreFamliy_data")
    public void cadreFamliy_data(HttpServletResponse response,
                                 Integer cadreId,
                                 Integer pageSize, Integer pageNo,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids // 导出的记录（干部id)

    ) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreFamliyExample example = new CadreFamliyExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andCadreIdIn(Arrays.asList(ids));
            cadreFamliy_export(ids, SystemConstants.CADRE_STATUS_MIDDLE, response);
            return;
        }

        int count = cadreFamliyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreFamliy> records = cadreFamliyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
    
   /* @RequiresPermissions("cadreFamliy:list")
    @RequestMapping("/cadreFamliy_data")
    public void cadreFamliy_data(HttpServletResponse response,
                                    int cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export) {

        List<CadreFamliy> cadreFamliys = new ArrayList<>();
        {
            CadreFamliyExample example = new CadreFamliyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadreFamliys = cadreFamliyMapper.selectByExample(example);
        }
        modelMap.put("cadreFamliys", cadreFamliys);


        Map<Integer, CadreFamliy> cadreFamliyMap = new HashMap<>();
        for (CadreFamliy cadreFamliy : cadreFamliys) {
            cadreFamliyMap.put(cadreFamliy.getId(), cadreFamliy);
        }
        modelMap.put("cadreFamliyMap", cadreFamliyMap);

        List<CadreFamliyAbroad> cadreFamliyAbroads = new ArrayList<>();
        {
            CadreFamliyAbroadExample example = new CadreFamliyAbroadExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadreFamliyAbroads = cadreFamliyAbroadMapper.selectByExample(example);
        }
        modelMap.put("cadreFamliyAbroads", cadreFamliyAbroads);

        return "cadre/cadreFamliy/cadreFamliy_page";
    }*/

    @RequiresPermissions("cadreFamliy:edit")
    @RequestMapping(value = "/cadreFamliy_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamliy_au(CadreFamliy record, String _birthday, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_birthday)){
            record.setBirthday(DateUtils.parseDate(_birthday, "yyyy-MM"));
        }

        if (id == null) {
            cadreFamliyService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加家庭成员信息：%s", record.getId()));
        } else {

            // 干部信息本人直接修改数据校验
            CadreFamliy _record = cadreFamliyMapper.selectByPrimaryKey(id);
            if(_record.getCadreId().intValue() != record.getCadreId()){
                throw new IllegalArgumentException("数据异常");
            }
            record.setCadreId(_record.getCadreId());
            cadreFamliyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新家庭成员信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreFamliy:edit")
    @RequestMapping("/cadreFamliy_au")
    public String cadreFamliy_au(Integer id, int cadreId, ModelMap modelMap) {

        CadreView cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        if (id != null) {
            CadreFamliy cadreFamliy = cadreFamliyMapper.selectByPrimaryKey(id);
            modelMap.put("cadreFamliy", cadreFamliy);
        }
        return "cadre/cadreFamliy/cadreFamliy_au";
    }

    /*@RequiresPermissions("cadreFamliy:del")
    @RequestMapping(value = "/cadreFamliy_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamliy_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreFamliyService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除家庭成员信息：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreFamliy:del")
    @RequestMapping(value = "/cadreFamliy_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreFamliyService.batchDel(ids,cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除家庭成员信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreFamliy_export(Integer[] cadreIds, Byte status, HttpServletResponse response) {

        List<CadreFamliy> cadreFamliys = iCadreMapper.getCadreFamliys(cadreIds, status);
        int rownum = cadreFamliys.size();
        String[] titles = {"工号", "干部", "所在单位及职务", "称谓","姓名","政治面貌","工作单位及职务"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreFamliy cadreFamliy = cadreFamliys.get(i);
            CadreView cadre = CmTag.getCadreById(cadreFamliy.getCadreId());
            SysUserView uv = cadre.getUser();
            String[] values = {
                    uv.getCode(),
                    uv.getRealname(),
                    cadre.getTitle(),
                    cadreFamliy.getTitle()==null?"":SystemConstants.CADRE_FAMLIY_TITLE_MAP.get(cadreFamliy.getTitle()),
                    cadreFamliy.getRealname(),
                    cadreFamliy.getPoliticalStatus()==null?"":metaTypeService.getName(cadreFamliy.getPoliticalStatus()),
                    cadreFamliy.getUnit()
            };
            valuesList.add(values);
        }
        String fileName = "家庭成员信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cadreFamliy_selects")
    @ResponseBody
    public Map cadreFamliy_selects(Integer pageSize, Integer pageNo, int cadreId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreFamliyExample example = new CadreFamliyExample();
        Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andRealnameLike("%" + searchStr + "%");
        }

        int count = cadreFamliyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CadreFamliy> cadreFamliys = cadreFamliyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cadreFamliys && cadreFamliys.size()>0){

            for(CadreFamliy cadreFamliy:cadreFamliys){

                Select2Option option = new Select2Option();
                option.setText(cadreFamliy.getRealname());
                option.setId(cadreFamliy.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
