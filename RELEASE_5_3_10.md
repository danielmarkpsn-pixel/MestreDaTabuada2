# Mestre da Tabuada 5.3.10 — Correção do Modo Treino

- VersionCode: 63
- VersionName: 5.3.10
- compileSdk: 36
- targetSdk: 36

## Correção principal
O Modo Treino agora possui uma lógica própria, separada de “As 4 operações”.

Ao escolher uma operação e uma lista de 1 a 10, a lista escolhida passa a controlar diretamente os exercícios de treino.
- Adição: lista N pratica N + 1 até N + 10.
- Subtração: lista N pratica (N + K) - K, com resultado sempre não negativo.
- Multiplicação: lista N pratica N × 1 até N × 10.
- Divisão: lista N pratica (N × K) ÷ N, sempre com resultado inteiro.
- Matemática/“As 4 operações” continua usando sua geração geral e independente.

A imagem principal, assinatura Release, targetSdk 36 e geração de APK/AAB da versão anterior foram preservados.
