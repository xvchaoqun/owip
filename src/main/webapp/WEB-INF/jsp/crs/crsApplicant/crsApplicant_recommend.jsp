<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
  <div class="au">
    <div id="dispatch-cadres-view">
      <div class="widget-box">
        <div class="widget-header">
          <h4 class="smaller">
            推荐/自荐
            <div class="buttons pull-right ">
              <a href="javascript:;" class="btn btn-xs btn-success" onclick="_back()"
                 style="margin-right: 10px; top: -5px;">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
            </div>
          </h4>
        </div>
        <div class="widget-body">
          <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/crsApplicant_recommend" id="modalForm" method="post"
                  enctype="multipart/form-data">
              <div class="row">
                <input type="hidden" name="id" value="${crsApplicant.id}">
                <input type="hidden" name="file">

                <div class="form-group">
                  <label class="col-xs-3 control-label">推荐/自荐</label>
                  <div class="col-xs-6 label-text"  style="font-size: 15px;">
                    <input type="checkbox" class="big" name="isRecommend"
                    ${crsApplicant.isRecommend?"checked":""} data-off-text="自荐" data-on-text="推荐"/>
                  </div>
                </div>
                <div class="form-group recommend">
                  <label class="col-xs-3 control-label">组织推荐</label>
                  <div class="col-xs-6">
                    <input class="form-control" type="text" name="recommendOw"
                           value="${crsApplicant.recommendOw}">
                  </div>
                </div>
                <div class="form-group recommend">
                  <label class="col-xs-3 control-label">干部推荐</label>
                  <div class="col-xs-6">
                    <input class="form-control" type="text" name="recommendCadre"
                           value="${crsApplicant.recommendCadre}">
                  </div>
                </div>
                <div class="form-group recommend">
                  <label class="col-xs-3 control-label">群众推荐</label>
                  <div class="col-xs-6">
                    <input class="form-control" type="text" name="recommendCrowd"
                           value="${crsApplicant.recommendCrowd}">
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
  <div class="preview" style="margin-left: 25px; margin-right: 0px;">
    <div class="widget-box">
      <div class="widget-header">
        <h4 class="smaller">
          pdf扫描件预览
          <div style="position: absolute; left:125px;top:8px;">
            <form action="${ctx}/crsApplicant_recommend_upload"
                  enctype="multipart/form-data" method="post"
                  class="btn-upload-form">
              <button type="button"
                      data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                      class="btn btn-xs btn-primary">
                <i class="ace-icon fa fa-upload"></i>
                上传pdf扫描件
              </button>
              <input type="file" name="file" id="upload-file"/>
            </form>
          </div>

        </h4>
      </div>
      <div class="widget-body">
        <div class="widget-main">
          <div id="dispatch-file-view">
            <c:import url="${ctx}/swf/preview?type=html&path=${crsApplicant.recommendPdf}"/>
          </div>
        </div>
      </div>
    </div>
  </div>

</div>
<script>
  $("#modal :checkbox").bootstrapSwitch();
  function isRecommendChange(){
    //console.log($("input[name=isRecommend]").bootstrapSwitch("state"))
    if($("input[name=isRecommend]").bootstrapSwitch("state")) {
      $(".preview, .form-group.recommend").show();
    }else {
      $(".preview, .form-group.recommend").hide();
    }
  }
  $('input[name=isRecommend]').on('switchChange.bootstrapSwitch', function(event, state) {
    isRecommendChange();
  });
  isRecommendChange();

  $("#upload-file").change(function () {
    if ($("#upload-file").val() != "") {
      var $this = $(this);
      var $form = $this.closest("form");
      var $btn = $("button", $form).button('loading');
      var viewHtml = $("#dispatch-file-view").html()
      $("#dispatch-file-view").html('<img src="${ctx}/img/loading.gif"/>')
      $form.ajaxSubmit({
        success: function (ret) {
          if (ret.success) {
            //console.log(ret)
            $("#dispatch-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.file));

            $("#modalForm input[name=file]").val(ret.file);
            $("#modalForm input[name=fileName]").val(ret.fileName);
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

  function _back(){
    $("#step-content li.active .loadPage").click();
  }

  $("#modalForm").validate({
    submitHandler: function (form) {

      var isRecommend = $("input[name=isRecommend]").bootstrapSwitch("state");
      if(isRecommend){
        var file = $("#modalForm input[name=file]").val();
        if($.trim(file)==''){
          $.tip({$target:$(".btn-upload-form"), my:'top center', at:'bottom center', msg:"请上传pdf扫描文件"})
          return;
        }
        var recommendOw = $("#modalForm input[name=recommendOw]").val();
        var recommendCadre = $("#modalForm input[name=recommendCadre]").val();
        var recommendCrowd = $("#modalForm input[name=recommendCrowd]").val();
        if($.trim(recommendOw)=='' && $.trim(recommendCadre)=='' && $.trim(recommendCrowd)==''){
          $.tip({$target:$('#dispatch-cadres-view [type="submit"]'), msg:"请填写推荐内容"})
          return;
        }
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