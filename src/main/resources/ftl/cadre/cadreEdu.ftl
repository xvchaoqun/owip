<#list cadreEdus as cadreEdu>
<p>${cadreEdu.enrolTime?string("yyyy.MM")}${cadreEdu.finishTime???string("-","-至今")}${(cadreEdu.finishTime?string("yyyy.MM"))!}&nbsp;${cadreEdu.school!}${cadreEdu.dep!}<#if cadreEdu.major??>${cadreEdu.major?ensure_ends_with("专业")}</#if><@eduSuffix eduId="${cadreEdu.eduId!}"/></p>
</#list>