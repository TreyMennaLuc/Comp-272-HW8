
import java.io.*;
import java.util.*;

public class homework8 {
    ArrayList<MyLinkedList<Integer>> vertexSet = new ArrayList<>();
    ArrayList<Edge> edgeSet = new ArrayList<>();
    int[] findSet; //given index find set#

    public void setFindSetSize(int size) {
        this.findSet = new int[size];
    }

    public void addEdgeSet(Edge newEdge) {
        this.edgeSet.add(newEdge);
        if(this.edgeSet.size() > 0) {
            Edge prev = edgeSet.get(edgeSet.size()-1);
            prev.setNext(newEdge);
            newEdge.setPrev(prev);
        }
    }

    public void refreshFindSet()
    {
        this.findSet = new int[60000];
        for(int x = 0; x < 60000; x++)
        {
            this.findSet[x] = x;
        }
    }
    public void refreshVertexSet()
    {
        this.vertexSet.clear();
        for(int x = 0; x < 60000; x++)
        {
            try {
                this.vertexSet.get(x).addLast(x);
            } catch (IndexOutOfBoundsException e) {
                MyLinkedList<Integer> newList = new MyLinkedList<>();
                newList.addLast(x);
                this.vertexSet.add(newList);//Add something to fill this position.
            }
        }
    }
    public void uncheckEdges() {
        for (Edge uncheckIt : edgeSet) {
            uncheckIt.setUnchecked();
        }
    }

    public int ccCount()
    {
        HashSet<Integer> ccCount = new HashSet<>();
        for (int i : findSet) {
            ccCount.add(i);
        }
        return ccCount.size();
    }

    public int largestCC()
    {
        int largestCC = 0;
        for (MyLinkedList<Integer> currentSet : vertexSet) {
            int mLLSize = currentSet.size;
            if (mLLSize > largestCC) {
                largestCC = mLLSize;
            }
        }
        return largestCC;
    }

    public int isCCTree()
    {
        int numOfTrees = 0;
        int numEdges;
        //To see if CC is a tree check if edges are == vertices-1
        for (MyLinkedList<Integer> integerMyLinkedList : this.vertexSet) {
            numEdges = 0;
            MyLinkedList<Integer> currentSet = integerMyLinkedList;
            if (!currentSet.isEmpty()) {
                for (int j = 0; j < this.edgeSet.size(); j++) {
                    Edge currentEdge = this.edgeSet.get(j);
                    if (currentSet.contains(currentEdge.u) || (currentSet.contains(currentEdge.v))) {
                        numEdges++;
                    }

                }
            }
            if (numEdges == currentSet.size - 1) {
                numOfTrees++;
            }

        }
        return numOfTrees;
    }
    //Breadth first search - use queue
    /*Look at q then add neighbors to back of queue
Have to BFS each component
        */
    public void bFS() {
        refreshFindSet();
        refreshVertexSet();
        System.out.println("Running breadth first search");
        Queue<Edge> queue = new LinkedList<>();
        queue.add(this.edgeSet.get(0));
        Iterator<Edge> i = this.edgeSet.iterator();
        while (i.hasNext()) {
            Edge n = i.next();
            if (!n.isChecked()) {
                queue.add(n);
            }
        }
        while (queue.size() != 0) {
                Edge n = queue.poll();
                int value1 = n.u;
                int value2 = n.v;
                int value1SetNum = this.findSet[value1];
                int value2SetNum = this.findSet[value2];
                if(!(value1SetNum == value2SetNum))
                {
                    this.mergeComponents(value1, value2);
                }
                n.setChecked();
            }

        for(int x = 0; x < findSet.length; x++)
        {
            int setNum = findSet[x];
            try {
                this.vertexSet.get(setNum).addLast(x);
            } catch (IndexOutOfBoundsException e) {
                MyLinkedList<Integer> newList = new MyLinkedList<>();
                newList.addLast(x);
                this.vertexSet.add(newList);//Add something to fill this position.
            }
        }
        System.out.println("Number of CC's: " + ccCount());
        System.out.println("Largest CC: " + largestCC());
        //System.out.println("Number of Trees: " + isCCTree());
        uncheckEdges();
    }

    //Depth first search

    public void dfs(Edge current)
    {
        if(!current.isChecked()) {
            Queue<Edge> queue = new LinkedList<>();
            int value1 = current.u;
            int value2 = current.v;
            int value1SetNum = this.findSet[value1];
            int value2SetNum = this.findSet[value2];
            if (!(value1SetNum == value2SetNum)) {
                this.mergeComponents(value1, value2);
            }
            current.setChecked();
            //For each unmarked neighbor of x of v chose one and put the others on the back burner
            while (current.hasUncheckedNext()) {
               queue.add(current.getNext());
            }
            while (current.hasUncheckedPrev()) {
                queue.add(current.getPrev());
            }
            while(!queue.isEmpty())
            {
                dfs(queue.poll());
            }
        }
    }

    public void traverseGraph() {
        refreshFindSet();
        refreshVertexSet();
        System.out.println("Traversing using depth first search");
        for (Edge thisEdge : edgeSet) {
            if (!thisEdge.isChecked()) {
                dfs(thisEdge);
            }
        }
            for(int x = 0; x < findSet.length; x++)
            {
                int setNum = findSet[x];
                try {
                    this.vertexSet.get(setNum).addLast(x);
                } catch (IndexOutOfBoundsException e) {
                    MyLinkedList<Integer> newList = new MyLinkedList<>();
                    newList.addLast(x);
                    this.vertexSet.add(newList);//Add something to fill this position.
                }
            }
            System.out.println("Number of CC's: " + ccCount());
            System.out.println("Largest CC: " + largestCC());
            //System.out.println("Number of Trees: " + isCCTree());
    }
    //On the Fly
    public void onTheFlyAlgorithm()
    {
        refreshVertexSet();
        refreshFindSet();
        System.out.println("Running on the fly algorithm");
        for (Edge currentEdge : edgeSet) {
            int value1 = currentEdge.u;
            int value2 = currentEdge.v;
            int value1SetNum = this.findSet[value1];
            int value2SetNum = this.findSet[value2];
            if (!(value1SetNum == value2SetNum)) {
                this.mergeComponents(value1, value2);
            }
        }
        for(int x = 0; x < findSet.length; x++)
        {
            int setNum = findSet[x];
            try {
                this.vertexSet.get(setNum).addLast(x);
            } catch (IndexOutOfBoundsException e) {
                MyLinkedList<Integer> newList = new MyLinkedList<>();
                newList.addLast(x);
                this.vertexSet.add(setNum,newList);//Add something to fill this position.
            }
        }
        System.out.println("Number of CC's: " + ccCount());
        System.out.println("Largest CC: " + largestCC());
        //System.out.println("Number of Trees: " + isCCTree());


    }
    public void mergeComponents(int set1, int set2)
    {
        MyLinkedList<Integer> setList1 = vertexSet.get(set1);
        MyLinkedList<Integer> setList2 = vertexSet.get(set2);
        if(setList1.size >=  setList2.size)
        {
            setList1.appendList(setList2);
            for(int x = 0;x < findSet.length; x++)
            {
                if(findSet[x] == set2)
                {
                    findSet[x] = set1;
                }
            }
        }
        else if(setList1.size <  setList2.size)
        {
            setList2.appendList(setList1);
            for(int x = 0;x < findSet.length; x++)
            {
                if(findSet[x] == set1)
                {
                    findSet[x] = set2;
                }
            }
        }

        //can use MyLinkedList Append list
        //append smaller list to bigger list
        //find the set for u and find the set for v
        //if they are not equal - quit
        //else, go to vertexSets and find the sizes of the sets corresponding to the set number obtained from findSet
        //The larger set is called p and the smaller is called q
        //1. call append list in MyLinkedList to append q to p
        //2. go to findSet and modify the label of the smaller set to the larger label.
    }
    public static void main(String[] args)
    {
        homework8 edgeS = new homework8();
        edgeS.setFindSetSize(60000);
        File file = new File("artist_edges.csv");
        System.out.println("Creating edgeSet");
        try {
            Scanner sc = new Scanner(file).useDelimiter("\\D");
            while(sc.hasNext())
            {
                        int a1 = sc.nextInt();
                        int a2 = sc.nextInt();
                        Edge edge = new Edge(a1,a2);
                        edgeS.addEdgeSet(edge);
            }
        }
        catch (FileNotFoundException e) {
        System.out.println("file not found");
    }
        edgeS.onTheFlyAlgorithm();
        edgeS.bFS();
        edgeS.traverseGraph();
    }

}
