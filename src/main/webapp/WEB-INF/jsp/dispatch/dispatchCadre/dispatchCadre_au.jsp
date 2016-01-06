<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchCadre!=null}">编辑</c:if><c:if test="${dispatchCadre==null}">添加</c:if>干部任免</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchCadre_au" id="modalForm" method="post">
        <div class="row">
            <div class="col-xs-6">
        <input type="hidden" name="id" value="${dispatchCadre.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属发文</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatch_selects"
                            name="dispatchId" data-placeholder="请选择发文">
                        <option value="${dispatch.id}">${dispatch.code}</option>
                    </select>
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">类别</label>
            <div class="col-xs-6">
                <c:forEach var="DISPATCH_CADRE_TYPE" items="${DISPATCH_CADRE_TYPE_MAP}">
                <label class="label-text">
                    <input name="type" type="radio" class="ace" value="${DISPATCH_CADRE_TYPE.key}"
                           <c:if test="${dispatchCadre.type==DISPATCH_CADRE_TYPE.key}">checked</c:if>/>
                    <span class="lbl"> ${DISPATCH_CADRE_TYPE.value}</span>
                </label>
                </c:forEach>
            </div>
        </div>

			<div class="form-group">
				<label class="col-xs-3 control-label">任免方式</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="wayId" data-placeholder="请选择任免方式">
                        <option></option>
                        <c:forEach var="way" items="${wayMap}">
                            <option value="${way.value.id}">${way.value.name}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=wayId]").val('${dispatchCadre.wayId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任免程序</label>
				<div class="col-xs-6">
                    <select required class="form-control" data-rel="select2" name="procedureId" data-placeholder="请选择任免程序">
                        <option></option>
                        <c:forEach var="procedure" items="${procedureMap}">
                            <option value="${procedure.value.id}">${procedure.value.name}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=procedureId]").val('${dispatchCadre.procedureId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">关联干部</label>
				<div class="col-xs-8">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                            name="cadreId" data-placeholder="请选择干部">
                        <option value="${cadre.id}">${sysUser.realname}</option>
                    </select>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-8">
                        <input required class="form-control" type="text" name="name" value="${dispatchCadre.name}">
				</div>
			</div>
            </div>
            <div class="col-xs-6">
            <div class="form-group">
                <label class="col-xs-3 control-label">职务</label>
                <div class="col-xs-8">
                    <input required class="form-control" type="text" name="post" value="${dispatchCadre.post}">
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务属性</label>
				<div class="col-xs-6">
                    <select name="postId" data-rel="select2" data-placeholder="请选择职务属性">
                        <option></option>
                        <c:forEach items="${postMap}" var="post">
                            <option value="${post.key}">${post.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=postId]").val('${dispatchCadre.postId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政级别</label>
				<div class="col-xs-6">
                    <select required class="form-control" data-rel="select2" name="adminLevelId" data-placeholder="请选择行政级别">
                        <option></option>
                        <c:forEach var="adminLevel" items="${adminLevelMap}">
                            <option value="${adminLevel.value.id}">${adminLevel.value.name}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=adminLevelId]").val('${dispatchCadre.adminLevelId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属单位</label>
				<div class="col-xs-6">
                    <select required class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                        <option></option>
                        <c:forEach items="${unitMap}" var="unit">
                            <option value="${unit.key}">${unit.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=unitId]").val('${dispatchCadre.unitId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-8">
                        <textarea class="form-control limited" name="remark" rows="5">${dispatchCadre.remark}</textarea>
				</div>
			</div>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatchCadre!=null}">确定</c:if><c:if test="${dispatchCadre==null}">添加</c:if>"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
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
    $('#modalForm select[name=dispatchId][data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 300,
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
    register_user_select($('#modalForm select[name=cadreId]'));
</script>