<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="space-4"></div>
<c:set var="_query"
       value="${not empty param.userId ||not empty param.enrollTime || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="crsApplicant:edit">
    <c:if test="${cls==1}">
        <a class="popupBtn btn btn-primary btn-sm" data-width="700"
           data-url="${ctx}/crsApplicant_au?postId=${param.postId}"><i class="fa fa-plus"></i> 添加</a>
        <%--  <a class="jqOpenViewBtn btn btn-primary btn-sm"
             data-url="${ctx}/crsApplicant_au"
             data-grid-id="#jqGrid2"
             data-querystr="&postId=${param.postId}"><i class="fa fa-edit"></i>
              修改</a>--%>
    </c:if>
    <a href="javascript:void(0)" class="jqOpenViewBtn btn btn-success btn-sm"
       data-open-by="page"
       data-load-el="#step-body-content-view"
       data-grid-id="#jqGrid2"
       data-querystr="&cls=${cls}"
       data-url="${ctx}/crsApplicant_recommend"><i class="fa fa-thumbs-o-up"></i> 推荐/自荐</a>

    <c:if test="${cls==2}">
        <button id="unSpecialBtn" class="jqItemBtn btn btn-warning btn-sm"
                data-title="取消破格"
                data-msg="确定取消破格？"
                data-grid-id="#jqGrid2"
                data-querystr="specialStatus=0"
                data-callback="_reload"
                data-url="${ctx}/crsApplicant_special"><i class="fa fa-star-o"></i> 取消破格
        </button>
    </c:if>
    <c:if test="${cls==3}">
        <a href="javascript:void(0)" class="jqOpenViewBtn btn btn-warning btn-sm"
           data-open-by="page"
           data-load-el="#step-body-content-view"
           data-grid-id="#jqGrid2"
           data-querystr="&cls=${cls}"
           data-url="${ctx}/crsApplicant_special"><i class="fa fa-star"></i> 破格</a>
    </c:if>
    <c:if test="${cls==2 || cls==3}">
        <button class="jqOpenViewBtn btn btn-info btn-sm"
                data-grid-id="#jqGrid2"  data-width="1000"
                data-url="${ctx}/crsApplicant_requireCheck?view=1"
                data-id-name="applicantId"><i class="fa fa-search"></i> 资格审核情况
        </button>
        <button id="unSpecialBtn" class="jqItemBtn btn btn-primary btn-sm"
                data-title="重新审核"
                data-msg="确定重新审核？"
                data-grid-id="#jqGrid2"
                data-callback="_stepReload"
                data-url="${ctx}/crsApplicant_requireCheck_back"><i class="fa fa-reply"></i> 重新审核
        </button>
    </c:if>
    <c:if test="${cls!=4}">
        <button data-url="${ctx}/user/crsPost_quit"
                data-title="退出"
                data-msg="确定退出竞聘？"
                data-grid-id="#jqGrid2"
                data-id-name="applicantId"
                data-querystr="postId=${param.postId}"
                data-callback="_stepReload"
                class="jqItemBtn btn btn-danger btn-sm">
            <i class="fa fa-minus-circle"></i> 退出
        </button>
    </c:if>
    <c:if test="${cls==4}">
        <button data-url="${ctx}/user/crsPost_reApply"
                data-title="重新报名"
                data-msg="确定重新报名？"
                data-grid-id="#jqGrid2"
                data-id-name="applicantId"
                data-querystr="postId=${param.postId}"
                data-callback="_stepReload"
                class="jqItemBtn btn btn-warning btn-sm">
            <i class="fa fa-reply"></i> 重新报名
        </button>
    </c:if>
    </shiro:hasPermission>
    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-grid-id="#jqGrid2"
            data-url="${ctx}/sysApprovalLog"
            data-width="850"
            data-querystr="&displayType=1&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT%>">
        <i class="fa fa-history"></i> 操作记录
    </button>
<c:if test="${cls==2 || cls==3}">
    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-grid-id="#jqGrid2"  data-width="800"
            data-url="${ctx}/crsApplicant_career"><i class="fa fa-search"></i> 管理工作经历
    </button>
    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-grid-id="#jqGrid2"  data-width="800"
            data-url="${ctx}/crsApplicant_report"><i class="fa fa-search"></i> 工作设想和预期目标
    </button>

    <div class="btn-group">
        <button data-toggle="dropdown" class="btn btn-success btn-sm dropdown-toggle">
            <i class="fa fa-download"></i> 导出  <span class="caret"></span>
        </button>
        <ul class="dropdown-menu dropdown-success" role="menu">
            <li>
                <a href="javascript:;" class="jqLinkBtn"
                   data-need-id="false" data-grid-id="#jqGrid2"
                   data-url="${ctx}/crsApplicant_data" data-querystr="cls=${cls}&postId=${param.postId}&export=1">
                    <i class="fa fa-file-excel-o"></i> 报名汇总表</a>
            </li>
            <li>
                <a href="javascript:;" class="jqLinkBtn"
                   data-need-id="false" data-grid-id="#jqGrid2"
                   data-url="${ctx}/crsApplicant_export" data-querystr="postId=${param.postId}">
                    <i class="fa fa-file-word-o"></i> 应聘人报名表</a>
            </li>
        </ul>
    </div>
    </c:if>
    <%-- <shiro:hasPermission name="crsApplicant:del">
         <button data-url="${ctx}/crsApplicant_batchDel"
                 data-title="删除"
                 data-msg="确定删除这{0}条数据？"
                 data-grid-id="#jqGrid2"
                 class="jqBatchBtn btn btn-danger btn-sm">
             <i class="fa fa-trash"></i> 删除
         </button>
     </shiro:hasPermission>--%>
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
                <div class="form-group">
                    <label>用户</label>
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?key=1"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>报名时间</label>
                    <div class="input-group tooltip-success" data-rel="tooltip" title="报名时间范围">
                        <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
                        <input placeholder="请选择报名时间范围" data-rel="date-range-picker"
                               class="form-control date-range-picker" type="text"
                               name="enrollTime" value="${param.enrollTime}"/>
                    </div>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-target="#step-body-content-view"
                       data-form="#searchForm2"
                       data-url="${ctx}/crsApplicant?postId=${param.postId}&cls=${cls}"><i class="fa fa-search"></i> 查找</a>

                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                data-target="#step-body-content-view"
                                data-url="${ctx}/crsApplicant?postId=${param.postId}&cls=${cls}">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="space-4"></div>
<div class="rownumbers">
    <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="40"></table>
    <div id="jqGridPager2"></div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    function _reload() {
        $("#jqGrid2").trigger("reloadGrid");
    }
    $.register.user_select($("#searchForm2 select[name=userId]"))
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/crsApplicant_data?callback=?&cls=${cls}&postId=${param.postId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '报名时间', name: 'enrollTime', width: 150, formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}, frozen: true
            },
            {label: '工作证号', name: 'user.code', width: 120, frozen: true},
            {label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {

                if(rowObject.cadre==undefined) return cellvalue;
                return $.cadre(rowObject.cadre.id, cellvalue, "_blank");

            }, frozen: true},
            <c:if test="${cls==2}">
            {label: '排序', align: 'center', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:"#jqGrid2",url: "${ctx}/crsApplicant_changeOrder"}, frozen: true},
            </c:if>
            {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 200, frozen: true},
            <c:if test="${cls==1}">
            {label: '信息审核', name: 'infoCheckStatus', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==${CRS_APPLICANT_INFO_CHECK_STATUS_INIT})
                    return '<button class="popupBtn btn btn-warning btn-xs" data-url="${ctx}/crsApplicant_infoCheck?applicantId={0}"><i class="fa fa-check-circle"></i> 信息审核</button>'
                            .format(rowObject.id);
                else
                return _cMap.CRS_APPLICANT_INFO_CHECK_STATUS_MAP[cellvalue];
            }, frozen: true},
            {label: '资格审核', name: '_requireCheck', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.infoCheckStatus!=${CRS_APPLICANT_INFO_CHECK_STATUS_PASS}) return '-';

                return '<button class="popupBtn btn btn-success btn-xs" data-width="1000" data-url="${ctx}/crsApplicant_requireCheck?applicantId={0}"><i class="fa fa-hourglass-1"></i> 资格审核</button>'
                        .format(rowObject.id);
            }, frozen: true},
            </c:if>
            {label: '性别', name: 'cadre.gender', width: 50, formatter: $.jgrid.formatter.GENDER},
            {label: '出生时间', name: 'cadre.birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '年龄', name: 'cadre.birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {label: '民族', name: 'cadre.nation', width: 60},
            {label: '政治面貌', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty, formatoptions:{useCadre:true}},
            {label: '党派加入时间', name: '_growTime', width: 120, formatter: $.jgrid.formatter.growTime, formatoptions:{useCadre:true}},
            {label: '最高学历', name: 'cadre.eduId', formatter: $.jgrid.formatter.MetaType},
            {label: '毕业学校', name: 'cadre.school', width: 150},
            {label: '所学专业', name: 'cadre.major', width: 180, align: 'left'},
            {
                label: '参加工作时间',
                name: 'cadre.workStartTime',
                width: 120,
                formatter: 'date',
                formatoptions: {newformat: 'Y.m'}
            },
            {label: '到校时间', name: 'cadre.arriveTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '专业技术职务', name: 'cadre.proPost', width: 120},
            {
                label: '专技职务评定时间',
                name: 'cadre.proPostTime',
                width: 130,
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {label: '专技岗位等级', name: 'cadre.proPostLevel', width: 150},
            {
                label: '专技岗位分级时间',
                name: 'cadre.proPostLevelTime',
                width: 130,
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {label: '管理岗位等级', name: 'cadre.manageLevel', width: 150},
            {
                label: '管理岗位分级时间',
                name: 'cadre.manageLevelTime',
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

                    return $.swfPreview(rowObject.recommendPdf, "${crsPost.name}-推荐-" + rowObject.user.realname + ".pdf", str.join(","));
                } else {
                    return "个人报名";
                }
                return '<a href="javascript:void(0)" class="loadPage" data-load-el="#step-body-content-view" ' +
                        'data-url="${ctx}/crsApplicant_recommend?id={0}&cls=${cls}">推荐/自荐</a>'.format(rowObject.id);
            }
            }
            <c:if test="${cls==2}">
            , {
                label: '通过方式',
                name: '_isRequireCheckPass',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {
                    //console.log(rowObject.requireCheckStatus =='${CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS}'&& rowObject.isRequireCheckPass)
                    return (rowObject.requireCheckStatus == '${CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS}' && rowObject.isRequireCheckPass) ? '破格通过' : '审核通过';
                }
            }
            </c:if>
            , {
                hidden: true, name: 'isRequireCheckPass', formatter: function (cellvalue, options, rowObject) {
                    return (rowObject.requireCheckStatus == '${CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS}' && rowObject.isRequireCheckPass) ? 1 : 0;
                }
            }
        ]
        <c:if test="${cls==2}">
        ,
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id);
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#unSpecialBtn").prop("disabled", true);
            } else if (status) {
                var rowData = $(this).getRowData(id);
                //console.log(rowData);
                $("#unSpecialBtn").prop("disabled", rowData.isRequireCheckPass == 0);
            } else {
                $("#unSpecialBtn").prop("disabled", true);
            }
        },
        rowattr: function (rowData, rowObject, rowId) {

            if ((rowObject.requireCheckStatus == '${CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS}' && rowObject.isRequireCheckPass)) {
                return {'class': 'warning'}
            }
        }
        </c:if>
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>