<#list cadreWorks as cadreWork>
<p>${cadreWork.startTime?string("yyyy.MM")}${cadreWork.endTime???string("—","—至今")}${(cadreWork.endTime?string("yyyy.MM"))!}
    &nbsp;${cadreWork.detail!}</p>
    <#list cadreWork.subCadreWorks as subCadreWork>
    <#if subCadreWork_index==0><p style="text-indent: 2em">其间：</#if>
    <#if subCadreWork_index gt 0><p style="text-indent: 5em"></#if>
    ${subCadreWork.startTime?string("yyyy.MM")}${subCadreWork.endTime???string("—","—至今")}${(subCadreWork.endTime?string("yyyy.MM"))!}
        &nbsp;${subCadreWork.detail!}</p>
</#list>
</#list>