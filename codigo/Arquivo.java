
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro> {

  private static int TAM_CABECALHO = 12;
  RandomAccessFile arq;
  String nomeArquivo = "";
  Constructor<T> construtor;

  public Arquivo(String na, Constructor<T> c) throws Exception {
    this.nomeArquivo = na;
    this.construtor = c;
    arq = new RandomAccessFile(na, "rw");
    arq.seek(4);
    arq.writeLong(0);
    if (arq.length() < TAM_CABECALHO) {
      arq.seek(0);
      arq.writeInt(0);
    }
  }

  public int create(T obj) throws Exception {
    arq.seek(0);
    int ultimoID = arq.readInt();
    ultimoID++;
    arq.seek(0);
    arq.writeInt(ultimoID);
    obj.setId(ultimoID);

    byte[] ba = obj.toByteArray();
    short tam = (short) ba.length;
    long proxend;
    proxend = arq.readLong();
        if(proxend == 0){
          arq.seek(arq.length());
        // long endereco = arq.getFilePointer();
          
          arq.write(' '); // lápide
          arq.writeShort(tam);
          arq.writeLong(-1);
          
          arq.write(ba);
        }else{
          arq.seek(proxend);
          short tamarrayapontado;
          arq.skipBytes(1);
          tamarrayapontado = arq.readShort();
          
          //proxend = arq.readLong();
          if(tam<=tamarrayapontado){
            long prox = arq.readLong();
            arq.seek(proxend);
            arq.write(' '); // lápide
            arq.writeShort(tam);
            
            arq.writeLong(-1);
            arq.write(ba);
            if (prox != 0){
              arq.seek(4);
              arq.writeLong(prox);
            }else {
              arq.seek(4);
              arq.writeLong(0);
            }
          }else{
            long anteproxend = 4;
            arq.seek(proxend);
            tamarrayapontado = arq.readShort();
            //arq.skipBytes(1);
            //anteproxend = proxend;
            //proxend = arq.readLong();
            
            while (proxend !=0 || tam>tamarrayapontado) {
              arq.seek(proxend);
              arq.seek(proxend-1-2);
              tamarrayapontado = arq.readShort();
              arq.skipBytes(1);
              anteproxend = proxend;
              proxend = arq.readLong();

            }
            if (proxend == 0){
              arq.seek(anteproxend);
              arq.writeLong(0);
              arq.seek(arq.length());
              arq.write(' '); // lápide
              arq.writeShort(tam);
              
              arq.writeLong(-1);        
              arq.write(ba);
            }else{
              arq.seek(anteproxend+1+2);
              arq.writeLong(proxend);
              arq.seek(proxend);
              arq.write(' '); // lápide
              arq.writeShort(tam);
              
              arq.writeLong(-1);        
              arq.write(ba);
            }

          }
        }
        
        
    
        return obj.getId();
      }

  public T read(int id) throws Exception {
    T l = construtor.newInstance();
    byte[] ba;
    short tam;
    byte lapide;
    long t;
    arq.seek(TAM_CABECALHO);
    while (arq.getFilePointer() < arq.length()) {

      lapide = arq.readByte();
      tam = arq.readShort();
      t = arq.readLong();
      if (lapide == ' ') {
        ba = new byte[tam];
        arq.read(ba);
        l.fromByteArray(ba);
        if (l.getId() == id)
          return l;
      } else {
        arq.skipBytes(tam);
      }
    }
    return null;
  }

  public boolean delete(int id) throws Exception {
    T l = construtor.newInstance();
    byte[] ba;
    short tam;
    byte lapide;
    long endereco;
    long posicao;
    long ultimoApagado;

    arq.seek(TAM_CABECALHO);
    while (arq.getFilePointer() < arq.length()) {
        endereco = arq.getFilePointer();
        lapide = arq.readByte();
        tam = arq.readShort();
        posicao = arq.readLong();
        
        if (lapide == ' ') {
            ba = new byte[tam];
            arq.read(ba);
            l.fromByteArray(ba);
            if (l.getId() == id) {
                arq.seek(endereco);
                arq.write('*');
                
                // Atualiza o ponteiro do próximo registro apagado
                arq.seek(4);
                if (arq.length() >= 8) {
                    ultimoApagado = arq.readLong();
                } else {
                    ultimoApagado = 0; // Define o último apagado como 0 se o arquivo estiver vazio ou contiver apenas o cabeçalho
                }
                if (ultimoApagado == 0) {
                    // Se a lista de registros apagados estiver vazia
                    arq.seek(4);
                    arq.writeLong(endereco);
                } else {
                    // Se a lista de registros apagados não estiver vazia
                    while (true) {
                        arq.seek(ultimoApagado + 2 + 1);
                        long proximoApagado = arq.readLong();
                        if (proximoApagado == 0) {
                            // Encontrou o último elemento da lista
                            arq.seek(ultimoApagado);
                            arq.writeLong(endereco);
                            break;
                        }
                        ultimoApagado = proximoApagado;
                    }
                }
                return true;
            }
        } else {
            arq.skipBytes(tam);
        }
    }
    return false;
}



public boolean update(T objAlterado) throws Exception {
  T l = construtor.newInstance();
  byte[] ba;
  short tam;
  byte lapide;
  long endereco;
  long prox;
  long proxend = 0; // Inicializa com 0

  arq.seek(TAM_CABECALHO);
  while (arq.getFilePointer() < arq.length()) {
      endereco = arq.getFilePointer();

      lapide = arq.readByte();
      tam = arq.readShort();
      prox = arq.readLong();

      if (lapide == ' ') {
          ba = new byte[tam];
          arq.read(ba);
          l.fromByteArray(ba);
          if (l.getId() == objAlterado.getId()) {
              byte[] ba2 = objAlterado.toByteArray();
              short tam2 = (short) ba2.length;
              if (tam2 <= tam) {
                  // Atualiza os dados do objeto
                  arq.seek(endereco + 1 + 2 + 8);
                  arq.write(ba2);
                  return true; // Retorna verdadeiro se a atualização for bem-sucedida
              } else {
                  // Primeiro apaga o registro antigo
                  arq.seek(endereco);
                  arq.write('*');

                  // Atualiza o ponteiro do próximo registro
                  if (prox == 0) {
                      arq.seek(4);
                      arq.writeLong(endereco);
                  } else {
                      while (prox != 0) {
                          proxend = prox;
                          arq.seek(prox);
                          lapide = arq.readByte();
                          tam = arq.readShort();
                          prox = arq.readLong();
                          if (prox == 0) {
                            break; // Adiciona uma verificação extra para evitar seek com valor 0
                        }
                      }
                      arq.seek(proxend);
                      arq.writeLong(endereco);
                  }

                  // Encontra espaço para o novo registro
                  prox = proxend;
                  while (prox != 0) {
                      arq.seek(prox);
                      arq.skipBytes(1);
                      short tamarrayapontado = arq.readShort();
                      prox = arq.readLong();

                      if (prox == 0 || tam2 <= tamarrayapontado) {
                          break;
                      }
                  }
                  // Agora proxend aponta para o próximo registro livre ou para o final do arquivo
                  arq.seek(prox);
                  arq.write(' '); // lápide
                  arq.writeShort(tam2);
                  arq.writeLong(-1);
                  arq.write(ba2);
                  return true; // Retorna verdadeiro após a atualização bem-sucedida
              }
          }
      } else {
          arq.skipBytes(tam);
      }
  }
  return false; // Retorna falso se o objeto não for encontrado para atualização
}










  public void close() throws Exception {
    arq.close();
  }
}

