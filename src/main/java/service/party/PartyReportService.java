package service.party;

import domain.party.PartyReport;
import domain.party.PartyReportExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.helper.PartyHelper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartyReportService extends BaseMapper {

    public boolean idDuplicate(Integer id,Integer partyId, Integer branchId, Integer year) {

        Assert.isTrue(year != null, "null");
        PartyReportExample example = new PartyReportExample();
        PartyReportExample.Criteria criteria = example.createCriteria();
            if (id != null)
                criteria.andIdNotEqualTo(id);
            if (year != null)
                criteria.andYearEqualTo(year);
            if (partyId != null)
                criteria.andPartyIdEqualTo(partyId);
            if (branchId != null)
                criteria.andBranchIdEqualTo(branchId);
        return partyReportMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PartyReport record) {
        if (!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(), record.getPartyId(),record.getBranchId())) {
            throw new UnauthorizedException();
        }
        Assert.isTrue(!idDuplicate(null, record.getPartyId(),record.getBranchId(), record.getYear()), "党支部重复");
       /* if (ShiroHelper.hasRole(RoleConstants.ROLE_ODADMIN)) {
            record.setStatus(OW_REPORT_STATUS_REPORT);
        }*/
        partyReportMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        PartyReport record = partyReportMapper.selectByPrimaryKey(id);
        if (!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(), record.getPartyId(), record.getBranchId())) {
            throw new UnauthorizedException();
        }
        partyReportMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PartyReportExample example = new PartyReportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyReportMapper.deleteByExample(example);
    }

    @Transactional
    public void delFile(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        commonMapper.excuteSql("update ow_party_report set eva_result=null,eva_file=null where id in("
                + StringUtils.join(ids, ",") + ")");

    }

    @Transactional
    public void batchReport(Integer[] ids,Byte status){
       /* if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),record.getPartyId())){
            throw new UnauthorizedException();
        }*/
        if(ids==null || ids.length==0) return;

        PartyReport record =new PartyReport();
        record.setStatus(status);

        PartyReportExample example = new PartyReportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyReportMapper.updateByExampleSelective(record,example);
    }
    @Transactional
    public void updateByPrimaryKeySelective(PartyReport record) {
        PartyReport partyReport = partyReportMapper.selectByPrimaryKey(record.getId());
        if (!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(), partyReport.getPartyId(), partyReport.getBranchId())) {
            throw new UnauthorizedException();
        }
        if (record.getPartyId() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getPartyId(), record.getBranchId(), record.getYear()), "duplicate");
        partyReportMapper.updateByPrimaryKeySelective(record);
    }

    // 批量导入
    @Transactional
    public int partyReportImport(List<PartyReport> records) throws InterruptedException {

        int addCount = 0;

        for (PartyReport record : records) {
            insertSelective(record);
            addCount++;
        }
        return addCount;
    }

    public Map<Integer, PartyReport> findAll() {

        PartyReportExample example = new PartyReportExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PartyReport> records = partyReportMapper.selectByExample(example);
        Map<Integer, PartyReport> map = new LinkedHashMap<>();
        for (PartyReport record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
