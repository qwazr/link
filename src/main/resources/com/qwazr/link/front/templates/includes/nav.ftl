<nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
    <a class="navbar-brand" href="/">Qwazr LINK</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item<#if request.servletPath='/jobs'> active</#if>">
                <a class="nav-link" href="/jobs">Jobs</a>
            </li>
            <li class="nav-item<#if request.servletPath='/library'> active</#if>">
                <a class="nav-link" href="/library">Library</a>
            </li>
        </ul>
    </div>
</nav>