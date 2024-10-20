# TP02 - Índices Indiretos e Relacionamento 1:N

## Relatório de Desenvolvimento do Trabalho Prático 2
### Ana Cristina, Felipe Vilhena, Kenia Teixeira, Lucas Gabriel

## Descrição Geral

Este projeto tem como objetivo estender um sistema CRUD genérico com a implementação de índices indiretos e o relacionamento 1:N entre as entidades `Tarefa` e `Categoria`. Utilizamos estruturas de dados avançadas, como a árvore B+, para realizar buscas eficientes e garantir a integridade entre as entidades relacionadas.

---

## Descrição das Classes e Métodos

### Classe `Tarefa`

A classe `Tarefa` representa as tarefas que serão manipuladas no sistema. Cada tarefa possui atributos como nome, data de início, data de fim, status, prioridade, e um `idCategoria`, que define a qual categoria essa tarefa pertence.

#### Atributos:
- **`int id`**: Identificador único da tarefa.
- **`String nome`**: Nome da tarefa.
- **`LocalDate inicio`**: Data de início da tarefa.
- **`LocalDate fim`**: Data de fim da tarefa.
- **`Byte status`**: Status da tarefa (1 para ativo, 0 para inativo).
- **`Byte prioridade`**: Prioridade da tarefa.
- **`int idCategoria`**: ID da categoria associada à tarefa (chave estrangeira).

#### Métodos:
- **`void setId(int id)`**: Define o ID da tarefa.
- **`void setNome(String nome)`**: Define o nome da tarefa.
- **`void setInicio(LocalDate inicio)`**: Define a data de início da tarefa.
- **`void setFim(LocalDate fim)`**: Define a data de fim da tarefa.
- **`void setStatus(Byte status)`**: Define o status da tarefa.
- **`void setPrioridade(Byte prioridade)`**: Define a prioridade da tarefa.
- **`void setIdCategoria(int idCategoria)`**: Define o ID da categoria associada.
- **`int getId()`**: Retorna o ID da tarefa.
- **`String getNome()`**: Retorna o nome da tarefa.
- **`LocalDate getInicio()`**: Retorna a data de início da tarefa.
- **`LocalDate getFim()`**: Retorna a data de fim da tarefa.
- **`Byte getStatus()`**: Retorna o status da tarefa.
- **`Byte getPrioridade()`**: Retorna a prioridade da tarefa.
- **`int getIdCategoria()`**: Retorna o ID da categoria associada à tarefa.
- **`byte[] toByteArray()`**: Converte a tarefa para um array de bytes para armazenamento.
- **`void fromByteArray(byte[] array)`**: Constrói a tarefa a partir de um array de bytes.

---

### Classe `Categoria`

A classe `Categoria` representa as categorias às quais as tarefas podem ser associadas. Cada categoria possui um nome e um ID único.

#### Atributos:
- **`int id`**: Identificador único da categoria.
- **`String nome`**: Nome da categoria.

#### Métodos:
- **`void setId(int id)`**: Define o ID da categoria.
- **`void setNome(String nome)`**: Define o nome da categoria.
- **`int getId()`**: Retorna o ID da categoria.
- **`String getNome()`**: Retorna o nome da categoria.
- **`byte[] toByteArray()`**: Converte a categoria para um array de bytes para armazenamento.
- **`void fromByteArray(byte[] array)`**: Constrói a categoria a partir de um array de bytes.

---

### Classe `ArquivoTarefas`

A classe `ArquivoTarefas` é uma extensão da classe genérica `Arquivo<Tarefa>` e é responsável pelo gerenciamento do armazenamento de tarefas. Além das operações básicas do CRUD, ela implementa a relação 1:N entre tarefas e categorias, utilizando uma árvore B+ para organizar as tarefas de acordo com o `idCategoria`.

#### Métodos Sobrescritos:
- **`int create(Tarefa tarefa)`**: Cria uma nova tarefa, associando-a a uma categoria válida.
- **`Tarefa read(int id)`**: Lê uma tarefa com base no seu ID.
- **`boolean delete(int id)`**: Exclui uma tarefa, verificando a integridade da categoria associada.
- **`boolean update(Tarefa tarefa)`**: Atualiza uma tarefa existente.

---

### Classe `ArquivoCategorias`

A classe `ArquivoCategorias` é uma extensão da classe genérica `Arquivo<Categoria>` e é responsável pela manipulação das categorias. Ela utiliza uma árvore B+ para organizar as categorias por nome, permitindo a busca por nome e por ID.

#### Métodos Sobrescritos:
- **`int create(Categoria categoria)`**: Cria uma nova categoria.
- **`Categoria read(int id)`**: Lê uma categoria com base no seu ID.
- **`boolean delete(int id)`**: Exclui uma categoria, garantindo que não haja tarefas associadas.
- **`boolean update(Categoria categoria)`**: Atualiza uma categoria existente.

---

### Classe `VisaoTarefas`

A classe `VisaoTarefas` é responsável por toda a interação com o usuário em relação às tarefas. Contém métodos para exibir e coletar dados sobre tarefas.

#### Métodos:
- **`Tarefa leTarefa()`**: Solicita os dados de uma nova tarefa ao usuário.
- **`void mostraTarefa(Tarefa tarefa)`**: Exibe os dados de uma tarefa ao usuário.

---

### Classe `VisaoCategorias`

A classe `VisaoCategorias` é responsável pela interface com o usuário relacionada às categorias.

#### Métodos:
- **`Categoria leCategoria()`**: Solicita os dados de uma nova categoria ao usuário.
- **`void mostraCategoria(Categoria categoria)`**: Exibe os dados de uma categoria ao usuário.

---

### Classe `ControleTarefas`

A classe `ControleTarefas` gerencia a lógica de negócios do sistema, incluindo o relacionamento entre tarefas e categorias, e faz uso das classes de `ArquivoTarefas` e `VisaoTarefas`.

---

### Classe `ControleCategorias`

A classe `ControleCategorias` gerencia as categorias e assegura que as exclusões sejam feitas de maneira segura, verificando se não há tarefas associadas à categoria antes de removê-la.

---

### Experiência de Desenvolvimento

#### Implementação dos Requisitos:
Todos os requisitos foram implementados com sucesso. O sistema gerencia as tarefas e categorias de forma eficiente, utilizando árvores B+ para organização dos dados e garantindo a integridade do relacionamento 1:N.

#### Desafios Enfrentados:
- **Relacionamento 1:N**: Implementar a árvore B+ para gerenciar o relacionamento entre tarefas e categorias foi desafiador, especialmente ao garantir a integridade referencial durante a exclusão de categorias.
- **Índice Indireto**: A implementação do índice indireto por nome de categoria utilizando a árvore B+ exigiu cuidado para garantir que as buscas e atualizações funcionassem corretamente.

#### Resultados Alcançados:
- **O sistema está funcionando conforme esperado.**
- **As operações de inclusão, exclusão, busca e atualização estão funcionando corretamente.**
- **As tarefas são corretamente associadas a categorias e a exclusão de categorias vinculadas a tarefas é bloqueada.**

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

