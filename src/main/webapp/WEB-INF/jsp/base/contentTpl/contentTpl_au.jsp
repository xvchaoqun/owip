<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${contentTpl!=null}">编辑</c:if><c:if test="${contentTpl==null}">添加</c:if>内容模板</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/contentTpl_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${contentTpl.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">模板名称</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${contentTpl.name}">
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                    <select required name="type" data-rel="select2" data-width="200"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${CONTENT_TPL_TYPE_MAP}" var="type">
                            <option value="${type.key}">${type.value}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=type]").val('${contentTpl.type}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">代码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${contentTpl.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">模板内容</label>
				<div class="col-xs-8">
                        <textarea class="form-control" name="content" rows="8">${contentTpl.content}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">模板引擎</label>
				<div class="col-xs-6">
                    <select required name="engine" data-rel="select2" data-width="200"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${CONTENT_TPL_ENGINE_MAP}" var="type">
                            <option value="${type.key}">${type.value}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=engine]").val('${contentTpl.engine}');
                    </script>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">内容类别</label>
                <div class="col-xs-6">
                    <select required name="contentType" data-rel="select2" data-width="200"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${CONTENT_TPL_CONTENT_TYPE_MAP}" var="type">
                            <option value="${type.key}">${type.value}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=contentType]").val('${contentTpl.contentType}');
                    </script>
                </div>
            </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-8">
                <textarea class="form-control" name="remark">${contentTpl.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${contentTpl!=null}">确定</c:if><c:if test="${contentTpl==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });

    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>