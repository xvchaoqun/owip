package controller.sc.scMatter;

import bean.UserBean;
import controller.global.OpException;
import controller.sc.ScBaseController;
import domain.sc.scMatter.*;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScMatterItemController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterItem:list")
    @RequestMapping("/scMatterItem")
    public String scMatterItem(@RequestParam(defaultValue = "1") Integer cls,
                               Integer userId,
                               ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        if(cls==-1){
            return "sc/scMatter/scMatterItem/scMatter_item_page";
        }

        return "sc/scMatter/scMatterItem/scMatterItem_page";
    }

    @RequiresPermissions("scMatterItem:list")
    @RequestMapping("/scMatterItem_data")
    public void scMatterItem_data(HttpServletResponse response,
                                  Integer matterId,
                                  Integer year,
                                  Boolean type,
                                  Integer userId,
                                 Byte backStatus,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterItemViewExample example = new ScMatterItemViewExample();
        ScMatterItemViewExample.Criteria criteria = example.createCriteria().andMatterIsDeletedEqualTo(false);
        example.setOrderByClause("draw_time desc, real_hand_time desc, id desc");
        if(matterId != null){
            criteria.andMatterIdEqualTo(matterId);
        }
        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(backStatus!=null){

            if(backStatus==1){
                criteria.andRealHandTimeIsNotNull();
            }else if(backStatus==2){
                criteria.andRealHandTimeIsNull();
            }else if(backStatus==3){
                criteria.andRealHandTimeIsNull().andHandTimeLessThanOrEqualTo(new Date());
            }
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scMatterItem_export(example, response);
            return;
        }

        long count = scMatterItemViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterItemView> records= scMatterItemViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterItem.class, scMatterItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterItem:edit")
    @RequestMapping(value = "/scMatterItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterItem_au(ScMatterItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scMatterItemService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "添加个人有关事项-填报记录：%s", record.getId()));
        } else {

            scMatterItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "更新个人有关事项-填报记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterItem:edit")
    @RequestMapping("/scMatterItem_au")
    public String scMatterItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterItem scMatterItem = scMatterItemMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterItem", scMatterItem);
        }
        return "sc/scMatter/scMatterItem/scMatterItem_au";
    }
    
    @RequiresPermissions("scMatterItem:import")
    @RequestMapping("/scMatterItem_import")
    public String scMatterItem_import(ModelMap modelMap) {

        return "sc/scMatter/scMatterItem/scMatterItem_import";
    }

    @RequiresPermissions("scMatterItem:import")
    @RequestMapping(value = "/scMatterItem_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterItem_import(int matterId, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<ScMatterItem> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            ScMatterItem record = new ScMatterItem();
            row++;

            String code = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(code)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null){
                throw new OpException("第{0}行工作证号[{1}]不存在", row, code);
            }
            record.setUserId(uv.getUserId());
            
            record.setTitle(StringUtils.trimToNull(xlsRow.get(2)));

            String realHandTime = StringUtils.trimToNull(xlsRow.get(3));
            record.setRealHandTime(DateUtils.parseStringToDate(realHandTime));

            String fillTime = StringUtils.trimToNull(xlsRow.get(4));
            record.setFillTime(DateUtils.parseStringToDate(fillTime));

            record.setMatterId(matterId);
            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = scMatterItemService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入填报对象成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("scMatterItem:del")
    @RequestMapping(value = "/scMatterItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMatterItemService.del(id);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "删除个人有关事项-填报记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterItem:del")
    @RequestMapping(value = "/scMatterItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMatterItemService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "批量删除个人有关事项-填报记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scMatterItem_export(ScMatterItemViewExample example, HttpServletResponse response) {

        List<ScMatterItemView> records = scMatterItemViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","填报类型|120","工作证号|100", "姓名|100",
                "所在单位及职务|280|left","领表时间|100","应交回时间|100","实交回日期|100","封面填表日期|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScMatterItemView record = records.get(i);

            String[] values = {
                            record.getYear() +"",
                            record.getType()?"个别填报":"年度集中填报",
                            record.getCode(),
                            record.getRealname(),
                            record.getTitle(),
                            DateUtils.formatDate(record.getDrawTime(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getHandTime(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getRealHandTime(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getFillTime(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "个人有关事项_填报记录(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 根据账号或姓名或学工号选择账号
    @RequestMapping("/scMatterUser_selects")
    @ResponseBody
    public Map scMatterUser_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterUserViewExample example = new ScMatterUserViewExample();
        //example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(searchStr)) {
            ScMatterUserViewExample.Criteria criteria = example.or().andUsernameLike(SqlUtils.like(searchStr));
            ScMatterUserViewExample.Criteria criteria1 = example.or().andCodeLike(SqlUtils.like(searchStr));
            ScMatterUserViewExample.Criteria criteria2 = example.or().andRealnameLike(SqlUtils.like(searchStr));
        }

        long count = scMatterUserViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterUserView> uvs = scMatterUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != uvs && uvs.size() > 0) {
            for (ScMatterUserView uv : uvs) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", uv.getUserId() + "");
                option.put("text", uv.getRealname());
                UserBean userBean = userBeanService.get(uv.getUserId());
                option.put("user", userBean);

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    option.put("unit", userBean.getUnit());
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

   /* @RequestMapping("/scMatterItem_selects")
    @ResponseBody
    public Map scMatterItem_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterItemExample example = new ScMatterItemExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        int count = scMatterItemMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterItem> scMatterItems = scMatterItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterItems && scMatterItems.size()>0){

            for(ScMatterItem scMatterItem:scMatterItems){

                Select2Option option = new Select2Option();
                option.setText(scMatterItem.getName());
                option.setId(scMatterItem.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
