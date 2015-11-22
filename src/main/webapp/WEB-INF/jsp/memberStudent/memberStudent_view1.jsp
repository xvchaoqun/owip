<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <form class="form-horizontal" action="${ctx}/memberStudent_au" id="modalForm" method="post">

		<div class="row">
			<div class="col-sm-6">
		<input type="hidden" name="userId" value="${memberStudent.userId}">
			<div class="form-group">
				<label class="col-xs-3 control-label">创建时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="createTime" value="${memberStudent.createTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提交书面申请书时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="applyTime" value="${memberStudent.applyTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">来源</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="source" value="${memberStudent.source}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">转正时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="positiveTime" value="${memberStudent.positiveTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为入党积极分子时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="activeTime" value="${memberStudent.activeTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">政治面貌</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="politicalStatus" value="${memberStudent.politicalStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">组织关系转入时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="transferTime" value="${memberStudent.transferTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属党支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchId" value="${memberStudent.branchId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为发展对象时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="candidateTime" value="${memberStudent.candidateTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属分党委</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${memberStudent.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">入党时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="growTime" value="${memberStudent.growTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="status" value="${memberStudent.status}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">延期毕业年限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="delayYear" value="${memberStudent.delayYear}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学制</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="period" value="${memberStudent.period}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学生证号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${memberStudent.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">教育类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="eduCategory" value="${memberStudent.eduCategory}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">性别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="gender" value="${memberStudent.gender}">
				</div>
			</div>
				</div>
					<div class="col-sm-6">
			<div class="form-group">
				<label class="col-xs-3 control-label">民族</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="nation" value="${memberStudent.nation}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">实际毕业年月</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="actualGraduateTime" value="${memberStudent.actualGraduateTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">预计毕业年月</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="expectGraduateTime" value="${memberStudent.expectGraduateTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">实际入学年月</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="actualEnrolTime" value="${memberStudent.actualEnrolTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">同步来源</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="syncSource" value="${memberStudent.syncSource}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学生类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${memberStudent.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否全日制</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isFullTime" value="${memberStudent.isFullTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${memberStudent.realname}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">招生年度</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="enrolYear" value="${memberStudent.enrolYear}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">籍贯</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="nativePlace" value="${memberStudent.nativePlace}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培养方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="eduWay" value="${memberStudent.eduWay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">身份证号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="idcard" value="${memberStudent.idcard}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培养层次</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="eduLevel" value="${memberStudent.eduLevel}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">年级</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="grade" value="${memberStudent.grade}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培养类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="eduType" value="${memberStudent.eduType}">
				</div>
			</div>
						</div>
			</div>
    </form>

<script>
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