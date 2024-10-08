
// On my honor:
// - I have not used source code obtained from another current or
// former student, or any other unauthorized source, either
// modified or unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.
import student.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * // -------------------------------------------------------------------------
 * /**
 * Test class for methods in Graph Class
 * 
 * @author shrut
 * @version Sep 19, 2024
 */
public class GraphTest extends TestCase {
    // ~ Fields ................................................................
    /*
     * initializes Graph graph
     */
    private Graph graph;

    // ~ Constructors ..........................................................

    /**
     * Constructor, initializes a new graph of size 5
     */
    public void setUp() {
        graph = new Graph(5);
    }
    // ~Public Methods ........................................................


    /**
     * Tests adding edge between two valid nodes
     */
    public void testAddEdgeValid() {
        graph.newNode(new Node(0));
        graph.newNode(new Node(1));

        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 0));

    }


    /**
     * Tests adding edge between non existent nodes
     */
    public void testAddEdgeNonExistent() {
        graph.addEdge(1, 2);

        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 1));
        assertNotNull(graph.getVertex()[1]);
        assertNotNull(graph.getVertex()[2]);

    }


    /**
     * Tests adding edge between two nodes that have edge already
     */
    public void testAddEdgeExisting() {

        graph.newNode(new Node(1));
        graph.newNode(new Node(2));
        graph.addEdge(1, 2);
        assertTrue(graph.hasEdge(1, 2));
        graph.addEdge(1, 2);
        assertTrue(graph.hasEdge(1, 2));
        graph.addEdge(2, 1);
        assertTrue(graph.hasEdge(2, 1));
    }


    /**
     * Tests adding edge between the same node
     */
    public void testAddEdgeSame() {
        graph.newNode(new Node(1));
        graph.addEdge(1, 1);
        assertTrue(graph.hasEdge(1, 1));
    }


    /**
     * Tests adding edge after expanding
     */
    public void testAddEdgeExpanding() {
        Graph testGraph = new Graph(1);
        testGraph.newNode(new Node(0));
        testGraph.newNode(new Node(1));
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        graph.addEdge(1, 2);
        assertTrue(graph.hasEdge(1, 2));
    }


    /**
     * Tests adding edge with out of bound index
     */
    public void testAddEdgeOutOfBounds() {
        try {
            graph.addEdge(-1, 3);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Out of bounds");
        }

        try {
            graph.addEdge(3, -1);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Out of bounds");
        }
    }


    /**
     * Tests removing edge between two valid nodes that have edge
     */
    public void testRemoveEdgeValid() {

        graph.addEdge(1, 2);
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 1));

        graph.removeEdge(1, 2);
        assertFalse(graph.hasEdge(1, 2));

    }


    /**
     * Tests removing edge when only dest exists
     */
    public void testRemoveEdgeDest() {
        graph.newNode(new Node(0));
        graph.removeEdge(0, 1);

    }


    /**
     * Tests removing edge when only src exists
     */
    public void testRemoveEdgeSrc() {
        graph.newNode(new Node(1));
        graph.removeEdge(0, 1);

    }


    /**
     * Tests removing edge that isn't there
     */
    public void testRemoveEdgeNotThere() {

        graph.removeEdge(0, 1);

        graph.newNode(new Node(0));
        graph.newNode(new Node(1));
        assertFalse(graph.hasEdge(0, 1));

        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
    }


    /**
     * Tests remove edge from null node
     */
    public void testRemoveEdgeNullNode() {
        graph.newNode(new Node(0));
        assertNull(graph.getVertex()[1]);
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
    }


    /**
     * Tests remove edge from empty graph
     */
    public void testRemoveEdgeEmptyGraph() {
        assertNull(graph.getVertex()[2]);
        assertNull(graph.getVertex()[3]);
        graph.removeEdge(2, 3);
        assertFalse(graph.hasEdge(2, 3));
    }


    /**
     * Tests expand graph
     */
    public void testExpand() {
        for (int i = 0; i < 5; i++) {
            graph.newNode(new Node(i));
        }
        // expands
        graph.newNode(new Node(6));
    }


    /**
     * Tests the union and find method after an edge is added between two valid
     * nodes
     */
    public void testUnionFind() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 0);
        graph.addEdge(3, 4);

        assertTrue(graph.hasEdge(1, 2)); // both connected
        assertFalse(graph.hasEdge(1, 3)); // they not connected

        graph.unionConnectedNodes();

        int a = graph.find(1);
        int b = graph.find(2);

        assertEquals(a, b);
        assertFalse(graph.find(1) == graph.find(3));
    }


    /**
     * Tests print graph
     */
    public void testPrintGraph() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        graph.printGraph();
        System.setOut(System.out);
        String expectedOutput = "There are 2 connected components\n"
            + "The largest connected component has 3 elements";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }


    /**
     * Tests a new node
     */
    public void testNewNode() {
        int initialMaxSize = 4;
        graph = new Graph(initialMaxSize);

        Node node1 = new Node(0);
        graph.newNode(node1);
        assertNotNull(graph.getVertex()[node1.getIndex()]);
        assertEquals(0, graph.getVertex()[node1.getIndex()].getSize());
        assertEquals(1, graph.getNumberOfNodes());

        // Continue inserting nodes
        Node node2 = new Node(1);
        graph.newNode(node2);
        assertNotNull(graph.getVertex()[node2.getIndex()]);
        assertEquals(0, graph.getVertex()[node2.getIndex()].getSize());
        assertEquals(2, graph.getNumberOfNodes());

        Node node3 = new Node(2);
        graph.newNode(node3);
        assertNotNull(graph.getVertex()[node3.getIndex()]);
        assertEquals(0, graph.getVertex()[node3.getIndex()].getSize());
        assertEquals(3, graph.getNumberOfNodes());

        // make the graph expand
        Node node4 = new Node(3);
        graph.newNode(node4);

        // Assert that expansion has occurred
        assertEquals(8, graph.getVertex().length);
        assertEquals(4, graph.getNumberOfNodes());
        // Insert another node post-expansion
        Node node5 = new Node(4);
        graph.newNode(node5);
        assertNotNull(graph.getVertex()[node5.getIndex()]);
        assertEquals(0, graph.getVertex()[node5.getIndex()].getSize());
        assertEquals(5, graph.getNumberOfNodes());
    }


    /**
     * Tests removing a node with edges
     */
    public void testRemoveNode() {
        // Add nodes and edges
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        graph.newNode(node1);
        graph.newNode(node2);
        graph.newNode(node3);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));

        assertNotNull(graph.getVertex()[1]);
        assertNotNull(graph.getVertex()[2]);
        assertNotNull(graph.getVertex()[3]);

        assertEquals(graph.getVertex()[1].getSize(), 1);

        graph.removeNode(node2);

        assertNull(graph.getVertex()[2]);

        assertFalse(graph.getVertex()[1].contains(2));
        assertFalse(graph.getVertex()[3].contains(2));

        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 3));

    }


    /**
     * Tests removing node without edges
     */
    public void testRemoveNodeNoEdge() {
        graph.newNode(new Node(0));
        assertNotNull(graph.getVertex()[0]);
        graph.removeNode(new Node(0));
        assertNull(graph.getVertex()[(new Node(0)).getIndex()]);

    }


    /**
     * Tests removing node that doesn't exist
     */
    public void testRemoveNonExistingNode() {

        Node testNode = new Node(0);
        graph.newNode(testNode);

        Node nonExistingNode = new Node(4);
        graph.removeNode(nonExistingNode);

        assertNotNull(graph.getVertex()[testNode.getIndex()]);
        assertNull(graph.getVertex()[nonExistingNode.getIndex()]);

    }


    /**
     * Tests removing node with out of bounds index
     */
    public void testRemoveOutOfBounds() {
        Node negative = new Node(-1);
        Node greater = new Node(6);

        graph.removeNode(negative);
        graph.removeNode(greater);

        assertNull(graph.getVertex()[0]);
        assertNull(graph.getVertex()[1]);
        assertNull(graph.getVertex()[2]);
        assertNull(graph.getVertex()[3]);
        assertNull(graph.getVertex()[4]);

    }


    /**
     * Tests removing a null node
     */
    public void testRemoveNodeNull() {
        try {
            graph.removeNode(null);
        }
        catch (NullPointerException e) {
            System.out.println("Null point exception");
        }
        assertNull(graph.getVertex()[0]);
        assertNull(graph.getVertex()[1]);
        assertNull(graph.getVertex()[2]);
        assertNull(graph.getVertex()[3]);
        assertNull(graph.getVertex()[4]);
        assertEquals(graph.getNumberOfNodes(), 0);

        graph.newNode(new Node(1));
        assertNotNull(graph.getVertex()[1]);

    }


    /**
     * Tests find method
     */
    public void testFind() {
        graph.newNode(new Node(0));
        assertEquals(graph.find(0), 0);
        graph.newNode(new Node(1));
        graph.addEdge(0, 1);
        graph.unionConnectedNodes();
        assertEquals(graph.find(1), 0);
        assertEquals(graph.find(0), 0);

        graph.newNode(new Node(2));
        graph.addEdge(0, 2);

    }


    /**
     * Tests advanced remove
     */
    public void testAdvancedRemove() {
        Node artistA = new Node(0);
        Node songA = new Node(1);
        Node songC = new Node(2);
        Node artistB = new Node(3);
        Node songB = new Node(4);
        Node songD = new Node(5);

        graph.newNode(artistA);
        graph.newNode(songA);
        graph.addEdge(0, 1);
        graph.printGraph();
        assertEquals(graph.getConnectedComponents(), 1);

        graph.newNode(songC);
        graph.printGraph();
        assertEquals(graph.getConnectedComponents(), 2);

        graph.addEdge(0, 2);
        graph.printGraph();
        assertEquals(graph.getConnectedComponents(), 1);

        graph.newNode(artistB);
        graph.addEdge(3, 2);
        graph.unionConnectedNodes();
        assertEquals(graph.find(3), 0);

        graph.newNode(songB);
        graph.addEdge(3, 4);
        graph.unionConnectedNodes();
        assertEquals(graph.find(4), 0);

        graph.newNode(songD);
        graph.addEdge(3, 5);
        graph.unionConnectedNodes();
        assertEquals(graph.find(5), 0);

        graph.removeNode(artistB);

        graph.printGraph();
        assertEquals(graph.getConnectedComponents(), 3);

    }


    /**
     * Tests many possibilities of remove node
     */
    public void testRemoveNode2() {

        // removing a negative node
        Node negative = new Node(-1);
        graph.removeNode(negative);
        assertTrue(graph.getNumberOfNodes() == 0);

        // removing out of bounds node
        Node outOfBounds = new Node(11);
        graph.removeNode(outOfBounds);
        assertTrue(graph.getNumberOfNodes() == 0);

        Node nullNode = new Node(1);
        graph.removeNode(nullNode);
        assertTrue(graph.getNumberOfNodes() == 0);

        Node valid = new Node(2);
        graph.newNode(valid);
        graph.addEdge(2, 1);
        graph.addEdge(2, 3);
        graph.removeNode(valid);
        assertTrue(graph.getNumberOfNodes() == 0);

        Node testNode = new Node(1);
        graph.newNode(testNode);
        assertTrue(graph.getNumberOfNodes() == 1);
        graph.removeNode(testNode);
        assertTrue(graph.getNumberOfNodes() == 0);
    }


    /**
     * test for union
     */
    public void testUnion() {
        Node root = new Node(0);
        Node bottom1 = new Node(1);
        Node bottom2 = new Node(2);

        graph.newNode(root);
        graph.newNode(bottom1);
        graph.newNode(bottom2);

        graph.addEdge(0, 2);
        graph.addEdge(0, 1);

        graph.unionConnectedNodes();
        assertEquals(graph.getWeight(0), 3);

        Node bottom3 = new Node(3);
        graph.newNode(bottom3);

        graph.addEdge(0, 3);
        graph.unionConnectedNodes();
        assertEquals(graph.getWeight(0), 4);
        assertEquals(graph.getRoot(0), -1);
        assertEquals(graph.getRoot(3), 0);

    }


    /**
     * Add edge but song and artist are in the graph already
     */
    public void testAddEdgeNoDuplicate() {
        graph = new Graph(10);
        graph.getVertex()[0] = new DoubleLL<>();
        graph.getVertex()[2] = new DoubleLL<>();
        graph.getVertex()[0].insert(2);
        graph.getVertex()[2].insert(0);
        assertTrue(graph.hasEdge(0, 2));
        graph.addEdge(0, 2);
        assertEquals(1, graph.getVertex()[0].getSize());
        assertEquals(1, graph.getVertex()[2].getSize());
    }

}
