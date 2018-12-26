package service.abroad;

import controller.global.OpException;
import domain.abroad.TaiwanRecord;
import domain.abroad.TaiwanRecordExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.AbroadConstants;

import java.util.Arrays;

@Service
public class TaiwanRecordService extends AbroadBaseMapper {

    @Transactional
    public void insertSelective(TaiwanRecord record) {

        taiwanRecordMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        taiwanRecordMapper.deleteByPrimaryKey(id);
    }

    // 假删除
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        TaiwanRecordExample example = new TaiwanRecordExample();
        example.createCriteria().andIdIn(Arrays.asList(ids))
                .andHandleDateIsNull(); // 上交证件后不可删除

        TaiwanRecord record = new TaiwanRecord();
        record.setIsDeleted(true);
        taiwanRecordMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(TaiwanRecord record) {

        Integer id = record.getId();
        TaiwanRecord _taiwanRecord = taiwanRecordMapper.selectByPrimaryKey(id);
        if(_taiwanRecord==null || _taiwanRecord.getIsDeleted()
                || _taiwanRecord.getHandleDate() != null){
            // 上交后不允许修改
            throw new OpException("因公赴台备案状态异常");
        }

        taiwanRecordMapper.updateByPrimaryKeySelective(record);

        if(StringUtils.isNotBlank(record.getPassportCode())){
            commonMapper.excuteSql("update abroad_taiwan_record set handle_type=null, " +
                    "expect_date=null, handle_date=null where id="+ id);
        }
        if(record.getHandleType()!=null && record.getHandleType()==
                AbroadConstants.ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OW){

            commonMapper.excuteSql("update abroad_taiwan_record set " +
                    "expect_date=null, handle_date=null where id=" + id);
        }
    }
}
