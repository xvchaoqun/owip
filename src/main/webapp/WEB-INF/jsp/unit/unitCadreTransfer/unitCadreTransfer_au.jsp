<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" onclick='unitCadreTransfer_page("${param.groupId}")' aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unitCadreTransfer!=null}">编辑</c:if><c:if test="${unitCadreTransfer==null}">添加</c:if>任职信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitCadreTransfer_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitCadreTransfer.id}">
        <input type="hidden" name="groupId" value="${unitCadreTransferGroup.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属分组</label>
				<div class="col-xs-6">
                    <input type="text" disabled value="${unitCadreTransferGroup.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">关联干部</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax--url="${ctx}/cadre_selects"
                            name="cadreId" data-placeholder="请选择干部">
                        <c:if test="${not empty cadre}">
                        <option value="${cadre.id}">${cm:getUserById(cadre.userId).realname}</option>
                        </c:if>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${unitCadreTransfer.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务属性</label>
				<div class="col-xs-6">
                    <select required class="form-control"  data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_post"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=postId]").val(${unitCadreTransfer.postId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任职日期</label>
				<div class="col-xs-6">
                    <div  class="input-group">
                        <input required class="form-control date-picker" name="_appointTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(unitCadreTransfer.appointTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">免职日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_dismissTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(unitCadreTransfer.dismissTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited"name="remark">${unitCadreTransfer.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" onclick='unitCadreTransfer_page("${param.groupId}")' class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${unitCadreTransfer!=null}">确定</c:if><c:if test="${unitCadreTransfer==null}">添加</c:if>"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
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
                        unitCadreTransfer_page("${param.groupId}");
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