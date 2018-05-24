<#list cadreResumes as cadreResume>
<p>${cadreResume.startDate?string("yyyy.MM")}${cadreResume.endDate???string("-","-")}${(cadreResume.endDate?string("yyyy.MM"))!}
&nbsp;${cadreResume.detail!}<#if cadreResume.containResumes?? && (cadreResume.containResumes?size>0)>（其间：<#list cadreResume.containResumes as subResume>
        ${subResume.startDate?string("yyyy.MM")}${subResume.endDate???string("-","-")}${(subResume.endDate?string("yyyy.MM"))!}
        &nbsp;${subResume.detail!}<#if subResume_has_next>；</#if></#list>）
    </#if>
    <#if  cadreResume.overlapResumes?? && (cadreResume.overlapResumes?size>0) >
        <br/>（<#list cadreResume.overlapResumes as subResume>${subResume.startDate?string("yyyy.MM")}${subResume.endDate???string("-","-")}${(subResume.endDate?string("yyyy.MM"))!}
            &nbsp;${subResume.detail!}<#if subResume_has_next>；</#if></#list>）
    </#if>
</p>
</#list>