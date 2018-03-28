<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h3>修改人事基础信息（其中带*的字段每天会被人事库信息同步覆盖）</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/sysUserInfo_au" id="modalForm" method="post"  enctype="multipart/form-data">
    <input type="hidden" name="userId" value="${sysUser.id}">

    <div class="row">
        <div class="col-xs-4">
            <div class="form-group">
                <label class="col-xs-3 control-label">头像</label>

                <div class="col-xs-6"  style="width:170px">
                    <input type="file" name="_avatar" id="_avatar"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">*姓名</label>

                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="realname" value="${ui.realname}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">*性别</label>

                <div class="col-xs-6">
                    <div class="radio">
                        <c:forEach var="gender" items="${GENDER_MAP}">
                            <label>
                                <input name="gender" type="radio" class="ace" value="${gender.key}"
                                       <c:if test="${ui.gender==gender.key}">checked</c:if>/>
                                <span class="lbl"> ${gender.value}</span>
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">*出生日期</label>

                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_birth" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(ui.birth,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">*身份证号码</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="idcard" value="${ui.idcard}">
                </div>
            </div>


        </div>
        <div class="col-xs-4">
            <div class="form-group">
                <label class="col-xs-3 control-label">*民族</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="nation" value="${ui.nation}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">*籍贯</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="nativePlace" value="${ui.nativePlace}">
                    <span class="help-block">* 研究生不同步籍贯</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">出生地</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="homeplace" value="${ui.homeplace}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">户籍地</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="household" value="${ui.household}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">办公电话</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="phone" value="${ui.phone}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">手机号</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="mobile" value="${ui.mobile}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">短信称谓</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="msgTitle" value="${ui.msgTitle}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">家庭电话</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="homePhone" value="${ui.homePhone}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">邮箱</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="email" value="${ui.email}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">熟悉专业有何特长</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="specialty" value="${ui.specialty}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">健康状况</label>

                <div class="col-xs-6">
                    <select data-rel="select2" name="health"
                            data-placeholder="请选择" data-width="162">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_health"/>
                    </select>
                    <script type="text/javascript">
                        $("select[name=health]").val('${ui.health}');
                    </script>
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
<style>
    #modalForm .ace-file-container{
        height: 198px!important;
    }
</style>
<script>
    $.fileInput($("#_avatar"), {
        style:'well',
        btn_choose:'更换头像',
        btn_change:null,
        no_icon:'ace-icon fa fa-picture-o',
        thumbnail:'large',
        droppable:true,
        previewWidth: 143,
        previewHeight: 198,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    });
    $("#_avatar").find('button[type=reset]').on(ace.click_event, function(){
        //$('#user-profile input[type=file]').ace_file_input('reset_input');
        $("#_avatar").ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar/${sysUser.username}'}]);
    });
    $("#_avatar").ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar/${sysUser.username}'}]);

    $("#item-content button[type=submit]").click(function () {
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