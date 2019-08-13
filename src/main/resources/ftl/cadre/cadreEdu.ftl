<#list cadreEdus as cadreEdu>
<p>${cadreEdu.enrolTime?string("yyyy.MM")}${cadreEdu.finishTime???string("—","—至今")}${(cadreEdu.finishTime?string("yyyy.MM"))!}&nbsp;&nbsp;${cadreEdu.school!}${cadreEdu.dep!}<#if cadreEdu.major?? && cadreEdu.major?trim!=''>${cadreEdu.major?ensure_ends_with("专业")}</#if><@eduSuffix eduId="${cadreEdu.eduId!}"/><#if cadreEdu.note?? && cadreEdu.note?trim!=''>（${cadreEdu.note?trim}）</#if></p>
    <#list cadreEdu.subCadreWorks as subCadreWork>
    <#if subCadreWork_index==0><p style="text-indent: 0em">其间：</#if><#if subCadreWork_index gt 0><p style="text-indent: 0em"></#if>${subCadreWork.startTime?string("yyyy.MM")}${subCadreWork.endTime???string("—","—至今")}${(subCadreWork.endTime?string("yyyy.MM"))!}&nbsp;&nbsp;${subCadreWork.detail!}</p>
</#list>
</#list>