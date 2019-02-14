package service.sc.scRecord;

import domain.sc.scMotion.ScMotion;
import domain.sc.scRecord.ScRecord;
import domain.sc.scRecord.ScRecordExample;
import domain.sc.scRecord.ScRecordView;
import domain.sc.scRecord.ScRecordViewExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ScRecordService extends ScBaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScRecordExample example = new ScRecordExample();
        ScRecordExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scRecordMapper.countByExample(example) > 0;
    }*/

    public String getNextSeq(Date holdDate) {

        ScRecordViewExample example = new ScRecordViewExample();
        ScRecordViewExample.Criteria criteria = example.createCriteria()
                .andHoldDateEqualTo(holdDate);
        example.setOrderByClause("seq desc");

        List<ScRecordView> scRecords = scRecordViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        if(scRecords.size()==0) return DateUtils.formatDate(holdDate, "yyyyMMdd0101");

        ScRecordView scRecord = scRecords.get(0);
        String seq = scRecord.getSeq();

        return DateUtils.formatDate(holdDate, "yyyyMMdd01")
                + String.format("%02d", Integer.valueOf(seq.substring(10, 12))+1);
    }

    @Transactional
    public void insertSelective(ScRecord record){

        Integer motionId = record.getMotionId();
        ScMotion scMotion = scMotionMapper.selectByPrimaryKey(motionId);
        record.setSeq(getNextSeq(scMotion.getHoldDate()));

        scRecordMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scRecordMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScRecordExample example = new ScRecordExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scRecordMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScRecord record){
        return scRecordMapper.updateByPrimaryKeySelective(record);
    }
}
