package service.sys;

import domain.ExtJzg;
import domain.ExtJzgExample;
import domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import shiro.PasswordHelper;
import shiro.SaltPassword;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class SyncUserService extends BaseMapper {

    @Autowired
    private  SysUserService sysUserService;
    @Autowired
    protected PasswordHelper passwordHelper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 同步教职工人事库
    public void syncJZG(){

        int count = extJzgMapper.countByExample(new ExtJzgExample());
        int pageSize = 1000;
        int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);
        for (int i=0; i <pageNo; i++) {
            logger.debug(String.format("总数：%s， 每页%s条， 当前%s页", count, pageSize, pageNo));
            List<ExtJzg> extJzgs = extJzgMapper.selectByExampleWithRowbounds(new ExtJzgExample(), new RowBounds(i * pageSize, pageSize));
            for (ExtJzg extJzg : extJzgs) {
                String code = StringUtils.trim(extJzg.getZgh());
                SysUser record = new SysUser();
                record.setUsername(code);
                record.setCode(code);
                record.setType(SystemConstants.USER_TYPE_JZG);
                record.setRealname(StringUtils.trim(extJzg.getXm()));
                record.setGender(NumberUtils.toByte(extJzg.getXbm()));
                record.setBirth(extJzg.getCsrq());
                record.setIdcard(StringUtils.trim(extJzg.getSfzh()));
                record.setMobile(StringUtils.trim(extJzg.getYddh()));
                record.setEmail(StringUtils.trim(extJzg.getDzxx()));
                record.setSource(SystemConstants.USER_SOURCE_JZG);
                record.setLocked(false);

                SysUser sysUser = sysUserService.findByUsername(code);
                try {
                    if (sysUser == null) {
                        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                        record.setSalt(encrypt.getSalt());
                        record.setPasswd(encrypt.getPassword());
                        record.setCreateTime(new Date());
                        sysUserService.insertSelective(record);
                    } else {
                        record.setId(sysUser.getId());
                        sysUserService.updateByPrimaryKeySelective(record, sysUser.getUsername());
                    }
                }catch (Exception ex){
                    logger.error("同步出错", ex);
                }
            }
        }
    }
}
