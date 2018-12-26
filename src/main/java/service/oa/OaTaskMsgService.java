package service.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.base.ShortMsgService;
import service.sys.SysUserService;

@Service
public class OaTaskMsgService extends OaBaseMapper {

    @Autowired
    private OaTaskUserService oaTaskUserService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ShortMsgService shortMsgService;

   /* @Transactional
    public void insertSelective(OaTaskMsg record) {

        oaTaskMsgMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        oaTaskMsgMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        OaTaskMsgExample example = new OaTaskMsgExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        oaTaskMsgMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(OaTaskMsg record) {
        return oaTaskMsgMapper.updateByPrimaryKeySelective(record);
    }*/
}
