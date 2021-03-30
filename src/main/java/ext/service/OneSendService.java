package ext.service;

import com.edu.bnu.msg.OneSendResult;
import com.edu.bnu.msg.OneSendUtils;
import domain.base.OneSend;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.base.OneSendMapper;
import service.SpringProps;
import shiro.ShiroHelper;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lm on 2017/12/11.
 */
@Service
public class OneSendService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SpringProps springProps;
    @Autowired
    private OneSendMapper oneSendMapper;

    // userList格式：工号|手机号
    public OneSend sendMsg(List<String> userList, List<String> realnameList, String content) {

        if(userList.size()==0) return null;

        OneSend _oneSend = new OneSend();

        if (springProps.shortMsgSend) {
            // 发送微信提醒
            OneSendResult oneSendResult = OneSendUtils.sendWechat(userList.toArray(new String[]{}), "", content);
             _oneSend.setType(oneSendResult.getType());
             _oneSend.setIsSuccess(oneSendResult.isSuccess());
             _oneSend.setRet(oneSendResult.getRet());
        }else{
            _oneSend.setIsSuccess(false);
            _oneSend.setRet("test");
        }

        _oneSend.setSendUserId(ShiroHelper.getCurrentUserId());
        _oneSend.setContent(content);
        _oneSend.setRecivers(StringUtils.join(realnameList, ","));
        _oneSend.setCodes(StringUtils.join(userList, ","));
        _oneSend.setSendTime(new Date());

        oneSendMapper.insertSelective(_oneSend);

        return _oneSend;
    }

    // userList格式：工号|手机号 发送信息相同，防止重复发送
    public OneSend sendMsg(Set<String> userList, Set<String> realnameList, String content) {

        if(userList.size()==0) return null;

        OneSend _oneSend = new OneSend();

        if (springProps.shortMsgSend) {
            // 发送微信提醒
            OneSendResult oneSendResult = OneSendUtils.sendWechat(userList.toArray(new String[]{}), "", content);
            _oneSend.setType(oneSendResult.getType());
            _oneSend.setIsSuccess(oneSendResult.isSuccess());
            _oneSend.setRet(oneSendResult.getRet());
        }else{
            _oneSend.setIsSuccess(false);
            _oneSend.setRet("test");
        }

        _oneSend.setSendUserId(ShiroHelper.getCurrentUserId());
        _oneSend.setContent(content);
        _oneSend.setRecivers(StringUtils.join(realnameList, ","));
        _oneSend.setCodes(StringUtils.join(userList, ","));
        _oneSend.setSendTime(new Date());

        oneSendMapper.insertSelective(_oneSend);

        return _oneSend;
    }
}
