# TP02 - Índices Indiretos e Relacionamento 1:N

## Relatório de Desenvolvimento do Trabalho Prático 2
### Ana Cristina, Felipe Vilhena, Kenia Teixeira, Lucas Gabriel

---

## Resumo

Este projeto implementa um sistema CRUD com suporte para índices indiretos e relacionamento 1:N entre tarefas e categorias, garantindo integridade e eficiência no acesso aos dados. Utilizamos estruturas de dados avançadas, como a árvore B+, para realizar buscas eficientes e manter a consistência entre as entidades relacionadas, permitindo a recuperação e manipulação de dados de forma otimizada.

---

## Descrição Geral

O sistema é um CRUD aprimorado que implementa índices indiretos e um relacionamento 1:N entre as classes Tarefa e Categoria. A implementação inclui estruturas como árvore B+ e hash extensível para otimização de buscas e garantia de integridade referencial.

---

## Descrição das Classes e Métodos

### Classe Tarefa

A classe Tarefa representa uma tarefa com atributos essenciais, que podem ser associados a uma Categoria através de um identificador de categoria, garantindo o relacionamento 1:N.

#### Atributos:
- **int id**: ID único da tarefa.
- **String nome**: Nome da tarefa.
- **String descricao**: Descrição da tarefa.
- **int idCategoria**: ID da categoria à qual a tarefa pertence (chave estrangeira).

#### Métodos:
- **toBytes()**: Serializa o objeto Tarefa para um array de bytes, facilitando o armazenamento em arquivo.
- **fromBytes(byte[] bytes)**: Reconstrói o objeto Tarefa a partir de um array de bytes.
- **exibirTarefa()**: Exibe as informações da tarefa, incluindo o nome da categoria vinculada, se disponível.

---

### Classe Categoria

A classe Categoria define uma categoria, que pode ter múltiplas tarefas associadas a ela, implementando a parte N do relacionamento 1:N.

#### Atributos:
- **int id**: ID único da categoria.
- **String nome**: Nome da categoria.

#### Métodos:
- **toBytes()**: Serializa o objeto Categoria para um array de bytes.
- **fromBytes(byte[] bytes)**: Reconstrói o objeto Categoria a partir de um array de bytes.
- **exibirCategoria()**: Exibe as informações da categoria, incluindo a lista de tarefas associadas.

---

### Classe Arquivo

A classe abstrata Arquivo define métodos e atributos comuns para a manipulação de arquivos em disco e manipulação dos objetos Tarefa e Categoria.

#### Atributos:
- **String nomeArquivo**: Nome do arquivo onde os dados serão salvos.
- **RandomAccessFile raf**: Acesso ao arquivo.

#### Métodos:
- **inserir(Object obj)**: Insere um novo objeto no arquivo e retorna seu ID.
- **ler(int id)**: Lê um objeto com um ID específico no arquivo.
- **atualizar(int id, Object obj)**: Atualiza um objeto específico no arquivo.
- **remover(int id)**: Remove um objeto do arquivo.
- **listar()**: Lista todos os objetos armazenados no arquivo.

---

### Classe ArquivoCategorias

A classe ArquivoCategorias é especializada para operações de CRUD com categorias e manipulação de tarefas associadas, integrando-se com uma árvore B+ para a indexação e vinculação 1:N.

#### Atributos:
- **ArvoreBMais<NomeId> arvoreB**: Estrutura de árvore B+ que indexa as categorias pelo nome.

#### Métodos:
- **inserirCategoria(Categoria categoria)**: Insere uma nova categoria no arquivo e a indexa na árvore B+.
- **lerCategoria(int id)**: Lê uma categoria específica no arquivo.
- **atualizarCategoria(int id, Categoria categoria)**: Atualiza uma categoria no arquivo.
- **removerCategoria(int id)**: Remove uma categoria e verifica se há tarefas associadas antes de excluí-la.

---

### Classe ArquivoTarefas

A classe ArquivoTarefas gerencia operações de CRUD para tarefas, incluindo métodos para listar tarefas por categoria e garantir que a associação à categoria seja válida.

#### Atributos:
- **ArvoreBMais<NomeId> arvoreB**: Estrutura de árvore B+ para indexar tarefas pelo nome e ID da categoria.

#### Métodos:
- **inserirTarefa(Tarefa tarefa)**: Insere uma nova tarefa e atualiza a árvore B+ com o índice indireto da categoria.
- **lerTarefa(int id)**: Lê uma tarefa específica do arquivo.
- **atualizarTarefa(int id, Tarefa tarefa)**: Atualiza uma tarefa e verifica a validade da categoria.
- **removerTarefa(int id)**: Remove uma tarefa, desvinculando-a da categoria correspondente.
- **listarTarefasPorCategoria(int idCategoria)**: Lista todas as tarefas associadas a uma categoria específica.

---

### Classe NomeIDTarefa

A classe `NomeIDTarefa` representa o vínculo entre o nome de uma tarefa e seu identificador, possibilitando a implementação de um índice indireto para tarefas usando hash extensível.

#### Atributos:
- **String nome**: Nome da tarefa.
- **int idTarefa**: Identificador único da tarefa.
- **short TAMANHO**: Constante que define o tamanho do registro no hash extensível (54 bytes).

#### Métodos:
- **getNome()**: Retorna o nome da tarefa.
- **getIDTarefa()**: Retorna o ID da tarefa.
- **hashCode()**: Retorna o hash gerado a partir do nome, utilizado para inserção e busca na hash extensível.
- **size()**: Retorna o tamanho do registro.
- **toString()**: Retorna uma representação em String da tarefa no formato "(nome;idTarefa)".
- **toByteArray()**: Serializa os atributos do objeto `NomeIDTarefa` para um array de bytes.
- **fromByteArray(byte[] b)**: Deserializa um array de bytes para reconstruir um objeto `NomeIDTarefa`.

---

### Classe Popular

A classe `Popular` é responsável por criar e popular o sistema com categorias iniciais necessárias para o registro de tarefas.

#### Atributos:
- **Arquivo\<Categoria> categorias**: Instância de `Arquivo` para gerenciar o armazenamento de objetos do tipo `Categoria`.
- **Categoria categoria**: Objeto de categoria que será manipulado e adicionado ao sistema.

#### Métodos:
- **create(String nomeCategoria)**: Cria uma nova categoria com o nome especificado e a insere no arquivo de categorias.
- **list()**: Lista todas as categorias existentes no arquivo e as exibe, auxiliando na visualização das categorias disponíveis no sistema.

---
