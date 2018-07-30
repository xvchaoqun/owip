<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cpcAllocation"
             data-url-export="${ctx}/cpcAllocation_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.unitId ||not empty param.postId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${type==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cpcAllocation?type=1"><i class="fa fa-table"></i> 内设机构干部配备详情</a>
                    </li>
                    <li class="<c:if test="${type==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cpcAllocation?type=2"><i class="fa fa-bar-chart"></i> 内设机构干部配备统计</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="space-4"></div>
                        <c:if test="${type==1}">
                        <div class="buttons" style="position: absolute; top:35px;">
                            <shiro:hasPermission name="cpcAllocation:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/cpcAllocation_selectUnits"><i
                                        class="fa fa-plus"></i> 设置</a>
                                <a class="btn btn-primary btn-sm"
                                   href="javascript:;" onclick="_update()"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cpcAllocation:del">
                                <button class="btn btn-danger btn-sm"
                                        onclick="_del(this)"
                                        data-url="#"
                                        data-title="删除"
                                        data-msg="确定删除吗？（删除后不可恢复）"
                                        data-callback="_reload">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <a class="btn btn-success btn-sm"
                               href="${ctx}/cpcAllocation?type=1&export=1"><i class="fa fa-download"></i> 导出</a>
                        </div>
                        <jsp:include page="cpc_table.jsp"/>
                        </c:if>
                        <c:if test="${type==2}">
                            <div class="buttons" style="position: absolute; top:35px;">
                                <a class="btn btn-success btn-sm"
                                   href="${ctx}/cpcAllocation?type=2&export=1"><i class="fa fa-download"></i> 导出</a>
                            </div>
                            <jsp:include page="cpc_stat_table.jsp"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<div class="footer-margin"/>
<c:if test="${type==1}">
<script>

    function _reload(){
        $.hideView("${ctx}/cpcAllocation");
    }
    $("input[type=checkbox][name=checkall]").click(function(){

        $("input[type=checkbox][name=unitId]").prop("checked", this.checked);
    });

    function _update(){

        var unitIds = [];
        $("input[type=checkbox][name=unitId]:checked").each(function(){
            unitIds.push($(this).val());
        });

        if(unitIds.length!=1){
            SysMsg.info("请选择一个单位。");
            return;
        }
        $.loadModal("${ctx}/cpcAllocation_au?unitId=" + unitIds[0]);
    }

    function _del(btn){

        var unitIds = [];
        $("input[type=checkbox][name=unitId]:checked").each(function(){
            unitIds.push($(this).val());
        });

        if(unitIds.length==0){
            SysMsg.info("请选择单位。");
            return;
        }

        $(btn).data("url", "${ctx}/cpcAllocation_batchDel?unitIds=" + unitIds);

        $.confirm(btn);
    }

    $('[data-tooltip="tooltip"]').tooltip({html: true});
</script>
</c:if>