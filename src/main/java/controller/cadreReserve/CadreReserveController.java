package controller.cadreReserve;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cadreReserve.CadreReserve;
import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import persistence.cadre.common.CadreReserveCount;
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
public class CadreReserveController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("cadreReserve:list")
    @RequestMapping("/cadreReserve/search")
    public String search() {

        return "cadreReserve/cadreReserve_search";
    }

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping(value = "/cadreReserve/search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_search(int cadreId) {

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        String msg = "";
        CadreView cadre = iCadreMapper.getCadre(cadreId);

        if (cadre == null) {
            msg = "该用户不存在";
        } else {
            resultMap.put("realname", cadre.getRealname());

            if (cadre == null) {
                msg = "该用户不是优秀年轻干部";
            } else {
                CadreReserve cadreReserve = cadreReserveService.getNormalRecord(cadre.getId());
                if (cadreReserve == null) {
                    msg = "该用户不是优秀年轻干部";
                } else {
                    resultMap.put("cadreId", cadre.getId());
                    resultMap.put("reserveType", cadreReserve.getType());
                }
            }
        }
        resultMap.put("msg", msg);

        return resultMap;
    }

    // 转移
    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping("/cadreReserve_transfer")
    public String cadreReserve_transfer(int id, ModelMap modelMap) {

        CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
        modelMap.put("cadreReserve", cadreReserve);

        if(cadreReserve!=null){
            modelMap.put("cadre", CmTag.getCadreById(cadreReserve.getCadreId()));
        }

        return "cadreReserve/cadreReserve_transfer";
    }

    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping(value = "/cadreReserve_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_transfer(int cadreId, int type) {

        Map<Integer, MetaType> reserveTypeMap = CmTag.getMetaTypes("mc_cadre_reserve_type");
        if(!reserveTypeMap.containsKey(type)){
            return failed("转移至不存在。");
        }

        CadreReserve cadreReserve = cadreReserveService.getNormalRecord(cadreId);
        if(cadreReserve!=null) {
            if (cadreReserve.getType() == type) {
                return failed("不允许转移至相同的库。");
            }

            cadreReserveService.updateType(cadreReserve.getId(), type);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping("/cadreReserve")
    public String cadreReserve(Byte status, Integer reserveType,
                               Integer cadreId, ModelMap modelMap) {

        Map<Integer, MetaType> cadreReserveTypeMap = metaTypeService.metaTypes("mc_cadre_reserve_type");

        if (status == null && reserveType == null) {
            // 默认页面
            reserveType = new ArrayList<>(cadreReserveTypeMap.keySet()).get(0);
        }
        if (reserveType != null) {
            // 正常状态的优秀年轻干部库，读取指定的类别
            status = CadreConstants.CADRE_RESERVE_STATUS_NORMAL;
        }
        if (status != CadreConstants.CADRE_RESERVE_STATUS_NORMAL) {
            // 非正常状态的优秀年轻干部库，读取全部的类别
            reserveType = null;
        }

        modelMap.put("status", status);
        modelMap.put("reserveType", reserveType);

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
        }

        Map<Byte, Integer> statusCountMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : CadreConstants.CADRE_RESERVE_STATUS_MAP.entrySet()) {
            statusCountMap.put(entry.getKey(), 0);
        }
        Map<Integer, Integer> normalCountMap = new HashMap<>();
        for (Map.Entry<Integer, MetaType> entry : cadreReserveTypeMap.entrySet()) {
            normalCountMap.put(entry.getKey(), 0);
        }
        List<CadreReserveCount> cadreReserveCounts = iCadreMapper.selectCadreReserveCount();
        for (CadreReserveCount crc : cadreReserveCounts) {
            Byte st = crc.getStatus();
            if (st == CadreConstants.CADRE_RESERVE_STATUS_NORMAL) {
                Integer type = crc.getType();
                Integer count = normalCountMap.get(type);
                if (count == null) count = 0; // 不可能的情况
                normalCountMap.put(type, count + crc.getNum());
            }
            Integer stCount = statusCountMap.get(st);
            if (stCount == null) stCount = 0;
            statusCountMap.put(st, stCount + crc.getNum());
        }
        modelMap.put("statusCountMap", statusCountMap);
        modelMap.put("normalCountMap", normalCountMap);

        return "cadreReserve/cadreReserve_page";
    }

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping("/cadreReserve_data")
    public void cadreReserve_data(HttpServletResponse response, Byte status, Integer reserveType,
                                  Integer cadreId,
                                  Integer adminLevel,
                                  Integer postType,
                                  String title,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {

        Map<Integer, MetaType> cadreReserveTypeMap = metaTypeService.metaTypes("mc_cadre_reserve_type");

        if (status == null && reserveType == null) {
            // 默认页面
            reserveType = new ArrayList<>(cadreReserveTypeMap.keySet()).get(0);
        }
        if (reserveType != null) {
            // 正常状态的优秀年轻干部库，读取指定的类别
            status = CadreConstants.CADRE_RESERVE_STATUS_NORMAL;
        }
        if (status != CadreConstants.CADRE_RESERVE_STATUS_NORMAL) {
            // 非正常状态的优秀年轻干部库，读取全部的类别
            reserveType = null;
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreReserveViewExample example = new CadreReserveViewExample();
        CadreReserveViewExample.Criteria criteria = example.createCriteria();

        if (status != null)
            criteria.andReserveStatusEqualTo(status);
        if (status == null || status == CadreConstants.CADRE_RESERVE_STATUS_NORMAL)
            criteria.andReserveTypeEqualTo(reserveType);

        example.setOrderByClause("reserve_sort_order asc");

        if (cadreId != null) {
            criteria.andIdEqualTo(cadreId);
        }

        if (adminLevel != null) {
            criteria.andAdminLevelEqualTo(adminLevel);
        }
        if (postType != null) {
            criteria.andPostTypeEqualTo(postType);
        }

        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(SqlUtils.like(title));
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andReserveIdIn(Arrays.asList(ids));
            cadreReserve_export(reserveType, example, response);
            return;
        }

        long count = cadreReserveViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreReserveView> Cadres = cadreReserveViewMapper.
                selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }


    // 只有干部库中类型为优秀年轻干部时，才可以修改干部库的信息
    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping(value = "/cadreReserve_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_au(boolean _isCadre,
                                  Integer cadreId,
                                  Integer userId, Integer reserveId, Integer reserveType, String reserveRemark,
                                  Cadre cadreRecord, HttpServletRequest request) {
        if (_isCadre) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
            userId = cadre.getUserId();
        }
        CadreReserve record = new CadreReserve();
        record.setId(reserveId);
        record.setType(reserveType);
        record.setRemark(reserveRemark);
        if (reserveId == null) {
            cadreReserveService.insertOrUpdateSelective(userId, record, cadreRecord);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加优秀年轻干部：%s", record.getId()));
        } else {
            cadreReserveService.updateByPrimaryKeySelective(record, cadreRecord);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新优秀年轻干部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping("/cadreReserve_au")
    public String cadreReserve_au(Integer id, int reserveType, ModelMap modelMap) {

        if (id != null) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
            reserveType = cadreReserve.getType();
            modelMap.put("cadreReserve", cadreReserve);
            CadreView cadre = iCadreMapper.getCadre(cadreReserve.getCadreId());
            modelMap.put("cadre", cadre);
        }
        modelMap.put("reserveType", reserveType);

        return "cadreReserve/cadreReserve_au";
    }

    /*@RequiresPermissions("cadreReserve:del")
    @RequestMapping(value = "/cadreReserve_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            cadreReserveService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除优秀年轻干部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping("/cadreReserve_pass")
    public String cadreReserve_pass(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
            CadreView cadre = iCadreMapper.getCadre(cadreReserve.getCadreId());
            modelMap.put("cadreReserve", cadreReserve);
            modelMap.put("cadre", cadre);
        }
        return "cadreReserve/cadreReserve_pass";
    }

    // 列为考察对象
    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping(value = "/cadreReserve_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_pass(Integer reserveId, String reserveRemark,
                                    Cadre cadreRecord, HttpServletRequest request) {

        CadreReserve record = new CadreReserve();
        record.setId(reserveId);
        record.setRemark(reserveRemark);

        Cadre cadre = cadreReserveService.pass(record, cadreRecord);

        SysUserView user = cadre.getUser();
        logger.info(addLog(LogConstants.LOG_ADMIN, "优秀年轻干部列为考察对象：%s-%s", user.getRealname(), user.getCode()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:abolish")
    @RequestMapping(value = "/cadreReserve_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_abolish(HttpServletRequest request, Integer id, ModelMap modelMap) {

        cadreReserveService.abolish(id);
        logger.info(addLog(LogConstants.LOG_ADMIN, "撤销优秀年轻干部：%s", id));

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cadreReserve:abolish")
    @RequestMapping(value = "/cadreReserve_unAbolish", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_unAbolish(HttpServletRequest request, Integer id, ModelMap modelMap) {

        cadreReserveService.unAbolish(id);
        logger.info(addLog(LogConstants.LOG_ADMIN, "返回优秀年轻干部库：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:del")
    @RequestMapping(value = "/cadreReserve_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            cadreReserveService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除已撤销优秀年轻干部：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:del")
    @RequestMapping(value = "/cadreReserve_batchDelPass", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_batchDelPass(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            cadreReserveService.batchDelPass(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除已列为考察对象的优秀年轻干部：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:changeOrder")
    @RequestMapping(value = "/cadreReserve_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreReserveService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "优秀年轻干部调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    private void cadreReserve_export(Integer reserveType, CadreReserveViewExample example, HttpServletResponse response) {

        SXSSFWorkbook wb = cadreReserveExportService.export(reserveType, example);

        String cadreReserveType = metaTypeService.getName(reserveType);
        String fileName = CmTag.getSysConfig().getSchoolName() + "优秀年轻干部_" + DateUtils.formatDate(new Date(), "yyyyMMdd");

        if (cadreReserveType != null)
            fileName = CmTag.getSysConfig().getSchoolName() + "优秀年轻干部（" + cadreReserveType + "）_" + DateUtils.formatDate(new Date(), "yyyyMMdd");

        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    /*public void cadreReserve_export(CadreReserveViewExample example, HttpServletResponse response) {

        List<CadreReserveView> records = cadreReserveViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工号", "姓名", "行政级别", "职务属性", "职务", "所在单位及职务", "手机号", "办公电话", "家庭电话", "电子邮箱", "备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreReserveView record = records.get(i);
            SysUserView sysUser = record.getUser();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    metaTypeService.getName(record.getTypeId()),
                    metaTypeService.getName(record.getPostId()),
                    record.getPost(),
                    record.getTitle(),
                    record.getMobile(),
                    record.getPhone(),
                    record.getHomePhone(),
                    record.getEmail(),
                    record.getRemark()
            };
            valuesList.add(values);
        }

        String fileName = "考察对象_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
*/
    @RequiresPermissions("cadreReserve:import")
    @RequestMapping("/cadreReserve_import")
    public String cadreReserve_import(int reserveType, ModelMap modelMap) {

        modelMap.put("reserveType", reserveType);
        return "cadreReserve/cadreReserve_import";
    }

    @RequiresPermissions("cadreReserve:import")
    @RequestMapping(value = "/cadreReserve_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_import(HttpServletRequest request, int reserveType) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<Cadre> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            Cadre record = new Cadre();

            String userCode = StringUtils.trim(xlsRow.get(0));
            if(StringUtils.isBlank(userCode)){
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            int userId = uv.getId();
            record.setUserId(userId);

            record.setTitle(StringUtils.trimToNull(xlsRow.get(2)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(3)));

            records.add(record);
        }

        int addCount = cadreReserveService.batchImport(records, reserveType);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", addCount);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入优秀年轻干部成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
