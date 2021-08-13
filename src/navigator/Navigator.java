package navigator;

public abstract class Navigator <X> {

    /*Data Members*/
    protected int currentIndex = -1;
    protected int size;

    /*Methods*/
    //Has next element
    public boolean hasNextElement () {
        return this.currentIndex < this.size - 1;
    }

    //Get next element
    abstract public X getNextElement () throws Exception;

    //Has previous element
    public boolean hasPreviousElement () {
        return this.currentIndex > 0;
    }

    //Get previous element
    abstract public X getPreviousElement () throws Exception;
}
