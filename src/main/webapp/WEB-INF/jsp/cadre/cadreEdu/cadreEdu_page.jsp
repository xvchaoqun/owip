<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
   <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-list"></i> 学习经历</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:;" onclick="_innerPage(2)"><i class="fa fa-list-ol"></i> 预览</a>
    </li>
</ul>
   </shiro:lacksPermission>
</shiro:hasPermission>
<c:if test="${type==1}">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="cadreEdu:edit">
            <a class="popupBtn btn btn-warning btn-sm"
               data-width="800"
               data-url="${ctx}/hf_content?code=hf_cadre_edu">
                <i class="fa fa-info-circle"></i> 填写说明</a>
            <%--<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
                <a class="popupBtn btn btn-warning btn-sm"
                   data-url="${ctx}/cadreEdu_rule?cadreId=${param.cadreId}"><i class="fa fa-search"></i>
                    学历学位的认定规则</a>
            </shiro:hasPermission>--%>
            <a class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreEdu_au?cadreId=${param.cadreId}"
               data-width="900"><i class="fa fa-plus"></i>
                添加学习经历</a>
            <a class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreEdu_au"
               data-grid-id="#jqGrid_cadreEdu"
               data-querystr="&cadreId=${param.cadreId}"
               data-width="900"><i class="fa fa-edit"></i>
                修改学习经历</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreEdu:del">
            <button data-url="${ctx}/cadreEdu_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreEdu"
                    data-querystr="cadreId=${param.cadreId}"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
        </shiro:lacksPermission>
    </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadreEdu" class="jqGrid2"></table>
    <div id="jqGridPager_cadreEdu"></div>
</c:if>
<c:if test="${type==2}">
    <div class="space-4"></div>
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
                        <jsp:useBean id='map' class='java.util.HashMap' scope='request'>
                            <c:set target='${map}' property='cadreEdus' value='${cadreEdus}'/>
                        </jsp:useBean>
                        ${cm:freemarker(map, '/cadre/cadreEdu.ftl')}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        最终数据（<span
                            style="font-weight: bolder; color: red;"
                            id="saveTime">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <textarea id="content">${cadreInfo.content}</textarea>
                    </div>
                    <div class="modal-footer center">
                        <a href="javascript:;" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            同步自动生成的数据
                        </a>
                        <input id="saveBtn" type="button" onclick="updateCadreInfo()" class="btn btn-primary" value="保存"/>

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
            filterMode:true,
            htmlTags:{
                p : ['style']
            },
            cssPath:"${ctx}/css/ke.css",
            items: ["source", "|", "fullscreen", "|", 'preview'],
            height: '550px',
            width: '100%'
        });
        function updateCadreInfo() {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: ke.html(),
                type: "<%=CadreConstants.CADRE_INFO_TYPE_EDU%>"
            }, function (ret) {
                if (ret.success) {
                    /*SysMsg.info("保存成功", "", function () {
                        _innerPage(2)
                    });*/
                    _innerPage(2, function () {
                        $("#saveBtn").tip({content: '<i class="fa fa-check-circle green"></i> 保存成功', position:{my:'bottom center'}});
                    });
                }
            });
        }
        function copyOrginal() {
            //console.log($("#orginal").html())
            ke.html($("#orginal").html());
            $("#saveTime").html("未保存");
            $("#saveBtn").tip({content: '<i class="fa fa-check-circle green"></i> 复制成功，请点击"保存"按钮进行保存', position:{my:'bottom center'}});
            //SysMsg.info("复制成功，请务必点击\"保存\"按钮进行保存")
        }
    </script>
</c:if>
<c:if test="${type==1}">
    <script>
        function _innerPage(type, fn) {
            $("#view-box .tab-content").loadPage({url:"${ctx}/cadreEdu_page?cadreId=${param.cadreId}&type=" + type, callback:fn})
        }
        var needTutorEduTypes = ${cm:toJSONArray(needTutorEduTypes)};
        $("#jqGrid_cadreEdu").jqGrid({
            <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
            multiselect:false,
            </c:if>
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreEdu",
            url: '${ctx}/cadreEdu_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: colModels.cadreEdu
        }).jqGrid("setFrozenColumns");
        $(window).triggerHandler('resize.jqGrid2');

        $.register.fancybox(function () {
            //console.log(this)
            this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}" target="_blank">点击下载</a>】</div></div>'
                            .format($(this.element).data('path'));
        });
    </script>
</c:if>