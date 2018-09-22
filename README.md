# mailbox_entre_processos
Comunicação entre processos usando o mecanismo de comunicação indireta. implementar o modelo de Mailbox.

Para enviar uma mensagem utilizar o seguinte padrão: "enviar:remetente:destinatário:mensagem", exemplo: "enviar:1:2:olá mundo",
nesse exemplo a mensagem "olá mundo" está sendo enviada do remetente 1 para o destinatário 2.

Para receber todas as mensagens de sua mailbox utilizar o seguinte padrão: "receber:destinatŕario", exemplo: "receber:2", nesse 
exemplo o destinatário 2 vai receber todas as mensagens que foram enviadas para ele.
a mailbox guarda todas as mensagens em um ArrayList, e retorna todas, sem apagar nenhuma. 
