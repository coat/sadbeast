<#import "layout.ftl" as layout>
<@layout.main "New Post">
<#if errors??><div style="color:indianred">You have failed</div></#if>
<form action="/topics/${topic.id}/${topic.handle}/post" method="post">
    <label for="content">Post</label>
    <textarea name ="content" class="u-full-width" placeholder="${msg.getString("content.placeholder")}" id="content" cols="7">${(topic.content)!}</textarea>
    <input class="button-primary" type="submit" value="Post">
</form>
</@layout.main>
