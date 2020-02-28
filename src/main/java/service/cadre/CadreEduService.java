package service.cadre;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.*;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaClassService;
import service.base.MetaTypeService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class CadreEduService extends BaseMapper {

    @Autowired
    private CadreService cadreService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MetaClassService metaClassService;

    //根据code找到领导干部的学习经历中，对应的学历学位，同时更新最高学历和最高学位
    public void updateHighEdu(String code){

        SysUserView vu = sysUserService.findByCode(code);
        Integer userId = vu.getUserId();
        CadreExample cadreExample = new CadreExample();
        cadreExample.createCriteria().andUserIdEqualTo(userId);
        List<Cadre> cadres = cadreMapper.selectByExample(cadreExample);
        Byte status = 0;
        List<Integer> eduIds = new ArrayList<>();
        eduIds.add(159);
        eduIds.add(182);
        List<Integer> cadreIds = new ArrayList<>();
        for (Cadre cadre : cadres){
            Integer cadreId = cadre.getId();
            CadreEduExample example = new CadreEduExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(status);
            List<CadreEdu> cadreEdus = cadreEduMapper.selectByExample(example);
            example.createCriteria().andEduIdIn(eduIds);
            cadreEdus.remove(example);
            Date latestTime = cadreEdus.get(0).getFinishTime();
            Integer cadreEduId = cadreEdus.get(0).getId();
            for (CadreEdu cadreEdu : cadreEdus){
                Date time = cadreEdu.getFinishTime();
                if (time != null) {
                    if (time.after(latestTime)) {
                        latestTime = time;
                        cadreEduId = cadreEdu.getId();
                    }
                }
            }
            CadreEdu _cadreEdu = cadreEduMapper.selectByPrimaryKey(cadreEduId);
            CadreEdu record = new CadreEdu();
            record.setId(cadreEduId);
            if (_cadreEdu.getIsGraduated() != null) {
                if (_cadreEdu.getIsGraduated()) {
                    record.setIsHighDegree(true);
                }
            }
            if (_cadreEdu.getHasDegree()) {
                record.setIsHighEdu(true);
            }
            cadreEduMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 根据起始时间读取学习经历（用于任免审批表导入时）
    public CadreEdu getByEduTime(int cadreId, Date enrolTime, Date finishTime) {

        if (enrolTime == null) return null;

        CadreEduExample example = new CadreEduExample();
        CadreEduExample.Criteria criteria = example.createCriteria()
                .andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        criteria.andEnrolTimeEqualTo(enrolTime);
        if (finishTime != null) {
            criteria.andFinishTimeEqualTo(finishTime);
        } else {
            criteria.andFinishTimeIsNull();
        }

        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return cadreEdus.size() > 0 ? cadreEdus.get(0) : null;
    }

    public List<Integer> needTutorEduTypes() {

        MetaType eduDoctor = CmTag.getMetaTypeByCode("mt_edu_doctor");
        MetaType eduMaster = CmTag.getMetaTypeByCode("mt_edu_master");
        MetaType eduSstd = CmTag.getMetaTypeByCode("mt_edu_sstd");
        List<Integer> needTutorEduTypeIds = new ArrayList<>();
        needTutorEduTypeIds.add(eduDoctor.getId());
        needTutorEduTypeIds.add(eduMaster.getId());
        needTutorEduTypeIds.add(eduSstd.getId());

        return needTutorEduTypeIds;
    }

    // 查找某个干部的学习经历
    public List<CadreEdu> list(int cadreId, Boolean isGraduated) {

        List<CadreEdu> cadreEdus = null;
        {
            CadreEduExample example = new CadreEduExample();
            CadreEduExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            if (isGraduated != null)
                criteria.andIsGraduatedEqualTo(isGraduated);
            example.setOrderByClause("enrol_time asc");

            cadreEdus = cadreEduMapper.selectByExample(example);
        }
        if(cadreEdus!=null) {
            for (CadreEdu cadreEdu : cadreEdus) {
                Integer fid = cadreEdu.getId();
                CadreWorkExample example = new CadreWorkExample();
                example.createCriteria().andFidEqualTo(fid)
                        .andIsEduWorkEqualTo(true)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                example.setOrderByClause("start_time asc");
                List<CadreWork> subCadreWorks = cadreWorkMapper.selectByExample(example);
                cadreEdu.setSubCadreWorks(subCadreWorks);
            }
        }

        return cadreEdus;
    }

    // 获取全日制教育、在职教育（各读取已毕业的最后一条记录）
    public CadreEdu[] getByLearnStyle(int cadreId) {

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL)
                .andIsGraduatedEqualTo(true);
        example.setOrderByClause("enrol_time asc"); // 按时间顺序排序，取时间最晚的
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExample(example);

        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        MetaType fullltimeType = metaTypeMap.get("mt_fulltime");
        MetaType onjobType = metaTypeMap.get("mt_onjob");

        CadreEdu[] result = new CadreEdu[2];
        for (CadreEdu cadreEdu : cadreEdus) {

            Integer learnStyle = cadreEdu.getLearnStyle();
            if (learnStyle != null) {
                if (learnStyle.intValue() == fullltimeType.getId()) {
                    result[0] = cadreEdu; // fulltimeEdu
                } else if (learnStyle.intValue() == onjobType.getId()) {
                    result[1] = cadreEdu; // onjobEdu
                }
            }
        }
        return result;
    }

    public boolean hasHighEdu(Integer id, int cadreId, Boolean isHighEdu) {

        return hasHighEdu(id, cadreId, isHighEdu, SystemConstants.RECORD_STATUS_FORMAL);
    }

    public boolean hasHighEdu(Integer id, int cadreId, Boolean isHighEdu, byte status) {

        // isHighEdu=null return false
        if (BooleanUtils.isNotTrue(isHighEdu)) return false;

        CadreEduExample example = new CadreEduExample();
        CadreEduExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsHighEduEqualTo(true).andStatusEqualTo(status);
        if (id != null) criteria.andIdNotEqualTo(id);
        return cadreEduMapper.countByExample(example) > 0;
    }

    // 在读只允许一条记录
    public boolean isNotGraduated(Integer id, int cadreId, Boolean isGraduated) {

        if (BooleanUtils.isTrue(isGraduated)) return false;

        CadreEduExample example = new CadreEduExample();
        CadreEduExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsGraduatedEqualTo(false).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        if (id != null) criteria.andIdNotEqualTo(id);
        return cadreEduMapper.countByExample(example) > 0;
    }

    /*public boolean hasFirstHighDegree(Integer id, int cadreId, Boolean isHighDegree){

        return hasFirstHighDegree(id, cadreId, isHighDegree, SystemConstants.RECORD_STATUS_FORMAL);
    }*/

    // 是否有第一个最高学位（供完整性判断时使用）
    public boolean hasFirstHighDegree(Integer id, int cadreId, byte status) {

        return firstHighDegreeCount(id, cadreId, status) > 0;
    }

    public int firstHighDegreeCount(Integer id, int cadreId, byte status) {

        CadreEduExample example = new CadreEduExample();
        CadreEduExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsHighDegreeEqualTo(true).andIsSecondDegreeEqualTo(false)
                .andStatusEqualTo(status);
        if (id != null) criteria.andIdNotEqualTo(id);
        return (int) cadreEduMapper.countByExample(example);
    }

    // 校正第一个最高学位
    private void adjustHighDegreeAnyway(int cadreId) {
        /**
         * 当前记录更新为第二个最高学位，必须要存在第一个最高学位，否则选择一个时间最早的为第一个最高学位
         */
        int firstHighDegreeCount = firstHighDegreeCount(null, cadreId, SystemConstants.RECORD_STATUS_FORMAL);
        if (firstHighDegreeCount == 0) {

            CadreEduExample example = new CadreEduExample();
            example.createCriteria().andCadreIdEqualTo(cadreId)
                    .andIsHighDegreeEqualTo(true)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("enrol_time asc");
            List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
            if (cadreEdus.size() > 0) {
                CadreEdu record = new CadreEdu();
                record.setId(cadreEdus.get(0).getId());
                record.setIsSecondDegree(false);
                cadreEduMapper.updateByPrimaryKeySelective(record);
            }
        } else if (firstHighDegreeCount > 1) { // 如果存在多个第一个最高学位，则仅保留时间最早的一个

            CadreEduExample example = new CadreEduExample();
            example.createCriteria().andCadreIdEqualTo(cadreId)
                    .andIsHighDegreeEqualTo(true)
                    .andIsSecondDegreeEqualTo(false)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("enrol_time asc");
            List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
            if (cadreEdus.size() > 0) {

                example = new CadreEduExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andIsHighDegreeEqualTo(true)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL)
                        .andIdNotEqualTo(cadreEdus.get(0).getId());

                CadreEdu record = new CadreEdu();
                record.setIsSecondDegree(true);
                cadreEduMapper.updateByExampleSelective(record, example);
            }
        }
    }

    /**
     * 如果干部的最高学位是双学位或多个学位，则调整干部的最高学位，
     * 使其只有一个最高学位（is_second_degree=0)，其他都是第二个学位（is_second_degree=1)
     *
     * @param id             待添加或更新的记录ID
     * @param isSecondDegree 更新为是否第二个学位
     */
    public void adjustHighDegree(int id, Integer cadreId, boolean isSecondDegree) {

        if(cadreId==null) return;

        CadreEdu cadreEdu = cadreEduMapper.selectByPrimaryKey(id);
        if (cadreEdu == null || BooleanUtils.isNotTrue(cadreEdu.getIsHighDegree())) {

            // 修改非最高学位或删除记录或异常的情况，找不到当前记录，则检验一下
            adjustHighDegreeAnyway(cadreId);
        } else {

            cadreId = cadreEdu.getCadreId();
            {
                // 先更新当前学历
                CadreEdu record = new CadreEdu();
                record.setId(id);
                record.setIsSecondDegree(isSecondDegree);
                cadreEduMapper.updateByPrimaryKeySelective(record);
            }

            if (isSecondDegree) {

                adjustHighDegreeAnyway(cadreId);
            } else {
                /**
                 * 当前记录更新为第一个最高学位，则其他最高学位均更新为第二个最高学位
                 */
                CadreEduExample example = new CadreEduExample();
                example.createCriteria().andCadreIdEqualTo(cadreId)
                        .andIsHighDegreeEqualTo(true)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL)
                        .andIdNotEqualTo(id);

                CadreEdu record = new CadreEdu();
                record.setIsSecondDegree(true);
                cadreEduMapper.updateByExampleSelective(record, example);
            }
        }
    }

    public CadreEdu getHighEdu(int cadreId) {

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsGraduatedEqualTo(true).andIsHighEduEqualTo(true)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (cadreEdus.size() > 0) return cadreEdus.get(0);
        return null;
    }

    // 获取全日制/在职的最高学历
    public CadreEdu getHighEdu(int cadreId, int learnStyle) {

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsGraduatedEqualTo(true)
                .andLearnStyleEqualTo(learnStyle).andEduIdIsNotNull()
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);

        example.setOrderByClause("is_high_edu desc, is_second_degree asc, enrol_time desc");
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExample(example);

        if(cadreEdus.size()==1) return cadreEdus.get(0);

        // 如果存在多个最高学历，则以第一个最高学位对应的学历为准
        for (CadreEdu cadreEdu : cadreEdus) {
            if(BooleanUtils.isFalse(cadreEdu.getIsSecondDegree())
                    && cadreEdu.getEduId()!=null) { // 且学历不为空的
                return cadreEdu;
            }
        }

        return cadreEdus.size()>0?cadreEdus.get(0):null;
    }

    // 获取全日制/在职的最高学位
    public List<CadreEdu> getHighDegrees(int cadreId, Integer learnStyle) {

        CadreEduExample example = new CadreEduExample();
        CadreEduExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsGraduatedEqualTo(true)
                .andHasDegreeEqualTo(true)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);

        if(learnStyle!=null){
            criteria.andLearnStyleEqualTo(learnStyle);
        }
        example.setOrderByClause("is_high_degree desc, is_second_degree asc, enrol_time desc");

        List<CadreEdu> records = cadreEduMapper.selectByExample(example);
        List<CadreEdu> cadreEdus = new ArrayList<>();

        for (CadreEdu record : records) {
            if(BooleanUtils.isTrue(record.getIsHighDegree())){
                cadreEdus.add(record);
            }
        }

        if(cadreEdus.size()==0){ // 如果没有设置最高学位，则读取第一个学位
            if(records.size()>0){
                cadreEdus.add(records.get(0));
            }
        }

        return cadreEdus;
    }

    // 返回某个学历
    public CadreEdu getCadreEdu(int cadreId, int eduId) {

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andEduIdEqualTo(eduId)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (cadreEdus.size() > 0) return cadreEdus.get(0);
        return null;
    }

    // 更新或添加时，检查规则
    public void checkUpdate(CadreEdu record) {

        if (isNotGraduated(record.getId(), record.getCadreId(), record.getIsGraduated())) {
            throw new OpException("已经存在一条在读记录");
        }

        // 非第二最高学位，不允许存在多个最高学历
        if (BooleanUtils.isNotTrue(record.getIsSecondDegree())
                && hasHighEdu(record.getId(), record.getCadreId(), record.getIsHighEdu())) {

            if(BooleanUtils.isTrue(record.getIsHighDegree())){

                throw new OpException("已经存在最高学历（注：如获得了双学位请勾选“第二个最高学位”选项）");
            }else {
                throw new OpException("已经存在最高学历");
            }
        }
    }

    // 添加一条正式记录
    @Transactional
    public void insertSelective(CadreEdu record) {

        checkUpdate(record);

        record.setSubWorkCount(0);
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        cadreEduMapper.insertSelective(record);

        adjustHighDegree(record.getId(), record.getCadreId(), BooleanUtils.isTrue(record.getIsSecondDegree()));
    }

    @Transactional
    public void del(Integer id) {

        cadreEduMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreEduExample example = new CadreEduExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            long count = cadreEduMapper.countByExample(example);
            if (count != ids.length) {
                throw new OpException("参数有误");
            }
        }

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreEduMapper.deleteByExample(example);
    }

    // 更新一条正式记录， 或者更新 添加或修改申请 的记录
    @Transactional
    public void updateByPrimaryKey(CadreEdu record) {

        checkUpdate(record);

        if(!record.getHasDegree()){
            record.setIsSecondDegree(false);
        }

        //record.setStatus(null);
        CadreEdu cadreEdu = cadreEduMapper.selectByPrimaryKey(record.getId());
        record.setSubWorkCount(cadreEdu.getSubWorkCount()); // 保留原来的其间工作数量

        cadreEduMapper.updateByPrimaryKey(record); // 覆盖更新

        // 学历允许为空（有些特殊时期只有学位，没有学历）
        if (record.getEduId() == null) {
            commonMapper.excuteSql("update cadre_edu set edu_id=null where id=" + record.getId());
        }

        // 调整最高学位
        adjustHighDegree(record.getId(), record.getCadreId(), record.getIsSecondDegree());
    }

    // 更新修改申请的内容（仅允许管理员和本人更新自己的申请）
    @Transactional
    public void updateModify(CadreEdu record, Integer applyId) {

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
        CadreEduExample example = new CadreEduExample();
        CadreEduExample.Criteria criteria = example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN)) {
            CadreView cadre = cadreService.dbFindByUserId(currentUserId);
            criteria.andCadreIdEqualTo(cadre.getId()); // 保证本人只更新自己的记录
        }

        record.setId(null);
        record.setStatus(null);
        if (cadreEduMapper.updateByExampleSelective(record, example) > 0) {
            if (!record.getHasDegree()) { // 没有获得学位，清除学位名称等字段
                commonMapper.excuteSql("update cadre_edu set degree=null, is_high_degree=null,is_second_degree=0," +
                        " degree_country=null, degree_unit=null, degree_time=null where id=" + id);
            }

            if(mta.getUserId().intValue() == currentUserId) {
                // 更新申请时间
                ModifyTableApply _record = new ModifyTableApply();
                _record.setId(mta.getId());
                _record.setCreateTime(new Date());
                modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
            }
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreEdu record, Integer id, boolean isDelete, String reason) {

        Integer userId = ShiroHelper.getCurrentUserId();

        CadreEdu original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadreEduMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) { // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            }else { // 修改申请

                {
                    // 如果不存在删除申请时，则不允许提交存在最高学位（学历）的修改申请
                    ModifyTableApplyExample example = new ModifyTableApplyExample();
                    example.createCriteria()
                            .andApplyUserIdEqualTo(userId)
                            .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU)
                            .andStatusEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
                    if (modifyTableApplyMapper.countByExample(example) == 0) {
                        checkUpdate(record);
                    }
                }

                original = cadreEduMapper.selectByPrimaryKey(record.getId());
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;

                if (StringUtils.isBlank(record.getCertificate())) { // 证件留空则保留原证件
                    record.setCertificate(original.getCertificate());
                }
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
                    .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU)
                    .andStatusEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
            List<ModifyTableApply> applies = modifyTableApplyMapper.selectByExample(example);
            if (applies.size() > 0) {
                throw new OpException(String.format("当前记录对应的修改或删除申请[序号%s]已经存在，请等待审核。", applies.get(0).getId()));
            }
        }

        CadreView cadre = cadreService.dbFindByUserId(userId);
        record.setCadreId(cadre.getId());  // 保证本人只能提交自己的申请
        record.setId(null);
        record.setStatus(SystemConstants.RECORD_STATUS_MODIFY);
        if (!record.getHasDegree()) { // 没有获得学位，清除学位名称等字段
            record.setDegree(null);
            record.setIsHighDegree(null);
            record.setDegreeCountry(null);
            record.setDegreeUnit(null);
            record.setDegreeTime(null);
        }
        cadreEduMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_edu");
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

            // 以下四个字段供校正第一个最高学位使用
            Integer id = null;
            Integer cadreId = null;
            boolean isSecondHighDegree = false;

            if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD) {

                CadreEdu modify = cadreEduMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
                modify.setSubWorkCount(0);

                checkUpdate(modify);
                cadreEduMapper.insertSelective(modify); // 插入新纪录
                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

                id = modify.getId();
                cadreId = modify.getCadreId();
                isSecondHighDegree = BooleanUtils.isTrue(modify.getIsSecondDegree());

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadreEdu modify = cadreEduMapper.selectByPrimaryKey(modifyId);
                CadreEdu original = cadreEduMapper.selectByPrimaryKey(originalId);
                if(original!=null) {
                    modify.setId(originalId);
                    modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                    modify.setSubWorkCount(original.getSubWorkCount()); // 防止申请之后，再添加其间工作经历

                    checkUpdate(modify);
                    cadreEduMapper.updateByPrimaryKey(modify); // 覆盖原纪录
                }

                id = originalId;
                cadreId = modify.getCadreId();
                isSecondHighDegree = BooleanUtils.isTrue(modify.getIsSecondDegree());

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                CadreEdu original = cadreEduMapper.selectByPrimaryKey(originalId);

                if(original!=null){
                    // 更新最后删除的记录内容
                    record.setOriginalJson(JSONUtils.toString(original, false));
                    // 删除原纪录
                    cadreEduMapper.deleteByPrimaryKey(originalId);

                    id = originalId;
                    cadreId = original.getCadreId();
                    isSecondHighDegree = BooleanUtils.isTrue(original.getIsSecondDegree());
                }
            }

            // 调整最高学位
            adjustHighDegree(id, cadreId, isSecondHighDegree);
        }

        CadreEdu modify = new CadreEdu();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadreEduMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录

        return record;
    }

    public void cadreEdu_export(Integer[] ids, int exportType, Integer reserveType, HttpServletResponse response) {

        List<CadreEdu> cadreEdus = new ArrayList<>();
        if (exportType == 0) { // 现任干部
            cadreEdus = iCadreMapper.getCadreEdus(ids, CadreConstants.CADRE_STATUS_MIDDLE);
        } else if (exportType == 1) { // 年轻干部
            cadreEdus = iCadreMapper.getCadreReserveEdus(ids, reserveType, CadreConstants.CADRE_RESERVE_STATUS_NORMAL);
        }
        long rownum = cadreEdus.size();
        Set<Integer> needTutorEduTypes = new HashSet<>(needTutorEduTypes());

        String[] titles = {"工作证号|100", "姓名|80", "所在单位|100", "所在单位及职务|150|left", "学历|100",
                "毕业/在读|80", "入学时间|100", "毕业时间|90", "是否最高学历|100", "毕业/在读学校|100",
                "院系|80", "所学专业|80", "学校类型|80", "学习方式|80", "是否获得学位|100",
                "学位|80", "是否最高学位|100", "学位授予国家|100", "学位授予单位|100", "学位授予日期|100",
                "导师姓名|80", "导师所在单位及职务|100|left", "学历学位证书|100", "备注|80", "补充说明|80"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreEdu record = cadreEdus.get(i);
            CadreView cadre = CmTag.getCadreById(record.getCadreId());

            Unit unit = CmTag.getUnit(cadre.getUnitId());
            boolean hasDegree = org.apache.commons.lang3.BooleanUtils.isTrue(record.getHasDegree());
            String[] values = {
                    cadre.getCode(),
                    cadre.getRealname(),
                    unit == null ? "" : unit.getName(),
                    cadre.getTitle(),
                    metaTypeService.getName(record.getEduId()),

                    org.apache.commons.lang3.BooleanUtils.isTrue(record.getIsGraduated()) ? "毕业" : "在读",
                    DateUtils.formatDate(record.getEnrolTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(record.getFinishTime(), DateUtils.YYYYMM),
                    org.apache.commons.lang3.BooleanUtils.isTrue(record.getIsHighDegree()) ? "是" : "否",
                    record.getSchool(),

                    record.getDep(),
                    record.getMajor(),
                    CadreConstants.CADRE_SCHOOL_TYPE_MAP.get(record.getSchoolType()),
                    metaTypeService.getName(record.getLearnStyle()),
                    org.apache.commons.lang3.BooleanUtils.isTrue(record.getHasDegree()) ? "是" : "否",

                    hasDegree ? record.getDegree() : "-",
                    hasDegree ? (record.getIsHighDegree() ? "是" : "否") : "-",
                    hasDegree ? record.getDegreeCountry() : "-",
                    hasDegree ? record.getDegreeUnit() : "-",
                    hasDegree ? DateUtils.formatDate(record.getDegreeTime(), DateUtils.YYYYMM) : "-",

                    needTutorEduTypes.contains(record.getEduId()) ? record.getTutorName() : "-",
                    needTutorEduTypes.contains(record.getEduId()) ? record.getTutorTitle() : "-",
                    StringUtils.isBlank(record.getCertificate()) ? "-" : "已上传",
                    "", ""
            };
            valuesList.add(values);
        }
        String fileName = "学习经历(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
