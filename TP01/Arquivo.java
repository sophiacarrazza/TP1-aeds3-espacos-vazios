
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro> {

  protected RandomAccessFile arquivo;
  protected Constructor<T> construtor;

  String nomeArquivo = "";
  final protected int TAM_CABECALHO = 4;

  public Arquivo(String filmes, Constructor<T> c) throws Exception {
    this.nomeArquivo = filmes;
    this.construtor = c;

    arquivo = new RandomAccessFile(filmes, "rw");
    arquivo.setLength(0); // Limpa o conteúdo do arquivo
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

    arquivo.seek(TAM_CABECALHO);
    while (arquivo.getFilePointer() < arquivo.length()) {
      byte lapide = arquivo.readByte();
      int tamanho_antigo = arquivo.readInt();
      if (lapide == '*' && (tamanho_antigo >= tam && tamanho_antigo <= tam * 1.5)) { // Se o tamanho do registro
                                                                                     // deletado for suficiente (50% a
                                                                                     // mais do que o necessário)

        int id_deletado = arquivo.readInt(); // id do registro deletado
        arquivo.write(' ');
        arquivo.writeInt(tam);
        arquivo.write(ba);
        obj.setId(id_deletado);
        return obj.getId();
      }
      arquivo.skipBytes(tamanho_antigo);
    }
    if (arquivo.getFilePointer() == arquivo.length()) { // Se chegou no final do arquivo
      arquivo.write(' ');
      arquivo.writeInt(tam);
      arquivo.write(ba);

    }
    return obj.getId();
  }

  public boolean update(T obj) throws Exception {
    int id = obj.getId();
    long endereco = getenderecoid(id);
    if (endereco != -1) {
      byte[] ba = obj.toByteArray();
      int tam = ba.length;

      arquivo.seek(endereco + 1); // pula o byte de lapide
      int tamanho_antigo = arquivo.readInt();

      if (tam > tamanho_antigo) { // se o novo tamanho for maior

        // procura por outro espaço vazio maior ou igual ao novo tamanho
        while (arquivo.getFilePointer() < arquivo.length()) {
          arquivo.readByte();
          int tamanho = arquivo.readInt();
          if (tamanho_antigo >= tam && tamanho_antigo <= tam * 1.5) { // encontrou espaço vazio suficiente
            long novoEndereco = arquivo.getFilePointer() - 5; // volta para o início do registro
            arquivo.seek(novoEndereco);
            arquivo.write(' ');
            arquivo.writeInt(tam);
            arquivo.write(ba);
            return true;
          } else {
            arquivo.skipBytes(tamanho);
          }
        }
        // se nenhum espaço vazio suficiente foi encontrado, adicionar novo registro no
        // final
        arquivo.seek(arquivo.length());
        arquivo.write(' ');
        arquivo.writeInt(tam);
        arquivo.write(ba);
        return true;
      } else {
        // se o novo tamanho for menor ou igual, atualiza no mesmo registro
        arquivo.seek(endereco + 5); // vai para onde os dados estão armazenados
        arquivo.write(ba);
        return true;
      }
    }
    return false; // se o registro com o ID fornecido não foi encontrado
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
    long endereco = getenderecoid(id);
    if (endereco != -1) {
      arquivo.seek(endereco);
      arquivo.write('*');
      return true;
    }
    return false;
  }

  private long getenderecoid(int id) throws Exception {
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