<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreFamliyAbroad!=null}">编辑</c:if><c:if test="${cadreFamliyAbroad==null}">添加</c:if>家庭成员海外情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreFamliyAbroad_au?cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreFamliyAbroad.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">家庭成员</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax"
                            data-ajax-url="${ctx}/cadreFamliy_selects?cadreId=${cadre.id}" name="famliyId" data-placeholder="请选择">
                        <option value="${cadreFamliy.id}">${cadreFamliy.realname}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">移居类别</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="type" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_abroad_type"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=type]").val(${cadreFamliyAbroad.type});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">移居时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_abroadTime" type="text"
                               data-date-min-view-mode="1"
                               data-date-format="yyyy-mm" value="${cm:formatDate(cadreFamliyAbroad.abroadTime,'yyyy-MM')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">移居国家</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="country" value="${cadreFamliyAbroad.country}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">现居住城市</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="city" value="${cadreFamliyAbroad.city}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreFamliyAbroad!=null}">确定</c:if><c:if test="${cadreFamliyAbroad==null}">添加</c:if>"/>
</div>

<script>

    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreFamliyAbroad").trigger("reloadGrid");
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