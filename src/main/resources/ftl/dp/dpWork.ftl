<#list dpWorks as dpWork>
<p>${dpWork.startTime?string("yyyy.MM")}${dpWork.endTime???string("—","—至今&nbsp;")}${(dpWork.endTime?string("yyyy.MM"))!}&nbsp;&nbsp;${dpWork.detail!}</p>
    <#list dpWork.subDpWorks as subDpWork>
    <#if subDpWork_index==0><p style="text-indent: 0em">（其间：</#if><#if subDpWork_index gt 0><p style="text-indent: 0em"></#if>${subDpWork.startTime?string("yyyy.MM")}${subDpWork.endTime???string("—","—至今")}${(subDpWork.endTime?string("yyyy.MM"))!}&nbsp;&nbsp;${subDpWork.detail!}）</p>
</#list>
</#list>