package controller.dr;

import controller.global.OpException;
import domain.dr.DrOnlineCandidate;
import domain.dr.DrOnlineCandidateExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/dr")
@Controller
public class DrOnlineCandidateController extends DrBaseController {

    @RequiresPermissions("drOnlinePost:edit")
    @RequestMapping("/drOnlineCandidate_page")
    public String drOnlineCandidate_page(Integer postId, Integer pageSize, Integer pageNo, ModelMap modelMap){

        if (postId != null) {

            modelMap.put("postId", postId);

            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            DrOnlineCandidateExample example = new DrOnlineCandidateExample();
            DrOnlineCandidateExample.Criteria criteria = example.createCriteria().andPostIdEqualTo(postId);
            example.setOrderByClause(" sort_order desc");

            long count = drOnlineCandidateMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<DrOnlineCandidate> candidates = drOnlineCandidateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("candidates", candidates);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }

        return "/dr/drOnline/drOnlineCandidate/drOnlineCandidate_page";
    }

    @RequiresPermissions("drOnlinePost:edit")
    @RequestMapping("/drOnlineCandidate_au")
    public String drOnlineCandidate_au(Integer id, ModelMap modelMap){

        if (null != id) {
            DrOnlineCandidate drOnlineCandidate = drOnlineCandidateMapper.selectByPrimaryKey(id);
            modelMap.put("drOnlineCandidate", drOnlineCandidate);
        }

        return "/dr/drOnline/drOnlineCandidate/drOnlineCandidate_au";
    }

    @RequiresPermissions("drOnlinePost:edit")
    @RequestMapping(value = "/drOnlineCandidate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlineCandidate_au(DrOnlineCandidate record, HttpServletRequest request){

        Integer id = record.getId();
        Integer postId = record.getPostId();

        if (id == null){

            if (!drOnlinePostService.checkCandidateNum(postId))
                throw new OpException("已达最多推荐人数");
            if (drOnlineCandidateService.checkDuplicate(record.getUserId(), record.getPostId()))
                throw new OpException("添加重复");

            drOnlineCandidateService.insert(postId, record.getUserId());
            logger.info(addLog(LogConstants.LOG_DR, "管理员添加候选人：%s"));
        }else {
            drOnlineCandidateService.update(record);

        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlinePost:edit")
    @RequestMapping(value = "/drOnlineCandidate_del", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnlineCandidate_del(Integer id){

        if (null != id){

            //防止中途修改数据
            /*DrOnlineCandidate record = drOnlineCandidateMapper.selectByPrimaryKey(id);
            DrOnline drOnline = drOnlinePostService.getPost(record.getPostId()).getDrOnline();
            if (drOnline.getStatus() == DrConstants.DR_ONLINE_FINISH ||drOnline.getStatus() == DrConstants.DR_ONLINE_RELEASE)
                throw new OpException("民主推荐进行中或已完成，不可修改数据");*/

            drOnlineCandidateService.del(id);
        }

        return success(FormUtils.SUCCESS);
    }

    //管理员编辑候选人
    @RequestMapping("/dr_sysUser_selects")
    @ResponseBody
    public Map dr_sysUser_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        Byte[] types = {SystemConstants.USER_TYPE_JZG};

        searchStr = StringUtils.trimToNull(searchStr);
        long count = iSysMapper.countUserList(searchStr, types);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = iSysMapper.selectUserList(searchStr, types, new RowBounds((pageNo - 1) * pageSize, pageSize));


        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != uvs && uvs.size() > 0) {
            for (SysUserView uv : uvs) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", uv.getId() + "");
                option.put("text", uv.getRealname());
                option.put("code", uv.getCode());
                option.put("realname", uv.getRealname());
                option.put("gender", uv.getGender());
                option.put("nation", uv.getNation());

                //option.put("user", userBeanService.get(uv.getId()));
                options.add(option);
            }
        }
        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    //候选人排序
    @RequiresPermissions("drOnlinePost:edit")
    @RequestMapping(value = "/candidate_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map candidate_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        drOnlineCandidateService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_DR, "线上民主推荐候选人调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
