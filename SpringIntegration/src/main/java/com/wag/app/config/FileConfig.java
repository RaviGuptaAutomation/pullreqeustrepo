package com.wag.app.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

@Configuration
@EnableIntegration
public class FileConfig {

	@Bean
	@InboundChannelAdapter(value="fileInputChannel",poller = @Poller(fixedDelay = "1000"))
	public FileReadingMessageSource fileReadingMessageSource() {
		
		FileReadingMessageSource reader = new FileReadingMessageSource();
		CompositeFileListFilter<File> filter = new CompositeFileListFilter<>();
		filter.addFilter(new SimplePatternFileListFilter("*.txt"));
		reader.setDirectory(new File("D:\\spring_project"));
		reader.setFilter(filter);
		return reader;
		
	}
	
	@Bean
	@ServiceActivator(inputChannel = "fileInputChannel")
	public FileWritingMessageHandler fileWritingMessageHandler() {
		
		FileWritingMessageHandler writer = new FileWritingMessageHandler(new File("D:\\writer"));
		writer.setAutoCreateDirectory(true);
		writer.setExpectReply(false);
		
		return writer;
	}
	
}
