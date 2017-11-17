package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdNormValue;
import domain.pmd.PmdNormValueExample;
import domain.pmd.PmdNormValueLog;
import domain.pmd.PmdNormValueLogExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PmdNormValueService extends BaseMapper {

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
    public void use(int id) {

        PmdNormValue pmdNormValue = pmdNormValueMapper.selectByPrimaryKey(id);
        Date now = new Date();
        int userId = ShiroHelper.getCurrentUserId();

        {   // 先关闭已启用的额度
            PmdNormValue currentPmdNormValue = getCurrentPmdNormValue(pmdNormValue.getNormId());
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
