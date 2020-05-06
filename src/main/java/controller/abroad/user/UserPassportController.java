package controller.abroad.user;

import controller.abroad.AbroadBaseController;
import domain.abroad.Passport;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawExample;
import domain.abroad.PassportExample;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.AbroadConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/3/20.
 */
@Controller
@RequestMapping("/user/abroad")
public class UserPassportController extends AbroadBaseController {

    @RequiresPermissions("userApplySelf:*")
    @RequestMapping("/passport")
    public String passport(HttpServletResponse response,
                                // 1证件列表 2申请证件列表
                                @RequestParam(defaultValue = "1") Integer type, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 1) {
            return "forward:/user/abroad/passportList_page";
        }

        return "forward:/user/abroad/passportApply";
    }

    @RequiresPermissions("userApplySelf:*")
    @RequestMapping("/passportList_page")
    public String passportApply_page(@CurrentUser SysUserView loginUser,
                                     // 1证件列表 2申请证件列表
                                     @RequestParam(defaultValue = "1") Integer type, ModelMap modelMap) {

        modelMap.put("type", type);
        int userId = loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        List<Passport> keepPassports = new ArrayList<>();
        {
            PassportExample example = new PassportExample();
            example.createCriteria().andCadreIdEqualTo(cadre.getId())
                    .andTypeEqualTo(AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP);
            keepPassports = passportMapper.selectByExample(example);
        }
        modelMap.put("keepPassports", keepPassports);

        List<Passport> cancelPassports = new ArrayList<>();
        {
            PassportExample example = new PassportExample();
            example.createCriteria().andCadreIdEqualTo(cadre.getId())
                    .andTypeEqualTo(AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL);
            cancelPassports = passportMapper.selectByExample(example);
        }
        modelMap.put("cancelPassports", cancelPassports);

        List<Passport> lostPassports = new ArrayList<>();
        {
            PassportExample example = new PassportExample();
            example.createCriteria().andCadreIdEqualTo(cadre.getId())
                    .andTypeEqualTo(AbroadConstants.ABROAD_PASSPORT_TYPE_LOST);
            lostPassports = passportMapper.selectByExample(example);
        }
        modelMap.put("lostPassports", lostPassports);


        return "abroad/user/passport/passportList_page";
    }

    @RequiresPermissions("userApplySelf:*")
    @RequestMapping("/passport_useLogs")
    public String passport_useLogs(@CurrentUser SysUserView loginUser, int id, ModelMap modelMap) {

        int userId = loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);

        Passport passport = passportMapper.selectByPrimaryKey(id);
        if (passport.getCadreId().intValue() != cadre.getId()) {
            throw new UnauthorizedException();
        }
        modelMap.put("passport", passport);

        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "abroad/passport/passport_useLogs";
    }

    // 取消集中管理确认单
    @RequiresPermissions("userApplySelf:*")
    @RequestMapping("/passport_cancel")
    public String passport_cancel(@CurrentUser SysUserView loginUser, int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        int userId = loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);

        if (passport.getCadreId().intValue() != cadre.getId()) {
            throw new UnauthorizedException();
        }
        modelMap.put("passport", passport);

        return "abroad/passport/passport_cancel";
    }

    @RequiresPermissions("userApplySelf:*")
    @RequestMapping("/passport_lost_view")
    public String passport_lost_view(@CurrentUser SysUserView loginUser,int id, ModelMap modelMap) {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        int userId = loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);

        if (passport.getCadreId().intValue() != cadre.getId()) {
            throw new UnauthorizedException();
        }
        modelMap.put("passport", passport);

        return "abroad/passport/passport_lost_view";
    }

    @RequiresPermissions("userApplySelf:*")
    @RequestMapping("/passport_lostProof_download")
    public void passport_lostProof_download(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request,
                                            HttpServletResponse response) throws IOException {

        Passport passport = passportMapper.selectByPrimaryKey(id);
        int userId = loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);

        if (passport.getCadreId().intValue() != cadre.getId()) {
            throw new UnauthorizedException();
        }

        String lostProof = passport.getLostProof();
        String filePath = springProps.uploadPath + lostProof;

        MetaType passportType = CmTag.getMetaType(passport.getClassId());
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());

        String fileName = URLEncoder.encode(sysUser.getRealname() + "-" + passportType.getName()
                + "（丢失证明）" + FileUtils.getExtention(lostProof), "UTF-8");

        DownloadUtils.download(request, response, filePath, fileName);
    }

    // 使用记录
    @RequiresPermissions("userApplySelf:*")
    @RequestMapping("/passportDraw_data")
    public void passportDraw_data(@CurrentUser SysUserView loginUser, HttpServletResponse response,
                                  @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport_draw") String sort,
                                  @OrderParam(required = false, defaultValue = "desc") String order,
                                  int passportId, Integer year,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  Integer pageSize, Integer pageNo) throws IOException {

        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        int userId = loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);

        if (passport.getCadreId().intValue() != cadre.getId()) {
            throw new UnauthorizedException();
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PassportDrawExample example = new PassportDrawExample();
        PassportDrawExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        criteria.andPassportIdEqualTo(passportId);
        if (year != null) {
            criteria.andApplyDateBetween(DateUtils.parseDate(year + "0101"), DateUtils.parseDate(year + "1230"));
        }

        if (export == 1) {
            passportDrawService.passportDraw_export(AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF, example, response);
            return;
        }

        long count = passportDrawMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportDraw> passportDraws = passportDrawMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", passportDraws);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
