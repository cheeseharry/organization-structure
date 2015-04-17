package org.javers.organization.structure.ui;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.javers.organization.structure.domain.Person;

public class PersonForm extends CustomComponent {

    final FormLayout form = new FormLayout();
    final Button save = new Button("save");
    private final Controller controller;

    public PersonForm(Controller controller) {
        this.controller = controller;
        setCompositionRoot(form);
        final BeanFieldGroup<Person> binder = new BeanFieldGroup<Person>(Person.class);
        binder.setBuffered(false);
        Person person = new Person();
        binder.setItemDataSource(person);

        for (final Object propertyId : binder.getUnboundPropertyIds()) {
            Field field = binder.buildAndBind(propertyId);
            if (field instanceof TextField) {
                ((TextField) field).setNullRepresentation("");
            }
            form.addComponent(field);
        }

        form.addComponent(save);

        save.addClickListener(event -> {
            BeanFieldGroup<Person> b = binder;
            Person p = b.getItemDataSource().getBean();
            controller.updatePerson(p);
        });
    }
}
