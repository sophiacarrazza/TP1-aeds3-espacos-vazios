
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro> {

  protected RandomAccessFile arquivo;
  protected Constructor<T> construtor;
  
  String nomeArquivo = "";
  final protected int TAM_CABECALHO = 4;

  public Arquivo(String filmes , Constructor<T> c) throws Exception {
    this.nomeArquivo = filmes;
    this.construtor = c;
    
    arquivo = new RandomAccessFile(filmes, "rw");
    if (arquivo.length() < TAM_CABECALHO) {
      arquivo.seek(0);
      arquivo.writeInt(0);
    }
  }

  public int create(T obj) throws Exception {
    arquivo.seek(0);
    int ultimoID = arquivo.readInt();
    ultimoID++;
    arquivo.seek(0);
    arquivo.writeInt(ultimoID);
    obj.setId(ultimoID);

    byte[] ba = obj.toByteArray();
    int tam = ba.length;
    
          arquivo.seek(arquivo.length());
        // long endereco = arquivo.getFilePointer();
          
          arquivo.write(' '); // lápide
          arquivo.writeInt(tam);
          
          arquivo.write(ba);
        
        
        
    
        return obj.getId();
      }

  public boolean Update(T obj){
    
    int id = obj.getId();
    try{
      arquivo.seek(TAM_CABECALHO);
      while(arquivo.getFilePointer() < arquivo.length()){

        byte[] ba = obj.toByteArray();
        int objTam = ba.length;


        long lapide = arquivo.getFilePointer();
        byte lapideValor = arquivo.readByte();
        int tamanhoRegistro = arquivo.readInt();
        
        int idAtual = arquivo.readInt();
        

        if(idAtual == id && lapideValor == ' '){
          arquivo.seek(lapide);

          if(tamanhoRegistro >= objTam){

              arquivo.writeByte(' ');
              arquivo.writeInt(objTam);
              arquivo.write(ba);

          } else {
            arquivo.seek(lapide);
            arquivo.writeByte('*');
            arquivo.seek(arquivo.length());
            arquivo.writeByte(' ');
            arquivo.writeInt(objTam);
            arquivo.write(ba);
          }
          return true;
        } else{
          arquivo.skipBytes(tamanhoRegistro - 4);
        }
      }

    } catch (Exception e){
      System.out.println("Ocorreu uma excessao: "+ e);
      
    }
    return false;
    
  }
  public T read(int id) throws Exception {
    T l = construtor.newInstance();
    byte[] ba;
    int tam;
    byte lapide;
    arquivo.seek(TAM_CABECALHO);
    while (arquivo.getFilePointer() < arquivo.length()) {


      lapide = arquivo.readByte();
      tam = arquivo.readInt();
      if (lapide == ' ') {
        ba = new byte[tam];
        arquivo.read(ba);
        l.fromByteArray(ba);
        if (l.getId() == id)
          return l;
      } else {
        arquivo.skipBytes(tam);
      }
    }
    return null;
  }
  public boolean delete(int id) throws Exception {
        long endereco =getenderecoid(id);
        arquivo.seek(endereco);
        if (endereco!=-1){
            arquivo.write('*');              
            return true;
        }
    return false;
}
    private long getenderecoid(int id) throws Exception{
        T l = construtor.newInstance();
    byte[] ba;
    int tam;
    byte lapide;
    long endereco;

    arquivo.seek(TAM_CABECALHO);
    while (arquivo.getFilePointer() < arquivo.length()) {
        endereco = arquivo.getFilePointer();
        lapide = arquivo.readByte();
        tam = arquivo.readInt();
        
        if (lapide == ' ') {
            ba = new byte[tam];
            arquivo.read(ba);
            l.fromByteArray(ba);
            if (l.getId() == id) {
                
                return endereco;
            }
        } else {
            arquivo.skipBytes(tam);
        }
    }
    return (long) -1;
    }

  public void close() {
    try {
      if (arquivo != null) {
        arquivo.close();
      }
    } catch (IOException e) {
      System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
    }
  }

  

}