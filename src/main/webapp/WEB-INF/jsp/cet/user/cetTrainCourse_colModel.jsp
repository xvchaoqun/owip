<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<script>
  <c:if test="${param.planType==CET_PROJECT_PLAN_TYPE_OFFLINE || param.planType==CET_PROJECT_PLAN_TYPE_ONLINE}">
  var colModel = [
    /*{label: '课程编号', name: 'cetCourse.sn', frozen:true},*/
    {
      label: '课程名称',
      name: 'cetCourse.name',
      width: 400,
      align: 'left', frozen:true
    },
    <c:if test="${param.planType==CET_PROJECT_PLAN_TYPE_ONLINE}">
    {label: '播放', name: 'duration', width: 110,formatter: function (cellvalue, options, rowObject){

      //console.log(rowObject.id + " " + selectedTrainCourseIds + " " + $.inArray(rowObject.id, selectedTrainCourseIds)<0)
      if(selectedTrainCourseIds==undefined || $.inArray(rowObject.id, selectedTrainCourseIds)<0){

        return ('<button class="btn btn-xs btn-success" onClick=\'SysMsg.info("请您选课后观看。")\'><i class="fa fa-play-circle"></i> 播放</button>')
      }
      return ('<button class="linkBtn btn btn-xs btn-success" data-url="${ctx}/cet/cetCourse_video?id={0}&trainCourseId={1}&_={2}" '
      +' data-target="_blank"><i class="fa fa-play-circle"></i> 播放</button>')
              .format(rowObject.cetCourse.id, rowObject.id, new Date().getTime())
      /*return '<a class="various" title="{1}" data-path="{0}" data-fancybox-type="iframe" href="${ctx}/cet/cetCourse_video?id={0}&trainCourseId={2}">播放</a>'
              .format(rowObject.cetCourse.id, rowObject.cetCourse.name, rowObject.id);*/
    }},
    </c:if>
    {label: '学时', name: 'cetCourse.period', width: 60},
    {
      label: '开始时间',
      name: 'startTime',
      width: 130, formatter: function (cellvalue, options, rowObject){
        if(cellvalue==undefined) return '--'
        return $.date(cellvalue, "yyyy-MM-dd HH:mm");
    }},
    {
      label: '结束时间',
      name: 'endTime',
      width: 130, formatter: function (cellvalue, options, rowObject){
      if(cellvalue==undefined) return '--'
      return $.date(cellvalue, "yyyy-MM-dd HH:mm");
    }},
    <c:if test="${param.planType==CET_PROJECT_PLAN_TYPE_OFFLINE}">
    {label: '上课地点', name: 'address', width: 200, align:'left'},
    </c:if>
    {label: '主讲人', name: 'cetCourse.cetExpert.realname', width: 90},
    {label: '所在单位', name: 'cetCourse.cetExpert.unit', width: 200, align: 'left'},
    {label: '职务和职称', name: 'cetCourse.cetExpert.post', width: 200, align: 'left'},
    /*{label: '授课方式', name: 'cetCourse.teachMethod', formatter: $.jgrid.formatter.MetaType},*/
  ]
  </c:if>
  <c:if test="${param.planType==CET_PROJECT_PLAN_TYPE_PRACTICE}">
  var colModel = [
    {label: '编号', name: 'cetCourse.sn', frozen:true},
    {label: '实践教学名称', name: 'cetCourse.name', width: 300, align: 'left'},
    {label: '实践教学地点', name: 'cetCourse.address', width: 300, align: 'left'},
    {label: '学时', name: 'cetCourse.period', width: 70},
    {
      label: '开始时间',
      name: 'startTime',
      width: 150,
      formatter: 'date',
      formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}
    },
    {
      label: '结束时间',
      name: 'endTime',
      width: 150,
      formatter: 'date',
      formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'},
    }
  ]
  </c:if>
</script>