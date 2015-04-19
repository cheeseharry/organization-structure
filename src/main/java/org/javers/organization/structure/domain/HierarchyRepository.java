package org.javers.organization.structure.domain;

import java.util.List;

public interface HierarchyRepository {

    void save(Hierarchy hierarchy);

    List<Hierarchy> findAll();

    Hierarchy find(String id);

    void update(Hierarchy hierachy);
}
