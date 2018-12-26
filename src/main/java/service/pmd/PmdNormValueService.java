package service.pmd;

import controller.global.OpException;
import domain.pmd.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PmdNormValueService extends PmdBaseMapper {

    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdConfigMemberService pmdConfigMemberService;

    public PmdNormValue getCurrentPmdNormValue(int normId){

        PmdNormValueExample example = new PmdNormValueExample();
        example.createCriteria().andNormIdEqualTo(normId)
                .andIsEnabledEqualTo(true);

        List<PmdNormValue> pmdNormValues = pmdNormValueMapper.selectByExample(example);
        if(pmdNormValues.size()>1){
            throw new OpException("数据异常，请稍后重试。");
        }
        return pmdNormValues.size()==0?null:pmdNormValues.get(0);
    }

    // 启用额度
    @Transactional
    @CacheEvict(value = "PmdConfigMember", allEntries = true)
    public void use(int id) {

        PmdNormValue pmdNormValue = pmdNormValueMapper.selectByPrimaryKey(id);
        int normId = pmdNormValue.getNormId();
        PmdNorm pmdNorm = pmdNormMapper.selectByPrimaryKey(normId);
        BigDecimal amount = pmdNormValue.getAmount();
        Date now = new Date();
        int userId = ShiroHelper.getCurrentUserId();

        {   // 先关闭已启用的额度
            PmdNormValue currentPmdNormValue = getCurrentPmdNormValue(normId);
            if(currentPmdNormValue!=null){
                Integer currentPmdNormValueId = currentPmdNormValue.getId();
                {
                    PmdNormValueLogExample example = new PmdNormValueLogExample();
                    example.createCriteria().andNormValueIdEqualTo(currentPmdNormValueId);
                    example.setOrderByClause("start_time desc");
                    List<PmdNormValueLog> pmdNormValueLogs =
                            pmdNormValueLogMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

                    PmdNormValueLog record = new PmdNormValueLog();
                    record.setId(pmdNormValueLogs.get(0).getId());
                    record.setEndTime(now);
                    record.setEndUserId(userId);
                    pmdNormValueLogMapper.updateByPrimaryKeySelective(record);
                }
                {
                    PmdNormValue record = new PmdNormValue();
                    record.setId(currentPmdNormValueId);
                    record.setIsEnabled(false);
                    pmdNormValueMapper.updateByPrimaryKeySelective(record);
                }
            }
        }

        {
            PmdNormValue record = new PmdNormValue();
            record.setId(id);
            record.setIsEnabled(true);
            pmdNormValueMapper.updateByPrimaryKeySelective(record);
        }
        {
            PmdNormValueLog record = new PmdNormValueLog();
            record.setNormValueId(id);
            record.setStartTime(now);
            record.setStartUserId(userId);
            pmdNormValueLogMapper.insertSelective(record);
        }


        if(pmdNorm.getType()== PmdConstants.PMD_NORM_TYPE_PAY) {

            PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();

            // 更新缴费党员库
            PmdConfigMemberTypeExample example = new PmdConfigMemberTypeExample();
            example.createCriteria().andNormIdEqualTo(normId);
            List<PmdConfigMemberType> pmdConfigMemberTypes = pmdConfigMemberTypeMapper.selectByExample(example);
            for (PmdConfigMemberType pmdConfigMemberType : pmdConfigMemberTypes) {

                int configMemberTypeId = pmdConfigMemberType.getId();

                PmdConfigMember record = new PmdConfigMember();
                record.setDuePay(amount);
                record.setHasReset(true);
                PmdConfigMemberExample example2 = new PmdConfigMemberExample();
                example2.createCriteria().andConfigMemberTypeIdEqualTo(configMemberTypeId);

                pmdConfigMemberMapper.updateByExampleSelective(record, example2);

                // 更新当月缴费情况（未缴费的）
                PmdMemberExample example3 = new PmdMemberExample();
                example3.createCriteria().andMonthIdEqualTo(currentPmdMonth.getId())
                        .andConfigMemberTypeIdEqualTo(configMemberTypeId)
                        .andHasPayEqualTo(false);
                List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example3);
                for (PmdMember pmdMember : pmdMembers) {

                    pmdConfigMemberService.updatePmdMemberDuePay(pmdMember.getUserId(), amount, "修改额度");
                }
            }
        }
    }

    @Transactional
    public void insertSelective(PmdNormValue record){

        pmdNormValueMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        pmdNormValueMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PmdNormValueExample example = new PmdNormValueExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdNormValueMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdNormValue record){
        return pmdNormValueMapper.updateByPrimaryKeySelective(record);
    }
}
