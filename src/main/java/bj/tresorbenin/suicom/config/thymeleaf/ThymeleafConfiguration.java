package bj.tresorbenin.suicom.config.thymeleaf;
import org.springframework.context.annotation.Configuration;

import org.thymeleaf.spring6.SpringTemplateEngine;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class ThymeleafConfiguration {

   public SpringTemplateEngine thymeleafLayoutDialect() {
      SpringTemplateEngine engine = new SpringTemplateEngine();
      engine.addDialect(new LayoutDialect());
      return engine;
   }
}
