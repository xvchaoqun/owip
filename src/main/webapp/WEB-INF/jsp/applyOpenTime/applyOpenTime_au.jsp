<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${applyOpenTime!=null}">编辑</c:if><c:if test="${applyOpenTime==null}">添加</c:if>党员申请开放时间段</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applyOpenTime_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${applyOpenTime.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属分党委</label>
				<div class="col-xs-6">
                    <select class="form-control" name="partyId" data-rel="select2" data-placeholder="请选择分党委">
                        <option></option>
                        <c:forEach items="${partyMap}" var="party">
                                <option value="${party.key}">${party.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=partyId]").val('${applyOpenTime.partyId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属党支部</label>
				<div class="col-xs-6">
                    <select class="form-control" name="branchId" data-rel="select2" data-placeholder="请选择党支部">
                        <option></option>
                        <c:forEach items="${branchMap}" var="branch">
                            <option value="${branch.key}">${branch.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=branchId]").val('${applyOpenTime.branchId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">开始时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_startTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(applyOpenTime.startTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">结束时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_endTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(applyOpenTime.endTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">生效阶段</label>
				<div class="col-xs-6">
                    <select required name="type" data-rel="select2" data-placeholder="请选择阶段">
                        <option></option>
                        <c:forEach items="${applyStageTypeMap}" var="type">
                            <c:if test="${type.key>0}">
                            <option value="${type.key}">${type.value}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=type]").val(${applyOpenTime.type});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否全局</label>
				<div class="col-xs-6">
                    <label>
                        <input name="isGlobal" ${applyOpenTime.isGlobal?"checked":""} class="ace ace-switch ace-switch-5" type="checkbox" />
                        <span class="lbl"></span>
                    </label>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${applyOpenTime!=null}">确定</c:if><c:if test="${applyOpenTime==null}">添加</c:if>"/>
</div>

<script>

    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
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
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#modalForm [data-rel="select2-ajax"]').select2({
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