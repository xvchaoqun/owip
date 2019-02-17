package service.cadreInspect;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cadreInspect.CadreInspect;
import domain.cadreInspect.CadreInspectExample;
import domain.cadreReserve.CadreReserve;
import domain.modify.ModifyCadreAuth;
import domain.sys.SysUserView;
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
import service.modify.ModifyCadreAuthService;
import service.sys.SysUserService;
import sys.constants.CadreConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.List;

@Service
public class CadreInspectService extends BaseMapper {

    public static final String TABLE_NAME = "cadre_inspect";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreReserveService cadreReserveService;
    @Autowired
    private CadreAdLogService cadreAdLogService;
    @Autowired(required = false)
    protected ModifyCadreAuthService modifyCadreAuthService;

    // 直接添加考察对象时执行检查
    public void directAddCheck(Integer id, int userId) {

        // 不在干部库中，肯定可以添加
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre == null) return;

        int cadreId = cadre.getId();
        String realname = cadre.getUser().getRealname();

        /*if(cadre.getStatus()==CadreConstants.CADRE_STATUS_NOW||
                cadre.getStatus()==CadreConstants.CADRE_STATUS_LEAVE||
                cadre.getStatus()==CadreConstants.CADRE_STATUS_LEADER_LEAVE){
            throw new OpException(realname + "已经在"
                    + CadreConstants.CADRE_STATUS_MAP.get(cadre.getStatus()) + "中");
        }*/

        // 本库检查
        CadreInspectExample example = new CadreInspectExample();
        CadreInspectExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadre.getId())
                .andStatusEqualTo(CadreConstants.CADRE_INSPECT_STATUS_NORMAL);
        if (id != null) criteria.andIdNotEqualTo(id);

        if(cadreInspectMapper.countByExample(example) > 0){
            throw new OpException(realname + "已经是考察对象");
        }

        // 后备干部库检查
        if(cadreReserveService.getNormalRecord(cadreId)!=null
                ||cadreReserveService.getFromTempRecord(cadreId)!=null){
            throw new OpException(realname + "已经是后备干部");
        }
    }

    // 至多有一条正常状态的考察对象，否则抛出异常
    public CadreInspect getNormalRecord(int cadreId){

        CadreInspectExample example = new CadreInspectExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(CadreConstants.CADRE_INSPECT_STATUS_NORMAL);
        List<CadreInspect> cadreInspects = cadreInspectMapper.selectByExample(example);
        if(cadreInspects.size()>1){
            CadreInspect cadreInspect = cadreInspects.get(0);
            CadreView cadre = iCadreMapper.getCadre(cadreInspect.getCadreId());
            throw new OpException("考察对象"+cadre.getUser().getRealname() +"状态异常，存在多条记录");
        }

        return (cadreInspects.size()==0)?null:cadreInspects.get(0);
    }

    /**
     * 直接添加考察对象
     * 返回: true 添加  false 更新
     */
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public boolean insertOrUpdateSelective(int userId, CadreInspect record, Cadre cadreRecord) {

        CadreView cadre = cadreService.dbFindByUserId(userId);
        // 检查
        if(cadre!=null) {
            int cadreId = cadre.getId();
            // 后备干部库检查
            if (cadreReserveService.getNormalRecord(cadreId) != null) {
                throw new OpException(cadre.getRealname() + "已经是后备干部");
            }
        }

        // 添加考察对象角色
        sysUserService.addRole(userId, RoleConstants.ROLE_CADREINSPECT);

        SysUserView uv = sysUserService.findById(userId);
        if(CmTag.hasRole(uv.getUsername(), RoleConstants.ROLE_CADRERECRUIT)){
            sysUserService.delRole(userId, RoleConstants.ROLE_CADREINSPECT);
        }

        Integer cadreId = null;
        {

            if (cadre == null) { // 不在干部库的情况

                if (cadreRecord == null) cadreRecord = new Cadre();
                // 先添加到干部库（类型：考察对象）
                cadreRecord.setId(null); // 防止误传ID过来
                cadreRecord.setUserId(userId);
                cadreRecord.setStatus(CadreConstants.CADRE_STATUS_INSPECT);
                cadreMapper.insertSelective(cadreRecord);

                cadreId = cadreRecord.getId();
            } else {
                cadreId = cadre.getId();

                // 已经在干部库中的情况，如果后备干部[非干部]撤销了，需要更新为考察对象
                if(cadre.getStatus()== CadreConstants.CADRE_STATUS_NOT_CADRE
                    || cadre.getStatus()== CadreConstants.CADRE_STATUS_RECRUIT
                    || cadre.getStatus()== CadreConstants.CADRE_STATUS_RESERVE) {
                    cadreRecord = new Cadre();
                    cadreRecord.setId(cadreId);
                    cadreRecord.setStatus(CadreConstants.CADRE_STATUS_INSPECT);
                    cadreMapper.updateByPrimaryKeySelective(cadreRecord);
                }
            }
        }

        CadreInspect normalRecord = getNormalRecord(cadreId);
        if(normalRecord==null) {
            record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + CadreConstants.CADRE_INSPECT_STATUS_NORMAL
                    + " and type=" + CadreConstants.CADRE_INSPECT_TYPE_DEFAULT));
            record.setCadreId(cadreId);
            record.setStatus(CadreConstants.CADRE_INSPECT_STATUS_NORMAL);
            record.setType(CadreConstants.CADRE_INSPECT_TYPE_DEFAULT);
            cadreInspectMapper.insertSelective(record);
        }else{
            record.setId(normalRecord.getId());
            record.setType(CadreConstants.CADRE_INSPECT_TYPE_DEFAULT);
            cadreInspectMapper.updateByPrimaryKeySelective(record);
        }

        // 记录任免日志
        cadreAdLogService.addLog(cadreId, normalRecord==null?"添加考察对象":"更新考察对象",
                CadreConstants.CADRE_AD_LOG_MODULE_INSPECT, record.getId());

        return normalRecord==null;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void updateByPrimaryKeySelective(CadreInspect record, Cadre cadreRecord) {

        CadreInspect cadreInspect = cadreInspectMapper.selectByPrimaryKey(record.getId());
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreInspect.getCadreId());
        if (cadre.getStatus()== CadreConstants.CADRE_STATUS_NOT_CADRE
                || cadre.getStatus()== CadreConstants.CADRE_STATUS_RECRUIT
                || cadre.getStatus() == CadreConstants.CADRE_STATUS_INSPECT) {

            cadreRecord.setId(cadre.getId());
            cadreRecord.setUserId(null);
            cadreRecord.setStatus(CadreConstants.CADRE_STATUS_INSPECT);
            cadreService.updateByPrimaryKeySelective(cadreRecord);
        }

        record.setStatus(cadreInspect.getStatus());
        cadreInspectMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int batchImport(final List<Cadre> records) {

        int addCount = 0;
        for (Cadre record : records) {

            int userId = record.getUserId();
            CadreInspect cadreInspect = new CadreInspect();
            cadreInspect.setStatus(CadreConstants.CADRE_INSPECT_STATUS_NORMAL);
            cadreInspect.setRemark(record.getRemark());
            record.setRemark(null);

            if(insertOrUpdateSelective(userId, cadreInspect, record))
                addCount++;
        }

        return addCount;
    }

   /* @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            CadreInspect cadreInspect = cadreInspectMapper.selectByPrimaryKey(id);
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreInspect.getCadreId());
            if(cadre.getStatus()==CadreConstants.CADRE_STATUS_INSPECT){
                cadreMapper.deleteByPrimaryKey(cadre.getId());
            }

            if(cadreInspect.getStatus()==CadreConstants.CADRE_INSPECT_STATUS_NORMAL){

                SysUserView uv = sysUserService.findById(cadre.getUserId());
                // 删除考核对象角色
                sysUserService.delRole(uv.getId(), RoleConstants.ROLE_CADREINSPECT, uv.getUsername(), uv.getCode());
            }
            cadreInspectMapper.deleteByPrimaryKey(id);
        }
    }*/

    // 通过常委会任命
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public Cadre pass(CadreInspect record, Cadre _cadre) {

        CadreInspect cadreInspect = cadreInspectMapper.selectByPrimaryKey(record.getId());
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreInspect.getCadreId());
        Integer cadreId = cadre.getId();
        if (cadreInspect.getStatus() != CadreConstants.CADRE_INSPECT_STATUS_NORMAL) {
            throw new IllegalArgumentException("[通过常委会任命]考察对象"
                    +cadre.getUser().getRealname()+"状态异常：" + cadreInspect.getStatus());
        }

        // 记录任免日志
        cadreAdLogService.addLog(cadreId, "通过常委会任命",
                CadreConstants.CADRE_AD_LOG_MODULE_INSPECT, cadreInspect.getId());

        Byte status = CadreConstants.CADRE_STATUS_MIDDLE;
        if(cadre.getStatus()==CadreConstants.CADRE_STATUS_LEADER_LEAVE){// 如果原来是离任校领导，重新任命之后也是离任校领导？
            status = CadreConstants.CADRE_STATUS_LEADER;
        }
        // 覆盖干部库
        _cadre.setId(cadreId);
        _cadre.setStatus(status);
        _cadre.setUserId(null);
        _cadre.setSortOrder(getNextSortOrder(CadreService.TABLE_NAME, "status=" + status));
        cadreService.updateByPrimaryKeySelective(_cadre);

        // 如果原来是后备干部发展过来的，此时肯定有一条记录在后备干部【列为考察对象列表中】，需要这条记录的状态更改为【后备干部已使用】
        {
            CadreReserve fromTempRecord = cadreReserveService.getFromTempRecord(cadreId);
            if(fromTempRecord!=null){
                fromTempRecord.setStatus(CadreConstants.CADRE_RESERVE_STATUS_ASSIGN);
                cadreReserveMapper.updateByPrimaryKeySelective(fromTempRecord);
            }
        }
        // 通过常委会任命
        record.setStatus(CadreConstants.CADRE_INSPECT_STATUS_ASSIGN);
        cadreInspectMapper.updateByPrimaryKeySelective(record);

        // 改变账号角色，考核对象->干部
        sysUserService.changeRole(cadre.getUserId(), RoleConstants.ROLE_CADREINSPECT, RoleConstants.ROLE_CADRE);

        // 删除直接修改信息的权限（如果有的话）
        if(modifyCadreAuthService!=null && cadre!=null){

            List<ModifyCadreAuth> modifyCadreAuths = modifyCadreAuthService.findAll(cadre.getId());
            List<Integer> idList = new ArrayList<>();
            for (ModifyCadreAuth modifyCadreAuth : modifyCadreAuths) {
                idList.add(modifyCadreAuth.getId());
            }
            modifyCadreAuthService.batchDel(idList.toArray(new Integer[]{}));
        }

        return cadreMapper.selectByPrimaryKey(cadre.getId());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void abolish(Integer id) {

        if (id == null) return;
        CadreInspect cadreInspect = cadreInspectMapper.selectByPrimaryKey(id);
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreInspect.getCadreId());
        int cadreId = cadre.getId();
        // 只有正常状态的考察对象，才可以撤销
        if(cadreInspect.getStatus() != CadreConstants.CADRE_INSPECT_STATUS_NORMAL){
            throw new IllegalArgumentException("考察对象"
                    +cadre.getUser().getRealname()+"状态异常:" + cadreInspect.getStatus());
        }

        // 记录任免日志
        cadreAdLogService.addLog(cadreId, "撤销考察对象",
                CadreConstants.CADRE_AD_LOG_MODULE_INSPECT, cadreInspect.getId());

        // 已撤销
        CadreInspect record = new CadreInspect();
        record.setId(id);
        record.setStatus(CadreConstants.CADRE_INSPECT_STATUS_ABOLISH);
        cadreInspectMapper.updateByPrimaryKeySelective(record);

        // 删除考核对象角色
        sysUserService.delRole(cadre.getUserId(), RoleConstants.ROLE_CADREINSPECT);

        // 如果原来是后备干部发展过来的，此时肯定有一条记录在后备干部【列为考察对象列表中】，需要将这条记录返回【后备干部库】
        {
            CadreReserve fromTempRecord = cadreReserveService.getFromTempRecord(cadreId);
            if(fromTempRecord!=null){
                fromTempRecord.setStatus(CadreConstants.CADRE_RESERVE_STATUS_NORMAL);
                cadreReserveMapper.updateByPrimaryKeySelective(fromTempRecord);
            }
        }

    }


    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;
        byte orderBy = ORDER_BY_ASC;
        CadreInspect entity = cadreInspectMapper.selectByPrimaryKey(id);
        byte type = entity.getType();
        // 只对正常状态进行排序
        Assert.isTrue(entity.getStatus() == CadreConstants.CADRE_INSPECT_STATUS_NORMAL);

        Integer baseSortOrder = entity.getSortOrder();

        CadreInspectExample example = new CadreInspectExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andStatusEqualTo(CadreConstants.CADRE_INSPECT_STATUS_NORMAL)
                    .andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andStatusEqualTo(CadreConstants.CADRE_INSPECT_STATUS_NORMAL)
                    .andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreInspect> overEntities = cadreInspectMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CadreInspect targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder(TABLE_NAME,
                        "status=" + CadreConstants.CADRE_INSPECT_STATUS_NORMAL + " and type=" + type,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(TABLE_NAME,
                        "status=" + CadreConstants.CADRE_INSPECT_STATUS_NORMAL + " and type=" + type,
                        baseSortOrder, targetEntity.getSortOrder());

            CadreInspect record = new CadreInspect();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreInspectMapper.updateByPrimaryKeySelective(record);
        }
    }
}
