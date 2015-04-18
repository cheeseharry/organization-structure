package org.javers.organization.structure.ui;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.javers.organization.structure.domain.Person;

public class PersonForm extends CustomComponent {

    private final BeanFieldGroup<Person> binder = new BeanFieldGroup(Person.class);

    public PersonForm(Controller controller) {
        FormLayout form = new FormLayout();
        Button save = new Button("save");
        setCompositionRoot(form);

        binder.setBuffered(false);
        binder.setItemDataSource(new Person());

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

    public void selectPerson(Person person) {
        binder.setItemDataSource(person);
    }
}
