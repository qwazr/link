if (current.statusCode === 200) {
  console.log('*** URI: ' + current.uri);
  var content = current.body.content;
  if (current.contentType === 'text/html' && content) {
    extractor = new com.qwazr.link.ExtractorTool();
    var extract = extractor.extract(current.body.content);
    print(extract.documents);
  }

}
