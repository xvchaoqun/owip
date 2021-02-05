<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="contentDiv" style="width: 1148px">

            <jsp:include page="menu.jsp"/>
            <div class="tab-content" style="padding: 5px 4px 0px">
                <table border=0 cellpadding=0 cellspacing=0
                       style='border-collapse:collapse;table-layout:fixed;'>
                    <tr height=41 style='mso-height-source:userset;height:31.15pt'>
                        <td colspan=18 height=41 class=xl97>
                            <div style="color:#337ab7;text-align:center;font-weight:600">各二级党组织研究生队伍党员信息分析</div>
                            <div style="font-size:15px;font-weight:300">（数据源自${year}年${month}月年统数据）</div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=1 rowspan="2" height=46 class=xl70 width=170 style='height:34.5pt;width:128pt'>二级党组织</td>
                        <td colspan=9 class=xl70 width=162>硕士研究生群体党员情况分析</td>
                        <td colspan=9 class=xl70 width=162>博士研究生群体党员情况分析</td>
                    </tr>
                    <tr>
                        <c:forEach items="${columns}" var="entity" varStatus="vs">
                            <td colspan=1 class=xl70 width=162>${entity}</td>
                        </c:forEach>

                        <c:forEach items="${columns}" var="entity" varStatus="vs">
                            <td colspan=1 class=xl70 width=162>${entity}</td>
                        </c:forEach>
                    </tr>

                    <c:forEach items="${data}" var="dat" varStatus="dts">
                            <c:if test="${dat.identity == 'masters'}">
                                <tr>
                                <td colspan=1 class=xl70 width=162>${empty dat.partyName ? 0 : dat.partyName}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.applyTotal ? 0 : dat.applyTotal}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.activityTotal ? 0 : dat.activityTotal}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.developTotal ? 0 : dat.developTotal}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.formalMembers ? 0 : dat.formalMembers}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.preparedMembers ? 0 : dat.preparedMembers}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.generalCount ? 0 : dat.generalCount}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.total ? 0 : dat.total}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.masterScale ? 0 : dat.masterScale}</td>
                                <td colspan=1 class=xl70 width=162>${dat.masterPercent}</td>
                            </c:if>

                            <c:if test="${dat.identity == 'doctors'}">
                                <td colspan=1 class=xl70 width=162>${empty dat.applyTotal ? 0 : dat.applyTotal}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.activityTotal ? 0 : dat.activityTotal}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.developTotal ? 0 : dat.developTotal}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.formalMembers ? 0 : dat.formalMembers}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.preparedMembers ? 0 : dat.preparedMembers}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.generalCount ? 0 : dat.generalCount}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.total ? 0 : dat.total}</td>
                                <td colspan=1 class=xl70 width=162>${empty dat.doctorScale ? 0 : dat.doctorScale}</td>
                                <td colspan=1 class=xl70 width=162>${dat.doctorPercent}</td>
                                </tr>
                            </c:if>

                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<style>
    tr {
        mso-height-source: auto;
        mso-ruby-visibility: none;
    }

    col {
        mso-width-source: auto;
        mso-ruby-visibility: none;
    }

    br {
        mso-data-placement: same-cell;
    }

    ruby {
        ruby-align: left;
    }

    .style0 {
        mso-number-format: General;
        text-align: general;
        vertical-align: bottom;
        white-space: nowrap;
        mso-rotate: 0;
        mso-background-source: auto;
        mso-pattern: auto;
        color: black;
        font-size: 11.0pt;
        font-weight: 400;
        font-style: normal;
        text-decoration: none;
        font-family: 宋体;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        border: none;
        mso-protection: locked visible;
        mso-style-name: 常规;
        mso-style-id: 0;
    }

    td {
        mso-style-parent: style0;
        padding-top: 1px;
        padding-right: 1px;
        padding-left: 1px;
        mso-ignore: padding;
        color: black;
        font-size: 11.0pt;
        font-weight: 400;
        font-style: normal;
        text-decoration: none;
        font-family: 宋体;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        mso-number-format: General;
        text-align: general;
        vertical-align: bottom;
        border: none;
        mso-background-source: auto;
        mso-pattern: auto;
        mso-protection: locked visible;
        white-space: nowrap;
        mso-rotate: 0;
    }

    .xl65 {
        mso-style-parent: style0;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl66 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl67 {
        mso-style-parent: style0;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
        layout-flow: vertical-ideographic;
    }

    .xl68 {
        mso-style-parent: style0;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl69 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl70 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl71 {
        mso-style-parent: style0;
        color: windowtext;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl72 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl73 {
        mso-style-parent: style0;
        color: windowtext;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl74 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl75 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl76 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: 0%;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl77 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: 0%;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl78 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: none;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl79 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl80 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
        layout-flow: vertical-ideographic;
    }

    .xl81 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl82 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        /*border-bottom:1pt solid windowtext;*/
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl83 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl84 {
        mso-style-parent: style0;
        text-align: left;
        vertical-align: top;
        border-top: none;
        border-right: none;
        border-bottom: none;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl85 {
        mso-style-parent: style0;
        text-align: left;
        vertical-align: top;
        white-space: normal;
    }

    .xl86 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl87 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: 2.0pt double windowtext;
        white-space: normal;
    }

    .xl88 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl89 {
        mso-style-parent: style0;
        color: windowtext;
        font-size: 18.0pt;
        font-family: 华文中宋;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        text-align: center;
        vertical-align: middle;
        border-top: none;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl90 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
        layout-flow: vertical-ideographic;
    }

    .xl91 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl92 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl93 {
        mso-style-parent: style0;
        color: windowtext;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl94 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
        /*layout-flow:vertical-ideographic;*/
    }

    .xl95 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 2.0pt double windowtext;
        white-space: normal;
    }

    .xl96 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl97 {
        mso-style-parent: style0;
        color: windowtext;
        font-size: 18.0pt;
        font-family: 华文中宋;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl98 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl99 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl100 {
        mso-style-parent: style0;
        color: windowtext;
        text-align: left;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: none;
        border-left: none;
        white-space: normal;
    }

    .xl101 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: none;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl102 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: none;
        border-left: none;
        white-space: normal;
    }

    .xl103 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: none;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl104 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: none;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl105 {
        mso-style-parent: style0;
        color: red;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl106 {
        mso-style-parent: style0;
        color: red;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl107 {
        mso-style-parent: style0;
        color: red;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl108 {
        mso-style-parent: style0;
        color: red;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl109 {
        mso-style-parent: style0;
        color: red;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl110 {
        mso-style-parent: style0;
        color: #4F81BD;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl111 {
        mso-style-parent: style0;
        color: #4F81BD;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl112 {
        mso-style-parent: style0;
        color: #4F81BD;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl113 {
        mso-style-parent: style0;
        color: #4F81BD;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl114 {
        mso-style-parent: style0;
        color: #C00000;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl115 {
        mso-style-parent: style0;
        color: #C00000;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl116 {
        mso-style-parent: style0;
        color: #C00000;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

</style>
