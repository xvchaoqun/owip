<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="GENDER_UNKNOWN" value="<%=SystemConstants.GENDER_UNKNOWN%>"/>
<h3>修改学生党员基础信息</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/studentInfo_au" autocomplete="off" disableautocomplete id="modalForm"
      method="post">
    <input type="hidden" name="userId" value="${student.userId}">
    <div class="row">
        <div class="col-xs-6">
            <div class="form-group">
                <label class="col-xs-3 control-label">系统账号</label>
                <div class="col-xs-6 label-text">
                    ${sysUser.code}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>账号类别</label>
                <div class="col-xs-6">
                    <select required data-rel="select2" name="syncSource" data-placeholder="请选择">
                        <option></option>
                        <option value="${USER_SOURCE_BKS}">本科生</option>
                        <option value="${USER_SOURCE_YJS}">研究生</option>
                    </select>
                    <script>
                        $("#modalForm select[name=syncSource]").val(${student.syncSource});
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 姓名</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="realname" value="${sysUser.realname}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 性别</label>
                <div class="col-xs-6 label-text">
                    <div class="input-group">
                        <c:forEach var="gender" items="${GENDER_MAP}">
                            <c:if test="${gender.key!=GENDER_UNKNOWN}">
                            <label>
                                <input required name="gender" type="radio" class="ace" value="${gender.key}"
                                       <c:if test="${sysUser.gender==gender.key}">checked</c:if>/>
                                <span class="lbl" style="padding-right: 5px;"> ${gender.value}</span>
                            </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 身份证号</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="idcard" value="${sysUser.idcard}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 出生日期</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input required class="form-control date-picker" name="_birth" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(sysUser.birth,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 籍贯</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="nativePlace" value="${sysUser.nativePlace}">
                    <span class="help-block">${_pMap['nativePlaceHelpBlock']}</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 民族</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="nation" value="${sysUser.nation}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">学生类别</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="type" value="${student.type}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">培养层次</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="eduLevel" value="${student.eduLevel}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">培养类型</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="eduType" value="${student.eduType}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">教育类别</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="eduCategory" value="${student.eduCategory}">
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label">培养方式</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="eduWay" value="${student.eduWay}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">是否全日制</label>
                <div class="col-xs-6">
                    <label>
                        <input name="isFullTime" ${student.isFullTime?"checked":""} type="checkbox"/>
                        <span class="lbl"></span>
                    </label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <label class="col-xs-3 control-label">招生年度</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="enrolYear" value="${student.enrolYear}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">学制</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="period" value="${student.period}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">年级</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="grade" value="${student.grade}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">实际入学年月</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_actualEnrolTime" type="text"
                               data-date-min-view-mode="1" placeholder="yyyy.mm"
                               data-date-format="yyyy.mm" value="${cm:formatDate(student.actualEnrolTime, "yyyy.MM")}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">预计毕业年月</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_expectGraduateTime" type="text"
                               data-date-min-view-mode="1" placeholder="yyyy.mm"
                               data-date-format="yyyy.mm"
                               value="${cm:formatDate(student.expectGraduateTime, "yyyy.MM")}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">实际毕业年月</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_actualGraduateTime" type="text"
                               data-date-min-view-mode="1" placeholder="yyyy.mm"
                               data-date-format="yyyy.mm"
                               value="${cm:formatDate(student.actualGraduateTime, "yyyy.MM")}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">延期毕业年限</label>
                <div class="col-xs-6">
                    <input class="form-control float" type="text" name="delayYear" value="${student.delayYear}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">学籍状态</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="xjStatus" value="${student.xjStatus}">
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
        <button class="hideView btn" type="button">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            取消
        </button>
    </div>
</div>

<script>
    $("#body-content-view button[type=submit]").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //SysMsg.success('提交成功。', '成功',function(){
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                        //});
                    }
                }
            });
        }
    });
    $.register.date($('.date-picker'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
</script>