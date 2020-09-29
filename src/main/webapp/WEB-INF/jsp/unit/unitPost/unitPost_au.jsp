<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="UNIT_POST_STATUS_NORMAL" value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unitPost!=null}">编辑</c:if><c:if test="${unitPost==null}">添加</c:if>${status==UNIT_POST_STATUS_NORMAL?'现有':'已撤销'}岗位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitPost_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitPost.id}">
        <input type="hidden" name="status" value="${status}">
		<div class="col-xs-12">
			<div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>所属单位</label>
				<%--<c:if test="${not empty unit}">
				<div class="col-xs-6 label-text">
					${unit.name}
					<input type="hidden" name="unitId" value="${unit.id}">
				</div>
				</c:if>
				<c:if test="${empty unit}">--%>
				<div class="col-xs-8">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
							name="unitId" data-placeholder="请选择"  data-width="272">
						<option value="${unit.id}">${unit.name}</option>
					</select>
				</div>
				<%--</c:if>--%>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>岗位编号</label>
				<div class="col-xs-8">
                    <input required class="form-control" type="text" name="code" value="${unitPost.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>岗位名称</label>
				<div class="col-xs-8">
                        <input required class="form-control" type="text" name="name" value="${unitPost.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">分管工作</label>
				<div class="col-xs-8">
                        <textarea class="noEnter form-control" name="job">${unitPost.job}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>是否正职</label>
				<div class="col-xs-8">
					<div class="input-group">
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="isPrincipal" id="isPrincipal1" value="1">
							<label for="isPrincipal1">
								是
							</label>
						</div>
						&nbsp;&nbsp;
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="isPrincipal" id="isPrincipal0" value="0">
							<label for="isPrincipal0">
								否
							</label>
						</div>
					</div>
					<c:if test="${not empty unitPost.isPrincipal}">
					<script>
						$("#modalForm input[name=isPrincipal][value=${unitPost.isPrincipal?1:0}]").prop("checked", true);
					</script>
					</c:if>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>是否班子<br/>负责人</label>
				<div class="col-xs-8">
				<select required name="leaderType" data-width="272" data-placeholder="请选择" data-rel="select2">
					<option></option>
					<c:forEach items="<%=SystemConstants.UNIT_POST_LEADER_TYPE_MAP%>" var="leaderType">
						<option value="${leaderType.key}">${leaderType.value}</option>
					</c:forEach>
				</select>
				<script>
					$("#modalForm select[name=leaderType]").val('${unitPost.leaderType}');
				</script>
				</div>
			</div>
			</div>
			<div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>岗位级别</label>
				<div class="col-xs-6">
					 <select required data-rel="select2" name="adminLevel" data-placeholder="请选择">
						<option></option>
						<jsp:include page="/metaTypes?__id=${cm:getMetaClassByCode('mc_admin_level').id}"/>
					</select>

					<script type="text/javascript">
						$("#modalForm select[name=adminLevel]").val('${unitPost.adminLevel}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>职务属性</label>
				<div class="col-xs-6">
					<select required name="postType" data-rel="select2" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_post').id}"/>
					</select>
					<script>
						$("#modalForm select[name=postType]").val('${unitPost.postType}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>职务类别</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="postClass"
							 data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_post_class').id}"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=postClass]").val(${unitPost.postClass});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>是否占干部职数</label>
				<div class="col-xs-8">
					<div class="input-group">
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="isCpc" id="isCpc1" value="1">
							<label for="isCpc1">
								占职数
							</label>
						</div>
						&nbsp;&nbsp;
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="isCpc" id="isCpc0" value="0">
							<label for="isCpc0">
								不占职数
							</label>
						</div>
					</div>
					<c:if test="${not empty unitPost.isCpc}">
					<script>
						$("#modalForm input[name=isCpc][value=${unitPost.isCpc?1:0}]").prop("checked", true);
					</script>
					</c:if>
				</div>
			</div>
		    <c:if test="${cadrePost!=null}">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>是否同步<br/>任职情况</label>
				<div class="col-xs-8">
					<div class="input-group">
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="isSync" id="isSync1" value="1">
							<label for="isSync1">
								是
							</label>
						</div>
						&nbsp;&nbsp;
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="isSync" id="isSync0" value="0" checked>
							<label for="isSync0">
								否
							</label>
						</div>
					</div>
					<span class="help-block blue">注：当前该岗位关联的干部是【${cadrePost.cadre.realname}】，如果选择“是”，则选择干部对应的任职信息也将更新。</span>
				</div>
			</div>
            </c:if>
			<div class="form-group" id="cadreDiv" style="${cadrePost==null?'':'display: none'}">
				<label class="col-xs-4 control-label">${cadrePost==null?'':'变更'}任职干部</label>
				<div class="col-xs-8">
					<select  <%--${cadrePost==null?'':'disabled'}--%> data-rel="select2-ajax"
								data-ajax-url="${ctx}/cadre_selects"
								name="cadreId" data-placeholder="请输入账号或姓名或学工号">
							<option value="${cadrePost.cadre.id}">${cadrePost.cadre.realname}-${cadrePost.cadre.code}</option>
					</select>
					<span class="help-block blue">注：变更前后两个干部的任职信息也将更新。</span>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-7">
					<textarea class="form-control limited" name="remark">${unitPost.remark}</textarea>
				</div>
			</div>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${unitPost!=null}">确定</c:if><c:if test="${unitPost==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#${param.jqGrid}").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.ajax_select($("#modalForm select[name=unitId]"))
    $.register.user_select($("#modalForm select[name=cadreId]"));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
	$("input[type=radio][name=isSync]").click(function(){
		if($(this).val()=='1') {
			$("#cadreDiv").show();
			/*$("#modalForm select[name=cadreId]").removeAttr("disabled");*/
		}else {
			$("#cadreDiv").hide();
			/*$("#modalForm select[name=cadreId]").prop("disabled", true);*/
		}
	});
</script>