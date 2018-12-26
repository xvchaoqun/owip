package service.pcs;

import domain.pcs.PcsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.SpringProps;
import service.sys.SysUserService;
import sys.utils.DateUtils;
import sys.utils.MySqlUtils;
import sys.utils.PropertiesUtils;

import java.util.Date;

@Service
public class PcsVoteService extends PcsBaseMapper {

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
                "dw_back_vote=null, jw_back_vote=null, committee_can_select=0 where id="+ configId);

        commonMapper.excuteSql("delete pvc.* from pcs_vote_candidate pvc, pcs_vote_group pvg " +
                "where pvc.group_id=pvg.id and pvg.config_id=" + configId);

        commonMapper.excuteSql("update pcs_vote_group set has_report=0, vote=null,valid=null, invalid=null where config_id="+ configId);

        commonMapper.excuteSql("delete from pcs_vote_member where config_id="+ configId);


        // 删除录入人角色
        /*String role = RoleConstants.ROLE_PCS_VOTE_DW;
        List<SysUserView> sysUserViews = sysUserService.findByRole(role);
        for (SysUserView sysUserView : sysUserViews) {
            sysUserService.delRole(sysUserView.getUserId(), role);
        }
        role = RoleConstants.ROLE_PCS_VOTE_JW;
        sysUserViews = sysUserService.findByRole(role);
        for (SysUserView sysUserView : sysUserViews) {
            sysUserService.delRole(sysUserView.getUserId(), role);
        }*/
    }
}
