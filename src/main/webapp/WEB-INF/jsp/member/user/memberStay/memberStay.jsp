<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_STAY_TYPE_MAP" value="<%=MemberConstants.MEMBER_STAY_TYPE_MAP%>"/>
<div class="row">
<div class="widget-box" style="width: 910px; margin-bottom: 30px;">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> ${MEMBER_STAY_TYPE_MAP.get(type)}组织关系暂留申请
            <c:if test="${param.auth=='admin'}">
                <a href="javascript:;" class="hideView btn btn-xs btn-success pull-right"
                   style="margin-top: 10px;margin-left: 10px;">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
            </c:if>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table table-bordered table-striped">
                <jsp:include page="/WEB-INF/jsp/member/memberStay/memberStay_table.jsp"/>
            </table>
        </div>
    </div>
</div>
</div>
<script>
    function _applyBack(){
        SysMsg.confirm("确定撤销申请吗？", "操作确认", function () {
            $.post("${ctx}/user/memberStay_back",function(ret){

                if(ret.success){
                    SysMsg.success("撤销成功。",function(){
                        $.hashchange();
                    });
                }
            });
        });
    }
</script>
