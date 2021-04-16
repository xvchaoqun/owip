<#list dataList as row>
    <w:p w:rsidR="00466809" w:rsidRDefault="003B1A40">
        <w:pPr>
            <w:rPr>
                <w:rFonts w:ascii="宋体"/>
                <w:sz w:val="24"/>
            </w:rPr>
        </w:pPr>
         <#list row as col>
            <#if col_index!=0>
                <w:r>
                    <w:rPr>
                        <w:rFonts w:ascii="宋体" w:hint="eastAsia"/>
                        <w:sz w:val="24"/>
                    </w:rPr>
                    <w:t xml:space="preserve"><#if needWhiteSpace?? && needWhiteSpace>    </#if>${col}<#if col_has_next>  </#if></w:t>
                </w:r>
            </#if>
         </#list>
    </w:p>
</#list>
