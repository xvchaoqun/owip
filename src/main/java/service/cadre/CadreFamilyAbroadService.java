package service.cadre;

import controller.global.OpException;
import domain.cadre.*;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.ModifyConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CadreFamilyAbroadService extends BaseMapper {

    public CadreFamilyAbroad get(int id) {

        return cadreFamilyAbroadMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insertSelective(CadreFamilyAbroad record) {

        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        return cadreFamilyAbroadMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cadreFamilyAbroadMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId) {

        if (ids == null || ids.length == 0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreFamilyAbroadExample example = new CadreFamilyAbroadExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            long count = cadreFamilyAbroadMapper.countByExample(example);
            if (count != ids.length) {
                throw new OpException("参数有误");
            }
        }
        CadreFamilyAbroadExample example = new CadreFamilyAbroadExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreFamilyAbroadMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreFamilyAbroad record) {

        record.setStatus(null);
        return cadreFamilyAbroadMapper.updateByPrimaryKeySelective(record);
    }

    // 更新修改申请的内容（仅允许管理员和本人更新自己的申请）
    @Transactional
    public void updateModify(CadreFamilyAbroad record, Integer applyId) {

        if (applyId == null) {
            throw new OpException("参数有误");
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        if ((!ShiroHelper.isPermitted(RoleConstants.PERMISSION_CADREADMIN) && mta.getUserId().intValue() != currentUserId) ||
                mta.getStatus() != ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY) {
            throw new OpException(String.format("您没有权限更新该记录[申请序号:%s]", applyId));
        }

        int id = record.getId();
        CadreFamilyAbroadExample example = new CadreFamilyAbroadExample();
        CadreFamilyAbroadExample.Criteria criteria = example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_MODIFY);

        if(!ShiroHelper.isPermitted(RoleConstants.PERMISSION_CADREADMIN)){
            CadreView cadre = CmTag.getCadreByUserId(currentUserId);
            criteria.andCadreIdEqualTo(cadre.getId()); // 保证本人只更新自己的记录
        }

        record.setId(null);
        record.setStatus(null);
        if (cadreFamilyAbroadMapper.updateByExampleSelective(record, example) > 0 && mta.getUserId().intValue() == currentUserId) {

            // 更新申请时间
            ModifyTableApply _record = new ModifyTableApply();
            _record.setId(mta.getId());
            _record.setCreateTime(new Date());
            modifyTableApplyMapper.updateByPrimaryKeySelective(_record);
        }
    }

    // 添加、修改、删除申请（仅允许本人提交自己的申请）
    @Transactional
    public void modifyApply(CadreFamilyAbroad record, Integer id, boolean isDelete, String reason) {

        CadreFamilyAbroad original = null; // 修改、删除申请对应的原纪录
        byte type;
        if (isDelete) { // 删除申请时id不允许为空
            record = cadreFamilyAbroadMapper.selectByPrimaryKey(id);
            original = record;
            type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE;
        } else {
            if (record.getId() == null) // 添加申请
                type = ModifyConstants.MODIFY_TABLE_APPLY_TYPE_ADD;
            else { // 修改申请
                original = cadreFamilyAbroadMapper.selectByPrimaryKey(record.getId());
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
                    .andModuleEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD)
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
        cadreFamilyAbroadMapper.insertSelective(record);


        ModifyTableApply _record = new ModifyTableApply();
        _record.setModule(ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD);
        _record.setUserId(userId);
        _record.setApplyUserId(userId);
        _record.setTableName("cadre_family_abroad");
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

                CadreFamilyAbroad modify = cadreFamilyAbroadMapper.selectByPrimaryKey(modifyId);
                modify.setId(null);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreFamilyAbroadMapper.insertSelective(modify); // 插入新纪录
                record.setOriginalId(modify.getId()); // 添加申请，更新原纪录ID

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY) {

                CadreFamilyAbroad modify = cadreFamilyAbroadMapper.selectByPrimaryKey(modifyId);
                modify.setId(originalId);
                modify.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

                cadreFamilyAbroadMapper.updateByPrimaryKey(modify); // 覆盖原纪录

            } else if (type == ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE) {

                // 更新最后删除的记录内容
                record.setOriginalJson(JSONUtils.toString(cadreFamilyAbroadMapper.selectByPrimaryKey(originalId), false));
                // 删除原纪录
                cadreFamilyAbroadMapper.deleteByPrimaryKey(originalId);
            }
        }else{
            CmTag.sendMsg(mta.getApplyUserId(), "您提交的【家庭成员移居国（境）外情况】信息修改申请(序号：{0})未通过审核，请进入干部个人信息修改申请模块查看详情", mta.getId()+"");
        }

        CadreFamilyAbroad modify = new CadreFamilyAbroad();
        modify.setId(modifyId);
        modify.setStatus(SystemConstants.RECORD_STATUS_APPROVAL);
        cadreFamilyAbroadMapper.updateByPrimaryKeySelective(modify); // 更新为“已审核”的修改记录
        return record;
    }

    public void cadreFamilyAbroadExport(CadreFamilyAbroadExample example, List<Integer> cadreIds,  HttpServletRequest request, HttpServletResponse response) {
        List<CadreFamilyAbroad> records = cadreFamilyAbroadMapper.selectByExample(example);

        String[] titles = {"工号|150", "姓名|150", "所在单位及职务|300", "称谓|150", "姓名|150",
                "移居国家（地区）|200", "现居住城市|200", "移居时间|100", "移居类别|150", "备注|250"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            CadreFamilyAbroad record = records.get(i);
            if (record != null) {
                CadreView cadreView = CmTag.getCadreById(record.getCadreId());
                CadreFamily cadreFamily = cadreFamilyMapper.selectByPrimaryKey(record.getFamilyId());
                String[] values = {
                        cadreView.getCode() != null ? cadreView.getCode() : "",
                        cadreView.getRealname() != null ? cadreView.getRealname() : "",
                        cadreView.getTitle() != null ? cadreView.getTitle() : "",
                        cadreFamily.getTitle() != null ? CmTag.getMetaType(cadreFamily.getTitle()).getName() : "",
                        cadreFamily.getRealname() != null ? cadreFamily.getRealname() : "",
                        record.getCountry() != null ? record.getCountry() : "",
                        record.getCity() != null ? record.getCity() : "",

                        record.getAbroadTime() != null ? DateUtils.formatDate(record.getAbroadTime(), DateUtils.YYYYMM) : "",
                        record.getType() != null ? CmTag.getMetaType(record.getType()).getName() : "",
                        record.getRemark()
                };
                valuesList.add(values);
            }
        }
        String fileName = "家庭成员海外情况" + "(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ").xlsx";
        SXSSFWorkbook wb = new SXSSFWorkbook(500);

        ExportHelper.createSheet("家庭成员海外情况", wb, titles, valuesList);

        List<Integer> hasCadreIds = records.stream().map(CadreFamilyAbroad::getCadreId).collect(Collectors.toList());
        cadreIds.removeAll(hasCadreIds); // 没有填写家庭成员海外情况的干部

        String[] titles2 = {"工号|150", "姓名|150", "所在单位及职务|300", "无此类情况|150"};
        List<String[]> valuesList2 = new ArrayList<>();
        for (Integer cadreId : cadreIds) {

            CadreInfoCheck cadreInfoCheck = cadreInfoCheckMapper.selectByPrimaryKey(cadreId);
            CadreView cadreView = CmTag.getCadreById(cadreId);
            String[] values = {
                    cadreView.getCode() != null ? cadreView.getCode() : "",
                    cadreView.getRealname() != null ? cadreView.getRealname() : "",
                    cadreView.getTitle() != null ? cadreView.getTitle() : "",
                    (cadreInfoCheck==null||cadreInfoCheck.getFamilyAbroad()==null)?"":"已确认"
            };
            valuesList2.add(values);
        }

        ExportHelper.createSheet("无此类情况", wb, titles2, valuesList2);

        ExportHelper.output(wb, fileName, request, response);

        ExportHelper.export(titles, valuesList, fileName, response);
    }

}
