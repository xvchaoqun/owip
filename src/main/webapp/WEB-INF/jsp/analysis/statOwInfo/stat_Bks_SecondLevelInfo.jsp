<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" id="cartogram">
    <div class="col-xs-12">
        <div id="contentDiv" style="width: 1148px">
            <div class="tab-content" style="padding: 5px 4px 0px">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <%--<li class="<c:if test="${cls==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/statOwInfo?cls=1">
                            <i class="fa fa-th"></i> 全校研究生队伍党员信息分析</a>
                    </li>
                    <li class="<c:if test="${cls==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/statOwInfo?cls=2">
                            <i class="fa fa-th"></i> 各二级党组织研究生队伍党员信息分析</a>
                    </li>
                    <li class="<c:if test="${cls==3}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/statOwInfo?cls=3">
                            <i class="fa fa-th"></i> 全校本科生队伍党员信息分析</a>
                    </li>
                    <li class="<c:if test="${cls==4}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/statOwInfo?cls=4">
                            <i class="fa fa-th"></i> 各二级党组织本科生队伍党员信息分析</a>
                    </li>--%>
                    <jsp:include page="menu.jsp"/>

                </ul>
                <col width=86 span=2 style='mso-width-source:userset;mso-width-alt:2752;width:65pt'>
                <col width=101 span=11 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>
                <table border=0 cellpadding=0 cellspacing=0 width=1283 style='border-collapse:collapse;table-layout:fixed;width:966pt'>
                    <tr height=52 style='mso-height-source:userset;height:39.0pt'>
                        <td colspan=13 height=52 class=xl99 width=1283 style='height:39.0pt;width:966pt'>
                            <a name="Print_Area">${_school}<c:if test="${param.cls==1}">${checkParty.name}</c:if>二级党委本科生队伍党员信息分析</a>
                        </td>
                    </tr>
                    <tr height=28 style='mso-height-source:userset;height:21.0pt'>
                        <td colspan=13 height=28 class=xl100 style='height:21.0pt'>（数据源自${cm:formatDate(now,'yyyy年MM月dd日')}年统数据）</td>
                    </tr>
                    <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan="1" height=33 class=xl73 style=' border-right:.5pt solid black;height:24.95pt;'>二级党组织</td>
                            <td colspan="" class=xl73 style='border-left:none;width: 80px'>年级</td>
                            <td class=xl73 style='border-left:none'>入党申请人</td>
                            <td colspan="2" class=xl73 style='border-left:none;width: 50px'>入党积极分子</td>
                            <td class=xl73 style='border-left:none'>发展对象</td>
                            <td class=xl73 style='border-left:none'>正式党员</td>
                            <td class=xl73 style='border-left:none'>预备党员</td>
                            <td class=xl73 style='border-left:none'>普通学生</td>
                            <td class=xl73 style='border-left:none'>合计</td>
                            <td colspan="2" class=xl73 style='border-left:none;width: 90px'>培养情况占比</td>
                            <td class=xl73 style='border-left:none'>党员占比</td>
                    </tr>
                    <c:forEach items="${data}" var="data">
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td colspan="1" height=33 class=xl73 style=' border-right:.5pt solid black;height:24.95pt' rowspan="6">${data.partyName}</td>
                            <td class=xl73 >往届</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td colspan="2" class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td colspan="2" class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>-</td>
                        </tr>

                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td class=xl73 >2016</td>
                            <td class=xl73 style='border-left:none'>${data.count_16}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.activeNum_16}</td>
                            <td class=xl73 style='border-left:none'>${data.devNum_16}</td>
                            <td class=xl73 style='border-left:none'>${data.positivePartyCount_16}</td>
                            <td class=xl73 style='border-left:none'>${data.growPartyCount_16}</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>${data.totalCount_16}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.train_16}</td>
                            <td class=xl73 style='border-left:none'>${data.partyProportion_16}</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td class=xl73 >2017</td>
                            <td class=xl73 style='border-left:none'>${data.count_17}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.activeNum_17}</td>
                            <td class=xl73 style='border-left:none'>${data.devNum_17}</td>
                            <td class=xl73 style='border-left:none'>${data.positivePartyCount_17}</td>
                            <td class=xl73 style='border-left:none'>${data.growPartyCount_17}</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>${data.totalCount_17}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.train_17}</td>
                            <td class=xl73 style='border-left:none'>${data.partyProportion_17}</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td class=xl73 >2018</td>
                            <td class=xl73 style='border-left:none'>${data.count_18}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.activeNum_18}</td>
                            <td class=xl73 style='border-left:none'>${data.devNum_18}</td>
                            <td class=xl73 style='border-left:none'>${data.positivePartyCount_18}</td>
                            <td class=xl73 style='border-left:none'>${data.growPartyCount_18}</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>${data.totalCount_18}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.train_18}</td>
                            <td class=xl73 style='border-left:none'>${data.partyProportion_18}</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td class=xl73 >2019</td>
                            <td class=xl73 style='border-left:none'>${data.count_19}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.activeNum_19}</td>
                            <td class=xl73 style='border-left:none'>${data.devNum_19}</td>
                            <td class=xl73 style='border-left:none'>${data.positivePartyCount_19}</td>
                            <td class=xl73 style='border-left:none'>${data.growPartyCount_19}</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>${data.totalCount_19}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.train_19}</td>
                            <td class=xl73 style='border-left:none'>${data.partyProportion_19}</td>
                        </tr>
                        <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                            <td class=xl73 >合计</td>
                            <td class=xl73 style='border-left:none'>${data.allParty}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.activeTotalCount}</td>
                            <td class=xl73 style='border-left:none'>${data.devTotalCount}</td>
                            <td class=xl73 style='border-left:none'>${data.positivePartyTotalCount}</td>
                            <td class=xl73 style='border-left:none'>${data.growPartyTotalCount}</td>
                            <td class=xl73 style='border-left:none'>-</td>
                            <td class=xl73 style='border-left:none'>${data.totalCount}</td>
                            <td colspan="2" class=xl73 style='border-left:none'>${data.train}</td>
                            <td class=xl73 style='border-left:none'>${data.partyProportion}</td>
                        </tr>


                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<style>
    /*tr
    {mso-height-source:auto;
        mso-ruby-visibility:none;}*/
    col
    {mso-width-source:auto;
        mso-ruby-visibility:none;}
    br
    {mso-data-placement:same-cell;}
    ruby
    {ruby-align:left;}

    button{
        margin-left: 10px;
    }

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