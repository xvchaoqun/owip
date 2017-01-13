package service.abroad;

import bean.ShortMsgBean;
import domain.abroad.PassportApply;
import domain.abroad.PassportApplyExample;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.utils.ContextHelper;
import shiro.ShiroHelper;
import service.base.ShortMsgService;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Service
public class PassportApplyService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShortMsgService shortMsgService;
    @Transactional
    public void apply(PassportApply record){

        Integer cadreId = record.getCadreId();
        Integer classId = record.getClassId();

        // （2）	以下情况不能再次申请护照：未审批、审批通过但未办理完交回；
        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_INIT).andClassIdEqualTo(classId);
        if(passportApplyMapper.countByExample(example)>0){
            throw new RuntimeException("您已经申请办理了"+record.getPassportClass().getName() +"，请不要重复申请");
        }
        PassportApplyExample example2 = new PassportApplyExample();
        example2.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_PASS)
                .andAbolishEqualTo(false).andClassIdEqualTo(classId).andHandleDateIsNull();
        if(passportApplyMapper.countByExample(example2)>0){
            throw new RuntimeException("您已经申请办理了"+record.getPassportClass().getName() +"，申请已通过，请办理证件交回");
        }

        passportApplyMapper.insertSelective(record);

        HttpServletRequest request = ContextHelper.getRequest();
        try {
            // 发送短信
            ShortMsgBean shortMsgBean = shortMsgService.getShortMsgBean(ShiroHelper.getCurrentUserId(),
                    null, "passportApplySubmit", record.getId());
            shortMsgService.send(shortMsgBean, IpUtils.getRealIp(request));
        }catch (Exception ex){
            ex.printStackTrace();
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            String username = (shiroUser!=null)?shiroUser.getUsername():null;
            logger.error("短信发送失败, {}, {}, {}, {}, {}, {}, {}",
                    new Object[]{username, ex.getMessage(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }
    }
    @Transactional
    public void del(Integer id){

        passportApplyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void abolish(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        PassportApply record = new PassportApply();
        record.setAbolish(true);

        passportApplyMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        PassportApply record = new PassportApply();
        record.setIsDeleted(true);
        passportApplyMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void batchUnDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        PassportApply record = new PassportApply();
        record.setIsDeleted(false);
        passportApplyMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PassportApply record){

        return passportApplyMapper.updateByPrimaryKeySelective(record);
    }
}
