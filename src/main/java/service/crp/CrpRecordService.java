package service.crp;

import domain.crp.CrpRecord;
import domain.crp.CrpRecordExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.Date;

@Service
public class CrpRecordService extends BaseMapper {


    @Transactional
    public void insertSelective(CrpRecord record) {

        crpRecordMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        crpRecordMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrpRecordExample example = new CrpRecordExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        crpRecordMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CrpRecord record) {

        crpRecordMapper.updateByPrimaryKeySelective(record);

        if((record.getType()== SystemConstants.CRP_RECORD_TYPE_OUT &&
                record.getToUnitType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_out_unit_other").getId())
         || (record.getType()== SystemConstants.CRP_RECORD_TYPE_IN &&
                record.getToUnitType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_in_unit_other").getId())
        || (record.getType()== SystemConstants.CRP_RECORD_TYPE_TRANSFER &&
                record.getToUnitType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_transfer_unit_other").getId())){
            updateMapper.excuteSql("update crp_record set to_unit=null where id=" + record.getId());
        }

        if((record.getType()== SystemConstants.CRP_RECORD_TYPE_OUT &&
                record.getTempPostType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_out_post_other").getId())
                || (record.getType()== SystemConstants.CRP_RECORD_TYPE_IN &&
                record.getTempPostType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_in_post_other").getId())
                || (record.getType()== SystemConstants.CRP_RECORD_TYPE_TRANSFER &&
                record.getTempPostType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_transfer_post_other").getId())){
            updateMapper.excuteSql("update crp_record set temp_post=null where id=" + record.getId());
        }
    }

    // 挂职结束
    public void finish(Integer id, Date realEndDate) {

        CrpRecord record = new CrpRecord();
        record.setId(id);
        record.setIsFinished(true);
        record.setRealEndDate(realEndDate);

        crpRecordMapper.updateByPrimaryKeySelective(record);
    }
}
