package service.dispatch;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreExample;
import domain.sys.SysUser;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.cadre.CadreService;
import service.sys.SysUserService;
import shiro.PasswordHelper;
import sys.constants.CadreConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.SaltPassword;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.Date;

@Service
public class DispatchCadreService extends BaseMapper {

    @Autowired
    private DispatchService dispatchService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    protected PasswordHelper passwordHelper;

    // 按类别统计某个发文下的录入人数
    public int count(int dispatchId, byte type) {

        DispatchCadreExample example = new DispatchCadreExample();
        example.createCriteria().andDispatchIdEqualTo(dispatchId).andTypeEqualTo(type);
        return (int) dispatchCadreMapper.countByExample(example);
    }

    @Transactional
    public void insertSelective(DispatchCadre record) {

        record.setSortOrder(getNextSortOrder("dispatch_cadre", null));
        dispatchCadreMapper.insertSelective(record);

        dispatchService.update_dispatch_real_count();
    }

    @Transactional
    public void del(Integer id) {

        dispatchCadreMapper.deleteByPrimaryKey(id);

        dispatchService.update_dispatch_real_count();
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        DispatchCadreExample example = new DispatchCadreExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dispatchCadreMapper.deleteByExample(example);

        dispatchService.update_dispatch_real_count();
    }

    @Transactional
    public void updateByPrimaryKeySelective(DispatchCadre record) {

        dispatchCadreMapper.updateByPrimaryKeySelective(record);

        dispatchService.update_dispatch_real_count();

        if(record.getRecordId()==null){
            commonMapper.excuteSql("update dispatch_cadre set record_id=null where id=" + record.getId());
        }
    }

    // 添加历史离任干部
    @Transactional
    public void addLeaveCadre(Integer userId, Boolean needCreate, String realname, Cadre record) {

        // 默认是处级干部
        if(record.getStatus()==null) {
            record.setStatus(CadreConstants.CADRE_STATUS_CJ_LEAVE);
        }

        if (userId == null) {
            if (BooleanUtils.isNotTrue(needCreate)) {
                throw new OpException("请选择系统账号或确认是否需要生成系统账号");
            }

            if (StringUtils.isBlank(realname)) {
                throw new OpException("请输入干部姓名");
            }

            // 需要生成账号
            String code = sysUserService.genCode("gb");
            String passwd = RandomStringUtils.randomNumeric(10);
            SysUser sysUser = new SysUser();
            sysUser.setUsername(code);
            sysUser.setCode(code);
            sysUser.setLocked(false);
            SaltPassword encrypt = passwordHelper.encryptByRandomSalt(passwd);
            sysUser.setSalt(encrypt.getSalt());
            sysUser.setPasswd(encrypt.getPassword());
            sysUser.setCreateTime(new Date());
            sysUser.setType(SystemConstants.USER_TYPE_JZG);
            sysUser.setSource(SystemConstants.USER_SOURCE_REG);
            sysUser.setRoleIds(sysUserService.buildRoleIds(RoleConstants.ROLE_GUEST));
            sysUserService.insertSelective(sysUser);

            SysUserInfo sysUserInfo = new SysUserInfo();
            sysUserInfo.setRealname(realname);
            sysUserInfo.setUserId(sysUser.getId());
            sysUserService.insertOrUpdateUserInfoSelective(sysUserInfo);

            userId = sysUser.getId();

        } else {
            SysUserView uv = CmTag.getUserById(userId);
            if (uv == null) {
                throw new OpException("所选系统账号不存在。");
            }
            if (CmTag.hasRole(uv.getUsername(), RoleConstants.ROLE_CADRE_CJ)) {
                throw new OpException("所选系统账号已是干部，无需添加。");
            }
        }

        record.setUserId(userId);
        cadreService.insertSelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    /*@Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        DispatchCadre entity = dispatchCadreMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        DispatchCadreExample example = new DispatchCadreExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DispatchCadre> overEntities = dispatchCadreMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            DispatchCadre targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("dispatch_cadre", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dispatch_cadre", null, baseSortOrder, targetEntity.getSortOrder());

            DispatchCadre record = new DispatchCadre();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dispatchCadreMapper.updateByPrimaryKeySelective(record);
        }
    }*/
}
