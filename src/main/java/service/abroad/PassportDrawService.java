package service.abroad;

import bean.ShortMsgBean;
import controller.global.OpException;
import domain.abroad.*;
import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.base.ShortMsgService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class PassportDrawService extends AbroadBaseMapper {

    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected AbroadShortMsgService abroadShortMsgService;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 添加/更新办理证件记录
    @Transactional
    public void addOrUpdate(PassportDraw record, ApplySelf applySelf) {

        if(record.getId()==null){

            if(applySelf!=null){
                applySelfMapper.insertSelective(applySelf);
                record.setApplyId(applySelf.getId());
            }
            if(record.getApplyDate()==null){
                record.setApplyDate(new Date());
            }

            record.setStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_PASS);
            record.setIsDeleted(false);
            record.setCreateTime(new Date());
            record.setDrawStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN);
            passportDrawMapper.insertSelective(record);
        }else{

            passportDrawMapper.updateByPrimaryKeySelective(record);

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(record.getId());
            if(passportDraw.getApplyId()!=null) {
                applySelf.setId(passportDraw.getApplyId());
                applySelfMapper.updateByPrimaryKeySelective(applySelf);
            }
        }
    }

    @Transactional
    public int batchImport(List<Map<String, Object>> records) {

        int addCount = 0;
        for (Map<String, Object> record : records) {

            PassportDraw passportDraw = (PassportDraw) record.get("passportDraw");
            ApplySelf applySelf = (ApplySelf) record.get("applySelf");

            PassportDraw hasImportPassportDraw = iAbroadMapper.getHasImportPassportDraw(passportDraw.getPassportId(),
                    passportDraw.getRealStartDate());

            if(hasImportPassportDraw==null){

                if(applySelf!=null){
                    applySelfMapper.insertSelective(applySelf);
                    passportDraw.setApplyId(applySelf.getId());
                }

                passportDrawMapper.insertSelective(passportDraw);
                addCount++;

            }else{

                if(applySelf!=null) {
                    if (hasImportPassportDraw.getApplyId() == null) {
                        applySelfMapper.insertSelective(applySelf);
                        passportDraw.setApplyId(applySelf.getId());
                    } else {
                        applySelf.setId(hasImportPassportDraw.getApplyId());
                        applySelfMapper.updateByPrimaryKeySelective(applySelf);
                    }
                }

                passportDraw.setId(hasImportPassportDraw.getId());
                passportDrawMapper.updateByPrimaryKeySelective(passportDraw);
            }
        }

        return addCount;
    }

    // 拒绝归还证件借出记录
    public PassportDraw getRefuseReturnPassportDraw(int passportId) {

        PassportDrawExample example = new PassportDrawExample();
        example.createCriteria().andPassportIdEqualTo(passportId).andIsDeletedEqualTo(false)
                .andDrawStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW)
                .andUsePassportEqualTo(AbroadConstants.ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN);
        List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);

        Assert.isTrue(passportDraws.size()<=1, "证件拒绝归还状态异常");

        return (passportDraws.size()==1)?passportDraws.get(0):null;
    }

    public List<PassportDrawFile> getPassportDrawFiles(int drawId) {

        PassportDrawFileExample example = new PassportDrawFileExample();
        example.createCriteria().andDrawIdEqualTo(drawId);
        return passportDrawFileMapper.selectByExample(example);
    }

    @Transactional
    public int insertSelective(PassportDraw record) {

        record.setIsDeleted(false);
        record.setApplyDate(new Date());
        record.setCreateTime(new Date());
        record.setStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_INIT);
        record.setDrawStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_UNDRAW);
        record.setJobCertify(false);
        return passportDrawMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        passportDrawMapper.deleteByPrimaryKey(id);
    }

    // 删除（默认逻辑删除），真删除只有在逻辑删除之后
    @Transactional
    public void batchDel(Integer[] ids, boolean isDeleted, boolean isReal) {

        if (ids == null || ids.length == 0) return;

        if (isReal) { // 删除已经[逻辑删除]，且未审批的记录
            for (Integer id : ids) {
                PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
                if (passportDraw.getStatus() == AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_INIT
                        && passportDraw.getIsDeleted()) {

                    PassportDrawFileExample example = new PassportDrawFileExample();
                    example.createCriteria().andDrawIdEqualTo(id);
                    passportDrawFileMapper.deleteByExample(example); // 先删除相关材料

                    passportDrawMapper.deleteByPrimaryKey(id);
                } else {
                    throw new OpException("该记录已经审批，不可以删除");
                }
            }
        } else {
            PassportDrawExample example = new PassportDrawExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));

            PassportDraw record = new PassportDraw();
            record.setIsDeleted(isDeleted);
            passportDrawMapper.updateByExampleSelective(record, example);

            for (Integer id : ids) {
                PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
                sysApprovalLogService.add(id, passportDraw.getCadre().getUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                        isDeleted?"逻辑删除":"恢复", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
            }
        }
    }

    // 领取证件
    @Transactional
    public void drawPassport(PassportDraw record) {

        updateByPrimaryKeySelective(record);

        // 将证件标记为已借出
        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(record.getId());
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        if (passport.getIsLent()) {
            throw new OpException("该证件已经借出");
        }
        Passport _record = new Passport();
        _record.setId(passport.getId());
        _record.setIsLent(true);
        passportMapper.updateByPrimaryKeySelective(_record);

        sysApprovalLogService.add(record.getId(), passportDraw.getCadre().getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                "领取证件", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 归还证件
    @Transactional
    public void returnPassport(PassportDraw record) {

        updateByPrimaryKeySelective(record);

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(record.getId());

        if(record.getUsePassport() != AbroadConstants.ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN) {
            // 将证件标记为未借出
            Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
            if (!passport.getIsLent()) {
                throw new OpException("该证件未借出");
            }
            Passport _record = new Passport();
            _record.setId(passport.getId());
            _record.setIsLent(false);
            passportMapper.updateByPrimaryKeySelective(_record);

            sysApprovalLogService.add(record.getId(), passportDraw.getCadre().getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                    "归还证件", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);

            // 归还证件后通知本人
            ShortMsgBean shortMsgBean = abroadShortMsgService.getShortMsgBean(ShiroHelper.getCurrentUserId(),
                    null, "passportDrawReturnSuccess", passportDraw.getId());
            shortMsgService.send(shortMsgBean, IpUtils.getRealIp(ContextHelper.getRequest()));
        }else{

            sysApprovalLogService.add(record.getId(), passportDraw.getCadre().getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                    "拒不交回证件", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, record.getReturnRemark());
        }
    }

    // 重置归还状态为 “未归还”
    public void resetReturnPassport(int id) {

        iAbroadMapper.resetReturnPassport(id);

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
        sysApprovalLogService.add(id, passportDraw.getCadre().getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW,
                "重置归还状态", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PassportDraw record) {
        return passportDrawMapper.updateByPrimaryKeySelective(record);
    }

    // 使用记录导出
    public void passportDraw_export(byte exportType, PassportDrawExample example, HttpServletResponse response) {

        String type = "因私出国（境）";
        if (exportType == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW) {
            type = "因公赴台、长期因公出国";
        } else if (exportType == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER) {
            type = "处理其他事务";
        } else {
            exportType = AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF;
        }

        List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);
        long rownum = passportDrawMapper.countByExample(example);

        String[] titles = null;
        if (exportType == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF) {
            titles = new String[]{"序号|50", "工作证号|100", "姓名|100", "所在单位及职务|250|left", "证件名称|100",
                    "证件号码|100", "申请日期|100", "申请编码|100", "因私出国（境）行程|100", /*"是否签注|100",*/
                    "出行时间|100", "回国时间|100", "前往国家或地区|100", "因私出国境事由|180", "借出日期|100",
                    "归还日期"};

        } else if (exportType == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW) {
            titles = new String[]{"序号|100", "工作证号|100", "姓名|100", "所在单位及职务|250|left", "证件名称|100",
                    "证件号码|100", "申请日期|100", "申请编码|100", "申请类型|100", "出行时间|100",
                    "回国时间|100", "出行天数|100", "因公事由|100", "费用来源|100", "是否签注|100",
                    "借出日期|100", "归还日期"};

        } else if (exportType == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER) {
            titles = new String[]{"序号|100", "工作证号|100", "姓名|100", "所在单位及职务|250|left", "证件名称|100",
                    "证件号码|100", "申请日期|100", "申请编码|100", "使用时间|100", "归还时间|100",
                    "使用天数|80", "事由|180", "借出日期|100", "归还日期|100"};
        }

        MetaType normalPassport = CmTag.getMetaTypeByCode("mt_passport_normal");
        List<String[]> valueList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PassportDraw passportDraw = passportDraws.get(i);
            Cadre cadre = passportDraw.getCadre();
            SysUserView uv = passportDraw.getUser();
            Passport passport = passportDraw.getPassport();
            ApplySelf applySelf = passportDraw.getApplySelf();
            String xingcheng = "";
            String needSign = "";
            String startDate = "";
            String endDate = "";
            String toCountry = "";
            String reason = passportDraw.getReason();

            if (passportDraw.getType() == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF) {
                xingcheng = "S" + applySelf.getId();
                if (passport.getClassId().intValue() != normalPassport.getId()) {
                    needSign = BooleanUtils.isTrue(passportDraw.getNeedSign()) ? "是" : "否";
                }
                startDate = DateUtils.formatDate(applySelf.getApplyDate(), DateUtils.YYYY_MM_DD);
                endDate = DateUtils.formatDate(applySelf.getEndDate(), DateUtils.YYYY_MM_DD);
                toCountry = applySelf.getToCountry();
                reason = applySelf.getReason();
            } else if (passportDraw.getType() == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW) {
                //xingcheng = "T"+passportDraw.getId();
                if (passport.getClassId().intValue() != normalPassport.getId()) {
                    needSign = BooleanUtils.isTrue(passportDraw.getNeedSign()) ? "是" : "否";
                }
                startDate = DateUtils.formatDate(passportDraw.getStartDate(), DateUtils.YYYY_MM_DD);
                endDate = DateUtils.formatDate(passportDraw.getEndDate(), DateUtils.YYYY_MM_DD);
                //toCountry="台湾";
            } else if (passportDraw.getType() == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER) {
                //xingcheng = "Q"+passportDraw.getId();
                startDate = DateUtils.formatDate(passportDraw.getStartDate(), DateUtils.YYYY_MM_DD);
                endDate = DateUtils.formatDate(passportDraw.getEndDate(), DateUtils.YYYY_MM_DD);
            }
            String[] values = null;
            if (exportType == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF) {
                values = new String[]{
                        String.valueOf(i + 1),
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        passport.getPassportClass().getName(),

                        passport.getCode(),
                        DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD),
                        String.format("D%s", passportDraw.getId()),
                        xingcheng,
                        // needSign,

                        startDate,
                        endDate, toCountry, StringUtils.replace(reason, "+++", ","),
                        DateUtils.formatDate(passportDraw.getDrawTime(), DateUtils.YYYY_MM_DD),

                        DateUtils.formatDate(passportDraw.getRealReturnDate(), DateUtils.YYYY_MM_DD),
                };
            } else if (exportType == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW) {
                /*titles = new String[]{"序号", "工作证号", "姓名", "所在单位及职务", "证件名称",
                        "证件号码", "申请日期", "申请编码", "申请类型", "出行时间",
                        "回国时间", "出行天数", "因公事由", "费用来源", "是否签注",
                        "借出日期", "归还日期"};*/
                values = new String[]{
                        String.valueOf(i + 1),
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        passport.getPassportClass().getName(),

                        passport.getCode(),
                        DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD),
                        String.format("D%s", passportDraw.getId()),
                        AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_MAP.get(passportDraw.getType()),
                        startDate,

                        endDate, DateUtils.getDayCountBetweenDate(passportDraw.getStartDate(), passportDraw.getEndDate()) + "",
                         StringUtils.replace(reason, "+++", ","), passportDraw.getCostSource(), needSign,

                        DateUtils.formatDate(passportDraw.getDrawTime(), DateUtils.YYYY_MM_DD),
                        DateUtils.formatDate(passportDraw.getRealReturnDate(), DateUtils.YYYY_MM_DD),
                };
            } else if (exportType == AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER) {

                /*titles = new String[]{"序号", "工作证号", "姓名", "所在单位及职务", "证件名称",
                        "证件号码", "申请日期", "申请编码", "使用时间", "归还时间",
                        "使用天数", "事由",  "借出日期", "归还日期"};*/
                values = new String[]{
                        String.valueOf(i + 1),
                        uv.getCode(),
                        uv.getRealname(),
                        cadre.getTitle(),
                        passport.getPassportClass().getName(),

                        passport.getCode(),
                        DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD),
                        String.format("D%s", passportDraw.getId()),
                        startDate,
                        endDate,

                        DateUtils.getDayCountBetweenDate(passportDraw.getStartDate(), passportDraw.getEndDate()) + "",
                        reason,
                        DateUtils.formatDate(passportDraw.getDrawTime(), DateUtils.YYYY_MM_DD),
                        DateUtils.formatDate(passportDraw.getRealReturnDate(), DateUtils.YYYY_MM_DD),
                };
            }
            valueList.add(values);
        }

        String fileName = type + "证件使用记录";
        ExportHelper.export(titles, valueList, fileName, response);
    }
}
