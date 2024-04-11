import java.io.File;

public class Principal {

    public static void main(String args[]) {
        File file = new File("TP01/filmes.db");
        file.delete();

        Arquivo<Filme> arquivoFilmes;
        Filme filme1 = new Filme(-1, "Barbie", 10, "Cristina", 4.30);
        Filme filme2 = new Filme(-1, "Harry Potter", 11, "Viajuntos", 7.50);
        Filme filme3 = new Filme(-1, "Borboletas", 29, "Juliano", 2.15);
        Filme filme4 = new Filme(-1, "Homem Aranha", 5, "Marvel", 8.30);
        Filme filme5 = new Filme(-1, "Interestrelar", 1, "Viajantes", 7.50);
        Filme filme6 = new Filme(-1, "Enrolados", 19, "Disney", 8.84);
        int idFilme1, idFilme2, idFilme3, idFilme4, idFilme5, idFilme6;

        try {
            arquivoFilmes = new Arquivo<Filme>("filmes.db", Filme.class.getConstructor());

            // CREATE

            // Seção: Criação de Filme 1
            idFilme1 = arquivoFilmes.create(filme1);
            System.out.println("Filme \"" + filme1.getNome() + "\" criado com o ID: " + idFilme1);

            // Seção: Criação de Filme 2
            idFilme2 = arquivoFilmes.create(filme2);
            System.out.println("Filme \"" + filme2.getNome() + "\" criado com o ID: " + idFilme2);

            // Seção: Criação de Filme 3
            idFilme3 = arquivoFilmes.create(filme3);
            System.out.println("Filme \"" + filme3.getNome() + "\" criado com o ID: " + idFilme3);

            // Seção: Criação de Filme 4
            idFilme4 = arquivoFilmes.create(filme4);
            System.out.println("Filme \"" + filme4.getNome() + "\" criado com o ID: " + idFilme4);

            // Seção: Criação de Filme 5
            idFilme5 = arquivoFilmes.create(filme5);
            System.out.println("Filme \"" + filme5.getNome() + "\" criado com o ID: " + idFilme5);

            // Seção: Criação de Filme 6
            idFilme6 = arquivoFilmes.create(filme6);
            System.out.println("Filme \"" + filme6.getNome() + "\" criado com o ID: " + idFilme6);

            // READ

            // Seção: Leitura e Verificação de Filme 1
            if ((filme1 = arquivoFilmes.read(idFilme1)) != null)
                System.out.println("Filme encontrado: " + filme1.getNome() + " (ID: " + filme1.getId() + ")");
            else
                System.out.println("Filme de ID " + idFilme1 + " não encontrado!");

            // Seção: Leitura e Verificação de Filme 2
            if ((filme2 = arquivoFilmes.read(idFilme2)) != null)
                System.out.println("Filme encontrado: " + filme2.getNome() + " (ID: " + filme2.getId() + ")");
            else
                System.out.println("Filme de ID " + idFilme2 + " não encontrado!");

            // Seção: Leitura e Verificação de Filme 3
            if ((filme3 = arquivoFilmes.read(idFilme3)) != null)
                System.out.println("Filme encontrado: " + filme3.getNome() + " (ID: " + filme3.getId() + ")");
            else
                System.out.println("Filme de ID " + idFilme3 + " não encontrado!");

            // Seção: Leitura e Verificação de Filme 4
            if ((filme4 = arquivoFilmes.read(idFilme4)) != null)
                System.out.println("Filme encontrado: " + filme4.getNome() + " (ID: " + filme4.getId() + ")");
            else
                System.out.println("Filme de ID " + idFilme4 + " não encontrado!");

            // Seção: Leitura e Verificação de Filme 5
            if ((filme5 = arquivoFilmes.read(idFilme5)) != null)
                System.out.println("Filme encontrado: " + filme5.getNome() + " (ID: " + filme5.getId() + ")");
            else
                System.out.println("Filme de ID " + idFilme5 + " não encontrado!");

            // Seção: Leitura e Verificação de Filme 6
            if ((filme6 = arquivoFilmes.read(idFilme6)) != null)
                System.out.println("Filme encontrado: " + filme6.getNome() + " (ID: " + filme6.getId() + ")");
            else
                System.out.println("Filme de ID " + idFilme6 + " não encontrado!");

            // UPDATE

            // // Seção: Atualização de Filme 1

            filme1.setNome("Poor Things");
            if (arquivoFilmes.update(filme1))
                System.out.println("Filme de ID " + filme1.getId() + " alterado para: " +
                        filme1.getNome());
            else
                System.out.println("Não foi possível atualizar o filme de ID " +
                        filme1.getId());

            // // Seção: Atualização de Filme 2
            filme2.setNome("AntArestral");
            if (arquivoFilmes.update(filme2))
                System.out.println("Filme de ID " + filme2.getId() + " alterado para: " +
                        filme2.getNome());
            else
                System.out.println("Não foi possível atualizar o filme de ID " +
                        filme2.getId());

            // // Seção: Atualização de Filme 3
            filme3.setNome("Flores");
            if (arquivoFilmes.update(filme3))
                System.out.println("Filme de ID " + filme3.getId() + " alterado para: " +
                        filme3.getNome());
            else
                System.out.println("Não foi possível atualizar o filme de ID " +
                        filme3.getId());

            // // Seção: Atualização de Filme 4
            filme4.setNome("Hulk");
            if (arquivoFilmes.update(filme4))
                System.out.println("Filme de ID " + filme4.getId() + " alterado para: " +
                        filme4.getNome());
            else
                System.out.println("Não foi possível atualizar o filme de ID " +
                        filme4.getId());

            // // Seção: Atualização de Filme 5
            filme5.setNome("Barbie and Dreamhouse");
            if (arquivoFilmes.update(filme5))
                System.out.println("Filme de ID " + filme5.getId() + " alterado para: " +
                        filme5.getNome());
            else
                System.out.println("Não foi possível atualizar o filme de ID " +
                        filme5.getId());

            // // Seção: Atualização de Filme 6
            filme6.setNome("Branca de neve");
            if (arquivoFilmes.update(filme6))
                System.out.println("Filme de ID " + filme6.getId() + " alterado para: " +
                        filme6.getNome());
            else
                System.out.println("Não foi possível atualizar o filme de ID " +
                        filme6.getId());

            // DELETE

            // Seção: Exclusão de Filme 1
            if (arquivoFilmes.delete(filme1.getId()))
                System.out.println("Filme \"" + filme1.getNome() + "\" de ID " + idFilme1 + " excluído!");
            else
                System.out
                        .println("Filme \"" + filme1.getNome() + "\" de ID " + idFilme1 + " não foi possível excluir!");

            // Seção: Exclusão de Filme 2
            if (arquivoFilmes.delete(filme2.getId()))
                System.out.println("Filme \"" + filme2.getNome() + "\" de ID " + idFilme2 + " excluído!");
            else
                System.out
                        .println("Filme \"" + filme2.getNome() + "\" de ID " + idFilme2 + " não foi possível excluir!");

            // Seção: Exclusão de Filme 3
            if (arquivoFilmes.delete(filme3.getId()))
                System.out.println("Filme \"" + filme3.getNome() + "\" de ID " + idFilme3 + " excluído!");
            else
                System.out
                        .println("Filme \"" + filme3.getNome() + "\" de ID " + idFilme3 + " não foi possível excluir!");

            // Seção: Exclusão de Filme 4
            if (arquivoFilmes.delete(filme4.getId()))
                System.out.println("Filme \"" + filme4.getNome() + "\" de ID " + idFilme4 + " excluído!");
            else
                System.out
                        .println("Filme \"" + filme4.getNome() + "\" de ID " + idFilme4 + " não foi possível excluir!");

            // Seção: Exclusão de Filme 5
            if (arquivoFilmes.delete(filme5.getId()))
                System.out.println("Filme \"" + filme5.getNome() + "\" de ID " + idFilme5 + " excluído!");
            else
                System.out
                        .println("Filme \"" + filme5.getNome() + "\" de ID " + idFilme5 + " não foi possível excluir!");

            // Seção: Exclusão de Filme 6
            if (arquivoFilmes.delete(filme6.getId()))
                System.out.println("Filme \"" + filme6.getNome() + "\" de ID " + idFilme6 + " excluído!");
            else
                System.out
                        .println("Filme \"" + filme6.getNome() + "\" de ID " + idFilme6 + " não foi possível excluir!");

            // FECHANDO ARQUIVO
            arquivoFilmes.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
