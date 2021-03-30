package service.pm;

import controller.global.OpException;
import domain.party.Branch;
import domain.party.Party;
import domain.party.PartyExample;
import domain.pm.Pm3Guide;
import domain.pm.Pm3GuideExample;
import ext.service.OneSendService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import service.LoginUserService;
import service.party.PartyService;
import shiro.ShiroHelper;
import sys.constants.Pm3Constants;
import sys.constants.RoleConstants;
import sys.utils.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Pm3GuideService extends PmBaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private OneSendService oneSendService;

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
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
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

    // 通知还未提交月报的党委
    public void noticeUnSubmitParty(Date meetingMonth, Integer[] partyIds, String notice){

        List<Party> unSubmitPartyList = getUnSubmitPartyList(meetingMonth, partyIds);

        Set<String> userList = new HashSet<>();
        Set<String> realnameList = new HashSet<>();
        for (Party party : unSubmitPartyList) {

            OwAdmin owAdmin = new OwAdmin();
            owAdmin.setPartyId(party.getId());
            List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList(owAdmin, new RowBounds());
            if (owAdmins != null && owAdmins.size() > 0) {
                Set<String> codes = owAdmins.stream().map(OwAdmin::getCode).collect(Collectors.toSet());
                Set<String> realnames = owAdmins.stream().map(OwAdmin::getRealname).collect(Collectors.toSet());
                userList.addAll(codes);
                realnameList.addAll(realnames);
            }
        }

        if(userList.size()>0) {
            oneSendService.sendMsg(userList, realnameList, notice);
        }
    }

    // 通知还未提交月报的党支部
    public void noticeUnSubmitBranch(Date meetingMonth, Integer[] partyId, String notice){

        List<Branch> unSubmitBranchList = getUnSubmitBranchList(meetingMonth, partyId);

        Set<String> userList = new HashSet<>();
        Set<String> realnameList = new HashSet<>();
        for (Branch branch : unSubmitBranchList) {

            OwAdmin owAdmin = new OwAdmin();
            owAdmin.setBranchId(branch.getId());
            List<OwAdmin> owAdmins = iPartyMapper.selectBranchAdminList(owAdmin, new RowBounds());
            if (owAdmins != null && owAdmins.size() > 0) {
                Set<String> codes = owAdmins.stream().map(OwAdmin::getCode).collect(Collectors.toSet());
                Set<String> realnames = owAdmins.stream().map(OwAdmin::getRealname).collect(Collectors.toSet());
                userList.addAll(codes);
                realnameList.addAll(realnames);
            }
        }

        if(userList.size()>0) {
            oneSendService.sendMsg(userList, realnameList, notice);
        }
    }

    // 查询还未全部提交月报的党支部的所属分党委列表
    public List<Party> getUnSubmitPartyList(Date meetingMonth, Integer[] partyIds){

        PartyExample example = new PartyExample();
        PartyExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        if (partyIds != null && partyIds.length > 0) {
            criteria.andIdIn(Arrays.asList(partyIds));
        }
        example.setOrderByClause("sort_order desc");
        List<Party> parties = partyMapper.selectByExample(example);

        int year = DateUtils.getYear(meetingMonth);
        int month = DateUtils.getMonth(meetingMonth);

        List<Party> unSubmitPartyList = new ArrayList<>();
        for (Party party : parties) {

            int partyId = party.getId();
            if(partyService.isDirectBranch(partyId)){

                if(iPmMapper.unSubmitDirectBranch(year, month, partyId, Pm3Constants.PM_3_STATUS_OW)!=null){
                    unSubmitPartyList.add(party);
                }
            }else{

                List<Branch> branches = getUnSubmitBranchList(meetingMonth, new Integer[]{partyId});
                if(branches.size()>0){
                    unSubmitPartyList.add(party);
                }
            }
        }

        return unSubmitPartyList;
    }

    // 查询还未提交月报的党支部
    public List<Branch> getUnSubmitBranchList(Date meetingMonth, Integer[] partyIds){

        int year = DateUtils.getYear(meetingMonth);
        int month = DateUtils.getMonth(meetingMonth);

        return iPmMapper.selectUnSubmitBranchList(year, month, Arrays.asList(partyIds), Pm3Constants.PM_3_STATUS_PARTY);
    }
}
