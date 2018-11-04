package service.pcs;

import controller.global.OpException;
import domain.pcs.PcsConfig;
import domain.pcs.PcsConfigExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PcsConfigService extends BaseMapper {

    // 获取当前党代会
    public PcsConfig getCurrentPcsConfig() {

        PcsConfigExample example = new PcsConfigExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIsCurrentEqualTo(true);
        List<PcsConfig> pcsConfigs = pcsConfigMapper.selectByExample(example);
        if (pcsConfigs.size() > 1) throw new OpException("党代会状态异常：当前包含多个党代会");

        return (pcsConfigs.size() == 1) ? pcsConfigs.get(0) : null;
    }

    public boolean idDuplicate(Integer id, String name) {

        Assert.isTrue(StringUtils.isNotBlank(name), "名称不能为空");

        PcsConfigExample example = new PcsConfigExample();
        PcsConfigExample.Criteria criteria = example.createCriteria()
                .andIsDeletedEqualTo(false).andNameEqualTo(name);
        if (id != null) criteria.andIdNotEqualTo(id);

        return pcsConfigMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PcsConfig record) {

        if (record.getIsCurrent()) {
            commonMapper.excuteSql("update pcs_config set is_current=0");
        }

        Assert.isTrue(!idDuplicate(null, record.getName()), "名称重复");
        pcsConfigMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PcsConfigExample example = new PcsConfigExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        PcsConfig record = new PcsConfig();
        record.setIsCurrent(false);
        record.setIsDeleted(true);
        pcsConfigMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsConfig record) {

        if (StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "名称重复");

        if (record.getIsCurrent()) {
            commonMapper.excuteSql("update pcs_config set is_current=0");
        }

        pcsConfigMapper.updateByPrimaryKeySelective(record);
    }

    // 所有的党代会
    public Map<Integer, PcsConfig> findAll(){

        PcsConfigExample example = new PcsConfigExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("create_time desc");

        List<PcsConfig> pcsConfigs = pcsConfigMapper.selectByExample(example);
        Map<Integer, PcsConfig> resultMap = new HashMap<>();
        for (PcsConfig pcsConfig : pcsConfigs) {
            resultMap.put(pcsConfig.getId(), pcsConfig);
        }

        return resultMap;
    }
}
