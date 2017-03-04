package service.cadre;

import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreEdu;
import domain.cadre.CadreEduExample;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.modify.ModifyTableApplyMapper;
import service.BaseMapper;
import service.base.MetaTypeService;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CadreEduService extends BaseMapper {

    @Autowired
    private ModifyTableApplyMapper modifyTableApplyMapper;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private MetaTypeService metaTypeService;

    // 获取全日制教育、在职教育
    public CadreEdu[] getByLearnStyle(int cadreId){

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        example.setOrderByClause("enrol_time asc"); // 按时间顺序排序，取时间最晚的
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExample(example);

        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        MetaType fullltimeType = metaTypeMap.get("mt_fullltime");
        MetaType onjobType = metaTypeMap.get("mt_onjob");

        CadreEdu[] result = new CadreEdu[2];
        for (CadreEdu cadreEdu : cadreEdus) {

            Integer learnStyle = cadreEdu.getLearnStyle();
            if(learnStyle.intValue()==fullltimeType.getId()){
                result[0] = cadreEdu; // fullltimeEdu
            }else if(learnStyle.intValue()==onjobType.getId()){
                result[1] = cadreEdu; // onjobEdu
            }
        }
        return result;
    }

    public boolean hasHighEdu(Integer id, int cadreId, Boolean isHighEdu){

        if(BooleanUtils.isFalse(isHighEdu)) return false;

        CadreEduExample example = new CadreEduExample();
        CadreEduExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsHighEduEqualTo(true).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        if(id!=null) criteria.andIdNotEqualTo(id);
        return cadreEduMapper.countByExample(example) > 0;
    }

    // 在读只允许一条记录
    public boolean isNotGraduated(Integer id, int cadreId, Boolean isGraduated){

        if(BooleanUtils.isTrue(isGraduated)) return false;

        CadreEduExample example = new CadreEduExample();
        CadreEduExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsGraduatedEqualTo(false).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        if(id!=null) criteria.andIdNotEqualTo(id);
        return cadreEduMapper.countByExample(example) > 0;
    }

    public boolean hasHighDegree(Integer id, int cadreId, Boolean isHighDegree){

        if(BooleanUtils.isFalse(isHighDegree)) return false;

        CadreEduExample example = new CadreEduExample();
        CadreEduExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsHighDegreeEqualTo(true).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        if(id!=null) criteria.andIdNotEqualTo(id);
        return cadreEduMapper.countByExample(example) > 0;
    }

    public CadreEdu getHighEdu(int cadreId){

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIsHighEduEqualTo(true)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(cadreEdus.size()>0) return  cadreEdus.get(0);
        return null;
    }
    public CadreEdu getHighDegree(int cadreId){

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIsHighDegreeEqualTo(true)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(cadreEdus.size()>0) return  cadreEdus.get(0);
        return null;
    }

    // 更新或添加时，检查规则
    public void checkUpdate(CadreEdu record){

        if(isNotGraduated(record.getId(), record.getCadreId(), record.getIsGraduated())){
            throw new RuntimeException("已经存在一条在读记录");
        }

        if(hasHighEdu(record.getId(), record.getCadreId(), record.getIsHighEdu())){
            throw new RuntimeException("已经存在最高学历");
        }
        if(hasHighDegree(record.getId(), record.getCadreId(), record.getIsHighDegree())){
            throw new RuntimeException("已经存在最高学位");
        }
    }

    // 添加一条正式记录
    @Transactional
    public int insertSelective(CadreEdu record){

        checkUpdate(record);

        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        return cadreEduMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cadreEduMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreEduExample example = new CadreEduExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreEduMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreEduMapper.deleteByExample(example);
    }

    // 更新一条正式记录， 或者更新 添加或修改申请 的记录
    @Transactional
    public void updateByPrimaryKey(CadreEdu record){

        checkUpdate(record);

        //record.setStatus(null);
        cadreEduMapper.updateByPrimaryKey(record);

        /*if(!record.getHasDegree()){ // 没有获得学位，清除学位名称等字段
            updateMapper.del_caderEdu_hasDegree(record.getId());
        }*/
    }

    // 更新修改申请的内容（仅允许本人更新自己的申请）
    @Transactional
    public void updateModify(CadreEdu record, Integer applyId){

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
        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andIdEqualTo(id).andCadreIdEqualTo(cadre.getId()) // 保证本人只更新自己的记录
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        record.setId(null);
        record.setStatus(null);
        if(cadreEduMapper.updateByExampleSelective(record, example)>0) {
            if (!record.getHasDegree()) { // 没有获得学位，清除学位名称等字段
                updateMapper.del_caderEdu_hasDegree(id);
            }

            // 更新申请时间
            ModifyTableApply _record= new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreEdu record, Integer id, boolean isDelete){

        CadreEdu original = null; // 修改、删除申请对应的原纪录
        byte type;
        if(isDelete){ // 删除申请时id不允许为空
            record = cadreEduMapper.selectByPrimaryKey(id);
            original = record;
            type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        }else{
            if(record.getId()==null) // 添加申请
                type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreEduMapper.selectByPrimaryKey(record.getId());
                type = SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;

                if(StringUtils.isBlank(record.getCertificate())){ // 证件留空则保留原证件
                    record.setCertificate(original.getCertificate());
                }
            }
        }

        Integer originalId = original==null?null:original.getId();
        if(type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type==SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE){
            // 如果是修改或删除请求，则只允许一条未审批记录存在
            ModifyTableApplyExample example = new ModifyTableApplyExample();
            example.createCriteria().andOriginalIdEqualTo(originalId) // 此时originalId肯定不为空
                    .andModuleEqualTo(SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU)
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
        cadreEduMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_edu");
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

            CadreEdu modify = cadreEduMapper.selectByPrimaryKey(modifyId);
            modify.setId(null);
            modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

            checkUpdate(modify);
            cadreEduMapper.insertSelective(modify); // 插入新纪录
            record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

        } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

            CadreEdu modify = cadreEduMapper.selectByPrimaryKey(modifyId);
            modify.setId(originalId);
            modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

            checkUpdate(modify);
            cadreEduMapper.updateByPrimaryKey(modify); // 覆盖原纪录

        } else if (type == SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

            // 更新最后删除的记录内容
            record.setOriginalJson(JSONUtils.toString(cadreEduMapper.selectByPrimaryKey(originalId), false));
            // 删除原纪录
            cadreEduMapper.deleteByPrimaryKey(originalId);
        }

        return record;
    }
}
