package service.pcs;

import controller.global.OpException;
import domain.pcs.PcsConfig;
import domain.pcs.PcsConfigExample;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.tags.CmTag;
import sys.utils.ContentUtils;
import sys.utils.PatternUtils;
import sys.utils.DateUtils;

import java.util.*;

@Service
public class PcsConfigService extends PcsBaseMapper {

    // 党代会名称
    public String getPcsName(int pcsConfigId){

        PcsConfig pcsConfig = pcsConfigMapper.selectByPrimaryKey(pcsConfigId);
        String name = ContentUtils.trimAll(pcsConfig.getName());
        String schoolName = CmTag.getSysConfig().getSchoolName();
        String prefix1 = "中国共产党" + schoolName;
        String prefix2 = "中共" + schoolName;

        if(name.startsWith(prefix1)) return name;
        if(name.startsWith(prefix2)) return name;

        return prefix1 + name;
    }

    // 正则提取党代会届数（eg.中共xxx大学第十五次党员代表大会，提取得到“十五”）
    public String getPcsNum(int pcsConfigId){

        PcsConfig pcsConfig = pcsConfigMapper.selectByPrimaryKey(pcsConfigId);
        String name = ContentUtils.trimAll(pcsConfig.getName());

        return PatternUtils.withdraw(".*第(.*)次.*", name);
    }

    // 获取当前党代会
    public PcsConfig getCurrentPcsConfig() {

        PcsConfigExample example = new PcsConfigExample();
        example.createCriteria().andIsDeletedEqualTo(false).andIsCurrentEqualTo(true);
        List<PcsConfig> pcsConfigs = pcsConfigMapper.selectByExample(example);
        if (pcsConfigs.size() > 1) throw new OpException("党代会状态异常：当前包含多个党代会");

        return (pcsConfigs.size() == 1) ? pcsConfigs.get(0) : null;
    }

    public List<Integer> getPartyIdList(){

        PcsConfig currentPcsConfig = getCurrentPcsConfig();
        if(currentPcsConfig==null) return new ArrayList<>();

        return iPcsMapper.getPartyIdList(currentPcsConfig.getId());
    }

    public List<Integer> getBranchIdList(int partyId){

        PcsConfig currentPcsConfig = getCurrentPcsConfig();
        if(currentPcsConfig==null) return new ArrayList<>();

        return iPcsMapper.getBranchIdList(currentPcsConfig.getId(), partyId);
    }

    // 获取候选人年龄
    public Integer getCandidateAge(Date createTime, Date birth) {

        DateTime begin = new DateTime(birth);
        DateTime end = new DateTime(getAgeBaseDate(createTime));
        Period p = new Period(begin, end, PeriodType.years());

        return p.getYears();
    }

    // 年龄计算基准日期（默认为党代会创建后3个月）
    public Date getAgeBaseDate(Date createTime){

        Date _finishDate = null;
        if(createTime!=null) {
            // 假定3个月后党代会结束
            Date finishDate = DateUtils.getDateBeforeOrAfterMonthes(createTime, 3);
            if(DateUtils.compareDate(new Date(), finishDate)) { // 超过了结束时间，年龄计算冻结
                _finishDate = DateUtils.getDateBeforeOrAfterMonthes(createTime, 3);
            }
        }
        if(_finishDate==null){ // 在党代会召开期间，以当前日期为基准计算年龄
            _finishDate = new Date();
        }

        return _finishDate;
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
