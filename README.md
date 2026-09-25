# Mestre da Tabuada 5.1 — pronto para compilação online

Projeto Android nativo em Java, preparado para gerar APK sem Android Studio usando **GitHub Actions**.

## Gerar APK online pelo GitHub

1. Crie uma conta no GitHub em https://github.com/
2. Crie um repositório novo, por exemplo `MestreDaTabuada`.
3. Extraia este ZIP e envie **todos os arquivos e pastas** para o repositório.
4. No GitHub, abra a aba **Actions**.
5. O workflow **Build APK - Mestre da Tabuada 5.1** será executado automaticamente.
6. Quando terminar, abra a execução concluída.
7. Em **Artifacts**, baixe `MestreDaTabuada-5.1-debug-apk`.
8. Extraia o arquivo ZIP e instale o `app-debug.apk` no celular.

## Dados técnicos
- Application ID: `com.danielmarques.mestredatabuada`
- Namespace: `com.danielmarques.mestredatabuada`
- Version: 5.1
- Compile/Target SDK: 35
- Min SDK: 23
- Gradle: 8.10.2
- Android Gradle Plugin: 8.7.3
- Build: `assembleDebug`

## Observação
O APK de debug é apropriado para testes e instalação direta. Para publicar na Google Play, depois será necessário gerar uma versão **Release assinada** com uma chave própria.

Desenvolvido por Daniel Marques via IA.

## Mestre da Tabuada 5.3 — Identidade visual
- Novo mascote baseado na foto fornecida pelo desenvolvedor.
- Novo ícone do aplicativo com o mascote.
- Mascote integrado à tela de login, início, avatar, conquistas e resultado.
- Assets em `app/src/main/res/drawable/` e ícones nas pastas `mipmap-*`.
