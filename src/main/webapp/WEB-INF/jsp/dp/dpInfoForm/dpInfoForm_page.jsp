<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<style>
<!--

 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	font-size:10.5pt;
	font-family:"Times New Roman",serif;}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:center;
	layout-grid-mode:char;
	border:none;
	padding:0cm;
	font-size:9.0pt;
	font-family:"Times New Roman",serif;}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{margin:0cm;
	margin-bottom:.0001pt;
	layout-grid-mode:char;
	font-size:9.0pt;
	font-family:"Times New Roman",serif;}
p.MsoBodyTextIndent, li.MsoBodyTextIndent, div.MsoBodyTextIndent
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	text-indent:24.0pt;
	font-size:12.0pt;
	font-family:幼圆;}
p
	{margin-right:0cm;
	margin-bottom:2.05pt;
	margin-left:0cm;
	font-size:12.0pt;
	font-family:宋体;}
p.MsoAcetate, li.MsoAcetate, div.MsoAcetate
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	font-size:9.0pt;
	font-family:"Times New Roman",serif;}
p.a, li.a, div.a
	{mso-style-name:默认;
	margin:0cm;
	margin-bottom:.0001pt;
	font-size:11.0pt;
	font-family:"Arial Unicode MS",sans-serif;
	color:black;}
.MsoChpDefault
	{font-size:10.0pt;}
 /* Page Definitions */
 @page WordSection1
	{size:21.0cm 842.0pt;
	margin:3.0cm 70.9pt 70.9pt 70.9pt;
	layout-grid:15.6pt;}
div.WordSection1
	{page:WordSection1;}
-->
    .resume p {
 text-indent: -10em;
 margin: 0 0 0 10em;
}
</style>
<div style="position: absolute; top:25px; left:10px;">
    <a href="javascript:;" class="downloadBtn btn btn-primary"
       data-url="${ctx}/dp/dpInfoForm_download?userId=${param.userId}">
        <i class="ace-icon fa fa-download "></i>
        下载(WORD)
    </a>
</div>
<div>
<div align=left style="float:left;padding-right: 10px;padding-bottom: 20px;">
<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=644 style='border-collapse:collapse;border:none'>
    <tr>
        <td colspan="7" style="border: none">
            <p class=MsoNormal align=center style='text-align:center;padding-bottom:20px'>
<span style='font-size:22.0pt;font-family:方正小标宋简体;letter-spacing:5.0pt'>基本情况登记表</span></p>

        </td>
    </tr>
 <tr style='page-break-inside:avoid;height:32.55pt'>
  <td width=92 style='width:69.95pt;border-top:1.5pt;border-left:1.5pt;
  border-bottom:1.0pt;border-right:1.0pt;border-color:windowtext;border-style:
  solid;padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>姓名</span></p>
  </td>
  <td width=79 style='width:59.15pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center'>${bean.realname}</p>
  </td>
  <td width=92 style='width:69.95pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>性别</span></p>
  </td>
  <td width=98 style='width:73.35pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center'>${GENDER_MAP.get(bean.gender)}</p>
  </td>
  <td width=75 style='width:50pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 14pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>出生年月</span></p>
  </td>
  <td width=88 style='width:66.0pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>${cm:formatDate(bean.birth, "yyyy.MM")}</p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>（<span lang=EN-US>${bean.age}</span>岁）</p>
  </td>
  <td width=130 rowspan=4 style='width:97.85pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <img src="data:image/jpeg;base64,${bean.avatar}" width="110"/>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;height:32.55pt'>
  <td width=92 style='width:69.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>民族</span></p>
  </td>
  <td width=79 style='width:59.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>${bean.nation}</p>
  </td>
  <td width=92 style='width:69.95pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>籍贯</span></p>
  </td>
  <td width=98 style='width:73.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center'>${bean.nativePlace}</p>
  </td>
  <td width=75 style='width:50pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>出生地</span></p>
  </td>
  <td width=88 style='width:66.0pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center'>${bean.homePlace}</p>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;height:32.55pt'>
  <td width=92 style='width:69.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 3pt 0cm 3pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>加入党</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>派时间</span></p>
  </td>
  <td width=79 style='width:59.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>
      <c:if test="${not empty bean.dpGrowTime}">${cm:formatDate(bean.dpGrowTime, "yyyy.MM")}</c:if>
  </p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>
      <c:if test="${not empty bean.dpPartyId}">${bean.dpParty.name}</c:if>
  </p>
  </td>
  <td width=92 style='width:68.95pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 3pt 0cm 3pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>参加工作时间</span></p>
  </td>
  <td width=98 style='width:73.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center'>${cm:formatDate(bean.workTime, "yyyy.MM")}</p>
  </td>
  <td width=75 style='width:50pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 14pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>健康状况</span></p>
  </td>
  <td width=88 style='width:66.0pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>${bean.health}</p>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;height:32.55pt'>
  <td width=92 style='width:69.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 3pt 0cm 3pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
          style='font-size:14.0pt;font-family:宋体'>专业技</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
          style='font-size:14.0pt;font-family:宋体'>术职务</span></p>
  </td>
  <td width=158 colspan=2 style='width:118.25pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'>${bean.proPost}</p>
  </td>
  <td width=98 style='width:73.35pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>熟悉专业有何专长</span></p>
  </td>
  <td width=180 colspan=2 style='width:135.0pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center'>${bean.specialty}</p>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;height:50pt'>
  <td width=92 rowspan=2 style='width:69.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:50pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>学历</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>学位</span></p>
  </td>
  <td width=79 style='width:59.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:50pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>全日制教育</span></p>
  </td>
  <td width=177 colspan=2 style='width:132.45pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:50pt'>
  <p class=MsoNormal align=center style='text-align:center'>${bean.edu} ${bean.degree}</p>
  </td>
  <td width=92 style='width:68.95pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 3pt 0cm 3pt;height:50pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>毕业院校系及专业</span></p>
  </td>
  <td width=219 colspan=2 style='width:163.9pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 3pt 0cm 3pt;height:50pt'>
  <p class=MsoNormal align=center style='text-align:center'>${bean.schoolDepMajor1}${bean.sameSchool?'':'<br/>'}${bean.schoolDepMajor2}</p>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;height:50pt'>
  <td width=79 style='width:59.15pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:50pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>在职<br/>教育</span></p>
  </td>
  <td width=177 colspan=2 style='width:132.45pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:50pt'>
  <p class=MsoNormal align=center style='text-align:center'>${bean.inEdu} ${bean.inDegree}</p>
  </td>
  <td width=92 style='width:68.95pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 3pt 0cm 3pt;height:50pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>毕业院校系及专业</span></p>
  </td>
  <td width=219 colspan=2 style='width:163.9pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 3pt 0cm 3pt;height:50pt'>
  <p class=MsoNormal align=center style='text-align:center'>${bean.inSchoolDepMajor1}${bean.sameInSchool?'':'<br/>'}${bean.inSchoolDepMajor2}</p>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;height:23.55pt'>
  <td width=157 colspan=2 style='width:117.5pt;border-top:none;border-left:
  solid windowtext 1.5pt;border-bottom:solid windowtext 1.0pt;border-right:
  solid windowtext 1.0pt;padding:2pt 5.4pt 2pt 5.4pt;height:23.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>现任职务及<br/>主要社会兼职
</span></p>
  </td>
  <td width=487 colspan=5 style='width:365.35pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:23.55pt'>
  <p class=MsoNormal align=center style='text-align:center'><b>${bean.title};${bean.partTimeJob}</b></p>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;'>
  <td width=92 style='width:69.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:366.05pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>简</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:宋体'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:宋体'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:宋体'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:宋体'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:宋体'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  lang=EN-US style='font-size:14.0pt;font-family:宋体'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>历</span></p>
  </td>
  <td width=566 class="resume" colspan=6 style='width:424.55pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:366.05pt'>
        ${bean.resumeDesc}
  </td>
 </tr>
</table>
</div>

<div style="padding-top:63px;">
<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 align=left
 width=644 style='border-collapse:collapse;border:none;'>
 <tr style='height:70pt'>
  <td width=78 style='width:68.95pt;border-top:1.5pt;border-left:1.5pt;
  border-bottom:1.0pt;border-right:1.0pt;border-color:windowtext;border-style:
  solid;padding:0cm 4pt 0cm 4pt;height:70pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>主要荣誉</span></p>
  </td>
  <td width=550 colspan=5 style='width:412.85pt;border-top:solid windowtext 1.5pt;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:70pt'>
  ${bean.reward}${bean.otherReward}
  </td>
 </tr>
 <tr style='height:40pt'>
  <td width=78 style='width:68.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 4pt 0cm 4pt;height:40pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>年度考核结果</span></p>
  </td>
  <td width=550 colspan=5 style='width:412.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:40pt'>
  ${bean.eva}
  </td>
 </tr>
 <tr style='height:60pt'>
  <td width=78 style='width:68.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 4pt 0cm 4pt;height:60pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>培训情况</span></p>

  </td>
  <td width=550 colspan=5 class="resume" style='width:412.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:60pt'>
  ${bean.trainDesc}
  </td>
 </tr>
    <tr style='height:175.5pt'>
        <td width=78 style='width:68.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 4pt 0cm 4pt;height:175.5pt'>
            <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
                    style='font-size:14.0pt;font-family:宋体'>主要学术成果工作业绩</span></p>

        </td>
        <td width=550 colspan=5 class="resume" style='width:412.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:175.5pt'>
            ${bean.achievements}
        </td>
    </tr>
 <tr style='page-break-inside:avoid;height:32.55pt'>
     <c:set var="_size" value="${fn:length(bean.dpFamilies)}"/>
  <td width=78 align="center" rowspan='${(_size>4?_size:4) + 1}' style='width:68.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
      <div style="width: 28pt;">
      <div style="float:left;width: 14pt;">
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>主要家庭成员</span></p></div>
      <div style="float:left;width: 14pt;margin-top: 11px">
          <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
                  style='font-size:14.0pt;font-family:宋体'>及社会关系</span></p></div>
      </div>
  </td>
  <td width=63 style='width:47.55pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>称 谓</span></p>
  </td>
  <td width=72 style='width:53.7pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>姓 名</span></p>
  </td>
  <td width=64 style='width:48.1pt;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>出生<br/>年月</span></p>
  </td>
  <td width=74 style='width:55.75pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>政治<br/>面貌</span></p>
  </td>
  <td width=220 style='width:200pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
  style='font-size:14.0pt;font-family:宋体'>工 作 单 位 及 职 务</span></p>
  </td>
 </tr>
    <c:forEach items="${bean.dpFamilies}" var="f">
      <tr style='page-break-inside:avoid;height:32.55pt'>
          <td width=63 style='width:47.55pt;border-top:none;border-left:none;
          border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
          padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
          <p class=MsoNormal align=center style='text-align:center'>${cm:getMetaType(f.title).name}</p>
          </td>
          <td width=72 style='width:53.7pt;border-top:none;border-left:none;border-bottom:
          solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;
          height:32.55pt'>
          <p class=MsoNormal align=center style='text-align:center'>${f.realname}</p>
          </td>
          <td width=64 style='width:48.1pt;border-top:none;border-left:none;border-bottom:
          solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;
          height:32.55pt'>
          <p class=MsoNormal align=center style='text-align:center'>
              <c:if test="${f.birthday!=null}">
                ${cm:formatDate(f.birthday, "yyyy.MM")}
              </c:if>
          </p>
          </td>
          <td width=74 style='width:55.75pt;border-top:none;border-left:none;
          border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
          padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
          <p class=MsoNormal align=center style='text-align:center'>${cm:getMetaType(f.getPoliticalStatus()).name}</p>
          </td>
          <td width=220 style='width:200pt;border-top:none;border-left:none;
          border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
          padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
          <p class=MsoNormal align=center style='text-align:center'>${f.unit}</p>
          </td>
     </tr>
    </c:forEach>
    <c:forEach begin="1" end="${4-_size<0?0:(4-_size)}">
      <tr style='page-break-inside:avoid;height:32.55pt'>
          <td width=63 style='width:47.55pt;border-top:none;border-left:none;
          border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
          padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
          </td>
          <td width=72 style='width:53.7pt;border-top:none;border-left:none;border-bottom:
          solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;
          height:32.55pt'>
          </td>
          <td width=64 style='width:48.1pt;border-top:none;border-left:none;border-bottom:
          solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;
          height:32.55pt'>
          </td>
          <td width=74 style='width:55.75pt;border-top:none;border-left:none;
          border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
          padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
          </td>
          <td width=220 style='width:200pt;border-top:none;border-left:none;
          border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
          padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
          </td>
     </tr>
    </c:forEach>
    <tr style='height:32.55pt'>
        <td width=78 style='width:68.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 4pt 0cm 4pt;height:32.55pt'>
            <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
                    style='font-size:14.0pt;font-family:宋体'>通讯地址</span></p>

        </td>
        <td width=259.1 colspan=3 class="resume" style='width:412.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
            ${bean.address}
        </td>
        <td width=78 style='width:68.95pt;border-top:none;border-left:solid windowtext 1.0pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 4pt 0cm 4pt;height:32.55pt'>
            <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
                    style='font-size:14.0pt;font-family:宋体'>邮政编码</span></p>

        </td>
        <td width=259.1 colspan=2 class="resume" style='width:412.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
            ${bean.postalCode}
        </td>
    </tr>
    <tr style='height:32.55pt'>
        <td width=78 style='width:68.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 4pt 0cm 4pt;height:32.55pt'>
            <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
                    style='font-size:14.0pt;font-family:宋体'>联系电话</span></p>

        </td>
        <td width=259.1 colspan=3 class="resume" style='width:412.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
            ${bean.phone}
        </td>
        <td width=78 style='width:68.95pt;border-top:none;border-left:solid windowtext 1.0pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 4pt 0cm 4pt;height:32.55pt'>
            <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
                    style='font-size:14.0pt;font-family:宋体'>电子邮件</span></p>

        </td>
        <td width=259.1 colspan=2 class="resume" style='width:412.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
            ${bean.email}
        </td>
    </tr>
    <tr style='height:32.55pt'>
        <td width=78 style='width:68.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 4pt 0cm 4pt;height:32.55pt'>
            <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
                    style='font-size:14.0pt;font-family:宋体'>手机</span></p>

        </td>
        <td width=259.1 colspan=3 class="resume" style='width:412.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
            ${bean.mobile}
        </td>
        <td width=78 style='width:68.95pt;border-top:none;border-left:solid windowtext 1.0pt;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 4pt 0cm 4pt;height:32.55pt'>
            <p class=MsoNormal align=center style='text-align:center;line-height:15.0pt'><span
                    style='font-size:14.0pt;font-family:宋体'>传真</span></p>

        </td>
        <td width=259.1 colspan=2 class="resume" style='width:412.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
            ${bean.fax}
        </td>
    </tr>
 <tr style='height:32.55pt'>
  <td width=628 colspan="6" style='width:68.95pt;border-top:none;border-left:solid windowtext 1.5pt;
  border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:32.55pt'>
  <p class=MsoNormal align=center style='text-align:left;line-height:15.0pt'><span
  style='font-size:13.0pt;font-family:宋体'>填报单位：（盖章）&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</span>
      <span style='font-size:14.0pt;font-family:宋体'>年 &nbsp;月 &nbsp;日</span></p>
  </td>
 </tr>
</table>
</div>
    </div>
<div class="footer-margin lower"/>
