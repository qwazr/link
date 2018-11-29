var extractor = new com.qwazr.link.ExtractorTool();

var result = extractor.extract("/Users/ekeller/test.pdf");

print(result.metas);
print(result.documents);
