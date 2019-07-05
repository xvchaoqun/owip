<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_OUT_STATUS_MAP" value="<%=MemberConstants.MEMBER_OUT_STATUS_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_APPLY" value="<%=MemberConstants.MEMBER_OUT_STATUS_APPLY%>"/>

<div class="row">
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 组织关系转出申请</h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table table-bordered table-striped">
                <jsp:include page="/WEB-INF/jsp/member/memberOut/memberOut_table.jsp"/>
            </table>
        </div>
    </div>
</div>
</div>
    <script>
    function _applyBack(){
        SysMsg.confirm("确定撤销申请吗？", "操作确认", function () {
            $.post("${ctx}/user/memberOut_back",function(ret){

                if(ret.success){
                    SysMsg.success("撤销成功。",function(){
                        $.hashchange();
                    });
                }
            });
        });
    }
</script>