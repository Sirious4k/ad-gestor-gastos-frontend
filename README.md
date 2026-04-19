# 💸 Gestor de Gastos — App Android

Aplicación móvil nativa para Android que permite registrar, visualizar y eliminar transacciones financieras personales (gastos e ingresos), con autenticación de usuarios y organización por categorías.

---

## 📱 Capturas de pantalla

> 🔜 Próximamente

---

## ✨ Funcionalidades

- 🔐 **Autenticación** — Login y registro de usuarios
- ➕ **Crear transacciones** — Registra gastos e ingresos con monto, fecha, categoría y descripción
- 📋 **Listado de transacciones** — Visualiza todas tus transacciones filtrando por usuario
- 🗑️ **Eliminar transacciones** — Borra cualquier registro con un solo tap
- 🗂️ **Categorías** — Organiza tus transacciones por categoría
- 👤 **Perfil de usuario** — Visualiza y gestiona tu información personal

---

## 🛠️ Stack tecnológico

| Capa | Tecnología |
|------|------------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Arquitectura | MVVM (ViewModel + Repository) |
| Navegación | Navigation Compose |
| HTTP Client | Retrofit 2 + OkHttp 4 |
| Serialización | Gson |
| Async | Kotlin Coroutines |
| Estado | LiveData |
| Min SDK | Android 7.0 (API 24) |
| Target SDK | Android 16 (API 36) |

---

## 🏗️ Arquitectura del proyecto

El proyecto sigue el patrón **MVVM** con separación clara de responsabilidades:

```
app/src/main/java/com/brandon/gestorgastos/
├── api/                    # Definición de endpoints (Retrofit)
│   └── ApiService.kt
├── model/                  # Modelos de datos
│   ├── Transaccion.kt
│   ├── Categoria.kt
│   ├── Usuario.kt
│   ├── TipoTransaccion.kt
│   ├── LoginRequest.kt
│   └── RegisterRequest.kt
├── repository/             # Capa de acceso a datos
│   ├── AuthRepository.kt
│   └── TransaccionRepository.kt
├── viewmodel/              # Lógica de negocio y estado de UI
│   ├── AuthViewModel.kt
│   └── TransaccionViewModel.kt
├── ui/
│   ├── screens/            # Pantallas principales
│   │   ├── LoginScreen.kt
│   │   ├── RegisterScreen.kt
│   │   ├── TransaccionesScreen.kt
│   │   ├── CrearTransaccionScreen.kt
│   │   └── PerfilScreen.kt
│   ├── components/         # Componentes reutilizables
│   │   ├── TransaccionCard.kt
│   │   └── BottomNavBar.kt
│   ├── navigation/         # Configuración de rutas
│   │   └── AppNavigation.kt
│   ├── theme/              # Colores, tipografía y tema
│   └── RetrofitClient.kt
├── utils/
│   └── Utils.kt
└── MainActivity.kt
```

---

## 🚀 Instalación y ejecución

### Prerrequisitos

- Android Studio Hedgehog o superior
- JDK 11+
- Dispositivo o emulador con Android 7.0+ (API 24)
- Backend en ejecución (ver [repositorio del backend](#-repositorio-relacionado))

### Pasos

1. Clona el repositorio:
```bash
git clone https://github.com/BrandonLYP/ad-gestor-gastos-frontend.git
```

2. Abre el proyecto en **Android Studio**

3. Configura la URL del backend en `RetrofitClient.kt`:
```kotlin
private const val BASE_URL = "http://TU_IP:8080/"
```
> ⚠️ Si usas el emulador de Android Studio, reemplaza `localhost` por `10.0.2.2`

4. Sincroniza Gradle y ejecuta la app con **Run ▶**

---

## 🔗 API consumida

La app se conecta al backend REST con los siguientes endpoints:

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/auth/login` | Iniciar sesión |
| `POST` | `/api/auth/register` | Registrar nuevo usuario |
| `GET` | `/api/transacciones?usuarioId={id}` | Listar transacciones del usuario |
| `POST` | `/api/transacciones` | Crear nueva transacción |
| `DELETE`| `/api/transacciones/{id}` | Eliminar transacción |
| `GET` | `/api/categorias` | Obtener categorías disponibles |
| `POST` | `/api/categorias` | Crear nueva categoría |

---

## 📦 Repositorio relacionado

🔙 **Backend:** [ad-gestor-gastos-backend](https://github.com/BrandonLYP/ad-gestor-gastos-backend) — API REST desarrollada con Spring Boot y PostgreSQL

---

## 👨‍💻 Autor

**Brandon** — [@BrandonLYP](https://github.com/BrandonLYP)

---

## 📄 Licencia

Este proyecto está bajo la licencia [MIT](LICENSE).
