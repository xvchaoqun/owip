<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" id="cartogram">
    <div class="col-xs-12">
        <div id="contentDiv" style="width: 1100px">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <div class="buttons pull-left hidden-sm hidden-xs" style="left:20px; position: relative">
                    <button style="margin-right: 10px;vertical-align: top!important;" class="jqExportBtn btn btn-success btn-sm tooltip-success"
                            data-url="${ctx}/pm/annualStat"
                            data-rel="tooltip" data-placement="top" title="导出组织生活年度统计">
                        <i class="fa fa-download"></i> 导出组织生活年度统计</button>
                    <select data-rel="select2" name="year" data-width="120px">
                        <c:forEach items="${yearList}" var="year">
                            <option value="${year}">${year}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("select[name=year]").val(${year});
                    </script>
                </div>
            </ul>

            <div class="tab-content" style="padding: 5px 4px 0px">
                <table border=0 cellpadding=0 cellspacing=0 width=1554 style='border-collapse:
 collapse;table-layout:fixed;width:1173pt'>
                    <col width=50 style='mso-width-source:userset;mso-width-alt:1600;width:38pt'>
                    <col class=xl69 width=108 style='mso-width-source:userset;mso-width-alt:3456;
 width:81pt'>
                    <col class=xl69 width=140 style='mso-width-source:userset;mso-width-alt:4480;
 width:105pt'>
                    <col class=xl69 width=154 style='mso-width-source:userset;mso-width-alt:4928;
 width:116pt'>
                    <col width=36 span=12 style='mso-width-source:userset;mso-width-alt:1152;
 width:27pt'>
                    <col width=72 style='mso-width-source:userset;mso-width-alt:2304;width:54pt'>
                    <col width=59 style='mso-width-source:userset;mso-width-alt:1888;width:44pt'>
                    <col width=34 span=12 style='mso-width-source:userset;mso-width-alt:1088;
 width:26pt'>
                    <col width=69 style='mso-width-source:userset;mso-width-alt:2208;width:52pt'>
                    <col width=62 style='mso-width-source:userset;mso-width-alt:1984;width:47pt'>
                    <tr height=53 style='mso-height-source:userset;height:40.15pt'>
                        <td height=53 width=50 style='height:40.15pt;width:38pt'></td>
                        <td colspan=31 class=xl71 width=1504 style='width:1135pt'>组织生活开展情况统计表</td>
                    </tr>
                    <tr height=33 style='mso-height-source:userset;height:25.15pt'>
                        <td rowspan=2 height=85 class=xl67 width=50 style='height:64.75pt;width:38pt'>　</td>
                        <td rowspan=2 class=xl67 width=108 style='width:81pt'>院系级党组织名称</td>
                        <td rowspan=2 class=xl67 width=140 style='width:105pt'>党支部名称</td>
                        <td rowspan=2 class=xl72 width=154 style='border-bottom:.5pt solid black;
  width:116pt'>党支部类型</td>
                        <td colspan=14 class=xl74 style='border-right:.5pt solid black;border-left:
  none'>支委会次数</td>
                        <td colspan=14 class=xl74 style='border-right:.5pt solid black;border-left:
  none'>党员集体活动</td>
                    </tr>
                    <tr height=52 style='mso-height-source:userset;height:39.6pt'>
                        <td height=52 class=xl65 style='height:39.6pt;border-top:none;border-left:
  none'>1月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>2月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>3月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>4月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>5月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>6月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>7月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>8月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>9月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>10月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>11月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>12月</td>
                        <td class=xl67 width=72 style='border-top:none;border-left:none;width:54pt'>按时开展月份数</td>
                        <td class=xl65 style='border-top:none;border-left:none'>完成率</td>
                        <td class=xl65 style='border-top:none;border-left:none'>1月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>2月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>3 月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>4 月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>5月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>6月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>7月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>8月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>9月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>10月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>11月</td>
                        <td class=xl65 style='border-top:none;border-left:none'>12月</td>
                        <td class=xl67 width=69 style='border-top:none;border-left:none;width:52pt'>按时开展月份数</td>
                        <td class=xl65 style='border-top:none;border-left:none'>完成率</td>
                    </tr>
                    <c:forEach items="${pmStatList}" var="pmStat">
                        <c:set value="${1+count}" var="count"/>
                        <tr class=xl70 height=29 style='mso-height-source:userset;height:22.15pt'>
                            <td height=29 class=xl68 width=50 style='height:22.15pt;border-top:none;width:38pt'>　${count}</td>
                            <td class=xl68 width=108 style='border-top:none;border-left:none;width:81pt'>　${pmStat.partyName}</td>
                            <td class=xl68 width=140 style='border-top:none;border-left:none;width:105pt'>　${pmStat.branchName}</td>
                            <td class=xl68 width=154 style='border-top:none;border-left:none;width:116pt'>　${pmStat.type}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcJau}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcFeb}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcMar}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcApr}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcMay}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcJun}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcJul}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcAug}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcSept}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcOct}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcNov}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcDec}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcHoldTime}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.bcFinishPercent}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaJau}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaFeb}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaMar}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaApr}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaMay}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaJun}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaJul}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaAug}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaSept}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaOct}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaNov}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaDec}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaHoldTime}</td>
                            <td class=xl66 style='border-top:none;border-left:none'>　${pmStat.gaFinishPercent}</td>
                        </tr>
                    </c:forEach>
                    <![if supportMisalignedColumns]>
                    <tr height=0 style='display:none'>
                        <td width=50 style='width:38pt'></td>
                        <td width=108 style='width:81pt'></td>
                        <td width=140 style='width:105pt'></td>
                        <td width=154 style='width:116pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=36 style='width:27pt'></td>
                        <td width=72 style='width:54pt'></td>
                        <td width=59 style='width:44pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=34 style='width:26pt'></td>
                        <td width=69 style='width:52pt'></td>
                        <td width=62 style='width:47pt'></td>
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
    .style0
    {mso-number-format:General;
        text-align:general;
        vertical-align:middle;
        white-space:nowrap;
        mso-rotate:0;
        mso-background-source:auto;
        mso-pattern:auto;
        color:black;
        font-size:11.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:等线;
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
        font-family:等线;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        mso-number-format:General;
        text-align:general;
        vertical-align:middle;
        border:none;
        mso-background-source:auto;
        mso-pattern:auto;
        mso-protection:locked visible;
        white-space:nowrap;
        mso-rotate:0;}
    .xl65
    {mso-style-parent:style0;
        font-size:12.0pt;
        text-align:center;
        border:.5pt solid windowtext;}
    .xl66
    {mso-style-parent:style0;
        text-align:center;
        border:.5pt solid windowtext;}
    .xl67
    {mso-style-parent:style0;
        font-size:12.0pt;
        text-align:center;
        border:.5pt solid windowtext;
        white-space:normal;}
    .xl68
    {mso-style-parent:style0;
        text-align:center;
        border:.5pt solid windowtext;
        white-space:normal;}
    .xl69
    {mso-style-parent:style0;
        white-space:normal;}
    .xl70
    {mso-style-parent:style0;
        text-align:center;}
    .xl71
    {mso-style-parent:style0;
        font-size:16.0pt;
        font-family:方正粗黑宋简体, monospace;
        mso-font-charset:134;
        text-align:center;}
    .xl72
    {mso-style-parent:style0;
        font-size:12.0pt;
        text-align:center;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:none;
        border-left:.5pt solid windowtext;
        white-space:normal;}
    .xl73
    {mso-style-parent:style0;
        text-align:center;
        border-top:none;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;
        white-space:normal;}
    .xl74
    {mso-style-parent:style0;
        font-size:12.0pt;
        text-align:center;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:.5pt solid windowtext;}
    .xl75
    {mso-style-parent:style0;
        font-size:12.0pt;
        text-align:center;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:none;}
    .xl76
    {mso-style-parent:style0;
        text-align:center;
        border-top:.5pt solid windowtext;
        border-right:.5pt solid windowtext;
        border-bottom:.5pt solid windowtext;
        border-left:none;}
    .xl77
    {mso-style-parent:style0;
        text-align:center;
        border-top:.5pt solid windowtext;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:none;}
</style>
<script>
    $('[data-rel="select2"]').change(function () {
        $("#cartogram").showLoading({text: '正在加载数据'});
        $.post("${ctx}/pm/annualStat", {year: this.value} , function (html) {
            $("#cartogram").replaceWith(html);
            $("#cartogram").hideLoading();
        });
    });
    $('[data-rel="select2"]').select2({
        allowClear: false,
    });
</script>