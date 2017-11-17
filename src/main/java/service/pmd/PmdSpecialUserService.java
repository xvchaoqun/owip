package service.pmd;

import bean.XlsPmdSpecialUser;
import controller.global.OpException;
import domain.pmd.PmdSpecialUser;
import domain.pmd.PmdSpecialUserExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmdSpecialUserService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "工作证号为空");

        PmdSpecialUserExample example = new PmdSpecialUserExample();
        PmdSpecialUserExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return pmdSpecialUserMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="PmdSpecialUser:ALL", allEntries = true)
    public void insertSelective(PmdSpecialUser record) {

        if(idDuplicate(null, record.getCode()))
                throw new OpException("工作证号重复："+ record.getCode() + "-" +record.getRealname());
        pmdSpecialUserMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PmdSpecialUser:ALL", allEntries = true)
    public void del(Integer id) {

        pmdSpecialUserMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PmdSpecialUser:ALL", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PmdSpecialUserExample example = new PmdSpecialUserExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdSpecialUserMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PmdSpecialUser:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(PmdSpecialUser record) {
        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "工作证号为空");
        return pmdSpecialUserMapper.updateByPrimaryKeySelective(record);
    }

    // <code, PmdSpecialUser>
    @Cacheable(value="PmdSpecialUser:ALL")
    public Map<String, PmdSpecialUser> findAll() {

        PmdSpecialUserExample example = new PmdSpecialUserExample();
        example.createCriteria();
        List<PmdSpecialUser> pmdSpecialUseres = pmdSpecialUserMapper.selectByExample(example);
        Map<String, PmdSpecialUser> map = new LinkedHashMap<>();
        for (PmdSpecialUser pmdSpecialUser : pmdSpecialUseres) {
            map.put(pmdSpecialUser.getCode().trim(), pmdSpecialUser);
        }

        return map;
    }

    @Transactional
    @CacheEvict(value="PmdSpecialUser:ALL", allEntries = true)
    public int imports(final List<XlsPmdSpecialUser> beans) {

        int success = 0;
        for (XlsPmdSpecialUser uRow : beans) {

            if(idDuplicate(null, uRow.getCode())){
                continue;
            }
            PmdSpecialUser record = new PmdSpecialUser();
            record.setCode(uRow.getCode());
            record.setRealname(uRow.getRealname());
            record.setUnit(uRow.getUnit());
            record.setType(uRow.getType());

            insertSelective(record);
            success++;
        }

        return success;
    }
}
