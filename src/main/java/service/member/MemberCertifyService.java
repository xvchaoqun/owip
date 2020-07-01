package service.member;

import domain.member.Member;
import domain.member.MemberCertify;
import domain.member.MemberCertifyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MemberCertifyService extends MemberBaseMapper {

    @Transactional
    public void insertSelective(MemberCertify record){

        Member member = memberMapper.selectByPrimaryKey(record.getUserId());
        record.setPoliticalStatus(member.getPoliticalStatus());
        record.setSn(generateSn(record.getYear()));
        record.setCreateTime(new Date());

        memberCertifyMapper.insertSelective(record);
    }

    private Integer generateSn(Integer year) {

        int sn = 1;
        MemberCertifyExample example = new MemberCertifyExample();
        example.createCriteria().andYearEqualTo(year);
        example.setOrderByClause("sn desc");
        List<MemberCertify> memberCertifies = memberCertifyMapper.selectByExample(example);
        if (memberCertifies.size() > 0) {
            String _sn = String.valueOf(memberCertifies.get(0).getSn()).substring(3);
            System.out.println(_sn);
            sn = Integer.parseInt(_sn) + 1;
        }
        return Integer.valueOf(String.format("%s%03d", year, sn));
    }

    @Transactional
    public void del(Integer id){

        memberCertifyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberCertifyExample example = new MemberCertifyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberCertifyMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(MemberCertify record){

        memberCertifyMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, MemberCertify> findAll() {

        MemberCertifyExample example = new MemberCertifyExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<MemberCertify> records = memberCertifyMapper.selectByExample(example);
        Map<Integer, MemberCertify> map = new LinkedHashMap<>();
        for (MemberCertify record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
