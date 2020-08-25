package service.cadre;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreParty;
import domain.cadre.CadrePartyExample;
import domain.cadre.CadreView;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.global.CacheHelper;
import service.member.MemberQuitService;
import service.sys.SysUserService;
import sys.constants.CadreConstants;
import sys.constants.MemberConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2018/6/20.
 */
@Service
public class CadrePartyService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CadreService cadreService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MemberQuitService memberQuitService;
    @Autowired
    private CacheHelper cacheHelper;

    // 添加或删除角色（干部其他信息-民主党派成员）
    public void updateRole(int userId){

        MetaType metaType= CmTag.getMetaTypeByCode("mt_dp_qz");  //群众
        int crowdId = metaType.getId();
        CadrePartyExample example = new CadrePartyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(CadreConstants.CADRE_PARTY_TYPE_DP)
                .andClassIdNotEqualTo(crowdId);
        if(cadrePartyMapper.countByExample(example) > 0){
            sysUserService.addRole(userId, RoleConstants.ROLE_CADRE_DP);
        }else{
            sysUserService.delRole(userId, RoleConstants.ROLE_CADRE_DP);
        }
    }

    // 判断类型是否重复（中共党员、第一民主党派）
    public boolean typeDuplicate(Integer id, int userId, byte type, Boolean isFirst) {

        CadrePartyExample example = new CadrePartyExample();
        CadrePartyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(type);
        if (type == CadreConstants.CADRE_PARTY_TYPE_DP ) {
            if(BooleanUtils.isTrue(isFirst)) {
                criteria.andIsFirstEqualTo(true); // 最多只有一个第一民主党派
            }else{
                return false; // 可添加多个 非第一民主党派
            }
        }
        if (id != null) criteria.andIdNotEqualTo(id);

        return cadrePartyMapper.countByExample(example) > 0;
    }

    // 判断民主党派是否重复
    public boolean classDuplicate(Integer id, int userId, int classId) {

        CadrePartyExample example = new CadrePartyExample();
        CadrePartyExample.Criteria criteria =
                example.createCriteria()
                .andUserIdEqualTo(userId).andTypeEqualTo(CadreConstants.CADRE_PARTY_TYPE_DP)
                .andClassIdEqualTo(classId);

        if (id != null) criteria.andIdNotEqualTo(id);

        return cadrePartyMapper.countByExample(example) > 0;
    }

    // 获取 第一民主党派 或 中共党员
    public CadreParty getOwOrFirstDp(int userId, byte type) {

        CadrePartyExample example = new CadrePartyExample();
        CadrePartyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(type);
        if(type==CadreConstants.CADRE_PARTY_TYPE_DP){
            criteria.andIsFirstEqualTo(true);
        }

        List<CadreParty> cadreParties = cadrePartyMapper.selectByExample(example);
        return cadreParties.size() > 0 ? cadreParties.get(0) : null;
    }

    // 获取非第一民主党派（适用多个民主党派的情况）
    public List<CadreParty> getDpParties(int userId) {

        CadrePartyExample example = new CadrePartyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(CadreConstants.CADRE_PARTY_TYPE_DP)
                .andIsFirstEqualTo(false);

        return cadrePartyMapper.selectByExample(example);
    }

    @SuppressWarnings("checkstyle:WhitespaceAround")
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true)
    })
    public void addOrUpdateCadreParty(CadreParty record) {

        int userId = record.getUserId();

        CadreView cv = cadreService.dbFindByUserId(userId);
        if (cv == null) {
            // 不在干部库中，需要添加为临时干部
            cadreService.addTempCadre(userId);
            cv = cadreService.dbFindByUserId(userId);
        }

        if(record.getType()==CadreConstants.CADRE_PARTY_TYPE_DP){

            // 添加民主党派前，先删除“群众”
            iCadreMapper.deleteCadreParty(record.getUserId(), true);

            if(cv.getDpTypeId() == null) {

                // 还没有民主党派，设置为第一民主党派
                record.setIsFirst(true);

            }else {

                // 原来是 “群众”，则新建一条记录
                MetaType firstDpType = CmTag.getMetaType(cv.getDpTypeId());
                if(BooleanUtils.isTrue(firstDpType.getBoolAttr())){
                    record.setId(null);
                    record.setIsFirst(true);
                }
            }

            if(typeDuplicate(record.getId(), userId, record.getType(), record.getIsFirst())){

                throw new OpException("第一民主党派已经存在，请勿重复添加");
            }

            if(classDuplicate(record.getId(), userId, record.getClassId())){

                MetaType dpType = CmTag.getMetaType(record.getClassId());
                throw new OpException("添加重复，已经是{0}", dpType.getName());
            }

        }else if(typeDuplicate(record.getId(), userId,
                record.getType(), null)){

                throw new OpException("已经是中共党员，请勿重复添加");
        }

        if (record.getId() == null) {
            cadrePartyMapper.insertSelective(record);
        }else {
            Date growTime = record.getGrowTime();
            if(growTime==null){
                commonMapper.excuteSql("update cadre_party set grow_time=null where id="+ record.getId());
            }
            cadrePartyMapper.updateByPrimaryKeySelective(record);
        }

        updateRole(userId);

        cacheHelper.clearCadreCache(cv.getId());
    }

     @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true)
    })
    public void batchImport(List<CadreParty> records) {

        for (CadreParty record : records) {

            int userId = record.getUserId();
            byte type = record.getType();
            CadreParty cadreParty = getOwOrFirstDp(userId, type);
            if (cadreParty != null) {
                record.setId(cadreParty.getId());
            }

            if(record.getType()==CadreConstants.CADRE_PARTY_TYPE_DP) {
                record.setIsFirst(true); // 导入的都是第一民主党派
            }
            addOrUpdateCadreParty(record);

            updateRole(userId);
        }
    }

    // 删除类型为type的所有(民主)党派
    @Transactional
    public void del(int userId, Byte type) {

        CadrePartyExample example = new CadrePartyExample();
        CadrePartyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        cadrePartyMapper.deleteByExample(example);

        updateRole(userId);

        cacheHelper.clearCadreCache(CmTag.getCadreId(userId));
    }

    @Transactional
    public void addOrUpdateCrowd(CadreParty record) {

        int userId = record.getUserId();

        CadreView cv = cadreService.dbFindByUserId(userId);
        if (cv == null) {
            // 不在干部库中，需要添加为临时干部
            cadreService.addTempCadre(userId);
        }

        // 删除某人在 民主党派库中非“群众”的记录
        iCadreMapper.deleteCadreParty(userId,false);
        //党员出党
        memberQuitService.quit(userId, MemberConstants.MEMBER_STATUS_QUIT);

        MetaType metaType= CmTag.getMetaTypeByCode("mt_dp_qz");  //群众
        int crowdId = metaType.getId();

        //查询当前用户是否已经是群众,是则更新，不是则插入
        CadrePartyExample example = new CadrePartyExample();
        example.createCriteria().andClassIdEqualTo(crowdId)
                .andUserIdEqualTo(userId).andTypeEqualTo(record.getType());
        List<CadreParty> cadreParties = cadrePartyMapper.selectByExample(example);
        if (cadreParties!=null && cadreParties.size()>0){

            CadreParty cadreParty = cadreParties.get(0);
            record.setId(cadreParty.getId());
            record.setIsFirst(true);
            cadrePartyMapper.updateByPrimaryKeySelective(record);
        }else {

            record.setClassId(crowdId);
            record.setIsFirst(true);
            cadrePartyMapper.insert(record);
        }

        updateRole(userId);
    }
}
