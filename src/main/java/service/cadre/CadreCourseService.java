package service.cadre;

import domain.cadre.Cadre;
import domain.cadre.CadreCourse;
import domain.cadre.CadreCourseExample;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
public class CadreCourseService extends BaseMapper {
    @Autowired
    private CadreService cadreService;

    public List<CadreCourse> find(int cadreId, byte type){

        CadreCourseExample example = new CadreCourseExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("sort_order asc");
        return cadreCourseMapper.selectByExample(example);
    }

    @Transactional
    public void batchAdd(CadreCourse record, String names){
        if(StringUtils.isNotBlank(names)){
            String[] nameArray = names.split("#");
            for (String name : nameArray) {
                if(StringUtils.isNotBlank(name)){
                    record.setId(null);
                    record.setName(name);
                    record.setSortOrder(getNextSortOrder("cadre_course",
                            "cadre_id=" + record.getCadreId() +" and status="+SystemConstants.RECORD_STATUS_FORMAL
                                    + " and type="+record.getType()));
                    record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
                    cadreCourseMapper.insertSelective(record);
                }
            }
        }
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreCourseExample example = new CadreCourseExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreCourseMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }
        CadreCourseExample example = new CadreCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreCourseMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreCourse record){
        record.setStatus(null);
        return cadreCourseMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CadreCourse entity = cadreCourseMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer cadreId = entity.getCadreId();
        Byte type = entity.getType();

        CadreCourseExample example = new CadreCourseExample();
        if (addNum > 0) { // 下降

            example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreCourse> overEntities = cadreCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreCourse targetEntity = overEntities.get(overEntities.size()-1);
            if (addNum > 0)
                commonMapper.downOrder("cadre_course", "cadre_id=" + cadreId +" and status="+SystemConstants.RECORD_STATUS_FORMAL
                        + " and type="+type, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_course", "cadre_id=" + cadreId +" and status="+SystemConstants.RECORD_STATUS_FORMAL
                        + " and type="+type, baseSortOrder, targetEntity.getSortOrder());

            CadreCourse record = new CadreCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreCourseMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 更新修改申请的内容（仅允许本人更新自己的申请）
    @Transactional
    public void updateModify(CadreCourse record, Integer applyId){

        if(applyId==null){
            throw new IllegalArgumentException();
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if (mta.getUserId().intValue() != currentUserId ||
                mta.getStatus() != SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new RuntimeException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        Cadre cadre = cadreService.dbFindByUserId(currentUserId);

        int id = record.getId();
        CadreCourseExample example = new CadreCourseExample();
        example.createCriteria().andIdEqualTo(id).andCadreIdEqualTo(cadre.getId()) // 保证本人只更新自己的记录
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        record.setId(null);
        record.setStatus(null);
        if(cadreCourseMapper.updateByExampleSelective(record, example)>0) {

            // 更新申请时间
            ModifyTableApply _record= new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreCourse record, Integer id, boolean isDelete){

        CadreCourse original = null; // 修改、删除申请对应的原纪录
        byte type;
        if(isDelete){ // 删除申请时id不允许为空
            record = cadreCourseMapper.selectByPrimaryKey(id);
            original = record;
            type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        }else{
            if(record.getId()==null) // 添加申请
                type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreCourseMapper.selectByPrimaryKey(record.getId());
                type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;

            }
        }

        Integer originalId = original==null?null:original.getId();
        if(type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type==SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE){
            // 如果是修改或删除请求，则只允许一条未审批记录存在
            ModifyTableApplyExample example = new ModifyTableApplyExample();
            example.createCriteria().andOriginalIdEqualTo(originalId) // 此时originalId肯定不为空
                    .andModuleEqualTo(SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE)
                    .andStatusEqualTo(SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
            List<ModifyTableApply> applies = modifyTableApplyMapper.selectByExample(example);
            if(applies.size()>0){
                throw new RuntimeException(String.format("当前记录对应的修改或删除申请[序号%s]已经存在，请等待审核。", applies.get(0).getId()));
            }
        }

        Integer userId = ShiroHelper.getCurrentUserId();
        Cadre cadre = cadreService.dbFindByUserId(userId);
        record.setCadreId(cadre.getId());  // 保证本人只能提交自己的申请
        record.setId(null);
        record.setStatus(SystemConstants.RECORD_STATUS_MODIFY);
        cadreCourseMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_course");
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

            CadreCourse modify = cadreCourseMapper.selectByPrimaryKey(modifyId);
            modify.setId(null);
            modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

            cadreCourseMapper.insertSelective(modify); // 插入新纪录
            record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

        } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

            CadreCourse modify = cadreCourseMapper.selectByPrimaryKey(modifyId);
            modify.setId(originalId);
            modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

            cadreCourseMapper.updateByPrimaryKey(modify); // 覆盖原纪录

        } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

            // 更新最后删除的记录内容
            record.setOriginalJson(JSONUtils.toString(cadreCourseMapper.selectByPrimaryKey(originalId), false));
            // 删除原纪录
            cadreCourseMapper.deleteByPrimaryKey(originalId);
        }

        return record;
    }
}
