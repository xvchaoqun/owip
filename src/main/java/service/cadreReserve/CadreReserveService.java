package service.cadreReserve;

import bean.XlsCadreReserve;
import domain.cadre.Cadre;
import domain.cadreReserve.CadreReserve;
import domain.cadreReserve.CadreReserveExample;
import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.ibatis.session.RowBounds;
import org.eclipse.jdt.internal.core.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.cadre.CadreService;
import service.sys.SysUserService;
import service.unit.UnitService;
import sys.constants.SystemConstants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreReserveService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private UnitService unitService;

    public boolean idDuplicate(Integer id, int userId){

        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre == null) return false;

        CadreReserveExample example = new CadreReserveExample();
        CadreReserveExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadre.getId())
                .andStatusEqualTo(SystemConstants.CADRE_RESERVE_STATUS_NORMAL);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadreReserveMapper.countByExample(example) > 0;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "CadreReserve:ALL", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void insertSelective(int userId, CadreReserve record, Cadre cadreRecord){

        SysUserView uv = sysUserService.findById(userId);
        // 添加后备干部身份
        sysUserService.addRole(uv.getId(), SystemConstants.ROLE_CADRERESERVE, uv.getUsername(), uv.getCode());

        Integer cadreId = null;
        {
            Cadre cadre = cadreService.findByUserId(userId);
            if(cadre==null) { // 不在干部库的情况

                if(cadreRecord==null) cadreRecord = new Cadre();
                // 先添加到干部库（类型：后备干部）
                cadreRecord.setId(null); // 防止误传ID过来
                cadreRecord.setUserId(userId);
                cadreRecord.setIsDp(false);
                cadreRecord.setStatus(SystemConstants.CADRE_STATUS_RESERVE);
                cadreMapper.insertSelective(cadreRecord);

                cadreId = cadreRecord.getId();
            }else{
                cadreId = cadre.getId();

                // 已经在干部库中的情况，不进行任何操作
            }
        }

        Integer maxSortOrder = commonMapper.getMaxSortOrder("cadre_reserve",
                "status=" + SystemConstants.CADRE_RESERVE_STATUS_NORMAL);

        record.setSortOrder(maxSortOrder==null?1:maxSortOrder+1);
        record.setCadreId(cadreId);
        record.setStatus(SystemConstants.CADRE_RESERVE_STATUS_NORMAL);
        cadreReserveMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "CadreReserve:ALL", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void updateByPrimaryKeySelective(CadreReserve record, Cadre cadreRecord){

        CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(record.getId());
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreReserve.getCadreId());
        if(cadre.getStatus()==SystemConstants.CADRE_STATUS_RESERVE){
            // 此时不需要清除干部库的缓存，因为后备干部库不在干部库的缓存中
            cadreRecord.setId(cadre.getId());
            cadreService.updateByPrimaryKeySelective(cadreRecord);
        }

        cadreReserveMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "CadreReserve:ALL", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int importCadreReserves(final List<XlsCadreReserve> beans, byte reserveType) {

        int success = 0;
        for(XlsCadreReserve uRow: beans){

            Cadre cadreRecord = new Cadre();
            String userCode = uRow.getUserCode();
            SysUserView uv = sysUserService.findByCode(userCode);
            if(uv== null) throw  new RuntimeException("工作证号："+userCode+"不存在");
            cadreRecord.setUserId(uv.getId());
            cadreRecord.setTypeId(uRow.getAdminLevel());
            cadreRecord.setPostId(uRow.getPostId());
            Unit unit = unitService.findUnitByCode(uRow.getUnitCode());
            if(unit==null){
                throw  new RuntimeException("单位编号："+uRow.getUnitCode()+"不存在");
            }
            cadreRecord.setUnitId(unit.getId());
            cadreRecord.setPost(uRow.getPost());
            cadreRecord.setTitle(uRow.getTitle());

            CadreReserve record = new CadreReserve();
            record.setType(reserveType);
            record.setStatus(SystemConstants.CADRE_RESERVE_STATUS_NORMAL);
            record.setRemark(uRow.getReserveRemark());

            Integer cadreId = null;
            Cadre cadre = cadreService.findByUserId(uv.getId());
            // 添加干部到后备干部库的情况，只需要添加后备干部信息
            if(cadre!=null){

                cadreId = cadre.getId();
                // 如果原来存在后备干部信息，则更新（这种情况应该不存在，因为从后备干部库撤销的话，应该删除干部库里的信息）；否则保持不变
                if(cadre.getStatus()==SystemConstants.CADRE_STATUS_RESERVE){
                    cadreRecord.setId(cadreId);
                    cadreMapper.updateByPrimaryKeySelective(cadreRecord);
                }

            }else{ // 添加非干部库账号的情况，需要初始化干部库信息

                cadreRecord.setIsDp(false);
                cadreRecord.setStatus(SystemConstants.CADRE_STATUS_RESERVE);
                cadreMapper.insertSelective(cadreRecord);

                cadreId = cadreRecord.getId();
            }

            CadreReserveExample example = new CadreReserveExample();
            example.createCriteria().andCadreIdEqualTo(cadreId)
                    .andStatusEqualTo(SystemConstants.CADRE_RESERVE_STATUS_NORMAL);
            if(cadreReserveMapper.countByExample(example)>0){
                throw  new RuntimeException("工作证号："+userCode+"已经在后备干部库中");
            }

            record.setSortOrder(getNextSortOrder("cadre_reserve","status=" + SystemConstants.CADRE_RESERVE_STATUS_NORMAL));
            record.setCadreId(cadreId);
            cadreReserveMapper.insertSelective(record);

            // 添加后备干部身份
            sysUserService.addRole(uv.getId(), SystemConstants.ROLE_CADRERESERVE, uv.getUsername(), uv.getCode());

            success++;
        }

        return success;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "CadreReserve:ALL", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreReserve.getCadreId());
            if(cadre.getStatus()==SystemConstants.CADRE_STATUS_RESERVE){
                cadreMapper.deleteByPrimaryKey(cadre.getId());
            }
            cadreReserveMapper.deleteByPrimaryKey(id);
        }
    }

    // 后备干部缓存列表
    @Cacheable(value="CadreReserve:ALL")
    public Map<Integer, CadreReserveView> findAll() {

        CadreReserveViewExample example = new CadreReserveViewExample();
        example.setOrderByClause("sort_order desc");
        List<CadreReserveView> cadreReserveViews = cadreReserveViewMapper.selectByExample(example);
        Map<Integer, CadreReserveView> map = new LinkedHashMap<>();
        for (CadreReserveView cadreReserveView : cadreReserveViews) {
            map.put(cadreReserveView.getReserveId(), cadreReserveView);
        }
        return map;
    }

    @Transactional
    @CacheEvict(value = "CadreReserve:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CadreReserve entity = cadreReserveMapper.selectByPrimaryKey(id);
        byte reserveType = entity.getType();
        // 只对后备干部正常状态进行排序
        Assert.isTrue(entity.getStatus()==SystemConstants.CADRE_RESERVE_STATUS_NORMAL);

        Integer baseSortOrder = entity.getSortOrder();

        CadreReserveExample example = new CadreReserveExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_RESERVE_STATUS_NORMAL)
                    .andTypeEqualTo(reserveType).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

                example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_RESERVE_STATUS_NORMAL)
                    .andTypeEqualTo(reserveType).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreReserve> overEntities = cadreReserveMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreReserve targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cadre_reserve",
                        "status=" + SystemConstants.CADRE_RESERVE_STATUS_NORMAL + " and type="+reserveType,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_reserve",
                        "status=" + SystemConstants.CADRE_RESERVE_STATUS_NORMAL + " and type="+reserveType,
                        baseSortOrder, targetEntity.getSortOrder());

            CadreReserve record = new CadreReserve();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreReserveMapper.updateByPrimaryKeySelective(record);
        }
    }
}
