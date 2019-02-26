<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
  <div class="col-xs-12">

    <div id="body-content" class="myTableDiv"
         data-url-page="${ctx}/crsStat"
         data-url-export="${ctx}/crsStatCandidate_data"
         data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
      <c:set var="_query" value="${ not empty param.year ||not empty param.isFirst
      || not empty param.startAge|| not empty param.endAge|| not empty param.dpTypes
      || not empty param.maxEdus|| not empty param.isMiddle}"/>
      <div class="tabbable">
        <jsp:include page="menu.jsp"/>
        <div class="tab-content">
          <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">

            </div>
              <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                  <div class="widget-header">
                      <h4 class="widget-title">搜索</h4>

                      <div class="widget-toolbar">
                          <a href="javascript:;" data-action="collapse">
                              <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                          </a>
                      </div>
                  </div>
                  <div class="widget-body">
                      <div class="widget-main no-padding">
                          <form class="form-inline search-form" id="searchForm">
                              <div class="form-group">
                                  <label>年份</label>

                                  <div class="input-group">
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                      <input class="form-control date-picker" placeholder="请选择年份"
                                             name="year" type="text"
                                             data-date-format="yyyy" data-date-min-view-mode="2"
                                             value="${param.year}"/>
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label>专家推荐排名</label>
                                  <select data-rel="select2" name="isFirst" data-placeholder="请选择">
                                      <option></option>
                                      <option value="1">排名第一</option>
                                      <option value="0">排名第二</option>
                                  </select>
                              </div>
                              <div class="form-group">
                                  <label>年龄</label>
                                  <input class="num" type="text" name="startAge"
                                         value="${param.startAge}"> 至 <input class="num"
                                                                             type="text"
                                                                             name="endAge"
                                                                             value="${param.endAge}">
                              </div>
                              <div class="form-group">
                                  <label>党派</label>
                                  <select class="multiselect" multiple="" name="dpTypes"
                                          style="width: 250px;">
                                      <option value="-1">非党干部</option>
                                      <option value="0">中共党员</option>
                                      <c:import url="/metaTypes?__code=mc_democratic_party"/>
                                  </select>
                              </div>
                              <div class="form-group">
                                  <label>最高学历</label>
                                  <select class="multiselect" multiple="" name="maxEdus">
                                      <c:import url="/metaTypes?__code=mc_edu"/>
                                  </select>
                              </div>
                              <div class="form-group">
                                  <label>是否现任干部</label>
                                  <select data-rel="select2" name="isMiddle" data-placeholder="请选择">
                                      <option></option>
                                      <option value="1">是</option>
                                      <option value="0">否</option>
                                  </select>
                              </div>
                              <div class="clearfix form-actions center">
                                  <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                      查找</a>

                                  <c:if test="${_query}">&nbsp;
                                      <button type="button" class="reloadBtn btn btn-warning btn-sm">
                                          <i class="fa fa-reply"></i> 重置
                                      </button>
                                  </c:if>
                              </div>
                          </form>
                      </div>
                  </div>
              </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
          </div>
        </div>
      </div>
    </div>
    <div id="body-content-view"></div>
  </div>
</div>
<script>
    $.register.date($('.date-picker'));
    $.register.multiselect($('#searchForm select[name=dpTypes]'), ${cm:toJSONArray(selectDpTypes)});
    $.register.multiselect($('#searchForm select[name=maxEdus]'), ${cm:toJSONArray(selectMaxEdus)});
  $("#jqGrid").jqGrid({
    url: '${ctx}/crsStatCandidate_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
    colModel: [
        {label: '年度', name: 'crsPostYear', width:'60', frozen: true},
        {
            label: '编号', name: 'crsPostSeq', formatter: function (cellvalue, options, rowObject) {
            var type = _cMap.CRS_POST_TYPE_MAP[rowObject.crsPostType];
            return type + "〔" + rowObject.crsPostYear + "〕" + rowObject.crsPostSeq + "号";

        }, width: 180, frozen: true
        },
        {label: '招聘岗位', name: 'crsPostName', width:'300', frozen: true, formatter: function (cellvalue, options, rowObject) {
            return '<a href="javascript:;" class="openView" data-url="${ctx}/crsPost_detail?id={0}">{1}</a>'
                    .format(rowObject.crsPostId, cellvalue);
        }},
        {label: '分管工作', name: 'crsPostJob', width:'300', formatter: $.jgrid.formatter.NoMultiSpace},
        {label: '应聘人工作证号', name: 'code', width: 150, frozen: true},
        {
            label: '应聘人姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
            return $.cadre(rowObject.id, cellvalue);
        }, frozen: true
        },
        {label: '专家推荐排名', name: 'isFirst', width: 120, formatter: function (cellvalue, options, rowObject) {
            return cellvalue?"排名第1":"排名第2";
        }},
        {label: '专家组人数', name: 'expertCount', width: 120},
        {label: '排第1票数', name: 'recommendFirstCount', width: 120},
        {label: '排第2票数', name: 'recommendSecondCount', width: 120},
        {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
        {label: '性别', name: 'gender', width: 50, formatter:$.jgrid.formatter.GENDER},
        {label: '民族', name: 'nation', width: 60},
        {label: '出生时间', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
        {label: '党派', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty},
        {label: '党派加入时间', name: '_growTime', width: 120, formatter: $.jgrid.formatter.growTime},
        {
            label: '参加工作时间', name: 'workTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y.m'}
        },
        {label: '到校时间', name: 'arriveTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '最高学历', name: 'eduId', formatter: $.jgrid.formatter.MetaType},
        {label: '毕业学校', name: 'school', width: 150},
        {label: '所学专业', name: 'major', width: 180, align: 'left'},
        {label: '专业技术职务', name: 'proPost', width: 120},
        {label: '专技职务评定时间', name: 'proPostTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '专技岗位等级', name: 'proPostLevel', width: 150},
        {
            label: '专技岗位分级时间',
            name: 'proPostLevelTime',
            width: 130,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        {label: '管理岗位等级', name: 'manageLevel', width: 150},
        {
            label: '管理岗位分级时间',
            name: 'manageLevelTime',
            width: 130,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '推荐/自荐', name: 'isRecommend', width: 180, formatter: function (cellvalue, options, rowObject) {

            var str = [];
            if (cellvalue) {
                if ($.trim(rowObject.recommendOw) != '') str.push(rowObject.recommendOw);
                if ($.trim(rowObject.recommendCadre) != '') str.push(rowObject.recommendCadre);
                if ($.trim(rowObject.recommendCrowd) != '') str.push(rowObject.recommendCrowd);

                return $.swfPreview(rowObject.recommendPdf, rowObject.crsPostName+"-推荐-" + rowObject.realname + ".pdf", str.join(","));
            } else {
                return "个人报名";
            }
            return '<a href="javascript:void(0)" class="loadPage" data-load-el="#step-body-content-view" ' +
                    'data-url="${ctx}/crsApplicant_recommend?id={0}&cls=${param.cls}">推荐/自荐</a>'.format(rowObject.applicantId);
        }},
        {label: '应聘报名表', name: '_table', formatter: function (cellvalue, options, rowObject) {
            return '<button class="linkBtn btn btn-success btn-xs" ' +
                    'data-url="${ctx}/crsApplicant_export?ids[]={0}"><i class="fa fa-download"></i> 导出</button>'
                            .format(rowObject.applicantId)
        }},
        {label: '应聘PPT', name: 'ppt', formatter: function (cellvalue, options, rowObject) {
            if(rowObject.ppt==undefined) return ''
            return '<a href="${ctx}/attach/download?path={0}&filename={1}">下载</a>'
                    .format(rowObject.ppt, rowObject.pptName)
        }},
        {label: '是否为现任干部', name: 'status', width: 180, formatter: function (cellvalue, options, rowObject) {
            return cellvalue==${CADRE_STATUS_MIDDLE}?"是":"否"
        }}, {name: 'candidateId', key:true, hidden:true}
    ]
  }).jqGrid("setFrozenColumns");
  $(window).triggerHandler('resize.jqGrid');
  $.initNavGrid("jqGrid", "jqGridPager");
  $('#searchForm [data-rel="select2"]').select2();
  $('[data-rel="tooltip"]').tooltip();
</script>