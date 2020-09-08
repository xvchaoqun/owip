package service.cet;

import controller.global.OpException;
import domain.cet.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CetPlanCourseObjResultService extends CetBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CetCourseItemService cetCourseItemService;
    @Autowired
    private CetPlanCourseObjService cetPlanCourseObjService;

    public CetPlanCourseObjResult get(int planCourseObjId, int courseItemId) {

        CetPlanCourseObjResultExample example = new CetPlanCourseObjResultExample();
        example.createCriteria().andPlanCourseObjIdEqualTo(planCourseObjId)
                .andCourseItemIdEqualTo(courseItemId);
        List<CetPlanCourseObjResult> cetPlanCourseObjResults = cetPlanCourseObjResultMapper.selectByExample(example);
        return (cetPlanCourseObjResults.size() > 0) ? cetPlanCourseObjResults.get(0) : null;
    }

    // 导入结果
    @Transactional
    public int imports(List<Map<Integer, String>> xlsRows, int planCourseId) {

        CetPlanCourse cetPlanCourse = cetPlanCourseMapper.selectByPrimaryKey(planCourseId);
        if (cetPlanCourse == null) {
            throw new OpException("导入失败，该课程不存在。");
        }
        int courseId = cetPlanCourse.getCourseId();
        Map<Integer, CetCourseItem> cetCourseItemMap = cetCourseItemService.findAll(courseId);
        List<CetCourseItem> cetCourseItems = new ArrayList<>(cetCourseItemMap.values());
        int courseItemSize = cetCourseItems.size();
        if (courseItemSize == 0) {
            throw new OpException("导入失败，该课程还没有设置专题班。");
        }

        int success = 0;
        for (int i = 0; i < xlsRows.size(); i++) {

            Map<Integer, String> xlsRow = xlsRows.get(i);
            int columnSize = xlsRow.size();
            if (columnSize < courseItemSize * 2 + 5) {
                throw new OpException("导入失败，Excel文件有误，请严格按照录入样表的表头准备数据（根据实际的专题班数量进行修改）");
            }

            String code = xlsRow.get(0); // 第一列是工作证号
            if (StringUtils.isBlank(code)) {
                continue;
                //throw new OpException("导入失败，第{}行的工作证号为空。", (i+1));
            }
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null) {
                continue;
                //throw new OpException("导入失败，第{}行的学员在系统中不存在。", (i+1));
            }

            CetPlanCourseObj cetPlanCourseObj = cetPlanCourseObjService.getByUserId(uv.getId(), planCourseId);
            if (cetPlanCourseObj == null) {
                continue;
                //throw new OpException("导入失败，第{}行的学员没有选择此课程。", (i+1));
            }

            int planCourseObjId = cetPlanCourseObj.getId();
            // 先清空成绩，再导入
            delResult(planCourseObjId);

            String period = xlsRow.get(2); // 第2列是完成总学时数

            /*String courseNum_1 = xlsRow.get(3); // 第四列是专题班1完成课程数
            String period_1 = xlsRow.get(4); // 第五列是专题班1完成学时数

            String courseNum_2 = xlsRow.get(5); // 第六列是专题班2完成课程数
            String period_2 = xlsRow.get(6); // 第七列是专题班2完成学时数*/

            String _num = xlsRow.get(3 + (courseItemSize * 2)); // 第八列是提交学习心得数
            String _isFinished = xlsRow.get(4 + (courseItemSize * 2)); // 第九列是是否结业

            if (StringUtils.isNotBlank(_num) && !NumberUtils.isDigits(_num)) {
                throw new OpException("导入失败，第{0}行的学员的学习心得数有误。", (i + 1));
            }
            int num = StringUtils.isBlank(_num) ? 0 : Integer.valueOf(_num);
            boolean isFinished = StringUtils.equals(_isFinished, "是");

            {
                CetPlanCourseObj record = new CetPlanCourseObj();
                record.setId(planCourseObjId);
                record.setNum(num);
                record.setIsFinished(isFinished);
                if(StringUtils.isNotBlank(period)) {
                    record.setPeriod(new BigDecimal(period));
                }

                cetPlanCourseObjMapper.updateByPrimaryKeySelective(record);
            }

            for (int j = 0; j < cetCourseItems.size(); j++) {

                CetCourseItem cetCourseItem = cetCourseItems.get(j);
                Integer courseItemId = cetCourseItem.getId();

                String _courseNum = xlsRow.get(3 + j * 2);
                String _period = xlsRow.get(4 + j * 2);

                if (StringUtils.isNotBlank(_courseNum) && !NumberUtils.isDigits(_courseNum)) {
                    throw new OpException("导入失败，第{0}行的学员的课程数有误。", (i + 1));
                }
                if (StringUtils.isNotBlank(_period) && !NumberUtils.isCreatable(_period)) {
                    throw new OpException("导入失败，第{0}行的学员的学时数有误。", (i + 1));
                }


                CetPlanCourseObjResult cetPlanCourseObjResult = get(planCourseObjId, courseItemId);
                CetPlanCourseObjResult record = new CetPlanCourseObjResult();
                record.setPlanCourseObjId(planCourseObjId);
                record.setCourseItemId(courseItemId);
                if(StringUtils.isNotBlank(_courseNum)) {
                    record.setCourseNum(Integer.valueOf(_courseNum));
                }
                if(StringUtils.isNotBlank(_period)) {
                    record.setPeriod(new BigDecimal(_period));
                }

                if(cetPlanCourseObjResult==null) {
                    cetPlanCourseObjResultMapper.insertSelective(record);
                }else{
                    record.setId(cetPlanCourseObjResult.getId());
                    cetPlanCourseObjResultMapper.updateByPrimaryKeySelective(record);
                }
            }
            success++;
        }

        return success;
    }

    public boolean idDuplicate(Integer id, int planCourseObjId, int courseItemId) {

        CetPlanCourseObjResultExample example = new CetPlanCourseObjResultExample();
        CetPlanCourseObjResultExample.Criteria criteria = example.createCriteria()
                .andPlanCourseObjIdEqualTo(planCourseObjId).andCourseItemIdEqualTo(courseItemId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return cetPlanCourseObjResultMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetPlanCourseObjResult record) {

        cetPlanCourseObjResultMapper.insertSelective(record);
    }

    // 清除某个学员的成绩
    @Transactional
    public void delResult(int planCourseObjId) {

        CetPlanCourseObjResultExample example = new CetPlanCourseObjResultExample();
        example.createCriteria().andPlanCourseObjIdEqualTo(planCourseObjId);
        cetPlanCourseObjResultMapper.deleteByExample(example);

        commonMapper.excuteSql("update cet_plan_course_obj set num=null, is_finished=null where id=" + planCourseObjId);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetPlanCourseObjResult record) {
        return cetPlanCourseObjResultMapper.updateByPrimaryKeySelective(record);
    }

    // 清除学员的成绩
    @Transactional
    public void clear(int planCourseId, Integer[] objIds) {

        for (Integer objId : objIds) {

            CetPlanCourseObj cetPlanCourseObj = cetPlanCourseObjService.get(objId, planCourseId);
            if (cetPlanCourseObj == null) continue;

            delResult(cetPlanCourseObj.getId());
        }
    }

    // 添加学员成绩
    @Transactional
    public void add(CetPlanCourseObj record, HttpServletRequest request) {

        int planCourseId = record.getPlanCourseId();
        int objId = record.getObjId();
        CetPlanCourseObj cetPlanCourseObj = cetPlanCourseObjService.get(objId, planCourseId);
        if (cetPlanCourseObj == null) return;

        CetPlanCourse cetPlanCourse = cetPlanCourseMapper.selectByPrimaryKey(planCourseId);
        if (cetPlanCourse == null) {
            throw new OpException("导入失败，该课程不存在。");
        }
        int courseId = cetPlanCourse.getCourseId();
        Integer cetPlanCourseObjId = cetPlanCourseObj.getId();
        record.setId(cetPlanCourseObjId);
        cetPlanCourseObjMapper.updateByPrimaryKeySelective(record);

        // 清除结果
        //delResult(cetPlanCourseObjId);

        Map<Integer, CetCourseItem> cetCourseItemMap = cetCourseItemService.findAll(courseId);
        List<CetCourseItem> cetCourseItems = new ArrayList<>(cetCourseItemMap.values());

        for (CetCourseItem cetCourseItem : cetCourseItems) {

            Integer cetCourseItemId = cetCourseItem.getId();
            String _courseNum = request.getParameter("courseNum_" + cetCourseItemId);
            String _period = request.getParameter("period_" + cetCourseItemId);

            if (!NumberUtils.isDigits(_courseNum)) {
                throw new OpException("课程数有误。");
            }
            if (!NumberUtils.isCreatable(_period)) {
                throw new OpException("学时数有误。");
            }

            CetPlanCourseObjResult cetPlanCourseObjResult = get(cetPlanCourseObjId, cetCourseItemId);
            CetPlanCourseObjResult _record = new CetPlanCourseObjResult();
            _record.setPlanCourseObjId(cetPlanCourseObjId);
            _record.setCourseItemId(cetCourseItemId);
            _record.setCourseNum(Integer.valueOf(_courseNum));
            _record.setPeriod(new BigDecimal(_period));

            if(cetPlanCourseObjResult==null) {
                cetPlanCourseObjResultMapper.insertSelective(_record);
            }else{
                _record.setId(cetPlanCourseObjResult.getId());
                cetPlanCourseObjResultMapper.updateByPrimaryKeySelective(_record);
            }
        }
    }
}
