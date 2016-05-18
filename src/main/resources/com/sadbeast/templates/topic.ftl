<#import "layout.ftl" as layout>
<@layout.main "${topic.title}">
<h4>${topic.title}</h4>
    <#list topic.posts as post>
        <div class="row<#if post?item_parity == 'even'> stripe</#if>" style="padding:.5em">
            <div class="three columns author">
                <p>
                <strong>${post.author}</strong>
                    <br><small>${post.created}</small>
                </p>
            </div>
            <div class="nine columns">${post.content}</div>
        </div>
    </#list>
    <a class="button button-primary" href="/topics/${topic.id}/${topic.handle}/post" style="margin-top:1em">Post</a>
</@layout.main>
