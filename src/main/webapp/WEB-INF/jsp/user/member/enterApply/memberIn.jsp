<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div  style="padding-top: 50px;"></div>
<c:if test="${memberIn.status==MEMBER_IN_STATUS_BACK}">
  <div class="alert alert-danger">
    <button type="button" class="close" data-dismiss="alert">
      <i class="ace-icon fa fa-times"></i>
    </button>
    <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if test="${not empty memberIn.reason}">: ${memberIn.reason}</c:if>
    <br>
  </div>
</c:if>
    <div class="page-header">
      <h1>
        组织关系转入
        <a class="popupBtn btn btn-success btn-xs"
           data-width="800"
           data-url="${ctx}/hf_content?code=${HTML_FRAGMENT_MEMBER_IN_NOTE_BACK}">
          <i class="fa fa-info-circle"></i> 申请说明</a>
      </h1>
    </div>
<form class="form-horizontal" action="${ctx}/user/memberIn" id="modalForm" method="post">
  <input type="hidden" name="id" value="${memberIn.id}">
  <div class="row">
    <div class="col-xs-6">
      <div class="form-group">
        <label class="col-xs-5 control-label">介绍信抬头</label>
        <div class="col-xs-6">
          <input required class="form-control left-input" type="text" name="fromTitle" value="${memberIn.fromTitle}">
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-5 control-label">介绍信有效期天数</label>
        <div class="col-xs-6">
          <input required id="spinner" class="form-control digits input-group"
                 data-rule-min="1" style="width: 100px"
                 type="text" name="validDays" value="${memberIn.validDays}">
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-5 control-label">类别</label>
        <div class="col-xs-6">
          <select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="168">
            <option></option>
            <c:forEach items="${MEMBER_INOUT_TYPE_MAP}" var="_type">
              <option value="${_type.key}">${_type.value}</option>
            </c:forEach>
          </select>
          <script>
            $("#modalForm select[name=type]").val(${memberIn.type});
          </script>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-5 control-label">党籍状态</label>
        <div class="col-xs-6">
          <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"  data-width="168">
            <option></option>
            <c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
              <option value="${_status.key}">${_status.value}</option>
            </c:forEach>
          </select>
          <script>
            $("#modalForm select[name=politicalStatus]").val(${memberIn.politicalStatus});
          </script>
        </div>
      </div>


      <div class="form-group">
        <label class="col-xs-5 control-label">请选择分党委</label>
        <div class="col-xs-6">
          <select required class="form-control"  data-rel="select2-ajax" data-width="260"
                  data-ajax-url="${ctx}/party_selects?del=0"
                  name="partyId" data-placeholder="请选择">
            <option value="${party.id}">${party.name}</option>
          </select>
        </div>
      </div>
      <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
        <label class="col-xs-5 control-label">请选择党支部</label>
        <div class="col-xs-6">
          <select class="form-control"  data-rel="select2-ajax"  data-width="260"
                  data-ajax-url="${ctx}/branch_selects?del=0"
                  name="branchId" data-placeholder="请选择">
            <option value="${branch.id}">${branch.name}</option>
          </select>
        </div>
      </div>
      <script>
        register_party_branch_select($("#modalForm"), "branchDiv",
                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}", null,null, true );
      </script>
        <div class="form-group">
          <label class="col-xs-5 control-label">转出单位</label>
          <div class="col-xs-6">
            <input required class="form-control left-input" type="text" name="fromUnit" value="${memberIn.fromUnit}">
          </div>
        </div>

        <div class="form-group">
          <label class="col-xs-5 control-label">转出单位地址</label>
          <div class="col-xs-6">
            <input required class="form-control left-input" type="text" name="fromAddress" value="${memberIn.fromAddress}">
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-5 control-label">转出单位联系电话</label>
          <div class="col-xs-6">
            <input required class="form-control left-input" maxlength="20" type="text" name="fromPhone" value="${memberIn.fromPhone}">
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-5 control-label">转出单位传真</label>
          <div class="col-xs-6">
            <input required class="form-control left-input" byteMaxLength="20" maxlength="20" type="text" name="fromFax" value="${memberIn.fromFax}">
          </div>
        </div>
      <div class="form-group">
        <label class="col-xs-5 control-label">转出单位邮编</label>
        <div class="col-xs-6">
          <input required class="form-control left-input isZipCode"
                 maxlength="6"
                 type="text" name="fromPostCode" value="${memberIn.fromPostCode}">
        </div>
      </div>
    </div>
    <div class="col-xs-6 right-div">

      <div class="form-group">
        <label class="col-xs-5 control-label">党费缴纳至年月</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input required class="form-control date-picker" name="_payTime" type="text"
                   autocomplete="off" disableautocomplete
                   data-date-format="yyyy-mm"
                   data-date-min-view-mode="1"
                   value="${cm:formatDate(memberIn.payTime,'yyyy-MM')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>

      <div class="form-group">
        <label class="col-xs-5 control-label">转出办理时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input required class="form-control date-picker" name="_fromHandleTime" type="text"
                   autocomplete="off" disableautocomplete
                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.fromHandleTime,'yyyy-MM-dd')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-5 control-label">转入办理时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <c:set var="handleTime" value="${cm:formatDate(memberIn.handleTime,'yyyy-MM-dd')}"/>
            <input required class="form-control date-picker" name="_handleTime" type="text"
                   autocomplete="off" disableautocomplete
                   data-date-format="yyyy-mm-dd" value="${empty handleTime?today:handleTime}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>

      <div class="form-group">
        <label class="col-xs-5 control-label">提交书面申请书时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input class="form-control date-picker" name="_applyTime" type="text"
                   autocomplete="off" disableautocomplete
                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.applyTime,'yyyy-MM-dd')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-5 control-label">确定为入党积极分子时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input class="form-control date-picker" name="_activeTime" type="text"
                   autocomplete="off" disableautocomplete
                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.activeTime,'yyyy-MM-dd')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-5 control-label">确定为发展对象时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input class="form-control date-picker" name="_candidateTime" type="text"
                   autocomplete="off" disableautocomplete
                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.candidateTime,'yyyy-MM-dd')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-5 control-label">入党时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input class="form-control date-picker" name="_growTime" type="text"
                   autocomplete="off" disableautocomplete
                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.growTime,'yyyy-MM-dd')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-5 control-label">转正时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input class="form-control date-picker" name="_positiveTime" type="text"
                   autocomplete="off" disableautocomplete
                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.positiveTime,'yyyy-MM-dd')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="clearfix form-actions">
    <div style="margin-left: 500px;">
      <button class="btn btn-info" type="submit" id="submitBtn" data-loading-text="提交中..."  data-success-text="您的申请已提交成功" autocomplete="off">
        <i class="ace-icon fa fa-check bigger-110"></i>
        提交
      </button>

      &nbsp; &nbsp; &nbsp;
      <button class="hideView btn btn-default" type="button">
        <i class="ace-icon fa fa-undo bigger-110"></i>
        返回
      </button>
    </div>
  </div>
</form>
<style>
  #modalForm .row{
    width: 1100px;
  }
  #modalForm .input-group{
    width:150px;
  }
  #modalForm input.left-input{
    width: 260px;
  }
  #modalForm .right-div .col-xs-6{
    width:auto;
  }
  #modalForm .help-block{
    white-space: nowrap;
  }
</style>
<script src="${ctx}/assets/js/fuelux/fuelux.spinner.js"></script>
<script src="${ctx}/assets/js/ace/elements.spinner.js"></script>
      <script>
        $('#spinner').ace_spinner({value:0,min:1,max:999,step:1, on_sides: true, icon_up:'ace-icon fa fa-plus bigger-110', icon_down:'ace-icon fa fa-minus bigger-110', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
        $('#modalForm [data-rel="select2"]').select2();
        register_date($('.date-picker'));

        var jqValid = $("form").validate({
          submitHandler: function (form) {
            /*alert($("#branchDiv").is(":hidden"))
            alert($.trim($('select[name=branchId]').val()))
            return;*/
            if(!$("#branchDiv").is(":hidden")){
              if($.trim($('select[name=branchId]').val())=='') {
                bootbox.alert("请选择支部。");
                return;
              }
            }
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
              success:function(ret){
                if(ret.success){
                  bootbox.alert("提交成功。",function(){
                      $btn.button("success").addClass("btn-success");
                    $.hashchange();
                  });
                }else{
                  $btn.button('reset');
                }
              }
            });
          }});

        $('#modalForm select[name=politicalStatus]').change(function(){
          var $input = $("#modalForm  input[name=_positiveTime]");
          if($(this).val()=='${MEMBER_POLITICAL_STATUS_POSITIVE}') {
            $input.attr("required", "required");
          }else {
            $input.removeAttr("required");
          }
          jqValid.element($input);
        }).change();
</script>
