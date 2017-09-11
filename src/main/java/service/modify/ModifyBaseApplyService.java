package service.modify;

import controller.global.OpException;
import domain.modify.ModifyBaseApply;
import domain.modify.ModifyBaseApplyExample;
import domain.modify.ModifyBaseItem;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import service.BaseMapper;
import service.SpringProps;
import service.sys.AvatarService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ModifyBaseApplyService extends BaseMapper {

    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected AvatarService avatarService;

    // 查找未完成审批的申请（只有一条）
    public ModifyBaseApply get(int userId){

        ModifyBaseApplyExample example = new ModifyBaseApplyExample();
        ModifyBaseApplyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);

        List<Byte> statusList = new ArrayList<>();
        statusList.add(SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY);
        statusList.add(SystemConstants.MODIFY_BASE_APPLY_STATUS_PART_CHECK);
        criteria.andStatusIn(statusList);

        List<ModifyBaseApply> records = modifyBaseApplyMapper.selectByExample(example);
        if(records.size()>0) return records.get(0);

        return null;
    }

    // 提交申请
    @Transactional
    public void apply(MultipartFile _avatar, // 头像是特殊的字段
                        String[] codes,  // 数据库字段代码
                        String[] tables,  // 数据库表名
                        String[] tableIdNames,  // 数据库表主键名
                        String[] names,  // 字段中文名
                        String[] originals,  // 原来的值
                        String[] modifys, // 更改的值
                        Byte[] types // 更改的值类型，表名不为空时有效
                        ) throws IOException {

        SysUserView uv = ShiroHelper.getCurrentUser();
        int applyUserId = uv.getId();
        int userId = uv.getId(); // 当前只允许本人申请

        String ip = IpUtils.getRealIp(ContextHelper.getRequest());
        Date now = new Date();
        ModifyBaseApply mba = new ModifyBaseApply();

        {// 提交申请记录
            ModifyBaseApplyExample example = new ModifyBaseApplyExample();
            example.createCriteria().andUserIdEqualTo(userId).
                    andStatusEqualTo(SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY);
            if (modifyBaseApplyMapper.countByExample(example) > 0) {
                throw new OpException("您已经提交了申请，请等待审核完成。");
            }

            mba.setApplyUserId(applyUserId);
            mba.setUserId(userId);
            mba.setCreateTime(now);
            mba.setIp(ip);
            mba.setStatus(SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY);

            modifyBaseApplyMapper.insertSelective(mba);
        }

        int modifyCount = 0;

        String avatar = avatarService.uploadAvatar(_avatar);
        String backupAvatar = null;

        if(StringUtils.isNotBlank(avatar)){ // 头像单独处理

            backupAvatar = avatarService.backupAvatar(userId);

            ModifyBaseItem mbi = new ModifyBaseItem();
            mbi.setApplyId(mba.getId());
            mbi.setCode("avatar");
            mbi.setTableName("sys_user_info");
            mbi.setTableIdName("user_id");
            mbi.setName("头像");
            mbi.setOrginalValue(backupAvatar);
            mbi.setModifyValue(avatar);
            mbi.setType(SystemConstants.MODIFY_BASE_ITEM_TYPE_IMAGE);
            mbi.setCreateTime(now);
            mbi.setIp(ip);
            mbi.setStatus(SystemConstants.MODIFY_BASE_ITEM_STATUS_APPLY);

            modifyBaseItemMapper.insertSelective(mbi);

            modifyCount++;
        }

        if(codes!=null){// 提交修改字段
            int count = codes.length;
            for(int i=0; i<count; i++){

                if(!StringUtils.equalsIgnoreCase(originals[i], modifys[i])){ // 存入有差别的字段
                    ModifyBaseItem mbi = new ModifyBaseItem();
                    mbi.setApplyId(mba.getId()); // 关联申请记录
                    mbi.setCode(codes[i]);
                    mbi.setTableName(tables[i]);
                    mbi.setTableIdName(tableIdNames[i]);
                    mbi.setName(names[i]);
                    mbi.setOrginalValue(originals[i]);
                    mbi.setModifyValue(modifys[i]);
                    mbi.setType(types[i]);
                    mbi.setCreateTime(now);
                    mbi.setIp(ip);
                    mbi.setStatus(SystemConstants.MODIFY_BASE_ITEM_STATUS_APPLY);

                    modifyBaseItemMapper.insertSelective(mbi);

                    modifyCount++;
                }
            }
        }

        if(modifyCount==0){
            throw new OpException("您没有任何修改了的字段，不需要提交。");
        }
    }

    // 本人撤销（真删除）
    @Transactional
    public void back(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        for (Integer id : ids) {

            ModifyBaseApply mba = modifyBaseApplyMapper.selectByPrimaryKey(id);
            if(mba.getUserId().intValue()!=currentUserId ||
                    mba.getStatus()!=SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY){
                throw new OpException(String.format("您没有权限撤销该记录[ID:%s]", id));
            }
        }

        ModifyBaseApplyExample example = new ModifyBaseApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids))
                .andStatusEqualTo(SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY); // 只有待审核时才可以删除
        modifyBaseApplyMapper.deleteByExample(example);
    }

    // 管理员删除（假删除）
    @Transactional
    public void fakeDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ModifyBaseApplyExample example = new ModifyBaseApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids))
                .andStatusEqualTo(SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY); // 只有待审核时才可以操作
        ModifyBaseApply record = new ModifyBaseApply();

        record.setStatus(SystemConstants.MODIFY_BASE_APPLY_STATUS_DELETE);
        modifyBaseApplyMapper.updateByExampleSelective(record, example);
    }


    // 管理员删除（真删除）
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ModifyBaseApplyExample example = new ModifyBaseApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        modifyBaseApplyMapper.deleteByExample(example);
    }
}
