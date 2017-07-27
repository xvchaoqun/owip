<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <h3>完善信息</h3>
</div>
<div class="modal-body">
<form class="form-horizontal" action="${ctx}/profile_sign" id="signForm" method="post" enctype="multipart/form-data">
  <div class="form-group">
    <label class="col-xs-3 control-label" style="line-height: 200px">手写签名</label>
    <div class="col-xs-2 file" style="width:360px;height: 200px">
        <input required type="file" name="sign" />

    </div>
    <span class="help-block" style="line-height: 200px">为了使显示效果最佳，推荐使用300*200大小的图片</span>
  </div>
    <div class="form-group" style="padding-top: 20px">
        <label class="col-xs-3 control-label">办公电话</label>
        <div class="col-xs-2">
            <input required class="form-control" type="text" name="phone" value="${_user.phone}">
        </div>
    </div>
  <div class="form-group" style="padding-top: 20px">
    <label class="col-xs-3 control-label">手机号码</label>
    <div class="col-xs-2">
      <input required class="form-control" type="text" name="mobile" value="${_user.mobile}">
    </div>
  </div>
</form>
</div>
<div class="modal-footer center">
  <input id="submit" class="btn btn-success" value="提交"/>
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
    $.fileInput($('input[type=file]'),{
      style:'well',
      btn_choose:'请选择手写签名',
      btn_change:null,
      no_icon:'ace-icon fa fa-picture-o',
      thumbnail:'large',
      droppable:true,
      previewWidth: 300,
      previewHeight: 200,
      allowExt: ['jpg', 'jpeg', 'png', 'gif'],
      allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    })
    /*$('#signForm button[type=reset]').on(ace.click_event, function(){
      $('input[type=file]').ace_file_input('reset_input');
    });*/
  //$('input[type=file]').ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/sign'}]);

  $("#submit").click(function(){
    if($('input[type=file]').val()==''){
      SysMsg.info("请选择手写签名图片");
      return;
    }
      if($.trim($('input[name=phone]').val())==''){
          SysMsg.info("请填写办公电话","",function(){
              $('input[name=phone]').val('').focus()
          });
          return;
      }
  if($.trim($('input[name=mobile]').val())==''){
      SysMsg.info("请填写手机号","",function(){
          $('input[name=mobile]').val('').focus()
      });
      return;
  }
    $("#signForm").ajaxSubmit({
      success:function(ret){
        if(ret.success){
          //SysMsg.success('提交成功。', '成功', function(){
            $.hashchange("", "${ctx}/passportApply")
          //});
        }
      }
    });
  });
</script>