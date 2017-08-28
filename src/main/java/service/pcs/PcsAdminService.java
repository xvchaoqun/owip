package service.pcs;

import domain.base.MetaType;
import domain.party.PartyMemberView;
import domain.party.PartyMemberViewExample;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsAdminExample;
import domain.pcs.PcsAdminReport;
import domain.pcs.PcsAdminReportExample;
import domain.pcs.PcsConfig;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PcsAdminService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PcsOwService pcsOwService;

    // 判断是否上报
    public boolean hasReport(int partyId, int configId, byte stage){

        // 分党委已经下发名单
        if(pcsOwService.hasIssue(configId, stage)) return true;

        PcsAdminReportExample example = new PcsAdminReportExample();
        example.createCriteria().andPartyIdEqualTo(partyId)
                .andConfigIdEqualTo(configId)
                .andStageEqualTo(stage);

        return pcsAdminReportMapper.countByExample(example)>0;
    }

    // 管理员上报，上报后数据不可修改
    @Transactional
    public void report(int partyId, int configId, byte stage) {

        Integer userId = ShiroHelper.getCurrentUserId();

        PcsAdminReport record = new PcsAdminReport();
        record.setPartyId(partyId);
        record.setUserId(userId);
        record.setConfigId(configId);
        record.setStage(stage);
        record.setCreateTime(new Date());
        record.setIp(ContextHelper.getRealIp());

        pcsAdminReportMapper.insertSelective(record);
    }

    // 后台同步当前党代会管理员角色：
    // 1、删除所有非当前党代会的所有管理员
    // 2、重置当前党代会的管理员（保留不重复的普通管理员）
    @Transactional
    public void syncCurrentPcsAdmin() {

        // 当前党代会ID
        int configId = -1;
        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        if (pcsConfig != null) {
            configId = pcsConfig.getId();
        }

        // 去掉所有党代会的书记、副书记的管理员角色，并删除，保留当前党代会的普通管理员待下一步处理
        List<PcsAdmin> pcsAdmins = new ArrayList<>();
        Set<Integer> pcsNormalAdminIds = new HashSet<>(); // 普通管理员userId
        {
            PcsAdminExample example = new PcsAdminExample();
            //example.createCriteria().andConfigIdEqualTo(configId);
            pcsAdmins = pcsAdminMapper.selectByExample(example);
            for (PcsAdmin pcsAdmin : pcsAdmins) {
                Integer userId = pcsAdmin.getUserId();
                if (pcsAdmin.getType() == SystemConstants.PCS_ADMIN_TYPE_NORMAL
                        && pcsAdmin.getConfigId() == configId) {
                    // 当前党代会的普通管理员暂不处理
                    pcsNormalAdminIds.add(userId);
                } else {
                    SysUserView uv = sysUserService.findById(userId);
                    sysUserService.delRole(userId, SystemConstants.ROLE_PCS_ADMIN, uv.getUsername(), uv.getCode());
                    pcsAdminMapper.deleteByPrimaryKey(pcsAdmin.getId());
                }
            }
        }
        if(configId!=-1) {
            Map<String, MetaType> codeKeyMap = metaTypeService.codeKeyMap();
            MetaType partySecretaryType = codeKeyMap.get("mt_party_secretary");
            MetaType partyViceSecretaryType = codeKeyMap.get("mt_party_vice_secretary");

            // 所有的分党委书记、副书记
            List<PartyMemberView> partyMemberViews = new ArrayList<>();
            {
                PartyMemberViewExample example = new PartyMemberViewExample();
                example.createCriteria()
                        .andPostIdIn(Arrays.asList(partySecretaryType.getId(),
                                partyViceSecretaryType.getId()))
                        .andIsAdminEqualTo(true);

                partyMemberViews = partyMemberViewMapper.selectByExample(example);
            }

            // 赋予所有的分党委书记、副书记党代会管理员角色，如果他之前已经是普通管理员，则先删除（这样做避免重复管理员的出现）
            for (PartyMemberView partyMemberView : partyMemberViews) {

                Integer userId = partyMemberView.getUserId();
                if(pcsNormalAdminIds.contains(userId)){

                    PcsAdminExample example = new PcsAdminExample();
                    example.createCriteria().andUserIdEqualTo(userId);
                    pcsAdminMapper.deleteByExample(example);
                }

                int partyId = partyMemberView.getGroupPartyId(); // 此处应该用分党委委员会所在的分党委ID，保证不为空

                PcsAdmin record = new PcsAdmin();
                record.setUserId(userId);
                record.setConfigId(configId);
                record.setPartyId(partyId);
                record.setType((partyMemberView.getPostId().intValue()==partySecretaryType.getId())?
                        SystemConstants.PCS_ADMIN_TYPE_SECRETARY:SystemConstants.PCS_ADMIN_TYPE_VICE_SECRETARY);
                pcsAdminMapper.insertSelective(record);

                SysUserView uv = sysUserService.findById(userId);
                sysUserService.addRole(userId, SystemConstants.ROLE_PCS_ADMIN, uv.getUsername(), uv.getCode());
            }
        }
    }

    // 获取用户管理的分党委（每个用户最多只管理一个分党委）
    public PcsAdmin getAdmin(int userId) {

        PcsAdminExample example = new PcsAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<PcsAdmin> pcsAdmins = pcsAdminMapper.selectByExample(example);

        return (pcsAdmins.size() > 0)? pcsAdmins.get(0):null;
    }

    // 获取普通的分党委管理员
    public List<PcsAdmin> getNormalAdmins(int partyId) {

        PcsAdminExample example = new PcsAdminExample();
        example.createCriteria().andPartyIdEqualTo(partyId)
                .andTypeEqualTo(SystemConstants.PCS_ADMIN_TYPE_NORMAL);
        return pcsAdminMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int userId) {

        PcsAdminExample example = new PcsAdminExample();
        PcsAdminExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return pcsAdminMapper.countByExample(example) > 0;
    }

    // 添加分党委管理员
    @Transactional
    public void add(PcsAdmin record) {

        // 只能添加普通管理员
        record.setType(SystemConstants.PCS_ADMIN_TYPE_NORMAL);
        pcsAdminMapper.insertSelective(record);

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        sysUserService.addRole(userId, SystemConstants.ROLE_PCS_ADMIN, uv.getUsername(), uv.getCode());
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;
        for (Integer id : ids) {
            PcsAdmin pcsAdmin = pcsAdminMapper.selectByPrimaryKey(id);

            Integer userId = pcsAdmin.getUserId();
            SysUserView uv = sysUserService.findById(userId);
            sysUserService.delRole(userId, SystemConstants.ROLE_PCS_ADMIN, uv.getUsername(), uv.getCode());
        }

        PcsAdminExample example = new PcsAdminExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsAdminMapper.deleteByExample(example);
    }
}
