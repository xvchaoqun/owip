<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div style="width: 900px">
<h3>修改账号基础信息</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/baseInfo_au" autocomplete="off"
      disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
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

                <div class="col-xs-6 label-text">
                    ${GENDER_MAP.get(ui.gender)}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">出生日期</label>

                <div class="col-xs-6 label-text">
                    ${cm:formatDate(ui.birth,'yyyy-MM-dd')}
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

                <div class="col-xs-6 label-text">
                    ${ui.nation}
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
                <%--<div class="form-group">
                    <label class="col-xs-3 control-label">出生地</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="homeplace" value="${ui.homeplace}">
                        <span class="help-block">${_pMap['nativePlaceHelpBlock']}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">户籍地</label>

                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="household" value="${ui.household}">
                        <span class="help-block">${_pMap['nativePlaceHelpBlock']}</span>
                    </div>
                </div>--%>
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
            注：如果是处级干部（含离任），则籍贯、出生地、户籍地、手机号信息由干部专员维护。
        </div>
    </c:if>
    <div class="col-md-offset-3 col-md-9">
        <button class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="hideView btn" type="button">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            返回
        </button>
    </div>
</div>
</div>
<%--<style>
    #modalForm .ace-file-container{
        height: 198px!important;
    }
</style>--%>
<script>
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
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
</script>