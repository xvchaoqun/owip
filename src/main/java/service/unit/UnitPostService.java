package service.unit;

import domain.cadre.CadreView;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadreView;
import domain.dispatch.DispatchCadreViewExample;
import domain.unit.UnitPost;
import domain.unit.UnitPostExample;
import domain.unit.UnitPostView;
import domain.unit.UnitPostViewExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.base.MetaTypeService;
import sys.constants.DispatchConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UnitPostService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;

    public boolean idDuplicate(Integer id, String code) {

        UnitPostExample example = new UnitPostExample();
        UnitPostExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return unitPostMapper.countByExample(example) > 0;
    }
    
    // 查询某单位下的所有岗位（包含已撤销）
    public List<UnitPost> list(int unitId) {

        UnitPostExample example = new UnitPostExample();
        UnitPostExample.Criteria criteria = example.createCriteria()
                .andUnitIdEqualTo(unitId)
                .andStatusNotEqualTo(SystemConstants.UNIT_POST_STATUS_DELETE);
        example.setOrderByClause("status asc, sort_order desc");

        return unitPostMapper.selectByExample(example);
    }

    // 单位、级别下的占职数的岗位
    public List<UnitPostView> query(int unitId, int adminLevelId, Boolean displayEmpty) {

        UnitPostViewExample example = new UnitPostViewExample();
        UnitPostViewExample.Criteria criteria = example.createCriteria()
                .andUnitIdEqualTo(unitId)
                .andAdminLevelEqualTo(adminLevelId)
                .andIsCpcEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(BooleanUtils.isTrue(displayEmpty)){
            criteria.andCadreIdIsNull();
        }
        return unitPostViewMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(UnitPost record) {

        Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate");
        record.setSortOrder(getNextSortOrder("unit_post",
                String.format("unit_id=%s and status=%s", record.getUnitId(), record.getStatus())));
        unitPostMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        unitPostMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        UnitPostExample example = new UnitPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitPostMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(UnitPost record) {
        if (record.getCode() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");
        return unitPostMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        byte orderBy = ORDER_BY_DESC;

        UnitPost entity = unitPostMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitPostExample example = new UnitPostExample();
        if (addNum * orderBy > 0) {

            example.createCriteria().andUnitIdEqualTo(entity.getUnitId()).andStatusEqualTo(entity.getStatus())
                    .andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andUnitIdEqualTo(entity.getUnitId()).andStatusEqualTo(entity.getStatus())
                    .andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<UnitPost> overEntities = unitPostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            UnitPost targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum * orderBy > 0)
                commonMapper.downOrder("unit_post", String.format("unit_id=%s and status=%s", entity.getUnitId(), entity.getStatus()),
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("unit_post", String.format("unit_id=%s and status=%s", entity.getUnitId(), entity.getStatus()),
                        baseSortOrder, targetEntity.getSortOrder());

            UnitPost record = new UnitPost();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitPostMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void abolish(int id, Date abolishDate) {

        UnitPost record = new UnitPost();
        record.setId(id);
        record.setAbolishDate(abolishDate);
        record.setStatus(SystemConstants.UNIT_POST_STATUS_ABOLISH);

        UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
        record.setSortOrder(getNextSortOrder("unit_post",
                String.format("unit_id=%s and status=%s", unitPost.getUnitId(), SystemConstants.UNIT_POST_STATUS_ABOLISH)));

        unitPostMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void unabolish(int id) {

        UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
        int sortOrder = getNextSortOrder("unit_post",
                String.format("unit_id=%s and status=%s", unitPost.getUnitId(), SystemConstants.UNIT_POST_STATUS_NORMAL));

        commonMapper.excuteSql(String.format("update unit_post set abolish_date=null, status=%s, sort_order=%s where id=%s",
                SystemConstants.UNIT_POST_STATUS_NORMAL, sortOrder, id));
    }

    // 岗位历史任职干部导出
    public void exportCadres(Integer unitPostId, DispatchCadreViewExample example,
                             HttpServletResponse response) throws IOException {

        UnitPost unitPost = unitPostMapper.selectByPrimaryKey(unitPostId);
        List<DispatchCadreView> records = dispatchCadreViewMapper.selectByExample(example);

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/unit/unitPost_cadres.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("name", unitPost.getName());
        cell.setCellValue(str);

        int startRow = 2;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);

        for (int i = 0; i < rowCount; i++) {

            DispatchCadreView record = records.get(i);
            Dispatch dispatch = record.getDispatch();
            CadreView cadre = record.getCadre();

            int column = 0;
            row = sheet.getRow(startRow++);

            cell = row.getCell(column++);
            cell.setCellValue(record.getYear());

            cell = row.getCell(column++);
            cell.setCellValue(dispatch.getDispatchCode());

            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(dispatch.getWorkTime(), DateUtils.YYYYMM));

            cell = row.getCell(column++);
            cell.setCellValue(DispatchConstants.DISPATCH_CADRE_TYPE_MAP.get(record.getType()));

            cell = row.getCell(column++);
            cell.setCellValue(cadre.getCode());

            cell = row.getCell(column++);
            cell.setCellValue(cadre.getRealname());

            cell = row.getCell(column++);
            cell.setCellValue(record.getPost());

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getPostId()));

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getAdminLevelId()));

            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(dispatch.getMeetingTime(), DateUtils.YYYY_MM_DD));

            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(dispatch.getPubTime(), DateUtils.YYYY_MM_DD));
        }

        ExportHelper.output(wb, unitPost.getName() + "岗位历史任职干部.xlsx", response);
    }

    // 空缺或兼职岗位导出
    public void exportOpenList(UnitPostViewExample example, HttpServletResponse response) throws IOException {

        List<UnitPostView> unitPosts = unitPostViewMapper.selectByExample(example);

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/unit/unitPost_openList.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        String schoolName = CmTag.getSysConfig().getSchoolName();
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", schoolName);
        cell.setCellValue(str);

        int startRow = 2;
        int rowCount = unitPosts.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);

        for (int i = 0; i < rowCount; i++) {

            UnitPostView record = unitPosts.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);

            cell = row.getCell(column++);
            cell.setCellValue(i+1);

            cell = row.getCell(column++);
            cell.setCellValue(record.getName());

            cell = row.getCell(column++);
            cell.setCellValue(record.getUnitName());

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getUnitTypeId()));

            cell = row.getCell(column++);
            cell.setCellValue(record.getJob());

            cell = row.getCell(column++);
            cell.setCellValue(BooleanUtils.isTrue(record.getIsPrincipalPost())?"是":"否");

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getAdminLevel()));

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getPostType()));

            cell = row.getCell(column++);
            cell.setCellValue(BooleanUtils.isTrue(record.getIsCpc())?"是":"否");

            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(record.getOpenDate(), DateUtils.YYYY_MM_DD));

        }

        ExportHelper.output(wb, schoolName + "空缺中层干部岗位列表.xlsx", response);
    }
}
