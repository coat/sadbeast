<#macro main title="Dashboard">
  <#compress>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SadBeast - ${title}</title>

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <#--<link rel="icon" sizes="192x192" href="images/touch/chrome-touch-icon-192x192.png">-->

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="SadBeast">
    <#--<link rel="apple-touch-icon-precomposed" href="apple-touch-icon-precomposed.png">-->

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <#--<meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">-->
    <meta name="msapplication-TileColor" content="#3372DF">

    <link href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="/css/material.min.css">
    <link rel="stylesheet" href="/css/admin.css">
  </head>
  <body>
    <div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
      <header class="demo-header mdl-layout__header mdl-color--white mdl-color--grey-100 mdl-color-text--grey-600">
        <div class="mdl-layout__header-row">
          <span class="mdl-layout-title">Dashboard</span>
          <div class="mdl-layout-spacer"></div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
            <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
              <i class="material-icons">search</i>
            </label>
            <div class="mdl-textfield__expandable-holder">
              <input class="mdl-textfield__input" type="text" id="search" />
              <label class="mdl-textfield__label" for="search">Enter your query...</label>
            </div>
          </div>
        </div>
      </header>
      <div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
<#--        <header class="demo-drawer-header">
        </header>-->
        <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
          <a class="mdl-navigation__link" href="/admin"><i class="mdl-color-text--blue-grey-400 material-icons">home</i>Dashboard</a>
          <a class="mdl-navigation__link" href="/logout"><i class="mdl-color-text--blue-grey-400 material-icons">exit_to_app</i>Logout</a>
        </nav>
      </div>
      <main class="mdl-layout__content mdl-color--grey-100">
        <#nested>
      </main>
    </div>
    <script src="/js/material.min.js"></script>
  </body>
</html>
</#compress>
</#macro>
