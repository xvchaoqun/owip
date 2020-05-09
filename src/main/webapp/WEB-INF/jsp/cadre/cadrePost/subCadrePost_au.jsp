<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<c:set value="${_pMap['postTimeToDay']=='true'}" var="_p_postTimeToDay"/>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3><c:if test="${cadrePost!=null}">编辑</c:if><c:if test="${cadrePost==null}">添加</c:if>兼任职务</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/cadrePost_au?cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="id" value="${cadrePost.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label">姓名</label>
			<div class="col-xs-6 label-text">
				${cadre.realname}
			</div>
		</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">关联岗位</label>
					<div class="col-xs-6">
						<select data-ajax-url="${ctx}/unitPost_selects" data-width="272"
								name="unitPostId" data-placeholder="请选择">
							<option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.name}-${unitPost.unitName}</option>
						</select>
						<span class="help-block blue">注：如果选择了关联岗位，则以下蓝色字段将同步此岗位相关的信息，且不可修改</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label blue"><span class="star">*</span>岗位名称</label>
					<div class="col-xs-6">
						<textarea required class="form-control noEnter" name="postName" rows="2">${cadrePost.postName}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label blue"><span class="star">*</span>职务属性</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="postType"
								data-width="272" data-placeholder="请选择">
							<option></option>
							<c:import url="/metaTypes?__code=mc_post"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=postType]").val(${cadrePost.postType});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label"><span class="star">*</span>行政级别</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="adminLevel"
								data-width="272" data-placeholder="请选择">
							<option></option>
							<c:import url="/metaTypes?__code=mc_admin_level"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=adminLevel]").val(${cadrePost.adminLevel});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label blue"><span class="star">*</span>职务类别</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="postClassId"
								data-width="272" data-placeholder="请选择">
							<option></option>
							<c:import url="/metaTypes?__code=mc_post_class"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=postClassId]").val(${cadrePost.postClassId});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label blue"><span class="star">*</span>兼任单位</label>
					<div class="col-xs-6">
						<select required data-rel="select2-ajax"
								data-width="272" data-ajax-url="${ctx}/unit_selects"
								name="unitId" data-placeholder="请选择所属单位">
							<option value="${unit.id}">${unit.name}</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label"><span class="star">*</span>职务</label>
					<div class="col-xs-6">
						<textarea required class="form-control noEnter" name="post">${cadrePost.post}</textarea>
					</div>
				</div>
				<div class="form-group">
                    <label class="col-xs-3 control-label">任职日期</label>
                    <div class="col-xs-6">
                        <div class="input-group">
							<c:if test="${_p_postTimeToDay}">
                                <input class="form-control date-picker" name="_lpWorkTime" type="text"
                                   data-date-format="yyyy.mm.dd" value="${cm:formatDate(cadrePost.lpWorkTime,'yyyy.MM.dd')}"/>
                            </c:if>
                            <c:if test="${!_p_postTimeToDay}">
                                <input class="form-control date-picker" name="_lpWorkTime" type="text"
                                    data-date-min-view-mode="1" data-date-format="yyyy.mm" value="${cm:formatDate(cadrePost.lpWorkTime,'yyyy.MM')}"/>
                            </c:if>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">任职始任日期</label>
                    <div class="col-xs-6">
                        <div class="input-group">
							<c:if test="${_p_postTimeToDay}">
                                <input class="form-control date-picker" name="_npWorkTime" type="text"
                                   data-date-format="yyyy.mm.dd" value="${cm:formatDate(cadrePost.npWorkTime,'yyyy.MM.dd')}"/>
                            </c:if>
                            <c:if test="${!_p_postTimeToDay}">
                                <input class="form-control date-picker" name="_npWorkTime" type="text"
                                    data-date-min-view-mode="1" data-date-format="yyyy.mm" value="${cm:formatDate(cadrePost.npWorkTime,'yyyy.MM')}"/>
                            </c:if>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
				<div class="form-group">
					<label class="col-xs-3 control-label">是否占职数</label>
					<div class="col-xs-6 label-text"  style="font-size: 15px;">
						<input type="checkbox" class="big" name="isCpc" ${(cadrePost==null ||cadrePost.isCpc)?"checked":""}/>
					</div>
				</div>
	</form>
</div>
<div class="modal-footer">
	<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
	<input type="submit" class="btn btn-primary" value="<c:if test="${cadrePost!=null}">确定</c:if><c:if test="${cadrePost==null}">添加</c:if>"/>
</div>

<script>
	$("#modalForm :checkbox").bootstrapSwitch();
	$.register.date($('.date-picker'));

	$("#modalForm").validate({
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
	$.register.ajax_select($('#modalForm select[name=unitId]'));

	function _templateResult(state) {
        var $state = state.text;
        if(state.code!=undefined){
            $state += "("+ state.code + ")";
        }
        if(state.up!=undefined){
            if ($.trim(state.up.job)!='')
                $state += "-" + state.up.job;
            if ($.trim(state.up.unitName)!='')
                $state += "-" + state.up.unitName;
        }
        return $state;
    }

    $.register.del_select($('#modalForm select[name=unitPostId]'), {templateResult:_templateResult,
		templateSelection: _templateResult}).on("change", function () {
        //console.log($(this).select2("data")[0])
        if($(this).select2("data")[0]!=undefined) {
			  var up = $(this).select2("data")[0]['up'];
		  }
        //console.log(up)
        if(up!=undefined){
            $('#modalForm textarea[name=postName]').val(up.name)
            $("#modalForm select[name=postType]").val(up.postType).trigger("change");
            $("#modalForm select[name=adminLevel]").val(up.adminLevel).trigger("change");
            $("#modalForm select[name=postClassId]").val(up.postClass).trigger("change");
            var option = new Option(up.unitName, up.unitId, true, true);
            $("#modalForm select[name=unitId]").append(option).trigger('change');
            $("input[name=isCpc]").bootstrapSwitch("state", up.isCpc)

        	$('#modalForm label.blue').closest(".form-group")
                .find("select,input,textarea").prop("disabled", true)
        }else{
            $('#modalForm label.blue').closest(".form-group")
                .find("select,input,textarea").prop("disabled", false)
        }
    });

	<c:if test="${not empty unitPost}">
    $('#modalForm label.blue').closest(".form-group")
                .find("select,input,textarea").prop("disabled", true);
    </c:if>
</script>