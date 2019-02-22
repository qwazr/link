if (current.statusCode === 200) {
  console.log('*** URI: ' + current.uri);
  var content = current.body.content;

  if (current.contentType === 'text/html' && content) {
    extractor = new com.qwazr.link.ExtractorTool();
    var extract = extractor.extract(current.body.content, {
      "css": [".container > ul > li"],
    "xpath": ["span[@itemProp='producdID']"]});


    /html/body/div[4]/div[1]/div[2]/div[1]/div[1]/div/div[1]/span/span
    html body div#container div#pp div#product_1356389.middle div.left div.top div.left div.left.ratingandRef span.ref span
    print(extract.documents);
  }

  if (current.uri.startsWith('/ref/'))

}
