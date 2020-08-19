package service.pcs;

import domain.pcs.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PcsPollInspectorService extends PcsBaseMapper {

    @Autowired
    private PcsBranchService pcsBranchService;

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

    // 根据投票信息得到党代会的党支部
    public PcsBranch getPcsBranch(PcsPoll pcsPoll) {

        int configId = pcsPoll.getConfigId();
        int partyId = pcsPoll.getPartyId();
        Integer branchId = pcsPoll.getBranchId();

        return pcsBranchService.get(configId, partyId, branchId);
    }

}
