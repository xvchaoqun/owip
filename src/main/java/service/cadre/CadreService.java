package service.cadre;

import controller.global.OpException;
import domain.abroad.Passport;
import domain.abroad.PassportExample;
import domain.cadre.*;
import domain.cadreInspect.CadreInspect;
import domain.cm.CmMemberView;
import domain.modify.ModifyCadreAuth;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.abroad.PassportMapper;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadreInspect.CadreInspectService;
import service.cadreReserve.CadreReserveService;
import service.cm.CmMemberService;
import service.global.CacheHelper;
import service.modify.ModifyCadreAuthService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.HttpResponseMethod;
import sys.constants.*;
import sys.tags.CmTag;
import sys.utils.JSONUtils;

import java.util.*;

@Service
public class CadreService extends BaseMapper implements HttpResponseMethod {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String TABLE_NAME = "cadre";

    @Autowired(required = false)
    private PassportMapper passportMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CacheHelper cacheHelper;
    @Autowired
    private CadreReserveService cadreReserveService;
    @Autowired
    private CadreInspectService cadreInspectService;
    @Autowired
    private CadreAdLogService cadreAdLogService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired(required = false)
    protected ModifyCadreAuthService modifyCadreAuthService;
    @Autowired(required = false)
    protected CmMemberService cmMemberService;

    public CadreView getCadre(int id){
        return iCadreMapper.getCadre(id);
    }

    // 添加临时干部（无角色）
    @Transactional
    public Cadre addTempCadre(int userId) {

        Cadre record = new Cadre();
        record.setUserId(userId);
        record.setStatus(CadreConstants.CADRE_STATUS_NOT_CADRE);
        // 其他干部
        if (record.getType() == null) {
            record.setType(CadreConstants.CADRE_TYPE_OTHER);
        }
        insertSelective(record);

        return record;
    }

    /*
        直接添加干部时执行的检查
     */
    public void directAddCheck(Integer id, int userId) {

        CadreExample example = new CadreExample();
        CadreExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId).andStatusIn(new ArrayList<Byte>(CadreConstants.CADRE_STATUS_SET));
        if (id != null) criteria.andIdNotEqualTo(id);
        long count = cadreMapper.countByExample(example);
        if (count > 0) {
            CadreView cadre = dbFindByUserId(userId);
            throw new OpException(cadre.getUser().getRealname()
                    + "已经在" + CadreConstants.CADRE_STATUS_MAP.get(cadre.getStatus()) + "中");
        }

        if (id == null && count == 0) { // 新添加干部的时候，判断一下是否在优秀年轻干部库或考察对象库中
            CadreView cadre = dbFindByUserId(userId);
            if (cadre != null) {
                Integer cadreId = cadre.getId();
                String realname = cadre.getUser().getRealname();
                if (cadreInspectService.getNormalRecord(cadreId) != null) {
                    throw new OpException(realname + "已经是考察对象");
                }

                if (cadreReserveService.getNormalRecord(cadreId) != null) {
                    throw new OpException(realname + "已经是优秀年轻干部");
                }

                // 此种情况应该不可能发生，上面的考察对象已经检查过了
                if (cadreReserveService.getFromTempRecord(cadreId) != null) {
                    throw new OpException(realname + "已经列为考察对象[优秀年轻干部]");
                }
            }
        }
    }

    // 提任（处级干部->校领导）
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void promote(int id, String title) {

        Cadre record = new Cadre();
        record.setStatus(CadreConstants.CADRE_STATUS_LEADER);
        if (StringUtils.isNotBlank(title))
            record.setTitle(title);
        record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + CadreConstants.CADRE_STATUS_LEADER));

        CadreExample example = new CadreExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(CadreConstants.CADRE_STATUS_MIDDLE);

        cadreMapper.updateByExampleSelective(record, example);
    }

    // 离任
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public byte leave(int id, String title, Integer dispatchCadreId, Integer[] postIds) {

        Byte status = null;
        Cadre cadre = cadreMapper.selectByPrimaryKey(id);
        byte orgStatus = cadre.getStatus();
        if (orgStatus == CadreConstants.CADRE_STATUS_MIDDLE) {
            status = CadreConstants.CADRE_STATUS_MIDDLE_LEAVE;
        } else if (orgStatus == CadreConstants.CADRE_STATUS_LEADER) {
            status = CadreConstants.CADRE_STATUS_LEADER_LEAVE;
        }

        if (status == null) return -1;

        // 记录任免日志
        cadreAdLogService.addLog(id, "干部离任",
                CadreConstants.CADRE_AD_LOG_MODULE_CADRE, id);

        if (status == CadreConstants.CADRE_STATUS_MIDDLE_LEAVE && passportMapper != null) {

            /**2016.11.08
             *
             * 干部离任时，所有的在集中管理中的证件都移动到 取消集中管理证件库：
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

        // 清除岗位信息
        commonMapper.excuteSql("update cadre_post set unit_post_id=null where id in("
                + StringUtils.join(postIds, ",") + ")");

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
                    CadreConstants.CADRE_AD_LOG_MODULE_CADRE, id);

            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            int userId = cadre.getUserId();
            if (cadre.getStatus() != CadreConstants.CADRE_STATUS_MIDDLE_LEAVE
                    && cadre.getStatus() != CadreConstants.CADRE_STATUS_LEADER_LEAVE) {
                throw new OpException("干部[" + cadre.getUser().getRealname() + "]状态异常：" + cadre.getStatus());
            }

            // 添加考察对象角色
            sysUserService.addRole(userId, RoleConstants.ROLE_CADREINSPECT);

            // 检查
            cadreInspectService.directAddCheck(null, userId);

            // 添加到考察对象中
            CadreInspect record = new CadreInspect();
            record.setSortOrder(getNextSortOrder(CadreInspectService.TABLE_NAME, "status=" + CadreConstants.CADRE_INSPECT_STATUS_NORMAL));
            record.setCadreId(cadre.getId());
            record.setStatus(CadreConstants.CADRE_INSPECT_STATUS_NORMAL);
            record.setType(CadreConstants.CADRE_INSPECT_TYPE_DEFAULT);
            record.setRemark(CadreConstants.CADRE_STATUS_MAP.get(cadre.getStatus()) + "重新任用");
            cadreInspectMapper.insertSelective(record);
        }
    }

    public Cadre getByUserId(int userId) {
        CadreExample example = new CadreExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<Cadre> cadres = cadreMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (cadres.size() > 0) return cadres.get(0);
        return null;
    }

    public CadreView dbFindByUserId(int userId) {

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<CadreView> cadres = cadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (cadres.size() > 0) return cadres.get(0);

        return null;
    }

    // <userId, cadreView>
    public Map<Integer, CadreView> dbFindByUserIds(List<Integer> userIds) {

        Map<Integer, CadreView> cadreMap = new HashMap<>();
        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andUserIdIn(userIds);
        List<CadreView> cadres = cadreViewMapper.selectByExample(example);
        for (CadreView cadre : cadres) {
            cadreMap.put(cadre.getUserId(), cadre);
        }

        return cadreMap;
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

        Assert.isTrue(record.getStatus() != null && record.getStatus() != CadreConstants.CADRE_STATUS_RESERVE
                && record.getStatus() != CadreConstants.CADRE_STATUS_INSPECT, "wrong status"); // 非优秀年轻干部、考察对象

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + record.getStatus()));
        CadreView cadre = dbFindByUserId(userId);
        if (cadre == null) {
            //if(record.getStatus()!=null)
            cadreMapper.insertSelective(record);
        } else {
            // 考察对象或优秀年轻干部被撤销时，干部信息仍然在库中，现在是覆盖更新
            record.setId(cadre.getId());
            cadreMapper.updateByPrimaryKeySelective(record);
        }

        if (CadreConstants.CADRE_STATUS_SET.contains(record.getStatus())) {
            // 添加干部身份
            sysUserService.addRole(userId, RoleConstants.ROLE_CADRE);

            // 删除直接修改信息的权限（如果有的话）
            if (modifyCadreAuthService != null && cadre != null) {

                List<ModifyCadreAuth> modifyCadreAuths = modifyCadreAuthService.findAll(cadre.getId());
                List<Integer> idList = new ArrayList<>();
                for (ModifyCadreAuth modifyCadreAuth : modifyCadreAuths) {
                    idList.add(modifyCadreAuth.getId());
                }
                modifyCadreAuthService.batchDel(idList.toArray(new Integer[]{}));
            }
        }

        // 记录任免日志
        cadreAdLogService.addLog(record.getId(), "添加干部",
                CadreConstants.CADRE_AD_LOG_MODULE_CADRE, record.getId());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int batchImport(List<Cadre> records) {

        int addCount = 0;
        for (Cadre record : records) {

            int userId = record.getUserId();
            CadreView cv = dbFindByUserId(userId);
            if (cv == null) {
                // 默认是处级干部
                if (record.getType() == null) {
                    record.setType(CadreConstants.CADRE_TYPE_CJ);
                }
                insertSelective(record);
                addCount++;
            } else {
                record.setId(cv.getId());
                updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
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
            Assert.isTrue(cadre.getStatus() != CadreConstants.CADRE_STATUS_RESERVE
                    && cadre.getStatus() != CadreConstants.CADRE_STATUS_INSPECT, "wrong status"); // 非优秀年轻干部、考察对象

            cadreMapper.deleteByPrimaryKey(id);

            // 删除干部身份
            sysUserService.delRole(cadre.getUserId(), RoleConstants.ROLE_CADRE);

            SysUserView uv = cadre.getUser();
            logger.info(addLog(LogConstants.LOG_ADMIN,
                    "删除干部：id=%s, code=%s, realname=%s", id, uv.getCode(), uv.getRealname()));
        }
    }

    public CadreParty get(int userId, byte type) {

        CadrePartyExample example = new CadrePartyExample();
        example.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(type);
        List<CadreParty> cadreParties = cadrePartyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cadreParties.size() > 0 ? cadreParties.get(0) : null;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void cadreParty_batchDel(Integer[] ids) {

        for (Integer id : ids) {
            CadreParty cadreParty = cadrePartyMapper.selectByPrimaryKey(id);
            Integer userId = cadreParty.getUserId();
            CadreView cadreView = dbFindByUserId(userId);
            if (cadreView != null) {
                Integer cadreId = cadreView.getId();
                // 记录任免日志
                cadreAdLogService.addLog(cadreId, "删除干部党派：" + JSONUtils.toString(cadreParty, false),
                        CadreConstants.CADRE_AD_LOG_MODULE_CADRE, cadreId);
            }
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
    public void updateByPrimaryKeySelective(Cadre record) {

        cadreMapper.updateByPrimaryKeySelective(record);

        if (BooleanUtils.isNotTrue(record.getIsDouble())) { // 不是双肩挑
            commonMapper.excuteSql("update cadre set double_unit_ids=null where id=" + record.getId());
        }
    }

    // 干部列表（包含优秀年轻干部、考察对象）
    @Cacheable(value = "Cadre:ALL")
    public Map<Integer, CadreView> findAll() {

        Set<Byte> cadreStatusSet = new HashSet<>();
        cadreStatusSet.addAll(CadreConstants.CADRE_STATUS_SET);
        cadreStatusSet.add(CadreConstants.CADRE_STATUS_RESERVE);
        cadreStatusSet.add(CadreConstants.CADRE_STATUS_INSPECT);

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andStatusIn(new ArrayList<>(cadreStatusSet));
        example.setOrderByClause("sort_order desc");
        List<CadreView> records = cadreViewMapper.selectByExample(example);
        Map<Integer, CadreView> map = new LinkedHashMap<>();
        for (CadreView record : records) {
            map.put(record.getId(), record);
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

        Cadre entity = cadreMapper.selectByPrimaryKey(id);
        changeOrder(TABLE_NAME, "status=" + entity.getStatus(), ORDER_BY_DESC, id, addNum);
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
    public int countCommitteeMember() {

        Map<Integer, CmMemberView> resultMap
                = cmMemberService.committeeMemberMap();
        return resultMap.size();
    }

    // 常委
    public List<CmMemberView> committeeMembers() {

        Map<Integer, CmMemberView> resultMap
                = cmMemberService.committeeMemberMap();
        return new ArrayList<>(resultMap.values());
    }

    // 批量排序
    @Transactional
    @CacheEvict(value = "Cadre:ALL", allEntries = true)
    public void batchSort(byte status, List<Integer> cadreIdList) {

        commonMapper.excuteSql("update cadre set sort_order=null where status=" + status
                + " and id in(" + StringUtils.join(cadreIdList, ",") + ")");

        for (Integer cadreId : cadreIdList) {

            Cadre record = new Cadre();
            record.setId(cadreId);
            record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + status));

            cadreMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 更换干部的工号（仅更换两个账号的code和username，不改变干部的cadreId和userId）
    @Transactional
    public void changeCode(int userId, int newUserId, String remark) {

        if (userId == newUserId) return;

        SysUserView user = sysUserService.findById(userId);
        String oldCode = user.getCode();
        String oldUsername = user.getUsername();
        CadreView checkCadre = dbFindByUserId(newUserId);
        SysUserView newUser = sysUserService.findById(newUserId);
        String newCode = newUser.getCode();
        String newUsername = newUser.getUsername();
        if (checkCadre != null) {

            throw new OpException("{0}({1})已经在干部库中({2})，无法更换",
                    newUser.getRealname(), newCode,
                    CadreConstants.CADRE_STATUS_MAP.get(checkCadre.getStatus()));
        }

        /*if (!StringUtils.equals(user.getIdcard(), newUser.getIdcard())) {
            throw new OpException("身份证号码不相同，无法更换");
        }*/

        Byte type = newUser.getType();
        if (type != SystemConstants.USER_TYPE_JZG) {
            throw new OpException("账号不是教职工。" + newUser.getCode() + "," + newUser.getRealname());
        }

        // 仅更换两个账号的code和username
        SysUser record = new SysUser();
        record.setId(userId);
        record.setUsername(oldUsername + "_");
        record.setCode(oldCode + "_");
        sysUserMapper.updateByPrimaryKeySelective(record);

        record = new SysUser();
        record.setId(newUserId);
        record.setUsername(oldUsername);
        record.setCode(oldCode);
        sysUserMapper.updateByPrimaryKeySelective(record);

        record = new SysUser();
        record.setId(userId);
        record.setUsername(newUsername);
        record.setCode(newCode);
        sysUserMapper.updateByPrimaryKeySelective(record);


        user = sysUserService.findById(userId);
        newUser = sysUserService.findById(newUserId);
        // 重新同步教职工信息
        CmTag.snycTeacherInfo(newUserId, newUser);
        CmTag.snycTeacherInfo(userId, user);

        cacheHelper.clearUserCache(user);
        cacheHelper.clearUserCache(newUser);

        cacheHelper.clearCadreCache();

        CadreView cv = dbFindByUserId(userId);
        int cadreId = cv.getId();
        // 记录任免日志
        cadreAdLogService.addLog(cadreId, "更换工号" + oldCode + "->" + newCode + "，" + remark,
                CadreConstants.CADRE_AD_LOG_MODULE_CADRE, cadreId);
    }
}
