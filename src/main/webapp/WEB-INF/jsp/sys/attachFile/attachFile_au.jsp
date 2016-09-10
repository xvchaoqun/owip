<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${attachFile!=null}">编辑</c:if><c:if test="${attachFile==null}">添加</c:if>系统附件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/attachFile_au" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${attachFile.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">附件</label>
            <div class="col-xs-6">
                <input ${empty attachFile.id?"required":""} class="form-control" type="file" name="_file" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">下载文件名</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="filename" value="${attachFile.filename}" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">唯一标识</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="code" value="${attachFile.code}" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">类别</label>
            <div class="col-xs-6">
                <select required name="type" data-placeholder="请选择" class="select2 tag-input-style" data-rel="select2">
                    <option></option>
                    <c:forEach items="${ATTACH_FILE_TYPE_MAP}" var="_type">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=type]").val('${attachFile.type}');
                </script>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited"name="remark">${attachFile.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${attachFile!=null}">确定</c:if><c:if test="${attachFile==null}">添加</c:if>"/>
</div>

<script>

    $('textarea.limited').inputlimiter();
    $('#modalForm input[type=file]').ace_file_input({
        no_file:'请选择文件 ...',
        btn_choose:'选择',
        btn_change:'更改',
        droppable:false,
        onchange:null,
        thumbnail:false //| true | large
        //whitelist:'gif|png|jpg|jpeg'
        //blacklist:'exe|php'
        //onchange:''
        //
    });

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
</script>