package service.analysis;

import domain.member.*;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.member.MemberApplyViewMapper;
import persistence.member.MemberViewMapper;
import persistence.party.PartyMapper;
import service.BaseMapper;
import service.base.MetaTypeService;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class StatCodService extends BaseMapper {

    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    private MemberViewMapper memberViewMapper;
    @Autowired
    private MemberApplyViewMapper memberApplyViewMapper;

    public void codMemberExport(MemberViewExample example, HttpServletResponse response) {
        List<MemberView> memberViews = memberViewMapper.selectByExample(example);
        int rownum = memberViews.size();
        String[] titles = {"姓名|100","身份证号码|250","性别|100","出生日期|150","民族|100",
                "籍贯|150","年龄|100","学历|100","学位|100","毕业院校|250","专业|250",
                "入党时间|200","转正日期|200", "工作岗位|200",
                "从事专业技术服务|200","新的社会阶层|200","人员类别|150",
                "是否农民工|150","手机号码|150","联系电话|150","所在党支部|250","联合支部单位名称|200", "档案所在单位|200",
                "户籍所在地|200","现居住地|200","失去联系情形|150","失去联系日期|150","信息完整度|150"
        };
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberView memberView = memberViews.get(i);
            Integer age = DateUtils.getYear(new Date()) - DateUtils.getYear(memberView.getBirth());
            String gender ="--";
            if(memberView.getGender() != null && memberView.getGender()==1){
                gender = "男";
            }else if (memberView.getGender() != null && memberView.getGender()==2){
                gender = "女";
            }
            String politicalStatus = "--";
            if (memberView.getPoliticalStatus() != null && memberView.getPoliticalStatus() == 1){
                politicalStatus = "预备党员";
            }else if (memberView.getPoliticalStatus() != null && memberView.getPoliticalStatus() == 0){
                politicalStatus = "正式党员";
            }
            String[] values = {
                    memberView.getRealname() == null ? "" : memberView.getRealname(),
                    memberView.getIdcard() == null ? "" : memberView.getIdcard(),
                    gender,
                    DateUtils.formatDate(memberView.getBirth(),DateUtils.YYYYMM),
                    memberView.getNation() == null ? "" : memberView.getNation(),
                    memberView.getNativePlace() == null ? "" : memberView.getNativePlace(),
                    age.toString(),
                    memberView.getEducation() == null ? "" : memberView.getEducation(),
                    memberView.getDegree() == null ? "" : memberView.getDegree(),
                    memberView.getSchool() == null ? "" : memberView.getSchool(),
                    memberView.getMajor() == null ? "" : memberView.getMajor(),
                    DateUtils.formatDate(memberView.getGrowTime(),DateUtils.YYYYMM),
                    DateUtils.formatDate(memberView.getPositiveTime(),DateUtils.YYYYMM),
                    memberView.getPostClass() == null ? "" : memberView.getPostClass(),
                    memberView.getProPost() == null ? "" : memberView.getProPost(),
                    "",//新的社会阶层
                    politicalStatus,
                    "",//是否农民工
                    memberView.getMobile() == null ? "" : memberView.getMobile(),
                    "",//联系电话
                    memberView.getBranchName() == null ? "" : memberView.getBranchName(),
                    "",//联合支部单位名称
                    "",//档案所在单位
                    "",//户籍所在地
                    memberView.getAddress() == null ? "" : memberView.getAddress(),
                    "",//失去联系情形
                    "",//失去联系日期
                    "",//信息完整度
            };
            valuesList.add(values);
        }
        String fileName = "中组部年度统计-党员(" + DateUtils.formatDate(new Date(), "yyyyMMddHH") + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }


    public void codApplyExport(MemberApplyViewExample example, HttpServletResponse response){
        List<MemberApplyView> memberApplyViews = memberApplyViewMapper.selectByExample(example);
        int rownum = memberApplyViews.size();
        String[] titles = {"学工号|150","姓名|150","公民身份证号码|250","性别|100","民族|150","籍贯|100",
                "出生日期|150","学历|100","人员类别|100","入党申请时间|150","确定为积极分子时间|150","确定为发展对象时间|250",
                "所在党组织|250","入党时间|150", "转正时间|150","工作岗位|150","现居住地|100","移动电话|150",
                "联系电话|150","党员档案所在单位|150","从事专业技术职务|150","新社会阶层类型|250","一线情况|200"
        };
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberApplyView record = memberApplyViews.get(i);
            SysUserView uv = CmTag.getUserById(record.getUserId());
            String _stage = "";
            if (record.getStage() == OwConstants.OW_APPLY_STAGE_INIT || record.getStage() == OwConstants.OW_APPLY_STAGE_PASS){
                _stage = OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_INIT);
            }else {
                _stage = OwConstants.OW_APPLY_STAGE_MAP.get(record.getStage());
            }

            String partyName = record.getPartyId()==null?"":partyMapper.selectByPrimaryKey(record.getPartyId()).getName();
            String branchName = "";
            if (record.getBranchId() != null) {
                branchName="-"+branchMapper.selectByPrimaryKey(record.getBranchId()).getName();
            }
            String[] values = {
                    uv.getCode(),
                    uv.getRealname(),
                    uv.getIdcard(),
                    uv.getGender()==null?"": SystemConstants.GENDER_MAP.get(uv.getGender()),
                    uv.getNation(),
                    uv.getNativePlace(),
                    uv.getBirth()==null?"":DateUtils.formatDate(uv.getBirth(),DateUtils.YYYYMM),
                    "",//学历
                    _stage,
                    record.getApplyTime()==null?"":DateUtils.formatDate(record.getApplyTime(),DateUtils.YYYYMM),
                    record.getActiveTime()==null?"":DateUtils.formatDate(record.getActiveTime(),DateUtils.YYYYMM),
                    record.getCandidateTime()==null?"":DateUtils.formatDate(record.getCandidateTime(),DateUtils.YYYYMM),
                    partyName+branchName,
                    record.getPassTime()==null?"":DateUtils.formatDate(record.getPassTime(),DateUtils.YYYYMM),
                    record.getPositiveTime()==null?"":DateUtils.formatDate(record.getPositiveTime(),DateUtils.YYYYMM),
                    uv.getPost(),
                    "",//居住地址
                    uv.getPhone(),
                    "",//联系电话
                    "",//党员档案所在单位
                    "",//从事专业技术职务
                    "",//新社会阶层类型
                    "",//一线情况
            };
            valuesList.add(values);

        }
        String fileName = "中组部年度统计-申请人(" + DateUtils.formatDate(new Date(), "yyyyMMddHH") + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }


}
