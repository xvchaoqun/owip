<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CET_UPPER_TRAIN_UPPER" value="<%=CetConstants.CET_UPPER_TRAIN_UPPER%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_SELF" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF%>"/>
<script>
  var colModel = [
   <c:if test="${cls==3}">
    {label: '未通过原因', width: 210, align: 'left', name: 'backReason', frozen:true},
    </c:if>
    { label: '年度',name: 'year', frozen: true},
    {label: '参训人姓名', name: 'user.realname', frozen:true},
    {label: '参训人工号', width: 110, name: 'user.code', frozen:true},
    {label: '时任单位及职务', name: 'title', align: 'left', width: 350},
    {label: '职务属性', name: 'postId', width: 120, align: 'left',formatter: $.jgrid.formatter.MetaType},
    {
      label: '培训班主办方', name: 'organizer', width: 150, align: 'left', formatter: function (cellvalue, options, rowObject) {
      if (cellvalue == 0) {
        return $.trim(rowObject.otherOrganizer)
      }
      return $.jgrid.formatter.MetaType(cellvalue)
    }
    },
    {label: '培训班类型', name: 'trainType', width: 150, formatter: $.jgrid.formatter.MetaType},
    {
      label: '专项培训班', name: 'specialType', width: 300, align: 'left',formatter: function (cellvalue, options, rowObject) {
      if (cellvalue == 0) {
        return '无'
      }
      return $.jgrid.formatter.MetaType(cellvalue)
    }
    },
    {label: '培训班名称', name: 'trainName', align: 'left',width: 350},
    {label: '培训<br/>开始时间', name: 'startDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
    {label: '培训<br/>结束时间', name: 'endDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
    {
      label: '培训天数', name: '_day', width: 80, formatter: function (cellvalue, options, rowObject) {
      return $.dayDiff(rowObject.startDate, rowObject.endDate);
    }
    },
    {label: '培训学时', name: 'period', width: 80},
    {label: '培训地点', name: 'address', align: 'left', width: 180},
    {label: '培训总结', name: '_note', width: 200, formatter: function (cellvalue, options, rowObject) {

      var ret = "";
      var fileName = "培训总结("+rowObject.user.realname+")";
      var pdfNote = rowObject.pdfNote;
      if ($.trim(pdfNote) != '') {

        //console.log(fileName + " =" + pdfNote.substr(pdfNote.indexOf(".")))
        ret = '<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                        .format(encodeURI(pdfNote), encodeURI(fileName))
                + '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                        .format(encodeURI(pdfNote), encodeURI(fileName));
      }
      var wordNote = rowObject.wordNote;
      if ($.trim(wordNote) != '') {
        ret += '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                .format(encodeURI(wordNote), encodeURI(fileName));
      }
      return ret;
    }},
    {label: '${upperType==CET_UPPER_TRAIN_UPPER?'派出':'组织'}单位', name: 'unitId', align: 'left', width: 150, formatter: function (cellvalue, options, rowObject) {
      if (!rowObject.type) {
        return '党委组织部'
      }

      return $.jgrid.formatter.unit(cellvalue)
    }},
    {label: '操作人', name: 'addUser.realname'},
    {label: '添加时间', name: 'addTime', width: 150},
    <c:if test="${param.addType!=CET_UPPER_TRAIN_ADD_TYPE_SELF}">
    {label: '是否计入<br/>年度学习任务', name: 'isValid', formatter: function (cellvalue, options, rowObject) {
      if (cellvalue==undefined) {
        return '--'
      }
      return cellvalue?'是':'否'
    }},
    </c:if>
    {label: '备注', name: 'remark', width: 150}
  ]
</script>