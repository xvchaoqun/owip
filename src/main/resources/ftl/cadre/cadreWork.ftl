<#list cadreWorks as cadreWork>
<p>${cadreWork.startTime?string("yyyy.MM")}${cadreWork.endTime???string("—","—至今&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")}${(cadreWork.endTime?string("yyyy.MM"))!}&nbsp;&nbsp;${cadreWork.detail!}<#if cadreWork.note?? && cadreWork.note?trim!=''>（${cadreWork.note?trim}）</#if></p>
    <#list cadreWork.subCadreWorks as subCadreWork>
    <#if subCadreWork_index==0><p style="text-indent: 0em">其间：</#if><#if subCadreWork_index gt 0><p style="text-indent: 0em"></#if>${subCadreWork.startTime?string("yyyy.MM")}${subCadreWork.endTime???string("—","—至今")}${(subCadreWork.endTime?string("yyyy.MM"))!}&nbsp;&nbsp;${subCadreWork.detail!}</p>
</#list>
</#list>