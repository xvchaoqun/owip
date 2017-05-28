<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<link rel="stylesheet" href="${ctx}/css/cpc.css"/>
<table border=0 cellpadding=0 cellspacing=0 width=1125 class=xl6324425
       style='border-collapse:collapse;table-layout:fixed;width:847pt'>
<shiro:hasPermission name="cpcAllocation:edit">
  <col class=xl6324425 width=32>
  </shiro:hasPermission>
  <col class=xl6324425 width=32>
  <col class=xl7124425 width=151>
  <col class=xl6324425 width=42>
  <col class=xl6324425 width=57>
  <col class=xl7124425 width=116>
  <col class=xl6324425 width=57>
  <col class=xl6324425 width=42>
  <col class=xl6324425 width=57>
  <col class=xl7124425 width=242>
  <col class=xl6324425 width=57>
  <col class=xl6324425 width=42>
  <col class=xl6324425 width=57>
  <col class=xl7124425 width=116>
  <col class=xl6324425 width=57>
  <tr height=52>
    <td colspan=14 height=52 class=xl8224425 width=1068>北京师范大学内设机构干部配备情况
    </td>
  </tr>
  <tr height=30>
    <td colspan=7 height=30 class=xl8324426 width=1068>
      统计日期：${cm:formatDate(now,'yyyy年MM月dd日')}
    </td>
    <td colspan=7 height=30 class=xl8324425 width=1068>
      注：<span class="isCpc">(占职数)</span>/<span class="notCpc">(不占职数)</span>
    </td>
  </tr>
  <tr class=xl6524425 height=30>
    <shiro:hasPermission name="cpcAllocation:edit">
    <td rowspan=3 height=90 class=xl6624425 width=32>
      <input type="checkbox" name="checkall">
    </td>
    </shiro:hasPermission>
    <td rowspan=3 height=90 class=xl6624425 width=32>序号
    </td>
    <td rowspan=3 class=xl6624425 width=200>单<span
            style='mso-spacerun:yes'>&nbsp; </span>位
    </td>
    <td colspan=4 class=xl6624425 width=272>正处级干部
    </td>
    <td colspan=4 class=xl8524425 width=398>副处级干部
    </td>
    <td colspan=4 class=xl8024425 width=272 style='border-right: 2.0pt double black;'>
      无行政级别干部
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
    <td class=xl7624425 width=57>现任数</td>
    <td class=xl7724425 width=116>现任干部
    </td>
  </tr>

  <c:forEach items="${beans}" var="bean" varStatus="vs">
    <c:if test="${!vs.last}">
      <tr height=40 style='mso-height-source:userset;height:30.0pt'>
        <shiro:hasPermission name="cpcAllocation:edit">
        <td height=40 class=xl6424425 width=32>
          <input type="checkbox" name="unitId" value="${bean.unit.id}"></td>
        </shiro:hasPermission>
        <td height=40 class=xl6424425 width=32>
        ${vs.index+1}</td>
        <td class=xl6924425 width=200>
        ${bean.unit.name}
        </td>
        <td class=xl6424425 width=42>${bean.mainNum}</td>
        <td class=xl6424425 width=57>${bean.mainCount}</td>
        <td class=xl6924425 width=116>
          <mytag:cpc_cadres cadrePosts="${bean.mains}"/>
        </td>
        <td class=xl6824425 width=57>
          <c:if test="${bean.mainLack==0}">0</c:if>
          <c:if test="${bean.mainLack!=0}">
            <span class="badge ${bean.mainLack>0?'badge-success':'badge-danger'}">${bean.mainLack}</span>
          </c:if>
        </td>
        <td class=xl6724425 width=42>${bean.viceNum}</td>
        <td class=xl6424425 width=57>${bean.viceCount}</td>
        <td class=xl6924425 width=242>
          <mytag:cpc_cadres cadrePosts="${bean.vices}"/>
        </td>
        <td class=xl6824425 width=57>
          <c:if test="${bean.viceLack==0}">0</c:if>
          <c:if test="${bean.viceLack!=0}">
            <span class="badge ${bean.viceLack>0?'badge-success':'badge-danger'}">${bean.viceLack}</span>
          </c:if>
        </td>
        <td class=xl7524425 width=42>${bean.noneNum}</td>
        <td class=xl6424425 width=57>${bean.noneCount}</td>
        <td class=xl6924425 width=116>
          <mytag:cpc_cadres cadrePosts="${bean.nones}"/>
        </td>
        <td class=xl6824425 width=57>
          <c:if test="${bean.noneLack==0}">0</c:if>
          <c:if test="${bean.noneLack!=0}">
            <span class="badge ${bean.noneLack>0?'badge-success':'badge-danger'}">${bean.noneLack}</span>
          </c:if>
        </td>
      </tr>
    </c:if>
    <c:if test="${vs.last}">
      <tr height=40>
        <td colspan=${cm:isPermitted("cpcAllocation:edit")?3:2} height=40 class=xl8424425 width=183>合<span
                style='mso-spacerun:yes'>&nbsp; </span>计
        </td>
        <td class=xl6424425 width=42>${bean.mainNum}</td>
        <td class=xl6424425 width=57>${bean.mainCount}</td>
        <td class=xl6924425 width=116>　</td>
        <td class=xl6824425 width=57>${bean.mainLack}</td>
        <td class=xl6724425 width=42>${bean.viceNum}</td>
        <td class=xl6424425 width=57>${bean.viceCount}</td>
        <td class=xl6924425 width=242>　</td>
        <td class=xl6824425 width=57>${bean.viceLack}</td>
        <td class=xl6424425 width=42>${bean.noneNum}</td>
        <td class=xl6424425 width=57>${bean.noneCount}</td>
        <td class=xl6924425 width=116>　
        </td>
        <td class=xl6824425 width=57>${bean.noneLack}</td>
      </tr>
    </c:if>
  </c:forEach>
</table>
