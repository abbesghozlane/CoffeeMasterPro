# CoffeeMasterPro

Android Kotlin + Jetpack Compose skeleton for Coffee Master Pro

This is a starter project created by the assistant. It includes:

- Gradle Kotlin build scripts
- Jetpack Compose setup
- Core data models (UserModels, ReportModels, CapitalModels)
- PermissionsManager
- ExportManager with Pdf/Docx generator stubs
- UI components: Button3D and basic theme

How to open

- Open the `CoffeeMasterPro` folder in Android Studio (recommended) or build with Gradle.

Build & run (notes)

 - Recommended: Open the project in Android Studio, let it sync Gradle, then run on an emulator or device.
 - From PowerShell (requires Android SDK/gradle wrapper):

```powershell
# from project root
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug   # installs to a connected device/emulator
```

Build on GitHub (no Android Studio required)
-----------------------------------------

If you can't build locally, push the project to a GitHub repository and use the included GitHub Actions workflow to build the debug APK automatically. Steps:

1. Create a new repository on GitHub and push the current project to it (from your project root):

```powershell
git init
git add .
git commit -m "Initial commit: CoffeeMasterPro"
git branch -M main
git remote add origin https://github.com/<your-username>/<repo-name>.git
git push -u origin main
```

2. Once pushed, go to the repository on GitHub → Actions → run the "Android CI - Build APK" workflow (or it will run automatically on push). The workflow will build the debug APK and upload it as an artifact named `app-debug-apk`.

3. Download the artifact from the Actions run and install it on your tablet:

```powershell
adb install -r <path-to-downloaded>/app-debug-apk/app-debug.apk
```

This is the recommended path if you don't have Android Studio installed locally.

Notes:
- The PDF/DOCX generators are placeholders; replace with full implementations before production.
- You need Android SDK and appropriate platform tools installed to build/run.

Notes

- PDF and DOCX generators are placeholders. Replace with real implementations (iText or Apache POI code) when integrating.
- Add navigation and ViewModels as next steps.
