# LevelUp — GitHub cloud build

هذه النسخة مجهزة للبناء على GitHub Actions، لذلك لا تحتاج AndroidIDE أو جهازًا قويًا.

## من الهاتف فقط
1. أنشئ Repository جديدًا على GitHub.
2. ارفع محتويات هذا المشروع إلى المستودع.
3. افتح تبويب Actions.
4. اختر `Build LevelUp APK`.
5. اضغط `Run workflow`.
6. بعد نجاح البناء افتح نتيجة التشغيل، ثم قسم Artifacts.
7. نزّل `LevelUp-debug-apk` وفك الضغط لتحصل على `app-debug.apk`.

الـ workflow يستخدم Gradle على خادم GitHub ويُخرج APK كـ Artifact.
