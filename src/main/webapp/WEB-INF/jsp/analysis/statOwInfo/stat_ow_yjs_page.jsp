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
                            <div style="color:#337ab7;text-align:center;font-weight:600">${_school}研究生队伍党员信息分析</div>
                            <div style="font-size:15px;font-weight:300">（数据源自${year}年${month}月年统数据）</div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14" align="right">注：百分比按四舍五入取值</td>
                    </tr>
                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170 style='height:34.5pt;width:128pt'>学生信息</td>
                        <td colspan=2 class=xl70 width=162>学生总数</td>
                        <td colspan=2 class=xl70 width=162>党员</td>
                        <td colspan=2 class=xl70 width=162>正式党员</td>
                        <td colspan=2 class=xl70 width=162>预备党员</td>
                        <td colspan=2 class=xl70 width=162>党员占比</td>
                        <td colspan=2 class=xl70 width=162>入党申请人</td>
                        <td colspan=2 class=xl70 width=162>入党积极分子</td>
                        <td colspan=2 class=xl70 width=162>发展对象</td>
                    </tr>

                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170>全校研究生总数</td>
                        <td colspan=2 height=46 class=xl70 width=170>${total}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.partyMembersCount + doctors.partyMembersCount}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.formalMembers + doctors.formalMembers}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.preparedMembers + doctors.preparedMembers}</td>
<%--                        <td colspan=2 height=46 class=xl70 width=170>${cm:divide(masters.partyMembersCount + doctors.partyMembersCount, total, 2) * 100}%</td>--%>
                        <td colspan=2 height=46 class=xl70 width=170>${percent}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.applyTotal + doctors.applyTotal}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.activityTotal + doctors.activityTotal}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.developTotal + doctors.developTotal}</td>
                    </tr>

                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170>其中</td>
                        <td colspan=16 height=46 class=xl70 width=170></td>
                    </tr>

                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170>博士生总数</td>
                        <td colspan=2 height=46 class=xl70 width=170>${doctors.total}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${doctors.partyMembersCount}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${doctors.formalMembers}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${doctors.preparedMembers}</td>
<%--                        <td colspan=2 height=46 class=xl70 width=170>${cm:divide(doctors.partyMembersCount, doctors.total, 4) * 100}%</td>--%>
                        <td colspan=2 height=46 class=xl70 width=170>${doctors.doctorPercent}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${doctors.applyTotal}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${doctors.activityTotal}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${doctors.developTotal}</td>
                    </tr>

                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170>硕士生总数</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.total}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.partyMembersCount}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.formalMembers}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.preparedMembers}</td>
<%--                        <td colspan=2 height=46 class=xl70 width=170>${cm:divide(masters.partyMembersCount, masters.total, 4) * 100}%</td>--%>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.masterPercent}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.applyTotal}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.activityTotal}</td>
                        <td colspan=2 height=46 class=xl70 width=170>${masters.developTotal}</td>
                    </tr>
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
