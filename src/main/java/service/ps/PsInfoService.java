package service.ps;

import domain.base.MetaType;
import domain.ps.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.PsConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class PsInfoService extends PsBaseMapper {

    @Autowired
    private PsAdminService psAdminService;

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void insertSelective(PsInfo record){

        record.setSortOrder(getNextSortOrder("ps_info","is_history="+record.getIsHistory()));
        psInfoMapper.insertSelective(record);
    }

    //（假删除）
    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {

            PsInfo record = new PsInfo();
            record.setId(id);
            record.setIsDeleted(true);

            psInfoMapper.updateByPrimaryKeySelective(record);

            updatePsAdminsRole(id);
        }
    }

    @Transactional
    @CacheEvict(value="PsInfo:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(PsInfo record){

        return psInfoMapper.updateByPrimaryKeySelective(record);
    }

    // 更新某二级党校所有现任管理员的权限
    public void updatePsAdminsRole(int psId){

        PsAdminExample example = new PsAdminExample();
        example.createCriteria().andPsIdEqualTo(psId)
                .andIsHistoryEqualTo(false);

        List<PsAdmin> psAdmins = psAdminMapper.selectByExample(example);
        for (PsAdmin psAdmin : psAdmins) {

            psAdminService.updateRole(psAdmin.getUserId(), psAdmin.getType());
        }
    }

    //更新二级党校状态
    @Transactional
    public void updatePsInfoStatus(Integer[] ids, boolean isHistory, Date abolishDate){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids){
            PsInfo psInfo = new PsInfo();
            psInfo.setId(id);
            psInfo.setAbolishDate(abolishDate);
            psInfo.setIsHistory(isHistory);
            psInfo.setSortOrder(getNextSortOrder("ps_info","is_history="+isHistory));

            psInfoMapper.updateByPrimaryKeySelective(psInfo);

            updatePsAdminsRole(id);
        }
    }

    @Cacheable(value="PsInfo:ALL")
    public Map<Integer, PsInfo> findAll() {

        PsInfoExample example = new PsInfoExample();
        example.createCriteria()
                .andIsHistoryEqualTo(false)
                .andIsDeletedEqualTo(false);
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

        PsInfo entity = psInfoMapper.selectByPrimaryKey(id);
        Boolean isHistory = entity.getIsHistory();
        changeOrder("ps_info", "is_history="+isHistory, ORDER_BY_DESC, id, addNum);
    }

     @Transactional
    public int batchImport(List<PsInfo> records) {

        int addCount = 0;
        for (PsInfo record : records) {

            insertSelective(record);
            addCount++;
        }

        return addCount;
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
                .andIsFinishEqualTo(PsConstants.PS_STATUS_NOT_HISTORY);

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

    public Long getAllCountNumberById(Integer id){

        Map<String, Long> countMap = iPsMapper.count(getPartyIdList(null,id));

        if (countMap == null) {
            return null;
        }

        return countMap.get(PsConstants.COUNTNUMBER);
    }

    public String getPartyNameById(String partyIdsString){

        if (partyIdsString == null) return null;

        String[] partyIdArray = partyIdsString.split(",");
        StringBuffer partyName = new StringBuffer("");
        for (String partyIdString : partyIdArray){
            if (!StringUtils.equals(partyName,"")) partyName.append("、");

            Integer partyId = Integer.valueOf(partyIdString);
            partyName.append(partyMapper.selectByPrimaryKey(partyId).getName());
        }
        return partyName.toString();
    }
}
