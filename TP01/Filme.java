import java.io.*;

public class Filme implements Registro {
    private int id;
    private String nome;
    private int diaLancamento;
    private String diretor;
    private double avaliacao;

    public Filme(int id, String nome, int diaLancamento, String diretor, double avaliacao) {
        this.id = id;
        this.nome = nome;
        this.diaLancamento = diaLancamento;
        this.diretor = diretor;
        this.avaliacao = avaliacao;
    }

    public Filme() {}

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDiaLancamento() {
        return diaLancamento;
    }

    public void setDiaLancamento(int diaLancamento) {
        this.diaLancamento = diaLancamento;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    // Métodos para definir e obter os atributos do filme
    public void setFilme() {
        setFilme("", -1, "", -1);
    }

    public void setFilme(String nome, int diaLancamento, String diretor, double avaliacao) {
        this.nome = nome;
        this.diaLancamento = diaLancamento;
        this.diretor = diretor;
        this.avaliacao = avaliacao;
    }

    // Implementação dos métodos da interface Registro
    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);
        dataOutput.writeInt(id);
        dataOutput.writeUTF(nome);
        dataOutput.writeInt(diaLancamento);
        dataOutput.writeUTF(diretor);
        dataOutput.writeDouble(avaliacao);
        return byteArrayOutput.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] byteArray) throws IOException {
        ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(byteArray);
        DataInputStream dataInput = new DataInputStream(byteArrayInput);
        id = dataInput.readInt();
        nome = dataInput.readUTF();
        diaLancamento = dataInput.readInt();
        diretor = dataInput.readUTF();
        avaliacao = dataInput.readDouble();
    }

    @Override
    public int compareTo(Object b) {
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
