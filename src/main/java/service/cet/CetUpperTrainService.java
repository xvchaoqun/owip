package service.cet;

import domain.cadre.Cadre;
import domain.cet.CetUpperTrain;
import domain.cet.CetUpperTrainExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.cadre.CadreService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Service
public class CetUpperTrainService extends BaseMapper {

    @Autowired
    protected SysApprovalLogService sysApprovalLogService;
    @Autowired
    private CadreService cadreService;

    @Transactional
    public void insertSelective(CetUpperTrain record, Integer[] userIds){

        if(userIds==null || userIds.length==0) return;

        int addUserId = ShiroHelper.getCurrentUserId();
        Date now = new Date();

        Map<Integer, Cadre> cadreMap = cadreService.dbFindByUserIds(Arrays.asList(userIds));

        for (Integer userId : userIds) {

            record.setUserId(userId);
            record.setAddUserId(addUserId);
            record.setAddTime(now);

            Cadre cadre = cadreMap.get(userId);
            if(cadre!=null){
                record.setTitle(cadre.getTitle());
                record.setPostId(cadre.getPostId());
            }

            cetUpperTrainMapper.insertSelective(record);

            sysApprovalLogService.add(record.getId(), record.getUserId(),
                    (record.getAddType()== CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF)?
                            SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN,
                    "添加调训记录", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }
    }

    @Transactional
    public void del(Integer id){

        cetUpperTrainMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetUpperTrainExample example = new CetUpperTrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetUpperTrainMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CetUpperTrain record){

        CetUpperTrain oldRecord = cetUpperTrainMapper.selectByPrimaryKey(record.getId());

        cetUpperTrainMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN,
                "更新调训记录", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, JSONUtils.toString(oldRecord, false));
    }
}
