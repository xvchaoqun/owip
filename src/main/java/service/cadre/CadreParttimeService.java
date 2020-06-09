package service.cadre;

import controller.global.OpException;
import domain.cadre.*;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.apache.ibatis.session.RowBounds;
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
public class CadreParttimeService extends BaseMapper {

    @Autowired
    private CadreService cadreService;

    public List<CadreParttime> list(int cadreId) {

        CadreParttimeExample example = new CadreParttimeExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("start_time asc");

        return cadreParttimeMapper.selectByExample(example);
    }

    @Transactional
    public int insertSelective(CadreParttime record) {

        record.setSortOrder(getNextSortOrder("cadre_parttime", "cadre_id=" + record.getCadreId()
                + " and status=" + SystemConstants.RECORD_STATUS_FORMAL));
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        return cadreParttimeMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreParttimeExample example = new CadreParttimeExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreParttimeMapper.countByExample(example);
            if (count != ids.length) {
                throw new OpException("参数有误");
            }
        }

        CadreParttimeExample example = new CadreParttimeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreParttimeMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreParttime record) {
        record.setStatus(null);
        return cadreParttimeMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int cadreId, int addNum) {

        if (addNum == 0) return;

        CadreParttime entity = cadreParttimeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreParttimeExample example = new CadreParttimeExample();
        if (addNum > 0) {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderGreaterThan(baseSortOrder)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderLessThan(baseSortOrder)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreParttime> overEntities = cadreParttimeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CadreParttime targetEntity = overEntities.get(overEntities.size() - 1);
            if (addNum > 0)
                commonMapper.downOrder("cadre_parttime", "cadre_id=" + cadreId + " and status=" + SystemConstants.RECORD_STATUS_FORMAL,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_parttime", "cadre_id=" + cadreId + " and status=" + SystemConstants.RECORD_STATUS_FORMAL,
                        baseSortOrder, targetEntity.getSortOrder());

            CadreParttime record = new CadreParttime();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreParttimeMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 更新修改申请的内容（仅允许管理员和本人更新自己的申请）
    @Transactional
    public void updateModify(CadreParttime record, Integer applyId) {

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
        CadreParttimeExample example = new CadreParttimeExample();
        CadreParttimeExample.Criteria criteria = example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN)){
            CadreView cadre = cadreService.dbFindByUserId(currentUserId);
            criteria.andCadreIdEqualTo(cadre.getId()); // 保证本人只更新自己的记录
        }

        record.setId(null);
        record.setStatus(null);
        if (cadreParttimeMapper.updateByExampleSelective(record, example) > 0 && mta.getUserId().intValue() == currentUserId) {

            // 更新申请时间
            ModifyTableApply _record = new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreParttime record, Integer id, boolean isDelete, String reason) {

        CadreParttime original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadreParttimeMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreParttimeMapper.selectByPrimaryKey(record.getId());
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
                    .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME)
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
        cadreParttimeMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_parttime");
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

                CadreParttime modify = cadreParttimeMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreParttimeMapper.insertSelective(modify); // 插入新纪录
                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadreParttime modify = cadreParttimeMapper.selectByPrimaryKey(modifyId);
                modify.setId(originalId);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreParttimeMapper.updateByPrimaryKey(modify); // 覆盖原纪录

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                // 更新最后删除的记录内容
                record.setOriginalJson(JSONUtils.toString(cadreParttimeMapper.selectByPrimaryKey(originalId), false));
                // 删除原纪录
                cadreParttimeMapper.deleteByPrimaryKey(originalId);
            }
        }

        CadreParttime modify = new CadreParttime();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadreParttimeMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录
        return record;
    }

    // 导入时使用，用来判断是否覆盖
    public CadreParttime getCadreParttime(int cadreId, String unit, Date startTime) {

        CadreParttimeExample example = new CadreParttimeExample();
        CadreParttimeExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
        .andUnitEqualTo(unit);
        if (startTime != null) {
            criteria.andStartTimeEqualTo(startTime);
        }

        List<CadreParttime> cadreParttimes = cadreParttimeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cadreParttimes.size() == 1 ? cadreParttimes.get(0) : null;
    }

    @Transactional
    public int bacthImport(List<CadreParttime> records) {

        int addCount = 0;
        for (CadreParttime record : records) {
            CadreParttime cadreParttime = getCadreParttime(record.getCadreId(),
            record.getUnit(), record.getStartTime());
            if (cadreParttime == null) {
                insertSelective(record);
                addCount++;
            } else {
                record.setId(cadreParttime.getId());
                updateByPrimaryKeySelective(record);
            }
        }
        return addCount;
    }
}
