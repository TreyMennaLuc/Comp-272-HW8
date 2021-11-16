import java.util.Iterator;

public class Edge {
    int u;
    int v;
    boolean checked;
    Edge prev;
    Edge next;


    public void setNext(Edge n){
        next = n;
    }

    public void setPrev(Edge p){
        prev=p;
    }
    public Edge getNext() {
        return next;

    }
    public Edge getPrev() {
        return prev;

    }

    Edge()
    {
         u = 0;
         v = 0;
         checked = false;
         prev = null;
         next = null;
    }

    public boolean hasNext()
    {

        return this.getNext() != null;
    }
    public boolean hasUncheckedNext()
    {
        Edge next = this.getNext();
        return !next.isChecked();
    }
    public boolean hasUncheckedPrev()
    {
        Edge prev = this.getPrev();
        return !prev.isChecked();
    }

    Edge(int u, int v)
    {
        this.v = v;
        this.u = u;
        this.checked = false;
    }

    public void setEdge(int u, int v)
    {
        this.v = v;
        this.u = u;
        this.checked = false;
    }
    public void setChecked()
    {
        this.checked = true;
    }
    public void setUnchecked(){this.checked = false;}
    public boolean isChecked()
    {
        return this.checked;
    }

}
