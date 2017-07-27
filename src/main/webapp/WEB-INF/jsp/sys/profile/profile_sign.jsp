<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row">
  <div class="col-xs-12">
    <div id="user-profile-3" class="user-profile row">
      <div class="col-sm-offset-1 col-sm-10">

        <div class="space"></div>

        <form class="form-horizontal" action="${ctx}/profile_sign" id="signForm" method="post" enctype="multipart/form-data">
          <div class="tabbable">
            <jsp:include page="menu.jsp"/>

            <div class="tab-content profile-edit-tab-content">
<div class="modal-body">
    <div class="form-group">
      <label class="col-xs-3 control-label" style="line-height: 200px">手写签名</label>
      <div class="col-xs-2 file" style="width:360px;">
        <input required type="file" name="sign" />

      </div>
      <div style="float: left;line-height: 240px"> * 为了使显示效果最佳，推荐使用300*200大小的图片</div>
    </div>
  <div class="form-group" style="padding-top: 20px">
    <label class="col-xs-3 control-label">办公电话</label>
    <div class="col-xs-2">
      <input required class="form-control" type="text" name="phone" value="${_user.phone}">
    </div>
  </div>
    <div class="form-group" style="padding-top: 20px">
      <label class="col-xs-3 control-label">手机号</label>
      <div class="col-xs-2">
        <input required class="form-control" type="text" name="mobile" value="${_user.mobile}">
      </div>
    </div>
</div>

            </div>
          </div>

          <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9">
              <button  id="submit" class="btn btn-info" type="button">
                <i class="ace-icon fa fa-check bigger-110"></i>
                保存
              </button>
              &nbsp; &nbsp;
              <button class="btn" type="reset">
                <i class="ace-icon fa fa-undo bigger-110"></i>
                重置
              </button>
            </div>
          </div>
        </form>
      </div><!-- /.span -->
    </div>
  </div>
</div>
<style>
  .ace-file-container{
    height: 200px!important;
  }
  .ace-file-multiple .ace-file-container .ace-file-name .ace-icon{
    line-height: 120px!important;
  }
  .ace-file-multiple .ace-file-container .ace-file-name.large{
    border-bottom: none!important;
  }
  .ace-file-multiple .ace-file-container .ace-file-name.large:after{
    display: none!important;
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
    previewHeight: 198,
    allowExt: ['jpg', 'jpeg', 'png', 'gif'],
    allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
  })
  $('#signForm button[type=reset]').on(ace.click_event, function(){
    <c:if test="${not empty _user.sign}">
    $('input[type=file]').ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/sign'}]);
    </c:if>
  });
  <c:if test="${not empty _user.sign}">
  $('input[type=file]').ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/sign'}]);
  </c:if>
  $("#submit").click(function(){

    $("#signForm").ajaxSubmit({
      success:function(ret){
        if(ret.success){
          SysMsg.success('保存成功。', '成功', function(){

          });
        }
      }
    });
  });
</script>