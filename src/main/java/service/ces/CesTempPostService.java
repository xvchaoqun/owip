package service.ces;

import domain.ces.CesTempPost;
import domain.ces.CesTempPostExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.Date;

@Service
public class CesTempPostService extends BaseMapper {


    @Transactional
    public void insertSelective(CesTempPost record) {

        //record.setSortOrder(getNextSortOrder("ces_temp_post", "1=1"));
        cesTempPostMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cesTempPostMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CesTempPostExample example = new CesTempPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cesTempPostMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CesTempPost record) {

        cesTempPostMapper.updateByPrimaryKeySelective(record);

        if((record.getType()== SystemConstants.CES_TEMP_POST_TYPE_OUT &&
                record.getToUnitType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_out_unit_other").getId())
         || (record.getType()== SystemConstants.CES_TEMP_POST_TYPE_IN &&
                record.getToUnitType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_in_unit_other").getId())
        || (record.getType()== SystemConstants.CES_TEMP_POST_TYPE_TRANSFER &&
                record.getToUnitType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_transfer_unit_other").getId())){
            updateMapper.excuteSql("update ces_temp_post set to_unit=null where id=" + record.getId());
        }

        if((record.getType()== SystemConstants.CES_TEMP_POST_TYPE_OUT &&
                record.getTempPostType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_out_post_other").getId())
                || (record.getType()== SystemConstants.CES_TEMP_POST_TYPE_IN &&
                record.getTempPostType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_in_post_other").getId())
                || (record.getType()== SystemConstants.CES_TEMP_POST_TYPE_TRANSFER &&
                record.getTempPostType().intValue()!= CmTag.getMetaTypeByCode("mt_temppost_transfer_post_other").getId())){
            updateMapper.excuteSql("update ces_temp_post set temp_post=null where id=" + record.getId());
        }
    }

    // 挂职结束
    public void finish(Integer id, Date realEndDate) {

        CesTempPost record = new CesTempPost();
        record.setId(id);
        record.setIsFinished(true);
        record.setRealEndDate(realEndDate);

        cesTempPostMapper.updateByPrimaryKeySelective(record);
    }
}
