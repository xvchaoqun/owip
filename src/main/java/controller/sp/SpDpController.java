package controller.sp;

import controller.global.OpException;
import domain.sp.SpDp;
import domain.sp.SpDpExample;
import domain.sp.SpDpExample.Criteria;
import domain.sys.SysUserView;
import domain.unit.Unit;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sp")
public class SpDpController extends SpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sp:list")
    @RequestMapping("/spDp")
    public String spDp() {

        return "sp/spDp/spDp_page";
    }

    @RequiresPermissions("sp:list")
    @RequestMapping("/spDp_data")
    @ResponseBody
    public void spDp_data(HttpServletResponse response,
                                    Integer dp,
                                    Integer userId,
                                    Boolean isCadre,
                                
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

        SpDpExample example = new SpDpExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (dp!=null) {
            criteria.andDpEqualTo(dp);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (isCadre!=null) {
            criteria.andIsCadreEqualTo(isCadre);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            spDp_export(example, response);
            return;
        }

        long count = spDpMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SpDp> records= spDpMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(spDp.class, spDpMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spDp_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spDp_au(SpDp record, HttpServletRequest request) {

        Integer id = record.getId();

        if (spDpService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        spDpService.updateRecord(record);

        if (id == null) {
            
            spDpService.insertSelective(record);
            logger.info(log( LogConstants.LOG_SP, "添加民主党派主委：{0}", record.getId()));
        } else {

            spDpService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_SP, "更新民主党派主委：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping("/spDp_au")
    public String spDp_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SpDp spDp = spDpMapper.selectByPrimaryKey(id);
            modelMap.put("spDp", spDp);

            SysUserView sysUserView = CmTag.getUserById(spDp.getUserId());
            modelMap.put("sysUser",sysUserView);

            Unit unit = CmTag.getUnit(spDp.getUnitId());
            modelMap.put("unit",unit);
        }
        return "sp/spDp/spDp_au";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spDp_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spDp_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            spDpService.del(id);
            logger.info(log( LogConstants.LOG_SP, "删除民主党派主委：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spDp_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map spDp_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            spDpService.batchDel(ids);
            logger.info(log( LogConstants.LOG_SP, "批量删除民主党派主委：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spDp_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spDp_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        spDpService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_SP, "民主党派主委调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void spDp_export(SpDpExample example, HttpServletResponse response) {

        List<SpDp> records = spDpMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"民主党派机构|100","姓名|100","所在单位|100","专业技术职务|100","是否领导干部|100","所担任行政职务|100","联系方式|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            SpDp record = records.get(i);
            String[] values = {
                record.getDp()+"",
                            record.getUserId()+"",
                            record.getUnitId()+"",
                            record.getProPost(),
                            record.getIsCadre()+"",
                            record.getAdminPost(),
                            record.getPhone(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("民主党派主委(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/spDp_selects")
    @ResponseBody
    public Map spDp_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SpDpExample example = new SpDpExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        long count = spDpMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SpDp> records = spDpMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(SpDp record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getUserId());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }


    @RequiresPermissions("sp:edit")
    @RequestMapping("/spDp_import")
    public String unitPost_import(ModelMap modelMap) {

        //spDpService.getLayerType("");

        return "sp/spDp/spDp_import";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spDp_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spTalent_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<SpDp> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            SpDp record = new SpDp();
            row++;

            String userCode = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)){
                throw new OpException("第{0}行工作证号为空",row);
            }
            SysUserView sysUserView = CmTag.getUserByCode(userCode);
            if (sysUserView == null){
                throw new OpException("第{0}行工作证号[{1}]不存在",row,userCode);
            }
            record.setUserId(sysUserView.getUserId());

            String unitCode = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(unitCode)){
                throw new OpException("第{0}行所在单位编号为空",row);
            }
            Unit unit = getUnitByCode(unitCode);
            if (unit == null){
                throw new OpException("第{0}行所在单位编号[{1}]不存在",row,unitCode);
            }
            record.setUnitId(unit.getId());

/*            record.setCountry(StringUtils.trimToNull(xlsRow.get(2)));

            String demo = StringUtils.trimToNull(xlsRow.get(3));
            record.setArriveDate(DateUtils.parseDate(StringUtils.trimToNull(xlsRow.get(3)),DateUtils.YYYY_MM_DD));
            record.setAuthorizedType(StringUtils.trimToNull(xlsRow.get(4)));
            record.setStaffType(StringUtils.trimToNull(xlsRow.get(5)));

            String politicsStatus = StringUtils.trimToNull(xlsRow.get(6));
            if (StringUtils.isBlank(politicsStatus)){
                throw new OpException("第{0}行的政治面貌为空",row);
            }
            MetaType politicalStatus = CmTag.getMetaTypeByName("mc_political_status", politicsStatus);
            if (politicalStatus == null){
                throw new OpException("第{0}行的政治面貌[{1}]元数据中不存在",row,politicsStatus);
            }
            record.setPoliticsStatus(politicalStatus.getId());

            record.setProPost(StringUtils.trimToNull(xlsRow.get(7)));
            record.setProPostLevel(StringUtils.trimToNull(xlsRow.get(8)));
            record.setFirstSubject(StringUtils.trimToNull(xlsRow.get(9)));
            record.setTalentTitle(StringUtils.trimToNull(xlsRow.get(10)));
            record.setAwardDate(DateUtils.parseDate(StringUtils.trimToNull(xlsRow.get(11)),DateUtils.YYYY_MM_DD));
            record.setPhone(StringUtils.trimToNull(xlsRow.get(12)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(13)));

            spTalentService.updateRecord(record);*/

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

       // int addCount = spTalentService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        //resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        /*logger.info(log(LogConstants.LOG_ADMIN,
                "导入高层次人才成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));*/

        return resultMap;
    }
}
