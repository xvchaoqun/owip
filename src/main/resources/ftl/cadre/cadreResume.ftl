<#list cadreResumes as cadreResume>
<p>${cadreResume.startDate?string("yyyy.MM")}${cadreResume.endDate???string("-","-")}${(cadreResume.endDate?string("yyyy.MM"))!}
&nbsp;${cadreResume.detail!}
    <#if (cadreResume.resumes?? && cadreResume.resumes?size>0)>
        <#list cadreResume.resumes as f>
            <#if f_index == 0>
                <#assign firstResume=f/>
            </#if>
        </#list>
        <#if (cadreResume.resumes?size==1) && (firstResume.startDate?date lt cadreResume.startDate?date) >
            <br/>
        </#if>
        （
            <#if (cadreResume.resumes?size==1) && (firstResume.startDate?date gte cadreResume.startDate?date) >
                其间：
            </#if>
            <#if (cadreResume.resumes?size>1)>其间：</#if>
            <#list cadreResume.resumes as subResume>
            ${subResume.startDate?string("yyyy.MM")}${subResume.endDate???string("-","-")}${(subResume.endDate?string("yyyy.MM"))!}
            &nbsp;${subResume.detail!}<#if subResume_has_next>；</#if>
            </#list>
        ）
    </#if>
</p>
</#list>