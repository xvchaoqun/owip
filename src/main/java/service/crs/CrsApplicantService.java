package service.crs;

import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;

@Service
public class CrsApplicantService extends BaseMapper {

    public boolean idDuplicate(Integer id, int postId, int userId) {

        CrsApplicantExample example = new CrsApplicantExample();
        CrsApplicantExample.Criteria criteria = example.createCriteria().andPostIdEqualTo(postId).andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return crsApplicantMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CrsApplicant record) {

        record.setInfoCheckStatus(SystemConstants.CRS_APPLICANT_INFO_CHECK_STATUS_INIT);

        Assert.isTrue(!idDuplicate(null, record.getPostId(), record.getUserId()), "报名重复");
        crsApplicantMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        crsApplicantMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsApplicantExample example = new CrsApplicantExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        crsApplicantMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrsApplicant record) {
        //Assert.isTrue(!idDuplicate(record.getId(), record.getPostId(), record.getUserId()), "报名重复");
        record.setPostId(null);
        record.setUserId(null);
        return crsApplicantMapper.updateByPrimaryKeySelective(record);
    }
}
