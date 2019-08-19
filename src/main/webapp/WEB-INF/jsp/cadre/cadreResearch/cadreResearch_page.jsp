<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH%>" var="CADRE_INFO_TYPE_RESEARCH"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY%>" var="CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY%>" var="CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH_REWARD%>" var="CADRE_INFO_TYPE_RESEARCH_REWARD"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_PAPER_SUMMARY%>" var="CADRE_INFO_TYPE_PAPER_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_BOOK_SUMMARY%>" var="CADRE_INFO_TYPE_BOOK_SUMMARY"/>

<c:set var="CADRE_REWARD_TYPE_RESEARCH" value="<%=CadreConstants.CADRE_REWARD_TYPE_RESEARCH%>"/>
<c:set var="CADRE_RESEARCH_TYPE_DIRECT" value="<%=CadreConstants.CADRE_RESEARCH_TYPE_DIRECT%>"/>
<c:set var="CADRE_RESEARCH_TYPE_IN" value="<%=CadreConstants.CADRE_RESEARCH_TYPE_IN%>"/>

<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY?"active":""}">
        <a href="javascript:;" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}')"><i
                class="fa fa-list"></i> 主持科研项目</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY?"active":""}">
        <a href="javascript:;" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}')"><i class="fa fa-list"></i>
            参与科研项目</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_BOOK_SUMMARY?"active":""}">
        <a href="javascript:;" onclick="_innerPage('${CADRE_INFO_TYPE_BOOK_SUMMARY}')"><i class="fa fa-book"></i>
            出版著作</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_PAPER_SUMMARY?"active":""}">
        <a href="javascript:;" onclick="_innerPage('${CADRE_INFO_TYPE_PAPER_SUMMARY}')"><i class="fa fa-file-o"></i>
            发表论文</a>
    </li>
    <li class="${type==CADRE_INFO_TYPE_RESEARCH_REWARD?"active":""}">
        <a href="javascript:;" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH_REWARD}')"><i class="fa fa-list"></i>
            科研成果及获奖</a>
    </li>
<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <li class="${type==CADRE_INFO_TYPE_RESEARCH?"active":""}">
        <a href="javascript:;" onclick="_innerPage('${CADRE_INFO_TYPE_RESEARCH}')"><i class="fa fa-list-ol"></i> 预览</a>
    </li>
</shiro:lacksPermission>
    </shiro:hasPermission>
<shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <div class="buttons" style="position:absolute;left: 750px;">
        <a class="popupBtn btn btn-warning btn-sm"
           data-width="800"
           data-url="${ctx}/hf_content?code=hf_cadre_research">
            <i class="fa fa-info-circle"></i> 填写说明</a>
    </div>
</shiro:lacksPermission>
</ul>
<c:if test="${type==CADRE_INFO_TYPE_RESEARCH}">
    <div class="space-4"></div>
<div class="row">
    <div class="col-xs-6 preview-text">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    初始数据
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" style="min-height: 647px" id="orginal">
                    <jsp:useBean id='map' class='java.util.HashMap' scope='request'>
                        <c:set target='${map}' property='cadreResearchDirects' value='${cadreResearchDirects}'/>
                        <c:set target='${map}' property='cadreResearchIns' value='${cadreResearchIns}'/>
                        <c:set target='${map}' property='cadreBooks' value='${cadreBooks}'/>
                        <c:set target='${map}' property='cadrePapers' value='${cadrePapers}'/>
                        <c:set target='${map}' property='cadreRewards' value='${cadreRewards}'/>
                    </jsp:useBean>
                    ${cm:freemarker(map, '/cadre/cadreResearch.ftl')}
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-6">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">最终数据（<span
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
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                    <div class="modal-footer center">
                            <a href="javascript:;" onclick="copyOrginal()" class="btn btn-success">
                                <i class="ace-icon fa fa-copy"></i>
                                同步初始数据
                            </a>
                        <input type="button" id="saveBtn" onclick="updateCadreInfo()" class="btn btn-primary"
                               value="保存"/>

                    </div>
                    </shiro:lacksPermission>
                </c:if>
            </div>
        </div>
    </div>
</div>
</c:if>
<c:if test="${type==CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                <shiro:hasPermission name="cadreResearch:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                       data-url="${ctx}/cadreResearch_au?cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_DIRECT}"><i
                            class="fa fa-plus"></i>
                        添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadreResearch_au"
                       data-grid-id="#jqGrid_cadreResearch_direct"
                       data-querystr="&cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_DIRECT}"><i
                            class="fa fa-edit"></i>
                        修改</button>
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
                &nbsp;&nbsp;近5年的主持科研项目的情况。
        </shiro:lacksPermission>
    </c:if>
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <input type="checkbox" data-name="research_direct" name="check" class="cadre-info-check"> 无此类情况
        <div class="space-4"></div>
    </shiro:lacksPermission>
    </div>
    <table id="jqGrid_cadreResearch_direct" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadreResearch_direct"></div>
</c:if>
<c:if test="${type==CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                <shiro:hasPermission name="cadreResearch:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                       data-url="${ctx}/cadreResearch_au?cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_IN}"><i
                            class="fa fa-plus"></i>
                        添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadreResearch_au"
                       data-grid-id="#jqGrid_cadreResearch_in"
                       data-querystr="&cadreId=${param.cadreId}&researchType=${CADRE_RESEARCH_TYPE_IN}"><i
                            class="fa fa-edit"></i>
                        修改</button>
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
                &nbsp;&nbsp;近5年的参与科研项目的情况。
        </shiro:lacksPermission>
    </c:if>
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <input type="checkbox" data-name="research_in" name="check" class="cadre-info-check"> 无此类情况
    </shiro:lacksPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadreResearch_in" data-width-reduce="50" class="jqGrid2"></table>
    <div id="jqGridPager_cadreResearch_in"></div>
</c:if>
<c:if test="${type==CADRE_INFO_TYPE_BOOK_SUMMARY}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                <button class="popupBtn btn  btn-sm btn-success"
                   data-url="${ctx}/cadreBook_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                    添加</button>
                <button class="jqOpenViewBtn btn  btn-sm btn-primary"
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
                &nbsp;&nbsp;近5年的出版著作的情况。
        </shiro:lacksPermission>
    </c:if>
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <input type="checkbox" data-name="book" name="check" class="cadre-info-check"> 无此类情况
    </shiro:lacksPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadreBook" data-width-reduce="50" class="jqGrid2"></table>
    <div id="jqGridPager_cadreBook"></div>
</c:if>
<c:if test="${type==CADRE_INFO_TYPE_PAPER_SUMMARY}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                <button class="popupBtn btn  btn-sm btn-success"
                   data-url="${ctx}/cadrePaper_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                    添加</button>
                <button class="jqOpenViewBtn btn  btn-sm btn-primary"
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
                &nbsp;&nbsp;近5年的发表论文的情况。
        </shiro:lacksPermission>
    </c:if>
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <input type="checkbox" data-name="paper" name="check" class="cadre-info-check"> 无此类情况
    </shiro:lacksPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadrePaper" data-width-reduce="50" class="jqGrid2"></table>
    <div id="jqGridPager_cadrePaper"></div>
</c:if>
<c:if test="${type==CADRE_INFO_TYPE_RESEARCH_REWARD}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            <button class="popupBtn btn  btn-sm btn-success"
               data-url="${ctx}/cadreReward_au?rewardType=${CADRE_REWARD_TYPE_RESEARCH}&cadreId=${param.cadreId}"><i
                    class="fa fa-plus"></i>
                添加</button>
            <button class="jqOpenViewBtn btn  btn-sm btn-primary"
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
        </shiro:lacksPermission>
    </c:if>
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <input type="checkbox" data-name="research_reward" name="check" class="cadre-info-check"> 无此类情况
    </shiro:lacksPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadreReward" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadreReward"></div>
</c:if>



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
        width: '100%',
        readonlyMode:readonlyMode
    });
    function updateCadreInfo() {
        var html = $.trim(ke.html());
        //if (html != '') {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: html,
                type: "${type}"
            }, function (ret) {
                if (ret.success) {
                    _innerPage("${type}", function () {
                        $("#saveBtn").tip({content: '<i class="fa fa-check-circle green"></i> 保存成功', position:{my:'bottom center'}});
                    });
                }
            });
        //}
    }
    function copyOrginal() {
        var html = $.trim($("#orginal").html());
        if (html != '') {
            ke.html(html);
            $("#saveTime").html("未保存");
            $("#saveBtn").tip({content: '<i class="fa fa-check-circle green"></i> 复制成功，请点击"保存"按钮进行保存', position:{my:'bottom center'}});
        }
    }
</script>

<script>

    <c:if test="${!canUpdate}">
    $(".cadreView button.btn").prop("disabled", true);
    </c:if>
    $(".cadre-info-check").prop("checked", ${!canUpdate});
    <c:if test="${!canUpdateInfoCheck}">
    $(".cadre-info-check").prop("disabled", true);
    </c:if>

    function _innerPage(type, fn) {
        $("#view-box .tab-content").loadPage({url:"${ctx}/cadreResearch_page?cadreId=${param.cadreId}&type=" + type, callback:fn})
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
    }).jqGrid("setFrozenColumns");
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
    }).jqGrid("setFrozenColumns");
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
    }).jqGrid("setFrozenColumns");
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
    }).jqGrid("setFrozenColumns");
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
    }).jqGrid("setFrozenColumns");
    </c:if>
    $(window).triggerHandler('resize.jqGrid2');
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach_download?path={0}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'));
    });
</script>