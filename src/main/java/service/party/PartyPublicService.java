package service.party;

import domain.member.MemberApply;
import domain.party.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.member.MemberApplyMapper;
import service.BaseMapper;
import service.member.MemberApplyService;
import shiro.ShiroHelper;
import sys.constants.OwConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import java.util.Date;
import java.util.List;

@Service
public class PartyPublicService extends BaseMapper {

    @Autowired(required = false)
    protected MemberApplyMapper memberApplyMapper;
    @Autowired(required = false)
    protected MemberApplyService memberApplyService;
    @Autowired(required = false)
    protected PartyMemberService partyMemberService;

    public PartyPublic get(int partyId, byte type, Date pubDate) {

        PartyPublicExample example = new PartyPublicExample();
        PartyPublicExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId)
                .andTypeEqualTo(type).andPubDateEqualTo(pubDate);

        List<PartyPublic> partyPublics = partyPublicMapper.selectByExample(example);

        return partyPublics.size() == 0 ? null : partyPublics.get(0);
    }

    public boolean idDuplicate(Integer id, int partyId, byte type, Date pubDate) {

        PartyPublicExample example = new PartyPublicExample();
        PartyPublicExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId)
                .andTypeEqualTo(type).andPubDateEqualTo(pubDate);
        if (id != null) criteria.andIdNotEqualTo(id);

        return partyPublicMapper.countByExample(example) > 0;
    }

    @Cacheable(value = "PartyPublic", key = "#id")
    public PartyPublic get(int id) {

        PartyPublic partyPublic = partyPublicMapper.selectByPrimaryKey(id);

        PartyPublicUserExample example = new PartyPublicUserExample();
        example.createCriteria().andPublicIdEqualTo(id);
        example.setOrderByClause("id asc");
        List<PartyPublicUser> partyPublicUsers = partyPublicUserMapper.selectByExample(example);
        partyPublic.setPublicUsers(partyPublicUsers);

        return partyPublic;
    }

    @Cacheable(value = "PartyPublics")
    public List<PartyPublic> getPartyPublics() {

        PartyPublicExample example = new PartyPublicExample();
        example.createCriteria().andIsPublishEqualTo(true);
        example.setOrderByClause("pub_date desc, id desc");

        return partyPublicMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 20));
    }

    @CacheEvict(value = "PartyPublics", allEntries = true)
    public void refreshPartyPublics() {
    }

    @Transactional
    @CacheEvict(value = "PartyPublic", key = "#record.id")
    public void insertSelective(PartyPublic record, Integer[] userIds) {

        Assert.isTrue(!idDuplicate(null, record.getPartyId(),
                record.getType(), record.getPubDate()), "duplicate");

        record.setNum(userIds.length);
        record.setPubUsers("," + StringUtils.join(userIds, ",") + ",");
        record.setPartyName(CmTag.getParty(record.getPartyId()).getName());
        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setCreateTime(new Date());
        record.setIp(ContextHelper.getRealIp());
        partyPublicMapper.insertSelective(record);

        insertUsers(record.getId(), record.getType(), userIds);
    }

    @Transactional
    @CacheEvict(value = "PartyPublic", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            PartyPublic partyPublic = partyPublicMapper.selectByPrimaryKey(id);

            // 权限控制
            Integer partyId = partyPublic.getPartyId();
            if (!partyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                throw new UnauthorizedException();
            }

            if (partyPublic.getType() == OwConstants.OW_PARTY_PUBLIC_TYPE_GROW) {
                commonMapper.excuteSql("update ow_member_apply set grow_public_id=null where grow_public_id=" + id);
            } else {
                commonMapper.excuteSql("update ow_member_apply set positive_public_id=null where positive_public_id=" + id);
            }

            partyPublicMapper.deleteByPrimaryKey(id);
        }
    }

    @Transactional
    @CacheEvict(value = "PartyPublic", key = "#record.id")
    public void update(PartyPublic record, Integer[] userIds) {

        int publicId = record.getId();
        Assert.isTrue(!idDuplicate(publicId, record.getPartyId(),
                record.getType(), record.getPubDate()), "duplicate");

        record.setNum(userIds.length);
        record.setPubUsers("," + StringUtils.join(userIds, ",") + ",");
        record.setPartyName(CmTag.getParty(record.getPartyId()).getName());
        record.setUpdateTime(new Date());
        partyPublicMapper.updateByPrimaryKeySelective(record);

        PartyPublicUserExample example = new PartyPublicUserExample();
        example.createCriteria().andPublicIdEqualTo(publicId);
        partyPublicUserMapper.deleteByExample(example);

        insertUsers(publicId, record.getType(), userIds);
    }

    // 更新非唯一键之外的属性
    @Transactional
    @CacheEvict(value = "PartyPublic", key = "#record.id")
    public void updateByPrimaryKeySelective(PartyPublic record) {

        record.setPartyId(null);
        record.setType(null);
        record.setPubDate(null);

        partyPublicMapper.updateByPrimaryKeySelective(record);
    }

    private void insertUsers(int publicId, byte type, Integer[] userIds) {

        if (type == OwConstants.OW_PARTY_PUBLIC_TYPE_GROW) {
            commonMapper.excuteSql("update ow_member_apply set grow_public_id=null where grow_public_id=" + publicId);
        } else {
            commonMapper.excuteSql("update ow_member_apply set positive_public_id=null where positive_public_id=" + publicId);
        }

        for (Integer userId : userIds) {

            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            PartyPublicUser _record = new PartyPublicUser();
            _record.setPublicId(publicId);

            _record.setUserId(userId);
            _record.setPartyId(memberApply.getPartyId());
            _record.setPartyName(CmTag.getParty(memberApply.getPartyId()).getName());
            if (memberApply.getBranchId() != null) {
                Branch branch = branchMapper.selectByPrimaryKey(memberApply.getBranchId());
                _record.setBranchId(branch.getId());
                _record.setBranchName(branch.getName());
            }

            partyPublicUserMapper.insertSelective(_record);

            MemberApply record = new MemberApply();
            record.setUserId(userId);
            if (type == OwConstants.OW_PARTY_PUBLIC_TYPE_GROW) {
                record.setGrowPublicId(publicId);
            } else {
                record.setPositivePublicId(publicId);
            }
            memberApplyService.updateByPrimaryKeySelective(record);
        }
    }
}
