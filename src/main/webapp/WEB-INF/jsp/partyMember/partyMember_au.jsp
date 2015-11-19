<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${partyMember!=null}">编辑</c:if><c:if test="${partyMember==null}">添加</c:if>基层党组织成员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/partyMember_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${partyMember.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属班子</label>
				<div class="col-xs-6">
                    <select required class="form-control" name="groupId" data-rel="select2" data-placeholder="请选择所属班子">
                        <option></option>
                        <c:forEach items="${groupMap}" var="group">
                            <option value="${group.key}">${group.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=groupId]").val('${partyMember.groupId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">账号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${partyMember.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                    <select required class="form-control" name="typeId" data-rel="select2" data-placeholder="请选择类别">
                        <option></option>
                        <c:forEach items="${typeMap}" var="type">
                            <option value="${type.key}">${type.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=typeId]").val('${partyMember.typeId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否管理员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isAdmin" value="${partyMember.isAdmin}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${partyMember!=null}">确定</c:if><c:if test="${partyMember==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('[data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 200,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>