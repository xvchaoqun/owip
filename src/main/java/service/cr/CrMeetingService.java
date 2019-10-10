package service.cr;

import bean.ShortMsgBean;
import domain.cr.CrInfo;
import domain.cr.CrMeeting;
import domain.cr.CrMeetingExample;
import domain.sys.SysUserView;
import ext.service.ShortMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrMeetingService extends CrBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private SysUserService sysUserService;

    @Transactional
    public void insertSelective(CrMeeting record){

        crMeetingMapper.insertSelective(record);

        refreshMeetingNum(record.getInfoId());
    }


    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            CrMeeting crMeeting = crMeetingMapper.selectByPrimaryKey(id);
            crMeetingMapper.deleteByPrimaryKey(id);
            refreshMeetingNum(crMeeting.getInfoId());
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(CrMeeting record){

        crMeetingMapper.updateByPrimaryKeySelective(record);
    }

    // 更新招聘会数量
    public void refreshMeetingNum(int infoId){

        CrMeetingExample example = new CrMeetingExample();
        example.createCriteria().andInfoIdEqualTo(infoId);
        int num = (int) crMeetingMapper.countByExample(example);

        CrInfo record = new CrInfo();
        record.setId(infoId);
        record.setMeetingNum(num);

        crInfoMapper.updateByPrimaryKeySelective(record);
    }

    // 下发任务短信通知
    @Transactional
    public Map<String, Integer> sendMsg(int meetingId, String msg) {

        int sendUserId = ShiroHelper.getCurrentUserId();

        List<Integer> meetingUserIds = iCrMapper.getMeetingUserIds(meetingId);
        String ip = ContextHelper.getRealIp();

        int total = meetingUserIds.size();
        int success = 0;

        for (Integer userId : meetingUserIds) {

            SysUserView uv = sysUserService.findById(userId);
            String mobile = uv.getMobile();

            ShortMsgBean bean = new ShortMsgBean();
            bean.setType(ContentTplConstants.CONTENT_TPL_TYPE_MSG);
            bean.setSender(sendUserId);
            bean.setReceiver(userId);
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_CR);
            bean.setTypeStr("干部招聘-下发短信通知");
            bean.setMobile(mobile);
            bean.setContent(msg);

            boolean send = false;
            try {
                send = shortMsgService.send(bean, ip);
            } catch (Exception ex) {
                logger.error("异常", ex);
            }
            if (send) success++;
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("success", success);

        return resultMap;
    }
}
