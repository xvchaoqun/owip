<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchUnit!=null}">编辑</c:if><c:if test="${dispatchUnit==null}">添加</c:if>单位发文</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchUnit_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${dispatchUnit.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">发文</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatch_selects"
                            name="dispatchId" data-placeholder="请选择发文">
                        <option value="${dispatch.id}"> ${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year )}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属单位</label>
				<div class="col-xs-6">
                    <select name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                        <option></option>
                        <c:forEach items="${unitMap}" var="unit">
                            <option value="${unit.key}">${unit.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=unitId]").val('${dispatchUnit.unitId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="typeId" data-placeholder="请选择单位发文类型">
                        <option></option>
                        <c:forEach var="metaType" items="${metaTypeMap}">
                            <option value="${metaType.value.id}">${metaType.value.name}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=typeId]").val('${dispatchUnit.typeId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">年份</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                               data-date-format="yyyy" data-date-min-view-mode="2" value="${dispatchUnit.year}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" name="remark">${dispatchUnit.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatchUnit!=null}">确定</c:if><c:if test="${dispatchUnit==null}">添加</c:if>"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        //SysMsg.success('操作成功。', '成功');
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
</script>