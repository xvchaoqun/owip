package service.party;

import controller.global.OpException;
import domain.party.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;

import java.util.*;

@Service
public class BranchService extends BaseMapper {

    @Autowired
    private PartyMemberService partyMemberService;
    @Autowired
    private BranchMemberGroupService branchMemberGroupService;
    @Autowired
    private OrgAdminService orgAdminService;

    public Branch getByCode(String code){

        BranchExample example = new BranchExample();
        BranchExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        List<Branch> records = branchMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return records.size()==1?records.get(0):null;
    }

    // 批量转移支部到新的分党委
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "Branch:ALL", allEntries = true),
            @CacheEvict(value = "MemberApply", allEntries = true)
    })
    public void batchTransfer(Integer[] ids, int partyId, String remark){

        if(ids==null || ids.length==0) return;

        // 记录转移日志
        Date now = new Date();
        for (Integer id : ids) {

            Branch branch = branchMapper.selectByPrimaryKey(id);

            BranchTransferLog log = new BranchTransferLog();
            log.setBranchId(id);
            log.setPartyId(branch.getPartyId());
            log.setToPartyId(partyId);
            log.setCreateTime(now);
            log.setUserId(ShiroHelper.getCurrentUserId());
            log.setIp(ContextHelper.getRealIp());
            log.setRemark(remark);
            branchTransferLogMapper.insertSelective(log);
        }

        BranchExample example = new BranchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        Branch record = new Branch();
        record.setPartyId(partyId);
        branchMapper.updateByExampleSelective(record, example);

        String branchIds = StringUtils.join(ids, ",");

        String[] tableNameList = {"ow_apply_approval_log",
                "ow_apply_open_time", "ow_member_stay",
                "ow_member", "ow_member_abroad", "ow_member_apply",
                "ow_member_in", "ow_member_inflow",
                /*"ow_member_in_modify", "ow_member_modify",*/
                "ow_member_out", "ow_member_outflow", "ow_member_quit",
                "ow_member_return", "ow_member_transfer", "ow_org_admin"/*, "ow_retire_apply"*/};

        for (String tableName : tableNameList) {

            iMemberMapper.batchTransfer(tableName, branchIds);
        }

        // 校内转接特殊处理 to_party_id
        iMemberMapper.batchTransfer2(branchIds);

        // 更新支部转移次数
        iPartyMapper.updateBranchTransferCount(branchIds);
    }

    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        BranchExample example = new BranchExample();
        BranchExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return branchMapper.countByExample(example) > 0;
    }

    public void checkAuth(int partyId) {

        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin) throw new UnauthorizedException();
        }
    }

    public String genCode(int partyId) {

        int num;
        BranchExample example = new BranchExample();
        example.createCriteria().andPartyIdEqualTo(partyId);
        //example.setOrderByClause("code desc");
        example.setOrderByClause("right(code,3) desc"); // 支部转移之后，party.code还是原来的
        List<Branch> branchs = branchMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (branchs.size() > 0) {
            String code = branchs.get(0).getCode();
            String _code = code.substring(code.length() - 3);
            num = Integer.parseInt(_code) + 1;
        } else {
            num = 1;
        }
        Party party = partyMapper.selectByPrimaryKey(partyId);
        return party.getCode() + String.format("%03d", num);
    }

    @Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public void insertSelective(Branch record) {

        checkAuth(record.getPartyId());

        record.setIsDeleted(false);
        record.setCode(genCode(record.getPartyId()));
        record.setSortOrder(getNextSortOrder("ow_branch",
                "is_deleted=0 and party_id=" + record.getPartyId()));

        branchMapper.insertSelective(record);
    }

    /*@Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public void del(Integer id) {
        Branch branch = branchMapper.selectByPrimaryKey(id);
        checkAuth(branch.getPartyId());

        branchMapper.deleteByPrimaryKey(id);
    }*/

    @Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public void batchDel(Integer[] ids, boolean isDeleted) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            Branch branch = branchMapper.selectByPrimaryKey(id);
            checkAuth(branch.getPartyId());

            Branch record = new Branch();
            record.setId(id);
            record.setIsDeleted(isDeleted);

            if(!isDeleted){ // 恢复支部
                Party party = partyMapper.selectByPrimaryKey(branch.getPartyId());
                if(party.getIsDeleted())
                    throw new OpException(String.format("恢复支部失败，支部所属的分党委【%s】已删除。", party.getName()));

                record.setSortOrder(getNextSortOrder("ow_branch",
                "is_deleted=0 and party_id=" + record.getPartyId()));
            }else {
                // 删除所有的支部委员会
                BranchMemberGroupExample example = new BranchMemberGroupExample();
                example.createCriteria().andBranchIdEqualTo(id);
                List<BranchMemberGroup> branchMemberGroups = branchMemberGroupMapper.selectByExample(example);
                if (branchMemberGroups.size() > 0) {
                    List<Integer> groupIds = new ArrayList<>();
                    for (BranchMemberGroup branchMemberGroup : branchMemberGroups) {
                        groupIds.add(branchMemberGroup.getId());
                    }
                    branchMemberGroupService.batchDel(groupIds.toArray(new Integer[]{}), true);
                }

                // 删除所有的支部管理员
                orgAdminService.delAllOrgAdmin(null, id);
            }

            branchMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Branch record) {

        Branch branch = branchMapper.selectByPrimaryKey(record.getId());
        checkAuth(branch.getPartyId());

        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate code");
        return branchMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "Branch:ALL")
    public Map<Integer, Branch> findAll() {

        BranchExample example = new BranchExample();
        example.setOrderByClause("sort_order desc");
        List<Branch> branches = branchMapper.selectByExample(example);
        Map<Integer, Branch> map = new LinkedHashMap<>();
        for (Branch branch : branches) {
            map.put(branch.getId(), branch);
        }

        return map;
    }

    // 查找支部（根据党支部名称查找，用于导入数据）
    public Branch getBranch(String branchName, int partyId){

        BranchExample _example = new BranchExample();
        _example.createCriteria().andPartyIdEqualTo(partyId)
                .andNameEqualTo(branchName.trim());
        List<Branch> branchs =
                branchMapper.selectByExampleWithRowbounds(_example, new RowBounds(0,1));

        return branchs.size()==1?branchs.get(0):null;
    }

    @Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public int bacthImport(List<Branch> records) {

        int addCount = 0;
        for (Branch record : records) {

            int partyId = record.getPartyId();
            Branch _record = getBranch(record.getName(), partyId);
            if(_record==null){
                record.setCode(genCode(partyId));
                insertSelective(record);
                addCount++;
            }else{
                record.setId(_record.getId());
                updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
    }

    @Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        Branch entity = branchMapper.selectByPrimaryKey(id);
        Boolean isDeleted = entity.getIsDeleted();
        Integer partyId = entity.getPartyId();
        Integer baseSortOrder = entity.getSortOrder();

        BranchExample example = new BranchExample();
        if (addNum > 0) {

            example.createCriteria().andPartyIdEqualTo(partyId)
                    .andIsDeletedEqualTo(isDeleted).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andPartyIdEqualTo(partyId)
                    .andIsDeletedEqualTo(isDeleted).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Branch> overEntities = branchMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            Branch targetEntity = overEntities.get(overEntities.size() - 1);

            String whereSql = "is_deleted=" + isDeleted + " and party_id="+partyId;
            if (addNum > 0)
                commonMapper.downOrder("ow_branch", whereSql, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_branch", whereSql, baseSortOrder, targetEntity.getSortOrder());

            Branch record = new Branch();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMapper.updateByPrimaryKeySelective(record);
        }
    }
}
