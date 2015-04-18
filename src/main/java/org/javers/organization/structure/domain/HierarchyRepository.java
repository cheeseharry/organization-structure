package org.javers.organization.structure.domain;

import java.util.List;

public interface HierarchyRepository {

    void save(Hierarchy hierarchy);

    List<Hierarchy> findAll();

    void update(Hierarchy hierachy);
}
