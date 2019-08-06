package service.ps;

import domain.base.MetaType;
import domain.ps.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.PsInfoConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.*;

@Service
public class PsInfoService extends PsBaseMapper {

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void insertSelective(PsInfo record){

        record.setSortOrder(getNextSortOrder("ps_info","is_history="+record.getIsHistory()));
        psInfoMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void del(Integer id){

        psInfoMapper.deleteByPrimaryKey(id);
    }

    /*@Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void history(Integer[] ids, String _abolishDate){

        if(ids==null || ids.length==0) return;

        PsInfoExample example = new PsInfoExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        PsInfo record = new PsInfo();
        if (StringUtils.isNotBlank(_abolishDate)){
            record.setAbolishDate(DateUtils.parseDate(_abolishDate,DateUtils.YYYYMMDD_DOT));
        }
        record.setIsHistory(PsInfoConstants.PS_STATUS_IS_HISTORY);
        psInfoMapper.updateByExampleSelective(record, example);
    }*/

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsInfoExample example = new PsInfoExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psInfoMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(PsInfo record){

        return psInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PsInfo:ALL")
    public Map<Integer, PsInfo> findAll() {

        PsInfoExample example = new PsInfoExample();
        example.createCriteria().andIsHistoryEqualTo(false);
        example.setOrderByClause("sort_order desc");
        List<PsInfo> psInfoes = psInfoMapper.selectByExample(example);
        Map<Integer, PsInfo> map = new LinkedHashMap<>();
        for (PsInfo psInfo : psInfoes) {
            map.put(psInfo.getId(), psInfo);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "PsInfo:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        PsInfo entity = psInfoMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Boolean isHistory = entity.getIsHistory();

        PsInfoExample example = new PsInfoExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andIsHistoryEqualTo(isHistory).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andIsHistoryEqualTo(isHistory).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PsInfo> overEntities = psInfoMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            PsInfo targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("ps_info", "is_history="+isHistory, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ps_info", "is_history="+isHistory, baseSortOrder, targetEntity.getSortOrder());

            PsInfo record = new PsInfo();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            psInfoMapper.updateByPrimaryKeySelective(record);
        }
    }

    //根据建设单位ID 得到所属二级党校ID
    public Integer getPsInfoIdByparty(Integer partyId){
        PsPartyExample psPartyExample = new PsPartyExample();
        psPartyExample.createCriteria()
                .andPartyIdEqualTo(partyId).andIsFinishEqualTo(false);
        List<PsParty> psParties = psPartyMapper.selectByExample(psPartyExample);
        if (psParties.size()>0){
            return psParties.get(0).getPsId();
        }else {
            return -1;
        }
    }

    //获取未结束的建设单位ID
    public List<Integer> getPartyIdList(Boolean isHost, Integer psId){
        List<PsParty> psPartyList = getPsPsParty(null,psId);
        List<Integer> psPartyIdList = new ArrayList<Integer>();
        for (PsParty psParty : psPartyList){
            if(isHost == null){//全部未结束的建设单位Id
                psPartyIdList.add(psParty.getPartyId());
                continue;
            }
            if (isHost){//未结束的主建设单位ID
                if(psParty.getIsHost()){
                    psPartyIdList.add(psParty.getPartyId());
                }
            }else {//未结束的联合建设单位ID
                if(!psParty.getIsHost()){
                    psPartyIdList.add(psParty.getPartyId());
                }
            }
        }
        if(psPartyIdList.size() == 0) psPartyIdList.add(-1);
        return psPartyIdList;
    }

    //获取现任的组织人员
    public List<PsMember> getPsMember(String code, Integer id){
        MetaType metaType = CmTag.getMetaTypeByCode(code);
        PsMemberExample psMemberExample = new PsMemberExample();
        psMemberExample.createCriteria().andPsIdEqualTo(id)
                .andIsHistoryEqualTo(false).andTypeEqualTo(metaType.getId());
        return psMemberMapper.selectByExample(psMemberExample);
    }

    /**
     * 获取未结束的二级党校建设单位
     * @param isHost 是否主建单位
     * @param psId 二级党校id
     * @return 建设单位信息
     */
    public List<PsParty> getPsPsParty(Boolean isHost,Integer psId){
        PsPartyExample psPartyExample = new PsPartyExample();
        psPartyExample.createCriteria()
                .andPsIdEqualTo(psId)
                .andIsFinishEqualTo(PsInfoConstants.PS_STATUS_NOT_HISTORY);

        List<PsParty> allPsParties = psPartyMapper.selectByExample(psPartyExample);
        if(isHost == null) return allPsParties;//全部建设单位

        List<PsParty> psParties = new ArrayList<>();
        for(PsParty psParty : allPsParties){
            if (psParty.getIsHost() == isHost){
                psParties.add(psParty);
            }
        }
        return psParties;
    }

    //更新二级党校状态
    public void updatePsInfoStatus(Integer[] ids, Boolean isHistory, String _abolishDate){
        if(ids==null || ids.length==0) return;

        Date abolishDate = null;
        if (StringUtils.isNotBlank(_abolishDate)){
            abolishDate = DateUtils.parseDate(_abolishDate,DateUtils.YYYYMMDD_DOT);
        }
        for (Integer id : ids){
            PsInfo psInfo = new PsInfo();
            psInfo.setAbolishDate(abolishDate);
            psInfo.setIsHistory(isHistory);
            psInfo.setId(id);
            psInfo.setSortOrder(getNextSortOrder("ps_info","is_history="+isHistory));

            psInfoMapper.updateByPrimaryKeySelective(psInfo);
        }

    }
}
