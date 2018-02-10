package service.analysis;

import bean.analysis.MemberStatByPartyBean;
import bean.analysis.StatByteBean;
import bean.analysis.StatIntBean;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import sys.constants.MemberConstants;
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

    // 党员总数（默认前二十）
    public List<MemberStatByPartyBean> partyMap(Integer top){
        if(top==null) top = 20;
        return statMemberMapper.memberApply_groupByPartyId(top);
    }

    // 按阶段统计入党申请
    public Map politicalStatusMap(Integer partyId, Integer branchId){

        Map<Byte, Integer> _map = new HashMap<>();
        List<StatByteBean> statByteBeans = statMemberMapper.member_groupByPoliticalStatus(partyId, branchId);
        for (StatByteBean statByteBean : statByteBeans) {
            _map.put(statByteBean.getType(), statByteBean.getNum());
        }

        Map<Byte, Integer> map = new LinkedHashMap<>();
        for (Byte key : MemberConstants.MEMBER_POLITICAL_STATUS_MAP.keySet()) {
            map.put(key, _map.get(key));
        }

        return map;
    }

    // 按类型统计入党申请
    public Map typeMap(Byte politicalStatus, Integer partyId, Integer branchId){

        Map<Byte, Integer> _map = new HashMap<>();
        List<StatByteBean> statByteBeans = statMemberMapper.member_groupByType(politicalStatus, partyId, branchId);
        for (StatByteBean statByteBean : statByteBeans) {
            _map.put(statByteBean.getType(), statByteBean.getNum());
        }

        Map<Byte, Integer> map = new LinkedHashMap<>();
        for (Byte key : MemberConstants.MEMBER_TYPE_MAP.keySet()) {
            map.put(key, _map.get(key));
        }

        return map;
    }

    // 按阶段统计入党申请
    public Map applyMap(Byte type, Integer partyId, Integer branchId){

        Map<Byte, Integer> _applyMap = new HashMap<>();
        List<StatByteBean> statByteBeans = statMemberMapper.memberApply_groupByStage(type, partyId, branchId);
        for (StatByteBean statByteBean : statByteBeans) {
            _applyMap.put(statByteBean.getType(), statByteBean.getNum());
        }

        Map<Byte, Integer> applyMap = new LinkedHashMap<>();
        for (Byte key : SystemConstants.APPLY_STAGE_MAP.keySet()) {
            Integer count = _applyMap.get(key);
            if(key==SystemConstants.APPLY_STAGE_INIT){
                Integer initCount = _applyMap.get(SystemConstants.APPLY_STAGE_INIT);
                Integer passCount = _applyMap.get(SystemConstants.APPLY_STAGE_PASS);
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
            Integer _year = statIntBean.getType();
            /*byte key = MemberConstants.MEMBER_AGE_0; // 未知年龄
            if(_year!=null){
                if(_year > year-20){ // 20岁及以下
                    key = MemberConstants.MEMBER_AGE_20;
                }else if(_year > year-30){ // 21~30
                    key = MemberConstants.MEMBER_AGE_21_30;
                }else if(_year > year-40){ // 31~40
                    key = MemberConstants.MEMBER_AGE_31_40;
                }else if(_year > year-50){ // 41~50
                    key = MemberConstants.MEMBER_AGE_41_50;
                }else{ // 51及以上
                    key = MemberConstants.MEMBER_AGE_51;
                }
            }*/
            byte key = MemberConstants.getMemberAgeRange(_year);
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
}
