package server.listeners;


import common.ConstantsServer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;


@WebListener
public class ThymeLeafListener implements ServletContextListener {

    public static final String TEMPLATE_ENGINE_ATTR = ConstantsServer.THYMELEAF_TEMPLATE_ENGINE_INSTANCE;



    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(sce.getServletContext());
        ITemplateEngine templateEngine = templateEngine(application);

        sce.getServletContext().setAttribute(TEMPLATE_ENGINE_ATTR, templateEngine);

    }

    private ITemplateEngine templateEngine(IWebApplication application) {
        TemplateEngine templateEngine = new TemplateEngine();

        WebApplicationTemplateResolver templateResolver = templateResolver(application);
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private WebApplicationTemplateResolver templateResolver(IWebApplication application) {
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix(ConstantsServer.WEB_INF_TEMPLATES);
        templateResolver.setSuffix(ConstantsServer.HTML);
        templateResolver.setCacheTTLMs(3600000L);

        templateResolver.setCacheable(true);

        return templateResolver;
    }


}

