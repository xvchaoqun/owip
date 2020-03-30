package service.sys;

import domain.sys.AttachFile;
import domain.sys.AttachFileExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class AttachFileService extends BaseMapper {

    public String genCode() {

        String prefix = "af";
        String code = "";
        int count = 0;
        do {
            code = prefix + "_" + RandomStringUtils.randomAlphanumeric(6).toLowerCase();
            AttachFileExample example = new AttachFileExample();
            example.createCriteria().andCodeEqualTo(code);
            count = (int) attachFileMapper.countByExample(example);
        } while (count > 0);
        return code;
    }

    public AttachFile get(String code) {

        if (StringUtils.isBlank(code)) return null;

        AttachFileExample example = new AttachFileExample();
        example.createCriteria().andCodeEqualTo(code);
        List<AttachFile> attachFiles = attachFileMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (attachFiles.size() > 0) ? attachFiles.get(0) : null;
    }

    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        AttachFileExample example = new AttachFileExample();
        AttachFileExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return attachFileMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(AttachFile record) {

        Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate code");

         record.setSortOrder(getNextSortOrder("sys_attach_file", null));
        return attachFileMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        attachFileMapper.deleteByPrimaryKey(id);
    }

    //删除系统附件
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            AttachFile attachFile = new AttachFile();
            attachFile.setId(id);
            attachFile.setIsDeleted(true);
            attachFile.setSortOrder(getNextSortOrder("sys_attach_file","is_deleted="+true));
            attachFileMapper.updateByPrimaryKeySelective(attachFile);
        }
    }

    //彻底删除系统附件
    @Transactional
    public void doBatchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        AttachFileExample example = new AttachFileExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsDeletedEqualTo(true);
        attachFileMapper.deleteByExample(example);
    }

    //返回列表
    @Transactional
    public void batchUnDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            AttachFile attachFile = new AttachFile();
            attachFile.setId(id);
            attachFile.setIsDeleted(false);
            attachFile.setSortOrder(getNextSortOrder("sys_attach_file","is_deleted="+false));
            attachFileMapper.updateByPrimaryKeySelective(attachFile);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(AttachFile record) {
        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate code");
        return attachFileMapper.updateByPrimaryKeySelective(record);
    }
    
    @Transactional
	public void changeOrder(int id, int addNum) {

        changeOrder("sys_attach_file", null, ORDER_BY_DESC, id, addNum);
	}
}
