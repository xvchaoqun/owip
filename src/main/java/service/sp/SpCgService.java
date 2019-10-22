package service.sp;

import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.cg.CgMember;
import domain.cg.CgMemberExample;
import domain.sp.SpCg;
import domain.sp.SpCgExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.cg.CgMemberMapper;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpCgService extends SpBaseMapper {

    @Autowired
    private CgMemberMapper cgMemberMapper;

    public boolean idDuplicate(Integer id, Byte type,Integer userId){

        SpCgExample example = new SpCgExample();
        SpCgExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type).andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return spCgMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(SpCg record){

        record.setSortOrder(getNextSortOrder("sp_cg", "type="+record.getType()));
        spCgMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        spCgMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SpCgExample example = new SpCgExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        spCgMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SpCg record){
        /*if(StringUtils.isNotBlank(record.getPost()))
            Assert.isTrue(!idDuplicate(record.getId(),null, "duplicate");*/
        spCgMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, SpCg> findAll() {

        SpCgExample example = new SpCgExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<SpCg> records = spCgMapper.selectByExample(example);
        Map<Integer, SpCg> map = new LinkedHashMap<>();
        for (SpCg record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        SpCg spCg = spCgMapper.selectByPrimaryKey(id);

        changeOrder("sp_cg", "type="+spCg.getType(), ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateRecord(SpCg spCg){

        spCg.setIsCadre(false);
        CadreView cadre = CmTag.getCadreByUserId(spCg.getUserId());

        if (cadre !=null){

            spCg.setIsCadre(isCurrentCadre(cadre.getStatus()));
            spCg.setAdminPost(cadre.getTitle());
        }
    }

    @Transactional
    public void spCg_relevance(SpCg record){

        List<CgMember> cgMembers = getCgMemberByTeamId(record.getCgTeamId());

        Map<Integer, MetaType> metaTypeMap = CmTag.getMetaTypes("mc_cg_staff");

        for (CgMember cgMember : cgMembers){

            if (cgMember.getUserId()==null) continue;

            MetaType metaType = metaTypeMap.get(cgMember.getPost());

            SpCg spCg = new SpCg();
            spCg.setCgTeamId(cgMember.getTeamId());
            spCg.setUserId(cgMember.getUserId());
            spCg.setType(record.getType());
            spCg.setPost(metaType.getName());
            spCg.setSeat(cgMember.getSeat());
            updateRecord(spCg);

            if (idDuplicate(spCg.getId(),spCg.getType(),spCg.getUserId()))
                continue;

            insertSelective(spCg);
        }
    }

    //得到关联委员会所有成员
    public List<CgMember> getCgMemberByTeamId(Integer cgTeamId){

        CgMemberExample cgMemberExample = new CgMemberExample();
        cgMemberExample.createCriteria()
                .andTeamIdEqualTo(cgTeamId)
                .andIsCurrentEqualTo(true);
        return cgMemberMapper.selectByExample(cgMemberExample);
    }
}
