<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="cetUnitTraintDiv">
    <c:import url="/user/cet/cetUnitTrain_au"/>
</div>
<script>
    function reRecordTrain(){
        var projectId = $("input[type=radio]:checked").val();
        $.post("${ctx}/user/cet/cetUnitTrain_list",{userId:${userId}, projectId:projectId},function(ret){
            //console.log(${userId}+","+projectId)
            if(ret.success) {
                $("#modal").modal("hide");
                $("#jqGrid").trigger("reloadGrid");
                $("#unitPostDiv").load("${ctx}/user/cet/cetUnitTrain_list?userId=${param.userId}")
            }
        });
    }
</script>