package service.sc.scPassport;

import bean.ShortMsgBean;
import domain.cadre.CadreView;
import domain.sc.scPassport.ScPassportHand;
import domain.sc.scPassport.ScPassportMsg;
import ext.service.ShortMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;
import service.sys.UserBeanService;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;

import java.util.Date;

@Service
public class ScPassportMsgService extends ScBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected ShortMsgService shortMsgService;

    @Transactional
    public void send(int handId, String msg){

        ScPassportHand scPassportHand = scPassportHandMapper.selectByPrimaryKey(handId);
        CadreView cadre = scPassportHand.getCadre();
        int userId = cadre.getUserId();

        boolean success = false;
        try {
            String mobile = userBeanService.getMsgMobile(userId);

            ShortMsgBean bean = new ShortMsgBean();
            bean.setType(ContentTplConstants.CONTENT_TPL_TYPE_MSG);
            bean.setReceiver(userId);
            bean.setMobile(mobile);
            bean.setContent(msg);
            bean.setRelateId(null);
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_SC_PASSPORT);
            bean.setTypeStr("新任干部提交证件");

            success = shortMsgService.send(bean, "127.0.0.1");
        } catch (Exception ex) {
            logger.error("异常", ex);
            logger.error("新任干部提交证件短信通知失败。接收人：{}, {},{}", new Object[]{
                    cadre.getRealname(), cadre.getMobile(), ex.getMessage()
            });
        }

        ScPassportMsg record = new ScPassportMsg();
        record.setHandId(handId);
        record.setUserId(userId);
        record.setMsg(msg);
        record.setSendTime(new Date());
        record.setSuccess(success);

        scPassportMsgMapper.insertSelective(record);
    }
}
