package service.party;

import bean.CadreResume;
import domain.party.PartyPost;
import domain.party.PartyPostExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PartyPostService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PartyPostExample example = new PartyPostExample();
        PartyPostExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyPostMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PartyPost record){

        //Assert.isTrue(!idDuplicate(null, String.valueOf(record.getUserId())), "duplicate");
        //record.setSortOrder(getNextSortOrder("ow_party_post", null));
        partyPostMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        partyPostMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyPostExample example = new PartyPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyPostMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PartyPost record){
        //if(StringUtils.isNotBlank(String.valueOf(record.getId())))
            //Assert.isTrue(!idDuplicate(record.getId(), String.valueOf(record.getId())), "duplicate");
        partyPostMapper.updateByPrimaryKeySelective(record);
    }

    // 读取党内任职经历简历（近三年）
    public List<CadreResume> resume(int userId) {

        List<CadreResume> resumes = new ArrayList<>();
        PartyPostExample example = new PartyPostExample();
        example.createCriteria().andUserIdEqualTo(userId).andEndDateInYears(new Date(), 3);
        example.setOrderByClause("start_date asc");
        List<PartyPost> partyPosts = partyPostMapper.selectByExample(example);

        for (PartyPost record : partyPosts) {

            CadreResume resume = new CadreResume();
            resume.setIsWork(true);
            resume.setStartDate(record.getStartDate());
            resume.setEndDate(record.getEndDate());
            resume.setDetail(record.getDetail());

            resumes.add(resume);
        }

        return resumes;
    }
}
