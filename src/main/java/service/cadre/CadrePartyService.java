package service.cadre;

import controller.global.OpException;
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
import sys.constants.CadreConstants;

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

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void addOrUPdateCadreParty(CadreParty record) {

        int userId = record.getUserId();
        CadreView cv = cadreService.dbFindByUserId(userId);

        if(record.getType()==CadreConstants.CADRE_PARTY_TYPE_DP){

            if(BooleanUtils.isNotTrue(record.getIsFirst())) {
                if (cv.getDpTypeId() == null) {
                    throw new OpException("请先添加第一民主党派");
                }
            }else if(typeDuplicate(record.getId(), userId,
                record.getType(), record.getIsFirst())){

                throw new OpException("第一民主党派已经存在，请勿重复添加");
            }

            if(classDuplicate(record.getId(), userId, record.getClassId())){

                throw new OpException("民主党派添加重复");
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

        if (cv == null) {
            // 不在干部库中，需要添加为临时干部
            cadreService.addTempCadre(userId);
        }
    }

     @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
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
            addOrUPdateCadreParty(record);
        }
    }

    // 删除类型为type的所有(民主)党派
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void del(int userId, Byte type) {

        CadrePartyExample example = new CadrePartyExample();
        CadrePartyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        cadrePartyMapper.deleteByExample(example);
    }
}
