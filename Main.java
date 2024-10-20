import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws Exception {
    CrudTarefas crudTarefas = new CrudTarefas();
    CrudCategorias crudCategorias = new CrudCategorias();
    Scanner scanf = new Scanner(System.in);
    int resposta = 0;
    while (true) {
      try {
        /* Opções do Menu */
        System.out.println("Inicio");

        System.out.println("1) Tarefas");
        System.out.println("2) Categorias");
        System.out.println("3) Sair");
        resposta = scanf.nextInt();

        /* Switch de acordo com a escolha do usuário */
        switch (resposta) {
          case 1:
            crudTarefas.iniciarTarefas();
            break;
          case 2:
            crudCategorias.iniciarCategoria();
            break;
          case 3:
            scanf.close();
            System.out.println("Saindo...");
            System.exit(0);
          default:
            System.out.println("Opção Inválida");
        }

      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      }
    }
  }

  public static class CrudTarefas {
    public static ArquivoTarefas arquivoTarefas;
    public static ArquivoCategorias arquivoCategorias;
    Scanner scanf = new Scanner(System.in);

    // Método para iniciar as operações de tarefas
    public void iniciarTarefas() throws Exception {
      arquivoTarefas = new ArquivoTarefas();
      arquivoCategorias = new ArquivoCategorias();
      int resposta = 0;
      System.out.println("Inicio > Tarefas");

      System.out.println("1) Incluir");
      System.out.println("2) Buscar");
      System.out.println("3) Alterar");
      System.out.println("4) Excluir");
      System.out.println("5) Retornar ao Menu Anterior");

      resposta = scanf.nextInt();

      switch (resposta) {
        case 1:
          criarTarefa();
          break;
        case 2:
          listarTarefas();
          break;
        case 3:
          atualizarTarefa();
          break;
        case 4:
          Deletar();
          break;
        case 5:
          break;
        default:
          System.out.println("Opção Invalida");
      }
    }

    private void listadeTarefas(String nomeCategoria) throws Exception {
      try {
        ArrayList<Tarefa> t = arquivoCategorias.read(nomeCategoria);

        for (int i = 0; i < t.size(); i++) {
          System.out.println("[" + "Nome da Tarefa: " + t.get(i).getNome() + " || Data de Inicio: "
              + t.get(i).getInicio() + " || Data de Fim: " + t.get(i).getFim() +
              " || Status: " + t.get(i).getStatus() + " || Prioridade: " + t.get(i).getPrioridade() + "]");
        }
      } catch (Exception e) {
        System.out.println(e.getLocalizedMessage());
      }
    }

    public void Deletar() throws Exception {
      String nomeCategoria, nomeTarefa;

      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria a qual pertence a tarefa que deseja deletar");
        arquivoCategorias.listar();
        nomeCategoria = scanf.nextLine();

        System.out.println("Digite o nome da Tarefa que deseja deletar");
        this.listadeTarefas(nomeCategoria);
        nomeTarefa = scanf.nextLine();

        arquivoCategorias.deleteTarefa(nomeCategoria, nomeTarefa);

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    public void listarTarefas() throws Exception {
      String nomeCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria a qual pertence a tarefa que deseja Listar");
        arquivoCategorias.listar();
        nomeCategoria = scanf.nextLine();

        this.listadeTarefas(nomeCategoria);

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    public void atualizarTarefa() throws Exception {
      Tarefa t = new Tarefa();
      String input, nomedaTarefa = "", nomedaCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria a qual ela pertence");
        arquivoCategorias.listar();
        nomedaCategoria = scanf.nextLine();

        this.listadeTarefas(nomedaCategoria);

        while (t.getNome() == null) {
          System.out.println("Digite o nome da tarefa que deseja Alterar");
          nomedaTarefa = scanf.nextLine();
          ArrayList<Tarefa> lista = arquivoCategorias.read(nomedaCategoria);
          for (Tarefa tarefa : lista) {
            if (tarefa.getNome().equals(nomedaTarefa)) {
              t = tarefa;
            }
          }
          if (t.getNome() == null) {
            System.out.println("Tarefa não encontrada, tente novamente");
          }
        }
        System.out.println("Digite seu novo nome");
        t.setNome(scanf.nextLine());

        LocalDate data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (data == null) {
          System.out.println("Digite a data de inicio (No formato dd/MM/yyyy)");
          input = scanf.nextLine();
          try {
            data = LocalDate.parse(input, formatter);
          } catch (Exception e) {
            System.out.println("Data inválida, favor utilizar o formato (dd/MM/yyyy)");
            data = null;
          }
          if (data != null) {
            t.setInicio(data);
          }
        }

        data = null;
        while (data == null) {
          System.out.println("Digite a data do Fim (No formato dd/MM/yyyy)");
          input = scanf.nextLine();
          try {
            data = LocalDate.parse(input, formatter);
          } catch (Exception e) {
            System.out.println("Data inválida, favor utilizar o formato (dd/MM/yyyy)");
            data = null;
          }
          if (data != null) {
            t.setFim(data);
          }
        }

        System.out.println("Digite os Status da nova tarefa (Um numero)");
        t.setStatus((byte) scanf.nextInt());

        System.out.println("Digite a prioridade da nova Tarefa (Um numero inteiro)");
        t.setPrioridade((byte) scanf.nextInt());

        arquivoCategorias.updateTarefa(nomedaCategoria, nomedaTarefa, t);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public void criarTarefa() throws Exception {
      Tarefa t = new Tarefa();
      String input;
      try {
        scanf.nextLine();

        System.out.println("Digite o nome da tarefa");
        t.setNome(scanf.nextLine());

        System.out.println("Digite o índice da categoria que deseja adicionar esta tarefa");
        arquivoCategorias.listar();
        t.setIdCategoria(scanf.nextInt());

        LocalDate data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        scanf.nextLine();
        while (data == null) {
          System.out.println("Digite a data de inicio (No formato dd/MM/yyyy)");
          input = scanf.nextLine();
          try {
            data = LocalDate.parse(input, formatter);
          } catch (Exception e) {
            System.out.println("Data inválida, favor utilizar o formato (dd/MM/yyyy)");
            data = null;
          }
          if (data != null) {
            t.setInicio(data);
          }
        }

        data = null;
        while (data == null) {
          System.out.println("Digite a data do Fim (No formato dd/MM/yyyy)");
          input = scanf.nextLine();
          try {
            data = LocalDate.parse(input, formatter);
          } catch (Exception e) {
            System.out.println("Data inválida, favor utilizar o formato (dd/MM/yyyy)");
            data = null;
          }
          if (data != null) {
            t.setFim(data);
          }
        }

        System.out.println("Digite os Status da tarefa (0 para não iniciado, 1 para em andamento e 2 para finalizado)");
        t.setStatus((byte) scanf.nextInt());

        System.out.println("Digite a prioridade da Tarefa (Um numero inteiro)");
        t.setPrioridade((byte) scanf.nextInt());

        arquivoTarefas.create(t);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static class CrudCategorias {
    public static ArquivoCategorias categoria;
    Scanner scanf = new Scanner(System.in);

    public void iniciarCategoria() throws Exception {
      categoria = new ArquivoCategorias();
      int resposta = 0;
      System.out.println("Inicio > Categorias");

      System.out.println("1) Incluir");
      System.out.println("2) Buscar");
      System.out.println("3) Alterar");
      System.out.println("4) Excluir");
      System.out.println("5) Retornar ao Menu Anterior");

      resposta = scanf.nextInt();

      switch (resposta) {
        case 1:
          criarCategoria();
          break;
        case 2:
          listarCategoria();
          break;
        case 3:
          atualizarCategoria();
          break;
        case 4:
          deletarCategoria();
          break;
        case 5:
          break;
        default:
          System.out.println("Opção Inválida");
          break;
      }
    }

    public void criarCategoria() throws Exception {
      Categoria c = new Categoria();
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da nova Categoria");
        c.setNome(scanf.nextLine());

        categoria.create(c);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public void listarCategoria() throws Exception {
      try {
        System.out.println("Categorias disponíveis:");
        categoria.listar();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    public void atualizarCategoria() throws Exception {
      String nomeCategoria;
      Categoria c = null;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria que deseja alterar");
        listarCategoria();
        nomeCategoria = scanf.nextLine();

        ArrayList<Categoria> categorias = categoria.read();
        for (Categoria cat : categorias) {
          if (cat.getNome().equals(nomeCategoria)) {
            c = cat;
            break;
          }
        }

        if (c != null) {
          System.out.println("Digite o novo nome da Categoria");
          c.setNome(scanf.nextLine());

          categoria.update(nomeCategoria, c);
        } else {
          System.out.println("Categoria não encontrada");
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public void deletarCategoria() throws Exception {
      String nomeCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria que deseja deletar");
        listarCategoria();
        nomeCategoria = scanf.nextLine();

        categoria.delete(nomeCategoria);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // Classe ArquivoTarefas - Exemplo de como pode ser implementada
  public static class ArquivoTarefas {
    // Método para criar tarefa
    public void create(Tarefa t) {
      // Implementar a criação da tarefa
    }

    // Método para listar as tarefas
    public void listar() {
      // Implementar a listagem das tarefas
    }

    // Método para ler tarefas de uma categoria
    public ArrayList<Tarefa> read(String categoria) {
      // Implementar a leitura de tarefas de uma categoria
      return new ArrayList<>();
    }
  }

  // Classe ArquivoCategorias - Exemplo de como pode ser implementada
  public static class ArquivoCategorias {
    // Método para criar categoria
    public void create(Categoria c) {
      // Implementar a criação da categoria
    }

    public ArrayList<App.Tarefa> read(String nomeCategoria) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    // Método para listar categorias
    public void listar() {
      // Implementar a listagem das categorias
    }

    // Método para ler categorias
    public ArrayList<Categoria> read() {
      // Implementar a leitura de categorias
      return new ArrayList<>();
    }

    // Método para deletar tarefa de uma categoria
    public void deleteTarefa(String nomeCategoria, String nomeTarefa) {
      // Implementar a exclusão de tarefa de uma categoria
    }

    // Método para atualizar tarefa em uma categoria
    public void updateTarefa(String nomeCategoria, String nomeTarefa, Tarefa t) {
      // Implementar a atualização da tarefa
    }

    // Método para deletar categoria
    public void delete(String nomeCategoria) {
      // Implementar a exclusão da categoria
    }

    // Método para atualizar categoria
    public void update(String nomeCategoria, Categoria c) {
      // Implementar a atualização da categoria
    }
  }

  // Classe Categoria
  public static class Categoria {
    private String nome;

    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }
  }

  // Classe Tarefa
  public static class Tarefa {
    private String nome;
    private LocalDate inicio;
    private LocalDate fim;
    private byte status;
    private byte prioridade;
    private int idCategoria;

    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public LocalDate getInicio() {
      return inicio;
    }

    public void setInicio(LocalDate inicio) {
      this.inicio = inicio;
    }

    public LocalDate getFim() {
      return fim;
    }

    public void setFim(LocalDate fim) {
      this.fim = fim;
    }

    public byte getStatus() {
      return status;
    }

    public void setStatus(byte status) {
      this.status = status;
    }

    public byte getPrioridade() {
      return prioridade;
    }

    public void setPrioridade(byte prioridade) {
      this.prioridade = prioridade;
    }

    public int getIdCategoria() {
      return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
      this.idCategoria = idCategoria;
    }
  }
}
