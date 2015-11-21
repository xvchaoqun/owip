<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberTeacher!=null}">编辑</c:if><c:if test="${memberTeacher==null}">添加</c:if>教职工党员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberTeacher_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberTeacher.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">创建时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="createTime" value="${memberTeacher.createTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提交书面申请书时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="applyTime" value="${memberTeacher.applyTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">来源</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="source" value="${memberTeacher.source}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${memberTeacher.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">转正时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="positiveTime" value="${memberTeacher.positiveTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为入党积极分子时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="activeTime" value="${memberTeacher.activeTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">更新时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="updateTime" value="${memberTeacher.updateTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">政治面貌</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="politicalStatus" value="${memberTeacher.politicalStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">组织关系转入时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="transferTime" value="${memberTeacher.transferTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属党支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchId" value="${memberTeacher.branchId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为发展对象时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="candidateTime" value="${memberTeacher.candidateTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属分党委</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${memberTeacher.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">入党时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="growTime" value="${memberTeacher.growTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="status" value="${memberTeacher.status}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">工作证号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${memberTeacher.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">最高学历</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="education" value="${memberTeacher.education}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">性别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="gender" value="${memberTeacher.gender}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">民族</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="nation" value="${memberTeacher.nation}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">毕业学校类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="schoolType" value="${memberTeacher.schoolType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职称级别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="titleLevel" value="${memberTeacher.titleLevel}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">人员状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="staffStatus" value="${memberTeacher.staffStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">退休时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="retireTime" value="${memberTeacher.retireTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">岗位类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="postClass" value="${memberTeacher.postClass}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">专业技术职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="proPost" value="${memberTeacher.proPost}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所学专业</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="major" value="${memberTeacher.major}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="post" value="${memberTeacher.post}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">毕业学校</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="school" value="${memberTeacher.school}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否离休</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isHonorRetire" value="${memberTeacher.isHonorRetire}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">岗位子类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="postType" value="${memberTeacher.postType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学位授予日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="degreeTime" value="${memberTeacher.degreeTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">管理岗位等级</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="manageLevel" value="${memberTeacher.manageLevel}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">联系邮箱</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="email" value="${memberTeacher.email}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任职级别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="postLevel" value="${memberTeacher.postLevel}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">工勤岗位等级</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="officeLevel" value="${memberTeacher.officeLevel}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">人才/荣誉称号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="talentTitle" value="${memberTeacher.talentTitle}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">居住地址</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="address" value="${memberTeacher.address}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">最高学位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="degree" value="${memberTeacher.degree}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">联系手机</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="mobile" value="${memberTeacher.mobile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出生日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="birth" value="${memberTeacher.birth}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">编制类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="authorizedType" value="${memberTeacher.authorizedType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${memberTeacher.realname}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">到校日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="arriveTime" value="${memberTeacher.arriveTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">籍贯</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="nativePlace" value="${memberTeacher.nativePlace}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">婚姻状况</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maritalStatus" value="${memberTeacher.maritalStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">人员分类</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="staffType" value="${memberTeacher.staffType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">家庭电话</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="phone" value="${memberTeacher.phone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">身份证号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="idcard" value="${memberTeacher.idcard}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">在岗情况</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="onJob" value="${memberTeacher.onJob}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">专技岗位等级</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="proPostLevel" value="${memberTeacher.proPostLevel}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberTeacher!=null}">确定</c:if><c:if test="${memberTeacher==null}">添加</c:if>"/>
</div>

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