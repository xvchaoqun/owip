package service.dp.dpCommon;

import domain.cadre.*;
import domain.dp.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.dp.DpBaseMapper;
import sys.HttpResponseMethod;
import sys.constants.DpConstants;
import sys.constants.SystemConstants;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DpCommonService extends DpBaseMapper  implements HttpResponseMethod {

    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected MetaTypeService metaTypeService;

    @Cacheable(value = "DpParty:ALL", key = "#partyId")
    public DpParty getDpPartyByPartyId(Integer partyId) {

        DpPartyExample example = new DpPartyExample();
        example.createCriteria().andIdEqualTo(partyId);
        List<DpParty> dpParties = dpPartyMapper.selectByExampleWithRowbounds(example,new RowBounds(0, 1));

        return (dpParties.size() > 0) ? dpParties.get(0) : null;
    }

    @Cacheable(value = "DpPartyMember:ALL",key = "#groupId")
    public DpParty getDpPartyByGroupId(Integer groupId) {

        DpPartyMemberGroupExample dpPartyMemberGroupExample = new DpPartyMemberGroupExample();
        dpPartyMemberGroupExample.createCriteria().andIdEqualTo(groupId);
        List<DpPartyMemberGroup> dpPartyMemberGroups = dpPartyMemberGroupMapper.selectByExample(dpPartyMemberGroupExample);
        List<Integer> partyIds = new ArrayList<>();
        for (DpPartyMemberGroup dpPartyMemberGroup : dpPartyMemberGroups){
            partyIds.add(dpPartyMemberGroup.getPartyId());
        }

        DpPartyExample example = new DpPartyExample();
        example.createCriteria().andIdIn(partyIds);
        List<DpParty> dpParties = dpPartyMapper.selectByExampleWithRowbounds(example,new RowBounds(0, 1));

        return (dpParties.size() > 0) ? dpParties.get(0) : null;
    }

    @Transactional
    public void syncCadreInfo(Integer cls){

        List<Integer> userIds = new ArrayList<>();
        if (cls == DpConstants.DP_MEMBER_TYPE_MEMBER) {
            userIds = iDpPropertyMapper.findCadreFromdpMember();
        }else if (cls == DpConstants.DP_MEMBER_TYPE_NPM){
            userIds = iDpPropertyMapper.findCadreFromdpNpm();
        }else if (cls == DpConstants.DP_MEMBER_TYPE_OM){
            userIds = iDpPropertyMapper.findCadreFromdpOm();
        }else if (cls == DpConstants.DP_MEMBER_TYPE_NPR){
            userIds = iDpPropertyMapper.findCadreFromdpNpr();
        }else if (cls == DpConstants.DP_MEMBER_TYPE_PRCM){
            userIds = iDpPropertyMapper.findCadreFromdpPrCm();
        }
            for (Integer userId : userIds){
                CadreView cadreView = cadreService.dbFindByUserId(userId);
                Integer cadreId = cadreView.getId();
                if (userId != null &&cadreId != null) {
                    try {
                        Map<Integer, Integer> eduIds = syncEdu(userId, cadreId);
                        syncWork(userId, cadreId, eduIds);
                        syncReward(userId, cadreId);
                        syncFamily(userId, cadreId);
                        syncEva(userId, cadreId);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }

    }

    //同步干部的学习经历
    @Transactional
    public Map<Integer, Integer> syncEdu(Integer userId, Integer cadreId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        DpEduExample dpEduExample = new DpEduExample();
        dpEduExample.createCriteria().andUserIdEqualTo(userId);
        dpEduMapper.deleteByExample(dpEduExample);

        CadreEduExample cadreEduExample = new CadreEduExample();
        cadreEduExample.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExample(cadreEduExample);

        //存储<新id，旧id>
        Map<Integer, Integer> eduIds = new HashMap<>();
        for (CadreEdu cadreEdu : cadreEdus){
            DpEdu record = new DpEdu();
            PropertyUtils.copyProperties(record, cadreEdu);
            record.setId(null);
            record.setUserId(userId);
            dpEduMapper.insertSelectiveSync(record);
            if (cadreEdu.getSubWorkCount() > 0) {
                eduIds.put(record.getId(), cadreEdu.getId());
            }
        }
        return eduIds;
    }

    //同步干部的工作经历
    @Transactional
    public void syncWork(Integer userId, Integer cadreId, Map<Integer, Integer> eduIds) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        DpWorkExample dpWorkExample = new DpWorkExample();
        dpWorkExample.createCriteria().andUserIdEqualTo(userId);
        dpWorkMapper.deleteByExample(dpWorkExample);

        List<CadreWork> cadreWorks = getCadreWorks(cadreId);

        Map<Integer, Integer> workIds = new HashMap<>();
        for (CadreWork cadreWork : cadreWorks){
            DpWork record = new DpWork();
            PropertyUtils.copyProperties(record, cadreWork);
            record.setId(null);
            record.setUserId(userId);
            record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
            dpWorkMapper.insertSelectiveSync(record);
            workIds.put(record.getId(), cadreWork.getId());
            //System.out.println("000000"+record.getId());
        }

        DpWorkExample dpWorkExample1 = new DpWorkExample();
        dpWorkExample1.createCriteria().andUserIdEqualTo(userId);
        List<DpWork> dpWorks = dpWorkMapper.selectByExample(dpWorkExample1);

        for (DpWork dpWork : dpWorks) {
            Integer dpWorkId = dpWork.getId();
            if (dpWork.getIsEduWork()) {
                for (Map.Entry<Integer, Integer> entry : eduIds.entrySet()) {
                    if (entry.getValue().equals(dpWork.getFid())) {
                        dpWork.setId(dpWorkId);
                        dpWork.setFid(entry.getKey());
                    }
                }
            } else if (dpWork.getFid() != null && !dpWork.getIsEduWork()){
                for (Map.Entry<Integer, Integer> entry : workIds.entrySet()) {
                    if (entry.getValue().equals(dpWork.getFid())) {
                        dpWork.setId(dpWorkId);
                        dpWork.setFid(entry.getKey());
                    }
                }
            }
            dpWorkMapper.updateByPrimaryKeySelective(dpWork);
        }


    }

    //同步干部的奖励情况
    public void syncReward(Integer userId, Integer cadreId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        DpRewardExample dpRewardExample = new DpRewardExample();
        dpRewardExample.createCriteria().andUserIdEqualTo(userId).andRewardTypeEqualTo(metaTypeService.findByName("mc_dp_reward_type", "其他奖励").getId());
        if (dpRewardExample != null) {
            dpRewardMapper.deleteByExample(dpRewardExample);
        }

        CadreRewardExample cadreRewardExample = new CadreRewardExample();
        cadreRewardExample.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        List<CadreReward> cadreRewards = cadreRewardMapper.selectByExample(cadreRewardExample);

        for (CadreReward cadreReward : cadreRewards){
            DpReward record = new DpReward();
            //BeanUtils.copyProperties(record, cadreReward);
            record.setId(null);
            record.setUserId(userId);
            record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
            record.setRewardType(metaTypeService.findByName("mc_dp_reward_type", "其他奖励").getId());
            record.setRewardLevel(cadreReward.getRewardLevel());
            record.setRewardTime(cadreReward.getRewardTime());
            record.setName(cadreReward.getName());
            record.setUnit(cadreReward.getUnit());
            record.setProof(cadreReward.getProof());
            record.setProofFilename(cadreReward.getProofFilename());
            record.setIsIndependent(cadreReward.getIsIndependent());
            record.setRank(cadreReward.getRank());
            record.setSortOrder(cadreReward.getSortOrder());
            record.setRemark(cadreReward.getRemark());
            dpRewardMapper.insert(record);
        }

    }

    //同步干部的家庭情况
    @Transactional
    public void syncFamily(Integer userId, Integer cadreId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        DpFamilyExample dpFamilyExample = new DpFamilyExample();
        dpFamilyExample.createCriteria().andUserIdEqualTo(userId);
        dpFamilyMapper.deleteByExample(dpFamilyExample);

        CadreFamilyExample cadreFamilyExample = new CadreFamilyExample();
        cadreFamilyExample.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        List<CadreFamily> cadreFamilies = cadreFamilyMapper.selectByExample(cadreFamilyExample);

        for (CadreFamily cadreFamily : cadreFamilies){
            DpFamily record = new DpFamily();
            PropertyUtils.copyProperties(record, cadreFamily);
            record.setId(null);
            record.setUserId(userId);
            /*record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
            record.setTitle(cadreFamily.getTitle());
            record.setRealname(cadreFamily.getRealname());
            record.setBirthday(cadreFamily.getBirthday());
            record.setWithGod(cadreFamily.getWithGod());
            record.setPoliticalStatus(cadreFamily.getPoliticalStatus());
            record.setUnit(cadreFamily.getUnit());
            record.setSortOrder(cadreFamily.getSortOrder());*/
            dpFamilyMapper.insert(record);
        }
    }

    //同步干部的年度考核
    @Transactional
    public void syncEva(Integer userId, Integer cadreId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        DpEvaExample dpEvaExample = new DpEvaExample();
        dpEvaExample.createCriteria().andUserIdEqualTo(userId);
        dpEvaMapper.deleteByExample(dpEvaExample);

        CadreEvaExample cadreEvaExample = new CadreEvaExample();
        cadreEvaExample.createCriteria().andCadreIdEqualTo(cadreId);
        List<CadreEva> cadreEvas = cadreEvaMapper.selectByExample(cadreEvaExample);

        for (CadreEva cadreEva : cadreEvas){
            DpEva record = new DpEva();
            PropertyUtils.copyProperties(record, cadreEva);
            record.setId(null);
            record.setUserId(userId);
            /*record.setYear(cadreEva.getYear());
            record.setType(cadreEva.getType());
            record.setTitle(cadreEva.getTitle());
            record.setRemark(cadreEva.getRemark());*/
            dpEvaMapper.insert(record);
        }

    }

    public List<CadreWork> getCadreWorks(Integer cadreId) {
        CadreWorkExample cadreWorkExample = new CadreWorkExample();
        cadreWorkExample.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        return cadreWorkMapper.selectByExample(cadreWorkExample);
    }
}
