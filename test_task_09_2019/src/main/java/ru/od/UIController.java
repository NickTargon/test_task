package ru.od;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Title("test task 09.2019")
@SpringUI
@Theme("valo")
public class UIController extends UI {

    private final SpringViewProvider viewProvider;

    private final Logger logger = LoggerFactory.getLogger(UIController.class);

    @Autowired
    public UIController(SpringViewProvider viewProvider) {

        this.viewProvider = viewProvider;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Navigator navigator = new Navigator(this, this);

        navigator.addProvider(viewProvider);

        logger.info("Inited ");
    }
}
