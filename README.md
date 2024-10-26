# TP02 - Índices Indiretos e Relacionamento 1:N

## Relatório de Desenvolvimento do Trabalho Prático 2
### Ana Cristina, Felipe Vilhena, Kenia Teixeira, Lucas Gabriel

## Descrição Geral

Este projeto tem como objetivo estender um sistema CRUD genérico com a implementação de índices indiretos e o relacionamento 1:N entre as entidades `Tarefa` e `Categoria`. Utilizamos estruturas de dados avançadas, como a árvore B+, para realizar buscas eficientes e garantir a integridade entre as entidades relacionadas.

---

## Descrição das Classes e Métodos

### Classe `Arquivo`

A classe `Arquivo` serve para manipulação de registros em arquivos, implementando operações de criação, leitura, exclusão e atualização de registros. Utiliza o mecanismo de hashing extensível para indexação eficiente.

#### Atributos:
- **`T objeto`**: Instância do objeto genérico.
- **`Constructor<T> construtor`**: Construtor da classe do tipo T.
- **`File diretorio`**: Diretório para armazenar o arquivo.
- **`String fileName`**: Nome do arquivo.
- **`int fimCabecalho = 4`**: Índice para mapeamento de IDs e endereços de registro.

#### Métodos:
- **`Arquivo(Constructor<T> construtor, String fN)`**: Construtor.
- **`int create(T objeto)`**: Cria um novo registro no arquivo.
- **`T read(int id)`**: Lê um registro com base no ID.
- **`boolean delete(int id)`**: Exclui logicamente um registro pelo ID.
- **`boolean update(T novoObjeto)`**: Atualiza um registro no arquivo.
- **`ArrayList<T> list()`**: Lista todos os registros ativos no arquivo.

---

### Classe `ArquivoCategorias`

A classe `ArquivoCategorias` é responsável por operações CRUD com categorias e manipulação de tarefas associadas. Herda da classe genérica `Arquivo` e utiliza uma estrutura de árvore B+ para indexação das categorias pelo nome.

#### Atributos:
- **`ArvoreBMais<NomeId> arvoreB`**: Estrutura de árvore B+ para indexação de categorias pelo nome.

#### Métodos:
- **`ArquivoCategorias()`**: Construtor.
- **`int create(String nomeCategoria)`**: Método público para criação de uma nova categoria.
- **`ArrayList<Tarefa> read(String nomeCategoria)`**: Método de leitura das tarefas associadas a uma categoria.
- **`boolean update(String nomeCategoria, String novaCategoria)`**: Método para atualização do nome de uma categoria.
- **`boolean updateTarefa(String nomeCategoria, String nomeTarefa, Tarefa updateTarefa)`**: Método para atualização de uma tarefa associada a uma categoria.
- **`boolean deleteTarefa(String nomeCategoria, String nomeTarefa)`**: Método para exclusão de uma tarefa associada a uma categoria.
- **`boolean delete(String nomeCategoria)`**: Método para exclusão de uma categoria pelo nome.
- **`void listar()`**: Lista todas as categorias.

---

### Classe `ArquivoTarefas`

A classe `ArquivoTarefas` é responsável pelo gerenciamento de tarefas associadas a categorias. Herda da classe genérica `Arquivo` e utiliza uma estrutura de árvore B+ para indexação.

#### Atributos:
- **`ArvoreBMais<ID_com_IDTarefa> arvoreB`**: Estrutura de árvore B+ para indexação de tarefas por ID de categoria e ID de tarefa.

#### Métodos:
- **`ArquivoTarefas()`**: Construtor.
- **`int create(Tarefa tarefa)`**: Método para criação de uma nova tarefa.
- **`ArrayList<Tarefa> read(NomeId parNomeId)`**: Método de leitura das tarefas associadas a uma categoria.
- **`boolean update(NomeId parNomeId, String nomeTarefa, Tarefa updateTarefa)`**: Método para atualização de uma tarefa específica.
- **`boolean delete(NomeId parNomeId, String nomeTarefa)`**: Método para exclusão de uma tarefa específica.
- **`void listar()`**: Lista todas as tarefas registradas no sistema.

---

### Classe `ArvoreBMais`

A classe `ArvoreBMais` armazena pares chave-valor para facilitar o acesso aos registros indexados, com suporte para busca e armazenamento eficiente.

#### Atributos:
- **`int ordem`**: Número máximo de filhos que uma página pode conter.
- **`RandomAccessFile arquivo`**: Arquivo para armazenamento da árvore.
- **`boolean cresceu, diminuiu`**: Variáveis auxiliares para funções recursivas.

#### Métodos:
- **`ArvoreBMais(Constructor<T> c, int o, String na)`**: Construtor.
- **`boolean empty()`**: Verifica se a árvore está vazia.
- **`ArrayList<T> read(T elem)`**: Busca recursiva por um elemento.
- **`boolean create(T elem)`**: Inclusão de novos elementos na árvore.
- **`boolean delete(T elem)`**: Remoção de elementos na árvore.
- **`void print()`**: Imprime a árvore.

---

### Classe `Categoria`

A classe `Categoria` representa as categorias às quais as tarefas podem ser associadas. Cada categoria possui um nome e um ID único.

#### Atributos:
- **`int id`**: Identificador único da categoria.
- **`String nome`**: Nome da categoria.

#### Métodos:
- **`Categoria(String nome)`**: Construtor.
- **`void setId(int id)`**: Define o ID da categoria.
- **`int getId()`**: Retorna o ID da categoria.
- **`String getNome()`**: Retorna o nome da categoria.
- **`byte[] toByteArray()`**: Converte a categoria para um array de bytes.

---

### Classe `HashExtensivel`

A classe `HashExtensivel` implementa uma tabela hash extensível para gerenciamento de registros.

#### Atributos:
- **`String nomeArquivoDiretorio, nomeArquivoCestos`**: Nomes dos arquivos para diretório e cestos.
- **`Diretorio diretorio`**: Estrutura de diretório na memória.

#### Métodos:
- **`boolean create(T elem)`**: Insere um novo elemento na hash extensível.
- **`T read(int chave)`**: Busca um elemento pela chave.
- **`boolean update(T elem)`**: Atualiza os dados de um elemento.
- **`boolean delete(int chave)`**: Remove um elemento.
- **`void close()`**: Fecha diretório e cesto.

---

### Classe `IDEndereço`

A classe `IDEndereço` representa um registro de hash extensível, associando um ID a um endereço de armazenamento.

#### Atributos:
- **`int id`**: Identificador único do registro.
- **`long endereco`**: Endereço associado ao ID.

#### Métodos:
- **`int getId()`**: Retorna o ID do registro.
- **`long getEndereco()`**: Retorna o endereço associado ao ID.
- **`byte[] toByteArray()`**: Converte o registro para um array de bytes.

---

### Classe `NomeId`

A classe `NomeId` representa um registro que armazena um nome e um ID associado, utilizado na árvore B+.

#### Atributos:
- **`String nome`**: Nome associado ao ID.
- **`int id`**: ID associado ao nome.

#### Métodos:
- **`NomeId(String nome, int id)`**: Construtor.
- **`String getNome()`**: Retorna o nome do registro.
- **`int getId()`**: Retorna o ID do registro.
- **`byte[] toByteArray()`**: Converte o registro para um array de bytes.


---

### Classe `Tarefa`

A classe `Tarefa` representa uma tarefa individual associada a uma categoria. Cada tarefa possui um nome, descrição e status.

#### Atributos:
- **`int id`**: Identificador único da tarefa.
- **`String nome`**: Nome da tarefa.
- **`String descricao`**: Descrição detalhada da tarefa.
- **`boolean status`**: Status da tarefa (concluída ou não).

#### Métodos:
- **`Tarefa(String nome, String descricao, boolean status)`**: Construtor que inicializa uma nova tarefa.
- **`void setId(int id)`**: Define o ID da tarefa.
- **`int getId()`**: Retorna o ID da tarefa.
- **`String getNome()`**: Retorna o nome da tarefa.
- **`String getDescricao()`**: Retorna a descrição da tarefa.
- **`boolean isConcluida()`**: Verifica se a tarefa está concluída.
- **`void marcarComoConcluida()`**: Marca a tarefa como concluída.
- **`byte[] toByteArray()`**: Converte a tarefa para um array de bytes.

---

## Estruturas e Técnicas Utilizadas

- **Árvores B+**: Implementamos árvores B+ para indexação eficiente das entidades, principalmente para a busca de categorias e tarefas.
- **Hash Extensível**: Utilizamos uma hash extensível para mapeamento rápido de IDs para endereços de registro, melhorando o desempenho nas operações de leitura.
- **Relacionamento 1:N**: As entidades `Categoria` e `Tarefa` estão relacionadas por meio de uma estrutura que permite associar várias tarefas a uma única categoria, mantendo integridade e consistência nos dados.

---

## Checklist

- O CRUD (com índice direto) de categorias foi implementado? **Sim**
- Há um índice indireto de nomes para as categorias? **Sim**
- O atributo de ID de categoria, como chave estrangeira, foi criado na classe Tarefa? **Sim**
- Há uma árvore B+ que registra o relacionamento 1:N entre tarefas e categorias? **Sim**
- É possível listar as tarefas de uma categoria? **Sim**
- A remoção de categorias checa se há alguma tarefa vinculada a ela? **Sim**
- A inclusão da categoria em uma tarefa se limita às categorias existentes? **Sim**
- O trabalho está funcionando corretamente? **Sim**
- O trabalho está completo? **Sim**
- O trabalho é original e não a cópia de um trabalho de outro grupo? **Sim**

---
