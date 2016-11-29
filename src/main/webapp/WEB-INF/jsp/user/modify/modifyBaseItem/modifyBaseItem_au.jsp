<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

    <form class="form-horizontal" action="${ctx}/user/modifyBaseItem_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${record.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">${record.name}</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="modifyValue" value="${record.modifyValue}">
				</div>
			</div>
    </form>

<script>

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _reload();
                    }
                }
            });
        }
    });

   // register_date($('.date-picker'));
</script>