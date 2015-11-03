function consumeService(path, service, type) {
  var field_id = "input#" + service + "ServiceLoaded_" + type;
  var loaded = $(field_id).val();
  var appName = "";
  
  if (location.pathname.search('/verbumdomini') == 0) {
    appName = '/verbumdomini';
  }

  if (loaded == 'false') {
    if (type == 'xml') {
      consumeXml(appName, path, service, field_id);
    } else if (type == 'json') {
      consumeJson(appName, path, service, field_id);
    } else {
      alert('Unsupported render type ' + type);
    }
  }
}

function consumeJson(appName, path, service, field_id) {
  $('#loading').show();

  var type = 'json';
  var url = location.protocol + '//' + location.host + appName + '/apirest' + path;
  var dataField = service + '_' + type;

  $.getJSON(url, function(data) {
    $('#' + dataField).append('<pre>' + JSON.stringify(data, null, 2) + '</pre>');
    $(field_id).val('true');
  }).done(function() {
    $('#loading').hide();
  }).error(function(jqXHR, textStatus, errorThrown) {
    alert('Service ' + url + ' unavailable. Try again later. ' + errorThrown);
  	$('#loading').hide();
  });
}

function consumeXml(appName, path, service, field_id) {
  $('#loading').show();

  var type = 'xml';
  var url = location.protocol + '//' + location.host + appName + '/apirest' + path;
  var dataField = service + '_' + type;

  $.get(url, function(data) {
    var xml = vkbeautify.xml(data, 2);
    xml = xml.replace(/</g, '&lt;').replace(/>/g, '&gt;');

    $('#' + dataField).append('<pre>' + xml + '</pre>');
    $(field_id).val('true');
  }, 'text').done(function() {
    $('#loading').hide();
  }).error(function(jqXHR, textStatus, errorThrown) {
    alert('Service ' + url + ' unavailable. Try again later. ' + errorThrown);
  	$('#loading').hide();
  });
}