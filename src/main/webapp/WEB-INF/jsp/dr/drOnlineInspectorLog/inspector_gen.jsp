<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>      
<form id="genForm" method="post" action="${ctx}/dr/inspector_gen?onlineId=${onlineId}">
<div class="component">
            <table class="table table-bordered  table-striped">
              <thead>
                <tr>
					<th style="width: 50px;">单位/身份</th>
					<c:forEach items="${inspectorTypes}" var="inspectorType">
					<th style="width: 10px;">${inspectorType.type}</th>
					</c:forEach>
                </tr>
              </thead>
              <tbody>
              	<c:forEach items="${units}" var="unit">
              	<tr>
              		<th>${unit.name}</th>
              		<c:forEach items="${inspectorTypes}" var="inspectorType" >
              		<c:set var="totalKey" value="total_${unit.id}_${inspectorType.id}"/>
              		<td>
						<input type="text"  name="total_${unit.id}_${inspectorType.id}" value="${requestScope[totalKey]}">
					</td>
					</c:forEach>
              	</tr>
              	</c:forEach>
              </tbody>
            </table>
            </div>
            <button class="btn btn-lg btn-block btn-primary" style="margin-bottom: 100px;" type="button" id="add_entity">生成</button>
</form>
<style>
	.sticky-wrap {
		overflow-x: auto;
		overflow-y: hidden;
		position: relative;
		/* margin: 3em 0; */
		width: 100%;
	}
	body{
		background-color: inherit;
	}
	input{
		width: 30px;
	}
	.table{
		width: auto;
	}
	table.table > tbody> tr > td{
		text-align: center;vertical-align: middle;
	}
	.table thead th{
		vertical-align: top;
	}
</style>
<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/scripts.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${ctx}/css/dr.css" />
<script>
$(function(){
	stickheader();

	$("#add_entity").click(function(){
		
		$("#genForm").submit();return false;
	});
	$("#genForm").validate({
		rules: {
			<c:forEach items="${units}" var="unit" varStatus="vs1">
          		<c:forEach items="${inspectorTypes}" var="inspectorType" varStatus="vs2">
          		<c:set var="totalKey" value="total_${unit.id}_${inspectorType.id}"/>
					<c:if test="${not empty requestScope[totalKey]}">
          			total_${unit.id}_${inspectorType.id} : { digits:true, min:parseInt('${requestScope[totalKey]}'),max:500}
          			<c:if test="${!(vs1.last && vs2.last)}">,</c:if>
					</c:if>
          		</c:forEach>
          	</c:forEach>
		},
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(data){
					if(data.success){
						$("#modal").modal('hide');
						toastr.success('操作成功。', '成功');
						window.opener._reload();
					}
				}
			});
		}
		/* 重写错误显示消息方法,以alert方式弹出错误消息 */  
        ,showErrors: function(errorMap, errorList) {

            $.each( errorList, function(i,v){
				$(v.element).qtip({content:v.message, show: true});
            });
        },
        /* 失去焦点时验证 */   
        onfocusout: function(element) { $(element).valid(); }
	});
});
</script>
