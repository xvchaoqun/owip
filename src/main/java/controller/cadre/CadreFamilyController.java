package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreFamily;
import domain.cadre.CadreFamilyExample;
import domain.cadre.CadreFamilyExample.Criteria;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
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
public class CadreFamilyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreFamily:list")
    @RequestMapping("/cadreFamily_page")
    public String cadreFamily_page(HttpServletResponse response,
                                Integer cadreId, ModelMap modelMap) {

        if (cadreId!=null) {

            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        {
            /*CadreFamilyAbroadExample example = new CadreFamilyAbroadExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            modelMap.put("cadreFamilyAbroadCount", cadreFamilyAbroadMapper.countByExample(example));*/

            String name = "family_abroad";
            modelMap.put("canUpdateInfoCheck", cadreInfoCheckService.canUpdateInfoCheck(cadreId, name));
            modelMap.put("canUpdate", cadreInfoCheckService.canUpdate(cadreId, name));
        }

        modelMap.put("cadreTutors", JSONUtils.toString(cadreTutorService.findAll(cadreId).values()));
        return "cadre/cadreFamily/cadreFamily_page";
    }

    @RequiresPermissions("cadreFamily:list")
    @RequestMapping("/cadreFamily_data")
    public void cadreFamily_data(HttpServletResponse response,
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

        CadreFamilyExample example = new CadreFamilyExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("cadre_id asc, sort_order asc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            SecurityUtils.getSubject().checkPermission("cadre:exportFamily");
            if(ids!=null && ids.length>0)
                criteria.andCadreIdIn(Arrays.asList(ids));
            cadreFamily_export(ids, CadreConstants.CADRE_STATUS_MIDDLE, response);
            return;
        }

        long count = cadreFamilyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreFamily> records = cadreFamilyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
    
   /* @RequiresPermissions("cadreFamily:list")
    @RequestMapping("/cadreFamily_data")
    public void cadreFamily_data(HttpServletResponse response,
                                    int cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export) {

        List<CadreFamily> cadreFamilys = new ArrayList<>();
        {
            CadreFamilyExample example = new CadreFamilyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadreFamilys = cadreFamilyMapper.selectByExample(example);
        }
        modelMap.put("cadreFamilys", cadreFamilys);


        Map<Integer, CadreFamily> cadreFamilyMap = new HashMap<>();
        for (CadreFamily cadreFamily : cadreFamilys) {
            cadreFamilyMap.put(cadreFamily.getId(), cadreFamily);
        }
        modelMap.put("cadreFamilyMap", cadreFamilyMap);

        List<CadreFamilyAbroad> cadreFamilyAbroads = new ArrayList<>();
        {
            CadreFamilyAbroadExample example = new CadreFamilyAbroadExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadreFamilyAbroads = cadreFamilyAbroadMapper.selectByExample(example);
        }
        modelMap.put("cadreFamilyAbroads", cadreFamilyAbroads);

        return "cadre/cadreFamily/cadreFamily_page";
    }*/

    @RequiresPermissions("cadreFamily:edit")
    @RequestMapping(value = "/cadreFamily_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamily_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入
            CadreFamily record, String _birthday, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_birthday)){
            record.setBirthday(DateUtils.parseDate(_birthday, "yyyy-MM"));
        }
        record.setWithGod(BooleanUtils.isTrue(record.getWithGod()));

        if (id == null) {

            if (!toApply) {
                cadreFamilyService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加家庭成员信息：%s", record.getId()));
            } else {
                cadreFamilyService.modifyApply(record, null, false);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-家庭成员信息：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreFamily _record = cadreFamilyMapper.selectByPrimaryKey(id);
            if (_record.getCadreId().intValue() != record.getCadreId()) {
                throw new IllegalArgumentException("数据异常");
            }

            if (!toApply) {
                cadreFamilyService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新家庭成员信息：%s", record.getId()));
            } else {
                if (_isUpdate == false) {
                    cadreFamilyService.modifyApply(record, id, false);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-家庭成员信息：%s", record.getId()));
                } else {
                    // 更新修改申请的内容
                    cadreFamilyService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-家庭成员信息：%s", record.getId()));
                }
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreFamily:edit")
    @RequestMapping("/cadreFamily_au")
    public String cadreFamily_au(Integer id, int cadreId, ModelMap modelMap) {

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        if (id != null) {
            CadreFamily cadreFamily = cadreFamilyMapper.selectByPrimaryKey(id);
            modelMap.put("cadreFamily", cadreFamily);
        }
        return "cadre/cadreFamily/cadreFamily_au";
    }

    /*@RequiresPermissions("cadreFamily:del")
    @RequestMapping(value = "/cadreFamily_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamily_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreFamilyService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除家庭成员信息：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreFamily:del")
    @RequestMapping(value = "/cadreFamily_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreFamilyService.batchDel(ids,cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除家庭成员信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreFamily:changeOrder")
    @RequestMapping(value = "/cadreFamily_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamily_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreFamilyService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "干部家庭成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreFamily_export(Integer[] cadreIds, Byte status, HttpServletResponse response) {

        List<CadreFamily> cadreFamilys = iCadreMapper.getCadreFamilys(cadreIds, status);
        int rownum = cadreFamilys.size();
        String[] titles = {"工号|100", "干部|80", "所在单位及职务|400|left", "称谓|100","姓名|80",
                "政治面貌|100","工作单位及职务|500|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreFamily cadreFamily = cadreFamilys.get(i);
            CadreView cadre = CmTag.getCadreById(cadreFamily.getCadreId());
            SysUserView uv = cadre.getUser();
            String[] values = {
                    uv.getCode(),
                    uv.getRealname(),
                    cadre.getTitle(),
                    cadreFamily.getTitle()==null?"":CadreConstants.CADRE_FAMILY_TITLE_MAP.get(cadreFamily.getTitle()),
                    cadreFamily.getRealname(),
                    cadreFamily.getPoliticalStatus()==null?"":metaTypeService.getName(cadreFamily.getPoliticalStatus()),
                    cadreFamily.getUnit()
            };
            valuesList.add(values);
        }
        String fileName = "家庭成员信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cadreFamily_selects")
    @ResponseBody
    public Map cadreFamily_selects(Integer pageSize, Integer pageNo, int cadreId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreFamilyExample example = new CadreFamilyExample();
        Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andRealnameLike("%" + searchStr + "%");
        }

        long count = cadreFamilyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CadreFamily> cadreFamilys = cadreFamilyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cadreFamilys && cadreFamilys.size()>0){

            for(CadreFamily cadreFamily:cadreFamilys){

                Select2Option option = new Select2Option();
                option.setText(cadreFamily.getRealname());
                option.setId(cadreFamily.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
