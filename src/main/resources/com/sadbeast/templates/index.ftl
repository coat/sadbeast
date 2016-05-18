<#import "layout.ftl" as layout>
<@layout.main "Home">
    <#list topics as topic>
    <div class="row<#if topic?item_parity == 'even'> stripe</#if>" style="padding:.5em">
        <div class="six columns topic">
            <div>
                <a href="/topics/${topic.id}/${topic.handle}">${topic.title}</a>
                <small>by ${topic.author}</small>
            </div>
        </div>
        <div class="two columns post-count">${topic.postCount}</div>
        <div class="four columns last-user" style="">
        ${topic.lastUser}
            <br>
            <small>${topic.lastDate}</small>
        </div>
    </div>
    </#list>
<a class="button button-primary" href="/topic" style="margin-top:1em">New Topic</a>
</@layout.main>