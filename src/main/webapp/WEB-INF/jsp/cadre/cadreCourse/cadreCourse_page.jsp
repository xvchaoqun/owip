<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 承担本、硕、博课程情况</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 教学成果及获奖情况</a>
    </li>
    <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
    <li class="${type==3?"active":""}">
        <a href="javascript:" onclick="_innerPage(3)"><i class="fa fa-flag"></i> 预览</a>
    </li>
    </shiro:hasPermission>
    <div class="buttons" style="position:absolute;left: 500px;">
        <a class="popupBtn btn btn-warning btn-sm"
           data-width="800"
           data-url="${ctx}/hf_content?code=${HF_CADRE_COURSE}">
            <i class="fa fa-info-circle"></i> 填写说明</a>
    </div>
</ul>

<c:if test="${type==1}">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="cadreCourse:edit">
            <a class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreCourse_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                添加</a>
            <a class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreCourse_au"
               data-grid-id="#jqGrid_cadreCourse"
               data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                修改</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreCourse:del">
            <button data-url="${ctx}/cadreCourse_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreCourse"
                    data-querystr="cadreId=${param.cadreId}"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-times"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
        </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadreCourse" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadreCourse"></div>
</c:if>
<c:if test="${type==2}">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <a class="popupBtn btn  btn-sm btn-info"
           data-url="${ctx}/cadreReward_au?rewardType=${CADRE_REWARD_TYPE_TEACH}&cadreId=${param.cadreId}"><i
                class="fa fa-plus"></i>
            添加</a>
        <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                data-url="${ctx}/cadreReward_au"
                data-grid-id="#jqGrid_cadreReward"
                data-querystr="&rewardType=${CADRE_REWARD_TYPE_TEACH}&cadreId=${param.cadreId}">
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
        </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadreReward" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadreReward"></div>
</c:if>
<c:if test="${type==3}">
    <div class="row two-frames">
        <div class="left">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        初始数据
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="min-height: 647px" id="orginal">
                        <c:if test="${fn:length(bksCadreCourses)>0}">
                            <p>本科生课程：<c:forEach items="${bksCadreCourses}" varStatus="vs" var="course">${course.name}
                                <c:if test="${!vs.last}">、</c:if>
                            </c:forEach>
                            </p>
                        </c:if>
                        <c:if test="${fn:length(ssCadreCourses)>0}">
                            <p>硕士生课程：<c:forEach items="${ssCadreCourses}" varStatus="vs" var="course">${course.name}
                                <c:if test="${!vs.last}">、</c:if>
                            </c:forEach>
                            </p>
                        </c:if>
                        <c:if test="${fn:length(bsCadreCourses)>0}">
                            <p>博士生课程：<c:forEach items="${bsCadreCourses}" varStatus="vs" var="course">${course.name}
                                <c:if test="${!vs.last}">、</c:if>
                            </c:forEach>
                            </p>
                        </c:if>
                        <c:if test="${fn:length(cadreRewards)>0}">
                            <p>获奖情况：</p>
                            <c:forEach items="${cadreRewards}" var="cadreReward">
                                <p style="text-indent: 2em">${cm:formatDate(cadreReward.rewardTime, "yyyy.MM")}&nbsp;荣获${cadreReward.name}
                                    <c:if test="${not empty cadreReward.rank}">(排名第${cadreReward.rank})</c:if>&nbsp;${cadreReward.unit}</p>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="right">
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
<div class="footer-margin"/>
<c:if test="${type==3}">
    <script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
    <script>

        var ke = KindEditor.create('#content', {
            cssPath:"${ctx}/css/ke.css",
            items: ["source", "|", "fullscreen"],
            height: '550px',
            width: '700px'
        });

        function updateCadreInfo() {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: ke.html(),
                type: "${CADRE_INFO_TYPE_TEACH}"
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

<script type="text/template" id="sort_tpl">
<a href="#" class="jqOrderBtn" data-grid-id="#jqGrid_cadreCourse"
       data-url="${ctx}/cadreCourse_changeOrder?cadreId=${param.cadreId}" data-id="{{=id}}"
       data-direction="-1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
<a href="#" class="jqOrderBtn" data-grid-id="#jqGrid_cadreCourse"
       data-url="${ctx}/cadreCourse_changeOrder?cadreId=${param.cadreId}"
       data-id="{{=id}}" data-direction="1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    function _innerPage(type) {
        $("#view-box .tab-content").load("${ctx}/cadreCourse_page?cadreId=${param.cadreId}&type=" + type)
    }
    <c:if test="${type==1}">
    $("#jqGrid_cadreCourse").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreCourse",
        url: '${ctx}/cadreCourse_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreCourse
    });
    </c:if>
    <c:if test="${type==2}">
    $("#jqGrid_cadreReward").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreReward",
        url: '${ctx}/cadreReward_data?rewardType=${CADRE_REWARD_TYPE_TEACH}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreReward
    });
    </c:if>
    $(window).triggerHandler('resize.jqGrid2');

    /*function _delCallback(target) {
     $("#jqGrid_cadreCourse").trigger("reloadGrid");
     }*/

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'));
    });
</script>