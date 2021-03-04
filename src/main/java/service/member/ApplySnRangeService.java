package service.member;

import controller.global.OpException;
import domain.member.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class ApplySnRangeService extends MemberBaseMapper {

    @Autowired
    private MemberApplyService memberApplyService;

    public String getDisplaySn(String prefix, long sn, int len){

        return StringUtils.trimToEmpty(prefix) + String.format("%0"+ len + "d", sn);
    }

    @Transactional
    public void insertSelective(ApplySnRange record){

        record.setSortOrder(getNextSortOrder("ow_apply_sn_range", "year="+record.getYear()));
        applySnRangeMapper.insertSelective(record);

        long startSn = record.getStartSn();
        long endSn = record.getEndSn();
        for (long i = startSn; i <= endSn; i++) {

            ApplySn applySn = new ApplySn();
            applySn.setRangeId(record.getId());
            applySn.setYear(record.getYear());
            applySn.setSn(i);
            applySn.setIsUsed(false);
            applySn.setDisplaySn(getDisplaySn(record.getPrefix(), i, record.getLen()));

            applySnMapper.insertSelective(applySn);
        }
    }

    public ApplySnRange get(int id) {

        return applySnRangeMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {

            ApplySnRange applySnRange = applySnRangeMapper.selectByPrimaryKey(id);

            ApplySnExample example = new ApplySnExample();
            example.createCriteria().andRangeIdEqualTo(id).andIsUsedEqualTo(true);
            long useCount = applySnMapper.countByExample(example);

            if(applySnRange.getUseCount()>0 || useCount > 0){

                String startSn = getDisplaySn(applySnRange.getPrefix(), applySnRange.getStartSn(), applySnRange.getLen());
                String endSn = getDisplaySn(applySnRange.getPrefix(), applySnRange.getEndSn(), applySnRange.getLen());
                throw new OpException("编码段{0}~{1}已被部分使用，不可删除。", startSn, endSn);
            }
        }

        ApplySnRangeExample example = new ApplySnRangeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        applySnRangeMapper.deleteByExample(example);
    }

    // 用于更新编码端本身的信息
    @Transactional
    public void updateByPrimaryKeySelective(ApplySnRange record){

        long startSn = record.getStartSn();
        long endSn = record.getEndSn();
        int rangeId = record.getId();

        ApplySnRange oldApplySnRange = applySnRangeMapper.selectByPrimaryKey(record.getId());
        if(startSn != oldApplySnRange.getStartSn() || endSn != oldApplySnRange.getEndSn()){

            ApplySnExample example = new ApplySnExample();
            example.createCriteria().andRangeIdEqualTo(rangeId)
                    .andSnNotBetween(startSn, endSn).andIsUsedEqualTo(true);
            if(applySnMapper.countByExample(example)>0){
                throw new OpException("存在已被使用的编码在修改后的编码段之外", startSn, endSn);
            }
        }

        { // 删除修改后的编码段之外的未使用编码
            ApplySnExample example = new ApplySnExample();
            example.createCriteria().andRangeIdEqualTo(rangeId)
                    .andSnNotBetween(startSn, endSn).andIsUsedEqualTo(false);
            applySnMapper.deleteByExample(example);
        }

        for (long i = startSn; i <= endSn; i++) {

            ApplySn checkApplySn = iMemberMapper.getApplySnByRangeId(rangeId, i);
            if(checkApplySn==null){

                ApplySn applySn = new ApplySn();
                applySn.setRangeId(record.getId());
                applySn.setYear(record.getYear());
                applySn.setSn(i);
                applySn.setIsUsed(false);
                applySn.setDisplaySn(getDisplaySn(record.getPrefix(), i, record.getLen()));

                applySnMapper.insertSelective(applySn);
            }else{

                ApplySn applySn = new ApplySn();
                applySn.setId(checkApplySn.getId());

                applySn.setRangeId(record.getId());
                applySn.setYear(record.getYear());
                applySn.setDisplaySn(getDisplaySn(record.getPrefix(), i, record.getLen()));

                applySnMapper.updateByPrimaryKeySelective(applySn);

                if(BooleanUtils.isTrue(checkApplySn.getIsUsed())){

                    MemberApply memberApply = new MemberApply();
                    memberApply.setUserId(checkApplySn.getUserId());
                    memberApply.setApplySn(applySn.getDisplaySn());

                    memberApplyService.updateByPrimaryKeySelective(memberApply);
                }
            }
        }

        applySnRangeMapper.updateByPrimaryKeySelective(record);
    }

    // 用于更新编码端使用情况
    @Transactional
    public void updateCount(int rangeId){

        iMemberMapper.updateApplySnRangeCount(rangeId);
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        ApplySnRange entity = applySnRangeMapper.selectByPrimaryKey(id);
        changeOrder("ow_apply_sn_range", "year="+entity.getYear(), ORDER_BY_DESC, id, addNum);
    }
}
