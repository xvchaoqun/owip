package service.cadre;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadrePostPro;
import domain.cadre.CadrePostProExample;
import domain.cadre.CadreView;
import domain.ext.ExtJzg;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.ext.ExtService;
import service.global.CacheHelper;
import shiro.ShiroHelper;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CadrePostProService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private CacheHelper cacheHelper;
    @Autowired
    private ExtService extService;

    // 是否已有当前专技岗位
    public boolean idDuplicate(Integer id, int cadreId){

        CadrePostProExample example = new CadrePostProExample();
        CadrePostProExample.Criteria criteria = example.createCriteria()
                .andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL)
                .andIsCurrentEqualTo(true);
        if(id!=null){
            criteria.andIdNotEqualTo(id);
        }
        return cadrePostProMapper.countByExample(example)>0;
    }

    public void syncPost(CadrePostPro record){

        CadreView cv = iCadreMapper.getCadre(record.getCadreId());
        ExtJzg extJzg = extService.getExtJzg(cv.getCode());

        if(CmTag.getBoolProperty("useCadrePost") || extJzg == null) {

            Cadre cadre = cadreMapper.selectByPrimaryKey(record.getCadreId());
            int userId = cadre.getUserId();
            String proPost = SqlUtils.toParamValue(metaTypeService.getName(record.getPost()));
            String proPostTime = SqlUtils.toParamValue(DateUtils.formatDate(record.getHoldTime(), DateUtils.YYYY_MM_DD));
            String proPostLevel = SqlUtils.toParamValue(metaTypeService.getName(record.getLevel()));
            String proPostLevelTime = SqlUtils.toParamValue(DateUtils.formatDate(record.getGradeTime(), DateUtils.YYYY_MM_DD));

            commonMapper.excuteSql(String.format("update sys_teacher_info set pro_post=%s, " +
                    "pro_post_time=%s, pro_post_level=%s, pro_post_level_time=%s where user_id=%s",
                    proPost, proPostTime, proPostLevel, proPostLevelTime, userId));

            cacheHelper.clearCadreCache();
        }
    }

    // 一次性同步所有的系统设定的当前岗位，作为干部档案页的岗位信息
    @Transactional
    public void syncAllCadrePost(){

        CadrePostProExample example = new CadrePostProExample();
        example.createCriteria()
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL)
                .andIsCurrentEqualTo(true);
        List<CadrePostPro> records = cadrePostProMapper.selectByExample(example);
        for (CadrePostPro record : records) {
            syncPost(record);
        }
    }

    @Transactional
    public void insertSelective(CadrePostPro record) {

        if(record.getIsCurrent() && idDuplicate(null, record.getCadreId())) {
            throw new OpException("已经存在当前专技岗位。");
        }

        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        cadrePostProMapper.insertSelective(record);

        if(record.getIsCurrent()){
            syncPost(record);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadrePostProExample example = new CadrePostProExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            long count = cadrePostProMapper.countByExample(example);
            if (count != ids.length) {
                throw new OpException("参数有误");
            }
        }

        CadrePostProExample example = new CadrePostProExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePostProMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CadrePostPro record) {

        if(record.getIsCurrent() && idDuplicate(record.getId(), record.getCadreId())) {
            throw new OpException("已经存在当前专技岗位。");
        }

        record.setStatus(null);
        cadrePostProMapper.updateByPrimaryKeySelective(record);

        if(record.getIsCurrent()){
            syncPost(record);
        }
    }

    // 更新修改申请的内容（仅允许本人更新自己的申请）
    @Transactional
    public void updateModify(CadrePostPro record, Integer applyId) {

        if (applyId == null) {
            throw new OpException("参数有误");
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if (mta.getUserId().intValue() != currentUserId ||
                mta.getStatus() != ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new OpException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        CadreView cadre = CmTag.getCadreByUserId(currentUserId);

        int id = record.getId();
        CadrePostProExample example = new CadrePostProExample();
        example.createCriteria().andIdEqualTo(id).andCadreIdEqualTo(cadre.getId()) // 保证本人只更新自己的记录
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        record.setId(null);
        record.setStatus(null);
        if (cadrePostProMapper.updateByExampleSelective(record, example) > 0) {

            // 更新申请时间
            ModifyTableApply _record = new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadrePostPro record, Integer id, boolean isDelete) {

        CadrePostPro original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadrePostProMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadrePostProMapper.selectByPrimaryKey(record.getId());
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
                    .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO)
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
        cadrePostProMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_post_pro");
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

                CadrePostPro modify = cadrePostProMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                if(modify.getIsCurrent() && idDuplicate(null, modify.getCadreId())) {
                    throw new OpException("已经存在当前专技岗位。");
                }

                cadrePostProMapper.insertSelective(modify); // 插入新纪录
                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

                if(modify.getIsCurrent()){
                    syncPost(modify);
                }

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadrePostPro modify = cadrePostProMapper.selectByPrimaryKey(modifyId);
                modify.setId(originalId);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                if(modify.getIsCurrent() && idDuplicate(originalId, modify.getCadreId())) {
                    throw new OpException("已经存在当前专技岗位。");
                }

                cadrePostProMapper.updateByPrimaryKey(modify); // 覆盖原纪录

                if(modify.getIsCurrent()){
                    syncPost(modify);
                }

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                // 更新最后删除的记录内容
                record.setOriginalJson(JSONUtils.toString(cadrePostProMapper.selectByPrimaryKey(originalId), false));
                // 删除原纪录
                cadrePostProMapper.deleteByPrimaryKey(originalId);
            }
        }

        CadrePostPro modify = new CadrePostPro();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadrePostProMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录
        return record;
    }
}
