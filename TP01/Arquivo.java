import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro> {

    private static int TAM_CABECALHO = 12;
    RandomAccessFile arq;
    String nomeArquivo = "";
    Constructor<T> construtor;

    public Arquivo(String nomeArquivo, Constructor<T> construtor) throws Exception {
        this.nomeArquivo = nomeArquivo;
        this.construtor = construtor;
        arq = new RandomAccessFile(nomeArquivo, "rw");
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

        byte[] byteArray = obj.toByteArray();
        short tam = (short) byteArray.length;
        long proxEndereco;
        proxEndereco = arq.readLong();
        if (proxEndereco == 0) {
            arq.seek(arq.length());
            // long endereco = arq.getFilePointer();

            arq.write(' '); // lápide
            arq.writeShort(tam);
            arq.writeLong(-1);

            arq.write(byteArray);
        } else {
            arq.seek(proxEndereco);
            short tamarrayapontado;
            arq.skipBytes(1);
            tamarrayapontado = arq.readShort();

            // proxEndereco = arq.readLong();
            if (tam <= tamarrayapontado) {
                long prox = arq.readLong();
                arq.seek(proxEndereco);
                arq.write(' '); // lápide
                arq.writeShort(tam);

                arq.writeLong(-1);
                arq.write(byteArray);
                if (prox != 0) {
                    arq.seek(4);
                    arq.writeLong(prox);
                } else {
                    arq.seek(4);
                    arq.writeLong(0);
                }
            } else {
                long anteproxEndereco = 4;
                arq.seek(proxEndereco);
                tamarrayapontado = arq.readShort();
                // arq.skipBytes(1);
                // anteproxEndereco = proxEndereco;
                // proxEndereco = arq.readLong();

                while (proxEndereco != 0 || tam > tamarrayapontado) {
                    if (tam >= (tamarrayapontado / 2) && tam <= tamarrayapontado) {
                        arq.seek(proxEndereco);
                        arq.seek(proxEndereco - 1 - 2);
                        tamarrayapontado = arq.readShort();
                        arq.skipBytes(1);
                        anteproxEndereco = proxEndereco;
                        proxEndereco = arq.readLong();
                        break;
                    }

                }
                if (proxEndereco == 0) {
                    arq.seek(anteproxEndereco);
                    arq.writeLong(0);
                    arq.seek(arq.length());
                    arq.write(' '); // lápide
                    arq.writeShort(tam);

                    arq.writeLong(-1);
                    arq.write(byteArray);
                } else {
                    arq.seek(anteproxEndereco + 1 + 2);
                    arq.writeLong(proxEndereco);
                    arq.seek(proxEndereco);
                    arq.write(' '); // lápide
                    arq.writeShort(tam);

                    arq.writeLong(-1);
                    arq.write(byteArray);
                }

            }
        }

        return obj.getId();
    }

    public T read(int id) throws Exception {
        T l = construtor.newInstance();
        byte[] byteArray;
        short tam;
        byte lapide;
        long t;
        arq.seek(TAM_CABECALHO);
        while (arq.getFilePointer() < arq.length()) {

            lapide = arq.readByte();
            tam = arq.readShort();
            t = arq.readLong();
            if (lapide == ' ') {
                byteArray = new byte[tam];
                arq.read(byteArray);
                l.fromByteArray(byteArray);
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
        byte[] byteArray;
        short tam;
        byte lapide;
        long endereco;
        long proxApagado;
        long ultimoApagado;

        arq.seek(TAM_CABECALHO);
        while (arq.getFilePointer() < arq.length()) {
            endereco = arq.getFilePointer();
            lapide = arq.readByte();
            tam = arq.readShort();
            proxApagado = arq.readLong();

            if (lapide == ' ') {
                byteArray = new byte[tam];
                arq.read(byteArray);
                l.fromByteArray(byteArray);
                if (l.getId() == id) {
                    arq.seek(endereco);
                    arq.write('*');

                    // atualiza o ponteiro do próximo registro apagado para o antigo primeiro
                    // registro apagado
                    arq.seek(4);
                    ultimoApagado = arq.readLong();
                    arq.seek(endereco + 2 + 1);
                    arq.writeLong(ultimoApagado);

                    // atualiza o ponteiro do primeiro registro apagado para o registro que tá sendo
                    // deletado
                    arq.seek(4);
                    arq.writeLong(endereco);

                    return true;
                }
            } else {
                arq.skipBytes(tam);
            }
        }
        return false;
    }

    public boolean update(T objAlterado) throws Exception {
        arq.seek(TAM_CABECALHO);

        long proxEndereco = -1;

        while (arq.getFilePointer() < arq.length()) {
            long endereco = arq.getFilePointer();
            byte lapide = arq.readByte();
            short tam = arq.readShort();
            long prox = arq.readLong();

            System.out.println("Endereco: " + endereco + ", Lapide: " + lapide + ", Tam: " + tam + ", Prox: " + prox
                    + ", Arquivo length: " + arq.length());

            if (lapide == ' ') {
                byte[] byteArray = new byte[tam];
                arq.read(byteArray);

                T l = construtor.newInstance();
                l.fromByteArray(byteArray);

                if (l.getId() == objAlterado.getId()) {
                    byte[] byteArray2 = objAlterado.toByteArray();
                    short tam2 = (short) byteArray2.length;

                    if (tam2 <= tam) {
                        // Atualiza os dados do objeto
                        arq.seek(endereco + 1 + 2 + 8);
                        arq.write(byteArray2);
                        return true;
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
                                proxEndereco = prox;
                                if (prox < 0 || prox > arq.length()) {
                                    throw new IOException("prob ponteiro: " + prox);
                                }
                                arq.seek(prox);
                                lapide = arq.readByte();
                                tam = arq.readShort();
                                prox = arq.readLong();

                                if (prox == 0) {
                                    break;
                                }
                            }
                            arq.seek(endereco);
                            arq.writeLong(proxEndereco);
                        }

                        // Encontra espaço para o novo registro
                        arq.seek(arq.length());
                        arq.write(' ');
                        arq.writeShort(tam2);
                        arq.writeLong(-1);
                        arq.write(byteArray2);
                        return true;
                    }
                }
            } else {
                arq.skipBytes(tam);
            }
        }

        return false;
    }

    public void close() throws Exception {
        arq.close();
    }
}
