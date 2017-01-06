package service.cadreTemp;

import bean.XlsCadreTemp;
import domain.cadre.Cadre;
import domain.cadreReserve.CadreReserve;
import domain.cadreTemp.CadreTemp;
import domain.cadreTemp.CadreTempExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.ibatis.session.RowBounds;
import org.eclipse.jdt.internal.core.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.cadre.CadreAdLogService;
import service.cadre.CadreService;
import service.cadreReserve.CadreReserveService;
import service.sys.SysUserService;
import service.unit.UnitService;
import sys.constants.SystemConstants;

import java.util.List;

@Service
public class CadreTempService extends BaseMapper {

    public static final String TABLE_NAME = "cadre_temp";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreReserveService cadreReserveService;
    @Autowired
    private CadreAdLogService cadreAdLogService;
    @Autowired
    private UnitService unitService;

    // 直接添加考察对象时执行检查
    public void directAddCheck(Integer id, int userId) {

        // 不在干部库中，肯定可以添加
        Cadre cadre = cadreService.dbFindByUserId(userId);
        if (cadre == null) return;

        int cadreId = cadre.getId();
        String realname = cadre.getUser().getRealname();

        /*if(cadre.getStatus()==SystemConstants.CADRE_STATUS_NOW||
                cadre.getStatus()==SystemConstants.CADRE_STATUS_LEAVE||
                cadre.getStatus()==SystemConstants.CADRE_STATUS_LEADER_LEAVE){
            throw new RuntimeException(realname + "已经在"
                    + SystemConstants.CADRE_STATUS_MAP.get(cadre.getStatus()) + "中");
        }*/

        // 本库检查
        CadreTempExample example = new CadreTempExample();
        CadreTempExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadre.getId())
                .andStatusEqualTo(SystemConstants.CADRE_TEMP_STATUS_NORMAL);
        if (id != null) criteria.andIdNotEqualTo(id);

        if(cadreTempMapper.countByExample(example) > 0){
            throw new RuntimeException(realname + "已经是考察对象");
        }

        // 后备干部库检查
        if(cadreReserveService.getNormalRecord(cadreId)!=null
                ||cadreReserveService.getFromTempRecord(cadreId)!=null){
            throw new RuntimeException(realname + "已经是后备干部");
        }
    }

    // 至多有一条正常状态的考察对象，否则抛出异常
    public CadreTemp getNormalRecord(int cadreId){

        CadreTempExample example = new CadreTempExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.CADRE_TEMP_STATUS_NORMAL);
        List<CadreTemp> cadreTemps = cadreTempMapper.selectByExample(example);
        if(cadreTemps.size()>1){
            CadreTemp cadreTemp = cadreTemps.get(0);
            Cadre cadre = cadreService.findAll().get(cadreTemp.getCadreId());
            throw new IllegalArgumentException("考察对象"+cadre.getUser().getRealname()
                    +"状态异常，存在多条记录");
        }

        return (cadreTemps.size()==0)?null:cadreTemps.get(0);
    }

    // 直接添加考察对象
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void insertSelective(int userId, CadreTemp record, Cadre cadreRecord) {

        // 检查
        directAddCheck(record.getId(), userId);

        SysUserView uv = sysUserService.findById(userId);
        // 添加考察对象角色
        sysUserService.addRole(uv.getId(), SystemConstants.ROLE_CADRETEMP, uv.getUsername(), uv.getCode());

        Integer cadreId = null;
        {
            Cadre cadre = cadreService.dbFindByUserId(userId);
            if (cadre == null) { // 不在干部库的情况

                if (cadreRecord == null) cadreRecord = new Cadre();
                // 先添加到干部库（类型：考察对象）
                cadreRecord.setId(null); // 防止误传ID过来
                cadreRecord.setUserId(userId);
                cadreRecord.setIsDp(false);
                cadreRecord.setStatus(SystemConstants.CADRE_STATUS_TEMP);
                cadreMapper.insertSelective(cadreRecord);

                cadreId = cadreRecord.getId();
            } else {
                cadreId = cadre.getId();

                // 已经在干部库中的情况，如果后备干部[非干部]撤销了，需要更新为考察对象
                if(cadre.getStatus()==SystemConstants.CADRE_STATUS_RESERVE) {
                    cadreRecord = new Cadre();
                    cadreRecord.setId(cadreId);
                    cadreRecord.setStatus(SystemConstants.CADRE_STATUS_TEMP);
                    cadreMapper.updateByPrimaryKeySelective(cadreRecord);
                }
            }
        }

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + SystemConstants.CADRE_TEMP_STATUS_NORMAL
                + " and type=" + SystemConstants.CADRE_TEMP_TYPE_DEFAULT));
        record.setCadreId(cadreId);
        record.setStatus(SystemConstants.CADRE_TEMP_STATUS_NORMAL);
        record.setType(SystemConstants.CADRE_TEMP_TYPE_DEFAULT);
        cadreTempMapper.insertSelective(record);

        // 记录任免日志
        cadreAdLogService.addLog(cadreId, "添加考察对象",
                SystemConstants.CADRE_AD_LOG_MODULE_TEMP, record.getId());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void updateByPrimaryKeySelective(CadreTemp record, Cadre cadreRecord) {

        CadreTemp cadreTemp = cadreTempMapper.selectByPrimaryKey(record.getId());
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreTemp.getCadreId());
        if (cadre.getStatus() == SystemConstants.CADRE_STATUS_TEMP) {

            cadreRecord.setId(cadre.getId());
            cadreRecord.setUserId(null);
            cadreRecord.setStatus(cadre.getStatus());
            cadreService.updateByPrimaryKeySelective(cadreRecord);
        }

        record.setStatus(cadreTemp.getStatus());
        cadreTempMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int importCadreTemps(final List<XlsCadreTemp> beans) {

        int success = 0;
        for (XlsCadreTemp uRow : beans) {

            Cadre cadreRecord = new Cadre();
            String userCode = uRow.getUserCode();
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) throw new RuntimeException("工作证号：" + userCode + "不存在");
            int userId = uv.getId();
            cadreRecord.setUserId(userId);
            cadreRecord.setTypeId(uRow.getAdminLevel());
            cadreRecord.setPostId(uRow.getPostId());
            Unit unit = unitService.findUnitByCode(uRow.getUnitCode());
            if (unit == null) {
                throw new RuntimeException("单位编号：" + uRow.getUnitCode() + "不存在");
            }
            cadreRecord.setUnitId(unit.getId());
            //cadreRecord.setPost(uRow.getPost());
            cadreRecord.setTitle(uRow.getTitle());

            CadreTemp record = new CadreTemp();
            record.setStatus(SystemConstants.CADRE_TEMP_STATUS_NORMAL);
            record.setRemark(uRow.getTempRemark());

            insertSelective(userId, record, cadreRecord);
            success++;
        }

        return success;
    }

   /* @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            CadreTemp cadreTemp = cadreTempMapper.selectByPrimaryKey(id);
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreTemp.getCadreId());
            if(cadre.getStatus()==SystemConstants.CADRE_STATUS_TEMP){
                cadreMapper.deleteByPrimaryKey(cadre.getId());
            }

            if(cadreTemp.getStatus()==SystemConstants.CADRE_TEMP_STATUS_NORMAL){

                SysUserView uv = sysUserService.findById(cadre.getUserId());
                // 删除考核对象角色
                sysUserService.delRole(uv.getId(), SystemConstants.ROLE_CADRETEMP, uv.getUsername(), uv.getCode());
            }
            cadreTempMapper.deleteByPrimaryKey(id);
        }
    }*/

    // 通过常委会任命
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public Cadre pass(CadreTemp record, Cadre cadreRecord) {

        CadreTemp cadreTemp = cadreTempMapper.selectByPrimaryKey(record.getId());
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreTemp.getCadreId());
        Integer cadreId = cadre.getId();
        if (cadreTemp.getStatus() != SystemConstants.CADRE_TEMP_STATUS_NORMAL) {
            throw new IllegalArgumentException("[通过常委会任命]考察对象"
                    +cadre.getUser().getRealname()+"状态异常：" + cadreTemp.getStatus());
        }

        // 记录任免日志
        cadreAdLogService.addLog(cadreId, "通过常委会任命",
                SystemConstants.CADRE_AD_LOG_MODULE_TEMP, cadreTemp.getId());

        // 覆盖干部库
        cadreRecord.setId(cadreId);
        cadreRecord.setStatus(SystemConstants.CADRE_STATUS_NOW);
        cadreRecord.setUserId(null);
        cadreRecord.setSortOrder(getNextSortOrder(CadreService.TABLE_NAME, "status=" + SystemConstants.CADRE_STATUS_NOW));
        cadreService.updateByPrimaryKeySelective(cadreRecord);

        // 如果原来是后备干部发展过来的，此时肯定有一条记录在后备干部【列为考察对象列表中】，需要这条记录的状态更改为【后备干部已使用】
        {
            CadreReserve fromTempRecord = cadreReserveService.getFromTempRecord(cadreId);
            if(fromTempRecord!=null){
                fromTempRecord.setStatus(SystemConstants.CADRE_RESERVE_STATUS_ASSIGN);
                cadreReserveMapper.updateByPrimaryKeySelective(fromTempRecord);
            }
        }
        // 通过常委会任命
        record.setStatus(SystemConstants.CADRE_TEMP_STATUS_ASSIGN);
        cadreTempMapper.updateByPrimaryKeySelective(record);

        SysUserView uv = sysUserService.findById(cadre.getUserId());
        // 改变账号角色，考核对象->干部
        sysUserService.changeRole(uv.getId(), SystemConstants.ROLE_CADRETEMP,
                SystemConstants.ROLE_CADRE, uv.getUsername(), uv.getCode());

        return cadreMapper.selectByPrimaryKey(cadre.getId());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void abolish(Integer id) {

        if (id == null) return;
        CadreTemp cadreTemp = cadreTempMapper.selectByPrimaryKey(id);
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreTemp.getCadreId());
        int cadreId = cadre.getId();
        // 只有正常状态的考察对象，才可以撤销
        if(cadreTemp.getStatus() != SystemConstants.CADRE_TEMP_STATUS_NORMAL){
            throw new IllegalArgumentException("考察对象"
                    +cadre.getUser().getRealname()+"状态异常:" + cadreTemp.getStatus());
        }

        // 记录任免日志
        cadreAdLogService.addLog(cadreId, "撤销考察对象",
                SystemConstants.CADRE_AD_LOG_MODULE_TEMP, cadreTemp.getId());

        // 已撤销
        CadreTemp record = new CadreTemp();
        record.setId(id);
        record.setStatus(SystemConstants.CADRE_TEMP_STATUS_ABOLISH);
        cadreTempMapper.updateByPrimaryKeySelective(record);

        SysUserView uv = cadre.getUser();
        // 删除考核对象角色
        sysUserService.delRole(uv.getId(), SystemConstants.ROLE_CADRETEMP, uv.getUsername(), uv.getCode());

        // 如果原来是后备干部发展过来的，此时肯定有一条记录在后备干部【列为考察对象列表中】，需要将这条记录返回【后备干部库】
        {
            CadreReserve fromTempRecord = cadreReserveService.getFromTempRecord(cadreId);
            if(fromTempRecord!=null){
                fromTempRecord.setStatus(SystemConstants.CADRE_RESERVE_STATUS_NORMAL);
                cadreReserveMapper.updateByPrimaryKeySelective(fromTempRecord);
            }
        }

    }


    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CadreTemp entity = cadreTempMapper.selectByPrimaryKey(id);
        byte type = entity.getType();
        // 只对正常状态进行排序
        Assert.isTrue(entity.getStatus() == SystemConstants.CADRE_TEMP_STATUS_NORMAL);

        Integer baseSortOrder = entity.getSortOrder();

        CadreTempExample example = new CadreTempExample();
        if (addNum < 0) {

            example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_TEMP_STATUS_NORMAL)
                    .andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_TEMP_STATUS_NORMAL)
                    .andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreTemp> overEntities = cadreTempMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CadreTemp targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum < 0)
                commonMapper.downOrder(TABLE_NAME,
                        "status=" + SystemConstants.CADRE_TEMP_STATUS_NORMAL + " and type=" + type,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(TABLE_NAME,
                        "status=" + SystemConstants.CADRE_TEMP_STATUS_NORMAL + " and type=" + type,
                        baseSortOrder, targetEntity.getSortOrder());

            CadreTemp record = new CadreTemp();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreTempMapper.updateByPrimaryKeySelective(record);
        }
    }
}
