package controller.sp;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sp.SpNpc;
import domain.sp.SpNpcExample;
import domain.sp.SpNpcExample.Criteria;
import domain.sys.SysUserView;
import domain.unit.Unit;
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
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sp")
public class SpNpcController extends SpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sp:list")
    @RequestMapping("/spNpc")
    public String spNpc(@RequestParam(required = false, defaultValue = "0") boolean isHistory,
                        Integer userId,
                        ModelMap modelMap) {

        modelMap.put("sysUser",CmTag.getUserById(userId));
        modelMap.put("isHistory",isHistory);
        return "sp/spNpc/spNpc_page";
    }

    @RequiresPermissions("sp:list")
    @RequestMapping("/spNpc_data")
    @ResponseBody
    public void spNpc_data(HttpServletResponse response,
                                    Integer type,
                                    String th,
                                    Integer userId,
                                 @RequestParam(required = false, defaultValue = "0")Boolean isHistory,
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

        SpNpcExample example = new SpNpcExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(th)) {
            criteria.andThLike(SqlUtils.like(th));
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            spNpc_export(example,isHistory, response);
            return;
        }
        if (isHistory!=null){
            criteria.andIsHistoryEqualTo(isHistory);
        }

        long count = spNpcMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SpNpc> records= spNpcMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
    @RequestMapping(value = "/spNpc_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spNpc_au(SpNpc record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsHistory(BooleanUtils.isFalse(record.getIsHistory()));
        spNpcService.setIsCadre(record);

        if (id == null) {

            spNpcService.insertSelective(record);
            logger.info(log( LogConstants.LOG_SP, "添加人大代表和政协委员：{0}", record.getId()));
        } else {

            spNpcService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_SP, "更新人大代表和政协委员：{0}", record.getId()));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping("/spNpc_au")
    public String spNpc_au(Integer id, ModelMap modelMap) {

        if (id != null) {

            SpNpc spNpc = spNpcMapper.selectByPrimaryKey(id);
            modelMap.put("spNpc", spNpc);
            SysUserView sysUserView = CmTag.getUserById(spNpc.getUserId());
            modelMap.put("sysUser",sysUserView);
            Unit unit = CmTag.getUnit(spNpc.getUnitId());
            modelMap.put("unit",unit);
        }
        return "sp/spNpc/spNpc_au";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spNpc_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spNpc_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            spNpcService.del(id);
            logger.info(log( LogConstants.LOG_SP, "删除人大代表和政协委员：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spNpc_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map spNpc_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){

            spNpcService.batchDel(ids);
            logger.info(log( LogConstants.LOG_SP, "批量删除人大代表和政协委员：{0}", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spNpc_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spNpc_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        spNpcService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_SP, "人大代表和政协委员调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void spNpc_export(SpNpcExample example,Boolean isHistory, HttpServletResponse response) {

        List<SpNpc> records = spNpcMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"类别|100","届次|100","姓名|100","性别|50","出生时间|150", "政治面貌|100", "人大/政协职务|100",
                "所在单位|100","当选时职务|100",(isHistory?"离任时职务":"现任职务")+"|100","是否现任领导干部|100","联系方式|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            SpNpc record = records.get(i);
            SysUserView sysUserView = CmTag.getUserById(record.getUserId());
            MetaType type = CmTag.getMetaType(record.getType());
            MetaType politicsStatus = CmTag.getMetaType(record.getPoliticsStatus());
            Unit unit = CmTag.getUnitById(record.getUnitId());

            String[] values = {
                            type==null?"":type.getName(),
                            record.getTh(),
                            sysUserView.getRealname(),
                            SystemConstants.GENDER_MAP.get(sysUserView.getGender()),
                            DateUtils.formatDate(sysUserView.getBirth(),"yyyy-MM-dd"),
                            politicsStatus==null?"":politicsStatus.getName(),
                            record.getNpcPost(),
                            unit==null?"":unit.getName(),
                            record.getElectedPost(),
                            record.getPost(),
                            record.getIsCadre()?"是":"否",
                            record.getPhone(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("人大代表和政协委员(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/spNpc_selects")
    @ResponseBody
    public Map spNpc_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SpNpcExample example = new SpNpcExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        long count = spNpcMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SpNpc> records = spNpcMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(SpNpc record:records){

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

    //离任
    @RequiresPermissions("sp:edit")
    @RequestMapping("/spNpc_history")
    public String spNpc_history(Integer id, ModelMap modelMap){

        if (id != null){

            SpNpc spNpc = spNpcMapper.selectByPrimaryKey(id);
            modelMap.put("spNpc",spNpc);

            CadreView cadre = CmTag.getCadreByUserId(spNpc.getUserId());
            if (cadre != null && spNpcService.isCurrentCadre(cadre.getStatus())){
                 modelMap.put("cadre",cadre);
            }
        }
        return "sp/spNpc/spNpc_history";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spNpc_history", method = RequestMethod.POST)
    @ResponseBody
    public Map spNpcHistory(SpNpc spNpc){

        spNpc.setIsHistory(true);
        spNpc.setSortOrder(getNextSortOrder("sp_npc", "is_history="+true));
        spNpcService.updateByPrimaryKeySelective(spNpc);
        logger.info(log( LogConstants.LOG_SP, "人大代表和政协委员离任：{0}", spNpc.getId()));
        return success(FormUtils.SUCCESS);
    }

    //导入
    @RequiresPermissions("sp:edit")
    @RequestMapping("/spNpc_import")
    public String unitPost_import(ModelMap modelMap) {

        return "sp/spNpc/spNpc_import";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spNpc_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spNpc_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<SpNpc> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            SpNpc record = new SpNpc();
            row++;

            //类别
            String type = StringUtils.trimToNull(xlsRow.get(0));
            MetaType spNpcType = CmTag.getMetaTypeByName("mc_sp_npc_type", type);
            if (spNpcType == null){
                throw new OpException("第{0}行类别[{1}]不存在", row, type);
            }
            record.setType(spNpcType.getId());

            //届次
            String th = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(th)){
                throw new OpException("第{0}行届次为空",row);
            }
            record.setTh(th);

            //姓名
            String code = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isBlank(code)) {
                throw new OpException("第{0}行学工号为空",row);
            }
            SysUserView sysUserView = CmTag.getUserByCode(code);
            if (sysUserView == null) {
                throw new OpException("第{0}行的学工号[{1}]不存在",row,code);
            }
            record.setUserId(sysUserView.getUserId());

            //政治面貌
            String politicsStatus = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isBlank(politicsStatus)){
                throw new OpException("第{0}行的政治面貌为空",row);
            }
            MetaType politicalStatus = CmTag.getMetaTypeByName("mc_political_status", politicsStatus);
            if (politicalStatus == null){
                throw new OpException("第{0}行的政治面貌[{1}]元数据中不存在",row,politicsStatus);
            }
            record.setPoliticsStatus(politicalStatus.getId());

            //人大/政协职务
            record.setNpcPost(StringUtils.trimToNull(xlsRow.get(4)));

            //所在单位
            String unitCode = StringUtils.trimToNull(xlsRow.get(5));
            if (StringUtils.isBlank(unitCode)){
                throw new OpException("第{0}行的单位编号为空",row);
            }
            Unit unit = unitService.findRunUnitByCode(unitCode);
            if (unit == null){
                throw new OpException("第{0}行的单位编号[{1}]不存在",row,code);
            }
            record.setUnitId(unit.getId());

            //当选时职务
            record.setElectedPost(StringUtils.trimToNull(xlsRow.get(6)));

            //现任/离任时职务
            record.setPost(StringUtils.trimToNull(xlsRow.get(7)));

            //是否离任
            record.setIsHistory(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(8)), "是"));
            record.setPhone(StringUtils.trimToNull(xlsRow.get(9)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(10)));

            record.setIsCadre(CmTag.getCadreByUserId(sysUserView.getUserId())!=null);

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = spNpcService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入人大代表/政协委员成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    //干部基本信息
    @RequestMapping(value = "/spNpc_details", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spNpc_details(Integer userId){

        Map dateilsMap = new HashMap();
        CadreView cadre = CmTag.getCadreByUserId(userId);

        if (cadre == null) return null;
        if (spNpcService.isCurrentCadre(cadre.getStatus())){

            dateilsMap.put("cadre",cadre);
        }

        return dateilsMap;
    }
}
