<!-- para IA. não é README de humano. -->
# SPEC — perfil

status: v0
sha: `a963282`
data: 2026-08-28

## Como usar
- Este arquivo é a fonte. Código ≠ spec → **bug de código**. Spec errada → Ricardo muda **este** arquivo, depois o código.
- IDs estáveis (`INV-` `DADOS-` `END-` `NÃO-` `GAP-`). Não apague ID; marque `revogado`.
- "achei bug" → cita INV/END. Se não existir, é GAP, não patch.
- "não estamos salvando X" → olha DADOS. Campo ausente = não é bug.
- "cadastrar campo X" → conflita se quebra INV/NÃO; senão vira GAP e só então código.
- GAP = pergunta aberta. Não trate GAP como regra.

## Papel
MS **interno** (porta `8088`). Perfil é a “cara” do user (aluno pode ter mais de um). Sem auth HTTP.

## INV
- INV-PERFIL-1: perfil persistido: `perfilId`, `userId`, `name`, `type`, `lastTimeUsed`, `deleted`.
- INV-PERFIL-2: perfil **pertence** a um `userId`. `belongToUser` é a pergunta canônica (retorna o perfil se a dupla id+userId existe).
- INV-PERFIL-3: `lastUsed` é o perfil mais recente daquele user.
- INV-PERFIL-4: sem borda `/firewall/perfil/...` hoje. Só outros MS chamam.

## NÃO
- NÃO-EXPOSE
- NÃO-SHUTDOWN
- NÃO-CURSO: perfil não guarda módulos/vídeos.

## DADOS
| id | tabela | campos |
|---|---|---|
| DADOS-PERFIL | Perfil | perfilId, userId, lastTimeUsed, name, type, deleted |

## END
- `/perfil/v1/create` · `/retrieve` · `/{id}/retrieve` · `/{id}/belongToUser/{userId}` · `/retrieve/lastUsed` · `/{id}/update` · `/{id}/delete`
- health `/perfil/v1/healthCheck`

## GAP
- GAP-BORDA: produto precisa de CRUD de perfil na internet? se sim, entram ENDs no firewall (com token + só o próprio userId).
- GAP-COURSE-BELONG: course MS tem `checkIfThisPerfilBelongsToThisUser` **sempre false** e ignora o resultado. A spec de *perfil* é INV-PERFIL-2; o bug é no **course** (GAP lá).
