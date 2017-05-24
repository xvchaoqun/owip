<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<link rel="stylesheet" href="css/cpc.css"/>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/cpcAllocation_page"
                 data-url-export="${ctx}/cpcAllocation_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.unitId ||not empty param.postId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cpcAllocation:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/cpcAllocation_selectUnits"><i class="fa fa-plus"></i> 设置</a>
                </shiro:hasPermission>

                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>
            </div>
            <div class="space-4"></div>

            <table border=0 cellpadding=0 cellspacing=0 width=1125 class=xl6324425
                   style='border-collapse:collapse;table-layout:fixed;width:847pt'>
                <col class=xl6324425 width=32 >
                <col class=xl7124425 width=151 >
                <col class=xl6324425 width=42 >
                <col class=xl6324425 width=57>
                <col class=xl7124425 width=116>
                <col class=xl6324425 width=57>
                <col class=xl6324425 width=42>
                <col class=xl6324425 width=57 >
                <col class=xl7124425 width=242>
                <col class=xl6324425 width=57>
                <col class=xl6324425 width=42>
                <col class=xl6324425 width=57>
                <col class=xl7124425 width=116>
                <col class=xl6324425 width=57>
                <tr height=52>
                    <td colspan=13 height=52 class=xl8224425 width=1068>北京师范大学内设机构干部配备情况
                    </td>
                    <td class=xl6324425 width=57></td>
                </tr>
                <tr height=30>
                    <td colspan=14 height=30 class=xl8324425 width=1068 >统计日期：2017年2月16日
                    </td>
                </tr>
                <tr class=xl6524425 height=30>
                    <td rowspan=3 height=90 class=xl6624425 width=32>序号
                    </td>
                    <td rowspan=3 class=xl6624425 width=151>单<span
                            style='mso-spacerun:yes'>&nbsp; </span>位
                    </td>
                    <td colspan=4 class=xl6624425 width=272>正处级干部
                    </td>
                    <td colspan=4 class=xl8524425 width=398>副处级干部
                    </td>
                    <td colspan=4 class=xl8024425 width=272 style='border-right: 2.0pt double black;'>无行政级别干部
                    </td>
                </tr>
                <tr class=xl6524425 height=30 style='mso-height-source:userset;height:22.9pt'>
                    <td rowspan=2 height=60 class=xl6624425 width=42 style='height:45.8pt;
  border-top:none;width:32pt'>职数
                    </td>
                    <td colspan=2 class=xl6624425 width=173>现任干部情况</td>
                    <td rowspan=2 class=xl7924425 width=57>空缺数</td>
                    <td rowspan=2 class=xl7824425 width=42>职数</td>
                    <td colspan=2 class=xl6624425 width=299 style="border-right: none;">现任干部情况</td>
                    <td rowspan=2 class=xl7924425 width=57>空缺数</td>
                    <td rowspan=2 class=xl7824425 width=42>职数</td>
                    <td colspan=2 class=xl6624425 width=173 style="border-right: none;">现任干部情况</td>
                    <td rowspan=2 class=xl7924425 width=57>空缺数</td>
                </tr>
                <tr class=xl6524425 height=30>
                    <td height=30 class=xl6624425 width=57>现任数
                    </td>
                    <td class=xl7024425 width=116>现任干部
                    </td>
                    <td class=xl6624425 width=57>现任数
                    </td>
                    <td class=xl7024425 width=242>现任干部
                    </td>
                    <td class=xl7624425 width=57 >现任数</td>
                    <td class=xl7724425 width=116>现任干部
                    </td>
                </tr>

                <tr height=40 style='mso-height-source:userset;height:30.0pt'>
                    <td height=40 class=xl6424425 width=32>1
                    </td>
                    <td class=xl6924425 width=151>党委/校长办公室
                        （校史研究室）
                    </td>
                    <td class=xl6424425 width=42>1
                    </td>
                    <td class=xl6424425 width=57>1
                    </td>
                    <td class=xl6924425 width=116 >康震
                    </td>
                    <td class=xl6824425 width=57>0
                    </td>
                    <td class=xl6724425 width=42>5</td>
                    <td class=xl6424425 width=57>4
                    </td>
                    <td class=xl6924425 width=242>吴汉锋、杨钊、魏书亮、李亚飞
                    </td>
                    <td class=xl6824425 width=57>1
                    </td>
                    <td class=xl7524425 width=42>　</td>
                    <td class=xl6424425 width=57>　</td>
                    <td class=xl6924425 width=116>　</td>
                    <td class=xl6824425 width=57>0
                    </td>
                </tr>

                <tr height=40>
                    <td colspan=2 height=40 class=xl8424425 width=183>合<span style='mso-spacerun:yes'>&nbsp; </span>计
                    </td>
                    <td class=xl6424425 width=42>139</td>
                    <td class=xl6424425 width=57>133
                    </td>
                    <td class=xl6924425 width=116>　
                    </td>
                    <td class=xl6824425 width=57>6
                    </td>
                    <td class=xl6724425 width=42>234</td>
                    <td class=xl6424425 width=57>211
                    </td>
                    <td class=xl6924425 width=242>　
                    </td>
                    <td class=xl6824425 width=57>23
                    </td>
                    <td class=xl6424425 width=42>14</td>
                    <td class=xl6424425 width=57>14
                    </td>
                    <td class=xl6924425 width=116>　
                    </td>
                    <td class=xl6824425 width=57>0
                    </td>
                </tr>
            </table>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>
</script>