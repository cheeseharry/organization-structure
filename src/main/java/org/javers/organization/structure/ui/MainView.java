package org.javers.organization.structure.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.javers.organization.structure.domain.Person;
import org.javers.organization.structure.infrastructure.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class MainView extends UI {

    private OrganizationTree organizationTree;
    private PersonForm personForm;

    @Autowired
    public MainView(Controller controller, DataInitializer dataInitializer) {
        dataInitializer.populate();
        organizationTree = new OrganizationTree(controller);
        personForm = new PersonForm(controller);
        controller.injectView(this);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        setContent(mainLayout);
        mainLayout.addComponent(organizationTree);
        mainLayout.addComponent(personForm);
        mainLayout.setExpandRatio(organizationTree, 3f);
        mainLayout.setExpandRatio(personForm, 7f);
    }

    public void selectPersonOnForm(Person person) {
        personForm.selectPerson(person);
    }
}
