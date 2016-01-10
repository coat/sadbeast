<#import "layout.ftl" as layout>
<@layout.main "Home">
    <#list topics as topic>
    <div class="row<#if topic?item_parity == 'even'> stripe</#if>">
        <div class="six columns">
            <a href="/topics/${topic.id}/${topic.handle}">${topic.title}</a>
            <small>by ${topic.author}</small>
        </div>
        <div class="two columns">${topic.postCount}</div>
        <div class="four columns u-pull-right">${topic.lastUser}</div>
    </div>
    </#list>
    <a class="button button-primary" href="/topic">New Topic</a>
</@layout.main>