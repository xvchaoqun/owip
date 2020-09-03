package service.pcs;

import bean.ShortMsgBean;
import controller.global.OpException;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsAdminExample;
import domain.pcs.PcsConfig;
import domain.sys.SysUserView;
import ext.service.ShortMsgService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import service.base.MetaTypeService;
import service.party.PartyAdminService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private PartyAdminService partyAdminService;
    @Autowired
    private ShortMsgService shortMsgService;

    // 后台同步党代会管理员角色：
    // 1、删除所有党代会的所有书记、副书记管理员
    // 2、同步最新的分党委书记、副书记管理员（保留不重复的普通管理员）
    /*@Transactional
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

                    sysUserService.delRole(userId, RoleConstants.ROLE_PCS_PARTY);
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

            PcsAdminExample example = new PcsAdminExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<PcsAdmin> pcsAdminList= pcsAdminMapper.selectByExample(example);
            if(pcsAdminList.size()>0)  continue;

            int partyId = partyMemberView.getGroupPartyId(); // 此处应该用分党委委员会所在的分党委ID，保证不为空

            PcsAdmin record = new PcsAdmin();
            record.setUserId(userId);
            record.setPartyId(partyId);
            record.setType((partyMemberView.getPostId().intValue() == partySecretaryType.getId()) ?
                    PcsConstants.PCS_ADMIN_TYPE_SECRETARY : PcsConstants.PCS_ADMIN_TYPE_VICE_SECRETARY);
            pcsAdminMapper.insertSelective(record);

            sysUserService.addRole(userId, RoleConstants.ROLE_PCS_PARTY);
        }

    }*/

    // 获取分党委管理员信息（每个用户最多只管理一个分党委）
    public PcsAdmin getAdmin(int userId) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        List<OwAdmin> owAdmins = partyAdminService.getOwAdmins(userId);
        if(owAdmins.size()==0) return null; // 必须是分党委管理员，否则不允许管理党代会
        OwAdmin owAdmin = owAdmins.get(0); // 按分党委顺序仅读取管理的第一个分党委
        int partyId = owAdmin.getPartyId();

        PcsAdmin pcsAdmin = new PcsAdmin();
        pcsAdmin.setUserId(userId);
        pcsAdmin.setPartyId(partyId);

        PcsAdminExample example = new PcsAdminExample();
        example.createCriteria().andConfigIdEqualTo(configId).andUserIdEqualTo(userId);
        List<PcsAdmin> pcsAdmins = pcsAdminMapper.selectByExample(example);

        if(pcsAdmins.size()>0){ // 如果还没录入管理员信息，则读取分党委管理员的信息

            PcsAdmin _pcsAdmin = pcsAdmins.get(0);
            pcsAdmin.setMobile(_pcsAdmin.getMobile());
            pcsAdmin.setUnit(_pcsAdmin.getUnit());
        }else{
            SysUserView uv = CmTag.getUserById(userId);
            pcsAdmin.setMobile(uv.getMobile());
            pcsAdmin.setUnit(uv.getUnit());
        }

        return pcsAdmin;
    }

    public boolean idDuplicate(Integer id, int userId) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        PcsAdminExample example = new PcsAdminExample();
        PcsAdminExample.Criteria criteria =
                        example.createCriteria().andUserIdEqualTo(userId).andConfigIdEqualTo(configId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return pcsAdminMapper.countByExample(example) > 0;
    }

    // 添加分党委管理员信息
    @Transactional
    public void addOrUpdate(PcsAdmin record) {

        Integer id=record.getId();
        Integer userId=record.getUserId();

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if (idDuplicate(id, userId)) {

            //在分党委管理员与党代会管理员信息中partyId不一致时，可以添加
            PcsAdmin _pcsAdmin = getAdmin(userId);

            PcsAdminExample example = new PcsAdminExample();
            PcsAdminExample.Criteria criteria =
                    example.createCriteria().andUserIdEqualTo(userId).andConfigIdEqualTo(configId);
            if (id != null) {
                criteria.andIdNotEqualTo(id);
            }

            List<PcsAdmin> PcsAdmins=pcsAdminMapper.selectByExample(example);
            PcsAdmin oldPcsAdmin=PcsAdmins.get(0);

            if (_pcsAdmin.getPartyId().intValue() != oldPcsAdmin.getPartyId()) {
                record.setId(oldPcsAdmin.getId());
            }else {
                throw new OpException("该用户已是[{0}]管理员",CmTag.getParty(oldPcsAdmin.getPartyId()).getName());
            }

        }

        if (record.getId() != null) {
            pcsAdminMapper.updateByPrimaryKeySelective(record);

        } else {
            record.setConfigId(configId);
            pcsAdminMapper.insertSelective(record);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

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
    public Map<String, Integer> sendMsg(byte type, byte stage, String mobile, String msg) {

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
                pcsAdmins = iPcsMapper.hasNotReportPcsAdmins(configId, stage);
            } else {
                pcsAdmins = iPcsMapper.hasNotReportPcsPrAdmins(configId, stage);
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
