<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=PsConstants.COUNTNUMBER%>" var="COUNTNUMBER"/>
<c:set value="<%=PsConstants.TEACHERNUMBER%>" var="TEACHERNUMBER"/>
<c:set value="<%=PsConstants.STUDENTNUMBER%>" var="STUDENTNUMBER"/>
<c:set value="<%=PsConstants.RETIRENUMBER%>" var="RETIRENUMBER"/>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter" style="margin-right: 20px;">
            <i class="ace-icon fa fa-info-circle"></i>
            基本信息</h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <table class="table table-unhover table-bordered table-striped">
                <tbody>
                    <tr>
                        <td>二级党校名称</td>
                        <td style="min-width: 80px">${psInfo.name}</td>
                        <td>主建单位</td>
                        <td style="min-width: 80px">${cm:displayParty(hostParty.partyId, null)}</td>
                        <td>成立时间</td>
                        <td style="min-width: 80px">${cm:formatDate(psInfo.foundDate,'yyyy-MM')}</td>
                    </tr>
                    <tr>
                        <td>联合建设单位</td>
                        <td colspan="5">
                            <c:forEach items="${jointPartyList}" var="jointParty" varStatus="status">
                                ${cm:displayParty(jointParty.partyId, null)}${status.last?"":"、"}
                            </c:forEach>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter" style="margin-right: 20px;">
            <i class="ace-icon fa fa-history"></i>
            组织架构</h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <table class="table table-unhover table-bordered table-striped">
                <tbody>
                    <tr>
                        <td>校长</td>
                        <td style="min-width: 80px">${cm:getUserById(principal.userId).realname}</td>
                        <td>所在单位及职务</td>
                        <td style="min-width: 80px">${principal.title}</td>
                        <td>联系方式</td>
                        <td style="min-width: 80px"><t:mask src="${principal.mobile}" type="mobile"/></td>
                    </tr>
                    <c:forEach items="${viceprincipalList}" var="viceprincipal">
                        <tr>
                            <td>副校长</td>
                            <td>${cm:getUserById(viceprincipal.userId).realname}</td>
                            <td>所在单位及职务</td>
                            <td>${viceprincipal.title}</td>
                            <td>联系方式</td>
                            <td><t:mask src="${viceprincipal.mobile}" type="mobile"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-circle-o-notch"></i>
            党员统计</h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <table id="myTable" class="table table-unhover table-bordered table-striped">
                <tbody>
                    <tr>
                        <td rowspan="2" valign="middle" style="line-height: 4">党员总人数</td>
                        <td rowspan="2" style="min-width: 80px; line-height: 4" class="countNumber">
                            ${allPartyNubmerCount.get(COUNTNUMBER)}
                        </td>
                        <td>在职教职工党员数</td>
                        <td style="min-width: 80px" class="countNumber">
                            ${allPartyNubmerCount.get(TEACHERNUMBER)}
                        </td>
                        <td>学生党员数</td>
                        <td style="min-width: 80px" class="countNumber">
                           ${allPartyNubmerCount.get(STUDENTNUMBER)}
                        </td>
                    </tr>
                    <tr>
                        <td>离退休党员数</td>
                        <td colspan="3" class="countNumber">
                            ${allPartyNubmerCount.get(RETIRENUMBER)}
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="2" style="line-height: 4">主建单位党员总人数</td>
                        <td rowspan="2" style="line-height: 4" class="countNumber">
                            ${hostPartyNumberCount.get(COUNTNUMBER)}
                        </td>
                        <td>在职教职工党员数</td>
                        <td style="min-width: 80px" class="countNumber">
                            ${hostPartyNumberCount.get(TEACHERNUMBER)}
                        </td>
                        <td>学生党员数</td>
                        <td style="min-width: 80px" class="countNumber">
                            ${hostPartyNumberCount.get(STUDENTNUMBER)}
                        </td>
                    </tr>
                    <tr>
                        <td>离退休党员数</td>
                        <td colspan="3" class="countNumber">
                            ${hostPartyNumberCount.get(RETIRENUMBER)}
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="2" style="line-height: 4">联合建设单位党员总人数</td>
                        <td rowspan="2" style="line-height: 4" class="countNumber">
                            ${notHostPartyNumberCount.get(COUNTNUMBER)}
                        </td>
                        <td>在职教职工党员数</td>
                        <td style="min-width: 80px" class="countNumber">
                            ${notHostPartyNumberCount.get(TEACHERNUMBER)}
                        </td>
                        <td>学生党员数</td>
                        <td style="min-width: 80px" class="countNumber">
                            ${notHostPartyNumberCount.get(STUDENTNUMBER)}
                        </td>
                    </tr>
                    <tr>
                        <td>离退休党员数</td>
                        <td colspan="3" class="countNumber">
                            ${notHostPartyNumberCount.get(RETIRENUMBER)}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    var arr=$("td[class=countNumber]");
    for (var i=0;i<arr.length;i++){
        var isNull = $(arr[i]).text().trim()=='';
        if (!isNull){$(arr[i].append('人'));}
    }
</script>