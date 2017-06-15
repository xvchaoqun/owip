package service.abroad;

import bean.ShortMsgBean;
import domain.abroad.PassportApply;
import domain.abroad.PassportApplyExample;
import domain.base.MetaType;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.tags.CmTag;
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
import java.util.Map;

@Service
public class PassportApplyService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShortMsgService shortMsgService;

    public void checkDuplicate(int cadreId, int classId){

        MetaType passportClass = CmTag.getMetaType(classId);
        // （2）	以下情况不能再次申请护照：未审批、审批通过但未办理完交回；
        {
            PassportApplyExample example = new PassportApplyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId)
                    .andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_INIT)
                    .andClassIdEqualTo(classId).andIsDeletedEqualTo(false);
            if (passportApplyMapper.countByExample(example) > 0) {
                throw new RuntimeException("您已经申请办理了" + passportClass.getName() + "，请不要重复申请");
            }
        }
        {
            PassportApplyExample example = new PassportApplyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId)
                    .andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_PASS)
                    .andAbolishEqualTo(false).andClassIdEqualTo(classId)
                    .andHandleDateIsNull().andIsDeletedEqualTo(false);
            if (passportApplyMapper.countByExample(example) > 0) {
                throw new RuntimeException("您已经申请办理了" + passportClass.getName() + "，申请已通过，请办理证件交回");
            }
        }
    }

    @Transactional
    public void apply(PassportApply record){

        Integer cadreId = record.getCadreId();
        Integer classId = record.getClassId();
        checkDuplicate(cadreId, classId);

        passportApplyMapper.insertSelective(record);
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

        for (Integer id : ids) {
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            Integer cadreId = passportApply.getCadreId();
            Integer classId = passportApply.getClassId();
            checkDuplicate(cadreId, classId);

            PassportApply record = new PassportApply();
            record.setId(id);
            record.setIsDeleted(false);
            passportApplyMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void doBatchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsDeletedEqualTo(true);

        passportApplyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PassportApply record){

        return passportApplyMapper.updateByPrimaryKeySelective(record);
    }
}
