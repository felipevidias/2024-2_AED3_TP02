import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws Exception {
    CrudTarefas crudTarefas = new CrudTarefas();
    CrudCategorias crudCategorias = new CrudCategorias();

    Scanner scanner = new Scanner(System.in);
    int resposta = 0;
    while (true) {
      try {
        /* Printando na telas as Opções */
        System.out.println("---MENU----");
        System.out.println("1) Tarefas...");
        System.out.println("2) Categorias");
        System.out.println("3) Sair......");
        resposta = scanner.nextInt();

        /* Switch de acordo com a escolha do usuário */
        switch (resposta) {
          case 1:
            crudTarefas.iniciarTarefas(scanner);
            break;
          case 2:
            crudCategorias.iniciarCategoria(scanner);
            break;
          case 3:
            scanner.close();
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

    // Método para iniciar as operações de tarefas
    public void iniciarTarefas(Scanner scanner) throws Exception {
      arquivoTarefas = new ArquivoTarefas();
      arquivoCategorias = new ArquivoCategorias();
      int resposta = 0;
      System.out.println("---TAREFAS---");

      System.out.println("1) Incluir");
      System.out.println("2) Buscar");
      System.out.println("3) Alterar");
      System.out.println("4) Excluir");
      System.out.println("5) Menu Principal");

      resposta = scanner.nextInt();

      switch (resposta) {
        case 1:
          criarTarefa(scanner);
          break;
        case 2:
          listarTarefas(scanner);
          break;
        case 3:
          atualizarTarefa(scanner);
          break;
        case 4:
          Deletar(scanner);
          break;
        case 5:
          break;
        default:
          System.out.println("Opção Invalida");
      }
    }

    private void listadeTarefas(Scanner scanner, String nomeCategoria) throws Exception {
      try {
        ArrayList<Tarefa> t = arquivoCategorias.read(nomeCategoria);

        for (int i = 0; i < t.size(); i++) {
          System.out.println("[" + "Nome da Tarefa: " + t.get(i).getNome() + "||" + "Data de Inicio: "
              + t.get(i).getInicio() + "||" + "Data de Fim: " + t.get(i).getFim() + "||" +
              "Status: " + t.get(i).getStatus() + "||" + "Prioridade: " + t.get(i).getPrioridade() + "]");
        }
      } catch (Exception e) {
        System.out.println(e.getLocalizedMessage());
      }
    }

    /* Interface Deletando Tarefa */
    public void Deletar(Scanner scanner) throws Exception {
      String nomeCategoria, nomeTarefa;

      try {
        scanner.nextLine();
        System.out.println("Digite o nome da Categoria a qual pertence a tarefa que deseja deletar");
        arquivoCategorias.listar();
        nomeCategoria = scanner.nextLine();

        System.out.println("Digite o nome da Tarefa que deseja deletar");
        this.listadeTarefas(scanner, nomeCategoria);
        nomeTarefa = scanner.nextLine();

        arquivoCategorias.deleteTarefa(nomeCategoria, nomeTarefa);

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    /* Interface Listando Tarefa */
    public void listarTarefas(Scanner scanner) throws Exception {
      String nomeCategoria;
      try {
        scanner.nextLine();
        System.out.println("Digite o nome da Categoria a qual pertence a tarefa que deseja Listar");
        arquivoCategorias.listar();
        nomeCategoria = scanner.nextLine();

        this.listadeTarefas(scanner, nomeCategoria);

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    /* Interface Atualizando Tarefa */
    public void atualizarTarefa(Scanner scanner) throws Exception {

      Tarefa t = new Tarefa();
      String input, nomedaTarefa = "", nomedaCategoria;
      try {
        /* Evitando Buffer */
        scanner.nextLine();

        /* Scanneando o nome da Categoria */
        System.out.println("Digite o nome da Categoria a qual ela pertence");
        arquivoCategorias.listar();
        nomedaCategoria = scanner.nextLine();

        this.listadeTarefas(scanner, nomedaCategoria);

        while (t.getNome() == null) {
          /* Scanneando o Nome da Tarefa */
          System.out.println("Digite o nome da tarefa que deseja Alterar");
          nomedaTarefa = scanner.nextLine();
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
        t.setNome(scanner.nextLine());

        /* Scanneando a Data de Inicio */
        LocalDate data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (data == null) {
          System.out.println("Digite a data de inicio (No formato dd/MM/yyyy)");
          input = scanner.nextLine();
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
        /* Scanneando a Data Final */
        while (data == null) {
          System.out.println("Digite a data do Fim (No formato dd/MM/yyyy)");
          input = scanner.nextLine();
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

        /* Scanneando o Status da Tarefa */
        System.out.println("Digite os Status da nova tarefa (Um numero)");
        t.setStatus((byte) scanner.nextInt());

        /* Scanneando a Prioridade da Tarefa */
        System.out.println("Digite a prioridade da nova Tarefa (Um numero inteiro)");
        t.setPrioridade((byte) scanner.nextInt());

        arquivoCategorias.updateTarefa(nomedaCategoria, nomedaTarefa, t);

        t = null;

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    /* Interface de Criação da Tarefa */
    public void criarTarefa(Scanner scanner) throws Exception {

      Tarefa t = new Tarefa();
      String input;
      try {
        /* Evitando Buffer */
        scanner.nextLine();

        /* Scanneando o Nome da Tarefa */
        System.out.println("Digite o nome da tarefa");
        t.setNome(scanner.nextLine());

        System.out.println("Digite o índice da categoria que deseja adicionar esta tarefa");
        System.out.println();
        arquivoCategorias.listar();
        System.out.println();
        t.setIdCategoria(scanner.nextInt());

        /* Scanneando a Data de Inicio */
        LocalDate data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        scanner.nextLine();
        while (data == null) {
          System.out.println("Digite a data de inicio (No formato dd/MM/yyyy)");
          input = scanner.nextLine();
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
        /* Scanneando a Data Final */
        while (data == null) {
          System.out.println("Digite a data do Fim (No formato dd/MM/yyyy)");
          input = scanner.nextLine();
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

        /* Scanneando o Status da Tarefa */
        System.out.println("Digite os Status da tarefa (0 para não iniciado, 1 para em andamento e 2 para finalizado)");
        t.setStatus((byte) scanner.nextInt());

        /* Scanneando a Prioridade da Tarefa */
        System.out.println("Digite a prioridade da Tarefa (Um numero inteiro)");
        t.setPrioridade((byte) scanner.nextInt());

        arquivoTarefas.create(t);

        t = null;

      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      }
    }
  }

  public static class CrudCategorias {
    public static ArquivoCategorias arquivoCategorias;

    public void iniciarCategoria(Scanner scanner) throws Exception {
      arquivoCategorias = new ArquivoCategorias();
      int resposta = 0;
      System.out.println("---CATEGORIAS---");

      System.out.println("1) Incluir");
      System.out.println("2) Listar");
      System.out.println("3) Deletar");
      System.out.println("4) Atualizar");
      System.out.println("5) Menu Principal");

      resposta = scanner.nextInt();

      switch (resposta) {
        case 1:
          criarCategoria(scanner);
          break;
        case 2:
          listarCategoria();
          break;
        case 3:
          Deletar(scanner);
          break;
        case 4:
          atualizarCategoria(scanner);
          break;
        case 5:
          break;
        default:
          System.out.println("Opção Invalida");
      }
    }

    private void listarCategoria() throws Exception {
      System.out.println("Lista de Categorias");
      arquivoCategorias.listar();
    }

    /* Interface Criando Categoria */
    public void criarCategoria(Scanner scanner) throws Exception {
      String nome = "";
      try {
        scanner.nextLine();
        System.out.println("Digite o nome da categoria");
        nome = scanner.nextLine();
        arquivoCategorias.create(nome);

      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      }
    }

    /* Interface Atualizando Categoria */
    public void atualizarCategoria(Scanner scanner) throws Exception {
      String nome, novoNome;
      try {
        scanner.nextLine();
        System.out.println("Digite o nome da categoria que deseja atualizar");
        nome = scanner.nextLine();
        System.out.println("Digite o novo nome da categoria");
        novoNome = scanner.nextLine();
        arquivoCategorias.update(nome, novoNome);
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      }
    }

    /* Interface Deletando Categoria */
    public void Deletar(Scanner scanner) throws Exception {
      String nome;
      try {
        scanner.nextLine();
        System.out.println("Digite o nome da categoria que deseja deletar");
        listarCategoria();
        nome = scanner.nextLine();
        arquivoCategorias.delete(nome);
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      }
    }
  }
}
