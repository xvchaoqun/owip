package service.cadre;

import bean.CadreResume;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.*;
import domain.crp.CrpRecord;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import domain.unit.Unit;
import freemarker.EduSuffix;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.crp.CrpRecordService;
import service.dispatch.DispatchCadreRelateService;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.tags.CmTag;
import sys.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

    // 根据起始时间读取工作经历（用于任免审批表导入时）
    public CadreWork getByWorkTime(int cadreId, Date startTime, Date endTime){

        if(startTime ==null) return null;

        CadreWorkExample example = new CadreWorkExample();
        CadreWorkExample.Criteria criteria = example.createCriteria()
                .andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);;
        criteria.andStartTimeEqualTo(startTime);
        if(endTime != null){
            criteria.andEndTimeEqualTo(endTime);
        }else{
            criteria.andEndTimeIsNull();
        }

        List<CadreWork> records = cadreWorkMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return records.size()>0?records.get(0):null;
    }

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

                if(learnStyle==null) continue; // 学习方式为空时不计入简历预览

                CadreResume eduResume = new CadreResume();
                eduResume.setIsWork(false);
                eduResume.setStartDate(cadreEdu.getEnrolTime());
                eduResume.setEndDate(cadreEdu.getFinishTime());

                String enrolTime = DateUtils.formatDate(cadreEdu.getEnrolTime(), DateUtils.YYYYMM);
                //String finishTime = DateUtils.formatDate(cadreEdu.getFinishTime(), DateUtils.YYYYMM);
                if (learnStyle.intValue() == fulltimeType.getId()) {

                    String major = StringUtils.trimToNull(cadreEdu.getMajor());
                    if(major!=null){
                        major = StringUtils.trimToEmpty(StringUtils.appendIfMissing(cadreEdu.getMajor(), "专业"));
                    }else{
                        major = "";
                    }
                    String detail = String.format("%s%s%s%s%s", StringUtils.trimToEmpty(cadreEdu.getSchool()),
                            StringUtils.trimToEmpty(cadreEdu.getDep()),
                            major,
                            StringUtils.trimToEmpty(EduSuffix.getEduSuffix(cadreEdu.getEduId())),
                            StringUtils.isNotBlank(cadreEdu.getNote())?String.format("（%s）", cadreEdu.getNote()):""
                    );
                    eduResume.setDetail(detail);

                    // 全日制学习经历： 根据开始时间将学习经历插入到工作经历之中。
                    int insertPos = 0;
                    for (int i = 0; i < resumes.size(); i++) {

                        CadreResume resume = resumes.get(i);
                        if(resume.isWork()) {
                            String startDate = DateUtils.formatDate(resume.getStartDate(), DateUtils.YYYYMM);
                            if (enrolTime.compareTo(startDate) <= 0) {
                                insertPos = i;
                                break;
                            }
                            insertPos = i+1;
                        }
                    }

                    resumes.add(insertPos, eduResume);

                } else if (learnStyle.intValue() == onjobType.getId()) {

                    String major = StringUtils.trimToNull(cadreEdu.getMajor());
                    if(major!=null){
                        major = StringUtils.trimToEmpty(StringUtils.appendIfMissing(cadreEdu.getMajor(), "专业"));
                    }else{
                        major = "";
                    }

                    String detail = String.format("在%s%s%s在职%s学习%s%s%s", StringUtils.trimToEmpty(cadreEdu.getSchool()),
                            StringUtils.trimToEmpty(cadreEdu.getDep()),
                            major,
                            StringUtils.trimToEmpty(EduSuffix.getEduSuffix2(cadreEdu.getEduId())),
                            cadreEdu.getIsGraduated()?"毕业":"",
                            cadreEdu.getHasDegree()?
                            String.format("，获%s学位", cadreEdu.getDegree()):"",
                            StringUtils.isNotBlank(cadreEdu.getNote())?String.format("（%s）", cadreEdu.getNote()):"");
                    eduResume.setDetail(detail);

                    // 非全日制学习经历： 根据开始时间和结束时间将学习经历插入到某条工作经历的其间之内。
                    // 第一步： 看结束时间，某条学习经历的结束时间在哪条工作经历之内， 那么这条学习经历就要在这条工作经历的其间。
                    insertSubResume(eduResume, resumes);
                }
            }
        }


        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
        // 挂职经历
        List<CrpRecord> crpRecords = crpRecordService.findRecords(cadre.getUserId());
        for (CrpRecord record : crpRecords) {

            String unitStr = "--";
            if(record.getType()== CrpConstants.CRP_RECORD_TYPE_OUT) {
                unitStr = record.getUnit();
            }else{
                Integer unitId = record.getUnitId();
                Unit unit = CmTag.getUnit(unitId);
                unitStr = CmTag.getSysConfig().getSchoolName();
                if(unit!=null){
                    unitStr += unit.getName();
                }
            }

            String detail = String.format("在%s挂职任%s", unitStr, record.getPost());
            CadreResume crpResume = new CadreResume();
            crpResume.setIsWork(false);
            crpResume.setStartDate(record.getStartDate());
            crpResume.setEndDate(record.getEndDate());
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

        String _startDate = DateUtils.formatDate(subResume.getStartDate(), DateUtils.YYYYMM);
        String _endDate = DateUtils.formatDate(subResume.getEndDate(), DateUtils.YYYYMM);

        CadreResume insertResume = null;
        for (int i = 0; i < resumes.size(); i++) {

            CadreResume resume = resumes.get(i);
            if(resume.isWork()) {
                String startDate = DateUtils.formatDate(resume.getStartDate(), DateUtils.YYYYMM);
                String endDate = DateUtils.formatDate(resume.getEndDate(), DateUtils.YYYYMM);

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
                    String startDate = DateUtils.formatDate(resume.getStartDate(), DateUtils.YYYYMM);
                    if (_startDate.compareTo(startDate) <= 0) {
                        insertPos = i;
                        break;
                    }
                    insertPos = i+1;
                }

                containResumes.add(insertPos, subResume);
            }else{

                List<CadreResume> overlapResumes = insertResume.getOverlapResumes();
                if (overlapResumes == null) overlapResumes = new ArrayList<>();

                int insertPos = 0;
                for (int i = 0; i < overlapResumes.size(); i++) {
                    CadreResume resume = overlapResumes.get(i);
                    String startDate = DateUtils.formatDate(resume.getStartDate(), DateUtils.YYYYMM);
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

    // 其间工作经历转移至主要工作经历
    @Transactional
    public void transfer(Integer id) {

        CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
        Integer fid = cadreWork.getFid();
        if (fid == null) {
            throw new OpException("非其间工作经历，不允许转移。");
        }
        commonMapper.excuteSql("update cadre_work set fid=null where id=" + id);

        updateSubWorkCount(fid);
    }

    // 主要工作经历修改为其间工作经历
    @Transactional
    public void transferToSubWork(int id, int fid) {

        CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
        if (cadreWork.getFid() != null) {
            throw new OpException("非主要工作经历，不允许转移。");
        }
        if(cadreWork.getSubWorkCount()>0){
            throw new OpException("存在其间工作经历，不允许转移。");
        }

        CadreWork topCadreWork = cadreWorkMapper.selectByPrimaryKey(fid);
        if(topCadreWork==null || topCadreWork.getFid()!=null){
            throw new OpException("主要工作经历信息有误。");
        }

        CadreWork record = new CadreWork();
        record.setId(id);
        record.setFid(fid);
        cadreWorkMapper.updateByPrimaryKeySelective(record);

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

        {  // 先删除下面的其间工作经历（如果有）（不包括修改申请生成的记录，它们的fid将会更新为null）
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

        //  如果待删除的记录是其间工作经历，则在删除之后，需要更新它的父工作经历的其间工作经历数量
        List<CadreWork> subCadreWorks = null;
        {
            // 1、读取待删除的记录中是其间工作经历的记录
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andFidIsNotNull();
            subCadreWorks = cadreWorkMapper.selectByExample(example);
        }
        {
            ///2、删除所有的记录
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cadreWorkMapper.deleteByExample(example);

            // 3、更新父工作经历的其间工作数量
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

    // 更新修改申请的内容（仅允许管理员和本人更新自己的申请）
    @Transactional
    public void updateModify(CadreWork record, Integer applyId) {

        if (applyId == null) {
            throw new OpException("数据有误，请稍后再试。");
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if ((!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN) && mta.getUserId().intValue() != currentUserId) ||
                mta.getStatus() != ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new OpException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        int id = record.getId();
        CadreWorkExample example = new CadreWorkExample();
        CadreWorkExample.Criteria criteria = example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN)){
            CadreView cadre = cadreService.dbFindByUserId(currentUserId);
            criteria.andCadreIdEqualTo(cadre.getId()); // 保证本人只更新自己的记录
        }

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
                modify.setSubWorkCount(original.getSubWorkCount()); // 防止申请之后，再添加其间工作经历

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

    public void cadreWork_export(Integer[] ids, int exportType, Integer reserveType, HttpServletResponse response) {

        List<CadreWork> cadreWorks = new ArrayList<>();
        if(exportType==0) { // 现任干部
            cadreWorks = iCadreMapper.getCadreWorks(ids, CadreConstants.CADRE_STATUS_MIDDLE);
        }else if(exportType==1) { // 年轻干部
            cadreWorks = iCadreMapper.getCadreReserveWorks(ids, reserveType, CadreConstants.CADRE_RESERVE_STATUS_NORMAL);
        }

        long rownum = cadreWorks.size();

        String[] titles = {"工作证号|100", "姓名|80", "所在单位|100","所在单位及职务|150|left",
                "开始日期|90", "结束日期|90", "工作类型|150|left",
                "工作单位及担任职务（或专技职务）|350|left", "是否担任领导职务|70", "备注|150|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            CadreWork record = cadreWorks.get(i);
            CadreView cadre = CmTag.getCadreById(record.getCadreId());
            Unit unit = CmTag.getUnit(cadre.getUnitId());
            String[] values = {
                    cadre.getCode(),
                    cadre.getRealname(),
                    unit==null?"":unit.getName(),
                    cadre.getTitle(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYYMM),
                    metaTypeService.getName(record.getWorkType()),
                    record.getDetail(),
                    BooleanUtils.isTrue(record.getIsCadre())?"是":"否",
                    record.getRemark()
            };
            valuesList.add(values);
        }

        String fileName = "工作经历(" + DateUtils.formatDate(new Date(), "yyyyMMdd")+")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
