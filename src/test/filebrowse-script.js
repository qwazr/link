var browser = new com.qwazr.library.files.FilesTool();

browser.browse("/Users/ekeller/Documents/Jaeksoft", 2, {
  file: function (filePath, depth) {
    print(depth + ' ' + filePath);
  }
});
