<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cr/constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${cm:formatDate(crInfo.addDate, "yyyy.MM.dd")} 招聘信息
                </span>
    </div>
    <div class="space-4"></div>
    <c:set var="_query"
           value="${not empty param.userId ||not empty param.submitTime
            ||not empty param.postId ||not empty param.firstPostId ||not empty param.secondPostId
            || not empty param.hasReport || not empty param.sort}"/>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="crApplicant:edit">
            <button class="popupBtn btn btn-info btn-sm"
                    data-url="${ctx}/crApplicant_au?infoId=${param.infoId}">
                <i class="fa fa-plus"></i> 添加
            </button>
            <button class="jqOpenViewBtn btn btn-primary btn-sm"
                    data-url="${ctx}/crApplicant_au"
                    data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                修改
            </button>
        </shiro:hasPermission>

        <button class="jqExportBtn btn btn-success btn-sm"
                   data-grid-id="#jqGrid2"
                   data-url="${ctx}/crApplicant_export?infoId=${param.infoId}">
                    <i class="fa fa-file-word-o"></i> 应聘人报名表</button>

        <button class="jqExportBtn btn btn-warning btn-sm"
                   data-grid-id="#jqGrid2"
                   data-url="${ctx}/crApplicant_data?infoId=${param.infoId}&export=1">
                    <i class="fa fa-file-excel-o"></i> 报名汇总表</button>

        <shiro:hasPermission name="crApplicant:del">
            <button data-url="${ctx}/crApplicant_batchDel?infoId=${param.infoId}"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid2"
                    data-callback="_delCallback"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
        <shiro:hasPermission name="crApplicant:report">
            <button data-url="${ctx}/crApplicant_report?infoId=${param.infoId}&hasReport=1"
                    data-title="已提交纸质表"
                    data-msg="确定已提交纸质表？（已选{0}条数据）"
                    data-grid-id="#jqGrid2"
                    data-callback="_delCallback"
                    class="jqBatchBtn btn btn-info btn-sm">
                <i class="fa fa-check"></i> 已交纸质表
            </button>
            <button data-url="${ctx}/crApplicant_report?infoId=${param.infoId}&hasReport=0"
                    data-title="未交纸质表"
                    data-msg="确定未交纸质表？（已选{0}条数据）"
                    data-grid-id="#jqGrid2"
                    data-callback="_delCallback"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-times"></i> 未交纸质表
            </button>
        </shiro:hasPermission>
        <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                data-url="${ctx}/crApplicant_data"
                data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
            <i class="fa fa-download"></i> 导出
        </button>--%>
    </div>
    <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
        <div class="widget-header">
            <h4 class="widget-title">搜索</h4>
            <span class="widget-note">${note_searchbar}</span>
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
                        <label>应聘人</label>
                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>提交时间</label>
                        <div class="input-group tooltip-success" data-rel="tooltip" title="提交时间范围">
                            <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
                            <input placeholder="请选择提交时间范围" data-rel="date-range-picker"
                                   class="form-control date-range-picker" type="text"
                                   name="submitTime" value="${param.submitTime}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>报名岗位</label>
                        <select required name="postId" class="multiselect" multiple="" data-width="273">
                            <c:forEach items="${postMap}" var="post">
                                <option value="${post.value.id}">${post.value.name}</option>
                            </c:forEach>
                        </select>
                         <script>
                            $.register.multiselect($('#searchForm2 select[name=postId]'), ${cm:toJSONArray(selectPostIds)});
                        </script>
                    </div>
                    <div class="form-group">
                        <label>第一志愿</label>
                        <select required name="firstPostId" data-width="273" data-placeholder="请选择" data-rel="select2">
                            <option></option>
                            <c:forEach items="${postMap}" var="post">
                                <option value="${post.value.id}">${post.value.name}</option>
                            </c:forEach>
                        </select>
                         <script>
                            $("#searchForm2 select[name=firstPostId]").val('${param.firstPostId}')
                        </script>
                    </div>
                    <div class="form-group">
                        <label>第二志愿</label>
                        <select required name="secondPostId" data-width="273" data-placeholder="请选择" data-rel="select2">
                            <option></option>
                            <c:forEach items="${postMap}" var="post">
                                <option value="${post.value.id}">${post.value.name}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#searchForm2 select[name=secondPostId]").val('${param.secondPostId}')
                        </script>
                    </div>
                    <div class="form-group">
                        <label>纸质表</label>
                        <select data-rel="select2" data-width="100" name="hasReport"  data-placeholder="请选择">
                            <option></option>
                            <option value="1">已提交</option>
                            <option value="0">未提交</option>
                        </select>
                        <script>
                            $("#searchForm2 select[name=hasReport]").val('${param.hasReport}')
                        </script>
                    </div>
                    <div class="clearfix form-actions center">
                        <a class="jqSearchBtn btn btn-default btn-sm"
                           data-url="${ctx}/crApplicant?infoId=${param.infoId}"
                           data-target="#body-content-view"
                           data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                        <c:if test="${_query}">&nbsp;
                            <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/crApplicant?infoId=${param.infoId}"
                                    data-target="#body-content-view">
                                <i class="fa fa-reply"></i> 重置
                            </button>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="40"></table>
    <div id="jqGridPager2"></div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    function _delCallback() {
        $("#jqGrid2").trigger("reloadGrid");
        $("#jqGrid").trigger("reloadGrid");
    }
    var postMap = ${cm:toJSONObject(postMap)}
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers: true,
        url: '${ctx}/crApplicant_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '应聘人', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.cadre==undefined) return cellvalue;
                return $.cadre(rowObject.cadre.id, cellvalue, {loadId:'body-content-view2',
                    hideId:'body-content-view'});
            }, frozen: true},
            {label: '工作证号', name: 'user.code', width:120},
           /* { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2',url:'${ctx}/crApplicant_changeOrder', frozen:true }},*/
            {label: '第一志愿', name: 'firstPostId', width:280 , align:'left', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined || postMap[cellvalue]==undefined) return '--'
                    return postMap[cellvalue].name
                }},
            {label: '资格审核', name: 'firstPostId', width:120 , formatter: function (cellvalue, options, rowObject) {

                    if(cellvalue==undefined || postMap[cellvalue]==undefined) return '--'

                    var btnCss = 'btn-primary'
                    var btnLabel = '<i class="fa fa-hand-o-right"></i> 待审核'
                    if(rowObject.firstCheckStatus=='${CR_REQUIRE_CHECK_STATUS_PASS}'){
                        btnCss = 'btn-success'
                        btnLabel = '<i class="fa fa-check"></i> 通过'
                    }else if(rowObject.firstCheckStatus=='${CR_REQUIRE_CHECK_STATUS_UNPASS}'){
                        btnCss = 'btn-danger'
                        btnLabel = '<i class="fa fa-times"></i> 未通过'
                    }

                    return ('<button class="popupBtn btn {2} btn-xs" ' +
                        '                data-width="1000" ' +
                        '                data-url="${ctx}/crApplicant_requireCheck?applicantId={0}&postId={1}" ' +
                        '                data-id-name="applicantId">{3}'  +
                        '        </button>').format(rowObject.id, cellvalue, btnCss, btnLabel)
                }
            },
            {label: '第二志愿', name: 'secondPostId', width:280 , align:'left', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined || postMap[cellvalue]==undefined) return '--'
                    return postMap[cellvalue].name
                }},
            {label: '资格审核', name: 'secondPostId', width:120, formatter: function (cellvalue, options, rowObject) {

                    if(cellvalue==undefined || postMap[cellvalue]==undefined) return '--'

                    var btnCss = 'btn-primary'
                    var btnLabel = '<i class="fa fa-hand-o-right"></i> 待审核'
                    if(rowObject.secondCheckStatus=='${CR_REQUIRE_CHECK_STATUS_PASS}'){
                        btnCss = 'btn-success'
                        btnLabel = '<i class="fa fa-check"></i> 通过'
                    }else if(rowObject.secondCheckStatus=='${CR_REQUIRE_CHECK_STATUS_UNPASS}'){
                        btnCss = 'btn-danger'
                        btnLabel = '<i class="fa fa-times"></i> 未通过'
                    }

                    return ('<button class="popupBtn btn {2} btn-xs" ' +
                        '                data-width="1000" ' +
                        '                data-url="${ctx}/crApplicant_requireCheck?applicantId={0}&postId={1}" ' +
                        '                data-id-name="applicantId">{3}'  +
                        '        </button>').format(rowObject.id, cellvalue, btnCss, btnLabel)

                }
            },
            {label: '年度考核结果', name: 'eva', width:150 , align:'left', formatter: function (cellvalue, options, rowObject) {
                      if(cellvalue==undefined) return '--'
                    var evas = cellvalue.split(",")
                    return $.map(evas, function(eva){
                        return $.jgrid.formatter.MetaType(eva)
                    })
            }},
            {label: '竞聘理由', name: 'reason', width:400 , align:'left'},
            {label: '保存时间', name: 'enrollTime', width:160},
            {label: '提交时间', name: 'submitTime', width:160},
            {label: '纸质表', name: 'hasReport', formatter:$.jgrid.formatter.TRUEFALSE,
                formatoptions: {on: '已提交', off:'<span class="red">未提交</span>'}},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>