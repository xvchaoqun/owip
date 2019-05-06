package service.ext;

import bean.UserBean;
import domain.ext.ExtBks;
import domain.ext.ExtBksExample;
import domain.ext.ExtJzg;
import domain.ext.ExtJzgExample;
import domain.ext.ExtYjs;
import domain.ext.ExtYjsExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.sys.UserBeanService;
import sys.constants.SystemConstants;

import java.util.List;

/**
 * Created by fafa on 2015/11/21.
 */
@Service
public class ExtService extends BaseMapper {

    @Autowired
    private UserBeanService userBeanService;

    // 获取用户所在的学校人事库或学生库中的单位名称
    public String getUnit(int userId) {

        UserBean userBean = userBeanService.get(userId);
        String code = userBean.getCode();
        Byte type = userBean.getType();
        String unit = null;
        if (type == SystemConstants.USER_TYPE_JZG) {
            ExtJzg extJzg = getExtJzg(code);
            if (extJzg != null) {
                unit = userBean.getUnit();
                if (StringUtils.isNotBlank(extJzg.getYjxk())) unit += "-" + extJzg.getYjxk();
            }

        } else if (type == SystemConstants.USER_TYPE_YJS) {
            ExtYjs extYjs = getExtYjs(code);
            if (extYjs != null) {
                unit = extYjs.getYxsmc();
                if (StringUtils.isNotBlank(extYjs.getYjfxmc())) unit += "-" + extYjs.getYjfxmc();
            }
        } else if (type == SystemConstants.USER_TYPE_BKS) {
            ExtBks extBks = getExtBks(code);
            if (extBks != null) {
                unit = extBks.getYxmc();
                if (StringUtils.isNotBlank(extBks.getZymc())) unit += "-" + extBks.getZymc();
            }
        }
        return unit;
    }

    // 学生所在院系
    public String getDep(int userId) {

        UserBean userBean = userBeanService.get(userId);
        String code = userBean.getCode();
        Byte type = userBean.getType();
        String dep = null;
        if (type == SystemConstants.USER_TYPE_YJS) {
            ExtYjs extYjs = getExtYjs(code);
            if (extYjs != null) {
                dep = extYjs.getYxsmc();
            }
        } else if (type == SystemConstants.USER_TYPE_BKS) {
            ExtBks extBks = getExtBks(code);
            if (extBks != null) {
                dep = extBks.getYxmc();
            }
        }
        return dep;
    }
    // 学生所在专业
    public String getMajor(int userId) {

        UserBean userBean = userBeanService.get(userId);
        String code = userBean.getCode();
        Byte type = userBean.getType();
        String major = null;
        if (type == SystemConstants.USER_TYPE_YJS) {
            ExtYjs extYjs = getExtYjs(code);
            if (extYjs != null) {
                major = extYjs.getZymc();
            }
        } else if (type == SystemConstants.USER_TYPE_BKS) {
            ExtBks extBks = getExtBks(code);
            if (extBks != null) {
                major = extBks.getZymc();
            }
        }
        return major;
    }

    // 照片、籍贯、 出生地、户籍地： 这四个字段只从人事库同步一次， 之后就不
    // 再同步这个信息了。 然后可以对这四个字段进行编辑。

    // 籍贯
    public String getExtNativePlace(byte source, String code) {

        if (source == SystemConstants.USER_SOURCE_JZG) {
            ExtJzg extJzg = getExtJzg(code);
            if (extJzg != null) {
                return extJzg.getJg();
            }
        }
        if (source == SystemConstants.USER_SOURCE_BKS) {
            ExtBks extBks = getExtBks(code);
            if (extBks != null) {
                return extBks.getSf();
            }
        }
        if (source == SystemConstants.USER_SOURCE_YJS) {
            ExtYjs extYjs = getExtYjs(code);
            if (extYjs != null) {
                return StringUtils.defaultIfBlank(extYjs.getSyszd(), extYjs.getHkszd());
            }
        }
        return null;
    }

    public ExtBks getExtBks(String code){

        if(StringUtils.isBlank(code)) return null;

        ExtBksExample example = new ExtBksExample();
        example.createCriteria().andXhEqualTo(code);
        List<ExtBks> extBkses = extBksMapper.selectByExample(example);
        if(extBkses.size()>0) return extBkses.get(0);

        return null;
    }

    public ExtYjs getExtYjs(String code){

        if(StringUtils.isBlank(code)) return null;

        ExtYjsExample example = new ExtYjsExample();
        example.createCriteria().andXhEqualTo(code);
        List<ExtYjs> extYjses = extYjsMapper.selectByExample(example);
        if(extYjses.size()>0) return extYjses.get(0);

        return null;
    }

    public ExtJzg getExtJzg(String code){

        if(StringUtils.isBlank(code)) return null;

        ExtJzgExample example = new ExtJzgExample();
        example.createCriteria().andZghEqualTo(code);
        List<ExtJzg> extJzges = extJzgMapper.selectByExampleWithBLOBs(example);
        if(extJzges.size()>0) return extJzges.get(0);

        return null;
    }
}
