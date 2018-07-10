Projeto Taurus Mobile
== 

Build:
--
Em ..\app\src\main\res\values\strings.xml

Altere o valor da string  

```xml
<string name="release_apk">XXXXXX</string>
```
Onde XXXXXX é a release corrente do Taurus e segue o seguite padrão: **S180427_RM_01**

No menu Build clique em Build Variants, selecione **release**

Clique em Build > Build APK para criar a compilação de lançamento confirme e verifique em:

```bash
 \app\release\
```


Referências: 
--

https://developer.android.com/studio/publish/app-signing?hl=pt-br#sign-auto