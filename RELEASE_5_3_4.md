# Mestre da Tabuada 5.3.5 — RELEASE

## Ajustes desta revisão
- removida a tela/reação de mascote comemorando após acerto
- removida a referência visual ao `mascot_happy`
- Modo treino agora oferece Adição, Subtração, Multiplicação, Divisão e Mistura das 4 operações, além das tabuadas
- cabeçalho das telas ampliado para evitar títulos e subtítulos cortados
- botões com duas linhas receberam altura maior para evitar texto cortado
- textos dos botões foram ajustados para caber melhor em telas menores
- imagem de apresentação do mascote recebeu enquadramento maior e sem corte do conteúdo
- versão `5.3.5` / versionCode `58`

## Release / assinatura
- build `assembleRelease`
- `debuggable false`
- assinatura configurada para usar as Code Signing Identities do Codemagic
- verificação automática da assinatura com `apksigner`
- o keystore não fica dentro do projeto nem deve ser enviado ao GitHub

## Configuração no Codemagic

1. Abra **Team settings → codemagic.yaml settings → Code signing identities → Android keystores**.
2. Faça upload do arquivo `mestre-da-tabuada-release.jks`.
3. Informe a senha do keystore, alias e senha da chave.
4. Use exatamente este **Reference name**:

`mestre_tabuada_release`

5. Faça commit/push desta versão para o GitHub.
6. Execute o workflow **Mestre da Tabuada 5.3.5 RELEASE**.
7. O APK final estará em `app/build/outputs/apk/release/`.

## Importante

O arquivo `.jks` é uma chave privada. **Não coloque o keystore nem `key.properties` no GitHub.** Guarde uma cópia segura do keystore, pois futuras atualizações do app devem continuar usando a mesma chave.
