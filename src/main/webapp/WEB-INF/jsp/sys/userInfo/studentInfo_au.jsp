<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h3>修改学生党员基础信息</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/studentInfo_au" autocomplete="off" disableautocomplete id="baseInfoForm"
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
                        $("#baseInfoForm select[name=syncSource]").val(${student.syncSource});
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
                            <label>
                                <input required name="gender" type="radio" class="ace" value="${gender.key}"
                                       <c:if test="${sysUser.gender==gender.key}">checked</c:if>/>
                                <span class="lbl" style="padding-right: 5px;"> ${gender.value}</span>
                            </label>
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
                        <input required class="form-control date-picker" name="birth" type="text"
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
                     <select name="nation" data-rel="select2" data-placeholder="请选择" data-width="150">
                             <option></option>
                        <c:forEach items="${cm:getMetaTypes('mc_nation').values()}" var="nation">
                            <option value="${nation.name}">${nation.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#baseInfoForm select[name=nation]").val('${cm:ensureEndsWith(sysUser.nation, '族')}');
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">学生类别</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="type" value="${student.type}">
                    <span class="help-block">注：城镇应届、农村应届等</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">学籍状态</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="xjStatus" value="${student.xjStatus}">
                    <span class="help-block">注：注册学籍、无学籍、已毕业、肄业、延期等</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">培养层次<br/>（研究生）</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="eduLevel" value="${student.eduLevel}">
                    <span class="help-block">注：硕士、博士等</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">培养类型<br/>（研究生）</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="eduType" value="${student.eduType}">
                    <span class="help-block">注：专业学位、学术型学位等</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">教育类别</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="eduCategory" value="${student.eduCategory}">
                    <span class="help-block">注：全日制、非全日制等</span>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label">培养方式</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="eduWay" value="${student.eduWay}">
                    <span class="help-block">注：定向、非定向、委托培养等</span>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <label class="col-xs-3 control-label">所在年级</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="grade" value="${student.grade}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">学制</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="period" value="${student.period}">
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
        <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-reply bigger-110"></i>
            返回
        </button>
    </div>
</div>

<script>
    $("#body-content-view button[type=submit]").click(function () {
        $("#baseInfoForm").submit();
        return false;
    });
    $("#baseInfoForm").validate({
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
    $("#baseInfoForm :checkbox").bootstrapSwitch();
    $('#baseInfoForm [data-rel="select2"]').select2();
</script>