package service.abroad;

import domain.abroad.TaiwanRecord;
import domain.abroad.TaiwanRecordExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;

@Service
public class TaiwanRecordService extends BaseMapper {

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
        example.createCriteria().andIdIn(Arrays.asList(ids));
        TaiwanRecord record = new TaiwanRecord();
        record.setIsDeleted(true);
        taiwanRecordMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(TaiwanRecord record) {

        taiwanRecordMapper.updateByPrimaryKeySelective(record);

        if(StringUtils.isNotBlank(record.getPassportCode())){
            commonMapper.excuteSql("update abroad_taiwan_record set handle_type=null, " +
                    "expect_date=null, handle_date=null where id="+ record.getId());
        }
        if(record.getHandleType()!=null && record.getHandleType()==
                SystemConstants.TAIWAN_RECORD_HANDLE_TYPE_OW){

            commonMapper.excuteSql("update abroad_taiwan_record set " +
                    "expect_date=null, handle_date=null where id="+ record.getId());
        }
    }
}
