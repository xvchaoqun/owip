<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	layout-grid-mode:char;
	punctuation-wrap:simple;
	text-autospace:none;
	font-size:14.0pt;
	font-family:宋体;
	layout-grid-mode:line;}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{mso-style-link:"页眉 字符";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:center;
	layout-grid-mode:char;
	punctuation-wrap:simple;
	text-autospace:none;
	border:none;
	padding:0cm;
	font-size:9.0pt;
	font-family:"Times New Roman",serif;}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{mso-style-link:"页脚 字符";
	margin:0cm;
	margin-bottom:.0001pt;
	layout-grid-mode:char;
	punctuation-wrap:simple;
	text-autospace:none;
	font-size:9.0pt;
	font-family:"Times New Roman",serif;}
p.MsoAcetate, li.MsoAcetate, div.MsoAcetate
	{mso-style-link:"批注框文本 字符";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	layout-grid-mode:char;
	punctuation-wrap:simple;
	text-autospace:none;
	font-size:9.0pt;
	font-family:"Times New Roman",serif;}
span.a
	{mso-style-name:"页眉 字符";
	mso-style-link:页眉;}
span.a0
	{mso-style-name:"页脚 字符";
	mso-style-link:页脚;}
span.a1
	{mso-style-name:"批注框文本 字符";
	mso-style-link:批注框文本;}
.MsoChpDefault
	{font-size:10.0pt;}
 /* Page Definitions */
 @page WordSection1
	{size:21.0cm 842.0pt;
	margin:31.45pt 2.0cm 34.0pt 62.35pt;
	layout-grid:15.6pt;}
div.WordSection1
	{page:WordSection1;}
@page WordSection2
	{size:21.0cm 842.0pt;
	margin:81.05pt 2.0cm 1.0cm 59.55pt;
	layout-grid:15.6pt;}
div.WordSection2
	{page:WordSection2;}
-->
</style>
<div style="position: absolute; top:25px; left:10px;">
    <a href="javascript:;" class="downloadBtn btn btn-primary"
       data-url="${ctx}/partyMemberInfoForm_download?cadreId=${param.cadreId}&branchId=${param.branchId}">
        <i class="ace-icon fa fa-download "></i>
        下载(WORD)
    </a>
</div>
<div align=left style="float:left;padding-right: 10px;padding-bottom: 20px;">

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0
 style='margin-left:116.2pt;border-collapse:collapse'>
 <tr>
  <td width=644 valign=bottom style='width:330.35pt;padding:0cm 0cm 0cm 0cm'>
  <p class=MsoNormal style='margin-top:10.9pt;margin-right:0cm;margin-bottom:
  1.55pt;margin-left:0cm;vertical-align:top'><span style='font-size:24.0pt'>党支部书记信息采集表</span></p>
  </td>
 </tr>
</table>


<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=644
 style='margin-left:.75pt;border-collapse:collapse;border:none'>
 <tr style='height:35.45pt'>
  <td width=68 colspan=2 style='width:51.0pt;border-top:1.5pt;border-left:1.5pt;
  border-bottom:1.0pt;border-right:1.0pt;border-color:windowtext;border-style:
  solid;padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>姓<span
  lang=EN-US>&nbsp; </span>名</p>
  </td>
  <td width=87 style='width:65.25pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><b>${bean.realname}</b></p>
  </td>
  <td width=71 style='width:53.15pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>性<span
  lang=EN-US>&nbsp; </span>别</p>
  </td>
  <td width=87 style='width:65.1pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><a
  name="A0104_2"></a><b>${GENDER_MAP.get(bean.gender)}</b></p>
  </td>
  <td width=88 style='width:65.75pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>出生年月</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><span
  lang=EN-US>(</span>岁<span lang=EN-US>)</span></p>
  </td>
  <td width=89 style='width:66.5pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:35.45pt'>
   <b>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>${cm:formatDate(bean.birth, "yyyy.MM")}</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>（<span lang=EN-US>${bean.age}</span>岁）</p>
    </b>
  </td>
  <td width=137 rowspan=4 style='width:102.9pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center'><a name="P0192A_12"></a><img src="data:image/jpeg;base64,${bean.avatar}" width="110"/></p>
  </td>
</tr>
 <tr style='height:34.6pt'>
  <td width=68 colspan=2 style='width:51.0pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:34.6pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>民<span
  lang=EN-US>&nbsp; </span>族</p>
  </td>
  <td width=87 style='width:65.25pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:34.6pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><a
  name="A0117_4"></a><b>${bean.nation}</b></p>
  </td>
  <td width=71 style='width:53.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:34.6pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>籍<span
  lang=EN-US>&nbsp; </span>贯</p>
  </td>
  <td width=87 style='width:65.1pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;
  height:34.6pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><b>${bean.nativePlace}</b></p>
  </td>
  <td width=88 style='width:65.75pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:34.6pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>出
  生 地</p>
  </td>
  <td width=89 style='width:66.5pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;
  height:34.6pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><a
  name="A0114_6"></a><b>${bean.homeplace}</b></p>
  </td>

 </tr>
 <tr style='height:34.0pt'>
  <td width=68 colspan=2 style='width:51.0pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:34.0pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>入<span
  lang=EN-US>&nbsp; </span>党</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>时<span
  lang=EN-US>&nbsp; </span>间</p>
  </td>
  <td width=87 style='width:65.25pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:34.0pt'>
   <b>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>
      <c:if test="${bean.dpTypeId>0}">
                <c:if test="${not empty bean.owGrowTime}">${cm:formatDate(bean.owGrowTime, "yyyy.MM")}；</c:if>${cm:getMetaType(bean.dpTypeId).extraAttr}<c:forEach items="${bean.dpParties}" var="dpParty">；${cm:getMetaType(dpParty.classId).extraAttr}</c:forEach>
            </c:if>
            <c:if test="${empty bean.dpTypeId}">
                <c:if test="${not empty bean.owGrowTime}">
                    ${cm:formatDate(bean.owGrowTime, "yyyy.MM")}
                </c:if>
            </c:if>
  </p>
   </b>
  </td>
  <td width=71 style='width:53.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:34.0pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>参加工</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>作时间</p>
  </td>
  <td width=87 style='width:65.1pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;
  height:34.0pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><a
  name="A0134_8"></a><b>${cm:formatDate(bean.workTime, "yyyy.MM")}</b></p>
  </td>
  <td width=88 style='width:65.75pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:34.0pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><span
  style='font-size:12.0pt'>一线教师党支部书记</span></p>
  </td>
  <td width=89 style='width:66.5pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;
  height:34.0pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><a
  name="A0127_9"></a><b>${bean.isPrefessionalSecretary?"是":"否"}</b></p>
  </td>
 </tr>
 <tr style='height:35.45pt'>
  <td width=68 colspan=2 style='width:51.0pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>专业技</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>术职务</p>
  </td>
  <td width=158 colspan=2 style='width:118.4pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><a
  name="A0125_10"></a><b>${bean.proPost}</b></p>
  </td>
  <td width=87 style='width:65.1pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;
  height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>熟悉专业</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>有何专长</p>
  </td>
  <td width=176 colspan=2 style='width:132.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'><a
  name="A0187A_11"></a><b>${bean.specialty}</b></p>
  </td>

 </tr>
 <tr style='height:17.3pt'>
  <td width=68 colspan=2 rowspan=2 style='width:51.0pt;border-top:none;
  border-left:solid windowtext 1.5pt;border-bottom:solid windowtext 1.0pt;
  border-right:solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;height:17.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'>学
  历</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt'>学
  位</p>
  </td>
  <td width=87  style='width:65.25pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:17.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>全日制</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>教<span
  lang=EN-US>&nbsp; </span>育</p>
  </td>
  <td width=158 colspan=2  style='width:118.25pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:17.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><b>${bean.edu} ${bean.degree}</b></p>
  </td>
  <td width=88 style='width:65.75pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:17.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>毕业院校</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>系及专业</p>
  </td>
  <td width=226 colspan=2 style='width:169.4pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 4.25pt 0cm 4.25pt;height:17.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><b>
   ${bean.schoolDepMajor1}${bean.sameSchool?'':'<br/>'}${bean.schoolDepMajor2}</b></p>
  </td>
</tr>

 <tr style='height:17.3pt'>
  <td width=87 style='width:65.25pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:17.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>在<span
  lang=EN-US>&nbsp; </span>职</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>教<span
  lang=EN-US>&nbsp; </span>育</p>
  </td>
  <td width=158 colspan=2  style='width:118.25pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:17.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><b>
   ${bean.inEdu} ${bean.inDegree}</b></p>
  </td>
  <td width=88 style='width:65.75pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:17.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>毕业院校</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>系及专业</p>
  </td>
  <td width=226 colspan=2  style='width:169.4pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 4.25pt 0cm 4.25pt;height:17.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><b>
   ${bean.inSchoolDepMajor1}${bean.sameInSchool?'':'<br/>'}${bean.inSchoolDepMajor2}</b></p>
  </td>
</tr>

 <tr style='height:35.45pt'>
  <td width=155 colspan=3 style='width:116.25pt;border-top:none;border-left:
  solid windowtext 1.5pt;border-bottom:solid windowtext 1.0pt;border-right:
  solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>所在党支部</p>
  </td>
  <td width=471 colspan=5 style='width:353.4pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 0cm 0cm 4.25pt;height:35.45pt'>
  <p class=MsoNormal style='line-height:15.0pt'><a name="A0215_17"></a><b>${bean.branchName}</b></p>
  </td>
 </tr>
 <tr style='height:43.0pt'>
  <td width=155 colspan=3 style='width:116.25pt;border-top:none;border-left:
  solid windowtext 1.5pt;border-bottom:solid windowtext 1.0pt;border-right:
  solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;height:43.0pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>任现职时间</p>
  </td>
  <td width=471 colspan=5 style='width:353.4pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 0cm 0cm 4.25pt;height:43.0pt'>
  <p class=MsoNormal style='line-height:15.0pt'><a name="RMZW_19"></a><b>${bean.assignDate}</b></p>
  </td>
</tr>
 <tr style='height:35.45pt'>
  <td width=155 colspan=3 style='width:116.25pt;border-top:none;border-left:
  solid windowtext 1.5pt;border-bottom:solid windowtext 1.0pt;border-right:
  solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>行政职务</p>
  </td>
  <td width=471 colspan=5 style='width:353.4pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 0cm 0cm 4.25pt;height:35.45pt'>
  <p class=MsoNormal style='line-height:15.0pt'><b>${bean.title}</b></p>
  </td>
</tr>
 <tr style='height:35.45pt'>
  <td width=155 colspan=3 style='width:116.25pt;border-top:none;border-left:
  solid windowtext 1.5pt;border-bottom:solid windowtext 1.0pt;border-right:
  solid windowtext 1.0pt;padding:0cm 0cm 0cm 0cm;height:35.45pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>联系方式</p>
  </td>
  <td width=471 colspan=5 style='width:353.4pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 0cm 0cm 4.25pt;height:35.45pt'>
  <p class=MsoNormal style='line-height:15.0pt'><a name="RMZW_18"></a><b>${bean.mobile}</b></p>
  </td>
</tr>
 <tr style='page-break-inside:avoid;height:356.65pt'>
  <td width=52 style='width:39.2pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:356.65pt'>
  <p class=MsoNormal align=center style='text-align:center'>简</p>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center'>历</p>
  </td>
  <td width=574 colspan=7 style='border-top:none;border-left:
  none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.65pt 0cm 5.65pt;height:356.65pt'><p class="MsoNormal" style="margin-left:110.0pt;text-indent:-110.0pt;
  line-height:18.0pt"><strong>${bean.resume}</strong></p></td>
</tr>

</table>

</div>

<div style="padding-top:65px;">

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=644
 style='margin-left:.75pt;border-collapse:collapse;border:none'>
 <tr style='page-break-inside:avoid;height:143.3pt'>
  <td width=52 style='width:37.1pt;border-top:1.5pt;border-left:1.5pt;
  border-bottom:1.0pt;border-right:1.0pt;border-color:windowtext;border-style:
  solid;padding:0cm 0cm 0cm 0cm;height:143.3pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>党内</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>奖惩</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>情况</p>
  </td>
  <td width=574 style='border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.65pt 0cm 5.65pt;height:143.3pt'><p class="MsoNormal" style="margin-left:110.0pt;text-indent:-110.0pt;
  line-height:18.0pt"><strong>${bean.reward}</strong></p></td>
 </tr>
 <tr style='page-break-inside:avoid;height:105.85pt'>
  <td width=52 style='width:37.1pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  padding:0cm 0cm 0cm 0cm;height:105.85pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>年核</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>度结</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:16.0pt'>考果</p>
  </td>
  <td width=574 style='border-top:none;border-left:none;
  border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.65pt 0cm 5.65pt;height:105.85pt'><p class="MsoNormal" style="margin-left:110.0pt;text-indent:-110.0pt;
  line-height:18.0pt"><strong>${bean.ces}</strong></p></td>
 </tr>
</table>
<%--<p class=MsoNormal style='margin-top:7.8pt;text-indent:21.7pt;line-height:16.0pt'>填表人：管理员</p>--%>
</div>
<div class="footer-margin lower"/>