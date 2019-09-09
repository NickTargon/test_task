package ru.od.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.od.model.MainEntity;
import ru.od.repository.MainEntityRepository;


@SpringComponent
@SpringView(name = "")
@UIScope
public class SimpleView extends Panel implements View{
    private final MainEntityRepository mainEntityRepository;
    private final VerticalLayout rootLayout = new VerticalLayout();
    private final Button loadButton = new Button("Загрузить еще");
    private int currentPage;

    private final Logger logger = LoggerFactory.getLogger(SimpleView.class);

    @Autowired
    public SimpleView(MainEntityRepository mainEntityRepository) {
        HorizontalLayout dataLayout = new HorizontalLayout();
        rootLayout.addComponent(dataLayout);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addComponent(loadButton);
        rootLayout.addComponent(buttonLayout);

        setContent(rootLayout);
        this.mainEntityRepository = mainEntityRepository;

        currentPage = 0;

    }

    @Override
    public void attach() {
        super.attach();
        logger.info("Enter");

        load();

        loadButton.addClickListener(clickEvent -> load());
    }

    private void load() {
        Page<MainEntity> mainEntities = mainEntityRepository.findAll(new PageRequest(currentPage, 10));

        if (currentPage == mainEntities.getTotalPages()) {
            loadButton.setDisableOnClick(true);
        }

        currentPage++;

        for (MainEntity mainEntity : mainEntities) {
            String format = String.format("Имя сущности %s", mainEntity.getName());
            logger.info(format);
            rootLayout.addComponent(new Label("----------"));
            rootLayout.addComponent(new Label(format));
            rootLayout.addComponent(new Label(String.format("Количество элементов подсущности %d", mainEntity.getSubEntities().size())));
            rootLayout.addComponent(new Label("----------"));
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        rootLayout.setSpacing(true);
        rootLayout.setMargin(true);
        setSizeFull();
        rootLayout.setSizeUndefined();
        rootLayout.setWidth("100%");
    }
}
