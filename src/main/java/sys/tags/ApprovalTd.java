package sys.tags;

import domain.ApplySelf;
import domain.Cadre;
import domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import persistence.ApplySelfMapper;
import service.abroad.ApplySelfService;
import service.cadre.CadreService;
import service.sys.SysResourceService;
import service.sys.SysUserService;

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
        //HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        ApplySelfService applySelfService = (ApplySelfService) wac.getBean("applySelfService");


        String btnTd = "<td><button class=\"approvalBtn btn btn-success btn-mini\"\n" +
                "        data-id=\"%s\" data-approvaltypeid=\"%s\">\n" +
                "        <i class=\"fa fa-edit\"></i> 审批\n" +
                "        </button></td>";
        String td = "";

        Map<Integer, Integer> approvalResultMap = applySelfService.getApprovalResultMap(applySelfId);
        int size = approvalResultMap.size();
        Integer[] vals = approvalResultMap.values().toArray(new Integer[size]);
        Integer[] keys = approvalResultMap.keySet().toArray( new Integer[size]);

        Integer firstVal = vals[0];
        if(firstVal==null) {
            if(view) td = "<td>未审批</td>";
            else td = String.format(btnTd, applySelfId, -1);
        }else if (firstVal==0){
            td = "<td>未通过</td>";
        }else{
            td = "<td>通过</td>";
        }
        boolean goToNext = true;
        int last = 0;
        for (int i = 1; i < size-1; i++) {
            Integer val = vals[i];
            if(val !=null && val == -1) {
                td += "<td>-</td>";
                last++;
            } else if(firstVal==null || firstVal==0 || goToNext==false){
                td += "<td class='not_approval'></td>";
                goToNext = false;
            } else {
                if(val==null){
                    if(view)
                        td += "<td>未审批</td>";
                     else
                        td += String.format(btnTd, applySelfId, keys[i]);
                    goToNext = false;
                }else if(val == 0 ){
                    td += "<td>未通过</td>";
                    goToNext = false;
                    last++;
                }else if(val == 1 ){
                    td += "<td>通过</td>";
                    last++;
                }
            }
        }

        Integer lastVal = vals[size-1];
        if(last==size-2){
            if(lastVal==null) {
                if (view)
                    td += "<td>未审批</td>";
                else
                    td += String.format(btnTd, applySelfId, 0);
            }else if(lastVal == 0 ){
                td += "<td>未通过</td>";
            }else if(lastVal == 1 ){
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

            if((firstVal!=null && firstVal==0)||(lastVal!=null)) { //初审未通过，或者终审完成，需要短信提醒
                td += String.format("<td><button data-id=\"%s\" data-status=\"%s\" data-name=\"%s\"" +
                        "        class=\"shortMsgBtn btn btn-primary btn-mini\">\n" +
                        "        <i class=\"fa fa-info-circle\"></i> 短信提醒\n" +
                        "        </button></td>", applySelfId, (lastVal!=null && lastVal==1), sysUser.getRealname());
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
