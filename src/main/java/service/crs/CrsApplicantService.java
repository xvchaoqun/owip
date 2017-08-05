package service.crs;

import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantExample;
import mixin.MixinUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;

@Service
public class CrsApplicantService extends BaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;

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

        sysApprovalLogService.add(record.getId(), record.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "添加报名人员", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                JSONUtils.toString(record, MixinUtils.baseMixins(), false));
    }

    /*@Transactional
    public void del(Integer id) {

        crsApplicantMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsApplicantExample example = new CrsApplicantExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        crsApplicantMapper.deleteByExample(example);
    }*/

    @Transactional
    public void updateByPrimaryKeySelective(CrsApplicant record) {

        CrsApplicant oldRecord = crsApplicantMapper.selectByPrimaryKey(record.getId());

        //Assert.isTrue(!idDuplicate(record.getId(), record.getPostId(), record.getUserId()), "报名重复");
        record.setPostId(null);
        record.setUserId(null);
        crsApplicantMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(record.getId(), record.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "更新报名人员", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                JSONUtils.toString(oldRecord, MixinUtils.baseMixins(), false));
    }
}
