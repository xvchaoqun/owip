package service.sys;

import domain.sys.SysMsg;
import domain.sys.SysMsgExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.sys.SysMsgMapper;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMsgService extends BaseMapper {

    @Autowired
    private SysMsgMapper sysMsgMapper;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        SysMsgExample example = new SysMsgExample();
        SysMsgExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return sysMsgMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(SysMsg record){

        sysMsgMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        sysMsgMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SysMsgExample example = new SysMsgExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        sysMsgMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SysMsg record){
       /* if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");*/
        sysMsgMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="SysMsg:ALL")
    public Map<Integer, SysMsg> findAll() {

        SysMsgExample example = new SysMsgExample();
        //example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<SysMsg> records = sysMsgMapper.selectByExample(example);
        Map<Integer, SysMsg> map = new LinkedHashMap<>();
        for (SysMsg record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
