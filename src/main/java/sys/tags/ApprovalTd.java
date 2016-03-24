package sys.tags;

import bean.ApprovalResult;
import domain.ApplySelf;
import domain.Cadre;
import domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import persistence.ApplySelfMapper;
import service.abroad.ApplySelfService;
import service.cadre.CadreService;
import service.sys.SysUserService;
import shiro.ShiroUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("serial")
public class ApprovalTd extends BodyTagSupport {

    private int applySelfId;
    private boolean view;
    @Override
    public int doStartTag() {
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        ApplySelfService applySelfService = (ApplySelfService) wac.getBean("applySelfService");

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

        String btnTd = "<td><button %s class=\"approvalBtn btn %s btn-mini  btn-xs\"\n" +
                "        data-id=\"%s\" data-approvaltypeid=\"%s\">\n" +
                "        <i class=\"fa fa-edit\"></i> 审批\n" +
                "        </button></td>";
        String _btnTd = "<td><button %s class=\"openView btn %s btn-mini  btn-xs\"\n" +
                "        data-url=\"%s/applySelf_view?type=aproval&id=%s&approvalTypeId=%s\">\n" +
                "        <i class=\"fa fa-edit\"></i> 审批\n" +
                "        </button></td>";
        String td = "";

        Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(applySelfId);
        int size = approvalResultMap.size();
        ApprovalResult[] vals = approvalResultMap.values().toArray(new ApprovalResult[size]);
        Integer[] keys = approvalResultMap.keySet().toArray( new Integer[size]);

        // 初审td
        ApprovalResult firstVal = vals[0];
        if(firstVal.getValue()==null) {
            if(view)
                td = "<td>未审批</td>";
            else {
                boolean canApproval = applySelfService.canApproval(shiroUser.getId(), applySelfId, -1);
                String disabled = canApproval?"":"disabled";
                String style = canApproval?"btn-success":"btn-default";
                td = String.format(_btnTd, disabled, style,  request.getContextPath(), applySelfId, -1);
            }
        }else if (firstVal.getValue()==0){
            td = "<td>未通过</td>";
        }else{
            td = "<td>通过</td>";
        }


        boolean goToNext = true;
        int last = 0;
        boolean lastIsUnPass = false;
        for (int i = 1; i < size-1; i++) {
            ApprovalResult val = vals[i];
            if(val.getValue() !=null && val.getValue() == -1) {
                td += "<td>-</td>";
                last++;
            } else if(firstVal.getValue()==null || firstVal.getValue()==0 || goToNext==false){
                td += "<td class='not_approval'></td>";
                goToNext = false;
            } else {
                if(val.getValue()==null){
                    if(view)
                        td += "<td>未审批</td>";
                     else {
                        boolean canApproval = applySelfService.canApproval(shiroUser.getId(), applySelfId, keys[i]);
                        String disabled = canApproval?"":"disabled";
                        String style = canApproval?"btn-success":"btn-default";
                        td += String.format(btnTd, disabled, style, applySelfId, keys[i]);
                    }
                    goToNext = false;
                }else if(val.getValue() == 0 ){
                    td += "<td>未通过</td>";
                    goToNext = false;
                    lastIsUnPass = true; // 未通过，直接到组织部终审
                    last++;
                }else if(val.getValue() == 1 ){
                    td += "<td>通过</td>";
                    last++;
                }
            }
        }

        // 终审td
        ApprovalResult lastVal = vals[size-1];
        if(last==size-2 || lastIsUnPass){ // 前面已经审批完成，或者 前面有一个未通过，直接到组织部终审
            if(lastVal.getValue()==null) {
                if (view)
                    td += "<td>未审批</td>";
                else {
                    boolean canApproval = applySelfService.canApproval(shiroUser.getId(), applySelfId, 0);
                    String disabled = canApproval?"":"disabled";
                    String style = canApproval?"btn-success":"btn-default";
                    td += String.format(btnTd, disabled,style,  applySelfId, 0);
                }
            }else if(lastVal.getValue() == 0 ){
                td += "<td>未通过</td>";
            }else if(lastVal.getValue() == 1 ){
                td += "<td>通过</td>";
            }
        }else{
            td += "<td class='not_approval'></td>";
        }
        if(!view && SecurityUtils.getSubject().hasRole("cadreAdmin")) {
            ApplySelfMapper applySelfMapper = (ApplySelfMapper) wac.getBean("applySelfMapper");
            CadreService cadreService = (CadreService) wac.getBean("cadreService");
            SysUserService sysUserService = (SysUserService) wac.getBean("sysUserService");
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
            Cadre cadre = cadreService.findAll().get(applySelf.getCadreId());
            SysUser sysUser = sysUserService.findById(cadre.getUserId());

            if((firstVal.getValue()!=null && firstVal.getValue()==0)||(lastVal.getValue()!=null)) { //初审未通过，或者终审完成，需要短信提醒
                td += String.format("<td><button data-id=\"%s\" data-userid=\"%s\" data-status=\"%s\" data-name=\"%s\"" +
                        "        class=\"shortMsgBtn btn btn-primary btn-mini btn-xs\">\n" +
                        "        <i class=\"fa fa-info-circle\"></i> 短信提醒\n" +
                        "        </button></td>", applySelfId, sysUser.getId(), (lastVal.getValue()!=null && lastVal.getValue()==1), sysUser.getRealname());
            }else{
                td +="<td></td>";
            }
        }

        JspWriter writer = pageContext.getOut();
        try {
            writer.write(td);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }

    public int getApplySelfId() {
        return applySelfId;
    }

    public void setApplySelfId(int applySelfId) {
        this.applySelfId = applySelfId;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }
}
