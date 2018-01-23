<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row passport_apply" style="width:1500px">
  <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <div style="margin-bottom: 8px">

      <div class="buttons">
        <a href="javascript:" class="hideView btn btn-sm btn-success">
          <i class="ace-icon fa fa-backward"></i>
          返回
        </a>
        <button id="print" class="btn btn-info btn-sm"
                style="margin-left: 50px" ><i class="fa fa-print"></i>  打印接收单</button>

        <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_CADREADMIN}">
         <span style="padding-left: 20px;">接收人</span>
          <input type="text" name="receiver" value="${scMatterAccess.receiver}">
          <button id="submit" class="btn btn-warning btn-sm"
                  data-rel="tooltip" data-placement="bottom"
                  title="点击即提交签字拍照文件"><i class="fa fa-upload"></i>  确认办理</button>
        </shiro:hasAnyRoles>
        <div class="pull-right" style="margin-right: 450px;">
          <a href="javascript:" onclick="opencam()" class="btn btn-primary btn-sm">
            <i class="fa fa-camera"></i>
            点此拍照</a>
          <a class="btn btn-warning btn-sm" onclick="_rotate()"><i class="fa fa-rotate-right"></i> 旋转</a>
        </div>
      </div>
    </div>
  </ul>
  <div class="preview" style="margin: 20px 5px 50px 0px;">
    <img data-src="${ctx}/sc/scMatterAccess_report?id=${param.id}&format=image&_=<%=new Date().getTime()%>" src="${ctx}/img/loading.gif"
         onload="lzld(this)"/>
  </div>
  <div class="info" style="margin-top: 20px; margin-bottom: 50px; padding-left: 5px;width: 850px">
    <form class="form-horizontal" action="${ctx}/sc/scMatterAccess_query" id="modalForm" method="post"  enctype="multipart/form-data">
      <input type="hidden" name="id" value="${param.id}">
      <div class="form-group">
        <div class="col-xs-12 file" style="height: 842px">
          <input required type="file" name="_cancelPic" />
          <input type="hidden" name="_base64">
          <input type="hidden" name="_rotate">
        </div>
      </div>
    </form>
  </div>
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
    <a href="javascript:" class="btn btn-success" onclick="snap()"><i class="fa fa-camera" aria-hidden="true" ></i> 拍照</a>
    <a href="javascript:" class="btn btn-default" onclick="closecam()"><i class="fa fa-close" aria-hidden="true" ></i> 取消</a>
  </div>
</div>
<style>
  /*.ace-file-multiple .ace-file-container{
      height: 840px;
  }*/
  .ace-file-multiple .ace-file-container .ace-file-name .ace-icon{
    margin-top: -80px;
    line-height: 380px;
  }
  .ace-file-multiple .ace-file-container:before{
    line-height: 220px;
    font-size: 28pt;
  }
</style>
<script src="${ctx}/extend/js/webcam.min.js"></script>
<script src="${ctx}/extend/js/jQueryRotate.js"></script>
<%--<script src="${ctx}/extend/js/jquery.jqprint-0.3.js"></script>--%>
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
      $('input[type=file][name=_cancelPic]').ace_file_input('show_file_list', [
        {type: 'image', name: '签字拍照.jpg', path: data_uri}]);
    } );
    try {
      Webcam.reset();
    }catch(e){

    }
    $(".webcam-container").modal('hide');
  }
  function closecam(){
    try {
      Webcam.reset();
    }catch(e){

    }
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


  $("#print").click(function(){
    $.print("${ctx}/sc/scMatterAccess_report?id=${param.id}&format=pdf");
  });

  $.fileInput($('input[type=file]'),{
    style:'well',
    btn_choose:'请选择签字拍照',
    btn_change:null,
    no_icon:'ace-icon fa fa-picture-o',
    thumbnail:'fit',
    droppable:true,
    //previewWidth: 840,
    //previewHeight: 560,
    allowExt: ['jpg', 'jpeg', 'png', 'gif'],
    allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif'],
    before_change:function(){
      reset_rotate();
      //console.log("==============change")
      return true;
    },
    before_remove:function(){
      reset_rotate();
      //console.log("==============remove")
      $("input[name=_base64]").val('');
      return true;
    }
  })/*.end().find('button[type=reset]').on(ace.click_event, function(){
   $('input[type=file]').ace_file_input('reset_input');
   });*/
  $("#submit").click(function(){
    if($('input[type=file]').val()=='' && $("input[name=_base64]").val()==''){
      SysMsg.info("请选择签字图片或进行拍照。");
      return;
    }
    var receiver = $.trim($('input[name=receiver]').val());
    if(receiver==''){
      SysMsg.info("请输入接收人。");
      return;
    }
    $("#modalForm").ajaxSubmit({
      data:{receiver:receiver},
      success:function(ret){
        if(ret.success){
          //SysMsg.success('提交成功。', '成功', function(){
          page_reload();
          //});
        }
      }
    });
  });
  $('[data-rel="tooltip"]').tooltip();
</script>