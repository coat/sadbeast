<#import "layout.ftl" as layout>
<@layout.main "Home">
<#if errors??>
<p style="color:#a94442">${msg.getString("post.error")}</p>
</#if>
<form method="post" action="/">
    <textarea name="post" class="u-full-width" placeholder="${msg.getString("post.placeholder")}" maxlength="1024"<#if errors??> style="border-color:#a94442"</#if>>${post}</textarea>
    <input class="button-primary" type="submit" value="${msg.getString("post.label")}" />
</form>
<#list posts as post>
<div class="row post<#if post?item_parity == 'even'> stripe</#if>">
    <div class="ten columns">
        ${post.content}
    </div>
    <div class="two columns date">
        ${post.created?date}
    </div>
</div>
<#if post_index == 19>
<div class="row">
    <div class="twelve columns" style="padding:1em 0">
        <a class="u-pull-right" href="/?prev=${post.index}">more</a>
    </div>
</div>
</#if>
</#list>
</@layout.main>