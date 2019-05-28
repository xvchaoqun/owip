package service.oa;

import controller.global.OpException;
import domain.oa.OaTaskAdmin;
import domain.oa.OaTaskAdminExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.global.CacheHelper;
import service.sys.SysUserService;
import sys.constants.RoleConstants;
import sys.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class OaTaskAdminService extends OaBaseMapper {

    @Autowired
    private CacheHelper cacheHelper;
    @Autowired
    private SysUserService sysUserService;

    @Transactional
    @CacheEvict(value = "OaTaskAdmin", key = "#record.userId")
    public void insertSelective(OaTaskAdmin record) {

        record.setCreateTime(new Date());
        oaTaskAdminMapper.insertSelective(record);
        sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_OA_ADMIN);
    }

    @Transactional
    public void del(Integer userId) {

        oaTaskAdminMapper.deleteByPrimaryKey(userId);

        sysUserService.delRole(userId, RoleConstants.ROLE_OA_ADMIN);
        cacheHelper.clearCache("OaTaskAdmin", userId + "");
    }

    @Transactional
    public void batchDel(Integer[] userIds) {

        if (userIds == null || userIds.length == 0) return;

        OaTaskAdminExample example = new OaTaskAdminExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        List<OaTaskAdmin> oaTaskAdmins = oaTaskAdminMapper.selectByExample(example);

        oaTaskAdminMapper.deleteByExample(example);

        for (OaTaskAdmin oaTaskAdmin : oaTaskAdmins) {

            int userId = oaTaskAdmin.getUserId();
            sysUserService.delRole(userId, RoleConstants.ROLE_OA_ADMIN);
            cacheHelper.clearCache("OaTaskAdmin", userId + "");
        }
    }

    @Transactional
    @CacheEvict(value = "OaTaskAdmin", key = "#record.userId")
    public void updateByPrimaryKeySelective(OaTaskAdmin record) {

        if(StringUtils.isBlank(record.getTypes())){
            throw new OpException("工作类型不能为空。");
        }

        oaTaskAdminMapper.updateByPrimaryKeySelective(record);
        sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_OA_ADMIN);
    }

    // 获取协同办公管理员管理的类型
    @Cacheable(value = "OaTaskAdmin", key = "#userId")
    public List<Integer> adminTypes(int userId) {

        List<Integer> adminTypeSet = new ArrayList<>();
        OaTaskAdmin oaTaskAdmin = oaTaskAdminMapper.selectByPrimaryKey(userId);
        if (oaTaskAdmin != null) {
            adminTypeSet = new ArrayList<>(NumberUtils.toIntSet(oaTaskAdmin.getTypes(), ","));
        }

        return adminTypeSet;
    }
}
