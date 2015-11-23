<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreEdu!=null}">编辑</c:if><c:if test="${cadreEdu==null}">添加</c:if>干部学习经历</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreEdu_au" id="modalForm" method="post">
		<div class="row">
			<div class="col-xs-6">
        <input type="hidden" name="id" value="${cadreEdu.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属干部</label>
				<div class="col-xs-6">
					<select required="" data-rel="select2-ajax" data-ajax--url="${ctx}/cadre_selects"
							name="cadreId" data-placeholder="请选择干部">
						<option value="${cadre.id}">${sysUser.username}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学历</label>
				<div class="col-xs-6">
					<select data-rel="select2" name="eduId" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__code=mc_edu"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=eduId]").val(${cadreEdu.eduId});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否最高学历</label>
				<div class="col-xs-6">
					<label>
						<input name="isHighEdu" ${cadreEdu.isHighEdu?"checked":""} class="ace ace-switch ace-switch-5" type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">毕业学校</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="school" value="${cadreEdu.school}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">院系</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="dep" value="${cadreEdu.dep}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所学专业</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="major" value="${cadreEdu.major}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学校类型</label>
				<div class="col-xs-6">
					<select data-rel="select2" name="schoolType" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__code=mc_school"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=schoolType]").val(${cadreEdu.schoolType});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">入学时间</label>
				<div class="col-xs-6">
					<div class="input-group">
					<input class="form-control date-picker" name="_enrolTime" type="text"
						   data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreEdu.enrolTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">毕业时间</label>
				<div class="col-xs-6">
					<div class="input-group">
					<input class="form-control date-picker" name="_finishTime" type="text"
						   data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreEdu.finishTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">学制</label>
				<div class="col-xs-6">
						<select data-rel="select2" name="schoolLen" data-placeholder="请选择">
							<option></option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=schoolLen]").val(${cadreEdu.schoolLen});
						</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学习方式</label>
				<div class="col-xs-6">
					<select data-rel="select2" name="learnStyle" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__code=mc_learn_style"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=learnStyle]").val(${cadreEdu.learnStyle});
					</script>
				</div>
			</div>
			</div>
			<div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-4 control-label">学位</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="degree" value="${cadreEdu.degree}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">是否为最高学位</label>
				<div class="col-xs-6">
					<label>
						<input name="isHighDegree" ${cadreEdu.isHighDegree?"checked":""} class="ace ace-switch ace-switch-5" type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">学位授予国家</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="degreeCountry" value="${cadreEdu.degreeCountry}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">学位授予单位</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="degreeUnit" value="${cadreEdu.degreeUnit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">学位授予日期</label>
				<div class="col-xs-6">
					<div class="input-group">
					<input class="form-control date-picker" name="_degreeTime" type="text"
						   data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreEdu.degreeTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">导师姓名</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="tutorName" value="${cadreEdu.tutorName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">导师目前所在单位及职务</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="tutorUnit" value="${cadreEdu.tutorUnit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="remark" rows="5">${cadreEdu.remark}</textarea>
				</div>
			</div>
				</div></div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreEdu!=null}">确定</c:if><c:if test="${cadreEdu==null}">添加</c:if>"/>
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