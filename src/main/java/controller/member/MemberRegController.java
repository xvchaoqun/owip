package controller.member;

import controller.global.OpException;
import domain.member.MemberReg;
import domain.member.MemberRegExample;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.shiro.AuthToken;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberRegController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private VerifyAuth<MemberReg> checkVerityAuth2(int id) {
        MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberReg, memberReg.getPartyId());
    }

    @RequestMapping(value = "/member_reg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_reg(String username, String passwd, Byte type,
                             String realname, String idcard, String phone,
                             Integer party, String captcha, HttpServletRequest request) {

        username = StringUtils.trimToNull(username);
        realname = StringUtils.trimToNull(realname);
        idcard = StringUtils.trimToNull(idcard);
        phone = StringUtils.trimToNull(phone);
        captcha = StringUtils.trimToNull(captcha);

        String _captcha = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isBlank(_captcha) || !_captcha.equalsIgnoreCase(captcha)) {
            return failed("验证码错误。");
        }
        if (!CmTag.validUsername(username)) {
            return failed(CmTag.getStringProperty("usernameMsg"));
        }
        if (!CmTag.validPasswd(passwd)) {
            return failed(CmTag.getStringProperty("passwdMsg"));
        }
        if (type == null || SystemConstants.USER_TYPE_MAP.get(type) == null) {
            return failed("类型有误");
        }
        if (StringUtils.isBlank(realname) || StringUtils.length(realname) < 2) {
            return failed("请填写真实姓名");
        }
        IdcardValidator idcardValidator = new IdcardValidator();
        if (!idcardValidator.isValidatedAllIdcard(idcard)) {
            return failed("身份证号码有误。");
        }
        if (!CmTag.validMobile(phone)) {
            return failed("手机号码有误");
        }
        if (party == null || partyService.findAll().get(party) == null) {
            return failed("请选择分党委。");
        }
		/*try {
			memberRegService.reg(username, passwd, type, realname,
					idcard, phone, party, IpUtils.getRealIp(request));
		}catch (RegException re){
			return failed(re.getMessage());
		}catch (Exception ex){
			logger.error("异常", ex);
			logger.error("注册失败：" + ex.getMessage());
			return failed("系统错误：" + ex.getMessage());
		}*/
        memberRegService.reg(username, passwd, type, realname,
                idcard, phone, party, IpUtils.getRealIp(request));

        logger.info(String.format("%s 注册成功", username));

        AuthToken token = new AuthToken(username,
                passwd.toCharArray(), false, request.getRemoteHost(), null, null);

        SecurityUtils.getSubject().login(token);

        logger.info(addLog(LogConstants.LOG_USER, "注册后登录成功"));

        return success();
    }

    @RequiresPermissions("memberReg:list")
    @RequestMapping("/memberReg")
    public String memberReg(@RequestParam(defaultValue = "1") Integer cls,
                            Integer userId,
                            Integer importUserId,
                            Integer partyId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (importUserId != null) {
            modelMap.put("importUser", sysUserService.findById(importUserId));
        }
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        // 分党委党总支直属党支部待审核总数
        modelMap.put("partyApprovalCount", memberRegService.count(null));

        return "member/memberReg/memberReg_page";
    }

    @RequiresPermissions("memberReg:list")
    @RequestMapping("/memberReg_data")
    public void memberReg_data(@RequestParam(defaultValue = "1") Integer cls, HttpServletResponse response,
                               Integer userId,
                               String username,
                               Integer partyId,
                               String idcard,
                               Byte type,
                               String realname,
                               Integer importUserId,
                               Integer importSeq,
                               @RequestParam(required = false, defaultValue = "0") int export,
                          @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberRegExample example = new MemberRegExample();
        MemberRegExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (partyId != null)
            criteria.andPartyIdEqualTo(partyId);

        criteria.addPermits(loginUserService.adminPartyIdList());

        if (StringUtils.isNotBlank(username)) {
            criteria.andUsernameLike(SqlUtils.like(username));
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameLike(SqlUtils.like(realname));
        }
        if (StringUtils.isNotBlank(idcard)) {
            criteria.andIdcardLike(SqlUtils.like(idcard));
        }
        if(importUserId!=null){
            criteria.andImportUserIdEqualTo(importUserId);
        }
        if(importSeq!=null){
            criteria.andImportSeqEqualTo(importSeq);
        }

        if (cls == 1) {
            criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_APPLY);
        } else if (cls == 2) {
            criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_DENY);
        } else {
            criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_PASS);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            memberReg_export(example, response);
            return;
        }

        long count = memberRegMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberReg> memberRegs = memberRegMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberRegs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberReg:list")
    @RequestMapping("/memberReg_approval")
    public String memberReg_approval(@CurrentUser SysUserView loginUser, Integer id, ModelMap modelMap) {

        MemberReg currentMemberReg = null;
        if (id != null) {
            currentMemberReg = memberRegMapper.selectByPrimaryKey(id);
            if (currentMemberReg.getStatus() != SystemConstants.USER_REG_STATUS_APPLY)
                currentMemberReg = null;
        } else {
            currentMemberReg = memberRegService.next(null);
        }
        if (currentMemberReg == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("memberReg", currentMemberReg);

        // 是否是当前记录的管理员
        modelMap.put("isAdmin", ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)
                || partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberReg.getPartyId()));

        // 读取总数
        modelMap.put("count", memberRegService.count(null));
        // 下一条记录
        modelMap.put("next", memberRegService.next(currentMemberReg));
        // 上一条记录
        modelMap.put("last", memberRegService.last(currentMemberReg));

        return "member/memberReg/memberReg_approval";
    }

    @RequiresPermissions("memberReg:check")
    @RequestMapping("/memberReg_deny")
    public String memberReg_deny(Integer id, ModelMap modelMap) {

        MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
        modelMap.put("memberReg", memberReg);
        Integer userId = memberReg.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "member/memberReg/memberReg_deny";
    }

    @RequiresPermissions("memberReg:check")
    @RequestMapping(value = "/memberReg_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_deny(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                 Integer id,
                                 String reason) {

        VerifyAuth<MemberReg> verifyAuth = checkVerityAuth2(id);
        MemberReg memberReg = verifyAuth.entity;

        int loginUserId = loginUser.getId();
        int userId = memberReg.getUserId();

        memberRegService.deny(memberReg.getId());
        logger.info(addLog(LogConstants.LOG_MEMBER, "拒绝用户注册申请：%s", id));

        applyApprovalLogService.add(memberReg.getId(),
                memberReg.getPartyId(), null, userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "分党委党总支直属党支部审核",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_DENY, reason);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReg:check")
    @RequestMapping(value = "/memberReg_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                  Integer id) {

        VerifyAuth<MemberReg> verifyAuth = checkVerityAuth2(id);
        MemberReg memberReg = verifyAuth.entity;

        memberRegService.pass(memberReg.getId());
        logger.info(addLog(LogConstants.LOG_MEMBER, "用户注册-分党委审核：%s", id));

        int loginUserId = loginUser.getId();
        int userId = memberReg.getUserId();

        applyApprovalLogService.add(memberReg.getId(),
                memberReg.getPartyId(), null, userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "审核",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS, null);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReg:edit")
    @RequestMapping(value = "/memberReg_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_au(MemberReg record, HttpServletRequest request) {

        Integer id = record.getId();

        if (memberRegService.idcardDuplicate(id, record.getIdcard())) {
            return failed("身份证已被注册。");
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);

        if(id!=null) {

            if (memberRegService.usernameDuplicate(id, record.getUserId(), record.getUsername())
                || sysUserService.idDuplicate(record.getUserId(), record.getUsername(), record.getCode())) {
                return failed("用户名或学工号已被注册。");
            }

            MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
            if(memberReg.getStatus()!=SystemConstants.USER_REG_STATUS_APPLY){
                return failed("注册账号状态有误，不可修改。");
            }

            Integer partyId = memberReg.getPartyId();
            // 只能修改本党委的注册账号信息，但可以修改为联系其他分党委
            //===========权限
            Integer loginUserId = ShiroHelper.getCurrentUserId();
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
                boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
                if (!isAdmin) throw new UnauthorizedException();
            }

            memberRegService.updateByPrimaryKeySelective(record);
            logger.info(log(LogConstants.LOG_ADMIN, "更新系统注册账号信息：{0}", record.getId()));

            applyApprovalLogService.add(memberReg.getId(),
                memberReg.getPartyId(), null, memberReg.getUserId(),
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "更新信息",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);
        }else{

            // 只能添加本党委的党员
            Integer partyId = record.getPartyId();
            Integer loginUserId = ShiroHelper.getCurrentUserId();
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
                boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
                if (!isAdmin) throw new UnauthorizedException();
            }

            String prefix = CmTag.getStringProperty("memberRegPrefix", "dy");
            MemberReg memberReg = memberRegService.addMemberReg(record, prefix, -1,
                    new Date(), ContextHelper.getRealIp());
            resultMap.put("memberReg", memberReg);

            logger.info(log(LogConstants.LOG_ADMIN, "添加系统注册账号：{0}", memberReg.getUsername()));
        }

        return resultMap;
    }

    @RequiresPermissions("memberReg:edit")
    @RequestMapping("/memberReg_au")
    public String memberReg_au(Integer id,
                               ModelMap modelMap) {

        if (id != null) {
            MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
            modelMap.put("memberReg", memberReg);

            Integer partyId = memberReg.getPartyId();
            modelMap.put("party", partyService.findAll().get(partyId));
        }

        return "member/memberReg/memberReg_au";
    }

    @RequiresPermissions("memberReg:import")
    @RequestMapping("/memberReg_import")
    public String memberReg_import(Integer id, ModelMap modelMap) {

        return "member/memberReg/memberReg_import";
    }

    @RequiresPermissions("memberReg:import")
    @RequestMapping(value = "/memberReg_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<String, Party> runPartyMap = new HashMap<>();
        for (Party party : partyMap.values()) {
            if (BooleanUtils.isNotTrue(party.getIsDeleted())) {
                runPartyMap.put(party.getCode(), party);
            }
        }

        List<MemberReg> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            MemberReg record = new MemberReg();
            row++;
            String realname = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(realname)) {
                throw new OpException("第{0}行姓名为空", row);
            }
            record.setRealname(realname);

            String idcard = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(idcard)) {
                throw new OpException("第{0}行身份证号码为空", row);
            }
            IdcardValidator idcardValidator = new IdcardValidator();
            if (!idcardValidator.isValidatedAllIdcard(idcard)) {
                throw new OpException("第{0}行身份证号码有误。", row);
            }
            record.setIdcard(idcard);

            String type = StringUtils.trimToNull(xlsRow.get(2));
            if (!SystemConstants.USER_TYPE_MAP.containsValue(type)) {
                throw new OpException("第{0}行类别有误，取值只能为[{1}]。", row,
                        StringUtils.join(SystemConstants.USER_TYPE_MAP.values(), ","));
            }
            for (Map.Entry<Byte, String> entry : SystemConstants.USER_TYPE_MAP.entrySet()) {
                byte _type = entry.getKey();
                if (StringUtils.equals(type, entry.getValue())) {
                    record.setType(_type);
                    break;
                }
            }

            String mobile = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isBlank(mobile)) {
                throw new OpException("第{0}行手机号为空", mobile);
            }
            if (!CmTag.validMobile(mobile)) {
                return failed("手机号码有误");
            }
            record.setPhone(mobile);

            String partyCode = StringUtils.trim(xlsRow.get(5));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行分党委编码为空", row);
            }
            Party party = runPartyMap.get(partyCode);
            if (party == null) {
                throw new OpException("第{0}行分党委编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(party.getId());

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        Map<String, Object> importResultMap = memberRegService.bacthImport(records);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.putAll(importResultMap);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_MEMBER, "批量生成系统账号{0}个，生成批次：{1}",
                resultMap.get("addCount"), resultMap.get("importSeq")));

        return resultMap;
    }

    @RequiresPermissions("memberReg:changepw")
    @RequestMapping(value = "/memberReg_changepw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_changepw(Integer id, String password, HttpServletRequest request) {

        if (!CmTag.validPasswd(password)) {
            return failed(CmTag.getStringProperty("passwdMsg"));
        }

        MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
        Integer partyId = memberReg.getPartyId();
        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin) throw new UnauthorizedException();
        }

        memberRegService.changepw(id, password);

        logger.info(addLog(LogConstants.LOG_ADMIN, "修改注册用户%s登录密码", memberReg.getUsername()));

        applyApprovalLogService.add(memberReg.getId(),
                memberReg.getPartyId(), null, memberReg.getUserId(),
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "修改登录密码",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReg:changepw")
    @RequestMapping("/memberReg_changepw")
    public String memberReg_changepw(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
            modelMap.put("memberReg", memberReg);
        }
        return "member/memberReg/memberReg_changepw";
    }

    public void memberReg_export(MemberRegExample example, HttpServletResponse response) {

        List<MemberReg> records = memberRegMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"账号|100", "密码|80", "姓名|100", "身份证号码|180", "手机号码|100",
                "联系"+CmTag.getStringProperty("partyName",
                "分党委") + "|250"};

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberReg record = records.get(i);
            Integer partyId = record.getPartyId();

            String[] values = {
                    record.getUsername(),
                    record.getPasswd(),
                    record.getRealname(),
                    record.getIdcard(),
                    record.getPhone(),
                    partyId == null ? "" : partyService.findAll().get(partyId).getName()
            };
            valuesList.add(values);
        }
        String fileName = "用户注册账号";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequiresPermissions("memberReg:del")
    @RequestMapping(value = "/memberReg_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberRegService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除用户注册：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReg:del")
    @RequestMapping(value = "/memberReg_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberRegService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除用户注册：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
