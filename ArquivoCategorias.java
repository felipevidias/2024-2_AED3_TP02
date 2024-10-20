import java.io.File;
import java.util.ArrayList;

public class ArquivoCategorias extends Arquivo<Categoria> {
    ArvoreBMais<NomeId> arvoreB;

    /* Criando o Arquivo de Categorias */
    public ArquivoCategorias() throws Exception {
        super(Categoria.class.getConstructor(), "ArquivoCategoria");
        try {
            arvoreB = new ArvoreBMais<>(NomeId.class.getConstructor(), 5, "./dados/ArvoreCategorias");
        } catch (Exception e) {
            throw new Exception();
        }
    }

    /* CRUD DE CATEGORIA */

    /* Método Publico para a criação de Categoria. Retorna a Categoria criada */
    public int create(String nomeCategoria) throws Exception {
        Categoria categoria = new Categoria(nomeCategoria);
        return this.create1(categoria);
    }

    /* Método Privado da criação de Categoria. Retorna o ID da Categoria */
    private int create1(Categoria categoria) throws Exception {
        int id = super.create(categoria);
        categoria.setId(id);
        try {
            arvoreB.create(new NomeId(categoria.getNome(), categoria.getId()));
        } catch (Exception e) {
            // Tratamento de erro na criação da categoria
        }
        return id;
    }

    /*
     * Método de leitura listando as Tarefas da Categoria passada como parametro.
     * Retorna as Tarefas
     */
    public ArrayList<Tarefa> read(String nomeCategoria) throws Exception {
        ArrayList<Tarefa> t = new ArrayList<>();
        ArquivoTarefas tarefas = new ArquivoTarefas();
        try {
            ArrayList<NomeId> categorias = arvoreB.read(new NomeId(nomeCategoria));

            /* Se a Categoria estiver vazia, incapaz de fazer o método */
            if (categorias.isEmpty()) {
                throw new Exception("Categoria inexistente");
            }
            t = tarefas.read(categorias.get(0));
        } catch (Exception e) {
            // Tratamento de erro na leitura do arquivo
        }
        return t;
    }

    /*
     * Método de atualização do nome de uma Categoria. Retornando se foi feito com
     * Sucesso ou Não.
     */
    public boolean update(String nomeCategoria, String novaCategoria) throws Exception {
        Categoria cat = new Categoria(novaCategoria);

        try {
            ArrayList<NomeId> categorias = arvoreB.read(new NomeId(nomeCategoria));
            /* Se a Categoria estiver vazia, incapaz de fazer o método */
            if (categorias.isEmpty()) {
                throw new Exception("Categoria Inexistente");
            }
            cat.setId(categorias.get(0).getId());

            if (super.update(cat)) {
                // Sucesso na atualização
            }

            arvoreB.delete(categorias.get(0));
            arvoreB.create(new NomeId(cat.getNome(), cat.getId()));
        } catch (Exception e) {
            // Tratamento de erro no update
        }

        return true;
    }

    /*
     * Método de atualização chamando o Método Update de Tarefa. Retorna um
     * booleano.
     */
    public boolean updateTarefa(String nomeCategoria, String nomeTarefa, Tarefa updateTarefa) throws Exception {
        ArrayList<NomeId> categorias = arvoreB.read(new NomeId(nomeCategoria));
        ArquivoTarefas tarefas = new ArquivoTarefas();

        try {
            /* Se a Categoria estiver vazia, incapaz de fazer o método */
            if (categorias.isEmpty()) {
                throw new Exception("Categoria Inexistente");
            }

        } catch (Exception e) {
            // Tratamento de erro no updateTarefa
        }

        return tarefas.update(categorias.get(0), nomeTarefa, updateTarefa);
    }

    /* Método de Delete. Procura pelo nome. Retorna Booleano */
    public boolean deleteTarefa(String nomeCategoria, String nomeTarefa) throws Exception {
        ArrayList<NomeId> categorias = arvoreB.read(new NomeId(nomeCategoria));
        ArquivoTarefas tarefas = new ArquivoTarefas();

        try {
            /* Se a Categoria estiver vazia, incapaz de fazer o método */
            if (categorias.isEmpty()) {
                throw new Exception("Categoria Inexistente");
            }
        } catch (Exception e) {
            // Tratamento de erro no delete
        }

        return tarefas.delete(categorias.get(0), nomeTarefa);
    }

    /*
     * Método de Deletar Categoria. Procura pelo nome da Categoria e a deleta.
     * Retorna booleano
     */
    public boolean delete(String nomeCategoria) throws Exception {
        try {
            ArrayList<NomeId> cat = arvoreB.read(new NomeId(nomeCategoria));

            /* Se a Categoria estiver vazia, incapaz de fazer o método */
            if (cat.isEmpty()) {
                throw new Exception("Categoria Inexistente");
            }

            ArquivoTarefas tarefas = new ArquivoTarefas();
            ArrayList<Tarefa> t = tarefas.read(cat.get(0));

            if (!t.isEmpty())
                throw new Exception("Tarefas existentes dentro desta categoria");

            return super.delete(cat.get(0).getId()) ? arvoreB.delete(cat.get(0)) : false;
        } catch (Exception e) {
            // Tratamento de erro ao deletar categoria
        }
        return false;
    }

    /* Listando as Categorias */
    public void listar() throws Exception {
        try {
            ArrayList<Categoria> categorias = super.list();

            if (categorias.isEmpty())
                throw new Exception("Categorias ainda não foram criadas");

            for (int i = 0; i < categorias.size(); i++) {
                System.out.println(
                        "Indice: " + categorias.get(i).getId() + " Nome da Categoria: " + categorias.get(i).getNome());
            }
        } catch (Exception e) {
            // Tratamento de erro ao listar categorias
        }
    }
}
