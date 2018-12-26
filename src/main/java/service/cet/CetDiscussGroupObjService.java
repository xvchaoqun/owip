package service.cet;

import domain.cet.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;

import java.util.*;

@Service
public class CetDiscussGroupObjService extends CetBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CetProjectObjService cetProjectObjService;

    public CetDiscussGroupObj getByDiscussId(Integer objId, int discussId) {

        CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
        example.createCriteria().andObjIdEqualTo(objId).andDiscussIdEqualTo(discussId);
        List<CetDiscussGroupObj> cetDiscussGroupObjs =
                cetDiscussGroupObjMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetDiscussGroupObjs.size()==1?cetDiscussGroupObjs.get(0):null;
    }

    public CetDiscussGroupObj getByDiscussGroupId(Integer objId, int discussGroupId) {

        CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
        example.createCriteria().andObjIdEqualTo(objId).andDiscussGroupIdEqualTo(discussGroupId);
        List<CetDiscussGroupObj> cetDiscussGroupObjs =
                cetDiscussGroupObjMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetDiscussGroupObjs.size()==1?cetDiscussGroupObjs.get(0):null;
    }

    @Transactional
    public void insertSelective(CetDiscussGroupObj record){

        cetDiscussGroupObjMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetDiscussGroupObjMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetDiscussGroupObjMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetDiscussGroupObj record){

        return cetDiscussGroupObjMapper.updateByPrimaryKeySelective(record);
    }

    // 导入分组/参会结果
    @Transactional
    public Map<String, Object>  imports(List<Map<Integer, String>> xlsRows, int discussGroupId, boolean isFinished) {

        CetDiscussGroup cetDiscussGroup = cetDiscussGroupMapper.selectByPrimaryKey(discussGroupId);
        int discussId = cetDiscussGroup.getDiscussId();
        CetProject cetProject = iCetMapper.getCetProjectOfDiscussGroup(discussGroupId);
        int projectId = cetProject.getId();
        int success = 0;
        List<Map<Integer, String>> failedXlsRows = new ArrayList<>();
        for (Map<Integer, String> xlsRow : xlsRows) {

            String code = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(code)) continue;
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null){
                failedXlsRows.add(xlsRow);
                continue;
            }
            int userId = uv.getId();
            CetProjectObj cetProjectObj = cetProjectObjService.get(userId, projectId);
            if (cetProjectObj == null){
                failedXlsRows.add(xlsRow);
                continue;
            }
            int objId = cetProjectObj.getId();

            CetDiscussGroupObj record = new CetDiscussGroupObj();
            record.setDiscussId(discussId);
            record.setDiscussGroupId(discussGroupId);
            record.setObjId(cetProjectObj.getId());
            record.setIsFinished(isFinished);

            CetDiscussGroupObj byDiscussId = getByDiscussId(objId, discussId);
            int ret = 0;
            if(byDiscussId!=null){
                record.setId(byDiscussId.getId());
                ret = cetDiscussGroupObjMapper.updateByPrimaryKeySelective(record);
            }else{
                ret = cetDiscussGroupObjMapper.insertSelective(record);
            }

            if(ret==1) {
                success++;
            }else{
                failedXlsRows.add(xlsRow);
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        resultMap.put("failedXlsRows", failedXlsRows);
        return resultMap;
    }
}
