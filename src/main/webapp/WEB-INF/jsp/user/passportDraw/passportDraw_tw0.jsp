<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="body-content">
<form class="form-horizontal" action="${ctx}/user/passportDraw_tw_au" id="applyForm" method="post" enctype="multipart/form-data">
  <div class="form-group">
    <label class="col-xs-3 control-label">出行时间</label>
    <div class="col-xs-2">
      <div class="input-group">
        <input required class="form-control date-picker" name="_startDate" type="text"
               data-date-format="yyyy-mm-dd" />
        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
      </div>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">回国时间</label>
    <div class="col-xs-2">
      <div class="input-group">
        <input required class="form-control date-picker" name="_endDate" type="text"
               data-date-format="yyyy-mm-dd" />
        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
      </div>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">出访事由</label>
    <div class="col-xs-6 choice">
      <input name="_reason" type="checkbox" value="旅游"> 学术会议&nbsp;&nbsp;
      <input name="_reason" type="checkbox" value="探亲"> 考察访问&nbsp;&nbsp;
      <input name="_reason" type="checkbox" value="访友"> 合作研究&nbsp;&nbsp;
      <input name="_reason" type="checkbox" value="继承"> 进修&nbsp;&nbsp;
      <input name="_reason" type="checkbox" value="其他"> 其他
      <input name="_reason_other" type="text">
      <input name="reason" type="hidden"/>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">费用来源</label>
    <div class="col-xs-6 choice">
      <input  name="_costSource"type="radio" value="自费"> 自费&nbsp;&nbsp;
      <input name="_costSource" type="radio" value="其他来源"> 其他来源
      <input name="_costSource_other" type="text">
      <input name="costSource" type="hidden">
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">国台办批件</label>
    <div class="col-xs-2 file">
      <div class="files">
      <input class="form-control" type="file" name="_files[]" />
      </div>
      <button type="button" onclick="addFile()" class="btn btn-default btn-mini btn-xs"><i class="fa fa-plus"></i></button>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">台湾通行证大陆居民往来台湾通行证</label>
    <div class="col-xs-6">
      <div id="signBtn">
      <button type="button" data-url="${ctx}/user/passportDraw_self_sign?type=tw"
              class="openView btn btn-primary btn-mini btn-xs">申请台湾签注</button>
      </div>
      <input type="hidden" name="needSign" value="0">
    </div>
  </div>

</form>
<div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding: 50px;">
  <input id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
</div>
<div class="modal-footer center">
  <input id="submit" class="btn btn-success" value="提交申请"/>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <input class="btn btn-default" value="取消" onclick="location.href='${ctx}/user/passportDraw'"/>
</div>
  </div>
<div id="item-content">
</div>
<style>

  input[type=radio], input[type=checkbox]{
    width: 20px;
    height: 20px;
    _vertical-align: -1px;/*针对IE6使用hack*/
    vertical-align: -3px;
  }
  .choice{
    font-size: 20px;
  }
  .form-group{
    padding-bottom: 10px;
  }
  .file label{
    margin-bottom:15px;
  }

</style>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>
  ace_file_input($('input[type=file]'))
  function ace_file_input($file){
    $file.ace_file_input({
      no_file:'请选择文件 ...',
      btn_choose:'选择',
      btn_change:'更改',
      droppable:false,
      onchange:null,
      thumbnail:false //| true | large
      //whitelist:'gif|png|jpg|jpeg'
      //blacklist:'exe|php'
      //onchange:''
      //
    });
  }
  function addFile(){
    var _file = $('<input class="form-control" type="file" name="_files[]" />');
    $(".files").append(_file);
    ace_file_input(_file);
    return false;
  }

  $("#submit").click(function(){$("#applyForm").submit();return false;});
  $("#applyForm").validate({
    submitHandler: function (form) {

      // 出国（境）事由
      var $_reason = $("input[name=_reason][value='其他']");
      var _reason_other = $("input[name=_reason_other]").val().trim();
      if($_reason.is(":checked")){
        if(_reason_other==''){
          SysMsg.info("请输入其他出国（境）事由", '', function(){
            $("input[name=_reason_other]").val('').focus();
          });
          return;
        }
      }
      var reasons = [];
      $.each($("input[name=_reason]:checked"), function(){
        if($(this).val()=='其他'){
          reasons.push("其他:"+_reason_other);
        }else
          reasons.push($(this).val());
      });
      if(reasons.length==0){
        SysMsg.info("请选择出国（境）事由");
        return;
      }
      $("input[name=reason]").val(reasons.join("+++"));

      // 费用来源
      var $_costSource = $("input[name=_costSource][value='其他来源']");
      var _costSource_other = $("input[name=_costSource_other]").val().trim();
      if($_costSource.is(":checked")){
        if(_costSource_other==''){
          SysMsg.info("请输入其他费用来源", '', function(){
            $("input[name=_costSource_other]").val('').focus();
          });
          return;
        }
      }
      var costSources = [];
      $.each($("input[name=_costSource]:checked"), function(){
        if($(this).val()=='其他来源'){
          costSources.push("其他来源:"+_costSource_other);
        }else
          costSources.push($(this).val());
      });
      if(costSources.length==0){
        SysMsg.info("请选择费用来源");
        return;
      }
      $("input[name=costSource]").val(costSources.join("+++"));

      // 所需国台办批件
      var fileCount = 0;
      $.each($("input[type=file]"), function(){
        if($(this).val()!='') fileCount++;
      });
      if(fileCount==0){
        SysMsg.info("请上传国台办批件");
        return;
      }

      if($("#agree").is(":checked") == false){
        $('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
        return false;
      }

      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            SysMsg.success('操作成功。', '成功', function(){
               location.href = "${ctx}/user/passportDraw?type=2";
            });
          }
        }
      });
    }
  });
  $('#applyForm [data-rel="select2"]').select2();
  $('[data-rel="tooltip"]').tooltip();
  $('.date-picker').datepicker({
    language:"zh-CN",
    autoclose: true,
    todayHighlight: true
  })
  register_user_select($('[data-rel="select2-ajax"]'));
  $('textarea.limited').inputlimiter();
</script>