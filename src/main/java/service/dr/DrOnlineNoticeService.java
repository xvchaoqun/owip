package service.dr;

import domain.dr.DrOnlineNotice;
import domain.dr.DrOnlineNoticeExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrOnlineNoticeService extends DrBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DrOnlineNoticeExample example = new DrOnlineNoticeExample();
        DrOnlineNoticeExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return drOnlineNoticeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DrOnlineNotice record){

        drOnlineNoticeMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        drOnlineNoticeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DrOnlineNoticeExample example = new DrOnlineNoticeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlineNoticeMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DrOnlineNotice record){

        drOnlineNoticeMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DrOnlineNotice> findAll() {

        DrOnlineNoticeExample example = new DrOnlineNoticeExample();
        example.createCriteria();
        List<DrOnlineNotice> records = drOnlineNoticeMapper.selectByExample(example);
        Map<Integer, DrOnlineNotice> map = new LinkedHashMap<>();
        for (DrOnlineNotice record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
