<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="contentDiv" style="width: 1148px">

            <jsp:include page="menu.jsp"/>
            <div class="tab-content" style="padding: 5px 4px 0px">
                <table border=0 cellpadding=0 cellspacing=0 width=1283 style='border-collapse:collapse;table-layout:fixed;width:966pt'>
                    <col width=86 span=2 style='mso-width-source:userset;mso-width-alt:2752;width:65pt'>
                    <col width=101 span=11 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>
                    <tr height=52 style='mso-height-source:userset;height:39.0pt'>
                        <td colspan=13 height=52 class=xl99 width=1283 style='height:39.0pt;width:966pt'>
                            <a name="Print_Area">${_school}<c:if test="${param.cls==1}">${checkParty.name}</c:if>本科生队伍党员信息分析</a>
                        </td>
                    </tr>
                    <tr height=28 style='mso-height-source:userset;height:21.0pt'>
                        <td colspan=13 height=28 class=xl100 style='height:21.0pt'>（数据源自${cm:formatDate(now,'yyyy年MM月dd日')}年统数据）</td>
                    </tr>
                    <c:if test="${param.cls!=1}">
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan=1 height=33 class=xl70 style=' border-right:.5pt solid black;height:24.95pt'>统计信息</td>
                            <td colspan=1 class=xl73 style='border-left:none'>学生总数</td>
                            <td colspan=1 class=xl73 style='border-left:none'>正式党员</td>
                            <td colspan=1 class=xl73 style='border-left:none'>预备党员</td>
                            <td colspan=1 class=xl73 style='border-left:none'>党员</td>
                            <td colspan=1 class=xl73 style='border-left:none'>占比</td>
                            <td colspan=1 class=xl73 style='border-left:none'>入党申请人</td>
                            <td colspan=1 class=xl73 style='border-left:none'>占比</td>
                            <td colspan=2 class=xl73 style='border-left:none'>入党积极分子</td>
                            <td colspan=1 class=xl73 style='border-left:none'>占比</td>
                            <td colspan=1 class=xl73 style='border-left:none'>发展对象</td>
                            <td colspan=1 class=xl73 style='border-left:none'>占比</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan=1 height=33 class=xl70 style=' border-right:.5pt solid black;height:24.95pt'>总数</td>
                            <td colspan=1 class=xl73 >${studentNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${positivePartyNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${growPartyNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${count}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${Proportion}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${totalNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${applyStr}</td>
                            <td colspan=2 class=xl73 style='border-left:none'>${activeNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${activeStr}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devStr}</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan=1 height=33 class=xl70 style=' border-right:.5pt solid black;height:24.95pt'>其中</td>
                            <td colspan=12 class=xl73 style='border-left:none'></td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan=1 height=33 class=xl70 style=' border-right:.5pt solid black;height:24.95pt'>2020级</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                            <td colspan=2 class=xl73 style='border-left:none'>0</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                            <td colspan=1 class=xl73 style='border-left:none'>0</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan=1 height=33 class=xl70 style=' border-right:.5pt solid black;height:24.95pt'>2019级</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${studentNum_19}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${positivePartyNum_19}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${growPartyNum_19}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${count_19}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${partyStr_19}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${totalNum_19}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${applyStr_19}</td>
                            <td colspan=2 class=xl73 style='border-left:none'>${activeNum_19}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${activeStr_19}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devNum_19}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devStr_19}</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan=1 height=33 class=xl70 style=' border-right:.5pt solid black;height:24.95pt'>2018级</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${studentNum_18}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${positivePartyNum_18}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${growPartyNum_18}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${count_18}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${partyStr_18}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${totalNum_18}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${applyStr_18}</td>
                            <td colspan=2 class=xl73 style='border-left:none'>${activeNum_18}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${activeStr_18}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devNum_18}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devStr_18}</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan=1 height=33 class=xl70 style=' border-right:.5pt solid black;height:24.95pt'>2017级</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${studentNum_17}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${positivePartyNum_17}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${growPartyNum_17}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${count_17}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${partyStr_17}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${totalNum_17}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${applyStr_17}</td>
                            <td colspan=2 class=xl73 style='border-left:none'>${activeNum_17}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${activeStr_17}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devNum_17}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devStr_17}</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan=1 height=33 class=xl70 style=' border-right:.5pt solid black;height:24.95pt'>2016级</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${studentNum_16}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${positivePartyNum_16}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${growPartyNum_16}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${count_16}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${partyStr_16}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${totalNum_16}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${applyStr_16}</td>
                            <td colspan=2 class=xl73 style='border-left:none'>${activeNum_16}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${activeStr_16}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devNum_16}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${devStr_16}</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan=1 height=33 class=xl70 style=' border-right:.5pt solid black;height:24.95pt'>其他年级</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherStudentNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherPositivePartyNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherGrowPartyNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherCountNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherPartyStr}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherTotalNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherApplyStr}</td>
                            <td colspan=2 class=xl73 style='border-left:none'>${otherActiveNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherActiveStr}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherDevNum}</td>
                            <td colspan=1 class=xl73 style='border-left:none'>${otherDevStr}</td>
                        </tr>
                    </c:if>

                </table>
            </div>
        </div>
    </div>
</div>
<style>
    tr
    {mso-height-source:auto;
        mso-ruby-visibility:none;}
    col
    {mso-width-source:auto;
        mso-ruby-visibility:none;}
    br
    {mso-data-placement:same-cell;}
    ruby
    {ruby-align:left;}
    .style16
    {mso-number-format:0%;
        mso-style-name:百分比;
        mso-style-id:5;}
    .style0
    {mso-number-format:General;
        text-align:general;
        vertical-align:bottom;
        white-space:nowrap;
        mso-rotate:0;
        mso-background-source:auto;
        mso-pattern:auto;
        color:black;
        font-size:11.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        border:none;
        mso-protection:locked visible;
        mso-style-name:常规;
        mso-style-id:0;}
    td
    {mso-style-parent:style0;
        padding-top:1px;
        padding-right:1px;
        padding-left:1px;
        mso-ignore:padding;
        color:black;
        font-size:11.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        mso-number-format:General;
        text-align:general;
        vertical-align:bottom;
        border:none;
        mso-background-source:auto;
        mso-pattern:auto;
        mso-protection:locked visible;
        white-space:nowrap;
        mso-rotate:0;}
    .xl65
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        white-space:normal;}
    .xl66
    {mso-style-parent:style0;
        font-size:14.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;}
    .xl67
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        vertical-align:middle;
        white-space:normal;}
    .xl68
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;}
    .xl69
    {mso-style-parent:style16;
        font-size:12.0pt;
        font-weight:700;
        mso-number-format:Percent;}
    .xl70
    {mso-style-parent:style0;
        font-size:14.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;}
    .xl71
    {mso-style-parent:style0;
        font-size:14.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:none;}
    .xl72
    {mso-style-parent:style0;
        font-size:14.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:none;}
    .xl73
    {mso-style-parent:style0;
        font-size:14.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid windowtext;}
    .xl74
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid windowtext;
        white-space:normal;}
    .xl75
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:1.0pt solid windowtext;
        white-space:normal;}
    .xl76
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:1.0pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;
        white-space:normal;}
    .xl77
    {mso-style-parent:style0;
        font-size:14.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:1.0pt solid windowtext;
        border-left:1.0pt solid windowtext;}
    .xl78
    {mso-style-parent:style0;
        font-size:14.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:1.0pt solid windowtext;
        border-left:.5pt solid windowtext;}
    .xl79
    {mso-style-parent:style0;
        font-size:14.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:1.0pt solid windowtext;
        border-bottom:1.0pt solid windowtext;
        border-left:.5pt solid windowtext;}
    .xl80
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid windowtext;}
    .xl81
    {mso-style-parent:style16;
        font-size:12.0pt;
        font-weight:700;
        mso-number-format:Percent;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid windowtext;}
    .xl82
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl83
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1.0pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:1.0pt solid windowtext;
        white-space:normal;}
    .xl84
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1.0pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;
        white-space:normal;}
    .xl85
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1.0pt solid windowtext;
        border-right:1.0pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;
        white-space:normal;}
    .xl86
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl87
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;}
    .xl88
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:none;}
    .xl89
    {mso-style-parent:style16;
        font-size:12.0pt;
        font-weight:700;
        mso-number-format:Percent;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;
        white-space:normal;}
    .xl90
    {mso-style-parent:style16;
        font-size:12.0pt;
        font-weight:700;
        mso-number-format:Percent;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl91
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;
        white-space:normal;}
    .xl92
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:none;
        border-left:.5pt solid windowtext;
        white-space:normal;}
    .xl93
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:none;
        border-left:none;
        white-space:normal;}
    .xl94
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:none;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;
        white-space:normal;}
    .xl95
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:none;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl96
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1.0pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:1.0pt solid windowtext;
        white-space:normal;}
    .xl97
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1.0pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl98
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1.0pt solid windowtext;
        border-right:1.0pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl99
    {mso-style-parent:style0;
        font-size:20.0pt;
        font-weight:700;
        font-family:华文中宋;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        text-align:center;
        vertical-align:middle;}
    .xl100
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:none;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:none;}
</style>
<script>
    $('[data-rel="select2"]').change(function () {
        $.post("${ctx}/stat/partySum?cls=1", {partyId: this.value} , function (html) {
            $("#cartogram").replaceWith(html);
        });
    });
    $('[data-rel="select2"]').select2();

    $(".unit-group").click(function () {
        $("#searchForm input[name=cls]").val($(this).data('type'));
        $(".jqSearchBtn").click();
    })
</script>