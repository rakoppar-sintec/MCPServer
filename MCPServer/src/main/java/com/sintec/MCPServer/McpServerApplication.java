package com.sintec.MCPServer;

import com.sintec.MCPServer.service.GadgetsService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpServerApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider tools(GadgetsService gadgetsService) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(gadgetsService)
				.build();
	}
}

