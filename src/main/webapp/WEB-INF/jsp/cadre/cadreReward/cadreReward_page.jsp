<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CADRE_REWARD_TYPE_OTHER" value="<%=CadreConstants.CADRE_REWARD_TYPE_OTHER%>"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_REWARD_OTHER%>" var="CADRE_INFO_TYPE_REWARD_OTHER"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_REWARD%>" var="CADRE_INFO_TYPE_REWARD"/>
<c:set value="${_pMap['rewardOnlyYear']=='true'}" var="_p_rewardOnlyYear"/>
<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
            <li class="${type==1?"active":""}">
                <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-list"></i> 奖励情况</a>
            </li>
            <shiro:hasPermission name="cadrePunish:list">
            <li class="${type==4?"active":""}">
                <a href="javascript:;" class="loadPage" data-load-el="#view-box .tab-content"
                   data-url="${ctx}/cadreReward_page?cadreId=${param.cadreId}&type=4"><i class="fa fa-list"></i>
                    受处分情况</a>
            </li>
            </shiro:hasPermission>
            <li class="${type==2?"active":""}">
                <a href="javascript:;" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 预览</a>
            </li>
            <li class="${type==3?"active":""}">
                <a href="javascript:;" onclick="_innerPage(3)"><i class="fa fa-list-ol"></i> 干部任免审批表奖惩情况预览</a>
            </li>
        </ul>
    </shiro:lacksPermission>
</shiro:hasPermission>
<c:if test="${type==1}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
            <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                <a class="popupBtn btn btn-warning btn-sm"
                   data-width="800"
                   data-url="${ctx}/hf_content?code=hf_cadre_reward">
                    <i class="fa fa-info-circle"></i> 填写说明</a>
                <shiro:hasPermission name="cadreReward:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/cadreReward_au?rewardType=${CADRE_REWARD_TYPE_OTHER}&cadreId=${param.cadreId}">
                        <i class="fa fa-plus"></i>
                        添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cadreReward_au"
                            data-grid-id="#jqGrid_cadreReward"
                            data-querystr="&rewardType=${CADRE_REWARD_TYPE_OTHER}&cadreId=${param.cadreId}"><i
                            class="fa fa-edit"></i>
                        修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreReward:del">
                    <button data-url="${ctx}/cadreReward_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_cadreReward"
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </shiro:hasPermission>
            </shiro:lacksPermission>
        </c:if>
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            <input type="checkbox" data-name="reward" name="check" class="cadre-info-check"> 无此类情况
        </shiro:lacksPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadreReward" class="jqGrid2"></table>
    <div id="jqGridPager_cadreReward"></div>
</c:if>
<c:if test="${type==4}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN)}">
            <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                <shiro:hasPermission name="cadrePunish:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/cadrePunish_au?rewardType=${CADRE_REWARD_TYPE_OTHER}&cadreId=${param.cadreId}">
                        <i class="fa fa-plus"></i>
                        添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cadrePunish_au"
                            data-grid-id="#jqGrid_cadrePunish"
                            data-querystr="&rewardType=${CADRE_REWARD_TYPE_OTHER}&cadreId=${param.cadreId}"><i
                            class="fa fa-edit"></i>
                        修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadrePunish:del">
                    <button data-url="${ctx}/cadrePunish_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_cadrePunish"
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </shiro:hasPermission>
            </shiro:lacksPermission>
        </c:if>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadrePunish" class="jqGrid2"></table>
    <div id="jqGridPager_cadrePunish"></div>
</c:if>
<c:if test="${type==2}">
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
                            <c:set target='${map}' property='cadreRewards' value='${cadreRewards}'/>
                            <c:set target='${map}' property='cadrePunishes' value='${cadrePunishes}'/>
                        </jsp:useBean>
                            ${cm:freemarker(map, '/cadre/cadreReward.ftl')}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        最终数据（<span
                            style="font-weight: bolder; color: red;"
                            id="saveTime">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <textarea id="content">${cadreInfo.content}</textarea>
                        <input type="hidden" name="content">
                    </div>
                    <div class="modal-footer center">
                        <a href="javascript:;" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            同步自动生成的数据
                        </a>
                        <input id="saveBtn" type="button" onclick="updateCadreInfo()" class="btn btn-primary"
                               value="保存"/>

                    </div>
                </div>
            </div>

        </div>
    </div>
</c:if>
<c:if test="${type==3}">
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
                        <jsp:useBean id='map3' class='java.util.HashMap' scope='request'>
                            <c:set target='${map3}' property='cadreRewards' value='${cadreRewards}'/>
                            <c:set target='${map3}' property='cadrePunishes' value='${cadrePunishes}'/>
                        </jsp:useBean>
                            ${cm:freemarker(map3, '/cadre/cadreReward.ftl')}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        最终数据（<span
                            style="font-weight: bolder; color: red;"
                            id="saveTime">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <textarea id="content">${cadreInfo.content}</textarea>
                        <input type="hidden" name="content">
                    </div>
                    <div class="modal-footer center">
                        <a href="javascript:;" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            同步自动生成的数据
                        </a>
                        <input id="saveBtn" type="button" onclick="updateCadreInfo()" class="btn btn-primary"
                               value="保存"/>

                    </div>
                </div>
            </div>

        </div>
    </div>
</c:if>
<script>
    function _innerPage(type, fn) {
        $("#view-box .tab-content").loadPage({
            url: "${ctx}/cadreReward_page?rewardType=${CADRE_REWARD_TYPE_OTHER}&cadreId=${param.cadreId}&type=" + type,
            callback: fn
        })
    }
    <c:if test="${type==3}">
    var ke = KindEditor.create('#content', {
        cssPath: "${ctx}/css/ke.css",
        items: ["source", "|", "fullscreen"],
        height: '550px',
        width: '100%'
    });

    function updateCadreInfo() {
        $.post("${ctx}/cadreInfo_updateContent", {
            cadreId: '${param.cadreId}',
            content: ke.html().removeSpan(),
            type: "${CADRE_INFO_TYPE_REWARD}"
        }, function (ret) {
            if (ret.success) {

                _innerPage(3, function () {
                    $("#saveBtn").tip({
                        content: '<i class="fa fa-check-circle green"></i> 保存成功',
                        position: {my: 'bottom center'}
                    });
                });
            }
        });
    }
    function copyOrginal() {
        //console.log($("#orginal").html())
        ke.html($("#orginal").html());
        $("#saveTime").html("未保存");
        $("#saveBtn").tip({
            content: '<i class="fa fa-check-circle green"></i> 复制成功，请点击"保存"按钮进行保存',
            position: {my: 'bottom center'}
        });
    }
    </c:if>
    <c:if test="${type==2}">
    var ke = KindEditor.create('#content', {
        cssPath: "${ctx}/css/ke.css",
        items: ["source", "|", "fullscreen"],
        height: '550px',
        width: '100%'
    });
    function updateCadreInfo() {
        $.post("${ctx}/cadreInfo_updateContent", {
            cadreId: '${param.cadreId}',
            content: ke.html().removeSpan(),
            type: "${CADRE_INFO_TYPE_REWARD_OTHER}"
        }, function (ret) {
            if (ret.success) {

                _innerPage(2, function () {
                    $("#saveBtn").tip({
                        content: '<i class="fa fa-check-circle green"></i> 保存成功',
                        position: {my: 'bottom center'}
                    });
                });
            }
        });
    }
    function copyOrginal() {
        //console.log($("#orginal").html())
        ke.html($("#orginal").html());
        $("#saveTime").html("未保存");
        $("#saveBtn").tip({
            content: '<i class="fa fa-check-circle green"></i> 复制成功，请点击"保存"按钮进行保存',
            position: {my: 'bottom center'}
        });
    }
    </c:if>
    <c:if test="${type==1}">
    <c:if test="${!canUpdate}">
    $(".cadreView button.btn").prop("disabled", true);
    </c:if>
    $(".cadre-info-check").prop("checked", ${!canUpdate});
    <c:if test="${!canUpdateInfoCheck}">
    $(".cadre-info-check").prop("disabled", true);
    </c:if>
    $("#jqGrid_cadreReward").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect: false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreReward",
        url: '${ctx}/cadreReward_data?rewardType=${CADRE_REWARD_TYPE_OTHER}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreReward
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');

    function _delCallback(target) {
        $("#jqGrid_cadreReward").trigger("reloadGrid");
    }
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach_download?path={0}" target="_blank">点击下载</a>】</div></div>'
            .format($(this.element).data('path'));
    });
    </c:if>
    <c:if test="${type==4}">
    $("#jqGrid_cadrePunish").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN)}">
        multiselect: false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePunish",
        url: '${ctx}/cadrePunish_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadrePunish
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');

    function _delCallback(target) {
        $("#jqGrid_cadrePunish").trigger("reloadGrid");
    }
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    </c:if>
</script>