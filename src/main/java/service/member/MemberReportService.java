package service.member;

import domain.member.Member;
import domain.member.MemberReport;
import domain.member.MemberReportExample;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import shiro.ShiroHelper;
import sys.helper.PartyHelper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static sys.constants.OwConstants.OW_REPORT_STATUS_UNREPORT;

@Service
public class MemberReportService extends MemberBaseMapper {

    public boolean idDuplicate(Integer id, Integer userId,Integer year){

        Assert.isTrue(year!=null, "null");

        MemberReportExample example = new MemberReportExample();
        MemberReportExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId).andYearEqualTo(year);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberReportMapper.countByExample(example) > 0;
    }
    @Transactional
    public void insertSelective(MemberReport record){

        if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),record.getPartyId())){
                throw new UnauthorizedException();
        }

        Assert.isTrue(!idDuplicate(null, record.getUserId(),record.getYear()), "duplicate");

        memberReportMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){
        MemberReport record= memberReportMapper.selectByPrimaryKey(id);
        if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),record.getPartyId())){
            throw new UnauthorizedException();
        }

        memberReportMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){
       /* if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),record.getPartyId())){
            throw new UnauthorizedException();
        }*/
        if(ids==null || ids.length==0) return;

        MemberReportExample example = new MemberReportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(OW_REPORT_STATUS_UNREPORT);
        memberReportMapper.deleteByExample(example);
    }
    @Transactional
    public void batchReport(Integer[] ids,Byte status){
       /* if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),record.getPartyId())){
            throw new UnauthorizedException();
        }*/
        if(ids==null || ids.length==0) return;

        MemberReport record =new MemberReport();
        record.setStatus(status);

        MemberReportExample example = new MemberReportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberReportMapper.updateByExampleSelective(record,example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(MemberReport record){

        MemberReport memberReport= memberReportMapper.selectByPrimaryKey(record.getId());
        if(memberReport.getBranchId()!=null){
            if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),memberReport.getPartyId(), memberReport.getBranchId())){
                throw new UnauthorizedException();
            }
        }else {
            if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),memberReport.getPartyId())){
                throw new UnauthorizedException();
            }
        }
        if(record.getUserId()!= null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getUserId(),record.getYear()), "duplicate");
        memberReportMapper.updateByPrimaryKeySelective(record);
    }
    // 批量导入
    @Transactional
    public int memberReportImport(List<MemberReport> records) throws InterruptedException {

        int addCount = 0;

        for (MemberReport record : records) {
            insertSelective(record);
            addCount++;
        }
        return addCount;
    }
    public Map<Integer, MemberReport> findAll() {

        MemberReportExample example = new MemberReportExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<MemberReport> records = memberReportMapper.selectByExample(example);
        Map<Integer, MemberReport> map = new LinkedHashMap<>();
        for (MemberReport record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
