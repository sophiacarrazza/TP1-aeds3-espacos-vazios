import java.io.*;

class Principal {

  public static void main(String args[]) {

    File f = new File("filmes.db");
    f.delete();

    Arquivo <Filme> arqFilmes;
    Filme l1 = new Filme(-1, "Barbie", 10, "Cristina", 4.30);
    Filme l2 = new Filme(-1, "Harry Potter", 11, "Joana", 6.00);
    Filme l3 = new Filme(-1, "Borboletas", 29, "Juliano", 2.15);
    Filme l4 = new Filme(-1, "Homem Aranha", 5, "Marvel", 8.30);
    Filme l5 = new Filme(-1, "Interestrel", 1, "Viajantes", 7.50);
    Filme l6 = new Filme(-1, "Enrolados", 19, "Disney", 8.84);
    int id1, id2, id3, id4, id5, id6;

    try {
        arqFilmes= new Arquivo<>("filmes.db", Filme.class.getConstructor());

      id1 = arqFilmes.create(l1);
      System.out.println("Filme criado com o ID: " + id1);

      id2 = arqFilmes.create(l2);
      System.out.println("Filme criado com o ID: " + id2);

      id3 = arqFilmes.create(l3);
      System.out.println("Filme criado com o ID: " + id3);

      id4 = arqFilmes.create(l4);
      System.out.println("Filme criado com o ID: " + id4);

      id5 = arqFilmes.create(l5);
      System.out.println("Filme criado com o ID: " + id5);

      if ((l3 = arqFilmes.read(id3)) != null)
        System.out.println("Filme encontrado: " + l3.getNome());
      else
        System.out.println("Filme de ID " + id3 + " não encontrado!");

      if (arqFilmes.delete(l4.getId()))
        System.out.println("Filme de ID " + id4 + " excluido!");
      else
        System.out.println("Filme de ID " + id4 + " não foi possivel excluir!");

      

      //l5.setNome("Interestrelar");
      //if (arqFilmes.update(l5))
      //  System.out.println("Filme de ID " + l5.getId() + " Nome: " +  l5.getNome() + " alterado!");
      //else
       // System.out.println("Filme de ID " + l5.getId() + " Nome: " +  l5.getNome() + " não foi possivel atualizar!");
      if (arqFilmes.delete(l5.getId()))
        System.out.println("Filme de ID " + id5 + " excluido!");
      else
        System.out.println("Filme de ID " + id5 + " não foi possivel excluir!");
      
      
      id6 = arqFilmes.create(l6);
      System.out.println("Filme criado com o ID: " + id6);
      //if ((l3 = arqFilmes.read(id2)) != null)
      //  System.out.println(l3);
      //else
      //  System.out.println("Filme de ID " + id2 + " não encontrado!");

      arqFilmes.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
