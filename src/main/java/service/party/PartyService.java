package service.party;

import domain.base.MetaType;
import domain.party.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.base.MetaTypeService;
import shiro.ShiroHelper;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PartyService extends BaseMapper {
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private PartyMemberGroupService partyMemberGroupService;
    @Autowired
    private PartyAdminService partyAdminService;
    @Autowired
    private OrgAdminService orgAdminService;

    // 树形选择分党委（状态正常的）
    public TreeNode getTree(Set<Integer> selectIdSet) {

        if (null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = CmTag.getStringProperty("partyName") + "列表";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        PartyExample example = new PartyExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause(" sort_order desc");
        List<Party> partyList = partyMapper.selectByExample(example);
        for (Party party : partyList) {

            TreeNode node = new TreeNode();
            node.title = party.getName();
            node.key = party.getId() + "";
            if (selectIdSet.contains(party.getId().intValue())) {
                node.select = true;
            }

            rootChildren.add(node);
        }

        return root;
    }

    public PartyView getPartyView(int partyId) {

        PartyViewExample example = new PartyViewExample();
        example.createCriteria().andIdEqualTo(partyId);

        List<PartyView> partyViews = partyViewMapper.selectByExample(example);
        return partyViews.size() == 0 ? null : partyViews.get(0);
    }

    public Party getByCode(String code){

        PartyExample example = new PartyExample();
        PartyExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);

        //控制其他模块不会查到内设党总支，后续如果党支部和党员用到内设党总支，再做修改
        if (CmTag.getBoolProperty("use_inside_pgb")){
            criteria.andFidIsNull();
        }
        List<Party> records = partyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return records.size()==1?records.get(0):null;
    }

    // 判断partyId和branchId的有效性
    public boolean isPartyContainBranch(int partyId, Integer branchId) {

        Party party = findAll().get(partyId);
        if (party == null) return false;

        if (branchId != null) {

            Branch branch = branchService.findAll().get(branchId);
            return (branch != null && branch.getPartyId() == partyId);
        } else {

            Map<String, MetaType> codeKeyMap = metaTypeService.codeKeyMap();
            MetaType directBranchType = codeKeyMap.get("mt_direct_branch");
            // 直属党支部返回true
            return (party.getClassId() == directBranchType.getId().intValue());
        }
    }

    // 是否直属党支部
    public boolean isDirectBranch(int partyId) {

        Party party = findAll().get(partyId);
        MetaType metaType = metaTypeService.findAll().get(party.getClassId());
        return StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_direct_branch");
    }

    // 是否分党委
    public boolean isParty(int partyId) {

        Party party = findAll().get(partyId);
        MetaType metaType = metaTypeService.findAll().get(party.getClassId());
        return StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_party");
    }

    // 是否党总支
    public boolean isPartyGeneralBranch(int partyId) {

        Party party = findAll().get(partyId);
        MetaType metaType = metaTypeService.findAll().get(party.getClassId());
        return StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_party_general_branch");
    }


    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "duplicate code");

        PartyExample example = new PartyExample();
        PartyExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return partyMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public int insertSelective(Party record) {

        //Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate code");
        record.setSortOrder(getNextSortOrder("ow_party", "is_deleted=0"));
        record.setIsDeleted(false);
        return partyMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public int bacthImport(List<Party> records) {

        int addCount = 0;
        for (Party record : records) {
            String code = record.getCode();
            Party _record = getByCode(code);
            if(_record==null){
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
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public void batchDel(Integer[] ids, boolean isDeleted) {

        if (ids == null || ids.length == 0) return;


        for (Integer id : ids) {

            Party record = new Party();
            record.setId(id);
            record.setIsDeleted(isDeleted);
            if (isDeleted) {
                // 删除所有的领导班子
                {
                    PartyMemberGroupExample example = new PartyMemberGroupExample();
                    example.createCriteria().andPartyIdEqualTo(id);
                    List<PartyMemberGroup> partyMemberGroups = partyMemberGroupMapper.selectByExample(example);
                    if (partyMemberGroups.size() > 0) {
                        List<Integer> groupIds = new ArrayList<>();
                        for (PartyMemberGroup partyMemberGroup : partyMemberGroups) {
                            groupIds.add(partyMemberGroup.getId());
                        }
                        partyMemberGroupService.batchDel(groupIds.toArray(new Integer[]{}), true, null);
                    }
                }
                // 删除所有的支部
                {
                    BranchExample example = new BranchExample();
                    example.createCriteria().andPartyIdEqualTo(id);
                    List<Branch> branchs = branchMapper.selectByExample(example);
                    if (branchs.size() > 0) {
                        List<Integer> branchIds = new ArrayList<>();
                        for (Branch branch : branchs) {
                            branchIds.add(branch.getId());
                        }
                        branchService.batchDel(branchIds.toArray(new Integer[]{}), true);
                    }
                }

                // 删除所有的分党委管理员
                orgAdminService.delAllOrgAdmin(id, null);
            } else {
                record.setSortOrder(getNextSortOrder("ow_party", "is_deleted=0")); // 恢复：更新排序
            }

            partyMapper.updateByPrimaryKeySelective(record);
        }


        /*PartyExample example = new PartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        Party record = new Party();
        record.setIsDeleted(isDeleted);
        partyMapper.updateByExampleSelective(record, example);*/
    }

    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Party record) {
        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate code");
        return partyMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "Party:ALL")
    public Map<Integer, Party> findAll() {

        PartyExample example = new PartyExample();
        example.setOrderByClause("sort_order desc");
        List<Party> partyes = partyMapper.selectByExample(example);
        Map<Integer, Party> map = new LinkedHashMap<>();
        for (Party party : partyes) {
            map.put(party.getId(), party);
        }

        return map;
    }

    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public void changeOrder(int id, int addNum, byte type) {

        changeOrder("ow_party", "is_deleted=0 " + (type==1?"and fid is not null":"and fid is null"), ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateIntegrity(){

        PartyViewExample partyViewExample = new PartyViewExample();
        partyViewExample.createCriteria().andIsDeletedEqualTo(false);
        List<PartyView> partyViews = partyViewMapper.selectByExample(partyViewExample);

        for (PartyView partyView : partyViews){

            checkIntegrity(partyView);
        }
    }

    public void checkIntegrity(PartyView partyView){
        if (partyView==null) return;

        Double a = 0.00; Double b = 0.00;
        if (StringUtils.isNotBlank(partyView.getName())){a++;}//名称
        b++;
        if (partyView.getFoundTime()!=null){a++;}//成立时间
        b++;
        if (partyView.getUnitId()!=null){a++;}//所属单位
        b++;
        if (partyView.getClassId()!=null){a++;}//党总支类别
        b++;
        if (partyView.getTypeId()!=null){a++;}//组织类别
        b++;
        a++;b++;//所在单位属性
        if (StringUtils.isNotBlank(partyView.getPhone())){a++;}//联系电话
        b++;
        if (partyView.getIsBg()!=null){a++;}//是否为标杆院系
        b++;
        if (partyView.getIsBg()){//是标杆院系
            if (partyView.getBgDate()!=null){a++;}//如果是标杆院系，检查标杆评定时间
            b++;
        }
        if (partyView.getAppointTime()!=null){a++;}//任命时间
        b++;
        if (partyView.getTranTime()!=null){a++;}//应换届时间
        b++;
        a++;b++;//班子成员信息
        a++;b++;//支部排序
        BigDecimal molecule = new BigDecimal(a);
        BigDecimal denominator = new BigDecimal(b);

        Party party = new Party();
        party.setId(partyView.getId());
        party.setIntegrity(molecule.divide(denominator,2,BigDecimal.ROUND_HALF_UP));

        partyMapper.updateByPrimaryKeySelective(party);
    }

    public Map getPartyIntegrity (){

        Map allMap = new HashMap<>();

        Map<Integer,Integer> branchMap = new HashMap<>();//党支部完整度统计信息
        Map<Integer,Map> memberMap = new HashMap<>();//党员完整度统计信息

            PartyExample example = new PartyExample();
            example.setOrderByClause("sort_order desc");
            example.createCriteria().andIsDeletedEqualTo(false);
            List<Party> parties = partyMapper.selectByExample(example);//全部分党委信息

            allMap.put("parties",parties);

            for (Party party : parties){

                Integer partyId = party.getId();
                branchMap.put(partyId,iPartyMapper.countBranchNotIntegrity(partyId));
                memberMap.put(partyId,iMemberMapper.countMemberNotIntegrity(partyId,null));
            }

        allMap.put("branchMap",branchMap);
        allMap.put("memberMap",memberMap);
        return allMap;
    }

    public Map getBranchIntegrity(Integer partyId){

        if (partyId == null) return new HashMap();

        Map allMap = new HashMap<>();

        Map<Integer,Map> memberMap = new HashMap<>();//党员完整度统计信息

        BranchExample example = new BranchExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andPartyIdEqualTo(partyId);

        List<Branch> branches = branchMapper.selectByExample(example);

        allMap.put("branches",branches);

        for (Branch branch : branches){

            memberMap.put(branch.getId(),iMemberMapper.countMemberNotIntegrity(null,branch.getId()));
            }

        allMap.put("memberMap",memberMap);
        return allMap;
    }

    //根据userId获取所有管理的基层党组织
    public List<Party> getPartysByUserId(Integer userId){

        List<Integer> adminPartyIdList = partyAdminService.adminPartyIdList(userId);

        PartyExample partyExample = new PartyExample();
        partyExample.setOrderByClause("sort_order desc");
        PartyExample.Criteria criteria = partyExample.createCriteria();
        criteria.andIsDeletedEqualTo(false);

        if (ShiroHelper.isPermitted("partyIntegrity:*"))//如果是党建管理员
            return partyMapper.selectByExample(partyExample);

        if (adminPartyIdList.size()>0){//如果所管理的分党委不为null

            criteria.andIdIn(adminPartyIdList);
            return partyMapper.selectByExample(partyExample);
            }

        return new ArrayList<>();
    }

    //生成分党委的code
    public String genCode(String startCode) {

        int num;
        PartyExample example = new PartyExample();
        example.createCriteria().andCodeStartLike(startCode);
        //example.setOrderByClause("code desc");
        example.setOrderByClause("right(code,2) desc");
        List<Party> partyList = partyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (partyList.size() > 0) {
            String code = partyList.get(0).getCode();
            String _code = code.substring(code.length() - 2);
            num = Integer.parseInt(_code) + 1;
        } else {
            num = 1;
        }
        return startCode + String.format("%02d", num);
    }

    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public void batchDelPgb(Integer[] ids) {

        PartyExample example = new PartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andFidIsNotNull();
        partyMapper.deleteByExample(example);
    }

    public Party getByName(String name){

        if (StringUtils.isBlank(name)) return null;
        PartyExample example = new PartyExample();
        PartyExample.Criteria criteria = example.createCriteria().andNameEqualTo(name);

        //控制其他模块不会查到内设党总支，后续如果党支部和党员用到内设党总支，再做修改
        if (CmTag.getBoolProperty("use_inside_pgb")){
            criteria.andFidIsNull();
        }
        List<Party> partyList = partyMapper.selectByExample(example);

        return partyList.size() > 0 ? partyList.get(0) : null;
    }
}