<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<form class="form-horizontal" action="${ctx}/user/passportDraw_other_au" id="applyForm" method="post" enctype="multipart/form-data">
  <div class="form-group">
    <label class="col-xs-3 control-label">所需证件</label>
    <div class="col-xs-6">
      <select required data-rel="select2" name="passportId" data-placeholder="请选择">
        <option></option>
        <c:forEach items="${passports}" var="passport">
          <c:set var="passportType" value="${cm:getMetaType('mc_passport_type', passport.classId)}"/>
          <option value="${passport.id}">${passportType.name}</option>
        </c:forEach>
      </select>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">申请事由</label>
    <div class="col-xs-3">
      <textarea required class="form-control limited" type="text" name="reason" rows="3"></textarea>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">开始日期</label>
    <div class="col-xs-2">
      <div class="input-group">
        <input required class="form-control date-picker" name="_startDate" type="text"
               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportDraw.startDate,'yyyy-MM-dd')}" />
        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
      </div>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">结束日期</label>
    <div class="col-xs-2">
      <div class="input-group">
        <input required class="form-control date-picker" name="_endDate" type="text"
               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportDraw.endDate,'yyyy-MM-dd')}" />
        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
      </div>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">其他说明材料</label>
    <div class="col-xs-2 file">
      <div class="files">
        <input class="form-control" type="file" name="_files[]" />
      </div>
      <button type="button" onclick="addFile()" class="btn btn-default btn-mini btn-xs"><i class="fa fa-plus"></i></button>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">备注</label>
    <div class="col-xs-3">
      <textarea class="form-control limited" type="text" name="remark" rows="3"></textarea>
    </div>
  </div>
</form>
<div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding: 50px;">
  <input id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
</div>
<div class="modal-footer center">
  <input id="submit" class="btn btn-success" value="提交申请"/>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <input class="closeView btn btn-default" value="返回"/>
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

  $("#submit").click(function(){ $("#applyForm").submit(); return false; });
  $("#applyForm").validate({
    submitHandler: function (form) {
      if($("#agree").is(":checked") == false){
        $('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
        return false;
      }
      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            SysMsg.success('操作成功。', '成功', function(){
              location.href = "${ctx}/user/passportDraw?type=3";
            });
          }
        }
      });
    }
  });
  $('textarea.limited').inputlimiter();
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