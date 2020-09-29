<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h3>修改人员基本信息（其中带<span style="color: red">*</span>的字段每天会被校园门户账号信息进行同步覆盖）</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/sysUserInfo_au" autocomplete="off" disableautocomplete id="modalForm"
      method="post" enctype="multipart/form-data">
    <input type="hidden" name="userId" value="${sysUser.id}">

    <div class="row">
        <div class="col-xs-4">
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.avatar}"><span class="star">*</span></c:if> 头像</label>

                <div class="col-xs-6" style="width:170px">
                    <input type="file" name="_avatar" id="_avatar"/>
                </div>
                <c:if test="${!sync.avatar}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.avatar?"checked":""}
                               data-name="avatar" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.realname}"><span class="star">*</span></c:if> 姓名</label>

                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="realname" value="${ui.realname}">
                </div>
                <c:if test="${!sync.realname}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.realname?"checked":""}
                               data-name="realname" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.gender}"><span class="star">*</span></c:if> 性别</label>

                <div class="col-xs-6 label-text">
                    <div class="input-group">
                        <c:forEach var="gender" items="${GENDER_MAP}">
                            <label>
                                <input name="gender" type="radio" class="ace" value="${gender.key}"
                                       <c:if test="${ui.gender==gender.key}">checked</c:if>/>
                                <span class="lbl" style="padding-right: 5px;"> ${gender.value}</span>
                            </label>
                        </c:forEach>
                    </div>
                </div>
                <c:if test="${!sync.gender}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.gender?"checked":""}
                               data-name="gender" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.birth}"><span class="star">*</span></c:if> 出生日期</label>

                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="birth" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(ui.birth,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
                <c:if test="${!sync.birth}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.birth?"checked":""}
                               data-name="birth" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.idcard}"><span class="star">*</span></c:if> 身份证号码</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="idcard" value="${ui.idcard}">
                </div>
                <c:if test="${!sync.idcard}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.idcard?"checked":""}
                               data-name="idcard" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.nation}"><span class="star">*</span></c:if> 民族</label>

                <div class="col-xs-6">
                     <select name="nation" data-rel="select2" data-placeholder="请选择" data-width="150">
                             <option></option>
                        <c:forEach items="${cm:getMetaTypes('mc_nation').values()}" var="nation">
                            <option value="${nation.name}">${nation.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=nation]").val('${cm:ensureEndsWith(ui.nation, '族')}');
                    </script>
                </div>
                <c:if test="${!sync.nation}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.nation?"checked":""}
                               data-name="nation" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.nativePlace}"><span class="star">*</span></c:if> 籍贯</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="nativePlace" value="${ui.nativePlace}">
                    <span class="help-block">${_pMap['nativePlaceHelpBlock']}</span>
                </div>
                <c:if test="${!sync.nativePlace}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.nativePlace?"checked":""}
                               data-name="nativePlace" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.homeplace}"><span class="star">*</span></c:if> 出生地</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="homeplace" value="${ui.homeplace}">
                    <span class="help-block">${_pMap['nativePlaceHelpBlock']}</span>
                </div>
                <c:if test="${!sync.homeplace}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.homeplace?"checked":""}
                               data-name="homeplace" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.household}"><span class="star">*</span></c:if> 户籍地</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="household" value="${ui.household}">
                    <span class="help-block">${_pMap['nativePlaceHelpBlock']}</span>
                </div>
                <c:if test="${!sync.household}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.household?"checked":""}
                               data-name="household" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>

        </div>
        <div class="col-xs-4">

            <c:if test="${sysUser.type==USER_TYPE_JZG}">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><c:if test="${!sync.proPost}"><span class="star">*</span></c:if> 专业技术职务</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="proPost" value="${teacherInfo.proPost}">
                    </div>
                    <c:if test="${!sync.proPost}">
                        <div class="col-xs-6">
                            <input type="checkbox" ${userSync.proPost?"checked":""}
                                   data-name="proPost" class="syncOnce big"/> 仅同步一次
                        </div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label class="col-xs-4 control-label"><c:if test="${!sync.proPostLevel}"><span class="star">*</span></c:if> 专业技术职务级别</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="proPostLevel" value="${teacherInfo.proPostLevel}">
                    </div>
                    <c:if test="${!sync.proPostLevel}">
                        <div class="col-xs-6">
                            <input type="checkbox" ${userSync.proPostLevel?"checked":""}
                                   data-name="proPostLevel" class="syncOnce big"/> 仅同步一次
                        </div>
                    </c:if>
                </div>
            </c:if>


            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.mobile}"><span class="star">*</span></c:if> 手机号</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="mobile" value="${ui.mobile}">
                </div>
                <c:if test="${!sync.mobile}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.mobile?"checked":""}
                               data-name="mobile" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><c:if test="${!sync.email}"><span class="star">*</span></c:if> 邮箱</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="email" value="${ui.email}">
                </div>
                <c:if test="${!sync.email}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.email?"checked":""}
                               data-name="email" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
            </div>
            <c:if test="${sysUser.type==USER_TYPE_JZG}">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><c:if test="${!sync.phone}"><span class="star">*</span></c:if> 办公电话</label>

                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="phone" value="${ui.phone}">
                    </div>
                    <c:if test="${!sync.phone}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.phone?"checked":""}
                               data-name="phone" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">短信称谓</label>

                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="msgTitle" value="${ui.msgTitle}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><c:if test="${!sync.homePhone}"><span class="star">*</span></c:if> 家庭电话</label>

                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="homePhone" value="${ui.homePhone}">
                    </div>
                    <c:if test="${!sync.homePhone}">
                    <div class="col-xs-6">
                        <input type="checkbox" ${userSync.homePhone?"checked":""}
                               data-name="homePhone" class="syncOnce big"/> 仅同步一次
                    </div>
                </c:if>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">熟悉专业有何特长</label>

                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="specialty" value="${ui.specialty}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">健康状况</label>

                    <div class="col-xs-6">
                        <select data-rel="select2" name="health"
                                data-placeholder="请选择" data-width="162">
                            <option></option>
                            <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_health').id}"/>
                        </select>
                        <script type="text/javascript">
                            $("select[name=health]").val('${ui.health}');
                        </script>
                    </div>
                </div>
                <div class="form-group">
                  <label class="col-xs-4 control-label" style="line-height: 200px">手写签名</label>
                  <div class="col-xs-8 file" style="width:360px;">
                    <input type="file" name="_sign" id="_sign" />
                      <span class="help-block"> * 为了使显示效果最佳，推荐使用300*200大小的PNG图片</span>
                  </div>
                </div>
            </c:if>
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
<style>
  .ace-file-container{
    height: 200px!important;
  }
  .ace-file-multiple .ace-file-container .ace-file-name .ace-icon{
    line-height: 120px!important;
  }
</style>
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
            name: '${ctx}/avatar?path=${cm:sign(ui.avatar)}'
        }]);
    });
    $("#_avatar").ace_file_input('show_file_list', [{
        type: 'image',
        name: '${ctx}/avatar?path=${cm:sign(ui.avatar)}'
    }]);

      $.fileInput($('#_sign'),{
        style:'well',
        btn_choose:'请选择手写签名',
        btn_change:null,
        no_icon:'ace-icon fa fa-picture-o',
        thumbnail:'large',
        droppable:true,
        previewWidth: 300,
        previewHeight: 198,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
      })
      <c:if test="${not empty ui.sign}">
      $('#_sign').ace_file_input('show_file_list', [{type: 'image',
          name: '${ctx}/sign?userId=${ui.userId}&_='+new Date().getTime()}]);
      </c:if>

    $("#body-content-view button[type=submit]").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            var syncNames = $.map($("input.syncOnce:checked"), function(input){
                console.log(input)
                return $(input).data("name");
            })
            //console.log("syncNames="+ syncNames.join(","))
            $(form).ajaxSubmit({
                data:{syncNames:syncNames.join(",")},
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
</script>