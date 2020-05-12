package service.cadre;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreFamily;
import domain.cadre.CadreFamilyAbroadExample;
import domain.cadre.CadreFamilyExample;
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
public class CadreFamilyService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;

    public CadreFamily get(int id) {

        return cadreFamilyMapper.selectByPrimaryKey(id);
    }

    // 根据姓名查找（正式记录）
    public CadreFamily get(int cadreId, String realname) {

        if (StringUtils.isBlank(realname)) return null;

        CadreFamilyExample example = new CadreFamilyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andRealnameEqualTo(realname)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);

        List<CadreFamily> cadreFamilies = cadreFamilyMapper.selectByExample(example);
        return cadreFamilies.size() > 0 ? cadreFamilies.get(0) : null;
    }

    public void addCheck(int cadreId, int title) {

        // 任免审批表导入时，不存在的称谓插入后，此处不可使用缓存
        MetaType titleType = metaTypeMapper.selectByPrimaryKey(title);
        boolean isUnique = BooleanUtils.isTrue(titleType.getBoolAttr());

        if (isUnique) {
            CadreFamilyExample example = new CadreFamilyExample();
            CadreFamilyExample.Criteria criteria = example.createCriteria()
                    .andTitleEqualTo(title).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            criteria.andCadreIdEqualTo(cadreId);

            if (cadreFamilyMapper.countByExample(example) > 0) {
                CadreView cv = iCadreMapper.getCadre(cadreId);
                throw new OpException((cv.getUserId().intValue()!=ShiroHelper.getCurrentUserId()?"("+cv.getRealname()+")":"")
                        + metaTypeService.getName(title) + "添加重复");
            }
        }

        //  2019.07.03 注释
        /*CadreFamilyExample example = new CadreFamilyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        if(cadreFamilyMapper.countByExample(example)>=6){
            throw new OpException("最多只允许添加6个家庭成员");
        }*/
    }

    public void modifyCheck(Integer id, int cadreId, int title) {

        MetaType titleType = CmTag.getMetaType(title);
        boolean isUnique = BooleanUtils.isTrue(titleType.getBoolAttr());

        if (isUnique) {
            CadreFamilyExample example = new CadreFamilyExample();
            CadreFamilyExample.Criteria criteria = example.createCriteria()
                    .andTitleEqualTo(title)
                    .andStatusNotEqualTo(SystemConstants.RECORD_STATUS_APPROVAL);
            criteria.andCadreIdEqualTo(cadreId);
            if(id!=null){
                criteria.andIdNotEqualTo(id);
            }

            if (cadreFamilyMapper.countByExample(example) > 0) {
                throw new OpException(metaTypeService.getName(title) + "添加重复");
            }
        }
    }

    public void updateCheck(int id, int cadreId, int title) {

        MetaType titleType = CmTag.getMetaType(title);
        boolean isUnique = BooleanUtils.isTrue(titleType.getBoolAttr());
        if (isUnique) {
            CadreFamilyExample example = new CadreFamilyExample();
            CadreFamilyExample.Criteria criteria = example.createCriteria()
                    .andTitleEqualTo(title).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            criteria.andCadreIdEqualTo(cadreId);
            criteria.andIdNotEqualTo(id);

            if (cadreFamilyMapper.countByExample(example) > 0) {
                throw new OpException(metaTypeService.getName(title) + "添加重复");
            }
        }
    }

    @Transactional
    public int insertSelective(CadreFamily record) {

        if(record.getTitle()!=null) {
            addCheck(record.getCadreId(), record.getTitle());
        }
        if (BooleanUtils.isTrue(record.getWithGod())) {
            record.setBirthday(null);
        }
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        record.setSortOrder(getNextSortOrder("cadre_family",
                "cadre_id=" + record.getCadreId() + " and status=" + SystemConstants.RECORD_STATUS_FORMAL));
        return cadreFamilyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cadreFamilyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreFamilyExample example = new CadreFamilyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            long count = cadreFamilyMapper.countByExample(example);
            if (count != ids.length) {
                throw new OpException("参数有误");
            }
        }

        {
            // 删除关联的家庭成员移居国（境）外的情况
            CadreFamilyAbroadExample example = new CadreFamilyAbroadExample();
            example.createCriteria().andFamilyIdIn(Arrays.asList(ids));
            cadreFamilyAbroadMapper.deleteByExample(example);
        }

        CadreFamilyExample example = new CadreFamilyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreFamilyMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CadreFamily record) {

        if (record.getTitle() != null)
            updateCheck(record.getId(), record.getCadreId(), record.getTitle());

        record.setCadreId(null);
        record.setStatus(null);
        cadreFamilyMapper.updateByPrimaryKeySelective(record);

        if (BooleanUtils.isTrue(record.getWithGod())) {
            commonMapper.excuteSql("update cadre_family set birthday=null where id=" + record.getId());
        }
    }

    // 更新修改申请的内容（仅允许管理员和本人更新自己的申请）
    @Transactional
    public void updateModify(CadreFamily record, Integer applyId) {

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
        CadreFamilyExample example = new CadreFamilyExample();
        CadreFamilyExample.Criteria criteria = example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN)) {
            CadreView cadre = CmTag.getCadreByUserId(currentUserId);
            criteria.andCadreIdEqualTo(cadre.getId()); // 保证本人只更新自己的记录
        }

        record.setId(null);
        record.setStatus(null);
        if (cadreFamilyMapper.updateByExampleSelective(record, example) > 0 && mta.getUserId().intValue() == currentUserId) {

            // 更新申请时间
            ModifyTableApply _record = new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreFamily record, Integer id, boolean isDelete, String reason) {

        CadreFamily original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadreFamilyMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreFamilyMapper.selectByPrimaryKey(record.getId());
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;
            }
        }

        // 拥有管理干部信息或管理干部本人信息的权限，不允许提交申请
        if (CmTag.canDirectUpdateCadreInfo(record.getCadreId())) {
            throw new OpException("您有直接修改[干部基本信息-干部信息]的权限，请勿在此提交申请。");
        }

        Integer originalId = original == null ? null : original.getId();
        if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {
            // 如果是修改或删除请求，则只允许一条未审批记录存在
            ModifyTableApplyExample example = new ModifyTableApplyExample();
            example.createCriteria().andOriginalIdEqualTo(originalId) // 此时originalId肯定不为空
                    .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY)
                    .andStatusEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
            List<ModifyTableApply> applies = modifyTableApplyMapper.selectByExample(example);
            if (applies.size() > 0) {
                throw new OpException(String.format("当前记录对应的修改或删除申请[序号%s]已经存在，请等待审核。", applies.get(0).getId()));
            }
        }

        // 检查是否重复添加了
        if(type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD){

           modifyCheck(record.getId(), record.getCadreId(), record.getTitle());
        }

        Integer userId = ShiroHelper.getCurrentUserId();

        CadreView cadre = CmTag.getCadreByUserId(userId);
        record.setCadreId(cadre.getId());  // 保证本人只能提交自己的申请
        record.setId(null);
        record.setStatus(SystemConstants.RECORD_STATUS_MODIFY);
        cadreFamilyMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_family");
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

                CadreFamily modify = cadreFamilyMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                insertSelective(modify); // 插入新纪录
                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadreFamily original = cadreFamilyMapper.selectByPrimaryKey(originalId);
                if(original!=null) {
                    CadreFamily modify = cadreFamilyMapper.selectByPrimaryKey(modifyId);
                    modify.setId(originalId);
                    modify.setSortOrder(original.getSortOrder()); // 保持和原纪录排序一致
                    modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                    cadreFamilyMapper.updateByPrimaryKey(modify); // 覆盖原纪录
                }

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                // 更新最后删除的记录内容
                record.setOriginalJson(JSONUtils.toString(cadreFamilyMapper.selectByPrimaryKey(originalId), false));
                // 删除原纪录
                cadreFamilyMapper.deleteByPrimaryKey(originalId);
            }
        }

        CadreFamily modify = new CadreFamily();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadreFamilyMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录

        return record;
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        CadreFamily entity = cadreFamilyMapper.selectByPrimaryKey(id);
        Integer cadreId = entity.getCadreId();

        changeOrder("cadre_family", "cadre_id=" + cadreId + " and status=" + SystemConstants.RECORD_STATUS_FORMAL,
                ORDER_BY_ASC, id, addNum);
    }
}
