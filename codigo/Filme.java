import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class Filme implements Registro {
    int id;
    String nome;
    int diaLancamento;
    String diretor;
    double avaliacao;
    


    public Filme(int i, String string, int j, String string2, double d) {
        id=i;
        nome=string;
        diaLancamento=j;
        diretor=string2;
        avaliacao= d;
    }
    public Filme(){
        
    }
    public int getId() {
        return id;

    }
    public String getNome() {
        return nome;
    }
    public int getdiaLancamento() {
        return diaLancamento;
    }
    public String getDiretor() {
        return diretor;
    }
    public double getAvaliacao (){
        return avaliacao;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setdiaLancamento(int diaLancamento) {
        this.diaLancamento = diaLancamento;
    }
    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }
    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public void setFilme(){
        setFilme("",-1,"", -1);
    }
    public void setFilme(String n, int d, String di, float a ){
        nome = n;
        diaLancamento = d;
        diretor = di;
        avaliacao = a;
    }
    public byte[] toByteArray () throws Exception {
        // out = indo para o arquivo
        // in = chegando no programa
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream da = new DataOutputStream(ba);
        da.writeInt(id);
        da.writeUTF(nome);
        da.writeInt(diaLancamento);
        da.writeUTF(diretor);
        da.writeDouble(avaliacao);
        return ba.toByteArray();
    }
    public void fromByteArray (byte [] ba) throws Exception{
        ByteArrayInputStream ba_novo = new ByteArrayInputStream(ba);
        DataInputStream da = new DataInputStream(ba_novo);
        id = da.readInt();
        nome = da.readUTF();
        diaLancamento = da.readInt();
        diretor = da.readUTF();
        avaliacao = da.readDouble();

    }
    @Override
    public int compareTo(Object b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
    
    
}
