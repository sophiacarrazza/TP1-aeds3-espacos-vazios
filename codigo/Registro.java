public interface Registro extends Comparable<Object> {

    public int getId();

    public void setId(int id);

    public byte[] toByteArray() throws Exception;

    public void fromByteArray(byte [] ba) throws Exception;

    public int compareTo(Object b);

}
