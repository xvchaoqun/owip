package controller.sp;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sp.SpTalent;
import domain.sp.SpTalentExample;
import domain.sp.SpTalentExample.Criteria;
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
public class SpTalentController extends SpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sp:list")
    @RequestMapping("/spTalent")
    public String spTalent(Integer userId,ModelMap modelMap) {

        modelMap.put("sysUser",CmTag.getUserById(userId));
        return "sp/spTalent/spTalent_page";
    }

    @RequiresPermissions("sp:list")
    @RequestMapping("/spTalent_data")
    @ResponseBody
    public void spTalent_data(HttpServletResponse response,
                                    Integer userId,
                                    String talentTitle,
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

        SpTalentExample example = new SpTalentExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(talentTitle)) {
            criteria.andTalentTitleLike(SqlUtils.like(talentTitle));
        }
        if (isCadre!=null) {
            criteria.andIsCadreEqualTo(isCadre);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            spTalent_export(example, response);
            return;
        }

        long count = spTalentMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SpTalent> records= spTalentMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spTalent_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spTalent_au(SpTalent record, HttpServletRequest request) {

        Integer id = record.getId();

        //判断是否是现任干部
        spTalentService.updateRecord(record);

        if (spTalentService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if (id == null) {
            
            spTalentService.insertSelective(record);
            logger.info(log( LogConstants.LOG_SP, "添加高层次人才：{0}", record.getId()));
        } else {

            spTalentService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_SP, "更新高层次人才：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping("/spTalent_au")
    public String spTalent_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SpTalent spTalent = spTalentMapper.selectByPrimaryKey(id);
            modelMap.put("spTalent", spTalent);

            SysUserView sysUserView = CmTag.getUserById(spTalent.getUserId());
            modelMap.put("sysUser",sysUserView);

            Unit unit = CmTag.getUnit(spTalent.getUnitId());
            modelMap.put("unit",unit);
        }
        return "sp/spTalent/spTalent_au";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spTalent_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spTalent_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            spTalentService.del(id);
            logger.info(log( LogConstants.LOG_SP, "删除高层次人才：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spTalent_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map spTalent_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            spTalentService.batchDel(ids);
            logger.info(log( LogConstants.LOG_SP, "批量删除高层次人才：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spTalent_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spTalent_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        spTalentService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_SP, "高层次人才调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void spTalent_export(SpTalentExample example, HttpServletResponse response) {

        List<SpTalent> records = spTalentMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名|100","所在单位|100","编制类别|100","人员分类|100","政治面貌|100","专业技术职务|100","专技岗位等级|100","一级学科|100","人才/荣誉称号|100","是否领导干部|100","所担任行政职务|100","授予日期|100","联系方式|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            SpTalent record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getUnitId()+"",
                            record.getAuthorizedType(),
                            record.getStaffType(),
                            record.getPoliticsStatus()+"",
                            record.getProPost(),
                            record.getProPostLevel(),
                            record.getFirstSubject(),
                            record.getTalentTitle(),
                            record.getIsCadre()+"",
                            record.getAdminPost(),
                            DateUtils.formatDate(record.getAwardDate(), DateUtils.YYYY_MM_DD),
                            record.getPhone(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("高层次人才(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/spTalent_selects")
    @ResponseBody
    public Map spTalent_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SpTalentExample example = new SpTalentExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = spTalentMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SpTalent> records = spTalentMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(SpTalent record:records){

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
    @RequestMapping("/spTalent_import")
    public String unitPost_import(ModelMap modelMap) {

        return "sp/spTalent/spTalent_import";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spTalent_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spTalent_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<SpTalent> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            SpTalent record = new SpTalent();
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

            record.setCountry(StringUtils.trimToNull(xlsRow.get(2)));

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

            spTalentService.updateRecord(record);

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = spTalentService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入高层次人才成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequestMapping(value = "/spTalent_details", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spTalent_details(Integer userId){

        Map dateilsMap = new HashMap();
        CadreView cadre = CmTag.getCadreByUserId(userId);

        if (cadre == null) return null;
        if (spTalentService.isCurrentCadre(cadre.getStatus())){

            dateilsMap.put("cadre",cadre);
        }
        return dateilsMap;
    }
}
