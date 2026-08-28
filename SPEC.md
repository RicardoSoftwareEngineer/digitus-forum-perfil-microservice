<!-- para IA. não é README de humano. -->
# SPEC — perfil

status: v0.2
sha: `a963282`
data: 2026-08-28

## Como usar
- Este arquivo é a fonte. Código ≠ spec → **bug de código**. Spec errada → Ricardo muda **este** arquivo, depois o código.
- IDs estáveis (`REGRA-` `DADOS-` `CONTRATO-` `NÃO-` `GAP-`). Não apague ID; marque `revogado`.
- "achei bug" → cita REGRA/CONTRATO. Se não existir, é GAP, não patch.
- "não estamos salvando X" → olha DADOS. Campo ausente = não é bug.
- "cadastrar campo X" → conflita se quebra REGRA/NÃO; senão vira GAP e só então código.
- GAP = pergunta aberta. Não trate GAP como regra.

## Papel
MS **interno** (porta `8088`). Perfil é a “cara” do user (aluno pode ter mais de um). Sem auth HTTP.

## REGRA
- REGRA-PERFIL-1: perfil persistido: `perfilId`, `userId`, `name`, `type`, `lastTimeUsed`, `deleted`.
- REGRA-PERFIL-2: perfil **pertence** a um `userId`. `belongToUser` é a pergunta canônica (retorna o perfil se a dupla id+userId existe).
- REGRA-PERFIL-3: produto quer um “último perfil usado”. Código hoje: `lastTimeUsed` **nunca é escrito**; `retrieveLastUsed` devolve o primeiro da lista ou **cria** um perfil default (`aluno novo` / `STUDENT`).
- REGRA-PERFIL-4: sem borda `/firewall/perfil/...` hoje. Só outros MS chamam.

## NÃO
- NÃO-EXPOSE
- NÃO-SHUTDOWN
- NÃO-CURSO: perfil não guarda módulos/vídeos.

## DADOS
| id | tabela | campos |
|---|---|---|
| DADOS-PERFIL | Perfil | perfilId, userId, lastTimeUsed, name, type, deleted |

## CONTRATO
- `/perfil/v1/create` · `/retrieve` · `/{id}/retrieve` · `/{id}/belongToUser/{userId}` · `/retrieve/lastUsed` · `/{id}/update` · `/{id}/delete`
- health `/perfil/v1/healthCheck`

## GAP
- GAP-BORDA: produto precisa de CRUD de perfil na internet? se sim, entram ENDs no firewall (com token + só o próprio userId).
- GAP-COURSE-BELONG: course MS tem `checkIfThisPerfilBelongsToThisUser` **sempre false** e ignora o resultado. A spec de *perfil* é REGRA-PERFIL-2; o bug é no **course** (GAP lá).
