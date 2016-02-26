<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <h3>选择需要申办的证件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal">
        <input type="hidden" name="id" value="${passportApply.id}">

			<div class="form-group">
				<label class="col-xs-3 control-label">申办证件名称</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_passport_type"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=classId]").val(${passportApply.classId});
                    </script>
				</div>
			</div>

    </form>
</div>
<div class="modal-footer center">
    <input id="next" class="btn btn-primary"data-url="${ctx}/user/passportApply_confirm" value="下一步"/>
</div>
<script>
    $("#next").click(function(){
        var classId = $('form select[name=classId]').val();
        if(classId==undefined || classId==''){
            SysMsg.info("请选择证件名称");
            return;
        }
        $("#apply-content").load("${ctx}/user/passportApply_confirm?classId="+classId);
    });
    $('form [data-rel="select2"]').select2();
</script>