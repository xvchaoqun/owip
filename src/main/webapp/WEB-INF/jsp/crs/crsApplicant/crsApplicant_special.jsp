<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
  <div class="preview">
    <div class="widget-box">
      <div class="widget-header">
        <h4 class="widget-title">
          证明文件预览
          <div style="position: absolute; left:135px;top:0px;">
            <form action="${ctx}/crsApplicant_special_upload"
                  enctype="multipart/form-data" method="post"
                  class="btn-upload-form">
              <button type="button"
                      data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                      class="hideView btn btn-xs btn-primary">
                <i class="ace-icon fa fa-upload"></i>
                上传pdf证明文件
              </button>
              <input type="file" name="file" id="upload-file"/>
            </form>
          </div>
          <div class="buttons pull-right ">

            <a href="javascript:;" class="btn btn-xs btn-success"  onclick="_back()"
               style="margin-right: 10px; top: -5px;">
              <i class="ace-icon fa fa-backward"></i>
              返回</a>
          </div>
        </h4>
      </div>
      <div class="widget-body">
        <div class="widget-main">
          <div id="dispatch-file-view">
            <c:import url="${ctx}/pdf_preview?type=html&path=${crsApplicant.specialPdf}"/>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="au">
    <div id="dispatch-cadres-view">
      <div class="widget-box">
        <div class="widget-header">
          <h4 class="widget-title">
            破格信息
          </h4>
        </div>
        <div class="widget-body">
          <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/crsApplicant_special" autocomplete="off" disableautocomplete id="modalForm" method="post"
                  enctype="multipart/form-data">
              <div class="row">
                <input type="hidden" name="id" value="${crsApplicant.id}">
                <input type="hidden" name="filePath" value="${crsApplicant.specialPdf}">
                <input type="hidden" name="specialStatus" value="1">
                <div class="form-group">
                  <label class="col-xs-3 control-label"><span class="star">*</span>备注</label>

                  <div class="col-xs-6">
                    <textarea required class="form-control limited" name="specialRemark" rows="10">${crsApplicant.specialRemark}</textarea>
                  </div>
                </div>
              </div>
              <div class="clearfix form-actions center">
                <button class="btn btn-info btn-sm" type="submit">
                  <i class="ace-icon fa fa-check "></i>
                  确定
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

    </div>
  </div>
</div>
<script>

  function _back(){
    $("#step-content li.active .loadPage").click();
  }

  $("#upload-file").change(function () {
    if ($("#upload-file").val() != "") {
      var $this = $(this);
      var $form = $this.closest("form");
      var $btn = $("button", $form).button('loading');
      var viewHtml = $("#dispatch-file-view").html()
      $("#dispatch-file-view").html('<img src="${ctx}/img/loading.gif"/>');
      $form.ajaxSubmit({
        success: function (ret) {
          if (ret.success) {
            //console.log(ret)
            $("#dispatch-file-view").load("${ctx}/pdf_preview?type=html&path=" + encodeURI(ret.file));

            $("#modalForm input[name=filePath]").val(ret.file);
            //$("#modalForm input[name=fileName]").val(ret.fileName);
          }else{
            $("#dispatch-file-view").html(viewHtml)
          }
          $btn.button('reset');
          $this.removeAttr("disabled");
        }
      });
      $this.attr("disabled", "disabled");
    }
  });

  $("#modalForm").validate({
    submitHandler: function (form) {
      var file = $("#modalForm input[name=filePath]").val();
      if($.trim(file)==''){
        $.tip({$target:$(".btn-upload-form"), my:'top center', at:'bottom center', msg:"请上传pdf证明文件"})
        return;
      }
      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            _back();
          }
        }
      });
    }
  });
</script>