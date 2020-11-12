package service.pm;

import controller.global.OpException;
import domain.pm.Pm3Guide;
import domain.pm.Pm3GuideExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.*;

@Service
public class Pm3GuideService extends PmBaseMapper {

    @Transactional
    public void insertSelective(Pm3Guide record){
        pm3GuideMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        pm3GuideMapper.deleteByPrimaryKey(id);
    }

    public void isDuplicate(String meetingMonth) {

        Pm3GuideExample example = new Pm3GuideExample();
        example.createCriteria().andMeetingMonthEqualTo(DateUtils.parseDate(meetingMonth, DateUtils.YYYY_MM));
        List<Pm3Guide> pm3GuideList = pm3GuideMapper.selectByExample(example);
        if (pm3GuideList!=null&&pm3GuideList.size()>0)
            throw new OpException("添加重复");
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        Pm3GuideExample example = new Pm3GuideExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pm3GuideMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(Pm3Guide record){
        pm3GuideMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, Pm3Guide> findAll() {

        Pm3GuideExample example = new Pm3GuideExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<Pm3Guide> records = pm3GuideMapper.selectByExample(example);
        Map<Integer, Pm3Guide> map = new LinkedHashMap<>();
        for (Pm3Guide record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void delFile(Integer id, String filePath) {

        Pm3Guide record = pm3GuideMapper.selectByPrimaryKey(id);

        // 权限校验
        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)){
            throw new OpException("权限不足");
        }

        if (StringUtils.isNotBlank(record.getGuideFilenames()) && record.getGuideFiles().contains(filePath)) {
            List<String> pathList = new ArrayList<String>(Arrays.asList(StringUtils.split(record.getGuideFiles(), ";")));
            List<String> nameList = new ArrayList<String>(Arrays.asList(StringUtils.split(record.getGuideFilenames(), ";")));
            int index = pathList.indexOf(filePath);
            pathList.remove(index);
            nameList.remove(index);
            if(pathList.size() == 0){
                commonMapper.excuteSql("update pm3_guide set guide_files=null,guide_filenames=null where id="+id);
            }else{
                record.setGuideFilenames(StringUtils.join(nameList, ";"));
                record.setGuideFiles(StringUtils.join(pathList, ";"));
                pm3GuideMapper.updateByPrimaryKeySelective(record);
            }
        }
    }
}
