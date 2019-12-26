package service.party;

import domain.party.PartyReport;
import domain.party.PartyReportExample;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static sys.constants.OwConstants.OW_REPORT_STATUS_REPORT;

@Service
public class PartyReportService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer partyId, Integer year) {

        Assert.isTrue(year != null, "null");

        PartyReportExample example = new PartyReportExample();
        PartyReportExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId).andYearEqualTo(year);
        if (id != null) criteria.andIdNotEqualTo(id);

        return partyReportMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PartyReport record) {
        if (!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(), record.getPartyId())) {
            throw new UnauthorizedException();
        }
        Assert.isTrue(!idDuplicate(null, record.getPartyId(), record.getYear()), "duplicate");
        if (ShiroHelper.hasRole(RoleConstants.ROLE_ODADMIN)) {
            record.setStatus(OW_REPORT_STATUS_REPORT);
        }
        partyReportMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        PartyReport record = partyReportMapper.selectByPrimaryKey(id);
        if (!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(), record.getPartyId())) {
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
    public void updateByPrimaryKeySelective(PartyReport record) {
        PartyReport partyReport = partyReportMapper.selectByPrimaryKey(record.getId());
        if (!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(), partyReport.getPartyId())) {
            throw new UnauthorizedException();
        }
        if (record.getPartyId() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getPartyId(), record.getYear()), "duplicate");
        partyReportMapper.updateByPrimaryKeySelective(record);
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
