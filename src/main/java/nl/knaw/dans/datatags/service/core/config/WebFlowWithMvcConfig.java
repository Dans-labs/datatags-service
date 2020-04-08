package nl.knaw.dans.datatags.service.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.ViewFactoryCreator;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.webflow.view.AjaxThymeleafViewResolver;
import org.thymeleaf.spring5.webflow.view.FlowAjaxThymeleafView;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Configuration
public class WebFlowWithMvcConfig extends AbstractFlowConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(WebFlowWithMvcConfig.class);
    private static final List<?> pluginList = new ArrayList<>();
    @Autowired
    Environment env;
    
    @Autowired
    private LocalValidatorFactoryBean localValidatorFacotryBean;


    // hookup url for start webflow.
    //	if you are using pattern like- "/**/*-flow.xml"  url hookup will be make by folder mapping relativly /**/
    //in this example we have 3 *-flow.xml file and each file url hookup is /register/start , /register2 ,  /register3

    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder() //
                .setBasePath("classpath:flows") //
                .addFlowLocationPattern("/**/*-flow.xml") //
                .setFlowBuilderServices(this.flowBuilderServices()) //
                .build();
    }

    @Bean
    public FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder(this.flowRegistry()) //
                .setMaxFlowExecutionSnapshots(-1)
                .build();
    }

    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder() //
                .setViewFactoryCreator(this.mvcViewFactoryCreator()) // Important!
                .setValidator(this.localValidatorFacotryBean).build();
    }
    // ----------------------------------------------------------

    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(-1);
        handlerMapping.setFlowRegistry(this.flowRegistry());
        return handlerMapping;
    }

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(this.flowExecutor());
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    @Bean
    public ViewFactoryCreator mvcViewFactoryCreator() {
        MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        factoryCreator.setViewResolvers(Collections.singletonList(this.thymeleafViewResolver()));
        factoryCreator.setUseSpringBeanBinding(true);
        return factoryCreator;
    }

    @Bean
    @Description("Thymeleaf AJAX view resolver for Spring WebFlow")
    public AjaxThymeleafViewResolver thymeleafViewResolver() {
        AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
        viewResolver.setViewClass(FlowAjaxThymeleafView.class);
        viewResolver.setTemplateEngine(this.templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(this.templateResolver());
        return templateEngine;
    }
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    //TODO: Using plugins mechanism
    private void registerPlugins(){
        LOG.info("#                                                             #");
        LOG.info("#                 Trying to register plugin...                #");
//        try {
            //Eko says: of course we should check many thing here, eq: dir exist, files extension, structures, etc!
            //Checking whether the plugins directory exist or not, is already done by checkingRequiredDirs();
            File pluginsBaseDir = new File(Objects.requireNonNull(env.getProperty("bridge.plugins.dir")));
            File pluginsDir[] = pluginsBaseDir.listFiles(pathname -> !pathname.isFile());
            if (pluginsDir != null) {
                for (File darPluginDir : pluginsDir) {
//                    DarPluginConf darPluginConf = PluginRegisterService.attachDarPlugin(darPluginDir);
//                    if (darPluginConf == null) break;
//
//                    pluginList.add(darPluginConf);
                }
            }

            LOG.info("# Number of plugin: {}", pluginList.size());

//        } catch (MalformedURLException e) {
//            LOG.error("Fail to start the DataTags Service.....");
//            LOG.error("MalformedURLException, cause by: {}", e.getMessage());
//            System.exit(1);
//        } catch (FileNotFoundException e) {
//            LOG.error("Fail to start the DataTags Service.....");
//            LOG.error("plugins directory doesn't exist" );
//            LOG.error("FileNotFoundException, cause by: {}", e.getMessage());
//            System.exit(1);//Yes, the application will terminated!
//        }
        if (pluginList.isEmpty()) {
            LOG.warn("No plugin is registered. Adding at least one plugin is required, otherwise you cannot use this application");
            LOG.warn("Adding plugin can be done by uploading the plugins in zip format through bridge api");
        }
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer()
//    {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:8888");
//            }
//        };
//    }
}
