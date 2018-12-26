package service.cet;

import bean.XlsTrainCourse;
import controller.global.OpException;
import domain.cet.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CetTrainCourseService extends CetBaseMapper {

    @Autowired
    private CetCourseService cetCourseService;

    public CetTrainCourse get(int trainId, int courseId) {

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId).andCourseIdEqualTo(courseId);
        List<CetTrainCourse> cetTrainCourses = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (cetTrainCourses.size() > 0) ? cetTrainCourses.get(0) : null;
    }

    @Transactional
    @CacheEvict(value = "CetTrainCourses", key = "#record.trainId")
    public void insertSelective(CetTrainCourse record) {

        record.setSortOrder(getNextSortOrder("cet_train_course", "train_id=" + record.getTrainId()));
        cetTrainCourseMapper.insertSelective(record);
    }


    @Transactional
    @CacheEvict(value = "CetTrainCourses", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        cetTrainCourseMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value = "CetTrainCourses", allEntries = true)
    public void updateByPrimaryKeySelective(CetTrainCourse record) {

        record.setTrainId(null);
        cetTrainCourseMapper.updateByPrimaryKeySelective(record);
        if(record.getApplyLimit()==null){
            commonMapper.excuteSql("update cet_train_course set apply_limit=null where id=" + record.getId());
        }
    }

    @Cacheable(value = "CetTrainCourses", key = "#trainId")
    public Map<Integer, CetTrainCourse> findAll(int trainId) {

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId);
        example.setOrderByClause("sort_order asc");
        List<CetTrainCourse> trainCoursees = cetTrainCourseMapper.selectByExample(example);
        Map<Integer, CetTrainCourse> map = new LinkedHashMap<>();
        for (CetTrainCourse trainCourse : trainCoursees) {
            map.put(trainCourse.getId(), trainCourse);
        }

        return map;
    }

    @Transactional
    public void batchAddFile(List<CetTrainCourseFile> records) {

        for (CetTrainCourseFile record : records) {
            //record.setSortOrder(getNextSortOrder("sc_group_file", null));
            cetTrainCourseFileMapper.insertSelective(record);
        }
    }

    @Transactional
    public void delFile(Integer fileId) {

        cetTrainCourseFileMapper.deleteByPrimaryKey(fileId);
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetTrainCourses", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;
        byte orderBy = ORDER_BY_ASC;
        CetTrainCourse entity = cetTrainCourseMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer trainId = entity.getTrainId();

        CetTrainCourseExample example = new CetTrainCourseExample();
        if (addNum * orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder)
                    .andTrainIdEqualTo(trainId);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder)
                    .andTrainIdEqualTo(trainId);
            example.setOrderByClause("sort_order desc");
        }

        List<CetTrainCourse> overEntities = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CetTrainCourse targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum * orderBy > 0)
                commonMapper.downOrder("cet_train_course", "train_id=" + trainId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_train_course", "train_id=" + trainId, baseSortOrder, targetEntity.getSortOrder());

            CetTrainCourse record = new CetTrainCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetTrainCourseMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 添加课程
    @Transactional
    public void selectCourses(int trainId, Integer[] courseIds) {

        if (courseIds == null || courseIds.length == 0) return;

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        Boolean isOnCampus = cetTrain.getIsOnCampus();
        for (Integer courseId : courseIds) {

            CetTrainCourse cetTrainCourse = get(trainId, courseId);
            if (cetTrainCourse != null) continue;

            CetTrainCourse record = new CetTrainCourse();
            record.setTrainId(trainId);
            record.setCourseId(courseId);
            if (!isOnCampus) {
                // 校外培训,同步课程名称和专家姓名
                CetCourse cetCourse = cetCourseService.get(courseId);
                record.setName(cetCourse.getName());
                record.setTeacher(cetCourse.getCetExpert().getRealname());
            }
            record.setSortOrder(getNextSortOrder("cet_train_course", "train_id=" + record.getTrainId()));
            cetTrainCourseMapper.insertSelective(record);
        }
    }

    // 关联评估表
    @Transactional
    @CacheEvict(value = "CetTrainCourses", key = "#trainId")
    public void evaTable(int trainId, Integer[] ids, int evaTableId) {

        if (ids.length == 0) return;
        for (Integer id : ids) {
            CetTrainCourse trainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
            if (trainCourse.getTrainId().intValue() == trainId
                    && (trainCourse.getEvaTableId() == null || trainCourse.getEvaTableId().intValue() != evaTableId)) {

                {
                    CetTrainEvaResultExample example = new CetTrainEvaResultExample();
                    example.createCriteria().andTrainCourseIdEqualTo(id);
                    if (cetTrainEvaResultMapper.countByExample(example) > 0) {
                        throw new OpException(String.format("课程[%s]已经产生了评估结果，不可以修改评估表", trainCourse.getName()));
                    }
                }

                CetTrainCourse record = new CetTrainCourse();
                record.setId(id);
                record.setEvaTableId(evaTableId);
                cetTrainCourseMapper.updateByPrimaryKeySelective(record);
            }
        }
    }

    // 0：评课进行中 1:已关闭评课 2：评课未开始（未上课） 3：评课已结束
    public int evaIsClosed(int courseId) {

        CetTrainCourse trainCourse = cetTrainCourseMapper.selectByPrimaryKey(courseId);
        CetTrain train = cetTrainMapper.selectByPrimaryKey(trainCourse.getTrainId());
        if (BooleanUtils.isTrue(train.getEvaClosed())) {
            return 1;
        }

        Date now = new Date();
        Date openTime = trainCourse.getStartTime();
        Date closeTime = train.getEvaCloseTime();

        if (openTime != null && now.before(openTime)) {
            return 2;
        }
        if (closeTime != null && now.after(closeTime)) {
            return 3;
        }

        return 0;
    }


    @Transactional
    @CacheEvict(value = "CetTrainCourses", key = "#trainId")
    public int imports(final List<XlsTrainCourse> beans, int trainId) {

        int success = 0;
        for (XlsTrainCourse uRow : beans) {

            CetTrainCourse record = new CetTrainCourse();
            record.setName(uRow.getName());
            record.setTeacher(uRow.getTeacher());
            record.setStartTime(uRow.getStartTime());
            record.setEndTime(uRow.getEndTime());
            record.setTrainId(trainId);

            insertSelective(record);
            success++;
        }

        return success;
    }

    // 获取课程参训人（已选课学员） <userId, CetTraineeCourseView>
    public Map<Integer, CetTraineeCourseView> findTrainees(int trainCourseId) {

        CetTraineeCourseViewExample example = new CetTraineeCourseViewExample();
        CetTraineeCourseViewExample.Criteria criteria = example.createCriteria().andTrainCourseIdEqualTo(trainCourseId);

        Map<Integer, CetTraineeCourseView> resultMap = new HashMap<>();
        List<CetTraineeCourseView> cetTraineeCourseViews = cetTraineeCourseViewMapper.selectByExample(example);
        for (CetTraineeCourseView cetTraineeCourseView : cetTraineeCourseViews) {

            resultMap.put(cetTraineeCourseView.getUserId(), cetTraineeCourseView);
        }

        return resultMap;
    }

    // key: userId
    /*public TreeNode selectObjs_tree(int trainCourseId) {

        // 已选课参训人员（包含本人选课的）
        Map<Integer, CetTraineeCourseView> trainees = findTrainees(trainCourseId);
        Set<Integer> selectedUserIdSet = trainees.keySet();

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        CetProject cetProject = iCetMapper.getCetProject(cetTrainCourse.getTrainId());
        int projectId = cetProject.getId();

        TreeNode root = new TreeNode();
        root.title = "培训对象";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        // 培训对象的参训人员类型
        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(projectId);
        for (CetTraineeType cetTraineeType : cetTraineeTypes) {
            int traineeTypeId = cetTraineeType.getId();
            // 培训对象列表
            List<CetProjectObj> cetProjectObjs = cetProjectObjService.cetProjectObjList(projectId, traineeTypeId);

            TreeNode titleNode = new TreeNode();
            titleNode.expand = false;
            titleNode.isFolder = true;
            List<TreeNode> titleChildren = new ArrayList<TreeNode>();
            titleNode.children = titleChildren;

            int selectCount = 0;
            for (CetProjectObj cetProjectObj : cetProjectObjs) {

                int userId = cetProjectObj.getUserId();
                SysUserView uv = sysUserService.findById(userId);
                TreeNode node = new TreeNode();
                node.title = uv.getRealname() + ("-" + uv.getCode());
                node.key =  userId + "";

                if(selectedUserIdSet.contains(userId)){

                    CetTraineeCourseView ctee = trainees.get(userId);
                    if(ctee.getCanQuit()){
                        node.addClass = "self"; // 本人已选课的
                    }else{
                        node.select = true;
                        selectCount++;
                    }
                    // 已签到，不允许操作了
                    if(ctee.getIsFinished()){
                        node.unselectable = true;
                    }
                }

                titleChildren.add(node);
            }

            titleNode.title = cetTraineeType.getName()
                    + String.format("(%s", selectCount > 0 ? selectCount + "/" : "") + cetProjectObjs.size() + "人)";
            rootChildren.add(titleNode);
        }

        return root;
    }

    // 管理员设置课程参训人
    @Transactional
    public void selectObjs(int trainCourseId, Integer[] userIds) {

        // 已选课参训人员（包含本人选课的）
        Map<Integer, CetTraineeCourseView> trainees = findTrainees(trainCourseId);
        // 本次待删除的参训人
        Set<Integer> removeUserIdSet = trainees.keySet();
        if(userIds!=null && userIds.length>0) {
            removeUserIdSet.removeAll(Arrays.asList(userIds));
        }
        for (int userId : removeUserIdSet) {

            CetTraineeCourseView ctc = trainees.get(userId);
            if(ctc.getCanQuit()){
                // 本人选课，不处理
                continue;
            }else{
                if(ctc.getIsFinished()){
                    SysUserView uv = sysUserService.findById(userId);
                    throw new OpException("参训人{0}已上课签到，不可删除。", uv.getRealname());
                }
                // 退课
                cetTraineeCourseService.applyItem(userId, trainCourseId, false, true, "退课");
            }
        }

        if(userIds!=null && userIds.length>0) {

            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
            int trainId = cetTrainCourse.getTrainId();
            CetProject cetProject = iCetMapper.getCetProject(trainId);
            int projectId = cetProject.getId();

            for (Integer userId : userIds) {

                // 如果还没选过课，则先创建参训人
                int traineeId;
                CetTraineeView cetTrainee = cetTraineeService.get(userId, trainId);
                if (cetTrainee != null) {
                    traineeId = cetTrainee.getId();
                } else {
                    CetProjectObj cetProjectObj = cetProjectObjService.get(userId, projectId);
                    CetTrainee record = new CetTrainee();
                    record.setObjId(cetProjectObj.getId());
                    record.setIsQuit(false);
                    record.setTrainId(trainId);
                    cetTraineeMapper.insertSelective(record);
                    traineeId = record.getId();
                }

                CetTraineeCourseView ctc = trainees.get(userId);
                if (ctc == null) {
                    // 选课
                    cetTraineeCourseService.applyItem(userId, trainCourseId, true, true, "必选课");
                } else {
                    Boolean canQuit = ctc.getCanQuit();
                    if (canQuit) {
                        // 本人已选课，修改为必选课
                        CetTraineeCourse record = new CetTraineeCourse();
                        record.setId(ctc.getId());
                        record.setCanQuit(false);
                        cetTraineeCourseMapper.updateByPrimaryKeySelective(record);

                        sysApprovalLogService.add(traineeId, userId,
                                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                                "修改为必选课", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                                cetTrainCourse.getCetCourse().getName());
                    }
                }

            }
        }
    }*/
}
