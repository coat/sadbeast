<#import "layout.ftl" as layout>
<@layout.main "Dashboard">

<div class="mdl-grid demo-content">
    <div class="demo-charts mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
        <div class="mdl-card__title">
            <h4 class="mdl-card__title-text">Posts</h4>
        </div>
        <div class="mdl-card__supporting-text">
            ${count} <#if count == 1>post<#else>posts</#if>
        </div
    </div>
</div>
</@layout.main>
