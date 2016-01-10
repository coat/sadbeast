<#import "layout.ftl" as layout>
<@layout.main "New Topic">
<form action="/topic" method="post">
    <div class="row">
        <div class="six columns">
            <label for="title">Title</label>
            <input name="title" class="u-full-width" type="text" id="title" value="${(topic.title)!}" autofocus>
        </div>
    </div>
    <label for="content">Post</label>
    <textarea name ="content" class="u-full-width" placeholder="${msg.getString("content.placeholder")}" id="content" cols="7">${(topic.content)!}</textarea>
    <input class="button-primary" type="submit" value="Post">
</form>
</@layout.main>
