package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreParty;
import domain.cadre.CadrePartyView;
import domain.cadre.CadrePartyViewExample;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CadrePartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreParty:list")
    @RequestMapping("/cadreParty")
    public String cadreParty(Integer userId,
                             @RequestParam(defaultValue = "1", required = false) Byte cls,
                             Byte type, ModelMap modelMap) {

        if (userId!=null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }

        modelMap.put("type", type);
        modelMap.put("cls", cls);

        return "cadre/cadreParty/cadreParty_page";
    }
    @RequiresPermissions("cadreParty:list")
    @RequestMapping("/cadreParty_data")
    public void cadreParty_data(HttpServletResponse response,
                                @RequestParam(defaultValue = "1", required = false) Byte cls, // 1: 民主党派成员 2：群众
                                byte type, // 1: 民主党派 2：中共党员
                                Byte status,
                                Integer userId,
                                Integer adminLevel,
                                Integer postType,
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

        CadrePartyViewExample example = new CadrePartyViewExample();
        CadrePartyViewExample.Criteria criteria =
                example.createCriteria().andTypeEqualTo(type);

        if(type==1) {

            List<Integer> dpTypeIds = new ArrayList<>();
            List<Integer> crowdIds = new ArrayList<>();
            Map<Integer, MetaType> dpTypes = CmTag.getMetaTypes("mc_democratic_party");
            for (MetaType metaType : dpTypes.values()) {
                if (BooleanUtils.isNotTrue(metaType.getBoolAttr())) {
                    dpTypeIds.add(metaType.getId());
                }else{
                    crowdIds.add(metaType.getId());
                }
            }
            if(cls==1) {
                // 仅显示民主党派，不显示群众
                criteria.andClassIdIn(dpTypeIds);
            }else if(cls==2){
                criteria.andClassIdIn(crowdIds);
            }else{
                criteria.andIdIsNull();
            }
        }

        example.setOrderByClause("cadre_sort_order desc, is_first desc");
        if (status!=null) {
            criteria.andCadreStatusEqualTo(status);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (adminLevel !=null) {
            criteria.andAdminLevelEqualTo(adminLevel);
        }
        if (dpTypeId!=null) {
            criteria.andClassIdEqualTo(dpTypeId);
        }
        if (postType !=null) {
            criteria.andPostTypeEqualTo(postType);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andCadreTitleLike(SqlUtils.like(title));
        }

        long count = cadrePartyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePartyView> Cadres = cadrePartyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(CadreView.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreParty:edit")
    @RequestMapping(value = "/cadreParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreParty_au(CadreParty record,
                                String _growTime,
                                Integer cls,
                                HttpServletRequest request) {

        record.setGrowTime(DateUtils.parseStringToDate(_growTime));

        if (cls==2){

            cadrePartyService.addOrUpdateCrowd(record);

            logger.info(addLog(LogConstants.LOG_ADMIN, "更新群众：%s",
                    JSONUtils.toString(record, MixinUtils.baseMixins(), false)));
            return success(FormUtils.SUCCESS);
        }

        if(record.getType()==CadreConstants.CADRE_PARTY_TYPE_DP) {
            record.setIsFirst(BooleanUtils.isTrue(record.getIsFirst()));
        }

        cadrePartyService.addOrUpdateCadreParty(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部党派：%s",
                JSONUtils.toString(record, MixinUtils.baseMixins(), false)));

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
    public Map batchDel(HttpServletRequest request, Byte type, Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            cadreService.cadreParty_batchDel(ids);
            String name = "民主党派";
            if(type!=null&& type==CadreConstants.CADRE_PARTY_TYPE_DP){
                name = "党员（干部）";
            }
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除%s：%s", name, StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreParty:import")
    @RequestMapping("/cadreParty_import")
    public String cadreParty_import(ModelMap modelMap) {

        return "cadre/cadreParty/cadreParty_import";
    }

    @RequiresPermissions("cadreParty:import")
    @RequestMapping(value = "/cadreParty_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreParty_import(byte type, HttpServletRequest request) throws InvalidFormatException, IOException {

        //User sessionUser = getAdminSessionUser(request);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);
        List<CadreParty> records = new ArrayList<>();
        int row = 1;

        if(type== CadreConstants.CADRE_PARTY_TYPE_OW) {
            for (Map<Integer, String> xlsRow : xlsRows) {

                row++;
                String _code = StringUtils.trimToNull(xlsRow.get(0));
                String _growTime = StringUtils.trimToNull(xlsRow.get(2));
                if (_code == null) {
                    throw new OpException("第{0}行工作证号为空", row);
                }
                SysUserView uv = sysUserService.findByCode(_code);
                if (uv == null) {
                    throw new OpException("第{0}行工作证号[{1}]不存在", row, _code);
                }
                String remark = StringUtils.trimToNull(xlsRow.get(3));

                CadreParty record = new CadreParty();
                record.setUserId(uv.getId());
                record.setType(type);
                record.setGrowTime(DateUtils.parseStringToDate(_growTime));
                record.setRemark(remark);

                records.add(record);
            }

        }else if(type== CadreConstants.CADRE_PARTY_TYPE_DP) {

            Map<Integer, MetaType> metaTypeMap = metaTypeService.metaTypes("mc_democratic_party");

            for (Map<Integer, String> xlsRow : xlsRows) {

                row++;
                String _code = StringUtils.trimToNull(xlsRow.get(0));
                String _dpName = StringUtils.trimToNull(xlsRow.get(2));

                MetaType dpType = null;
                for (MetaType metaType : metaTypeMap.values()) {

                    if(StringUtils.equalsIgnoreCase(metaType.getName(), _dpName)
                        || StringUtils.equalsIgnoreCase(metaType.getExtraAttr(), _dpName)){

                        dpType = metaType;
                    }
                }

                if(dpType==null){
                    throw new OpException("第{0}行民主党派[{1}]不存在", row, _dpName);
                }

                String _growTime = StringUtils.trimToNull(xlsRow.get(3));
                if (_code == null) {
                    throw new OpException("第{0}行工作证号为空", row);
                }
                SysUserView uv = sysUserService.findByCode(_code);
                if (uv == null) {
                    throw new OpException("第{0}行工作证号[{1}]不存在", row, _code);
                }
                String post = StringUtils.trimToNull(xlsRow.get(4));
                String remark = StringUtils.trimToNull(xlsRow.get(5));

                CadreParty record = new CadreParty();
                record.setUserId(uv.getId());
                record.setType(type);
                record.setClassId(dpType.getId());
                record.setGrowTime(DateUtils.parseStringToDate(_growTime));
                record.setPost(post);
                record.setRemark(remark);

                records.add(record);
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        if(records.size()>0) {
            cadrePartyService.batchImport(records);
        }
        int successCount = records.size();
        resultMap.put("successCount", successCount);
        resultMap.put("total", xlsRows.size());

        logger.info(log(LogConstants.LOG_ADMIN, "导入或更新{0}，共{1}人",
                CadreConstants.CADRE_PARTY_TYPE_MAP.get(type)), successCount);

        return resultMap;
    }
}
