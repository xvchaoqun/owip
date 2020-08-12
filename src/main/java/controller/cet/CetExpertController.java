package controller.cet;

import controller.global.OpException;
import domain.cet.*;
import domain.cet.CetExpertExample.Criteria;
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
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetExpertController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetExpert:list")
    @RequestMapping("/cetExpert")
    public String cetExpert() {

        return "cet/cetExpert/cetExpert_page";
    }

    @RequiresPermissions("cetExpert:list")
    @RequestMapping("/cetExpert_data")
    public void cetExpert_data(HttpServletResponse response,
                               Byte type,
                               String realname,
                               Integer userId,
                               String code,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetExpertViewExample example = new CetExpertViewExample();
        CetExpertViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameLike(SqlUtils.like(realname));
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeEqualTo(code);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetExpert_export(example, response);
            return;
        }

        long count = cetExpertViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetExpertView> records = cetExpertViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetExpert.class, cetExpertMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetExpert:edit")
    @RequestMapping(value = "/cetExpert_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetExpert_au(CetExpert record, HttpServletRequest request) {

        Integer id = record.getId();

        if (record.getType() == CetConstants.CET_EXPERT_TYPE_IN) {
            if (record.getUserId() == null) return failed("请选择专家");

            if (cetExpertService.idDuplicate(record.getId(), record.getUserId())) {
                return failed("专家重复");
            }

            SysUserView uv = CmTag.getUserById(record.getUserId());
            record.setRealname(uv.getRealname());
            record.setCode(null);
        } else {
            if (StringUtils.isBlank(record.getCode())) return failed("请填写专家编号");

            if (cetExpertService.idDuplicate(record.getId(), record.getCode())) {
                return failed("专家编号重复");
            }

            record.setUserId(null);
        }

        if (id == null) {
            cetExpertService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加专家信息：%s", record.getRealname()));
        } else {
            cetExpertService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新专家信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetExpert:edit")
    @RequestMapping("/cetExpert_au")
    public String cetExpert_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetExpert cetExpert = cetExpertMapper.selectByPrimaryKey(id);
            modelMap.put("cetExpert", cetExpert);
        }
        return "cet/cetExpert/cetExpert_au";
    }

    @RequiresPermissions("cetExpert:import")
    @RequestMapping("/cetExpert_import")
    public String cetExpert_import(ModelMap modelMap) {

        return "cet/cetExpert/cetExpert_import";
    }

    @RequiresPermissions("cetExpert:import")
    @RequestMapping(value = "/cetExpert_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetExpert_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CetExpert> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            CetExpert record = new CetExpert();
            row++;

            String _type = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(_type)) {
                throw new OpException("第{0}行专家类别为空", row);
            }
            if (StringUtils.contains(_type, "校内")) {
                record.setType(CetConstants.CET_EXPERT_TYPE_IN);
            } else if (StringUtils.contains(_type, "校外")) {
                record.setType(CetConstants.CET_EXPERT_TYPE_OUT);
            } else {
                throw new OpException("第{0}行专家类别[{1}]有误", _type);
            }

            String code = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(code)) {
                throw new OpException("第{0}行工号/编号为空", row);
            }
            if (record.getType() == CetConstants.CET_EXPERT_TYPE_IN) {

                SysUserView uv = sysUserService.findByCode(code);
                if (uv == null) {
                    throw new OpException("第{0}行工号[{1}]不存在", row, code);
                }
                int userId = uv.getId();
                record.setUserId(userId);
                record.setRealname(uv.getRealname());
            } else {

                record.setCode(code);

                String realname = StringUtils.trimToNull(xlsRow.get(2));
                if (StringUtils.isBlank(realname)) {
                    throw new OpException("第{0}行姓名为空", row);
                }
                record.setRealname(realname);
            }

            record.setUnit(StringUtils.trimToNull(xlsRow.get(3)));
            record.setPost(StringUtils.trimToNull(xlsRow.get(4)));
            record.setContact(StringUtils.trimToNull(xlsRow.get(5)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(6)));

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = cetExpertService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入培训专家成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("cetExpert:del")
    @RequestMapping(value = "/cetExpert_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetExpert_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetExpertService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除专家信息：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetExpert:del")
    @RequestMapping(value = "/cetExpert_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetExpert_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cetExpertService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除专家信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetExpert:list")
    @RequestMapping("/cetExpert_info")
    public String cetExpert_info(Integer id, ModelMap modelMap){

        CetCourseExample example = new CetCourseExample();
        example.createCriteria().andExpertIdEqualTo(id);
        example.setOrderByClause("found_date asc");
        List<CetCourse> cetCourses = cetCourseMapper.selectByExample(example);
        modelMap.put("cetCourses", cetCourses);

        return "cet/cetExpert/cetExpert_info";
    }

    @RequiresPermissions("cetExpert:changeOrder")
    @RequestMapping(value = "/cetExpert_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetExpert_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetExpertService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "专家信息调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetExpert_export(CetExpertViewExample example, HttpServletResponse response) {

        List<CetExpertView> records = cetExpertViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名|100", "所在单位|100", "职务和职称|100", "联系方式|100", "排序|100", "备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetExpertView record = records.get(i);
            String[] values = {
                    record.getRealname(),
                    record.getUnit(),
                    record.getPost(),
                    record.getContact(),
                    record.getSortOrder() + "",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "专家信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetExpert_selects")
    @ResponseBody
    public Map cetExpert_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetExpertExample example = new CetExpertExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andRealnameLike(SqlUtils.like(searchStr));
        }

        long count = cetExpertMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetExpert> cetExperts = cetExpertMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != cetExperts && cetExperts.size() > 0) {

            for (CetExpert cetExpert : cetExperts) {

                Map<String, Object> option = new HashMap<>();
                option.put("id", cetExpert.getId());
                option.put("text", cetExpert.getRealname());
                option.put("unit", cetExpert.getUnit());
                option.put("post", cetExpert.getPost());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
