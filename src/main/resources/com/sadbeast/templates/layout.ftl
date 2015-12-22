<#macro main title="Home">
    <#compress>
    <!DOCTYPE html>
    <html lang="en-us">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="Write something...">
        <meta name="keywords" content="SadBeast, Sad Beast, anonymous, confession, feelings">

        <title>SadBeast - ${title}</title>
        <link href="//fonts.googleapis.com/css?family=Droid+Serif" rel="stylesheet" type="text/css">
        <link href="/css/normalize.css" rel="stylesheet" type="text/css">
        <link href="/css/skeleton.css" rel="stylesheet" type="text/css">
        <link href="/css/main.css" rel="stylesheet" type="text/css">

        <script>
            (function (i, s, o, g, r, a, m) {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function () {
                            (i[r].q = i[r].q || []).push(arguments)
                        }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

            ga('create', 'UA-2274959-10', 'auto');
            ga('send', 'pageview');
        </script>
    </head>
    <body>
    <div class="row" style="padding:1em">
        <div class="twelve columns">
            <h1><a href="/">SadBeast</a></h1>
            <#nested>
        </div>
    </div>
    </body>
    </html>
    </#compress>
</#macro>
