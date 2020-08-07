<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div>
    <h3>上传证件首页</h3>
</div>
<div class="modal-body">
    <div class="well" style="margin-top: 20px;/* font-size: 20px*/">
            <form class="form-horizontal" action="${ctx}/abroad/passport_uploadPic"
                  autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
                <c:set var="passport" value="${cm:getPassport(param.id)}"/>
                <c:set var="passportType" value="${cm:getMetaType(passport.classId)}"/>
                <div class="row">
                <div class="col-xs-6" style="width: 300px;">
                <input type="hidden" value="${param.id}" name="id">
                <div class="form-group">
                    <label class="col-xs-4 control-label">姓名</label>
                    <div class="col-xs-6 label-text">
                        ${passport.user.realname}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">证件名称</label>
                    <div class="col-xs-8 label-text" style="font-size: 14px">
                        ${passportType.name}
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-4 control-label">证件号码</label>
                    <div class="col-xs-6 label-text">
                        ${passport.code}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">发证日期</label>
                    <div class="col-xs-6 label-text">
                        ${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">有效期</label>
                    <div class="col-xs-6 label-text">
                        ${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}
                    </div>
                </div>
                </div>
                <div class="col-xs-6" style="width: 430px; margin-left: 50px;">
                    <div class="form-group">
                        <input type="file" name="_pic" />
                        <input type="hidden" name="_base64">
                        <input type="hidden" name="_rotate">
                        <div class="pull-right">或
                            <a href="javascript:;" onclick="opencam()" class="btn btn-primary btn-xs">
                                <i class="fa fa-camera"></i>
                                点此拍照</a>
                            <a class="btn btn-warning btn-xs" onclick="_rotate()"><i class="fa fa-rotate-right"></i> 旋转</a>
                        </div>
                    </div>
                </div>
                </div>
            </form>

    </div>
</div>
<div class="modal-footer center" style="margin-bottom: 25px;">

    <input type="submit" class="btn btn-success" style="width: 200px" value="确认"/>
    <input type="button" class="hideView btn btn-default" style="width: 200px" value="返回"/>
</div>

<div class="webcam-container modal">
    <div class="modal-header">
        <button type="button" onclick="closecam()" class="close">&times;</button>
        <h3>点击允许，打开摄像头</h3>
    </div>
    <div class="modal-body">
    <div id="my_camera"></div>
        </div>
    <div class="modal-footer">
        <a href="javascript:;" class="btn btn-success" onclick="snap()"><i class="fa fa-camera" aria-hidden="true" ></i> 拍照</a>
        <a href="javascript:;" class="btn btn-default" onclick="closecam()"><i class="fa fa-close" aria-hidden="true" ></i> 取消</a>
    </div>
</div>

<style>
    /*.ace-file-multiple .ace-file-container{
        height: 350px;
    }*/
    .tags{
        width: 300px;
    }
    .ace-file-multiple .ace-file-container .ace-file-name .ace-icon{
        line-height: 260px;
        margin-top: -50px;
    }
    .ace-file-multiple .ace-file-container:before{
        line-height: 120px;
        font-size: 20pt;
    }
</style>
<script src="${ctx}/extend/js/webcam.min.js"></script>
<script src="${ctx}/extend/js/jQueryRotate.js"></script>
<script>

    var i=1;
    function _rotate(){
        $(".ace-file-input img").rotate(90*i);
        $("input[name=_rotate]").val(90*i);
        i++;
        //console.log($(".ace-file-input img").attr("src"))
    }
    function reset_rotate(){
        //alert("reset")
        i=1;
        $("input[name=_rotate]").val('');
    }

    function snap(){
        Webcam.snap( function(data_uri) {
            //console.log(data_uri)
            reset_rotate();
            $("input[name=_base64]").val(data_uri);
            $('input[type=file][name=_pic]').ace_file_input('show_file_list', [
                {type: 'image', name: '证件首页拍照.jpg', path: data_uri}]);
        } );
        Webcam.reset();
        $(".webcam-container").modal('hide');
    }
    function closecam(){
        Webcam.reset();
        $(".webcam-container").modal('hide');
    }

    function opencam(){

        Webcam.set({
            width: 640,
            height: 480,
            //force_flash: true,
            //flip_horiz:true,
            image_format: 'jpeg',
            jpeg_quality: 100
        });

        Webcam.attach('#my_camera');

        $(".webcam-container").modal('show').draggable({handle :".modal-header"});
    }

    $.fileInput($('input[type=file][name=_pic]'),{
        style:'well',
        btn_choose:'请点击选择证件首页图片',
        btn_change:null,
        no_icon:'ace-icon fa fa-picture-o',
        thumbnail:'fit',
        droppable:true,
        //previewWidth: 420,
        //previewHeight: 280,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif'],
        before_change:function(){
            reset_rotate();
            return true;
        },
        before_remove:function(){
            reset_rotate();
            $("input[name=_base64]").val('');
            return true;
        }
    })/*.end().find('button[type=reset]').on(ace.click_event, function(){

        $('input[type=file][name=_pic]').ace_file_input('reset_input');
    })*/
    <c:if test="${not empty passport.pic}">
    $('input[type=file][name=_pic]').ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/pic?path=${cm:sign(passport.pic)}&_=<%=new Date().getTime()%>'}]);
    </c:if>
    $("input[type=submit]").click(function(){
        $("#modalForm").submit();return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        bootbox.hideAll();
                        $.hideView();
                    }
                }
            });
        }
    });
</script>
