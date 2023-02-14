package bj.tresorbenin.suicom.config.thymeleaf;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
public class ThymeleafConfiguration {

   public SpringTemplateEngine thymeleafLayoutDialect() {
      SpringTemplateEngine engine = new SpringTemplateEngine();
      engine.addDialect(new LayoutDialect());
      return engine;
   }
}
