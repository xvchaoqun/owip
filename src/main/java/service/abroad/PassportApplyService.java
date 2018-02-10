package service.abroad;

import controller.global.OpException;
import domain.abroad.PassportApply;
import domain.abroad.PassportApplyExample;
import domain.base.MetaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.ShortMsgService;
import sys.constants.AbroadConstants;
import sys.tags.CmTag;

import java.util.Arrays;

@Service
public class PassportApplyService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShortMsgService shortMsgService;

    // 检测是否已通过申请，但是未交证件
    public boolean checkApplyPassButNotHandle(int cadreId, int classId){

        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS)
                .andAbolishEqualTo(false).andClassIdEqualTo(classId)
                .andHandleDateIsNull().andIsDeletedEqualTo(false);
        return (passportApplyMapper.countByExample(example) > 0);
    }
    // 检测是否新申请
    public boolean checkNewApply(int cadreId, int classId){

        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_INIT)
                .andClassIdEqualTo(classId).andIsDeletedEqualTo(false);
        return (passportApplyMapper.countByExample(example) > 0);
    }

    public void checkDuplicate(int cadreId, int classId){

        MetaType passportClass = CmTag.getMetaType(classId);
        // （2）	以下情况不能再次申请护照：未审批、审批通过但未办理完交回；
        if (checkNewApply(cadreId, classId)) {
            throw new OpException("您已经申请办理了" + passportClass.getName() + "，请不要重复申请");
        }

        if (checkApplyPassButNotHandle(cadreId, classId)) {
            throw new OpException("您已经申请办理了" + passportClass.getName() + "，申请已通过，请办理证件交回");
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
