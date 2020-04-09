<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${drOnline!=null?'编辑':'添加'}线上民主推荐说明模板</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/drOnline_noticeEdit" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${drOnline.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 模板名称</label>
                <div class="col-xs-6">
                    <select data-rel="select2" name="niticeName" data-placeholder="请选择模板">
                        <option></option>
                        <c:forEach items="${noticeMap}" var="entry">
                            <option value="${entry.value.id}">${entry.value.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
			<div class="form-group">
				<div class="col-xs-6">
					<input type="hidden" name="notice">
					<textarea id="contentId">
                        <c:if test="${drOnline!=null}">${drOnline.notice}</c:if>
                    </textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty drOnline.notice?'确定':'添加'}</button>
</div>
<script>
	var ke = KindEditor.create('#contentId', {
		allowFileManager: true,
		uploadJson: '${ctx}/ke/upload_json',
		fileManagerJson: '${ctx}/ke/file_manager_json',
		height: '400px',
		width: '570px',
		minWidth: 570,
	});

	var noticeMap = ${cm:toJSONObject(noticeMap)};
    var dr = ${cm:toJSONObject(drOnline)};
    $("select[name=niticeName]").on("change",function(){
        //console.log($(this).val().length)
        var noticeId = $(this).val();
        if (noticeId.length == 0) {
            if (dr.notice != undefined) {
                ke.html(KindEditor.unescape(dr.notice))
            }
        }
        $.each(noticeMap, function (key, value) {
            if (key == noticeId){
                ke.html(KindEditor.unescape(noticeMap[noticeId].content))
            }else{
                //有待解决
                ke.readonly(false);
            }
        })
    });

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
			$("#modalForm input[name=notice]").val(ke.html());
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>