<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_PASS%>" var="CET_UNITTRAIN_RERECORD_PASS"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_PARTY%>" var="CET_UNITTRAIN_RERECORD_PARTY"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_CET%>" var="CET_UNITTRAIN_RERECORD_CET"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_SAVE%>" var="CET_UNITTRAIN_RERECORD_SAVE"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_MAP%>" var="CET_UNITTRAIN_RERECORD_MAP"/>
<c:set var="ROLE_CET_ADMIN" value="<%=RoleConstants.ROLE_CET_ADMIN%>"/>
<c:set var="ROLE_CET_ADMIN_PARTY" value="<%=RoleConstants.ROLE_CET_ADMIN_PARTY%>"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_PASS%>" var="CET_UNIT_PROJECT_STATUS_PASS"/>
<div class="widget-box transparent">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="tab-content padding-4" id="detail-content">
                <c:set var="_query" value="${not empty param.userId || not empty param.traineeTypeId || not empty param.title
                 || not empty param.postType || not empty param.identities || not empty param.prePeriod || not empty param.subPeriod || not empty param.score}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <c:if test="${empty reRecord}">
                    <c:if test="${cm:hasRole(ROLE_CET_ADMIN) || cetUnitProject.status!=CET_UNIT_PROJECT_STATUS_PASS}">
                    <shiro:hasPermission name="cetUnitProject:edit">
                        <button class="popupBtn btn btn-success btn-sm" data-width="1000"
                                data-url="${ctx}/cet/cetUnitTrain_au?projectId=${param.projectId}">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                data-url="${ctx}/cet/cetUnitTrain_au" data-width="1000"
                                data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                            修改
                        </button>
                        <shiro:hasRole name="${ROLE_CET_ADMIN}">
                            <button class="popupBtn btn btn-info btn-sm tooltip-success"
                            data-url="${ctx}/cet/cetUnitTrain_import?projectId=${param.projectId}"
                            data-rel="tooltip" data-placement="top"
                            title="从Excel中导入培训记录"><i class="fa fa-upload"></i> 批量导入</button>
                        </shiro:hasRole>
                        <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                data-url="${ctx}/cet/cetUnitTrain_data?projectId=${param.projectId}"
                                data-grid-id="#jqGrid2"
                                data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                            <i class="fa fa-download"></i> 导出</button>
                        <button data-url="${ctx}/cet/cetUnitTrain_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？（删除后不可恢复，请谨慎操作！）"
                                data-grid-id="#jqGrid2"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
                    </c:if>
                    </c:if>
                    <c:if test="${reRecord==1 && cm:isPermitted('cetUnitProject:check')}">
                            <button data-url="${ctx}/cet/cetUnitTrain_check"
                                    data-grid-id="#jqGrid2"
                                    class="jqOpenViewBatchBtn btn btn-success btn-sm">
                                <i class="fa fa-check-circle-o"></i> 审批
                            </button>
                    </c:if>
                    <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                            data-url="${ctx}/cet/cetUnitTrain_data"
                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                        <i class="fa fa-download"></i> 导出
                    </button>--%>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                        <div class="widget-toolbar">
                            <a href="#" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding columns">
                            <form class="form-inline search-form" id="trianSearchForm">
                                <div class="form-group">
                                    <label>参训人姓名</label>
                                    <select data-ajax-url="${ctx}/sysUser_selects" data-rel="select2-ajax"
                                            name="userId" data-placeholder="请输入账号或姓名或工号">
                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>参训人类型</label>
                                    <select data-rel="select2" name="traineeTypeId" data-placeholder="请选择"data-width="272">
                                        <option></option>
                                        <c:forEach items="${traineeTypeMap}" var="entity">
                                            <c:if test="${entity.value.code!='t_reserve' && entity.value.code!='t_candidate'}">
                                                <option value="${entity.value.id}">${entity.value.name}</option>
                                            </c:if>
                                        </c:forEach>
                                        <option value="0">其他</option>
                                    </select>
                                    <script>$("#trianSearchForm select[name=traineeTypeId]").val(${param.traineeTypeId});</script>
                                </div>
                                <div class="form-group">
                                    <label>时任单位及职务</label>
                                    <input class="form-control search-query" name="title" type="text" value="${param.title}"
                                           placeholder="请输入">
                                </div>
                                <div class="form-group">
                                    <label>时任职务属性</label>
                                    <select data-rel="select2" name="postType"
                                            data-width="150"
                                            data-placeholder="请选择">
                                        <option></option>
                                        <c:import url="/metaTypes?__code=mc_post"/>
                                    </select>
                                    <script type="text/javascript">
                                        $("#trianSearchForm select[name=postType]").val(${param.postType});
                                    </script>
                                </div>
                                <c:if test="${cm:getMetaTypes('mc_cet_identity').size()>0}">
                                    <div class="form-group">
                                        <label>参训人身份</label>
                                        <select  class="multiselect" multiple="" name="identities"
                                                 data-width="120"
                                                 data-placeholder="请选择">
                                            <c:import url="/metaTypes?__code=mc_cet_identity"/>
                                        </select>
                                        <script type="text/javascript">
                                            $.register.multiselect($('#trianSearchForm select[name=identities]'), ${cm:toJSONArray(selectIdentities)});
                                        </script>
                                    </div>
                                </c:if>
                                <div class="form-group column">
                                    <label>培训学时</label>
                                    <div class="input-group input">
                                        <input style="width: 50px" class="form-control search-query float" type="text" name="prePeriod"
                                               value="${param.prePeriod}">
                                    </div>
                                    <label>至</label>
                                    <div class="input-group">
                                        <input style="width: 50px" class="form-control search-query float"
                                               type="text"
                                               name="subPeriod"
                                               value="${param.subPeriod}">
                                    </div>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/cet/cetUnitTrain_page?projectId=${param.projectId}&reRecord=${reRecord}"
                                       data-target="#train-content"
                                       data-form="#trianSearchForm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/cet/cetUnitTrain_page?projectId=${param.projectId}&reRecord=${reRecord}"
                                                data-target="#train-content">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="5"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
</div>
<script>
    var traineeTypeMap = ${cm:toJSONObject(traineeTypeMap)};
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/cet/cetUnitTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '参训人工号', width: 110, name: 'user.code', frozen:true},
            {label: '参训人姓名', name: 'user.realname', frozen:true},
            { label: '参训人类型', name: 'traineeTypeId', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined)return '--';
                if(cellvalue==0) return rowObject.otherTraineeType;
                return traineeTypeMap[cellvalue].name;
            }, width:180},
            <c:if test="${reRecord==1}">
            {label: '补录进度', name: 'status', width: 120, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue==${CET_UNITTRAIN_RERECORD_CET})
                        return '${CET_UNITTRAIN_RERECORD_MAP.get(CET_UNITTRAIN_RERECORD_CET)}';
                    else if (cellvalue==${CET_UNITTRAIN_RERECORD_PARTY})
                        return '${CET_UNITTRAIN_RERECORD_MAP.get(CET_UNITTRAIN_RERECORD_PARTY)}';
                    else if (cellvalue==${CET_UNITTRAIN_RERECORD_SAVE})
                        return '${CET_UNITTRAIN_RERECORD_MAP.get(CET_UNITTRAIN_RERECORD_SAVE)}';
                }},
            </c:if>
            {label: '时任单位及职务', name: 'title', align: 'left', width: 350},
            {label: '时任职务属性', name: 'postType', width: 120, align: 'left',formatter: $.jgrid.formatter.MetaType},
            <c:if test="${cm:getMetaTypes('mc_cet_identity').size()>0}">
                {
                    label: '参训人身份', name: 'identity', width: 150, align: 'left', formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == null || cellvalue == '') {
                            return "--";
                        }
                        return ($.map(cellvalue.split(","), function(identity){
                            if (identity != null && identity != '')
                                return $.jgrid.formatter.MetaType(identity);
                        })).join("，")
                    }},
            </c:if>

            {label: '完成培训学时', name: 'period'},
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
            /*{label: '操作人', name: 'addUser.realname'},*/
            {label: '添加时间', name: 'addTime', width: 150},
            <c:if test="${reRecord==1}">
                {label: '补录审批备注', name: 'reason', width: 150},
            </c:if>
            { label: '备注',name: 'remark', align: 'left', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>