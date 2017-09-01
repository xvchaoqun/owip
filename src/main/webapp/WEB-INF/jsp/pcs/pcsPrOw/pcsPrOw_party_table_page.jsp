<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable" style="margin: 10px 20px; width: 900px">
    <div class="space-4"></div>
    <a href="${ctx}/pcsPrOw_export?file=${empty param.partyId?6:4}&partyId=${param.partyId}&stage=${param.stage}">
        <i class="fa fa-download"></i> ${empty param.partyId?"附件6. 各分党委酝酿代表候选人初步人选统计表（组织部汇总）":"附件4. 分党委酝酿代表候选人初步人选统计表（分党委上报组织部）"}</a>
    <div class="space-4"></div>
    <table class="table table-bordered table-striped" data-offset-top="101">
        <thead class="multi">
        <tr>
            <th width="40" rowspan="2">序号</th>
            <th rowspan="2" colspan="2">程序</th>
            <th rowspan="2" width="80">代表总数</th>
            <th colspan="3">代表构成</th>
            <th colspan="3">其中</th>
        </tr>
        <tr>
            <th width="110">专业技术人员和干部代表</th>
            <th width="60">学生代表</th>
            <th width="70">离退休代表</th>
            <th width="60">妇女代表</th>
            <th width="80">少数民族代表</th>
            <th width="80">50岁以下代表</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td class="ltitle">1</td>
            <td colspan="2" class="ltitle">应选代表情况</td>
            <c:set var="expectCount"
                   value="${pcsPrAllocate.proCount +pcsPrAllocate.stuCount +pcsPrAllocate.retireCount }"/>
            <td>${expectCount}</td>
            <td>${pcsPrAllocate.proCount}</td>
            <td>${pcsPrAllocate.stuCount}</td>
            <td>${pcsPrAllocate.retireCount}</td>
            <td>${pcsPrAllocate.femaleCount}</td>
            <td>${pcsPrAllocate.minorityCount}</td>
            <td>${pcsPrAllocate.underFiftyCount}</td>
        </tr>
        <tr>
            <td rowspan="3" class="ltitle">2</td>
            <td rowspan="3" class="ltitle">“一上”推荐</td>
            <td class="ltitle">推荐结果</td>
            <c:set var="actualCount"
                   value="${realPcsPrAllocate.proCount +realPcsPrAllocate.stuCount +realPcsPrAllocate.retireCount }"/>
            <td>${actualCount}</td>
            <td>${realPcsPrAllocate.proCount}</td>
            <td>${realPcsPrAllocate.stuCount}</td>
            <td>${realPcsPrAllocate.retireCount}</td>
            <td>${realPcsPrAllocate.femaleCount}</td>
            <td>${realPcsPrAllocate.minorityCount}</td>
            <td>${realPcsPrAllocate.underFiftyCount}</td>
        </tr>
        <tr>
            <td class="ltitle">差额</td>
            <td>${actualCount-expectCount}</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
        </tr>
        <tr>
            <td class="ltitle">差额
                比率
            </td>
            <td>
                <c:if test="${expectCount>0}">
                    <fmt:formatNumber value="${(cm:parseInt(actualCount) - expectCount)/expectCount}"
                                      type="percent"
                                      pattern="#0.0%"/>
                </c:if>
            </td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
        </tr>
        </tbody>
    </table>
</div>

<style>
    .table th, .table td {
        text-align: center;
    }

    .ltitle {
        text-align: center !important;
        vertical-align: middle !important;
        background-color: #f9f9f9 !important;
    }

</style>