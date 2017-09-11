package service.abroad;

import domain.abroad.Passport;
import domain.abroad.PassportExample;
import domain.abroad.SafeBox;
import domain.abroad.SafeBoxExample;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.common.PassportSearchBean;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.sys.SysUserService;
import sys.Utils;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SafeBoxService extends BaseMapper {

    @Autowired
    private CadreService cadreService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private SysUserService sysUserService;

    public SafeBox getByCode(String code) {

        SafeBoxExample example = new SafeBoxExample();
        example.createCriteria().andCodeEqualTo(code.trim());
        List<SafeBox> safeBoxes = safeBoxMapper.selectByExample(example);
        if (safeBoxes.size() > 0) return safeBoxes.get(0);
        return null;
    }

    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        SafeBoxExample example = new SafeBoxExample();
        SafeBoxExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return safeBoxMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value = "SafeBox:ALL", allEntries = true)
    public int insertSelective(SafeBox record) {

        Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate code");
        record.setSortOrder(getNextSortOrder("abroad_safe_box", "1=1"));
        return safeBoxMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "SafeBox:ALL", allEntries = true)
    public void del(Integer id) {

        safeBoxMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value = "SafeBox:ALL", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        SafeBoxExample example = new SafeBoxExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        safeBoxMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value = "SafeBox:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(SafeBox record) {
        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate code");
        return safeBoxMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "SafeBox:ALL")
    public Map<Integer, SafeBox> findAll() {

        SafeBoxExample example = new SafeBoxExample();
        example.setOrderByClause("sort_order desc");
        List<SafeBox> safeBoxes = safeBoxMapper.selectByExample(example);
        Map<Integer, SafeBox> map = new LinkedHashMap<>();
        for (SafeBox safeBox : safeBoxes) {
            map.put(safeBox.getId(), safeBox);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "SafeBox:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        SafeBox entity = safeBoxMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        SafeBoxExample example = new SafeBoxExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<SafeBox> overEntities = safeBoxMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            SafeBox targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("abroad_safe_box", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("abroad_safe_box", null, baseSortOrder, targetEntity.getSortOrder());

            SafeBox record = new SafeBox();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            safeBoxMapper.updateByPrimaryKeySelective(record);
        }
    }


    public void safeBoxPassport_export(HttpServletResponse response, Integer[] ids) {

        Map<Integer, CadreView> cadreMap = cadreService.findAll();
        Map<Integer, MetaType> passportType = metaTypeService.metaTypes("mc_passport_type");
        //Map<Integer, SafeBox> safeBoxMap = findAll();
        List<SafeBox> safeBoxes = new ArrayList<>();
        {
            SafeBoxExample example = new SafeBoxExample();
            SafeBoxExample.Criteria criteria = example.createCriteria();
            if (ids != null && ids.length > 0) {
                criteria.andIdIn(Arrays.asList(ids));
            }
            example.setOrderByClause("sort_order desc");
            safeBoxes = safeBoxMapper.selectByExample(example);
        }

        int rowNum = 0;
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //sheet.setDefaultColumnWidth(12);
        //sheet.setDefaultRowHeight((short)(20*60));
        {
            Row titleRow = sheet.createRow(rowNum);
            titleRow.setHeight((short) (35.7 * 30));
            Cell headerCell = titleRow.createCell(0);
            XSSFCellStyle cellStyle = wb.createCellStyle();
            // 设置单元格居中对齐
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            XSSFFont font = wb.createFont();
            // 设置字体加粗
            font.setFontName("宋体");
            font.setFontHeight((short) 350);
            cellStyle.setFont(font);
            headerCell.setCellStyle(cellStyle);
            headerCell.setCellValue(CmTag.getSysConfig().getSchoolName() + "干部因私出国（境）证件一览表");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }

        for (SafeBox safeBox : safeBoxes) {

            Integer safeBoxId = safeBox.getId();
            PassportSearchBean bean = new PassportSearchBean(null, null, null, null, null,
                    safeBoxId, null, null);
            List<Passport> passports = iAbroadMapper.selectPassportList(bean, new RowBounds());
            int size = passports.size();
            if (size == 0) continue;

            PassportExample example = new PassportExample();
            example.createCriteria().andSafeBoxIdEqualTo(safeBoxId).
                    andTypeEqualTo(SystemConstants.PASSPORT_TYPE_KEEP);
            long keepCount = passportMapper.countByExample(example);

            Row header = sheet.createRow(rowNum);
            header.setHeight((short) (35.7 * 18));
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue(String.format("保险柜%s：证件总数%s本，其中集中管理%s本，取消集中管理（未确认）%s本。",
                    safeBox.getCode(), size, keepCount, size - keepCount));
            headerCell.setCellStyle(Utils.getBgColorStyle(wb));

            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
            XSSFRow firstRow = (XSSFRow) sheet.createRow(rowNum++);
            firstRow.setHeight((short) (35.7 * 12));
            String[] titles = {"序号", "工作证号", "姓名", "所在单位及职务", "证件名称", "证件号码",
                    "发证件日期", "有效期", "证件状态", "是否借出"};
            int columnCount = titles.length;
            for (int i = 0; i < columnCount; i++) {
                XSSFCell cell = firstRow.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(Utils.getHeadStyle(wb));

                //sheet.setColumnWidth(i, (short) (35.7*100));
            }
            sheet.setColumnWidth(0, (short) (35.7 * 50));
            sheet.setColumnWidth(1, (short) (35.7 * 100));
            sheet.setColumnWidth(2, (short) (35.7 * 50));
            sheet.setColumnWidth(3, (short) (35.7 * 300));
            sheet.setColumnWidth(4, (short) (35.7 * 160));
            sheet.setColumnWidth(5, (short) (35.7 * 100));
            sheet.setColumnWidth(6, (short) (35.7 * 100));
            sheet.setColumnWidth(7, (short) (35.7 * 100));
            sheet.setColumnWidth(8, (short) (35.7 * 120));
            sheet.setColumnWidth(9, (short) (35.7 * 100));

            for (int i = 0; i < size; i++) {
                Passport passport = passports.get(i);
                CadreView cadre = cadreMap.get(passport.getCadreId());
                SysUserView uv = sysUserService.findById(cadre.getUserId());

                String[] values = {
                        String.valueOf(i + 1),
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        passportType.get(passport.getClassId()).getName(),
                        passport.getCode(),
                        DateUtils.formatDate(passport.getIssueDate(), DateUtils.YYYY_MM_DD),
                        DateUtils.formatDate(passport.getExpiryDate(), DateUtils.YYYY_MM_DD),
                        SystemConstants.PASSPORT_TYPE_MAP.get(passport.getType()),
                        BooleanUtils.isTrue(passport.getIsLent()) ? "借出" : "-"
                };

                Row row = sheet.createRow(rowNum++);
                row.setHeight((short) (35.7 * 18));
                for (int j = 0; j < columnCount; j++) {

                    XSSFCell cell = (XSSFCell) row.createCell(j);
                    cell.setCellValue(values[j]);
                    cell.setCellStyle(Utils.getBodyStyle(wb));
                }
            }

        }
        String fileName = CmTag.getSysConfig().getSchoolName() + "干部因私出国（境）证件一览表(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
