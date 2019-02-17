package controller.cadre;

import bean.XlsUpload;
import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreParty;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
public class CadrePartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreParty:list")
    @RequestMapping("/cadreParty")
    public String cadreParty(Integer userId, Byte type, ModelMap modelMap) {

        if (userId!=null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }

        modelMap.put("type", type);

        return "cadre/cadreParty/cadreParty_page";
    }
    @RequiresPermissions("cadreParty:list")
    @RequestMapping("/cadreParty_data")
    public void cadreParty_data(HttpServletResponse response,
                                byte type,
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
        if (adminLevel !=null) {
            criteria.andAdminLevelEqualTo(adminLevel);
        }
        if (dpTypeId!=null) {
            criteria.andDpTypeIdEqualTo(dpTypeId);
        }
        if (postType !=null) {
            criteria.andPostTypeEqualTo(postType);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(CadreView.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }



    @RequiresPermissions("cadreParty:edit")
    @RequestMapping(value = "/cadreParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreParty_au(CadreParty record,
                                HttpServletRequest request) {

        if(cadrePartyService.idDuplicate(record.getId(), record.getUserId(), record.getType())){

            return failed("添加重复");
        }

        cadreService.addOrUPdateCadreParty(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部党派：%s", JSONUtils.toString(record, MixinUtils.baseMixins(), false)));

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
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部党派：%s", StringUtils.join(ids, ",")));
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
        List<Map<Integer, String>> xlsRows = XlsUpload.getXlsRows(sheet);
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
            for (Map<Integer, String> xlsRow : xlsRows) {

                row++;
                String _code = StringUtils.trimToNull(xlsRow.get(0));
                String _dpName = StringUtils.trimToNull(xlsRow.get(2));

                MetaType dpType = metaTypeService.findByName("mc_democratic_party", _dpName);
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
            cadreService.cadrePartyImport(records);
        }
        resultMap.put("successCount", records.size());
        resultMap.put("total", xlsRows.size());

        return resultMap;
    }
}
