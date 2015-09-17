function consumeService(url, service, type) {
  var field_id = "input#" + service + "ServiceLoaded_" + type;
  var loaded = $(field_id).val();

  if (loaded == 'false') {
    if (type == 'xml') {
      consumeXml(url, service, field_id);
    } else if (type == 'json') {
      consumeJson(url, service, field_id);
    } else {
      alert('Unsupported render type ' + type);
    }
  }
}

function consumeJson(url, service, field_id) {
  $('#loading').show();

  var type = 'json';
  var url = 'http://' + location.host + '/verbumdomini/apirest' + url;
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
  });
}

function consumeXml(url, service, field_id) {
  $('#loading').show();

  var type = 'xml';
  var url = 'http://' + location.host + '/verbumdomini/apirest' + url;
  var dataField = service + '_' + type;

  $.getJSON(url, function(data) {
    $('#' + dataField).append('<pre>' + JSON.stringify(data, null, 2) + '</pre>');
    $(field_id).val('true');
  }).done(function() {
    $('#loading').hide();
  }).error(function(jqXHR, textStatus, errorThrown) {
    alert('Service ' + url + ' unavailable. Try again later. ' + errorThrown);
  });
}