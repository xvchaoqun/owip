<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
      {label: '课程编号', name: 'sn', frozen:true},
      {label: '设立时间', name: 'foundDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen:true},
      {label: '课程名称', name: 'name', width: 300, align: 'left', frozen:true},
        <c:if test="${param.type=='admin'}">
      {label: '课程要点', name: '_summary', width: 80, formatter: function (cellvalue, options, rowObject) {
          var btnStr = "添加";
          var btnCss = "btn-success";
          var iCss = "fa-plus";
          if (rowObject.hasSummary){
              btnStr = "查看";
              btnCss = "btn-primary";
              iCss = "fa-search";
          }

          return ('<button class="popupBtn btn {2} btn-xs" data-width="750" ' +
          'data-url="${ctx}/cet/cetCourse_summary?id={0}"><i class="fa {3}"></i> {1}</button>')
                  .format(rowObject.id, btnStr, btnCss, iCss);
      }, frozen:true},
      {
          label: '排序', align: 'center', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
          formatoptions: {url: "${ctx}/cet/cetCourse_changeOrder"}, frozen:true
      },
      </c:if>
      {label: '主讲人', name: 'cetExpert.realname', frozen:true},
      {label: '所在单位', name: 'cetExpert.unit', width: 300, align: 'left'},
      {label: '职务和职称', name: 'cetExpert.post', width: 120, align: 'left'},
      {label: '授课方式', name: 'teachMethod', formatter: $.jgrid.formatter.MetaType},
      {label: '学时', name: 'period', width: 70},
      <c:if test="${param.isOnline==1}">
      {label: '时长', name: 'duration'},
      </c:if>
      {
          label: '专题分类', name: 'courseTypeId', formatter: function (cellvalue, options, rowObject) {
          if (cellvalue == undefined) return ''
          var courseTypeMap = ${cm:toJSONObject(courseTypeMap)};
          return courseTypeMap[cellvalue].name
      }
      },
        <c:if test="${param.type=='admin'}">
      {label: '详情', name: '_detail'},
      {label: '备注', name: 'remark', width: 400}
      </c:if>
  ]
</script>
