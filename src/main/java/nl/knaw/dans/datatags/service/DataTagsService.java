package nl.knaw.dans.datatags.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "nl.knaw.dans.datatags.service.generated.io.swagger"
        , "nl.knaw.dans.datatags.service.generated.io.swagger.config"
        , "nl.knaw.dans.datatags.service.core.handlers"
        , "nl.knaw.dans.datatags.service.core.api"
        , "nl.knaw.dans.datatags.service.core.config"
        , "nl.knaw.dans.datatags.service.core.model"
        , "nl.knaw.dans.datatags.service.utils"})
public class DataTagsService implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(DataTagsService.class).run(args);
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }

//    public ReloadableResourceBundleMessageSource messageSource() {
//        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
//        source.setBasename("classpath:test"); // name of the resource bundle
//        source.setDefaultEncoding("UTF-8");
//        source.setCacheSeconds(10);
//        return source;
//    }
}
