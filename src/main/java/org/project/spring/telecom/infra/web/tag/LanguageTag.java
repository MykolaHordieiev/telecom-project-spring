package org.project.spring.telecom.infra.web.tag;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@Log4j2
public class LanguageTag extends TagSupport {

    private String message;
    private final Utf8Control utf8Control = new Utf8Control();

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        Locale locale = (Locale) session.getAttribute("selectedLocale");
        if (message != null && !message.isEmpty()) {
            ResourceBundle messages = ResourceBundle.getBundle("i18n.resources", locale, utf8Control);
            String locMessage = messages.getString(message);
            try {
                pageContext.getOut().print(locMessage);
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        return SKIP_BODY;
    }
}
