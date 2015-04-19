package org.javers.organization.structure.infrastructure;

import org.javers.core.Javers;
import org.javers.spring.auditable.AuthorProvider;
import org.javers.spring.auditable.aspect.JaversAuditableRepositoryAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyJaversPostProcessor extends JaversAuditableRepositoryAspect {

    @Autowired
    public MyJaversPostProcessor(Javers javers, UserContext userContext) {
        super(javers, new AuthorProvider() {
            @Override
            public String provide() {
                return userContext.getLoggedUser();
            }
        });
    }
}
