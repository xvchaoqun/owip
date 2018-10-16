package service.cadre;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadrePostAdmin;
import domain.cadre.CadrePostAdminExample;
import domain.cadre.CadreView;
import domain.ext.ExtJzg;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.ext.ExtService;
import service.global.CacheService;
import shiro.ShiroHelper;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CadrePostAdminService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ExtService extService;

    // 是否已有当前管理岗位
    public boolean idDuplicate(Integer id, int cadreId){

        CadrePostAdminExample example = new CadrePostAdminExample();
        CadrePostAdminExample.Criteria criteria = example.createCriteria()
                .andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL)
                .andIsCurrentEqualTo(true);
        if(id!=null){
            criteria.andIdNotEqualTo(id);
        }
        return cadrePostAdminMapper.countByExample(example)>0;
    }

    public void syncPost(CadrePostAdmin record){

        CadreView cv = cadreViewMapper.selectByPrimaryKey(record.getCadreId());
        ExtJzg extJzg = extService.getExtJzg(cv.getCode());
        // 如果是系统注册账号(extJzg=null)，则不同步人事库信息，使用系统的相关数据
        if(BooleanUtils.isTrue(CmTag.getSysConfig().getUseCadrePost()) || extJzg == null) {

            Cadre cadre = cadreMapper.selectByPrimaryKey(record.getCadreId());
            int userId = cadre.getUserId();
            String manageLevel = SqlUtils.toParamValue(metaTypeService.getName(record.getLevel()));
            String manageLevelTime = SqlUtils.toParamValue(DateUtils.formatDate(record.getGradeTime(), DateUtils.YYYY_MM_DD));

            commonMapper.excuteSql(String.format("update sys_teacher_info set manage_level=%s, " +
                            "manage_level_time=%s where user_id=%s",
                    manageLevel, manageLevelTime, userId));

            cacheService.clearCadreCache();
        }
    }

    // 一次性同步所有的系统设定的当前岗位，作为干部档案页的岗位信息
    @Transactional
    public void syncAllCadrePost(){

        CadrePostAdminExample example = new CadrePostAdminExample();
        example.createCriteria()
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL)
                .andIsCurrentEqualTo(true);
        List<CadrePostAdmin> records = cadrePostAdminMapper.selectByExample(example);
        for (CadrePostAdmin record : records) {
            syncPost(record);
        }
    }

    @Transactional
    public void insertSelective(CadrePostAdmin record) {

        if(record.getIsCurrent() && idDuplicate(null, record.getCadreId())) {
            throw new OpException("已经存在当前管理岗位。");
        }

        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        cadrePostAdminMapper.insertSelective(record);
        if(record.getIsCurrent()){
            syncPost(record);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadrePostAdminExample example = new CadrePostAdminExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            long count = cadrePostAdminMapper.countByExample(example);
            if (count != ids.length) {
                throw new IllegalArgumentException("数据异常");
            }
        }

        CadrePostAdminExample example = new CadrePostAdminExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePostAdminMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CadrePostAdmin record) {

        if(record.getIsCurrent() && idDuplicate(record.getId(), record.getCadreId())) {
            throw new OpException("已经存在当前管理岗位。");
        }

        record.setStatus(null);
        cadrePostAdminMapper.updateByPrimaryKeySelective(record);

        if(record.getIsCurrent()){
            syncPost(record);
        }
    }

    // 更新修改申请的内容（仅允许本人更新自己的申请）
    @Transactional
    public void updateModify(CadrePostAdmin record, Integer applyId) {

        if (applyId == null) {
            throw new IllegalArgumentException();
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if (mta.getUserId().intValue() != currentUserId ||
                mta.getStatus() != ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new OpException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        CadreView cadre = CmTag.getCadreByUserId(currentUserId);

        int id = record.getId();
        CadrePostAdminExample example = new CadrePostAdminExample();
        example.createCriteria().andIdEqualTo(id).andCadreIdEqualTo(cadre.getId()) // 保证本人只更新自己的记录
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        record.setId(null);
        record.setStatus(null);
        if (cadrePostAdminMapper.updateByExampleSelective(record, example) > 0) {

            // 更新申请时间
            ModifyTableApply _record = new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadrePostAdmin record, Integer id, boolean isDelete) {

        // 拥有管理干部信息或管理干部本人信息的权限，不允许提交申请
        if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN)
                || ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMINSELF)){
            throw new OpException("您有直接修改[干部基本信息-干部信息]的权限，请勿在此提交申请。");
        }

        CadrePostAdmin original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadrePostAdminMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadrePostAdminMapper.selectByPrimaryKey(record.getId());
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;
            }
        }

        Integer originalId = original == null ? null : original.getId();
        if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {
            // 如果是修改或删除请求，则只允许一条未审批记录存在
            ModifyTableApplyExample example = new ModifyTableApplyExample();
            example.createCriteria().andOriginalIdEqualTo(originalId) // 此时originalId肯定不为空
                    .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN)
                    .andStatusEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
            List<ModifyTableApply> applies = modifyTableApplyMapper.selectByExample(example);
            if (applies.size() > 0) {
                throw new OpException(String.format("当前记录对应的修改或删除申请[序号%s]已经存在，请等待审核。", applies.get(0).getId()));
            }
        }

        Integer userId = ShiroHelper.getCurrentUserId();

        CadreView cadre = CmTag.getCadreByUserId(userId);
        record.setCadreId(cadre.getId());  // 保证本人只能提交自己的申请
        record.setId(null);
        record.setStatus(SystemConstants.RECORD_STATUS_MODIFY);
        cadrePostAdminMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_post_admin");
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

                CadrePostAdmin modify = cadrePostAdminMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                if(modify.getIsCurrent() && idDuplicate(null, modify.getCadreId())) {
                    throw new OpException("已经存在当前管理岗位。");
                }

                cadrePostAdminMapper.insertSelective(modify); // 插入新纪录
                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID
                if(modify.getIsCurrent()){
                    syncPost(modify);
                }
            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadrePostAdmin modify = cadrePostAdminMapper.selectByPrimaryKey(modifyId);
                modify.setId(originalId);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                if(modify.getIsCurrent() && idDuplicate(originalId, modify.getCadreId())) {
                    throw new OpException("已经存在当前管理岗位。");
                }
                cadrePostAdminMapper.updateByPrimaryKey(modify); // 覆盖原纪录
                if(modify.getIsCurrent()){
                    syncPost(modify);
                }
            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                // 更新最后删除的记录内容
                record.setOriginalJson(JSONUtils.toString(cadrePostAdminMapper.selectByPrimaryKey(originalId), false));
                // 删除原纪录
                cadrePostAdminMapper.deleteByPrimaryKey(originalId);
            }
        }

        CadrePostAdmin modify = new CadrePostAdmin();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadrePostAdminMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录
        return record;
    }
}
