package controller.cr;

import controller.global.OpException;
import domain.cr.CrPost;
import domain.cr.CrPostExample;
import domain.cr.CrPostExample.Criteria;
import domain.cr.CrRequire;
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

public class CrPostController extends CrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crPost:list")
    @RequestMapping("/crPost")
    public String crPost(Integer infoId, ModelMap modelMap) {

        modelMap.put("crInfo", crInfoMapper.selectByPrimaryKey(infoId));

        Map<Integer, CrRequire> requireMap = crRequireService.findAll();
        modelMap.put("requireMap", requireMap);

        return "cr/crPost/crPost_page";
    }

    @RequiresPermissions("crPost:list")
    @RequestMapping("/crPost_data")
    @ResponseBody
    public void crPost_data(HttpServletResponse response,
                                    Integer infoId,
                                    String name,
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

        CrPostExample example = new CrPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");
        if(infoId!=null){
            criteria.andInfoIdEqualTo(infoId);
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            crPost_export(example, response);
            return;
        }

        long count = crPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrPost> records= crPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crPost.class, crPostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crPost:edit")
    @RequestMapping(value = "/crPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crPost_au(CrPost record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            crPostService.insertSelective(record);
            logger.info(log( LogConstants.LOG_CR, "添加岗位：{0}", record.getId()));
        } else {

            crPostService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_CR, "更新岗位：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crPost:edit")
    @RequestMapping("/crPost_au")
    public String crPost_au(Integer id, Integer infoId, ModelMap modelMap) {

        if (id != null) {
            CrPost crPost = crPostMapper.selectByPrimaryKey(id);
            modelMap.put("crPost", crPost);

            infoId = crPost.getInfoId();
        }

        Map<Integer, CrRequire> requireMap = crRequireService.findAll();
        modelMap.put("crRequires", requireMap.values());

        modelMap.put("infoId", infoId);

        return "cr/crPost/crPost_au";
    }

    @RequiresPermissions("crPost:del")
    @RequestMapping(value = "/crPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map crPost_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            crPostService.batchDel(ids);
            logger.info(log( LogConstants.LOG_CR, "批量删除岗位：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("crPost:changeOrder")
    @RequestMapping(value = "/crPost_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crPost_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crPostService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_CR, "岗位调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void crPost_export(CrPostExample example, HttpServletResponse response) {

        List<CrPost> records = crPostMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"岗位名称|100","招聘人数|100","岗位职责|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrPost record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getNum()+"",
                            record.getDuty(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("岗位(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/crPost_selects")
    @ResponseBody
    public Map crPost_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrPostExample example = new CrPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = crPostMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CrPost> records = crPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CrPost record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
    
    @RequiresPermissions("crPost:edit")
    @RequestMapping("/crPost_import")
    public String crPost_import(ModelMap modelMap) {

        return "cr/crPost/crPost_import";
    }

    @RequiresPermissions("crPost:edit")
    @RequestMapping(value = "/crPost_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crPost_import(int infoId, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CrPost> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            CrPost record = new CrPost();
            record.setInfoId(infoId);
            row++;

            String name = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isNotBlank(name)) {
                record.setName(name);
            }

            String _num = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(_num) || !StringUtils.isNumeric(_num)) {
                throw new OpException("第{0}行招聘人数有误", row);
            }
            record.setNum(Integer.valueOf(_num));

            String duty = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isNotBlank(duty)) {
                record.setDuty(duty);
            }

            record.setRemark(StringUtils.trimToNull(xlsRow.get(3)));
            record.setCreateTime(new Date());

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = crPostService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入招聘岗位成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
