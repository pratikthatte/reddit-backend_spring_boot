package com.project.redditclone.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class MailContentBuilder {
	private static Logger log = LoggerFactory.getLogger(MailContentBuilder.class);
	private final TemplateEngine templateEngine;

	public MailContentBuilder(TemplateEngine templateEngine) {
		super();
		this.templateEngine = templateEngine;
	}
	String build(String message) {
		Context context = new Context();
		context.setVariable("message", message);
		log.info("Message is: " + context.getVariable("message"));
		return templateEngine.process("mailTemplate", context);
	}
}
