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
                            <div style="color:#337ab7;text-align:center;font-weight:600">教工队伍党员信息分析</div>
                            <div style="font-size:15px;font-weight:300">（数据源自${year}年${month}月年统数据）</div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="12" align="right">注：百分比按四舍五入取值</td>
                    </tr>
                    <tr>
                        <td colspan=2 rowspan="2" height=46 class=xl70 width=170>信息分类</td>
                        <td colspan=3 class=xl70 width=162>总数</td>
                        <td colspan=7 class=xl70 width=162>党员和发展党员信息</td>
                    </tr>

                    <tr>
                        <td colspan=1 height=46 class=xl70 width=170>合计</td>
                        <td colspan=1 height=46 class=xl70 width=170>男</td>
                        <td colspan=1 height=46 class=xl70 width=170>女</td>
                        <td colspan=1 height=46 class=xl70 width=170>正式党员</td>
                        <td colspan=1 height=46 class=xl70 width=170>预备党员</td>
                        <td colspan=1 height=46 class=xl70 width=170>合计</td>
                        <td colspan=1 height=46 class=xl70 width=170>占比</td>
                        <td colspan=1 height=46 class=xl70 width=170>入党申请人</td>
                        <td colspan=1 height=46 class=xl70 width=170>入党积极分子</td>
                        <td colspan=1 height=46 class=xl70 width=170>发展对象</td>
                    </tr>

                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170 style="width: 10%">校编教工</td>
                        <td colspan=1 height=46 class=xl70 width=170>${formationManAndWoman}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${formationMan}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${formationWoman}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${schoolFormation}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${schoolPrepared}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${schoolPartyTotal}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${schoolPercent}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${applySchool}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${activitySchool}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${developSchool}</td>
                    </tr>

                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170 style="width: 10%">专任教师</td>
                        <td colspan=1 height=46 class=xl70 width=170>${speciallyManAndWoman}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${speciallyMan}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${speciallyWoman}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${speciallyFormation}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${speciallyPrepared}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${speciallyPartyTotal}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${speciallyPercent}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${applySpecially}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${activitySpecially}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${developSpecially}</td>
                    </tr>

                    <tr>
                        <td colspan=12 height=46 class=xl70 width=170>专任教师中区分职称级别</td>
                    </tr>

                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170 style="width: 10%">正高级</td>
                        <td colspan=1 height=46 class=xl70 width=170>${isLevel}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${isLevelMan}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${isLevelWoman}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${speciallyIsLevelFormation}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${preparedSpeciallyIsLevelFormation}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${isLevelPartyTotal}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${isLevelPercent}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${applySpeciallyIsLevel}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${activitySpeciallyIsLevel}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${developSpeciallyIsLevel}</td>
                    </tr>

                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170 style="width: 10%">副高级</td>
                        <td colspan=1 height=46 class=xl70 width=170>${deputyLevel}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${deputyLevelMan}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${deputyLevelWoman}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${speciallyDeputyLevel}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${preparedSpeciallyDeputyLevel}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${deputyLevelPartyTotal}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${deputyLevelPercent}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${applySpeciallyDeputyLevel}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${activitySpeciallyDeputyLevel}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${developSpeciallyDeputyLevel}</td>
                    </tr>

                    <tr>
                        <td colspan=2 height=46 class=xl70 width=170 style="width: 10%">中级及以下</td>
                        <td colspan=1 height=46 class=xl70 width=170>${isLevelTotal}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${followingMan}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${followingWoman}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${intermediateFormation}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${intermediatePrepared}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${intermediateTotal}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${intermediatePercent}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${mediumApply}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${mediumActivity}</td>
                        <td colspan=1 height=46 class=xl70 width=170>${mediumDevelop}</td>
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
