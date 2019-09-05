package service.pcs;

import bean.ShortMsgBean;
import controller.global.OpException;
import domain.base.MetaType;
import domain.party.PartyMemberView;
import domain.party.PartyMemberViewExample;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsAdminExample;
import domain.pcs.PcsConfig;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import ext.service.ShortMsgService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.base.MetaTypeService;
import service.party.PartyMemberService;
import service.party.PartyService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.ContentTplConstants;
import sys.constants.PcsConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;

import java.util.*;

@Service
public class PcsAdminService extends PcsBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PartyMemberService partyMemberService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private PcsPartyService pcsPartyService;

    // 后台同步党代会管理员角色：
    // 1、删除所有党代会的所有书记、副书记管理员
    // 2、同步最新的分党委书记、副书记管理员（保留不重复的普通管理员）
    @Transactional
    public void syncCurrentPcsAdmin() {

        // 去掉所有党代会的书记、副书记的管理员角色，并删除，保留当前党代会的普通管理员待下一步处理
        List<PcsAdmin> pcsAdmins = new ArrayList<>();
        Set<Integer> pcsNormalAdminIds = new HashSet<>(); // 普通管理员userId
        {
            PcsAdminExample example = new PcsAdminExample();
            //example.createCriteria().andConfigIdEqualTo(configId);
            pcsAdmins = pcsAdminMapper.selectByExample(example);
            for (PcsAdmin pcsAdmin : pcsAdmins) {
                Integer userId = pcsAdmin.getUserId();
                if (pcsAdmin.getType() == PcsConstants.PCS_ADMIN_TYPE_NORMAL) {
                    // 党代会的普通管理员暂不处理
                    pcsNormalAdminIds.add(userId);
                } else {

                    sysUserService.delRole(userId, RoleConstants.ROLE_PCS_ADMIN);
                    pcsAdminMapper.deleteByPrimaryKey(pcsAdmin.getId());
                }
            }
        }

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
                    .andIsPresentEqualTo(true)
                    .andIsDeletedEqualTo(false)
                    .andIsAdminEqualTo(true);

            partyMemberViews = partyMemberViewMapper.selectByExample(example);
        }

        // 赋予所有的分党委书记、副书记党代会管理员角色，如果他之前已经是普通管理员，则先删除（这样做避免重复管理员的出现）
        for (PartyMemberView partyMemberView : partyMemberViews) {

            Integer userId = partyMemberView.getUserId();
            if (pcsNormalAdminIds.contains(userId)) {

                PcsAdminExample example = new PcsAdminExample();
                example.createCriteria().andUserIdEqualTo(userId);
                pcsAdminMapper.deleteByExample(example);
            }

            int partyId = partyMemberView.getGroupPartyId(); // 此处应该用分党委委员会所在的分党委ID，保证不为空

            PcsAdmin record = new PcsAdmin();
            record.setUserId(userId);
            record.setPartyId(partyId);
            record.setType((partyMemberView.getPostId().intValue() == partySecretaryType.getId()) ?
                    PcsConstants.PCS_ADMIN_TYPE_SECRETARY : PcsConstants.PCS_ADMIN_TYPE_VICE_SECRETARY);
            pcsAdminMapper.insertSelective(record);

            sysUserService.addRole(userId, RoleConstants.ROLE_PCS_ADMIN);
        }

    }

    // 获取用户管理的分党委（每个用户最多只管理一个分党委）
    public PcsAdmin getAdmin(int userId) {

        PcsAdminExample example = new PcsAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<PcsAdmin> pcsAdmins = pcsAdminMapper.selectByExample(example);

        return (pcsAdmins.size() > 0) ? pcsAdmins.get(0) : null;
    }

    // 获取普通的分党委管理员
    public List<PcsAdmin> getNormalAdmins(int partyId) {

        PcsAdminExample example = new PcsAdminExample();
        example.createCriteria().andPartyIdEqualTo(partyId)
                .andTypeEqualTo(PcsConstants.PCS_ADMIN_TYPE_NORMAL);
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
    public void addOrUpdate(PcsAdmin record, String mobile) {

        int userId = record.getUserId();
        byte type = PcsConstants.PCS_ADMIN_TYPE_NORMAL;

        if (idDuplicate(record.getId(), record.getUserId())) {
            throw new OpException("该用户已经是党代会管理员");
        }

        PartyMemberView pmv = partyMemberService.getPartyMemberView(record.getPartyId(), userId);
        if (pmv != null) {

            Map<String, MetaType> codeKeyMap = metaTypeService.codeKeyMap();
            MetaType partySecretaryType = codeKeyMap.get("mt_party_secretary");
            MetaType partyViceSecretaryType = codeKeyMap.get("mt_party_vice_secretary");

            if (pmv.getPostId().intValue() == partySecretaryType.getId()) {
                type = PcsConstants.PCS_ADMIN_TYPE_SECRETARY;
            } else if (pmv.getPostId().intValue() == partyViceSecretaryType.getId()) {
                type = PcsConstants.PCS_ADMIN_TYPE_VICE_SECRETARY;
            }

            /*if (type != PcsConstants.PCS_ADMIN_TYPE_NORMAL) {
                if (pmv.getPartyId().intValue() != record.getPartyId()) {
                    Party party = partyService.findAll().get(record.getPartyId());
                    throw new OpException("{0}是{1}的{2}，无法添加。",
                            pmv.getRealname(), party.getName(), PcsConstants.PCS_ADMIN_TYPE_MAP.get(type));
                }
            }*/
        }
        // 更新为最新的管理员类型
        record.setType(type);
        if (record.getId() != null) {
            PcsAdmin _pcsAdmin = pcsAdminMapper.selectByPrimaryKey(record.getId());
            if (_pcsAdmin.getUserId().intValue() != record.getUserId()) {
                // 更换用户的情况，先删除原用户的管理员角色
                sysUserService.delRole(_pcsAdmin.getUserId(), RoleConstants.ROLE_PCS_ADMIN);

                pcsAdminMapper.updateByPrimaryKeySelective(record);
            }
        } else {
            pcsAdminMapper.insertSelective(record);
        }
        // 添加管理员角色
        sysUserService.addRole(userId, RoleConstants.ROLE_PCS_ADMIN);

        // 如果有手机号码，则更新系统的手机号码
        if (StringUtils.isNotBlank(mobile)) {
            SysUserInfo _record = new SysUserInfo();
            _record.setUserId(userId);
            _record.setMobile(mobile);
            sysUserService.insertOrUpdateUserInfoSelective(_record);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;
        for (Integer id : ids) {
            PcsAdmin pcsAdmin = pcsAdminMapper.selectByPrimaryKey(id);

            Integer userId = pcsAdmin.getUserId();
            sysUserService.delRole(userId, RoleConstants.ROLE_PCS_ADMIN);
        }

        PcsAdminExample example = new PcsAdminExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsAdminMapper.deleteByExample(example);
    }

  /*  // 更新管理员手机号码
    @Transactional
    public void updateInfo(int id, String remark, String mobile) {

        PcsAdmin pcsAdmin = pcsAdminMapper.selectByPrimaryKey(id);
        PcsAdmin _pcsAdmin = new PcsAdmin();
        _pcsAdmin.setId(id);
        _pcsAdmin.setRemark(StringUtils.trimToEmpty(remark));
        pcsAdminMapper.updateByPrimaryKeySelective(_pcsAdmin);

        SysUserInfo record = new SysUserInfo();
        record.setUserId(pcsAdmin.getUserId());
        record.setMobile(mobile);

        sysUserService.insertOrUpdateUserInfoSelective(record);
    }*/

    // type=1 两委委员 type=2 党代表
    public Map<String, Integer> sendMsg(byte type, byte stage, byte adminType, String mobile, String msg) {

        int total = 0;
        int success = 0;

        String ip = ContextHelper.getRealIp();
        int sendUserId = ShiroHelper.getCurrentUserId();
        String typeName = (type == 1) ? "党代会-两委委员" : "党代会-党代表";
        if (StringUtils.isNotBlank(mobile)) {

            // 发送给指定手机号码
            ShortMsgBean bean = new ShortMsgBean();
            bean.setType(ContentTplConstants.CONTENT_TPL_TYPE_MSG);
            bean.setSender(sendUserId);
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_PCS);
            bean.setTypeStr(typeName);
            bean.setMobile(mobile);
            bean.setContent(msg);

            if (shortMsgService.send(bean, ip)) {
                total++;
                success++;
            }
        } else {

            List<PcsAdmin> pcsAdmins = null;
            PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
            int configId = currentPcsConfig.getId();
            if (type == 1) {
                pcsAdmins = iPcsMapper.hasNotReportPcsAdmins(configId, stage, adminType);
            } else {
                pcsAdmins = iPcsMapper.hasNotReportPcsPrAdmins(configId, stage, adminType);
            }
            if (pcsAdmins != null) {
                total = pcsAdmins.size();
                for (PcsAdmin pcsAdmin : pcsAdmins) {
                    int userId = pcsAdmin.getUserId();
                    SysUserView uv = sysUserService.findById(userId);
                    mobile = uv.getMobile();
                    if (StringUtils.isNotBlank(mobile)) {

                        ShortMsgBean bean = new ShortMsgBean();
                        bean.setType(ContentTplConstants.CONTENT_TPL_TYPE_MSG);
                        bean.setReceiver(userId);
                        bean.setSender(sendUserId);
                        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_PCS);
                        bean.setTypeStr(typeName);
                        bean.setMobile(mobile);
                        bean.setContent(msg);

                        try {
                            if (shortMsgService.send(bean, ip)) success++;
                        } catch (Exception ex) {
                            logger.error("异常", ex);
                        }

                    }
                }
            }
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("success", success);

        return resultMap;
    }

    public Map<String, Integer> sendMsg2(String mobile, String msg) {

        String ip = ContextHelper.getRealIp();
        int sendUserId = ShiroHelper.getCurrentUserId();
        String typeName = "两委委员-下发名单短信通知";

        int total = 0;
        int success = 0;
        if (StringUtils.isNotBlank(mobile)) {

            // 发送给指定手机号码
            ShortMsgBean bean = new ShortMsgBean();
            bean.setType(ContentTplConstants.CONTENT_TPL_TYPE_MSG);
            bean.setSender(sendUserId);
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_PCS);
            bean.setTypeStr(typeName);
            bean.setMobile(mobile);
            bean.setContent(msg);

            if (shortMsgService.send(bean, ip)) {
                total++;
                success++;
            }
        } else {
            PcsAdminExample example = new PcsAdminExample();
            List<PcsAdmin> pcsAdmins = pcsAdminMapper.selectByExample(example);
            total = pcsAdmins.size();
            for (PcsAdmin pcsAdmin : pcsAdmins) {
                int userId = pcsAdmin.getUserId();
                SysUserView uv = sysUserService.findById(userId);
                mobile = uv.getMobile();
                if (StringUtils.isNotBlank(mobile)) {

                    ShortMsgBean bean = new ShortMsgBean();
                    bean.setType(ContentTplConstants.CONTENT_TPL_TYPE_MSG);
                    bean.setReceiver(userId);
                    bean.setSender(sendUserId);
                    bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_PCS);
                    bean.setTypeStr(typeName);
                    bean.setMobile(mobile);
                    bean.setContent(msg);

                    try {
                        if (shortMsgService.send(bean, ip)) success++;
                    } catch (Exception ex) {
                        logger.error("异常", ex);
                    }

                }
            }
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("success", success);

        return resultMap;
    }

    public Map<String, Integer> sendMsg3(Integer partyId, String mobile, String msg) {

        String ip = ContextHelper.getRealIp();
        int sendUserId = ShiroHelper.getCurrentUserId();
        String typeName = "党代表-审核通知";

        int total = 0;
        int success = 0;
        if (StringUtils.isNotBlank(mobile)) {

            // 发送给指定手机号码
            ShortMsgBean bean = new ShortMsgBean();
            bean.setType(ContentTplConstants.CONTENT_TPL_TYPE_MSG);
            bean.setSender(sendUserId);
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_PCS);
            bean.setTypeStr(typeName);
            bean.setMobile(mobile);
            bean.setContent(msg);

            if (shortMsgService.send(bean, ip)) {
                total++;
                success++;
            }
        } else {
            PcsAdminExample example = new PcsAdminExample();
            example.createCriteria().andPartyIdEqualTo(partyId);
            List<PcsAdmin> pcsAdmins = pcsAdminMapper.selectByExample(example);
            total = pcsAdmins.size();
            for (PcsAdmin pcsAdmin : pcsAdmins) {
                int userId = pcsAdmin.getUserId();
                SysUserView uv = sysUserService.findById(userId);
                mobile = uv.getMobile();
                if (StringUtils.isNotBlank(mobile)) {

                    ShortMsgBean bean = new ShortMsgBean();
                    bean.setType(ContentTplConstants.CONTENT_TPL_TYPE_MSG);
                    bean.setReceiver(userId);
                    bean.setSender(sendUserId);
                    bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_PCS);
                    bean.setTypeStr(typeName);
                    bean.setMobile(mobile);
                    bean.setContent(msg);

                    try {
                        if (shortMsgService.send(bean, ip)) success++;
                    } catch (Exception ex) {
                        logger.error("异常", ex);
                    }

                }
            }
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("success", success);

        return resultMap;
    }
}
