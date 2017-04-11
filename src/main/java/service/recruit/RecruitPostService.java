package service.recruit;

import domain.recruit.RecruitPost;
import domain.recruit.RecruitPostExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;

@Service
public class RecruitPostService extends BaseMapper {


    @Transactional
    public void insertSelective(RecruitPost record){

        recruitPostMapper.insertSelective(record);
    }


    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        RecruitPostExample example = new RecruitPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        RecruitPost record = new RecruitPost();
        record.setStatus(SystemConstants.RECRUIT_POST_STATUS_DELETE);
        recruitPostMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(RecruitPost record){

        return recruitPostMapper.updateByPrimaryKeySelective(record);
    }
}
