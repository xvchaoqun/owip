package service.cadre;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreReward;
import domain.cadre.CadreRewardExample;
import domain.cadre.CadreView;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import java.util.*;

@Service
public class CadreRewardService extends BaseMapper {
    @Autowired
    private CadreService cadreService;
    @Autowired
    private MetaTypeService metaTypeService;

    // 获取情况（用于任免审批表）
    public List<CadreReward> list(int cadreId) {

        Map<String, MetaType> codeKeyMap = metaTypeService.codeKeyMap();
        MetaType gjj = codeKeyMap.get("mc_reward_gjj"); // 国家级
        MetaType sbj = codeKeyMap.get("mc_reward_sbj"); // 省部级
        MetaType dtj = codeKeyMap.get("mc_reward_dtj"); // 地厅级
        List<Integer> rewardLevels = new ArrayList<>();
        rewardLevels.add(gjj.getId());
        rewardLevels.add(sbj.getId());
        rewardLevels.add(dtj.getId());

        CadreRewardExample example = new CadreRewardExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andRewardLevelIn(rewardLevels)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("reward_time desc");

        return cadreRewardMapper.selectByExample(example);
    }

    // 获取情况
    public List<CadreReward> list(int cadreId, byte rewardType) {

        CadreRewardExample example = new CadreRewardExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andRewardTypeEqualTo(rewardType)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("reward_time desc");
        return cadreRewardMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(CadreReward record) {

  /*      record.setSortOrder(getNextSortOrder("cadre_reward",
                "cadre_id=" + record.getCadreId() +" and status="+SystemConstants.RECORD_STATUS_FORMAL));*/
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        cadreRewardMapper.insertSelective(record);

        if(BooleanUtils.isTrue(record.getIsIndependent())){
            commonMapper.excuteSql("update cadre_reward set rank=null where id="+ record.getId());
        }
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreRewardExample example = new CadreRewardExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            long count = cadreRewardMapper.countByExample(example);
            if (count != ids.length) {
                throw new OpException("参数有误");
            }
        }
        CadreRewardExample example = new CadreRewardExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreRewardMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CadreReward record) {
        record.setStatus(null);
        cadreRewardMapper.updateByPrimaryKeySelective(record);
        if(BooleanUtils.isTrue(record.getIsIndependent())){
            commonMapper.excuteSql("update cadre_reward set rank=null where id="+ record.getId());
        }
    }

    // 更新修改申请的内容（仅允许管理员和本人更新自己的申请）
    @Transactional
    public void updateModify(CadreReward record, Integer applyId) {

        if (applyId == null) {
            throw new OpException("参数有误");
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if ((!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN) && mta.getUserId().intValue() != currentUserId) ||
                mta.getStatus() != ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new OpException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        int id = record.getId();
        CadreRewardExample example = new CadreRewardExample();
        CadreRewardExample.Criteria criteria = example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN)){
            CadreView cadre = cadreService.dbFindByUserId(currentUserId);
            criteria.andCadreIdEqualTo(cadre.getId()); // 保证本人只更新自己的记录
        }

        record.setId(null);
        record.setStatus(null);
        if (cadreRewardMapper.updateByExampleSelective(record, example) > 0 && mta.getUserId().intValue() == currentUserId) {
            // 更新申请时间
            ModifyTableApply _record = new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreReward record, Integer id, byte rewardType, boolean isDelete, String reason) {

        byte module = 0;
        if (rewardType == CadreConstants.CADRE_REWARD_TYPE_TEACH) {
            module = ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH;
        } else if (rewardType == CadreConstants.CADRE_REWARD_TYPE_RESEARCH) {
            module = ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH;
        } else if (rewardType == CadreConstants.CADRE_REWARD_TYPE_OTHER) {
            module = ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER;
        }

        CadreReward original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadreRewardMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreRewardMapper.selectByPrimaryKey(record.getId());
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;

                if (StringUtils.isBlank(record.getProof())) { // 获奖证书留空则保留原获奖证书
                    record.setProof(original.getProof());
                    record.setProofFilename(original.getProofFilename());
                }
            }
        }

        // 拥有管理干部信息或管理干部本人信息的权限，不允许提交申请
        if(CmTag.canDirectUpdateCadreInfo(record.getCadreId())){
            throw new OpException("您有直接修改[干部基本信息-干部信息]的权限，请勿在此提交申请。");
        }

        Integer originalId = original == null ? null : original.getId();
        if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {
            // 如果是修改或删除请求，则只允许一条未审批记录存在
            ModifyTableApplyExample example = new ModifyTableApplyExample();
            example.createCriteria().andOriginalIdEqualTo(originalId) // 此时originalId肯定不为空
                    .andModuleEqualTo(module)
                    .andStatusEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
            List<ModifyTableApply> applies = modifyTableApplyMapper.selectByExample(example);
            if (applies.size() > 0) {
                throw new OpException(String.format("当前记录对应的修改或删除申请[序号%s]已经存在，请等待审核。", applies.get(0).getId()));
            }
        }

        Integer userId = ShiroHelper.getCurrentUserId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        record.setCadreId(cadre.getId());  // 保证本人只能提交自己的申请
        record.setId(null);
        record.setStatus(SystemConstants.RECORD_STATUS_MODIFY);
        cadreRewardMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(module);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_reward");
        _record.setOriginalId(originalId);
        _record.setModifyId(record.getId());
        _record.setType(type);
        _record.setReason(reason);
        _record.setOriginalJson(JSONUtils.toString(original, false));
        _record.setCreateTime(new Date());
        _record.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));
        _record.setStatus(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
        modifyTableApplyMapper.insert(_record);
    }

    // 审核修改申请
    @Transactional
    public ModifyTableApply approval(ModifyTableApply mta, ModifyTableApply record, Boolean status) {

        Integer originalId = mta.getOriginalId();
        Integer modifyId = mta.getModifyId();
        byte type = mta.getType();

        if (status) {
            if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD) {

                CadreReward modify = cadreRewardMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreRewardMapper.insertSelective(modify); // 插入新纪录
                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadreReward modify = cadreRewardMapper.selectByPrimaryKey(modifyId);
                modify.setId(originalId);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreRewardMapper.updateByPrimaryKey(modify); // 覆盖原纪录

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                // 更新最后删除的记录内容
                record.setOriginalJson(JSONUtils.toString(cadreRewardMapper.selectByPrimaryKey(originalId), false));
                // 删除原纪录
                cadreRewardMapper.deleteByPrimaryKey(originalId);
            }
        }

        CadreReward modify = new CadreReward();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadreRewardMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录
        return record;
    }
}
