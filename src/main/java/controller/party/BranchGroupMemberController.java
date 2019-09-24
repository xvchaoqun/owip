package controller.party;

import controller.BaseController;
import domain.party.BranchGroup;
import domain.party.BranchGroupMember;
import domain.party.BranchGroupMemberExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.party.BranchGroupMapper;
import persistence.party.BranchGroupMemberMapper;
import service.party.BranchGroupMemberService;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class BranchGroupMemberController extends BaseController {


    @Autowired
    private BranchGroupMemberMapper branchGroupMemberMapper;
    @Autowired
    private BranchGroupMemberService branchGroupMemberService;
    @Autowired
    private BranchGroupMapper branchGroupMapper;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("branchGroupMember:list")
    @RequestMapping("/branchGroupMember")
    public String branchGroupMember(Integer pageSize, Integer pageNo,Integer groupId,ModelMap modelMap) {

        if (groupId!=null){
            if (null == pageSize) {
                pageSize = 5;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            BranchGroupMemberExample branchGroupMemberExample = new BranchGroupMemberExample();

            branchGroupMemberExample.setOrderByClause("sort_order desc");

            branchGroupMemberExample.createCriteria().andGroupIdEqualTo(groupId);

            int count = (int)branchGroupMemberMapper.countByExample(branchGroupMemberExample);

            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }

            List<BranchGroupMember> branchGroupMembers = branchGroupMemberMapper.selectByExampleWithRowbounds(branchGroupMemberExample,new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("branchGroupMembers",branchGroupMembers);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (groupId!=null) {
                searchStr += "&groupId=" + groupId;
            }
            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }

        BranchGroup branchGroup = branchGroupMapper.selectByPrimaryKey(groupId);
        modelMap.put("branchGroup",branchGroup);
        return "party/branchGroupMember/branchGroupMember_page";
    }

    @RequiresPermissions("branchGroupMember:edit")
    @RequestMapping(value = "/branchGroupMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchGroupMember_au(BranchGroupMember record, HttpServletRequest request) {

        record.setIsLeader(BooleanUtils.isTrue(record.getIsLeader()));
        Integer id = record.getId();

        if (branchGroupMemberService.idDuplicate(id, record.getGroupId(),record.getUserId(),record.getIsLeader())) {
            return failed("添加重复");
        }

        if (id == null) {
            
            branchGroupMemberService.insertSelective(record);
            branchGroupMemberService.updateCountMenber(record.getGroupId());
            logger.info(log( LogConstants.LOG_PARTY, "添加党小组成员：{0}", record.getId()));
        } else {

            branchGroupMemberService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "更新党小组成员：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchGroupMember:del")
    @RequestMapping(value = "/branchGroupMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_branchGroupMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            BranchGroupMember branchGroupMember = branchGroupMemberMapper.selectByPrimaryKey(id);
            branchGroupMemberService.del(id);
            branchGroupMemberService.updateCountMenber(branchGroupMember.getGroupId());
            logger.info(log( LogConstants.LOG_PARTY, "删除党小组成员：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("branchGroupMember:edit")
    @RequestMapping(value = "/branchGroupMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leaderUnit_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        branchGroupMemberService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PARTY, "党小组成员调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/branchGroupMember_selects")
    @ResponseBody
    public Map branchGroupMember_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        BranchGroupMemberExample example = new BranchGroupMemberExample();
        BranchGroupMemberExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        long count = branchGroupMemberMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<BranchGroupMember> records = branchGroupMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(BranchGroupMember record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text","");
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
