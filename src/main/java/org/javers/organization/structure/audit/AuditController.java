package org.javers.organization.structure.audit;

import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.json.JsonConverter;
import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.HierarchyRepository;
import org.javers.organization.structure.domain.Person;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/audit")
public class AuditController {

    private Javers javers;

    private HierarchyRepository hierarchyRepository;

    @Autowired
    public AuditController(Javers javers, HierarchyRepository hierarchyRepository) {
        this.javers = javers;
        this.hierarchyRepository = hierarchyRepository;
    }

    @RequestMapping(value = "/employees/changes", method = RequestMethod.GET)
    public String getAllPersonsHistory(@RequestParam(value = "property") Optional<String> property) {
        QueryBuilder jqlQueryBuilder = QueryBuilder.byClass(Person.class)
                .withNewObjectChanges()
                .limit(20);

        jqlQueryBuilder = property.isPresent() ? jqlQueryBuilder.andProperty(property.get()) : jqlQueryBuilder;

        JsonConverter jsonConverter = javers.getJsonConverter();

        List<Change> changes = javers.findChanges(jqlQueryBuilder.build());

        changes.sort((o1, o2) -> -1 * o1.getCommitMetadata().get().getCommitDate().compareTo(o2.getCommitMetadata().get().getCommitDate()));

        return jsonConverter.toJson(changes);
    }

    @RequestMapping(value = "/employees/{id}/changes", method = RequestMethod.GET)
    public String getPersonHistory(@PathVariable long id, @RequestParam(value = "property") Optional<String> property) {
        QueryBuilder jqlQueryBuilder = QueryBuilder.byInstanceId(id, Person.class)
        .withNewObjectChanges()
        .limit(20);

        jqlQueryBuilder = property.isPresent() ? jqlQueryBuilder.andProperty(property.get()) : jqlQueryBuilder;

        JsonConverter jsonConverter = javers.getJsonConverter();

        List<Change> changes = javers.findChanges(jqlQueryBuilder.build());
        return jsonConverter.toJson(changes);
    }

    @RequestMapping(value = "/hierarchy/{left}/diff/{right}")
    public String hierarchyDiff(@PathVariable String left, @PathVariable String right) {
        Diff diff = javers.compare(hierarchyRepository.find(left), hierarchyRepository.find(right));

        JsonConverter jsonConverter = javers.getJsonConverter();
        return jsonConverter.toJson(diff);

    }

    @RequestMapping(value = "/employees/{employee}/snapshots")
    public String hierarchyDiff2(@PathVariable String employee) {
        QueryBuilder jqlQueryBuilder = QueryBuilder.byInstanceId(employee, Employee.class)
                .withNewObjectChanges()
                .limit(20);

        JsonConverter jsonConverter = javers.getJsonConverter();
        return jsonConverter.toJson(javers.findSnapshots(jqlQueryBuilder.build()));

    }

}
