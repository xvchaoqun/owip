package service.party;

import domain.base.MetaType;
import domain.member.Member;
import domain.party.Branch;
import domain.party.OrgAdmin;
import domain.party.Party;
import domain.party.PartyMember;
import domain.party.PartyMemberExample;
import domain.party.PartyMemberGroup;
import domain.party.PartyMemberView;
import domain.party.PartyMemberViewExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.sys.SysConfigService;
import service.unit.UnitService;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PartyMemberService extends BaseMapper {

    @Autowired
    private OrgAdminService orgAdminService;
    @Autowired
    private PartyMemberAdminService partyMemberAdminService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected PartyMemberGroupService partyMemberGroupService;
    @Autowired
    protected SysConfigService sysConfigService;

    // 获取现任分党委委员（拥有某种分工的）
    public List<PartyMemberView> getPartyMemberViews(int partyId,  int typeId){

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if(presentGroup==null) return new ArrayList<>();

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId())
                .andTypeIdsIn(Arrays.asList(typeId));

        return partyMemberViewMapper.selectByExample(example);
    }

    // 获取现任分党委委员（拥有某种分工的）
    public PartyMemberView getPartyMemberView(int partyId, int userId, int typeId){

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if(presentGroup==null) return null;

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId()).andUserIdEqualTo(userId)
                .andTypeIdsIn(Arrays.asList(typeId));
        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        return records.size()==0?null:records.get(0);
    }

    // 获取现任分党委委员
    public PartyMemberView getPartyMemberView(int partyId, int userId){

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if(presentGroup==null) return null;

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId()).andUserIdEqualTo(userId);

        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        return records.size()==0?null:records.get(0);
    }

    // 获取现任分党委书记
    public PartyMemberView getPartySecretary(int partyId){

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if(presentGroup==null) return null;

        MetaType partySecretaryType = CmTag.getMetaTypeByCode("mt_party_secretary");

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId()).andPostIdEqualTo(partySecretaryType.getId());

        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        return records.size()==0?null:records.get(0);
    }

    // 导出列表中分党委的所有委员
    public SXSSFWorkbook export(PartyMemberViewExample example) {


        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);

        int rowNum = 0;
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //sheet.setDefaultColumnWidth(12);
        //sheet.setDefaultRowHeight((short)(20*60));
        {
            Row titleRow = sheet.createRow(rowNum);
            titleRow.setHeight((short) (35.7 * 30));
            Cell headerCell = titleRow.createCell(0);
            CellStyle cellStyle = wb.createCellStyle();
            // 设置单元格居中对齐
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font font = wb.createFont();
            // 设置字体加粗
            font.setFontName("宋体");
            font.setFontHeight((short) 350);
            cellStyle.setFont(font);
            headerCell.setCellStyle(cellStyle);
            headerCell.setCellValue(CmTag.getSysConfig().getSchoolName() + "分党委委员");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }

        int count = records.size();
        String[] titles = {"工作证号", "姓名", "所在单位", "所属分党委", "职务",
                "分工", "任职时间", "性别", "民族", "身份证号",
                "出生时间", "党派", "党派加入时间", "到校时间", "岗位类别",
                "主岗等级", "专业技术职务", "专技职务等级", "管理岗位等级", "办公电话",
                "手机号", "所属党组织"};
        int columnCount = titles.length;
        Row firstRow = sheet.createRow(rowNum++);
        firstRow.setHeight((short) (35.7 * 12));
        for (int i = 0; i < columnCount; i++) {
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(ExportHelper.getHeadStyle(wb));
        }

        int columnIndex = 0;
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 工作证号
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 400));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200)); // 分工
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 50));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 出生时间
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));// 主岗等级
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150)); // 手机号
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 500));

        for (int i = 0; i < count; i++) {
            PartyMemberView record = records.get(i);
            SysUserView sysUser = record.getUser();
            Member member = memberService.get(record.getUserId());

            String partyName = "";// 党派
            String partyAddTime = "";

            if(record.getCadreDpType()!=null && record.getCadreDpType()>0 ){

                MetaType metaType = metaTypeMap.get(record.getCadreDpType().intValue());
                if(metaType!=null) partyName = metaType.getName();
                partyAddTime = DateUtils.formatDate(record.getCadreGrowTime(), DateUtils.YYYY_MM_DD);

            }else if(member!=null && member.getStatus()==MemberConstants.MEMBER_STATUS_NORMAL){

                partyName = "中共党员";
                partyAddTime = DateUtils.formatDate(member.getGrowTime(), DateUtils.YYYY_MM_DD);

            }else if(NumberUtils.longEqual(record.getCadreDpType(), 0L)){

                partyName = "中共党员";
                partyAddTime = DateUtils.formatDate(record.getCadreGrowTime(), DateUtils.YYYY_MM_DD);
            }


            String partyFullName = ""; // 所属党组织
            if(member!=null && member.getStatus()== MemberConstants.MEMBER_STATUS_NORMAL) {
                if (record.getPartyId() != null) {
                    Party party = partyMap.get(record.getPartyId());
                    if (party != null) {
                        partyFullName = party.getName();
                        if (record.getBranchId() != null) {
                            Branch branch = branchMap.get(record.getBranchId());
                            if (branch != null) {
                                partyFullName += "-" + branch.getName();
                            }
                        }
                    }
                }
            }

            List<String> typeNames = new ArrayList();
            String[] _typeIds = StringUtils.split(record.getTypeIds(), ",");
            if(_typeIds!=null && _typeIds.length>0){
                for (String typeId : _typeIds) {
                    MetaType metaType = metaTypeMap.get(Integer.valueOf(typeId));
                    if(metaType!=null) typeNames.add(metaType.getName());
                }
            }
            Unit unit = unitMap.get(record.getUnitId());
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    unit == null ? "" : unit.getName(),
                    partyMap.get(record.getGroupPartyId()).getName(),
                    metaTypeService.getName(record.getPostId()),

                    StringUtils.join(typeNames, ","),
                    DateUtils.formatDate(record.getAssignDate(), "yyyy.MM"),
                    record.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(record.getGender()),
                    record.getNation(),
                    record.getIdcard(),

                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),
                    partyName,
                    partyAddTime,
                    DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYY_MM_DD),
                    record.getPostClass(),

                    record.getMainPostLevel(),
                    record.getProPost(),
                    record.getProPostLevel(),
                    record.getManageLevel(),
                    record.getOfficePhone(),

                    record.getMobile(),
                    partyFullName
            };

            Row row = sheet.createRow(rowNum++);
            row.setHeight((short) (35.7 * 18));
            for (int j = 0; j < columnCount; j++) {

                Cell cell = row.createCell(j);
                String value = values[j];
                if (StringUtils.isBlank(value)) value = "-";
                cell.setCellValue(value);
                cell.setCellStyle(ExportHelper.getBodyStyle(wb));
            }
        }

        return wb;
    }

    // 查询用户是否是现任分党委、党总支、直属党支部班子的管理员
    public boolean isPresentAdmin(Integer userId, Integer partyId) {
        if (userId == null || partyId == null) return false;
        return iPartyMapper.isPartyAdmin(userId, partyId) > 0;
    }

    // 删除分党委管理员
    @Transactional
    public void delAdmin(int userId, int partyId) {

        List<PartyMember> partyMembers = iPartyMapper.findPartyAdminOfPartyMember(userId, partyId);
        for (PartyMember partyMember : partyMembers) { // 理论上只有一个
            partyMemberAdminService.toggleAdmin(partyMember);
        }
        List<OrgAdmin> orgAdmins = iPartyMapper.findPartyAdminOfOrgAdmin(userId, partyId);
        for (OrgAdmin orgAdmin : orgAdmins) { // 理论上只有一个
            orgAdminService.del(orgAdmin.getId(), orgAdmin.getUserId());
        }
    }

    public boolean idDuplicate(Integer id, int groupId, int userId, int postId) {

        {
            // 同一个人不可以在同一个委员会
            PartyMemberExample example = new PartyMemberExample();
            PartyMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (partyMemberMapper.countByExample(example) > 0) return true;
        }

        MetaType metaType = metaTypeService.findAll().get(postId);
        if (StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_party_secretary")) {

            // 每个委员会只有一个书记
            PartyMemberExample example = new PartyMemberExample();
            PartyMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andPostIdEqualTo(postId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (partyMemberMapper.countByExample(example) > 0) return true;
        }

        return false;
    }

    @Transactional
    public int insertSelective(PartyMember record, boolean autoAdmin) {

        record.setIsAdmin(false);
        record.setSortOrder(getNextSortOrder("ow_party_member", "group_id=" + record.getGroupId()));
        partyMemberMapper.insertSelective(record);

        if (autoAdmin) {
            partyMemberAdminService.toggleAdmin(record);
        }
        return 1;
    }

    @Transactional
    public void del(Integer id) {
        PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
        if (partyMember.getIsAdmin()) {
            partyMemberAdminService.toggleAdmin(partyMember);
        }
        partyMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;
        for (Integer id : ids) {
            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
            if (partyMember.getIsAdmin()) {
                partyMemberAdminService.toggleAdmin(partyMember);
            }
        }
        PartyMemberExample example = new PartyMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKey(PartyMember record, boolean autoAdmin) {

        PartyMember old = partyMemberMapper.selectByPrimaryKey(record.getId());
        record.setIsAdmin(old.getIsAdmin());
        record.setSortOrder(old.getSortOrder());
        record.setGroupId(old.getGroupId());
        partyMemberMapper.updateByPrimaryKeySelective(record);

        // 如果以前不是管理员，但是选择的类别是自动设定为管理员
        if (!record.getIsAdmin() && autoAdmin) {
            record.setUserId(old.getUserId());
            record.setGroupId(old.getGroupId());
            partyMemberAdminService.toggleAdmin(record);
        }

        if(record.getTypeIds()==null){
            commonMapper.excuteSql("update ow_party_member set type_ids=null where id="+record.getId());
        }

        return 1;
    }

    /*@Cacheable(value="PartyMember:ALL")
    public Map<Integer, PartyMember> findAll() {

        PartyMemberExample example = new PartyMemberExample();
        example.setOrderByClause("sort_order desc");
        List<PartyMember> partyMemberes = partyMemberMapper.selectByExample(example);
        Map<Integer, PartyMember> map = new LinkedHashMap<>();
        for (PartyMember partyMember : partyMemberes) {
            map.put(partyMember.getId(), partyMember);
        }

        return map;
    }*/

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        PartyMember entity = partyMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer groupId = entity.getGroupId();

        PartyMemberExample example = new PartyMemberExample();
        if (addNum > 0) {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PartyMember> overEntities = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PartyMember targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("ow_party_member", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_party_member", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());

            PartyMember record = new PartyMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partyMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
