<#list cadreWorks as cadreWork>
<p>${cadreWork.startTime?string("yyyy.MM")}${cadreWork.endTime???string("-","-至今")}${(cadreWork.endTime?string("yyyy.MM"))!}
    &nbsp;${cadreWork.unit}${cadreWork.post}</p>
    <#list cadreWork.subCadreWorks as subCadreWork>
    <#if subCadreWork_index==0><p style="text-indent: 2em">期间：</#if>
    <#if subCadreWork_index gt 0><p style="text-indent: 5em"></#if>
    ${subCadreWork.startTime?string("yyyy.MM")}${subCadreWork.endTime???string("-","-至今")}${(subCadreWork.endTime?string("yyyy.MM"))!}
        &nbsp;${subCadreWork.unit}${subCadreWork.post}</p>
</#list>
</#list>