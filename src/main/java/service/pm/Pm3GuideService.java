package service.pm;

import controller.global.OpException;
import domain.base.ContentTpl;
import domain.party.Branch;
import domain.party.Party;
import domain.pm.Pm3Guide;
import domain.pm.Pm3GuideExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import service.LoginUserService;
import shiro.ShiroHelper;
import sys.constants.ContentTplConstants;
import sys.constants.PmConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Pm3GuideService extends PmBaseMapper {

    @Autowired
    private LoginUserService loginUserService;

    @Transactional
    public void insertSelective(Pm3Guide record) {
        pm3GuideMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        pm3GuideMapper.deleteByPrimaryKey(id);
    }

    public void isDuplicate(String meetingMonth) {

        Pm3GuideExample example = new Pm3GuideExample();
        example.createCriteria().andMeetingMonthEqualTo(DateUtils.parseDate(meetingMonth, DateUtils.YYYY_MM));
        List<Pm3Guide> pm3GuideList = pm3GuideMapper.selectByExample(example);
        if (pm3GuideList != null && pm3GuideList.size() > 0)
            throw new OpException("添加重复");
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        Pm3GuideExample example = new Pm3GuideExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pm3GuideMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(Pm3Guide record) {
        pm3GuideMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, Pm3Guide> findAll() {

        Pm3GuideExample example = new Pm3GuideExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<Pm3Guide> records = pm3GuideMapper.selectByExample(example);
        Map<Integer, Pm3Guide> map = new LinkedHashMap<>();
        for (Pm3Guide record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void delFile(Integer id, String filePath) {

        Pm3Guide record = pm3GuideMapper.selectByPrimaryKey(id);

        // 权限校验
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            throw new OpException("权限不足");
        }

        if (StringUtils.isNotBlank(record.getGuideFilenames()) && record.getGuideFiles().contains(filePath)) {
            List<String> pathList = new ArrayList<String>(Arrays.asList(StringUtils.split(record.getGuideFiles(), ";")));
            List<String> nameList = new ArrayList<String>(Arrays.asList(StringUtils.split(record.getGuideFilenames(), ";")));
            int index = pathList.indexOf(filePath);
            pathList.remove(index);
            nameList.remove(index);
            if (pathList.size() == 0) {
                commonMapper.excuteSql("update pm3_guide set guide_files=null,guide_filenames=null where id=" + id);
            } else {
                record.setGuideFilenames(StringUtils.join(nameList, ";"));
                record.setGuideFiles(StringUtils.join(pathList, ";"));
                pm3GuideMapper.updateByPrimaryKeySelective(record);
            }
        }
    }

    //在这个方法里调用短信接口，使用codeSet
    public void notice(List<Integer> ids, boolean isOdAdmin, String msg) {

        if (ids == null || ids.size() == 0) return;
        Set<String> codeSets = new HashSet<>();//存储需要提醒的管理员工号
        if (isOdAdmin) {
            for (Integer id : ids) {
                OwAdmin owAdmin = new OwAdmin();
                owAdmin.setPartyId(id);
                List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList(owAdmin, new RowBounds());
                if (owAdmins != null && owAdmins.size() > 0) {
                    Set<String> codes = owAdmins.stream().map(OwAdmin::getCode).collect(Collectors.toSet());
                    codeSets.addAll(codes);
                }
            }
        } else {
            for (Integer id : ids) {
                OwAdmin owAdmin = new OwAdmin();
                owAdmin.setBranchId(id);
                List<OwAdmin> owAdmins = iPartyMapper.selectBranchAdminList(owAdmin, new RowBounds());
                if (owAdmins != null && owAdmins.size() > 0) {
                    Set<String> codes = owAdmins.stream().map(OwAdmin::getCode).collect(Collectors.toSet());
                    codeSets.addAll(codes);
                }
            }
        }
    }

    //定时发送提醒
    public void timingNotice() {

        List<Pm3Guide> pm3Guides = pm3GuideMapper.selectByExample(new Pm3GuideExample());
        ContentTpl partyTpl = CmTag.getContentTpl(ContentTplConstants.PM_3_NOTICE_PARTY);
        ContentTpl branchTpl = CmTag.getContentTpl(ContentTplConstants.PM_3_NOTICE_BRANCH);
        Date now = new Date();
        for (Pm3Guide pm3Guide : pm3Guides) {

            if (pm3Guide.getReportTime().after(now)) continue;

            Date meetingMonth = pm3Guide.getMeetingMonth();
            String _meetingMonth = DateUtils.formatDate(meetingMonth, "yyyy年MM月");
            String partyMsg = String.format(partyTpl.getContent(), _meetingMonth);
            String branchMsg = String.format(branchTpl.getContent(), _meetingMonth);
            int year = DateUtils.getYear(meetingMonth);
            int month = DateUtils.getMonth(meetingMonth);
            List<Party> partyList = iPmMapper.selectPartyList(year, month, PmConstants.PM_3_STATUS_OW, new RowBounds());
            List<Branch> branchList = iPmMapper.selectBranchList(year, month, loginUserService.adminPartyIdList(), PmConstants.PM_3_STATUS_SAVE, new RowBounds());
            if (partyList != null && partyList.size() > 0) {
                List<Integer> partyIdList = partyList.stream().map(Party::getId).collect(Collectors.toList());
                notice(partyIdList, true, partyMsg);
            }
            if (branchList != null && branchList.size() > 0) {
                List<Integer> branchIdList = branchList.stream().map(Branch::getId).collect(Collectors.toList());
                notice(branchIdList, false, branchMsg);
            }
        }
    }
}
