var webCrawler = new com.qwazr.link.WebCrawlerTool();

webCrawler.crawl("qwazr", {
  entry_url: "http://www.qwazr.com/",
  inclusion_patterns: ["http://www.qwazr.com/*"],
  max_depth: 2
});
