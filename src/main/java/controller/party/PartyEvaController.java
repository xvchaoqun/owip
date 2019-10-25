package controller.party;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.party.PartyEva;
import domain.party.PartyEvaExample;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.ExcelUtils;
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

public class PartyEvaController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyEva:list")
    @RequestMapping("/partyEva_page")
    public String partyEva_page() {

        return "party/partyEva/partyEva_page";
    }

    @RequiresPermissions("partyEva:list")
    @RequestMapping("/partyEva_data")
    @ResponseBody
    public void partyEva_data(HttpServletResponse response,
                                    Integer userId,
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyEvaExample example = new PartyEvaExample();
        PartyEvaExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        long count = partyEvaMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyEva> records= partyEvaMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partyEva.class, partyEvaMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("partyEva:edit")
    @RequestMapping(value = "/partyEva_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyEva_au(PartyEva record, HttpServletRequest request) {

        Integer id = record.getId();

        if (partyEvaService.idDuplicate(id, record.getUserId(), record.getYear())) {
            return failed("添加重复");
        }
        if (id == null) {

            partyEvaService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "添加年度考核记录：%s", record.getId()));
        } else {

            partyEvaService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "更新年度考核记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyEva:edit")
    @RequestMapping("/partyEva_au")
    public String partyEva_au(Integer id, Integer userId, ModelMap modelMap) {

        if (id != null) {
            PartyEva partyEva = partyEvaMapper.selectByPrimaryKey(id);
            modelMap.put("partyEva", partyEva);
            userId = partyEva.getUserId();
        }
        CadreView cadreView = cadreService.findAll().get(userId);
        modelMap.put("cadre", cadreView);
        
        return "party/partyEva/partyEva_au";
    }

    @RequiresPermissions("partyEva:import")
    @RequestMapping("/partyEva_import")
    public String partyEva_import() {

        return "party/partyEva/partyEva_import";
    }

    @RequiresPermissions("partyEva:import")
    @RequestMapping(value = "/partyEva_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyEva_import(HttpServletRequest request, Byte status) throws InvalidFormatException, IOException {

        //User sessionUser = getAdminSessionUser(request);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Map<Integer, String> titleRow = ExcelUtils.getTitleRowData(sheet);
        int cols = titleRow.size();
        if(cols < 4){
            throw new OpException("录入表格式有误。");
        }
        Map<Integer, Integer> yearMap = new HashMap<>();
        for (int i = 3; i < cols; i++) {
            Integer year = null;
            String _year = titleRow.get(i);
            if(StringUtils.isNotBlank(_year) && _year.trim().length()>=4){
                try {
                    year = Integer.parseInt(_year.substring(0, 4));
                }catch (Exception e){}
            }
            if(year==null){
                throw new OpException("第{0}列年份有误。", i+1);
            }
            yearMap.put(i, year);
        }

        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<PartyEva> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;

            String userCode = StringUtils.trim(xlsRow.get(0));
            if(StringUtils.isBlank(userCode)){
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }

            int userId = uv.getUserId();

            for (Map.Entry<Integer, Integer> yearEntry : yearMap.entrySet()) {

                PartyEva record = new PartyEva();
                record.setUserId(userId);

                int col = yearEntry.getKey();
                int year = yearEntry.getValue();

                record.setYear(year);

                String _type = StringUtils.trimToNull(xlsRow.get(col));
                if(_type!=null) {
                    MetaType metaType = metaTypeService.findByName("mc_cadre_eva", _type);
                    if (metaType == null) {
                        throw new OpException("第{0}行第{1}列考核结果[{2}]不存在", row, col + 1, _type);
                    }
                    record.setType(metaType.getId());

                    records.add(record);
                }
            }
        }

        int addCount = partyEvaService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入年度考核结果成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("partyEva:del")
    @RequestMapping(value = "/partyEva_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyEva_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            partyEvaService.del(id);
            logger.info(addLog( LogConstants.LOG_ADMIN, "删除年度考核记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyEva:del")
    @RequestMapping(value = "/partyEva_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyEva_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyEvaService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_ADMIN, "批量删除年度考核记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
