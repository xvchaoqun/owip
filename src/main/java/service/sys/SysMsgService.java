package service.sys;

import domain.party.*;
import domain.sys.SysMsg;
import domain.sys.SysMsgExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import persistence.sys.SysMsgMapper;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysMsgService extends BaseMapper {

    @Autowired
    private SysMsgMapper sysMsgMapper;

    @Transactional
    public void insertSelective(SysMsg record) {

        record.setSendTime(new Date());
        record.setSendUserId(ShiroHelper.getCurrentUserId());
        record.setStatus(SystemConstants.SYS_MSG_STATUS_UNCONFIRM);
        record.setIp(ContextHelper.getRealIp());
        sysMsgMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        SysMsgExample example = new SysMsgExample();
        SysMsgExample.Criteria criteria=example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(SystemConstants.SYS_MSG_STATUS_UNCONFIRM);
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)){
            criteria.andSendUserIdEqualTo(ShiroHelper.getCurrentUserId());
        }

        sysMsgMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SysMsg record) {
       /* if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");*/
        sysMsgMapper.updateByPrimaryKeySelective(record);
    }

    public int getSysMsgCount(int userId) {

        SysMsgExample example = new SysMsgExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(SystemConstants.SYS_MSG_STATUS_UNCONFIRM);
        return (int) sysMsgMapper.countByExample(example);
    }

    public int count(byte status) {

        int count = 0;

        SysMsgExample example = new SysMsgExample();
        example.createCriteria().andStatusEqualTo(status).andUserIdEqualTo(ShiroHelper.getCurrentUserId());

        count = (int) sysMsgMapper.countByExample(example);
        return count;
    }

    @Transactional
    public void batchConfirm(Integer[] ids) {

        SysMsg record = new SysMsg();
        record.setStatus(SystemConstants.SYS_MSG_STATUS_CONFIRM);
        record.setConfirmTime(new Date());

        SysMsgExample example = new SysMsgExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(SystemConstants.SYS_MSG_STATUS_UNCONFIRM);

        sysMsgMapper.updateByExampleSelective(record, example);

    }

    @Transactional
    public void partyRemind(Integer[] ids) {

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<PartyMemberGroup> groupList = partyMemberGroupMapper.selectByExample(example);
        if (groupList.size() > 0) {
            Set<Integer> partyIdList = groupList.stream().map(PartyMemberGroup::getPartyId).collect(Collectors.toSet());
            for (Integer partyId : partyIdList) {
                OwAdmin owAdmin = new OwAdmin();
                owAdmin.setPartyId(partyId);
                List<OwAdmin> records = iPartyMapper.selectPartyAdminList(owAdmin, new RowBounds());
                if (records != null && records.size() > 0) {
                    for (OwAdmin record : records) {
                        SysMsg sysMsg = new SysMsg();
                        sysMsg.setUserId(record.getUserId());
                        sysMsg.setTitle("领导班子换届提醒");
                        sysMsg.setContent("您管理的领导班子即将换届，请及时在系统的组织机构管理中进行换届操作。");
                        insertSelective(sysMsg);
                    }
                }
            }
        }
    }

    @Transactional
    public void branchRemind(Integer[] ids) {

        BranchMemberGroupExample example=new BranchMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<BranchMemberGroup> groupList = branchMemberGroupMapper.selectByExample(example);
        if (groupList.size() > 0) {
            Set<Integer> branchIdList = groupList.stream().map(BranchMemberGroup::getBranchId).collect(Collectors.toSet());
            for (Integer branchId : branchIdList) {
                OwAdmin owAdmin = new OwAdmin();
                owAdmin.setBranchId(branchId);
                List<OwAdmin> records = iPartyMapper.selectBranchAdminList(owAdmin, new RowBounds());
                if (records != null && records.size() > 0) {
                    for (OwAdmin record : records) {
                        SysMsg sysMsg = new SysMsg();
                        sysMsg.setUserId(record.getUserId());
                        sysMsg.setTitle("支部委员会换届提醒");
                        sysMsg.setContent("您管理的支部委员会即将换届，请及时在系统的组织机构管理中进行换届操作。");
                        insertSelective(sysMsg);
                    }
                }
            }
        }
    }
}
