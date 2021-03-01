package service.cadre;

import controller.global.OpException;
import domain.abroad.Passport;
import domain.abroad.PassportExample;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.base.MetaTypeExample;
import domain.cadre.*;
import domain.cadreInspect.CadreInspect;
import domain.cm.CmMemberView;
import domain.modify.ModifyCadreAuth;
import domain.sys.SysUser;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import domain.unit.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
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
import service.dp.dpCommon.DpCommonService;
import service.global.CacheHelper;
import service.modify.ModifyCadreAuthService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.HttpResponseMethod;
import sys.constants.*;
import sys.tags.CmTag;
import sys.utils.ContentUtils;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.PatternUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CadreService extends BaseMapper implements HttpResponseMethod {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String TABLE_NAME = "cadre";

    @Autowired(required = false)
    private CadreAdminLevelService cadreAdminLevelService;
    @Autowired(required = false)
    private CadrePostService cadrePostService;
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
    @Autowired
    protected DpCommonService dpCommonService;

    // 添加临时干部（无角色）
    @Transactional
    public Cadre addTempCadre(int userId) {

        Cadre record = new Cadre();
        record.setUserId(userId);
        record.setStatus(CadreConstants.CADRE_STATUS_NOT_CADRE);
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

    // 提任（科级干部->处级干部->校领导）
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre", key = "#id")
    })
    public void promote(int id, String title) {

        CadreView cadre = get(id);
        byte status = cadre.getStatus();
        if(status!=CadreConstants.CADRE_STATUS_KJ && status!=CadreConstants.CADRE_STATUS_CJ){
            throw new OpException("仅现任干部可以提任");
        }
        byte toStatus = (status==CadreConstants.CADRE_STATUS_KJ)?
                CadreConstants.CADRE_STATUS_CJ:CadreConstants.CADRE_STATUS_LEADER;

        Cadre record = new Cadre();
        record.setStatus(toStatus);
        if (StringUtils.isNotBlank(title))
            record.setTitle(title);
        record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + toStatus));

        CadreExample example = new CadreExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(status);

        cadreMapper.updateByExampleSelective(record, example);
    }

    // 离任
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre", key = "#id")
    })
    public byte leave(int id, String title, Integer dispatchCadreId,
                      String _deposeDate, String _appointDate,String originalPost,
                      Integer[] postIds) {

        Byte status = null;
        Cadre cadre = cadreMapper.selectByPrimaryKey(id);
        byte orgStatus = cadre.getStatus();
        if (orgStatus == CadreConstants.CADRE_STATUS_CJ) {
            status = CadreConstants.CADRE_STATUS_CJ_LEAVE;
        } else if (orgStatus == CadreConstants.CADRE_STATUS_KJ) {
            status = CadreConstants.CADRE_STATUS_KJ_LEAVE;
        } else if (orgStatus == CadreConstants.CADRE_STATUS_LEADER) {
            status = CadreConstants.CADRE_STATUS_LEADER_LEAVE;
        }

        if (status == null) {
            throw new OpException("只有现任干部可以进行离任操作");
        }

        // 记录任免日志
        cadreAdLogService.addLog(id, "干部离任",
                CadreConstants.CADRE_AD_LOG_MODULE_CADRE, id);

        if (status == CadreConstants.CADRE_STATUS_CJ_LEAVE && passportMapper != null) {

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

        if (StringUtils.isNotBlank(_deposeDate)){
         record.setDeposeDate(DateUtils.parseDate(_deposeDate,DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(_appointDate)){
            record.setAppointDate(DateUtils.parseDate(_appointDate,DateUtils.YYYYMMDD_DOT));
        }

        record.setOriginalPost(originalPost);
        record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + status));

        CadreExample example = new CadreExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(orgStatus);

        cadreMapper.updateByExampleSelective(record, example);

        // 清除岗位信息
        commonMapper.excuteSql("update cadre_post set unit_post_id=null where id in("
                + StringUtils.join(postIds, ",") + ")");

        //添加离任干部角色
        sysUserService.addRole(cadre.getUserId(),RoleConstants.ROLE_CADRE_LEAVE);

        return status;
    }

    // 重新任用， 离任->考察对象
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true)
    })
    public void re_assign(Integer[] ids) {

        for (Integer id : ids) {

            // 记录任免日志
            cadreAdLogService.addLog(id, "重新任用",
                    CadreConstants.CADRE_AD_LOG_MODULE_CADRE, id);

            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            int userId = cadre.getUserId();
            if (cadre.getStatus() != CadreConstants.CADRE_STATUS_CJ_LEAVE
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

            cacheHelper.clearCadreCache(id);
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
            @CacheEvict(value = "Cadre", key = "#result.id")
    })
    synchronized public Cadre insertSelective(Cadre record) {

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
            sysUserService.addRole(userId, RoleConstants.ROLE_CADRE_CJ);

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

        return record;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true)// 因私出国部分，有校领导和本单位正职的权限控制。
    })
    public int batchImport(List<Cadre> records) {

        int addCount = 0;
        for (Cadre record : records) {

            int userId = record.getUserId();
            CadreView cv = dbFindByUserId(userId);
            if (cv == null) {

                insertSelective(record);
                addCount++;
            } else {
                record.setId(cv.getId());
                updateByPrimaryKeySelective(record);
            }

            cacheHelper.clearCadreCache(record.getId());
        }

        return addCount;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true)
    })
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            Assert.isTrue(cadre.getStatus() != CadreConstants.CADRE_STATUS_RESERVE
                    && cadre.getStatus() != CadreConstants.CADRE_STATUS_INSPECT, "wrong status"); // 非优秀年轻干部、考察对象

            cadreMapper.deleteByPrimaryKey(id);

            // 删除干部身份
            sysUserService.delRole(cadre.getUserId(), RoleConstants.ROLE_CADRE_CJ);

            cacheHelper.clearCadreCache(id);

            SysUserView uv = cadre.getUser();
            logger.info(addLog(LogConstants.LOG_ADMIN,
                    "删除干部：id=%s, code=%s, realname=%s", id, uv.getCode(), uv.getRealname()));
        }
    }

    public CadreParty getCadreParty(int userId, byte type) {

        CadrePartyExample example = new CadrePartyExample();
        example.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(type);
        List<CadreParty> cadreParties = cadrePartyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cadreParties.size() > 0 ? cadreParties.get(0) : null;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true)
    })
    public void cadreParty_batchDel(Integer[] ids) {

        for (Integer id : ids) {

            CadreParty cadreParty = cadrePartyMapper.selectByPrimaryKey(id);
            Integer userId = cadreParty.getUserId();
            CadreView cadreView = dbFindByUserId(userId);
            if (cadreView != null) {
                Integer cadreId = cadreView.getId();
                // 记录任免日志
                cadreAdLogService.addLog(cadreId, "删除干部民主党派：" + JSONUtils.toString(cadreParty, false),
                        CadreConstants.CADRE_AD_LOG_MODULE_CADRE, cadreId);
            }
            cadrePartyMapper.deleteByPrimaryKey(id);

            dpCommonService.updateMemberRole(userId);
            //cadrePartyService.updateRole(userId);

            cacheHelper.clearCadreCache(cadreView.getId());
        }
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre", key = "#record.id")
    })
    public void updateByPrimaryKeySelective(Cadre record) {

        cadreMapper.updateByPrimaryKeySelective(record);

        if (BooleanUtils.isNotTrue(record.getIsDouble())) { // 不是双肩挑
            commonMapper.excuteSql("update cadre set double_unit_ids=null where id=" + record.getId());
        }
    }

    @Cacheable(value = "Cadre", key="#cadreId", condition = "#cadreId!=null")
    public CadreView get(Integer cadreId){

        if(cadreId==null) return null;

        return iCadreMapper.getCadre(cadreId);
    }

    // 干部列表（包含优秀年轻干部、考察对象）
    public List<Cadre> getCadres(){

        Set<Byte> cadreStatusSet = new HashSet<>();
        cadreStatusSet.addAll(CadreConstants.CADRE_STATUS_SET);
        cadreStatusSet.add(CadreConstants.CADRE_STATUS_RESERVE);
        cadreStatusSet.add(CadreConstants.CADRE_STATUS_INSPECT);

        CadreExample example = new CadreExample();
        example.createCriteria().andStatusIn(new ArrayList<>(cadreStatusSet));
        example.setOrderByClause("sort_order desc");

        return cadreMapper.selectByExample(example);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = id,
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "Cadre", key = "#id")
    public void changeOrder(int id, int addNum) {

        Cadre entity = cadreMapper.selectByPrimaryKey(id);
        changeOrder(TABLE_NAME, "status=" + entity.getStatus(), ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateWorkTime(int userId, Date _workTime) {

        // 修改参加工作时间
        TeacherInfo record = new TeacherInfo();
        record.setUserId(userId);
        record.setWorkTime(_workTime);
        teacherInfoMapper.updateByPrimaryKeySelective(record);

        cacheHelper.clearCadreCache(CmTag.getCadreId(userId));
    }

    @Transactional
    @CacheEvict(value = "Cadre", key = "#cadreId")
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
    public void batchSort(byte status, List<Integer> cadreIdList) {

        commonMapper.excuteSql("update cadre set sort_order=null where status=" + status
                + " and id in(" + StringUtils.join(cadreIdList, ",") + ")");

        for (Integer cadreId : cadreIdList) {

            Cadre record = new Cadre();
            record.setId(cadreId);
            record.setSortOrder(getNextSortOrder(TABLE_NAME, "status=" + status));

            cadreMapper.updateByPrimaryKeySelective(record);

            cacheHelper.clearCadreCache(cadreId);
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
        CmTag.snycTeacherInfo(newUserId, user.getCode());
        CmTag.snycTeacherInfo(userId, newUser.getCode());

        cacheHelper.clearUserCache(user);
        cacheHelper.clearUserCache(newUser);


        CadreView cv = dbFindByUserId(userId);
        int cadreId = cv.getId();
        cacheHelper.clearCadreCache(cadreId);

        // 记录任免日志
        cadreAdLogService.addLog(cadreId, "更换工号" + oldCode + "->" + newCode + "，" + remark,
                CadreConstants.CADRE_AD_LOG_MODULE_CADRE, cadreId);
    }

    @Transactional
    public void batchSortByAdminLevel(byte status){

        //清空临时表
        iCadreMapper.emptyTmpSort();
        int count = iCadreMapper.batchSortByAdminLevel(status);
        iCadreMapper.updateCadreByTmpSort(count);
    }

    @Transactional
    public void batchSortByUnit(byte status){

        //清空临时表
        iCadreMapper.emptyTmpSort();
        int count = iCadreMapper.batchSortByUnit(status);
        iCadreMapper.updateCadreByTmpSort(count);
    }

    /*
            1、可以录入有岗位的干部
            2、可以录入空岗（若每次都有空岗，则都会添加一个新的，不会覆盖）
            3、unitCode编码为完整的编码
        * */
    @Transactional
    public void cadreAll_import(List<Map<Integer, String>> xlsRows, Byte status, String unitCode){

        // 清空职务属性
        if (unitPostMapper.countByExample(new UnitPostExample()) == 0) {
            commonMapper.excuteSql("delete bmt.* from base_meta_type bmt , base_meta_class bmc where bmt.class_id=bmc.id and bmc.code='mc_post'");
        }

        List<String> codeList = new ArrayList<>();//记录已导入人员,用于跳过重复的人员
        List<Integer> postIdList = new ArrayList<>();//将撤销的岗位id
        List<UnitPost> _unitPostList = new ArrayList<>();//记录空岗，用于最后添加
        Map<Integer, List<String>> postUnitMap = new HashMap<>();//主职/兼职记录,默认第一条为主职 <cadreId, unitPostName_unitId>

        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            //1、处级干部
            String unitPostName = ContentUtils.trimAll(xlsRow.get(4));
            if (StringUtils.isBlank(unitPostName)){
                throw new OpException("第{0}行所在单位及职务为空", row);
            }
            Cadre cadre = new Cadre();
            int userId = 0;
            int cadreId = 0;
            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isNotBlank(userCode)) {//工号为空时，录入的可能是一条空闲岗位，这些记录需要核对是否确实没有任职人员
                //跳过重复记录
                if (codeList.contains(userCode)){
                    continue;
                }
                codeList.add(userCode);

                SysUserView uv = sysUserService.findByCode(userCode);
                if (uv == null) {
                    logger.error("第{0}行工作证号[{1}]不存在", row, userCode);
                    continue;
                }
                userId = uv.getId();
                cadre.setUserId(userId);
                cadre.setIsDep(StringUtils.contains(StringUtils.trimToNull(xlsRow.get(2)), "院系"));
                cadre.setTitle(unitPostName);
                String _isDouble = StringUtils.trimToNull(xlsRow.get(5));
                cadre.setIsDouble(StringUtils.equals(_isDouble, "是"));

                status = CadreConstants.CADRE_STATUS_LEADER;
                if (StringUtils.contains(StringUtils.trimToNull(xlsRow.get(3)), "处级")) {
                    status = CadreConstants.CADRE_STATUS_CJ;
                } else if (StringUtils.contains(StringUtils.trimToNull(xlsRow.get(3)), "科级")) {
                    status = CadreConstants.CADRE_STATUS_KJ;
                }
                cadre.setStatus(status);

                if (cadre.getIsDouble()) {

                    String doubleUnitName = StringUtils.trimToNull(xlsRow.get(7));
                    if (StringUtils.isNotBlank(doubleUnitName))
                        insertUnit(doubleUnitName, unitCode, status);
                }

                String remark = StringUtils.trimToNull(xlsRow.get(32));
                cadre.setRemark(remark);
                CadreView cv = dbFindByUserId(userId);
                if (cv == null) {

                    insertSelective(cadre);
                } else {
                    cadre.setId(cv.getId());
                    updateByPrimaryKeySelective(cadre);
                }
                cacheHelper.clearCadreCache(cadreId);
                cadreId = cadre.getId();
            }

            //2、内设机构
            String[] unitPostNames = StringUtils.split(unitPostName, ",|，");
            List<String> postUnitList = new ArrayList<>();
            String unitName = ContentUtils.trimAll(xlsRow.get(6));
            if (StringUtils.isBlank(unitName)){
                throw new OpException("第{0}行所在单位为空", row);
            }
            String[] unitNames = StringUtils.split(unitName, ",|，");
            if (unitPostNames.length != unitNames.length)
                throw new OpException("第{0}行单位和岗位的数量不对应", row);

            for (String name : unitNames) {

                Unit unit = insertUnit(name, unitCode, status);

                //添加岗位_单位map或者空岗
                if (cadreId == 0){
                    UnitPost unitPost = new UnitPost();
                    unitPost.setName(unitPostName);
                    unitPost.setIsCpc(true);
                    unitPost.setStatus(SystemConstants.UNIT_POST_STATUS_NORMAL);
                    unitPost.setUnitId(unit.getId());
                    unitPost.setIsPrincipal(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(8)), "是"));
                    String _postType = StringUtils.trimToNull(xlsRow.get(9));//职务属性（主职）
                    String _adminLevel = StringUtils.trimToNull(xlsRow.get(10));//岗位级别（主职）
                    MetaType adminLevel = CmTag.getMetaTypeByName("mc_admin_level", _adminLevel);
                    if (adminLevel == null)throw new OpException("第{0}行岗位级别[{1}]不存在", row, _postType);

                    String _postClass = StringUtils.trimToNull(xlsRow.get(11));
                    MetaType postClass = CmTag.getMetaTypeByName("mc_post_class", _postClass);
                    if (postClass == null)throw new OpException("第{0}行职务类别[{1}]不存在", row, _postClass);
                    unitPost.setIsCpc(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(12)), "是"));

                    unitPost.setPostType(metaTypeService.findOrCreate("mc_post", _postType).getId());
                    unitPost.setAdminLevel(adminLevel.getId());
                    unitPost.setPostClass(postClass.getId());
                    _unitPostList.add(unitPost);
                    continue;
                }else {
                    int index = ArrayUtils.indexOf(unitNames, name);
                    postUnitList.add(unitPostNames[index] + "_" + unit.getId());
                    postUnitMap.put(cadreId, postUnitList);
                }
            }
            if (cadreId == 0){//录入空闲岗位，跳过以下步骤
                continue;
            }

            //3、首先删除干部不再任职的干部现任职务；删除对应干部的职级信息
            List<CadrePost> _post = cadrePostService.getCadrePost(cadreId, null, postUnitMap.get(cadreId));//将要任的职务
            List<CadrePost> hasPost = cadrePostService.getCadrePost(cadreId, null, null);//现在任的职务
            for (CadrePost cadrePost : hasPost) {
                if (!Arrays.asList(_post).contains(cadrePost)){
                    postIdList.add(cadrePost.getUnitPostId());
                    cadrePostMapper.deleteByPrimaryKey(cadrePost.getId());
                }else {
                    cadrePost.setIsMainPost(false);
                    cadrePost.setIsFirstMainPost(false);
                    cadrePostMapper.updateByPrimaryKeySelective(cadrePost);
                }
            }

            List<CadreAdminLevel> cadreAdminLevelList = cadreAdminLevelService.getCadreAdminLevels(cadreId);
            for (CadreAdminLevel cadreAdminLevel : cadreAdminLevelList) {
                cadreAdminLevelMapper.deleteByPrimaryKey(cadreAdminLevel.getId());
            }

            //4、职级
            List<CadreAdminLevel> adminLevelList = new ArrayList<>();
            Date viceStartDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(25)));
            Date mainStartDate = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(26)));
            if (viceStartDate != null) {

                CadreAdminLevel adminLevel = new CadreAdminLevel();
                adminLevel.setCadreId(cadreId);
                adminLevel.setAdminLevel(CmTag.getMetaTypeByCode("mt_admin_level_vice").getId());
                adminLevel.setsWorkTime(viceStartDate);
                adminLevelList.add(adminLevel);
            }

            if (mainStartDate != null) {

                CadreAdminLevel adminLevel = new CadreAdminLevel();
                adminLevel.setCadreId(cadreId);
                adminLevel.setAdminLevel(CmTag.getMetaTypeByCode("mt_admin_level_main").getId());
                adminLevel.setsWorkTime(mainStartDate);
                adminLevelList.add(adminLevel);
            }
            for (CadreAdminLevel adminLevel : adminLevelList) {
                CadreAdminLevel cadreAdminLevel = cadreAdminLevelService.getByCadreId(cadreId, adminLevel.getAdminLevel());

                if (cadreAdminLevel != null) {
                    adminLevel.setId(cadreAdminLevel.getId());
                    cadreAdminLevelMapper.updateByPrimaryKeySelective(adminLevel);
                } else {
                    cadreAdminLevelMapper.insertSelective(adminLevel);
                }
            }

            //5、干部其他信息
            CadreParty cadreParty = new CadreParty();
            Byte partyType = null;
            String _growTime = StringUtils.trimToNull(xlsRow.get(28));
            String _partyType = StringUtils.trimToNull(xlsRow.get(29));
            if (StringUtils.isNotBlank(_growTime) && (StringUtils.isBlank(_partyType) || StringUtils.equals(_partyType, CadreConstants.CADRE_PARTY_TYPE_MAP.get(CadreConstants.CADRE_PARTY_TYPE_OW)))){//默认"中共党员"
                partyType = CadreConstants.CADRE_PARTY_TYPE_OW;
                cadreParty.setUserId(userId);
                cadreParty.setType(partyType);
                cadreParty.setGrowTime(DateUtils.parseStringToDate(_growTime));
                CadrePartyExample cadrePartyExample = new CadrePartyExample();
                cadrePartyExample.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(partyType);
                if (cadrePartyMapper.countByExample(cadrePartyExample) > 0){
                    cadrePartyMapper.updateByExampleSelective(cadreParty, cadrePartyExample);
                }else {
                    cadrePartyMapper.insertSelective(cadreParty);
                }

            }else if (StringUtils.isNotBlank(_growTime) && StringUtils.equals(_partyType, CadreConstants.CADRE_PARTY_TYPE_MAP.get(CadreConstants.CADRE_PARTY_TYPE_DP))){
                partyType = CadreConstants.CADRE_PARTY_TYPE_DP;
                cadreParty.setUserId(userId);
                cadreParty.setType(partyType);
                cadreParty.setGrowTime(DateUtils.parseStringToDate(_growTime));
                MetaClass metaClass = CmTag.getMetaClassByCode("mc_democratic_party");
                MetaTypeExample metaTypeExample = new MetaTypeExample();
                metaTypeExample.createCriteria().andExtraAttrEqualTo(_partyType).andClassIdEqualTo(metaClass.getId());
                List<MetaType> metaTypes = metaTypeMapper.selectByExample(metaTypeExample);
                if (metaTypes != null && metaTypes.size() > 0) {
                    cadreParty.setIsFirst(true);
                    cadreParty.setClassId(metaTypes.get(0).getId());
                }
                CadrePartyExample cadrePartyExample = new CadrePartyExample();
                cadrePartyExample.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(partyType);
                if (cadrePartyMapper.countByExample(cadrePartyExample) > 0){
                    cadrePartyMapper.updateByExampleSelective(cadreParty, cadrePartyExample);
                }else {
                    cadrePartyMapper.insertSelective(cadreParty);
                }
            }

            //6、其他
            TeacherInfo teacherInfo = new TeacherInfo();
            teacherInfo.setUserId(userId);
            teacherInfo.setProPost(StringUtils.trimToNull(xlsRow.get(27)));
            teacherInfo.setWorkTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(30))));
            TeacherInfo _teacherIfo = teacherInfoMapper.selectByPrimaryKey(userId);
            if (_teacherIfo != null){
                teacherInfo.setIsRetire(_teacherIfo.getIsRetire());
                teacherInfoMapper.updateByPrimaryKeySelective(teacherInfo);
            }else {
                teacherInfo.setIsRetire(false);
                teacherInfoMapper.insertSelective(teacherInfo);
            }

            //7、账号
            String mobile = StringUtils.trimToNull(xlsRow.get(31));
            if (StringUtils.isNotBlank(mobile)) {
                SysUserInfo userInfo = new SysUserInfo();
                userInfo.setUserId(userId);
                userInfo.setMobile(mobile);
                sysUserInfoMapper.updateByPrimaryKeySelective(userInfo);
            }
        }

        row = 1;
        codeList.removeAll(codeList);
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            String userCode = StringUtils.trim(xlsRow.get(0));

            //跳过空岗和重复记录
            if (StringUtils.isBlank(userCode)) {
                logger.error("第{0}行工作证号为空", row);
                continue;
            }else if (codeList.contains(userCode)){
                continue;
            }
            codeList.add(userCode);

            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                logger.error("第{0}行工作证号[{1}]不存在", row, userCode);
                continue;
            }
            Cadre cadre = getByUserId(uv.getUserId());
            int cadreId = cadre.getId();
            CadreView cv = CmTag.getCadreById(cadreId);

            //6、岗位
            List<String> postUnitList = postUnitMap.get(cadreId);//<岗位名称_单位Id>
            for (int i = 0; i < postUnitList.size(); i++){
                String postName = ContentUtils.trimAll(postUnitList.get(i).split("_")[0]);
                Integer unitId = Integer.valueOf(postUnitList.get(i).split("_")[1]);
                UnitPost unitPost = new UnitPost();
                //主职
                if (i == 0){
                    CadrePost mainPost = new CadrePost();
                    CadrePostExample cadrePostExample = new CadrePostExample();
                    cadrePostExample.createCriteria().andPostNameEqualTo(postName).andUnitIdEqualTo(unitId).andCadreIdEqualTo(cadreId);
                    List<CadrePost> mainPostList = cadrePostMapper.selectByExample(cadrePostExample);
                    if (mainPostList == null || mainPostList.size() == 0){//不存在主职
                        UnitPostViewExample unitPostExample = new UnitPostViewExample();
                        unitPostExample.createCriteria().andUnitIdEqualTo(unitId).andNameEqualTo(postName).andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL).andCadreIdIsNull();
                        List<UnitPostView> unitPostList = unitPostViewMapper.selectByExample(unitPostExample);
                        if (unitPostList == null || unitPostList.size() == 0){//不存在岗位
                            unitPost.setName(postName);
                            setUnitPost(unitId, unitPost);
                            unitPost.setIsPrincipal(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(8)), "是"));
                            String _postType = StringUtils.trimToNull(xlsRow.get(9));//职务属性（主职）
                            String _adminLevel = StringUtils.trimToNull(xlsRow.get(10));//岗位级别（主职）
                            MetaType adminLevel = CmTag.getMetaTypeByName("mc_admin_level", _adminLevel);
                            if (adminLevel == null)throw new OpException("第{0}行岗位级别[{1}]不存在", row, _postType);

                            String _postClass = StringUtils.trimToNull(xlsRow.get(11));
                            MetaType postClass = CmTag.getMetaTypeByName("mc_post_class", _postClass);
                            if (postClass == null)throw new OpException("第{0}行职务类别[{1}]不存在", row, _postClass);
                            unitPost.setIsCpc(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(12)), "是"));

                            unitPost.setPostType(metaTypeService.findOrCreate("mc_post", _postType).getId());
                            unitPost.setAdminLevel(adminLevel.getId());
                            unitPost.setPostClass(postClass.getId());
                            unitPostMapper.insertSelective(unitPost);
                        }else {//存在岗位
                            unitPost = unitPostMapper.selectByPrimaryKey(unitPostList.get(0).getId());
                            postIdList.remove(unitPostList.get(0).getId());
                        }
                        mainPost.setUnitPostId(unitPost.getId());
                        mainPost.setPostName(unitPost.getName());
                        mainPost.setIsPrincipal(unitPost.getIsPrincipal());
                        mainPost.setAdminLevel(unitPost.getAdminLevel());
                        mainPost.setPostType(unitPost.getPostType());
                        mainPost.setPostClassId(unitPost.getPostClass());
                        mainPost.setUnitId(unitPost.getUnitId());

                        mainPost.setCadreId(cv.getId());
                        mainPost.setPost(unitPost.getName());
                        mainPost.setIsFirstMainPost(true);
                        mainPost.setLpWorkTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(13))));
                        mainPost.setNpWorkTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(14))));
                        mainPost.setIsMainPost(true);
                        mainPost.setSortOrder(getNextSortOrder("cadre_post", "cadre_id=" + mainPost.getCadreId()
                                + " and is_main_post=" + mainPost.getIsMainPost()));
                        cadrePostService.insertSelective(mainPost);

                        cacheHelper.clearCadreCache(cadreId);
                    }else{
                        mainPost = mainPostList.get(0);
                        mainPost.setIsMainPost(true);
                        mainPost.setIsFirstMainPost(true);
                        cadrePostMapper.updateByPrimaryKeySelective(mainPost);
                    }
                }else {//兼职

                    CadrePost partPost = new CadrePost();
                    CadrePostExample cadrePostExample = new CadrePostExample();
                    cadrePostExample.createCriteria().andPostNameEqualTo(postName).andUnitIdEqualTo(unitId).andCadreIdEqualTo(cadreId);
                    List<CadrePost> partPostList = cadrePostMapper.selectByExample(cadrePostExample);
                    if (partPostList == null || partPostList.size() == 0) {//不存在兼职
                        UnitPostViewExample unitPostExample = new UnitPostViewExample();
                        unitPostExample.createCriteria().andUnitIdEqualTo(unitId).andNameEqualTo(postName).andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL).andCadreIdIsNull();
                        List<UnitPostView> unitPostList = unitPostViewMapper.selectByExample(unitPostExample);
                        String _postType = "";
                        MetaType adminLevel = null;
                        MetaType postClass = null;
                        if (unitPostList == null || unitPostList.size() == 0){//不存在岗位
                            unitPost.setName(postName);
                            setUnitPost(unitId, unitPost);
                            if (i==1){
                                unitPost.setIsPrincipal(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(15)), "是"));
                                _postType = StringUtils.trimToNull(xlsRow.get(16));//职务属性（主职）
                                String _adminLevel = StringUtils.trimToNull(xlsRow.get(17));//岗位级别（主职）
                                adminLevel = CmTag.getMetaTypeByName("mc_admin_level", _adminLevel);
                                if (adminLevel == null)throw new OpException("第{0}行岗位级别[{1}]不存在", row, _postType);

                                String _postClass = StringUtils.trimToNull(xlsRow.get(18));
                                postClass = CmTag.getMetaTypeByName("mc_post_class", _postClass);
                                if (postClass == null)throw new OpException("第{0}行职务类别[{1}]不存在", row, _postClass);
                                unitPost.setIsCpc(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(19)), "是"));
                            }else if (i==2){
                                unitPost.setIsPrincipal(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(20)), "是"));
                                _postType = StringUtils.trimToNull(xlsRow.get(21));//职务属性（主职）
                                String _adminLevel = StringUtils.trimToNull(xlsRow.get(22));//岗位级别（主职）
                                adminLevel = CmTag.getMetaTypeByName("mc_admin_level", _adminLevel);
                                if (adminLevel == null)throw new OpException("第{0}行岗位级别[{1}]不存在", row, _postType);

                                String _postClass = StringUtils.trimToNull(xlsRow.get(23));
                                postClass = CmTag.getMetaTypeByName("mc_post_class", _postClass);
                                if (postClass == null)throw new OpException("第{0}行职务类别[{1}]不存在", row, _postClass);
                                unitPost.setIsCpc(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(24)), "是"));
                            }

                            unitPost.setPostType(metaTypeService.findOrCreate("mc_post", _postType).getId());
                            unitPost.setAdminLevel(adminLevel.getId());
                            unitPost.setPostClass(postClass.getId());
                            unitPostMapper.insertSelective(unitPost);
                        }else {
                            unitPost = unitPostMapper.selectByPrimaryKey(unitPostList.get(0).getId());
                            postIdList.remove(unitPostList.get(0).getId());
                        }
                        partPost.setUnitPostId(unitPost.getId());
                        partPost.setPostName(unitPost.getName());
                        partPost.setIsPrincipal(unitPost.getIsPrincipal());
                        partPost.setAdminLevel(unitPost.getAdminLevel());
                        partPost.setPostType(unitPost.getPostType());
                        partPost.setPostClassId(unitPost.getPostClass());
                        partPost.setUnitId(unitPost.getUnitId());

                        partPost.setCadreId(cv.getId());
                        partPost.setPost(unitPost.getName());
                        partPost.setIsMainPost(false);

                        partPost.setSortOrder(getNextSortOrder("cadre_post", "cadre_id=" + partPost.getCadreId()
                                + " and is_main_post=" + partPost.getIsMainPost()));
                        cadrePostService.insertSelective(partPost);

                        cacheHelper.clearCadreCache(cadreId);
                    }else{
                        partPost = partPostList.get(0);
                        partPost.setIsMainPost(true);
                        partPost.setIsFirstMainPost(true);
                        cadrePostMapper.updateByPrimaryKeySelective(partPost);
                    }
                }
            }
        }

        //撤销不需要的岗位，插入空岗
        UnitPostViewExample needDelete = new UnitPostViewExample();
        UnitPostViewExample.Criteria criteria1 = needDelete.createCriteria().andCadreIdIsNull();
        if (postIdList != null && postIdList.size() > 0){
            criteria1.andIdIn(postIdList);
        }else {
            criteria1.andIdIsNull();
        }
        List<UnitPostView> needDeletePost = unitPostViewMapper.selectByExample(needDelete);
        for (UnitPostView unitPostView : needDeletePost) {
            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(unitPostView.getId());
            unitPost.setStatus(SystemConstants.UNIT_POST_STATUS_ABOLISH);
            unitPostMapper.updateByPrimaryKey(unitPost);
        }

        for (UnitPost unitPost : _unitPostList) {
            Unit unit = unitMapper.selectByPrimaryKey(unitPost.getUnitId());
            unitPost.setCode(genCode(unit.getCode(), false));
            unitPostMapper.insertSelective(unitPost);
        }

        //最后撤销单位
        List<Unit> unitList = unitMapper.selectByExample(new UnitExample());
        for (Unit unit : unitList) {
            int unitId = unit.getId();
            UnitPostExample unitPostExample = new UnitPostExample();
            UnitPostExample.Criteria criteria = unitPostExample.createCriteria().andUnitIdEqualTo(unitId);
            int unitPostCount = (int) unitPostMapper.countByExample(unitPostExample);

            CadrePostExample cadrePostExample = new CadrePostExample();
            cadrePostExample.createCriteria().andUnitIdEqualTo(unitId);
            int cadrePostCount = (int) cadrePostMapper.countByExample(cadrePostExample);

            criteria.andStatusNotEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL);
            int _unitPostCount = (int) unitPostMapper.countByExample(unitPostExample);

            if ((unitPostCount == _unitPostCount) || (unitPostCount == 0 && cadrePostCount == 0)) {
                unit.setStatus(SystemConstants.UNIT_STATUS_HISTORY);
                unitMapper.updateByPrimaryKey(unit);
            }
        }
    }

    //插入单
    public Unit insertUnit(String name, String unitCode, Byte status) {

        Unit unit = new Unit();
        UnitExample uExample = new UnitExample();
        uExample.createCriteria().andStatusEqualTo(SystemConstants.UNIT_STATUS_RUN).andIsDeletedEqualTo(false);
        List<Unit> unitList = unitMapper.selectByExample(uExample);
        List<String> unitNameList = unitList.stream().map(Unit::getName).collect(Collectors.toList());
        if (unitNameList.contains(name)) {
            UnitExample unitExample = new UnitExample();
            unitExample.createCriteria().andNameEqualTo(name).andIsDeletedEqualTo(false).andStatusEqualTo(SystemConstants.UNIT_STATUS_RUN);
            unit = unitMapper.selectByExample(unitExample).get(0);
        }else {
            unit = new Unit();
            unit.setName(name);
            unit.setCode(genCode(unitCode, true));
            unit.setCreateTime(new Date());
            unit.setStatus(SystemConstants.UNIT_STATUS_RUN);
            unit.setSortOrder(getNextSortOrder("unit", "status=" + status));
            if (PatternUtils.match(".*(系|院|班).*", name)) {
                unit.setTypeId(CmTag.getMetaTypeByCode("mt_unit_type_xy").getId());
            } else if (PatternUtils.match(".*(附属|附).*", name)) {
                unit.setTypeId(CmTag.getMetaTypeByCode("mc:ryu2r6").getId());
            } else if (PatternUtils.match(".*(公司).*", name)) {
                unit.setTypeId(CmTag.getMetaTypeByCode("mc:7kk9wz").getId());
            } else {
                unit.setTypeId(CmTag.getMetaTypeByCode("mc:iw29q5").getId());
            }
            unitMapper.insertSelective(unit);
        }

        return unit;
    }

    //获得不重复的编码
    public String genCode(String code, boolean isUnit){

        boolean isExisted;
        int count = 0;
        do {

            if (isUnit) {

                code = StringUtils.substring(code, 0, code.length() - 3);
                String unitMaxCode = iUnitMapper.getUnitMaxCode(code);
                if(unitMaxCode==null){
                    count = 1;
                }else {
                    count = Integer.valueOf(unitMaxCode.substring(unitMaxCode.length() - 3)) + 1;
                }

                code = code + String.format("%03d", count);
                UnitExample example = new UnitExample();
                example.createCriteria().andCodeEqualTo(code);

                isExisted = unitMapper.countByExample(example) > 0;
            }else {
                String unitPostMaxCode = iUnitMapper.getUnitPostMaxCode(code);
                if(unitPostMaxCode==null){
                    count = 1;
                }else {
                    count = Integer.valueOf(unitPostMaxCode.substring(unitPostMaxCode.length() - 3)) + 1;
                }
                UnitPostExample example = new UnitPostExample();

                code = code + String.format("%03d", count);
                example.createCriteria().andCodeEqualTo(code);

                isExisted = unitPostMapper.countByExample(example) > 0;
            }

            count++;

        }while (isExisted && count<=999);

        return code;
    }

    public void setUnitPost(int unitId, UnitPost unitPost){

        Unit unit = unitMapper.selectByPrimaryKey(unitId);
        unitPost.setIsCpc(true);
        unitPost.setStatus(SystemConstants.UNIT_POST_STATUS_NORMAL);
        unitPost.setUnitId(unitId);
        unitPost.setCode(genCode(unit.getCode(), false));

    }
}
