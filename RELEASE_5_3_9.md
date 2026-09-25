# Mestre da Tabuada 5.3.9 — Google Play

## Preparação
- `compileSdk 36`
- `targetSdk 36`
- `versionCode 62`
- `versionName 5.3.9`
- Release sem debug
- Codemagic gera `APK` e `AAB`
- AAB é verificado com `jarsigner`

## Conteúdo mantido
- Modo Treino com lista 1–10 em Adição, Subtração, Multiplicação e Divisão
- Mistura das 4 operações
- Matemática avançada
- Conquistas
- Avatar/loja
- Imagem principal corrigida para não cortar a arte

## Google Play
O arquivo a ser enviado ao Play Console é o `.aab` gerado em `app/build/outputs/bundle/release/`.
