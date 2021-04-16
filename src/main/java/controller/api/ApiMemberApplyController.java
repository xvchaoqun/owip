package controller.api;

import controller.BaseController;
import domain.member.MemberApply;
import domain.member.MemberApplyExample;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import interceptor.NeedSign;
import interceptor.SignParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.member.MemberApplyMapper;
import service.member.ApplyApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.gson.GsonUtils;
import sys.helper.PartyHelper;
import sys.security.Base64Utils;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

@Controller
@RequestMapping("/api/member")
public class ApiMemberApplyController extends BaseController {

    @Autowired
    private MemberApplyMapper memberApplyMapper;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private void log(Map<String, Object> result, HttpServletRequest request){

        logger.info(MessageFormat.format("{0}, {1}, {2}, {3}, {4}, result：{5}",
                        request.getRequestURI(),
                        request.getMethod(),
                        JSONUtils.toString(request.getParameterMap(), false),
                        RequestUtils.getUserAgent(request), IpUtils.getRealIp(request),
                JSONUtils.toString(result, false)));
    }

    @NeedSign
    @RequestMapping("/memberApply_sync")
    public void memberApply_sync(@SignParam(value = "code") String code,
                                 String partyCode,
                                 String branchCode,
                                 String bean, //MemberApply JSON串
                                 HttpServletRequest request, HttpServletResponse response) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        Map<String, Object> result = new HashMap<>();
        result.put("Success", false);

        try {
            List<Byte> userType = new ArrayList<>(Arrays.asList(new Byte[]{SystemConstants.USER_TYPE_BKS,SystemConstants.USER_TYPE_SS,SystemConstants.DEGREE_TYPE_BS}));
            SysUserViewExample sysUserExample = new SysUserViewExample();
            sysUserExample.createCriteria().andCodeEqualTo(code).andTypeIn(userType);
            List<SysUserView> sysUserList = sysUserViewMapper.selectByExample(sysUserExample);
            if (sysUserList == null || sysUserList.size() == 0) {

                result.put("Message", "组工系统中，不存在该学生账号["+code+"]");
                log(result, request);
                JSONUtils.write(response, result, false);
                return;
            }
            SysUserView uv = sysUserList.get(0);
            int userId = uv.getId();

            bean = new String(Base64Utils.decode(bean), "utf-8");

            MemberApply _bean = GsonUtils.ToBean(bean, MemberApply.class);
            Party party = partyService.getByCode(partyCode);
            Integer partyId = party.getId();
            _bean.setPartyId(partyId);
            if (!PartyHelper.isDirectBranch(partyId) && StringUtils.isNotBlank(branchCode)){
                Branch branch = branchService.getByCode(branchCode);
                _bean.setBranchId(branch.getId());
            }

            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if (memberApply == null){
                _bean.setUserId(userId);
                _bean.setFillTime(new Date());
                _bean.setIsRemove(false);
                memberApplyMapper.insertSelective(_bean);

                logger.info("添加发展党员："+_bean.getUserId());
                result.put("Message", "添加发展党员"+uv.getRealname()+"["+code+"]");
                result.put("Success", true);
                log(result, request);
                JSONUtils.write(response, result, false);
                return;

            }else {

                if (_bean != null && memberApply.getStage() > _bean.getStage()){

                    logger.info("推送失败，组工系统发展阶段超前："+memberApply.getUserId());
                    result.put("Message", "2");
                    log(result, request);
                    JSONUtils.write(response, result, false);
                    return;
                }

                List<String> hasChangeField = new ArrayList<>();
                int count = 0;

                // 判断是否有需要更新的字段
                Class clazz = memberApply.getClass();
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {// 所有的属性

                    String name = pd.getName();// 属性名
                    String columnName = ContentUtils.camelToUnderline(name);

                    Method readMethod = pd.getReadMethod();// get方法
                    // 在_bean上调用get方法等同于获得_bean的属性值
                    Object o1 = readMethod.invoke(_bean);
                    // 在memberApply上调用get方法等同于获得memberApply的属性值
                    Object o2 = readMethod.invoke(memberApply);

                    if (o1 instanceof Date) {
                        o1 = DateUtils.formatDate((Date) o1, DateUtils.YYYY_MM_DD_HH_MM_CHINA);
                    }
                    if (o2 instanceof Date) {
                        o2 = DateUtils.formatDate((Date) o2, DateUtils.YYYY_MM_DD_HH_MM_CHINA);
                    }

                    if (o1 == null && o2 == null) {
                        continue;
                    } else if (o1 == null && o2 != null) {// 只有发展党员数据不为空，组工系统数据为空时需要置空
                        if (o1 instanceof Date || org.apache.commons.lang.StringUtils.equals(columnName, "branch_id")) {
                            commonMapper.excuteSql("update ow_member_apply set " + columnName + "=null where user_id=" + userId);
                            hasChangeField.add(columnName);
                            count++;
                        }
                        continue;
                    }
                    if (!o1.equals(o2)) {// 比较这两个值是否相等,不等放入list
                        hasChangeField.add(columnName);
                    }
                }

                if (hasChangeField.size() - count > 0){
                    _bean.setUserId(userId);
                    MemberApplyExample memberApplyExample = new MemberApplyExample();
                    memberApplyExample.createCriteria().andUserIdEqualTo(userId);
                    memberApplyMapper.updateByExampleSelective(_bean, memberApplyExample);
                }

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(_bean.getStage()),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_DENY, "学生党员发展系统数据推送");

                if (hasChangeField.size() > 0){

                    logger.info("推送学生党员发展信息："+memberApply.getUserId()+",更新字段为："
                            + StringUtils.join(hasChangeField, ","));
                    result.put("Message", "组工系统更新为最新数据");
                    result.put("Success", true);
                    log(result, request);
                    JSONUtils.write(response, result, false);
                    return;
                }else {

                    result.put("Message", "1");
                    result.put("Success", true);
                    log(result, request);
                    JSONUtils.write(response, result, false);
                    return;
                }
            }

        }catch (Exception ex){

            logger.error("学生发展党员信息推送异常", ex);

            result.put("Message", "系统访问出错");
            JSONUtils.write(response, result, false);
            return;
        }
    }

    // 获得组工系统对应的阶段id
    public Byte setMemberApplyStage(Integer stageId){
        Byte stage = 0;
        if (stageId != null) {
            if (stageId >= 34) {
                stage = OwConstants.OW_APPLY_STAGE_POSITIVE;
            } else if (stageId >= 21) {
                stage = OwConstants.OW_APPLY_STAGE_GROW;
            } else if (stageId >= 17) {
                stage = OwConstants.OW_APPLY_STAGE_DRAW;
            } else if (stageId >= 11) {
                stage = OwConstants.OW_APPLY_STAGE_CANDIDATE;
            } else if (stageId >= 3) {
                stage = OwConstants.OW_APPLY_STAGE_ACTIVE;
            } else if (stageId >= 2) {
                stage = OwConstants.OW_APPLY_STAGE_PASS;
            } else if (stageId >= 1) {
                stage = OwConstants.OW_APPLY_STAGE_INIT;
            }
        }
        return stage;
    }
}
