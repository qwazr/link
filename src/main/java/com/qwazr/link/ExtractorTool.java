package com.qwazr.link;

import com.qwazr.component.ComponentInterface;
import com.qwazr.component.annotations.Component;
import com.qwazr.extractor.ExtractorManager;
import com.qwazr.extractor.ExtractorServiceInterface;
import com.qwazr.utils.ObjectMappers;

import java.io.IOException;

@Component("Text and metadata extractor")
public class ExtractorTool implements ComponentInterface {

	private static volatile ExtractorServiceInterface extractorService;

	ExtractorServiceInterface getExtractorService() throws IOException, ClassNotFoundException {
		if (extractorService != null)
			return extractorService;
		synchronized (ExtractorTool.class) {
			if (extractorService != null)
				return extractorService;
			return extractorService = new ExtractorManager().registerServices().getService();
		}
	}

	@Component("Extract text and metadata")
	public String extract(@Component("The path to the file") String path) throws IOException, ClassNotFoundException {
		return ObjectMappers.JSON.writeValueAsString(getExtractorService().putMagic(null, null, path, null, null));
	}
}
