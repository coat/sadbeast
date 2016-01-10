<#import "layout.ftl" as layout>
<@layout.main "Topic">
${topic.title}
    <#list topic.posts as post>
        <div class="row<#if post?item_parity == 'even'> stripe</#if>">
            <div class="two column">
                ${post.author}
            </div>
            <div class="ten column">
             ${post.content}
            </div>
        </div>
    </#list>
    <a class="button button-primary" href="/topics/${topic.id}/${topic.handle}/post">Post</a>
</@layout.main>
