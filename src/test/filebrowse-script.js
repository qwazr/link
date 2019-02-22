var filesTool = new com.qwazr.library.files.FilesTool();

filesTool.browse("/Users/ekeller/Documents/Jaeksoft", 2, {
  file: function (filePath, depth) {
    print(depth + ' ' + filePath);
  }
});
