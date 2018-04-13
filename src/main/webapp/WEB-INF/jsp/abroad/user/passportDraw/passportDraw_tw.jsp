<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="body-content">
  <div class="tabbable">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
      <div style="margin-bottom: 8px">

        <div class="buttons">
          <c:if test="${param.auth!='admin'}">
          <a data-url="${ctx}/user/abroad/passportDraw" class="loadPage btn btn-sm btn-success">
            <i class="ace-icon fa fa-backward"></i>
            返回
          </a>
          </c:if>
        <c:if test="${param.auth=='admin'}">
          <a data-url="${ctx}/abroad/passportDraw?type=2" class="loadPage btn btn-sm btn-success">
            <i class="ace-icon fa fa-backward"></i>
            返回
          </a>
        </c:if>
        </div>
      </div>
    </ul>

    <div class="tab-content">
      <div class="tab-pane in active">
<form class="form-horizontal" action="${ctx}/user/abroad/passportDraw_tw_au"
      id="applyForm" method="post" enctype="multipart/form-data">
  <input type="hidden" name="cadreId" value="${param.cadreId}">
  <div class="form-group">
    <label class="col-xs-3 control-label">申请类型</label>
    <div class="col-xs-6 choice label-text">
      <input ${empty passportTw?"disabled":""} name="type"type="radio" value="${ABROAD_PASSPORT_DRAW_TYPE_TW}"> 因公赴台&nbsp;&nbsp;
      <input name="type" type="radio" value="${ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF}"> 长期因公出国
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">出行时间</label>
    <div class="col-xs-2">
      <div class="input-group">
        <input class="form-control date-picker" name="_startDate" type="text"
               data-date-format="yyyy-mm-dd" />
        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
      </div>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">回国时间</label>
    <div class="col-xs-2">
      <div class="input-group">
        <input class="form-control date-picker" name="_endDate" type="text"
               data-date-format="yyyy-mm-dd"/>
        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
      </div>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">因公事由</label>
    <div class="col-xs-9 choice label-text">
      <input name="_reason" type="checkbox" value="学术会议"> 学术会议&nbsp;&nbsp;
      <input name="_reason" type="checkbox" value="考察访问"> 考察访问&nbsp;&nbsp;
      <input name="_reason" type="checkbox" value="合作研究"> 合作研究&nbsp;&nbsp;
      <input name="_reason" type="checkbox" value="进修"> 进修&nbsp;&nbsp;
      <input name="_reason" type="checkbox" value="其他"> 其他
      <input name="_reason_other" type="text">
      <input name="reason" type="hidden"/>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">费用来源</label>
    <div class="col-xs-6 choice  label-text">
      <input  name="_costSource"type="radio" value="自费"> 自费&nbsp;&nbsp;
      <input name="_costSource" type="radio" value="其他来源"> 其他来源
      <input name="_costSource_other" type="text">
      <input name="costSource" type="hidden">
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">批件</label>
    <div class="col-xs-2 file">
      <div class="files">
      <input class="form-control" type="file" name="_files[]" />
      </div>
      <button type="button" onclick="addFile()" class="btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
    </div>
  </div>
  <%--<div class="form-group">
    <label class="col-xs-3 control-label">申请使用证件名称</label>
    <div class="col-xs-6">
      <div style="padding-top: 12px">
      大陆居民往来台湾通行证（<span id="signBtn"><span style="color: darkred">未申请办理签注</span></span>）
      </div>
      <input type="hidden" name="needSign" value="0">
    </div>
  </div>--%>

  <div class="well center" style="margin-top: 20px; font-size: 20px">
    <div class="row" style="padding-left: 50px">
      <input type="hidden" name="needSign" value="0">
      <c:if test="${fn:length(passports)==0}">
        您没有因私出国（境）证件
      </c:if>
      <c:if test="${fn:length(passports)>0}">
        <div style="float: left; font-weight: bolder">申请使用证件名称：</div>
      </c:if>
      <c:forEach items="${passports}" var="passport">
        <c:set var="passportType" value="${cm:getMetaType(passport.classId)}"/>
        <div style="float: left; margin-right: 40px;">
          <input type="checkbox" class="big" name="passportId" value="${passport.id}"
                 data-passport-type="${passportType.code}"> ${passportType.name}
          <c:if test="${passportType.code != 'mt_passport_normal'}">
           <span class="signBtn"><span class="label" style="vertical-align: 4px; margin-left: 10px">未申请办理签注</span></span>
          </c:if>
        </div>
      </c:forEach>
    </div>
  </div>
</form>

<div class="modal-footer center">
  <input id="next" data-url="${ctx}/user/abroad/passportDraw_self_sign?type=tw&cadreId=${param.cadreId}&auth=${param.auth}"
         class="btn btn-primary" value="下一步"/>
  <input id="submit" type="button" style="display: none" class="btn btn-success" value="提交申请"/>
  <c:if test="${param.auth!='admin'}">
  <input class="loadPage btn btn-default" value="取消" data-url="${ctx}/user/abroad/passportDraw"/>
  </c:if>
</div>
        </div></div></div>
  </div>
<div id="body-content-view">
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

  #applyForm  .control-label{
    font-size: 20px;
    font-weight: bolder;
  }

  #applyForm .form-group{
    padding-bottom: 5px;
    padding-top:0px!important;
  }
</style>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

  $("input[type=radio][name=type]").click(function(){
    $(".signBtn").html('<span class="label" style="vertical-align: 4px; margin-left: 10px">未申请办理签注</span>');
    $("input[name=needSign]").val(0);
      if($(this).val()=='${ABROAD_PASSPORT_DRAW_TYPE_TW}'){
        $("[data-passport-type=mt_passport_tw]").prop("checked", true);
        $("[data-passport-type=mt_passport_normal], [data-passport-type=mt_passport_hk]").attr("disabled", "disabled");
      }else{
        $("input[type=checkbox][name=passportId]").prop("checked", false).prop("disabled", false);
      }
  });
  $("input[type=checkbox][name=passportId]").click(function(){
    $(".signBtn").html('<span class="label" style="vertical-align: 4px; margin-left: 10px">未申请办理签注</span>');
    $("input[name=needSign]").val(0);
    $("#next").val('下一步');
    if($(this).prop("checked")){
      $("input[type=checkbox][name=passportId]").not(this).prop("checked", false);
    }
    if($(this).data("passport-type")=='mt_passport_normal'){
        $("#next").hide();
        $("#submit").show();
    }else{
        $("#next").show();
        $("#submit").hide();
    }
  });

  $.fileInput($('input[type=file]'))
  function addFile(){
    var _file = $('<input class="form-control" type="file" name="_files[]" />');
    $(".files").append(_file);
    $.fileInput(_file)
    return false;
  }


  $("#next").click(function(){
    if($("#applyForm").valid() && formValid()) {
      var $container = $("#body-content");
      $container.showLoading({'afterShow':
              function() {
                setTimeout( function(){
                  $container.hideLoading();
                }, 2000 );
              }});
        $.get($(this).data("url"), {passportId:$('input[name=passportId]:checked').val()}, function (html) {
          $container.hideLoading().hide();
          $("#body-content-view").hide().html(html).fadeIn("slow");
        })
      }
  })

  function formValid(){
    if($("input[name=type]:checked").length==0){
      SysMsg.info("请选择申请类型");
      return false;
    }
    if($("input[name=type]:checked").val()=='${ABROAD_PASSPORT_DRAW_TYPE_TW}'){
      if($.trim("${passportTw}")==''){
        SysMsg.info("您还未提交大陆居民往来台湾通行证");
        return false;
      }
    }

    if($.trim($("input[name=_startDate]").val())==''){
      SysMsg.info("请选择出行时间", '', function(){
        $("input[name=_startDate]").val('').focus();
      });
      return false;
    }
    if($.trim($("input[name=_endDate]").val())==''){
      SysMsg.info("请选择回国时间", '', function(){
        $("input[name=_endDate]").val('').focus();
      });
      return false;
    }
      // 出国（境）事由
      var $_reason = $("input[name=_reason][value='其他']");
      var _reason_other = $("input[name=_reason_other]").val().trim();
      if($_reason.is(":checked")){
        if(_reason_other==''){
          SysMsg.info("请输入其他出访事由", '', function(){
            $("input[name=_reason_other]").val('').focus();
          });
          return false;
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
        SysMsg.info("请选择出访事由");
        return false;
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
          return false;
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
        return false;
      }
      $("input[name=costSource]").val(costSources.join("+++"));

      // 所需批件
      var fileCount = 0;
      $.each($("input[type=file]"), function(){
        if($(this).val()!='') fileCount++;
      });
      if(fileCount==0){
        SysMsg.info("请上传批件");
        return false;
      }
      // 所需证件
      var $passportId = $('input[name=passportId]:checked');
      var passportId = $passportId.val();
      if(passportId==undefined || passportId==''){
        SysMsg.info("请选择需要的证件");
        return;
      }

      return true;
  }
  $("#submit").click(function(){$("#applyForm").submit();return false;});
  $("#applyForm").validate({
    submitHandler: function (form) {

     if(!formValid()) return false;

      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            SysMsg.success('操作成功。', '成功', function(){
              <c:if test="${param.auth=='admin'}">
              $.hashchange("type=2", "${ctx}/abroad/passportDraw")
              </c:if>
              <c:if test="${param.auth!='admin'}">
              $.hashchange("type=2", "${ctx}/user/abroad/passportDraw")
              </c:if>
            });
          }
        }
      });
    }
  });
  $('#applyForm [data-rel="select2"]').select2();
  $('[data-rel="tooltip"]').tooltip();
  $.register.date($('.date-picker'))
  $.register.user_select($('[data-rel="select2-ajax"]'));
  $('textarea.limited').inputlimiter();
</script>