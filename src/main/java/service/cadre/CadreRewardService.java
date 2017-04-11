package service.cadre;

import domain.cadre.CadreReward;
import domain.cadre.CadreRewardExample;
import domain.cadre.CadreView;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CadreRewardService extends BaseMapper {
    @Autowired
    private CadreService cadreService;
    @Transactional
    public int insertSelective(CadreReward record){

  /*      record.setSortOrder(getNextSortOrder("cadre_reward",
                "cadre_id=" + record.getCadreId() +" and status="+SystemConstants.RECORD_STATUS_FORMAL));*/
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        return cadreRewardMapper.insertSelective(record);
    }
    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreRewardExample example = new CadreRewardExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreRewardMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }
        CadreRewardExample example = new CadreRewardExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreRewardMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreReward record){
        record.setStatus(null);
        return cadreRewardMapper.updateByPrimaryKeySelective(record);
    }

    // 更新修改申请的内容（仅允许本人更新自己的申请）
    @Transactional
    public void updateModify(CadreReward record, Integer applyId){

        if(applyId==null){
            throw new IllegalArgumentException();
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if (mta.getUserId().intValue() != currentUserId ||
                mta.getStatus() != SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new RuntimeException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        CadreView cadre = cadreService.dbFindByUserId(currentUserId);

        int id = record.getId();
        CadreRewardExample example = new CadreRewardExample();
        example.createCriteria().andIdEqualTo(id).andCadreIdEqualTo(cadre.getId()) // 保证本人只更新自己的记录
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        record.setId(null);
        record.setStatus(null);
        if(cadreRewardMapper.updateByExampleSelective(record, example)>0) {
            // 更新申请时间
            ModifyTableApply _record= new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreReward record, Integer id, byte rewardType, boolean isDelete){

        byte module = 0;
        if(rewardType==SystemConstants.CADRE_REWARD_TYPE_TEACH){
            module = SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH;
        }else if(rewardType==SystemConstants.CADRE_REWARD_TYPE_RESEARCH){
            module = SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH;
        }else if(rewardType==SystemConstants.CADRE_REWARD_TYPE_OTHER){
            module = SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER;
        }

        CadreReward original = null; // 修改、删除申请对应的原纪录
        byte type;
        if(isDelete){ // 删除申请时id不允许为空
            record = cadreRewardMapper.selectByPrimaryKey(id);
            original = record;
            type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        }else{
            if(record.getId()==null) // 添加申请
                type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreRewardMapper.selectByPrimaryKey(record.getId());
                type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;

                if(StringUtils.isBlank(record.getProof())){ // 获奖证书留空则保留原获奖证书
                    record.setProof(original.getProof());
                    record.setProofFilename(original.getProofFilename());
                }
            }
        }

        Integer originalId = original==null?null:original.getId();
        if(type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type==SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE){
            // 如果是修改或删除请求，则只允许一条未审批记录存在
            ModifyTableApplyExample example = new ModifyTableApplyExample();
            example.createCriteria().andOriginalIdEqualTo(originalId) // 此时originalId肯定不为空
                    .andModuleEqualTo(module)
                    .andStatusEqualTo(SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
            List<ModifyTableApply> applies = modifyTableApplyMapper.selectByExample(example);
            if(applies.size()>0){
                throw new RuntimeException(String.format("当前记录对应的修改或删除申请[序号%s]已经存在，请等待审核。", applies.get(0).getId()));
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
        _record.setOriginalJson(JSONUtils.toString(original, false));
        _record.setCreateTime(new Date());
        _record.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));
        _record.setStatus(SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
        modifyTableApplyMapper.insert(_record);
    }

    // 审核修改申请
    public ModifyTableApply approval(ModifyTableApply mta, ModifyTableApply record){

        Integer originalId = mta.getOriginalId();
        Integer modifyId = mta.getModifyId();
        byte type = mta.getType();

        if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_ADD) {

            CadreReward modify = cadreRewardMapper.selectByPrimaryKey(modifyId);
            modify.setId(null);
            modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

            cadreRewardMapper.insertSelective(modify); // 插入新纪录
            record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

        } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

            CadreReward modify = cadreRewardMapper.selectByPrimaryKey(modifyId);
            modify.setId(originalId);
            modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

            cadreRewardMapper.updateByPrimaryKey(modify); // 覆盖原纪录

        } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

            // 更新最后删除的记录内容
            record.setOriginalJson(JSONUtils.toString(cadreRewardMapper.selectByPrimaryKey(originalId), false));
            // 删除原纪录
            cadreRewardMapper.deleteByPrimaryKey(originalId);
        }

        return record;
    }
}
