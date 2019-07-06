package controller.abroad;

import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import service.abroad.*;
import service.base.CountryService;
import service.base.MetaTypeService;
import service.base.ShortMsgService;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.leader.LeaderService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
import shiro.ShiroHelper;
import sys.HttpResponseMethod;
import sys.tags.CmTag;

/**
 * Created by lm on 2017/9/20.
 */
public class AbroadBaseController extends AbroadBaseMapper implements HttpResponseMethod {

    // 确定 申请表 的 审批人
    public int getAbroadApplyConcatUserId() {

        if (CmTag.getBoolProperty("abroadContactUseSign")) {
            // 使用签名的情况下，使用当前登录的账号
            return ShiroHelper.getCurrentUserId();
        } else {
            // 使用姓名的情况下，使用固定的账号
            String abroadContactUserCode = CmTag.getStringProperty("abroadContactUser");
            SysUserView uv = CmTag.getUserByCode(abroadContactUserCode);
            // 如果留空或不存在，则仍然使用当前登录账号
            return uv == null ? ShiroHelper.getCurrentUserId() : uv.getUserId();
        }
    }

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected LeaderService cadreLeaderService;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;
    @Autowired
    protected CountryService countryService;
    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected CadreCommonService cadreCommonService;
    @Autowired
    protected MetaTypeService metaTypeService;

    @Autowired
    protected AbroadService abroadService;
    @Autowired
    protected ApplicatTypeService applicatTypeService;
    @Autowired
    protected ApprovalOrderService approvalOrderService;
    @Autowired
    protected ApproverService approverService;
    @Autowired
    protected ApproverBlackListService approverBlackListService;
    @Autowired
    protected ApproverTypeService approverTypeService;

    @Autowired
    protected AbroadAdditionalPostService abroadAdditionalPostService;

    @Autowired
    protected PassportDrawService passportDrawService;
    @Autowired
    protected ApprovalLogService approvalLogService;
    @Autowired
    protected ApplySelfService applySelfService;
    @Autowired
    protected PassportApplyService passportApplyService;
    @Autowired
    protected PassportService passportService;
    @Autowired
    protected SafeBoxService safeBoxService;
    @Autowired
    protected TaiwanRecordService taiwanRecordService;
    @Autowired
    protected AbroadExportService abroadExportService;

    @Autowired
    protected AbroadShortMsgService abroadShortMsgService;
}
