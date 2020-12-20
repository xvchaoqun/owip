<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="contentDiv" style="width: 1148px">

            <div class="tab-content" style="padding: 5px 4px 0px">
                <table border=0 cellpadding=0 cellspacing=0 width=1283 style='border-collapse:
 collapse;table-layout:fixed;width:966pt'>
                    <button class="downloadBtn pull-left btn btn-success btn-sm"
                            data-url="${ctx}/stat/owSum?export=1&${cm:encodeQueryString(pageContext.request.queryString)}"><i class="fa fa-download"></i>
                        导出
                    </button>
                    <col width=86 span=2 style='mso-width-source:userset;mso-width-alt:2752;
 width:65pt'>
                    <col width=101 span=11 style='mso-width-source:userset;mso-width-alt:3232;
 width:76pt'>
                    <tr height=52 style='mso-height-source:userset;height:39.0pt'>
                        <td colspan=13 height=52 class=xl99 width=1283 style='height:39.0pt;
  width:966pt'><a name="Print_Area">${_school}基层党组织及党员信息总表</a></td>
                    </tr>
                    <tr height=28 style='mso-height-source:userset;height:21.0pt'>
                        <td colspan=13 height=28 class=xl100 style='height:21.0pt'>（数据源自${cm:formatDate(now,'yyyy年MM月dd日')}年统数据）</td>
                    </tr>
                    <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                        <td colspan=5 height=33 class=xl70 style='border-right:.5pt solid black;
  height:24.95pt'>党工委、二级党组织总数</td>
                        <c:forEach items="${metaTypes}" var="metaType">
                            <td colspan=2 class=xl73 style='border-left:none'>${metaType.name}</td>
                        </c:forEach>
                    </tr>
                    <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                        <td colspan=5 height=33 class=xl70 style='border-right:.5pt solid black;
  height:24.95pt'>${partySumCount}</td>
                        <c:forEach items="${partyCounts}" var="partyCount">
                            <td colspan=2 class=xl73 style='border-left:none'>${partyCount}</td>
                        </c:forEach>
                    </tr>
                    <tr height=36 style='height:27.0pt;mso-xlrowspan:2'>
                        <td height=36 colspan=13 style='height:27.0pt;mso-ignore:colspan'></td>
                    </tr>
                    <tr class=xl65 height=34 style='mso-height-source:userset;height:26.1pt'>
                        <td rowspan=2 height=146 class=xl74 width=86 style='height:110.1pt;
  width:65pt'>内设<br>
                            党总支<br>
                            总数</td>
                        <td rowspan=2 class=xl91 width=86 style='width:65pt'>党支部<br>
                            总数</td>
                        <td colspan=3 class=xl96 width=303 style='border-right:1.0pt solid black;
  width:228pt'>教工党支部总数</td>
                        <td rowspan=2 class=xl86 width=101 style='width:76pt'>${cm:getMetaTypeByCode('mt_retire').name}</td>
                        <td colspan=3 class=xl83 width=303 style='border-right:1.0pt solid black;
  width:228pt'>师生纵向党支部总数</td>
                        <td colspan=4 class=xl82 width=404 style='width:304pt'>学生党支部总数</td>
                    </tr>
                    <tr class=xl65 height=112 style='mso-height-source:userset;height:84.0pt'>
                        <td height=112 class=xl75 width=101 style='height:84.0pt;border-top:none;
  width:76pt'>合计</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>${cm:getMetaTypeByCode('mt_professional_teacher').name}</td>
                        <td class=xl76 width=101 style='border-top:none;border-left:none;width:76pt'>${cm:getMetaTypeByCode('mt_support_teacher').name}</td>
                        <td class=xl75 width=101 style='border-top:none;width:76pt'>合计</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>${cm:getMetaTypeByCode('mt_undergraduate_assistant').name}</td>
                        <td class=xl76 width=101 style='border-top:none;border-left:none;width:76pt'>${cm:getMetaTypeByCode('mt_graduate_teacher').name}</td>
                        <td class=xl82 width=101 style='border-top:none;width:76pt'>合计</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>${cm:getMetaTypeByCode('mt_ss_graduate').name}</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>${cm:getMetaTypeByCode('mt_sb_graduate').name}</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>${cm:getMetaTypeByCode('mt_bs_graduate').name}</td>
                    </tr>
                    <tr class=xl66 height=33 style='mso-height-source:userset;height:24.95pt'>
                        <td height=33 class=xl73 style='height:24.95pt;border-top:none'></td>
                        <td class=xl70 style='border-top:none;border-left:none'>${branchTotalCount}</td>
                        <td class=xl77 style='border-top:none'>${professionalCount+supportCount}</td>
                        <td class=xl78 style='border-top:none;border-left:none'>${professionalCount}</td>
                        <td class=xl79 style='border-top:none;border-left:none'>${supportCount}</td>
                        <td class=xl71 style='border-top:none'>${retireCount}</td>
                        <td class=xl77 style='border-top:none'>${undergraduateCount+graduateCount}</td>
                        <td class=xl78 style='border-top:none;border-left:none'>${undergraduateCount}</td>
                        <td class=xl79 style='border-top:none;border-left:none'>${graduateCount}</td>
                        <td class=xl72 style='border-top:none'>${ssCount+sbCount+bsCount}</td>
                        <td class=xl73 style='border-top:none;border-left:none'>${ssCount}</td>
                        <td class=xl73 style='border-top:none;border-left:none'>${sbCount}</td>
                        <td class=xl73 style='border-top:none;border-left:none'>${bsCount}</td>
                    </tr>
                    <tr height=36 style='height:27.0pt;mso-xlrowspan:2'>
                        <td height=36 colspan=13 style='height:27.0pt;mso-ignore:colspan'></td>
                    </tr>
                    <tr class=xl67 height=33 style='mso-height-source:userset;height:24.95pt'>
                        <td colspan=2 rowspan=2 height=89 class=xl92 width=172 style='border-right:
  .5pt solid black;border-bottom:.5pt solid black;height:66.95pt;width:130pt'>党员统计</td>
                        <td rowspan=2 class=xl74 width=101 style='width:76pt'>师生党员<br>
                            总数</td>
                        <td rowspan=2 class=xl74 width=101 style='width:76pt'>教工党员<br>
                            总数</td>
                        <td colspan=4 class=xl74 width=404 style='border-left:none;width:304pt'>专任教师党员</td>
                        <td rowspan=2 class=xl74 width=101 style='width:76pt'>离退休<br>
                            教工党员<br>
                            总数</td>
                        <td colspan=4 class=xl74 width=404 style='border-left:none;width:304pt'>学生党员</td>
                    </tr>
                    <tr class=xl67 height=56 style='mso-height-source:userset;height:42.0pt'>
                        <td height=56 class=xl74 width=101 style='height:42.0pt;border-top:none;
  border-left:none;width:76pt'>总数</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>正高级</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>副高级</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>中级及<br>
                            以下</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>总数</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>本科生<br>
                            党员</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>硕士生<br>
                            党员</td>
                        <td class=xl74 width=101 style='border-top:none;border-left:none;width:76pt'>博士生<br>
                            党员</td>
                    </tr>
                    <tr class=xl68 height=33 style='mso-height-source:userset;height:24.95pt'>
                        <td colspan=2 height=33 class=xl87 style='border-right:.5pt solid black;
  height:24.95pt'>人数</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${totalCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${teacherCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${chiefCount+deputyCount+middleCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${chiefCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${deputyCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${middleCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${retireCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${bksCount+ssCount+bsCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${bksCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${ssCount}</td>
                        <td class=xl80 style='border-top:none;border-left:none'>${bsCount}</td>
                    </tr>
                    <tr class=xl69 height=33 style='mso-height-source:userset;height:24.95pt'>
                        <td colspan=2 height=33 class=xl89 width=172 style='border-right:.5pt solid black;
  height:24.95pt;width:130pt'>在群体中占比</td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                        <td class=xl81 style='border-top:none;border-left:none'></td>
                    </tr>
                    <tr height=18 style='height:13.5pt'>
                        <td height=18 colspan=13 style='height:13.5pt;mso-ignore:colspan'></td>
                    </tr>
                    <tr height=18 style='height:13.5pt'>
                        <td height=18 style='height:13.5pt'></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr height=18 style='height:13.5pt'>
                        <td height=18 style='height:13.5pt'></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr height=18 style='height:13.5pt'>
                        <td height=18 style='height:13.5pt'></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <![if supportMisalignedColumns]>
                    <tr height=0 style='display:none'>
                        <td width=86 style='width:65pt'></td>
                        <td width=86 style='width:65pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                        <td width=101 style='width:76pt'></td>
                    </tr>
                    <![endif]>
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

