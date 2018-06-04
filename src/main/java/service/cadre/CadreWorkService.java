package service.cadre;

import bean.CadreResume;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreEdu;
import domain.cadre.CadreView;
import domain.cadre.CadreWork;
import domain.cadre.CadreWorkExample;
import domain.crp.CrpRecord;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import freemarker.EduSuffix;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.crp.CrpRecordService;
import service.dispatch.DispatchCadreRelateService;
import shiro.ShiroHelper;
import sys.constants.DispatchConstants;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CadreWorkService extends BaseMapper {

    @Autowired(required = false)
    private DispatchCadreRelateService dispatchCadreRelateService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreEduService cadreEduService;
    @Autowired
    private CrpRecordService crpRecordService;
    @Autowired
    private MetaTypeService metaTypeService;

    // 干部任免审批表简历预览
    public List<CadreResume> resume(Integer cadreId) {

        List<CadreResume> resumes = new ArrayList<>();

        List<CadreWork> cadreWorks = list(cadreId);
        for (CadreWork cadreWork : cadreWorks) {

            CadreResume resume = new CadreResume();
            resume.setIsWork(true);
            resume.setStartDate(cadreWork.getStartTime());
            resume.setEndDate(cadreWork.getEndTime());
            resume.setDetail(cadreWork.getDetail());

            List<CadreWork> subCadreWorks = cadreWork.getSubCadreWorks();
            if (subCadreWorks != null) {
                List<CadreResume> containResumes = new ArrayList<>();
                List<CadreResume> overlayResumes = new ArrayList<>();
                for (CadreWork subCadreWork : subCadreWorks) {

                    CadreResume r = new CadreResume();
                    r.setIsWork(true);
                    r.setStartDate(subCadreWork.getStartTime());
                    r.setEndDate(subCadreWork.getEndTime());
                    r.setDetail(subCadreWork.getDetail());

                    if(DateUtils.between(subCadreWork.getStartTime(), cadreWork.getStartTime(), cadreWork.getEndTime())) {
                        containResumes.add(r);
                    }else{
                        overlayResumes.add(r);
                    }
                }
                resume.setContainResumes(containResumes);
                resume.setOverlapResumes(overlayResumes);
            }

            resumes.add(resume);
        }

        List<CadreEdu> cadreEdus = cadreEduService.list(cadreId, null);
        if (cadreEdus.size() > 0) {
            Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
            MetaType fulltimeType = metaTypeMap.get("mt_fulltime");
            MetaType onjobType = metaTypeMap.get("mt_onjob");

            for (CadreEdu cadreEdu : cadreEdus) {

                Integer learnStyle = cadreEdu.getLearnStyle();

                CadreResume eduResume = new CadreResume();
                eduResume.setIsWork(false);
                eduResume.setStartDate(cadreEdu.getEnrolTime());
                eduResume.setEndDate(cadreEdu.getFinishTime());

                String enrolTime = DateUtils.formatDate(cadreEdu.getEnrolTime(), "yyyy.MM");
                //String finishTime = DateUtils.formatDate(cadreEdu.getFinishTime(), "yyyy.MM");
                if (learnStyle.intValue() == fulltimeType.getId()) {

                    String detail = String.format("%s%s%s%s", StringUtils.trimToEmpty(cadreEdu.getSchool()),
                            StringUtils.trimToEmpty(cadreEdu.getDep()),
                            StringUtils.trimToEmpty(StringUtils.appendIfMissing(cadreEdu.getMajor(), "专业")),
                            StringUtils.trimToEmpty(EduSuffix.getEduSuffix(cadreEdu.getEduId())));
                    eduResume.setDetail(detail);

                    // 全日制学习经历： 根据开始时间将学习经历插入到工作经历之中。
                    int insertPos = 0;
                    for (int i = 0; i < resumes.size(); i++) {

                        CadreResume resume = resumes.get(i);
                        if(resume.isWork()) {
                            String startDate = DateUtils.formatDate(resume.getStartDate(), "yyyy.MM");
                            if (enrolTime.compareTo(startDate) <= 0) {
                                insertPos = i;
                                break;
                            }
                        }
                    }

                    resumes.add(insertPos, eduResume);

                } else if (learnStyle.intValue() == onjobType.getId()) {

                    String detail = String.format("在%s%s%s在职%s学习%s%s", StringUtils.trimToEmpty(cadreEdu.getSchool()),
                            StringUtils.trimToEmpty(cadreEdu.getDep()),
                            StringUtils.trimToEmpty(StringUtils.appendIfMissing(cadreEdu.getMajor(), "专业")),
                            StringUtils.trimToEmpty(EduSuffix.getEduSuffix2(cadreEdu.getEduId())),
                            cadreEdu.getIsGraduated()?"毕业":"",
                            cadreEdu.getHasDegree()?
                            String.format("，获%s%s", cadreEdu.getMajor(), cadreEdu.getDegree()):""
                            );
                    eduResume.setDetail(detail);

                    // 非全日制学习经历： 根据开始时间和结束时间将学习经历插入到某条工作经历的其间之内。
                    // 第一步： 看结束时间，某条学习经历的结束时间在哪条工作经历之内， 那么这条学习经历就要在这条工作经历的其间。
                    insertSubResume(eduResume, resumes);
                }
            }
        }


        CadreView cv = cadreService.findAll().get(cadreId);
        // 挂职经历
        List<CrpRecord> crpRecords = crpRecordService.findRecords(cv.getUserId());
        for (CrpRecord crpRecord : crpRecords) {

            String detail = String.format("在%s挂职任%s", crpRecord.getUnit(), crpRecord.getPost());
            CadreResume crpResume = new CadreResume();
            crpResume.setIsWork(false);
            crpResume.setStartDate(crpRecord.getStartDate());
            crpResume.setEndDate(crpRecord.getEndDate());
            crpResume.setDetail(detail);

            insertSubResume(crpResume, resumes);
        }

        return resumes;
    }

    /**
     * 把经历插入到工作经历的其间
     *
     *  第一步：看结束时间
     *  第二步：如果开始时间在工作经历的时间之外，则需换行显示
     *
     * @param subResume 待插入的经历
     * @param resumes  当前简历列表
     */
    private void insertSubResume(CadreResume subResume, List<CadreResume> resumes){

        String _startDate = DateUtils.formatDate(subResume.getStartDate(), "yyyy.MM");
        String _endDate = DateUtils.formatDate(subResume.getEndDate(), "yyyy.MM");

        CadreResume insertResume = null;
        for (int i = 0; i < resumes.size(); i++) {

            CadreResume resume = resumes.get(i);
            if(resume.isWork()) {
                String startDate = DateUtils.formatDate(resume.getStartDate(), "yyyy.MM");
                String endDate = DateUtils.formatDate(resume.getEndDate(), "yyyy.MM");

                if(_endDate==null && endDate==null){
                    insertResume = resume;
                    break;
                }else if(_endDate!=null){
                    if(_endDate.compareTo(startDate)>=0 && (endDate==null || _endDate.compareTo(endDate)<=0)){
                        insertResume = resume;
                        break;
                    }
                }
                insertResume = resume;
            }
        }
        if(insertResume == null){
            resumes.add(subResume);
        }else{

            if(DateUtils.between(subResume.getStartDate(), insertResume.getStartDate(), insertResume.getEndDate())) {

                List<CadreResume> containResumes = insertResume.getContainResumes();
                if (containResumes == null) containResumes = new ArrayList<>();

                int insertPos = 0;
                for (int i = 0; i < containResumes.size(); i++) {
                    CadreResume resume = containResumes.get(i);
                    String startDate = DateUtils.formatDate(resume.getStartDate(), "yyyy.MM");
                    if (_startDate.compareTo(startDate) >= 0) {
                        break;
                    }
                    insertPos = i;
                }

                containResumes.add(insertPos, subResume);
            }else{

                List<CadreResume> overlapResumes = insertResume.getOverlapResumes();
                if (overlapResumes == null) overlapResumes = new ArrayList<>();

                int insertPos = 0;
                for (int i = 0; i < overlapResumes.size(); i++) {
                    CadreResume resume = overlapResumes.get(i);
                    String startDate = DateUtils.formatDate(resume.getStartDate(), "yyyy.MM");
                    if (_startDate.compareTo(startDate) >= 0) {
                        break;
                    }
                    insertPos = i;
                }

                overlapResumes.add(insertPos, subResume);
            }
        }
    }

    // 获取树状列表
    public List<CadreWork> list(int cadreId) {

        List<CadreWork> cadreWorks = null;
        {
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andFidIsNull()
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("start_time asc");
            cadreWorks = cadreWorkMapper.selectByExample(example);
        }
        if (cadreWorks != null) {
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
    public void updateSubWorkCount(Integer fid) {
        if (fid != null) {
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andFidEqualTo(fid)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            int subWorkCount = (int) cadreWorkMapper.countByExample(example);
            CadreWork _mainWork = new CadreWork();
            _mainWork.setId(fid);
            _mainWork.setSubWorkCount(subWorkCount);
            cadreWorkMapper.updateByPrimaryKeySelective(_mainWork);
        }
    }

    @Transactional
    public void insertSelective(CadreWork record) {

        record.setSubWorkCount(0);
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        cadreWorkMapper.insertSelective(record); // 先插入

        updateSubWorkCount(record.getFid()); // 必须放插入之后
    }

    // 转移期间工作经历
    @Transactional
    public void transfer(Integer id) {

        CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
        Integer fid = cadreWork.getFid();
        if (fid == null) {
            throw new OpException("非期间工作经历，不允许转移。");
        }
        commonMapper.excuteSql("update cadre_work set fid=null where id=" + id);

        updateSubWorkCount(fid);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            long count = cadreWorkMapper.countByExample(example);
            if (count != ids.length) {
                throw new OpException("数据有误，请稍后再试。");
            }
        }

        {  // 先删除下面的期间工作经历（如果有）（不包括修改申请生成的记录，它们的fid将会更新为null）
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andFidIn(Arrays.asList(ids))
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);

            List<CadreWork> subCadreWorks = cadreWorkMapper.selectByExample(example);
            if (subCadreWorks.size() > 0) {
                cadreWorkMapper.deleteByExample(example);

                List<Integer> subCadreWorkIds = new ArrayList<>();
                for (CadreWork subCadreWork : subCadreWorks) {
                    subCadreWorkIds.add(subCadreWork.getId());
                }
                // 同时删除关联的任免文件
                dispatchCadreRelateService.delDispatchCadreRelates(subCadreWorkIds, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
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
            if (subCadreWorks != null) {
                for (CadreWork subCadreWork : subCadreWorks) {
                    updateSubWorkCount(subCadreWork.getFid());
                }
            }
        }

        // 同时删除关联的任免文件
        dispatchCadreRelateService.delDispatchCadreRelates(Arrays.asList(ids), DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreWork record) {

        record.setFid(null); // 不能更新所属工作经历
        record.setStatus(null);
        return cadreWorkMapper.updateByPrimaryKeySelective(record);
    }

    // 更新修改申请的内容（仅允许本人更新自己的申请）
    @Transactional
    public void updateModify(CadreWork record, Integer applyId) {

        if (applyId == null) {
            throw new OpException("数据有误，请稍后再试。");
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if (mta.getUserId().intValue() != currentUserId ||
                mta.getStatus() != ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
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
        if (cadreWorkMapper.updateByExampleSelective(record, example) > 0) {

            // 更新申请时间
            ModifyTableApply _record = new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreWork record, Integer id, boolean isDelete) {

        CadreWork original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadreWorkMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreWorkMapper.selectByPrimaryKey(record.getId());
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY;
            }
        }

        Integer originalId = original == null ? null : original.getId();
        if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY ||
                type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {
            // 如果是修改或删除请求，则只允许一条未审批记录存在
            ModifyTableApplyExample example = new ModifyTableApplyExample();
            example.createCriteria().andOriginalIdEqualTo(originalId) // 此时originalId肯定不为空
                    .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK)
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
        cadreWorkMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_work");
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

                CadreWork modify = cadreWorkMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreWorkMapper.insertSelective(modify); // 插入新纪录
                updateSubWorkCount(modify.getFid()); // 必须放插入之后

                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadreWork modify = cadreWorkMapper.selectByPrimaryKey(modifyId);
                modify.setId(originalId);

                CadreWork original = cadreWorkMapper.selectByPrimaryKey(originalId);
                modify.setSubWorkCount(original.getSubWorkCount()); // 防止申请之后，再添加期间工作经历

                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreWorkMapper.updateByPrimaryKey(modify); // 覆盖原纪录
                updateSubWorkCount(modify.getFid()); // 必须放插入之后

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                // 更新最后删除的记录内容
                record.setOriginalJson(JSONUtils.toString(cadreWorkMapper.selectByPrimaryKey(originalId), false));
                // 删除原纪录
                batchDel(new Integer[]{originalId}, mta.getCadre().getId());
            }
        }

        CadreWork modify = new CadreWork();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadreWorkMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录

        return record;
    }
}
