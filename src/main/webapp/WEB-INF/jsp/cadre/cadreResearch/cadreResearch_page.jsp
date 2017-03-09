<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}')"><i
                class="fa fa-flag"></i> 主持科研项目</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}')"><i class="fa fa-flag"></i>
            参与科研项目</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_BOOK_SUMMARY?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_BOOK_SUMMARY}')"><i class="fa fa-flag"></i>
            出版著作</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_PAPER_SUMMARY?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_PAPER_SUMMARY}')"><i class="fa fa-flag"></i>
            发表论文</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_RESEARCH_REWARD?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH_REWARD}')"><i class="fa fa-flag"></i>
            科研成果及获奖</a>
    </li>
<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
    <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
    <li class="${type==CADRE_INFO_TYPE_RESEARCH?"active":""}">
        <a href="javascript:" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH}')"><i class="fa fa-flag"></i> 预览</a>
    </li>
</shiro:lacksRole>
    </shiro:hasPermission>
<shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
    <div class="buttons" style="position:absolute;left: 750px;">
        <a class="popupBtn btn btn-warning btn-sm"
           data-width="800"
           data-url="${ctx}/hf_content?code=${HF_CADRE_RESEARCH}">
            <i class="fa fa-info-circle"></i> 填写说明</a>
    </div>
</shiro:lacksRole>
</ul>
<c:if test="${type!=CADRE_INFO_TYPE_RESEARCH_REWARD}">
<div class="row two-frames">
    <div class="left">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    ${(type==CADRE_INFO_TYPE_RESEARCH||type==CADRE_INFO_TYPE_RESEARCH_REWARD)?"初始数据":"参考模板"}
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" style="min-height: ${type==CADRE_INFO_TYPE_RESEARCH?'647px':'347px'}"
                     id="orginal">
                    <c:if test="${type==CADRE_INFO_TYPE_RESEARCH}">
                        <p>${researchInInfo.content}</p>

                        <p>${researchDirectInfo.content}</p>

                        <p>${bookInfo.content}</p>

                        <p>${paperInfo.content}</p>

                        <p><c:import url="/cadreReward_fragment"/></p>
                    </c:if>
                    <%--<c:if test="${type!=CADRE_INFO_TYPE_RESEARCH}">
                        ${htmlFragment.content}
                    </c:if>--%>

                </div>
            </div>
        </div>
    </div>
    <div class="right">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    ${type==CADRE_INFO_TYPE_RESEARCH?"最终数据":"编辑区"}（<span
                        style="font-weight: bolder; color: red;"
                        id="saveTime">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" style="margin-bottom: 10px">
                    <textarea id="content">${cadreInfo.content}</textarea>
                    <input type="hidden" name="content">
                </div>
                <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
                    <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
                    <div class="modal-footer center">
                        <c:if test="${type==CADRE_INFO_TYPE_RESEARCH || type==CADRE_INFO_TYPE_RESEARCH_REWARD}">
                            <a href="javascript:" onclick="copyOrginal()" class="btn btn-success">
                                <i class="ace-icon fa fa-copy"></i>
                                同步初始数据
                            </a>
                        </c:if>
                        <input type="button" id="saveBtn" onclick="updateCadreInfo()" class="btn btn-primary"
                               value="保存"/>

                    </div>
                    </shiro:lacksRole>
                </c:if>
            </div>
        </div>
    </div>
</div>
</c:if>
<c:if test="${type==CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}">
    <div class="space-4"></div>
    <div class="widget-box ${count==0?'collapsed':''}">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-battery-full"></i> 主持科研项目情况
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
                <div class="buttons">
                    <shiro:hasPermission name="cadreResearch:edit">
                        <a class="popupBtn btn btn-success btn-sm"
                           data-url="${ctx}/cadreResearch_au?cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_DIRECT}"><i
                                class="fa fa-plus"></i>
                            添加</a>
                        <a class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/cadreResearch_au"
                           data-grid-id="#jqGrid_cadreResearch_direct"
                           data-querystr="&cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_DIRECT}"><i
                                class="fa fa-edit"></i>
                            修改</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="cadreResearch:del">
                        <button data-url="${ctx}/cadreResearch_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid_cadreResearch_direct"
                                data-querystr="cadreId=${param.cadreId}"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-times"></i> 删除
                        </button>
                    </shiro:hasPermission>
                </div>
        </shiro:lacksRole>
        </c:if>
            </h4>
            <div class="widget-toolbar">
                <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main table-nonselect">
                <table id="jqGrid_cadreResearch_direct" data-width-reduce="60" class="jqGrid4"></table>
                <div id="jqGridPager_cadreResearch_direct"></div>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${type==CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}">
    <div class="space-4"></div>
    <div class="widget-box  ${count==0?'collapsed':''}">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-battery-full"></i> 参与科研项目情况
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
                <div class="buttons">
                    <shiro:hasPermission name="cadreResearch:edit">
                        <a class="popupBtn btn btn-success btn-sm"
                           data-url="${ctx}/cadreResearch_au?cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_IN}"><i
                                class="fa fa-plus"></i>
                            添加</a>
                        <a class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/cadreResearch_au"
                           data-grid-id="#jqGrid_cadreResearch_in"
                           data-querystr="&cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_IN}"><i
                                class="fa fa-edit"></i>
                            修改</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="cadreResearch:del">
                        <button data-url="${ctx}/cadreResearch_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid_cadreResearch_in"
                                data-querystr="cadreId=${param.cadreId}"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-times"></i> 删除
                        </button>
                    </shiro:hasPermission>
                </div>
        </shiro:lacksRole>
        </c:if>
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
    <div class="widget-box  ${count==0?'collapsed':''}">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-history"></i> 出版著作情况
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
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
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </div>
        </shiro:lacksRole>
        </c:if>
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
    <div class="widget-box  ${count==0?'collapsed':''}">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-history"></i> 发表论文情况
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
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
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </div>
    </shiro:lacksRole>
        </c:if>
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
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
        <div class="space-4"></div>
        <div class="jqgrid-vertical-offset buttons">
            <a class="popupBtn btn  btn-sm btn-info"
               data-url="${ctx}/cadreReward_au?rewardType=${CADRE_REWARD_TYPE_RESEARCH}&cadreId=${param.cadreId}"><i
                    class="fa fa-plus"></i>
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
                    data-querystr="cadreId=${param.cadreId}"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-times"></i> 删除
            </button>
        </div>
        </shiro:lacksRole>
    </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadreReward" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadreReward"></div>
</c:if>

<div class="footer-margin"/>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    var ke_height =${type==CADRE_INFO_TYPE_RESEARCH?550:250};
    var readonlyMode = false;
    <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
    ke_height += 60;
    readonlyMode = true;
    </c:if>
    var ke = KindEditor.create('#content', {
        cssPath:"${ctx}/css/ke.css",
        items: ["source", "|", "fullscreen"],
        height: ke_height + 'px',
        width: '700px',
        readonlyMode:readonlyMode
    });
    function updateCadreInfo() {
        var html = $.trim(ke.html());
        if (html != '') {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: html,
                type: "${type}"
            }, function (ret) {
                if (ret.success) {
                    _innerPage("${type}", function () {
                        $("#saveBtn").tip({content: "保存成功"});
                    });
                }
            });
        }
    }
    function copyOrginal() {
        var html = $.trim($("#orginal").html());
        if (html != '') {
            ke.html(html);
            $("#saveTime").html("未保存");
            $("#saveBtn").tip({content: '复制成功，请点击"保存"按钮进行保存'});
        }
    }
</script>

<script>
    function _innerPage(type, fn) {
        $("#view-box .tab-content").load("${ctx}/cadreResearch_page?cadreId=${param.cadreId}&type=" + type, null, fn)
    }
    <c:if test="${type==CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}">
    $("#jqGrid_cadreResearch_in").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreResearch_in",
        url: '${ctx}/cadreResearch_data?researchType=${CADRE_RESEARCH_TYPE_IN}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreResearch
    }).on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid4');
    });
    </c:if>
    <c:if test="${type==CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}">
    $("#jqGrid_cadreResearch_direct").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreResearch_direct",
        url: '${ctx}/cadreResearch_data?researchType=${CADRE_RESEARCH_TYPE_DIRECT}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreResearch
    }).on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid4');
    });
    </c:if>
    <c:if test="${type==CADRE_INFO_TYPE_BOOK_SUMMARY}">
    $("#jqGrid_cadreBook").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreBook",
        url: '${ctx}/cadreBook_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreBook
    }).on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid4');
    });
    </c:if>
    <c:if test="${type==CADRE_INFO_TYPE_PAPER_SUMMARY}">
    $("#jqGrid_cadrePaper").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePaper",
        url: '${ctx}/cadrePaper_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadrePaper
    }).on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid4');
    });
    </c:if>
    <c:if test="${type==CADRE_INFO_TYPE_RESEARCH_REWARD}">
    $("#jqGrid_cadreReward").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreReward",
        url: '${ctx}/cadreReward_data?rewardType=${CADRE_REWARD_TYPE_RESEARCH}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreReward
    }).on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid2');
    });
    </c:if>

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'));
    });
</script>