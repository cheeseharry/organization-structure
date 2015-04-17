package org.javers.organization.structure.ui;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Tree;
import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.Hierarchy;

import java.util.Iterator;
import java.util.List;

public class OrganizationTree extends CustomComponent {

    final HierarchicalContainer container = new HierarchicalContainer();

    public OrganizationTree(Controller controller) {
        setSizeFull();
        Tree sample = new Tree();
        sample.setSizeFull();
        setCompositionRoot(sample);

        List<Hierarchy> hierarchies = controller.getAllHierachies();
        Hierarchy first = hierarchies.get(0);
        container.addContainerProperty("login", String.class, null);
        createContainer(first);

        sample.setContainerDataSource(container);
        sample.setItemCaptionPropertyId("login");


        // Populate the tree


//        sample.setItemIconPropertyId(ExampleUtil.hw_PROPERTY_ICON);

        // Allow all nodes to have children
        for (final Object itemId : sample.getItemIds()) {
            sample.setChildrenAllowed(itemId, true);
        }

        // Expand all nodes
        for (final Iterator<?> it = sample.rootItemIds().iterator(); it
                .hasNext(); ) {
            sample.expandItemsRecursively(it.next());
        }
        sample.setDragMode(Tree.TreeDragMode.NODE);
        sample.setDropHandler(new TreeSortDropHandler(sample, container));

    }

    private void createContainer(Hierarchy first) {
        Employee root = first.getRoot();

        addSubordinates(root);
    }

    private void addSubordinates(Employee root) {
        for (Employee e: root.getAllEmployees()) {
            Item item = container.addItem(e);
            item.getItemProperty("login").setValue(e.getLogin());
            container.setParent(e, root);
            addSubordinates(e);
        }
    }

    private static class TreeSortDropHandler implements DropHandler {
        private final Tree tree;

        /**
         * Tree must use {@link HierarchicalContainer}.
         *
         * @param tree
         */
        public TreeSortDropHandler(final Tree tree,
                                   final HierarchicalContainer container) {
            this.tree = tree;
        }

        @Override
        public AcceptCriterion getAcceptCriterion() {
            // Alternatively, could use the following criteria to eliminate some
            // checks in drop():
            // new And(IsDataBound.get(), new DragSourceIs(tree));
            return AcceptAll.get();
        }

        @Override
        public void drop(final DragAndDropEvent dropEvent) {
            // Called whenever a drop occurs on the component

            // Make sure the drag source is the same tree
            final Transferable t = dropEvent.getTransferable();

            // see the comment in getAcceptCriterion()
            if (t.getSourceComponent() != tree
                    || !(t instanceof DataBoundTransferable)) {
                return;
            }

            final Tree.TreeTargetDetails dropData = ((Tree.TreeTargetDetails) dropEvent
                    .getTargetDetails());

            final Object sourceItemId = ((DataBoundTransferable) t).getItemId();
            // FIXME: Why "over", should be "targetItemId" or just
            // "getItemId"
            final Object targetItemId = dropData.getItemIdOver();

            // Location describes on which part of the node the drop took
            // place
            final VerticalDropLocation location = dropData.getDropLocation();

            moveNode(sourceItemId, targetItemId, location);

        }

        /**
         * Move a node within a tree onto, above or below another node depending
         * on the drop location.
         *
         * @param sourceItemId id of the item to move
         * @param targetItemId id of the item onto which the source node should be moved
         * @param location     VerticalDropLocation indicating where the source node was
         *                     dropped relative to the target node
         */
        private void moveNode(final Object sourceItemId,
                              final Object targetItemId, final VerticalDropLocation location) {
            final HierarchicalContainer container = (HierarchicalContainer) tree
                    .getContainerDataSource();

            // Sorting goes as
            // - If dropped ON a node, we preppend it as a child
            // - If dropped on the TOP part of a node, we move/add it before
            // the node
            // - If dropped on the BOTTOM part of a node, we move/add it
            // after the node if it has no children, or prepend it as a child if
            // it has children

            if (location == VerticalDropLocation.MIDDLE) {
                if (container.setParent(sourceItemId, targetItemId)
                        && container.hasChildren(targetItemId)) {
                    // move first in the container
                    container.moveAfterSibling(sourceItemId, null);
                }
            } else if (location == VerticalDropLocation.TOP) {
                final Object parentId = container.getParent(targetItemId);
                if (container.setParent(sourceItemId, parentId)) {
                    // reorder only the two items, moving source above target
                    container.moveAfterSibling(sourceItemId, targetItemId);
                    container.moveAfterSibling(targetItemId, sourceItemId);
                }
            } else if (location == VerticalDropLocation.BOTTOM) {
                if (container.hasChildren(targetItemId)) {
                    moveNode(sourceItemId, targetItemId,
                            VerticalDropLocation.MIDDLE);
                } else {
                    final Object parentId = container.getParent(targetItemId);
                    if (container.setParent(sourceItemId, parentId)) {
                        container.moveAfterSibling(sourceItemId, targetItemId);
                    }
                }
            }
        }
    }
}
