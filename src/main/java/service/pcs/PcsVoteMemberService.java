package service.pcs;

import domain.pcs.PcsVoteMember;
import domain.pcs.PcsVoteMemberExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class PcsVoteMemberService extends PcsBaseMapper {

    @Transactional
    public void insertSelective(PcsVoteMember record) {

        pcsVoteMemberMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PcsVoteMemberExample example = new PcsVoteMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        PcsVoteMember record = new PcsVoteMember();
        pcsVoteMemberMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsVoteMember record) {

        pcsVoteMemberMapper.updateByPrimaryKeySelective(record);
    }
}
