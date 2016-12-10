package assignment1;

import graphs.*;
import graphs.Graph.AdjacentEdge;
import java.util.*;

public class DelegateFinder {

    /**
     * @param delegates
     *            The set of delegates who will take part in the summit. You may
     *            assume that the set of delegates is not null and does not
     *            contain null values.
     * @param graph
     *            A (non-null) directed graph describing the possible
     *            proceedings of the summit. The graph should consist of zero or
     *            more vertices, and each vertex in the graph should be
     *            associated with an event that the given delegates take part
     *            in.
     * @param start
     *            A vertex from the graph that is designated as the start
     *            vertex. Every vertex of the graph should be reachable from the
     *            start vertex.
     * @param end
     *            A vertex from the graph that is designated as the end vertex.
     *            Every vertex of the graph should be able to reach the end
     *            vertex.
     * @param initial
     *            A subset of the given delegates who are in favour of the
     *            decision at the start of the summit.
     * @return This method returns the set of delegates who may be in favour of
     *         the decision at the end of the summit. (See assignment handout
     *         for details.)
     */
    public static Set<Delegate> findDelegates(Set<Delegate> delegates,
            DGraphAdj<Vertex, Event> graph, Vertex start, Vertex end,
            Set<Delegate> initial) {
        Set<Delegate> result = new HashSet<Delegate>();
        Set<Delegate> inFavourDelegates = new HashSet<Delegate>(initial);
        Map<Vertex, Set<Set<Delegate>>> vertexStates = new HashMap<Vertex, Set<Set<Delegate>>>();
        for (Vertex vertex : graph) {
            vertexStates.put(vertex, new HashSet<Set<Delegate>>());
        }

        DFSVisit(graph, start, end, inFavourDelegates, result, vertexStates);
        return result;
    }

    /**
     * This method traverse the graph using DFS recursively. When revisit the
     * same vertex with the same state, it stops further recursion calls.
     * 
     * @param graph
     *            the graph representing the proceedings of the summit
     * @param start
     *            the vertex that the recursion call starts with
     * @param end
     *            the end vertex of the summit
     * @param inFavourDelegatesBeforeEvent
     *            the set of delegates that are in favour of the decision before
     *            visiting the start vertex
     * @param result
     *            the set of delegates that may be in favour of the decision
     *            after the summit
     * @param vertexStates
     *            the hash map that memorises all states (set of delegates that
     *            are in favour of the decision) when visiting each vertex
     */
    private static void DFSVisit(DGraphAdj<Vertex, Event> graph, Vertex start,
            Vertex end, Set<Delegate> inFavourDelegatesBeforeEvent,
            Set<Delegate> result,
            Map<Vertex, Set<Set<Delegate>>> vertexStates) {
        if (start == end) {
            result.addAll(inFavourDelegatesBeforeEvent);
        }
        // if add returns true, it means before adding, the set does not contain
        // the element -> we have not visit the vertex with the same state.
        // Otherwise, stops further recursion calls, as it will not produce new
        // outcomes.
        if (vertexStates.get(start).add(inFavourDelegatesBeforeEvent)) {
            for (AdjacentEdge<Vertex, Event> edge : graph.adjacent(start)) {
                Set<Delegate> inFavourDelegatesAfterEvent = processEvent(
                        edge.edgeInfo, inFavourDelegatesBeforeEvent);
                DFSVisit(graph, edge.target, end, inFavourDelegatesAfterEvent,
                        result, vertexStates);
            }
        }
    }

    /**
     * This method calculates the set of delegates that will be in favour of
     * the decision after the given event
     * 
     * @param event
     *            the event that is being processed
     * @param inFavourDelegatesBeforeEvent
     *            the set of delegates that are in favour of the decision before
     *            the event
     * @return This method returns a set of delegates that will be in favour of
     *         the decision after the event
     */
    private static Set<Delegate> processEvent(Event event,
            Set<Delegate> inFavourDelegatesBeforeEvent) {
        HashSet<Delegate> result = new HashSet<Delegate>();
        for (Delegate delegate : event) {
            for (Delegate dependentDelegate : event
                    .getDependentDelegates(delegate)) {
                if (inFavourDelegatesBeforeEvent.contains(dependentDelegate)) {
                    result.add(delegate);
                    break;
                }
            }
        }
        return result;
    }
}
