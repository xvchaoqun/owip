<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<script>
  var traineeTypeMap = ${cm:toJSONObject(traineeTypeMap)};
  var specialProjectTypeMap = ${cm:toJSONObject(specialProjectTypeMap)};
    var dailyProjectTypeMap = ${cm:toJSONObject(dailyProjectTypeMap)};
  var colModel = [
   <c:if test="${cls==3}">
    {label: '未通过原因', width: 210, align: 'left', name: 'backReason', frozen:true},
    </c:if>
    { label: '年度',name: 'year', frozen: true},
      {label: '参训人工号', width: 110, name: 'user.code', frozen:true},
      {label: '参训人姓名', name: 'user.realname', frozen:true},
      { label: '参训人类型', name: 'traineeTypeId', formatter: function (cellvalue, options, rowObject) {
              if(cellvalue==null)return '--';
              if(cellvalue==0) return rowObject.otherTraineeType;
              return traineeTypeMap[cellvalue].name
    }},
      {label: '时任单位及职务', name: 'title', align: 'left', width: 350, formatter: function (cellvalue, options, rowObject) {
              if(cellvalue == undefined){
                  if (rowObject.unit != undefined) {
                      return rowObject.unit.name;
                  }
                  return "--";
              }else{
                  return rowObject.title;
              }}},
    {label: '时任职务属性', width: 150, name: 'postType', align: 'left', formatter: function (cellvalue, options, rowObject) {
            if (cellvalue==undefined || cellvalue == null) return "--";
            return $.jgrid.formatter.MetaType(cellvalue);
        }},
      <c:if test="${cm:getMetaTypes('mc_cet_identity').size()>0}">
          {
              label: '参训人员身份', name: 'identity', width: 150, align: 'left', formatter: function (cellvalue, options, rowObject) {
                  if (cellvalue == null || cellvalue == '') {
                      return "--";
                  }
                  return ($.map(cellvalue.split(","), function(identity){
                      if (identity != null && identity != '')
                            return $.jgrid.formatter.MetaType(identity);
                  })).join("，")
              }},
      </c:if>
      {label: '培训班名称', name: 'trainName', align: 'left',width: 350},
      <c:if test="${param.type!=CET_UPPER_TRAIN_TYPE_ABROAD}">
      <c:if test="${param.type!=CET_UPPER_TRAIN_TYPE_SCHOOL}">
      {
      label: '培训班主办方', name: 'organizer', width: 252, align: 'left', formatter: function (cellvalue, options, rowObject) {
      if (cellvalue == 0) {
        return $.trim(rowObject.otherOrganizer)
      }
      return $.jgrid.formatter.MetaType(cellvalue)
    }},
      {label: '培训班类型', name: 'trainType', width: 150, formatter: $.jgrid.formatter.MetaType},
    </c:if>
  <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_SCHOOL}">
    {label: '培训类别', name: 'specialType', width: 80, formatter: function (cellvalue, options, rowObject) {
            if(cellvalue==undefined) return '--'
            return _cMap.CET_PROJECT_TYPE_MAP[cellvalue]
        }},
      {
        label: '培训班类型', name: 'projectTypeId', align:'left', formatter: function (cellvalue, options, rowObject) {
        if (cellvalue == undefined) return '--'
            if(rowObject.specialType==<%=CetConstants.CET_PROJECT_TYPE_SPECIAL%>) {
                if (specialProjectTypeMap[cellvalue] == undefined) return '--'
                return specialProjectTypeMap[cellvalue].name
            }else{
                if (dailyProjectTypeMap[cellvalue] == undefined) return '--'
                return dailyProjectTypeMap[cellvalue].name
            }
    }},
    {label: '培训内容分类', name: 'category', align:'left', width: 180, formatter: function (cellvalue, options, rowObject) {
        if($.trim(cellvalue)=='') return '--'
        return ($.map(cellvalue.split(","), function(category){
            return $.jgrid.formatter.MetaType(category);
        })).join("，")
    }},
  </c:if>
      </c:if>
    { label: '培训形式', name: 'isOnline', width: 90, formatter:$.jgrid.formatter.TRUEFALSE, formatoptions:{on:'线上培训', off:'线下培训'}},
    {label: '培训<br/>开始时间', name: 'startDate', width: 90, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
    {label: '培训<br/>结束时间', name: 'endDate', width: 90, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
    {
      label: '培训<br/>天数', name: '_day', width: 60, formatter: function (cellvalue, options, rowObject) {
      return $.dayDiff(rowObject.startDate, rowObject.endDate);
    }
    },
    {label: '培训<br/>学时', name: 'period', width: 60},
      <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_ABROAD}">
    {label: '前往国家', name: 'country', width: 80},
      </c:if>
    {label: '培训地点', name: 'address', align: 'left', width: 180},
    <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_ABROAD}">
    {label: '组织培训机构', name: 'agency', align: 'left', width: 180},
      </c:if>
    {label: '培训总结', name: '_note', width: 200, formatter: function (cellvalue, options, rowObject) {

      var ret = "";
      var fileName = "培训总结("+rowObject.user.realname+")";
      var pdfNote = rowObject.pdfNote;
      if ($.trim(pdfNote) != '') {

        //console.log(fileName + " =" + pdfNote.substr(pdfNote.indexOf(".")))
        ret = '<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                        .format(encodeURI(pdfNote), encodeURI(fileName))
                + '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                        .format(encodeURI(pdfNote), encodeURI(fileName));
      }
      var wordNote = rowObject.wordNote;
      if ($.trim(wordNote) != '') {
        ret += '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                .format(encodeURI(wordNote), encodeURI(fileName));
      }
      return ret;
    }},
    {label: '培训成绩', name: 'score'},
    {label: '是否<br/>结业', name: 'isGraduate',formatter: $.jgrid.formatter.TRUEFALSE, width: 50},
    <c:if test="${_p_cetSupportCert}">
    {label: '结业证书', name: 'isGraduate', width: 70, formatter: function (cellvalue, options, rowObject) {
        if(!rowObject.isGraduate) return '--'
        return $.button.modal({
                    style:"btn-success",
                    url:"${ctx}/cet/cetProjectObj_graduate?ids[]="+rowObject.id,
                    icon:"fa-search",
                    label:"查看", attr:"data-width='850'"})
    }},
    </c:if>
      <c:if test="${param.type!=CET_UPPER_TRAIN_TYPE_SCHOOL}">
    {label: '派出单位', name: 'unitId', align: 'left', width: 150, formatter: function (cellvalue, options, rowObject) {
      if (rowObject.type==${CET_UPPER_TRAIN_TYPE_OW}) {
        return '党委组织部'
      }

      return $.jgrid.formatter.unit(cellvalue)
    }},
      </c:if>
      <c:if test="${param.addType!=CET_UPPER_TRAIN_ADD_TYPE_SELF}">
    {label: '是否计入<br/>年度学习任务', name: 'isValid', formatter: function (cellvalue, options, rowObject) {
      if (cellvalue==undefined) {
        return '--'
      }
      return cellvalue?'是':'否'
    }},
    </c:if>
    /*{label: '操作人', name: 'addUser.realname'},*/
      {label: '备注', name: 'remark', width: 150},
    {label: '添加时间', name: 'addTime', width: 150}
  ]
</script>