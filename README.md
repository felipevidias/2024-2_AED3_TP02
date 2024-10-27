# TP02 - Índices Indiretos e Relacionamento 1:N

## Relatório de Desenvolvimento do Trabalho Prático 2
### Ana Cristina, Felipe Vilhena, Kenia Teixeira, Lucas Gabriel

## Resumo

Este projeto implementa um sistema CRUD com suporte para índices indiretos e relacionamento 1:N entre tarefas e categorias, garantindo integridade e eficiência no acesso aos dados. Utilizamos estruturas de dados avançadas, como a árvore B+, para realizar buscas eficientes e manter a consistência entre as entidades relacionadas, permitindo a recuperação e manipulação de dados de forma otimizada.

---

## Descrição Geral

Este projeto tem como objetivo estender um sistema CRUD genérico com a implementação de índices indiretos e o relacionamento 1:N entre as entidades `Tarefa` e `Categoria`. Utilizamos estruturas de dados avançadas, como a árvore B+, para realizar buscas eficientes e garantir a integridade entre as entidades relacionadas.

---
## Descrição das Classes e Métodos

### Classe Tarefa
...

---

### Classe `Arquivo`
...

---

### Classe `ArquivoCategorias`

A classe `ArquivoCategorias` é responsável por operações CRUD com categorias e manipulação de tarefas associadas. Herda da classe genérica `Arquivo` e utiliza uma estrutura de árvore B+ para indexação das categorias pelo nome. Esta árvore B+ facilita a criação de um relacionamento 1:N entre categorias e tarefas, permitindo a associação e a busca eficiente de todas as tarefas relacionadas a uma determinada categoria.

#### Atributos:
- **`ArvoreBMais<NomeId> arvoreB`**: Estrutura de árvore B+ para indexação de categorias pelo nome.

#### Métodos:
...

---

### Classe `ArquivoTarefas`

A classe `ArquivoTarefas` é responsável pelo gerenciamento de tarefas associadas a categorias. Herda da classe genérica `Arquivo` e utiliza uma estrutura de árvore B+ para indexação. Este índice possibilita o relacionamento 1:N entre tarefas e categorias, mantendo o sistema organizado e evitando duplicidade ou inconsistência nos dados de tarefas vinculadas.

#### Atributos:
...

---

## Estruturas e Técnicas Utilizadas

- **Árvores B+**: Implementamos árvores B+ para indexação eficiente das entidades, especialmente útil para buscar categorias e tarefas com alta velocidade em grandes volumes de dados. Elas garantem a integridade referencial e facilitam o relacionamento 1:N.
- **Hash Extensível**: Utilizamos uma hash extensível para mapeamento rápido de IDs para endereços de registro, o que melhora o desempenho nas operações de leitura e escrita, facilitando o gerenciamento dos dados.

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

