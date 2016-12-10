package assignment1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graphs.DGraphAdj;
import graphs.Vertex;

public class Test {

    public static void main(String[] args) {
        Delegate[] delegates = { new Delegate("d0"), new Delegate("d1"), new Delegate("d2"), new Delegate("d3") };
        
        
        Delegate[][][] eventArray = {
                { { delegates[0] }, { delegates[1] }, { delegates[2] },
                        { delegates[3] } },

                { {}, { delegates[0] }, { delegates[1], delegates[2] },
                        { delegates[3] } },

                { { delegates[0] }, { delegates[1] }, { delegates[2] },
                        { delegates[3] } }, };

        Event[] events = { createEvent(delegates, eventArray[0]),
                createEvent(delegates, eventArray[1]),
                createEvent(delegates, eventArray[2]) };
        
        
        
        Set<Delegate> initial = new HashSet<>();
        initial.add(delegates[0]);
        
        DGraphAdj<Vertex, Event> graph = new DGraphAdj<>();
        Vertex[] vertices = { new Vertex(), new Vertex(), new Vertex() };
        for (Vertex v : vertices) {
            graph.addVertex(v);
        }

        // the start and end vertices of the proceedings graph
        Vertex start = vertices[0];
        Vertex end = vertices[2];
        

        
        
        
        
        
    }
    
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
