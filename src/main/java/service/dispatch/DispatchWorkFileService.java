package service.dispatch;

import domain.base.MetaType;
import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileAuth;
import domain.dispatch.DispatchWorkFileAuthExample;
import domain.dispatch.DispatchWorkFileExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.base.MetaTypeService;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class DispatchWorkFileService extends BaseMapper {

    @Autowired
    protected MetaTypeService metaTypeService;

    public Set<Integer> getPostIds(int workFileId) {

        DispatchWorkFileAuthExample example = new DispatchWorkFileAuthExample();
        example.createCriteria().andWorkFileIdEqualTo(workFileId);

        List<DispatchWorkFileAuth> dispatchWorkFileAuths = dispatchWorkFileAuthMapper.selectByExample(example);
        Set<Integer> postIds = new HashSet<>();
        for (DispatchWorkFileAuth dispatchWorkFileAuth : dispatchWorkFileAuths) {
            postIds.add(dispatchWorkFileAuth.getPostId());
        }

        return postIds;
    }

    @Transactional
    public void updatePostIds(int workFileId, Integer[] postIds) {

        DispatchWorkFileAuthExample example = new DispatchWorkFileAuthExample();
        example.createCriteria().andWorkFileIdEqualTo(workFileId);
        dispatchWorkFileAuthMapper.deleteByExample(example);

        int postCount = 0;
        if(postIds!=null) {
            for (Integer postId : postIds) {

                DispatchWorkFileAuth record = new DispatchWorkFileAuth();
                record.setWorkFileId(workFileId);
                record.setPostId(postId);
                dispatchWorkFileAuthMapper.insert(record);
            }
            postCount = postIds.length;
        }

        // 更新已设置职务属性数量
        DispatchWorkFile record = new DispatchWorkFile();
        record.setId(workFileId);
        record.setPostCount(postCount);
        dispatchWorkFileMapper.updateByPrimaryKeySelective(record);
    }

    // 职务属性树结构
    public TreeNode getPostTree(Set<Integer> selectIdSet) {

        if (null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "职务属性";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");

        for (MetaType post : postMap.values()) {

            TreeNode node = new TreeNode();
            node.title = post.getName();
            node.key = post.getId() + "";

            if (selectIdSet.contains(post.getId())) {
                node.select = true;
            }
            rootChildren.add(node);
        }

        return root;
    }

    public boolean idDuplicate(Integer id, String code) {

        // 可以为空？
        if (StringUtils.isBlank(code)) return false;

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        DispatchWorkFileExample.Criteria criteria = example.createCriteria()
                .andCodeEqualTo(code.trim()).andStatusEqualTo(true);
        if (id != null) criteria.andIdNotEqualTo(id);

        return dispatchWorkFileMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DispatchWorkFile record) {

        record.setStatus(true);
        record.setCreateTime(new Date());
        Assert.isTrue(!idDuplicate(null, record.getCode()), "发文号重复");
        record.setSortOrder(getNextSortOrder("dispatch_work_file", "status=1 and type=" + record.getType()));
        dispatchWorkFileMapper.insertSelective(record);
    }

    @Transactional
    public void abolish(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            DispatchWorkFile dwf = dispatchWorkFileMapper.selectByPrimaryKey(id);
            Byte type = dwf.getType();
            int nextSortOrder = getNextSortOrder("dispatch_work_file", "status=0 and type=" + type);

            DispatchWorkFile record = new DispatchWorkFile();
            record.setId(id);
            record.setStatus(false);
            record.setSortOrder(nextSortOrder);
            dispatchWorkFileMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dispatchWorkFileMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DispatchWorkFile record, boolean canUpload) {

        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "发文号重复");
        dispatchWorkFileMapper.updateByPrimaryKeySelective(record);

        if(!canUpload){
            commonMapper.excuteSql("update dispatch_work_file set pdf_file_path=null, word_file_path=null where id=" + record.getId());
        }
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        DispatchWorkFile entity = dispatchWorkFileMapper.selectByPrimaryKey(id);

        Integer baseSortOrder = entity.getSortOrder();
        Byte type = entity.getType();
        Boolean status = entity.getStatus();

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(status).andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andStatusEqualTo(status).andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DispatchWorkFile> overEntities = dispatchWorkFileMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            DispatchWorkFile targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("dispatch_work_file", "status="+ status +" and type=" + type, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dispatch_work_file", "status="+ status +" and type=" + type, baseSortOrder, targetEntity.getSortOrder());

            DispatchWorkFile record = new DispatchWorkFile();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dispatchWorkFileMapper.updateByPrimaryKeySelective(record);
        }
    }
}
