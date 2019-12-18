<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div style="width: 900px">
<h3>修改基础信息</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/baseInfo_au" autocomplete="off"
      disableautocomplete id="baseInfoForm" method="post" enctype="multipart/form-data">
    <input type="hidden" name="userId" value="${sysUser.id}">

    <div class="row">
        <div class="col-xs-5">
            <c:if test="${empty cadre}">
            <div class="form-group">
                <label class="col-xs-4 control-label">头像</label>

                <div class="col-xs-6" style="width:170px">
                    <input type="file" name="_avatar" id="_avatar"/>
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
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">出生日期</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input required class="form-control date-picker" name="_birth" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(ui.birth,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">身份证号码</label>
                <div class="col-xs-6 label-text">
                    ${ui.idcard}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">民族</label>
                <div class="col-xs-6">
                    <select name="nation" data-rel="select2" data-placeholder="请选择" data-width="150">
                             <option></option>
                        <c:forEach items="${cm:getMetaTypes('mc_nation').values()}" var="nation">
                            <option value="${nation.name}">${nation.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#baseInfoForm select[name=nation]").val('${cm:ensureEndsWith(ui.nation, '族')}');
                    </script>
                </div>
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
            <c:if test="${sysUser.type==USER_TYPE_JZG}">
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
        </div>
    </div>
</form>

<div class="clearfix form-actions">
    <c:if test="${not empty cadre}">
        <div class="note">
            注：如果是领导干部（含离任），则籍贯、出生地、户籍地、手机号信息由干部专员维护。
        </div>
    </c:if>
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
</div>
<%--<style>
    #baseInfoForm .ace-file-container{
        height: 198px!important;
    }
</style>--%>
<script>
    <c:if test="${not empty cadre}">
    $("#baseInfoForm input[name=gender]").prop("disabled", true);
    $("#baseInfoForm input[name=_birth]").prop("disabled", true);
    $("#baseInfoForm select[name=nation]").prop("disabled", true);
    </c:if>
    $.fileInput($("#_avatar"), {
        style: 'well',
        btn_choose: '更换头像',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 143,
        previewHeight: 198,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    });
    $("#_avatar").find('button[type=reset]').on(ace.click_event, function () {
        //$('#user-profile input[type=file]').ace_file_input('reset_input');
        $("#_avatar").ace_file_input('show_file_list', [{
            type: 'image',
            name: '${ctx}/avatar?path=${cm:encodeURI(ui.avatar)}'
        }]);
    });
    $("#_avatar").ace_file_input('show_file_list', [{
        type: 'image',
        name: '${ctx}/avatar?path=${cm:encodeURI(ui.avatar)}'
    }]);

    $("#body-content-view button[type=submit]").click(function () {

        var idcard = '${ui.idcard}';
        var _birth = $("#baseInfoForm input[name=_birth]").val();
        var gender = $("#baseInfoForm input[name=gender]").val();
        if(idcard.length==15||idcard.length==18){
            if($.getGenderByIdcard(idcard)!=gender
                || $.getBirthdayByIdcard(idcard)!=_birth){

                SysMsg.confirm("性别或出生年月与身份证不符，请再次确认是否提交？", "信息确认", function(){

                    $("#baseInfoForm").submit();
                    return false;
                })
            }
        }
    });
    $("#baseInfoForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }
                }
            });
        }
    });
    $.register.date($('.date-picker'));
    $("#baseInfoForm :checkbox").bootstrapSwitch();
    $('#baseInfoForm [data-rel="select2"]').select2();
</script>