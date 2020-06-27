<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<script>
  <c:if test="${empty param.type || param.type==CET_COURSE_TYPE_OFFLINE||param.type==CET_COURSE_TYPE_ONLINE}">
  var colModel = [
      {label: '课程编号', name: 'sn', frozen:true},
      {label: '设立时间', name: 'foundDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
      {label: '课程名称', name: 'name', width: 300, align: 'left', frozen:true},
      <c:if test="${param.list=='admin'}">
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
          label: '排序',formatter: $.jgrid.formatter.sortOrder,
          formatoptions: {url: "${ctx}/cet/cetCourse_changeOrder"}, frozen:true
      },
      </c:if>
      {label: '主讲人', name: 'cetExpert.realname', formatter: function (cellvalue, options, rowObject) {
          if(rowObject.expertId==undefined) return '--'
          return cellvalue;
      }, frozen:true},
      {label: '所在单位', name: 'cetExpert.unit', formatter: function (cellvalue, options, rowObject) {
          if(rowObject.expertId==undefined) return '--'
          return cellvalue;
      }, width: 300, align: 'left'},
      {label: '职务和职称', name: 'cetExpert.post', formatter: function (cellvalue, options, rowObject) {
          if(rowObject.expertId==undefined) return '--'
          return cellvalue;
      }, width: 120, align: 'left'},
      <c:if test="${param.type==CET_COURSE_TYPE_OFFLINE}">
      {label: '授课方式', name: 'teachMethod', formatter: $.jgrid.formatter.MetaType},
      </c:if>
      {label: '学时', name: 'period', width: 70},
      <c:if test="${param.type==CET_COURSE_TYPE_ONLINE}">
      {label: '时长', name: 'duration'},
      {label: '播放', name: 'duration', formatter: function (cellvalue, options, rowObject){

          return ('<button class="linkBtn btn btn-xs btn-success" data-url="${ctx}/cet/cetCourse_video?id={0}&_={1}" '
                +' data-target="_blank"><i class="fa fa-play-circle"></i> 播放</button>')
                    .format(rowObject.id, new Date().getTime());

          /*return $.iframePreview(rowObject.name, '${ctx}/cet/cetCourse_video?id={0}&_={1}'
              .format(rowObject.id, new Date().getTime()), "播放")*/
      }},
      </c:if>
      <c:if test="${param.list=='admin'}">
      /*{label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
          return ('<button class="openView btn btn-warning btn-xs" ' +
          'data-url="${ctx}/cet/cetCourse_detail?courseId={0}"><i class="fa fa-search"></i> 详情</button>')
                  .format(rowObject.id);
      }},*/
      {label: '备注', name: 'remark', align: 'left', width: 200}
      </c:if>
  ]
  </c:if>
  <c:if test="${param.type==CET_COURSE_TYPE_SELF}">
  var colModel = [
      {label: '编号', name: 'sn', frozen:true},
      {label: '设立时间', name: 'foundDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
      {label: '名称', name: 'name', width: 300, align: 'left', frozen:true},
      {label: '学习内容', name: '_content', width: 80, formatter: function (cellvalue, options, rowObject) {
        return ('<button type="button" data-url="${ctx}/cet/cetCourseFile?courseId={0}" data-width="800" ' +
                'class="popupBtn btn btn-xs btn-success"><i class="ace-icon fa fa-search"></i> 详情</button>')
                .format(rowObject.id)
      }, frozen:true},
      <c:if test="${param.list=='admin'}">
      {
          label: '排序',formatter: $.jgrid.formatter.sortOrder,
          formatoptions: {url: "${ctx}/cet/cetCourse_changeOrder"}, frozen:true
      },
      </c:if>
      {label: '学时', name: 'period', width: 70},
      {label: '备注', name: 'remark', width: 400}
  ]
  </c:if>
  <c:if test="${param.type==CET_COURSE_TYPE_PRACTICE}">
  var colModel = [
      {label: '编号', name: 'sn', frozen:true},
      {label: '设立时间', name: 'foundDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
      {label: '实践教学名称', name: 'name', width: 300, align: 'left', frozen:true},
      <c:if test="${param.list=='admin'}">
      {
          label: '排序',formatter: $.jgrid.formatter.sortOrder,
          formatoptions: {url: "${ctx}/cet/cetCourse_changeOrder"}, frozen:true
      },
      </c:if>
      {label: '实践教学地点', name: 'address', width: 300, align: 'left'},
      {label: '学时', name: 'period', width: 70},
      {label: '备注', name: 'remark', width: 400}
  ]
  </c:if>
  <c:if test="${param.type==CET_COURSE_TYPE_SPECIAL}">
  var colModel = [
      {label: '编号', name: 'sn', frozen:true},
      {label: '设立时间', name: 'foundDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
      {label: ' 网上专题培训班名称', name: 'name', width: 300, align: 'left', frozen:true},
      <c:if test="${param.list=='admin'}">
      {
          label: '排序',formatter: $.jgrid.formatter.sortOrder,
          formatoptions: {url: "${ctx}/cet/cetCourse_changeOrder"}, frozen:true
      },
      </c:if>
      {label: ' 上级单位名称', name: 'address', width: 300, align: 'left'},
      {label: '总学时', name: 'totalPeriod', width: 80},
      {label: '专题班', name: '_items', width: 80, formatter: function (cellvalue, options, rowObject) {
          return ('<button type="button" data-url="${ctx}/cet/cetCourseItem?courseId={0}" ' +
          'class="popupBtn btn btn-xs btn-success"><i class="ace-icon fa fa-search"></i> 详情</button>')
                  .format(rowObject.id)
      }},
      {label: '备注', name: 'remark', width: 400}
  ]
  </c:if>
</script>
