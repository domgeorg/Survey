# Survey Android App
The Survey Android App is a coding challenge project designed to showcase my expertise in Android development. The application demonstrates the ability to load a questionnaire from the network, allow users to answer questions, and submit their responses. The primary goal is to create a seamless and intuitive survey-taking experience for users.

## This project is thoroughly tested with both UI and unit tests.
________________

## Video

## Architecture

The Survey App employs the Clean Architecture, which is structured into three main layers:

#### Data Layer

The data layer encapsulates the details of data sources and persistence, shielding the internal layers. It is responsible for retrieving and storing data through a defined repository.

#### Domain Layer

The domain layer defines the core elements of the software, independent of specific application layers. It includes business models, rules, entities, and use cases. This layer is crucial as it houses the business logic of our application, managing specific operational rules without knowledge from outer layers. In the domain layer, specific abstract use cases are defined, each with a single responsibility. These use cases encapsulate the business logic of the application, providing a clear separation from the outer layers. They are designed to be versatile and can be utilized across various sections of the application.

#### Presentation Layer

The presentation layer acts as the application's user interface design, involving user interaction and data presentation. Sportscaster follows the Unidirectional Data Flow (UDF) pattern, also known as State Hoisting. This architectural pattern ensures a uni-directional stream of information, where the state descends and the events ascend.

### Architecture Diagram

![Architecture](https://i.imgur.com/5XIgXMM.jpg)

## Core-Modules

### 1. `core-resources`

Encapsulates Android resources (fonts, animations, string values) for clean separation, enhancing maintainability and reusability.

### 2. `core-designsystem`

Centralizes Jetpack Compose UI components and an app-specific design system for consistent UI across the app, simplifying component creation.

### 3. `core-navigation`

Manages navigation events using `NavController`, providing a clean structure and decoupling navigation concerns from other app parts.

### 4. `core-network`

Handles network data fetching logic, ensuring clean separation for easy modification or extension without affecting other app parts.

### 5. `core-data`

Bridges `core-network` and `core-database`, abstracting data retrieval intricacies. Ensures the caller remains agnostic to data origin for a clean, modular architecture.

### 6. `core-domain`

Shapes high-level app functionality, exposing well-defined, abstract use cases with single responsibilities. Enables seamless integration across various app sections, fostering a modular architecture.

### 7. `core-utils`
A module housing extension functions, utility classes, and constants for common tasks, providing reusable components across various core and feature modules.

## Libraries Used

1. **KSP (Kotlin Symbol Processing):** KSP is a Kotlin compiler plugin that facilitates the generation of code during compilation. It's instrumental in reducing boilerplate code and improving overall code generation.

2. **Coroutines-Test:** This library provides testing utilities for Kotlin coroutines, allowing for the efficient testing of asynchronous code in a controlled environment.

3. **MockK:** MockK is a mocking library for Kotlin that simplifies the process of creating mocks for testing. It enhances the ease of writing test cases and improves overall testability.

4. **Hilt:** Hilt is a dependency injection library that is built on top of Dagger. It simplifies the implementation of Dagger for Android apps, making dependency injection more straightforward.

5. **Retrofit:** Retrofit simplifies the process of making network requests. It seamlessly converts HTTP API calls to Kotlin functions.

6. **Moshi:** Moshi is a modern JSON library for Kotlin and Java. It facilitates the parsing of JSON data into Kotlin objects and vice versa, enhancing data serialization and deserialization.

7. **Jetpack Compose:** Jetpack Compose is a modern UI toolkit for building native Android UIs. It simplifies UI development by using a declarative syntax and offers a more intuitive approach to UI design.

8. **Lottie-Compose:** Lottie-Compose is a library for playing Lottie animations in Jetpack Compose. Lottie animations are vector animations that can be easily integrated into Android applications.

9. **Android-Navigation-Compose:** This library simplifies navigation in Jetpack Compose applications, providing a declarative way to navigate between different screens.

10. **AndroidX-Arch-Core-Test:** This library provides testing utilities for AndroidX architecture components, allowing for the efficient testing of components like ViewModels and LiveData.

11. **AndroidX-Compose-Material3:** This library extends Jetpack Compose by providing Material Design 3 components, enhancing the visual and interactive aspects of the app's UI with the latest design principles.

## Icons Attribution

The icons used in the Sportscaster app are sourced from [icons8.com](https://icons8.com/), and all rights are reserved to Icons8.