import java.util.AbstractList;
import java.util.List;

public class Vector<E> extends AbstractList<E> implements List<E>
{
    protected Object[] elementData;
    protected float x;
    protected float y;


    public Vector(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        } else {
            this.elementData = new Object[initialCapacity];
        }
    }

    public Vector() {
        this(10);
    }

    @Override
    public E get(int i) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
