<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
  <div class="col-xs-12">
    <div class="widget-box transparent">
      <div class="widget-body">
        <div class="widget-main no-padding">
          <div class="tab-content padding-4">

            <form class="form-inline" action="${ctx}/pcsPrVote" id="recommendForm" method="post">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h3 class="panel-title"><span style="font-weight: bolder; color: #669fc7"><i
                          class="fa fa-users"></i>  党代表名单</span>
                  </h3>
                </div>
                <div class="collapse in">
                  <div class="panel-body">
                    <table id="jqGrid" data-width-reduce="30"
                           class="jqGrid4 table-striped"></table>
                  </div>
                </div>
              </div>
            </form>
            <div class="modal-footer center" style="margin-top: 20px">
              <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                      autocomplete="off"  ${!allowModify?"disabled":""}
                      class="btn btn-success btn-lg"><i class="fa fa-check"></i> 保存
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<style>
  .form-table {
    margin: 0 10px 10px 0px;
    border: 1px solid #e5e5e5;
  }

  .form-table tr td {
    padding: 5px;
    border: 1px solid #e5e5e5;
    white-space: nowrap;
  }

  .form-table input, .form-table input:focus {
    width: 80px;
    background-color: #f2dede;
    border: solid 1px darkred;
    font-size: 20px;
    font-weight: bolder;
    color: #000 !important;
    /*text-align: center !important;*/
  }

  .form-table tr td:nth-child(odd) {
    font-weight: bolder;
    background-color: #f9f9f9 !important;
    text-align: right !important;
    vertical-align: middle !important;
  }

  .panel input.vote {
    width: 60px !important;
    padding: 0px !important;
    text-align: center;
    font-weight: bolder;
    font-size: 18px;
    color: red;
  }
</style>
<script>
  var candidates = ${cm:toJSONArray(candidates)};
  var colModel = [
    {
      label: '党代表类型', name: 'type', width: 150, formatter: function (cellvalue, options, rowObject) {
      if (cellvalue == undefined) return '-';
      return _cMap.PCS_PR_TYPE_MAP[cellvalue]
    }
    },
    {label: '工作证号', name: 'code', width: 120, frozen: true},
    {label: '姓名', name: 'realname', width: 110, frozen: true},

    {
      label: '手机号', name: 'mobile', formatter: function (cellvalue, options, rowObject) {
      return ('<input required type="text" name="mobile{0}" data-container="{1}" value="{2}" class="mobile" maxlength="4">')
              .format(rowObject.userId,"#jqGrid", $.trim(cellvalue))
    }
    },
    {
      label: '邮箱', name: 'email', formatter: function (cellvalue, options, rowObject) {
      return ('<input required type="text" name="email{0}" data-container="{1}" value="{2}" class="email" maxlength="4">')
              .format(rowObject.userId,"#jqGrid", $.trim(cellvalue))
    }
    },
    {
      label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
    },
    {label: '出生年月', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
    {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
    {label: '民族', name: 'nation', width: 60},
    {
      label: '学历学位', name: '_learn', formatter: function (cellvalue, options, rowObject) {
      if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
        return $.jgrid.formatter.MetaType(rowObject.eduId);
      } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
        return $.trim(rowObject.education);
      }
      return "-"
    }
    },/*
     {
     label: '参加工作时间',
     name: 'workTime',
     width: 120,
     sortable: true,
     formatter: 'date',
     formatoptions: {newformat: 'Y-m-d'}
     },*/
    {
      label: '入党时间',
      name: 'growTime',
      width: 120,
      sortable: true,
      formatter: 'date',
      formatoptions: {newformat: 'Y-m-d'}
    },
    {
      label: '职别', name: 'proPost', formatter: function (cellvalue, options, rowObject) {
      if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
        return '干部';
      } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
        return (rowObject.isRetire) ? "离退休" : $.trim(cellvalue);
      }
      return $.trim(rowObject.eduLevel);
    }
    },
    {
      label: '职务', width: 200,
      name: 'post', formatter: function (cellvalue, options, rowObject) {
      if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
        return $.trim(cellvalue);
      }
      return "-"
    }
    },{label: '票数', name: 'vote3', width: 80}, {hidden: true, key: true, name: 'userId'}
  ];

  $("#jqGrid").jqGrid({
    pager: null,
    responsive: false,
    rownumbers: true,
    multiselect: false,
    height: 400,
    datatype: "local",
    rowNum: candidates.length,
    data: candidates,
    colModel: colModel,
    gridComplete: function () {
      <c:if test="${!allowModify}">
      $("#recommendForm input, .panel input").prop("disabled", true);
      </c:if>
    }
  });
  $(window).triggerHandler('resize.jqGrid4');

  $("#submitBtn").click(function () {
    var $null = null;
    $(".vote", ".panel").each(function () {
      var $this = $(this);
      if ($.trim($this.val()) == '') {
        $null = $this;
        return false;
      }
    });
    //console.log($null)
    if ($null != null) {
      var $panel = $null.closest('.panel');
      var $title = $panel.find('span.title');
      $.tip({
        $target: $title,
        //$container:$panel,
        at: "top center",
        my: "bottom left",
        msg: '请填写完整每位候选人的票数'
      });
    }

    $("#recommendForm").submit();
    return false;
  })

  $("#recommendForm").validate({
    submitHandler: function (form) {
      _ajaxSubmit(form);
    }
  });

  function _ajaxSubmit(form) {

    var items = [];
    $.each($("#jqGrid").jqGrid("getDataIDs"), function (i, userId) {
      var $row = $("[role='row'][id=" + userId + "]", "#jqGrid");
      var item = {};
      item.userId = userId;
      item.mobile = $.trim($("input.mobile", $row).val());
      item.email = $.trim($("input.email", $row).val());
      items.push(item);
    });

    //console.log(items)
    $(form).ajaxSubmit({
      data: {items: new Base64().encode(JSON.stringify(items))},
      success: function (ret) {
        if (ret.success) {
          SysMsg.success("提交成功。", function () {
            $.hashchange();
          });
          //$.hideView();
        }
      }
    });
  }

</script>