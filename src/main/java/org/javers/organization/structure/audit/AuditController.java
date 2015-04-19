package org.javers.organization.structure.audit;


import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.json.JsonConverter;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.organization.structure.domain.Hierarchy;
import org.javers.organization.structure.domain.HierarchyService;
import org.javers.organization.structure.domain.Person;
import org.javers.repository.jql.JqlQuery;
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
@RequestMapping("/audit")
public class AuditController {

    private Javers javers;
    private JsonConverter jsonConverter;
    private HierarchyService hierarchyService;

    @Autowired
    public AuditController(Javers javers, HierarchyService hierarchyService) {
        this.javers = javers;
        this.hierarchyService = hierarchyService;
        jsonConverter = javers.getJsonConverter();
    }

    @RequestMapping(value = "/persons/changes", method = RequestMethod.GET)
    public String getAllPersonChanges(@RequestParam String property) {
        QueryBuilder queryBuilder = QueryBuilder.byClass(Person.class).andProperty(property);

        JqlQuery<Person> jqlQuery = queryBuilder.build();

        List<Change> changes = javers.findChanges(jqlQuery);

        changes.sort(((o1, o2) -> -1 * o1.getCommitMetadata().get().getCommitDate().compareTo(o2.getCommitMetadata().get().getCommitDate())));

        return jsonConverter.toJson(changes);
    }

    @RequestMapping(value = "/persons/{login}/changes", method = RequestMethod.GET)
    public String getPersonChanges(@PathVariable String login, @RequestParam Optional<String> property) {
        QueryBuilder queryBuilder = QueryBuilder.byInstanceId(login, Person.class);

        queryBuilder = property.isPresent() ? queryBuilder.andProperty(property.get()) : queryBuilder;

        JqlQuery<Person> jqlQuery = queryBuilder.build();

        List<Change> changes = javers.findChanges(jqlQuery);

        changes.sort(((o1, o2) -> -1 * o1.getCommitMetadata().get().getCommitDate().compareTo(o2.getCommitMetadata().get().getCommitDate())));

        return jsonConverter.toJson(changes);
    }

    @RequestMapping(value = "/persons/snapshots", method = RequestMethod.GET)
    public String getAllPersonSnapshots(@RequestParam Optional<String> property) {
        QueryBuilder queryBuilder = QueryBuilder.byClass(Person.class);

        queryBuilder = property.isPresent() ? queryBuilder.andProperty(property.get()) : queryBuilder;

        JqlQuery<Person> jqlQuery = queryBuilder.build();

        List<CdoSnapshot> changes = javers.findSnapshots(jqlQuery);

        changes.sort(((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate())));

        return jsonConverter.toJson(changes);
    }

    @RequestMapping(value = "/persons/{login}/snapshots", method = RequestMethod.GET)
    public String getPersonSnapshots(@PathVariable String login, @RequestParam Optional<String> property) {

        QueryBuilder queryBuilder = QueryBuilder.byInstanceId(login, Person.class);

        queryBuilder = property.isPresent() ? queryBuilder.andProperty(property.get()) : queryBuilder;

        JqlQuery<Person> jqlQuery = queryBuilder.build();

        List<CdoSnapshot> changes = javers.findSnapshots(jqlQuery);

        changes.sort(((o1, o2) -> -1 * o1.getCommitMetadata().getCommitDate().compareTo(o2.getCommitMetadata().getCommitDate())));

        return jsonConverter.toJson(changes);
    }

    @RequestMapping(value = "hierarchy/{left}/diff/{right}", method = RequestMethod.GET)
    public String diffHierarchy(@PathVariable String left, @PathVariable String right) {
        Hierarchy l = hierarchyService.find(left);
        Hierarchy r = hierarchyService.find(right);

        Diff diff = javers.compare(l, r);
        return jsonConverter.toJson(diff);
    }

    @RequestMapping(value = "hierarchy/{left}/diff/{right}/newEmployees", method = RequestMethod.GET)
    public String newEmployeesHierarchy(@PathVariable String left, @PathVariable String right) {
        Hierarchy l = hierarchyService.find(left);
        Hierarchy r = hierarchyService.find(right);

        Diff diff = javers.compare(l, r);

        List<Change> changes = diff.getChanges(input ->
                (input instanceof NewObject
                        && input.getAffectedGlobalId().getCdoClass().getClientsClass() != Hierarchy.class));

        return jsonConverter.toJson(changes);
    }
}
