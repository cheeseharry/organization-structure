package org.javers.organization.structure.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class MainView extends UI {

    private Controller controller;

    @Autowired
    public MainView(Controller controller) {
        this.controller = controller;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout mainLayout = new HorizontalLayout();
        OrganizationTree organizationTree = new OrganizationTree(controller);
        PersonForm userForm = new PersonForm(controller);

        mainLayout.setSizeFull();
        setContent(mainLayout);
        mainLayout.addComponent(organizationTree);
        mainLayout.addComponent(userForm);
        mainLayout.setExpandRatio(organizationTree, 3f);
        mainLayout.setExpandRatio(userForm, 7f);
    }

}
