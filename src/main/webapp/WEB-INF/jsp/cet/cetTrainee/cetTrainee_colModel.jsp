<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
      <c:if test="${param.type=='popup'}">
      {name:'isFinished', hidden:true},
      { label: '上课情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
          return rowObject.isFinished?'<span class="text-success">已上课</span>':'<span class="text-danger">未上课</span>'
      }},
      {label: '签到时间', name: 'signTime', width: 160},
      {label: '签到方式', name: 'signType', width: 80, formatter: function (cellvalue, options, rowObject){
          if(cellvalue==undefined) return '-'
          return _cMap.CET_TRAINEE_SIGN_TYPE_MAP[cellvalue];
      } },
      </c:if>
      <c:if test="${param.type=='admin'}">
      { label: '选课情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
          if(rowObject.courseCount==0) return '-'
          return '{0}/{1}'.format(rowObject.finishCount, rowObject.courseCount);
      }},
      { label: '学时情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
          if(rowObject.totalPeriod==undefined) return '-'
          return '{0}/{1}'.format(rowObject.finishPeriod, rowObject.totalPeriod);
      }},
      </c:if>
      {label: '工作证号', name: 'code', width: 100, frozen: true},
      {label: '姓名', name: 'realname', width: 120, frozen: true},
      {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
      {
          label: '行政级别', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
          if (cellvalue == undefined) return '-';
          return _cMap.adminLevelMap[cellvalue].name;
      }},
      {
          label: '职务属性', name: 'postId', width: 150, formatter: function (cellvalue, options, rowObject) {
          if (cellvalue == undefined) return '-';
          return _cMap.postMap[cellvalue].name;
      }},
      {
          label: '党派', name: 'cadreDpType', width: 80, formatter: function (cellvalue, options, rowObject) {

          if (cellvalue == 0) return "中共党员"
          else if (cellvalue > 0) return _cMap.metaTypeMap[rowObject.dpTypeId].name
          return "-";
      }},
      {label: '专业技术职务', name: 'proPost', width: 120},
      {
          label: '任现职时间',
          name: 'lpWorkTime',
          formatter: 'date',
          formatoptions: {newformat: 'Y-m-d'}
      },
      {label: '联系方式', name: 'mobile', width: 120},
      {label: '电子邮箱', name: 'email', width: 150},
      <c:if test="${param.type=='admin'}">
        { label: '本年度参加培训情况',name: 'courseCount', width: 150, formatter: function (cellvalue, options, rowObject) {

          var yearPeriod = yearPeriodMap[rowObject.userId];
          return yearPeriod==undefined?'-':yearPeriod;
      }},
      { label: '备注',name: 'remark', width: 250}
    </c:if>
  ]
</script>
