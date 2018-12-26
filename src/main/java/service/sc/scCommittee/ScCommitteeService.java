package service.sc.scCommittee;

import domain.sc.scCommittee.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.cadre.CadreService;
import service.sc.ScBaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ScCommitteeService extends ScBaseMapper {

    @Autowired
    private CadreService cadreService;

    public List<ScCommittee> findAll(){

        ScCommitteeExample example = new ScCommitteeExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("hold_date desc");
        return  scCommitteeMapper.selectByExample(example);
    }

    public ScCommitteeView getView(int id){

        ScCommitteeViewExample example = new ScCommitteeViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<ScCommitteeView> scCommitteeViews = scCommitteeViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return scCommitteeViews.size()==1?scCommitteeViews.get(0):null;
    }

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScCommitteeExample example = new ScCommitteeExample();
        ScCommitteeExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scCommitteeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScCommittee record, List<ScCommitteeMember> scCommitteeMembers){

        record.setCommitteeMemberCount(cadreService.countCommitteeMember());

        scCommitteeMapper.insertSelective(record);

        updateUsers(record.getId(), scCommitteeMembers);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScCommitteeExample example = new ScCommitteeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScCommittee record = new ScCommittee();
        record.setIsDeleted(true);
        scCommitteeMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScCommittee record, List<ScCommitteeMember> scCommitteeMembers){

        scCommitteeMapper.updateByPrimaryKeySelective(record);

        updateUsers(record.getId(), scCommitteeMembers);
    }

    // 获取所有常委
    public List<ScCommitteeMemberView> getMemberList(int committeeId, Boolean isAbsent) {

        ScCommitteeMemberViewExample example = new ScCommitteeMemberViewExample();
        ScCommitteeMemberViewExample.Criteria criteria = example.createCriteria().andCommitteeIdEqualTo(committeeId);
        if(isAbsent!=null){
            criteria.andIsAbsentEqualTo(isAbsent);
        }
        example.setOrderByClause("is_absent asc, id asc");

        return scCommitteeMemberViewMapper.selectByExample(example);
    }

    // 更新常委
    @Transactional
    public void updateUsers(Integer committeeId, List<ScCommitteeMember> scCommitteeMembers) {

        {
            ScCommitteeMemberExample example = new ScCommitteeMemberExample();
            ScCommitteeMemberExample.Criteria criteria = example.createCriteria().andCommitteeIdEqualTo(committeeId);

            scCommitteeMemberMapper.deleteByExample(example);
        }
        if (scCommitteeMembers == null || scCommitteeMembers.size() == 0) return;

        for (ScCommitteeMember record : scCommitteeMembers) {

            record.setCommitteeId(committeeId);
            scCommitteeMemberMapper.insertSelective(record);
        }
    }

}
