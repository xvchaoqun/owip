package service.party;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.party.Organizer;
import domain.party.OrganizerExample;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.OwConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;

import java.util.*;

import static sys.constants.OwConstants.OW_ORGANIZER_TYPE_MAP;

@Service
public class OrganizerService extends BaseMapper {

    // 组织员选择树
    public TreeNode getTree(Set<Integer> selectIdSet) {

        TreeNode root = new TreeNode();
        root.title = "组织员";
        root.expand = true;
        root.isFolder = true;
        root.children = new ArrayList<>();

        TreeNode schoolNode = new TreeNode();
        schoolNode.title = "校级组织员";
        schoolNode.isFolder = true;
        List<TreeNode> schoolChildren = new ArrayList<TreeNode>();
        schoolNode.children = schoolChildren;
        root.children.add(schoolNode);

        TreeNode unitNode = new TreeNode();
        unitNode.title = "院系级组织员";
        unitNode.isFolder = true;
        List<TreeNode> unitChildren = new ArrayList<TreeNode>();
        unitNode.children = unitChildren;
        root.children.add(unitNode);


        OrganizerExample example = new OrganizerExample();
        example.createCriteria().andStatusEqualTo(OwConstants.OW_ORGANIZER_STATUS_NOW);
        example.setOrderByClause("type asc, sort_order desc");

        List<Organizer> organizers = organizerMapper.selectByExample(example);
        for (Organizer organizer : organizers) {

            String unit = organizer.getUnit();
            String post = organizer.getPost();
            SysUserView uv = organizer.getUser();
            int userId = uv.getUserId();
            TreeNode node = new TreeNode();
            node.title = uv.getRealname() + (post != null ? ("-" + post) : "") + (unit != null ? ("-" + unit) : "");

            int key = userId;
            node.key = key + "";

            if (selectIdSet.contains(key)) {
                node.select = true;
            }
            if(organizer.getType()==OwConstants.OW_ORGANIZER_TYPE_SCHOOL) {
                schoolChildren.add(node);
            }else{
                unitChildren.add(node);
            }
        }
        return root;
    }

    // 根据组织员类型和用户ID，获取组织员信息
    public Organizer get(byte type, int userId) {

        OrganizerExample example = new OrganizerExample();
        example.createCriteria()
                .andTypeEqualTo(type)
                // 非历任
                .andStatusNotEqualTo(OwConstants.OW_ORGANIZER_STATUS_HISTORY)
                .andUserIdEqualTo(userId);

        List<Organizer> organizers = organizerMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return organizers.size() == 0 ? null : organizers.get(0);
    }

    // 同一类型的组织员，现任或离任只允许存在一个
    public boolean idDuplicate(Integer id, byte type, int userId, Byte status) {

        if (status != null && status == OwConstants.OW_ORGANIZER_STATUS_HISTORY) return false;

        OrganizerExample example = new OrganizerExample();
        OrganizerExample.Criteria criteria =
                example.createCriteria()
                        .andTypeEqualTo(type)
                        // 非历任
                        .andStatusNotEqualTo(OwConstants.OW_ORGANIZER_STATUS_HISTORY)
                        .andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return organizerMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(Organizer record) {

        if(idDuplicate(null, record.getType(), record.getUserId(), record.getStatus())){
            throw new OpException(record.getUser().getRealname()+"("+record.getUser().getCode()+")"
                    + "已经是"
                    + OwConstants.OW_ORGANIZER_STATUS_MAP.get(record.getStatus())
                    + OW_ORGANIZER_TYPE_MAP.get(record.getType()));
        }
        record.setSortOrder(getNextSortOrder("ow_organizer",
                "type=" + record.getType() + " and status=" + record.getStatus()));

        syncBaseInfo(record);

        record.setAddTime(new Date());
        record.setAddUserId(ShiroHelper.getCurrentUserId());
        organizerMapper.insertSelective(record);

        // 添加离任信息时
        if (record.getStatus() == OwConstants.OW_ORGANIZER_STATUS_LEAVE) {

            // 同时添加一条历任信息
            record.setId(null);
            record.setStatus(OwConstants.OW_ORGANIZER_STATUS_HISTORY);

            insertSelective(record);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        OrganizerExample example = new OrganizerExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        organizerMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(Organizer record, boolean syncBaseInfo) {

        int id = record.getId();
        Organizer organizer = organizerMapper.selectByPrimaryKey(id);
        if (idDuplicate(id, organizer.getType(), organizer.getUserId(), organizer.getStatus())) {
            throw new OpException(organizer.getUser().getRealname()
                    + "已经是"
                    + OwConstants.OW_ORGANIZER_STATUS_MAP.get(organizer.getStatus())
                    + OwConstants.OW_ORGANIZER_TYPE_MAP.get(organizer.getType()));
        }

        if (syncBaseInfo) {
            syncBaseInfo(record);
        }

        // 不更新状态和类别
        record.setStatus(null);
        record.setType(null);
        organizerMapper.updateByPrimaryKeySelective(record);
    }

    // 同步基本信息
    private void syncBaseInfo(Organizer record) {

        Integer userId = record.getUserId();
        if (userId == null) return;

        SysUserInfo ui = sysUserInfoMapper.selectByPrimaryKey(userId);
        if (ui != null) {
            record.setUnit(ui.getUnit());
        }

        TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(userId);
        if (teacherInfo != null) {
            record.setPost(teacherInfo.getPost());
            record.setAuthorizedType(teacherInfo.getAuthorizedType());
            record.setStaffType(teacherInfo.getStaffType());
            record.setPostClass(teacherInfo.getPostClass());
            record.setMainPostLevel(teacherInfo.getMainPostLevel());
            record.setProPost(teacherInfo.getProPost());
            record.setIsRetire(teacherInfo.getIsRetire());
        }

        Member member = memberMapper.selectByPrimaryKey(userId);
        if (member != null) {
            record.setGrowTime(member.getGrowTime());
        }

        CadreView cv = CmTag.getCadreByUserId(userId);
        // 如果是现任领导干部，“所在单位、行政职务”两个字段已干部库为准
        if (cv != null && CadreConstants.CADRE_STATUS_NOW_SET.contains(cv.getStatus())) {
            record.setUnitId(cv.getUnitId());
            record.setUnit(cv.getUnitName());
            record.setPost(cv.getPost());
        }
    }

    // 批量导入
    @Transactional
    public int organizerImport(List<Organizer> records) throws InterruptedException {

        int addCount = 0;

        for (Organizer record : records) {

            Organizer organizer = get(record.getType(), record.getUserId());
            if(organizer!=null){
                record.setId(organizer.getId());
                updateByPrimaryKeySelective(record, false);
            }else {
                insertSelective(record);
            }
            addCount++;
        }
        return addCount;
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        Organizer entity = organizerMapper.selectByPrimaryKey(id);
        changeOrder("ow_organizer", String.format("type=%s and status=%s", entity.getType(), entity.getStatus()), ORDER_BY_DESC, id, addNum);
    }

    // 离任
    @Transactional
    public void leave(int id, Date dismissDate) {

        Organizer record = new Organizer();
        record.setId(id);
        record.setDismissDate(dismissDate);
        record.setStatus(OwConstants.OW_ORGANIZER_STATUS_LEAVE);

        organizerMapper.updateByPrimaryKeySelective(record);

        // 同时添加一条历任信息
        Organizer organizer = organizerMapper.selectByPrimaryKey(id);
        organizer.setId(null);
        organizer.setStatus(OwConstants.OW_ORGANIZER_STATUS_HISTORY);

        insertSelective(organizer);
    }

    // 离任组织员重新任用
    @Transactional
    public void reAppoint(int id, Date appointDate) {

        String _appointDate = DateUtils.formatDate(appointDate, DateUtils.YYYY_MM_DD);
        commonMapper.excuteSql("update ow_organizer set status=" + OwConstants.OW_ORGANIZER_STATUS_NOW
                + ", dismiss_date=null, appoint_date='" + _appointDate + "' where id=" + id);

        // 同步最新信息
        Organizer organizer = organizerMapper.selectByPrimaryKey(id);
        syncBaseInfo(organizer);
    }
}
