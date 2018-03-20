<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-body">
<style type="text/css">
    .b1 {
        white-space-collapsing: preserve;
    }

    .b2 {
        margin: 0.7875in 0.7875in 0.7875in 0.7875in;
    }

    .s1 {
        font-weight: bold;
    }

    .s2 {
        font-weight: bold;
        text-decoration: underline;
    }

    .p1 {
        text-align: center;
        hyphenate: auto;
        font-family: 华文中宋;
        font-size: 18pt;
    }

    .p2 {
        text-align: center;
        hyphenate: auto;
        font-family: 华文中宋;
        font-size: 10pt;
    }

    .p3 {
        /*text-indent: 2.6409721in;*/
        /*margin-top: 0.25in;*/
        margin-bottom: 0.108333334in;
        text-align: end;
        hyphenate: auto;
        font-family: 华文仿宋;
        font-size: 14pt;
    }

    .p4 {
        text-align: center;
        hyphenate: auto;
        font-family: 华文仿宋;
        font-size: 12pt;
    }

    .p5 {
        text-align: start;
        hyphenate: auto;
        font-family: 华文仿宋;
        font-size: 12pt;
    }

    .p6 {
        text-align: justify;
        hyphenate: auto;
        font-family: 华文仿宋;
        font-size: 12pt;
    }

    .p7 {
        text-align: start;
        hyphenate: auto;
        font-family: 宋体;
        font-size: 14pt;
    }

    .td1 {
        width: 0.34375in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .td2 {
        padding-left: 6px;
        width: 1.7034723in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .td3 {
        width: 4.195833in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .td4 {
        width: 0.7875in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }
    .td41 {
        width: 0.3875in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }
    .td5 {
        width: 1.0458333in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .td6 {
        width: 5.8993053in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .r1 {
        keep-together: always;
    }

    .t1 {
        table-layout: fixed;
        border-collapse: collapse;
        border-spacing: 0;
    }
    .stat{
        background-color: #CCFFCC;
        font-weight: bolder;
        font-size: 18px
    }
</style>
<c:set var="normNum" value="${trainEvaTable.normNum}"></c:set>
<c:set var="rankNum" value="${trainEvaTable.rankNum}"></c:set>
<c:set var="inspectorNum" value="${fn:length(inspectorTotalScoreMap)}"></c:set>
<fmt:formatNumber var="score" type="number" value="${inspectorNum>0?score/inspectorNum:''}" maxFractionDigits="1"/>
<table class="t1">
    <tbody>
    <tr>
        <td colspan="${2+rankNum}">
            <p class="p1">
                <span class="s1">${trainEvaTable.name}</span>
            </p>
        </td>
    </tr>
    <tr>
        <td colspan="${2+rankNum}">
            <p class="p3">
                <span class="s1">评估总分 : </span><span class="s2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${score}
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </p>
        </td>
    </tr>
    <tr>
        <td class="td1" rowspan="3">
            <p class="p4">
                <span>评估</span>
            </p>

            <p class="p4">
                <span>内容</span>
            </p>
        </td>
        <td class="td2" rowspan="3">
            <p class="p4">
                <span>评估指标</span>
            </p>
        </td>
        <td class="td3" colspan="${rankNum}">
            <p class="p4">
                <span>评估等级</span>
            </p>
        </td>
        <td class="td3 stat" colspan="2" rowspan="3">
            <p class="p4">
                <span>各项平均</span>
            </p>
        </td>
    </tr>
    <tr>
        <c:forEach items="${trainEvaTable.ranks}" var="rank">
        <td class="td4">
            <p class="p4">
                <span>${rank.name}</span>
            </p>
        </td>
        </c:forEach>
    </tr>
    <tr>
        <c:forEach items="${trainEvaTable.ranks}" var="rank">
            <td class="td4">
                <p class="p4">
                    <span>${rank.scoreShow}</span>
                </p>
            </td>
        </c:forEach>
    </tr>
    <c:forEach items="${trainEvaTable.norms}" var="norm">
        <c:if test="${norm.normNum==0}">
            <tr>
                <td class="td2" colspan="2">
                    <p class="p4">
                        <span>${norm.name}</span>
                    </p>
                </td>
                <c:forEach items="${trainEvaTable.ranks}" var="rank">
                    <c:set var="_key" value="${norm.id}_${rank.id}"></c:set>
                    <td class="td4">
                        <p class="p4">${normRankNumMap.get(_key)}</p>
                    </td>
                </c:forEach>
                <td class="td4">
                    <fmt:formatNumber var="_score" type="number" value="${normTotalScoreMap.get(norm.id)/inspectorNum}" maxFractionDigits="1"/>
                    <p class="p4">${_score}</p>
                </td>
                <td class="td4">
                    <p class="p4"></p>
                </td>
            </tr>
        </c:if>
        <c:if test="${norm.normNum>0}">
        <c:forEach items="${norm.subNorms}" var="subNorm" varStatus="vs">
            <tr>
                <c:if test="${vs.first}">
                    <td class="td1" rowspan="${norm.normNum}" style="padding: 15px;">
                        <p class="p4">
                            <span>${norm.name}</span>
                        </p>
                    </td>
                </c:if>
                <td class="td2">
                    <p class="p5">
                        <span>${subNorm.name}</span>
                    </p>
                </td>
                <c:forEach items="${trainEvaTable.ranks}" var="rank">
                    <c:set var="_key" value="${subNorm.id}_${rank.id}"></c:set>
                    <td class="td4">
                        <p class="p4">${normRankNumMap.get(_key)}</p>
                    </td>
                </c:forEach>
                <td class="td41 stat">
                    <fmt:formatNumber var="_score" type="number" value="${inspectorNum>0?normTotalScoreMap.get(subNorm.id)/inspectorNum:''}" maxFractionDigits="1"/>
                    <p class="p4">${_score}</p>
                </td>
                <c:if test="${vs.first}">
                <td class="td41 stat" rowspan="${norm.normNum}" >
                    <fmt:formatNumber var="_score" type="number" value="${inspectorNum>0?topNormTotalScoreMap.get(norm.id)/inspectorNum:''}" maxFractionDigits="1"/>
                    <p class="p4">${_score}</p>
                </td>
                </c:if>
            </tr>
        </c:forEach>
        </c:if>
    </c:forEach>
    <tr class="r1 stat">
        <td class="td1" colspan="2">
            <p class="p4">
                <span>汇总</span>
            </p>
        </td>
        <c:forEach items="${trainEvaTable.ranks}" var="rank">
            <td class="td4">
                <p class="p4">${rankNumMap.get(rank.id)}</p>
            </td>
        </c:forEach>
        <td class="td41" colspan="2" style="background-color: #FFFFCC;">
            <p class="p4">${score}</p>
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p4">
                <span>意见</span>
            </p>

            <p class="p4">
                <span>建议</span>
            </p>
        </td>
        <td class="td6" colspan="${3+rankNum}" style="padding: 15px;">
            <p class="p6">
                <c:forEach items="${feedbackMap}" var="entity" varStatus="vs">
                    ${vs.index+1}、${entity.value}
                    ${vs.last?'':'<br/>'}
                </c:forEach>
            </p>
        </td>
    </tr>
    </tbody>
</table>
<p class="p7"></p>
    </div>