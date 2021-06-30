package org.springframework.boot.web.server;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.context.annotation.BuildTimeBeanDefinitionsRegistrar;
import org.springframework.context.origin.BeanFactoryStructureAnalysis;
import org.springframework.context.support.GenericApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebServerFactoryAutoConfigurationBeanDefinitionOriginAnalyzer}.
 * @author Stephane Nicoll
 */
class WebServerFactoryAutoConfigurationBeanDefinitionOriginAnalyzerTests {

	private final WebServerFactoryAutoConfigurationBeanDefinitionOriginAnalyzer analyzer = new WebServerFactoryAutoConfigurationBeanDefinitionOriginAnalyzer();

	@Test
	void webServerFactoryCustomizerBeanPostProcessorIsAnalyzed() {
		GenericApplicationContext context = new AnnotationConfigServletWebApplicationContext();
		context.registerBean(ServletWebServerFactoryAutoConfiguration.class);
		BuildTimeBeanDefinitionsRegistrar registrar = new BuildTimeBeanDefinitionsRegistrar(context);
		BeanFactoryStructureAnalysis analysis = new BeanFactoryStructureAnalysis(registrar.processBeanDefinitions());
		this.analyzer.analyze(analysis);
		assertThat(analysis.processed().filter((candidate) ->
				WebServerFactoryCustomizerBeanPostProcessor.class.getName().equals(candidate.getBeanDefinition().getBeanClassName())))
				.singleElement().satisfies((origin) -> assertThat(origin.getOrigins()).singleElement().satisfies((parent) -> assertThat(parent.getBeanClassName()).isEqualTo(ServletWebServerFactoryAutoConfiguration.class.getName())));
	}

	@Test
	void errorPageRegistrarBeanPostProcessorIsAnalyzed() {
		GenericApplicationContext context = new AnnotationConfigServletWebApplicationContext();
		context.registerBean(ServletWebServerFactoryAutoConfiguration.class);
		BuildTimeBeanDefinitionsRegistrar registrar = new BuildTimeBeanDefinitionsRegistrar(context);
		BeanFactoryStructureAnalysis analysis = new BeanFactoryStructureAnalysis(registrar.processBeanDefinitions());
		this.analyzer.analyze(analysis);
		assertThat(analysis.processed().filter((candidate) ->
				ErrorPageRegistrarBeanPostProcessor.class.getName().equals(candidate.getBeanDefinition().getBeanClassName())))
				.singleElement().satisfies((origin) -> assertThat(origin.getOrigins()).singleElement().satisfies((parent) -> assertThat(parent.getBeanClassName()).isEqualTo(ServletWebServerFactoryAutoConfiguration.class.getName())));
	}

}
