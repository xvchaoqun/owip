package service.cadreReserve;

import bean.XlsCadreReserve;
import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cadreInspect.CadreInspect;
import domain.cadreReserve.CadreReserve;
import domain.cadreReserve.CadreReserveExample;
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
import service.cadreInspect.CadreInspectService;
import service.sys.SysUserService;
import service.unit.UnitService;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CadreReserveService extends BaseMapper {

    public static final String TABLE_NAME = "cadre_reserve";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreInspectService cadreInspectService;
    @Autowired
    private CadreAdLogService cadreAdLogService;
    @Autowired
    private UnitService unitService;

    // 直接添加后备干部时执行检查
    public void directAddCheck(Integer id, int userId){

        // 不在干部库中，肯定可以添加
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if(cadre == null) return;

        int cadreId = cadre.getId();
        String realname = cadre.getUser().getRealname();

        /*if(cadre.getStatus()==SystemConstants.CADRE_STATUS_NOW||
                cadre.getStatus()==SystemConstants.CADRE_STATUS_LEAVE||
                cadre.getStatus()==SystemConstants.CADRE_STATUS_LEADER_LEAVE){
            throw new OpException(realname + "已经在"
                    + SystemConstants.CADRE_STATUS_MAP.get(cadre.getStatus()) + "中");
        }*/

        // 本库检查
        CadreReserveExample example = new CadreReserveExample();
        CadreReserveExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusIn(Arrays.asList(SystemConstants.CADRE_RESERVE_STATUS_NORMAL
                        , SystemConstants.CADRE_RESERVE_STATUS_TO_INSPECT));
        if(id!=null) criteria.andIdNotEqualTo(id);

        List<CadreReserve> cadreReserves = cadreReserveMapper.selectByExample(example);
        if( cadreReserves.size() > 0){
            CadreReserve cadreReserve = cadreReserves.get(0);
            if(cadreReserve.getStatus()==SystemConstants.CADRE_RESERVE_STATUS_NORMAL) {
                throw new OpException(realname + "已经在"
                +SystemConstants.CADRE_RESERVE_TYPE_MAP.get(cadreReserve.getType()) + "中");
            }else if(cadreReserve.getStatus()==SystemConstants.CADRE_RESERVE_STATUS_TO_INSPECT){
                throw new OpException(realname + "已经列入考察对象");
            }
        }

        // 考察对象库检查
        CadreInspect normalRecord = cadreInspectService.getNormalRecord(cadreId);
        if(normalRecord!=null){
            throw new OpException(realname + "已经是考察对象");
        }
    }

    // 读取一下正常状态的记录
    public CadreReserve getNormalRecord(int cadreId){

        CadreReserveExample example = new CadreReserveExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.CADRE_RESERVE_STATUS_NORMAL);
        List<CadreReserve> cadreReserves = cadreReserveMapper.selectByExample(example);
        if(cadreReserves.size()>1){
            CadreReserve cadreReserve = cadreReserves.get(0);
            CadreView cadre = cadreService.findAll().get(cadreReserve.getCadreId());
            throw new IllegalArgumentException("后备干部"+cadre.getUser().getRealname()
                    +"状态异常，存在多条记录");
        }

        return (cadreReserves.size()==0)?null:cadreReserves.get(0);
    }

    // 至多有一条从考察对象过来的记录，否则抛出异常
    public CadreReserve getFromTempRecord(int cadreId){

        CadreReserveExample example = new CadreReserveExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.CADRE_RESERVE_STATUS_TO_INSPECT);
        List<CadreReserve> cadreReserves = cadreReserveMapper.selectByExample(example);
        if(cadreReserves.size()>1){
            CadreReserve cadreReserve = cadreReserves.get(0);
            CadreView cadre = cadreService.findAll().get(cadreReserve.getCadreId());
            throw new IllegalArgumentException("后备干部"+cadre.getUser().getRealname()
                    +"状态异常，存在多条后备干部[已列为考察对象]记录");
        }

        return (cadreReserves.size()==0)?null:cadreReserves.get(0);
    }

    // 直接添加后备干部
    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void insertSelective(int userId, CadreReserve record, Cadre cadreRecord){

        // 检查
        directAddCheck(record.getId(), userId);

        SysUserView uv = sysUserService.findById(userId);
        // 添加后备干部角色
        sysUserService.addRole(uv.getId(), SystemConstants.ROLE_CADRERESERVE, uv.getUsername(), uv.getCode());

        Integer cadreId = null;
        {
            CadreView cadre = cadreService.dbFindByUserId(userId);
            if(cadre==null) { // 不在干部库的情况

                if(cadreRecord==null) cadreRecord = new Cadre();
                // 先添加到干部库（类型：后备干部）
                cadreRecord.setId(null); // 防止误传ID过来
                cadreRecord.setUserId(userId);
                cadreRecord.setStatus(SystemConstants.CADRE_STATUS_RESERVE);
                cadreMapper.insertSelective(cadreRecord);

                cadreId = cadreRecord.getId();
            }else{
                cadreId = cadre.getId();

                // 经过了后备干部或考察对象[非干部]的撤销操作的情况，需要更新信息并放入后备干部库
                if(cadre.getStatus()==SystemConstants.CADRE_STATUS_INSPECT
                        || cadre.getStatus()==SystemConstants.CADRE_STATUS_RESERVE) {
                    cadreRecord.setId(cadreId);
                    cadreRecord.setStatus(SystemConstants.CADRE_STATUS_RESERVE);
                    cadreMapper.updateByPrimaryKeySelective(cadreRecord);
                }else{
                    // 现任干部库、离任干部库的情况 不更新干部信息
                }
            }
        }

        Assert.isNotNull(SystemConstants.CADRE_RESERVE_TYPE_MAP.get(record.getType())!=null);

        record.setSortOrder(getNextSortOrder(TABLE_NAME,
                "status=" + SystemConstants.CADRE_RESERVE_STATUS_NORMAL + " and type="+record.getType()));
        record.setCadreId(cadreId);
        record.setStatus(SystemConstants.CADRE_RESERVE_STATUS_NORMAL);
        cadreReserveMapper.insertSelective(record);

        // 记录任免日志
        cadreAdLogService.addLog(cadreId, "添加后备干部",
                SystemConstants.CADRE_AD_LOG_MODULE_RESERVE, record.getId());
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void updateByPrimaryKeySelective(CadreReserve record, Cadre cadreRecord){

        CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(record.getId());
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreReserve.getCadreId());
        if(cadre.getStatus()==SystemConstants.CADRE_STATUS_RESERVE){
            // 如果原来就在干部库中【后备干部】，则更新其中的信息
            cadreRecord.setId(cadre.getId());
            cadreRecord.setUserId(null);
            cadreRecord.setStatus(cadre.getStatus());
            cadreService.updateByPrimaryKeySelective(cadreRecord);
        }

        record.setStatus(cadreReserve.getStatus());
        cadreReserveMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int importCadreReserves(final List<XlsCadreReserve> beans, byte reserveType) {

        int success = 0;
        for(XlsCadreReserve uRow: beans){

            Cadre cadreRecord = new Cadre();
            String userCode = uRow.getUserCode();
            SysUserView uv = sysUserService.findByCode(userCode);
            if(uv== null) throw  new OpException("工作证号："+userCode+"不存在");
            int userId = uv.getId();
            cadreRecord.setUserId(userId);
            cadreRecord.setTypeId(uRow.getAdminLevel());
            cadreRecord.setPostId(uRow.getPostId());
            Unit unit = unitService.findUnitByCode(uRow.getUnitCode());
            if(unit==null){
                throw  new OpException("单位编号："+uRow.getUnitCode()+"不存在");
            }
            cadreRecord.setUnitId(unit.getId());
            cadreRecord.setPost(uRow.getPost());
            cadreRecord.setTitle(uRow.getTitle());

            CadreReserve record = new CadreReserve();
            record.setType(reserveType);
            record.setStatus(SystemConstants.CADRE_RESERVE_STATUS_NORMAL);
            record.setRemark(uRow.getReserveRemark());


            insertSelective(userId, record, cadreRecord);
            success++;
        }

        return success;
    }

    // 列为考察对象
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public Cadre pass(CadreReserve record, Cadre cadreRecord) {

        CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(record.getId());
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreReserve.getCadreId());
        int userId = cadre.getUserId();
        int cadreId = cadre.getId();
        if (cadreReserve.getStatus() != SystemConstants.CADRE_INSPECT_STATUS_NORMAL) {
            throw new IllegalArgumentException("[列为考察对象]后备干部"
                    +cadre.getUser().getRealname()+"状态异常:" + cadreReserve.getStatus());
        }

        // 记录任免日志
        cadreAdLogService.addLog(cadreId, "列为考察对象",
                SystemConstants.CADRE_AD_LOG_MODULE_RESERVE, cadreReserve.getId());

        cadreRecord.setId(cadreId);
        cadreRecord.setUserId(null); // 账号不变
        cadreRecord.setStatus(null); // 除了下面的情况，保持不变
        if(cadre.getStatus()==SystemConstants.CADRE_STATUS_RESERVE){
            // 如果原来是后备干部[非干部]，需要更新为考察对象
            cadreRecord.setStatus(SystemConstants.CADRE_STATUS_INSPECT);
        }
        cadreService.updateByPrimaryKeySelective(cadreRecord);

        // 已列为考察对象
        record.setStatus(SystemConstants.CADRE_RESERVE_STATUS_TO_INSPECT);
        cadreReserveMapper.updateByPrimaryKeySelective(record);

        SysUserView uv = sysUserService.findById(userId);
        // 改变账号角色，后备干部->考核对象
        sysUserService.changeRole(uv.getId(), SystemConstants.ROLE_CADRERESERVE,
                SystemConstants.ROLE_CADREINSPECT, uv.getUsername(), uv.getCode());

        // 检查
        //cadreInspectService.directAddCheck(null, userId);
        if(cadreInspectService.getNormalRecord(cadreId)!=null){
            throw new OpException(uv.getRealname() + "已经是考察对象");
        }
        // 添加到考察对象中
        CadreInspect _record = new CadreInspect();
        _record.setSortOrder(getNextSortOrder(CadreInspectService.TABLE_NAME,
                "status=" + SystemConstants.CADRE_INSPECT_STATUS_NORMAL
                        + " and type=" + SystemConstants.CADRE_INSPECT_TYPE_DEFAULT));
        _record.setCadreId(cadreId);
        _record.setStatus(SystemConstants.CADRE_INSPECT_STATUS_NORMAL);
        _record.setType(SystemConstants.CADRE_INSPECT_TYPE_DEFAULT);
        _record.setRemark(SystemConstants.CADRE_STATUS_MAP.get(cadre.getStatus()) + "列入考察对象");
        cadreInspectMapper.insertSelective(_record);

        return cadreMapper.selectByPrimaryKey(cadreId);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void abolish(Integer id) {

        if (id == null) return;
        CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreReserve.getCadreId());

        // 只有正常状态的后备干部，才可以撤销
        if(cadreReserve.getStatus() != SystemConstants.CADRE_RESERVE_STATUS_NORMAL){
            throw new IllegalArgumentException("后备干部"+cadre.getUser().getRealname()+"状态异常:" + cadreReserve.getStatus());
        }

        // 记录任免日志
        cadreAdLogService.addLog(cadre.getId(), "撤销后备干部",
                SystemConstants.CADRE_AD_LOG_MODULE_RESERVE, cadreReserve.getId());

        /*if (cadre.getStatus() == SystemConstants.CADRE_STATUS_RESERVE) {
            cadreMapper.deleteByPrimaryKey(cadre.getId());
        }*/

        SysUserView uv = cadre.getUser();
        // 删除后备干部角色
        sysUserService.delRole(uv.getId(), SystemConstants.ROLE_CADRERESERVE, uv.getUsername(), uv.getCode());

        CadreReserve record = new CadreReserve();
        record.setId(id);
        record.setStatus(SystemConstants.CADRE_RESERVE_STATUS_ABOLISH);
        cadreReserveMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreReserve.getCadreId());
            SysUserView uv = sysUserService.findById(cadre.getUserId());

            if(cadreReserve.getStatus()!=SystemConstants.CADRE_RESERVE_STATUS_ABOLISH){
                throw new OpException(uv.getRealname() + "不在已撤销后备干部库中，不可以删除");
            }

            cadreReserveMapper.deleteByPrimaryKey(id);

            // 记录任免日志
            cadreAdLogService.addLog(cadre.getId(), "删除已撤销后备干部", SystemConstants.CADRE_AD_LOG_MODULE_RESERVE, id);
        }
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CadreReserve entity = cadreReserveMapper.selectByPrimaryKey(id);
        byte type = entity.getType();
        // 只对后备干部正常状态进行排序
        Assert.isTrue(entity.getStatus()==SystemConstants.CADRE_RESERVE_STATUS_NORMAL);

        Integer baseSortOrder = entity.getSortOrder();

        CadreReserveExample example = new CadreReserveExample();
        if (addNum < 0) { // 正序

            example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_RESERVE_STATUS_NORMAL)
                    .andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

                example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_RESERVE_STATUS_NORMAL)
                    .andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreReserve> overEntities = cadreReserveMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreReserve targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum < 0) // 正序
                commonMapper.downOrder(TABLE_NAME,
                        "status=" + SystemConstants.CADRE_RESERVE_STATUS_NORMAL + " and type="+type,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(TABLE_NAME,
                        "status=" + SystemConstants.CADRE_RESERVE_STATUS_NORMAL + " and type="+type,
                        baseSortOrder, targetEntity.getSortOrder());

            CadreReserve record = new CadreReserve();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreReserveMapper.updateByPrimaryKeySelective(record);
        }
    }
}
