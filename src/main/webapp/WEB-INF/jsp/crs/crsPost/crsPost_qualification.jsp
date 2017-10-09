<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
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
<div class="modal-footer center">
    <c:if test="${not empty crsPost.qualification}">
    <a href="javascript:;" onclick="_stepContentReload()" class="btn btn-default">取消</a>
    </c:if>
    <input type="button" id="submitBtn" class="btn btn-primary" value="确定"/>
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
    $("#modalForm select[name=type]").on("change",function(){
        var type = $(this).val();
        if(type>0) {
            ke.html(KindEditor.unescape(templateMap[type].content));
            ke.readonly();
        }else{
            ke.readonly(false);
        }
    });

    $("#submitBtn").click(function(){$("#modalForm").submit(); return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {
            $("#modalForm input[name=qualification]").val(ke.html());
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _stepContentReload()
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
</script>