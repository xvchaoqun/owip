<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="alert alert-info" style="width: 1220px">
    <%--<button class="close" data-dismiss="alert">
        <i class="ace-icon fa fa-trash"></i>
    </button>--%>
    申请办理因私出国（境）证件的程序：<br/>
    1. 选择需要申办的证件名称，确认信息准确无误之后提交申请；<br/>
    2. 组织部备案之后，发短信通知申请人；<br/>
    3. 申请人登录系统打印申请表，到党委/校长办公室机要室盖章；<br/>
    4. 领取证件之后要及时交到组织部集中保管，不可擅自办理签证、签注和持证出国（境）。
</div>
<div id="apply-content">
<c:import url="/user/passportApply_select"/>
</div>