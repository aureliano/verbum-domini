function buildPath(name) {
  if (name === "home") {
    return "/";
  } else if (name === "change-log") {
    return "/change-log.xhtml#0.1.0";
  } else if (name === "sign-in") {
    return "/sign-in.xhtml";
  } else if (name === "sign-out") {
    return "/SignOutServlet";
  } else if (name === "bibles") {
    return "/app/bibles/";
  } else {
    return null;
  }
}

function createHref(name) {
  var appName = "";
  
  if (location.pathname.search('/verbumdomini') == 0) {
    appName = '/verbumdomini';
  }

  var path = buildPath(name);
  if (!path) {
    return null;
  }
  
  var baseUrl = location.protocol + '//' + location.host + appName;
  return baseUrl + path;
}

function navigateTo(resourceName) {
  var href = createHref(resourceName);
  
  if (!href) {
    alert("Could not create href to source " + name);
  }
  
  window.location = href;
}