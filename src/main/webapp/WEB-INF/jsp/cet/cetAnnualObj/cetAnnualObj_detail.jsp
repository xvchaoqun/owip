<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="widget-box transparent">
    <c:if test="${not empty param.objId}">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
               data-url="${ctx}/cet/cetAnnual_detail?annualId=${cetAnnual.id}&_pageNo=${param._pageNo}">
                <i class="ace-icon fa fa-backward"></i> 返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
             ${cetAnnual.year}年度${(traineeTypeMap.get(cetAnnual.traineeTypeId)).name}，${cetAnnualObj.user.realname}
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li class="active">
                    <a href="javascript:;">学习汇总</a>
                </li>
                <%--<li>
                    <a href="javascript:;">统计分析</a>
                </li>--%>
            </ul>
        </div>
    </div>
    </c:if>
    <c:if test="${empty param.objId && fn:length(years)>1}">
        <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
          <c:forEach items="${years}" var="_year" varStatus="vs">
          <li class="<c:if test="${year==_year || (empty year && vs.first)}">active</c:if>">
            <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetAnnualObj_detail?year=${_year}"><i class="fa fa-list"></i> ${_year}年度</a>
          </li>
          </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty cetAnnualObj}">
    <h4>暂无</h4>
    </c:if>
    <c:if test="${not empty cetAnnualObj}">
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="jqgrid-vertical-offset panel panel-default" style="margin-bottom: 0">
                    <div class="panel-heading">
                        <h3 class="panel-title"><span class="text-primary bolder"><i
                                class="fa fa-info-circle"></i>   基本信息</span>
                        </h3>
                    </div>
                    <div class="collapse in">
                        <div class="panel-body">
                            <label>年度：</label>
                           <span> ${cetAnnual.year}
                           </span>
                            <label>工作证号：</label><span>${cetAnnualObj.user.code}</span>
                            <label>姓名：</label>
                           <span> ${cetAnnualObj.user.realname}
                           </span>
                            <label>时任单位及职务：</label>
                            <span>${cetAnnualObj.title}</span>
                            <label>行政级别：</label>
                            <span>${cm:getMetaType(cetAnnualObj.adminLevel).name}</span>
                            <label>职务属性：</label>
                            <span>${cm:getMetaType(cetAnnualObj.postType).name}</span>
                            <label>任现职时间：</label>
                            <span>${cm:formatDate(cetAnnualObj.lpWorkTime, "yyyy-MM-dd")}</span>
                        </div>
                    </div>
                </div>
            <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
            <li class="active">
                <a href="javascript:;" class="loadPage"
                   data-load-el="#detail-content-view" data-callback="$.menu.liSelected"
                   data-url='${ctx}/cet/cetAnnualObj_items?objId=${cetAnnualObj.id}&isValid=1'><i
                        class="fa fa-check"></i> 计入年度学习任务
                </a>
            </li>
            <li>
                <a href="javascript:;" class="loadPage"
                   data-load-el="#detail-content-view" data-callback="$.menu.liSelected"
                   data-url='${ctx}/cet/cetAnnualObj_items?objId=${cetAnnualObj.id}&isValid=0'><i
                        class="fa fa-times"></i> 未计入年度学习任务
                </a>
            </li>

        </ul>
        <div class="space-4"></div>
        <div class="col-xs-12" id="detail-content-view">
            <c:import url="${ctx}/cet/cetAnnualObj_items?objId=${cetAnnualObj.id}&isValid=1"/>
        </div>
        <div style="clear: both"></div>
        </div>
    </div>
    </c:if>
</div>
<div class="footer-margin"/>
<style>
    .panel-body label{
        font-size: 14pt;
        font-weight: bolder;
        margin-left: 20px;
    }
    .panel-body span.result{
        font-size: 14pt;
        color: #a94442;
        font-weight: bolder;
    }
    .panel-body span.result.graduate{
        font-size: 14pt;
        color: #449d44;
        font-weight: bolder;
    }
</style>