<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable myTableDiv">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}')"><i class="fa fa-flag"></i> 主持科研项目</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}')"><i class="fa fa-flag"></i> 参与科研项目</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_BOOK_SUMMARY?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_BOOK_SUMMARY}')"><i class="fa fa-flag"></i> 出版著作</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_PAPER_SUMMARY?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_PAPER_SUMMARY}')"><i class="fa fa-flag"></i> 发表论文</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_RESEARCH_REWARD?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH_REWARD}')"><i class="fa fa-flag"></i> 科研成果及获奖</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_RESEARCH?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH}')"><i class="fa fa-flag"></i> 预览</a>
    </li>
</ul>

<div class="row two-frames">
    <div class="left">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    ${type==CADRE_INFO_TYPE_RESEARCH?"初始数据":"参考模板"}
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" style="min-height: ${type==CADRE_INFO_TYPE_RESEARCH?'647px':'347px'}" id="orginal">
                    <c:if test="${type==CADRE_INFO_TYPE_RESEARCH}">
                        <p>${researchInInfo.content}</p>
                        <p>${researchDirectInfo.content}</p>
                        <p>${bookInfo.content}</p>
                        <p>${paperInfo.content}</p>
                        <p>${researchRewardInfo.content}</p>
                    </c:if>
                    <c:if test="${type==CADRE_INFO_TYPE_RESEARCH_REWARD}">
                        <c:if test="${fn:length(cadreRewards)>0}">获奖情况：</c:if>
                        <c:forEach items="${cadreRewards}" var="cadreReward">
                            <p style="text-indent: 2em">${cm:formatDate(cadreReward.rewardTime, "yyyy.MM")}&nbsp;${cadreReward.name}&nbsp;${cadreReward.unit}</p>
                        </c:forEach>
                    </c:if>
                    <c:if test="${type!=CADRE_INFO_TYPE_RESEARCH && type!=CADRE_INFO_TYPE_RESEARCH_REWARD}">
                        ${htmlFragment.content}
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div class="right">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    ${type==CADRE_INFO_TYPE_RESEARCH?"最终数据":"编辑区"}（<span
                        style="font-weight: bolder; color: red;">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" style="margin-bottom: 10px">
                    <textarea id="content">${cadreInfo.content}</textarea>
                    <input type="hidden" name="content">
                </div>
                <div class="modal-footer center">
                    <c:if test="${type==CADRE_INFO_TYPE_RESEARCH || type==CADRE_INFO_TYPE_RESEARCH_REWARD}">
                        <a href="javascript:" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            同步自动生成的数据
                        </a>
                    </c:if>
                    <input type="button" onclick="updateCadreInfo()" class="btn btn-primary" value="保存"/>

                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${type==CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}">
    <div class="space-4"></div>
    <div class="widget-box collapsed">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-battery-full"></i> 主持科研项目情况
            <div class="buttons">
                <shiro:hasPermission name="cadreResearch:edit">
                    <a class="popupBtn btn btn-success btn-sm"
                       data-url="${ctx}/cadreResearch_au?cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_DIRECT}"><i class="fa fa-plus"></i>
                        添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadreResearch_au"
                       data-grid-id="#jqGrid_cadreResearch_direct"
                       data-querystr="&cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_DIRECT}"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreResearch:del">
                    <button data-url="${ctx}/cadreResearch_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_cadreResearch_direct"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
        </h4>
        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main table-nonselect">
            <table id="jqGrid_cadreResearch_direct" data-width-reduce="50" class="jqGrid4"></table>
            <div id="jqGridPager_cadreResearch_direct"></div>
        </div>
    </div>
</div>
</c:if>
    <c:if test="${type==CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}">
    <div class="space-4"></div>
    <div class="widget-box collapsed">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-battery-full"></i> 参与科研项目情况
                <div class="buttons">
                    <shiro:hasPermission name="cadreResearch:edit">
                        <a class="popupBtn btn btn-success btn-sm"
                           data-url="${ctx}/cadreResearch_au?cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_IN}"><i class="fa fa-plus"></i>
                            添加</a>
                        <a class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/cadreResearch_au"
                           data-grid-id="#jqGrid_cadreResearch_in"
                           data-querystr="&cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_IN}"><i class="fa fa-edit"></i>
                            修改</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="cadreResearch:del">
                        <button data-url="${ctx}/cadreResearch_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid_cadreResearch_in"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-times"></i> 删除
                        </button>
                    </shiro:hasPermission>
                </div>
            </h4>
            <div class="widget-toolbar">
                <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main table-nonselect">
                <table id="jqGrid_cadreResearch_in" data-width-reduce="50" class="jqGrid4"></table>
                <div id="jqGridPager_cadreResearch_in"></div>
            </div>
        </div>
    </div>
    </c:if>
    <c:if test="${type==CADRE_INFO_TYPE_BOOK_SUMMARY}">
    <div class="space-4"></div>
    <div class="widget-box collapsed">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-history"></i> 出版著作情况
                <div class="buttons">
                    <a class="popupBtn btn  btn-sm btn-info"
                       data-url="${ctx}/cadreBook_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加</a>
                    <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                            data-url="${ctx}/cadreBook_au"
                            data-grid-id="#jqGrid_cadreBook"
                            data-querystr="&cadreId=${param.cadreId}">
                        <i class="fa fa-edit"></i> 修改
                    </button>
                    <button data-url="${ctx}/cadreBook_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_cadreBook"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </div>
            </h4>

            <div class="widget-toolbar">
                <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <table id="jqGrid_cadreBook" data-width-reduce="50" class="jqGrid4"></table>
                <div id="jqGridPager_cadreBook"></div>
            </div>
        </div>
    </div>
    </c:if>
    <c:if test="${type==CADRE_INFO_TYPE_PAPER_SUMMARY}">
    <div class="space-4"></div>
    <div class="widget-box collapsed">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-history"></i> 发表论文情况
                <div class="buttons">
                    <a class="popupBtn btn  btn-sm btn-info"
                       data-url="${ctx}/cadrePaper_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加</a>
                    <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                            data-url="${ctx}/cadrePaper_au"
                            data-grid-id="#jqGrid_cadrePaper"
                            data-querystr="&cadreId=${param.cadreId}">
                        <i class="fa fa-edit"></i> 修改
                    </button>
                    <button data-url="${ctx}/cadrePaper_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_cadrePaper"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </div>
            </h4>

            <div class="widget-toolbar">
                <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <table id="jqGrid_cadrePaper" data-width-reduce="50" class="jqGrid4"></table>
                <div id="jqGridPager_cadrePaper"></div>
            </div>
        </div>
    </div>
    </c:if>
    <c:if test="${type==CADRE_INFO_TYPE_RESEARCH_REWARD}">
    <div class="space-4"></div>
    <div class="widget-box collapsed">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-history"></i> 科研成果及获奖情况
                <div class="buttons">
                    <a class="popupBtn btn  btn-sm btn-info"
                       data-url="${ctx}/cadreReward_au?rewardType=${CADRE_REWARD_TYPE_RESEARCH}&cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加</a>
                    <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                            data-url="${ctx}/cadreReward_au"
                            data-grid-id="#jqGrid_cadreReward"
                            data-querystr="&rewardType=${CADRE_REWARD_TYPE_RESEARCH}&cadreId=${param.cadreId}">
                        <i class="fa fa-edit"></i> 修改
                    </button>
                    <button data-url="${ctx}/cadreReward_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_cadreReward"
                            data-callback="_reload"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </div>
            </h4>

            <div class="widget-toolbar">
                <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <table id="jqGrid_cadreReward" data-width-reduce="50" class="jqGrid4"></table>
                <div id="jqGridPager_cadreReward"></div>
            </div>
        </div>
    </div>
    </c:if>


<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    var ke = KindEditor.create('#content', {
        items : ["source", "|", "fullscreen"],
        height: '${type==CADRE_INFO_TYPE_RESEARCH?'550px':'250px'}',
        width: '700px'
    });
    function updateCadreInfo() {
        $.post("${ctx}/cadreInfo_updateContent", {
            cadreId: '${param.cadreId}',
            content: ke.html(),
            type: "${type}"
        }, function (ret) {
            if (ret.success) {
                SysMsg.info("保存成功", "", function () {
                    _innerPage("${type}")
                });
            }
        });
    }
    function copyOrginal() {
        //console.log($("#orginal").html())
        ke.html($("#orginal").html());
        SysMsg.info("复制成功，请务必点击\"保存\"按钮进行保存")
    }
</script>

    <script>
        function _innerPage(type) {
            $("#view-box .tab-content").load("${ctx}/cadreResearch_page?cadreId=${param.cadreId}&type=" + type)
        }
        <c:if test="${type==CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}">
        $("#jqGrid_cadreResearch_in").jqGrid({
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreResearch_in",
            url: '${ctx}/cadreResearch_data?researchType=${CADRE_RESEARCH_TYPE_IN}&${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {label: '项目起始时间', width: 120, name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'},frozen:true },
                {label: '项目结题时间', width: 120, name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'},frozen:true },
                {label: '项目名称', name: 'name', width: 250},
                {label: '项目类型', name: 'type', width: 250},
                {label: '委托单位', name: 'unit', width: 250},
                {label: '备注', name: 'remark', width: 350}
            ]
        }).on("initGrid", function () {
            $(window).triggerHandler('resize.jqGrid4');
        });
        </c:if>
        <c:if test="${type==CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}">
        $("#jqGrid_cadreResearch_direct").jqGrid({
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreResearch_direct",
            url: '${ctx}/cadreResearch_data?researchType=${CADRE_RESEARCH_TYPE_DIRECT}&${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {label: '项目起始时间', width: 120, name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'},frozen:true },
                {label: '项目结题时间', width: 120, name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'},frozen:true },
                {label: '项目名称', name: 'name', width: 250},
                {label: '项目类型', name: 'type', width: 250},
                {label: '委托单位', name: 'unit', width: 250},
                {label: '备注', name: 'remark', width: 350}
            ]
        }).on("initGrid", function () {
            $(window).triggerHandler('resize.jqGrid4');
        });
        </c:if>
        <c:if test="${type==CADRE_INFO_TYPE_BOOK_SUMMARY}">
        $("#jqGrid_cadreBook").jqGrid({
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreBook",
            url: '${ctx}/cadreBook_data?${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {label: '出版日期', name: 'pubTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'},frozen:true },
                {label: '著作名称', name: 'name', width: 350},
                {label: '出版社', name: 'publisher', width: 280},
                {label: '类型', name: 'type', width: 120, formatter:function(cellvalue, options, rowObject){
                    return _cMap.CADRE_BOOK_TYPE_MAP[cellvalue]
                }},
                {label: '备注', name: 'remark', width: 350}
            ]
        }).on("initGrid", function () {
            $(window).triggerHandler('resize.jqGrid4');
        });
        </c:if>
        <c:if test="${type==CADRE_INFO_TYPE_PAPER_SUMMARY}">
        $("#jqGrid_cadrePaper").jqGrid({
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadrePaper",
            url: '${ctx}/cadrePaper_data?${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {label: '发表日期', name: 'pubTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'},frozen:true },
                {label: '论文', name: 'filePath', width: 250,
                    formatter: function (cellvalue, options, rowObject) {
                        if(rowObject.filePath==undefined) return '-';
                        return '<a href="${ctx}/attach/download?path={0}&filename={1}">{1}</a>'
                                .format(rowObject.filePath,rowObject.fileName);
                    }},
                {label: '备注', name: 'remark', width: 350}
            ]
        }).on("initGrid", function () {
            $(window).triggerHandler('resize.jqGrid4');
        });
        </c:if>
        <c:if test="${type==CADRE_INFO_TYPE_RESEARCH_REWARD}">
        $("#jqGrid_cadreReward").jqGrid({
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreReward",
            url: '${ctx}/cadreReward_data?rewardType=${CADRE_REWARD_TYPE_RESEARCH}&${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {label: '日期', name: 'rewardTime', formatter: 'date', formatoptions: {newformat: 'Y.m'},frozen:true },
                {label: '获得奖项', name: 'name', width: 350},
                {label: '颁奖单位', name: 'unit', width: 280},
                {label: '获奖证书', name: 'proof', width: 250,
                    formatter: function (cellvalue, options, rowObject) {
                        if(rowObject.proof==undefined) return '-';
                        return '<a href="${ctx}/attach/download?path={0}&filename={1}">{1}</a>'
                                .format(rowObject.proof,rowObject.proofFilename);
                    }},
                {label: '排名', name: 'rank', formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==0) return '-';
                    return '第{0}'.format(cellvalue);
                }},
                {label: '备注', name: 'remark', width: 350}
            ]
        }).on("initGrid", function () {
            $(window).triggerHandler('resize.jqGrid4');
        });
        </c:if>

        $('#searchForm [data-rel="select2"]').select2();
        $('[data-rel="tooltip"]').tooltip();
    </script>