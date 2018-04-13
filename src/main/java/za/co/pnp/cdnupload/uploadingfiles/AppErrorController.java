package za.co.pnp.cdnupload.uploadingfiles;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/errors")
public class AppErrorController implements EmbeddedServletContainerCustomizer
{
    @Override
    public void customize(final ConfigurableEmbeddedServletContainer factory)
    {

        factory.addErrorPages(new ErrorPage("/errors/unexpected"));
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/errors/notfound"));
    }

    @RequestMapping("unexpected")
    @ResponseBody
    public String unexpectedError(final HttpServletRequest request)
    {
        return "Exception: " + request.getAttribute("javax.servlet.error.exception");
    }

    @RequestMapping("notfound")
    @ResponseBody
    public String notFound()
    {
        return "Error 404";
    }
}