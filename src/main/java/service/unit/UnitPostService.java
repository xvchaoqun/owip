package service.unit;

import controller.global.OpException;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadreView;
import domain.dispatch.DispatchCadreViewExample;
import domain.sys.SysUserView;
import domain.unit.*;
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
import service.cadre.CadrePostService;
import service.sys.SysUserService;
import sys.constants.DispatchConstants;
import sys.constants.RoleConstants;
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
import java.util.stream.Collectors;

@Service
public class UnitPostService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private CadrePostService cadrePostService;
    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, String code) {

        UnitPostExample example = new UnitPostExample();
        UnitPostExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return unitPostMapper.countByExample(example) > 0;
    }

    public UnitPost getByCode(String code) {

        UnitPostExample example = new UnitPostExample();
        example.createCriteria().andCodeEqualTo(code);
        List<UnitPost> records = unitPostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return records.size() == 1 ? records.get(0) : null;
    }

    // 单位的现有岗位中， 行政班子负责人和党委班子负责人，分别最多一个
    public boolean leaderTypeDuplicate(Integer id, int unitId, Byte leaderType) {

        if (leaderType == null || leaderType == SystemConstants.UNIT_POST_LEADER_TYPE_NOT)
            return false;

        UnitPostExample example = new UnitPostExample();
        UnitPostExample.Criteria criteria = example.createCriteria()
                .andUnitIdEqualTo(unitId).andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL)
                .andLeaderTypeEqualTo(leaderType);
        if (id != null) criteria.andIdNotEqualTo(id);

        return unitPostMapper.countByExample(example) > 0;
    }

    public UnitPostView getByLeaderType(int unitId, Byte leaderType) {

        if (leaderType == null || leaderType == SystemConstants.UNIT_POST_LEADER_TYPE_NOT)
            return null;

        UnitPostViewExample example = new UnitPostViewExample();
        example.createCriteria().andUnitIdEqualTo(unitId).andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL)
                .andLeaderTypeEqualTo(leaderType);
        List<UnitPostView> records = unitPostViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return records.size() == 1 ? records.get(0) : null;
    }

    // 查询某单位下的所有岗位（包含已撤销）
    public List<UnitPost> list(int unitId) {

        UnitPostExample example = new UnitPostExample();
        UnitPostExample.Criteria criteria = example.createCriteria()
                .andUnitIdEqualTo(unitId)
                .andStatusNotEqualTo(SystemConstants.UNIT_POST_STATUS_DELETE);
        example.setOrderByClause("status asc, sort_order asc");

        return unitPostMapper.selectByExample(example);
    }

    // 单位、级别下的占职数的岗位
    public List<UnitPostView> query(int unitId, int adminLevel, Boolean displayEmpty) {

        UnitPostViewExample example = new UnitPostViewExample();
        UnitPostViewExample.Criteria criteria = example.createCriteria()
                .andUnitIdEqualTo(unitId)
                .andAdminLevelEqualTo(adminLevel)
                .andIsCpcEqualTo(true)
                .andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL);
        example.setOrderByClause("sort_order asc");

        if (BooleanUtils.isTrue(displayEmpty)) {
            criteria.andCadreIdIsNull();
        }
        return unitPostViewMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(UnitPost record,CadrePost cadrePost) {

        Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate");
        Assert.isTrue(!leaderTypeDuplicate(null, record.getUnitId(), record.getLeaderType()), "leaderType duplicate");
        record.setSortOrder(getNextSortOrder("unit_post",
                String.format("unit_id=%s and status=%s", record.getUnitId(), record.getStatus())));
        unitPostMapper.insertSelective(record);

        syncCadrePost(record,null,cadrePost,true);

    }

    //根据单位编码生成岗位编码
    @Transactional
    public String generateCode(String unitCode) {

        Unit unit = unitService.findRunUnitByCode(unitCode);
        if(unit==null){
            throw new OpException("单位编码不存在：" + unitCode);
        }
        int num = 0;
        UnitPostExample example = new UnitPostExample();
        example.createCriteria().andUnitIdEqualTo(unit.getId());
        example.setOrderByClause("right(code,3) desc");
        List<UnitPost> unitPosts = unitPostMapper.selectByExample(example);
        if (unitPosts.size() > 0) {
            for (int i = 0; i <= unitPosts.size(); i++) {
                String code = unitPosts.get(i).getCode();
                String _code = code.substring(code.length() - 3);
                try {
                    num = Integer.parseInt(_code) + 1;
                    break;
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        } else {
            num = 1;
        }
        return unitCode + String.format("%03d", num);
    }

    //批量插入，读一条插入一条
    @Transactional
    public int singleImport(UnitPost record,CadrePost cadrePost) {

       /* String name = record.getName();*/
        int addCount = 0;
        Integer oldCadreId=null;
       /* Integer unitId = record.getUnitId();*/
        UnitPost unitPost=getByCode(record.getCode());
        if(unitPost!=null){
            record.setId(unitPost.getId());
        }
        if (record.getId() != null) {
           /* Byte status = 1;
            UnitPostExample example = new UnitPostExample();
            example.createCriteria().andNameEqualTo(name).andUnitIdEqualTo(unitId).andUnitIdEqualTo(record.getUnitId()).andStatusEqualTo(status);
            List<UnitPost> unitPosts = unitPostMapper.selectByExample(example);
            if (unitPosts.size() == 0) {
                insertSelective(record);
                addCount++;
            } else {*/
            CadrePost cp=cadrePostService.getByUnitPostId(record.getId());//原关联干部
            if(cp!=null){
                oldCadreId=cp.getCadreId();
                if(cadrePost.getCadreId()==null){
                    cadrePost.setCadreId(cp.getCadreId());
                }
            }
            updateByPrimaryKeySelective(record,oldCadreId,cadrePost,true);
           /* }*/
        } else {
            insertSelective(record,cadrePost);
            addCount++;
        }
        return addCount;
    }

    @Transactional
    public int bacthImport(List<UnitPost> records) {

        int addCount = 0;
        for (UnitPost record : records) {
            String name = record.getName();
            Integer unitId = record.getUnitId();
            Byte status = 1;
            UnitPostExample example = new UnitPostExample();
            example.createCriteria().andNameEqualTo(name).andUnitIdEqualTo(unitId).andStatusEqualTo(status);
            List<UnitPost> unitPosts = unitPostMapper.selectByExample(example);
            if (unitPosts.size() == 0) {
                /*insertSelective(record);*/
                addCount++;
            } else {
                /*updateByPrimaryKeySelective(record);*/
            }
        }

        return addCount;
    }

    //原来的按岗位编码批量导入岗位信息
    @Transactional
    public int bacthImportByCode(List<UnitPost> records) {

        int addCount = 0;
        for (UnitPost record : records) {
            String code = record.getCode();
            UnitPost unitPost = getByCode(code);
            if (unitPost == null) {
                /*insertSelective(record);*/
                addCount++;
            } else {
                record.setId(unitPost.getId());
                /*updateByPrimaryKeySelective(record);*/
            }
        }

        return addCount;
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        UnitPostExample example = new UnitPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitPostMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(UnitPost record,Integer oldCadreId,CadrePost cadrePost, boolean isSync) {

        if (record.getCode() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");
        Assert.isTrue(!leaderTypeDuplicate(record.getId(), record.getUnitId(), record.getLeaderType()), "leaderType duplicate");

         unitPostMapper.updateByPrimaryKeySelective(record);

         syncCadrePost(record,oldCadreId,cadrePost, isSync);

    }

    //同步关联干部任职信息
    @Transactional
    public void syncCadrePost(UnitPost unitPost,Integer oldCadreId,CadrePost record, Boolean isSync) {
        Integer newCadreId=record.getCadreId();
       // Boolean isMainPost=record.getIsMainPost();
        Boolean isFirstMainPost=record.getIsFirstMainPost();
        record.setUnitPostId(unitPost.getId());

        if(BooleanUtils.isTrue(isSync)){
            record.setPost(unitPost.getName());
            if(record.getAdminLevel()==null){
                record.setAdminLevel(unitPost.getAdminLevel());
            }
        }

        CadrePost cadrePost=cadrePostService.getByUnitPostId(unitPost.getId());

        if(newCadreId==null){
            if(oldCadreId!=null) { //更换了关联干部,清空原干部关联
                commonMapper.excuteSql("update cadre_post set unit_post_id=null where id=" + cadrePost.getId());
            }
        }else if(newCadreId.equals(oldCadreId)){
            record.setId(cadrePost.getId());
            cadrePostService.updateByPrimaryKeySelective(record, isSync);
        }else if(!newCadreId.equals(oldCadreId)){
            if(oldCadreId!=null){ //更换了关联干部,清空原干部关联
                commonMapper.excuteSql("update cadre_post set unit_post_id=null where id=" + cadrePost.getId());
            }

            CadrePost mainCadrePost=cadrePostService.getFirstMainCadrePost(newCadreId);
            if((BooleanUtils.isTrue(isFirstMainPost)||isFirstMainPost==null)&&mainCadrePost!=null){  //关联岗位更换干部，更新第一主职
                record.setId(mainCadrePost.getId());
                cadrePostService.updateByPrimaryKeySelective(record, isSync);

                /*if(record.getNpWorkTime()==null&&record.getLpWorkTime()==null){
                    commonMapper.excuteSql("update cadre_post set np_dispatch_id=null, " +
                        "lp_dispatch_id=null, np_work_time=null, lp_work_time=null where id=" + mainCadrePost.getId());
                }*/
            }else{
                if(record.getIsMainPost()==null){
                    record.setIsMainPost(true);
                }
                if(record.getIsFirstMainPost()==null){
                    record.setIsFirstMainPost(true);
                }
                cadrePostService.insertSelective(record);//无岗位信息，插入
            }
        }
    }

    public List<UnitPostView> findAll() {

        UnitPostViewExample example = new UnitPostViewExample();
        example.setOrderByClause("status asc, unit_status asc, unit_sort_order asc, sort_order asc");

        return unitPostViewMapper.selectByExample(example);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        UnitPost entity = unitPostMapper.selectByPrimaryKey(id);
        changeOrder("unit_post", String.format("unit_id=%s and status=%s", entity.getUnitId(), entity.getStatus()), ORDER_BY_ASC, id, addNum);
    }

    @Transactional
    public void abolish(Integer[] ids, Date abolishDate) {

        for (Integer id : ids) {

            UnitPost record = new UnitPost();
            record.setId(id);
            record.setAbolishDate(abolishDate);
            record.setStatus(SystemConstants.UNIT_POST_STATUS_ABOLISH);

            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
            record.setSortOrder(getNextSortOrder("unit_post",
                    String.format("unit_id=%s and status=%s", unitPost.getUnitId(), SystemConstants.UNIT_POST_STATUS_ABOLISH)));

            unitPostMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void unabolish(Integer[] ids) {

        for (Integer id : ids) {
            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
            int sortOrder = getNextSortOrder("unit_post",
                    String.format("unit_id=%s and status=%s", unitPost.getUnitId(), SystemConstants.UNIT_POST_STATUS_NORMAL));

            commonMapper.excuteSql(String.format("update unit_post set abolish_date=null, status=%s, sort_order=%s where id=%s",
                    SystemConstants.UNIT_POST_STATUS_NORMAL, sortOrder, id));
        }
    }

    @Transactional
    public void sortByCode(Integer unitId, boolean asc) {

        UnitPostExample example = new UnitPostExample();
        UnitPostExample.Criteria criteria =
                example.createCriteria().andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL);
        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        example.setOrderByClause("code " + (asc ? "asc" : "desc"));
        List<UnitPost> unitPosts = unitPostMapper.selectByExample(example);
        int sortOrder = 1;
        for (UnitPost unitPost : unitPosts) {
            UnitPost record = new UnitPost();
            record.setId(unitPost.getId());
            record.setSortOrder(sortOrder++);
            unitPostMapper.updateByPrimaryKeySelective(record);
        }
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
            cell.setCellValue(metaTypeService.getName(record.getPostType()));

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getAdminLevel()));

            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(dispatch.getMeetingTime(), DateUtils.YYYY_MM_DD));

            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(dispatch.getPubTime(), DateUtils.YYYY_MM_DD));
        }

        ExportHelper.output(wb, unitPost.getName() + "岗位历史任职干部.xlsx", response);
    }

    // 空缺或兼职岗位导出
    public void exportOpenList(Byte displayType, UnitPostViewExample example, HttpServletResponse response) throws IOException {

        String filename = "岗位列表";
        if (displayType != null) {
            if (displayType == 1) {
                filename = "待补充的岗位列表";
            } else if (displayType == 2) {
                filename = "待调整的岗位列表";
            }
        }

        List<UnitPostView> unitPosts = unitPostViewMapper.selectByExample(example);

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/unit/unitPost_openList.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        String schoolName = CmTag.getSysConfig().getSchoolName();
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", schoolName + filename);
        cell.setCellValue(str);

        int startRow = 2;
        int rowCount = unitPosts.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);

        for (int i = 0; i < rowCount; i++) {

            UnitPostView record = unitPosts.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);

            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            cell = row.getCell(column++);
            cell.setCellValue(record.getName());

            cell = row.getCell(column++);
            cell.setCellValue(record.getUnitName());

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getUnitTypeId()));

            cell = row.getCell(column++);
            cell.setCellValue(record.getJob());

            cell = row.getCell(column++);
            cell.setCellValue(BooleanUtils.isTrue(record.getIsPrincipal()) ? "是" : "否");

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getAdminLevel()));

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getPostType()));

            cell = row.getCell(column++);
            cell.setCellValue(BooleanUtils.isTrue(record.getIsCpc()) ? "是" : "否");

            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(record.getOpenDate(), DateUtils.YYYY_MM_DD));

        }

        ExportHelper.output(wb, schoolName + filename + ".xlsx", response);
    }

    public void updateUnitPostRole(List<UnitPostView> records) {
        //行政班子负责人
        List<SysUserView> admins = sysUserService.findByRole(RoleConstants.ROLE_UNIT_ADMIN_XZ);
        //党委班子负责人
        List<SysUserView> partys = sysUserService.findByRole(RoleConstants.ROLE_UNIT_ADMIN_DW);

        for (SysUserView record: admins) {
            if (!records.contains(record)) {
                sysUserService.delRole(record.getUserId(), RoleConstants.ROLE_UNIT_ADMIN_XZ);
            }
        }
        for (SysUserView record: partys) {
            if (!records.contains(record)) {
                sysUserService.delRole(record.getUserId(), RoleConstants.ROLE_UNIT_ADMIN_DW);
            }
        }
        for (UnitPostView record: records) {
            CadreView cadre = record.getCadre();
            Byte leaderType = record.getLeaderType();
            if (cadre != null) {
                int userId = cadre.getUserId();
                if (leaderType == SystemConstants.UNIT_POST_LEADER_TYPE_DW) {
                    sysUserService.addRole(userId, RoleConstants.ROLE_UNIT_ADMIN_DW);
                } else if (leaderType == SystemConstants.UNIT_POST_LEADER_TYPE_XZ) {
                    sysUserService.addRole(userId, RoleConstants.ROLE_UNIT_ADMIN_XZ);
                }
            }
        }
    }

    // 查询班子负责人管理的单位
    public List<Integer> getAdminUnitIds(int cadreId){

        UnitPostViewExample example = new UnitPostViewExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andLeaderTypeIn(Arrays.asList(SystemConstants.UNIT_POST_LEADER_TYPE_DW,
                        SystemConstants.UNIT_POST_LEADER_TYPE_XZ));
        List<UnitPostView> unitPosts = unitPostViewMapper.selectByExample(example);

        return unitPosts.stream().map(UnitPostView::getUnitId).collect(Collectors.toList());
    }
}
