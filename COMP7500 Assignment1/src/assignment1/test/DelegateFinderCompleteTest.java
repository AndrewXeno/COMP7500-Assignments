package assignment1.test;

import assignment1.*;
import graphs.DGraphAdj;
import graphs.Vertex;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link DelegateFinder} implementation class.
 * 
 * We will use a more comprehensive test suite to test your code, so you should
 * add your own tests to this test suite to help you to debug your
 * implementation.
 */
public class DelegateFinderCompleteTest {

	/**
	 * A basic test based on the example from the assignment handout (with 0 and
	 * 1 switched)
	 **/
	@Test(timeout = 500)
	public void basicTest() throws Exception {

		// the delegates who will take part in the summit
		Delegate[] delegates = new Delegate[4];
		for (int i = 0; i < delegates.length; i++) {
			delegates[i] = new Delegate("d" + i);
		}

		// create the events in the summit
		Delegate[][][] eventArray = {
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] } },

				{ { delegates[1] }, {}, { delegates[0], delegates[2] },
						{ delegates[3] } },

				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] } }, };

		Event[] events = new Event[eventArray.length];
		for (int i = 0; i < events.length; i++) {
			events[i] = createEvent(delegates, eventArray[i]);
		}

		// the graph describing the possible proceedings of the summit
		DGraphAdj<Vertex, Event> graph = new DGraphAdj<>();
		Vertex[] vertices = new Vertex[3];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex();
			graph.addVertex(vertices[i]);
		}
		graph.addEdge(vertices[0], vertices[1], events[0]);
		graph.addEdge(vertices[1], vertices[2], events[1]);
		graph.addEdge(vertices[2], vertices[1], events[2]);

		// the start and end vertices of the proceedings graph
		Vertex start = vertices[0];
		Vertex end = vertices[2];

		// the delegates in favour of the decision at the start of the summit
		Set<Delegate> initial = new HashSet<>();
		initial.add(delegates[1]);

		// the expected result of the test
		Set<Delegate> expectedResult = new HashSet<>();
		expectedResult.add(delegates[0]);
		expectedResult.add(delegates[2]);

		// the actual result of the test
		Set<Delegate> actualResult = DelegateFinder.findDelegates(
				new HashSet<Delegate>(Arrays.asList(delegates)), graph, start,
				end, initial);

		Assert.assertEquals(expectedResult, actualResult);
	}

	/** A test of a directed acyclic graph **/
	@Test(timeout = 500)
	public void dagTest() throws Exception {

		// the delegates who will take part in the summit
		Delegate[] delegates = new Delegate[5];
		for (int i = 0; i < delegates.length; i++) {
			delegates[i] = new Delegate("d" + i);
		}

		// create the events in the summit
		Delegate[][][] eventArray = {
				// e0
				{
						{ delegates[1], delegates[3] },
						{ delegates[1] },
						{ delegates[0] },
						{ delegates[0], delegates[2], delegates[3],
								delegates[4] }, { delegates[4] } },
				// e1
				{ {}, { delegates[0] }, { delegates[2] }, { delegates[3] },
						{ delegates[4] } },

				// e2
				{ { delegates[0] }, { delegates[2] }, { delegates[2] },
						{ delegates[2] }, { delegates[3], delegates[4] } },

				// e3
				{ { delegates[0] }, { delegates[2] }, { delegates[2] },
						{ delegates[3] }, { delegates[4] } },

				// e4
				{ { delegates[0] }, { delegates[2] },
						{ delegates[1], delegates[3] }, { delegates[1] },
						{ delegates[4] } },

				// e5
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[2] }, { delegates[4] } } };

		Event[] events = new Event[eventArray.length];
		for (int i = 0; i < events.length; i++) {
			events[i] = createEvent(delegates, eventArray[i]);
		}

		// the graph describing the possible proceedings of the summit
		DGraphAdj<Vertex, Event> graph = new DGraphAdj<>();
		Vertex[] vertices = new Vertex[5];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex();
			graph.addVertex(vertices[i]);
		}
		graph.addEdge(vertices[0], vertices[1], events[0]);
		graph.addEdge(vertices[0], vertices[2], events[1]);
		graph.addEdge(vertices[1], vertices[2], events[2]);
		graph.addEdge(vertices[1], vertices[3], events[3]);
		graph.addEdge(vertices[2], vertices[3], events[4]);
		graph.addEdge(vertices[3], vertices[4], events[5]);

		// the start and end vertices of the proceedings graph
		Vertex start = vertices[0];
		Vertex end = vertices[4];

		// the delegates in favour of the decision at the start of the summit
		Set<Delegate> initial = new HashSet<>();
		initial.add(delegates[1]);
		initial.add(delegates[2]);

		// the expected result of the test
		Set<Delegate> expectedResult = new HashSet<>();
		expectedResult.add(delegates[0]);
		expectedResult.add(delegates[1]);
		expectedResult.add(delegates[4]);

		// the actual result of the test
		Set<Delegate> actualResult = DelegateFinder.findDelegates(
				new HashSet<Delegate>(Arrays.asList(delegates)), graph, start,
				end, initial);

		Assert.assertEquals(expectedResult, actualResult);
	}

	/** Test of a cyclic graph -- one loop **/
	@Test(timeout = 500)
	public void oneLoopTest() throws Exception {

		// the delegates who will take part in the summit
		Delegate[] delegates = new Delegate[6];
		for (int i = 0; i < delegates.length; i++) {
			delegates[i] = new Delegate("d" + i);
		}

		// create the events in the summit
		Delegate[][][] eventArray = {
				// e0
				{
						{ delegates[1], delegates[3] },
						{ delegates[1] },
						{},
						{ delegates[0], delegates[2], delegates[3],
								delegates[4] }, { delegates[4] },
						{ delegates[0] } },
				// e1
				{ {}, {}, { delegates[2] }, { delegates[3] }, { delegates[4] },
						{} },

				// e2
				{ { delegates[0] }, { delegates[2] }, { delegates[2] },
						{ delegates[2] }, { delegates[3], delegates[4] },
						{ delegates[5] } },

				// e3
				{ { delegates[0] }, { delegates[2] }, { delegates[2] },
						{ delegates[3] }, { delegates[4] }, {} },

				// e4
				{ { delegates[0] }, { delegates[2] },
						{ delegates[1], delegates[3] }, { delegates[1] },
						{ delegates[4] }, { delegates[5] } },

				// e5
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[2] }, { delegates[4] }, { delegates[5] } },

				// e6
				{ { delegates[0] }, {}, {}, {}, {}, {} }

		};

		Event[] events = new Event[eventArray.length];
		for (int i = 0; i < events.length; i++) {
			events[i] = createEvent(delegates, eventArray[i]);
		}

		// the graph describing the possible proceedings of the summit
		DGraphAdj<Vertex, Event> graph = new DGraphAdj<>();
		Vertex[] vertices = new Vertex[5];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex();
			graph.addVertex(vertices[i]);
		}
		graph.addEdge(vertices[0], vertices[1], events[0]);
		graph.addEdge(vertices[0], vertices[2], events[1]);
		graph.addEdge(vertices[1], vertices[2], events[2]);
		graph.addEdge(vertices[1], vertices[3], events[3]);
		graph.addEdge(vertices[2], vertices[3], events[4]);
		graph.addEdge(vertices[3], vertices[4], events[5]);
		graph.addEdge(vertices[4], vertices[0], events[6]);

		// the start and end vertices of the proceedings graph
		Vertex start = vertices[0];
		Vertex end = vertices[4];

		// the delegates in favour of the decision at the start of the summit
		Set<Delegate> initial = new HashSet<>();
		initial.add(delegates[1]);
		initial.add(delegates[2]);

		// the expected result of the test
		Set<Delegate> expectedResult = new HashSet<>();
		expectedResult.add(delegates[0]);
		expectedResult.add(delegates[1]);
		expectedResult.add(delegates[4]);
		expectedResult.add(delegates[5]);

		// the actual result of the test
		Set<Delegate> actualResult = DelegateFinder.findDelegates(
				new HashSet<Delegate>(Arrays.asList(delegates)), graph, start,
				end, initial);

		Assert.assertEquals(expectedResult, actualResult);
	}

	/** A test of a cyclic graph -- many loops **/
	@Test(timeout = 500)
	public void cyclicGraphTest() throws Exception {

		// the delegates who will take part in the summit
		Delegate[] delegates = new Delegate[6];
		for (int i = 0; i < delegates.length; i++) {
			delegates[i] = new Delegate("d" + i);
		}

		// create the events in the summit
		Delegate[][][] eventArray = {

				// e0
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] }, { delegates[5] }, { delegates[4] } },

				// e1
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] }, { delegates[4] }, { delegates[5] } },

				// e2
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] }, { delegates[4] }, { delegates[5] } },

				// e3
				{ { delegates[1] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] }, { delegates[4] }, { delegates[5] } },

				// e4
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] }, { delegates[4] }, { delegates[5] } },

				// e5
				{ { delegates[0] }, { delegates[2] }, { delegates[2] },
						{ delegates[3] }, { delegates[4] }, { delegates[5] } },

				// e6
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] }, { delegates[4] }, { delegates[5] } },

				// e7
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] }, { delegates[4] }, { delegates[5] } },

				// e8
				{ { delegates[0] }, { delegates[1] }, { delegates[3] },
						{ delegates[4] }, { delegates[5] }, { delegates[5] } },

		};

		Event[] events = new Event[eventArray.length];
		for (int i = 0; i < events.length; i++) {
			events[i] = createEvent(delegates, eventArray[i]);
		}

		// the graph describing the possible proceedings of the summit
		DGraphAdj<Vertex, Event> graph = new DGraphAdj<>();
		Vertex[] vertices = new Vertex[7];
		for (int i = 0; i < 7; i++) {
			vertices[i] = new Vertex();
			graph.addVertex(vertices[i]);
		}
		graph.addEdge(vertices[0], vertices[1], events[0]);
		graph.addEdge(vertices[1], vertices[2], events[1]);
		graph.addEdge(vertices[1], vertices[4], events[2]);
		graph.addEdge(vertices[2], vertices[3], events[3]);
		graph.addEdge(vertices[3], vertices[1], events[4]);
		graph.addEdge(vertices[4], vertices[5], events[5]);
		graph.addEdge(vertices[5], vertices[1], events[6]);
		graph.addEdge(vertices[1], vertices[6], events[7]);
		graph.addEdge(vertices[6], vertices[1], events[8]);

		// the start and end vertices of the proceedings graph
		Vertex start = vertices[0];
		Vertex end = vertices[6];

		// the delegates in favour of the decision at the start of the summit
		Set<Delegate> initial = new HashSet<>();
		initial.add(delegates[5]);

		// the expected result of the test
		Set<Delegate> expectedResult = new HashSet<>();
		expectedResult.add(delegates[0]);
		expectedResult.add(delegates[1]);
		expectedResult.add(delegates[2]);
		expectedResult.add(delegates[3]);
		expectedResult.add(delegates[4]);

		// the actual result of the test
		Set<Delegate> actualResult = DelegateFinder.findDelegates(
				new HashSet<Delegate>(Arrays.asList(delegates)), graph, start,
				end, initial);

		Assert.assertEquals(expectedResult, actualResult);
	}

	/**
	 * A large test with a cycle: typical implementation should visit vertices
	 * the maximum number of times
	 **/
	@Test(timeout = 500)
	public void largeCycleTest() throws Exception {

		// the delegates who will take part in the summit
		Delegate[] delegates = new Delegate[30];
		for (int i = 0; i < delegates.length; i++) {
			delegates[i] = new Delegate("d" + i);
		}

		// create the events in the summit

		// create an event in which the first half of the delegates are
		// influenced by the previous delegate, and the second half are
		// influenced by every delegate in the second half.
		Delegate[][] lastEventArray = new Delegate[delegates.length][1];
		lastEventArray[0][0] = delegates[0];
		// the first half the delegates are influenced by the previous delegate
		for (int i = 1; i < (delegates.length) / 2; i++) {
			lastEventArray[i][0] = delegates[i - 1];
		}
		// the second half are influenced by the second half
		Delegate[] half = new Delegate[delegates.length - (delegates.length)
				/ 2];
		for (int i = 0; i < half.length; i++) {
			half[i] = delegates[i + (delegates.length) / 2];
		}
		for (int i = (delegates.length) / 2; i < delegates.length; i++) {
			lastEventArray[i] = half;
		}
		Event lastEvent = createEvent(delegates, lastEventArray);

		// the identity event
		Delegate[][] idEventArray = new Delegate[delegates.length][1];
		for (int i = 0; i < delegates.length; i++) {
			idEventArray[i][0] = delegates[i];
		}
		Event idEvent = createEvent(delegates, idEventArray);

		// the graph describing the possible proceedings of the summit
		DGraphAdj<Vertex, Event> graph = new DGraphAdj<>();
		Vertex[] vertices = new Vertex[40];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex();
			graph.addVertex(vertices[i]);
		}
		// connect the vertices in a ring
		for (int i = 0; i < vertices.length - 1; i++) {
			graph.addEdge(vertices[i], vertices[i + 1], idEvent);
		}
		graph.addEdge(vertices[vertices.length - 1], vertices[0], lastEvent);

		// the start and end vertices of the proceedings graph
		Vertex start = vertices[0];
		Vertex end = vertices[0];

		// the delegates in favour of the decision at the start of the summit
		Set<Delegate> initial = new HashSet<>();
		initial.add(delegates[0]);

		// the expected result of the test: the first half of the delegates
		Set<Delegate> expectedResult = new HashSet<>();
		for (int i = 0; i < (delegates.length) / 2; i++) {
			expectedResult.add(delegates[i]);
		}

		// the actual result of the test
		Set<Delegate> actualResult = DelegateFinder.findDelegates(
				new HashSet<Delegate>(Arrays.asList(delegates)), graph, start,
				end, initial);

		Assert.assertEquals(expectedResult, actualResult);
	}

	/*-------------------------------------------------------------------*/

	/**
	 * A helper function for creating an event from an array of the delegates
	 * who will take part in the event, and an array of arrays describing the
	 * corresponding dependencies for each delegate.
	 */
	private static Event createEvent(Delegate[] delegatesArray,
			Delegate[][] dependentArray) {
		Map<Delegate, Set<Delegate>> event = new HashMap<>();
		for (int i = 0; i < delegatesArray.length; i++) {
			event.put(delegatesArray[i],
					new HashSet<Delegate>(Arrays.asList(dependentArray[i])));
		}
		return new Event(event);
	}

}
