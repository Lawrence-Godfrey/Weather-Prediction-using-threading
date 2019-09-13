/**
 * Simple class to hold x and y value of vector
 * @author Lawrence Godfrey
 */
public class Vector
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

    public void divide(Vector numerator, int divider)
    {
        numerator.x=numerator.x/divider;
        numerator.y=numerator.y/divider;;
    }
}
