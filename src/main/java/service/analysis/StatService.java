package service.analysis;

import persistence.member.common.MemberStatByBranchBean;
import persistence.member.common.MemberStatByPartyBean;
import bean.StatByteBean;
import bean.StatIntBean;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/8/1.
 */
@Service
public class StatService extends BaseMapper{

    // 以分党委分类的党员总数（默认前二十）
    public List<MemberStatByPartyBean> partyMap(Integer top){
        if(top==null) top = 20;
        return statMemberMapper.memberApply_groupByPartyId(top);
    }

    //分党委下党支部分类的党员总数
    public List<MemberStatByBranchBean> branchMap(Integer partyId){

        return statMemberMapper.memberApply_groupByBranchId(partyId);
    }

    // 按阶段统计党员发展
    public Map politicalStatusMap(Integer partyId, Integer branchId){

        Map<Byte, Integer> _map = new HashMap<>();
        List<StatByteBean> statByteBeans = statMemberMapper.member_groupByPoliticalStatus(partyId, branchId);
        for (StatByteBean statByteBean : statByteBeans) {
            _map.put(statByteBean.getGroupBy(), statByteBean.getNum());
        }

        Map<Byte, Integer> map = new LinkedHashMap<>();
        for (Byte key : MemberConstants.MEMBER_POLITICAL_STATUS_MAP.keySet()) {
            map.put(key, _map.get(key));
        }

        return map;
    }

    // 按类型统计党员发展
    public Map typeMap(Byte politicalStatus, Integer partyId, Integer branchId){

        Map<Byte, Integer> _map = new HashMap<>();
        List<StatByteBean> statByteBeans = statMemberMapper.member_groupByType(politicalStatus, partyId, branchId);
        for (StatByteBean statByteBean : statByteBeans) {
            _map.put(statByteBean.getGroupBy(), statByteBean.getNum());
        }

        Map<Byte, Integer> map = new LinkedHashMap<>();
        for (Byte key : MemberConstants.MEMBER_TYPE_MAP.keySet()) {
            map.put(key, _map.get(key));
        }

        return map;
    }

    // 按阶段统计党员发展
    public Map applyMap(Byte type, Integer partyId, Integer branchId){

        Map<Byte, Integer> _applyMap = new HashMap<>();
        List<StatByteBean> statByteBeans = statMemberMapper.memberApply_groupByStage(type, partyId, branchId);
        for (StatByteBean statByteBean : statByteBeans) {
            _applyMap.put(statByteBean.getGroupBy(), statByteBean.getNum());
        }

        Map<Byte, Integer> applyMap = new LinkedHashMap<>();
        for (Byte key : OwConstants.OW_APPLY_STAGE_MAP.keySet()) {
            Integer count = _applyMap.get(key);
            if(key==OwConstants.OW_APPLY_STAGE_INIT){
                Integer initCount = _applyMap.get(OwConstants.OW_APPLY_STAGE_INIT);
                Integer passCount = _applyMap.get(OwConstants.OW_APPLY_STAGE_PASS);
                count = (initCount!=null?initCount:0) + (passCount!=null?passCount:0);
            }
            applyMap.put(key, count);
        }

        return applyMap;
    }
    // 按年龄结构统计
    public Map ageMap(Byte type, Integer partyId, Integer branchId){

        Map<Byte, Integer> _ageMap = new HashMap<>();
        List<StatIntBean> statIntBeans = new ArrayList<>();

        if (type==null || type == MemberConstants.MEMBER_TYPE_TEACHER)
            statIntBeans.addAll(statMemberMapper.member_teatcherGroupByBirth(partyId, branchId));
        if (type==null || type == MemberConstants.MEMBER_TYPE_STUDENT)
            statIntBeans.addAll(statMemberMapper.member_studentGroupByBirth(partyId, branchId));

        //int year = DateUtils.getCurrentYear();
        for (StatIntBean statIntBean : statIntBeans) {
            Integer age = statIntBean.getGroupBy();
            byte key = MemberConstants.getMemberAgeRange(age);
            Integer total = _ageMap.get(key);
            total = (total==null)?statIntBean.getNum():(total+statIntBean.getNum());
            _ageMap.put(key, total);
        }

        Map<Byte, Integer> ageMap = new LinkedHashMap<>();
        for (Byte key : MemberConstants.MEMBER_AGE_MAP.keySet()) {
            ageMap.put(key, _ageMap.get(key));
        }

        return ageMap;
    }

    //统计支部类型
    public Map branchTypeMap(Integer partyId){

        Map<Integer,Integer> branchTypeMap = new HashMap();
        List<String> branchTypes = statMemberMapper.getBranchTypes(partyId);
        for (String branchType : branchTypes){

            for (String type : branchType.split(",")){

                Integer key = Integer.parseInt(type);
                Integer value = branchTypeMap.get(key);

                if (value == null){

                    branchTypeMap.put(key,1);
                }else {

                    branchTypeMap.put(key,value+1);
                }
            }
        }
        return branchTypeMap;
    }

    //党员其他类型统计 1.性别 2.民族
    public Map otherMap(Integer type,Integer partyId){

        Map otherMap = new LinkedHashMap();
        if (type == 1){
            List<StatIntBean> others = statMemberMapper.member_countGroupByGender(partyId);

            for (StatIntBean other : others){

                if (other.getGroupBy()==null || other.getGroupBy()== SystemConstants.GENDER_UNKNOWN){
                    otherMap.put("无数据",other.getNum());
                }else if (other.getGroupBy()==SystemConstants.GENDER_MALE){
                    otherMap.put("男",other.getNum());
                }else if (other.getGroupBy()==SystemConstants.GENDER_FEMALE){
                    otherMap.put("女",other.getNum());
                }
            }
        }if (type == 2){

            otherMap.put("汉族",statMemberMapper.countHan(partyId));
            otherMap.put("少数民族",statMemberMapper.countMinority(partyId));
            otherMap.put("无数据",statMemberMapper.countNull(partyId));
        }

        return otherMap;
    }
}
