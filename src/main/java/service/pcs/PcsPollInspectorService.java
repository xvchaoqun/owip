package service.pcs;

import domain.member.Member;
import domain.member.MemberExample;
import domain.pcs.PcsPoll;
import domain.pcs.PcsPollInspector;
import domain.pcs.PcsPollInspectorExample;
import domain.pcs.PcsPollResultExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.PartyService;
import sys.constants.MemberConstants;

import java.util.*;

@Service
public class PcsPollInspectorService extends PcsBaseMapper {

    @Autowired
    private PartyService partyService;

    @Transactional
    public void insertSelective(PcsPollInspector record){

        pcsPollInspectorMapper.insertSelective(record);

        iPcsMapper.updatePollInspectorCount(record.getPollId());
    }

    @Transactional
    public void batchDel(Integer[] ids, Integer pollId){

        if(ids==null || ids.length==0) return;

        PcsPollResultExample example1 = new PcsPollResultExample();
        example1.createCriteria().andInspectorIdIn(Arrays.asList(ids));
        pcsPollResultMapper.deleteByExample(example1);

        PcsPollInspectorExample example = new PcsPollInspectorExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollInspectorMapper.deleteByExample(example);

        iPcsMapper.updatePollInspectorCount(pollId);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsPollInspector record){

        pcsPollInspectorMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, PcsPollInspector> findAll() {

        PcsPollInspectorExample example = new PcsPollInspectorExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PcsPollInspector> records = pcsPollInspectorMapper.selectByExample(example);
        Map<Integer, PcsPollInspector> map = new LinkedHashMap<>();
        for (PcsPollInspector record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void genInspector(Integer pollId, Integer count) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);

        for (Integer i = 0; i < count; i++) {
            PcsPollInspector record = new PcsPollInspector();
            record.setPollId(pollId);
            record.setUsername(buildUsername());
            record.setPasswd(RandomStringUtils.randomNumeric(6));
            record.setPartyId(pcsPoll.getPartyId());
            record.setBranchId(pcsPoll.getBranchId());
            record.setCreateTime(new Date());

            pcsPollInspectorMapper.insertSelective(record);
        }

        iPcsMapper.updatePollInspectorCount(pollId);

    }

    private String buildUsername() {
        String username = RandomStringUtils.random(5, "abcdefghigklmnopqrstuvwxyz")
                + RandomStringUtils.random(3, "12345678");
        PcsPollInspectorExample example = new PcsPollInspectorExample();
        example.createCriteria().andUsernameEqualTo(username);
        while (pcsPollInspectorMapper.countByExample(example) > 0){
            username = RandomStringUtils.random(5, "abcdefghigklmnopqrstuvwxyz")
                    + RandomStringUtils.random(3, "12345678");
        }

        return username;
    }

    public PcsPollInspector tryLogin(String username, String passwd) {

        username = StringUtils.trimToNull(username);
        passwd = StringUtils.trimToNull(passwd);

        PcsPollInspectorExample example = new PcsPollInspectorExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswdEqualTo(passwd);
        List<PcsPollInspector> inspectors = pcsPollInspectorMapper.selectByExample(example);

        return inspectors.size() > 0 ? inspectors.get(0) : null;
    }

    //@param politicalStatus 是正式党员还是预备党员
    //得到党支部/直属党支部的成员
    public List<Member> getBranchMember(PcsPoll pcsPoll, Byte politicalStatus) {

        Integer partyId = pcsPoll.getPartyId();
        Integer branchId = pcsPoll.getBranchId();

        MemberExample example = new MemberExample();
        MemberExample.Criteria criteria = example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);

        if (politicalStatus != null){
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }
        if (partyId != null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null){
            criteria.andBranchIdEqualTo(branchId);
        }

        /*if (partyId != null && branchId == null) {
            if (partyService.isDirectBranch(partyId)) {
                criteria.andBranchIdEqualTo(branchId);
            }
        }*/

        List<Member> members = memberMapper.selectByExample(example);

        return members;
    }

}
