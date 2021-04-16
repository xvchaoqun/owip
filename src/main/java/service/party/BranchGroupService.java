package service.party;

import domain.party.*;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.party.BranchGroupMapper;
import persistence.party.BranchGroupMemberMapper;
import service.BaseMapper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class BranchGroupService extends BaseMapper {

    @Autowired
    private BranchGroupMapper branchGroupMapper;
    @Autowired
    private BranchGroupMemberMapper branchGroupMemberMapper;
    @Autowired
    private PartyService partyService;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        BranchGroupExample example = new BranchGroupExample();
        BranchGroupExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return branchGroupMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(BranchGroup record){

        record.setSortOrder(getNextSortOrder("ow_branch_group", "branch_id="+record.getBranchId()));
        branchGroupMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        branchGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        BranchGroupExample example = new BranchGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchGroupMapper.deleteByExample(example);
        delGroupMember(ids);
    }

    @Transactional
    public void updateByPrimaryKeySelective(BranchGroup record){

        branchGroupMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, BranchGroup> findAll() {

        BranchGroupExample example = new BranchGroupExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<BranchGroup> records = branchGroupMapper.selectByExample(example);
        Map<Integer, BranchGroup> map = new LinkedHashMap<>();
        for (BranchGroup record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        BranchGroup branchGroup = branchGroupMapper.selectByPrimaryKey(id);
        changeOrder("ow_branch_group","branch_id="+branchGroup.getBranchId(), ORDER_BY_DESC, id, addNum);
    }

    // 导出党小组成员
    public void branch_groupMember_export(BranchViewExample example, HttpServletResponse response) {

        String[] titles = {"所在党组织|300","所属支部|200","党校小组名称|250","学工号|150","姓名|100","类别|100"};
        List<String[]> valuesList = new ArrayList<>();
        Map<Integer,Party> partyMap = partyService.findAll();

        List<BranchView> records = branchViewMapper.selectByExample(example);

        for (BranchView branchView : records){

            List<BranchGroup> branchGroups = getBranchGroups(branchView.getId());

            for (BranchGroup branchGroup : branchGroups){

                List<BranchGroupMember> branchGroupMembers = getBranchGroupMembers(branchGroup.getId());

                for (BranchGroupMember branchGroupMember : branchGroupMembers){

                    SysUserView sysUserView = CmTag.getUserById(branchGroupMember.getUserId());

                    String[] values = {
                            partyMap.get(branchView.getPartyId()).getName(),
                            branchView.getName(),
                            branchGroup.getName(),
                            sysUserView.getCode(),
                            sysUserView.getRealname(),
                            branchGroupMember.getIsLeader()?"组长":"成员"
                    };
                    valuesList.add(values);
                }
            }
        }

        String fileName = String.format("党小组(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    //得到支部下的所有党小组
    public List<BranchGroup> getBranchGroups(Integer branchId){

        BranchGroupExample branchGroupExample = new BranchGroupExample();
        branchGroupExample.createCriteria().andBranchIdEqualTo(branchId);
        branchGroupExample.setOrderByClause("sort_order desc");

        return branchGroupMapper.selectByExample(branchGroupExample);
    }

    //得到党小组下的所有成员
    public List<BranchGroupMember> getBranchGroupMembers(Integer groupId){

        BranchGroupMemberExample branchGroupMemberExample = new BranchGroupMemberExample();
        branchGroupMemberExample.createCriteria().andGroupIdEqualTo(groupId);
        branchGroupMemberExample.setOrderByClause("sort_order desc");

        return branchGroupMemberMapper.selectByExample(branchGroupMemberExample);
    }

    //删除党小组所有成员
    @Transactional
    public void delGroupMember(Integer[] ids){

        BranchGroupMemberExample branchGroupMemberExample = new BranchGroupMemberExample();
        branchGroupMemberExample.createCriteria().andGroupIdIn(Arrays.asList(ids));

        branchGroupMemberMapper.deleteByExample(branchGroupMemberExample);
    }
}
