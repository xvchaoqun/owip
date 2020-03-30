package service.dispatch;

import domain.base.MetaType;
import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileAuth;
import domain.dispatch.DispatchWorkFileAuthExample;
import domain.dispatch.DispatchWorkFileExample;
import org.apache.commons.lang.StringUtils;
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
        Set<Integer> postTypes = new HashSet<>();
        for (DispatchWorkFileAuth dispatchWorkFileAuth : dispatchWorkFileAuths) {
            postTypes.add(dispatchWorkFileAuth.getPostType());
        }

        return postTypes;
    }

    @Transactional
    public void updatePostTypes(int workFileId, Integer[] postTypes) {

        DispatchWorkFileAuthExample example = new DispatchWorkFileAuthExample();
        example.createCriteria().andWorkFileIdEqualTo(workFileId);
        dispatchWorkFileAuthMapper.deleteByExample(example);

        int postCount = 0;
        if(postTypes !=null) {
            for (Integer postType : postTypes) {

                DispatchWorkFileAuth record = new DispatchWorkFileAuth();
                record.setWorkFileId(workFileId);
                record.setPostType(postType);
                dispatchWorkFileAuthMapper.insert(record);
            }
            postCount = postTypes.length;
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
            int type = dwf.getType();
            int nextSortOrder = getNextSortOrder("dispatch_work_file", "status=0 and type=" + type);

            DispatchWorkFile record = new DispatchWorkFile();
            record.setId(id);
            record.setStatus(false);
            record.setSortOrder(nextSortOrder);
            dispatchWorkFileMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void batchTransfer(Integer[] ids, int type) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            DispatchWorkFile dwf = dispatchWorkFileMapper.selectByPrimaryKey(id);

            DispatchWorkFile record = new DispatchWorkFile();
            record.setId(id);
            record.setType(type);
            record.setSortOrder(getNextSortOrder("dispatch_work_file", "status="+dwf.getStatus()+" and type=" + type));

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

    @Transactional
    public void changeOrder(int id, int addNum) {

        DispatchWorkFile entity = dispatchWorkFileMapper.selectByPrimaryKey(id);
        int type = entity.getType();
        boolean status = entity.getStatus();
        changeOrder("dispatch_work_file", "status="+ status +" and type=" + type, ORDER_BY_DESC, id, addNum);
    }
}
