package service.cadre;

import bean.XlsCadre;
import controller.global.OpException;
import domain.abroad.Passport;
import domain.abroad.PassportExample;
import domain.cadre.Cadre;
import domain.cadre.CadreExample;
import domain.cadre.CadreParty;
import domain.cadre.CadrePartyExample;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.cadreInspect.CadreInspect;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import domain.unit.Unit;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadreInspect.CadreInspectService;
import service.cadreReserve.CadreReserveService;
import service.sys.SysUserService;
import service.unit.UnitService;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;
import sys.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CadreService extends BaseMapper {

    public static final String TABLE_NAME = "cadre";

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private CadreReserveService cadreReserveService;
    @Autowired
    private CadreInspectService cadreInspectService;
    @Autowired
    private CadreAdLogService cadreAdLogService;
    @Autowired
    protected MetaTypeService metaTypeService;

    // 根据CadreView.cadreDpType，获取干部的党派
    public String getCadreParty(Long cadreDpType){

        String partyName = null;// 党派
        if (NumberUtils.longEqual(cadreDpType, 0L)) {
            partyName = "中共党员";
        } else if (cadreDpType != null) {
            partyName = metaTypeService.getName(cadreDpType.intValue());
        }

        return partyName;
    }

    /*
        直接添加干部时执行的检查
     */
    public void directAddCheck(Integer id, int userId) {

        CadreExample example = new CadreExample();
        CadreExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId).andStatusIn(new ArrayList<Byte>(SystemConstants.CADRE_STATUS_SET));
        if (id != null) criteria.andIdNotEqualTo(id);
        long count = cadreMapper.countByExample(example);
        if (count > 0) {
            CadreView cadre = dbFindByUserId(userId);
            throw new OpException(cadre.getUser().getRealname()
                    + "已经在" + SystemConstants.CADRE_STATUS_MAP.get(cadre.getStatus()) + "中");
        }

        if (id == null && count == 0) { // 新添加干部的时候，判断一下是否在后备干部库或考察对象库中
            CadreView cadre = dbFindByUserId(userId);
            if (cadre != null) {
                Integer cadreId = cadre.getId();
                String realname = cadre.getUser().getRealname();
                if (cadreInspectService.getNormalRecord(cadreId) != null) {
                    throw new OpException(realname + "已经是考察对象");
                }

                if (cadreReserveService.getNormalRecord(cadreId) != null) {
                    throw new OpException(realname + "已经是后备干部");
                }

                // 此种情况应该不可能发生，上面的考察对象已经检查过了
                if (cadreReserveService.getFromTempRecord(cadreId) != null) {
                    throw new OpException(realname + "已经列为考察对象[后备干部]");
                }
            }
        }
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public byte leave(int id, String title, Integer dispatchCadreId) {

        Byte status = null;
        Cadre cadre = cadreMapper.selectByPrimaryKey(id);
        byte orgStatus = cadre.getStatus();
        if (orgStatus == SystemConstants.CADRE_STATUS_MIDDLE) {
            status = SystemConstants.CADRE_STATUS_MIDDLE_LEAVE;
        } else if (orgStatus == SystemConstants.CADRE_STATUS_LEADER) {
            status = SystemConstants.CADRE_STATUS_LEADER_LEAVE;
        }

        if (status == null) return -1;

        // 记录任免日志
        cadreAdLogService.addLog(id, "干部离任",
                SystemConstants.CADRE_AD_LOG_MODULE_CADRE, id);

        if (status == SystemConstants.CADRE_STATUS_MIDDLE_LEAVE) {

            /**2016.11.08
             *
             * 中层干部离任时，所有的在集中管理中的证件都移动到 取消集中管理证件库：
             *
             * 1、如果证件为“未借出”，就转移到“取消集中管理（未确认）”。
             * 2、如果证件“已借出”，直接转移到“取消集中管理证件（已确认）”中，最后一个字段“状态”为“免职前已领取”。
             同时借出记录的“实交组织部日期”为“已免职”。
             */
            {
                Passport record = new Passport();
                record.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL);
                record.setCancelType(AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_DISMISS);
                record.setCancelConfirm(false); // 未确认

                PassportExample example = new PassportExample();
                example.createCriteria().andCadreIdEqualTo(id).
                        andTypeEqualTo(AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP).andIsLentEqualTo(false);
                passportMapper.updateByExampleSelective(record, example);
            }
            {
                Passport record = new Passport();
                record.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL);
                record.setCancelType(AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_DISMISS);
                //record.setCancelPic(savePath);
                record.setCancelTime(new Date());
                record.setCancelConfirm(true); //已确认
                record.setCancelUserId(ShiroHelper.getCurrentUserId());
                record.setCancelRemark("在证件借出的情况下取消集中管理");

                PassportExample example = new PassportExample();
                example.createCriteria().andCadreIdEqualTo(id).
                        andTypeEqualTo(AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP).andIsLentEqualTo(true);
                passportMapper.updateByExampleSelective(record, example);
            }
        }

        Cadre record = new Cadre();
        record.setStatus(status);
        if (StringUtils.isNotBlank(title))
            record.setTitle(title);
        record.setDispatchCadreId(dispatchCadreId);
        record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + status));

        CadreExample example = new CadreExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(orgStatus);

        cadreMapper.updateByExampleSelective(record, example);

        return status;
    }

    // 重新任用， 离任->考察对象
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void re_assign(Integer[] ids) {

        for (Integer id : ids) {

            // 记录任免日志
            cadreAdLogService.addLog(id, "重新任用",
                    SystemConstants.CADRE_AD_LOG_MODULE_CADRE, id);

            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            int userId = cadre.getUserId();
            if (cadre.getStatus() != SystemConstants.CADRE_STATUS_MIDDLE_LEAVE
                    && cadre.getStatus() != SystemConstants.CADRE_STATUS_LEADER_LEAVE) {
                throw new IllegalArgumentException("干部[" + cadre.getUser().getRealname() + "]状态异常：" + cadre.getStatus());
            }

            // 添加考察对象角色
            sysUserService.addRole(userId, RoleConstants.ROLE_CADREINSPECT);

            // 检查
            cadreInspectService.directAddCheck(null, userId);

            // 添加到考察对象中
            CadreInspect record = new CadreInspect();
            record.setSortOrder(getNextSortOrder(CadreInspectService.TABLE_NAME, "status=" + SystemConstants.CADRE_INSPECT_STATUS_NORMAL));
            record.setCadreId(cadre.getId());
            record.setStatus(SystemConstants.CADRE_INSPECT_STATUS_NORMAL);
            record.setType(SystemConstants.CADRE_INSPECT_TYPE_DEFAULT);
            record.setRemark(SystemConstants.CADRE_STATUS_MAP.get(cadre.getStatus()) + "重新任用");
            cadreInspectMapper.insertSelective(record);
        }
    }

    public CadreView dbFindByUserId(int userId) {

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<CadreView> cadres = cadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (cadres.size() > 0) return cadres.get(0);

        return null;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    synchronized public void insertSelective(Cadre record) {

        int userId = record.getUserId();
        // 检查
        directAddCheck(null, userId);

        Assert.isTrue(record.getStatus() != null && record.getStatus() != SystemConstants.CADRE_STATUS_RESERVE
                && record.getStatus() != SystemConstants.CADRE_STATUS_INSPECT, "wrong status"); // 非后备干部、考察对象

        if(SystemConstants.CADRE_STATUS_SET.contains(record.getStatus())){
            // 添加干部身份
            sysUserService.addRole(userId, RoleConstants.ROLE_CADRE);
        }

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + record.getStatus()));
        CadreView cadre = dbFindByUserId(userId);
        if (cadre == null) {
            //if(record.getStatus()!=null)
            record.setIsCommitteeMember(BooleanUtils.isTrue(record.getIsCommitteeMember()));
            cadreMapper.insertSelective(record);
        } else {
            // 考察对象或后备干部被撤销时，干部信息仍然在库中，现在是覆盖更新
            record.setId(cadre.getId());
            cadreMapper.updateByPrimaryKeySelective(record);
        }

        // 记录任免日志
        cadreAdLogService.addLog(record.getId(), "添加干部",
                SystemConstants.CADRE_AD_LOG_MODULE_CADRE, record.getId());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int importCadres(final List<XlsCadre> cadres, byte status) {
        //int duplicate = 0;
        int success = 0;
        for (XlsCadre uRow : cadres) {

            Cadre record = new Cadre();
            String userCode = uRow.getUserCode();
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) throw new OpException("工作证号：" + userCode + "不存在");
            int userId = uv.getId();
            record.setUserId(userId);
            record.setStatus(status);
            record.setTypeId(uRow.getAdminLevel());
            record.setPostId(uRow.getPostId());
            Unit unit = unitService.findUnitByCode(uRow.getUnitCode());
            if (unit == null) {
                throw new OpException("单位编号：" + uRow.getUnitCode() + "不存在");
            }
            record.setUnitId(unit.getId());
            record.setPost(uRow.getPost());
            record.setTitle(uRow.getTitle());
            record.setRemark(uRow.getRemark());

            insertSelective(record);
            success++;
        }
        return success;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            Assert.isTrue(cadre.getStatus() != SystemConstants.CADRE_STATUS_RESERVE
                    && cadre.getStatus() != SystemConstants.CADRE_STATUS_INSPECT, "wrong status"); // 非后备干部、考察对象

            cadreMapper.deleteByPrimaryKey(id);

            // 删除干部身份
            sysUserService.delRole(cadre.getUserId(), RoleConstants.ROLE_CADRE);
        }
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void addOrUPdateCadreParty(CadreParty record) {

        if (record.getId() == null)
            cadrePartyMapper.insertSelective(record);
        else
            cadrePartyMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void cadreParty_batchDel(Integer[] ids) {

        for (Integer id : ids) {
            CadreParty cadreParty = cadrePartyMapper.selectByPrimaryKey(id);
            // 记录任免日志
            cadreAdLogService.addLog(id, "删除干部党派：" + JSONUtils.toString(cadreParty, false),
                    SystemConstants.CADRE_AD_LOG_MODULE_CADRE, id);
        }

        CadrePartyExample example = new CadrePartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePartyMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int updateByPrimaryKeySelective(Cadre record) {
        return cadreMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int updateByExampleSelective(Cadre record, CadreExample example) {

        return cadreMapper.updateByExampleSelective(record, example);
    }

    // 干部列表（包含后备干部、考察对象）
    @Cacheable(value = "Cadre:ALL")
    public Map<Integer, CadreView> findAll() {

        Set<Byte> cadreStatusSet = new HashSet<>();
        cadreStatusSet.addAll(SystemConstants.CADRE_STATUS_SET);
        cadreStatusSet.add(SystemConstants.CADRE_STATUS_RESERVE);
        cadreStatusSet.add(SystemConstants.CADRE_STATUS_INSPECT);

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andStatusIn(new ArrayList<>(cadreStatusSet));
        example.setOrderByClause("sort_order desc");
        List<CadreView> cadrees = cadreViewMapper.selectByExample(example);
        Map<Integer, CadreView> map = new LinkedHashMap<>();
        for (CadreView cadre : cadrees) {
            map.put(cadre.getId(), cadre);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = id,
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "Cadre:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        Cadre entity = cadreMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        byte status = entity.getStatus();

        CadreExample example = new CadreExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(status).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andStatusEqualTo(status).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Cadre> overEntities = cadreMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            Cadre targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder(TABLE_NAME, "status=" + status, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(TABLE_NAME, "status=" + status, baseSortOrder, targetEntity.getSortOrder());

            Cadre record = new Cadre();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    @CacheEvict(value = "Cadre:ALL", allEntries = true)
    public void updateWorkTime(int userId, Date _workTime) {

        // 修改参加工作时间
        TeacherInfo record = new TeacherInfo();
        record.setUserId(userId);
        record.setWorkTime(_workTime);
        teacherInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @CacheEvict(value = "Cadre:ALL", allEntries = true)
    public void updateTitle(int cadreId, String title) {

        // 修改所在单位及职务
        Cadre record = new Cadre();
        record.setId(cadreId);
        record.setTitle(title);
        cadreMapper.updateByPrimaryKeySelective(record);
    }

    // 常委数量
    public int countCommitteeMember(){

        CadreExample example = new CadreExample();
        example.createCriteria().andIsCommitteeMemberEqualTo(true);

        return (int)cadreMapper.countByExample(example);
    }
}
