<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="_query" value="${not empty param.realname || not empty param.status}"/>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
      <button class="popupBtn btn btn-info btn-sm"
         data-url="${ctx}/cet/cetTrainInspector_gen_oncampus?trainId=${cetTrain.id}">
        <i class="fa fa-plus"></i> 生成评课账号</button>

  <button class="linkBtn btn btn-success btn-sm tooltip-success"
       data-url="${ctx}/cet/cetTrainInspector_list?export=1&trainId=${cetTrain.id}"
          data-target="_blank" data-rel="tooltip" data-placement="top" title="导出全部的测评账号">
      <i class="fa fa-download"></i> 导出账号</button>

  <c:if test="${cetTrain.evaCount>0}">
      <c:if test="${cetTrain.evaAnonymous}">
          <button class="btn btn-warning btn-sm"
                  onclick="to_print_inspector(${cetTrain.id})"><i class="fa fa-print"></i> 打印账号</button>
      </c:if>
      <c:if test="${!cetTrain.evaAnonymous}">
      <button class="linkBtn btn btn-warning btn-sm"
              data-url="${ctx}/cet/cetTrainInspector_list?export=2&trainId=${cetTrain.id}"
              data-target="_blank"><i class="fa fa-print"></i> 打印二维码</button>
      </c:if>

      <button class="jqBatchBtn btn btn-danger btn-sm"
              data-msg="确定删除这{0}个评课账号？（关联的已评课数据均将删除，不可恢复，请谨慎操作！）"
              data-title="删除评课账号"
              data-grid-id="#jqGrid4"
              data-callback="_detailContentReload2"
              data-url="${ctx}/cet/cetTrainInspector_batchDel?trainId=${cetTrain.id}">
          <i class="fa fa-times"></i> 删除</button>
  </c:if>

</div>
<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
  <div class="widget-header">
    <h4 class="widget-title">搜索</h4>

    <div class="widget-toolbar">
      <a href="#" data-action="collapse">
        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
      </a>
    </div>
  </div>
  <div class="widget-body">
    <div class="widget-main no-padding">
      <form class="form-inline search-form" id="searchForm2">
        <input type="hidden" name="traineeTypeId" value="${traineeTypeId}">
        <div class="form-group">
          <label>姓名</label>
          <input class="form-control search-query" name="realname" type="text" value="${param.realname}"
                 placeholder="请输入姓名">
        </div>
        <div class="form-group">
          <label>测评状态</label>
          <select data-rel="select2" name="status" data-width="120" data-placeholder="请选择">
              <option></option>
              <option value="1">已完成</option>
              <option value="0">未完成</option>
          </select>
            <script>
                $("#searchForm2 select[name=status]").val("${param.status}");
            </script>
        </div>
        <div class="clearfix form-actions center">
          <a class="jqSearchBtn btn btn-default btn-sm"
             data-target="#detail-content-view"
             data-form="#searchForm2"
             data-url="${ctx}/cet/cetTrain_detail_eva/inspectors?trainId=${param.trainId}">
              <i class="fa fa-search"></i> 查找</a>
          <c:if test="${_query}">&nbsp;
            <button type="button" class="resetBtn btn btn-warning btn-sm"
                    data-target="#detail-content-view"
                    data-url="${ctx}/cet/cetTrain_detail_eva/inspectors?trainId=${param.trainId}">
              <i class="fa fa-reply"></i> 重置
            </button>
          </c:if>
        </div>
      </form>
    </div>
  </div>
</div>
<div class="rownumbers">
<div class="space-4"></div>
<table id="jqGrid4" class="jqGrid2 table-striped"></table>
<div id="jqGridPager4"></div>
</div>
<script>
    function to_print_inspector(trainId){

        bootbox.prompt({
            size:'small',
            title: "设置每页打印数量",
            value:'2',
            inputType: 'number',
            callback: function (result) {
                if(result!=null) {
                    print_inspector(trainId, result)
                }
            }
        })
    }

    function print_inspector(trainId, result){
        $.print("${ctx}/cet/cetTrainInspector_list?export=2&pagesize="+result+"&trainId="+ trainId)
    }

  function _callback4(target) {

    $("#jqGrid4").trigger("reloadGrid");
  }
  $('#searchForm2 [data-rel="select2"]').select2();
  $.register.date($('.date-picker'));
  var isEvaAnonymous = ${cetTrain.evaAnonymous};
  $("#jqGrid4").jqGrid({
    pager: "jqGridPager4",
    rownumbers:true,
    url: '${ctx}/cet/cetTrainInspector_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
    colModel: [
      {label: '账号', name: 'username', formatter: function (cellvalue, options, rowObject) {
          if(!isEvaAnonymous) return '-'
          return cellvalue;
      }},
      {label: '密码', name: 'passwd', formatter: function (cellvalue, options, rowObject) {
          if(!isEvaAnonymous) return '-'
          return cellvalue;
      }},
      {label: '工作证号', name: 'mobile', formatter: function (cellvalue, options, rowObject) {
          if(isEvaAnonymous) return '-'
          return cellvalue;
      }},
      {label: '姓名', name: 'realname', formatter: function (cellvalue, options, rowObject) {
          if(isEvaAnonymous) return '-'
          return cellvalue;
      }},
      {label: '测评状态', name: '_eva', width: 200, formatter: function (cellvalue, options, rowObject) {

        var statusTxt = _cMap.CET_TRAIN_INSPECTOR_STATUS_MAP[rowObject.status];
        if(rowObject.status==${CET_TRAIN_INSPECTOR_STATUS_INIT} && rowObject.finishCourseNum>0){
            if(rowObject.finishCourseNum==rowObject.selectedCourseNum){
              statusTxt='<span class="label label-success">已完成</span>';
            }else{
              statusTxt='<span class="label label-info">部分完成</span>';
            }
        }
        return statusTxt;
      }},
      {label: '已完成/暂存/选课数量', name: '_evaResult', width: 200, formatter: function (cellvalue, options, rowObject) {

        return '{0}/{1}/{2}'.format(rowObject.finishCourseNum==undefined?0:rowObject.finishCourseNum,
                rowObject.saveCourseNum==undefined?0:rowObject.saveCourseNum, rowObject.selectedCourseNum);
      }}
    ]
  }).jqGrid("setFrozenColumns");
  $(window).triggerHandler('resize.jqGrid2');
  $.initNavGrid("jqGrid4", "jqGridPager4");
</script>