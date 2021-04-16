<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div style="width: 900px">
<h3>修改基础信息</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/baseInfo_au" autocomplete="off"
      disableautocomplete id="updateForm" method="post" enctype="multipart/form-data">
    <input type="hidden" name="userId" value="${uv.id}">

    <div class="row">
        <div class="col-xs-5">
            <c:if test="${empty cadre}">
            <div class="form-group">
                <label class="col-xs-4 control-label">头像</label>

                <div class="col-xs-6" style="width:170px">
                    <div id="avatarDiv" style="width:145px">
                       <img width="135"  src="${ctx}/avatar?path=${cm:sign(uv.avatar)}&t=<%=new Date().getTime()%>"/>
                    </div>
                    <div style="margin-top: 5px;text-align: center">
                        <input type="hidden" name="base64Avatar">
                        <button type="button" class="popupBtn btn btn-xs btn-info" data-width="1050"
                                data-url="${ctx}/avatar_select?path=${cm:sign(uv.avatar)}&op=保存">
                            <i class="fa fa-edit"></i> 重传
                        </button>
                    </div>
                </div>
            </div>
            </c:if>
            <div class="form-group">
                <label class="col-xs-4 control-label">姓名</label>
                <div class="col-xs-6 label-text">
                    ${ui.realname}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">性别</label>
                <c:if test="${not empty cadre}">
                    <div class="col-xs-6 label-text">
                            ${GENDER_MAP.get(ui.gender)}
                    </div>
                </c:if>
                <c:if test="${empty cadre}">
                    <div class="col-xs-6">
                        <div class="input-group">
                            <c:forEach var="gender" items="${GENDER_MAP}">
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required ${ui.gender==gender.key?'checked':''}
                                           type="radio" name="gender" id="gender${gender.key}" value="${gender.key}">
                                    <label for="gender${gender.key}">
                                            ${gender.value}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">出生日期</label>
                <c:if test="${not empty cadre}">
                    <div class="col-xs-6 label-text">
                            ${cm:formatDate(ui.birth,'yyyy-MM-dd')}
                    </div>
                </c:if>
                <c:if test="${empty cadre}">
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 150px">
                            <input required class="form-control date-picker" name="birth" type="text"
                                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(ui.birth,'yyyy-MM-dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">身份证号码</label>
                <div class="col-xs-6 label-text">
                    ${ui.idcard}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">民族</label>
                <c:if test="${not empty cadre}">
                    <div class="col-xs-6 label-text">
                            ${cm:ensureEndsWith(ui.nation, '族')}
                    </div>
                </c:if>
                <c:if test="${empty cadre}">
                    <div class="col-xs-6">
                        <select name="nation" data-rel="select2" data-placeholder="请选择" data-width="150">
                                 <option></option>
                            <c:forEach items="${cm:getMetaTypes('mc_nation').values()}" var="nation">
                                <option value="${nation.name}">${nation.name}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#updateForm select[name=nation]").val('${cm:ensureEndsWith(ui.nation, '族')}');
                        </script>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-xs-7">
            <c:if test="${empty cadre}">
                <div class="form-group">
                    <label class="col-xs-3 control-label">籍贯</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="nativePlace" value="${ui.nativePlace}">
                        <span class="help-block">${_pMap['nativePlaceHelpBlock']}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">手机号</label>
                    <div class="col-xs-6">
                        <input class="form-control mobile" type="text" name="mobile" value="${ui.mobile}">
                    </div>
                </div>
            </c:if>
            <c:if test="${uv.type==USER_TYPE_JZG}">
            <div class="form-group">
                <label class="col-xs-3 control-label">办公电话</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="phone" value="${ui.phone}">
                </div>
            </div>
            </c:if>
            <div class="form-group">
                <label class="col-xs-3 control-label">电子邮箱</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="email" value="${ui.email}">
                </div>
            </div>
            <c:if test="${uv.type==USER_TYPE_JZG}">
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
                    <label class="col-xs-3 control-label">毕业学校</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="school" value="${teacherInfo.school}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">到校日期</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 150px">
                            <input class="form-control date-picker" name="_arriveTime" type="text"
                                   data-date-format="yyyy-mm-dd"
                                   value="${cm:formatDate(teacherInfo.arriveTime, "yyyy-MM-dd")}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">编制类别</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="authorizedType"
                               value="${teacherInfo.authorizedType}">
                        <span class="help-block">注：事业编制、非事业编等</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">人员类别</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="staffType" value="${teacherInfo.staffType}">
                        <span class="help-block">注：普通编制、校聘、院处聘、合同制等</span>
                    </div>
                </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 是否退休
                    <c:if test="${uv.type!=USER_TYPE_RETIRE}">
                <span class="prompt" data-title="退休状态说明" data-width="400"
							  data-prompt="<ul>
							  <li>如果该账号在“在职教职工党员库”中，修改为已退休状态后，该账号将自动归入“离退休党员库”</li>
							  <li>如果该账号在“离退休党员库”中，修改为未退休状态后，该账号将自动归入“在职教职工党员库”</li>
							  <li class='red'>如果该账号在人事系统中的状态变更为退休状态，则此处会自动同步变更为退休状态</li>
							  </ul>"><i class="fa fa-question-circle-o"></i></span>
                        </c:if>
                </label>
                <div class="col-xs-6">
                    <label>
                        <input name="isRetire" ${uv.type==USER_TYPE_RETIRE?"checked":""} type="checkbox"/>
                        <span class="lbl"></span>
                    </label>
                </div>
            </div>
             </c:if>
        </div>
    </div>
</form>

<div class="clearfix form-actions center">
    <c:if test="${not empty cadre}">
        <div class="note">
            注：如果是领导干部，则籍贯、出生地、户籍地、手机号信息由干部专员维护。
        </div>
    </c:if>
        <button id="updateBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中">
            <i class="ace-icon fa fa-save bigger-110"></i> 保存
    </button>
        &nbsp;
        <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-reply bigger-110"></i>
            返回
        </button>
</div>
</div>
<%--<style>
    #updateForm .ace-file-container{
        height: 198px!important;
    }
</style>--%>
<script>
    $("#updateBtn").click(function () {

        <c:if test="${empty cadre}">
        var idcard = '${ui.idcard}';
        if(idcard.length==15||idcard.length==18){

            var birth = $("#updateForm input[name=birth]").val();
            var gender = $("#updateForm input[name=gender]:checked").val();

            //console.log("$.getBirthdayByIdcard(idcard)="+$.getBirthdayByIdcard(idcard) +" birth="+birth)
            if($.getGenderByIdcard(idcard)!=gender
                || $.getBirthdayByIdcard(idcard)!=birth){

                SysMsg.confirm("性别或出生年月与身份证不符，请再次确认是否提交？", "信息确认", function(){

                    $("#updateForm").submit();
                    return false;
                })
                return false;
            }
        }
        </c:if>
         $("#updateForm").submit();
         return false;
    });
    $("#updateForm").validate({
        submitHandler: function (form) {
            var $btn = $("#updateBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $.register.date($('.date-picker'));
    $("#updateForm :checkbox").bootstrapSwitch();
    $('#updateForm [data-rel="select2"]').select2();
</script>