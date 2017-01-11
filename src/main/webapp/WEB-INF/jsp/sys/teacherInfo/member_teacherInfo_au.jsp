<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <h3>修改教职工党员人事信息</h3>
	<hr/>
    <form class="form-horizontal" action="${ctx}/member_teacherInfo_au" id="modalForm" method="post">
        <input type="hidden" name="userId" value="${teacher.userId}">
		<div class="row">
			<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-3 control-label">工作证号</label>
				<div class="col-xs-6 label-text">
					${sysUser.code}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="realname" value="${sysUser.realname}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">性别</label>
				<div class="col-xs-6">
					<div class="radio">
						<c:forEach var="gender" items="${GENDER_MAP}">
							<label>
								<input name="gender" type="radio" class="ace" value="${gender.key}"
									   <c:if test="${sysUser.gender==gender.key}">checked</c:if>/>
								<span class="lbl"> ${gender.value}</span>
							</label>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出生日期</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 150px">
						<input  class="form-control date-picker" name="_birth" type="text"
								data-date-format="yyyy-mm-dd" value="${cm:formatDate(sysUser.birth,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">籍贯</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="nativePlace" value="${sysUser.nativePlace}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">民族</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="nation" value="${sysUser.nation}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">身份证号</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="idcard" value="${sysUser.idcard}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">手机号码</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="extPhone" value="${teacher.extPhone}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">国家/地区</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="country" value="${teacher.country}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">学员结构</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="fromType" value="${teacher.fromType}">
						<span class="help-block">例如：本校、境内、境外等</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">所在单位</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="extUnit" value="${teacher.extUnit}">
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">最高学历</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="education" value="${teacher.education}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">最高学位</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="degree" value="${teacher.degree}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">学位授予日期</label>
					<div class="col-xs-6">
						<div class="input-group" style="width: 150px">
							<input  class="form-control date-picker" name="_degreeTime" type="text"
									data-date-format="yyyy-mm-dd" value="${cm:formatDate(teacher.degreeTime, "yyyy-MM-dd")}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>

				</div>
				<div class="col-xs-4">
					<div class="form-group">
						<label class="col-xs-3 control-label">所学专业</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="major" value="${teacher.major}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">学历毕业学校</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="school" value="${teacher.school}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">毕业学校类型</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="schoolType" value="${teacher.schoolType}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">学位授予学校</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="degreeSchool" value="${teacher.degreeSchool}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">到校日期</label>
						<div class="col-xs-6">
							<div class="input-group" style="width: 150px">
								<input  class="form-control date-picker" name="_arriveTime" type="text"
										data-date-format="yyyy-mm-dd" value="${cm:formatDate(teacher.arriveTime, "yyyy-MM-dd")}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">编制类别</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="authorizedType" value="${teacher.authorizedType}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">人员分类</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="staffType" value="${teacher.staffType}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">人员状态</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="staffStatus" value="${teacher.staffStatus}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">岗位类别</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="postClass" value="${teacher.postClass}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">主岗等级</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="mainPostLevel" value="${teacher.mainPostLevel}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">在岗情况</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="onJob" value="${teacher.onJob}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">专业技术职务</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="proPost" value="${teacher.proPost}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">专技岗位等级</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="proPostLevel" value="${teacher.proPostLevel}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">职称级别</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="titleLevel" value="${teacher.titleLevel}">
						</div>
					</div>

				</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label class="col-xs-3 control-label">管理岗位等级</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="manageLevel" value="${teacher.manageLevel}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">工勤岗位等级</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="officeLevel" value="${teacher.officeLevel}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">行政职务</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="post" value="${teacher.post}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">任职级别</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="postLevel" value="${teacher.postLevel}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">人才类型</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="talentType" value="${teacher.talentType}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">人才/荣誉称号</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="talentTitle" value="${teacher.talentTitle}">
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">居住地址</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="address" value="${teacher.address}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">婚姻状况</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="maritalStatus" value="${teacher.maritalStatus}">
					</div>
				</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">联系邮箱</label>
				<div class="col-xs-6">
                        <input class="form-control email" type="text" name="email" value="${sysUser.email}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">家庭电话</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="homePhone" value="${sysUser.homePhone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否退休</label>
				<div class="col-xs-6">
					<label>
						<input name="isRetire" ${teacher.isRetire?"checked":""}  type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">退休时间</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 150px">
						<input  class="form-control date-picker" name="_retireTime" type="text"
								data-date-format="yyyy-mm-dd" value="${cm:formatDate(teacher.retireTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否离休</label>
				<div class="col-xs-6">
					<label>
						<input name="isHonorRetire" ${teacher.isHonorRetire?"checked":""}  type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			</div>
			</div>
    </form>

<div class="clearfix form-actions">
	<div class="col-md-offset-3 col-md-9">
		<button class="btn btn-info" type="submit">
			<i class="ace-icon fa fa-check bigger-110"></i>
			提交
		</button>

		&nbsp; &nbsp; &nbsp;
		<button class="closeView btn" type="button">
			<i class="ace-icon fa fa-undo bigger-110"></i>
			取消
		</button>
	</div>
</div>

<script>
	$("#item-content button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						//SysMsg.success('提交成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							$(".closeView").click();
						//});
                    }
                }
            });
        }
    });
	register_date($('.date-picker'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
</script>