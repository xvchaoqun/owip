<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
    <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 兼职情况</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 预览</a>
    </li>
</ul>
    </shiro:lacksRole>
    </shiro:hasPermission>
<c:if test="${type==1}">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <a class="popupBtn btn btn-warning btn-sm"
           data-width="800"
           data-url="${ctx}/hf_content?code=${HF_CADRE_PARTTIME}">
            <i class="fa fa-info-circle"></i> 填写说明</a>
        <shiro:hasPermission name="cadreParttime:edit">
            <a class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreParttime_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                添加</a>
            <a class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreParttime_au"
               data-grid-id="#jqGrid_cadreParttime"
               data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                修改</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreParttime:del">
            <button data-url="${ctx}/cadreParttime_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreParttime"
                    data-querystr="cadreId=${param.cadreId}"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-times"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
        </shiro:lacksRole>
        </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadreParttime" class="jqGrid2"></table>
    <div id="jqGridPager_cadreParttime"></div>
</c:if>
<c:if test="${type==2}">
    <div class="row">
        <div class="col-xs-6 preview-text">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        初始数据
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="min-height: 647px" id="orginal">
                        <c:forEach items="${cadreParttimes}" var="cadreParttime">
                            <p>${cm:formatDate(cadreParttime.startTime, "yyyy.MM")}${(cadreParttime.endTime!=null)?"-":"-至今"}${cm:formatDate(cadreParttime.endTime, "yyyy.MM")}
                                &nbsp;${cadreParttime.unit}${cadreParttime.post}</p>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        最终数据（<span
                            style="font-weight: bolder; color: red;">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <textarea id="content">${cadreInfo.content}</textarea>
                        <input type="hidden" name="content">
                    </div>
                    <div class="modal-footer center">
                        <a href="javascript:" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            同步自动生成的数据
                        </a>
                        <input type="button" onclick="updateCadreInfo()" class="btn btn-primary" value="保存"/>

                    </div>
                </div>
            </div>

        </div>
    </div>
</c:if>

<c:if test="${type==2}">
    <script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
    <script>
        var ke = KindEditor.create('#content', {
            cssPath:"${ctx}/css/ke.css",
            items: ["source", "|", "fullscreen"],
            height: '550px',
            width: '100%'
        });
        function updateCadreInfo() {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: ke.html(),
                type: "${CADRE_INFO_TYPE_PARTTIME}"
            }, function (ret) {
                if (ret.success) {
                    SysMsg.info("保存成功", "", function () {
                        _innerPage(2)
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
</c:if>
<c:if test="${type==1}">
    <script>
        function _innerPage(type) {
            $("#view-box .tab-content").loadPage("${ctx}/cadreParttime_page?cadreId=${param.cadreId}&type=" + type)
        }
        $("#jqGrid_cadreParttime").jqGrid({
            <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
            multiselect:false,
            </c:if>
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreParttime",
            url: '${ctx}/cadreParttime_data?${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: colModels.cadreParttime
        }).on("initGrid", function () {
            $(window).triggerHandler('resize.jqGrid2');
        });

        function _delCallback(target) {
            $("#jqGrid_cadreParttime").trigger("reloadGrid");
        }

        $('#searchForm [data-rel="select2"]').select2();
        $('[data-rel="tooltip"]').tooltip();
    </script>
</c:if>