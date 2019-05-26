package service.ext;

import bean.UserBean;
import domain.base.MetaType;
import domain.ext.*;
import domain.member.Member;
import domain.party.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.party.BranchService;
import service.party.MemberService;
import service.party.PartyService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2015/11/21.
 */
@Service
public class ExtService extends BaseMapper {

    @Autowired
    private UserBeanService userBeanService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MemberService memberService;

    // 导出支部书记
    public void branch_secretary_export(BranchViewExample example, HttpServletResponse response) {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        MetaType secretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        List<BranchView> records = branchViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|100","姓名","编制类别","人员类别","人员状态","在岗情况","岗位类别", "主岗等级|150",
                "性别","出生日期|100", "年龄","年龄范围","民族", "国家/地区", "证件号码|150",
                "政治面貌","所在分党委、党总支、直属党支部|300|left","所在党支部|200|left", "所在单位", "入党时间|100","到校日期|100",
                "专业技术职务|150","专技岗位等级|150","管理岗位等级","任职级别","行政职务","学历","学历毕业学校|150|left","学位授予学校",
                "学位|100","学员结构", "人才类型", "人才称号", "籍贯","转正时间|100","手机号码|100","电子邮箱|150"};

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            BranchView branch = records.get(i);
            List<BranchMember> branchSecretary = iPartyMapper.findBranchMembers(secretaryType.getId(), branch.getId());

            if (branchSecretary.size() > 0) {
                Integer userId = branchSecretary.get(0).getUserId();
                SysUserView uv = sysUserService.findById(userId);
                ExtJzg extJzg = getExtJzg(uv.getCode());
                Date birth = uv.getBirth();
                String ageRange = "";
                if(birth!=null){
                    byte memberAgeRange = MemberConstants.getMemberAgeRange(birth);
                    if(memberAgeRange>0)
                        ageRange = MemberConstants.MEMBER_AGE_MAP.get(memberAgeRange);
                }
                Member member = memberService.get(userId);
                Integer partyId = (member!=null)?member.getPartyId():null;
                Integer branchId = (member!=null)?member.getBranchId():null;
                String[] values = {
                        uv.getCode(),
                        uv.getRealname(),
                        extJzg==null?"":extJzg.getBzlx(),
                        extJzg==null?"":extJzg.getRylx(),
                        extJzg==null?"":extJzg.getRyzt(), // 人员状态
                        extJzg==null?"":extJzg.getSfzg(), // 在岗情况
                        extJzg==null?"":extJzg.getGwlb(), // 岗位类别
                        extJzg==null?"":extJzg.getGwjb(), // 主岗等级--岗位级别
                        extJzg==null?"":extJzg.getXb(),
                        DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                        birth!=null?DateUtils.intervalYearsUntilNow(birth) + "":"",
                        ageRange, // 年龄范围
                        extJzg==null?"":extJzg.getMz(),
                        extJzg==null?"":extJzg.getGj(), // 国家/地区
                        extJzg==null?"":extJzg.getSfzh(), // 证件号码
                        member==null?"": MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(member.getPoliticalStatus()), // 政治面貌
                        partyId==null?"":partyMap.get(partyId).getName(),
                        branchId==null?"":branchMap.get(branchId).getName(),
                        //unitMap.get(record.getUnitId()).getName(),
                        extJzg==null?"":uv.getUnit(),
                        member==null?"":DateUtils.formatDate(member.getGrowTime(), DateUtils.YYYY_MM_DD),
                        extJzg==null?"":DateUtils.formatDate(extJzg.getLxrq(), DateUtils.YYYY_MM_DD), // 到校日期
                        "", // 专业技术职务
                        extJzg==null?"":extJzg.getZjgwdj(), //专技岗位等级
                        extJzg==null?"":extJzg.getGlgwdj(), // 管理岗位等级
                        extJzg==null?"":extJzg.getXzjb(), // 任职级别 -- 行政级别
                        extJzg==null?"":extJzg.getZwmc(), // 行政职务 -- 职务
                        extJzg==null?"":extJzg.getZhxlmc(), // 学历
                        extJzg==null?"":extJzg.getXlbyxx(), // 学历毕业学校
                        extJzg==null?"":extJzg.getXwsyxx(), // 学位授予学校
                        extJzg==null?"":extJzg.getZhxw(), // 学位
                        extJzg==null?"":extJzg.getXyjg(), // 学员结构 (学位授予国家)
                        extJzg==null?"":extJzg.getRclx(),
                        extJzg==null?"":extJzg.getRclx(),
                        extJzg==null?"":extJzg.getRcch(),
                        member==null?"":DateUtils.formatDate(member.getPositiveTime(), DateUtils.YYYY_MM_DD),
                        uv.getMobile(),
                        extJzg==null?"":extJzg.getDzxx()
                };
                valuesList.add(values);
            }
        }
        String fileName = "党支部书记(" + DateUtils.formatDate(new Date(), "yyyyMMdd") +")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

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
