package service.cadre;

import controller.global.OpException;
import domain.cadre.CadreTrain;
import domain.cadre.CadreTrainExample;
import domain.cadre.CadreView;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CadreTrainService extends BaseMapper {
    @Autowired
    private CadreService cadreService;


    public List<CadreTrain> list(int cadreId) {

        CadreTrainExample example = new CadreTrainExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("start_time asc");

        return cadreTrainMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(CadreTrain record) {

        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        cadreTrainMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreTrainExample example = new CadreTrainExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreTrainMapper.countByExample(example);
            if (count != ids.length) {
                throw new IllegalArgumentException("数据异常");
            }
        }

        CadreTrainExample example = new CadreTrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreTrainMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreTrain record) {
        record.setStatus(null);
        return cadreTrainMapper.updateByPrimaryKeySelective(record);
    }

    // 更新修改申请的内容（仅允许本人更新自己的申请）
    @Transactional
    public void updateModify(CadreTrain record, Integer applyId) {

        if (applyId == null) {
            throw new IllegalArgumentException();
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if (mta.getUserId().intValue() != currentUserId ||
                mta.getStatus() != ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new OpException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        CadreView cadre = cadreService.dbFindByUserId(currentUserId);

        int id = record.getId();
        CadreTrainExample example = new CadreTrainExample();
        example.createCriteria().andIdEqualTo(id).andCadreIdEqualTo(cadre.getId()) // 保证本人只更新自己的记录
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        record.setId(null);
        record.setStatus(null);
        if (cadreTrainMapper.updateByExampleSelective(record, example) > 0) {

            // 更新申请时间
            ModifyTableApply _record = new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreTrain record, Integer id, boolean isDelete) {

        CadreTrain original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadreTrainMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreTrainMapper.selectByPrimaryKey(record.getId());
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;
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
                    .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN)
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
        cadreTrainMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_train");
        _record.setOriginalId(originalId);
        _record.setModifyId(record.getId());
        _record.setType(type);
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

                CadreTrain modify = cadreTrainMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreTrainMapper.insertSelective(modify); // 插入新纪录
                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadreTrain modify = cadreTrainMapper.selectByPrimaryKey(modifyId);
                modify.setId(originalId);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreTrainMapper.updateByPrimaryKey(modify); // 覆盖原纪录

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                // 更新最后删除的记录内容
                record.setOriginalJson(JSONUtils.toString(cadreTrainMapper.selectByPrimaryKey(originalId), false));
                // 删除原纪录
                cadreTrainMapper.deleteByPrimaryKey(originalId);
            }
        }

        CadreTrain modify = new CadreTrain();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadreTrainMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录
        return record;
    }
}
