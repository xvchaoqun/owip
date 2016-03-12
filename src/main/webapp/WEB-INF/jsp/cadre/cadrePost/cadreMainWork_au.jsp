<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreMainWork!=null}">编辑</c:if><c:if test="${cadreMainWork==null}">添加</c:if>干部主职</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreMainWork_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreMainWork.id}">
		<input type="hidden" name="cadreId" value="${cadre.id}">
		<div class="row">
		<div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-4 control-label">所属干部</label>
				<div class="col-xs-6">
					<input type="text" value="${sysUser.realname}" disabled>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">主职</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="work" value="${cadreMainWork.work}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">职务属性</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="postId" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__code=mc_post"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=postId]").val(${cadreMainWork.postId});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">行政级别</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="adminLevelId" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__code=mc_admin_level"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=adminLevelId]").val(${cadreMainWork.adminLevelId});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">是否正职</label>
				<div class="col-xs-6">
					<label>
						<input name="isPositive" ${cadreMainWork.isPositive?"checked":""}  type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">职务类别</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="postClassId" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__code=mc_post_class"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=postClassId]").val(${cadreMainWork.postClassId});
					</script>
				</div>
			</div>

			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="col-xs-4 control-label">所属单位</label>
					<div class="col-xs-6">
						<select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
								name="unitId" data-placeholder="请选择所属单位">
							<option value="${unit.id}">${unit.name}</option>
						</select>
					</div>
				</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">任职日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_postTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreMainWork.postTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-4 control-label">现职务始任日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_startTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreMainWork.startTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-4 control-label">是否双肩挑</label>
				<div class="col-xs-6">
					<label>
						<input name="isDouble" ${cadreMainWork.isDouble?"checked":""}  type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">双肩挑单位</label>
				<div class="col-xs-6">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
							name="doubleUnitId" data-placeholder="请选择所属单位">
						<option value="${doubleUnit.id}">${doubleUnit.name}</option>
					</select>
				</div>
			</div>
			</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreMainWork!=null}">确定</c:if><c:if test="${cadreMainWork==null}">添加</c:if>"/>
</div>

<script>
	$("#modal :checkbox").bootstrapSwitch();
	register_date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _reload();
                        SysMsg.success('操作成功。', '成功');
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