<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:宋体;
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
	{font-family:Calibri;
	panose-1:2 15 5 2 2 2 4 3 2 4;}
@font-face
	{font-family:方正小标宋简体;}
@font-face
	{font-family:仿宋_GB2312;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:"\@方正小标宋简体";}
@font-face
	{font-family:"\@仿宋_GB2312";}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	font-size:10.5pt;
	font-family:"Calibri",sans-serif;}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{mso-style-link:"页眉 字符";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:center;
	layout-grid-mode:char;
	border:none;
	padding:0cm;
	font-size:9.0pt;
	font-family:"Calibri",sans-serif;}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{mso-style-link:"页脚 字符";
	margin:0cm;
	margin-bottom:.0001pt;
	layout-grid-mode:char;
	font-size:9.0pt;
	font-family:"Calibri",sans-serif;}
span.a
	{mso-style-name:"页眉 字符";
	mso-style-link:页眉;}
span.a0
	{mso-style-name:"页脚 字符";
	mso-style-link:页脚;}
.MsoChpDefault
	{font-family:"Calibri",sans-serif;}
 /* Page Definitions */
 @page WordSection1
	{size:595.3pt 841.9pt;
	margin:72.0pt 90.0pt 72.0pt 90.0pt;
	layout-grid:15.6pt;}
div.WordSection1
	{page:WordSection1;}
-->
</style>
<c:if test="${param.mobile=='1'}">
    <button type="button"
            style="padding: 15px; position: absolute;right: 0;top: -10px;z-index: 2222"
            data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
</c:if>
<div id="preview">
<div style="float: left; margin-right: 20px; padding-bottom: 20px;">
  <table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0 width=651
 style='width:488.3pt;border-collapse:collapse;border:none; float:left;'>
 <tr>
 <td colspan="12" style="border:none">
 <p class=MsoNormal align=center style='margin-top:7.8pt;margin-right:0cm;
margin-bottom:0pt;margin-left:0cm;text-align:center'><span style='font-size:
22.0pt;font-family:方正小标宋简体;letter-spacing:5.0pt'>干部任免审批表</span></p>
 </td>
 </tr>
 <tr style='height:31.45pt'>
  <td width=79 style='width:59.1pt;border-top:1.5pt;border-left:1.5pt;
  border-bottom:1.0pt;border-right:1.0pt;border-color:windowtext;border-style:
  solid;padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>姓</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>名</span></p>
  </td>
  <td width=91 colspan=2 style='width:68.25pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.realname}</span></p>
  </td>
  <td width=76 colspan=2 style='width:2.0cm;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>性</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>别</span></p>
  </td>
  <td width=94 colspan=2 style='width:70.85pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${GENDER_MAP.get(bean.gender)}</span></p>
  </td>
  <td width=95 colspan=3 style='width:70.9pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>出生年月</span><span
  lang=EN-US style='font-size:11.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>(&nbsp;&nbsp; </span><span style='font-size:11.0pt;
  font-family:仿宋_GB2312;color:windowtext'>岁</span><span lang=EN-US
  style='font-size:11.0pt;font-family:"Times New Roman",serif;color:windowtext'>)</span></p>
  </td>
  <td width=94 style='width:70.35pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${cm:formatDate(bean.birth, "yyyy.MM")}</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-family:仿宋_GB2312;color:windowtext'>（</span><span lang=EN-US
  style='font-family:"Times New Roman",serif;color:windowtext'>${bean.age}</span><span
  style='font-family:仿宋_GB2312;color:windowtext'>岁）</span></p>
  </td>
  <td width=123 rowspan=4 style='width:92.15pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:16.0pt;font-family:"Times New Roman",serif;color:windowtext;
  letter-spacing:5.0pt'>
    <img src="data:image/jpeg;base64,${bean.avatar}" width="110"/>
  </span></p>
  </td>
 </tr>
 <tr style='height:31.45pt'>
  <td width=79 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>民</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>族</span></p>
  </td>
  <td width=91 colspan=2 style='width:68.25pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.nation}</span></p>
  </td>
  <td width=76 colspan=2 style='width:2.0cm;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>籍</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>贯</span></p>
  </td>
  <td width=94 colspan=2 style='width:70.85pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.nativePlace}</span></p>
  </td>
  <td width=95 colspan=3 style='width:70.9pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>出</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>生</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>地</span></p>
  </td>
  <td width=94 style='width:70.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.homeplace}</span></p>
  </td>
 </tr>
 <tr style='height:31.45pt'>
  <td width=79 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>入</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>党</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>时</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>间</span></p>
  </td>
  <td width=91 colspan=2 style='width:68.25pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>
       <c:if test="${bean.dpTypeId>0}">
          <c:if test="${not empty bean.owGrowTime}">${cm:formatDate(bean.owGrowTime, "yyyy.MM")}；</c:if>${cm:getMetaType(bean.dpTypeId).extraAttr}
        </c:if>
        <c:if test="${empty bean.dpTypeId}">
          <c:if test="${not empty bean.owGrowTime}">
            ${cm:formatDate(bean.owGrowTime, "yyyy.MM")}
          </c:if>
        </c:if>
  </span></p>
  </td>
  <td width=76 colspan=2 style='width:2.0cm;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext;white-space:nowrap'>参加工</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>作时间</span></p>
  </td>
  <td width=94 colspan=2 style='width:70.85pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${cm:formatDate(bean.workTime, "yyyy.MM")}</span></p>
  </td>
  <td width=95 colspan=3 style='width:70.9pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext;white-space:nowrap'>健康状况</span></p>
  </td>
  <td width=94 style='width:70.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'> ${bean.health}</span></p>
  </td>
 </tr>
 <tr>
  <td width=79 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>专业技</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>术职务</span></p>
  </td>
  <td width=167 colspan=4 style='width:124.95pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.proPost}</span></p>
  </td>
  <td width=94 colspan=2 style='width:70.85pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext;white-space:nowrap'>熟悉专业</span></p>
    <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>有何专长</span></p>
  </td>
  <td width=188 colspan=4 style='width:141.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.specialty}</span></p>
  </td>
 </tr>
 <tr style='height:31.45pt'>
  <td width=79 rowspan=2 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>学</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>历</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>学</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>位</span></p>
  </td>
  <td width=91 colspan=2 style='width:68.25pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext;white-space:nowrap'>全日制</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>教</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>育</span></p>
  </td>
  <td width=170 colspan=4 style='width:127.55pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>
   ${bean.edu}
        <br/>
        ${bean.degree}
  </span></p>
  </td>
  <td width=95 colspan=3 style='width:70.9pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>毕业院校系及专业</span></p>
  </td>
  <td width=217 colspan=2 style='width:162.5pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.schoolDepMajor1}${bean.sameSchool?'':'<br/>'}${bean.schoolDepMajor2}</span></p>
  </td>
 </tr>
 <tr style='height:31.45pt'>
  <td width=91 colspan=2 style='width:68.25pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>在</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>职</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>教</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>育</span></p>
  </td>
  <td width=170 colspan=4 style='width:127.55pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>
   ${bean.inEdu}
        <br/>
        ${bean.inDegree}
  </span></p>
  </td>
  <td width=95 colspan=3 style='width:70.9pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>毕业院校系及专业</span></p>
  </td>
  <td width=217 colspan=2 style='width:162.5pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.inSchoolDepMajor1}${bean.sameInSchool?'':'<br/>'}${bean.inSchoolDepMajor2}</span></p>
  </td>
 </tr>
 <tr style='height:31.45pt'>
  <td width=170 colspan=3 style='width:127.35pt;border-top:none;border-left:
  solid windowtext 1.5pt;border-bottom:solid windowtext 1.0pt;border-right:
  solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>现</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>任</span><span lang=EN-US style='font-size:14.0pt;
  font-family:"Times New Roman",serif;color:windowtext'>&nbsp; </span><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>职</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>务</span></p>
  </td>
  <td width=481 colspan=9 style='width:360.95pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.post}</span></p>
  </td>
 </tr>
 <tr style='height:31.45pt'>
  <td width=170 colspan=3 style='width:127.35pt;border-top:none;border-left:
  solid windowtext 1.5pt;border-bottom:solid windowtext 1.0pt;border-right:
  solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>拟</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>任</span><span lang=EN-US style='font-size:14.0pt;
  font-family:"Times New Roman",serif;color:windowtext'>&nbsp; </span><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>职</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>务</span></p>
  </td>
  <td width=481 colspan=9 style='width:360.95pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.inPost}</span></p>
  </td>
 </tr>
 <tr style='height:31.45pt'>
  <td width=170 colspan=3 style='width:127.35pt;border-top:none;border-left:
  solid windowtext 1.5pt;border-bottom:solid windowtext 1.0pt;border-right:
  solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>拟</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>免</span><span lang=EN-US style='font-size:14.0pt;
  font-family:"Times New Roman",serif;color:windowtext'>&nbsp; </span><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>职</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>务</span></p>
  </td>
  <td width=481 colspan=9 style='width:360.95pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.prePost}</span></p>
  </td>
 </tr>
 <tr style='height:338.95pt'>
  <td width=79 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:338.95pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>简</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>历</span></p>
  </td>
  <td width=572 colspan=11 style='width:429.2pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:338.95pt'>
  ${bean.resumeDesc}
  </td>
 </tr>

 </table>
</div>

<div style="padding-top: 53px" class="clearfix">
  <table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0 width=651
 style='width:488.3pt;border-collapse:collapse;border:none;float:left;'>
 <tr style='height:77.5pt'>
  <td width=79 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:77.5pt;border-top: solid windowtext 1.5pt;'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>奖</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>惩</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>情</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>况</span></p>
  </td>
  <td width=572 colspan=11 style='width:429.2pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;border-top: solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:77.5pt'>
  <p class=MsoNormal align=left style='text-align:left;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>${bean.reward}</span></p>
  </td>
 </tr>
 <tr style='height:63.7pt'>
  <td width=79 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:63.7pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>年核</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>度结</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>考果</span></p>
  </td>
  <td width=572 colspan=11 style='width:429.2pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:63.7pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${bean.ces}</span></p>
  </td>
 </tr>
 <tr style='height:69.55pt'>
  <td width=79 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:69.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>任</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>免</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>理</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>由</span></p>
  </td>
  <td width=572 colspan=11 style='width:429.2pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:69.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>&nbsp;</span></p>
  </td>
 </tr>
 <tr>
  <td width=79 rowspan=8 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>家</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>庭</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>主</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>要</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>成</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>员</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>及</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>重</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>要</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>社</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>会</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>关</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>系</span></p>
  </td>
  <td width=72 style='width:54.05pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>称谓</span></p>
  </td>
  <td width=85 colspan=2 style='width:63.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>姓名</span></p>
  </td>
  <td width=76 colspan=2 style='width:2.0cm;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>出生</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>年月</span></p>
  </td>
  <td width=76 colspan=2 style='width:2.0cm;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>政</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>治</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>面</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>貌</span></p>
  </td>
  <td width=264 colspan=4 style='width:197.95pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>工</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>作</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>单</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>位</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>及</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>职</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>务</span></p>
  </td>
 </tr>
   <c:forEach items="${bean.cadreFamilys}" var="f">
 <tr style='height:31.45pt'>
  <td width=72 style='width:54.05pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${cm:getMetaType(f.title).name}</span></p>
  </td>
  <td width=85 colspan=2 style='width:63.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${f.realname}</span></p>
  </td>
  <td width=76 colspan=2 style='width:2.0cm;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'><c:if test="${f.birthday!=null}">
            ${cm:formatDate(f.birthday, "yyyy.MM")}
          </c:if></span></p>
  </td>
  <td width=76 colspan=2 style='width:2.0cm;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 7.4pt 0cm 7.4pt;height:31.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${cm:getMetaType(f.getPoliticalStatus()).name}</span></p>
  </td>
  <td width=264 colspan=4 style='width:197.95pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
  <p class=MsoNormal align=left style='text-align:left;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>${f.unit}</span></p>
  </td>
 </tr>
    </c:forEach>
   <c:if test="${fn:length(bean.cadreFamilys)<=6}">
   <c:forEach begin="0" end="${6-fn:length(bean.cadreFamilys)}">
      <tr style='height:31.45pt'>
     <td width=72 style='width:54.05pt;border-top:none;border-left:none;
     border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
     padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
     <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
     lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
     color:windowtext'></span></p>
     </td>
     <td width=85 colspan=2 style='width:63.8pt;border-top:none;border-left:none;
     border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
     padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
     <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
     lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
     color:windowtext'></span></p>
     </td>
     <td width=76 colspan=2 style='width:2.0cm;border-top:none;border-left:none;
     border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
     padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
     <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
     lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
     color:windowtext'></span></p>
     </td>
     <td width=76 colspan=2 style='width:2.0cm;border-top:none;border-left:none;
     border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
     padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
     <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
     lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
     color:windowtext'></span></p>
     </td>
     <td width=264 colspan=4 style='width:197.95pt;border-top:none;border-left:
     none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
     padding:0cm 5.4pt 0cm 5.4pt;height:31.45pt'>
     <p class=MsoNormal align=left style='text-align:left;line-height:15.0pt'><span
     lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
     color:windowtext'></span></p>
     </td>
    </tr>
    </c:forEach>
   </c:if>
 <tr style='height:75.05pt'>
  <td width=79 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:75.05pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>呈</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>报</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>单</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>位</span></p>
  </td>
  <td width=572 colspan=11 style='width:429.2pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:75.05pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=right style='text-align:right;line-height:15.0pt;
  word-break:break-all'><span lang=EN-US style='font-size:14.0pt;font-family:
  "Times New Roman",serif;color:windowtext'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>${cm:formatDate(bean.reportDate, "yyyy年MM月dd日")}</span></p>
  </td>
 </tr>
 <tr>
  <td width=79 style='width:59.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>审</span><span
  style='font-size:30.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>意</span></p>
  <p class=MsoNormal align=center style='margin-left:-21.0pt;text-align:center;
  line-height:15.0pt'><span style='font-size:14.0pt;font-family:仿宋_GB2312;
  color:windowtext'>批</span></p>
  <p class=MsoNormal align=center style='margin-left:-21.0pt;text-align:center;
  line-height:15.0pt'><span style='font-size:14.0pt;font-family:仿宋_GB2312;
  color:windowtext'>机</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>关</span><span
  style='font-size:30.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>见</span></p>
  </td>
  <td width=233 colspan=5 style='width:174.55pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (</span><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>盖章</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>)</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>年</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>月</span><span lang=EN-US style='font-size:14.0pt;
  font-family:"Times New Roman",serif;color:windowtext'>&nbsp; </span><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>日</span></p>
  </td>
  <td width=85 colspan=3 style='width:63.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>行</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>任</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>政</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>免</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>机</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>意</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>关</span><span
  style='font-size:14.0pt;font-family:"Times New Roman",serif;color:windowtext'>
  </span><span style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>见</span></p>
  </td>
  <td width=254 colspan=3 style='width:190.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (</span><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>盖章</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>)</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>年</span><span
  lang=EN-US style='font-size:14.0pt;font-family:"Times New Roman",serif;
  color:windowtext'>&nbsp; </span><span style='font-size:14.0pt;font-family:
  仿宋_GB2312;color:windowtext'>月</span><span lang=EN-US style='font-size:14.0pt;
  font-family:"Times New Roman",serif;color:windowtext'>&nbsp; </span><span
  style='font-size:14.0pt;font-family:仿宋_GB2312;color:windowtext'>日</span></p>
  </td>
 </tr>
 <tr>
 <td colspan="12" style='border:none'>
 <p class=MsoNormal><span style='font-size:14.0pt;font-family:仿宋_GB2312'>填表人：</span></p>
 </td>
 </tr>
 <tr height=0>
  <td width=79 style='border:none'></td>
  <td width=72 style='border:none'></td>
  <td width=19 style='border:none'></td>
  <td width=66 style='border:none'></td>
  <td width=9 style='border:none'></td>
  <td width=66 style='border:none'></td>
  <td width=28 style='border:none'></td>
  <td width=47 style='border:none'></td>
  <td width=9 style='border:none'></td>
  <td width=38 style='border:none'></td>
  <td width=94 style='border:none'></td>
  <td width=123 style='border:none'></td>
 </tr>
</table>
</div>
</div>
<c:if test="${param.mobile!='1'}">
  <div class="footer-margin lower"/>
</c:if>
<c:if test="${param.mobile=='1'}">
  <script>
    window.setTimeout(function() {
      //console.log($("#preview table").width())
      //console.log(window.screen.availWidth)
      var r = (window.screen.availWidth-20) / $("#preview table").width();
      $("#preview").css("-webkit-transform","scale(" + r + ")")
              .css("transform-origin","left top").css("padding", "20px");
      //console.log($("#body-content-view").height())
      //console.log($("#preview").height())
      $("#preview").css("margin-bottom", -1*$("#preview").height()*(1-r) + "px");
      //$("#btn-scroll-up").click();
      $(".modal-dialog").css("margin", "0")
    }, 400);

  </script>
</c:if>