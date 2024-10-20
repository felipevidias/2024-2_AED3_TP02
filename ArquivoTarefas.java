import java.io.IOException;
import java.util.ArrayList;

public class ArquivoTarefas extends Arquivo<Tarefa> {
    /* Instanciando o Objeto "ArvoreBMais" */
    ArvoreBMais<ID_com_IDTarefa> arvoreB;

    public ArquivoTarefas() throws Exception {
        super(Tarefa.class.getConstructor(), "arquivoTarefas");
        try {
            arvoreB = new ArvoreBMais<>(ID_com_IDTarefa.class.getConstructor(), 5, "./dados/ArvoreTarefas");
        } catch (Exception e) {
            throw new Exception("Erro na criação da Arvore");
        }
    }

    /* Método de Criação da Tarefa. Retornando o ID */
    @Override
    public int create(Tarefa tarefa) throws Exception {
        int id = super.create(tarefa);
        tarefa.setId(id);
        arvoreB.create(new ID_com_IDTarefa(tarefa.getIDCategoria(), id));
        return id;
    }

    /* Método de Leitura. Lendo os ID's do ArrayList. Retorna as Tarefas */
    public ArrayList<Tarefa> read(NomeId parNomeId) throws Exception {
        ArrayList<Tarefa> t = new ArrayList<>();
        ArrayList<ID_com_IDTarefa> id = arvoreB.read(new ID_com_IDTarefa(parNomeId.getId()));
        for (int i = 0; i < id.size(); i++) {
            t.add(super.read(id.get(i).getId2()));
        }
        return t;
    }

    /*
     * Método de Atualização. Procura o nome da Tarefa desejada para poder muda-la.
     * Retorna booleano.
     */
    public boolean update(NomeId parNomeId, String nomeTarefa, Tarefa updateTarefa) throws Exception {
        ArrayList<Tarefa> t = read(parNomeId);
        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).getNome().equals(nomeTarefa)) {
                updateTarefa.setId(t.get(i).getId());
                break;
            }
        }
        return super.update(updateTarefa);
    }

    /*
     * Método de Delete. Procura pelo nome da Tarefa para ser Deletada. Retorna um
     * booleano.
     */
    public boolean delete(NomeId parNomeId, String nomeTarefa) throws Exception {
        ArrayList<Tarefa> t = read(parNomeId);
        Tarefa delete = null;
        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).getNome().equals(nomeTarefa)) {
                delete = t.get(i);
                break;
            }
        }
        if (delete == null) {
            return false;
        }
        return super.delete(delete.getId()) && arvoreB.delete(new ID_com_IDTarefa(parNomeId.getId(), delete.getId()));
    }

    /* Método Listar as Tarefas */
    public void listar() throws Exception {
        try {
            ArrayList<Tarefa> categorias = super.list();
            if (categorias.isEmpty()) {
                throw new Exception("Categorias ainda não foram criadas");
            }
            for (int i = 0; i < categorias.size(); i++) {
                Tarefa tarefa = categorias.get(i);
                System.out.println("Nome da Tarefa: " + tarefa.getNome() +
                        " Data de Inicio: " + tarefa.getInicio() +
                        " Data de Fim: " + tarefa.getFim() +
                        " Status: " + tarefa.getStatus() +
                        " Prioridade: " + tarefa.getPrioridade());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
