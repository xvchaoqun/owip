<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>任职资格</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsPost_qualification" id="modalForm" method="post">
        <input type="hidden" name="id" value="${crsPost.id}">
			<div class="form-group">
				<div class="col-xs-6">
                    <select data-rel="select2" name="type" data-placeholder="请选择通用模板">
                        <option></option>
                        <c:forEach items="${templateMap}" var="entry">
                        <option value="${entry.key}">${entry.value.name}</option>
                        </c:forEach>
                    </select>
				</div>
			</div>
			<div class="form-group">
                <div class="col-xs-6">
                    <input type="hidden" name="qualification">
                    <textarea id="contentId">${crsPost.qualification}</textarea>
                </div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    var templateMap = ${cm:toJSONObject(templateMap)}
    var ke = KindEditor.create('#contentId', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '400px',
        width: '720px'
    });
    $("#modal form select[name=type]").on("change",function(){
        var type = $(this).val();
        if(type>0) {
            ke.html(templateMap[type].content);
            ke.readonly();
        }else{
            ke.readonly(false);
        }
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $("#modalForm input[name=qualification]").val(ke.html());
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#step-content li.active .loadPage").click()
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
</script>