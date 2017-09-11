package service.cadre;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cadre.CadreWork;
import domain.cadre.CadreWorkExample;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.dispatch.DispatchCadreRelateService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CadreWorkService extends BaseMapper {

    @Autowired
    private DispatchCadreRelateService dispatchCadreRelateService;
    @Autowired
    private CadreService cadreService;

    // 获取树状列表
    public List<CadreWork> findByCadre(int cadreId){

        List<CadreWork> cadreWorks = null;
        {
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andFidIsNull()
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("start_time asc");
            cadreWorks = cadreWorkMapper.selectByExample(example);
        }
        if(cadreWorks!=null) {
            for (CadreWork cadreWork : cadreWorks) {
                Integer fid = cadreWork.getId();
                CadreWorkExample example = new CadreWorkExample();
                example.createCriteria().andFidEqualTo(fid)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                example.setOrderByClause("start_time asc");
                List<CadreWork> subCadreWorks = cadreWorkMapper.selectByExample(example);
                cadreWork.setSubCadreWorks(subCadreWorks);
            }
        }
        return cadreWorks;
    }

    // 更新 子工作经历的数量
    public void updateSubWorkCount(Integer fid){
        if(fid!=null){
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andFidEqualTo(fid)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            int subWorkCount = cadreWorkMapper.countByExample(example);
            CadreWork _mainWork = new CadreWork();
            _mainWork.setId(fid);
            _mainWork.setSubWorkCount(subWorkCount);
            cadreWorkMapper.updateByPrimaryKeySelective(_mainWork);
        }
    }

    @Transactional
    public void insertSelective(CadreWork record){

        record.setSubWorkCount(0);
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        cadreWorkMapper.insertSelective(record); // 先插入

        updateSubWorkCount(record.getFid()); // 必须放插入之后
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreWorkMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }

        {  // 先删除下面的期间工作经历（如果有）（不包括修改申请生成的记录，它们的fid将会更新为null）
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andFidIn(Arrays.asList(ids))
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);

            List<CadreWork> subCadreWorks = cadreWorkMapper.selectByExample(example);
            if(subCadreWorks.size()>0) {
                cadreWorkMapper.deleteByExample(example);

                List<Integer> subCadreWorkIds = new ArrayList<>();
                for (CadreWork subCadreWork : subCadreWorks) {
                    subCadreWorkIds.add(subCadreWork.getId());
                }
                // 同时删除关联的任免文件
                dispatchCadreRelateService.delDispatchCadreRelates(subCadreWorkIds, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
            }
        }

        //  如果待删除的记录是期间工作经历，则在删除之后，需要更新它的父工作经历的期间工作经历数量
        List<CadreWork> subCadreWorks = null;
        {
            // 1、读取待删除的记录中是期间工作经历的记录
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andFidIsNotNull();
            subCadreWorks = cadreWorkMapper.selectByExample(example);
        }
        {
            ///2、删除所有的记录
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cadreWorkMapper.deleteByExample(example);

            // 3、更新父工作经历的期间工作数量
            if(subCadreWorks!=null) {
                for (CadreWork subCadreWork : subCadreWorks) {
                    updateSubWorkCount(subCadreWork.getFid());
                }
            }
        }

        // 同时删除关联的任免文件
        dispatchCadreRelateService.delDispatchCadreRelates(Arrays.asList(ids),  SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreWork record){

        record.setFid(null); // 不能更新所属工作经历
        record.setStatus(null);
        return cadreWorkMapper.updateByPrimaryKeySelective(record);
    }

    // 更新修改申请的内容（仅允许本人更新自己的申请）
    @Transactional
    public void updateModify(CadreWork record, Integer applyId){

        if(applyId==null){
            throw new IllegalArgumentException();
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if (mta.getUserId().intValue() != currentUserId ||
                mta.getStatus() != SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new OpException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        CadreView cadre = cadreService.dbFindByUserId(currentUserId);

        int id = record.getId();
        CadreWorkExample example = new CadreWorkExample();
        example.createCriteria().andIdEqualTo(id).andCadreIdEqualTo(cadre.getId()) // 保证本人只更新自己的记录
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        record.setId(null);
        record.setFid(null);
        record.setStatus(null);
        if(cadreWorkMapper.updateByExampleSelective(record, example)>0) {

            // 更新申请时间
            ModifyTableApply _record= new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreWork record, Integer id, boolean isDelete){

        CadreWork original = null; // 修改、删除申请对应的原纪录
        byte type;
        if(isDelete){ // 删除申请时id不允许为空
            record = cadreWorkMapper.selectByPrimaryKey(id);
            original = record;
            type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        }else{
            if(record.getId()==null) // 添加申请
                type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreWorkMapper.selectByPrimaryKey(record.getId());
                type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;
            }
        }

        Integer originalId = original==null?null:original.getId();
        if(type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type==SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE){
            // 如果是修改或删除请求，则只允许一条未审批记录存在
            ModifyTableApplyExample example = new ModifyTableApplyExample();
            example.createCriteria().andOriginalIdEqualTo(originalId) // 此时originalId肯定不为空
                    .andModuleEqualTo(SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK)
                    .andStatusEqualTo(SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
            List<ModifyTableApply> applies = modifyTableApplyMapper.selectByExample(example);
            if(applies.size()>0){
                throw new OpException(String.format("当前记录对应的修改或删除申请[序号%s]已经存在，请等待审核。", applies.get(0).getId()));
            }
        }

        Integer userId = ShiroHelper.getCurrentUserId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        record.setCadreId(cadre.getId());  // 保证本人只能提交自己的申请
        record.setId(null);
        record.setStatus(SystemConstants.RECORD_STATUS_MODIFY);
        cadreWorkMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_work");
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

            CadreWork modify = cadreWorkMapper.selectByPrimaryKey(modifyId);
            modify.setId(null);
            modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

            cadreWorkMapper.insertSelective(modify); // 插入新纪录
            updateSubWorkCount(modify.getFid()); // 必须放插入之后

            record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

        } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

            CadreWork modify = cadreWorkMapper.selectByPrimaryKey(modifyId);
            modify.setId(originalId);
            modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

            cadreWorkMapper.updateByPrimaryKey(modify); // 覆盖原纪录
            updateSubWorkCount(modify.getFid()); // 必须放插入之后

        } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

            // 更新最后删除的记录内容
            record.setOriginalJson(JSONUtils.toString(cadreWorkMapper.selectByPrimaryKey(originalId), false));
            // 删除原纪录
            batchDel(new Integer[]{originalId}, mta.getCadre().getId());
        }

        return record;
    }

}
