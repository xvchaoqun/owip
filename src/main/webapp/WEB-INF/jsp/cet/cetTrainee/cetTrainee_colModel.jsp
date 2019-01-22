<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
    var cetTraineeTypeMap = ${cm:toJSONObject(cetTraineeTypeMap)};
  var colModel = [
      <c:if test="${param.type=='sign'}">
      {name:'isFinished', hidden:true},
      { label: '上课情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
          return rowObject.isFinished?'<span class="text-success">已上课</span>':'<span class="text-danger">未上课</span>'
      }},
      {label: '签到时间', name: 'signTime', width: 160},
      {label: '签到方式', name: 'signType', width: 80, formatter: function (cellvalue, options, rowObject){
          if(cellvalue==undefined) return '-'
          return _cMap.CET_TRAINEE_SIGN_TYPE_MAP[cellvalue];
      } },
      {label: '参训人类别', name: 'traineeTypeId', width: 120, formatter: function (cellvalue, options, rowObject) {
          return cetTraineeTypeMap[cellvalue].name;
      }},
      {label: '工作证号', name: 'user.code', width: 110, frozen: true},
      {label: '姓名', name: 'user.realname', width: 120, frozen: true},
      </c:if>
      <c:if test="${param.type=='admin'}">
      { label: '学习详情',name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {
          return ('<button class="popupBtn btn btn-success btn-xs" data-width="1100" ' +
          'data-url="${ctx}/cet/cetTrainee_detail?userId={0}&trainId={1}"><i class="fa fa-search"></i> 查看</button>')
                  .format(rowObject.userId, rowObject.trainId);
      }, frozen: true},
      { label: '选课情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
          if(courseCount==0) return '-'
          return '{0}/{1}'.format(rowObject.courseCount, courseCount);
      }, frozen: true},
      { label: '签到情况',name: '_sign', width: 80, formatter: function (cellvalue, options, rowObject) {
          if(rowObject.courseCount==undefined) return '-'
          return '{0}/{1}'.format(rowObject.finishCount, rowObject.courseCount);
      }, frozen: true},
      { label: '完成学时数',name: 'finishPeriod', frozen: true},
      {label: '工作证号', name: 'cadre.code', width: 110, frozen: true},
      {label: '姓名', name: 'cadre.realname', width: 120, frozen: true},
      {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 350},
      {label: '行政级别', name: 'cadre.typeId', formatter:$.jgrid.formatter.MetaType},
      {label: '职务属性', name: 'cadre.postId', width: 150, formatter:$.jgrid.formatter.MetaType},
      {label: '党派', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty, formatoptions:{useCadre:true}},
      {label: '专业技术职务', name: 'cadre.proPost', width: 120},
      {
          label: '任现职时间',
          name: 'cadre.lpWorkTime',
          formatter: 'date',
          formatoptions: {newformat: 'Y-m-d'}
      },
      {label: '联系方式', name: 'cadre.mobile', width: 120},
      {label: '电子邮箱', name: 'cadre.email', width: 250, algin:"left"},

        { label: '本年度参加培训情况',name: 'courseCount', width: 150, formatter: function (cellvalue, options, rowObject) {

          var yearPeriod = yearPeriodMap[rowObject.userId];
          return yearPeriod==undefined?'-':yearPeriod;
      }},
      </c:if>
      { label: '备注',name: 'remark', width: 200, formatter: function (cellvalue, options, rowObject) {
          var str = "";
          if(rowObject.isQuit) str+="退班";
          str += " " + $.trim(cellvalue);

          return str;
      }}
  ]
</script>
