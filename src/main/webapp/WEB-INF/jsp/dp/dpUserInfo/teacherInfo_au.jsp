<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

    <h3>修改教职工党员人事信息</h3>
    <hr/>
    <form class="form-horizontal" action="${ctx}/dp/teacherInfo_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="userId" value="${teacherInfo.userId}">
        <div class="row">
            <div class="col-xs-4">
                <div class="form-group">
                    <label class="col-xs-3 control-label">工作证号</label>
                    <div class="col-xs-6 label-text">
                        ${sysUser.code}
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
                    <label class="col-xs-3 control-label">出生日期</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 150px">
                            <input class="form-control date-picker" name="_birth" type="text"
                                   data-date-format="yyyy.mm.dd" value="${cm:formatDate(sysUser.birth,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <c:if test="${empty cadre}">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">籍贯</label>
                        <div class="col-xs-6">
                            <input class="form-control" type="text" name="nativePlace" value="${sysUser.nativePlace}">
                            <span class="help-block">${_pMap['nativePlaceHelpBlock']}</span>
                        </div>
                    </div>
                </c:if>
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
                <c:if test="${empty cadre}">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">手机号码</label>
                        <div class="col-xs-6">
                            <input class="form-control mobile" type="text" name="mobile"
                                   value="${sysUser.mobile}">
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label class="col-xs-3 control-label">国家/地区</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="country" value="${sysUser.country}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">学员结构</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="fromType" value="${teacherInfo.fromType}">
                        <span class="help-block">例如：本校、境内、境外等</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">所在单位</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="unit" value="${sysUser.unit}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-3 control-label">最高学历</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="education" value="${teacherInfo.education}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">最高学位</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="degree" value="${teacherInfo.degree}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">学位授予日期</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 150px">
                            <input class="form-control date-picker" name="_degreeTime" type="text"
                                   data-date-format="yyyy.mm.dd"
                                   value="${cm:formatDate(teacherInfo.degreeTime, "yyyy.MM.dd")}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>

            </div>
            <div class="col-xs-4">
                <div class="form-group">
                    <label class="col-xs-3 control-label">所学专业</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="major" value="${teacherInfo.major}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">学历毕业学校</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="school" value="${teacherInfo.school}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">毕业学校类型</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="schoolType" value="${teacherInfo.schoolType}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">学位授予学校</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="degreeSchool" value="${teacherInfo.degreeSchool}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">到校日期</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 150px">
                            <input class="form-control date-picker" name="_arriveTime" type="text"
                                   data-date-format="yyyy.mm.dd"
                                   value="${cm:formatDate(teacherInfo.arriveTime, "yyyy.MM.dd")}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">编制类别</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="authorizedType"
                               value="${teacherInfo.authorizedType}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">人员类别</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="staffType" value="${teacherInfo.staffType}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">人员状态</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="staffStatus" value="${teacherInfo.staffStatus}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">岗位类别</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="postClass" value="${teacherInfo.postClass}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">主岗等级</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="mainPostLevel"
                               value="${teacherInfo.mainPostLevel}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">在岗情况</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="onJob" value="${teacherInfo.onJob}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">专业技术职务</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="proPost" value="${teacherInfo.proPost}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">专技岗位等级</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="proPostLevel" value="${teacherInfo.proPostLevel}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">职称级别</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="titleLevel" value="${teacherInfo.titleLevel}">
                    </div>
                </div>

            </div>
            <div class="col-xs-4">
                <div class="form-group">
                    <label class="col-xs-3 control-label">管理岗位等级</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="manageLevel" value="${teacherInfo.manageLevel}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">工勤岗位等级</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="officeLevel" value="${teacherInfo.officeLevel}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">行政职务</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="post" value="${teacherInfo.post}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">任职级别</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="postLevel" value="${teacherInfo.postLevel}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">人才类型</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="talentType" value="${teacherInfo.talentType}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">人才/荣誉称号</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="talentTitle" value="${teacherInfo.talentTitle}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-3 control-label">居住地址</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="address" value="${teacherInfo.address}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">婚姻状况</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="maritalStatus"
                               value="${teacherInfo.maritalStatus}">
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
                            <input name="isRetire" ${teacherInfo.isRetire?"checked":""} type="checkbox"/>
                            <span class="lbl"></span>
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">退休时间</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 150px">
                            <input class="form-control date-picker" name="_retireTime" type="text"
                                   data-date-format="yyyy.mm.dd"
                                   value="${cm:formatDate(teacherInfo.retireTime,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <%--<div class="form-group">
                    <label class="col-xs-3 control-label">是否离休</label>
                    <div class="col-xs-6">
                        <label>
                            <input name="isHonorRetire" ${teacherInfo.isHonorRetire?"checked":""} type="checkbox"/>
                            <span class="lbl"></span>
                        </label>
                    </div>
                </div>--%>
            </div>
        </div>
    </form>

    <div class="clearfix form-actions center">
            <button class="btn btn-info" type="submit">
                <i class="ace-icon fa fa-check bigger-110"></i>
                提交
            </button>

            &nbsp; &nbsp; &nbsp;
            <button class="hideView btn btn-default" type="button">
                <i class="ace-icon fa fa-undo bigger-110"></i>
                取消
            </button>
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
                        $.hashchange();
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