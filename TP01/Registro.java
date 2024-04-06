import java.io.IOException;

public interface Registro extends Comparable<Object> {

    int getId();

    void setId(int id);

    byte[] toByteArray() throws IOException;

    void fromByteArray(byte[] byteArray) throws IOException;

    @Override
    int compareTo(Object b);

}
