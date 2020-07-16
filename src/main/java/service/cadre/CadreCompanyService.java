package service.cadre;

import bean.MetaClassOption;
import controller.global.OpException;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.*;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import persistence.cadre.common.CadreCompanyStatBean;
import service.BaseMapper;
import service.base.MetaTypeService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.xlsx.ExcelTool;
import sys.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class CadreCompanyService extends BaseMapper {
    @Autowired
    private CadreService cadreService;

    @Transactional
    public int insertSelective(CadreCompany record) {
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        return cadreCompanyMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, Integer cadreId) {

        if (ids == null || ids.length == 0) return;
        if(cadreId!=null){
            // 干部信息本人直接修改数据校验
            CadreCompanyExample example = new CadreCompanyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            long count = cadreCompanyMapper.countByExample(example);
            if (count != ids.length) {
                throw new OpException("参数有误");
            }
        }else{
            // 必须 拥有 干部兼职管理 模块的管理权限， 才能随意删除
            SecurityUtils.getSubject().checkPermission("cadreCompanyList:menu");
        }
        CadreCompanyExample example = new CadreCompanyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreCompanyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreCompany record) {
        record.setStatus(null);
        return cadreCompanyMapper.updateByPrimaryKeySelective(record);
    }

    // 导入时使用，用来判断是否覆盖
    public CadreCompany getCadreCompany(int cadreId, int type, String unit, Date startTime) {

        CadreCompanyExample example = new CadreCompanyExample();
        CadreCompanyExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type)
                .andUnitEqualTo(unit);
        if (startTime != null) {
            criteria.andStartTimeEqualTo(startTime);
        }

        List<CadreCompany> cadreCompanies = cadreCompanyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cadreCompanies.size() == 1 ? cadreCompanies.get(0) : null;
    }

    @Transactional
    public int bacthImport(List<CadreCompany> records) {

        int addCount = 0;
        for (CadreCompany record : records) {
            CadreCompany cadreCompany = getCadreCompany(record.getCadreId(), record.getType(),
                    record.getUnit(), record.getStartTime());
            if (cadreCompany == null) {
                insertSelective(record);
                addCount++;
            } else {
                record.setId(cadreCompany.getId());
                updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
    }

    // 更新修改申请的内容（仅允许管理员和本人更新自己的申请）
    @Transactional
    public void updateModify(CadreCompany record, Integer applyId) {

        if (applyId == null) {
            throw new OpException("参数有误");
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if ((!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN) && mta.getUserId().intValue() != currentUserId) ||
                mta.getStatus() != ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new OpException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        int id = record.getId();
        CadreCompanyExample example = new CadreCompanyExample();
        CadreCompanyExample.Criteria criteria = example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN)){
            CadreView cadre = cadreService.dbFindByUserId(currentUserId);
            criteria.andCadreIdEqualTo(cadre.getId()); // 保证本人只更新自己的记录
        }

        record.setId(null);
        record.setStatus(null);
        if (cadreCompanyMapper.updateByExampleSelective(record, example) > 0 && mta.getUserId().intValue() == currentUserId) {

            // 更新申请时间
            ModifyTableApply _record = new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreCompany record, Integer id, boolean isDelete, String reason) {

        CadreCompany original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadreCompanyMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreCompanyMapper.selectByPrimaryKey(record.getId());
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;

                if (StringUtils.isBlank(record.getApprovalFile())) {
                    record.setApprovalFile(original.getApprovalFile());
                    record.setApprovalFilename(original.getApprovalFilename());
                }
            }
        }

        // 拥有管理干部信息或管理干部本人信息的权限，不允许提交申请
        if (CmTag.canDirectUpdateCadreInfo(record.getCadreId())) {
            throw new OpException("您有直接修改[干部基本信息-干部信息]的权限，请勿在此提交申请。");
        }

        Integer originalId = original == null ? null : original.getId();
        if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {
            // 如果是修改或删除请求，则只允许一条未审批记录存在
            ModifyTableApplyExample example = new ModifyTableApplyExample();
            example.createCriteria().andOriginalIdEqualTo(originalId) // 此时originalId肯定不为空
                    .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY)
                    .andStatusEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
            List<ModifyTableApply> applies = modifyTableApplyMapper.selectByExample(example);
            if (applies.size() > 0) {
                throw new OpException(String.format("当前记录对应的修改或删除申请[序号%s]已经存在，请等待审核。", applies.get(0).getId()));
            }
        }

        Integer userId = ShiroHelper.getCurrentUserId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        record.setCadreId(cadre.getId());  // 保证本人只能提交自己的申请
        record.setId(null);
        record.setStatus(SystemConstants.RECORD_STATUS_MODIFY);
        cadreCompanyMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_company");
        _record.setOriginalId(originalId);
        _record.setModifyId(record.getId());
        _record.setType(type);
        _record.setReason(reason);
        _record.setOriginalJson(JSONUtils.toString(original, false));
        _record.setCreateTime(new Date());
        _record.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));
        _record.setStatus(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
        modifyTableApplyMapper.insert(_record);
    }

    // 审核修改申请
    @Transactional
    public ModifyTableApply approval(ModifyTableApply mta, ModifyTableApply record, Boolean status) {

        Integer originalId = mta.getOriginalId();
        Integer modifyId = mta.getModifyId();
        byte type = mta.getType();

        if (status) {
            if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD) {

                CadreCompany modify = cadreCompanyMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreCompanyMapper.insertSelective(modify); // 插入新纪录
                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadreCompany modify = cadreCompanyMapper.selectByPrimaryKey(modifyId);
                modify.setId(originalId);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreCompanyMapper.updateByPrimaryKey(modify); // 覆盖原纪录

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                // 更新最后删除的记录内容
                record.setOriginalJson(JSONUtils.toString(cadreCompanyMapper.selectByPrimaryKey(originalId), false));
                // 删除原纪录
                cadreCompanyMapper.deleteByPrimaryKey(originalId);
            }
        }

        CadreCompany modify = new CadreCompany();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadreCompanyMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录
        return record;
    }

    // 结束兼职
    @Transactional
    public void finish(int id, Date finishTime, boolean isFinished) {

        if (isFinished) {

            CadreCompany record = new CadreCompany();
            record.setId(id);
            record.setFinishTime(finishTime);
            record.setIsFinished(true);
            cadreCompanyMapper.updateByPrimaryKeySelective(record);
        } else {

            commonMapper.excuteSql("update cadre_company set is_finished=0, finish_time=null where id=" + id);
        }
    }

    // Map<cadreId, bean>
    public Map<Integer, CadreCompanyStatBean> listCadreCompanyStatBeans(String cadreStatus) {

        List<Map> itemMap = iCadreMapper.cadreCompany_statMap(cadreStatus);

        Map<Integer, CadreCompanyStatBean> statMap = new LinkedHashMap<>();

        for (Map resultMap : itemMap) {

            int cadreId = ((Long) resultMap.get("cadre_id")).intValue();
            int type = ((Long) resultMap.get("type")).intValue();
            int num = ((Long) resultMap.get("num")).intValue();

            if (!statMap.containsKey(cadreId)) {
                CadreCompanyStatBean bean = new CadreCompanyStatBean();
                statMap.put(cadreId, bean);
            }
            CadreCompanyStatBean bean = statMap.get(cadreId);
            Map<Integer, Integer> typeMap = bean.getTypeMap();
            if (!typeMap.containsKey(type)) {
                typeMap.put(type, num);
            } else {
                typeMap.put(type, typeMap.get(type) + num);
            }

            bean.setTotalCount(bean.getTotalCount() + num);
        }

        return statMap;
    }


    // 兼职情况汇总表
    public void export(Integer[] ids, Byte cadreStatus, CadreCompanyViewExample example, int exportType, Integer reserveType, HttpServletResponse response) throws IOException {

        String schoolName = CmTag.getSysConfig().getSchoolName();
        String cadreType = "";
        if (cadreStatus != null) {
            if (cadreStatus == CadreConstants.CADRE_STATUS_LEADER) {
                cadreType = "现任校领导";
            } else if (cadreStatus == CadreConstants.CADRE_STATUS_LEADER_LEAVE) {
                cadreType = "离任校领导";
            } else if (cadreStatus == CadreConstants.CADRE_STATUS_CJ) {
                cadreType = "现任处级干部";
            } else if (cadreStatus == CadreConstants.CADRE_STATUS_CJ_LEAVE) {
                cadreType = "离任处级干部";
            } else if (cadreStatus == CadreConstants.CADRE_STATUS_KJ) {
                cadreType = "现任科级干部";
            } else if (cadreStatus == CadreConstants.CADRE_STATUS_KJ_LEAVE) {
                cadreType = "离任科级干部";
            }
        }else {
            MetaTypeService metaTypeService = CmTag.getBean(MetaTypeService.class);
            cadreType = metaTypeService.getName(reserveType);
        }
        List<CadreCompanyView> records = new ArrayList<>();
        if (exportType == 0){
            records = cadreCompanyViewMapper.selectByExample(example);
        }else {
            records = iCadreMapper.getCadreReserveCompany(ids, reserveType, CadreConstants.CADRE_RESERVE_STATUS_NORMAL);
        }

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cadre/cadre_company_list.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", schoolName + cadreType);
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("date", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);

        int startRow = 3;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            CadreCompanyView record = records.get(i);
            int column = 0;
            row = sheet.getRow(startRow++);

            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(CmTag.realnameWithEmpty(record.getRealname()));

            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(record.getTitle()));

            // 兼职类型
            String _type = "";
            int type = record.getType();
            if (type == CmTag.getMetaTypeByCode("mt_cadre_company_other").getId()) {
                _type = StringUtils.defaultString(record.getTypeOther(), "其他");
            } else {
                _type = CmTag.getMetaType(type).getName();
            }
            cell = row.getCell(column++);
            cell.setCellValue(_type);

            // 兼职单位
            cell = row.getCell(column++);
            cell.setCellValue(record.getUnit());

            // 兼任职务
            cell = row.getCell(column++);
            cell.setCellValue(record.getPost());

            // 兼职起始时间
            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(record.getStartTime(), DateUtils.YYYYMM));

            // 审批单位
            cell = row.getCell(column++);
            cell.setCellValue(record.getApprovalUnit());

            // 批复日期
            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(record.getApprovalDate(), DateUtils.YYYYMM));

            // 是否取酬
            cell = row.getCell(column++);
            cell.setCellValue(BooleanUtils.isTrue(record.getHasPay()) ? "是" : "否");

            // 所取酬劳是否全额上交学校
            cell = row.getCell(column++);
            cell.setCellValue(BooleanUtils.isTrue(record.getHasHand()) ? "是" : "否");
        }

        String fileName = String.format("%s兼职情况汇总表", schoolName + cadreType);
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    // 导出统计表 （最多支持7个兼职类别）
    public void exportStat(byte cadreStatus, HttpServletResponse response) throws IOException {

        Map<Integer, CadreCompanyStatBean> records = listCadreCompanyStatBeans(String.valueOf(cadreStatus));

        String schoolName = CmTag.getSysConfig().getSchoolName();
        String cadreType = "";
        if (cadreStatus == CadreConstants.CADRE_STATUS_LEADER) {
            cadreType = "现任校领导";
        } else if (cadreStatus == CadreConstants.CADRE_STATUS_LEADER_LEAVE) {
            cadreType = "离任校领导";
        } else if (cadreStatus == CadreConstants.CADRE_STATUS_CJ) {
            cadreType = "现任干部";
        }

        InputStream is = new FileInputStream(ResourceUtils
                .getFile("classpath:xlsx/cadre/cadre_company_stat.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);

        Map<Integer, MetaType> companyTypeMap = CmTag.getMetaTypes("mc_cadre_company_type");
        List<MetaType> companyTypeList = new ArrayList<>(companyTypeMap.values());
        if (companyTypeList.size() > 7) { // 最多支持7个兼职类别
            companyTypeList = companyTypeList.subList(0, 7);
        }

        if (companyTypeList.size() == 7) {
            wb.removeSheetAt(0); // sheet0支持6个以内的兼职类别
        } else {
            wb.removeSheetAt(1); // sheet1支持7个兼职类别
        }

        XSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0, "sheet1");

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", schoolName + cadreType);
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("date", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);

        row = sheet.getRow(3);
        for (int j = 0; j < companyTypeList.size(); j++) {
            cell = row.getCell(j + 5);
            MetaType metaType = companyTypeList.get(j);
            cell.setCellValue(metaType.getName());
        }

        Map<Integer, Integer> typeTotalCountMap = new HashMap<>();
        int totalCount = 0;

        int startRow = 4;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        int i = 0;
        for (Map.Entry<Integer, CadreCompanyStatBean> entry : records.entrySet()) {

            int cadreId = entry.getKey();
            CadreView cv = CmTag.getCadreById(cadreId);
            int column = 0;
            row = sheet.getRow(startRow++);

            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(++i);

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(CmTag.realnameWithEmpty(cv.getRealname()));

            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(cv.getTitle()));

            // 行政级别
            cell = row.getCell(column++);
            cell.setCellValue(cv.getAdminLevelName());

            // 兼职个数
            cell = row.getCell(column++);
            cell.setCellValue(entry.getValue().totalCount);

            totalCount += entry.getValue().totalCount;

            Map<Integer, Integer> typeMap = entry.getValue().getTypeMap();
            for (int j = 0; j < companyTypeList.size(); j++) {
                cell = row.getCell(j + 5);
                MetaType metaType = companyTypeList.get(j);
                int typeId = metaType.getId();
                if (typeMap.containsKey(typeId)) {

                    int num = typeMap.get(typeId);
                    cell.setCellValue(num);

                    if (!typeTotalCountMap.containsKey(typeId)) {
                        typeTotalCountMap.put(typeId, num);
                    } else {
                        typeTotalCountMap.put(typeId, typeTotalCountMap.get(typeId) + num);
                    }
                } else {
                    cell.setCellValue("-");
                }
            }
        }

        // 合计
        row = sheet.getRow(startRow);
        cell = row.getCell(4);
        cell.setCellValue(totalCount);

        for (int j = 0; j < companyTypeList.size(); j++) {
            cell = row.getCell(j + 5);
            Integer num = typeTotalCountMap.get(companyTypeList.get(j).getId());
            if (num != null) {
                cell.setCellValue(num);
            } else {
                cell.setCellValue("-");
            }
        }

        String fileName = String.format("%s企业、社团兼职", cadreType);
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    // 按类型导出干部汇总表
    public void exportStatByType(HttpServletResponse response) throws IOException {

        String schoolName = CmTag.getSysConfig().getSchoolName();

        InputStream is = new FileInputStream(ResourceUtils
                .getFile("classpath:xlsx/cadre/cadre_company_statByType.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);

        Map<Integer, MetaType> companyTypeMap = CmTag.getMetaTypes("mc_cadre_company_type");
        List<MetaType> companyTypeList = new ArrayList<>(companyTypeMap.values());

        if (companyTypeList.size() > 7) { // 最多支持7个兼职类别
            companyTypeList = companyTypeList.subList(0, 7);
        }
        int companyTypeSize = companyTypeList.size();

        if (companyTypeSize == 7) {
            wb.removeSheetAt(0); // sheet0支持6个以内的兼职类别
        } else {
            wb.removeSheetAt(1); // sheet1支持7个兼职类别
        }

        XSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0, "sheet1");

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", schoolName);
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue()
                .replace("date", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);

        row = sheet.getRow(3);
        for (int j = 0; j < companyTypeSize; j++) {
            cell = row.getCell(j * 2 + 4);
            MetaType metaType = companyTypeList.get(j);
            cell.setCellValue(metaType.getName());
        }

        MetaClass mcUnitType = CmTag.getMetaClassByCode("mc_unit_type");
        Map<String, MetaClassOption> unitTypeGroupMap = mcUnitType.getOptions();
        List<MetaClassOption> unitTypeGroupList = new ArrayList<>(unitTypeGroupMap.values());
        int unitTypeGroupSize = unitTypeGroupMap.size();
        int unitTypeGroupStartRow = 11;
        // 从第12行起，插入单位类型汇总数据
        ExcelUtils.insertRow(wb, sheet, unitTypeGroupStartRow,  unitTypeGroupSize - 1);

        List<Map> statByTypeData = statByTypeData();
        int startRow = 5;
        int column = 2;
        for (Map map : statByTypeData) {

            row = sheet.getRow(startRow);
            if(startRow>=unitTypeGroupStartRow){
                MetaClassOption option = unitTypeGroupList.get(startRow - unitTypeGroupStartRow);
                cell = row.getCell(1);
                cell.setCellValue(option.getName()); // 单位类型分组名称
            }

            for (int j = 0; j < (companyTypeSize + 1) * 2; j++) {
                cell = row.getCell(column + j);
                if (map.containsKey(column + j))
                    cell.setCellValue(map.get(column + j) + "");
                else
                    cell.setCellValue("-");
            }

            startRow++;
        }
        // 合并单位类型第一列
        sheet.addMergedRegion(ExcelTool.getCellRangeAddress(unitTypeGroupStartRow, 0,
                unitTypeGroupStartRow + unitTypeGroupSize - 1, 0));

        String fileName = String.format("%s干部兼职情况统计表", schoolName);
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    // 干部兼职统计
    public List<Map> statByTypeData() {

        Map<Integer, MetaType> companyTypeMap = CmTag.getMetaTypes("mc_cadre_company_type");
        List<MetaType> companyTypeList = new ArrayList<>(companyTypeMap.values());
        if (companyTypeList.size() > 7) { // 最多支持7个兼职类别
            companyTypeList = companyTypeList.subList(0, 7);
        }

        // 第一行数据
        Map<Integer, Integer> row1Map = new LinkedHashMap<>();
        row1Map.put(2, 0);
        row1Map.put(3, 0);
        // 兼职类型、总数
        List<Map> typeStatMap = iCadreMapper.cadreCompany_typeStatMap();
        Map<Integer, Map> _typeStatMap = new HashMap<>();
        for (Map map : typeStatMap) {
            int type = ((Long) map.get("type")).intValue();
            _typeStatMap.put(type, map);
        }
        for (int j = 0; j < companyTypeList.size(); j++) {
            MetaType metaType = companyTypeList.get(j);
            Map map = _typeStatMap.get(metaType.getId());
            if (map != null) {
                int personNum = ((Long) map.get("person_num")).intValue();
                int num = ((Long) map.get("num")).intValue();

                row1Map.put(j * 2 + 4, personNum);
                row1Map.put(j * 2 + 5, num);

                row1Map.put(2, row1Map.get(2) + personNum);
                row1Map.put(3, row1Map.get(3) + num);
            }
        }

        // 第2~4行数据
        Map<Integer, Integer> row2Map = new LinkedHashMap<>(); // 正处级
        row2Map.put(2, 0);
        row2Map.put(3, 0);
        Map<Integer, Integer> row3Map = new LinkedHashMap<>();
        row3Map.put(2, 0);
        row3Map.put(3, 0);
        Map<Integer, Integer> row4Map = new LinkedHashMap<>();
        row4Map.put(2, 0);
        row4Map.put(3, 0);

        // 是否双肩挑、行政级别
        List<Map> adminLevelStatMap = iCadreMapper.cadreCompany_adminLevelStatMap();
        Map<String, Map> _adminLevelStatMap = new HashMap<>();
        for (Map map : adminLevelStatMap) {
            int type = ((Long) map.get("type")).intValue();
            int adminLevel = ((Long) map.get("admin_level")).intValue();
            _adminLevelStatMap.put(type + "_" + adminLevel, map);
        }
        for (int j = 0; j < companyTypeList.size(); j++) {
            MetaType metaType = companyTypeList.get(j);
            Map map = _adminLevelStatMap.get(metaType.getId() + "_" + CmTag.getMetaTypeByCode("mt_admin_level_main").getId());
            if (map != null) {
                int personNum = ((Long) map.get("person_num")).intValue();
                int num = ((Long) map.get("num")).intValue();

                row2Map.put(j * 2 + 4, personNum);
                row2Map.put(j * 2 + 5, num);

                row2Map.put(2, row2Map.get(2) + personNum);
                row2Map.put(3, row2Map.get(3) + num);
            }

            map = _adminLevelStatMap.get(metaType.getId() + "_" + CmTag.getMetaTypeByCode("mt_admin_level_vice").getId());
            if (map != null) {
                int personNum = ((Long) map.get("person_num")).intValue();
                int num = ((Long) map.get("num")).intValue();

                row3Map.put(j * 2 + 4, personNum);
                row3Map.put(j * 2 + 5, num);

                row3Map.put(2, row3Map.get(2) + personNum);
                row3Map.put(3, row3Map.get(3) + num);
            }

            map = _adminLevelStatMap.get(metaType.getId() + "_" + CmTag.getMetaTypeByCode("mt_admin_level_none").getId());
            if (map != null) {
                int personNum = ((Long) map.get("person_num")).intValue();
                int num = ((Long) map.get("num")).intValue();

                row4Map.put(j * 2 + 4, personNum);
                row4Map.put(j * 2 + 5, num);

                row4Map.put(2, row4Map.get(2) + personNum);
                row4Map.put(3, row4Map.get(3) + num);
            }
        }

        // 第5~6行数据
        Map<Integer, Integer> row5Map = new LinkedHashMap<>(); // 正处级
        row5Map.put(2, 0);
        row5Map.put(3, 0);
        Map<Integer, Integer> row6Map = new LinkedHashMap<>();
        row6Map.put(2, 0);
        row6Map.put(3, 0);
        // 是否双肩挑、兼职类型
        List<Map> doubleStatMap = iCadreMapper.cadreCompany_doubleStatMap();
        Map<String, Map> _doubleStatMap = new HashMap<>();
        for (Map map : doubleStatMap) {
            int type = ((Long) map.get("type")).intValue();
            int isDouble = BooleanUtils.isTrue((Boolean) map.get("is_double")) ? 1 : 0;
            _doubleStatMap.put(type + "_" + isDouble, map);
        }
        for (int j = 0; j < companyTypeList.size(); j++) {
            MetaType metaType = companyTypeList.get(j);

            Map map = _doubleStatMap.get(metaType.getId() + "_1");
            if (map != null) {
                int personNum = ((Long) map.get("person_num")).intValue();
                int num = ((Long) map.get("num")).intValue();

                row5Map.put(j * 2 + 4, personNum);
                row5Map.put(j * 2 + 5, num);

                row5Map.put(2, row5Map.get(2) + personNum);
                row5Map.put(3, row5Map.get(3) + num);
            }

            map = _doubleStatMap.get(metaType.getId() + "_0");
            if (map != null) {
                int personNum = ((Long) map.get("person_num")).intValue();
                int num = ((Long) map.get("num")).intValue();

                row6Map.put(j * 2 + 4, personNum);
                row6Map.put(j * 2 + 5, num);

                row6Map.put(2, row6Map.get(2) + personNum);
                row6Map.put(3, row6Map.get(3) + num);
            }
        }


        // 第7~最后一行数据  根据兼职类型、单位类型分组 统计数量
        Map<String, Map<Integer, Integer>> rowsMap = new LinkedHashMap<>(); // 按 单位类型分组 排序
        MetaClass mcUnitType = CmTag.getMetaClassByCode("mc_unit_type");
        Map<String, MetaClassOption> unitTypeGroupMap = mcUnitType.getOptions();
        for (String unitTypeGroup : unitTypeGroupMap.keySet()) {
            Map<Integer, Integer> rowMap = new LinkedHashMap<>();
            rowMap.put(2, 0); // 初始化每行汇总数量
            rowMap.put(3, 0);
            rowsMap.put(unitTypeGroup, rowMap);
        }

        List<Map> unitTypeStatMap = iCadreMapper.cadreCompany_unitTypeGroupStatMap();
        Map<String, Map> _unitTypeStatMap = new HashMap<>();
        for (Map map : unitTypeStatMap) {
            int type = ((Long) map.get("type")).intValue();
            String unitTypeGroup = (String) map.get("unit_type_group");
            if (StringUtils.isNotBlank(unitTypeGroup)) {
                _unitTypeStatMap.put(type + "_" + unitTypeGroup, map);
            }
        }

        for (int j = 0; j < companyTypeList.size(); j++) {
            MetaType metaType = companyTypeList.get(j);

            for (Map.Entry<String, Map<Integer, Integer>> entry : rowsMap.entrySet()) {
                String unitTypeGroup = entry.getKey();
                Map<Integer, Integer> rowMap = entry.getValue();

                Map map = _unitTypeStatMap.get(metaType.getId() + "_" + unitTypeGroup);
                if (map != null) {

                    int personNum = ((Long) map.get("person_num")).intValue();
                    int num = ((Long) map.get("num")).intValue();

                    rowMap.put(j * 2 + 4, personNum);
                    rowMap.put(j * 2 + 5, num);

                    rowMap.put(2, rowMap.get(2) + personNum);
                    rowMap.put(3, rowMap.get(3) + num);
                }
            }
        }

        List<Map> dataList = new ArrayList<>();
        dataList.add(row1Map);
        dataList.add(row2Map);
        dataList.add(row3Map);
        dataList.add(row4Map);
        dataList.add(row5Map);
        dataList.add(row6Map);

        for (Map.Entry<String, Map<Integer, Integer>> entry : rowsMap.entrySet()) {
            Map<Integer, Integer> rowMap = entry.getValue();
            dataList.add(rowMap);
        }

        return dataList;
    }
}
