package service.oa;

import bean.ShortMsgBean;
import controller.global.OpException;
import domain.oa.*;
import domain.sys.SysUserView;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import service.SpringProps;
import service.base.ShortMsgService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.OaConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class OaTaskUserService extends OaBaseMapper{

    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private SysUserService sysUserService;

    // 读取任务对象（读取任务对象或负责人的任务, 不包含已删除）
    public OaTaskUserView getOwnTask(int taskId, int userId) {

        OaTaskUserViewExample example = new OaTaskUserViewExample();
        example.createCriteria()
                .andTaskIdEqualTo(taskId)
                .own(userId)
                .andIsDeleteEqualTo(false);

        List<OaTaskUserView> oaTaskUsers = oaTaskUserViewMapper.selectByExample(example);
        return oaTaskUsers.size() > 0 ? oaTaskUsers.get(0) : null;
    }

    // 读取任务对象（读取任务对象本人的，包含已删除的）
    public OaTaskUser getOaTaskUser(int taskId, int userId) {

        OaTaskUserExample example = new OaTaskUserExample();
        OaTaskUserExample.Criteria criteria = example.createCriteria()
                .andTaskIdEqualTo(taskId).andUserIdEqualTo(userId)
                /*.andIsDeleteEqualTo(false)*/;

        List<OaTaskUser> oaTaskUsers = oaTaskUserMapper.selectByExample(example);
        return oaTaskUsers.size() > 0 ? oaTaskUsers.get(0) : null;
    }

    // 报送附件列表
    public List<OaTaskUserFile> getUserFiles(int taskId, int userId) {

        OaTaskUserFileExample example = new OaTaskUserFileExample();
        example.createCriteria().andTaskIdEqualTo(taskId).andUserIdEqualTo(userId);
        example.setOrderByClause("create_time asc");

        return oaTaskUserFileMapper.selectByExample(example);
    }

    // 未上报的任务对象列表
    public List<OaTaskUserView> getUnReports(int taskId) {

        OaTaskUserViewExample example = new OaTaskUserViewExample();
        example.createCriteria().andTaskIdEqualTo(taskId)
                .andIsDeleteEqualTo(false).andHasReportEqualTo(false);

        return oaTaskUserViewMapper.selectByExample(example);
    }

    // 任务对象userId列表
    public Set<Integer> getTaskUserIdSet(int taskId) {

        Set<Integer> taskUserIdSet = new HashSet<>();
        OaTaskUserExample example = new OaTaskUserExample();
        example.createCriteria().andTaskIdEqualTo(taskId)
                .andIsDeleteEqualTo(false);

        List<OaTaskUser> oaTaskUsers = oaTaskUserMapper.selectByExample(example);
        for (OaTaskUser oaTaskUser : oaTaskUsers) {
            taskUserIdSet.add(oaTaskUser.getUserId());
        }
        return taskUserIdSet;
    }

    // 任务对象数量
    public int countTaskUsers(int taskId, Byte status, Boolean hasReport) {

        OaTaskUserExample example = new OaTaskUserExample();
        OaTaskUserExample.Criteria criteria =
                example.createCriteria().andTaskIdEqualTo(taskId)
                        .andIsDeleteEqualTo(false);
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (hasReport != null) {
            criteria.andHasReportEqualTo(hasReport);
        }

        return (int) oaTaskUserMapper.countByExample(example);
    }

    /*public boolean idDuplicate(Integer id, int taskId, int userId) {

        OaTaskUserExample example = new OaTaskUserExample();
        OaTaskUserExample.Criteria criteria = example.createCriteria()
                .andTaskIdEqualTo(taskId).andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return oaTaskUserMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(OaTaskUser record) {

        Assert.isTrue(!idDuplicate(null, record.getTaskId(), record.getUserId()), "任务对象重复");
        oaTaskUserMapper.insertSelective(record);
    }

    @Transactional
    public int updateByPrimaryKeySelective(OaTaskUser record) {
        if (record.getTaskId() != null && record.getUserId() != null)
            Assert.isTrue(!idDuplicate(record.getTaskId(), record.getTaskId(), record.getUserId()), "任务对象重复");
        record.setTaskId(null);
        record.setUserId(null);
        return oaTaskUserMapper.updateByPrimaryKeySelective(record);
    }*/

    // 审批（管理员）
    @Transactional
    public void check(int taskId, int[] taskUserIds, byte status, String remark) {

        if (!OaConstants.OA_TASK_USER_STATUS_MAP.containsKey(status)) {
            return;
        }

        for (int taskUserId : taskUserIds) {

            OaTaskUser record = new OaTaskUser();
            record.setStatus(status);
            if (status == OaConstants.OA_TASK_USER_STATUS_DENY) {
                record.setHasReport(false);
            }
            record.setCheckRemark(remark);
            OaTaskUserExample example = new OaTaskUserExample();
            example.createCriteria().andTaskIdEqualTo(taskId)
                    .andIsDeleteEqualTo(false)
                    .andUserIdEqualTo(taskUserId).andHasReportEqualTo(true)
                    .andStatusEqualTo(OaConstants.OA_TASK_USER_STATUS_INIT);

            oaTaskUserMapper.updateByExampleSelective(record, example);
        }

    }

    // 报送任务（本人或负责人）
    @Transactional
    public void report(int taskId, String content, MultipartFile[] files, String remark) throws IOException, InterruptedException {

        int userId = ShiroHelper.getCurrentUserId();
        OaTaskUserView oaTaskUser = getOwnTask(taskId, userId);

        if (oaTaskUser == null || (oaTaskUser.getStatus()!=null &&
                oaTaskUser.getStatus() == OaConstants.OA_TASK_USER_STATUS_PASS) ) {

            throw new OpException("数据异常，请稍后重试。");
        }

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {

                OaTaskUserFile record = new OaTaskUserFile();
                record.setTaskId(taskId);
                record.setUserId(userId);
                record.setFileName(file.getOriginalFilename());
                record.setFilePath(upload(file, "oa_task_file"));
                record.setCreateTime(new Date());

                oaTaskUserFileMapper.insertSelective(record);
            }
        }

        OaTaskUser record = new OaTaskUser();
        record.setId(oaTaskUser.getId());
        record.setContent(content);
        record.setRemark(remark);
        record.setHasReport(true);
        record.setReportUserId(userId);
        record.setReportTime(new Date());
        record.setStatus(OaConstants.OA_TASK_USER_STATUS_INIT);
        record.setIsBack(false);
        oaTaskUserMapper.updateByPrimaryKeySelective(record);
    }
    String upload(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        return upload(file, saveFolder, null, 0, 0);
    }
    String upload(MultipartFile file, String saveFolder,
                         String type,
                         int sImgWidth,
                         int sImgHeight) throws IOException, InterruptedException {

        if (file == null || file.isEmpty()) return null;

        SpringProps springProps = CmTag.getBean(SpringProps.class);


        // #tomcat版本>=8.0.39 下 win10下url路径中带正斜杠的文件路径读取不了
        String FILE_SEPARATOR = File.separator;

        String realPath = FILE_SEPARATOR + saveFolder +
                FILE_SEPARATOR + DateUtils.formatDate(new Date(), "yyyyMMdd") +
                FILE_SEPARATOR + UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String savePath = realPath + FileUtils.getExtention(originalFilename);
        FileUtils.copyFile(file, new File(springProps.uploadPath + savePath));

        if (StringUtils.equalsIgnoreCase(type, "doc")) {

            String pdfPath = realPath + ".pdf";
            FileUtils.word2pdf(springProps.uploadPath + savePath, springProps.uploadPath + pdfPath);
            String swfPath = realPath + ".swf";
            pdf2Swf(pdfPath, swfPath);

        }else if (StringUtils.equalsIgnoreCase(type, "pdf")) {

            String swfPath = realPath + ".swf";
            pdf2Swf(savePath, swfPath);

        } else if (StringUtils.equalsIgnoreCase(type, "pic")) {
            // 需要缩略图的情况
            String shortImgPath = realPath + "_s.jpg";
            Thumbnails.of(file.getInputStream())
                    .size(sImgWidth, sImgHeight)
                    .outputFormat("jpg")
                    .outputQuality(1.0f)
                    .toFile(springProps.uploadPath + shortImgPath);
        }

        return savePath;
    }
void pdf2Swf(String filePath, String swfPath) throws IOException, InterruptedException {

        SpringProps springProps = CmTag.getBean(SpringProps.class);

        FileUtils.pdf2Swf(springProps.swfToolsCommand, springProps.uploadPath + filePath,
                springProps.uploadPath + swfPath, springProps.swfToolsLanguagedir);
    }
    // 删除报送附件（本人或负责人）
    @Transactional
    public void batchDelFiles(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        int userId = ShiroHelper.getCurrentUserId();

        for (Integer id : ids) {

            OaTaskUserFile oaTaskUserFile = oaTaskUserFileMapper.selectByPrimaryKey(id);
            OaTaskUserView oaTaskUser = getOwnTask(oaTaskUserFile.getTaskId(), userId);
            if (oaTaskUser==null) {
                throw new OpException("没有权限");
            }

            oaTaskUserFileMapper.deleteByPrimaryKey(id);
        }
    }

    // 撤回报送（本人或负责人）
    @Transactional
    public void selfBack(int taskId) {

        int userId = ShiroHelper.getCurrentUserId();
        OaTaskUserView oaTaskUser = getOwnTask(taskId, userId);
        if (oaTaskUser == null || oaTaskUser.getStatus() == OaConstants.OA_TASK_USER_STATUS_PASS) {
            throw new OpException("撤回失败。");
        }

        OaTaskUser record = new OaTaskUser();
        record.setId(oaTaskUser.getId());
        record.setHasReport(false);
        oaTaskUserMapper.updateByPrimaryKeySelective(record);

        commonMapper.excuteSql("update oa_task_user set status=null, report_user_id=null, " +
                "report_time=null where id=" + oaTaskUser.getId());
    }

    // 退回（管理员）
    @Transactional
    public void back(int id) {

        OaTaskUser record = new OaTaskUser();
        record.setId(id);
        record.setHasReport(false);
        record.setStatus(OaConstants.OA_TASK_USER_STATUS_INIT);
        record.setIsBack(true);

        oaTaskUserMapper.updateByPrimaryKeySelective(record);

        commonMapper.excuteSql("update oa_task_user set status=null, report_user_id=null, " +
                "report_time=null where id=" + id);
    }

    // 下发任务短信通知
    @Transactional
    public Map<String, Integer> sendInfoMsg(int taskId, String msg) {

        int sendUserId = ShiroHelper.getCurrentUserId();

        Set<Integer> taskUserIdSet = getTaskUserIdSet(taskId);
        String ip = ContextHelper.getRealIp();

        int total = taskUserIdSet.size();
        int success = 0;

        for (Integer userId : taskUserIdSet) {

            SysUserView uv = sysUserService.findById(userId);
            String mobile = uv.getMobile();

            ShortMsgBean bean = new ShortMsgBean();
            bean.setSender(sendUserId);
            bean.setReceiver(userId);
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_OA);
            bean.setType("协同办公-下发任务短信通知");
            bean.setMobile(mobile);
            bean.setContent(msg);

            boolean send = false;
            try {
                send = shortMsgService.send(bean, ip);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            if(send)success++;

            OaTaskMsg oaTaskMsg = new OaTaskMsg();
            oaTaskMsg.setTaskId(taskId);
            oaTaskMsg.setUserId(userId);
            oaTaskMsg.setType(OaConstants.OA_TASK_MSG_TYPE_INFO);
            oaTaskMsg.setContent(msg);
            oaTaskMsg.setSuccess(send);
            oaTaskMsg.setSendUserId(sendUserId);
            oaTaskMsg.setSendTime(new Date());
            oaTaskMsg.setSendIp(ip);
            oaTaskMsgMapper.insertSelective(oaTaskMsg);
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("success", success);

        return resultMap;
    }

    // 短信催促未报送对象
    @Transactional
    public Map<String, Object> sendUnReportMsg(int taskId, String msg) {

        int sendUserId = ShiroHelper.getCurrentUserId();

        List<OaTaskUserView> unReports = getUnReports(taskId);
        String ip = ContextHelper.getRealIp();

        int total = unReports.size();
        int success = 0;
        List<SysUserView> failedUsers = new ArrayList<>();

        for (OaTaskUserView oaTaskUserView : unReports) {

            int userId = oaTaskUserView.getUserId();
            SysUserView uv = sysUserService.findById(userId);
            String mobile = uv.getMobile();

            ShortMsgBean bean = new ShortMsgBean();
            bean.setSender(sendUserId);
            bean.setReceiver(userId);
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_OA);
            bean.setType("协同办公-短信催促未报送对象");
            bean.setMobile(mobile);
            bean.setContent(msg);

            boolean send = false;
            try {
                send = shortMsgService.send(bean, ip);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            if (send) {
                success++;
            }else{
                failedUsers.add(uv);
            }

            OaTaskMsg oaTaskMsg = new OaTaskMsg();
            oaTaskMsg.setTaskId(taskId);
            oaTaskMsg.setUserId(userId);
            oaTaskMsg.setType(OaConstants.OA_TASK_MSG_TYPE_UNREPORT);
            oaTaskMsg.setContent(msg);
            oaTaskMsg.setSuccess(send);
            oaTaskMsg.setSendUserId(sendUserId);
            oaTaskMsg.setSendTime(new Date());
            oaTaskMsg.setSendIp(ip);
            oaTaskMsgMapper.insertSelective(oaTaskMsg);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("success", success);
        resultMap.put("failedUsers", failedUsers);

        return resultMap;
    }

    // 审核未通过短信提醒
    @Transactional
    public Boolean sendDenyMsg(int id, String msg) {

        OaTaskUser oaTaskUser = oaTaskUserMapper.selectByPrimaryKey(id);
        int taskId = oaTaskUser.getTaskId();
        int userId = oaTaskUser.getUserId();

        int sendUserId = ShiroHelper.getCurrentUserId();
        SysUserView uv = sysUserService.findById(userId);
        String mobile = uv.getMobile();

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(sendUserId);
        bean.setReceiver(userId);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_SHORT_OA);
        bean.setType("协同办公-审核未通过短信提醒");
        bean.setMobile(mobile);
        bean.setContent(msg);

        String ip = ContextHelper.getRealIp();
        boolean send = false;
        try {
            send = shortMsgService.send(bean, ip);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        OaTaskMsg oaTaskMsg = new OaTaskMsg();
        oaTaskMsg.setTaskId(taskId);
        oaTaskMsg.setUserId(userId);
        oaTaskMsg.setType(OaConstants.OA_TASK_MSG_TYPE_INFO);
        oaTaskMsg.setContent(msg);
        oaTaskMsg.setSuccess(send);
        oaTaskMsg.setSendUserId(sendUserId);
        oaTaskMsg.setSendTime(new Date());
        oaTaskMsg.setSendIp(ip);
        oaTaskMsgMapper.insertSelective(oaTaskMsg);

        return send;
    }

    // 指定负责人（本人）
    @Transactional
    public void assign(int taskId, int assignUserId, String mobile) {

        Integer userId = ShiroHelper.getCurrentUserId();
        if(assignUserId==userId){
            throw new OpException("不能指定自己为负责人。");
        }
        OaTaskUser oaTaskUser = getOaTaskUser(taskId, userId);
        if(oaTaskUser==null){
            throw new OpException("任务不存在。");
        }

        Integer oldAssignUserId = oaTaskUser.getAssignUserId();
        if(oldAssignUserId!=null && oldAssignUserId==assignUserId) return;

        OaTaskUser checkOaTaskUser = getOaTaskUser(taskId, assignUserId);
        if(checkOaTaskUser!=null && !checkOaTaskUser.getIsDelete()){

            SysUserView uv = sysUserService.findById(assignUserId);
            throw new OpException("{0}已经是该任务的任务对象，不可指定为负责人。", uv.getRealname());
        }

        OaTaskUser record = new OaTaskUser();
        record.setId(oaTaskUser.getId());
        record.setAssignUserId(assignUserId);
        record.setAssignUserMobile(mobile);

        oaTaskUserMapper.updateByPrimaryKeySelective(record);

        // 添加“协同办公负责人”的角色
        sysUserService.addRole(assignUserId, RoleConstants.ROLE_OA_USER);

        if(oldAssignUserId != null){

            OaTaskUserExample example = new OaTaskUserExample();
            example.createCriteria().andTaskIdEqualTo(taskId)
                    .andIsDeleteEqualTo(false)
                    .andAssignUserIdEqualTo(oldAssignUserId);
            if(oaTaskUserMapper.countByExample(example)==0){

                // 如果原负责人没有指定任何任务，则删除“协同办公负责人”的角色
                sysUserService.delRole(oldAssignUserId, RoleConstants.ROLE_OA_USER);
            }
        }
    }
}
