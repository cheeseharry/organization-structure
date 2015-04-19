package org.javers.organization.structure.domain;

import org.javers.core.Javers;
import org.javers.organization.structure.infrastructure.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HierarchyService {

    private HierarchyRepository hierarchyRepository;

    private Javers javers;
    private final UserContext userContext;

    @Autowired
    public HierarchyService(HierarchyRepository hierarchyRepository, Javers javers, UserContext userContext) {
        this.hierarchyRepository = hierarchyRepository;
        this.javers = javers;
        this.userContext = userContext;
    }

    public List<Hierarchy> findAll() {
        return hierarchyRepository.findAll();
    }

    public void update(Hierarchy selected) {
        hierarchyRepository.update(selected);

        javers.commit(userContext.getLoggedUser(), selected);
    }
}
