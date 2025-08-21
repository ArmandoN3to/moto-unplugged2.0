# Guia Rápido de Git para a Equipe

## 1\. O que é Git?

Git é um sistema de controle de versão distribuído. Pense nele como um "histórico completo" do seu projeto. Ele permite que você salve "fotos" (chamadas de *commits*) do seu código em diferentes estágios, volte no tempo se algo der errado e, o mais importante, trabalhe em equipe de forma organizada.

## 2\. Principais Nomenclaturas

Antes de partirmos para os comandos, é importante entender alguns termos essenciais:

  * **Repositório (Repo):** É a pasta do seu projeto que está sendo monitorada pelo Git. Contém todo o histórico e os arquivos.
  * **Commit:** É uma "foto" ou um "ponto de salvamento" do seu projeto em um determinado momento. Cada commit tem uma mensagem que descreve as alterações feitas.
  * **Branch:** É uma linha do tempo independente de commits. A `branch` principal (normalmente chamada de `main` ou `master`) contém a versão estável e oficial do projeto. Criamos outras `branches` para trabalhar em novas funcionalidades ou corrigir bugs sem afetar a linha principal.
  * **Merge:** É o ato de juntar o histórico de uma `branch` em outra. Por exemplo, quando uma nova funcionalidade (que estava em sua própria `branch`) fica pronta, fazemos o `merge` dela na `branch` de desenvolvimento.
  * **Remote:** É uma cópia do seu repositório que fica hospedada em um servidor, como o GitHub, GitLab ou Bitbucket. É o que permite que a equipe compartilhe o código.
  * **HEAD:** É um ponteiro que indica em qual `branch` e em qual `commit` você está trabalhando no momento.

## 3\. Comandos Essenciais do Dia a Dia

Aqui estão os comandos que você mais usará. Não se preocupe em decorar todos de uma vez; a prática leva à perfeição\!

| Comando | Descrição |
| :--- | :--- |
| `git clone [URL]` | Baixa uma cópia de um repositório remoto para a sua máquina. |
| `git status` | Mostra o estado atual do seu repositório (quais arquivos foram modificados, quais estão prontos para commit, etc.). |
| `git add [arquivo]` | Adiciona um arquivo modificado à "área de preparação" (*staging area*), preparando-o para o próximo commit. Use `git add .` para adicionar todos os arquivos modificados. |
| `git commit -m "mensagem"` | Salva as alterações que estão na *staging area* como um novo commit. A mensagem deve ser clara e descritiva. |
| `git push` | Envia seus commits locais para o repositório remoto, compartilhando suas alterações com a equipe. |
| `git pull` | Baixa as alterações do repositório remoto e as mescla com a sua versão local. **Sempre execute antes de começar a trabalhar\!** |
| `git branch [nome-da-branch]` | Cria uma nova branch. |
| `git checkout [nome-da-branch]` | Muda para a branch especificada. |
| `git merge [nome-da-branch]` | Junta as alterações da branch especificada na sua branch atual. |

## 4\. Nosso Modelo de Trabalho: Feature Branch Workflow

Para mantermos nosso trabalho organizado, seguiremos um fluxo baseado em *Feature Branches*. A ideia é simples: nunca trabalhamos diretamente nas `branches` principais.

### Nossas Branches Principais:

1.  **`main`**: Esta `branch` representa a versão de produção do nosso projeto. É o código que está funcionando e disponível para o usuário final. Ninguém faz alterações diretas aqui.
2.  **`development`**: Esta `branch` é a nossa base de desenvolvimento. Ela contém todas as funcionalidades já finalizadas e testadas, prontas para serem incluídas na próxima versão de produção.

### Como Funciona o Fluxo:

1.  **Sincronize sua `development` local:** Antes de começar qualquer coisa, garanta que sua `branch` `development` local está atualizada.

    ```bash
    git checkout development
    git pull origin development
    ```

2.  **Crie uma nova `branch` para sua tarefa:** A partir da `development`, crie uma nova `branch` com um nome descritivo para a sua tarefa (seja uma nova funcionalidade ou uma correção).

      * Para uma nova funcionalidade (feature):
        ```bash
        git checkout -b feature/nome-da-funcionalidade
        ```
      * Para uma correção de bug (fix):
        ```bash
        git checkout -b fix/descricao-da-correcao
        ```

3.  **Trabalhe na sua `branch`:** Agora você pode fazer todas as alterações, criar arquivos e fazer commits à vontade. Seu trabalho está isolado e não afeta o de ninguém.

    ```bash
    # Faça suas alterações...
    git add .
    git commit -m "feat: implementa funcionalidade X"
    git push -u origin feature/nome-da-funcionalidade
    ```

4.  **Abra um Pull Request (PR):** Quando terminar o trabalho na sua `branch`, você irá abrir um *Pull Request* (no GitHub/GitLab) para mesclar suas alterações na `branch` `development`. Isso permite que outros membros da equipe revisem seu código antes de ele ser integrado.

5.  **Merge e Deleção:** Após a aprovação do PR, suas alterações são mescladas na `development`. Depois disso, sua `branch` de feature/fix pode ser apagada.

## 5\. Padrão de Commits

Para manter nosso histórico de commits limpo e legível, vamos seguir um padrão chamado **Conventional Commits**. A estrutura de uma mensagem de commit deve ser:

**`<tipo>: <descrição>`**

### Principais Tipos:

  * **`feat`**: Usado quando você adiciona uma nova funcionalidade ao projeto.
  * **`fix`**: Usado para corrigir um bug.
  * **`docs`**: Usado para alterações na documentação.
  * **`style`**: Alterações que não afetam o significado do código (espaços em branco, formatação, etc.).
  * **`refactor`**: Uma alteração de código que não corrige um bug nem adiciona uma funcionalidade.
  * **`chore`**: Outras alterações que não modificam o código-fonte ou os testes (ex: atualização de dependências).

### Exemplos de Boas Mensagens de Commit:

  * `feat: adiciona sistema de login com e-mail e senha`
  * `fix: corrige cálculo de imposto no carrinho de compras`
  * `docs: atualiza o README com instruções de instalação`
  * `refactor: simplifica a lógica do método de validação de usuário`

Seguir esses padrões tornará nosso histórico muito mais fácil de entender no futuro\!

-----

### Modelo de Pull Request
-----
<img width="740" height="773" alt="image" src="https://github.com/user-attachments/assets/3dde558a-1cc1-4178-8311-fab94922db3c" />

