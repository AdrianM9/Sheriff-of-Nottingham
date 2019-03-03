package factories;

/**
 * Generic interface used for implementing factories which generates elements of a type.
 * @param <T> type of the elements which are created
 * @param <U> type of the parameter which will be used for knowing which element to create
 */
public interface IFactory<T, U> {
    /**
     * Creates and returns an object of type {@code T} based on the parameter of type {@code U}.
     * @param type parameter which will be used for choosing which object to create and return
     * @return object of type {@code T}
     */
    T createElement(U type);
}
