<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="contentDiv" style="width: 1148px" width=1283>

            <jsp:include page="menu.jsp"/>
            <div class="tab-content" style="padding: 2px 2px 0px">
                <table border=0 cellpadding=0 cellspacing=0 width=1283 >
                    <tr height=52 style='mso-height-source:userset;height:39.0pt'>
                        <td colspan=13 height=52 width=1283 class=xl99 width=1283 style='height:39.0pt;width:966pt'>
                            <a name="Print_Area">${_school}<c:if test="${param.cls==1}">${checkParty.name}</c:if>全校党支部书记队伍整体情况分析</a>
                        </td>
                    </tr>
                    <tr height=28 style='mso-height-source:userset;height:21.0pt'>
                        <td colspan=13 height=28 width=1283 class=xl100 style='height:21.0pt'>（数据源自${cm:formatDate(now,'yyyy年MM月dd日')}年统数据）</td>
                    </tr>
                    <tr height=33 width=1283 style='mso-height-source:userset;height:24.95pt'>
                        <td height=33 width="100" class=xl73 style=' border-right:.5pt solid black;height:24.95pt;' rowspan="2">二级党组织/支部及书记类型</td>
                        <td class=xl73 style='border-left:none;width: 80px' rowspan="2">本科生辅导员纵向党支部</td>
                        <td colspan="4" class=xl73 style='border-left:none'>研究生党支部</td>
                        <td colspan="4" class=xl73 style='border-left:none'>研究生辅导员纵向党支部</td>
                        <td colspan="4" class=xl73 style='border-left:none'>专任教师党支部</td>
                        <td height=33 class=xl73 style='border-left:none;width: 80px' rowspan="2">离退休教工党支部</td>
                        <td class=xl73 style='border-left:none;width: 80px' rowspan="2">机关行政产业后勤党支部</td>
                        <td class=xl73 style='border-left:none;width: 80px' rowspan="2">总计</td>

                    </tr><tr height=33 style='mso-height-source:userset;height:24.95pt'>
                        <td class=xl73 style='border-left:none;width: 70px'>硕士研究生党支部</td>
                        <td class=xl73 style='border-left:none;width: 70px'>博士研究生党支部</td>
                        <td class=xl73 style='border-left:none;width: 70px'>硕博研究生党支部</td>
                        <td class=xl73 style='border-left:none;width: 50px'>合计</td>
                        <td class=xl73 style='border-left:none;width: 70px'>正高级教师担任党支部书记</td>
                        <td class=xl73 style='border-left:none;width: 70px'>副高级教师担任党支部书记</td>
                        <td class=xl73 style='border-left:none;width: 70px'>中级及以下担任党支部书记</td>
                        <td class=xl73 style='border-left:none;width: 50px'>合计</td>
                        <td class=xl73 style='border-left:none;width: 70px'>正高级教师担任党支部书记</td>
                        <td class=xl73 style='border-left:none;width: 70px'>副高级教师担任党支部书记</td>
                        <td class=xl73 style='border-left:none;width: 70px'>中级及以下担任党支部书记</td>
                        <td class=xl73 style='border-left:none;width: 50px'>合计</td>
                    </tr>
                    <c:forEach items="${dataList}" var="data">
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td height=33 class=xl73 style=' border-right:.5pt solid black;height:24.95pt'>${data.partyName}</td>
                            <td class=xl73 style='border-left:none'>${data.undergraduateNum}</td>
                            <td class=xl73 style='border-left:none'>${data.ssGraduateNum}</td>
                            <td class=xl73 style='border-left:none'>${data.bsGraduateNum}</td>
                            <td class=xl73 style='border-left:none'>${data.sbGraduateNum}</td>
                            <td class=xl73 style='border-left:none'>${data.yjsTotal}</td>
                            <td class=xl73 style='border-left:none'>${data.directorYjsNum}</td>
                            <td class=xl73 style='border-left:none'>${data.deputyNum}</td>
                            <td class=xl73 style='border-left:none'>${data.intermediateNum}</td>
                            <td class=xl73 style='border-left:none'>${data.yjsTecherNum}</td>
                            <td class=xl73 style='border-left:none'>${data.directorTeacherNum}</td>
                            <td class=xl73 style='border-left:none'>${data.deputyTeacherNum}</td>
                            <td class=xl73 style='border-left:none'>${data.intermediateTeacher}</td>
                            <td class=xl73 style='border-left:none'>${data.professionalTeacherNum}</td>
                            <td class=xl73 style='border-left:none'>${data.retireNum}</td>
                            <td class=xl73 style='border-left:none'>${data.supportTeacherNum}</td>
                            <td class=xl73 style='border-left:none'>${data.rowTotalCount}</td>
                        </tr>

                    </c:forEach>

                    <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                        <td height=33 class=xl73 style=' border-right:.5pt solid black;height:24.95pt'>总计</td>
                        <td class=xl73 style='border-left:none'>${totalUndergraduateNum}</td>
                        <td class=xl73 style='border-left:none'>${totalSsGraduateNum}</td>
                        <td class=xl73 style='border-left:none'>${totalBsGraduateNum}</td>
                        <td class=xl73 style='border-left:none'>${totalSbGraduateNum}</td>
                        <td class=xl73 style='border-left:none'>${totalYjsNum}</td>
                        <td class=xl73 style='border-left:none'>${totalDirectorYjsNum}</td>
                        <td class=xl73 style='border-left:none'>${totalDeputyNum}</td>
                        <td class=xl73 style='border-left:none'>${totalIntermediate}</td>
                        <td class=xl73 style='border-left:none'>${totalYjsTecher}</td>
                        <td class=xl73 style='border-left:none'>${totalDirectorTeacher}</td>
                        <td class=xl73 style='border-left:none'>${totalDeputyTeacher}</td>
                        <td class=xl73 style='border-left:none'>${totalIntermediateTeacher}</td>
                        <td class=xl73 style='border-left:none'>${totalFulltimeTecher}</td>
                        <td class=xl73 style='border-left:none'>${totalRetireNum}</td>
                        <td class=xl73 style='border-left:none'>${totalSupportNum}</td>
                        <td class=xl73 style='border-left:none'>${allNum}</td>

                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<style>
    col
    {mso-width-source:auto;
        mso-ruby-visibility:none;}
    br
    {mso-data-placement:same-cell;}
    ruby
    {ruby-align:left;}


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

    .xl73
    {mso-style-parent:style0;
        font-size:14.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid windowtext;}

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
    .style0
    {mso-number-format:General;
        text-align:general;
        vertical-align:bottom;
        white-space:nowrap;
        mso-rotate:0;
        mso-background-source:auto;
        mso-pattern:auto;
        color:black;
        font-size:10.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:"Times New Roman", serif;
        mso-font-charset:0;
        border:none;
        mso-protection:locked visible;
        mso-style-name:常规;
        mso-style-id:0;}
    .font5
    {color:windowtext;
        font-size:8.5pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;}
    .font7
    {color:windowtext;
        font-size:15.5pt;
        font-weight:700;
        font-style:normal;
        text-decoration:none;
        font-family:华文中宋;
        mso-generic-font-family:auto;
        mso-font-charset:134;}
    .font8
    {color:windowtext;
        font-size:8.5pt;
        font-weight:700;
        font-style:normal;
        text-decoration:none;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;}
    td
    {mso-style-parent:style0;
        padding-top:1px;
        padding-right:1px;
        padding-left:1px;
        mso-ignore:padding;
        color:black;
        font-size:10.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:"Times New Roman", serif;
        mso-font-charset:0;
        mso-number-format:General;
        text-align:left;
        vertical-align:top;
        border:none;
        mso-background-source:auto;
        mso-pattern:auto;
        mso-protection:locked visible;
        white-space:nowrap;
        mso-rotate:0;}
    .xl65
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        border:.5pt solid black;
        white-space:normal;}
    .xl66
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid black;
        white-space:normal;}
    .xl67
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        border:.5pt solid black;
        white-space:normal;
        padding-left:18px;
        mso-char-indent-count:2;}
    .xl68
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        text-align:center;
        border:.5pt solid black;
        white-space:normal;}
    .xl69
    {mso-style-parent:style0;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        mso-number-format:0;
        text-align:center;
        border:.5pt solid black;
        white-space:nowrap;
        mso-text-control:shrinktofit;}

    .xl70
    {mso-style-parent:style0;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        mso-number-format:Percent;
        border:.5pt solid black;
        white-space:nowrap;
        mso-text-control:shrinktofit;
        padding-left:9px;
        mso-char-indent-count:1;}
    .xl71
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        text-align:right;
        vertical-align:middle;
        border:.5pt solid black;
        white-space:normal;
        padding-right:18px;
        mso-char-indent-count:2;}
    .xl72
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        border:.5pt solid black;
        white-space:normal;
        padding-left:9px;
        mso-char-indent-count:1;}
    .xl73
    {mso-style-parent:style0;
        vertical-align:middle;
        border:.5pt solid black;
        white-space:normal;}
    .xl74
    {mso-style-parent:style0;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        mso-number-format:0;
        text-align:right;
        border:.5pt solid black;
        white-space:nowrap;
        mso-text-control:shrinktofit;
        padding-right:9px;
        mso-char-indent-count:1;}
    .xl75
    {mso-style-parent:style0;
        text-align:center;
        white-space:normal;}
    .xl76
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        vertical-align:middle;
        border-top:.5pt solid black;
        border-right:.5pt solid black;
        border-bottom:none;
        border-left:.5pt solid black;
        white-space:normal;
        padding-left:9px;
        mso-char-indent-count:1;}
    .xl77
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        vertical-align:middle;
        border-top:none;
        border-right:.5pt solid black;
        border-bottom:none;
        border-left:.5pt solid black;
        white-space:normal;
        padding-left:9px;
        mso-char-indent-count:1;}
    .xl78
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        vertical-align:middle;
        border-top:none;
        border-right:.5pt solid black;
        border-bottom:.5pt solid black;
        border-left:.5pt solid black;
        white-space:normal;
        padding-left:9px;
        mso-char-indent-count:1;}
    .xl79
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        vertical-align:middle;
        border-top:.5pt solid black;
        border-right:.5pt solid black;
        border-bottom:none;
        border-left:.5pt solid black;
        white-space:normal;}
    .xl80
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        vertical-align:middle;
        border-top:none;
        border-right:.5pt solid black;
        border-bottom:none;
        border-left:.5pt solid black;
        white-space:normal;}
    .xl81
    {mso-style-parent:style0;
        color:windowtext;
        font-size:8.5pt;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        vertical-align:middle;
        border-top:none;
        border-right:.5pt solid black;
        border-bottom:.5pt solid black;
        border-left:.5pt solid black;
        white-space:normal;}
    .xl82
    {mso-style-parent:style0;
        white-space:normal;
        padding-left:36px;
        mso-char-indent-count:4;}

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