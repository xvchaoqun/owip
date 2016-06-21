<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box" style="width: 800px">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员出国（境）组织关系暂留申请</h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table table-bordered table-striped">
                <jsp:include page="/WEB-INF/jsp/party/graduateAbroad/graduateAbroad_table.jsp"/>
            </table>
        </div>
    </div>
</div>

<script>
    function _applyBack(){
        bootbox.confirm("确定撤销申请吗？", function (result) {
            if(result){
                $.post("${ctx}/user/graduateAbroad_back",function(ret){

                    if(ret.success){
                        bootbox.alert("撤销成功。",function(){
                            location.reload();
                        });
                    }
                });
            }
        });
    }
</script>
