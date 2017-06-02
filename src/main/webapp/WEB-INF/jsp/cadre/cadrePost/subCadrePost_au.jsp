<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3><c:if test="${cadrePost!=null}">编辑</c:if><c:if test="${cadrePost==null}">添加</c:if>兼任职务</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/cadrePost_au?cadreId=${cadre.id}" id="modalForm" method="post">
		<input type="hidden" name="id" value="${cadrePost.id}">
		<div class="form-group">
			<label class="col-xs-4 control-label">姓名</label>
			<div class="col-xs-6 label-text">
				${sysUser.realname}
			</div>
		</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">兼任单位</label>
					<div class="col-xs-6">
						<select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
								name="unitId" data-placeholder="请选择所属单位">
							<option value="${unit.id}">${unit.name}</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-4 control-label">兼任职务</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="post" value="${cadrePost.post}">
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
							$("#modal form select[name=postId]").val(${cadrePost.postId});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">职务级别</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="adminLevelId" data-placeholder="请选择">
							<option></option>
							<c:import url="/metaTypes?__code=mc_admin_level"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=adminLevelId]").val(${cadrePost.adminLevelId});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">是否占职数</label>
					<div class="col-xs-6 label-text"  style="font-size: 15px;">
						<input type="checkbox" class="big" name="isCpc" ${(cadrePost==null ||cadrePost.isCpc)?"checked":""}/>
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
							$("#modal form select[name=postClassId]").val(${cadrePost.postClassId});
						</script>
					</div>
				</div>
	</form>
</div>
<div class="modal-footer">
	<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
	<input type="submit" class="btn btn-primary" value="<c:if test="${cadrePost!=null}">确定</c:if><c:if test="${cadrePost==null}">添加</c:if>"/>
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
						//SysMsg.success('操作成功。', '成功');
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