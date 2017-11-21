package service.pcs;

import domain.pcs.PcsConfig;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.MySqlUtils;
import sys.utils.PropertiesUtils;

import java.util.Date;
import java.util.List;

@Service
public class PcsVoteService extends BaseMapper {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected PcsConfigService pcsConfigService;
    @Autowired
    protected SpringProps springProps;

    // 清空两委选举数据（当前党代会）
    @Transactional
    public void clear() throws InterruptedException {

        //commonMapper.excuteSql();
        String username = PropertiesUtils.getString("jdbc_user");
        String passwd = PropertiesUtils.getString("jdbc_password");
        String bakPath = springProps.uploadPath + FILE_SEPARATOR +
                "dbbak" + FILE_SEPARATOR;
        String bakName = "pcs_vote_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".sql";
        MySqlUtils.backup("localhost", username, passwd, bakPath, bakName,
                springProps.schema + " pcs_config pcs_vote_candidate pcs_vote_group pcs_vote_member");

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        commonMapper.excuteSql("update pcs_config set committee_join_count=null, dw_send_vote=null, jw_send_vote=null, " +
                "dw_back_vote=null, jw_back_vote=null, committee_can_select=null where id="+ configId);

        commonMapper.excuteSql("delete pvc.* from pcs_vote_candidate pvc, pcs_vote_group pvg " +
                "where pvc.group_id=pvg.id and pvg.config_id=" + configId);

        commonMapper.excuteSql("delete from pcs_vote_group where config_id="+ configId);

        commonMapper.excuteSql("delete from pcs_vote_member where config_id="+ configId);


        // 删除录入人角色
        String role = SystemConstants.ROLE_PCS_VOTE_DW;
        List<SysUserView> sysUserViews = sysUserService.findByRole(role);
        for (SysUserView sysUserView : sysUserViews) {
            sysUserService.delRole(sysUserView.getUserId(), role);
        }
        role = SystemConstants.ROLE_PCS_VOTE_JW;
        sysUserViews = sysUserService.findByRole(role);
        for (SysUserView sysUserView : sysUserViews) {
            sysUserService.delRole(sysUserView.getUserId(), role);
        }
    }
}
