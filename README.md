# TP1-aeds3-espacos-vazios
Primeiro trabalho prático da disciplina Algoritmos e Estruturas de Dados III feito por Lívia Câmara, Sophia Carrazza e Yan Sabarense.

**Experiência do grupo:**
    Nosso grupo implementou todos os requisitos que propomos realizar. A maior dificuldade foram os erros com ponteiros nas funções update e delete, mas os resultados foram alcançados com sucesso após debuggar o código múltiplas vezes e repensar a lógica (de um arquivo apagado apontar para o próximo de espaços vazios até finalizar o arquivo).
**Perguntas:**
- O que você considerou como perda aceitável para o reuso de espaços vazios, isto é, quais são os critérios para a gestão dos espaços vazios?<br/>
    Consideramos que um registro de espaços vazios é reaproveitado quando ele tem tamanho igual ou um pouco maior que o novo inserido (o arquivo inserido deve ocupar pelo menos 50% do resgistro de espaços vazios). <br/>

- O código do CRUD com arquivos de tipos genéricos está funcionando corretamente?
    Sim, os arquivos estão funcionando corretamente com tipos de filme. <br/>

- O CRUD tem um índice direto implementado com a tabela hash extensível?<br/>
    Não, nossa implementação foi feita com a conexão dos registros deletados por ponteiros para reutilizar novas inserções. <br/>

- A operação de inclusão busca o espaço vazio mais adequado para o novo registro antes de acrescentá-lo ao fim do arquivo?<br/>
    Sim, é feita uma condição ao criar o registro para que ele seja inserido nos espaços vazios mais adequados. <br/>

- A operação de alteração busca o espaço vazio mais adequado para o registro quando ele cresce de tamanho antes de acrescentá-lo ao fim do arquivo?<br/>
    Sim, a alteração com aumento de tamanho do registro foi adequada para o pedido no trabalho. <br/>

- As operações de alteração (quando for o caso) e de exclusão estão gerenciando os espaços vazios para que possam ser reaproveitados?<br/>
    Sim. <br/>

- O trabalho está funcionando corretamente?<br/>
    Sim, o trabalho funciona corretamente. <br/>

- O trabalho está completo?<br/>
    Sim, o trabalho está completo. <br/>

- O trabalho é original e não a cópia de um trabalho de um colega?<br/>
    Sim, o trabalho é original e foi desenvolvido por Lívia Câmara, Sophia Carrazza e Yan Sabarense. <br/>
