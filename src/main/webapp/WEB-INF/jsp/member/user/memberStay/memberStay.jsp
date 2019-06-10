<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_STAY_TYPE_MAP" value="<%=MemberConstants.MEMBER_STAY_TYPE_MAP%>"/>
<c:set var="MEMBER_STAY_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY%>"/>
<div class="row">
<div class="widget-box" style="width: 910px; margin-bottom: 30px;">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> ${MEMBER_STAY_TYPE_MAP.get(type)}组织关系暂留申请
            <c:if test="${memberStay.status==MEMBER_STAY_STATUS_OW_VERIFY}">
                <button class="hashchange btn btn-sm btn-success" style="margin-left: 10px;"
                        data-url="${ctx}/user/memberStay?type=${param.type}&isNew=1">
                    <i class="fa fa-plus"></i> 新申请</button>
            </c:if>

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
    function _applyBack(id){
        SysMsg.confirm("确定撤销申请吗？", "操作确认", function () {
            $.post("${ctx}/user/memberStay_back?id="+id,function(ret){

                if(ret.success){
                    SysMsg.success("撤销成功。",function(){
                        $.hashchange();
                    });
                }
            });
        });
    }
</script>
