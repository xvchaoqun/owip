package service.base;

import bean.ShortMsgBean;
import controller.global.OpException;
import domain.base.ContentTpl;
import domain.base.ShortMsg;
import domain.base.ShortMsgExample;
import domain.sys.SysUserView;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import service.sys.SysUserService;
import shiro.PasswordHelper;
import sys.SendMsgResult;
import sys.SendMsgUtils;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.PropertiesUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShortMsgService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SpringProps springProps;
    @Autowired
    private ContentTplService contentTplService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected CacheManager cacheManager;

    // 修改密码
    public int changePassword(String username){

        SysUserView uv = sysUserService.findByUsername(username);

        // 判断发短信频率
        Cache<String, String> findPassCache = cacheManager.getCache("FindPassDayCount");
        String cacheKey = DateUtils.formatDate(new Date(), DateUtils.YYYYMMDD) + "_" + uv.getMobile();
        String cacheVal = findPassCache.get(cacheKey);
        int seq = 0;
        if(cacheVal!=null){
            seq = Integer.parseInt(cacheVal.split("_")[0]);
        }
        if(seq >= 5){
            throw new OpException("该账号修改密码发送短信今日已发送了5次，请明天再试。");
        }

        seq = seq+1;

        String code = RandomStringUtils.randomNumeric(4);
        ContentTpl tpl = getTpl(ContentTplConstants.CONTENT_TPL_FIND_PASS);
        String msg = MessageFormat.format(tpl.getContent(), username, code, seq);

        ShortMsgBean bean = new ShortMsgBean();
        bean.setReceiver(uv.getId());
        bean.setMobile(uv.getMobile());
        bean.setContent(msg);
        bean.setRelateId(tpl.getId());
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
        bean.setType(tpl.getName());
        try {

            boolean send = send(bean, IpUtils.getRealIp(ContextHelper.getRequest()));
            if(send){

                findPassCache.put(cacheKey, seq+"_"+code);
                return seq;
            }
        }catch (Exception ex){
            logger.error("修改密码发送短信失败，{}, {}", new Object[]{username, ex.getMessage()});
        }

        return 0;
    }

    public ContentTpl getTpl(String key){

        ContentTpl contentTpl = contentTplService.codeKeyMap().get(key);
        if(contentTpl == null || StringUtils.isBlank(contentTpl.getContent()))
            throw new OpException(String.format("读取模板（CODE：%s）异常", key));

        return contentTpl;
    }

    public boolean send(ShortMsgBean shortMsgBean, String ip){

        Integer sender = shortMsgBean.getSender();
        Integer receiver = shortMsgBean.getReceiver();
        String content = shortMsgBean.getContent();
        String type = shortMsgBean.getType();
        String mobile = shortMsgBean.getMobile();

        boolean sendMsg = true;
        SysUserView uv = null;
        if(receiver!=null) { // 给系统用户发短信
            uv = sysUserService.findById(receiver);
            if (uv == null) {
                throw new OpException("用户不存在。");
            }
            String msgMobile = uv.getMsgMobile();
            if(StringUtils.isNotBlank(msgMobile)){
                mobile = msgMobile; // 代收短信手机号码
            }
            sendMsg = BooleanUtils.isFalse(uv.getNotSendMsg());
        }

        if(sendMsg && !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            throw new OpException("{0}（工号：{1}）手机号码有误（{2}）", uv.getRealname(), uv.getCode(), mobile);
        }
        if(StringUtils.isBlank(content)){
            throw new OpException("短信发送内容为空。");
        }

        ShortMsg record = new ShortMsg();
        record.setRelateId(shortMsgBean.getRelateId());
        record.setRelateType(shortMsgBean.getRelateType());
        record.setCreateTime(new Date());
        record.setMobile(mobile);
        record.setContent(content);
        record.setReceiverId(receiver);
        record.setSenderId(sender);
        record.setType(type);
        record.setIp(ip);

        boolean result = false;
        if(springProps.shortMsgSend) {
            String ret = null;
            if(sendMsg) {
                SendMsgResult sendMsgResult = SendMsgUtils.sendMsg(mobile, content);
                result = sendMsgResult.isSuccess();
                ret = sendMsgResult.getMsg();
            }else{
                ret = "系统禁止发送短信";
            }
            record.setStatus(result);
            record.setRet(ret);
            shortMsgMapper.insertSelective(record);
        }else{
            record.setRemark("test");
            record.setStatus(false);
            shortMsgMapper.insertSelective(record);
            result = true;
        }

        return result;
    }

   /* @Transactional
    public int insertSelective(ShortMsg record){

        return shortMsgMapper.insertSelective(record);
    }*/
    @Transactional
    public void del(Integer id){

        shortMsgMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ShortMsgExample example = new ShortMsgExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        shortMsgMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ShortMsg record){
        return shortMsgMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, ShortMsg> findAll() {

        ShortMsgExample example = new ShortMsgExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<ShortMsg> shortMsges = shortMsgMapper.selectByExample(example);
        Map<Integer, ShortMsg> map = new LinkedHashMap<>();
        for (ShortMsg shortMsg : shortMsges) {
            map.put(shortMsg.getId(), shortMsg);
        }

        return map;
    }
}
